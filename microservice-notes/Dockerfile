#Build
FROM maven:latest AS build
WORKDIR /app
COPY . .
RUN mvn clean package -DskipTests

#Runtime
FROM openjdk:17-alpine
WORKDIR /
COPY --from=build /app/target/notes_ms-0.0.1-SNAPSHOT.jar ./app.jar
EXPOSE 8083
ENTRYPOINT ["java", "-jar", "app.jar"]
