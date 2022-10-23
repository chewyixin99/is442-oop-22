package com.is442.oop.loanpass;

import com.is442.oop.loan.*;
import com.is442.oop.pass.*;
import com.is442.oop.data.models.*;

import java.util.ArrayList;
import java.util.HashSet;
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
import com.is442.oop.data.models.Pass;
import com.is442.oop.data.payloads.response.DataResponse;

@RestController
@RequestMapping("/loanpass")
public class LoanPassController {
    @Autowired
    LoanService loanService;

    @Autowired
    PassService passService;

    @GetMapping("/getLoanByPOI/{poi}")
    public ResponseEntity<DataResponse> getLoanByPOI(@PathVariable("poi") String poi){
        List<Pass> passes = passService.getAllPasses();
        List<Loan> loans= loanService.getAllLoan();

        HashSet<Integer> passIdWithPOI = new HashSet<Integer>();
        List<Loan> loansWithPOI = new ArrayList<Loan>();

        // Obtains all passID with the specified POI
        for (Pass p: passes){
            if (p.getPoi().equals(poi)){
                passIdWithPOI.add(p.getPassId());
            }
        }

        if (passIdWithPOI.isEmpty()){
            return new ResponseEntity<>(new DataResponse(loansWithPOI, String.format("Loans with poi %s found",poi)), HttpStatus.NOT_FOUND);
        }
        // Obtains all loans with passID in passIdWithPOI

        for (Loan l: loans){
            if (passIdWithPOI.contains(l.getPassId())){
                loansWithPOI.add(l);
            }
        }

        return new ResponseEntity<>(new DataResponse(loansWithPOI, "Loans"), HttpStatus.OK);

        
    }

}


