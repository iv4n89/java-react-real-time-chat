# Room Service

Microservicio de gestión de salas de chat.

## Responsabilidades

- Crear salas con nombres aleatorios
- Asignar usuarios a salas automáticamente (max 10 usuarios por sala)
- Gestionar entrada/salida de usuarios
- Cerrar salas vacías
- Publicar eventos a Kafka

## Arquitectura

Arquitectura Hexagonal / DDD:
- **Domain**: Lógica de negocio pura
- **Application**: Casos de uso
- **Infrastructure**: Adaptadores (REST, JPA, Kafka)

## Tecnologías

- Java 21
- Spring Boot 3.2+
- H2 Database (en memoria)
- Apache Kafka
- Lombok

## Ejecución

```bash
# Asegúrate de que Kafka esté corriendo
docker-compose up -d

# Ejecutar el servicio
./mvnw spring-boot:run
Endpoints
GET /api/rooms - Listar todas las salas
GET /api/rooms/available - Salas disponibles
GET /api/rooms/{roomId} - Obtener sala
POST /api/rooms/assign - Asignar usuario manualmente
DELETE /api/rooms/members/{userId} - Remover usuario
Kafka
Consume: user.events (UserCreatedEvent, UserDisconnectedEvent) Publica: room.events (RoomCreatedEvent, UserJoinedRoomEvent, etc.)
H2 Console
http://localhost:8082/h2-console
JDBC URL: jdbc:h2:mem:roomdb
Username: sa
Password: (vacío)
```
