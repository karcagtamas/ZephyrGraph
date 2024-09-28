# Backend

## Language Server requirements

The running environment need to have some configuration for the correct dependency resolution.

For the settings, you need to build the graph-model library with gradle: `gradle build` for project `backend:libs:graph-model` and the result `graph-model-1.0.0.jar` from the project's `/build/libs` folder.

### Windows

Need to place a file with the give path: `%HOMEPATH%\.config\kotlin-language-server\classpath.bat` with the content:

```cmd
@echo off
echo %HOMEPATH%\.config\kotlin-language-server\lib\graph-model-1.0.0.jar
```

And place the built `graph-model-1.0.0.jar` next to the bat file into a `lib` folder.

### Linux

Need to place a file with the give path: `~/.config/kotlin-language-server/classpath` with the content:

```bash
#!/bin/bash
echo /~/.config/kotlin-language-server/lib/graph-model-1.0.0.jar
```

And place the built `graph-model-1.0.0.jar` next to the bat file into a `lib` folder.