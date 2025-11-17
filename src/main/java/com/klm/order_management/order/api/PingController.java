package com.klm.order_management.order.api;

import com.klm.order_management.order.application.PingClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
public class PingController {

    private final PingClient pingClient;

    public PingController(PingClient pingClient) {
        this.pingClient = pingClient;
    }

    @GetMapping("/ping")
    public Mono<String> ping() {
        return pingClient.ping();
    }

    @GetMapping("/ping-twice")
    public Mono<String> pingTwice() {
        return Mono.fromFuture(pingClient.pingTwice());}
}