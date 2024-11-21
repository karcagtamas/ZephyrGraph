# Backend

## Language Server requirements

The running environment need to have some configuration for the correct dependency resolution.

For the settings, you need to build the graph-model library with gradle: `gradle build` for project `backend:libs:graph-model` and the result `graph-model-2.4.0.jar` from the project's `/build/libs` folder.

### Windows

Need to place a file with the give path: `%HOMEPATH%\.config\kotlin-language-server\classpath.bat` with the content:

```cmd
@echo off
echo %HOMEPATH%\.config\kotlin-language-server\lib\graph-model-2.4.0.jar
```

And place the built `graph-model-2.4.0.jar` next to the bat file into a `lib` folder.

### Linux

Need to place a file with the give path: `~/.config/kotlin-language-server/classpath` with the content:

```bash
#!/bin/bash
echo ~/.config/kotlin-language-server/lib/graph-model-2.4.0.jar
```

And place the built `graph-model-2.4.0.jar` next to the bat file into a `lib` folder.

## Build application

Pre-requisites: You need to install `gradle` and `make`.

Open the `./backend` folder and run `make gradle-build-lin` and `make gradle-build-graph-model-lin` commands.

After the execution you need to use the `./backend/build/install/CauseEffectGraph-shadow`.

## Setup the environment

For the language server you need to install the Kotlin stdlib globally and must be callable from the console.

Also need to copy the `./backend/language-server` folder next the excutable file into the `./backend/build/install/CauseEffectGraph-shadow/bin`.

## Run application

After the build the server is runnable. You need to run the executable in the `./backend/build/install/CauseEffectGraph-shadow/bin`. If the system is Linux then the file is the `CauseEffectGraph`, on Windows the `CauseEffectGraph.bat` is the executable.

For the production execution you need to pass the `prod` argument. F.i.: `./CauseEffectGraph prod`
