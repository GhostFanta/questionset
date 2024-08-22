# syntax=docker/dockerfile:experimental
FROM maven:3.8.3-openjdk-17 AS build
# https://github.com/sgerrand/alpine-pkg-glibc
WORKDIR /app
COPY . /app
RUN  mvn clean compile package

FROM openjdk:17-jdk-slim
ARG BUILD=/app/target
WORKDIR /app
COPY --from=build ${BUILD} /app
# this last command outputs the list of files added to the build context:
RUN find /app

ENTRYPOINT ["java","-jar","questionset-0.0.1-SNAPSHOT.jar"]