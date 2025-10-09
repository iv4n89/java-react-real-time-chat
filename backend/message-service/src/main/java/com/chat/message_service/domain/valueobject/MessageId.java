package com.chat.message_service.domain.valueobject;

import java.util.UUID;

public final class MessageId extends ValueObject<UUID> {
    public MessageId(UUID value) {
        super(value);
    }

    @Override
    protected String getValidationErrorMessage() {
        return "Invalid Message ID";
    }

    @Override
    protected void validate() {
        if (this.value == null) {
            throw new IllegalArgumentException(this.getValidationErrorMessage());
        }
    }

    public static MessageId generate() {
        return new MessageId(UUID.randomUUID());
    }

    public static MessageId fromString(String id) {
        return new MessageId(UUID.fromString(id));
    }
}
