package com.is442.oop.template;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.is442.oop.data.models.Template;
import com.is442.oop.data.payloads.response.DataResponse;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;

@RestController
@RequestMapping("/templates")
public class TemplateController {
    @Autowired
    TemplateService templateService;

    @Operation(summary = "Gets all templates", description = "Gets all templates", responses={
        @ApiResponse(responseCode = "200", description = "OK", content=@Content(mediaType = "application/json", schema = @Schema(implementation = Template.class)))
    })
    @GetMapping("")
    public ResponseEntity<DataResponse> getAllTemplates() {
        List<Template> templates = templateService.getAllTemplates();
        return new ResponseEntity<>(new DataResponse(templates, "Template"), HttpStatus.OK);
    }

    @Operation(summary = "Get template by id", description = "Get template by id", responses={
        @ApiResponse(responseCode = "200", description = "OK", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Template.class))),
        @ApiResponse(responseCode = "404", description = "Template not found" ,content = @Content)
    })
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


    @Operation(summary = "Create template", description = "Creates a new template", responses={
        @ApiResponse(responseCode = "201", description = "OK", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Template.class))),
        @ApiResponse(responseCode = "400", description = "Bad request", content = @Content)
    })
    @PostMapping("")
    public ResponseEntity<DataResponse> createTemplate(@RequestBody TemplateRequest templateRequest) {
        Template newTemplate = templateService.createTemplate(templateRequest);
        return new ResponseEntity<>(new DataResponse(newTemplate, "Template"), HttpStatus.CREATED);
    }


    @Operation(summary = "Update template", description = "Updates the template's details", responses={
        @ApiResponse(responseCode = "200", description = "OK", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Template.class))),
        @ApiResponse(responseCode = "400", description = "Bad request", content = @Content),
        @ApiResponse(responseCode = "404", description = "Template not found", content = @Content)
    })
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

    @Operation(summary = "Delete template", description = "Deletes a template", responses={
        @ApiResponse(responseCode = "200", description = "OK", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Template.class))),
        @ApiResponse(responseCode = "404", description = "Template not found", content = @Content)
    })
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
