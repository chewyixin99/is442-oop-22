package com.is442.oop.template;

import javax.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
@Getter // provide default getters
@Setter // provide default setters
@ToString // provide default toString()
@EqualsAndHashCode // provide default equals() and hashCode()
@NoArgsConstructor // no args constructor 
@AllArgsConstructor // all args constructor
public class TemplateRequest {
    @NotBlank
    @NotNull
    private String templateName;
    @NotBlank
    @NotNull
    private String templateData;
    @NotBlank
    @NotNull
    private String templateSubject;
}
