package com.piotr.atm.repositories;

import com.piotr.atm.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserJpaRepository extends JpaRepository<User, Long> {

    boolean existsUserByAccountNumberAndPin(Long accountNumber, String pin);

    User findUserByAccountNumberAndPin(Long accountNumber, String pin);

}
