package com.is442.oop;

import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.is442.oop.daos.PassDAOInt;
import com.is442.oop.data.models.Pass;
import com.is442.oop.data.payloads.request.PassRequest;
import com.is442.oop.data.payloads.response.MessageResponse;

@RestController
@RequestMapping("/passes")
public class PassController {
    @Autowired
    PassDAOInt passDAOInt;

    @GetMapping("")
    public ResponseEntity<List<Pass>> getAllPasses() {
        List<Pass> passes = passDAOInt.getAllPasses();
        return new ResponseEntity<>(passes, HttpStatus.OK);
    }

    @GetMapping("/{passId}")
    public ResponseEntity<Pass> getPass(@PathVariable("passId") Integer passId) {
        Pass pass = null;
        try {
            pass = passDAOInt.getPass(passId);
        } catch (Exception e) {
            return new ResponseEntity<>(pass, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(pass, HttpStatus.OK);
    }

    @PostMapping("")
    public ResponseEntity<MessageResponse> createPass(@RequestBody PassRequest passRequest) {
        MessageResponse newPass = passDAOInt.createPass(passRequest);
        return new ResponseEntity<>(newPass, HttpStatus.CREATED);
    }

    @PutMapping("/{passId}")
    public ResponseEntity<Pass> updatePass(
        @PathVariable("passId") Integer passId, 
        @RequestBody PassRequest passRequest
    ) {
        Pass updatePass = null;
        try {
            updatePass = passDAOInt.updatePass(passId, passRequest);
        } catch (Exception e) {
            return new ResponseEntity<>(updatePass, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(updatePass, HttpStatus.OK);
    }

    @DeleteMapping("/{passId}")
    public ResponseEntity<HttpStatus> deletePass(@PathVariable("passId") Integer passId) {
        try {
            passDAOInt.deletePass(passId);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
