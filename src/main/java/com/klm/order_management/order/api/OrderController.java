package com.klm.order_management.order.api;

import com.klm.order_management.order.api.requests.CreateOrderRequest;
import com.klm.order_management.order.api.requests.UpdatePaymentRequest;
import com.klm.order_management.order.api.response.CreateOrderResponse;
import com.klm.order_management.order.application.OrderService;
import com.klm.order_management.order.domain.aggregate.Order;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/orders")
@Tag(name = "User API", description = "Operations related to users")
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping()
    @ApiResponses({@ApiResponse(responseCode = "201", description = "Order created successfully"), @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @Tag(name = "Create Order", description = "Create a new order")
    public ResponseEntity<CreateOrderResponse> createOrder(@RequestBody CreateOrderRequest order) {
        var createOrderResponse = orderService.add(order);
        return new ResponseEntity<>(createOrderResponse, HttpStatus.CREATED);
    }


    @PutMapping("payments")
    @ApiResponses({@ApiResponse(responseCode = "202", description = "payment created successfully"), @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity updatePayment(@RequestBody UpdatePaymentRequest payment) {

        orderService.updatePayment(payment);
        return new ResponseEntity<>(null, HttpStatus.ACCEPTED);
    }

    @GetMapping("/{orderId}")
    @ApiResponses({@ApiResponse(responseCode = "200", description = "Order retrieved successfully"), @ApiResponse(responseCode = "404", description = "Order not found")
    })
    public ResponseEntity<Order> getOrder(@PathVariable("orderId") UUID orderId) {
        var order = orderService.findById(orderId);
        if (order == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return new ResponseEntity<>(order, HttpStatus.OK);
    }

    @GetMapping("/health")
    public ResponseEntity<String> healthCheck() {
        return ResponseEntity.ok("Order Service is healthy");
    }

    @GetMapping("ping")
    public ResponseEntity<String> ping() {
        return ResponseEntity.ok("pong");
    }

}

