package com.chat.room_service.infrastructure.adapter.in.rest;

import java.util.List;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.chat.room_service.application.service.RoomApplicationService;
import com.chat.room_service.domain.model.Room;
import com.chat.room_service.infrastructure.adapter.in.rest.dto.AssignUserRequest;
import com.chat.room_service.infrastructure.adapter.in.rest.dto.RoomResponse;
import com.chat.room_service.shared.exception.RoomFullException;
import com.chat.room_service.shared.exception.RoomNotFoundException;
import com.chat.room_service.shared.exception.UserNotInRoomException;

@RestController
@RequestMapping("/api/rooms")
@CrossOrigin(origins = "*")
public class RoomController {
    public final RoomApplicationService roomApplicationService;

    public RoomController(RoomApplicationService roomApplicationService) {
        this.roomApplicationService = roomApplicationService;
    }

    @GetMapping
    public ResponseEntity<List<RoomResponse>> getAllRooms() {
        List<Room> rooms = roomApplicationService.findAll();
        List<RoomResponse> roomResponses = rooms.stream()
            .map(RoomResponse::fromDomain)
            .toList();

        return ResponseEntity.ok(roomResponses);
    }

    @GetMapping("/available")
    public ResponseEntity<List<RoomResponse>> getAvailableRooms() {
        List<Room> rooms = roomApplicationService.executeAvailable();
        List<RoomResponse> response = rooms.stream()
            .map(RoomResponse::fromDomain)
            .toList();

        return ResponseEntity.ok(response);
    }

    @GetMapping("/{roomId}")
    public ResponseEntity<RoomResponse> getRoom(@PathVariable UUID roomId) {
        Room room = roomApplicationService.executeGetRoom(roomId);
        return ResponseEntity.ok(RoomResponse.fromDomain(room));
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<RoomResponse> getUserRoom(@PathVariable UUID userId) {
        Room room = roomApplicationService.executeGetRoomByUserId(userId);
        return ResponseEntity.ok(RoomResponse.fromDomain(room));
    }

    @PostMapping("/assign")
    public ResponseEntity<RoomResponse> assignUserToRoom(@RequestBody AssignUserRequest request) {
        Room room = roomApplicationService.executeAssign(request.userId(), request.username());
        return ResponseEntity
            .status(HttpStatus.CREATED)
            .body(RoomResponse.fromDomain(room));
    }

    @DeleteMapping("/members/{userId}")
    public ResponseEntity<Void> removeUserFromRoom(@PathVariable UUID userId) {
        roomApplicationService.executeRemoveUser(userId);
        return ResponseEntity.noContent().build();
    }

    @ExceptionHandler(RoomNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleRoomNotFound(RoomNotFoundException ex) {
        ErrorResponse error = new ErrorResponse(
            HttpStatus.NOT_FOUND.value(),
            ex.getMessage()
        );
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
    }

    @ExceptionHandler(RoomFullException.class)
    public ResponseEntity<ErrorResponse> handleRoomFull(RoomFullException ex) {
        ErrorResponse error = new ErrorResponse(
            HttpStatus.CONFLICT.value(),
            ex.getMessage()
        );
        return ResponseEntity.status(HttpStatus.CONFLICT).body(error);
    }

    @ExceptionHandler(UserNotInRoomException.class)
    public ResponseEntity<ErrorResponse> handleUserNotInRoom(UserNotInRoomException ex) {
        ErrorResponse error = new ErrorResponse(
            HttpStatus.INTERNAL_SERVER_ERROR.value(),
            "An unexpected error occurred: " + ex.getMessage()
        );
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
    }

    private record ErrorResponse(int status, String message) {};
}
