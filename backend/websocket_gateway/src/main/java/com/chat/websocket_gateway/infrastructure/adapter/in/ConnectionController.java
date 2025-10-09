package com.chat.websocket_gateway.infrastructure.adapter.in;

import java.util.Set;
import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.chat.websocket_gateway.application.port.in.ManageConnectionUseCase;
import com.chat.websocket_gateway.domain.model.WebSocketConnection;
import com.chat.websocket_gateway.domain.repository.ConnectionRepository;

@RestController
@RequestMapping("/api/connections")
@CrossOrigin(origins = "*")
public class ConnectionController {
    private final ConnectionRepository connectionRepository;
    private final ManageConnectionUseCase manageConnectionUseCase;

    public ConnectionController(ConnectionRepository connectionRepository, ManageConnectionUseCase manageConnectionUseCase) {
        this.connectionRepository = connectionRepository;
        this.manageConnectionUseCase = manageConnectionUseCase;
    }

    @GetMapping
    public ResponseEntity<Set<WebSocketConnection>> getAllConnections() {
        return ResponseEntity.ok(connectionRepository.findAll());
    }

    @GetMapping("/room/{roomId}")
    public ResponseEntity<ConnectionInfo> getConnectionsByRoom(@PathVariable UUID roomId) {
        Set<WebSocketConnection> connections = connectionRepository.findByRoomId(roomId);
        int count = manageConnectionUseCase.getConnectedUsersCount(roomId);
        return ResponseEntity.ok(new ConnectionInfo(roomId, count, connections));
    }

    private record ConnectionInfo(
        UUID roomId,
        int connectedUsers,
        Set<WebSocketConnection> connections
    ) {}
}
