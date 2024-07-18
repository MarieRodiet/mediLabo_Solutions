# Build stage
FROM maven:latest AS build
WORKDIR /app
COPY . .
RUN mvn clean package -DskipTests

# Runtime stage
FROM openjdk:21-jdk-slim
WORKDIR /
COPY --from=build /app/target/client-ui-0.0.1-SNAPSHOT.jar ./app.jar
EXPOSE 9004

ENTRYPOINT ["java", "-jar", "app.jar"]

