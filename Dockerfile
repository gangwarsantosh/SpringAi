# Use official Maven image with Java 17
FROM maven:3.9.2-eclipse-temurin-17 AS builder

WORKDIR /build

# Copy pom.xml and download dependencies
COPY pom.xml .
RUN mvn dependency:resolve

# Copy source code
COPY src ./src

# Build the application
RUN mvn clean package -DskipTests

# Use official Java 17 runtime image
FROM eclipse-temurin:17-jre-jammy

WORKDIR /app

# Copy built jar from builder stage
COPY --from=builder /build/target/springai-0.0.1-SNAPSHOT.jar app.jar

# Expose port
EXPOSE 8080

# Set environment variables
ENV OPENAI_API_KEY=""
ENV OLLAMA_URL="http://localhost:11434"

# Health check
HEALTHCHECK --interval=30s --timeout=10s --start-period=40s --retries=3 \
  CMD curl -f http://localhost:8080/api/user/profile || exit 1

# Run the application
ENTRYPOINT ["java", "-jar", "app.jar"]
