package com.inkmelo.VnPay;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/api/vnpay")
public class VnPayController {

    @Autowired
    private VnPayService vnPayService;

    @Autowired
    private QueryService queryService;

    @PostMapping("/create-payment")
    public String createPayment(HttpServletRequest req) throws Exception {
        return vnPayService.createPayment(req);
    }

    @GetMapping("/query-transaction")
    public String queryTransaction(HttpServletRequest req) throws Exception {
        return queryService.queryTransaction(req);
    }
}