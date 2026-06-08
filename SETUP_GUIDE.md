# Spring AI Backend - English Teaching Assistant

A comprehensive Spring Boot backend for an **English Teaching AI Assistant** with support for:
- Chat conversations with AI
- Grammar correction
- Pronunciation feedback  
- Vocabulary building
- Speaking practice scenarios
- User analytics and progress tracking
- Achievement system

## Architecture Overview

```
┌─────────────────────────────────────────────────────────┐
│         Frontend (Angular/React)                        │
└────────────────┬────────────────────────────────────────┘
                 │
                 ▼
┌─────────────────────────────────────────────────────────┐
│  Spring Boot REST API (23 Endpoints)                    │
├─────────────────────────────────────────────────────────┤
│  ┌───────────────────────────────────────────────────┐  │
│  │ Controllers (ChatController, UserController, ...) │  │
│  └──────────────────┬────────────────────────────────┘  │
│  ┌──────────────────▼────────────────────────────────┐  │
│  │ Services (ChatService, GrammarService, ...)       │  │
│  └──────────────────┬────────────────────────────────┘  │
│  ┌──────────────────▼────────────────────────────────┐  │
│  │ Repositories (JPARepository interfaces)           │  │
│  └──────────────────┬────────────────────────────────┘  │
└─────────────────────┼────────────────────────────────────┘
                      ▼
        ┌─────────────────────────────┐
        │   Spring AI Integration     │
        ├─────────────────────────────┤
        │  ┌─────────────────────┐   │
        │  │ OpenAI (Primary)    │   │
        │  └──────────┬──────────┘   │
        │             │              │
        │  ┌──────────▼──────────┐   │
        │  │ Ollama (Fallback)   │   │
        │  └─────────────────────┘   │
        └─────────────────────────────┘
                      │
        ┌─────────────▼─────────────┐
        │  H2 Database / MySQL      │
        │  (Entities Storage)       │
        └───────────────────────────┘
```

## API Endpoints (23 Total)

### 1. Chat Endpoints (2)
- `POST /api/chat/send` - Send a message and get AI response
- `GET /api/chat/history/{conversationId}` - Get chat history

### 2. User Endpoints (2)
- `GET /api/user/profile` - Get user profile
- `PUT /api/user/profile` - Update user profile

### 3. Analytics Endpoints (3)
- `GET /api/analytics/stats` - Get skill statistics
- `GET /api/analytics/daily` - Get daily statistics
- `GET /api/analytics/progress` - Get progress with time range

### 4. Vocabulary Endpoints (4)
- `GET /api/vocabulary/list` - Get vocabulary list
- `GET /api/vocabulary/search` - Search vocabulary
- `POST /api/vocabulary/save` - Save word for user
- `DELETE /api/vocabulary/remove/{wordId}` - Remove word

### 5. Speaking Practice Endpoints (4)
- `GET /api/speaking/scenarios` - Get speaking scenarios by difficulty
- `POST /api/speaking/session/start` - Start a speaking session
- `POST /api/speaking/response` - Submit pronunciation response
- `POST /api/speaking/session/end` - End a speaking session

### 6. Grammar Endpoints (2)
- `GET /api/grammar/corrections` - Get recent corrections
- `POST /api/grammar/check` - Check text for grammar errors

### 7. Achievements Endpoints (1)
- `GET /api/achievements/list` - Get user achievements

---

## Prerequisites

