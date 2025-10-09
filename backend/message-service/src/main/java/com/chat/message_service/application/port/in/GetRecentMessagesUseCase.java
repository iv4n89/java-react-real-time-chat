package com.chat.message_service.application.port.in;

import java.util.List;
import java.util.UUID;

import com.chat.message_service.domain.model.Message;

public interface GetRecentMessagesUseCase {
    List<Message> execute(UUID roomId, int limit);
}
