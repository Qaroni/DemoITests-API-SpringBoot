package com.qaroni.demoitests.application.adapters;

import com.qaroni.demoitests.infrastructure.integration.FizzBuzzEndpointProperties;
import com.qaroni.demoitests.infrastructure.integration.FizzBuzzResponseDTO;
import com.qaroni.demoitests.infrastructure.ports.outbound.FizzBuzzService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Service
public class FizzBuzzServiceImpl implements FizzBuzzService {

    @Autowired
    private WebClient fizzBuzzClient;

    @Autowired
    private FizzBuzzEndpointProperties fizzBuzzEndpointProperties;

    @Override
    public String fizzBuzz(int number) {
        FizzBuzzResponseDTO fizzBuzzResponseDTO = fizzBuzzClient.get()
                .uri(uriBuilder -> uriBuilder.path(fizzBuzzEndpointProperties.getService().concat("/" + number))
                        .build())
                .retrieve()
                .bodyToMono(FizzBuzzResponseDTO.class)
                .block();
        return fizzBuzzResponseDTO != null ? fizzBuzzResponseDTO.getOutput() : null;
    }
}
