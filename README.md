# Xeni Rate Limiter Service

## Overview

This is a backend service to register clients and enforce request rate limits using a fixed window rate limiting strategy. It is designed with extensibility in mind to support other strategies like token bucket or sliding window.

---

## Features

- ✅ Register client with a unique ID and rate limit
- ✅ Check if a client request is allowed at a given timestamp
- ✅ Fixed Window rate limiting strategy
- ✅ In-memory storage (no database required)
- ✅ Extensible design for future strategies
- ✅ JUnit 5 and Mockito-based test coverage

---

## Technologies

- Java 17+
- Spring Boot
- JUnit 5
- Mockito

---

## Setup Instructions

1. **Clone the repository:**
   ```bash
   git clone https://github.com/your-username/xeni-rate-limiter.git
   cd xeni-rate-limiter
