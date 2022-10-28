package com.is442.oop.data.payloads.dto;


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
public class AnalyticsPassBreakdownDTO {
    private int available;
    private int onloan;
    private int unreturned;

    public void addAvailable() {
        this.available += 1;
    }

    public void addOnLoan() {
        this.onloan += 1;
    }

    public void addUnreturned() {
        this.unreturned += 1;
    }

}
