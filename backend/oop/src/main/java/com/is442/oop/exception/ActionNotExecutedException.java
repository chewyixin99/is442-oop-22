package com.is442.oop.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class ActionNotExecutedException extends RuntimeException {
    private String resourceName;
    private String reason;

    public ActionNotExecutedException(String resourceName, String reason){
        super(String.format("Unsuccessful action - Resource: %s (%s).", resourceName, reason));
        this.resourceName = resourceName;
        this.reason = reason;
    };

    public String getResourceName() {
        return resourceName;
    };

    public String getReason() {
        return reason;
    }
}
