package com.klm.order_management.order.api;

import com.klm.order_management.order.api.requests.CreateOrderRequest;
import com.klm.order_management.order.api.requests.UpdatePaymentRequest;
import com.klm.order_management.order.api.response.CreateOrderResponse;
import com.klm.order_management.order.api.response.OrderDetailResponse;
import com.klm.order_management.order.application.OrderService;
import com.klm.order_management.order.application.mappers.OrderResponseMapper;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/orders")
@Tag(name = "Order API", description = "Operations related to orders")
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping()
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Order created successfully"), 
            @ApiResponse(responseCode = "400", description = "Invalid request data"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<CreateOrderResponse> createOrder(@Valid @RequestBody CreateOrderRequest order) {
        var createOrderResponse = orderService.add(order);
        return new ResponseEntity<>(createOrderResponse, HttpStatus.CREATED);
    }


    @PutMapping("payments")
    @ApiResponses({
            @ApiResponse(responseCode = "202", description = "Payment updated successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid request data"),
            @ApiResponse(responseCode = "404", description = "Order not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<Void> updatePayment(@Valid @RequestBody UpdatePaymentRequest payment) {
        orderService.updatePayment(payment);
        return ResponseEntity.accepted().build();
    }

    @GetMapping("/{orderId}")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Order retrieved successfully"), 
            @ApiResponse(responseCode = "404", description = "Order not found")
    })
    public ResponseEntity<OrderDetailResponse> getOrder(@PathVariable("orderId") UUID orderId) {
        var order = orderService.findById(orderId);
        var response = OrderResponseMapper.toOrderDetailResponse(order);
        return ResponseEntity.ok(response);
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

