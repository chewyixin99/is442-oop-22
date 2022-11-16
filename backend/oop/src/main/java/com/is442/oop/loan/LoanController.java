package com.is442.oop.loan;


import java.time.LocalDate;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.is442.oop.data.models.Loan;
import com.is442.oop.data.models.User;
import com.is442.oop.data.payloads.dto.LoanWithPrevUserDTO;
import com.is442.oop.data.payloads.response.DataResponse;
import com.is442.oop.exception.ResourceNotFoundException;

import io.swagger.v3.oas.annotations.Operation;

@RestController
@RequestMapping("/loan")
public class LoanController {
    @Autowired
    LoanService loanService;

    @Operation(summary = "Gets all loans", description = "Gets all loans")
    @GetMapping("")
    public ResponseEntity<DataResponse> getAllLoans(){
        List<Loan> loans = loanService.getAllLoan();
        List<LoanWithPrevUserDTO> result = new ArrayList<>();

        try {
            for (Loan loan: loans) {
                User prevUser = null;
                Optional<Loan> prevLoan = loanService.getLoanForPassByDateBefore(loan.getStartDate(), loan.getPass().getPassId());
                if (prevLoan.isPresent()) {
                    prevUser = prevLoan.get().getUser();
                }  
                result.add(LoanUtil.toPrevUserDTO(loan, prevUser));
            } 
        } catch (Exception e) {
            return new ResponseEntity<>(new DataResponse(result, e), HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return new ResponseEntity<>(new DataResponse(result, "Loan"), HttpStatus.OK);
    }
    // can be improved to give a more verbose error
    @Operation(summary = "Get loan by id", description = "Get loan by id")
    @GetMapping("/{loanId}")
    public ResponseEntity<DataResponse> getLoanByLoanID(@PathVariable("loanId") Integer loanId) {
        Loan loan = null;
        User prevUser = null;
        LoanWithPrevUserDTO result = null;

        try{
            loan = loanService.getLoanByLoanID(loanId);
            Optional<Loan> prevLoan = loanService.getLoanForPassByDateBefore(loan.getStartDate(), loan.getPass().getPassId());
            if (prevLoan.isPresent()) {
                prevUser = prevLoan.get().getUser();
            }
            result = LoanUtil.toPrevUserDTO(loan, prevUser);
        } catch (Exception e){
            return new ResponseEntity<>(new DataResponse(result, e), HttpStatus.NOT_FOUND); // I think this is not needed becuase empty is still a valid response for search
        }
        return new ResponseEntity<>(new DataResponse(result, "Loan"), HttpStatus.OK);
    }

    @Operation(summary = "Get loan by user id", description = "Get loan by user id")
    @GetMapping("/byUserId/{userId}")
    public ResponseEntity<DataResponse> getLoanByUserID(@PathVariable("userId") Integer userId){
        List<Loan> loans = loanService.getLoanByUserID(userId);
        if (loans.isEmpty()){
            return new ResponseEntity<>(new DataResponse(loans, "Loan"), HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(new DataResponse(loans, "Loan"), HttpStatus.OK);
    }

    @Operation(summary = "Get loan by pass id", description = "Get loan by pass id")
    @GetMapping("/byPassId/{passId}")
    public ResponseEntity<DataResponse> getLoanByPassID(@PathVariable("passId") Integer passId){
        List<Loan> loans = loanService.getLoanByPassID(passId);
        if (loans.isEmpty()){
            return new ResponseEntity<>(new DataResponse(loans, "Loan"), HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(new DataResponse(loans, "Loan"), HttpStatus.OK);
    }
    
    @Operation(summary = "Create loans for users", description = "User loan creation (w/ 2x loan/month, 2x booking/loan validations)")
    @PostMapping("/userCreateLoan")
    public ResponseEntity<DataResponse> userCreateLoan(@RequestBody LoanRequest createLoanRequest){
        Loan newLoan = null; 
        try {
            newLoan = loanService.userCreateLoan(createLoanRequest);           
        } catch (ResourceNotFoundException e) {
            return new ResponseEntity<>(new DataResponse(newLoan, e), HttpStatus.NOT_FOUND);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(new DataResponse(newLoan, e), HttpStatus.NOT_ACCEPTABLE);
        } catch (Exception e) {
            return new ResponseEntity<>(new DataResponse(newLoan, e), HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(new DataResponse(newLoan, "Loan"), HttpStatus.CREATED);
    }

    @Operation(summary = "Create loans for admins", description = "Admin loan creation (w/o 2x loan/month, 2x booking/loan validations)")
    @PostMapping("/adminCreateLoan")
    public ResponseEntity<DataResponse> admingCreateLoan(@RequestBody LoanRequest createLoanRequest){
        Loan newLoan = null; 
        try {
            newLoan = loanService.adminCreateLoan(createLoanRequest);           
        } catch (ResourceNotFoundException e) {
            return new ResponseEntity<>(new DataResponse(newLoan, e), HttpStatus.NOT_FOUND);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(new DataResponse(newLoan, e), HttpStatus.NOT_ACCEPTABLE);
        } catch (Exception e) {
            return new ResponseEntity<>(new DataResponse(newLoan, e), HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(new DataResponse(newLoan, "Loan"), HttpStatus.CREATED);
    }

    @Operation(summary = "Update loan", description = "Update loan to completed. This means the user has returned the pass to the office")
    @PutMapping("/updateCompleted")
    public ResponseEntity<DataResponse> updateLoanToCompleted(@RequestBody UpdateLoantoCompletedRequest updateLoanRequest){
        Loan updatedLoan = null;
        try{
            updatedLoan = loanService.updateLoanToCompleted(updateLoanRequest);
            return new ResponseEntity<>(new DataResponse(updatedLoan, "Loan"), HttpStatus.OK);
        } catch (Exception e){
            return new ResponseEntity<>(new DataResponse(updatedLoan, e), HttpStatus.NOT_FOUND);
        }
    }

    @Operation(summary = "Update loan", description = "Updates a loan based on the specified parameters")
    @PutMapping("/update")
    public ResponseEntity<DataResponse> updateLoan(@RequestBody UpdateLoanRequest updateLoanRequest){
        Loan updatedLoan = null;
        try{
            updatedLoan = loanService.updateLoan(updateLoanRequest);
            return new ResponseEntity<>(new DataResponse(updatedLoan, "Loan"), HttpStatus.OK);
        } catch (Exception e){
            return new ResponseEntity<>(new DataResponse(updatedLoan, e), HttpStatus.NOT_FOUND);
        }
    }

    @Operation(summary = "Delete loan", description = "Delete loan by id")
    @DeleteMapping("/{loanId}")
    public ResponseEntity<DataResponse> deleteLoan(@PathVariable("loanId")Integer loanId){
        Loan deletedLoan = null;
        try{
            deletedLoan = loanService.deleteLoan(loanId);
        } catch (Exception e){
            return new ResponseEntity<>(new DataResponse(deletedLoan, e) ,HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(new DataResponse(deletedLoan, "Loan"), HttpStatus.OK);
    }

    @Operation(summary = "Get loan by pass and date", description = "Get all loans for a pass that were made before the specified date")
    @GetMapping("/forPassByDateBefore/{queryDate}/{passId}")
    public ResponseEntity<DataResponse> getLoanForPassByDateBefore(
        @PathVariable("queryDate") String queryDate,
        @PathVariable("passId") Integer passId) {
        Optional<Loan> loan = loanService.getLoanForPassByDateBefore(LocalDate.parse(queryDate), passId);
        if (loan.isEmpty()) {
            return new ResponseEntity<>(new DataResponse(
                loan.orElse(null), 
                new ResourceNotFoundException("Loan", "queryDate", queryDate)), HttpStatus.NOT_FOUND
            );
        }
        return new ResponseEntity<>(new DataResponse(loan.get(), "Loan"), HttpStatus.OK);
    }

    @Operation(summary = "Get loans by month", description = "Gets all loans made in the specified month")
    @GetMapping("/getAllLoansGroupedByMonth")
    public ResponseEntity<DataResponse> getAllLoansGroupedByMonth() {
        Map<YearMonth, List<Loan>> results = null;

        try {
            results = loanService.getAllLoansGroupedByMonth();
        } catch (ResourceNotFoundException e) {
            return new ResponseEntity<DataResponse>(new DataResponse(results, e), HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<DataResponse>(new DataResponse(results, e), HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return new ResponseEntity<>(new DataResponse(results, "Loan"), HttpStatus.OK);
    }

    @Operation(summary = "Get number of loans grouped by month", description = "Gets number of loans made in the specified month")
    @GetMapping("/getNumLoansGroupedByMonth")
    public ResponseEntity<DataResponse> getNumLoansGroupedByMonth() {
        Map<YearMonth, Integer> results = null;

        try {
            results = loanService.getNumLoansGroupedByMonth();
        } catch (ResourceNotFoundException e) {
            return new ResponseEntity<DataResponse>(new DataResponse(results, e), HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<DataResponse>(new DataResponse(results, e), HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return new ResponseEntity<>(new DataResponse(results, "Loan"), HttpStatus.OK);
    }

    @Operation(summary = "Get number of loans grouped by Month and UserID", description = "Gets number of loans made by the specified user in the specified month")
    @GetMapping("/getNumLoansGroupedeByMonthByUserId/{userId}")
    public ResponseEntity<DataResponse> getNumLoansGroupedeByMonthByUserId(@PathVariable("userId") Integer userId) {
        Map<YearMonth, Integer> results = null;

        try {
            results = loanService.getNumLoansGroupedByMonthByUserId(userId);
        } catch (ResourceNotFoundException e) {
            return new ResponseEntity<DataResponse>(new DataResponse(results, e), HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<DataResponse>(new DataResponse(results, e), HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return new ResponseEntity<>(new DataResponse(results, "Loan"), HttpStatus.OK);
    }
}


