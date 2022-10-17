package com.is442.oop.data.models;

import javax.persistence.*;

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
@Entity
public class Template {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "template_id", nullable = false)
    private Integer templateId;

    @Column(name = "template", nullable = false)
    private String templateName;
    
    @Column(name = "template_data", nullable = true)
    private String templateData;
    
    private boolean defunct;
}
