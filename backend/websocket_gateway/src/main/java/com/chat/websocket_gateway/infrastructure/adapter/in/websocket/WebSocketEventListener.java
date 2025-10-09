package com.chat.websocket_gateway.infrastructure.adapter.in.websocket;

import java.util.UUID;

import org.springframework.context.event.EventListener;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionConnectEvent;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

import com.chat.websocket_gateway.application.port.in.ManageConnectionUseCase;

@Component
public class WebSocketEventListener {
    private final ManageConnectionUseCase manageConnectionUseCase;

    public WebSocketEventListener(ManageConnectionUseCase manageConnectionUseCase) {
        this.manageConnectionUseCase = manageConnectionUseCase;
    }

    @EventListener
    public void handleWebSocketConnectListener(SessionConnectEvent event) {
        StompHeaderAccessor headerAccessor = StompHeaderAccessor.wrap(event.getMessage());
        String sessionId = headerAccessor.getSessionId();

        String userIdStr = headerAccessor.getFirstNativeHeader("userId");
        String username = headerAccessor.getFirstNativeHeader("username");
        String roomIdStr = headerAccessor.getFirstNativeHeader("roomId");

        if (userIdStr != null && username != null && roomIdStr != null) {
            UUID userId = UUID.fromString(userIdStr);
            UUID roomId = UUID.fromString(roomIdStr);

            manageConnectionUseCase.connection(sessionId, userId, username, roomId);
        } else {
            System.err.println("Missing required headers for WebSocket connection");
        }
    }

    @EventListener
    public void handleWebSocketDisconnectListener(SessionDisconnectEvent event) {
        StompHeaderAccessor headerAccessor = StompHeaderAccessor.wrap(event.getMessage());
        String sessionId = headerAccessor.getSessionId();

        if (sessionId != null) {
            manageConnectionUseCase.disconnect(sessionId);
        }
    }
}
