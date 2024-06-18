package com.inkmelo.ghn;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.sql.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

@Service
public class GHNApis {

    @Value("${GHN.token}")
    private String token;

    @Value("${GHN.shop_id}")
    private String shopId;
    
    @Value("${GHN.url}")
    private String ghnUrl;
    
    @Value("${GHN.districtId}")
    private String districtId;

    // This function will return the amount of time needed for delivering in DAY(S)
    public Date calculateExpectedDeliveryTime(String to_district_id, String to_ward_code) {
        String output = "";
        try {
            URL url = new URL(ghnUrl + "/public-api/v2/shipping-order/leadtime");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");  // Changed to POST
            conn.setRequestProperty("Content-Type", "application/json; utf-8");
            conn.setRequestProperty("Accept", "application/json");
            conn.setRequestProperty("ShopId", shopId);
            conn.setRequestProperty("token", token);
            conn.setDoOutput(true);

            String requestBody = "{\"from_district_id\":" + districtId + "," +
                    "\"to_district_id\": " + to_district_id + "," +
                    "\"to_ward_code\": \"" + to_ward_code + "\"," +
                    "\"service_id\": 53320}";
            try (OutputStream os = conn.getOutputStream()) {
                os.write(requestBody.getBytes(StandardCharsets.UTF_8));
                os.flush();
            }

            if (conn.getResponseCode() != HttpURLConnection.HTTP_OK) {
                throw new RuntimeException("Failed : HTTP error code : "
                        + conn.getResponseCode());
            }

            try (BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream(), StandardCharsets.UTF_8))) {
                String line;
                while ((line = br.readLine()) != null) {
                    output += line;
                }
            }
            conn.disconnect();
        } catch (IOException | RuntimeException e) {
            e.printStackTrace();
        }

        JsonParser parser = new JsonParser();
        JsonObject outputObject = parser.parse(output).getAsJsonObject();
        JsonObject objData = outputObject.getAsJsonObject("data");
        String leadtime = objData.get("leadtime").getAsString();
        long expectedTimeInSeconds = Long.parseLong(leadtime);
        return new Date(expectedTimeInSeconds * 1000);
    }

    public String createOrder(String to_district_id,
                              String to_ward_code,
                              int quantity,
                              String to_name,
                              String to_phone,
                              String to_address) {
        String output = "";
        try {
            URL url = new URL(ghnUrl + "/public-api/v2/shipping-order/create");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");  // Changed to POST
            conn.setRequestProperty("Content-Type", "application/json; utf-8");
            conn.setRequestProperty("Accept", "application/json");
            conn.setRequestProperty("ShopId", shopId);
            conn.setRequestProperty("token", token);
            conn.setDoOutput(true);

            int avgHeight = 4 * quantity;
            int avgWidth = 15;
            int avgLength = 22;
            int avgWeight = 50 * quantity;
            String requestBody = "{\n" +
                    "    \"payment_type_id\": 2,\n" +
                    "    \"required_note\": \"CHOTHUHANG\",\n" +
                    "    \"to_name\": \"" + to_name + "\",\n" +
                    "    \"to_phone\": \"" + to_phone + "\",\n" +
                    "    \"to_address\": \"" + to_address + "\",\n" +
                    "    \"to_ward_code\": \"" + to_ward_code + "\",\n" +
                    "    \"to_district_id\": " + to_district_id + ",\n" +
                    "    \"weight\": " + avgWeight + ",\n" +
                    "    \"length\": " + avgLength + ",\n" +
                    "    \"width\": " + avgWidth + ",\n" +
                    "    \"height\": " + avgHeight + ",\n" +
                    "    \"pick_station_id\": 1444,\n" +
                    "    \"service_id\": null,\n" +
                    "    \"service_type_id\": 2,\n" +
                    "    \"items\": [\n" +
                    "         {\n" +
                    "             \"name\":\"Sách\",\n" +
                    "             \"quantity\": " + quantity + "\n" +
                    "         }\n" +
                    "     ]\n" +
                    "}";
            try (OutputStream os = conn.getOutputStream()) {
                os.write(requestBody.getBytes(StandardCharsets.UTF_8));
                os.flush();
            }

            if (conn.getResponseCode() != HttpURLConnection.HTTP_OK) {
                throw new RuntimeException("Failed : HTTP error code : "
                        + conn.getResponseCode());
            }

            try (BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream(), StandardCharsets.UTF_8))) {
                String line;
                while ((line = br.readLine()) != null) {
                    output += line;
                }
            }
            conn.disconnect();
        } catch (IOException | RuntimeException e) {
            e.printStackTrace();
        }
        return output;
    }

    public String getWardList(String district_id) {
        String output = "";
        try {
            URL url = new URL(ghnUrl + "/public-api/master-data/ward?district_id=" + district_id);  // Added district_id as query param
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Content-Type", "application/json; utf-8");
            conn.setRequestProperty("Accept", "application/json");
            conn.setRequestProperty("token", token);

            if (conn.getResponseCode() != HttpURLConnection.HTTP_OK) {
                throw new RuntimeException("Failed : HTTP error code : "
                        + conn.getResponseCode());
            }

            try (BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream(), StandardCharsets.UTF_8))) {
                String line;
                while ((line = br.readLine()) != null) {
                    output += line;
                }
            }
            conn.disconnect();
        } catch (IOException | RuntimeException e) {
            e.printStackTrace();
        }
        return output;
    }

    public String getDistrictList(String province_id) {
        String output = "";
        try {
            URL url = new URL("https://dev-online-gateway.ghn.vn/shiip/public-api/master-data/district");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/json; utf-8");
            conn.setRequestProperty("Accept", "application/json");
            conn.setRequestProperty("token", token);
            conn.setDoOutput(true);

            String requestBody = "{\"province_id\":" + province_id + "}";
            try (OutputStream os = conn.getOutputStream()) {
                os.write(requestBody.getBytes(StandardCharsets.UTF_8));
                os.flush();
            }

            if (conn.getResponseCode() != HttpURLConnection.HTTP_OK) {
                throw new RuntimeException("Failed : HTTP error code : "
                        + conn.getResponseCode());
            }

            try (BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream(), StandardCharsets.UTF_8))) {
                String line;
                while ((line = br.readLine()) != null) {
                    output += line;
                }
            }
            conn.disconnect();
        } catch (IOException | RuntimeException e) {
            e.printStackTrace();
        }
        return output;
    }


    // This function will return the list of provinces
    public String getProvinceList() {
        String output = "";
        try {
            URL url = new URL(ghnUrl + "/public-api/master-data/province");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Content-Type", "application/json; utf-8");
            conn.setRequestProperty("Accept", "application/json");
            conn.setRequestProperty("token", token);

            if (conn.getResponseCode() != HttpURLConnection.HTTP_OK) {
                throw new RuntimeException("Failed : HTTP error code : "
                        + conn.getResponseCode());
            }

            try (BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream(), StandardCharsets.UTF_8))) {
                String line;
                while ((line = br.readLine()) != null) {
                    output += line;
                }
            }
            conn.disconnect();
        } catch (IOException | RuntimeException e) {
            e.printStackTrace();
        }
        return output;
    }

    // This function will return the fee for shipping
    public String calculateFee(String to_district_id, String to_ward_code, int quantity) {
        String output = "";
        try {
            URL url = new URL(ghnUrl + "/public-api/v2/shipping-order/fee");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");  // Changed to POST
            conn.setRequestProperty("Content-Type", "application/json; utf-8");
            conn.setRequestProperty("Accept", "application/json");
            conn.setRequestProperty("token", token);
            conn.setRequestProperty("ShopId", shopId);
            conn.setDoOutput(true);

            int avgHeight = 4 * quantity;
            int avgWidth = 15;
            int avgLength = 22;
            int avgWeight = 50 * quantity;

            String requestBody = "{\"service_id\":null,\"service_type_id\": 2,\"insurance_value\":10000,"
                    + "\"coupon\":null,\"from_district_id\":"+ districtId + ","
                    + "\"to_district_id\":" + to_district_id + ",\"to_ward_code\":\"" + to_ward_code + "\","
                    + "\"height\":" + avgHeight + ",\"length\":" + avgLength + ",\"weight\":" + avgWeight + ",\"width\":" + avgWidth + "}";
            try (OutputStream os = conn.getOutputStream()) {
                os.write(requestBody.getBytes(StandardCharsets.UTF_8));
                os.flush();
            }

            if (conn.getResponseCode() != HttpURLConnection.HTTP_OK) {
                throw new RuntimeException("Failed : HTTP error code : "
                        + conn.getResponseCode());
            }

            try (BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream(), StandardCharsets.UTF_8))) {
                String line;
                while ((line = br.readLine()) != null) {
                    output += line;
                }
            }
            conn.disconnect();
        } catch (IOException | RuntimeException e) {
            e.printStackTrace();
        }
        return output;
    }
    
    public static String trackOrder(String orderCode) {
        String output = "";
        try {
            URL url = new URL("https://dev-online-gateway.ghn.vn/shiip/public-api/v2/shipping-order/tracking");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/json; utf-8");
            conn.setRequestProperty("Accept", "application/json");
            conn.setDoOutput(true);

            String requestBody = String.format("{\"order_code\": \"%s\"}", orderCode);

            try (OutputStream os = conn.getOutputStream()) {
                os.write(requestBody.getBytes());
                os.flush();
            }

            if (conn.getResponseCode() != HttpURLConnection.HTTP_OK) {
                throw new RuntimeException("Failed : HTTP error code : " + conn.getResponseCode());
            }

            try (BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"))) {
                String line;
                while ((line = br.readLine()) != null) {
                    output += line;
                }
            }
            conn.disconnect();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return output;
    }
}