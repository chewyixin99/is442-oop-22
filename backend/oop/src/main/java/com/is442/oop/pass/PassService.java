package com.is442.oop.pass;

import java.util.*;

import org.springframework.stereotype.Component;

import com.is442.oop.data.models.Pass;
import com.is442.oop.data.models.PassStatus;
import com.is442.oop.exception.ActionNotExecutedException;
import com.is442.oop.exception.ResourceNotFoundException;

@Component
public interface PassService {
    Pass getPass(Integer passId) throws ResourceNotFoundException;
    List<Pass> getAllPasses();
    Pass createPass(PassRequest passRequest) throws ActionNotExecutedException;
    Pass updatePass(Integer passId, PassRequest passRequest) throws ResourceNotFoundException, ActionNotExecutedException;
    Pass updatePassStatus(Integer passId, PassStatus passStatus) throws ActionNotExecutedException;
    Pass deletePassAttachment(Integer passId) throws ResourceNotFoundException, ActionNotExecutedException;
    Pass deletePassImage(Integer passId) throws ResourceNotFoundException, ActionNotExecutedException;
    Pass deletePass(Integer passId) throws ResourceNotFoundException;
    List<Pass> getUnreturnedPasses() throws ActionNotExecutedException;
}
