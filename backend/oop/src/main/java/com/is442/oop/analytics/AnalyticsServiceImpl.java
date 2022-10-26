package com.is442.oop.analytics;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.is442.oop.data.models.Loan;
import com.is442.oop.data.models.Pass;
import com.is442.oop.data.models.PassStatus;
import com.is442.oop.data.payloads.dto.AnalyticsPassBreakdownDTO;
import com.is442.oop.exception.ActionNotExecutedException;
import com.is442.oop.loan.LoanService;
import com.is442.oop.pass.PassService;

@Service
public class AnalyticsServiceImpl implements AnalyticsService {
    @Autowired
    LoanService loanService;

    @Autowired
    PassService passService;

    @Override
    public List<Loan> getLoansForMonth(Integer month) throws ActionNotExecutedException {
        List<Loan> loans = loanService.getAllLoan();
        List<Loan> loansForMonth = new ArrayList<Loan>();
        
        try {
            for (Loan l: loans){
                Integer loanMonth = l.getStartDate().getMonthValue();
    
                if (loanMonth.equals(month)){
                    loansForMonth.add(l);
                }
            }
        } catch (Exception e) {
            throw new ActionNotExecutedException("AnalyticsService", e);
        }
        
        if (loansForMonth.isEmpty()){
            throw new ActionNotExecutedException("AnalyticsService", String.format("Loans for month %s not found.",month));
        }

        return loans;
    };

    @Override
    public List<Loan> getLoansByUserID(Integer userId) throws ActionNotExecutedException {
        List<Loan> loans = loanService.getLoanByUserID(userId);
        if (loans.isEmpty()){
            throw new ActionNotExecutedException("LoanService", "No loan available.");
        }
        return loans;
    };

    @Override
    public List<Loan> getLoansPerUserPerMonth(Integer userId, Integer month) throws ActionNotExecutedException {
        List<Loan> loans = loanService.getAllLoan();
        List<Loan> loansPerUserPerMonth = new ArrayList<Loan>();
        try{
            for (Loan l: loans){
                Integer loanMonth = l.getStartDate().getMonthValue();
                if (l.getUserId() == userId && loanMonth.equals(month)) {
                    loansPerUserPerMonth.add(l);
                }
            }
        } catch (Exception e){
            throw new ActionNotExecutedException("LoanService", e);
        }
        if (loansPerUserPerMonth.isEmpty()){
            throw new ActionNotExecutedException("LoanService", "No loan available.");
        }
    
        return loansPerUserPerMonth;
    };

    @Override
    public AnalyticsPassBreakdownDTO getPassBreakdown() throws ActionNotExecutedException {
        List<Pass> passes = passService.getAllPasses();
        AnalyticsPassBreakdownDTO passBreakdown = new AnalyticsPassBreakdownDTO();

        try {
            for (Pass p: passes) {
                if (p.getPassStatus().equals(PassStatus.AVAILABLE)) passBreakdown.addAvailable();
                else if (p.getPassStatus().equals(PassStatus.ONLOAN)) passBreakdown.addOnLoan();
                else if (p.getPassStatus().equals(PassStatus.UNRETURNED)) passBreakdown.addUnreturned();
            }
        } catch (Exception e) {
            throw new ActionNotExecutedException("AnalyticsPassBreakdownDTO", e);
        }
        
        return passBreakdown;
    };
}
