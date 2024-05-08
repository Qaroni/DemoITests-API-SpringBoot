package com.qaroni.demoitests.infrastructure.integration;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class FizzBuzzResponseDTO {
    private int input;
    private String output;
}
