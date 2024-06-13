package com.inkmelo.ghn;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/ghn")
public class GHNController {

    @Autowired
    private GHNService ghnService;

    @PostMapping("/create-order")
    public Mono<CreateOrderResponse> createOrder(@RequestBody CreateOrderRequest request) {
        return ghnService.createOrder(request);
    }

    @GetMapping("/track-order")
    public Mono<String> trackOrder(@RequestParam String orderCode) {
        return ghnService.trackOrder(orderCode);
    }
}
