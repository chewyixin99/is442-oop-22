package com.is442.oop.loan;

import com.is442.oop.data.models.Loan;
import com.is442.oop.data.models.User;
import com.is442.oop.data.payloads.dto.LoanWithPrevUserDTO;

public class LoanUtil {
    public static LoanWithPrevUserDTO toPrevUserDTO(Loan loan, User prevUser) {
        LoanWithPrevUserDTO result = new LoanWithPrevUserDTO(loan, prevUser);
        return result;
    }
}
