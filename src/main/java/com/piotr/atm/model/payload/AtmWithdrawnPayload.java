package com.piotr.atm.model.payload;

import com.piotr.atm.validator.constraints.ValidateCash;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.Objects;

@NotNull(message = "YOU HAVE TO PROVIDE YOUR ACCOUNT NUMBER AND PIN")
public class AtmWithdrawnPayload extends AtmUserPayload {

    @Min(value = 10, message = "You have to request minimum 10 Euro")
    @Max(value = 1000, message = "You can request maximum 1000 Euro")
    @ValidateCash()
    private int requestedCash;

    public int getRequestedCash() {
        return requestedCash;
    }

    public void setRequestedCash(int requestedCash) {
        this.requestedCash = requestedCash;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        com.piotr.atm.model.payload.AtmWithdrawnPayload that = (com.piotr.atm.model.payload.AtmWithdrawnPayload) o;
        return requestedCash == that.requestedCash;
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), requestedCash);
    }
}
