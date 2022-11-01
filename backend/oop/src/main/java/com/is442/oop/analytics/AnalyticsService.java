package com.is442.oop.analytics;

import java.util.List;

import org.springframework.stereotype.Component;

import com.is442.oop.data.models.Loan;
import com.is442.oop.data.payloads.dto.AnalyticsPoiBreakdownDTO;
import com.is442.oop.data.payloads.dto.AnalyticsPassBreakdownDTO;
import com.is442.oop.exception.ActionNotExecutedException;

@Component
public interface AnalyticsService {
    List<Loan> getLoansForMonth(Integer month) throws ActionNotExecutedException;
    List<Loan> getLoansByUserID(Integer userId) throws ActionNotExecutedException;
    List<Loan> getLoansPerUserPerMonth(Integer userId, Integer month) throws ActionNotExecutedException;
    AnalyticsPassBreakdownDTO getPassBreakdown() throws ActionNotExecutedException;
    List<AnalyticsPoiBreakdownDTO> getPoiBreakdown() throws ActionNotExecutedException;
}
