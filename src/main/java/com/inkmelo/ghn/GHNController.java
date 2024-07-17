package com.inkmelo.ghn;

import java.util.Date;
import java.util.LinkedList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;

import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@Tag(name = "GHN", description = "GHN Management APIs")
public class GHNController {

    @Autowired
    private GHNApis ghnApis;

    @GetMapping("/store/api/v1/ghn/delivery-time")
    public Date calculateExpectedDeliveryTime(@RequestParam int toDistrictId, @RequestParam String toWardCode, @RequestParam Integer serviceId) {
        return ghnApis.calculateExpectedDeliveryTime(toDistrictId, toWardCode, serviceId);
    }

    @PostMapping("/create-order")
    public String createOrder(@RequestParam int toDistrictId,
                              @RequestParam String toWardCode,
                              @RequestParam int quantity,
                              @RequestParam String toName,
                              @RequestParam String toPhone,
                              @RequestParam String toAddress,
                              @RequestParam Integer serviceId) {
        return ghnApis.createOrder(toDistrictId, toWardCode, quantity, toName, toPhone, toAddress, serviceId, new LinkedList<>(), new LinkedList<>(), new LinkedList<>());
    }

    @GetMapping("/store/api/v1/ghn/wards")
    public String getWardList(@RequestParam String districtId) {
        return ghnApis.getWardList(districtId);
    }

    @GetMapping("/store/api/v1/ghn/districts")
    public String getDistrictList(@RequestParam String provinceId) {
        return ghnApis.getDistrictList(provinceId);
    }

    @GetMapping("/store/api/v1/ghn/provinces")
    public String getProvinceList() {
        return ghnApis.getProvinceList();
    }

    @GetMapping("/store/api/v1/ghn/calculate-fee")
    public String calculateFee(@RequestParam int toDistrictId, 
                               @RequestParam String toWardCode,
                               @RequestParam int quantity,
                               @RequestParam Integer serviceId) {
        return ghnApis.calculateFee(toDistrictId, toWardCode, quantity, serviceId);
    }
    
    @GetMapping("/store/api/v1/ghn/track-order/{orderCode}")
    public String trackOrder(@PathVariable String orderCode) {
        try {
            return ghnApis.trackOrder(orderCode);
        } catch (Exception e) {
            e.printStackTrace();
            return "Failed to track order: " + e.getMessage();
        }
    }
    
    @GetMapping("/store/api/v1/ghn/get-service/{to_district}")
    public GHNServiceResponse getAvailableServices(@PathVariable Integer to_district) throws JsonMappingException, JsonProcessingException {
        return ghnApis.getService(to_district);
    }
}