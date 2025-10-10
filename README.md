# Chat Application

A real-time chat application built with Spring Boot microservices and React frontend.

## 📋 Table of Contents

- [Overview](#overview)
- [Features](#features)
- [Architecture](#architecture)
- [Prerequisites](#prerequisites)
- [Installation](#installation)
- [Configuration](#configuration)
- [Running the Application](#running-the-application)
- [API Documentation](#api-documentation)
- [Frontend Structure](#frontend-structure)
- [Backend Services](#backend-services)
- [Testing](#testing)
- [Deployment](#deployment)

## 🚀 Overview

This chat application provides real-time messaging capabilities with automatic user assignment, room management, and message persistence using Kafka for event-driven communication.

## ✨ Features

- Real-time messaging via WebSockets
- Automatic user joining with generated usernames
- Automatic room assignment (max 10 users per room)
- Chat rooms with auto-generated names
- Message history persistence
- User presence indicators
- Responsive React frontend
- Event-driven microservices architecture with Kafka

## 🏗️ Architecture

```
├── backend/
│   ├── message-service/      # Message handling microservice (Spring Boot)
│   ├── room-service/         # Room management service (Spring Boot)
│   ├── user_service/         # User management service (Spring Boot)
│   └── websocket_gateway/    # WebSocket gateway (Spring Boot)
├── frontend/                 # React web client (Vite + TypeScript)
└── docker-compose.yaml      # Kafka infrastructure
```

## 📋 Prerequisites

- Java 21+
- Maven 3.8+
- Node.js 18+ (for frontend)
- Docker (for Kafka)

## 🔧 Installation

1. Clone the repository:
```bash
git clone <repository-url>
cd chat
```

2. Start Kafka infrastructure:
```bash
docker-compose up -d
```

3. Build backend services:
```bash
# Build all services
cd backend/message-service && mvn clean install
cd ../room-service && mvn clean install  
cd ../user_service && mvn clean install
cd ../websocket_gateway && mvn clean install
```

4. Install frontend dependencies:
```bash
cd frontend
npm install
```

## ⚙️ Configuration

### Environment Variables

Each service runs with default ports:
- user_service: 8081
- room-service: 8082  
- message-service: 8083
- websocket_gateway: 8080

Services use H2 in-memory databases for development.

### Kafka Setup

Start Kafka using the provided docker-compose:
```bash
docker-compose up -d
```

This starts:
- Zookeeper on port 2181
- Kafka on port 9092
- Kafka UI on port 8090

## 🚀 Running the Application

### Development Mode

1. Start Kafka:
```bash
docker-compose up -d
```

2. Start backend services (in separate terminals):
```bash
# User Service (port 8081)
cd backend/user_service
./mvnw spring-boot:run

# Room Service (port 8082)  
cd backend/room-service
./mvnw spring-boot:run

# Message Service (port 8083)
cd backend/message-service  
./mvnw spring-boot:run

# WebSocket Gateway (port 8080)
cd backend/websocket_gateway
./mvnw spring-boot:run
```

3. Start frontend:
```bash
cd frontend
npm run dev
```

The frontend will be available at `http://localhost:5173`

### Production Mode

```bash
# Build JAR files
cd backend/user_service && mvn clean package
cd ../room-service && mvn clean package  
cd ../message-service && mvn clean package
cd ../websocket_gateway && mvn clean package

# Run services
java -jar backend/user_service/target/user_service-0.0.1-SNAPSHOT.jar
java -jar backend/room-service/target/room-service-0.0.1-SNAPSHOT.jar
java -jar backend/message-service/target/message-service-0.0.1-SNAPSHOT.jar
java -jar backend/websocket_gateway/target/websocket_gateway-0.0.1-SNAPSHOT.jar

# Build and serve frontend
cd frontend
npm run build
npm run preview
```

## 📚 API Documentation

### User Endpoints

- `POST /api/users/join` - Join chat (auto-assigns to room)
- `GET /api/users/{userId}` - Get user details
- `DELETE /api/users/{userId}` - Disconnect user

### Room Endpoints

- `GET /api/rooms` - Get all rooms
- `GET /api/rooms/available` - Get available rooms
- `GET /api/rooms/{roomId}` - Get specific room
- `GET /api/rooms/user/{userId}` - Get user's room
- `POST /api/rooms/assign` - Manually assign user to room
- `DELETE /api/rooms/members/{userId}` - Remove user from room

### Message Endpoints

- `POST /api/messages` - Send message
- `GET /api/messages/room/{roomId}` - Get room messages

### WebSocket Connection

- **URL**: `ws://localhost:8080/ws`
- **Topic**: `/topic/room/{roomId}` - Subscribe to room messages

### WebSocket Events

Events published to `/topic/room/{roomId}`:
- `CHAT` - Chat message
- `JOIN` - User joined room  
- `LEAVE` - User left room
- `SYSTEM` - System notifications

## 🎨 Frontend Structure

```
frontend/
├── src/
│   ├── components/      # React components
│   │   ├── Chat/       # Chat-related components
│   │   └── Layout/     # Layout components
│   ├── hooks/          # Custom React hooks
│   ├── services/       # API service functions  
│   ├── context/        # React context providers
│   ├── types/          # TypeScript type definitions
│   └── assets/         # Static assets
├── public/             # Static public files
└── package.json        # Dependencies and scripts
```

### Key Components

- `ChatRoom` - Main chat interface
- `MessageList` - Message display component
- `MessageInput` - Message input component
- `UserList` - Online users sidebar
- `Header` - Application header

## 🔧 Backend Services

### User Service (Port 8081)

Handles user lifecycle management.

**Technologies:** Spring Boot, Spring Data JPA, H2 Database, Kafka
**Features:**
- Auto-generates unique usernames
- Publishes user events to Kafka
- H2 console: `http://localhost:8081/h2-console`

### Room Service (Port 8082)

Manages chat rooms and user assignments.

**Technologies:** Spring Boot, Spring Data JPA, H2 Database, Kafka
**Features:**
- Creates rooms with auto-generated names
- Assigns users to rooms (max 10 per room)
- Manages room lifecycle
- H2 console: `http://localhost:8082/h2-console`

### Message Service (Port 8083)

Handles message persistence and retrieval.

**Technologies:** Spring Boot, Spring Data JPA, H2 Database, Kafka
**Features:**
- Stores chat messages
- Provides message history
- Publishes message events
- H2 console: `http://localhost:8083/h2-console`

### WebSocket Gateway (Port 8080)

Manages WebSocket connections and real-time messaging.

**Technologies:** Spring Boot, WebSocket, STOMP, Kafka
**Features:**
- WebSocket connection management
- Real-time message broadcasting
- Kafka event consumption
- Connection tracking

## 🧪 Testing

```bash
# Run backend service tests
cd backend/user_service && mvn test
cd backend/room-service && mvn test  
cd backend/message-service && mvn test
cd backend/websocket_gateway && mvn test

# Run all backend tests
find backend -name "pom.xml" -execdir mvn test \;

# Run frontend tests
cd frontend
npm test
```

## 🚀 Deployment

### Using Docker Compose

```bash
# Start only Kafka infrastructure
docker-compose up -d
```

### Manual Deployment

```bash
# Build all services
find backend -name "pom.xml" -execdir mvn clean package \;

# Start Kafka
docker-compose up -d

# Run services
java -jar backend/user_service/target/user_service-0.0.1-SNAPSHOT.jar &
java -jar backend/room-service/target/room-service-0.0.1-SNAPSHOT.jar &
java -jar backend/message-service/target/message-service-0.0.1-SNAPSHOT.jar &
java -jar backend/websocket_gateway/target/websocket_gateway-0.0.1-SNAPSHOT.jar &

# Build and serve frontend
cd frontend && npm run build && npm run preview
```

### Service Ports

- Frontend: 5173 (dev) / 4173 (preview)
- WebSocket Gateway: 8080
- User Service: 8081  
- Room Service: 8082
- Message Service: 8083
- Kafka: 9092
- Kafka UI: 8090

