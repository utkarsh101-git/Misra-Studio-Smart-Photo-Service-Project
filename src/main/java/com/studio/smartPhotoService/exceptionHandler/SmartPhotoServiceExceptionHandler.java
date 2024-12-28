package com.studio.smartPhotoService.exceptionHandler;

import com.studio.smartPhotoService.exceptions.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

/*
    Global exception handler for this microservice
    Handles all exception occurred in this service
 */
@RestControllerAdvice
public class SmartPhotoServiceExceptionHandler {

    /* Handles Exception If wedding host already exists
     */

    private static final Logger logger = LoggerFactory.getLogger(SmartPhotoServiceExceptionHandler.class);

    @ExceptionHandler({ShouldNotOccurException.class, WeddingHostAlreadyExists.class, WeddingObjectAlreadyExistsException.class})
    public ResponseEntity<?> allExceptionHandler(RuntimeException e) {
        logger.info("this should not occur");
        logger.info(e.getMessage());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
    }

    /*
    Exception handling for any validation failed inside the WeddingHost Entity
    returns a Response Entity holding a Map with fields and error message
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<Map<String, String>> handleValidationExceptionForWeddingHostEntity(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();

        ex.getBindingResult().getFieldErrors().forEach(error ->
                errors.put(error.getField(), error.getDefaultMessage())
        );
        return ResponseEntity.badRequest().body(errors);
    }

    @ExceptionHandler({WeddingHostDoesNotExistException.class, WeddingMemberDoesNotExistsException.class, WeddingObjectDoesNotExistsException.class})
    public ResponseEntity<?> weddingHostDoesNotExistHandler(RuntimeException e) {
        logger.error("Exception occurred When Particular Object is not Found ");
        logger.error(e.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
    }
}
