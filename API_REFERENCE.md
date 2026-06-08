## API Quick Reference - 23 Endpoints

Base URL: `http://localhost:8080/api`

### CHAT ENDPOINTS (2/23)

| # | Method | Endpoint | Purpose |
|---|--------|----------|---------|
| 1 | POST | `/chat/send` | Send message & get AI response |
| 2 | GET | `/chat/history/{conversationId}` | Get chat history |

**Example #1 - Send Message:**
```bash
POST /api/chat/send
Content-Type: application/json

{
  "userId": 1,
  "message": "How to improve pronunciation?",
  "conversationId": 1
}

Response:
{
  "id": 1,
  "sender": "AI",
  "content": "To improve pronunciation...",
  "timestamp": "2024-06-06T10:30:00",
  "grammarCorrection": null,
  "pronunciationFeedback": null,
  "sentiment": "POSITIVE"
}
```

**Example #2 - Get History:**
```bash
GET /api/chat/history/1

Response: [
  {
    "id": 1,
    "sender": "USER",
    "content": "Hello",
    "timestamp": "2024-06-06T10:00:00"
  },
  {
    "id": 2,
    "sender": "AI",
    "content": "Hi there! How can I help?",
    "timestamp": "2024-06-06T10:01:00"
  }
]
```

---

### USER ENDPOINTS (2/23)

| # | Method | Endpoint | Purpose |
|---|--------|----------|---------|
| 3 | GET | `/user/profile` | Get user profile |
| 4 | PUT | `/user/profile` | Update user profile |

**Example #3 - Get Profile:**
```bash
GET /api/user/profile?userId=1

Response:
{
  "id": 1,
  "email": "john@example.com",
  "name": "John Doe",
  "avatar": "https://...",
  "level": "INTERMEDIATE",
  "createdAt": "2024-06-01T00:00:00"
}
```

**Example #4 - Update Profile:**
```bash
PUT /api/user/profile?userId=1
Content-Type: application/json

{
  "name": "John Smith",
  "level": "ADVANCED"
}

Response:
{
  "id": 1,
  "email": "john@example.com",
  "name": "John Smith",
  "level": "ADVANCED",
  "updatedAt": "2024-06-06T10:30:00"
}
```

---

### ANALYTICS ENDPOINTS (3/23)

| # | Method | Endpoint | Purpose |
|---|--------|----------|---------|
| 5 | GET | `/analytics/stats` | Get skill statistics |
| 6 | GET | `/analytics/daily` | Get daily statistics |
| 7 | GET | `/analytics/progress` | Get progress (time range) |

**Example #5 - Get Skill Stats:**
```bash
GET /api/analytics/stats?userId=1

Response: [
  {
    "skillName": "GRAMMAR",
    "score": 85.5,
    "level": "ADVANCED",
    "trend": "IMPROVING",
    "improvement": 5.2
  },
  {
    "skillName": "PRONUNCIATION",
    "score": 72.3,
    "trend": "STABLE"
  }
]
```

**Example #6 - Get Daily Stats:**
```bash
GET /api/analytics/daily?userId=1

Response:
{
  "date": "2024-06-06",
  "overallScore": 79.0,
  "messageCount": 15,
  "voiceCount": 3,
  "grammarCorrectionsCount": 5,
  "pronunciationScore": 82.0,
  "newWordsLearned": 2,
  "sessionDuration": 1800,
  "currentStreak": 7
}
```

**Example #7 - Get Progress:**
```bash
GET /api/analytics/progress?userId=1&timeRange=week

Response: [
  { "date": "2024-06-06", "overallScore": 79.0, "messageCount": 15 },
  { "date": "2024-06-05", "overallScore": 76.5, "messageCount": 12 },
  { "date": "2024-06-04", "overallScore": 74.0, "messageCount": 10 }
]
```

---

### VOCABULARY ENDPOINTS (4/23)

| # | Method | Endpoint | Purpose |
|---|--------|----------|---------|
| 8 | GET | `/vocabulary/list` | Get all vocabulary words |
| 9 | GET | `/vocabulary/search` | Search vocabulary |
| 10 | POST | `/vocabulary/save` | Save word for user |
| 11 | DELETE | `/vocabulary/remove/{wordId}` | Remove word |

**Example #8 - Get Vocabulary List:**
```bash
GET /api/vocabulary/list

Response: [
  {
    "id": 1,
    "word": "eloquent",
    "meaning": "Fluent and persuasive in speech",
    "difficulty": "ADVANCED",
    "exampleSentences": ["She gave an eloquent speech"],
    "pronunciation": "EL-uh-kwunt"
  }
]
```

**Example #9 - Search Vocabulary:**
```bash
GET /api/vocabulary/search?query=communicate

Response: [
  {
    "id": 2,
    "word": "communicate",
    "meaning": "Share or exchange information",
    "difficulty": "INTERMEDIATE"
  }
]
```

