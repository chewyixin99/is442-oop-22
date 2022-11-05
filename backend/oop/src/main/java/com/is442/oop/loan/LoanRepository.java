package com.is442.oop.loan;
import com.is442.oop.data.models.Loan;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface LoanRepository extends JpaRepository<Loan, Integer> {
    @Query(value = "select * from Loan l where l.end_date <= CURRENT_DATE and not l.is_completed and not l.defunct", nativeQuery = true)
    List<Loan> getAllExpiredLoans();
    
    @Query(value = "select * from loan l where l.start_date < :queryDate and pass_id = :passId order by start_date desc limit 1", nativeQuery = true)
    Optional<Loan> getLoanForPassByDate(
        @Param("queryDate") LocalDate queryDate,
        @Param("passId") Integer passId);
}
