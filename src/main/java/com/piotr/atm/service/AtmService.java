package com.piotr.atm.service;

import com.piotr.atm.model.response.SuccessResponse;

public interface AtmService {

   SuccessResponse getBalance(Long accountNumber, String pin);
   SuccessResponse withdrawnCash(Long accountNumber, String pin, int requestedCash, Long atmId);
   SuccessResponse displayStatistics(Long atmId, String pin);
}
