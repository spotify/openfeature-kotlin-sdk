#!/bin/bash

# Inspired by Sailr
# https://github.com/craicoverflow/sailr/blob/master/sailr.sh

release_tag=main

# checks that jq is usable
function check_jq_exists_and_executable {
if ! [ -x "$(command -v jq)" ]; then
  echo -e "\`commit-msg\` hook failed. Please install jq."
  exit 1
fi
}

# check if the config file exists
# if it doesnt we dont need to run the hook
function check_config {
  if [[ ! -f "$CONFIG" ]]; then
    echo -e "Pre-commit config file is missing!"
    exit 0
  fi
}

function set_config {
  CONFIG="$PWD/conventional-commit-config.json"
}

# set values from config file to variables
function set_config_values() {
  enabled=$(jq -r .enabled "$CONFIG")

  if [[ ! $enabled ]]; then
    exit 0
  fi

  revert=$(jq -r .revert "$CONFIG")
  types=($(jq -r '.types[]' "$CONFIG"))
  min_length=$(jq -r .length.min "$CONFIG")
  max_length=$(jq -r .length.max "$CONFIG")
}

# build the regex pattern based on the config file
function build_regex() {
  set_config_values

  regexp="^[.0-9]+$|"

  if $revert; then
      regexp="${regexp}^([Rr]evert|[Mm]erge):? )?.*$|^("
  fi

  for type in "${types[@]}"
  do
    regexp="${regexp}$type|"
  done

  regexp="${regexp%|})(\(.+\))?: "

  regexp="${regexp}.{$min_length,$max_length}$"
}


# Print out a standard error message which explains
# how the commit message should be structured
function print_error() {
  commit_message=$1
  regular_expression=$2
  echo -e "\n\033[31m[Invalid Commit Message]"
  echo -e "------------------------\033[0m\033[0m"
  echo -e "Valid types: \033[36m${types[@]}\033[0m"
  echo -e "Max length (first line): \033[36m$max_length\033[0m"
  echo -e "Min length (first line): \033[36m$min_length\033[0m\n"
  echo -e "\033[37mRegex: \033[33m$regular_expression\033[0m"
  echo -e "\033[37mActual commit message: \033[33m\"$commit_message\"\033[0m"
  echo -e "\033[37mActual length: \033[33m$(echo $commit_message | wc -c)\033[0m\n"
}

set_config

# check if the repo has a config file
check_config

# make sure jq is installed
check_jq_exists_and_executable

# get the first line of the commit message
if [[ ! -f "$1" ]]; then
  START_LINE="$(git log --oneline --format=%B -n 1 HEAD | head -n 1)"
else
  INPUT_FILE=$1
  START_LINE=`head -n1 $INPUT_FILE`
fi

build_regex

if [[ ! $START_LINE =~ $regexp ]]; then
  # commit message is invalid according to config - block commit
  print_error "$START_LINE" $regexp
  exit 1
fi
