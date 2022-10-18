package com.is442.oop.loan;

import java.util.List;

import org.springframework.stereotype.Component;

import com.is442.oop.data.models.Loan;
import com.is442.oop.data.payloads.response.MessageResponse;
import com.is442.oop.exception.ResourceNotFoundException;

@Component
public interface LoanService {

    List<Loan> getAllLoan();
    MessageResponse createLoan(LoanRequest loanRequest);
    Loan getLoanByLoanID(int loanID) throws ResourceNotFoundException;
    List<Loan> getLoanByUserID(int userID);
    List<Loan> getLoanByPassID(int passID);
    MessageResponse deleteLoan(Integer loanID)throws ResourceNotFoundException;

    MessageResponse updateLoanToCompleted(UpdateLoantoCompletedRequest updateLoanRequest) throws ResourceNotFoundException;
    MessageResponse updateLoan(UpdateLoanRequest updateLoanRequest) throws ResourceNotFoundException;
}
