package com.is442.oop.pass;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.is442.oop.data.models.Pass;

@Repository
public interface PassRepository extends JpaRepository<Pass, Integer> {
    
}
