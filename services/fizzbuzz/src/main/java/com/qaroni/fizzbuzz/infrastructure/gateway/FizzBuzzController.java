package com.qaroni.fizzbuzz.infrastructure.gateway;

import com.qaroni.fizzbuzz.domain.dto.FizzBuzzResponseDTO;
import com.qaroni.fizzbuzz.infrastructure.ports.inbound.FizzBuzzService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/fizzbuzz")
public class FizzBuzzController {

    @Autowired
    private FizzBuzzService fizzBuzzService;

    @GetMapping("/{number}")
    public ResponseEntity<FizzBuzzResponseDTO> fizzBuzz(@PathVariable int number) {
        return ResponseEntity.ok(FizzBuzzResponseDTO.builder()
                .input(number)
                .output(fizzBuzzService.fizzBuzz(number))
                .build());
    }

}
