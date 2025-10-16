package com.chiikawa.demo.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Service
@Slf4j
public class TestService {
    private WebClient webClient = WebClient
            .builder()
            .baseUrl("http://localhost:8080")
            .build();

    public Object testSynApi(){
        String uri = "/photos";

        log.info("TEST: Before calling Api");

        // var = Object
        var response = webClient.get()
                .uri(uri)
                .retrieve()
                .bodyToMono(String.class)
                .block();

        log.info("TEST: Response");

        log.info("TEST: After calling Api");

        return response;
    }
}
