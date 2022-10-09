package com.is442.oop.data.models;

import javax.persistence.*;

@Entity
public class EmailTemplate {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer templateId;
    private String templateName;
    private String templateData;
    private boolean defunct;
    
    public Integer getId() {
        return templateId;
    }

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

    @Override
    public String toString() {
        return "Template [templateId=" + templateId + ", templateData=" + templateData + ", defunct=" + defunct + "]";
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        };
        
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        };

        EmailTemplate template = (EmailTemplate) obj;
        return template.getId() == templateId;
    } 
}
