# Build stage
FROM maven:3.8.4-openjdk-17 AS build
WORKDIR /app

# Copy pom.xml first to leverage Docker cache
COPY pom.xml .
# Download dependencies first (separate layer for better caching)
RUN mvn dependency:go-offline -B

# Copy source code
COPY src ./src

# Build application
RUN mvn clean package -DskipTests

# Production stage
FROM openjdk:17-jdk-slim
WORKDIR /app

# Install curl for health checks (optional)
RUN apt-get update && apt-get install -y curl && rm -rf /var/lib/apt/lists/*

# Copy the built jar from build stage
COPY --from=build /app/target/*.jar app.jar

# Create a non-root user
RUN groupadd -r spring && useradd --no-log-init -r -g spring spring
USER spring

# Expose port 8080 (Spring Boot default)
EXPOSE 8080

# Add health check
HEALTHCHECK --interval=30s --timeout=3s --start-period=10s --retries=3 \
  CMD curl -f http://localhost:8080/api/health || exit 1

ENTRYPOINT ["java", "-jar", "app.jar"]