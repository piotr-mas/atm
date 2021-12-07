package com.piotr.atm.model.payload;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.Objects;

@NotNull(message = "YOU HAVE TO PROVIDE YOUR ACCOUNT NUMBER AND PIN")
public class AtmUserPayload extends AtmPayload {

    @NotNull(message = "YOU HAVE TO PROVIDE YOUR ACCOUNT NUMBER")
    @Min(value = 1, message = "Your account number must be greater than 0")
    @Max(value = 999999999, message = "Your account number must not be greater than 999999999")
    private Long accountNumber;

    public Long getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(Long accountNumber) {
        this.accountNumber = accountNumber;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        com.piotr.atm.model.payload.AtmUserPayload that = (com.piotr.atm.model.payload.AtmUserPayload) o;
        return Objects.equals(accountNumber, that.accountNumber);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), accountNumber);
    }
}
