package com.is442.oop;


import com.is442.oop.daos.LoanDAO;
import com.is442.oop.daos.LoanDAOInt;
import com.is442.oop.data.models.Loan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
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



}


