# Support Scripts

This folder contains scripts used to support the development.

## `conventional-commits.sh`

The [`conventional-commits.sh`](scripts/conventional-commits.sh) is a simple bash script that helps us enforce the conventional commits convention on our development flow.

To install it as a pre-commit hook you can simply:

```bash
cp scripts/conventional-commits.sh .git/hooks/commit-msg && chmod +x .git/hooks/commit-msg
```