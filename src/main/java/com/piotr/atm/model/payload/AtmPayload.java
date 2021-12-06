package com.piotr.atm.model.payload;

import java.util.Objects;

public class AtmPayload {

    private int pin;

    public int getPin() {
        return pin;
    }

    public void setPin(int pin) {
        this.pin = pin;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AtmPayload that = (AtmPayload) o;
        return pin == that.pin;
    }

    @Override
    public int hashCode() {
        return Objects.hash(pin);
    }
}
