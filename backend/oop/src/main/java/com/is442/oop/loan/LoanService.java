package com.is442.oop.loan;

import java.util.List;

import org.springframework.stereotype.Component;

import com.is442.oop.data.models.Loan;
import com.is442.oop.exception.*;

@Component
public interface LoanService {

    List<Loan> getAllLoan();
    Loan createLoan(LoanRequest loanRequest) throws ActionNotExecutedException;
    Loan getLoanByLoanID(int loanID) throws ResourceNotFoundException;
    List<Loan> getLoanByUserID(int userID);
    List<Loan> getLoanByPassID(int passID);
    Loan deleteLoan(Integer loanID)throws ResourceNotFoundException;
    Loan updateLoanToCompleted(UpdateLoantoCompletedRequest updateLoanRequest) throws RuntimeException;
    Loan updateLoan(UpdateLoanRequest updateLoanRequest) throws RuntimeException;
}
