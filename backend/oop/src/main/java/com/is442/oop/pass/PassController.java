package com.is442.oop.pass;

import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.is442.oop.data.models.Pass;
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

    @PostMapping("")
    public ResponseEntity<DataResponse> createPass(@RequestBody PassRequest passRequest) {
        Pass newPass = passService.createPass(passRequest);
        return new ResponseEntity<>(new DataResponse(newPass, "Pass"), HttpStatus.CREATED);
    }

    @PutMapping("/{passId}")
    public ResponseEntity<DataResponse> updatePass(
        @PathVariable("passId") Integer passId, 
        @RequestBody PassRequest passRequest
    ) {
        Pass updatePass = null;
        try {
            updatePass = passService.updatePass(passId, passRequest);
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
        return new ResponseEntity<>(new DataResponse(pass, "Pass"),HttpStatus.OK);
    }
}
