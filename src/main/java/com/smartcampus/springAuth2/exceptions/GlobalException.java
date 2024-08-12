package com.smartcampus.springAuth2.exceptions;

import com.smartcampus.springAuth2.schemas.SimpleResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.List;

@ControllerAdvice
public class GlobalException {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<SimpleResponse<String>> validationRequestBody(MethodArgumentNotValidException ex) {
        BindingResult result = ex.getBindingResult();
        List<FieldError> fieldErrors = result.getFieldErrors();
        String error = "n/a";
        for (FieldError fieldError : fieldErrors) {
            error = fieldError.getField() + " : " + fieldError.getDefaultMessage();
        }
        return new ResponseEntity<>(SimpleResponse.<String>
                        builder()
                .code(HttpStatus.BAD_REQUEST.value())
                .ok(false)
                .error(error)
                .value(null)
                .message("Solicitud de parametros no valida").build(),
                HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<SimpleResponse<String>> notFoundException(NotFoundException exception) {
        return new ResponseEntity<>(SimpleResponse.<String>
                        builder()
                .code(HttpStatus.NOT_FOUND.value())
                .ok(false)
                .error(null)
                .message(exception.getMessage())
                .value(null)
                .build(),
                HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(RequestTimeoutException.class)
    public ResponseEntity<SimpleResponse<String>> requestTimeoutException(RequestTimeoutException exception) {
        return new ResponseEntity<>(SimpleResponse.<String>
                        builder()
                .code(HttpStatus.REQUEST_TIMEOUT.value())
                .ok(false)
                .error(null)
                .message(exception.getMessage())
                .value(null)
                .build(),
                HttpStatus.REQUEST_TIMEOUT);
    }

    @ExceptionHandler(ConflictException.class)
    public ResponseEntity<SimpleResponse<String>> conflictException(ConflictException exception) {
        return new ResponseEntity<>(SimpleResponse.<String>
                        builder()
                .code(HttpStatus.CONFLICT.value())
                .ok(false)
                .error(null)
                .message(exception.getMessage())
                .value(null)
                .build(),
                HttpStatus.CONFLICT);
    }

    @ExceptionHandler(UnauthorizedException.class)
    public ResponseEntity<SimpleResponse<String>> unAuthorizedException(UnauthorizedException exception) {
        return new ResponseEntity<>(SimpleResponse.<String>
                        builder()
                .code(HttpStatus.UNAUTHORIZED.value())
                .ok(false)
                .error(null)
                .message(exception.getMessage())
                .value(null)
                .build(),
                HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(ForbiddenException.class)
    public ResponseEntity<SimpleResponse<String>> unAuthorizedException(ForbiddenException exception) {
        return new ResponseEntity<>(SimpleResponse.<String>
                        builder()
                .code(HttpStatus.FORBIDDEN.value())
                .ok(false)
                .error(null)
                .message(exception.getMessage())
                .value(null)
                .build(),
                HttpStatus.FORBIDDEN);
    }
}
