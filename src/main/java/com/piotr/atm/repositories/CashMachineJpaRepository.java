package com.piotr.atm.repositories;

import com.piotr.atm.model.entity.CashNotes;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CashMachineJpaRepository extends JpaRepository<CashNotes, Long> {

    boolean existsByMachineIdAndPin(Long machineId, String pin);

    CashNotes findCashNotesByMachineId(Long machineId);

    @Override
    <S extends CashNotes> S save(S entity);
}
