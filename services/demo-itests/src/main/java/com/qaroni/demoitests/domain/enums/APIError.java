package com.qaroni.demoitests.domain.enums;

import org.springframework.http.HttpStatus;

import java.util.Collections;
import java.util.List;

public enum APIError {
    VALIDATION_ERROR(HttpStatus.BAD_REQUEST, "The are attributes with wrong values", Collections.emptyList()),
    AUTHOR_NOT_FOUND(HttpStatus.NOT_FOUND, "Error retrieving author", List.of("Author not found")),
    AUTHOR_ALREADY_EXISTS(HttpStatus.CONFLICT, "Error creating author", List.of("Document ID already exists")),
    BOOK_NOT_FOUND(HttpStatus.NOT_FOUND, "Error retrieving book", List.of("Book not found")),
    BOOK_ALREADY_EXISTS(HttpStatus.CONFLICT, "Error creating book", List.of("ISBN value already exists"));

    private final String message;
    private final HttpStatus httpStatus;
    private final List<String> reasons;

    APIError(HttpStatus httpStatus, String message, List<String> reasons) {
        this.message = message;
        this.reasons = reasons;
        this.httpStatus = httpStatus;
    }

    public String getMessage() {
        return message;
    }

    public List<String> getReasons() {
        return reasons;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }
}
