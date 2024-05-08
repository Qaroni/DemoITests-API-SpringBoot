package com.qaroni.demoitests.infrastructure.integration;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.net.URI;

@Component
@ConfigurationProperties(prefix = "api.integration.fizzbuzz.endpoint")
@Data
public class FizzBuzzEndpointProperties {
    private URI uri;
    private String service;
}
