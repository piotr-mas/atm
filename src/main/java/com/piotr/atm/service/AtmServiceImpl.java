package com.piotr.atm.service;

import com.piotr.atm.model.Cash;
import com.piotr.atm.model.entity.CashNotes;
import com.piotr.atm.model.entity.User;
import com.piotr.atm.model.response.SuccessResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;


/**
 * ATM Service Implementation
 */
@Service
public class AtmServiceImpl implements AtmService{

    public static final String SUCCESS = "Success";
    public static final String INSUFFICIENT = "Not enough money in your account";
    public static final String INCORRECT_ACCOUNT_DETAILS = "Please provide correct account details";
    public static final String INCORRECT_ATM_DETAILS = "Please provide correct ATM id and pin";
    public static final String NO_CASH_IN_ATM = "Not enough money in ATM";
    public static final String NO_NOTES_FOR_REQUEST = "No enough notes for your request";
    public static final String WITHDRAWN = "Withdrawn";


    @Autowired
    private UserService userService;
    @Autowired
    private CashMachineService cashMachineService;

    /**
     * Get Balance
     * @param accountNumber
     * @param pin
     * @return
     */
    @Override
    public SuccessResponse getBalance(Long accountNumber, String pin) {
        SuccessResponse response = new SuccessResponse();
        //should not expose the customer balance if the pin is incorrect
        if(userService.isUserValid(accountNumber, pin)){
            response.setMessage(SUCCESS);
        }else {
            response.setMessage(INCORRECT_ACCOUNT_DETAILS);
            return response;
        }
        Cash cash = getUserCashBalance(accountNumber, pin);
        response.setCash(cash);
        return response;
    }

    /**
     * @param accountNumber
     * @param pin
     * @param requestedCash
     * @param atmId
     * @return
     */
    @Override
    public SuccessResponse withdrawnCash(Long accountNumber, String pin, int requestedCash, Long atmId) {
        SuccessResponse response = new SuccessResponse();
        //should not expose the customer balance if the pin is incorrect
        if(!userService.isUserValid(accountNumber, pin)){
            response.setMessage(INCORRECT_ACCOUNT_DETAILS);
            return response;
        }
        User userAccount = userService.getUserAccount(accountNumber, pin);
        //cannot dispense more money than it holds
        if(requestedCash <= userAccount.getBalance() + userAccount.getOverdraft()){
            Cash balance = withdrawnCashFromMachine(accountNumber, requestedCash, atmId);
            if(balance !=  null){
                response.setMessage(SUCCESS);
                response.setCash(balance);
                return response;
            }
        }
        response.setMessage(INSUFFICIENT);
        response.setCash(new Cash(userAccount.getBalance()));
        return response;
    }

    /**
     * @param accountNumber
     * @param requestedCash
     * @param atmId
     * @return
     */
     Cash withdrawnCashFromMachine(Long accountNumber, int requestedCash, Long atmId) {
        User dbUser = userService.getUserAccount(accountNumber);
         Long currentBalance = dbUser.getBalance();
         Long currentOverdraft = dbUser.getOverdraft();
        String message = hasCashMachineEnoughNotes(requestedCash, atmId);
        if(message.equals(WITHDRAWN)){
            if(currentBalance >= requestedCash){
                dbUser.setBalance(dbUser.getBalance() - requestedCash);
            }else{
                Long overdraft = requestedCash - currentBalance;
                dbUser.setBalance(0L);
                dbUser.setOverdraft(currentOverdraft - overdraft);
            }
            userService.updateUser(dbUser);
            return new Cash(dbUser.getBalance());
        }
        return null;
    }

    /**
     * @param requestedCash
     * @param atmId
     * @return
     */
    private String hasCashMachineEnoughNotes(int requestedCash, Long atmId) {
        CashNotes notes = cashMachineService.getCashNotes(atmId);
        Long totalAtmBalance = getTotalAtmBalance(notes);
        if(totalAtmBalance < requestedCash){
            //no enough money in cash machine
            return NO_CASH_IN_ATM;
        }else {
            return processRequest(requestedCash, notes);
        }
    }

