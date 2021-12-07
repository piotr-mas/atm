package com.piotr.atm.service;

import com.piotr.atm.model.entity.User;
import com.piotr.atm.repositories.UserJpaRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class UserServiceTest {

    @MockBean
    private UserJpaRepository userJpaRepository;

    @Autowired
    private UserService userService;
    private Long accountNumber = 123456789L;
    private String pin = "1223";
    private Long incorrectAccountNumber = 987654321L;
    private String incorrectPin = "4321";

    @BeforeEach
    void setUp() {
        User user = new User();
        user.setAccountNumber(accountNumber);
        user.setPin(pin);
        user.setBalance(100L);
        user.setOverdraft(200L);
        //Set up our mock repository
        Mockito.when(userJpaRepository.existsUserByAccountNumberAndPin(user.getAccountNumber(), user.getPin()))
                .thenReturn(true);
        Mockito.when(userJpaRepository.findUserByAccountNumberAndPin(user.getAccountNumber(), user.getPin()))
                .thenReturn(user);
        Mockito.when(userJpaRepository.getById(user.getAccountNumber()))
                .thenReturn(user);

    }

    @Test
    @DisplayName("Test isUserValid Success")
    void isUserValidSuccess() {
        boolean test = userJpaRepository.existsUserByAccountNumberAndPin(accountNumber, pin);
        //Assert the response
        assertTrue(test);
    }

    @Test
    @DisplayName("Test isUserValid Error")
    void isUserValidError() {
        boolean test = userJpaRepository.existsUserByAccountNumberAndPin(incorrectAccountNumber, incorrectPin);
        //Assert the response
        assertFalse(test);
    }

    @Test
    @DisplayName("Test getUserAccount Success")
    void getUserAccountSuccess() {
        User user = userJpaRepository.findUserByAccountNumberAndPin(accountNumber, pin);
        assertThat(user.getBalance()).isEqualTo(100);
        assertThat(user.getOverdraft()).isEqualTo(200);
    }

    @Test
    @DisplayName("Test getUserAccount Error")
    void getUserAccountError() {
        User user = userJpaRepository.findUserByAccountNumberAndPin(accountNumber, pin);
        assertThat(user.getBalance()).isNotEqualTo(200);
        assertThat(user.getOverdraft()).isNotEqualTo(100);
    }

    @Test
    @DisplayName("Test testGetUserAccount Success")
    void testGetUserAccountSuccess() {
        User user = userJpaRepository.getById(accountNumber);
        assertThat(user.getBalance()).isNotEqualTo(200);
        assertThat(user.getOverdraft()).isNotEqualTo(100);
    }

    @Test
    @DisplayName("Test testGetUserAccount Error")
    void testGetUserAccountError() {
        User user = userJpaRepository.getById(accountNumber);
        assertThat(user.getBalance()).isNotEqualTo(200);
        assertThat(user.getOverdraft()).isNotEqualTo(100);
    }

    @Test
    @DisplayName("Test updateUser Success")
    void updateUserSuccess() {
        User user = new User();
        user.setAccountNumber(accountNumber);
        user.setPin(pin);
        user.setBalance(100L);
        user.setOverdraft(200L);

        userService.updateUser(user);
        assertThat(user.getBalance()).isEqualTo(100);
    }

    @Test
    @DisplayName("Test updateUser Error")
    void updateUserError() {
        User user = new User();
        userService.updateUser(user);
        assertNull(user.getBalance());
    }
}