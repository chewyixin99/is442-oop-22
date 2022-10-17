package com.is442.oop.data.models;

import javax.persistence.*;

@Entity
public class Template {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "template_id", nullable = false)
    private Integer templateId;

    @Column(name = "template", nullable = false)
    private String templateName;
    
    @Column(name = "template_data", nullable = true)
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

        Template template = (Template) obj;
        return template.getId() == templateId;
    } 
}
