package com.is442.oop.data.payloads.request;

import javax.persistence.*;
import javax.validation.constraints.*;

import com.is442.oop.data.models.*;

public class PassRequest {
    @NotBlank
    @NotNull
    private String passDesc;
    @NotBlank
    @NotNull
    private String poi;
    @NotBlank
    @NotNull
    private int numGuests;
    @NotBlank
    @NotNull
    private double replacementFee;
    @NotBlank
    @NotNull
    private boolean isPhysical;
    @NotBlank
    @NotNull
    @Enumerated(EnumType.STRING)
    private PassStatus passStatus;
    @NotBlank
    @NotNull
    private boolean defunct;

    public String getPassDesc() {
        return passDesc;
    }
    public void setPassDesc(String passDesc) {
        this.passDesc = passDesc;
    }
    public String getPoi() {
        return poi;
    }
    public void setPoi(String poi) {
        this.poi = poi;
    }
    public int getNumGuests() {
        return numGuests;
    }
    public void setNumGuests(int numGuests) {
        this.numGuests = numGuests;
    }
    public double getReplacementFee() {
        return replacementFee;
    }
    public void setReplacementFee(double replacementFee) {
        this.replacementFee = replacementFee;
    }
    public boolean getPhysical() {
        return isPhysical;
    }
    public void setPhysical(boolean isPhysical) {
        this.isPhysical = isPhysical;
    }
    public PassStatus getPassStatus() {
        return passStatus;
    }
    public void setPassStatus(PassStatus passStatus) {
        this.passStatus = passStatus;
    }
    public boolean getDefunct() {
        return defunct;
    }
    public void setDefunct(boolean defunct) {
        this.defunct = defunct;
    }
}
