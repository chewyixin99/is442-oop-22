package com.is442.oop.template;

import java.util.*;

import com.is442.oop.data.models.Template;
import com.is442.oop.data.payloads.response.MessageResponse;
import com.is442.oop.exception.ResourceNotFoundException;

public interface TemplateService {
    Template getTemplate(Integer templateId) throws ResourceNotFoundException;
    List<Template> getAllTemplates();
    MessageResponse createTemplate(TemplateRequest createTemplateRequest);
    Template updateTemplate(Integer templateId, TemplateRequest updateTemplateRequest) throws ResourceNotFoundException;
    void deleteTemplate(Integer templateId) throws ResourceNotFoundException;
}
