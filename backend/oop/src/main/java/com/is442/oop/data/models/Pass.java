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

    @Column(name = "pass_number", nullable = false)
    private Integer passNumber;

    @Column(name = "pass_desc", nullable = true)
    private String passDesc;

    @Column(name = "poi", nullable = false)
    private String poi;

    @Column(name = "poi_url", nullable = true)
    private String poiUrl;

    @Column(name = "num_guests", nullable = false)
    private Integer numGuests;

    @Column(name = "replacement_fee", nullable = false)
    private Double replacementFee;

    @Column(name = "is_physical", nullable = false)
    private Boolean isPhysical;

    @Column(name = "pass_status", nullable = false)
    @Enumerated(EnumType.STRING)
    private PassStatus passStatus;

    @Column(name = "pass_attachment_name", nullable = true)
    private String passAttachmentName;

    @Lob
    @Column(name = "pass_attachment", nullable = true)
    private byte[] passAttachment;

    @Lob
    @Column(name = "pass_image", nullable = true)
    private byte[] passImage;

    @Column(name = "defunct", nullable = false)
    @Value("false")
    private boolean defunct;
}
