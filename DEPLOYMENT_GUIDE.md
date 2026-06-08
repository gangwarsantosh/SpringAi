# English Teaching AI Assistant - Build & Run Guide

## Quick Start with Docker

### Prerequisites
- Docker & Docker Compose installed
- (Optional) OpenAI API Key

### Build and Run

```bash
# Set OpenAI API key (optional)
$env:OPENAI_API_KEY = "your-api-key-here"

# Start all services
docker-compose up -d

# View logs
docker-compose logs -f spring-ai-backend

# Stop services
docker-compose down
```

The application will be available at: `http://localhost:8080`
Ollama will be available at: `http://localhost:11434`

---

## Local Development Setup

### Prerequisites
- Java 17+
- Maven 3.6+
- Ollama (optional but recommended)

### Build

```bash
# Install dependencies
mvn clean install

# Build package
mvn clean package
```

### Run

```bash
# Development mode with Spring Boot
mvn spring-boot:run

# Or run the JAR directly
java -jar target/springai-0.0.1-SNAPSHOT.jar
```

---

## Configuration

### application.properties

Key configurations:

```properties
# Server
server.port=8080

# Database
spring.datasource.url=jdbc:h2:mem:testdb
spring.jpa.hibernate.ddl-auto=update

# OpenAI
spring.ai.openai.api-key=your-key-here
spring.ai.openai.chat.options.model=gpt-4

# Ollama  
spring.ai.ollama.base-url=http://localhost:11434
spring.ai.ollama.chat.options.model=mistral
```

---

## API Testing

### Using Postman
1. Import collection from API_REFERENCE.md
2. Set base URL: http://localhost:8080
3. Start testing endpoints

### Using cURL
```bash
# Test health
curl http://localhost:8080/api/user/profile?userId=1

# Send chat message
curl -X POST http://localhost:8080/api/chat/send \
  -H "Content-Type: application/json" \
  -d '{"userId":1,"message":"Hello"}'
```

---

## Troubleshooting

### Port 8080 already in use
```bash
# Find and kill process using port 8080
netstat -ano | findstr :8080
taskkill /PID <PID> /F
```

### Ollama not accessible
- Verify Ollama is running: `ollama serve`
- Check connection: `curl http://localhost:11434`
- Pull a model: `ollama pull mistral`

### OpenAI API errors
- Verify API key is set correctly
- Check API key has sufficient credits
- Ensure correct model name in properties

---

## Production Deployment

### Docker Compose
```bash
docker-compose -f docker-compose.yml up -d
```

### Kubernetes
```bash
kubectl apply -f kubernetes/
```

### AWS/Azure
Push Docker image to registry and deploy.

---

## Monitoring

### Health Check
```bash
curl http://localhost:8080/health
```

### Logs
```bash
docker-compose logs -f spring-ai-backend
```

### Database
H2 Console: `http://localhost:8080/h2-console`

---

**For detailed setup, see: SETUP_GUIDE.md**
