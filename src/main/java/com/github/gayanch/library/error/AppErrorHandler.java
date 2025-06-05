package com.github.gayanch.library.error;

import com.github.gayanch.library.model.ErrorResponse;
import io.swagger.v3.oas.annotations.Hidden;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
@Hidden //Do not include in Open API web ui
public class AppErrorHandler {
    private static final Logger log = LoggerFactory.getLogger(AppErrorHandler.class);

    @ExceptionHandler(exception = AppException.class)
    public ResponseEntity<ErrorResponse> handleAppException(AppException ex) {
        log.error("Application error occurred", ex);

        var error = new ErrorResponse(ex.getCode(), ex.getMessage());
        return ResponseEntity.status(ex.getCode()).body(error);
    }

    @ExceptionHandler(exception = MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleValidationExceptions(MethodArgumentNotValidException ex) {
        log.error("Data validation error occurred", ex);

        var message = "%s %s".formatted(ex.getFieldError().getField(), ex.getFieldError().getDefaultMessage());

        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
            .body(new ErrorResponse(HttpStatus.BAD_REQUEST.value(), message));
    }

    //Catch all exception handler. Define more specific handlers to override this behavior
    @ExceptionHandler(exception = Exception.class)
    public ResponseEntity<ErrorResponse> catchAllErrorHandler(Exception ex) {
        log.error("Unknown error occurred", ex);

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
            .body(new ErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), ex.getMessage()));
    }
}
