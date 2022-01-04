package com.piotr.atm.service;

import com.piotr.atm.model.entity.CashNotes;
import com.piotr.atm.repositories.CashMachineJpaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Cash Machine Service with Repository access
 */
@Component
public class CashMachineService {

    @Autowired
    private CashMachineJpaRepository cashMachineJpaRepository;

    /**
     * @param atmId
     * @return
     */
    public CashNotes getCashNotes(Long atmId) {
        return cashMachineJpaRepository.findCashNotesByMachineId(atmId);
    }

    /**
     * @param notes
     * @return
     */
    public boolean updateCashMachineNotes(CashNotes notes) {
        cashMachineJpaRepository.save(notes);
        return true;
    }

    /**
     * @param atmId
     * @param pin
     * @return
     */
    public boolean checkCashMachineCredentials(Long atmId, String pin) {
        return cashMachineJpaRepository.existsByMachineIdAndPin(atmId, pin);
    }
}
