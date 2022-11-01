package com.is442.oop.scheduled;

import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.is442.oop.data.models.Loan;
import com.is442.oop.data.models.Pass;
import com.is442.oop.data.models.PassStatus;
import com.is442.oop.exception.ActionNotExecutedException;
import com.is442.oop.loan.LoanRepository;
import com.is442.oop.pass.PassRepository;
import com.is442.oop.pass.PassService;

@Service
public class ScheduledServiceImpl implements ScheduledService {
    
    @Autowired
    LoanRepository loanRepository;

    @Autowired
    PassRepository passRepository;

    @Autowired
    PassService passService;

    public List<Pass> updatePassesToUnreturned() throws ActionNotExecutedException {
        List<Loan> expiredLoans = null;
        try {
            expiredLoans = loanRepository.getAllExpiredLoans();
        } catch (Exception e) {
            throw new ActionNotExecutedException("LoanRepository", e);
        }

        List<Pass> unreturnedPasses = new ArrayList<Pass>();
        for (Loan l: expiredLoans) {
            try {
                Pass unreturnedPass = l.getPass();
                unreturnedPass.setPassStatus(PassStatus.UNRETURNED);
                passRepository.save(unreturnedPass);
                unreturnedPasses.add(unreturnedPass);
            } catch (Exception e) {
                throw new ActionNotExecutedException("PassService", e);
            }
        }

        return unreturnedPasses;
    };
}
    