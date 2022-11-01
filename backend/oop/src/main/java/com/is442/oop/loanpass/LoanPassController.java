package com.is442.oop.loanpass;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.is442.oop.data.models.Loan;
import com.is442.oop.data.payloads.response.DataResponse;
import com.is442.oop.exception.ResourceNotFoundException;

@RestController
@RequestMapping("/loanpass")
public class LoanPassController {
    @Autowired
    LoanPassService loanPassService;

    @GetMapping("/getLoanByPOI/{poi}")
    public ResponseEntity<DataResponse> getLoanByPOI(@PathVariable("poi") String poi){
        List<Loan> loansWithPOI = null;
        try {
            loansWithPOI = loanPassService.getLoanByPoi(poi);
        } catch (ResourceNotFoundException e) {
            return new ResponseEntity<DataResponse>(new DataResponse(loansWithPOI, e), HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<DataResponse>(new DataResponse(loansWithPOI, e), HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return new ResponseEntity<>(new DataResponse(loansWithPOI, "LoanPass"), HttpStatus.OK);
    }

}


