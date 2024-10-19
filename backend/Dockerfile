FROM ubuntu:latest as build

RUN apt-get update
RUN apt-get install openjdk-21-jdk -y

WORKDIR /dist

COPY ./build/install/CauseEffectGraph-shadow/. .

EXPOSE 8080

FROM build AS core

WORKDIR /dist/bin

ENTRYPOINT [ "./CauseEffectGraph", "prod" ]