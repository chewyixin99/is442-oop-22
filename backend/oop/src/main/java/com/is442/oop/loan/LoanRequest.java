package com.is442.oop.loan;

import com.fasterxml.jackson.annotation.JsonFormat;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Date;


public class LoanRequest {

    @NotBlank
    @NotNull
    private Integer userID;
    @NotBlank
    @NotNull
    private Integer passID;

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

}
