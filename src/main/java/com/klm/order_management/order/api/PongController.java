package com.klm.order_management.order.api;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PongController {

    private int counter = 0;

    @GetMapping("/pong")
    public String pong() {
        return "pong";
    }
}