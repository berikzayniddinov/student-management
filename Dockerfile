# Use Gradle to build the application
FROM gradle:8.0.2-jdk17 AS build
WORKDIR /app
COPY . .
RUN ./gradlew build -x test

# Use lightweight JDK image to run the application
FROM eclipse-temurin:17-jdk-alpine
WORKDIR /app
COPY --from=build /app/build/libs/*.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]
