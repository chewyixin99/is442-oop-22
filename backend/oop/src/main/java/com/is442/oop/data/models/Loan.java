package com.is442.oop.data.models;

import java.sql.*;
import javax.persistence.*;

@Entity
public class Loan {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    @ManyToOne
    private User user;
    @ManyToOne
    private User generalOfficePersonnel;
    @ManyToOne
    private Pass pass;
    private Date startDate;
    private Date endDate;
    private String place;
    private boolean isCompleted;
    
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    
    public User getUser() {
        return user;
    }
    public void setUser(User user) {
        this.user = user;
    }

    public User getGeneralOfficePersonnel() {
        return generalOfficePersonnel;
    }
    public void setGeneralOfficePersonnel(User generalOfficePersonnel) {
        this.generalOfficePersonnel = generalOfficePersonnel;
    }

    public Pass getPass() {
        return pass;
    }
    public void setPass(Pass pass) {
        this.pass = pass;
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

    public String getPlace() {
        return place;
    }
    public void setPlace(String place) {
        this.place = place;
    }

    public boolean isCompleted() {
        return isCompleted;
    }
    public void setCompleted(boolean isCompleted) {
        this.isCompleted = isCompleted;
    }
    
    @Override
    public String toString() {
        return "Loan [id=" + id + ", =user" + user + ", generalOfficePersonnel=" + generalOfficePersonnel + ", pass=" + pass + ", startDate=" + startDate
                + ", endDate=" + endDate + ", place=" + place + ", isCompleted=" + isCompleted + "]";
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
        return loan.getId() == id;
    }
}

