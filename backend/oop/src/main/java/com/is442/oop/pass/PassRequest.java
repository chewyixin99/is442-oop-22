package com.is442.oop.pass;

import javax.persistence.*;
import javax.validation.constraints.*;

import org.springframework.web.multipart.MultipartFile;

import com.is442.oop.data.models.*;

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
public class PassRequest {
    @NotBlank
    @NotNull
    private String passDesc;
    
    @NotBlank
    @NotNull
    private String poi;
    
    @NotBlank
    @NotNull
    private Integer passNumber;

    private String poiUrl;
    
    @NotBlank
    @NotNull
    private Integer numGuests;
    
    @NotBlank
    @NotNull
    private Double replacementFee;
    
    @NotBlank
    @NotNull
    private Boolean isPhysical;
    
    @NotBlank
    @NotNull
    @Enumerated(EnumType.STRING)
    private PassStatus passStatus;
    
    private String passAttachmentName;
    
    private MultipartFile passAttachment;

    private MultipartFile passImage;
    
}
