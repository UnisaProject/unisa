package za.ac.unisa.lms.tools.cronjobs.jobs;

import java.io.ByteArrayOutputStream;
import java.io.PrintWriter;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.xml.rpc.ServiceException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.sakaiproject.component.cover.ServerConfigurationService;
import org.sakaiproject.component.cover.ComponentManager;
import org.sakaiproject.email.api.EmailService;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.StatefulJob;

import za.ac.unisa.lms.tools.cronjobs.locks.SingleClusterInstanceJob;
import za.ac.unisa.unisa_axis.DataCleanup_DiscussionForumsWebService_jws.DataCleanup_DiscussionForumsWebServiceServiceLocator;
import za.ac.unisa.unisa_axis.DataCleanup_DiscussionForumsWebService_jws.DataCleanup_DiscussionForumsWebService_PortType;

public class DataCleanupDiscussionForumsJob2011_Y2 extends SingleClusterInstanceJob implements StatefulJob, OutputGeneratingJob {

	private ByteArrayOutputStream outputStream;
	private PrintWriter output;
	private Log log = LogFactory.getLog( this.getClass() );	
	private EmailService emailService;
	private static final String[] EMAIL_LIST = {"syzelle@unisa.ac.za", "magagjs@unisa.ac.za", "mphahsm@unisa.ac.za"};
	private static String emailAddressList = "";
	private static final String LOCAL_URL_PORT80 = "http://localhost:8080";
	private static final String LOCAL_URL_PORT82 = "http://localhost:8082";
	private static final String PORTAL_URL = "/portal";
	String url = "";
	private String webServiceResult = "";

	/*
	 * Crontab file
___________
Crontab syntax :-
A crontab file has five fields for specifying day , date and time  followed by the command to be run at that interval.
*     *   *   *    *  command to be executed
-     -    -    -    -
|     |     |     |     |
|     |     |     |     +----- day of week (0 - 6) (Sunday=0)
|     |     |     +------- month (1 - 12)
|     |     +--------- day of month (1 - 31)
|     +----------- hour (0 - 23)
+------------- min (0 - 59) (non-Javadoc)
	 */

