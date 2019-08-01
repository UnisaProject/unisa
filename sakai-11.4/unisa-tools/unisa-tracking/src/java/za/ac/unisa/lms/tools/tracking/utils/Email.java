package za.ac.unisa.lms.tools.tracking.utils;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;

import org.sakaiproject.component.cover.ComponentManager;
import org.sakaiproject.component.cover.ServerConfigurationService;
import org.sakaiproject.email.api.Attachment;
import org.sakaiproject.email.api.EmailService;
import org.sakaiproject.email.api.RecipientType;


public class Email {
	
	public void sendEmail(EmailService emailService,String attachmentFilepath,String filename,String emailAddress) throws AddressException {
		              emailAttachments(attachmentFilepath,filename);
		              
		              setBody(emailAddress.split("@")[0]);
		              setSubject("Requested PDF File From MyUNISA ("+filename+")");
		              setEmaiAddress(emailAddress);
				HashMap hm= new HashMap();
		
		emailService = (EmailService) ComponentManager.get(EmailService.class);
		//String tmpEmailFrom = ServerConfigurationService.getString("noReplyEmailFrom");
		String tmpEmailFrom = "assign@unisa.ac.za";
		
		InternetAddress toEmail = null;
		   try  {
		        toEmail = new InternetAddress(emailAddress);
		   } catch (AddressException e) {
		        // TODO Auto-generated catch block
		        e.printStackTrace();
		   }

		InternetAddress iaTo[] = new InternetAddress[1];
		iaTo[0] = toEmail;
		InternetAddress iaHeaderTo[] = new InternetAddress[1];
		iaHeaderTo[0] = toEmail;
		InternetAddress iaReplyTo[] = new InternetAddress[1];
		iaReplyTo[0] = new InternetAddress(tmpEmailFrom);
		List<String> contentList = new ArrayList<String>();
		contentList.add("Content-Type: text/html");
		//put elements to the map
		hm.put(RecipientType.TO,toEmail);
		
		emailService.sendMail(iaReplyTo[0], iaTo, subject, body, hm, iaReplyTo, contentList, attachmentList);

		//log.info("sending mail from "+iaReplyTo[0]+" to "+toEmail.toString());
	}
	private List<Attachment> attachmentList;
	 public void emailAttachments(String pdffullPath,String filename){
		                       File fileObject = new File(pdffullPath);
		                       attachmentList = new ArrayList<Attachment>();
		                       Attachment A = new Attachment(fileObject, filename);
		                       attachmentList.add(A);
		                       
	}
	 String body;
	private void setBody(String userID){
		
        //LecturerDAO lecturerDAO = new LecturerDAO();
        String surname = ""; //lecturerDAO.getStaffSurname(userID);

	     body = "<html> "+
        "<body> "+
        "Dear "+surname+" <br><br>"+
        "Please find attached the PDF file as requested by you from MyUNISA.<br><br>"+
        "Greetings<br>"+
        "myUnisa support team<br><br>"+
        "(Do not reply to this message,this message is auto generated by the server)"+
        "</body>"+
        "</html>";
	   
	}
	
	String subject;
	private void setSubject(String subject){
		       this.subject=subject;
	}
	private void setEmaiAddress(String emaiAddress){
		    this.emailAddress=emaiAddress;
	}
	//String email = "tguthanr@unisa.ac.za";
	String emailAddress ;


}