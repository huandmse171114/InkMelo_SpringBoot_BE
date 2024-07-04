package com.inkmelo.VnPay;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class VnPayController {

    private final VnPayService vnPayService;
    private final QueryService queryService;

    @PostMapping("/api/vnpay/create-payment")
    public String createPayment(HttpServletRequest req,
    		@RequestBody String returnURL) throws Exception {
    	
        return vnPayService.createPayment(req, returnURL);
    }

    @GetMapping("/api/vnpay/query-transaction")
    public String queryTransaction(HttpServletRequest req) throws Exception {
        return queryService.queryTransaction(req);
    }
    

}