package com.is442.oop.service;

import ch.qos.logback.core.recovery.ResilientOutputStreamBase;
import com.is442.oop.data.models.Loan;
import com.is442.oop.data.payloads.request.LoanRequest;
import com.is442.oop.data.payloads.request.UpdateLoanRequest;
import com.is442.oop.data.payloads.response.MessageResponse;
import com.is442.oop.data.repository.LoanRepository;
import com.is442.oop.exception.ResourceNotFoundException;

import org.aspectj.bridge.Message;
import org.hibernate.sql.Update;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class LoanServiceImpl implements LoanService{
    @Autowired
    LoanRepository loanRepository;

    @Override
    public List<Loan> getAllLoan() {
        return loanRepository.findAll();
    }
    // Done
    @Override
    public MessageResponse createLoan(LoanRequest loanRequest) {
        // Pass cannot be loaned for the day. Inserting validation here. Might need to change in the future, as users will select via POI, not via ID.
        Integer passID = loanRequest.getPassID();
        String startDate = loanRequest.getStartDate();

        List<Loan> loans = this.getLoanByPassID(passID);
        for (Loan l: loans){
            if (l.getStartDate().equals(startDate)){
                return new MessageResponse("This pass is already loaned for the day");
            }
        }

        Loan newLoan = new Loan();
        newLoan.setUserId(loanRequest.getUserID());
        newLoan.setPassId(loanRequest.getPassID());

        newLoan.setStartDate(loanRequest.getStartDate());
        newLoan.setEndDate(loanRequest.getEndDate());
        newLoan.setCompleted(false); // Defaults to incomplete
        newLoan.setGopId(1);
        loanRepository.save(newLoan);

        return new MessageResponse("New Loan has successfully been created");
    }

    // Done
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


    // Done
    @Override
    public List<Loan> getLoanByUserID(int userID) {
        List<Loan> loans = loanRepository.findAll();
        List<Loan> toReturn = new ArrayList<>();
        for (Loan l: loans){
            if (l.getUserId() == userID){
                 toReturn.add(l);
            }
        }
        return toReturn;
    }

    // Done
    @Override
    public List<Loan> getLoanByPassID(int passID) {
        List<Loan> loans = this.getAllLoan();
        List<Loan> toReturn = new ArrayList<>();
        for (Loan l: loans){
            if (l.getUserId() == passID){
                toReturn.add(l);
            }
        }
        return toReturn;

    }

    // Done
    @Override
    public MessageResponse updateLoanToCompleted(UpdateLoanRequest updateLoanRequest) throws ResourceNotFoundException {
        Optional<Loan> queryLoan = loanRepository.findById(updateLoanRequest.getLoanId());
        if (queryLoan.isEmpty()){
            throw new ResourceNotFoundException("Loan ID "+updateLoanRequest.getLoanId() + " does not exist");
        }
        Loan loan = queryLoan.get();
        if (loan.isCompleted()){
            return new MessageResponse("Loan ID "+updateLoanRequest.getLoanId() + " is already completed");
        }
        loan.setGopId(updateLoanRequest.getGopId());
        loan.setCompleted(true);
        loanRepository.save(loan);
        return new MessageResponse("Loan ID "+updateLoanRequest.getLoanId() + " successfully updated to completed");
    }

    // unimplemented
    @Override
    public MessageResponse deleteLoan(Integer loanID) throws ResourceNotFoundException {
        Optional<Loan> queryLoan = loanRepository.findById(loanID);
        if (queryLoan.isEmpty()){
            throw new ResourceNotFoundException("Loan of ID "+loanID+" does not exist");
        }

        loanRepository.deleteById(loanID);
        return new MessageResponse("Loan of ID "+loanID+" successfully deleted");
    }
}
