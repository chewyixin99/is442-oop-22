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
public class Pass {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "pass_id", nullable = false)
    private Integer passId;

    @Column(name = "pass_desc", nullable = true)
    private String passDesc;

    @Column(name = "poi", nullable = false)
    private String poi;

    @Column(name = "num_guests", nullable = false)
    private Integer numGuests;

    @Column(name = "replacement_fee", nullable = false)
    private double replacementFee;

    @Column(name = "is_physical", nullable = false)
    private boolean isPhysical;

    @Column(name = "pass_status", nullable = false)
    @Enumerated(EnumType.STRING)
    private PassStatus passStatus;

    @Column(name = "defunct", nullable = false)
    @Value("false")
    private boolean defunct;
}