- **Java 17+**
- **Maven 3.6+**
- **MySQL** (optional, can use H2)
- **Ollama** (for local LLM) - [Download](https://ollama.ai)
- **OpenAI API Key** - [Get here](https://platform.openai.com/api-keys)

---

## Installation & Setup

### 1. Clone the Project
```bash
cd d:\SpringAiProject
```

### 2. Install Dependencies
```bash
mvn clean install
```

### 3. Configure Ollama (Optional but Recommended)

**Install Ollama:**
- Download from [ollama.ai](https://ollama.ai)
- Install and run the application

**Pull a Model:**
```bash
ollama pull mistral
# or
ollama pull llama2
```

**Start Ollama Server:**
```bash
ollama serve
```

By default, Ollama runs on `http://localhost:11434`

### 4. Configure OpenAI API Key

**Option A: Environment Variable**
```powershell
# Windows PowerShell
$env:OPENAI_API_KEY = "your-api-key-here"
```

**Option B: Update application.properties**
```properties
spring.ai.openai.api-key=your-openai-api-key-here
```

### 5. Update Database Configuration

**For H2 (In-Memory)** - Default configuration, no changes needed

**For MySQL:**
Update `application.properties`:
```properties
spring.datasource.url=jdbc:mysql://localhost:3306/english_ai
spring.datasource.username=root
spring.datasource.password=your-password
spring.jpa.database-platform=org.hibernate.dialect.MySQL8Dialect
```

---

## Running the Application

### Development Mode
```bash
mvn spring-boot:run
```

### Production Build
```bash
mvn clean package
java -jar target/springai-0.0.1-SNAPSHOT.jar
```

The application will start on `http://localhost:8080`

---

## API Usage Examples

### 1. Send Chat Message
**Request:**
```bash
curl -X POST http://localhost:8080/api/chat/send \
  -H "Content-Type: application/json" \
  -d '{
    "userId": 1,
    "message": "How do I improve my English pronunciation?",
    "conversationId": 1
  }'
```

**Response:**
```json
{
  "id": 1,
  "sender": "AI",
  "content": "To improve your pronunciation, you should...",
  "timestamp": "2024-06-06T10:30:00",
  "grammarCorrection": null,
  "pronunciationFeedback": null
}
```

### 2. Check Grammar
**Request:**
```bash
curl -X POST http://localhost:8080/api/grammar/check \
  -H "Content-Type: application/json" \
  -d '{"text": "She go to the store every day"}'
```

**Response:**
```json
{
  "original": "She go to the store every day",
  "corrected": "She goes to the store every day",
  "explanation": "Use 'goes' (third person singular) with 'she'",
  "rule": "Subject-Verb Agreement",
  "errorType": "Verb Conjugation"
}
```

### 3. Get User Profile
**Request:**
```bash
curl -X GET "http://localhost:8080/api/user/profile?userId=1"
```

**Response:**
```json
{
  "id": 1,
  "email": "user@example.com",
  "name": "John Doe",
  "avatar": "https://...",
  "level": "INTERMEDIATE",
  "createdAt": "2024-06-01T00:00:00"
}
```

### 4. Get Speaking Scenarios
**Request:**
```bash
curl -X GET "http://localhost:8080/api/speaking/scenarios?difficulty=INTERMEDIATE"
```

**Response:**
```json
[
  {
    "id": 1,
    "title": "Job Interview",
    "description": "Practice a job interview scenario",
    "context": "You're interviewing for a software engineer position",
    "difficulty": "INTERMEDIATE",
    "topic": "Professional Communication"
  }
]
```

---

## Switching Between OpenAI and Ollama

### Priority System
The application automatically tries providers in this order:
1. **OpenAI** (if API key is configured)
2. **Ollama** (fallback)

### Force Ollama Only
Remove or comment out OpenAI configuration in `application.properties`:
```properties
# spring.ai.openai.api-key=${OPENAI_API_KEY:your-api-key}
spring.ai.ollama.base-url=http://localhost:11434
spring.ai.ollama.chat.options.model=mistral
```

### Force OpenAI Only
Comment out Ollama configuration:
```properties
spring.ai.openai.api-key=your-api-key
# spring.ai.ollama.base-url=http://localhost:11434
```

---

## Project Structure

```
src/main/java/com/springai/springai/
├── config/
│   └── AiConfiguration.java           # Spring AI bean configuration
├── controller/
│   ├── ChatController.java            # Chat endpoints
│   ├── UserController.java            # User endpoints
│   ├── AnalyticsController.java       # Analytics endpoints
│   ├── VocabularyController.java      # Vocabulary endpoints
│   ├── SpeakingPracticeController.java # Speaking endpoints
│   ├── GrammarController.java         # Grammar endpoints
│   └── AchievementController.java     # Achievement endpoints
├── model/
│   ├── User.java
│   ├── Message.java
│   ├── Conversation.java
│   ├── GrammarCorrection.java
│   ├── PronunciationFeedback.java
│   ├── SkillStats.java
│   ├── DailyStats.java
│   ├── VocabularyWord.java
│   ├── SpeakingScenario.java
│   ├── SpeakingSession.java
│   ├── Achievement.java
│   └── MispronunciationItem.java
├── repository/
│   ├── UserRepository.java
│   ├── MessageRepository.java
│   ├── ConversationRepository.java
│   ├── GrammarCorrectionRepository.java
│   ├── SkillStatsRepository.java
│   ├── DailyStatsRepository.java
│   ├── VocabularyRepository.java
│   ├── SpeakingScenarioRepository.java
│   ├── SpeakingSessionRepository.java
│   └── AchievementRepository.java
├── service/
│   ├── ChatService.java               # Chat logic with AI integration
│   ├── GrammarService.java            # Grammar checking with AI
│   ├── PronunciationService.java      # Pronunciation analysis
│   ├── AnalyticsService.java          # User analytics
│   ├── VocabularyService.java         # Vocabulary management
│   ├── SpeakingPracticeService.java   # Speaking practice logic
│   └── UserService.java               # User profile management
├── dto/
│   ├── MessageDTO.java
│   ├── DailyStatsDTO.java
│   ├── SkillStatsDTO.java
│   └── PronunciationFeedbackDTO.java
└── SpringaiApplication.java           # Main application class
```

---

## Dependencies

### Added to POM.xml
```xml
<!-- Spring AI -->
<dependency>
    <groupId>org.springframework.ai</groupId>
    <artifactId>spring-ai-starter-openai</artifactId>
</dependency>
<dependency>
    <groupId>org.springframework.ai</groupId>
    <artifactId>spring-ai-starter-model-ollama</artifactId>
</dependency>

<!-- Spring Data JPA -->
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-data-jpa</artifactId>
</dependency>

<!-- Database -->
<dependency>
    <groupId>com.h2database</groupId>
    <artifactId>h2</artifactId>
    <scope>runtime</scope>
</dependency>

<!-- Lombok -->
<dependency>
    <groupId>org.projectlombok</groupId>
    <artifactId>lombok</artifactId>
</dependency>
```

---

## Troubleshooting

### Ollama Connection Error
- Make sure Ollama is running: `ollama serve`
- Check if port 11434 is accessible
- Verify model is pulled: `ollama list`

### OpenAI API Key Error
- Check API key is correctly set
- Verify API key has sufficient credits
- Check API key permissions in OpenAI dashboard

### Database Errors
- For H2: Ensure h2 dependency is in pom.xml
- For MySQL: Create database manually
- Run migrations if needed

### JSON Parsing Errors
- Ensure AI responses are valid JSON
- Check error logs for detailed information

---

## Performance Tuning

### For Ollama (Local)
- Use smaller models for faster responses: `ollama pull orca-mini`
- Reduce temperature for consistent responses

### For OpenAI (Cloud)
- Use GPT-3.5-turbo for faster/cheaper responses
- Adjust temperature based on use case

---

## Testing the API

### Using Postman
1. Import the API endpoints
2. Set base URL: `http://localhost:8080`
3. Create requests for each endpoint

### Using cURL
```bash
# Test if server is running
curl http://localhost:8080/api/user/profile?userId=1
```

---

## Future Enhancements

- [ ] Real-time WebSocket support for live conversations
- [ ] Speech-to-Text integration (Google Cloud Speech API)
- [ ] Text-to-Speech integration
- [ ] JWT Authentication
- [ ] Rate limiting
- [ ] Caching with Redis
- [ ] Docker containerization
- [ ] Kubernetes deployment

---

## Support

For issues or questions, please refer to:
- [Spring AI Documentation](https://docs.spring.io/spring-ai/reference/)
- [Ollama Documentation](https://github.com/ollama/ollama)
- [OpenAI API Documentation](https://platform.openai.com/docs)

---

**Version:** 1.0.0  
**Last Updated:** June 6, 2024
