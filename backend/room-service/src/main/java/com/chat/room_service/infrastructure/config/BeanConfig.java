package com.chat.room_service.infrastructure.config;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.chat.room_service.application.port.in.AssignUserToRoomUseCase;
import com.chat.room_service.application.port.in.RemoveUserfromRoomUseCase;
import com.chat.room_service.application.port.out.EventPublisherPort;
import com.chat.room_service.application.port.out.RoomPersistencePort;
import com.chat.room_service.application.service.RoomApplicationService;
import com.chat.room_service.application.service.UserEventConsumerService;
import com.chat.room_service.domain.model.Room;
import com.chat.room_service.domain.model.RoomId;
import com.chat.room_service.domain.model.RoomStatus;
import com.chat.room_service.domain.repository.RoomRepository;
import com.chat.room_service.domain.service.RoomDomainService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

@Configuration
public class BeanConfig {
    @Bean
    public RoomDomainService roomDomainService(RoomPersistencePort roomPersistencePort) {
        return new RoomDomainService(new RoomRepository() {
            @Override
            public Room save(Room room) {
                return roomPersistencePort.save(room);
            }

            @Override
            public Optional<Room> findById(RoomId roomId) {
                return roomPersistencePort.findById(roomId);
            }

            @Override
            public Optional<Room> findByName(String name) {
                return roomPersistencePort.findByName(name);
            }

            @Override
            public List<Room> findByStatus(RoomStatus status) {
                return roomPersistencePort.findByStatus(status);
            }

            @Override
            public Optional<Room> findFirstByStatusOrderByCreatedAtAsc(RoomStatus status) {
                return roomPersistencePort.findFirstByStatusOrderByCreatedAtAsc(status);
            }

            @Override
            public Optional<Room> findByMemberUserId(UUID userId) {
                return roomPersistencePort.findByMemberUserId(userId);
            }

            @Override
            public List<Room> findAll() {
                return roomPersistencePort.findAll();
            }

            @Override
            public void deleteById(RoomId roomId) {
                roomPersistencePort.deleteById(roomId);
            }

            @Override
            public boolean existsByName(String name) {
                return roomPersistencePort.existsByName(name);
            }
        });
    }

    @Bean
    public RoomApplicationService roomApplicationService(
            RoomPersistencePort roomPersistencePort,
            EventPublisherPort eventPublisherPort,
            RoomDomainService roomDomainService) {
        return new RoomApplicationService(roomPersistencePort, eventPublisherPort, roomDomainService);
    }

    @Bean
    public UserEventConsumerService userEventConsumerService(
            AssignUserToRoomUseCase assignUserToRoomUseCase,
            RemoveUserfromRoomUseCase removeUserfromRoomUseCase) {
        return new UserEventConsumerService(assignUserToRoomUseCase, removeUserfromRoomUseCase);
    }

    @Bean
    public ObjectMapper objectMapper() {
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        return mapper;
    }
}
