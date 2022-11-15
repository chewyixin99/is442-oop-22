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
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.TreeMap;

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

    public boolean checkUserLoanable(Integer userId, YearMonth loanDate) {
        Map<YearMonth, Integer> numLoansPerMonth = getNumLoansGroupedByMonthByUserId(userId);
        Integer numLoans = 0;
        if (numLoansPerMonth.containsKey(loanDate)) {
            numLoans = numLoansPerMonth.get(loanDate);
        }

        if (numLoans == 2) {
            return false;
        }

        return true;
    }

    // Done
    @Transactional
    @Override
    public Loan createLoan(LoanRequest loanRequest) throws ActionNotExecutedException, ResourceNotFoundException {
        // Pass cannot be loaned for the day. Inserting validation here. Might need to change in the future, as users will select via POI, not via ID.
        boolean loanable = this.checkUserLoanable(loanRequest.getUserID(), YearMonth.from(loanRequest.getStartDate()));

        if (!loanable) {
            throw new IllegalArgumentException("User already encountered limit of 2 loans per month.");
        }
        
        Integer passID = loanRequest.getPassID();
        Pass pass = null;
        try {
            pass = passService.getPass(passID);
        } catch (Exception e) {
            throw new ResourceNotFoundException("Pass", "passId", passID);
        }

        LocalDate startDate = loanRequest.getStartDate();
        List<Loan> loans = this.getLoanByPassID(passID);
        for (Loan l: loans){
            if (l.getStartDate().equals(startDate) && !(l.isDefunct() || l.isCompleted())){
                throw new ActionNotExecutedException("Loan", "Pass is already loaned for the day");
            }
        }

        List<Loan> secondaryLoans = this.getLoanByPassID(loanRequest.getSecondaryPassID());
        for (Loan l: secondaryLoans){
            if (l.getStartDate().equals(startDate) && !(l.isDefunct() || l.isCompleted())){
                throw new ActionNotExecutedException("Loan", "Seconary pass is already loaned for the day");
            }
        }
        
        // Get user
        User user = null;
        try {
            user = userService.getUser(loanRequest.getUserID());
        } catch (Exception e) {
            throw new ResourceNotFoundException("User", "User Id", loanRequest.getUserID());
        }

        // First loan to first pass
        Loan newLoan = new Loan();
        newLoan.setUser(user);
        newLoan.setPass(pass);
        newLoan.setStartDate(loanRequest.getStartDate());
        newLoan.setEndDate(loanRequest.getEndDate());
        newLoan.setGopId(1);
        loanRepository.save(newLoan);

        Integer secondaryPassID = loanRequest.getSecondaryPassID();
        Pass secondaryPass = null;
        Loan secondaryLoan = null;

        if (secondaryPassID != null) {
            try {
                secondaryPass = passService.getPass(secondaryPassID);
            } catch (Exception e) {
                throw new ResourceNotFoundException("Pass", "secondaryPassID", secondaryPassID);
            }

            // Second loan to second pass
            secondaryLoan = new Loan();
            secondaryLoan.setUser(user);
            secondaryLoan.setPass(secondaryPass);
            secondaryLoan.setStartDate(loanRequest.getStartDate());
            secondaryLoan.setEndDate(loanRequest.getEndDate());
            secondaryLoan.setGopId(1);
            secondaryLoan.setPrimaryLoan(newLoan); // Set reference to main loan
            loanRepository.save(secondaryLoan);    
        }

        // Send email for passes
        try {
            int templateId = pass.getIsPhysical() ? 4 : 3;
            emailService.sendLoanConfirmationEmail(newLoan, templateId);
            
            if (secondaryPass != null) {
                templateId = secondaryPass.getIsPhysical() ? 4 : 3;
                emailService.sendLoanConfirmationEmail(newLoan, templateId);
                return secondaryLoan;
            }
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

    @Override
    public Map<YearMonth, List<Loan>> getAllLoansGroupedByMonth() throws ResourceNotFoundException, ActionNotExecutedException {
        List<Loan> allLoans = loanRepository.findAllByDefunctFalse();

        if (allLoans == null) {
            throw new ResourceNotFoundException("Loan", "getAllLoansGroupedByMonth", allLoans);
        }

        Map<YearMonth, List<Loan>> results = new TreeMap<>();

        try {
            for (Loan loan: allLoans) {
                YearMonth loanYearMonth = YearMonth.from(loan.getStartDate());
                List<Loan> loansInYearMonth = new ArrayList<>();
    
                if (results.containsKey(loanYearMonth)) {
                    loansInYearMonth = results.get(loanYearMonth);
                }
    
                loansInYearMonth.add(loan);
                results.put(loanYearMonth, loansInYearMonth);
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
            throw new ActionNotExecutedException("getAllLoansGroupedByMonth", e);
        }

        return results;
    }

    @Override
    public Map<YearMonth, Integer> getNumLoansGroupedByMonth() throws ResourceNotFoundException, ActionNotExecutedException {
        Map<YearMonth, List<Loan>> allLoansByMonth = this.getAllLoansGroupedByMonth();

        if (allLoansByMonth == null) {
            throw new ResourceNotFoundException("Loan", "getAllLoansGroupedByMonth", allLoansByMonth);
        }

        Map<YearMonth, Integer> results = new TreeMap<>();

        try {
            for (Map.Entry<YearMonth, List<Loan>> entries: allLoansByMonth.entrySet()) {
                results.put(entries.getKey(), entries.getValue().size());
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
            throw new ActionNotExecutedException("getNumLoansGroupedByMonth", e);
        }

        return results;
    }

    @Override
    public Map<YearMonth, Integer> getNumLoansGroupedByMonthByUserId(Integer userId) throws ResourceNotFoundException, ActionNotExecutedException {
        User user = null;

        try {
            user = userService.getUser(userId);
        } catch (Exception e) {
            throw new ResourceNotFoundException("User", "userId", userId);
        }

        List<Loan> allLoans = loanRepository.findAllByUserAndDefunctFalse(user);

        if (allLoans == null) {
            throw new ResourceNotFoundException("Loan", "findAllByUserAndDefunctFalse", allLoans);
        }

        Map<YearMonth, Integer> results = new TreeMap<>();

        try {
            for (Loan loan: allLoans) {
                if (loan.getPrimaryLoan() != null) {
                    continue;
                }

                YearMonth loanYearMonth = YearMonth.from(loan.getStartDate());
                Integer loansInYearMonth = 0;
    
                if (results.containsKey(loanYearMonth)) {
                    loansInYearMonth = results.get(loanYearMonth);
                }
    
                loansInYearMonth++ ;
                results.put(loanYearMonth, loansInYearMonth);
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
            throw new ActionNotExecutedException("findAllByUserAndDefunctFalse", e);
        }

        return results;
    }
}
