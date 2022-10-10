package com.is442.oop;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.is442.oop.daos.EmailTemplateDAOInt;
import com.is442.oop.data.models.EmailTemplate;
import com.is442.oop.data.payloads.request.TemplateRequest;
import com.is442.oop.data.payloads.response.MessageResponse;

import java.util.List;

@RestController
@RequestMapping("/templates")
public class TemplateController {
    @Autowired
    EmailTemplateDAOInt emailTemplateDAOInt;

    @GetMapping("")
    public ResponseEntity<List<EmailTemplate>> getAllTemplates() {
        List<EmailTemplate> emailTemplates = emailTemplateDAOInt.getAllTemplates();
        return new ResponseEntity<>(emailTemplates, HttpStatus.OK);
    }

    @GetMapping("/{templateId}")
    public ResponseEntity<EmailTemplate> getTemplate(@PathVariable("templateId") Integer templateId) {
        EmailTemplate emailTemplate = null;
        try {
            emailTemplate = emailTemplateDAOInt.getTemplate(templateId);
        } catch (Exception e) {
            return new ResponseEntity<>(emailTemplate, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(emailTemplate, HttpStatus.OK);
    }

    @PostMapping("")
    public ResponseEntity<MessageResponse> createTemplate(@RequestBody TemplateRequest templateRequest) {
        MessageResponse newTemplate = emailTemplateDAOInt.createTemplate(templateRequest);
        return new ResponseEntity<>(newTemplate, HttpStatus.CREATED);
    }

    @PutMapping("/{templateId}")
    public ResponseEntity<EmailTemplate> updateTemplate(@PathVariable("templateId") Integer templateId, @RequestBody TemplateRequest templateRequest) {
        EmailTemplate updateTemplate = null;
        try {
            updateTemplate = emailTemplateDAOInt.updateTemplate(templateId, templateRequest);
        } catch (Exception e) {
            return new ResponseEntity<>(updateTemplate, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(updateTemplate, HttpStatus.OK);
    }

    @DeleteMapping("/{templateId}")
    public ResponseEntity<HttpStatus> deleteTemplate(@PathVariable("templateId") Integer templateId) {
        try {
            emailTemplateDAOInt.deleteTemplate(templateId);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
