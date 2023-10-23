package com.example.CargoTracking.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice

public class GlobalExceptionHandler {
    @ExceptionHandler(value = UserNotFoundException.class)
    public ResponseEntity<ErrorMessage> userNotFoundException(UserNotFoundException userNotFoundException){
        ErrorMessage errorMessage = ErrorMessage.builder()
                .body(userNotFoundException.getMessage()).build();
        return new ResponseEntity(errorMessage, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(value = RecordNotFoundException.class)
    public ResponseEntity<ErrorMessage> recordNotFoundException(RecordNotFoundException recordNotFoundException){
        ErrorMessage errorMessage = ErrorMessage.builder().body(recordNotFoundException.getMessage()).build();
        return new ResponseEntity<>(errorMessage, HttpStatus.NOT_FOUND);
    }

}
