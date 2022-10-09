package com.is442.oop.daos;

import java.util.*;

import com.is442.oop.data.models.EmailTemplate;
import com.is442.oop.data.payloads.request.TemplateRequest;
import com.is442.oop.data.payloads.response.MessageResponse;
import com.is442.oop.exceptions.ResourceNotFoundException;

public interface EmailTemplateDAOInt {
    EmailTemplate getTemplate(Integer templateId) throws ResourceNotFoundException;
    List<EmailTemplate> getAllTemplates();
    MessageResponse createTemplate(TemplateRequest createTemplateRequest);
    EmailTemplate updateTemplate(Integer templateId, TemplateRequest updateTemplateRequest) throws ResourceNotFoundException;
    void deleteTemplate(Integer templateId) throws ResourceNotFoundException;
}
