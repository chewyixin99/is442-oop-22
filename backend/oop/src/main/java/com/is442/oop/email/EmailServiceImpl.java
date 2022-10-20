package com.is442.oop.email;

import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import com.is442.oop.data.models.Loan;
import com.is442.oop.data.models.Pass;
import com.is442.oop.data.models.Template;
import com.is442.oop.exception.ActionNotExecutedException;
import com.is442.oop.loan.LoanService;
import com.is442.oop.pass.PassService;
import com.is442.oop.template.TemplateService;

@Service
public class EmailServiceImpl implements EmailService {
    @Autowired
    JavaMailSender mailSender;

    @Autowired
    TemplateService templateService;

    @Autowired
    LoanService loanService;

    @Autowired
    PassService passService;

    /**
     * All emails should use a template, thus, should be able to be sent via sendGenericEmail().
     */
    @Override
    public void sendGenericEmail(String senderEmail, String recipientEmail, Integer templateId) throws ActionNotExecutedException {
        Template template = templateService.getTemplate(templateId);        
        SimpleMailMessage message = new SimpleMailMessage();

        try {
            message.setFrom(recipientEmail);
            message.setTo(senderEmail);
            message.setSubject(template.getTemplateSubject());
            message.setText(template.getTemplateData());
        } catch(Exception e) {
            throw new ActionNotExecutedException("Email", e);
        }

        mailSender.send(message);
    }


    /**
     * Email to be sent when pass is confirmed.
     */
    public void sendPassEmail(String senderEmail, String recipientEmail, Integer templateId, Integer loanId) throws ActionNotExecutedException {
        Template template = templateService.getTemplate(templateId);
        Loan loan = loanService.getLoanByLoanID(loanId);
        Pass pass = passService.getPass(loan.getPassId());
        
        MimeMessage message = mailSender.createMimeMessage();
        try {
            MimeMessageHelper messageHelper = new MimeMessageHelper(message, true);
            messageHelper.setFrom(recipientEmail);
            messageHelper.setTo(senderEmail);
            messageHelper.setSubject(template.getTemplateSubject());
            messageHelper.setText(template.getTemplateData());
            
            // Need implement attachment storing in db -> AWS link?
            // messageHelper.addAttachment(recipientEmail, null);
        } catch(Exception e) {
            throw new ActionNotExecutedException("Email", e);
        }

        mailSender.send(message);
    }
}
