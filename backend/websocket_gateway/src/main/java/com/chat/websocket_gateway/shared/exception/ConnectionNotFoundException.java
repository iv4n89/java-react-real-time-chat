package com.chat.websocket_gateway.shared.exception;

public class ConnectionNotFoundException extends RuntimeException {
    public ConnectionNotFoundException(String sessionId) {
        super("Connection with session ID " + sessionId + " not found.");
    }
}
