package com.klm.order_management.order.api;

import com.klm.order_management.order.api.requests.CreateOrderRequest;
import com.klm.order_management.order.api.requests.UpdatePaymentRequest;
import com.klm.order_management.order.api.response.CreateOrderResponse;
import com.klm.order_management.order.application.OrderService;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

}

