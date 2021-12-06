package com.piotr.atm.controller;

import com.piotr.atm.model.payload.AtmUserPayload;
import com.piotr.atm.model.payload.AtmWithdrawnPayload;
import com.piotr.atm.model.payload.AtmPayload;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/atm/")
public class AtmController {


    /**
     * @param payload
     * @return
     */
    @PostMapping("balance")
    public ResponseEntity<Integer> displayBalance(@RequestBody AtmUserPayload payload){
        return ResponseEntity.ok().body(10);
    }

    /**
     * @param atmId
     * @param payload
     * @return
     */
    @PostMapping("withdrawn/{atmId}")
    public ResponseEntity withdrawn(@PathVariable("atmId") Long atmId,
                              @RequestBody AtmWithdrawnPayload payload){
        return ResponseEntity.ok().body("This is withdrawn API");
    }

    /**
     * @param atmId
     * @param payload
     * @return
     */
    @PostMapping("statistics/{atmId}")
    public ResponseEntity displayStatistics(@PathVariable("atmId") Long atmId,
                                           @RequestBody AtmPayload payload){
        return ResponseEntity.ok().body("This is statistics API");
    }

}
