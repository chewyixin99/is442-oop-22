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
import com.is442.oop.data.payloads.dto.AnalyticsPoiBreakdownDTO;
import com.is442.oop.data.payloads.response.DataResponse;
import com.is442.oop.exception.ResourceNotFoundException;

import io.swagger.v3.oas.annotations.Operation;

@RestController
@RequestMapping("/analytics")
public class AnalyticsController {
    @Autowired
    AnalyticsService analyticsService;

    @Operation(summary="Gets all loans placed for the month", description="Gets all loans placed by month")
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

    @Operation(summary="Gets all loans placed by user ID", description="Gets all loans placed by user ID")
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

    @Operation(summary="Gets all loans placed by place of interest by month", description="Gets all loans placed by place of interest by month")
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

    @Operation(summary="Gets all pass breakdown", description="Obtains the loan breakdown by status. e.g. Available Onloan Unreturned")
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

    @Operation(summary="Gets all place of interest breakdown", description="Obtains the breakdown of number of loans placed by place of interest")
    @GetMapping("/getPoiBreakdown")
    public ResponseEntity<DataResponse> getPoiBreakdown() {
        List<AnalyticsPoiBreakdownDTO> poiBreakdown = null;
        try {
            poiBreakdown = analyticsService.getPoiBreakdown();
        } catch (ResourceNotFoundException e) {
            return new ResponseEntity<>(new DataResponse(poiBreakdown, e), HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(new DataResponse(poiBreakdown, e), HttpStatus.INTERNAL_SERVER_ERROR);
        }
        
        return new ResponseEntity<>(new DataResponse(poiBreakdown, "AnalyticsService"), HttpStatus.OK);
    }
}
