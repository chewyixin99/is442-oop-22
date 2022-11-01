package com.is442.oop.pass;

import java.io.IOException;
import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.is442.oop.data.models.Pass;
import com.is442.oop.data.models.PassStatus;
import com.is442.oop.exception.ActionNotExecutedException;
import com.is442.oop.exception.ResourceNotFoundException;

@Service
public class PassServiceImpl implements PassService {
    @Autowired
    PassRepository passRepository;

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
            pass.setIsPhysical(passRequest.getIsPhysical());
            pass.setPassStatus(passRequest.getPassStatus());

            String passAttachmentName = passRequest.getPassAttachmentName();
            if (passAttachmentName != null) pass.setPassAttachmentName(passAttachmentName);
            MultipartFile passAttachment = passRequest.getPassAttachment();
            if (passAttachment != null) pass.setPassAttachment(passAttachment.getBytes());
            MultipartFile passImage = passRequest.getPassImage();
            if (passImage != null) pass.setPassImage(passImage.getBytes());
            passRepository.save(pass);
        } catch (IOException e) {
            throw new ActionNotExecutedException("Pass", e);
        }
        return pass;
    }

    @Override
    public Pass deletePassAttachment(Integer passId) throws ResourceNotFoundException, ActionNotExecutedException {
        Pass pass = null;
        Optional<Pass> queryPass = passRepository.findById(passId);
        if (queryPass.isEmpty()) {
            throw new ResourceNotFoundException("Pass", "Pass ID", passId);
        }
        pass = queryPass.get();

        if (pass.getPassAttachment() == null) {
            throw new ActionNotExecutedException("Pass", "No existing attachment.");
        }
        
        try {
            pass = queryPass.get();
            pass.setPassAttachmentName(null);
            pass.setPassAttachment(null);
            passRepository.save(pass);
        } catch (Exception e) {
            throw new ActionNotExecutedException("Pass", e);
        }
        return pass;
    }

    @Override
    public Pass deletePassImage(Integer passId) throws ResourceNotFoundException, ActionNotExecutedException {
        Pass pass = null;
        Optional<Pass> queryPass = passRepository.findById(passId);
        if (queryPass.isEmpty()) {
            throw new ResourceNotFoundException("Pass", "Pass ID", passId);
        }
        pass = queryPass.get();

        if (pass.getPassImage() == null) {
            throw new ActionNotExecutedException("Pass", "No existing image.");
        }

        try {
            pass.setPassImage(null);
            passRepository.save(pass);
        } catch (Exception e) {
            throw new ActionNotExecutedException("Pass", e);
        }
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
            pass.setIsPhysical(passRequest.getIsPhysical());
            pass.setPassStatus(passRequest.getPassStatus());
            
            // Fields that may not be updated by user
            String passAttachmentName = passRequest.getPassAttachmentName();
            if (passAttachmentName != null) pass.setPassAttachmentName(passAttachmentName);
            MultipartFile passAttachment = passRequest.getPassAttachment();
            if (passAttachment != null) pass.setPassAttachment(passAttachment.getBytes());
            MultipartFile passImage = passRequest.getPassImage();
            if (passImage != null) pass.setPassImage(passImage.getBytes());
            passRepository.save(pass);
        } catch (Exception e) {
            throw new ActionNotExecutedException("Pass", e);
        }
        return pass;
    }

    @Override
    public Pass updatePassStatus(Integer passId, PassStatus passStatus) throws ActionNotExecutedException {
        Pass pass = null;
        Optional<Pass> queryPass = passRepository.findById(passId);
        if (queryPass.isEmpty()) {
            throw new ResourceNotFoundException("Pass", "Pass ID", passId);
        }

        try {
            pass = queryPass.get();
            pass.setPassStatus(passStatus);
            passRepository.save(pass);
        } catch (Exception e) {
            throw new ActionNotExecutedException("Pass", e);
        }
        return pass;
    };

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

    public List<Pass> getUnreturnedPasses() throws ActionNotExecutedException {
        return null;
    };
}
