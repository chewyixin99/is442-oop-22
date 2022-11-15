package com.is442.oop.loan;

import java.time.LocalDate;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class LoanRequest {

    @NotBlank
    @NotNull
    private Integer userID;
    @NotBlank
    @NotNull
    private Integer passID;

    private LocalDate startDate;
    private LocalDate endDate;

    private Integer secondaryPassID;

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public Integer getUserID() {
        return userID;
    }

    public void setUserID(Integer userID) {
        this.userID = userID;
    }

    public Integer getPassID() {
        return passID;
    }

    public void setPassID(Integer passID) {
        this.passID = passID;
    }

    public Integer getSecondaryPassID() {
        return secondaryPassID;
    }

    public void setSecondaryPassID(Integer secondaryPassID) {
        this.secondaryPassID = secondaryPassID;
    }
}
