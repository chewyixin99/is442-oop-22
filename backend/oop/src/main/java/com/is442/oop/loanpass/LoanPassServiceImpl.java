package com.is442.oop.loanpass;

import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.is442.oop.data.models.Loan;
import com.is442.oop.data.models.Pass;
import com.is442.oop.exception.ResourceNotFoundException;
import com.is442.oop.loan.LoanService;
import com.is442.oop.pass.PassService;

@Service
public class LoanPassServiceImpl implements LoanPassService {
    @Autowired
    LoanService loanService;

    @Autowired
    PassService passService;

    @Override
    public List<Loan> getLoanByPoi(String poi) throws ResourceNotFoundException {
        List<Pass> passes = passService.getAllPasses();
        List<Loan> loans= loanService.getAllLoan();

        HashSet<Pass> passWithPoi = new HashSet<Pass>();
        List<Loan> loansWithPoi = new ArrayList<Loan>();

        // Obtains all passes with the specified POI
        for (Pass pass: passes){
            if (pass.getPoi().equals(poi)){
                passWithPoi.add(pass);
            }
        }

        if (passWithPoi.isEmpty()){
            throw new ResourceNotFoundException("Pass", "poi", poi);
        }
        
        for (Loan loan: loans){
            if (passWithPoi.contains(loan.getPass())){
                loansWithPoi.add(loan);
            }
        }

        return loansWithPoi;
    }
}
