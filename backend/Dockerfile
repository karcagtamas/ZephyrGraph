FROM gradle:8.5-jdk21 as build

WORKDIR /src

COPY . .

RUN apt-get update
RUN apt-get install curl -y
RUN curl -fsSL https://deb.nodesource.com/setup_20.x | bash -
RUN apt-get install nodejs -y

RUN ./gradlew clean
# RUN ./gradlew installShadowDist
# RUN cp ./build/install/CauseEffectGraph-shadow/. /dist

FROM build AS core

WORKDIR /dist/bin

# ENTRYPOINT [ "java", "-jar", "CauseEffectGraph" ]