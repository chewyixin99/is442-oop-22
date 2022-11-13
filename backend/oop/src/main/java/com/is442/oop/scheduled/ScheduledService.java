package com.is442.oop.scheduled;

import java.util.List;

import org.springframework.stereotype.Component;

import com.is442.oop.data.models.Pass;
import com.is442.oop.exception.ActionNotExecutedException;

@Component
public interface ScheduledService {
    List<Pass> updatePassesToUnreturned() throws ActionNotExecutedException;

    void reminderCollectPass();
}
