package com.example.covidcasemanager.exception;

import com.example.covidcasemanager.exception.dto.ErrorMessageDto;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.stream.Collectors;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorMessageDto> handler(MethodArgumentNotValidException ex, HttpServletRequest request) {
        return ResponseEntity.badRequest().body(
                buildErrorMessage(HttpStatus.BAD_REQUEST, request.getRequestURI())
                        .message(ex.getBindingResult().getFieldErrors().stream()
                                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                                .collect(Collectors.toList()).toString())
                        .build()
        );
    }

    @ExceptionHandler(ResourceAlreadyExistsException.class)
    public ResponseEntity<ErrorMessageDto> handler(ResourceAlreadyExistsException ex, HttpServletRequest request) {
        return ResponseEntity.badRequest().body(
                buildErrorMessage(HttpStatus.BAD_REQUEST, request.getRequestURI())
                        .message(ex.getMessage())
                        .build()
        );
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorMessageDto> handler(ResourceNotFoundException ex, HttpServletRequest request) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                buildErrorMessage(HttpStatus.NOT_FOUND, request.getRequestURI())
                        .message(ex.getMessage())
                        .build()
        );
    }

    private ErrorMessageDto.ErrorMessageDtoBuilder buildErrorMessage(HttpStatus httpStatus, String path) {
        return ErrorMessageDto.builder()
                .timestamp(LocalDateTime.now())
                .status(httpStatus.value())
                .error(httpStatus.getReasonPhrase())
                .path(path);
    }
}
