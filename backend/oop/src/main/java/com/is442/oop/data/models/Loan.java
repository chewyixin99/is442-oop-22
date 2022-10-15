package com.is442.oop.data.models;

import javax.persistence.*;

import org.springframework.beans.factory.annotation.Value;

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

