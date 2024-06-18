package com.inkmelo.ghn;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/ghn")
public class GHNController {

    @Autowired
    private GHNApis ghnApis;

    @GetMapping("/delivery-time")
    public Date calculateExpectedDeliveryTime(@RequestParam String toDistrictId, @RequestParam String toWardCode) {
        return ghnApis.calculateExpectedDeliveryTime(toDistrictId, toWardCode);
    }

    @PostMapping("/create-order")
    public String createOrder(@RequestParam String toDistrictId,
                              @RequestParam String toWardCode,
                              @RequestParam int quantity,
                              @RequestParam String toName,
                              @RequestParam String toPhone,
                              @RequestParam String toAddress) {
        return ghnApis.createOrder(toDistrictId, toWardCode, quantity, toName, toPhone, toAddress);
    }

    @GetMapping("/wards")
    public String getWardList(@RequestParam String districtId) {
        return ghnApis.getWardList(districtId);
    }

    @PostMapping("/districts")
    public String getDistrictList(@RequestParam String provinceId) {
        return ghnApis.getDistrictList(provinceId);
    }

    @GetMapping("/provinces")
    public String getProvinceList() {
        return ghnApis.getProvinceList();
    }

    @PostMapping("/calculate-fee")
    public String calculateFee(@RequestParam String toDistrictId, 
                               @RequestParam String toWardCode, 
                               @RequestParam int quantity) {
        return ghnApis.calculateFee(toDistrictId, toWardCode, quantity);
    }
    
    @GetMapping("/track-order")
    public String trackOrder(@RequestParam String orderCode) {
        return GHNApis.trackOrder(orderCode);
    }
}