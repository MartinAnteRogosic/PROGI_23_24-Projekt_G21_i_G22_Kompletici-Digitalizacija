package hr.fer.progi.backend.exception;

import jakarta.persistence.Enumerated;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.util.Date;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(EmployeeNotFound.class)
    public ResponseEntity<ErrorObject> handleEmployeeNotFoundException(EmployeeNotFound ex, WebRequest request){
        ErrorObject errorObject = ErrorObject.builder()
                .statusCode(HttpStatus.NOT_FOUND)
                .message(ex.getMessage())
                .timestamp(new Date())
                .build();

        return new ResponseEntity<>(errorObject,HttpStatus.NOT_FOUND);
    }
}
