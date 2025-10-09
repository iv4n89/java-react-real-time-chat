package com.chat.message_service.shared.exception;

public class InvalidMessageContentException extends RuntimeException {
    public InvalidMessageContentException(String reason) {
        super("Invalid message content: " + reason);
    }
}
