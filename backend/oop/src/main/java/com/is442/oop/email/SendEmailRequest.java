package com.is442.oop.email;

import javax.validation.constraints.*;

public class SendEmailRequest {
    @NotBlank
    @NotNull
    private Integer loanId;
    
    public Integer getLoanId() {
        return loanId;
    }

    public void setLoanId(Integer loanId) {
        this.loanId = loanId;
    }
}
