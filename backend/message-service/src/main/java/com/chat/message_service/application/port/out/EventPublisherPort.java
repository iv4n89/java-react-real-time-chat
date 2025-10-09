package com.chat.message_service.application.port.out;

import com.chat.message_service.domain.event.MessageSentEvent;

public interface EventPublisherPort {
    void publishMessageSent(MessageSentEvent event);
}
