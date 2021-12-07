package com.piotr.atm.service;

import com.piotr.atm.model.entity.User;
import com.piotr.atm.repositories.UserJpaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * User service with repository access
 */
@Component
public class UserService {

    @Autowired
    private UserJpaRepository userJpaRepository;

    /**
     * @param accountNumber
     * @param pin
     * @return
     */
    public boolean isUserValid(Long accountNumber, String pin) {
        return userJpaRepository.existsUserByAccountNumberAndPin(accountNumber, pin);
    }

    /**
     * @param accountNumber
     * @param pin
     * @return
     */
    public User getUserAccount(Long accountNumber, String pin) {
        return userJpaRepository.findUserByAccountNumberAndPin(accountNumber, pin);
    }

    /**
     * @param accountNumber
     * @return
     */
    public User getUserAccount(Long accountNumber) {
        return userJpaRepository.getById(accountNumber);
    }

    /**
     * @param dbUser
     * @return
     */
    public boolean updateUser(User dbUser) {
        userJpaRepository.save(dbUser);
        return true;
    }
}