**Example #10 - Save Word:**
```bash
POST /api/vocabulary/save
Content-Type: application/json

{
  "userId": 1,
  "wordId": 1
}

Response:
{ "success": true }
```

**Example #11 - Remove Word:**
```bash
DELETE /api/vocabulary/remove/1?userId=1

Response:
{ "success": true }
```

---

### SPEAKING PRACTICE ENDPOINTS (4/23)

| # | Method | Endpoint | Purpose |
|---|--------|----------|---------|
| 12 | GET | `/speaking/scenarios` | Get speaking scenarios |
| 13 | POST | `/speaking/session/start` | Start speaking session |
| 14 | POST | `/speaking/response` | Submit pronunciation response |
| 15 | POST | `/speaking/session/end` | End speaking session |

**Example #12 - Get Scenarios:**
```bash
GET /api/speaking/scenarios?difficulty=INTERMEDIATE

Response: [
  {
    "id": 1,
    "title": "Job Interview",
    "description": "Practice a job interview",
    "context": "Interviewing for software engineer",
    "difficulty": "INTERMEDIATE",
    "topic": "Professional Communication",
    "estimatedDuration": 600
  }
]
```

**Example #13 - Start Session:**
```bash
POST /api/speaking/session/start
Content-Type: application/json

{
  "userId": 1,
  "scenarioId": 1
}

Response:
{
  "sessionId": 42
}
```

**Example #14 - Submit Response:**
```bash
POST /api/speaking/response
Content-Type: multipart/form-data

audio: <binary audio file>
sessionId: 42
spokenText: "I would like to apply for the software engineer position"

Response:
{
  "overallScore": 85.5,
  "spokenText": "I would like to apply for the software engineer position",
  "recordingUrl": "https://...",
  "mispronounced": [
    {
      "word": "position",
      "incorrect": "puh-ZISH-uhn",
      "correct": "puh-ZISH-uhn"
    }
  ],
  "suggestions": [
    "Good stress on 'software'",
    "Slow down on final syllables"
  ]
}
```

**Example #15 - End Session:**
```bash
POST /api/speaking/session/end
Content-Type: application/json

{
  "sessionId": 42
}

Response:
{ "success": true }
```

---

### GRAMMAR ENDPOINTS (2/23)

| # | Method | Endpoint | Purpose |
|---|--------|----------|---------|
| 16 | GET | `/grammar/corrections` | Get recent corrections |
| 17 | POST | `/grammar/check` | Check text for grammar |

**Example #16 - Get Corrections:**
```bash
GET /api/grammar/corrections

Response: [
  {
    "id": 1,
    "original": "She go to school",
    "corrected": "She goes to school",
    "errorType": "VERB_CONJUGATION"
  }
]
```

**Example #17 - Check Grammar:**
```bash
POST /api/grammar/check
Content-Type: application/json

{
  "text": "He dont like coffee"
}

Response:
{
  "id": 1,
  "original": "He dont like coffee",
  "corrected": "He doesn't like coffee",
  "explanation": "Use 'doesn't' (does not) with 'he' in negative sentences",
  "rule": "Verb Negation",
  "errorType": "CONTRACTION",
  "examples": [
    "She doesn't like tea",
    "It doesn't work"
  ]
}
```

---

### ACHIEVEMENT ENDPOINTS (1/23)

| # | Method | Endpoint | Purpose |
|---|--------|----------|---------|
| 18 | GET | `/achievements/list` | Get user achievements |

**Example #18 - Get Achievements:**
```bash
GET /api/achievements/list?userId=1

Response: [
  {
    "id": 1,
    "title": "First Steps",
    "description": "Complete your first chat session",
    "badge": "🌟"
  },
  {
    "id": 2,
    "title": "Grammar Master",
    "description": "Complete 50 grammar corrections",
    "badge": "📚"
  }
]
```

---

## HTTP Status Codes

| Code | Meaning | Example |
|------|---------|---------|
| 200 | OK | Successful request |
| 201 | Created | Resource created |
| 400 | Bad Request | Invalid parameters |
| 401 | Unauthorized | Missing authentication |
| 404 | Not Found | Resource not found |
| 500 | Server Error | Internal error |

---

## Request/Response Format

All requests and responses use **JSON** format.

**Common Headers:**
```
Content-Type: application/json
Accept: application/json
```

**Error Response:**
```json
{
  "status": 400,
  "message": "Invalid request",
  "timestamp": "2024-06-06T10:30:00"
}
```

---

## Rate Limiting

(To be implemented)

**Limits:**
- 100 requests per minute per user
- 1000 requests per hour per user

---

## Authentication

(To be implemented)

**Bearer Token:**
```
Authorization: Bearer <token>
```

---

**Total: 18 endpoints implemented**  
**Remaining endpoints in roadmap: Speech-to-text, notifications, etc.**
