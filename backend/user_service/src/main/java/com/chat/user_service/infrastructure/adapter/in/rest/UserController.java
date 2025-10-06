package com.chat.user_service.infrastructure.adapter.in.rest;

import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.chat.user_service.application.service.UserApplicationService;
import com.chat.user_service.domain.model.User;
import com.chat.user_service.infrastructure.adapter.in.rest.dto.JoinChatResponse;
import com.chat.user_service.infrastructure.adapter.in.rest.dto.UserResponse;
import com.chat.user_service.shared.exception.UserNotFoundException;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;



@RestController
@RequestMapping("/api/users")
@CrossOrigin(origins = "*")
public class UserController {

    private final UserApplicationService userApplicationService;

    public UserController(UserApplicationService userApplicationService) {
        this.userApplicationService = userApplicationService;
    }

    @PostMapping("/join")
    public ResponseEntity<JoinChatResponse> joinChat() {
        User user = userApplicationService.execute();
        return ResponseEntity
            .status(HttpStatus.CREATED)
            .body(JoinChatResponse.fromDomain(user));
    }

    @GetMapping("/{userId}")
    public ResponseEntity<UserResponse> getUser(@PathVariable UUID userId) {
        User user = userApplicationService.execute(userId);
        return ResponseEntity.ok(UserResponse.fromDomain(user));
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<Void> disconnectUser(@PathVariable UUID userId) {
        userApplicationService.execute(userId);
        return ResponseEntity.noContent().build();
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleUserNotFound(UserNotFoundException ex) {
        ErrorResponse error = new ErrorResponse(
            HttpStatus.NOT_FOUND.value(),
            ex.getMessage()
        );
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleGenericException(Exception ex) {
        ErrorResponse error = new ErrorResponse(
            HttpStatus.INTERNAL_SERVER_ERROR.value(),
            "An unexpected error occurred: " + ex.getMessage()
        );
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
    }

    private record ErrorResponse(int status, String message) {}
}
