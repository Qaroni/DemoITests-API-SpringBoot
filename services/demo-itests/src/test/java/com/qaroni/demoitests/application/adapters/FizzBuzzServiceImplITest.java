package com.qaroni.demoitests.application.adapters;

import com.qaroni.demoitests.infrastructure.ports.outbound.FizzBuzzService;
import com.qaroni.demoitests.support.wiremock.Wiremock;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DisplayName("FizzBuzzServiceImpl Integration Tests")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class FizzBuzzServiceImplITest {

    @Autowired
    FizzBuzzService fizzBuzzService;

    @Wiremock(port = 8080, stubs = "wiremock/fizzbuzz/fizz")
    @DisplayName("Given the number 3, when fizzBuzz is called, then it should return Fizz")
    @Test
    void fizz() {
            // Given
        int number = 3;
        String expected = "Fizz";

        // When
        String result = fizzBuzzService.fizzBuzz(number);

        // Then
        assertEquals(expected, result);
    }

    @Wiremock(port = 8080, stubs = "wiremock/fizzbuzz/buzz")
    @DisplayName("Given the number 5, when fizzBuzz is called, then it should return Buzz")
    @Test
    void buzz() {
        // Given
        int number = 5;
        String expected = "Buzz";

        // When
        String result = fizzBuzzService.fizzBuzz(number);

        // Then
        assertEquals(expected, result);
    }

    @Wiremock(port = 8080, stubs = "wiremock/fizzbuzz/fizzbuzz")
    @DisplayName("Given the number 15, when fizzBuzz is called, then it should return FizzBuzz")
    @Test
    void fizzBuzz() {
        // Given
        int number = 15;
        String expected = "FizzBuzz";

        // When
        String result = fizzBuzzService.fizzBuzz(number);

        // Then
        assertEquals(expected, result);
    }

    @Wiremock(port = 8080, stubs = "wiremock/fizzbuzz/number")
    @DisplayName("Given the number 1, when fizzBuzz is called, then it should return 1")
    @Test
    void number() {
        // Given
        int number = 1;
        String expected = "1";

        // When
        String result = fizzBuzzService.fizzBuzz(number);

        // Then
        assertEquals(expected, result);
    }
}
