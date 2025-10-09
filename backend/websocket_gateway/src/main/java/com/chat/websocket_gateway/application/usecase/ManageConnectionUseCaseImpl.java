package com.chat.websocket_gateway.application.usecase;

import java.util.UUID;

import com.chat.websocket_gateway.application.port.in.BroadcastMessageUseCase;
import com.chat.websocket_gateway.application.port.in.ManageConnectionUseCase;
import com.chat.websocket_gateway.domain.model.ChatMessage;
import com.chat.websocket_gateway.domain.model.WebSocketConnection;
import com.chat.websocket_gateway.domain.repository.ConnectionRepository;

public class ManageConnectionUseCaseImpl implements ManageConnectionUseCase {
    private final ConnectionRepository connectionRepository;
    private final BroadcastMessageUseCase broadcastMessageUseCase;

    public ManageConnectionUseCaseImpl(ConnectionRepository connectionRepository,
                                       BroadcastMessageUseCase broadcastMessageUseCase) {
        this.connectionRepository = connectionRepository;
        this.broadcastMessageUseCase = broadcastMessageUseCase;
    }

    @Override
    public void connection(String sessionId, UUID userId, String username, UUID roomId) {
        WebSocketConnection connection = WebSocketConnection.create(
            sessionId, userId, username, roomId);
        connectionRepository.addConnection(connection);

        System.out.println("User connected: " + username + " (session: " + sessionId + ", room: " + roomId + ")");
        ChatMessage joinMessage = ChatMessage.userJoined(roomId, username);
        broadcastMessageUseCase.execute(joinMessage);
    }

    @Override
    public void disconnect(String sessionId) {
        connectionRepository.findBySessionId(sessionId).ifPresent(connection -> {
            connectionRepository.removeConnection(sessionId);
            System.out.println("User disconnected: " + connection.getUsername() + " (session: " + sessionId + ")");
            ChatMessage leftMessage = ChatMessage.userLeft(
                connection.getRoomId(),
                connection.getUsername()
            );
            broadcastMessageUseCase.execute(leftMessage);
        });
    }

    @Override
    public int getConnectedUsersCount(UUID roomId) {
        return connectionRepository.countConnectionsByRoomId(roomId);
    }
}
