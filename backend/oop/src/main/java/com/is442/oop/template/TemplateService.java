package com.is442.oop.template;

import java.util.*;

import com.is442.oop.data.models.Template;
import com.is442.oop.exception.ResourceNotFoundException;

public interface TemplateService {
    Template getTemplate(Integer templateId) throws ResourceNotFoundException;
    List<Template> getAllTemplates();
    Template createTemplate(TemplateRequest createTemplateRequest);
    Template updateTemplate(Integer templateId, TemplateRequest updateTemplateRequest) throws ResourceNotFoundException;
    Template deleteTemplate(Integer templateId) throws ResourceNotFoundException;
}
