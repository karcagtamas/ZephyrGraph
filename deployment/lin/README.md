# Run the ZephyrGraph

## Prerequisites

To run the application on a Linux system, you need to prepeare the environment.

### Java

For the executing the application, you need to install Java21 OpenJDK to the system.

#### Ubuntu / Debian

To install Java21 OpenJDK on Ubuntu, you need to use the following command:

```sh
sudo apt install openjdk-21-jdk
```

#### Others

You need to search on the internet for other Linux distributions.

#### Test

To test the successfullness of the installation, you need to run the next command:

```sh
java -version
```

If you get any result, the Java is successfully installed.

### Kotlin Compiler

The other required tool is the Kotlin Compiler. This is used by the Kotlin Language Server for the Kotlin Script compilation.

#### Linux

To install Kotlin Compiler on any Linux Distributions, you can use the following commands:

```sh
sdk install kotlin
```

or

```sh
brew update
brew install kotlin
```

or

```sh
sudo snap install --classic kotlin
```

## Workspace folder

Create a `ceg-workspace` folder on the root of the drive system (like `/ceg-workspace`).

## Set environment variable

There is a `.config` folder under the `application-lin`. Create a new Environemnt variable with name `XDG_CONFIG_HOME` and set the value to the folder of the `.config`.

If the `.config` folder is in the `/application-lin` then the setting should be `XDG_CONFIG_HOME` = `/application-lin/.config`.

## Run

When everything is ready, you can run the application using the `run.sh` executable. Open the folder of the application (`application-lin`) and start the next command:

```sh
./run.sh
```

If the server successfully started, it is reachable on the port `8080` under `localhost`.

## Use

Open any modern browser and enter the `localhost:8080`. It is ready to use. Have fun.
