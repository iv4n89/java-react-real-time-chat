package com.chat.user_service.application.port.in;

import java.util.UUID;

import com.chat.user_service.domain.model.User;

public interface GetUserUseCase {
    User execute(UUID userId);
}
