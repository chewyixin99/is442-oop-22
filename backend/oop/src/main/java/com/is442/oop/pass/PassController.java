package com.is442.oop.pass;

import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.is442.oop.data.models.Pass;
import com.is442.oop.data.models.PassStatus;
import com.is442.oop.data.payloads.response.DataResponse;

@RestController
@RequestMapping("/passes")
public class PassController {
    @Autowired
    PassService passService;

    @GetMapping("")
    public ResponseEntity<DataResponse> getAllPasses() {
        List<Pass> passes = passService.getAllPasses();
        return new ResponseEntity<>(new DataResponse(passes, "Pass"), HttpStatus.OK);
    }

    @GetMapping("/{passId}")
    public ResponseEntity<DataResponse> getPass(@PathVariable("passId") Integer passId) {
        Pass pass = null;
        try {
            pass = passService.getPass(passId);
        } catch (Exception e) {
            return new ResponseEntity<>(new DataResponse(pass, e), HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(new DataResponse(pass, "Pass"), HttpStatus.OK);
    }

    @PostMapping(value = "", consumes = { MediaType.MULTIPART_FORM_DATA_VALUE })
    public ResponseEntity<DataResponse> createPass(
            @RequestParam(value = "passDesc", required = true) String passDesc,
            @RequestParam(value = "poi", required = true) String poi,
            @RequestParam(value = "poiUrl", required = false) String poiUrl,
            @RequestParam(value = "numGuests", required = true) Integer numGuests,
            @RequestParam(value = "replacementFee", required = true) Double replacementFee,
            @RequestParam(value = "isPhysical", required = true) Boolean isPhysical,
            @RequestParam(value = "passStatus", required = true) PassStatus passStatus,
            @RequestParam(value = "passAttachmentName", required = false) String passAttachmentName,
            @RequestParam(value = "passAttachment", required = false) MultipartFile passAttachment,
            @RequestParam(value = "passImage", required = false) MultipartFile passImage) {

        PassRequest passRequest = new PassRequest(passDesc, poi, poiUrl, numGuests, replacementFee, isPhysical,
                passStatus, passAttachmentName, passAttachment, passImage);
        Pass newPass = null;
        try {
            newPass = passService.createPass(passRequest);
        } catch (Exception e) {
            return new ResponseEntity<DataResponse>(new DataResponse(newPass, e), HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(new DataResponse(newPass, "Pass"), HttpStatus.CREATED);
    }

    /**
     * Updates all pass data - If files not included, will not remove fileds.
     */
    @PutMapping(value = "/{passId}", consumes = { MediaType.MULTIPART_FORM_DATA_VALUE })
    public ResponseEntity<DataResponse> updatePass(
            @PathVariable("passId") Integer passId,
            @RequestParam(value = "passDesc", required = true) String passDesc,
            @RequestParam(value = "poi", required = true) String poi,
            @RequestParam(value = "poiUrl", required = false) String poiUrl,
            @RequestParam(value = "numGuests", required = true) Integer numGuests,
            @RequestParam(value = "replacementFee", required = true) Double replacementFee,
            @RequestParam(value = "isPhysical", required = true) Boolean isPhysical,
            @RequestParam(value = "passStatus", required = true) PassStatus passStatus,
            @RequestParam(value = "passAttachmentName", required = false) String passAttachmentName,
            @RequestParam(value = "passAttachment", required = false) MultipartFile passAttachment,
            @RequestParam(value = "passImage", required = false) MultipartFile passImage) {
        Pass updatePass = null;
        PassRequest passRequest = new PassRequest(passDesc, poi, poiUrl, numGuests, replacementFee, isPhysical,
        passStatus, passAttachmentName, passAttachment, passImage);
        try {
            updatePass = passService.updatePass(passId, passRequest);
        } catch (Exception e) {
            return new ResponseEntity<>(new DataResponse(updatePass, e), HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(new DataResponse(updatePass, "Pass"), HttpStatus.OK);
    }

    @PutMapping("updatePassStatus/{passId}")
    public ResponseEntity<DataResponse> updatePassStatus(@PathVariable("passId") Integer passId, @RequestBody PassStatus passStatus) {
        Pass updatePass = null;
        try {
            updatePass = passService.updatePassStatus(passId, passStatus);
        } catch (Exception e) {
            return new ResponseEntity<>(new DataResponse(updatePass, e), HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(new DataResponse(updatePass, "Pass"), HttpStatus.OK);
    }

    @PutMapping(value = "/deletePassAttachment/{passId}")
    public ResponseEntity<DataResponse> deletePassAttachment(@PathVariable("passId") Integer passId) {
        Pass updatePass = null;
        try {
            updatePass = passService.deletePassAttachment(passId);
        } catch (Exception e) {
            return new ResponseEntity<>(new DataResponse(updatePass, e), HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(new DataResponse(updatePass, "Pass"), HttpStatus.OK);
    }

    @PutMapping(value = "/deletePassImage/{passId}")
    public ResponseEntity<DataResponse> deletePassImage(@PathVariable("passId") Integer passId) {
        Pass updatePass = null;
        try {
            updatePass = passService.deletePassImage(passId);
        } catch (Exception e) {
            return new ResponseEntity<>(new DataResponse(updatePass, e), HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(new DataResponse(updatePass, "Pass"), HttpStatus.OK);
    }

    @DeleteMapping("/{passId}")
    public ResponseEntity<DataResponse> deletePass(@PathVariable("passId") Integer passId) {
        Pass pass = null;
        try {
            pass = passService.deletePass(passId);
        } catch (Exception e) {
            return new ResponseEntity<>(new DataResponse(pass, e), HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(new DataResponse(pass, "Pass"), HttpStatus.OK);
    }

    @GetMapping("/byLoanId/{loanId}")
    public ResponseEntity<DataResponse> getPassByLoanId(@PathVariable("loanId") Integer loanId) {
        Pass pass = null;
        try {
            pass = passService.getPassByLoanId(loanId);
        } catch (Exception e) {
            return new ResponseEntity<>(new DataResponse(pass, e), HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(new DataResponse(pass, "Pass"), HttpStatus.OK);
    }
}
