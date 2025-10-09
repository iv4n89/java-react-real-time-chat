package com.chat.message_service.domain.valueobject;

public final class MessageContent extends ValueObject<String> {
    public MessageContent(String value) {
        super(value);
    }

    @Override
    protected String getValidationErrorMessage() {
        return "Invalid Message Content";
    }

    @Override
    protected void validate() {
        if (this.value == null || this.value.trim().isEmpty()) {
            throw new IllegalArgumentException(this.getValidationErrorMessage());
        }
    }

    public static MessageContent of(String content) {
        return new MessageContent(content);
    }
}
