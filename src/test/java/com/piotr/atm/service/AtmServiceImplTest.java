package com.piotr.atm.service;

import com.piotr.atm.model.entity.User;
import com.piotr.atm.model.response.SuccessResponse;
import com.piotr.atm.repositories.UserJpaRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static com.piotr.atm.service.AtmServiceImpl.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AtmServiceImplTest {

    @InjectMocks
    AtmServiceImpl service;

    @Mock
    private UserService userService;
    
    @Mock
    private CashMachineService cashMachineService;

    @Mock
    UserJpaRepository userJpaRepository;

    @Test
    void getBalanceWrongCredentials() {
        when(userService.isUserValid(1234L, "1234"))
                .thenReturn(false);

        SuccessResponse newResponse = service.getBalance(1234L, "1234");

        assertThat(newResponse).isNotNull();
        assertEquals(INCORRECT_ACCOUNT_DETAILS, newResponse.getMessage() );
    }

    @Test
    void getBalanceCorrectCredentials() {
        User user = new User();
        user.setBalance(100L);
        when(userService.isUserValid(1234L, "1234"))
                .thenReturn(true);
        when(userService.getUserAccount(1234L, "1234"))
                .thenReturn(user);

        SuccessResponse newResponse = service.getBalance(1234L, "1234");

        assertThat(newResponse).isNotNull();
        assertEquals(100L, newResponse.getCash().getBalanceLeft());
        assertEquals(SUCCESS, newResponse.getMessage());
    }

    @Test
    void withdrawnCashWrongCredentials() {
        when(userService.isUserValid(1234L, "1234"))
                .thenReturn(false);

        SuccessResponse newResponse = service
                .withdrawnCash(1234L, "1234", 1000, 1212L);

        assertThat(newResponse).isNotNull();
        assertEquals( INCORRECT_ACCOUNT_DETAILS, newResponse.getMessage());
    }

    @Test
    void withdrawnCashInsufficient() {
        User user = new User();
        user.setBalance(100L);
        user.setOverdraft(200L);
        when(userService.isUserValid(1234L, "1234"))
                .thenReturn(true);
        when(userService.getUserAccount(1234L, "1234"))
                .thenReturn(user);

        SuccessResponse newResponse = service
                .withdrawnCash(1234L, "1234", 1000, 1212L);

        assertThat(newResponse).isNotNull();
        assertEquals( 100L, newResponse.getCash().getBalanceLeft());
        assertEquals(INSUFFICIENT,newResponse.getMessage());
    }

    @Test
    void displayStatistics() {
        when(cashMachineService.checkCashMachineCredentials(1234L, "1234"))
                .thenReturn(false);

        SuccessResponse newResponse = service.displayStatistics(1234L, "1234");

        assertThat(newResponse).isNotNull();
        assertEquals(INCORRECT_ATM_DETAILS, newResponse.getMessage());
    }


    @Test
    void verifyIsUserValid() {
        service.getBalance(123456789L, "1234");

        verify(userService).isUserValid(123456789L, "1234");
    }

    @Test
    void verifyWithdrawnCash() {
        service.withdrawnCash(123456789L, "1234", 100, 111L);
        verify(userService).isUserValid(123456789L, "1234");
    }


}