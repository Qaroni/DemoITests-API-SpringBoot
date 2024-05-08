package com.qaroni.demoitests.application.adapters;

import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.core.WireMockConfiguration;
import com.qaroni.demoitests.infrastructure.ports.outbound.FizzBuzzService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.util.AssertionErrors;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DisplayName("FizzBuzzServiceImpl Integration Tests")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class FizzBuzzServiceImplITest {

    private static WireMockServer wireMockServer;

    @Autowired
    FizzBuzzService fizzBuzzService;

    @BeforeAll
    public static void setUp() {
        // TODO: It's failing
        /*WireMockConfiguration config = WireMockConfiguration.options()
                .port(8080)
                .usingFilesUnderClasspath("src/tests/resources/wiremock");

        wireMockServer = new WireMockServer(config);
        wireMockServer.start();*/
    }

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

}
