package com.chat.user_service.application.port.out;

import com.chat.user_service.domain.event.UserCreatedEvent;
import com.chat.user_service.domain.event.UserDisconnectedEvent;
import com.chat.user_service.domain.event.UserJoinedRoomEvent;

public interface EventPublisherPort {
    void publishUserCreated(UserCreatedEvent event);
    void publishUserJoinedRoom(UserJoinedRoomEvent event);
    void publishUserDisconnected(UserDisconnectedEvent event);
}
