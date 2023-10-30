# Use Gradle image for building the app
FROM gradle:8.4.0-jdk21 AS builder

WORKDIR /app

# Copy build scripts first for better caching
COPY build.gradle .
COPY settings.gradle .

# Copy source code
COPY src ./src
COPY config ./config

# Build the application
RUN gradle build

# Use Amazon Corretto image for running the app
FROM amazoncorretto:21-alpine

# Ensure the .jar file is in the build/libs directory, not build/lib
COPY --from=builder /app/build/libs/*.jar app.jar

EXPOSE 8080 8443
ENTRYPOINT ["java", "-jar", "app.jar"]
