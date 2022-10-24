package com.is442.oop.pass;

import java.io.IOException;
import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.is442.oop.data.models.Loan;
import com.is442.oop.data.models.Pass;
import com.is442.oop.exception.ActionNotExecutedException;
import com.is442.oop.exception.ResourceNotFoundException;
import com.is442.oop.loan.LoanService;

@Service
public class PassServiceImpl implements PassService {
    @Autowired
    PassRepository passRepository;

    @Autowired
    LoanService loanService;

    @Override
    public Pass getPass(Integer passId) throws ResourceNotFoundException {
        return passRepository.findById(passId).orElseThrow(() -> new ResourceNotFoundException("Pass", "Pass ID", passId));
    }

    @Override
    public List<Pass> getAllPasses() {
        return passRepository.findAll();
    }

    @Override
    public Pass createPass(PassRequest passRequest) throws ActionNotExecutedException {
        Pass pass = new Pass();
        try {
            pass.setPoi(passRequest.getPoi());
            pass.setPoiUrl(passRequest.getPoiUrl());
            pass.setPassDesc(passRequest.getPassDesc());
            pass.setNumGuests(passRequest.getNumGuests());
            pass.setReplacementFee(passRequest.getReplacementFee());
            pass.setPhysical(passRequest.isPhysical());
            pass.setPassStatus(passRequest.getPassStatus());
            pass.setPassAttachmentName(passRequest.getPassAttachmentName());
            pass.setPassAttachment(passRequest.getPassAttachment().getBytes());
        } catch (IOException e) {
            throw new ActionNotExecutedException("Pass", e);
        } catch (Exception e) {
            throw new ActionNotExecutedException("Pass", e);
        }
        passRepository.save(pass);
        return pass;
    }

    @Override
    public Pass updatePass(Integer passId, PassRequest passRequest) throws ResourceNotFoundException, ActionNotExecutedException {
        Pass pass = null;
        Optional<Pass> queryPass = passRepository.findById(passId);
        if (queryPass.isEmpty()) {
            throw new ResourceNotFoundException("Pass", "Pass ID", passId);
        }

        try {
            pass = queryPass.get();
            pass.setPoi(passRequest.getPoi());
            pass.setPoiUrl(passRequest.getPoiUrl());
            pass.setPassDesc(passRequest.getPassDesc());
            pass.setNumGuests(passRequest.getNumGuests());
            pass.setReplacementFee(passRequest.getReplacementFee());
            pass.setPhysical(passRequest.isPhysical());
            pass.setPassStatus(passRequest.getPassStatus());
            pass.setPassAttachmentName(passRequest.getPassAttachmentName());
            pass.setPassAttachment(passRequest.getPassAttachment().getBytes());
        } catch (IOException e) {
            throw new ActionNotExecutedException("Pass", e);
        } catch (Exception e) {
            throw new ActionNotExecutedException("Pass", e);
        }
        passRepository.save(pass);
        return pass;
    }

    @Override
    public Pass deletePass(Integer passId) throws ResourceNotFoundException {
        Pass pass = null;
        Optional<Pass> queryPass = passRepository.findById(passId);
        if (queryPass.isEmpty()) {
            throw new ResourceNotFoundException("Pass", "Pass ID", passId);
        }
        pass = queryPass.get();
        pass.setDefunct(true);
        passRepository.save(pass);
        return pass;
    }

    @Override
    public Pass getPassByLoanId(Integer loanId) throws RuntimeException {
        Pass pass = null;
        Loan loan = null;
        try {
            loan = loanService.getLoanByLoanID(loanId);
        } catch (Exception e) {
            throw e;
        }
        try {
            pass = this.getPass(loan.getPassId());            
        } catch (Exception e) {
            throw e;
        }
        return pass;
    }
}
