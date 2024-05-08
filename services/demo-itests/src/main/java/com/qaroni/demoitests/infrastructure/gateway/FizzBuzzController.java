package com.qaroni.demoitests.infrastructure.gateway;

import com.qaroni.demoitests.infrastructure.ports.outbound.FizzBuzzService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/external/fizzbuzz")
public class FizzBuzzController {

    @Autowired
    private FizzBuzzService fizzBuzzService;

    @GetMapping("/{number}")
    public String fizzBuzz(@PathVariable int number) {
        return fizzBuzzService.fizzBuzz(number);
    }
}
