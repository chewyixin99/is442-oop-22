package com.is442.oop.scheduled;

import java.time.LocalDate;
import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.is442.oop.data.models.Loan;
import com.is442.oop.data.models.Pass;
import com.is442.oop.data.models.PassStatus;
import com.is442.oop.data.models.User;
import com.is442.oop.email.EmailService;
import com.is442.oop.exception.ActionNotExecutedException;
import com.is442.oop.loan.LoanRepository;
import com.is442.oop.loan.LoanService;
import com.is442.oop.pass.PassRepository;
import com.is442.oop.pass.PassService;
import com.is442.oop.user.UserService;

@Service
public class ScheduledServiceImpl implements ScheduledService {
    
    @Autowired
    LoanRepository loanRepository;

    @Autowired
    PassRepository passRepository;

    @Autowired
    PassService passService;

    @Autowired
    LoanService loanService;

    @Autowired
    UserService userService;

    @Autowired
    EmailService emailService;


    public List<Pass> updatePassesToUnreturned() throws ActionNotExecutedException {
        List<Loan> expiredLoans = null;
        try {
            expiredLoans = loanRepository.getAllExpiredLoans();
        } catch (Exception e) {
            throw new ActionNotExecutedException("LoanRepository", e);
        }

        List<Pass> unreturnedPasses = new ArrayList<Pass>();
        for (Loan l: expiredLoans) {
            try {
                Pass unreturnedPass = l.getPass();
                unreturnedPass.setPassStatus(PassStatus.UNRETURNED);
                passRepository.save(unreturnedPass);
                unreturnedPasses.add(unreturnedPass);
            } catch (Exception e) {
                throw new ActionNotExecutedException("PassService", e);
            }
        }

        return unreturnedPasses;
    };


    
    public void reminderCollectPass() {
        LocalDate tomorrow = LocalDate.now().plusDays(1);
        // Obtain the passes that need to be collected tomorrow
        List<Loan> loans = loanService.getLoanByDate(tomorrow);
        HashSet<String> emails = new HashSet<String>();

        // Create a HashMap of email and the list of passes that need to be collected. These are Physical Passes Only
        HashMap<String, List<Pass>> emailPassMap = new HashMap<String, List<Pass>>();
        for (Loan l: loans) {
            String email = l.getUser().getEmail();
            if (l.getPass().getIsPhysical() == false) {
                continue;
            }
            if (emailPassMap.containsKey(email)) {
                emailPassMap.get(email).add(l.getPass());
            } else {
                List<Pass> passes = new ArrayList<Pass>();
                passes.add(l.getPass());
                emailPassMap.put(email, passes);
            }
        }

        // Send email to each user
        
        for (String email: emailPassMap.keySet()) {
            System.out.println(emailPassMap.keySet());
            String htmlString = "You have booked the following passes. Be sure to head down to GOP office and collect them. <br> <table border='1'> <tr> <th>Pass Number</th> <th>Pass Description</th> <th>Places Of Interest</th> <th> URL </th> <th>Number of Guests</th> <th>Replacement Fee</th>  </tr>";
            List<Pass> passToBeCollected = emailPassMap.get(email);

            // "<tr> <td>Alfreds Futterkiste</td> <td>Maria Anders</td> <td>Germany</td> </tr> <tr> <td>Centro comercial Moctezuma</td> <td>Francisco Chang</td> <td>Mexico</td> </tr> </table>"
            
            if (passToBeCollected.size() == 1) {
                Pass pass = passToBeCollected.get(0);
                String toSend = htmlString + String.format("<td>%d</td> <td>%s</td> <td>%s</td> <td> %s </td> <td>%d</td> <td>$%.2f</td></tr></table>", pass.getPassNumber(), pass.getPassDesc(), pass.getPoi(), pass.getPoiUrl(), pass.getNumGuests(), pass.getReplacementFee());

                emailService.sendGenericEmail(email,"[Reminder] Please Collcet Passes" , toSend);
                System.out.println("Email sent to " + email);
            }
            else{
                try{
                    String toSend = htmlString;
                    for (Pass pass: passToBeCollected){
                         toSend += String.format("<tr><td>%d</td> <td>%s</td> <td>%s</td> <td> %s </td> <td>%d</td> <td>$%.2f</td></tr>", pass.getPassNumber(), pass.getPassDesc(), pass.getPoi(), pass.getPoiUrl(), pass.getNumGuests(), pass.getReplacementFee());
        
                    }
                    toSend += "</table>";
                    emailService.sendGenericEmail(email,"[Reminder] Please Collect Passes" , toSend);
                    System.out.println("Email sent to " + email);    
                } catch (Exception e){
                    System.out.println(e.getStackTrace());
                }
            }
            System.out.println("Reminders sent");
        }
    }

    public void reminderReturnPass(){
        // obtain all loans that are expired
        List<Loan> expiredLoans = loanRepository.getAllExpiredLoans();
        
        // get unique list of users from expired loans
        HashSet<User> users = new HashSet<User>();
        for (Loan l: expiredLoans) {
            users.add(l.getUser());
        }

        // get map of users and their expired loans
        HashMap<User, List<Loan>> userLoanMap = new HashMap<User, List<Loan>>();
        for (Loan l: expiredLoans) {
            User user = l.getUser();
            if (userLoanMap.containsKey(user)) {
                userLoanMap.get(user).add(l);
            } else {
                List<Loan> loans = new ArrayList<Loan>();
                loans.add(l);
                userLoanMap.put(user, loans);
            }
        }

        // send email to each user
        for (User user: userLoanMap.keySet()) {
            System.out.println(userLoanMap.keySet());
            String htmlString = "You have not returned the following passes. Please head down to the GOP office to return them. If you have lost them, please pay the Replacement Fee. <br><br> <table border='1'> <tr> <th>Pass Number</th> <th>Pass Description</th> <th>Places Of Interest</th> <th> URL </th> <th>Number of Guests</th> <th>Replacement Fee</th>  </tr>";
            List<Loan> myExpiredLoans = userLoanMap.get(user);
            
            if (myExpiredLoans.size() == 1) {
                Pass pass = myExpiredLoans.get(0).getPass();
                String toSend = htmlString + String.format("<tr><td>%d</td> <td>%s</td> <td>%s</td> <td> %s </td> <td>%d</td> <td>$%.2f</td></tr></table>", pass.getPassNumber(), pass.getPassDesc(), pass.getPoi(), pass.getPoiUrl(), pass.getNumGuests(), pass.getReplacementFee());

                emailService.sendGenericEmail(user.getEmail(),"[Reminder] Please Return Passes" , toSend);
                System.out.println("Email sent to " + user.getEmail());
            }
            else{
                try{
                    String toSend = String.valueOf(htmlString);
                    for (Loan loan: myExpiredLoans){
                        Pass pass = loan.getPass();
                        toSend += String.format("<tr><td>%d</td> <td>%s</td> <td>%s</td> <td> %s </td> <td>%d</td> <td>$%.2f</td></tr>", pass.getPassNumber(), pass.getPassDesc(), pass.getPoi(), pass.getPoiUrl(), pass.getNumGuests(), pass.getReplacementFee());
        
                    }
                    toSend += "</table>";
                    System.out.println("LOOK HERE");
                    System.out.println(toSend);
                    emailService.sendGenericEmail(user.getEmail(),"[Reminder] Please Return Passes" , toSend);
                    System.out.println("Email sent to " + user.getEmail());    
    
                } catch (Exception e){
                    System.out.println(e.getStackTrace());
                }
                
            }
        }
        System.out.println("-----------Reminder's completed-----------");

    }   

}
    