FROM gradle:8.6-jdk21 AS build
COPY --chown=gradle:gradle . /home/gradle/src
WORKDIR /home/gradle/src
RUN gradle build -x test --no-daemon

FROM openjdk:21 AS production
EXPOSE 8080
WORKDIR /app
COPY --from=build /home/gradle/src/build/libs/*.jar /app/miracles_store.jar
ENTRYPOINT ["java","-jar","/app/miracles_store.jar"]
