package com.is442.oop.daos;

import com.is442.oop.data.models.Loan;
import com.is442.oop.data.payloads.request.LoanRequest;
import com.is442.oop.data.payloads.response.MessageResponse;
import com.is442.oop.data.repository.LoanRepository;
import com.is442.oop.exceptions.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.util.List;
import java.util.Optional;

@Service
public class LoanDAO implements LoanDAOInt{
    @Autowired
    LoanRepository loanRepository;

    @Override
    public List<Loan> getAllLoan() {
        return loanRepository.findAll();
    }

    @Override
    public MessageResponse createLoan(LoanRequest loanRequest) {
        Loan newLoan = new Loan();
        newLoan.setId(loanRequest.getLoanID());
        newLoan.setUserId(loanRequest.getUserID());
        newLoan.setPassId(loanRequest.getPassID());

        // THese 2 set the start and end date. Can be done within this header itself, aka,
        // I will set the start and end here
        newLoan.setStartDate(loanRequest.getStartDate());
        newLoan.setEndDate(loanRequest.getEndDate());
        loanRepository.save(newLoan);

        return new MessageResponse("New Loan has successfully been created");
    }

    // This method is INCOMPLETE
    @Override
    public Optional<Loan> updateLoan(Integer loanID, LoanRequest loanRequest) throws ResourceNotFoundException {
        Optional<Loan> loan = loanRepository.findById(loanID);
        if (loan.isEmpty()){
            throw new ResourceNotFoundException("Loan of ID "+loanID+" is not found");
        }
        else{
            loan.get().setStartDate(loanRequest.getStartDate());
            loan.get().setEndDate(loanRequest.getEndDate());
            loan.get().setPassId(loanRequest.getPassID());
            loanRepository.save(loan.get());
            return loan;

        }

    }

    @Override
    public Loan getLoanByLoanID(int loanID) throws ResourceNotFoundException{
        Loan loan = null;
        Optional<Loan> queryLoan = loanRepository.findById(loanID);
        if (queryLoan.isEmpty()){
            throw new ResourceNotFoundException("Resource not found.");
        }

        loan = queryLoan.get();
        return loan;

    }


    // unsure of hwo to implement this
    @Override
    public List<Loan> getLoanByUserID(int userID) {
        return null;
    }

    // unsure of how to implement this
    @Override
    public List<Loan> getLoanByPassID(int passID) {
        return null;
    }

    // unsure of how to implement
    @Override
    public Optional<Loan> updateLoanToCompleted(Integer loanID, Integer userID, Integer gopID, LoanRequest loanRequest) throws ResourceNotFoundException {
        return Optional.empty();
    }

    // unimplementedd
    @Override
    public void deleteLoan(Integer loanID) throws ResourceNotFoundException {
        System.out.println("Hello");
    }
}
