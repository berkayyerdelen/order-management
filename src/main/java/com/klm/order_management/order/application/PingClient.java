package com.klm.order_management.order.application;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import reactor.core.publisher.Mono;
import reactor.util.retry.Retry;

import java.io.IOException;
import java.time.Duration;

@Service
public class PingClient {

    private final WebClient client;

    public PingClient(@Qualifier("pingHttpClient") WebClient client) {
        this.client = client;
    }

    public Mono<String> ping() {
        return client.get()
                .uri("/pong")
                .retrieve()
                .bodyToMono(String.class)
                .retryWhen(
                        Retry.fixedDelay(3, Duration.ofMillis(500)) // 3 retries with 500ms delay
                                .filter(throwable -> throwable instanceof WebClientResponseException || throwable instanceof IOException)
                );
    }
}