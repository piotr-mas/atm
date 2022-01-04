package com.piotr.atm.model.entity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Objects;

@Entity
@Table(name = "ATM_USERS")
public class User {
    @NotNull
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "account_number", nullable = false)
    private Long accountNumber;

    @Column(name = "pin", nullable = false)
    private String pin;

    @Column(name = "balance", nullable = false)
    private Long balance;

    @Column(name = "overdraft", nullable = false)
    private Long overdraft;

    public Long getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(Long id) {
        this.accountNumber = id;
    }

    public String getPin() {
        return pin;
    }

    public void setPin(String pin) {
        this.pin = pin;
    }

    public Long getBalance() {
        return balance;
    }

    public void setBalance(Long balance) {
        this.balance = balance;
    }

    public Long getOverdraft() {
        return overdraft;
    }

    public void setOverdraft(Long overdraft) {
        this.overdraft = overdraft;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return accountNumber == user.accountNumber && pin == user.pin && balance == user.balance && overdraft == user.overdraft;
    }

    @Override
    public int hashCode() {
        return Objects.hash(accountNumber, pin, balance, overdraft);
    }
}
