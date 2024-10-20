FROM ubuntu:latest as build

RUN apt-get update
RUN apt-get install openjdk-21-jdk -y

WORKDIR /dist

COPY ./build/install/CauseEffectGraph-shadow/. .

COPY ./libs/graph-model/build/libs/. /home/ubuntu/.config/kotlin-language-server/lib/.
COPY ./classpath /home/ubuntu/.config/kotlin-language-server/.

FROM build AS core

WORKDIR /dist/bin

EXPOSE 8080

ENTRYPOINT [ "./CauseEffectGraph", "prod" ]