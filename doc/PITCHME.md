---?image=https://upload.wikimedia.org/wikipedia/commons/0/0e/Bach_Seal.svg&size=cover&opacity=5
@title[Bach.java]
@snap[midpoint span-100]
# Bach@color[gray](.java)
#### Lightweight Java Build Tool for modular projects
@snapend
@snap[south span-100 text-07 text-blue]
😉 Christian Stein &nbsp; 🎼 [github.com/sormuras/bach](https://sormuras.github.io/bach) &nbsp; 📰 [sormuras.de](http://sormuras.de)
@snapend
Note:
- Hello.
- My name is...
- Your build tools are "overkill".
- Prepare to (die) yet another one.

+++?image=https://raw.githubusercontent.com/sormuras/sormuras.github.io/master/asset/img/avatar-sormuras-1000-1000.jpg&size=cover&opacity=23
@snap[north span-90]
## .about
@snapend
@snap[west span-40]
@ul[list-no-bullets](false)
- **Christian Stein**
- [Micromata](https://www.micromata.de) @note[Customised software solutions.]
- [OpenJDK](https://openjdk.java.net/census#cstein) @note[JDK Project – Author]
- [JUnit 5](https://junit.org/junit5) @note[Team member since 2018]
- [Apache Maven](https://maven.apache.org) @note[Maven plugin: compiler, surefire]
@ulend
@snapend
@snap[east span-40 text-center]
<a href="https://www.micromata.de"><img src="https://github.com/sormuras/testing-in-the-modular-world/raw/master/img/micromata-logo-horizontal.png" height="120" /></a>
<br>
<a href="https://junit.org/junit5"><img src="https://github.com/sormuras/testing-in-the-modular-world/raw/master/img/junit5-logo.png" height="60" /></a>
&nbsp;
<a href="https://maven.apache.org"><img src="https://github.com/sormuras/testing-in-the-modular-world/raw/master/img/maven-logo-black-on-white.png" height="60" /></a>
@snapend
@snap[south span-100 text-07 text-blue]
🐦 [@sormuras](https://twitter.com/sormuras) &nbsp; 📰 [sormuras.de](http://sormuras.de)
@snapend
Note:
- I work for Micromata, crafting customised software solutions.
- Found a P2 bug in `java.exe` [JDK-8234076](https://bugs.openjdk.java.net/browse/JDK-8234076)
- JUnit Team member since 2017
- Also an Apache Maven Committer [maven-compiler-plugin](https://github.com/apache/maven-compiler-plugin/pull/4)

---?image=https://upload.wikimedia.org/wikipedia/commons/0/0e/Bach_Seal.svg&size=cover&opacity=5
@snap[north span-90]
## Intro
@snapend
@snap[midpoint span-90]
@ul[list-no-bullets](false)
- **Intro**
- @css[text-gray](Demo)
- @css[text-gray](Features)
- @css[text-gray](Model)
- @css[text-gray](Outlook)
@ulend
@snapend
Note:
- Overview, Agenda, Plan for this talk

+++
@snap[north span-90]
### Status Quo
@snapend
@snap[midpoint span-90]
@ul[list-no-bullets]
- Many build tools in the wild
- JDK does **not** provide a build tool
- Which tools does the JDK offer?
@ulend
@snapend
@snap[south span-100 text-07 text-blue]
Bach.java - Intro
@snapend
Note:
- Show of hands: shell scripts, make, Ant, Maven, Gradle, Bazel, Buck, ...
- Why doesn't the JDK provide a build tool out of the box?
- A build tool in the scope of this talk is a program that "shuffles" the
  tools offered by the JDK in the right order, passing the right options,
  to produce consumable ... Java modules.

+++
@snap[north span-90]
### JDK Tools in 2020
@snapend
@snap[midpoint span-90]
@ul[list-no-bullets text-08](false)
- **1**: `javac` Compiler for the Java programming language
- **1**: `javap` Class file disassembler
- **1**: `javadoc` API documentation generator
- **1**: `java` Launcher for Java applications
- **1**: `jar` Java Archive (JAR) file manager
- **8**: `jdeps` Class dependency analyzer
- **8**: `jdeprscan` Deprecated API use finder
- **9**: `jlink` Custom runtime image assembler
- **9**: `jmod` JMOD file manager
- **14**: `jpackage` Package self-contained Java applications
@ulend
@snapend
@snap[south span-100 text-07 text-blue]
Bach.java - Intro
@snapend
Note:
- Here are the main or foundation tools shipping with JDK

+++?image=doc/img/jdk-and-build-tools.svg&size=90% auto
@title[JDK and Build Tools]
@snap[north span-90]
### JDK and Build Tools
@snapend
@snap[south span-100 text-07 text-blue]
Bach.java - Intro
@snapend

+++?image=doc/img/jdk-and-build-tools-with-bach.svg&size=90% auto
@title[JDK, Bach.java, Tools]
@snap[north span-90]
### JDK and Build Tools
@snapend
@snap[south span-100 text-07 text-blue]
Bach.java - Intro
@snapend

+++
@snap[north span-90]
### Just Java
@snapend
@snap[midpoint span-90]
@ul[text-08](false)
- Lightweight JDK tool juggler
- Only `.java` files as sources
- That's it.
@ulend
@snapend
@snap[south span-100 text-07 text-blue]
Bach.java - Intro
@snapend

+++
@snap[north span-90]
### No tricks
@snapend
@snap[midpoint span-90]
@ul[text-08](false)
- No .xml, .yaml, ... .z?!
- No .groovy, .kts, ...
- No daemons, no cache services, ...
@ulend
@snapend
@snap[south span-100 text-07 text-blue]
Bach.java - Intro
@snapend

---?image=https://upload.wikimedia.org/wikipedia/commons/0/0e/Bach_Seal.svg&size=cover&opacity=5
@snap[north span-90]
## Demo
@snapend
@snap[midpoint span-90]
@ul[list-no-bullets](false)
- Intro ✔
- **Demo**
- @css[text-gray](Features)
- @css[text-gray](Model)
- @css[text-gray](Outlook)
@ulend
@snapend

+++
@snap[north span-90]
### Demo Project
@snapend
@snap[midpoint span-90]
@ul[list-no-bullets](false)
- `src/demo/module-info.java`
- `module demo {}`
@ulend
@snapend
@snap[south span-100 text-07 text-blue]
Bach.java - Demo
@snapend

+++
@snap[north span-90]
### Live or GIF
@snapend
@snap[midpoint span-90]
1. `build.bat`
1. `build.jsh`
1. `jshell https://bit.ly/bach-build`
@snapend
@snap[south span-100 text-07 text-blue]
Bach.java - Demo
@snapend

Note:
- From source to module in 6 steps
- Created with DOS batch script
- Recorded with https://blog.bahraniapps.com/gifcam-6-0/

+++?image=doc/screenplay/demo-1-build.gif&size=auto 90%
@snap[north-east]
### Demo 1
@snapend
@snap[east]
@ol[text-08]
1. Declare module
1. `build.bat`
1. Tree sources
1. Build!
1. Tree binaries
1. Describe module
@olend
@snapend
@snap[south span-100 text-07 text-blue]
Bach.java - Demo 1 - build.bat
@snapend

Note:
- From source to module in 6 steps
- Using DOS `.bat` syntax

+++?image=doc/screenplay/demo-2-jshell.gif&size=auto 90%
@snap[north-east]
### Demo 2
@snapend
@snap[east]
@ol[text-08]
1. Declare module
1. `build.jsh`
1. Tree sources
1. Build!
1. Tree binaries
1. Describe module
@olend
@snapend
@snap[south span-100 text-07 text-blue]
Bach.java - Demo 2 - jshell build.jsh
@snapend

Note:
- From source to module in 6 steps
- Using JShell syntax stored in `.jsh` file

+++?image=doc/screenplay/demo-3-bach.gif&size=auto 90%
@snap[north-east]
### Demo 3
@snapend
@snap[east]
@ol[text-08]
1. Declare module
1. *NOOP*
1. Tree sources
1. Build!
1. Tree binaries
1. Describe module
@olend
@snapend
@snap[south span-100 text-07 text-blue]
Bach.java - Demo 3 - jshell https://bit.ly/bach-build
@snapend

Note:
- From source to module in 6 steps
- Using no extra script file

+++
@snap[north]
### Demo 99
@snapend
@snap[midpoint span-90]
@ol
1. `jshell https://bit.ly/bach-init`
1. `bach scaffold`
1. Build!
@olend
@snapend
@snap[south span-100 text-07 text-blue]
Bach.java - Demo 99 - jshell https://bit.ly/bach-init
@snapend

Note:
- Show scaffolding
- `bach[.bat]` as short-cuts to `.bach/src/Bach.java`
- `Bach.java` loads module `de.sormuras.bach` via JitPack

+++
@snap[north]
### Other Tools
@snapend
@snap[midpoint span-90]
@table[table-header table-fragment text-06](doc/comparison/minimal.csv)
@snapend
@snap[south span-100 text-07 text-blue]
Bach.java - Demo - Comparison Minimal
@snapend

+++
@snap[north]
### Project 99
@snapend
@snap[midpoint span-90]
@table[table-header table-fragment text-06](doc/comparison/ninetynine.csv)
@snapend
@snap[south span-100 text-07 text-blue]
Bach.java - Demo - Comparison 99
@snapend

---?image=https://upload.wikimedia.org/wikipedia/commons/0/0e/Bach_Seal.svg&size=cover&opacity=5
@snap[north span-90]
## Features
@snapend
@snap[midpoint span-90]
@ul[list-no-bullets](false)
- Intro ✔
- Demo ✔
- **Features**
- @css[text-gray](Model)
- @css[text-gray](Outlook)
@ulend
@snapend

+++?include=doc/slides/features/PITCHME.md

---?image=https://upload.wikimedia.org/wikipedia/commons/0/0e/Bach_Seal.svg&size=cover&opacity=5
@snap[north span-90]
## Model
@snapend
@snap[midpoint span-90]
@ul[list-no-bullets](false)
- Intro ✔
- Demo ✔
- Features ✔
- **Model**
- @css[text-gray](Outlook)
@ulend
@snapend

+++
@snap[north span-90]
### Module, Packages, and Types
@snapend
@snap[midpoint span-90]
@ul[text-07](false)
- Module `de.sormuras.bach`
- Package `de.sormuras.bach.project`
- `Project` with basic properties
- `Structure` describes directory layout
  - `Folder`
  - `Realm` like main, test, ...
  - `Unit`
    - `Source`
  - `Library` manages 3rd-party modules
@ulend
@snapend
@snap[south span-100 text-07 text-blue]
Bach.java - Model
@snapend

---?image=https://upload.wikimedia.org/wikipedia/commons/0/0e/Bach_Seal.svg&size=cover&opacity=5
@snap[north span-90]
## Outlook
@snapend
@snap[midpoint span-90]
@ul[list-no-bullets](false)
- Intro ✔
- Demo ✔
- Features ✔
- Model ✔
- **Outlook**
@ulend
@snapend

+++
@snap[north span-90]
### Plan for Bach.java 2.1
@snapend
@snap[midpoint span-90]
@ul[list-no-bullets](false)
- Revise Model
- Support Annotation Processors
- Support `jpackage`
- External tools
  - Google-Java-Format
  - Error-Prone
  - Code Coverage
@ulend
@snapend
@snap[south span-100 text-07 text-blue]
Bach.java - Outlook
@snapend

+++
@snap[north span-90]
### Plan for Bach.java X, ...
@snapend
@snap[midpoint span-90]
@ul
- From `Bach.java` ...
- to `{JDK_HOME}/bin/jbach[.exe]`?
- 😉 `jbach`, `jbuild`, `javab`
- Create a JEP?
@ulend
@snapend
@snap[south span-100 text-07 text-blue]
Bach.java - Outlook
@snapend

---
@snap[north span-90]
# Thanks.
#### Happy building!
@snapend
@snap[midpoint span-90 text-08 text-center]
@ul[list-no-bullets](false)
- `Bach.java` is **Java** Build Tool
- `jshell https://bit.ly/bach-build`
@ulend
@snapend
@snap[south span-100 text-07 text-blue]
😉 Christian Stein &nbsp; 🎼 [github.com/sormuras/bach](https://sormuras.github.io/bach) &nbsp; 🐦 [@sormuras](https://twitter.com/sormuras)
@snapend

---?image=https://upload.wikimedia.org/wikipedia/commons/0/0e/Bach_Seal.svg&size=cover&opacity=5
@snap[north span-90]
## Backup
@snapend
@snap[midpoint span-90]
@ul[list-no-bullets](false)
- Intro ✔
- Demo ✔
- Features ✔
- Model ✔
- Outlook ✔
- **Backup**
@ulend
@snapend

+++
@snap[north span-90]
### Demo: Bach + JavaFX
@snapend
@snap[midpoint span-90]
- https://github.com/sormuras/bach-javafx
@snapend
@snap[south span-100 text-07 text-blue]
Bach.java - Backup
@snapend

+++
@snap[north span-90]
### Projects
@snapend
@snap[midpoint span-90]
@ul[list-no-bullets](false)
- [bach](https://github.com/sormuras/bach) @note[Use jshell/java to build your modular Java project]
- [modules](https://github.com/sormuras/modules) @note[Java modules published at Maven Central]
- [junit-platform-maven-plugin](https://github.com/sormuras/junit-platform-maven-plugin) @note[Maven Plugin launching the JUnit Platform]
- [mainrunner](https://github.com/sormuras/mainrunner) @note[JUnit Platform Test Engine launching Java programs]
- [install-jdk.sh](https://github.com/sormuras/bach#install-jdksh) @note[Install the latest-and-greatest OpenJDK releases, used by Travis CI]
- [download-jdk](https://github.com/sormuras/download-jdk) @note[GitHub Action]
@ulend
@snapend
@snap[south span-100 text-07 text-blue]
Bach.java - Backup
@snapend