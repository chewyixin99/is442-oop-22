package com.is442.oop.scheduled;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.is442.oop.data.models.Pass;
import com.is442.oop.data.payloads.response.DataResponse;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;


@RestController
@RequestMapping("/scheduled")
public class ScheduledController {
    @Autowired
    ScheduledService scheduledService;

    @Operation(summary="Updates passes to unreturned", description="At the end of the day, if the passes have not been returned to the office, the function will run and update the status of the pass to unreturned. Used for testing purposes.", responses={
        @ApiResponse(responseCode = "200", description = "OK", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Pass.class))),
        @ApiResponse(responseCode = "500", description = "Internal Server error" ,content = @Content)
    })
    @GetMapping("/updatePassesToUnreturned")
    public ResponseEntity<DataResponse> updatePassesToUnreturned() {
        List<Pass> unreturnedPasses = null;
        try {
            unreturnedPasses = scheduledService.updatePassesToUnreturned();
        } catch (Exception e) {
            return new ResponseEntity<>(new DataResponse(unreturnedPasses, e), HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(new DataResponse(unreturnedPasses, "ScheduledService"), HttpStatus.OK);
    }

    // Following function runs everyday at midnight
    @Scheduled(cron="0 0 0 * * *")
    // @Scheduled(fixedRate= 1000L)
    void emailCollectPass() {
        scheduledService.updatePassesToUnreturned();
        scheduledService.reminderCollectPass();
        scheduledService.reminderReturnPass();
    }
}
