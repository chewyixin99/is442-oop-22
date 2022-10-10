package com.is442.oop.daos;

import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.is442.oop.data.models.EmailTemplate;
import com.is442.oop.data.payloads.request.TemplateRequest;
import com.is442.oop.data.payloads.response.MessageResponse;
import com.is442.oop.data.repository.TemplateRepository;
import com.is442.oop.exceptions.ResourceNotFoundException;

@Service
public class EmailTemplateDAO implements EmailTemplateDAOInt {
    @Autowired
    TemplateRepository templateRepository;
    
    @Override
    public EmailTemplate getTemplate(Integer templateId) throws ResourceNotFoundException {
        return templateRepository.findById(templateId).orElseThrow(() -> new ResourceNotFoundException("Resource not found."));
    }

    @Override
    public List<EmailTemplate> getAllTemplates() {
        return templateRepository.findAll();
    }

    @Override
    public MessageResponse createTemplate(TemplateRequest createTemplateRequest) {
        EmailTemplate emailTemplate = new EmailTemplate();
        emailTemplate.setTemplateName(createTemplateRequest.getTemplateName());
        emailTemplate.setTemplateData(createTemplateRequest.getTemplateData());
        emailTemplate.setDefunct(createTemplateRequest.getDefunct());
        templateRepository.save(emailTemplate);
        return new MessageResponse(String.format("Email template created, ID: %d.", emailTemplate.getId()));
    }

    @Override
    public EmailTemplate updateTemplate(Integer templateId, TemplateRequest createTemplateRequest) throws ResourceNotFoundException {
        EmailTemplate emailTemplate = null;
        Optional<EmailTemplate> queryTemplate = templateRepository.findById(templateId);
        if (queryTemplate.isEmpty()) {
            throw new ResourceNotFoundException("Resource not found.");
        }

        emailTemplate = queryTemplate.get();
        emailTemplate.setTemplateName(createTemplateRequest.getTemplateName());
        emailTemplate.setTemplateData(createTemplateRequest.getTemplateData());
        templateRepository.save(emailTemplate);
        return emailTemplate;
    }

    @Override
    public void deleteTemplate(Integer templateId) throws ResourceNotFoundException {
        EmailTemplate emailTemplate = null;
        Optional<EmailTemplate> queryTemplate = templateRepository.findById(templateId);
        if (queryTemplate.isEmpty()) {
            throw new ResourceNotFoundException("Resource not found.");
        }
        emailTemplate = queryTemplate.get();
        emailTemplate.setDefunct(true);
        templateRepository.save(emailTemplate);
    }
}
