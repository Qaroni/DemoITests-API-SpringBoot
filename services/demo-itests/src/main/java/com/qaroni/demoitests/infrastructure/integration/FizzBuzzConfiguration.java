package com.qaroni.demoitests.infrastructure.integration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class FizzBuzzConfiguration {
    @Bean
    public WebClient fizzBuzzClient(FizzBuzzEndpointProperties fizzBuzzEndpointProperties) {
        return WebClient.create(fizzBuzzEndpointProperties.getUri().toString());
    }
}
