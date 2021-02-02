# Directory `.bach`

The `.bach` directory contains all Bach-related assets.

## Directory `.bach/bin`

The `.bach/bin` directory contains scripts and modules used to build modular Java projects.

- `bach`: Launch script for Linux and MacOS
- `bach.bat`: Launch script for Windows
- `Bootstrap.java`: Bach's platform-agnostic build program used by GitHub Actions workflows.
- `com.github.sormuras.bach@VERSION.jar`: Module `com.github.sormuras.bach`

## Directory `.bach/configuration`

The `.bach/configuration` directory contains sources of the configuration module for this project.
If this directory does not exist, Bach is running in "zero-configuration" mode.

## Directory `.bach/external-modules`

The `.bach/external-modules` directory contains all external modules required to build this project.
An external module is a module that neither is declared by the current project, nor a module that is provided by the Java Runtime.