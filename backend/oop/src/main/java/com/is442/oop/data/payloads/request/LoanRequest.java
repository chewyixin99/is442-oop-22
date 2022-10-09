package com.is442.oop.data.payloads.request;

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
    private Date startDate;
    private Date endDate;

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

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }




}
