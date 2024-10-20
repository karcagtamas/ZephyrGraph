FROM gradle:8.5-jdk21 AS build

RUN apt-get update

ENV XDG_CONFIG_HOME=/workspace/.config

RUN apt-get install curl wget unzip zip -y
RUN curl -s "https://get.sdkman.io" | bash
SHELL ["/bin/bash", "-c"]
RUN source "/root/.sdkman/bin/sdkman-init.sh" && sdk install kotlin

WORKDIR /dist

COPY ./build/install/CauseEffectGraph-shadow/. .

COPY ./libs/graph-model/build/libs/. $XDG_CONFIG_HOME/kotlin-language-server/lib/.
COPY ./classpath $XDG_CONFIG_HOME/kotlin-language-server/.
RUN chmod 777 $XDG_CONFIG_HOME/kotlin-language-server/classpath

COPY ./language-server/. ./bin/language-server/.


FROM build AS core

WORKDIR /dist/bin

EXPOSE 8080

ENV PATH="$PATH:/root/.sdkman/candidates/kotlin/current/bin"

ENTRYPOINT [ "./CauseEffectGraph", "prod" ]