package com.github.gayanch.library.error;

import com.github.gayanch.library.model.ErrorResponse;
import io.swagger.v3.oas.annotations.Hidden;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
@Hidden //Do not include in Open API web ui
public class AppErrorHandler {
    @ExceptionHandler(exception = AppException.class)
    public ResponseEntity<ErrorResponse> handleAppException(AppException ex) {
        var error = new ErrorResponse(ex.getCode(), ex.getMessage());
        return ResponseEntity.status(ex.getCode()).body(error);
    }

    //Catch all exception handler. Define more specific handlers to override this behavior
    @ExceptionHandler(exception = Exception.class)
    public ResponseEntity<ErrorResponse> catchAllErrorHandler(Exception ex) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
            .body(new ErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), ex.getMessage()));
    }
}
