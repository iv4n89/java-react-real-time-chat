package com.chat.room_service.application.port.in;

import java.util.UUID;

public interface RemoveUserfromRoomUseCase {
    void executeRemoveUser(UUID userId);
}
