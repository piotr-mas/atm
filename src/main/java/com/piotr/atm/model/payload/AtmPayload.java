package com.piotr.atm.model.payload;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.util.Objects;

@NotNull(message = "YOU HAVE TO PROVIDE YOUR ACCOUNT NUMBER AND PIN")
public class AtmPayload {

    @NotNull(message = "YOU HAVE TO PROVIDE YOUR PIN")
    @Pattern(regexp = "\\d{4}", message = "The pin must be a four digits")
    private String pin;

    public String getPin() {
        return pin;
    }

    public void setPin(String pin) {
        this.pin = pin;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        com.piotr.atm.model.payload.AtmPayload that = (com.piotr.atm.model.payload.AtmPayload) o;
        return pin == that.pin;
    }

    @Override
    public int hashCode() {
        return Objects.hash(pin);
    }
}
