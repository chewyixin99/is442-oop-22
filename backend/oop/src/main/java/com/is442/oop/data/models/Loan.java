package com.is442.oop.data.models;

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
    private Date startDate;
    private Date endDate;
    private boolean isCompleted;
    
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
    
    public Date getStartDate() {
        return startDate;
    }
    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }
    
    public Date getEndDate() {
        return endDate;
    }
    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public boolean isCompleted() {
        return isCompleted;
    }
    public void setCompleted(boolean isCompleted) {
        this.isCompleted = isCompleted;
    }
    
    @Override
    public String toString() {
        return "Loan [loanId=" + loanId + ", userId" + userId + ", gopId=" + gopId + ", passId=" + passId + ", startDate=" + startDate
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

