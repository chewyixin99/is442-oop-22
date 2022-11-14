package com.is442.oop.loan;

import com.is442.oop.data.models.Loan;
import com.is442.oop.data.models.Pass;
import com.is442.oop.data.models.User;
import com.is442.oop.email.EmailService;
import com.is442.oop.exception.*;
import com.is442.oop.pass.PassService;
import com.is442.oop.user.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

@Service
public class LoanServiceImpl implements LoanService{
    @Autowired
    LoanRepository loanRepository;

    @Autowired
    UserService userService;

    @Autowired
    PassService passService;

    @Autowired
    EmailService emailService;

    @Override
    public List<Loan> getAllLoan() {
        return loanRepository.findAll();
    }
    // Done
    @Transactional
    @Override
    public Loan createLoan(LoanRequest loanRequest) throws ActionNotExecutedException, ResourceNotFoundException {
        // Pass cannot be loaned for the day. Inserting validation here. Might need to change in the future, as users will select via POI, not via ID.

        Integer passID = loanRequest.getPassID();
        Pass pass = null;
        try {
            pass = passService.getPass(passID);
        } catch (Exception e) {
            throw new ResourceNotFoundException("User", "User Id", loanRequest.getUserID());
        }

        LocalDate startDate = loanRequest.getStartDate();
        List<Loan> loans = this.getLoanByPassID(passID);
        for (Loan l: loans){
            if (l.getStartDate().equals(startDate) && !(l.isDefunct() || l.isCompleted())){
                throw new ActionNotExecutedException("Loan", "Pass is already loaned for the day");
            }
        }
        
        User user = null;
        try {
            user = userService.getUser(loanRequest.getUserID());
        } catch (Exception e) {
            throw new ResourceNotFoundException("User", "User Id", loanRequest.getUserID());
        }

        Loan newLoan = new Loan();
        newLoan.setUser(user);
        newLoan.setPass(pass);
        newLoan.setStartDate(loanRequest.getStartDate());
        newLoan.setEndDate(loanRequest.getEndDate());
        newLoan.setGopId(1);
        loanRepository.save(newLoan);

        // To uncomment for mail sending when user creation gets completed
        try {
            int templateId = pass.getIsPhysical() ? 4 : 3;
            emailService.sendLoanConfirmationEmail(newLoan, templateId);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            throw new ActionNotExecutedException("sendLoanConfirmationEmail", e);
        }

        return newLoan;
    }

    // Done
    @Override
    public Loan getLoanByLoanID(int loanID) throws ResourceNotFoundException{
        Loan loan = null;
        Optional<Loan> queryLoan = loanRepository.findById(loanID);
        if (queryLoan.isEmpty()){
            throw new ResourceNotFoundException("Loan", "Loan ID", loanID);
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
            if (l.getUser().getUserId() == userID){
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
            if (l.getPass().getPassId() == passID){
                toReturn.add(l);
            }
        }
        return toReturn;
    }

    // Done
    @Override
    public Loan updateLoanToCompleted(UpdateLoantoCompletedRequest updateLoanRequest) throws RuntimeException {
        Integer queryLoanId = updateLoanRequest.getLoanId();
        Optional<Loan> queryLoan = loanRepository.findById(queryLoanId);
        if (queryLoan.isEmpty()){
            throw new ResourceNotFoundException("Loan", "Loan ID", queryLoanId);
        }
        Loan loan = queryLoan.get();
        if (loan.isCompleted()){
            throw new ActionNotExecutedException("Loan", String.format("Loan ID %d is already completed.", queryLoanId));
        }
        loan.setGopId(updateLoanRequest.getGopId());
        loan.setCompleted(true);
        loanRepository.save(loan);
        return loan;
    }

    // Done
    @Override
    public Loan deleteLoan(Integer loanId) throws ResourceNotFoundException {
        Optional<Loan> queryLoan = loanRepository.findById(loanId);
        if (queryLoan.isEmpty()){
            throw new ResourceNotFoundException("Loan", "Loan ID", loanId);
        }
        Loan loan = queryLoan.get();
        loan.setDefunct(true);
        loanRepository.save(loan);
        return loan;
    }

    @Override
    public Loan updateLoan(UpdateLoanRequest updateLoanRequest) throws RuntimeException {
        Integer queryLoanId = updateLoanRequest.getLoanId();
        Optional<Loan> queryLoan = loanRepository.findById(updateLoanRequest.getLoanId());
        if (queryLoan.isEmpty()){
            throw new ResourceNotFoundException("Loan", "Loan ID", queryLoanId);
        }

        Integer passID = updateLoanRequest.getPassId();
        Pass pass = null;
        try {
            pass = passService.getPass(passID);
        } catch (Exception e) {
            throw new ActionNotExecutedException("Pass", e);
        }

        User user = null;
        try {
            user = userService.getUser(updateLoanRequest.getUserId());
        } catch (Exception e) {
            throw new ResourceNotFoundException("User", "User Id", updateLoanRequest.getUserId());
        }

        try{

            Loan loan = queryLoan.get();
            // Making sure the values obtained are inputted during post
            LocalDate startDate = (updateLoanRequest.getStartDate() == null) ? loan.getStartDate() : updateLoanRequest.getStartDate();
            LocalDate endDate = (updateLoanRequest.getEndDate() == null) ? loan.getEndDate() : updateLoanRequest.getEndDate();
            Integer gopId = (updateLoanRequest.getGopId() == null) ? loan.getGopId() : updateLoanRequest.getGopId();

            // update the loan
            loan.setUser(user);
            loan.setPass(pass);
            loan.setStartDate(startDate);
            loan.setEndDate(endDate);
            loan.setGopId(gopId);
            loanRepository.save(loan);
            return loan;
        } catch (Exception e){
            throw e;
        }
        
    }

    @Override
    public Optional<Loan> getLoanForPassByDateBefore(LocalDate queryDate, Integer passId) {
        Optional<Loan> queryLoan = loanRepository.getLoanForPassByDate(queryDate, passId);   
        return queryLoan;
    }

    @Override
    public List<Loan> getLoanByDate(LocalDate date) {
        List<Loan> loans = this.getAllLoan();
        List<Loan> toReturn = new ArrayList<>();
        for (Loan l: loans){
            if (l.getStartDate().equals(date) && !(l.isDefunct() || l.isCompleted())){
                toReturn.add(l);
            }
        }
        return toReturn;
    }

}
