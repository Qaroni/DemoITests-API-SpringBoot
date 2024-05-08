package com.qaroni.fizzbuzz.domain.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class FizzBuzzResponseDTO {
    private int input;
    private String output;
}
