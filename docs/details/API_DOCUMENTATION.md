# Documentation API MyProf

## 1. Authentication API

### Login
```http
POST /api/v1/auth/login
Content-Type: application/json

{
  "email": "user@example.com",
  "password": "password123"
}

Response 200:
{
  "token": "eyJhbGciOiJIUzI1NiIs...",
  "refreshToken": "eyJhbGciOiJIUzI1NiIs...",
  "expiresIn": 3600
}
```

### Register
```http
POST /api/v1/auth/register
Content-Type: application/json

{
  "email": "user@example.com",
  "password": "password123",
  "role": "STUDENT",
  "firstName": "John",
  "lastName": "Doe"
}

Response 201:
{
  "userId": "123e4567-e89b-12d3-a456-426614174000",
  "email": "user@example.com",
  "role": "STUDENT"
}
```

## 2. Course Management API

### Create Course
```http
POST /api/v1/courses
Authorization: Bearer {token}
Content-Type: application/json

{
  "title": "Advanced Mathematics",
  "description": "Complex analysis and more",
  "price": 99.99,
  "duration": "12 weeks",
  "level": "ADVANCED"
}

Response 201:
{
  "courseId": "123e4567-e89b-12d3-a456-426614174000",
  "title": "Advanced Mathematics",
  "status": "DRAFT"
}
```

### Get Course
```http
GET /api/v1/courses/{courseId}
Authorization: Bearer {token}

Response 200:
{
  "courseId": "123e4567-e89b-12d3-a456-426614174000",
  "title": "Advanced Mathematics",
  "description": "Complex analysis and more",
  "instructor": {
    "id": "789e4567-e89b-12d3-a456-426614174000",
    "name": "Dr. Smith"
  },
  "lessons": [
    {
      "id": "lesson1",
      "title": "Introduction"
    }
  ]
}
```

## 3. Payment API

### Create Payment
```http
POST /api/v1/payments
Authorization: Bearer {token}
Content-Type: application/json

{
  "courseId": "123e4567-e89b-12d3-a456-426614174000",
  "amount": 99.99,
  "currency": "EUR",
  "paymentMethod": "CARD"
}

Response 200:
{
  "paymentId": "pay_123456789",
  "status": "PENDING",
  "clientSecret": "pi_123_secret_456"
}
```

## 4. Streaming API

### Start Stream
```http
POST /api/v1/streaming/start
Authorization: Bearer {token}
Content-Type: application/json

{
  "courseId": "123e4567-e89b-12d3-a456-426614174000",
  "quality": "HD",
  "expectedDuration": 3600
}

Response 200:
{
  "streamId": "stream_123456789",
  "rtmpUrl": "rtmp://streaming.myprof.com/live/123456789",
  "playbackUrl": "https://streaming.myprof.com/live/123456789"
}
```

## 5. AI Services API

### Auto-Grade Assignment
```http
POST /api/v1/ai/grade
Authorization: Bearer {token}
Content-Type: application/json

{
  "assignmentId": "123e4567-e89b-12d3-a456-426614174000",
  "submission": "The solution to the equation...",
  "type": "MATHEMATICS"
}

Response 200:
{
  "grade": 85,
  "feedback": "Good work! Consider...",
  "suggestions": [
    "Review chapter 3",
    "Practice more integration"
  ]
}
```

## 6. Analytics API

### Get User Progress
```http
GET /api/v1/analytics/progress/{userId}
Authorization: Bearer {token}

Response 200:
{
  "userId": "123e4567-e89b-12d3-a456-426614174000",
  "coursesCompleted": 5,
  "averageGrade": 87.5,
  "timeSpent": 45600,
  "strengths": ["Mathematics", "Physics"],
  "areasForImprovement": ["Chemistry"]
}
```
