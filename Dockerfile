FROM gradle:8.6-jdk as build

WORKDIR /build

COPY . .
COPY build.gradle build.gradle
COPY settings.gradle settings.gradle
COPY gradlew ./

RUN gradle clean bootJar downloadLibs

FROM openjdk:21

ARG JAR_FILE=miracles_store-0.0.1-SNAPSHOT.jar

WORKDIR /application

COPY --from=build /build/build/libs/${JAR_FILE} application.jar
COPY --from=build /build/build/dependency lib

ENTRYPOINT exec java ${JAVA_OPTS} -cp lib/*:application.jar com.example.miracles_store.MiraclesStoreApplication ${0} ${@}


