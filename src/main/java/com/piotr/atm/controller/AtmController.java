package com.piotr.atm.controller;

import com.piotr.atm.model.payload.AtmPayload;
import com.piotr.atm.model.payload.AtmUserPayload;
import com.piotr.atm.model.payload.AtmWithdrawnPayload;
import com.piotr.atm.model.response.ErrorResponse;
import com.piotr.atm.model.response.SuccessResponse;
import com.piotr.atm.service.AtmService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.Valid;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping(value = "/atm")
@Validated
public class AtmController {

    @Autowired
    private AtmService service;

    /**
     * @param payload
     * @return
     */
    @PostMapping("/balance")
    public ResponseEntity<SuccessResponse> displayBalance(@Valid @RequestBody AtmUserPayload payload){
        SuccessResponse response =  service.getBalance(payload.getAccountNumber(), payload.getPin());
        return ResponseEntity.ok().body(response);
    }

    /**
     * @param atmId
     * @param payload
     * @return
     */
    @PostMapping("/withdrawn/{atmId}")
    public ResponseEntity<SuccessResponse> withdrawn(@PathVariable("atmId")
                                    @Min(value = 1, message = "ATM id must be greater than 0")
                                    @Max(value = 9999, message = "ATM id must be lesser than 0") Long atmId,
                                    @Valid @RequestBody AtmWithdrawnPayload payload){
        SuccessResponse response = service.withdrawnCash(
                payload.getAccountNumber(), payload.getPin(), payload.getRequestedCash(), atmId);
        return ResponseEntity.ok().body(response);
    }

    /**
     * @param atmId
     * @param payload
     * @return
     */
    @PostMapping("/statistics/{atmId}")
    public ResponseEntity<SuccessResponse> displayStatistics(@PathVariable("atmId")
                                            @Min(value = 1, message = "ATM id must be greater than 0")
                                            @Max(value = 9999, message = "ATM id must be lesser than 0") Long atmId,
                                            @Valid @RequestBody AtmPayload payload){
        SuccessResponse response = service.displayStatistics(atmId, payload.getPin());
        return ResponseEntity.ok().body(response);
    }

    /**
     * Constraint Violation Exception
     * @param e
     * @return ErrorResponse
     */
    @ExceptionHandler(javax.validation.ConstraintViolationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    ErrorResponse handleConstraintViolationException(ConstraintViolationException e) {
        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setStatus("ERRORS");
        List<String> messages = new ArrayList<>();
        Set<ConstraintViolation<?>> errors = e.getConstraintViolations();
        for(ConstraintViolation violation : errors){
            messages.add(violation.getMessage());
        }
        errorResponse.setErrorMessages(messages);
        return errorResponse;
    }

    /**
     * Method Argument Not Valid Exception
     * @param ex
     * @return
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ErrorResponse handleValidationExceptions(MethodArgumentNotValidException ex) {
        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setStatus("ERRORS");
        List<String> messages = new ArrayList<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            messages.add(error.getDefaultMessage());
        });
        errorResponse.setErrorMessages(messages);
        return errorResponse;
    }
     /*@ExceptionHandler(ConstraintViolationException.class)
    public void constraintValidationException(HttpServletResponse response) throws IOException {
        response.sendError(HttpServletResponse.SC_BAD_REQUEST);
    }*/

}
