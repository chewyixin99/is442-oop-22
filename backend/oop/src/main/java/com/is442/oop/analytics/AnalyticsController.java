package com.is442.oop.analytics;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.is442.oop.data.models.Loan;
import com.is442.oop.data.payloads.dto.AnalyticsPassBreakdownDTO;
import com.is442.oop.data.payloads.response.DataResponse;

@RestController
@RequestMapping("/analytics")
public class AnalyticsController {
    @Autowired
    AnalyticsService analyticsService;

    @GetMapping("/getLoansForMonth/{month}")
    public ResponseEntity<DataResponse> getLoansForMonth(@PathVariable("month") Integer month ){
        List<Loan> loans = null;
        try {
            loans = analyticsService.getLoansForMonth(month);
        } catch (Exception e) {
            return new ResponseEntity<>(new DataResponse(loans, e), HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(new DataResponse(loans, "AnalyticsService"), HttpStatus.OK);
    }

    @GetMapping("/getLoansPerUserId/{userId}")
    public ResponseEntity<DataResponse> getLoanByUserID(@PathVariable("userId") Integer userId) {
        List<Loan> loans = null;
        try {
            loans = analyticsService.getLoansByUserID(userId);
        } catch (Exception e) {
            return new ResponseEntity<>(new DataResponse(loans, e), HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(new DataResponse(loans, "AnalyticsService"), HttpStatus.OK);
    }

    @GetMapping("/getLoansPerUserPerMonth/{userId}/{month}")
    public ResponseEntity<DataResponse> getLoansPerUserPerMonth(@PathVariable Integer userId, @PathVariable Integer month) {
        List<Loan> loans = null;
        try {
            loans = analyticsService.getLoansPerUserPerMonth(userId, month);
        } catch (Exception e) {
            return new ResponseEntity<>(new DataResponse(loans, e), HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(new DataResponse(loans, "AnalyticsService"), HttpStatus.OK);
    }

    @GetMapping("/getPassBreakdown")
    public ResponseEntity<DataResponse> getPassBreakdown() {
        AnalyticsPassBreakdownDTO passBreakdown = null;
        try {
            passBreakdown = analyticsService.getPassBreakdown();
        } catch (Exception e) {
            return new ResponseEntity<>(new DataResponse(passBreakdown, e), HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(new DataResponse(passBreakdown, "AnalyticsService"), HttpStatus.OK);
    }
}
