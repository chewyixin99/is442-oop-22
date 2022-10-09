package com.is442.oop.data.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.is442.oop.data.models.EmailTemplate;

@Repository
public interface TemplateRepository extends JpaRepository<EmailTemplate, Integer>{
}