    /**
     * @param requestedCash
     * @param notes
     * @return
     */
    private String processRequest(int requestedCash, CashNotes notes) {
        //should dispense the minimum number of notes per withdrawal
        List<Integer> iNotes = Arrays.asList(50, 20, 10, 5);

        for(Integer i : iNotes){
            int iCount = noteCounter(requestedCash, i);
            if(iCount >= 1){
                int finalCounter = getNumberOfNotesFromMachine(i, notes, iCount);
                if(finalCounter > 0){
                    requestedCash = requestedCash - finalCounter*i;
                    if(requestedCash == 0){
                        break;
                    }
                }
            }
        }
        if(requestedCash == 0){
            //withdrawn notes from cash machine
            cashMachineService.updateCashMachineNotes(notes);
            return WITHDRAWN;
        }else{
            return NO_NOTES_FOR_REQUEST;
        }
    }

    /**
     * @param iNote
     * @param notes
     * @param iCount
     * @return
     */
    private int getNumberOfNotesFromMachine(Integer iNote, CashNotes notes, int iCount) {
        int noteCounter = getNoteCounter(iNote, notes);
        if(noteCounter >= iCount){
            //reduce number of notes from cashNotes
            reduceNotes(iNote, notes, noteCounter - iCount);
            noteCounter = iCount;
        }else if(noteCounter > 0){
            reduceNotes(iNote, notes, 0);
        }
        return noteCounter;
    }

    /**
     * @param iNote
     * @param notes
     * @param newBalance
     */
    private void reduceNotes(Integer iNote, CashNotes notes, int newBalance) {
        switch (iNote){
            case 50: notes.setFifties(newBalance);
                break;
            case 20: notes.setTwenties(newBalance);
                break;
            case 10: notes.setTens(newBalance);
                break;
            case 5: notes.setFives(newBalance);
                break;
            default:
        }
    }

    /**
     * @param iNote
     * @param notes
     * @return
     */
    private int getNoteCounter(Integer iNote, CashNotes notes) {
        int noteCounter = 0;
        switch (iNote){
            case 50: noteCounter = notes.getFifties();
                break;
            case 20: noteCounter = notes.getTwenties();
                break;
            case 10: noteCounter = notes.getTens();
                break;
            case 5: noteCounter = notes.getFives();
                break;
            default: noteCounter = 0;
        }
        return noteCounter;
    }

    private int noteCounter(int requestedCash, int note){
        return requestedCash/note;
    }

    /**
     * @param atmId
     * @param pin
     * @return
     */
    @Override
    public SuccessResponse displayStatistics(Long atmId, String pin) {
        SuccessResponse response = new SuccessResponse();
        //should not expose the atm balance if the pin is incorrect
        if(isPinValidForATM(atmId, pin)){
            response.setMessage(SUCCESS);
        }else {
            response.setMessage("ERROR");
            response.setMessage(INCORRECT_ATM_DETAILS);
            return response;
        }
        Cash cash = fetchAtmContent(atmId);
        Long totalAtmBalance = getTotalAtmBalance(cash.getCashNotes());
        cash.setBalanceLeft(totalAtmBalance);
        response.setCash(cash);
        return response;
    }

    /**
     * @param cashNotes
     * @return
     */
    private Long getTotalAtmBalance(CashNotes cashNotes) {
        return Long.valueOf(cashNotes.getFives()*5 + cashNotes.getTens()*10 + cashNotes.getTwenties()*20 + cashNotes.getFifties()*50);
    }

    /**
     * @param atmId
     * @return
     */
    private Cash fetchAtmContent(Long atmId) {
        CashNotes notes = cashMachineService.getCashNotes(atmId);
        notes.setPin(null);
        return new Cash(notes);
    }

    /**
     * @param atmId
     * @param pin
     * @return
     */
    private boolean isPinValidForATM(Long atmId, String pin) {
        return cashMachineService.checkCashMachineCredentials(atmId, pin);
    }

    /**
     * @param accountNumber
     * @param pin
     * @return
     */
    private Cash getUserCashBalance(Long accountNumber, String pin) {
        User userAccount = userService.getUserAccount(accountNumber, pin);
        Cash cash = new Cash();
        cash.setBalanceLeft(userAccount.getBalance());
       return cash;
    }
}
