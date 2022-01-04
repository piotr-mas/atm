package com.piotr.atm.model.response;

import java.util.List;

public class ErrorResponse {

    private String status = "ERROR";
    private List<String> errorMessages;

    public void setStatus(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }

    public List<String> getErrorMessages() {
        return errorMessages;
    }

    public void setErrorMessages(List<String> errorMessages) {
        this.errorMessages = errorMessages;
    }
}
