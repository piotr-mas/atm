package com.piotr.atm.model.payload;

import java.util.Objects;


public class AtmWithdrawnPayload extends AtmUserPayload {


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
        AtmWithdrawnPayload that = (AtmWithdrawnPayload) o;
        return requestedCash == that.requestedCash;
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), requestedCash);
    }
}
