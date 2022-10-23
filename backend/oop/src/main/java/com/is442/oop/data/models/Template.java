package com.is442.oop.data.models;

import javax.persistence.*;

import org.springframework.beans.factory.annotation.Value;

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

    @Column(name = "template_name", nullable = false)
    private String templateName;

    @Column(name = "template_subject", nullable = false)
    private String templateSubject;
    
    @Column(name = "template_data", nullable = true)
    private String templateData;
    
    @Column(name = "defunct", nullable = false)
    @Value("false")
    private boolean defunct;
}
