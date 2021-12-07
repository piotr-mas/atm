package com.piotr.atm.model.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.piotr.atm.model.Cash;

import java.util.Objects;

public class SuccessResponse {

    @JsonInclude(value = JsonInclude.Include.NON_NULL)
    private String message;
    @JsonInclude(value = JsonInclude.Include.NON_NULL)
    private Cash cash;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Cash getCash() {
        return cash;
    }

    public void setCash(Cash cash) {
        this.cash = cash;
    }

}
