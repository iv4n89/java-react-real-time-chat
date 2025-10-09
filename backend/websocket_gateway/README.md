# WebSocket Gateway Service

Gateway para comunicación en tiempo real del chat.

## Responsabilidades

- Gestionar conexiones WebSocket (STOMP)
- Consumir eventos de Kafka
- Distribuir mensajes en tiempo real a clientes conectados

## WebSocket Endpoints

### Conexión
ws://localhost:8080/ws

### Suscripción (cliente se suscribe a una sala)
SUBSCRIBE /topic/room/{roomId}

### Enviar mensaje (opcional - también puede ir vía REST al Message Service)
SEND /app/chat/{roomId} Body: { userId, username, content }

## Kafka Consumers

- **chat.messages**: Distribuye mensajes de chat
- **room.events**: Notificaciones de sala (usuarios entrando/saliendo)
- **user.events**: Eventos de usuarios (opcional)

## Tecnologías

- Spring Boot WebSocket (STOMP)
- Spring Kafka (Consumer)
- SockJS (fallback)

## Ejecutar

```bash
./mvnw spring-boot:run
Endpoints REST (debugging)
GET /api/connections - Ver todas las conexiones activas
GET /api/connections/room/{roomId} - Ver conexiones de una sala
