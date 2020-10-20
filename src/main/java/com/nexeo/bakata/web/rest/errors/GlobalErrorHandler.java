package com.nexeo.bakata.web.rest.errors;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class GlobalErrorHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(NoSuchAccountException.class)
    public ResponseEntity<Object> handleNoSuchAccountException(final NoSuchAccountException ex) {
        String bodyOfResponse = "No account matched provided Id";
        return ResponseEntity.badRequest().body(bodyOfResponse+ex.getMessage());
    }
}