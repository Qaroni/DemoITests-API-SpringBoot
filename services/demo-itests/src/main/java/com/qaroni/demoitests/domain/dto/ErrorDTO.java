package com.qaroni.demoitests.domain.dto;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class ErrorDTO {
    private String description;
    private List<String> reasons;
}
