# Build
FROM maven:latest AS build
WORKDIR /app
COPY . .
RUN mvn clean package -DskipTests

# Runtime
FROM openjdk:17-alpine
WORKDIR /
COPY --from=build /app/target/patients-0.0.1-SNAPSHOT.jar ./app.jar
EXPOSE 9001
ENTRYPOINT ["java", "-jar", "app.jar"]


