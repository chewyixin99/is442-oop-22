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
public class Loan {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "loan_id", nullable = false)
    private int loanId;
    // @ManyToOne
    // private User user;
    @Column(name = "user_id", nullable = false)
    private int userId;
    // @ManyToOne
    // private User gop;
    @Column(name = "gop_id")
    private int gopId;
    // @ManyToOne
    // private Pass pass;
    @Column(name = "pass_id", nullable = false)
    private int passId;

    @Column(name = "start_date", nullable = false)
    private String startDate;

    @Column(name = "end_date", nullable = false)
    private String endDate;

    @Column(name = "is_completed", nullable = false)
    @Value("false") // Default value
    private boolean isCompleted;

    @Column(name = "defunct", nullable = false)
    @Value("false") // Default value
    private boolean defunct;

    @Column(name = "poi", nullable = false)
    private String poi;
}

