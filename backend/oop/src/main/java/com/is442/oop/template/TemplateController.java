package com.is442.oop.template;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.is442.oop.data.models.Template;
import com.is442.oop.data.payloads.response.DataResponse;

import java.util.List;

@RestController
@RequestMapping("/templates")
public class TemplateController {
    @Autowired
    TemplateService templateService;

    @GetMapping("")
    public ResponseEntity<DataResponse> getAllTemplates() {
        List<Template> templates = templateService.getAllTemplates();
        return new ResponseEntity<>(new DataResponse(templates, "Template"), HttpStatus.OK);
    }

    @GetMapping("/{templateId}")
    public ResponseEntity<DataResponse> getTemplate(@PathVariable("templateId") Integer templateId) {
        Template template = null;
        try {
            template = templateService.getTemplate(templateId);
        } catch (Exception e) {
            return new ResponseEntity<>(new DataResponse(template, e), HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(new DataResponse(template, "Template"), HttpStatus.OK);
    }

    @PostMapping("")
    public ResponseEntity<DataResponse> createTemplate(@RequestBody TemplateRequest templateRequest) {
        Template newTemplate = templateService.createTemplate(templateRequest);
        return new ResponseEntity<>(new DataResponse(newTemplate, "Template"), HttpStatus.CREATED);
    }

    @PutMapping("/{templateId}")
    public ResponseEntity<DataResponse> updateTemplate(@PathVariable("templateId") Integer templateId, @RequestBody TemplateRequest templateRequest) {
        Template updateTemplate = null;
        try {
            updateTemplate = templateService.updateTemplate(templateId, templateRequest);
        } catch (Exception e) {
            return new ResponseEntity<>(new DataResponse(updateTemplate, e), HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(new DataResponse(updateTemplate, "Template"), HttpStatus.OK);
    }

    @DeleteMapping("/{templateId}")
    public ResponseEntity<DataResponse> deleteTemplate(@PathVariable("templateId") Integer templateId) {
        Template template = null;
        try {
            template = templateService.deleteTemplate(templateId);
        } catch (Exception e) {
            return new ResponseEntity<>(new DataResponse(template, e), HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(new DataResponse(template, "Template"), HttpStatus.OK);
    }
}
