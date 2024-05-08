package com.qaroni.fizzbuzz.application.services;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@Slf4j
@DisplayName("FizzBuzz Service Test")
@SpringBootTest
class FizzBuzzServiceImplTest {

    @DisplayName("Test FizzBuzz")
    @Test
    void fizzBuzz() {

        log.info("Test FizzBuzz");

        FizzBuzzServiceImpl fizzBuzzService = new FizzBuzzServiceImpl();
        assertEquals("FizzBuzz", fizzBuzzService.fizzBuzz(15));
        assertEquals("Fizz", fizzBuzzService.fizzBuzz(3));
        assertEquals("Buzz", fizzBuzzService.fizzBuzz(5));
        assertEquals("1", fizzBuzzService.fizzBuzz(1));
    }
}