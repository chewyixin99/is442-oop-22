package com.is442.oop.data.models;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.sql.*;
import javax.persistence.*;

@Entity
public class Loan {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int loanId;
    // @ManyToOne
    // private User user;
    private int userId;
    // @ManyToOne
    // private User gop;
    private int gopId;
    // @ManyToOne
    // private Pass pass;
    private int passId;
    private String startDate;
    private String endDate;

    private boolean isCompleted;


    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }


    public int getId() {
        return loanId;
    }
    public void setId(int loanId) {
        this.loanId = loanId;
    }
    
    public int getUserId() {
        return userId;
    }
    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getGopId() {
        return gopId;
    }
    public void setGopId(int gopId) {
        this.gopId = gopId;
    }

    public int getPassId() {
        return passId;
    }
    public void setPassId(int passId) {
        this.passId = passId;
    }


    public boolean isCompleted() {
        return isCompleted;
    }
    public void setCompleted(boolean isCompleted) {
        this.isCompleted = isCompleted;
    }
    
    @Override
    public String toString() {
        return "Loan [loanId=" + loanId + ", userId=" + userId + ", gopId=" + gopId + ", passId=" + passId + ", startDate=" + startDate
                + ", endDate=" + endDate + ", isCompleted=" + isCompleted + "]";
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        };
        
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        };

        Loan loan = (Loan) obj;
        return loan.getId() == loanId;
    }
}

