# OpenFeature Kotlin SDK

Kotlin implementation of the OpenFeature SDK


## Commit Messages

Our commit messages follows the [Conventional Commits](https://www.conventionalcommits.org/en/v1.0.0/) conventions.

A CI check will enforce that you follow this conventions. If you wish to have a pre-commit hook setup up locally you can run:

```
cp scripts/conventional-commits.sh .git/hooks/commit-msg && chmod +x .git/hooks/commit-msg
```


## Formatting

This repo uses [ktlint](https://github.com/JLLeitschuh/ktlint-gradle) for formatting. 

Please consider adding a pre-commit hook for formatting using 

```
./gradlew addKtlintCheckGitPreCommitHook
```
Manual formatting is done by invoking
```
./gradlew ktlintFormat
```
