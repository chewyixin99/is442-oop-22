package com.is442.oop.email;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.time.LocalDate;

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
    PassService passService;

    String senderEmail = "sport.singapore.helpdesk@gmail.com";
    
    /**
     * ================================================== Main Services ==================================================
     * Processes email content before using this.sendGenericEmail() or this.sendAttachmentEmail()
     */
    // public void sendAdminEmail(Integer loanId, Integer templateId) throws ActionNotExecutedException {
    //     try {
    //         Loan loan = loanService.getLoanByLoanID(loanId);
    //         User user = userService.getUser(loan.getUserId());
    //         Template template = templateService.getTemplate(templateId);
    //         String emailSubject = template.getTemplateSubject();
    //         String emailBody = template.getTemplateData();
    //         this.sendGenericEmail(user.getEmail(), emailSubject, emailBody);
    //     } catch (Exception e) {
    //         throw new ActionNotExecutedException("sendAdminEmail", e);
    //     }
    // }

    public void sendLoanConfirmationEmail(Loan loan, Integer templateId) throws ActionNotExecutedException {
        try {
            User user = userService.getUser(loan.getUserId());
            Pass pass = loan.getPass();
            Template template = templateService.getTemplate(templateId);
            
            // Email fields
            String username = user.getUsername();
            String poi = pass.getPoi();
            String date = loan.getStartDate().toString();

            String emailSubject = template.getTemplateSubject();
            String templateData = template.getTemplateData();
            String emailBody = String.format(templateData, username, poi, date, poi, poi, poi, poi);
            InputStream attachmentStream = new ByteArrayInputStream(pass.getPassAttachment());
            DataSource attachment = new ByteArrayDataSource(attachmentStream, "application/octet-stream");
            this.sendAttachmentEmail(user.getEmail(), emailSubject, emailBody, pass.getPassAttachmentName(), attachment);
        } catch (Exception e) {
            throw new ActionNotExecutedException("sendLoanConfirmationEmail", e);
        }
    };


    // public void sendPassConfirmationEmail(Integer loanId, Integer templateId) throws ActionNotExecutedException {
    //     try {
    //         Loan loan = loanService.getLoanByLoanID(loanId);
    //         User user = userService.getUser(loan.getUserId());
    //         Pass pass = loan.getPass();
    //         Template template = templateService.getTemplate(templateId);
    
    //         InputStream attachmentStream = new ByteArrayInputStream(pass.getPassAttachment());
    //         DataSource attachment = new ByteArrayDataSource(attachmentStream, "application/octet-stream");
    //         this.sendAttachmentEmail(user.getEmail(), template, pass.getPassAttachmentName(), attachment);;
    //     } catch (Exception e) {
    //         throw new ActionNotExecutedException("sendPassConfirmationEmail", e);
    //     }
    // }

    /**
     * @param user                          User object
     * @param tempalteId                    Template Id
     * @param confirmationUrl               Url to be displayed and anchored in template's body
     * @throws ActionNotExecutedException   Catch all
     */
    public void sendSimpleConfirmationUrlEmail(User user, Integer tempalteId, String confirmationUrl) throws ActionNotExecutedException {
        try {
            Template template = templateService.getTemplate(tempalteId);
            String emailSubject = template.getTemplateSubject();
            String templateData = template.getTemplateData();
            String emailBody = String.format(templateData, user.getUsername(), confirmationUrl, confirmationUrl);
            this.sendGenericEmail(user.getEmail(), emailSubject, emailBody);
        } catch (Exception e) {
            throw new ActionNotExecutedException("sendSimpleConfirmationUrlEmail", e);
        }
    }
    
    /**
     * ================================================== Helper Services ==================================================
     */
    @Override
    public void sendGenericEmail(String recipientEmail, String emailSubject, String emailBody) throws ActionNotExecutedException {      
        MimeMessage message = mailSender.createMimeMessage();
        boolean isHtmlText = true;

        try {
            MimeMessageHelper messageHelper = new MimeMessageHelper(message, true);
            messageHelper.setFrom(this.senderEmail);
            messageHelper.setTo(recipientEmail);
            messageHelper.setSubject(emailSubject);
            messageHelper.setText(emailBody, isHtmlText);
        } catch(Exception e) {
            throw new ActionNotExecutedException("sendGenericEmail", e);
        }

        mailSender.send(message);
    }


    /**
     * Email to be sent when pass is confirmed.
     */
    @Override
    public void sendAttachmentEmail(String recipientEmail, String emailSubject, String emailBody, String attachmentName, DataSource attachment) throws ActionNotExecutedException {        
        MimeMessage message = mailSender.createMimeMessage();
        boolean isHtmlText = true;
        
        try {
            MimeMessageHelper messageHelper = new MimeMessageHelper(message, true);
            messageHelper.setFrom(this.senderEmail);
            messageHelper.setTo(recipientEmail);
            messageHelper.setSubject(emailSubject);
            messageHelper.setText(emailBody, isHtmlText);
            messageHelper.addAttachment(attachmentName, attachment);
        } catch(Exception e) {
            throw new ActionNotExecutedException("sendAttachmentEmail", e);
        }

        mailSender.send(message);
    }
}
