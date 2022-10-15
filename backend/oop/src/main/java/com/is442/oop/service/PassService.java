package com.is442.oop.service;

import java.util.*;

import org.springframework.stereotype.Component;

import com.is442.oop.data.models.Pass;
import com.is442.oop.data.payloads.request.PassRequest;
import com.is442.oop.data.payloads.response.MessageResponse;
import com.is442.oop.exception.ResourceNotFoundException;

@Component
public interface PassService {
    Pass getPass(Integer passId) throws ResourceNotFoundException;
    List<Pass> getAllPasses();
    MessageResponse createPass(PassRequest passRequest);
    Pass updatePass(Integer passId, PassRequest passRequest) throws ResourceNotFoundException;
    void deletePass(Integer passId) throws ResourceNotFoundException;
}
