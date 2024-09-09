# Thesis

- This is my Thesis repository what contains the codebase, notes and documentation

## Platform

The root of the project is the remote Ktor webserver. Ktor is a Kotlin based server application what support a DSL (Domain Specific Language) structured code base and solutions. The server is the computing center of the application. It is parsing the source graph codes into a well-structured Kotlin model and this model is converted to unit tests through a converting pipeline.

The web server provides a web client as a SPA (Single Page Application). This web client is originally written in ReactJS what is a JavaScript based library.

## Build the application

### Build prerequisities

For the application you need to have a running instance of [Docker](https://www.docker.com/) (it's supported on Windows and Linux operation systems). The build scripts will create a assembled, runnable and deployable docker image from the code repository.

### Windows

Run the `./build.bat` in the terminal (the current folder should be the root of the repository).

### Linux

Run the `./build` in the terminal (the current folder should be the root of the repository).

## Build the documentation

The documentation is written in LaTeX format. For the build you need to run the following make command: XY in the `/docs` folder.
