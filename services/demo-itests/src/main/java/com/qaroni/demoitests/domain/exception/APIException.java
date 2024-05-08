package com.qaroni.demoitests.domain.exception;

import com.qaroni.demoitests.domain.enums.APIError;
import org.springframework.http.HttpStatus;

import java.util.List;

public class APIException extends RuntimeException {
    private final HttpStatus httpStatus;
    private List<String> reasons;

    public APIException(APIError apiError) {
        super(apiError.getMessage());
        this.httpStatus = apiError.getHttpStatus();
        this.reasons = apiError.getReasons();
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }

    public List<String> getReasons() {
        return reasons;
    }

    public void setReasons(List<String> reasons) {
        this.reasons = reasons;
    }
}
