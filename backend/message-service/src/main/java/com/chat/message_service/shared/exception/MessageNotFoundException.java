package com.chat.message_service.shared.exception;

import java.util.UUID;

public class MessageNotFoundException extends RuntimeException {
    public MessageNotFoundException(UUID messageId) {
        super("Message with ID " + messageId + " not found.");
    }
}
