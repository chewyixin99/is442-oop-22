package com.is442.oop.pass;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.is442.oop.data.models.Pass;

@Repository
public interface PassRepository extends JpaRepository<Pass, Integer> {
    Optional<Pass> findByPassIdAndDefunctFalse(Integer passId);
}
