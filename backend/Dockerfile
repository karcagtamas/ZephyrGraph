FROM gradle:jdk21 as build

WORKDIR /src

COPY ./backend/. .

RUN apt install node -y
RUN apt install npm -y

RUN gradle build
RUN gradle installShadowDist
RUN cp ./build/install/CauseEffectGraph-shadow/. /dist

FROM build AS core

WORKDIR /dist/bin

EXPOSE 8080

ENTRYPOINT [ "java", "-jar", "CauseEffectGraph" ]