	public void executeLocked(JobExecutionContext context)
			throws JobExecutionException {
		
    	//get email address list
    	for ( int i=0; i<EMAIL_LIST.length; i++ ){
    		emailAddressList += EMAIL_LIST[i].toString()+",";
    	}	

		System.out.println( "~~~~~~~~~~ Cronjob: DataCleanupDiscussionForumsJob" );
		System.out.println( "~~~~~~~~~~ Sending email to: "+emailAddressList );
		String subjectStart = "DataCleanupDiscussionForumsJob Job Started";
		String bodyStart = "DataCleanupDiscussionForumsJob Job Started";
		
		//send email for start of job
		try {
			for ( int i=0; i<EMAIL_LIST.length; i++ ){
				sendEmail( subjectStart, bodyStart, EMAIL_LIST[i] );
			}	
		} catch (AddressException e) {
			log.error( this+": DataCleanupDiscussionForumsJob(): AddressException: "+
						"unable to send email to: "+emailAddressList+"\r\n"+e.getMessage() );
			e.printStackTrace();
		} catch ( Exception any ){
			log.error( this+": DataCleanupDiscussionForumsJob(): "+
					"Unable to send email to: "+emailAddressList+"\r\n"+any.getMessage() );
			any.printStackTrace();
		} 	
	
		String serverUrl = ServerConfigurationService.getString("serverUrl");
		
		// ----- get serverUrl (myDev, myQa, myUnisa or localhost)
		String localPortal80 = LOCAL_URL_PORT80 + PORTAL_URL;
		String localPortal82 = LOCAL_URL_PORT82 + PORTAL_URL;
		
		if ( serverUrl.equals(LOCAL_URL_PORT80) || serverUrl.equals(LOCAL_URL_PORT82)
			|| serverUrl.equals(localPortal80) || serverUrl.equals(localPortal82) ) {
			serverUrl = "https://mydev.int.unisa.ac.za";
		}
		
		//url = "http://localhost:8082";
		url = serverUrl;
		url = url+"/unisa-axis/DataCleanup_DiscussionForumsWebService.jws";
		//FreedomToasterWebService_PortType events = null;
		DataCleanup_DiscussionForumsWebService_PortType events = null;
		
		// registration periods to be considered 
		String[] periods = {"2011#Y2"};
		
		// for each period		
		for (int m=0; m < periods.length; m++) {

			String[] tmpPeriod = periods[m].split("#");
			
			String forYear = tmpPeriod[0];
			String forSem = tmpPeriod[1];
						
			/** Call webservice to perform Discussion Forums Data Cleanup in Sakai Database */
			try {
				URL url1 = new URL(url);
				events = new DataCleanup_DiscussionForumsWebServiceServiceLocator().getDataCleanup_DiscussionForumsWebService(url1);	 
			} catch (MalformedURLException e1) {
				e1.printStackTrace();
			} catch (ServiceException e3) {
				e3.printStackTrace();
			} catch (Exception e2) {
				e2.printStackTrace();
			} // end try
			
					
			try {
			
				webServiceResult = events.discussionForumsDataCleanup(forYear, forSem);	
				
				// determine what result string is returned by webservice and send appropriate email
				String subjectC = "";
				String bodyC = "";
				
				if( webServiceResult.trim().equals("Success") ){
					subjectC = "DataCleanupDiscussionForumsJob Job Completed SUCCESSFULLY";
					bodyC = "DataCleanupDiscussionForumsJob Job Completed SUCCESSFULLY!";
					System.out.println("~~~~~~~~~~ DataCleanupDiscussionForumsJob Job Completed SUCCESSFULLY");
				}else{
					subjectC = "DataCleanupDiscussionForumsJob Job FAILED";
					bodyC = "DataCleanupDiscussionForumsJob Job FAILED!";
					System.out.println("~~~~~~~~~~ DataCleanupDiscussionForumsJob Job FAILED");
				}

				//send email for completion of job
				try {
					for ( int i=0; i<EMAIL_LIST.length; i++ ){
						sendEmail( subjectC, bodyC, EMAIL_LIST[i] );
					}	
				} catch (AddressException e) {
					log.error( this+": DataCleanupDiscussionForumsJob(): AddressException: "+
								"unable to send email to: "+emailAddressList+"\r\n"+e.getMessage() );
					e.printStackTrace();
				} catch ( Exception any ){
					log.error( this+": DataCleanupDiscussionForumsJob(): "+
								"Unable to send email to: "+emailAddressList+"\r\n"+any.getMessage() );
					any.printStackTrace();
				} 
			
			}catch (Exception vE) {
				// TODO: handle exception
				vE.printStackTrace();
			} // end try discussionForumsDataCleanup
						
		} // end of for (int m=0; m < periods.length; m++)
			
	} // end of public void executeLocked
	
	public void sendEmail(String subject, String body, String emailAddress) throws AddressException {
		
		emailService = (EmailService) ComponentManager.get(EmailService.class);

		String tmpEmailFrom = ServerConfigurationService.getString("noReplyEmailFrom");


		InternetAddress toEmail = new InternetAddress(emailAddress);
		InternetAddress iaTo[] = new InternetAddress[1];
		iaTo[0] = toEmail;
		InternetAddress iaHeaderTo[] = new InternetAddress[1];
		iaHeaderTo[0] = toEmail;
		InternetAddress iaReplyTo[] = new InternetAddress[1];
		iaReplyTo[0] = new InternetAddress(tmpEmailFrom);
		List<String> contentList = new ArrayList<String>();
		contentList.add("Content-Type: text/html");

		emailService.sendMail(iaReplyTo[0],iaTo,subject,body,iaHeaderTo,iaReplyTo,contentList);
	} // end of sendEmail

	public String getOutput() {
		if (output != null) {
			output.flush();
			return outputStream.toString();
		}
		return null;
	} // end of public String getOutput()

} // end of public class DataCleanupDiscussionForumsJob