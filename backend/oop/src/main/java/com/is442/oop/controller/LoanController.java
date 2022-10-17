package com.is442.oop.controller;


import com.is442.oop.data.models.Loan;
import com.is442.oop.data.payloads.request.LoanRequest;
import com.is442.oop.data.payloads.request.UpdateLoanRequest;
import com.is442.oop.data.payloads.response.MessageResponse;
import com.is442.oop.exception.ResourceNotFoundException;
import com.is442.oop.service.LoanService;

import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpStatusCodeException;

import java.util.List;

@RestController
@RequestMapping("/loan")
public class LoanController {
    @Autowired
    LoanService loanService;

    @GetMapping("")
    public ResponseEntity<List<Loan>> getAllLoans(){
        List<Loan> loans = loanService.getAllLoan();
        return new ResponseEntity<>(loans, HttpStatus.OK);
    }
    // can be improved to give a more verbose error
    @GetMapping("/{loanId}")
    public ResponseEntity<Loan> getLoanByLoanID(@PathVariable("loanId") Integer loanId) {
        Loan loan = null;
        try{
            loan = loanService.getLoanByLoanID(loanId);
        } catch (Exception e){
            return new ResponseEntity<>(loan, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(loan, HttpStatus.OK);
    }

    // Done
    @GetMapping("/byUserId/{userId}")
    public ResponseEntity<List<Loan>> getLoanByUserID(@PathVariable("userId") Integer userId){
        List<Loan> loans = loanService.getLoanByUserID(userId);
        if (loans.isEmpty()){
            return new ResponseEntity<>(loans, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(loans, HttpStatus.OK);
    }

    @GetMapping("/byPassId/{passId}")
    public ResponseEntity<List<Loan>> getLoanByPassID(@PathVariable("passId") Integer passId){
        List<Loan> loans = loanService.getLoanByPassID(passId);
        if (loans.isEmpty()){
            return new ResponseEntity<>(loans, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(loans, HttpStatus.OK);
    }
    // Done
    @PostMapping("")
    public ResponseEntity<MessageResponse> createLoan(@RequestBody LoanRequest createLoanRequest){
        MessageResponse newLoan = loanService.createLoan(createLoanRequest);
        return new ResponseEntity<>(newLoan, HttpStatus.CREATED);
    }

    @PutMapping("/updateCompleted")
    public ResponseEntity<MessageResponse> updateLoanToCompleted(@RequestBody UpdateLoanRequest updateLoanRequest){
        MessageResponse updatedLoan = null;
        try{
            updatedLoan = loanService.updateLoanToCompleted(updateLoanRequest);
            return new ResponseEntity<>(updatedLoan, HttpStatus.OK);
        } catch (Exception e){
            return new ResponseEntity<>(updatedLoan, HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{loanId}")
    public ResponseEntity<MessageResponse> deleteLoan(@PathVariable("loanId")Integer loanId){
        MessageResponse deletedLoan= null;
        try{
            deletedLoan = loanService.deleteLoan(loanId);
            return new ResponseEntity<>(deletedLoan,HttpStatus.OK);
        } catch (Exception e){
            return new ResponseEntity<>(deletedLoan,HttpStatus.NOT_FOUND);
        }
    }
}


