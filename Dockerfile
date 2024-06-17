FROM openjdk:21

WORKDIR /app

COPY . .

CMD ["java", "-jar", "build/libs/miracles_store-0.0.1-SNAPSHOT.jar", "--spring.profiles.active=container", "--server.port=8080", "--server.address=0.0.0.0"]
