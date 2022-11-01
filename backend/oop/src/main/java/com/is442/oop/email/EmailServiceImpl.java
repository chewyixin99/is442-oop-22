package com.is442.oop.email;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import javax.activation.DataSource;
import javax.mail.internet.MimeMessage;
import javax.mail.util.ByteArrayDataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import com.is442.oop.data.models.Loan;
import com.is442.oop.data.models.Pass;
import com.is442.oop.data.models.Template;
import com.is442.oop.data.models.User;
import com.is442.oop.exception.ActionNotExecutedException;
import com.is442.oop.loan.LoanService;
import com.is442.oop.pass.PassService;
import com.is442.oop.template.TemplateService;
import com.is442.oop.user.UserService;

@Service
public class EmailServiceImpl implements EmailService {
    @Autowired
    JavaMailSender mailSender;

    @Autowired
    UserService userService;

    @Autowired
    TemplateService templateService;

    @Autowired
    LoanService loanService;

    @Autowired
    PassService passService;

    String senderEmail = "sport.singapore.helpdesk@gmail.com";
    
    /**
     * ================================================== Main Services ==================================================
     */

    /**
     * For admin emails with content differing based on tempalteId.
     * E.g. (1) Loan Confirmation, (2) Pass Unreturned, (3) Pass Missing
     */
    public void sendAdminEmail(Integer loanId, Integer templateId) throws ActionNotExecutedException {
        try {
            Loan loan = loanService.getLoanByLoanID(loanId);
            User user = userService.getUser(loan.getUserId());
            Template template = templateService.getTemplate(templateId);
            this.sendGenericEmail(user.getEmail(), template);
        } catch (Exception e) {
            throw new ActionNotExecutedException("Email", e);
        }
    }

    /**
     * Should be triggered on pass confirmation.
     */
    public void sendPassConfirmationEmail(Integer loanId, Integer templateId) throws ActionNotExecutedException {
        try {
            Loan loan = loanService.getLoanByLoanID(loanId);
            User user = userService.getUser(loan.getUserId());
            Pass pass = loan.getPass();
            Template template = templateService.getTemplate(templateId);
    
            InputStream attachmentStream = new ByteArrayInputStream(pass.getPassAttachment());
            DataSource attachment = new ByteArrayDataSource(attachmentStream, "application/octet-stream");
            this.sendAttachmentEmail(user.getEmail(), template, pass.getPassAttachmentName(), attachment);;
        } catch (Exception e) {
            throw new ActionNotExecutedException("Email", e);
        }
    }
    
    /**
     * ================================================== Helper Services ==================================================
     */

    /**
     * All emails should use a template, thus, should be able to be sent via sendGenericEmail().
     */
    @Override
    public void sendGenericEmail(String recipientEmail, Template template) throws ActionNotExecutedException {      
        SimpleMailMessage message = new SimpleMailMessage();

        try {
            message.setFrom(this.senderEmail);
            message.setTo(recipientEmail);
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
    @Override
    public void sendAttachmentEmail(String recipientEmail, Template template, String attachmentName, DataSource attachment) throws ActionNotExecutedException {        
        MimeMessage message = mailSender.createMimeMessage();
        try {
            MimeMessageHelper messageHelper = new MimeMessageHelper(message, true);
            messageHelper.setFrom(this.senderEmail);
            messageHelper.setTo(recipientEmail);
            messageHelper.setSubject(template.getTemplateSubject());
            messageHelper.setText(template.getTemplateData());
            messageHelper.addAttachment(attachmentName, attachment);
        } catch(Exception e) {
            throw new ActionNotExecutedException("Email", e);
        }

        mailSender.send(message);
    }
}
