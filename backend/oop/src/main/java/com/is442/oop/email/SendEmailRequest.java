package com.is442.oop.email;

import javax.validation.constraints.*;

public class SendEmailRequest {
    @NotBlank
    @NotNull
    private Integer loanId;

    @NotBlank
    @NotNull
    private Integer templateId;

    public Integer getLoanId() {
        return loanId;
    }

    public void setLoanId(Integer loanId) {
        this.loanId = loanId;
    }

    public Integer getTemplateId() {
        return templateId;
    }

    public void setTemplateId(Integer templateId) {
        this.templateId = templateId;
    }
}
