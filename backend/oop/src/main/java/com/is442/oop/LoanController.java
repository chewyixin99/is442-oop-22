package com.is442.oop;


import com.is442.oop.daos.LoanDAOInt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/loan")
public class LoanController {
    @Autowired
    LoanDAOInt loanDAOInt;





}


