/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ClientSide;

/**
 *
 * @author Sunil
 */


import java.util.Properties;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

//allow less secure app on that gamil account 
public class SendEmail {
    
    boolean sendEmail(String to,String subject,String msgbody) throws Exception
    {
        final String username = "meena120sunil@gmail.com";
        final String password = "Enter password";

        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");
        props.put("mail.smtp.ssl.trust", "smtp.gmail.com");
        Session session = Session.getInstance(props,
          new javax.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }
          });

        try {

            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress("meena120sunil@gmail.com"));
            message.setRecipients(Message.RecipientType.TO,
                InternetAddress.parse(to));
//            message.setSubject("A testing mail header !!!");
//            message.setText("Dear Mail Crawler,"+ "\n\n No spam to my email, please!");
            message.setSubject(subject);
            message.setText(msgbody);
            
            Transport.send(message);

//            System.out.println("Done");
            return true;

        } 

        catch (MessagingException e) 
        {
            // throw new RuntimeException(e);
            System.out.println("Mail failed."+e);
            return false;
        }
    }

//    boolean sendEmail(String email) {
//        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
//    }
}
