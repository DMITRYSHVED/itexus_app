FROM gradle:8.6-jdk21 AS build
ENV APP_HOME=/home/gradle/src

COPY --chown=gradle:gradle . $APP_HOME
WORKDIR $APP_HOME
RUN gradle build -x test --no-daemon

FROM openjdk:21 AS production
EXPOSE 8080
WORKDIR /app
COPY --from=build /home/gradle/src/build/libs/*.jar miracles_store.jar
ENTRYPOINT ["java","-jar","miracles_store.jar"]