# Run the ZephyrGraph

## Prerequisites

To run the application on a Windows system, you need to prepeare the environment.

### Java

For the executing the application, you need to install Java21 Oracle to the system. To do this, you need to download from [here](https://www.oracle.com/java/technologies/downloads/#jdk21-windows) and install.

#### Test

To test the successfullness of the installation, you need to run the next command in the Terminal:

```cmd
java -version
```

If you get any result, the Java is successfully installed.

### Kotlin Compiler

The other required tool is the Kotlin Compiler. This is used by the Kotlin Language Server for the Kotlin Script compilation.

You need to open the releases of the Kotlin Compiler on [Github](https://github.com/JetBrains/kotlin/releases/tag/v2.0.21) and download the `kotlin-compiler-2.0.21.zip` compressed file.

To install it, you need to unarchive the file to somewhere and its path to the environment variables. Append the path of the `bin` folder in `kotlinc` folder to the `Path` variable.

## Workspace folder

Create a `ceg-workspace` folder on the root of the Drive where the application will be run.

If the program is on the `C` drive, then the wolder should be the `C:/ceg-workspace`.

## Set environment variable

There is a `.config` folder under the `application-win`. Create a new Environemnt variable with name `XDG_CONFIG_HOME` and set the value to the folder of the `.config`.

If the `.config` folder is in the `C:/application-win` then the setting should be `XDG_CONFIG_HOME` = `C:/application-win/.config`.

## Run

When everything is ready, you can run the application using the `run.bat` executable. Open the folder of the application (`application-win`) and start the next command:

```cmd
./run.bat
```

If the server successfully started, it is reachable on the port `8080` under `localhost`.

## Use

Open any modern browser and enter the `localhost:8080`. It is ready to use. Have fun.
