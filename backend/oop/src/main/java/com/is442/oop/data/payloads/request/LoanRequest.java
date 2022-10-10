package com.is442.oop.data.payloads.request;

import com.fasterxml.jackson.annotation.JsonFormat;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Date;


public class LoanRequest {

    @NotBlank
    @NotNull
    private Integer loanID;
    @NotBlank
    @NotNull
    private Integer userID;
    @NotBlank
    @NotNull
    private Integer passID;

    private Integer gopID;
    private Boolean isCompleted;
    private String startDate;
    private String endDate;

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public Integer getLoanID() {
        return loanID;
    }

    public void setLoanID(Integer loanID) {
        this.loanID = loanID;
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

    public Integer getGopID() {
        return gopID;
    }

    public void setGopID(Integer gopID) {
        this.gopID = gopID;
    }

    public Boolean getCompleted() {
        return isCompleted;
    }

    public void setCompleted(Boolean completed) {
        isCompleted = completed;
    }
}
