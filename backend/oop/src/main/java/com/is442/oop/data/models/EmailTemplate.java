package com.is442.oop.data.models;

import javax.persistence.*;

@Entity
public class EmailTemplate {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int templateId;
    private String templateData;
    private boolean defunct;
    
    public int getId() {
        return templateId;
    }
    public void setId(int templateId) {
        this.templateId = templateId;
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
