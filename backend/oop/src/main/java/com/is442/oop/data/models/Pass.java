package com.is442.oop.data.models;

import javax.persistence.*;

@Entity
public class Pass {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int passId;
    private String passDesc;
    private String poi;
    private int numGuests;
    private double replacementFee;
    private boolean isPhysical;
    @Enumerated(EnumType.STRING)
    private PassStatus passStatus;
    private boolean defunct;

    public int getId() {
        return passId;
    }
    public void setId(int passId) {
        this.passId = passId;
    }

    public String getPoi() {
        return poi;
    }
    public void setPoi(String poi) {
        this.poi = poi;
    }

    public String getPassDesc() {
        return passDesc;
    }
    public void setPassDesc(String passDesc) {
        this.passDesc = passDesc;
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

    public boolean isPhysical() {
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
    
    @Override
    public String toString() {
        return "Pass [passId=" + passId + ", poi=" + poi + ", passDesc=" + passDesc + ", numGuests="
                + numGuests + ", replacementFee=" + replacementFee + ", isPhysical=" + isPhysical + ", passStatus=" + passStatus
                + ", defunct=" + defunct + "]";
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        };
        
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        };

        Pass pass = (Pass) obj;
        return pass.getId() == passId;    
    }
}
