package com.is442.oop.template;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.is442.oop.data.models.Template;
import com.is442.oop.data.payloads.response.MessageResponse;

import java.util.List;

@RestController
@RequestMapping("/templates")
public class TemplateController {
    @Autowired
    TemplateService templateService;

    @GetMapping("")
    public ResponseEntity<List<Template>> getAllTemplates() {
        List<Template> templates = templateService.getAllTemplates();
        return new ResponseEntity<>(templates, HttpStatus.OK);
    }

    @GetMapping("/{templateId}")
    public ResponseEntity<Template> getTemplate(@PathVariable("templateId") Integer templateId) {
        Template template = null;
        try {
            template = templateService.getTemplate(templateId);
        } catch (Exception e) {
            return new ResponseEntity<>(template, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(template, HttpStatus.OK);
    }

    @PostMapping("")
    public ResponseEntity<MessageResponse> createTemplate(@RequestBody TemplateRequest templateRequest) {
        MessageResponse newTemplate = templateService.createTemplate(templateRequest);
        return new ResponseEntity<>(newTemplate, HttpStatus.CREATED);
    }

    @PutMapping("/{templateId}")
    public ResponseEntity<Template> updateTemplate(@PathVariable("templateId") Integer templateId, @RequestBody TemplateRequest templateRequest) {
        Template updateTemplate = null;
        try {
            updateTemplate = templateService.updateTemplate(templateId, templateRequest);
        } catch (Exception e) {
            return new ResponseEntity<>(updateTemplate, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(updateTemplate, HttpStatus.OK);
    }

    @DeleteMapping("/{templateId}")
    public ResponseEntity<HttpStatus> deleteTemplate(@PathVariable("templateId") Integer templateId) {
        try {
            templateService.deleteTemplate(templateId);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
