package com.is442.oop.analytics;

import com.is442.oop.loan.*;
import com.is442.oop.pass.*;
import com.is442.oop.user.*;
import com.is442.oop.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.HashMap;

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
import com.is442.oop.data.models.Pass;
import com.is442.oop.data.models.User;
import com.is442.oop.data.payloads.response.DataResponse;

@RestController
@RequestMapping("/analytics")
public class Analytics {
    @Autowired
    LoanService loanService;

    @Autowired
    PassService passService;

    @Autowired
    UserService userService;

    @GetMapping("/getLoansForMonth/{month}")
    public ResponseEntity<DataResponse> getLoansForMonth(@PathVariable("month") String month){
        try{
            List<Loan> loans = loanService.getAllLoan();
            List<Loan> loansForMonth = new ArrayList<Loan>();
    
    
            for (Loan l: loans){
                String loanMonth = l.getStartDate().split("/")[1];
    
                if (loanMonth.equals(month)){
                    loansForMonth.add(l);
                }
            }
    
            if (loansForMonth.isEmpty()){
                return new ResponseEntity<>(new DataResponse(loansForMonth, String.format("Loans for month %s not found",month)), HttpStatus.NOT_FOUND);
            }
            return new ResponseEntity<>(new DataResponse(loans, String.format("Loans",month)), HttpStatus.OK);
        } catch (Exception e){
            return new ResponseEntity<>(new DataResponse(null, String.format("Months should be a string. Please manually remove the month that does not follow the DD/MM/YYYY format in the db. This is a quick fix, and will be handled in later releases",month)), HttpStatus.NOT_FOUND);
        }


    }


    @GetMapping("/getLoansPerUserID/{userId}")
    public ResponseEntity<DataResponse> getLoanByUserID(@PathVariable("userId") Integer userId){
        List<Loan> loans = loanService.getLoanByUserID(userId);
        if (loans.isEmpty()){
            return new ResponseEntity<>(new DataResponse(loans, "Loan"), HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(new DataResponse(loans, "Loan"), HttpStatus.OK);
    }

    @GetMapping("/getLoansPerUserPerMonth/{userID}/{month}")
    public ResponseEntity<DataResponse> getLoansPerUserPerMonth(@PathVariable Integer userID, @PathVariable String month){
        List<Loan> loans = loanService.getAllLoan();
        List<Loan> loansPerUserPerMonth = new ArrayList<Loan>();
        try{
            for (Loan l: loans){
                String loanMonth = l.getStartDate().split("/")[1];
                if (l.getUserId() == userID && loanMonth.equals(month)){
                    loansPerUserPerMonth.add(l);
                }
            }
    
            if (loansPerUserPerMonth.isEmpty()){
                return new ResponseEntity<>(new DataResponse(loansPerUserPerMonth, "Loans"), HttpStatus.NOT_FOUND);
            }
            return new ResponseEntity<>(new DataResponse(loansPerUserPerMonth, "Loans"), HttpStatus.OK);
    
        } catch (ArrayIndexOutOfBoundsException e){
            return new ResponseEntity<>(new DataResponse(null, String.format("Months should be a string. Please manually remove the month that does not follow the DD/MM/YYYY format in the db. This is a quick fix, and will be handled in later releases",month)), HttpStatus.NOT_FOUND);
        }
    }