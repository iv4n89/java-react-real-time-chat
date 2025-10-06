package com.chat.user_service.application.port.in;

import java.util.UUID;

public interface DisconnectUserUseCase {
    void executeDisconnect(UUID userId);
}
