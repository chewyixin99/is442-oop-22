package com.is442.oop.loan;
import com.is442.oop.data.models.Loan;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface LoanRepository extends JpaRepository<Loan, Integer> {
    @Query(value = "select * from Loan l where l.end_date <= CURRENT_DATE and not l.is_completed and not l.defunct", nativeQuery = true)
    List<Loan> getAllExpiredLoans();
}
