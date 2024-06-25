package com.inkmelo.ghn;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
public class GHNService {

    @Autowired
    private WebClient.Builder webClientBuilder;

    @Autowired
    private GHNProperties ghnProperties;

    public Mono<CreateOrderResponse> createOrder(CreateOrderRequest request) {
        String url = ghnProperties.getBaseUrl() + "/v2/shipping-order/create";

        return webClientBuilder.build()
                               .post()
                               .uri(url)
                               .header("Token", ghnProperties.getToken())
                               .contentType(MediaType.APPLICATION_JSON)
                               .bodyValue(request)
                               .retrieve()
                               .bodyToMono(CreateOrderResponse.class);
    }

    public Mono<String> trackOrder(String orderCode) {
        String url = ghnProperties.getBaseUrl() + "/v2/shipping-order/detail";

        return webClientBuilder.build()
                               .get()
                               .uri(uriBuilder -> uriBuilder
                                   .path(url)
                                   .queryParam("order_code", orderCode)
                                   .build())
                               .header("Token", ghnProperties.getToken())
                               .retrieve()
                               .bodyToMono(String.class);
    }
}
