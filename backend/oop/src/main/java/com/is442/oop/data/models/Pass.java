package com.is442.oop.data.models;

import javax.persistence.*;

@Entity
public class Pass {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    private String passName;
    private String passDesc;
    private int numberOfGuests;
    private double replacementFee;
    private boolean isPhysical;
    @Enumerated(EnumType.STRING)
    private PassStatus passStatus;
    private boolean defunct;

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    public String getPassName() {
        return passName;
    }
    public void setPassName(String passName) {
        this.passName = passName;
    }

    public String getPassDesc() {
        return passDesc;
    }
    public void setPassDesc(String passDesc) {
        this.passDesc = passDesc;
    }

    public int getNumberOfGuests() {
        return numberOfGuests;
    }
    public void setNumberOfGuests(int numberOfGuests) {
        this.numberOfGuests = numberOfGuests;
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
        return "Pass [id=" + id + ", passName=" + passName + ", passDesc=" + passDesc + ", numberOfGuests="
                + numberOfGuests + ", replacementFee=" + replacementFee + ", isPhysical=" + isPhysical + ", passStatus=" + passStatus
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
        return pass.getId() == id;    
    }
}
