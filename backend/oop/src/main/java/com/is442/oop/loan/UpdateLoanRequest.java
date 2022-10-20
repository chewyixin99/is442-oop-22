package com.is442.oop.loan;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class UpdateLoanRequest {

    @NotBlank
    @NotNull
    private Integer loanId;

    // @NotBlank
    // @NotNull
    private Integer gopId;

    // @NotBlank
    // @NotNull
    private Integer userId;

    // @NotBlank
    // @NotNull
    private Integer passId;

    // @NotBlank
    // @NotNull
    private boolean isCompleted;

    // @NotBlank
    // @NotNull
    private String startDate;

    // @NotBlank
    // @NotNull
    private String endDate;

    @Override
    public String toString() {
        return "{" +
            " loanId='" + getLoanId() + "'" +
            ", gopId='" + getGopId() + "'" +
            ", userId='" + getUserId() + "'" +
            ", passId='" + getPassId() + "'" +
            ", isCompleted='" + isIsCompleted() + "'" +
            ", startDate='" + getStartDate() + "'" +
            ", endDate='" + getEndDate() + "'" +
            "}";
    }

    public Integer getLoanId() {
        return this.loanId;
    }

    public void setLoanId(Integer loanId) {
        this.loanId = loanId;
    }

    public Integer getGopId() {
        return this.gopId;
    }

    public void setGopId(Integer gopId) {
        this.gopId = gopId;
    }

    public Integer getUserId() {
        System.out.println(this.userId);
        return this.userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getPassId() {
        return this.passId;
    }

    public void setPassId(Integer passId) {
        this.passId = passId;
    }

    public boolean isIsCompleted() {
        return this.isCompleted;
    }

    public boolean getIsCompleted() {
        return this.isCompleted;
    }

    public void setIsCompleted(boolean isCompleted) {
        this.isCompleted = isCompleted;
    }

    public String getStartDate() {
        return this.startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return this.endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }



}
