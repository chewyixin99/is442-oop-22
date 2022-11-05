package com.is442.oop.data.payloads.dto;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.is442.oop.data.models.Loan;
import com.is442.oop.data.models.Pass;
import com.is442.oop.data.models.User;


import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
@Getter // provide default getters
@Setter // provide default setters
@ToString // provide default toString()
@EqualsAndHashCode // provide default equals() and hashCode()
@NoArgsConstructor // no args constructor 
@AllArgsConstructor // all args constructor
public class LoanWithPrevUserDTO {
    private int loanId;
    private User user;
    private User prevUser;
    private int gopId;
    private Pass pass;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate startDate;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate endDate;

    private boolean isCompleted;
    private boolean defunct;

    public LoanWithPrevUserDTO(Loan loan, User prevUser) {
        this.loanId = loan.getLoanId();
        this.user = loan.getUser();
        this.gopId = loan.getGopId();
        this.pass = loan.getPass();
        this.startDate = loan.getStartDate();
        this.endDate = loan.getEndDate();
        this.isCompleted = loan.isCompleted();
        this.defunct = loan.isDefunct();
        this.prevUser = prevUser;
    }
}
