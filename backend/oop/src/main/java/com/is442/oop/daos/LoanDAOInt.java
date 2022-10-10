package com.is442.oop.daos;

import java.util.*;

import com.is442.oop.data.models.Loan;
import com.is442.oop.data.payloads.request.*;
import com.is442.oop.data.payloads.response.MessageResponse;
import com.is442.oop.exceptions.ResourceNotFoundException;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Component
public interface LoanDAOInt {

    List<Loan> getAllLoan();
    MessageResponse createLoan(LoanRequest loanRequest);
    Loan getLoanByLoanID(int loanID) throws ResourceNotFoundException;
    List<Loan> getLoanByUserID(int userID);
    List<Loan> getLoanByPassID(int passID);
    MessageResponse deleteLoan(Integer loanID)throws ResourceNotFoundException;

    MessageResponse updateLoanToCompleted(UpdateLoanRequest updateLoanRequest) throws ResourceNotFoundException;
}
