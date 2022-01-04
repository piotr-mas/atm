package com.piotr.atm.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.piotr.atm.model.entity.CashNotes;

import javax.persistence.Transient;
import java.io.Serializable;
import java.util.Objects;

public class Cash implements Serializable {

    private static final long serialVersionUID = 325959476419162278L;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Long balanceLeft;

    @Transient
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private CashNotes cashNotes;

    public Cash() {
    }
    public Cash(Long balance) {
        balanceLeft = balance;
    }

    public Cash(CashNotes cashNotes) {
        this.cashNotes = cashNotes;
    }

    public Long getBalanceLeft() {
        return balanceLeft;
    }

    public void setBalanceLeft(Long balanceLeft) {
        this.balanceLeft = balanceLeft;
    }

    public CashNotes getCashNotes() {
        return cashNotes;
    }

    public void setCashNotes(CashNotes cashNotes) {
        this.cashNotes = cashNotes;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Cash cash = (Cash) o;
        return balanceLeft == cash.balanceLeft && Objects.equals(cashNotes, cash.cashNotes);
    }

    @Override
    public int hashCode() {
        return Objects.hash(balanceLeft, cashNotes);
    }
}
