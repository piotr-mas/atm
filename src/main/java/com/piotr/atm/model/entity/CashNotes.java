package com.piotr.atm.model.entity;

import com.fasterxml.jackson.annotation.JsonInclude;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@Entity
@Table(name = "CASH_MACHINE_NOTES")
public class CashNotes implements Serializable {

    private static final long serialVersionUID = 6200032909863090491L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "machine_id", nullable = false)
    private Long machineId;

    private int fives;
    private int tens;
    private int twenties;
    private int fifties;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String pin;

    public Long getMachineId() {
        return machineId;
    }

    public void setMachineId(Long machineId) {
        this.machineId = machineId;
    }

    public int getFives() {
        return fives;
    }

    public void setFives(int fives) {
        this.fives = fives;
    }

    public int getTens() {
        return tens;
    }

    public void setTens(int tens) {
        this.tens = tens;
    }

    public int getTwenties() {
        return twenties;
    }

    public void setTwenties(int twenties) {
        this.twenties = twenties;
    }

    public int getFifties() {
        return fifties;
    }

    public void setFifties(int fifties) {
        this.fifties = fifties;
    }

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
        CashNotes cashNotes = (CashNotes) o;
        return fives == cashNotes.fives && tens == cashNotes.tens && twenties == cashNotes.twenties && fifties == cashNotes.fifties && pin == cashNotes.pin && Objects.equals(machineId, cashNotes.machineId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(machineId, fives, tens, twenties, fifties, pin);
    }
}
