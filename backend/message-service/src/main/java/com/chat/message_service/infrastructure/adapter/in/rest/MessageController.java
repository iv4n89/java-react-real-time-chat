package com.chat.message_service.infrastructure.adapter.in.rest;

import java.util.List;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.chat.message_service.application.service.MessageApplicationService;
import com.chat.message_service.domain.model.Message;
import com.chat.message_service.infrastructure.adapter.in.rest.dto.MessageResponse;
import com.chat.message_service.infrastructure.adapter.in.rest.dto.SendMessageRequest;
import com.chat.message_service.shared.exception.InvalidMessageContentException;

@RestController
@RequestMapping("/api/messages")
@CrossOrigin(origins = "*")
public class MessageController {
    private final MessageApplicationService messageApplicationService;

    public MessageController(MessageApplicationService messageApplicationService) {
        this.messageApplicationService = messageApplicationService;
    }

    @PostMapping
    public ResponseEntity<MessageResponse> sendMessage(@RequestBody SendMessageRequest request) {
        Message message = messageApplicationService.sendMessage(
            request.roomId(),
            request.userId(),
            request.username(),
            request.content()
        );
        return ResponseEntity
            .status(HttpStatus.CREATED)
            .body(MessageResponse.fromDomain(message));
    }

    @GetMapping("/room/{roomId}")
    public ResponseEntity<List<MessageResponse>> getRecentMessages(
        @PathVariable UUID roomId,
        @RequestParam(defaultValue = "50") int limit
    ) {
        List<Message> messages = messageApplicationService.getRecentMessages(roomId, limit);
        List<MessageResponse> response = messages.stream()
            .map(MessageResponse::fromDomain)
            .toList();
        return ResponseEntity.ok(response);
    }

    @ExceptionHandler(InvalidMessageContentException.class)
    public ResponseEntity<ErrorResponse> handleInvalidContent(InvalidMessageContentException ex) {
        ErrorResponse errorResponse = new ErrorResponse(HttpStatus.BAD_REQUEST.value(), ex.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ErrorResponse> handleIllegalArgument(IllegalArgumentException ex) {
        ErrorResponse errorResponse = new ErrorResponse(HttpStatus.BAD_REQUEST.value(), ex.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleGenericException(Exception ex) {
        ErrorResponse errorResponse = new ErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), "An unexpected error occurred.");
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
    }

    private record ErrorResponse(int status, String message) {}
}
