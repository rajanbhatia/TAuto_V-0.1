
import java.util.*;
import javax.mail.*;
import javax.mail.internet.*;
import javax.activation.*;

public class SendEmail
{
	  public static void main(String args[]){

	        try{
	            final String fromEmail = "rbhatia@waikato.ac.nz"; //requires valid gmail id
	            final String password = "jchdlbfiirhmznvl"; // password for gmail id  //If required (p gmail works fine), Enable two way password authentication and App Password
	            final String toEmail = "rajan.bhatia@waikato.ac.nz"; // can be any email id 

	            System.out.println("TLSEmail Start");
	            Properties props = new Properties();
	            props.put("mail.smtp.host", "smtp.gmail.com"); //SMTP Host
	            props.put("mail.smtp.port", "587"); //TLS Port
	            props.put("mail.smtp.auth", "true"); //enable authentication
	            props.put("mail.smtp.starttls.enable", "true"); //enable STARTTLS

	                //create Authenticator object to pass in Session.getInstance argument
	            Authenticator auth = new Authenticator() {
	                //override the getPasswordAuthentication method
	                protected PasswordAuthentication getPasswordAuthentication() {
	                    return new PasswordAuthentication(fromEmail, password);
	                }
	            };
	            Session session = Session.getInstance(props, auth);

	            MimeMessage message = new MimeMessage(session);
	            message.setFrom(new InternetAddress(fromEmail));
	            message.addRecipient(Message.RecipientType.TO, new InternetAddress(toEmail));

	            System.out.println("Mail Check 2");

	            message.setSubject("Oil Error Report");
	            message.setText("Hello");

	            System.out.println("Mail Check 3");

	            Transport.send(message);
	            System.out.println("Mail Sent");
	        }catch(Exception ex){
	            System.out.println("Mail fail");
	            System.out.println(ex.getMessage());
	        }
	    }
}