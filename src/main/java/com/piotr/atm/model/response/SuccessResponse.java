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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SuccessResponse response = (SuccessResponse) o;
        return message.equals(response.message) && cash.equals(response.cash);
    }

    @Override
    public int hashCode() {
        return Objects.hash(message, cash);
    }
}
