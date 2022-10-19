package com.is442.oop.loan;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.is442.oop.data.models.Loan;
import com.is442.oop.data.payloads.response.DataResponse;

@RestController
@RequestMapping("/loan")
public class LoanController {
    @Autowired
    LoanService loanService;

    @GetMapping("")
    public ResponseEntity<DataResponse> getAllLoans(){
        List<Loan> loans = loanService.getAllLoan();
        return new ResponseEntity<>(new DataResponse(loans, "Loan"), HttpStatus.OK);
    }
    // can be improved to give a more verbose error
    @GetMapping("/{loanId}")
    public ResponseEntity<DataResponse> getLoanByLoanID(@PathVariable("loanId") Integer loanId) {
        Loan loan = null;
        try{
            loan = loanService.getLoanByLoanID(loanId);
        } catch (Exception e){
            return new ResponseEntity<>(new DataResponse(loan, e), HttpStatus.NOT_FOUND); // I think this is not needed becuase empty is still a valid response for search
        }
        return new ResponseEntity<>(new DataResponse(loan, "Loan"), HttpStatus.OK);
    }

    // Done
    @GetMapping("/byUserId/{userId}")
    public ResponseEntity<DataResponse> getLoanByUserID(@PathVariable("userId") Integer userId){
        List<Loan> loans = loanService.getLoanByUserID(userId);
        if (loans.isEmpty()){
            return new ResponseEntity<>(new DataResponse(loans, "Loan"), HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(new DataResponse(loans, "Loan"), HttpStatus.OK);
    }

    @GetMapping("/byPassId/{passId}")
    public ResponseEntity<DataResponse> getLoanByPassID(@PathVariable("passId") Integer passId){
        List<Loan> loans = loanService.getLoanByPassID(passId);
        if (loans.isEmpty()){
            return new ResponseEntity<>(new DataResponse(loans, "Loan"), HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(new DataResponse(loans, "Loan"), HttpStatus.OK);
    }
    // Done
    @PostMapping("")
    public ResponseEntity<DataResponse> createLoan(@RequestBody LoanRequest createLoanRequest){
        Loan newLoan = loanService.createLoan(createLoanRequest);
        return new ResponseEntity<>(new DataResponse(newLoan, "Loan"), HttpStatus.CREATED);
    }

    @PutMapping("/updateCompleted")
    public ResponseEntity<DataResponse> updateLoanToCompleted(@RequestBody UpdateLoantoCompletedRequest updateLoanRequest){
        Loan updatedLoan = null;
        try{
            updatedLoan = loanService.updateLoanToCompleted(updateLoanRequest);
            return new ResponseEntity<>(new DataResponse(updatedLoan, "Loan"), HttpStatus.OK);
        } catch (Exception e){
            return new ResponseEntity<>(new DataResponse(updatedLoan, e), HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/update")
    public ResponseEntity<DataResponse> updateLoan(@RequestBody UpdateLoanRequest updateLoanRequest){
        Loan updatedLoan = null;
        try{
            updatedLoan = loanService.updateLoan(updateLoanRequest);
            return new ResponseEntity<>(new DataResponse(updatedLoan, "Loan"), HttpStatus.OK);
        } catch (Exception e){
            return new ResponseEntity<>(new DataResponse(updatedLoan, e), HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{loanId}")
    public ResponseEntity<DataResponse> deleteLoan(@PathVariable("loanId")Integer loanId){
        Loan deletedLoan = null;
        try{
            deletedLoan = loanService.deleteLoan(loanId);
            return new ResponseEntity<>(new DataResponse(deletedLoan, "Loan"), HttpStatus.OK);
        } catch (Exception e){
            return new ResponseEntity<>(new DataResponse(deletedLoan, e) ,HttpStatus.NOT_FOUND);
        }
    }
}


