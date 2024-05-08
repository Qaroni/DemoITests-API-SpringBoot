package com.qaroni.fizzbuzz.infrastructure.gateway;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

@Slf4j
@DisplayName("FizzBuzzController Integration Test")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class FizzBuzzControllerITest {

    public static final String CONTENT_TYPE = "application/json";

    private MockMvc mockMvc;

    @BeforeEach
    public void setUp(WebApplicationContext webApplicationContext) {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @DisplayName("Should return fizzbuzz response")
    @Test
    void fizzBuzz() throws Exception {
        log.info("Test fizzBuzz");

        // Given
        int number = 15;

        // When
        MvcResult mvcResult = mockMvc.perform(get("/fizzbuzz/".concat(String.valueOf(number))).contentType(CONTENT_TYPE))
                .andReturn();

        // Then
        assertAll(
            () -> assertEquals(200, mvcResult.getResponse().getStatus()),
            () -> assertEquals(CONTENT_TYPE, mvcResult.getResponse().getContentType()),
            () -> assertEquals("{\"input\":15,\"output\":\"FizzBuzz\"}", mvcResult.getResponse().getContentAsString())
        );
    }
}