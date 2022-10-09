package com.is442.oop.daos;

import java.util.*;

import com.is442.oop.data.models.Loan;
import com.is442.oop.data.payloads.request.*;
import com.is442.oop.data.payloads.response.MessageResponse;
import com.is442.oop.exceptions.ResourceNotFoundException;
import org.springframework.stereotype.Component;

@Component
public interface LoanDAOInt {
    MessageResponse createLoan(LoanRequest loanRequest);
    Optional<Loan> updateLoan(Integer loanID, LoanRequest loanRequest) throws ResourceNotFoundException;
    Loan getLoanByLoanID(int loanID) throws ResourceNotFoundException;
    List<Loan> getLoanByUserID(int userID);
    List<Loan> getLoanByPassID(int passID);
    Optional<Loan> updateLoanToCompleted(Integer loanID, Integer userID, Integer gopID, LoanRequest loanRequest) throws ResourceNotFoundException;
    void deleteLoan(Integer loanID)throws ResourceNotFoundException;
}
