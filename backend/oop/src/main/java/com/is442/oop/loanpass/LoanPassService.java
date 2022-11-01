package com.is442.oop.loanpass;

import java.util.*;

import org.springframework.stereotype.Component;

import com.is442.oop.data.models.Loan;
import com.is442.oop.exception.ResourceNotFoundException;


@Component
public interface LoanPassService {
    List<Loan> getLoanByPoi(String poi) throws ResourceNotFoundException;
}
