package com.is442.oop.loan;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class UpdateLoanRequest {

    @NotBlank
    @NotNull
    private Integer loanId;

    @NotBlank
    @NotNull
    private Integer gopId;


    public Integer getLoanId() {
        return loanId;
    }

    public void setLoanId(Integer loanId) {
        this.loanId = loanId;
    }

    public Integer getGopId() {
        return gopId;
    }

    public void setGopId(Integer gopId) {
        this.gopId = gopId;
    }




}
