package com.inkmelo.ghn;

public class CreateOrderRequest {
    // Các trường cần thiết cho request tạo đơn hàng GHN
    private String payment_type_id;
    private String note;
    private String required_note;
    private String to_name;
    private String to_phone;
    private String to_address;
    private String to_ward_name;
    private String to_district_name;
    private String to_province_name;
    private int cod_amount;
    private int weight;
    private int length;
    private int width;
    private int height;
    private int service_id;
    private int service_type_id;
    private int insurance_value;
    private String client_order_code;

}
