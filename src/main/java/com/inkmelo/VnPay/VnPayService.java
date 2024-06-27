package com.inkmelo.VnPay;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import jakarta.servlet.http.HttpServletRequest;

@Service
public class VnPayService {

    @Autowired
    private VnPayConfig vnPayconfig;

    public String createPayment(HttpServletRequest req) throws Exception {
        String vnp_Version = "2.1.0";
        String vnp_Command = "pay";
        String orderType = "other";
        
        String amountStr = req.getParameter("amount");
        if (amountStr == null || amountStr.isEmpty()) {
            amountStr = "10000";
        }
        
        long amount;
        try {
            amount = Long.parseLong(amountStr) * 100;
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Invalid amount value");
        }
        
        String bankCode = req.getParameter("bankCode");

        String vnp_TxnRef = VnPayConfig.getRandomNumber(8);
        String vnp_IpAddr = VnPayConfig.getIpAddress(req);

        String vnp_TmnCode = vnPayconfig.getTmnCode();

        Map<String, String> vnp_Params = new HashMap<>();
        vnp_Params.put("vnp_Version", vnp_Version);
        vnp_Params.put("vnp_Command", vnp_Command);
        vnp_Params.put("vnp_TmnCode", vnp_TmnCode);
        vnp_Params.put("vnp_Amount", String.valueOf(amount));
        vnp_Params.put("vnp_CurrCode", "VND");

        if (bankCode != null && !bankCode.isEmpty()) {
            vnp_Params.put("vnp_BankCode", bankCode);
        }
        vnp_Params.put("vnp_TxnRef", vnp_TxnRef);
        vnp_Params.put("vnp_OrderInfo", "Thanh toan don hang:" + vnp_TxnRef);
        vnp_Params.put("vnp_OrderType", orderType);

        String locate = req.getParameter("language");
        if (locate != null && !locate.isEmpty()) {
            vnp_Params.put("vnp_Locale", locate);
        } else {
            vnp_Params.put("vnp_Locale", "vn");
        }
        vnp_Params.put("vnp_ReturnUrl", vnPayconfig.getReturnUrl());
        vnp_Params.put("vnp_IpAddr", vnp_IpAddr);

        Calendar cld = Calendar.getInstance(TimeZone.getTimeZone("Etc/GMT+7"));
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
        String vnp_CreateDate = formatter.format(cld.getTime());
        vnp_Params.put("vnp_CreateDate", vnp_CreateDate);

        cld.add(Calendar.MINUTE, 15);
        String vnp_ExpireDate = formatter.format(cld.getTime());
        vnp_Params.put("vnp_ExpireDate", vnp_ExpireDate);

        List<String> fieldNames = new ArrayList<>(vnp_Params.keySet());
        Collections.sort(fieldNames);
        StringBuilder hashData = new StringBuilder();
        StringBuilder query = new StringBuilder();
        for (String fieldName : fieldNames) {
            String fieldValue = vnp_Params.get(fieldName);
            if (fieldValue != null && !fieldValue.isEmpty()) {
                hashData.append(fieldName).append('=').append(URLEncoder.encode(fieldValue, StandardCharsets.US_ASCII.toString()));
                query.append(URLEncoder.encode(fieldName, StandardCharsets.US_ASCII.toString())).append('=').append(URLEncoder.encode(fieldValue, StandardCharsets.US_ASCII.toString()));
                if (!fieldName.equals(fieldNames.get(fieldNames.size() - 1))) {
                    query.append('&');
                    hashData.append('&');
                }
            }
        }
        String queryUrl = query.toString();
        String vnp_SecureHash = VnPayConfig.hmacSHA512(vnPayconfig.getSecretKey(), hashData.toString());
        queryUrl += "&vnp_SecureHash=" + vnp_SecureHash;
        String paymentUrl = vnPayconfig.getPayUrl() + "?" + queryUrl;

        JsonObject job = new JsonObject();
        job.addProperty("code", "00");
        job.addProperty("message", "success");
        job.addProperty("data", paymentUrl);
        Gson gson = new Gson();
        return gson.toJson(job);
    }
}