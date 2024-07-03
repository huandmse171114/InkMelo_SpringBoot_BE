package com.inkmelo.ghn;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.sql.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;

@Service
public class GHNApis {

    @Value("${GHN.token}")
    private String token;

    @Value("${GHN.shop_id}")
    private int shopId;
    
    @Value("${GHN.url}")
    private String ghnUrl;
    
    @Value("${GHN.districtId}")
    private int districtId;

    @Value("${GHN.wardCode}")
    private String wardCode;

    @Value("${inkmelo.address}")
    private String address;
    
    // This function will return the amount of time needed for delivering in DAY(S)
    public Date calculateExpectedDeliveryTime(int to_district_id, String to_ward_code, Integer serviceId) {
        String output = "";
        HttpURLConnection conn = null;
        try {
            URL url = new URL(ghnUrl + "/public-api/v2/shipping-order/leadtime");
            conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Content-Type", "application/json; utf-8");
            conn.setRequestProperty("Accept", "application/json");
            conn.setRequestProperty("token", token);
            conn.setDoOutput(true);

            // Ensure the IDs are correctly formatted
            String requestBody = "{\"from_district_id\": " + districtId + "," +
                                 "\"from_ward_code\": \"" + wardCode + "\"," +
                                 "\"to_district_id\": " + to_district_id + "," +
                                 "\"to_ward_code\": \"" + to_ward_code + "\"," +
                                 "\"service_id\": " + serviceId + "}";

            // Log the request body
            System.out.println("Request Body: " + requestBody);

            try (OutputStream os = conn.getOutputStream()) {
                os.write(requestBody.getBytes(StandardCharsets.UTF_8));
                os.flush();
            }

            int responseCode = conn.getResponseCode();
            if (responseCode != HttpURLConnection.HTTP_OK) {
                System.err.println("Failed : HTTP error code : " + responseCode);
                System.err.println("Response Message: " + conn.getResponseMessage());
                throw new RuntimeException("Failed : HTTP error code : " + responseCode);
            }

            try (BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream(), StandardCharsets.UTF_8))) {
                StringBuilder sb = new StringBuilder();
                String line;
                while ((line = br.readLine()) != null) {
                    sb.append(line);
                }
                output = sb.toString();
            }
        } catch (ProtocolException e) {
            e.printStackTrace();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (RuntimeException e) {
            e.printStackTrace();
        } finally {
            if (conn != null) {
                conn.disconnect();
            }
        }

        // Log the output
        System.out.println("Response Body: " + output);

        if (output == null || output.isEmpty()) {
            System.err.println("Error: The response body is null or empty.");
            return null;
        }

        try {
            JsonObject outputObject = JsonParser.parseString(output).getAsJsonObject();
            if (outputObject.has("data")) {
                JsonObject objData = outputObject.getAsJsonObject("data");
                if (objData.has("leadtime")) {
                    String leadtime = objData.get("leadtime").getAsString();
                    long expectedTimeInSeconds = Long.parseLong(leadtime);
                    return new Date(expectedTimeInSeconds * 1000);
                } else {
                    System.err.println("Error: 'leadtime' not found in 'data' object.");
                }
            } else {
                System.err.println("Error: 'data' object not found in response.");
            }
        } catch (JsonSyntaxException | NumberFormatException | NullPointerException e) {
            e.printStackTrace();
        }

        return null;
    }
    
    public String createOrder(int to_district_id,
                              String to_ward_code,
                              int quantity,
                              String to_name,
                              String to_phone,
                              String to_address,
                              Integer serviceId) {
        String output = "";
        try {
            URL url = new URL(ghnUrl + "/public-api/v2/shipping-order/create");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");  // Changed to POST
            conn.setRequestProperty("Content-Type", "application/json; utf-8");
            conn.setRequestProperty("Accept", "application/json");
            conn.setRequestProperty("token", token);
            conn.setDoOutput(true);

            int avgHeight = 4 * quantity;
            int avgWidth = 15;
            int avgLength = 22;
            int avgWeight = 50 * quantity;
            String requestBody = "{\n" +
                    "    \"payment_type_id\": 2,\n" +
                    "    \"note\": null,\n" +
                    "    \"required_note\": \"CHOTHUHANG\",\n" +
                    "    \"from_name\": \"InkMelo\",\n" +
                    "    \"from_phone\": \"0328202721\",\n" +
                    "    \"from_address\": \"Your From Address Here\",\n" +  // Replace with actual address
                    "    \"from_ward_name\": \"Phường Long Thạnh Mỹ\",\n" +
                    "    \"from_district_name\": \"Thành Phố Thủ Đức\",\n" +
                    "    \"from_province_name\": \"HCM\",\n" +
                    "    \"return_phone\": \"0328202721\",\n" +
                    "    \"return_address\": \"Your Return Address Here\",\n" +  // Replace with actual address
                    "    \"return_district_id\": null,\n" +
                    "    \"return_ward_code\": \"\",\n" +
                    "    \"client_order_code\": \"\",\n" +
                    "    \"to_name\": \"" + to_name + "\",\n" +
                    "    \"to_phone\": \"" + to_phone + "\",\n" +
                    "    \"to_address\": \"" + to_address + "\",\n" +
                    "    \"to_ward_code\": \"" + to_ward_code + "\",\n" +
                    "    \"to_district_id\": " + to_district_id + ",\n" +
                    "    \"cod_amount\": 0,\n" +
                    "    \"content\": \"\",\n" +
                    "    \"weight\": " + avgWeight + ",\n" +
                    "    \"length\": " + avgLength + ",\n" +
                    "    \"width\": " + avgWidth + ",\n" +
                    "    \"height\": " + avgHeight + ",\n" +
                    "    \"pick_station_id\": 1444,\n" +
                    "    \"deliver_station_id\": null,\n" +
                    "    \"insurance_value\": 0,\n" +
                    "    \"service_id\": "+ serviceId +",\n" +
                    "    \"coupon\": null,\n" +
                    "    \"pick_shift\": [2],\n" +
                    "    \"items\": [\n" +
                    "         {\n" +
                    "             \"name\":\"Sách\",\n" +
                    "             \"code\": \"Book123\",\n" +
                    "             \"quantity\": " + quantity + ",\n" +  // Added comma
                    "             \"price\": 200000,\n" +
                    "             \"length\": 22,\n" +
                    "             \"width\": 15,\n" +
                    "             \"height\": 4,\n" +
                    "             \"weight\": 50,\n" +
                    "             \"category\": {\n" +
                    "                \"level1\": \"Book\"\n" +
                    "             }\n" +
                    "         }\n" +
                    "    ]\n" +
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
            URL url = new URL(ghnUrl + "/public-api/master-data/ward?district_id");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Content-Type", "application/json; utf-8");
            conn.setRequestProperty("Accept", "application/json");
            conn.setRequestProperty("token", token);
            conn.setDoOutput(true);

            String requestBody = "{\"district_id\":" + district_id + "}";
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

    public String getDistrictList(String province_id) {
        String output = "";
        try {
            URL url = new URL(ghnUrl + "/public-api/master-data/district");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
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
    public String calculateFee(int to_district_id, String to_ward_code, int quantity, int serviceId) {
        String output = "";
        try {
            URL url = new URL(ghnUrl + "/public-api/v2/shipping-order/fee");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/json; utf-8");
            conn.setRequestProperty("Accept", "application/json");
            conn.setRequestProperty("token", token);
            conn.setDoOutput(true);

            // Calculating dimensions and weight based on quantity
            int avgHeight = 4 * quantity;
            int avgWidth = 15;
            int avgLength = 22;
            int avgWeight = 50 * quantity;

            // Constructing the request body JSON
            String requestBody = "{" +
                    "\"service_id\":"+ serviceId +"," + 
                    "\"from_district_id\":" + districtId + "," +
                    "\"to_district_id\":" + to_district_id + "," +
                    "\"to_ward_code\":\"" + to_ward_code + "\"," +
                    "\"height\":" + avgHeight + "," +
                    "\"length\":" + avgLength + "," +
                    "\"weight\":" + avgWeight + "," +
                    "\"width\":" + avgWidth + "," +
                    "\"insurance_value\":500000," +
                    "\"coupon\":null," +
                    "\"items\": [" +
                    "   {" +
                    "       \"name\":\"Sách\"," +
                    "       \"quantity\":" + quantity + "," +
                    "       \"height\": 4," +
                    "       \"weight\": 50," +
                    "       \"length\": 22," +
                    "       \"width\": 15," +
                    "       \"price\": 200000," +
                    "       \"code\": \"Book123\"," +
                    "       \"category\": {" +
                    "           \"level1\": \"Book\"" +
                    "       }" +
                    "   }" +
                    "]" +
                    "}";

            // Log the request body
            System.out.println("Request Body: " + requestBody);

            try (OutputStream os = conn.getOutputStream()) {
                byte[] input = requestBody.getBytes(StandardCharsets.UTF_8);
                os.write(input, 0, input.length);
            }

            // Log the response code and message
            int responseCode = conn.getResponseCode();
            System.out.println("Response Code: " + responseCode);
            if (responseCode != HttpURLConnection.HTTP_OK) {
                String responseMessage = conn.getResponseMessage();
                System.out.println("Response Message: " + responseMessage);

                // Log the response body for more details
                try (BufferedReader br = new BufferedReader(new InputStreamReader(conn.getErrorStream(), StandardCharsets.UTF_8))) {
                    StringBuilder errorResponse = new StringBuilder();
                    String line;
                    while ((line = br.readLine()) != null) {
                        errorResponse.append(line);
                    }
                    System.out.println("Error Response: " + errorResponse.toString());
                }

//                throw new RuntimeException("Failed : HTTP error code : " + responseCode);
            }

            try (BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream(), StandardCharsets.UTF_8))) {
                StringBuilder response = new StringBuilder();
                String line;
                while ((line = br.readLine()) != null) {
                    response.append(line);
                }
                output = response.toString();
            }

            conn.disconnect();
        } catch (IOException | RuntimeException e) {
            e.printStackTrace();
        }
        return output;
    }
    
    public String trackOrder(String orderCode) {
        String output = "";
        try {
        	URL url = new URL(ghnUrl + "/public-api/v2/shipping-order/detail");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");  // Changed to POST
            conn.setRequestProperty("Content-Type", "application/json; utf-8");
            conn.setRequestProperty("Accept", "application/json");
            conn.setRequestProperty("token", token);
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
 // This function will return the list of available services
    public GHNServiceResponse getService(Integer to_district) throws JsonMappingException, JsonProcessingException {
        String output = "";
        try {
            URL url = new URL(ghnUrl + "/public-api/v2/shipping-order/available-services");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/json; utf-8");
            conn.setRequestProperty("Accept", "application/json");
            conn.setRequestProperty("token", token);
            conn.setDoOutput(true);

            // Constructing the request body JSON
            String requestBody = "{" +
                    "\"shop_id\":" + shopId + "," +
                    "\"from_district\":" + districtId + "," +
                    "\"to_district\":" + to_district +
                    "}";

            // Log the request body
            System.out.println("Request Body: " + requestBody);

            try (OutputStream os = conn.getOutputStream()) {
                os.write(requestBody.getBytes(StandardCharsets.UTF_8));
                os.flush();
            }

            int responseCode = conn.getResponseCode();
            System.out.println("Response Code: " + responseCode);

            if (responseCode != HttpURLConnection.HTTP_OK) {
                try (BufferedReader br = new BufferedReader(new InputStreamReader(conn.getErrorStream(), StandardCharsets.UTF_8))) {
                    String line;
                    while ((line = br.readLine()) != null) {
                        output += line;
                    }
                    System.err.println("Error Response: " + output);
                }
                throw new RuntimeException("Failed : HTTP error code : " + responseCode);
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
        
        ObjectMapper objectMapper = new ObjectMapper();
        GHNServiceResponse serviceResponse = objectMapper.readValue(output, GHNServiceResponse.class);
        List<GHNServiceResponseData> services = serviceResponse.getData();
        services = services.stream()
        		.filter(service -> service.getService_type_id() != 5)
        		.toList();
        
        serviceResponse.setData(services);
        
        return serviceResponse;
    }
}