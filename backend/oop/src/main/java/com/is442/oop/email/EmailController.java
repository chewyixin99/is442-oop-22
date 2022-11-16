package com.is442.oop.email;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.is442.oop.data.models.Loan;
import com.is442.oop.data.payloads.response.DataResponse;
import com.is442.oop.loan.LoanService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;

/**
 * EmailController to test EmailService independently
 */
@RestController
@RequestMapping("/emails")
public class EmailController {
    @Autowired
    LoanService loanService;

    @Autowired
    EmailService emailService;

    @Operation(summary="Send email to user", description="Sends email to user. This function is used for testing only", responses={
        @ApiResponse(responseCode = "200", description = "OK", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Loan.class))),
        @ApiResponse(responseCode = "400", description = "Bad Request" ,content = @Content)
    })
    @GetMapping("/sendLoanConfirmationEmail/{loanId}")
    public ResponseEntity<DataResponse> sendLoanConfirmationEmail(@PathVariable("loanId") Integer loanId) {
        try {
            Loan loan = loanService.getLoanByLoanID(loanId);
            int templateId = loan.getPass().getIsPhysical() ? 4 : 3;
            emailService.sendLoanConfirmationEmail(loan, templateId);
        } catch (Exception e) {
            return new ResponseEntity<>(new DataResponse(null, e), HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(new DataResponse(null, "Email"), HttpStatus.OK);
    }
}
