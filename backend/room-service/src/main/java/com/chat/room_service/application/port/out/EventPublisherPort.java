package com.chat.room_service.application.port.out;

import com.chat.room_service.domain.event.RoomClosedEvent;
import com.chat.room_service.domain.event.RoomCreatedEvent;
import com.chat.room_service.domain.event.RoomFullEvent;
import com.chat.room_service.domain.event.UserJoinedRoomEvent;
import com.chat.room_service.domain.event.UserLeftRoomEvent;

public interface EventPublisherPort {
    void publishRoomCreated(RoomCreatedEvent event);
    void publishUserJoinedRoom(UserJoinedRoomEvent event);
    void publishUserLeftRoom(UserLeftRoomEvent event);
    void publishRoomFull(RoomFullEvent event);
    void publishRoomClosed(RoomClosedEvent event);
}
