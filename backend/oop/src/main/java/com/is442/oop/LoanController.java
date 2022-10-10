package com.is442.oop;


import com.is442.oop.daos.LoanDAO;
import com.is442.oop.daos.LoanDAOInt;
import com.is442.oop.data.models.Loan;
import com.is442.oop.exceptions.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/loan")
public class LoanController {
    @Autowired
    LoanDAOInt loanDAOInt;

    @GetMapping("")
    public ResponseEntity<List<Loan>> getAllLoans(){
        List<Loan> loans = loanDAOInt.getAllLoan(); // how does this work?
        System.out.println(loans);
        return new ResponseEntity<>(loans, HttpStatus.OK);
    }
    // can be improved to give a more verbose error
    @GetMapping("/{loanId}")
    public ResponseEntity<Loan> getLoanByLoanID(@PathVariable("loanId") Integer loanId) {
        Loan loan = null;
        try{
            loan = loanDAOInt.getLoanByLoanID(loanId);
        } catch (Exception e){
            return new ResponseEntity<>(loan, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(loan, HttpStatus.OK);
    }

}


