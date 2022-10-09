package com.is442.oop.data.payloads.request;

import javax.validation.constraints.*;

public class TemplateRequest {
    @NotBlank
    @NotNull
    private String templateName;
    @NotBlank
    @NotNull
    private String templateData;
    @NotBlank
    @NotNull
    private boolean defunct;
    
    public String getTemplateName() {
        return templateName;
    }
    public void setTemplateName(String templateName) {
        this.templateName = templateName;
    }

    public String getTemplateData() {
        return templateData;
    }
    public void setTemplateData(String templateData) {
        this.templateData = templateData;
    }

    public boolean getDefunct() {
        return defunct;
    }
    public void setDefunct(boolean defunct) {
        this.defunct = defunct;
    }    
}
