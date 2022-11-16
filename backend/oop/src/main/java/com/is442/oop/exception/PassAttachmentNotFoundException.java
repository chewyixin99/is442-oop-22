package com.is442.oop.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.PRECONDITION_REQUIRED)
public class PassAttachmentNotFoundException extends ActionNotExecutedException {
    public PassAttachmentNotFoundException(Integer passId){
        super(String.format("Pass"), String.format("No pass attachment for pass (passId: %s)", passId));
    };
}
