package com.piotr.atm.controller;

import com.piotr.atm.model.response.SuccessResponse;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;



@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class AtmControllerTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    void displayBalanceGetNotSupported() {
        SuccessResponse response = restTemplate
                .getForObject("/atm/balance", SuccessResponse.class);

        assertNotNull(response);
        assertEquals("Request method 'GET' not supported", response.getMessage());
    }

    @Test
    void displayBalanceSuccess() {
        SuccessResponse successResponse = new SuccessResponse();

        SuccessResponse response = restTemplate
                .postForObject("/atm/balance", successResponse, SuccessResponse.class);

        assertNotNull(response);
    }

    @Test
    void withdrawnSuccess() {
        SuccessResponse successResponse = new SuccessResponse();
        successResponse.setMessage("Success");
        SuccessResponse response = restTemplate
                .postForObject("/atm/withdrawn/", successResponse, SuccessResponse.class, 12L);

        assertNotNull(response);
    }

    @Test
    void displayStatisticsSuccess() {
        SuccessResponse successResponse = new SuccessResponse();
        successResponse.setMessage("Success");
        SuccessResponse response = restTemplate
                .postForObject("/atm/statistics/", successResponse, SuccessResponse.class, 12L);

        assertNotNull(response);
    }


}