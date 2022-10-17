package com.is442.oop.template;

import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.is442.oop.data.models.Template;
import com.is442.oop.data.payloads.response.MessageResponse;
import com.is442.oop.exception.ResourceNotFoundException;

@Service
public class TemplateServiceImpl implements TemplateService {
    @Autowired
    TemplateRepository templateRepository;
    
    @Override
    public Template getTemplate(Integer templateId) throws ResourceNotFoundException {
        return templateRepository.findById(templateId).orElseThrow(() -> new ResourceNotFoundException("Resource not found."));
    }

    @Override
    public List<Template> getAllTemplates() {
        return templateRepository.findAll();
    }

    @Override
    public MessageResponse createTemplate(TemplateRequest createTemplateRequest) {
        Template template = new Template();
        template.setTemplateName(createTemplateRequest.getTemplateName());
        template.setTemplateData(createTemplateRequest.getTemplateData());
        template.setDefunct(createTemplateRequest.getDefunct());
        templateRepository.save(template);
        return new MessageResponse(String.format("Template created, ID: %d.", template.getTemplateId()));
    }

    @Override
    public Template updateTemplate(Integer templateId, TemplateRequest createTemplateRequest) throws ResourceNotFoundException {
        Template template = null;
        Optional<Template> queryTemplate = templateRepository.findById(templateId);
        if (queryTemplate.isEmpty()) {
            throw new ResourceNotFoundException("Resource not found.");
        }

        template = queryTemplate.get();
        template.setTemplateName(createTemplateRequest.getTemplateName());
        template.setTemplateData(createTemplateRequest.getTemplateData());
        templateRepository.save(template);
        return template;
    }

    @Override
    public void deleteTemplate(Integer templateId) throws ResourceNotFoundException {
        Template template = null;
        Optional<Template> queryTemplate = templateRepository.findById(templateId);
        if (queryTemplate.isEmpty()) {
            throw new ResourceNotFoundException("Resource not found.");
        }
        template = queryTemplate.get();
        template.setDefunct(true);
        templateRepository.save(template);
    }
}
