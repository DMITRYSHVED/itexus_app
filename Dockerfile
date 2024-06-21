FROM gradle:8.6-jdk21 AS build
ENV APP_HOME=/home/gradle/src
WORKDIR $APP_HOME
COPY --chown=gradle:gradle . $APP_HOME
RUN gradle build -x test --no-daemon

FROM openjdk:21 AS production
ENV APP_HOME=/home/gradle/src
WORKDIR /app
EXPOSE 8080
COPY --from=build $APP_HOME/build/libs/*.jar miracles_store.jar
ENTRYPOINT ["java","-jar","miracles_store.jar"]

