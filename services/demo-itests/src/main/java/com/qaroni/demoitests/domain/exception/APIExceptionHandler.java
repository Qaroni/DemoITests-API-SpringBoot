package com.qaroni.demoitests.domain.exception;

import com.qaroni.demoitests.domain.dto.ErrorDTO;
import com.qaroni.demoitests.domain.enums.APIError;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.ArrayList;
import java.util.List;

@RestControllerAdvice
public class APIExceptionHandler extends ResponseEntityExceptionHandler {
    @ExceptionHandler(APIException.class)
    public ResponseEntity<ErrorDTO> duplicateResource(APIException e, WebRequest request) {
        return ResponseEntity.status(e.getHttpStatus()).body(
                ErrorDTO.builder().description(e.getMessage()).reasons(e.getReasons()).build()
        );
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
                                                                  HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        List<String> reasons = new ArrayList<>();
        for (FieldError error : ex.getBindingResult().getFieldErrors()) {
            reasons.add(String.format("%s - %s", error.getField(), error.getDefaultMessage()));
        }
        return ResponseEntity.status(APIError.VALIDATION_ERROR.getHttpStatus())
                .body(ErrorDTO.builder().description(APIError.VALIDATION_ERROR.getMessage()).reasons(reasons).build());
    }
}
