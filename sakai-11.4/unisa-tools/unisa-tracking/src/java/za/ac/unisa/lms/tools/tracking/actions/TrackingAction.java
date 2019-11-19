package za.ac.unisa.lms.tools.tracking.actions;

import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.mail.internet.InternetAddress;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.sf.json.JSONObject;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.PDPageTree;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.font.PDFont;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;
import org.apache.struts.actions.LookupDispatchAction;
import org.sakaiproject.component.cover.ComponentManager;
import org.sakaiproject.component.cover.ServerConfigurationService;
import org.sakaiproject.email.api.Attachment;
import org.sakaiproject.email.api.EmailService;
import org.sakaiproject.email.api.RecipientType;
import org.sakaiproject.tool.api.Session;
import org.sakaiproject.tool.api.SessionManager;
import org.sakaiproject.user.api.User;

import za.ac.unisa.lms.tools.tracking.dao.KeyValue;
import za.ac.unisa.lms.tools.tracking.dao.TrackingDAO;
import za.ac.unisa.lms.tools.tracking.dao.WebServiceGateWay;
import za.ac.unisa.lms.tools.tracking.bo.Assignment;
import za.ac.unisa.lms.tools.tracking.bo.Consignment;
import za.ac.unisa.lms.tools.tracking.bo.Docket;
import za.ac.unisa.lms.tools.tracking.forms.DocketNumberDetails;
import za.ac.unisa.lms.tools.tracking.forms.TrackingForm;
import za.ac.unisa.lms.tools.tracking.pdf.PDFColumn;
import za.ac.unisa.lms.tools.tracking.pdf.PDFTable;
import za.ac.unisa.lms.tools.tracking.pdf.PDFTableBuilder;
import za.ac.unisa.lms.tools.tracking.pdf.PDFTableGenerator;
import za.ac.unisa.lms.tools.tracking.pdf.PdfDownloader;
import za.ac.unisa.lms.tools.tracking.utils.Email;
import za.ac.unisa.lms.tools.tracking.utils.EmailValidator;
import za.ac.unisa.lms.tools.tracking.utils.FindFile;

@SuppressWarnings({"unused", "rawtypes", "unchecked"})
public class TrackingAction extends LookupDispatchAction{

	// ----------------------------------- Instance Variables

	private Log log = LogFactory.getLog(TrackingAction.class.getName());
	private SessionManager sessionManager;
	private EmailService emailService;
    WebServiceGateWay pGateWay;
    TrackingDAO pTrackingDAO;
    ArrayList displayDockets;
    ArrayList displayDctAssignments;
    ArrayList displayUniqueNumbers;
    private String BOOK_IN;
    private String BOOK_OUT;
    private String RESULT;
    private String SEARCH;
    private String REPORT;
    private String USER_SELECTION;
    String validationValues;
    String validationResult;
    int count;
    private String consignmentNumber;
    int conListCount;
    int docCount;
    int uniqueAssCount;
    String bookINErrorStatus;
    String bookOUTErrorStatus;
    String enteredUserAdresss;
    String CSDCode;
    String userID;
    User user;
    String host ;
    String webServiceURL ;
    String bookInOutURL ;
    private Pattern regexPattern;
	private Matcher regMatcher;
	List<Consignment> masterConsignmentList = new ArrayList<Consignment>();  
	List<Docket> masterDocketList = new ArrayList<Docket>();
	Map<String, String> mapUnqAssignments = new LinkedHashMap<String, String>();
    
	Map<String, String> processDocketList = new LinkedHashMap<String, String>();
	Map<String, String> processDocketAssignmentList = new LinkedHashMap<String, String>();
	Map<String, String> processAssignmentList = new LinkedHashMap<String, String>();
	
    public TrackingAction(){ 

    	pGateWay = new WebServiceGateWay();
        pTrackingDAO = new TrackingDAO(pGateWay);
        displayDockets = new ArrayList();
        displayDctAssignments = new ArrayList();
        displayUniqueNumbers = new ArrayList();
        BOOK_IN = "checkin";
        BOOK_OUT = "checkout";
        RESULT = "result";
        SEARCH = "search";
        REPORT = "report";
        USER_SELECTION = "userselection";
        count = 0;
        conListCount=0;
        docCount = 0;
        uniqueAssCount = 0;
        bookINErrorStatus = null;
        bookOUTErrorStatus = null;
    	
    }

    protected String prepareBackendSession(String sessionCookieValue) {
    	pGateWay.setBackEndSessionCookieValue(sessionCookieValue);
    	log.debug("pGateWay.setBackEndSessionCookieValue from String to "+sessionCookieValue);
    	return sessionCookieValue;
    }
    
    protected String prepareBackendSession() {
    	sessionManager = (SessionManager) ComponentManager.get(SessionManager.class);
        Session currentSession = sessionManager.getCurrentSession();
    	pGateWay.setBackEndSessionCookieValue(currentSession.getAttribute("backEndSessionCookieValue").toString());
    	//pGateWay.setBackEndSessionCookieName(currentSession.getAttribute("backEndSessionCookieName").toString());
    	log.debug("pGateWay.setBackEndSessionCookieValue from currentSession value to "+currentSession.getAttribute("backEndSessionCookieValue").toString());
    	return currentSession.getAttribute("backEndSessionCookieValue").toString();
    }


    public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception{ 
        
    	/** Edmund Enumerate through all parameters received
	  	 * @return **/
	    //getAllRequestParamaters(request, response);
 
    	TrackingForm pTrackingForm = (TrackingForm)form;
    	//log.debug("TrackingAction - "+pTrackingForm.getNovelUserId()+" - execute");
    	
    	if(pTrackingForm.getHostName() == null) {
	    	pTrackingForm.setHostName(request.getHeader("Host"));
	    	//log.debug("TrackingAction - "+pTrackingForm.getNovelUserId()+" - execute - Hostname "+pTrackingForm.getHostName());
	    	pTrackingForm.setHost(pTrackingForm.getHostName());
	    	if(pTrackingForm.getHost().contains("dev.int.unisa.ac.za")){
				pTrackingForm.setWebServiceURL("http://stratusdev.unisa.ac.za/aol/asp/sql_exec_report4.asp?export=XML&myid=CDTRACKING&ID=");
				webServiceURL = "http://stratusdev.unisa.ac.za/aol/asp/sql_exec_report4.asp?export=XML&myid=CDTRACKING&ID=";
				pTrackingForm.setBookInOutURL("http://stratusdev.unisa.ac.za/aol/asp/sql_exec_xml.asp?report=XML");
				bookInOutURL = "http://stratusdev.unisa.ac.za/aol/asp/sql_exec_xml.asp?report=XML";
				pGateWay.setBackEndSessionCookieName("svrstratusdevunisaaczaSessionId");
			}else if(pTrackingForm.getHost().contains("qa.int.unisa.ac.za")) {
				pTrackingForm.setWebServiceURL("http://stratusqa.unisa.ac.za/aol/asp/sql_exec_report4.asp?export=XML&myid=CDTRACKING&ID=");
				webServiceURL = "http://stratusqa.unisa.ac.za/aol/asp/sql_exec_report4.asp?export=XML&myid=CDTRACKING&ID=";
				pTrackingForm.setBookInOutURL("http://stratusqa.unisa.ac.za/aol/asp/sql_exec_xml.asp?report=XML");
				bookInOutURL = "http://stratusqa.unisa.ac.za/aol/asp/sql_exec_xml.asp?report=XML";
				pGateWay.setBackEndSessionCookieName("svrstratusqaunisaaczaSessionId");
			}else if(pTrackingForm.getHost().contains(".unisa.ac.za")){
				pTrackingForm.setWebServiceURL("https://stratus.unisa.ac.za/aol/asp/sql_exec_report4.asp?export=XML&myid=CDTRACKING&ID=");
				webServiceURL = "https://stratus.unisa.ac.za/aol/asp/sql_exec_report4.asp?export=XML&myid=CDTRACKING&ID=";
				pTrackingForm.setBookInOutURL("https://stratus.unisa.ac.za/aol/asp/sql_exec_xml.asp?report=XML");
				bookInOutURL = "https://stratus.unisa.ac.za/aol/asp/sql_exec_xml.asp?report=XML";
				pGateWay.setBackEndSessionCookieName("svrstratusunisaaczaSessionId");
			}else{
				pTrackingForm.setWebServiceURL("http://stratusdev.unisa.ac.za/aol/asp/sql_exec_report4.asp?export=XML&myid=CDTRACKING&ID=");
				webServiceURL = "http://stratusdev.unisa.ac.za/aol/asp/sql_exec_report4.asp?export=XML&myid=CDTRACKING&ID=";
				pTrackingForm.setBookInOutURL("http://stratusdev.unisa.ac.za/aol/asp/sql_exec_xml.asp?report=XML");
				bookInOutURL = "http://stratusdev.unisa.ac.za/aol/asp/sql_exec_xml.asp?report=XML";
				pGateWay.setBackEndSessionCookieName("svrstratusdevunisaaczaSessionId");
			}
    	}
    	
    	if(request.getParameter("act") == null){
            return getUserInfo(mapping, form, request, response);
        }else{
            return super.execute(mapping, form, request, response);
        }
    }

    protected Map<String,String> getKeyMethodMap()
    {
        Map<String,String> trackingMap = new HashMap<String,String>();
        
        //Buttons
        trackingMap.put("button.back", "back");
        trackingMap.put("button.clear", "clear");
        //trackingMap.put("button.report", "report");
        //trackingMap.put("button.searchDetail", "searchDetail");
        trackingMap.put("button.addCon", "validateConsignmentList");
        
        //Destination Addresses
        trackingMap.put("displayRegionalOfficeDSAAInformation","displayRegionalOfficeDSAAInformation");
        trackingMap.put("displayCollegeInformation", "displayCollegeInformation");
        trackingMap.put("displaySchoolInformation", "displaySchoolInformation");
        trackingMap.put("displayDepartmentInformation", "displayDepartmentInformation");
        trackingMap.put("displayModuleInformation", "displayModuleInformation");
        trackingMap.put("displayProvinceInformation", "displayProvinceInformation");
        trackingMap.put("displayRegionalOfficeInformation", "displayRegionalOfficeInformation");
        trackingMap.put("displayBuildingInformation", "displayBuildingInformation");
        trackingMap.put("displayUserAddress", "displayUserAddress");
        trackingMap.put("getSavedAddress", "getSavedAddress");
        trackingMap.put("displayUserList", "displayUserList");
        
        //Results & PDF
        trackingMap.put("download", "download");
        trackingMap.put("reportPDF", "reportPDF");
        
        trackingMap.put("getUserInfo", "getUserInfo");
        trackingMap.put("retrieveCSDInformation", "retrieveCSDInformation");

        //Input Validations
        trackingMap.put("validateConsignmentList", "validateConsignmentList");
        trackingMap.put("validateDocketNumber", "validateDocketNumber");
        trackingMap.put("validateStudentUniqueNumber", "validateStudentUniqueNumber");
        
        //Processing Book IN/OUT
        trackingMap.put("processInput", "processInput");
        trackingMap.put("result", "processPDF");
        
        //PrintPDF, Search & Reports
        trackingMap.put("printpdf", "findReportPDF");
        trackingMap.put("findReportPDF", "findReportPDF");
        trackingMap.put("getPDFtoPrint","getPDFtoPrint");
        trackingMap.put("search", "historySearch");
        trackingMap.put("report", "historyReport");
        
        trackingMap.put("searchDetail","searchDetail");
        trackingMap.put("searchPDF","searchPDF");
        return trackingMap;
    }



    //Get Consignment List, Cover Dockets and Assignments for search and results pages only

	public String displayConsignmentDetails (ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response, String outSelect) throws Exception {
		
    	ActionMessages messages = new ActionMessages();
		TrackingForm pTrackingForm = (TrackingForm)form;
		WebServiceGateWay pGateWay = new WebServiceGateWay();
    	String consignmentNumber = "";
    	int showConginmentInfo = 593166;
 	   	int showUniqueAssignments = 156154;
 	   	
        prepareBackendSession();

 	   //log.debug("TrackingAction - "+pTrackingForm.getNovelUserId()+" - displayConsignmentDetails - **************************************************************");
 	   	
    	try{
    		 
    		if (pTrackingForm.isSearch()){
    			 consignmentNumber = pTrackingForm.getSearchString();
    		}else{
    			consignmentNumber = pTrackingForm.getDisplayShipListNumber();
    		}
    		if(outSelect.equalsIgnoreCase("Result")){
    			showConginmentInfo = 889309; //697460;
         	   	showUniqueAssignments = 333995;
         	   //log.debug("TrackingAction - "+pTrackingForm.getNovelUserId()+" - displayConsignmentDetails - RESULT - consignmentNumber="+consignmentNumber+", outselect="+outSelect+", showConginmentInfo="+showConginmentInfo+", showUniqueAssignments="+showUniqueAssignments);
    		}else{
    			//log.debug("TrackingAction - "+pTrackingForm.getNovelUserId()+" - displayConsignmentDetails -SEARCH - consignmentNumber="+consignmentNumber+", outselect="+outSelect+", showConginmentInfo="+showConginmentInfo+", showUniqueAssignments="+showUniqueAssignments);
    		}
            
     	   	String userSelection = "";
	   		if (pTrackingForm.getSearchRadio()!= null){
	   			userSelection = "";
	   		}else{
	   			userSelection = pTrackingForm.getUserSelection().toUpperCase();
	   		}
	   		//log.debug("TrackingAction - "+pTrackingForm.getNovelUserId()+" - displayConsignmentDetails - userSelection="+userSelection);
	   		//Get Cover Dockets from Database/Web Service
	   		//log.debug("TrackingAction - "+pTrackingForm.getNovelUserId()+" - displayConsignmentDetails - **************************************************************");
     	   	//log.debug("TrackingAction - "+pTrackingForm.getNovelUserId()+" - displayConsignmentDetails - (showConsignmentInfo) - consignmentNumber="+consignmentNumber+", UserSelection="+userSelection.toUpperCase());
            ArrayList getValues = pGateWay.showConsignmentInfo(pTrackingForm.getWebServiceURL(),showConginmentInfo, consignmentNumber,"", "value1", "value2", "value3");
            Iterator it = getValues.iterator();
            String errMsgDct="";
            String errMsgStu="";
            int dispDctCounter=0;
            String checkDct="";
            if(it.hasNext()){
	            while(it.hasNext()){
	    			KeyValue test1 = (KeyValue) it.next();
	    			if(test1.getValue1().equals("No records returned")){
	    				errMsgDct = " Docket Numbers ";
	    				//log.debug("TrackingAction - "+pTrackingForm.getNovelUserId()+" - displayConsignmentDetails  - Docket Numbers: No records returned");
	    				//log.debug("TrackingAction - "+pTrackingForm.getNovelUserId()+" - displayConsignmentDetails - **************************************************************");
	    			 }else{
	    				 DocketNumberDetails details = new DocketNumberDetails();
	    				 details.setDocketNumber(test1.getValue1());
	    				 if (!checkDct.equals(test1.getValue1())){
	    					 dispDctCounter++;
	    				 }
	    				 details.setDocketID(dispDctCounter);
	    				 checkDct = test1.getValue1();
	    				 //log.debug("TrackingAction - "+pTrackingForm.getNovelUserId()+" - displayConsignmentDetails  - Docket Numbers Added="+dispDctCounter+"----"+test1.getValue1());
	    				 //log.debug("TrackingAction - "+pTrackingForm.getNovelUserId()+" - displayConsignmentDetails - **************************************************************");
	    				 //Get Assignments linked to Dockets
	    				 //log.debug("TrackingAction - "+pTrackingForm.getNovelUserId()+" - displayConsignmentDetails  - Docket Assignments: Successful records returned");
	    				 details.setStudentNumber(test1.getValue2());
	    				 details.setUniqueAssignmentNumber(test1.getValue3());
	    				 //log.debug("TrackingAction - "+pTrackingForm.getNovelUserId()+" - displayConsignmentDetails  - Docket Assignments="+test1.getValue2()+ "----" + test1.getValue3());
	    				 pTrackingForm.getDisplayDockets().add(details);
	    				 //log.debug("TrackingAction - "+pTrackingForm.getNovelUserId()+" - displayConsignmentDetails - **************************************************************");
	                 }	
	    		}
            }else{
            	errMsgDct = " Docket Numbers ";
				//log.debug("TrackingAction - "+pTrackingForm.getNovelUserId()+" - displayConsignmentDetails  - Docket Numbers: No records returned");
				//log.debug("TrackingAction - "+pTrackingForm.getNovelUserId()+" - displayConsignmentDetails - **************************************************************");
            }
            
            //Getting Unique Assignments
            //log.debug("TrackingAction - "+pTrackingForm.getNovelUserId()+" - displayConsignmentDetails - **************************************************************");
            //log.debug("TrackingAction - "+pTrackingForm.getNovelUserId()+" - displayConsignmentDetails (showUniqueAssignmentListInfo) - consignmentNumber="+consignmentNumber+", UserSelection="+userSelection.toUpperCase());
            ArrayList uniqueNumbers = pGateWay.showUniqueAssignmentListInfo(pTrackingForm.getWebServiceURL() ,showUniqueAssignments, consignmentNumber,userSelection.toUpperCase(),"value1", "value2", "value3");
            Iterator uniqueNumber = uniqueNumbers.iterator();
            if(uniqueNumber.hasNext()){
            	while(uniqueNumber.hasNext()){
	    			KeyValue test12 = (KeyValue) uniqueNumber.next();
	    			if(test12.getValue1().equals("No records returned")){
	    				errMsgStu = " Unique Assignments ";
	    				//log.debug("TrackingAction - "+pTrackingForm.getNovelUserId()+" - displayConsignmentDetails  - Unique Assignments: No records returned");
	    				//log.debug("TrackingAction - "+pTrackingForm.getNovelUserId()+" - displayConsignmentDetails - **************************************************************");
	   			 	}else{
	   			 		//log.debug("TrackingAction - "+pTrackingForm.getNovelUserId()+" - displayConsignmentDetails  - Unique Assignments: Successful records returned");
	   			 		DocketNumberDetails stuDetails = new DocketNumberDetails();
	   			 		stuDetails.setStudentNumber(test12.getValue1());
	   			 		//log.debug("TrackingAction - "+pTrackingForm.getNovelUserId()+" - displayConsignmentDetails  - Unique Assignments="+test12.getValue1()+ "----" + test12.getValue2());
	   			 		stuDetails.setUniqueAssignmentNumber(test12.getValue2());
	   			 		//stuDetails.setRemove1(true);
	   			 		pTrackingForm.getDisplayUniqueNumbers().add(stuDetails);
	   			 		//log.debug("TrackingAction - "+pTrackingForm.getNovelUserId()+" - displayConsignmentDetails - **************************************************************");
	   			 	}
	            }
            }else{
            	errMsgStu = " Unique Assignments ";
				//log.debug("TrackingAction - "+pTrackingForm.getNovelUserId()+" - displayConsignmentDetails  - Unique Assignments: No records returned");
				//log.debug("TrackingAction - "+pTrackingForm.getNovelUserId()+" - displayConsignmentDetails - **************************************************************");
            }
            //log.debug("TrackingAction - "+pTrackingForm.getNovelUserId()+" - displayConsignmentDetails - Setting up Form Variables for Report Display");
            pTrackingForm.setDisplayDocketsForConsignment(pTrackingForm.getDisplayDockets());
            pTrackingForm.setDisplayDctAssignmentsForConsignment(pTrackingForm.getDisplayDctAssignments());
            pTrackingForm.setDisplayUniqueNumbersForConsignment(pTrackingForm.getDisplayUniqueNumbers());
            /*
            //log.debug("TrackingAction - "+pTrackingForm.getNovelUserId()+" - displayConsignmentDetails  - Error Checking - Dockets: "+errMsgDct+", Student: "+errMsgStu);
            //log.debug("TrackingAction - "+pTrackingForm.getNovelUserId()+" - displayConsignmentDetails  - Error Checking Sizes - Dockets: "+pTrackingForm.getDisplayDockets().size()+", Student: "+pTrackingForm.getDisplayUniqueNumbers().size());
            
            if(pTrackingForm.getDisplayDockets().size()== 0 && pTrackingForm.getDisplayUniqueNumbers().size()== 0){ 
	            if(!errMsgDct.equals("") || !errMsgStu.equals("")){
	            	//log.debug("TrackingAction - "+pTrackingForm.getNovelUserId()+" - displayConsignmentDetails  - Error Checking");
	            	String newErrorMsg = "Consignment list " + consignmentNumber + " doesn't contain any ";
	            	StringBuilder newError;
	            	if(!errMsgDct.equals("") && (errMsgStu.equals("") && pTrackingForm.getDisplayUniqueNumbers().isEmpty())){
	            		//log.debug("TrackingAction - "+pTrackingForm.getNovelUserId()+" - displayConsignmentDetails  - Docket Error");
	            		newError = (new StringBuilder()).append(newErrorMsg).append(errMsgDct);
	            	}else if(errMsgDct.equals("") && !errMsgStu.equals("")){
	            		//log.debug("TrackingAction - "+pTrackingForm.getNovelUserId()+" - displayConsignmentDetails  - Student Error");
	            		newError = (new StringBuilder()).append(newErrorMsg).append(errMsgStu);
	            	}else{
	            		//log.debug("TrackingAction - "+pTrackingForm.getNovelUserId()+" - displayConsignmentDetails  - Docket & Student Error");
	            		newError = (new StringBuilder()).append(newErrorMsg).append(errMsgDct).append("or").append(errMsgStu);
	            	}
	            	//log.debug("TrackingAction - "+pTrackingForm.getNovelUserId()+" - displayConsignmentDetails  - Final Error: "+newError);
	            	messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage("message.generalmessage", newError));
	                addErrors(request, messages);
	            }
            }
            */
            //log.debug("TrackingAction - "+pTrackingForm.getNovelUserId()+" - displayConsignmentDetails  - Final Setting DisplayForConsignment");
            
            String processType =  pTrackingForm.getUserSelection().toUpperCase();
	    	
            //log.debug("TrackingAction - "+pTrackingForm.getNovelUserId()+" - displayConsignmentDetails  - Final processType="+processType);
	    	if (processType.equalsIgnoreCase("OUT")){
	        	try{
	        		//log.debug("TrackingAction - "+pTrackingForm.getNovelUserId()+" - displayConsignmentDetails - **************************************************************");
	        		boolean checkEmail = false;
	        		//log.debug("TrackingAction - "+pTrackingForm.getNovelUserId()+" - displayConsignmentDetails - Check if Destination is Person or Manual with Email address, then send email");
			        if (pTrackingForm.getUsers() != null && !pTrackingForm.getUsers().equals("") && !pTrackingForm.getUsers().equals("0")){
			        	checkEmail = true;
			        }else if (pTrackingForm.getCsdmUsers() != null && !pTrackingForm.getCsdmUsers().equals("") && !pTrackingForm.getCsdmUsers().equals("0")){
			        	checkEmail = true;
			        }else if (pTrackingForm.getBuildingUsers() != null && !pTrackingForm.getBuildingUsers().equals("") && !pTrackingForm.getBuildingUsers().equals("0")){
			        	checkEmail = true;
			        }else if (pTrackingForm.getAddressType().equalsIgnoreCase("MANUAL") && pTrackingForm.getDisplayEmail() != null && !pTrackingForm.getDisplayEmail().equals("") ){
			        	checkEmail = true;
			        }
			        if (checkEmail){
		        		//log.debug("TrackingAction - "+pTrackingForm.getNovelUserId()+" - displayConsignmentDetails  - Is User or valid Email Address,  Send Notification Email");
		        		String emailResult = sendNotificationToUser(pTrackingForm);
		        		//log.debug("TrackingAction - "+pTrackingForm.getNovelUserId()+" - displayConsignmentDetails  - Email Result="+emailResult);
			        }
	        		//log.debug("TrackingAction - "+pTrackingForm.getNovelUserId()+" - displayConsignmentDetails - **************************************************************");
	        	}catch(Exception ex){
	                messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage("message.generalmessage", "Email notification to destination user failed. Please try again."));
	                addErrors(request, messages);
	            }
            }
        	
        }catch(Exception ex){
            messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage("message.generalmessage", "Web Server not responding. Please try again."));
            addErrors(request, messages);
        }
        return null;
    }

    public ActionForward processPDF(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception{
    	TrackingForm pTrackingForm = (TrackingForm)form;
    	try{
    		String pdffullPath = getServlet().getServletContext().getInitParameter("applicationPDFFullPath");
    		//log.debug("TrackingAction - "+pTrackingForm.getNovelUserId()+" - processPDF - pdffullPath="+pdffullPath);
    		//log.debug("TrackingAction - "+pTrackingForm.getNovelUserId()+" - processPDF - Calling PDFTableGenerator");
    		new PDFTableGenerator().generatePDF(form,pdffullPath);
    		
    		//List PDF Files
    		FindFile filer = new FindFile();
    		String pdfPath = getServlet().getServletContext().getInitParameter("applicationPDFFullPath");
			String pdfFileName = pTrackingForm.getReportPDFToDownload();
			
			//log.debug("TrackingAction - "+pTrackingForm.getNovelUserId()+" - processPDF - pdfPath="+pdfPath);
			//log.debug("TrackingAction - "+pTrackingForm.getNovelUserId()+" - processPDF - pdfFile="+pdfPath+"/"+pdfFileName);
			
			/*Debug
    		filer.searchFile(pdfPath, pdfFileName, pTrackingForm.getNovelUserId());
    		*/    		
    		
        }catch(Exception e){
    		//log.debug("TrackingAction - "+pTrackingForm.getNovelUserId()+" - processPDF - CreatePDF Crashed / "+e);

 		}
    	//log.debug("TrackingAction - "+pTrackingForm.getNovelUserId()+" - processPDF - GoTo Result");
    	return mapping.findForward("result");
    }
    		
    public ActionForward findReportPDF(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception{

    	TrackingForm pTrackingForm = (TrackingForm)form;
		ActionMessages messages = new ActionMessages();
		
		//log.debug("TrackingAction - "+pTrackingForm.getNovelUserId()+" - findReportPDF");

		String pdfPath = getServlet().getServletContext().getInitParameter("applicationPDFFullPath");
		String pdfFileName = pTrackingForm.getReportPDFToDownload();
		
		//log.debug("TrackingAction - "+pTrackingForm.getNovelUserId()+" - findReportPDF - pdf File Path="+pdfPath);
    	 
		File folder = new File(pdfPath);
		JSONObject listFilesObj = new JSONObject();
		int fileCount = 0;
		int fileFound = 0;
		
		try{
	       	Map<String, String> mapListFiles = new LinkedHashMap<String, String>();
	 
	       	String queryINOUT = request.getParameter("printRadio");
	       	String queryShip = request.getParameter("printStringShip");
	       	String queryUser = request.getParameter("printStringUser");
	       	String queryDateStart = request.getParameter("dateStart");
	       	String queryDateEnd = request.getParameter("dateEnd");
	       	String queryShipChk = request.getParameter("printShipChk");
	       	String queryUserChk = request.getParameter("printUserChk");
	       	
	       	//log.debug("TrackingAction - "+pTrackingForm.getNovelUserId()+" - findReportPDF - queryINOUT="+queryINOUT);
	       	//log.debug("TrackingAction - "+pTrackingForm.getNovelUserId()+" - findReportPDF - printShipChk="+queryShipChk);
	       	//log.debug("TrackingAction - "+pTrackingForm.getNovelUserId()+" - findReportPDF - printUserChk="+queryUserChk);
	       	//log.debug("TrackingAction - "+pTrackingForm.getNovelUserId()+" - findReportPDF - queryShip="+queryShip);
	       	//log.debug("TrackingAction - "+pTrackingForm.getNovelUserId()+" - findReportPDF - queryUser="+queryUser);
	       	//log.debug("TrackingAction - "+pTrackingForm.getNovelUserId()+" - findReportPDF - queryDateStart="+queryDateStart);
	       	//log.debug("TrackingAction - "+pTrackingForm.getNovelUserId()+" - findReportPDF - queryDateEnd="+queryDateEnd);
	       	
	       	ArrayList<String> fileFilter = new ArrayList<String>();
	       	if (queryINOUT != null && (queryINOUT.equals("IN") || queryINOUT.equals("OUT"))){
	       		String filterText = "";
	       		if (queryINOUT.equals("IN")){ 
	       			filterText = "_IN_";
	       		}else if (queryINOUT.equals("OUT")){ 
	       			filterText = "_OUT_";
	       		}
	       		fileFilter.add(filterText);
	       	}
	       	if (queryShipChk != null && queryShip != null && (queryShipChk.equalsIgnoreCase("OK"))){
				if (!queryShip.isEmpty() && !"".equals(queryShip) && queryShip != null){
					String filterText = "_"+queryShip+"_";
					fileFilter.add(filterText);
				}
	       	}
	       	if (queryUserChk != null && queryUser != null && (queryUserChk.equalsIgnoreCase("OK"))){
				if (!queryUser.isEmpty() && !"".equals(queryUser) && queryUser != null){
					String filterText = "_"+queryUser+"_";
					fileFilter.add(filterText);
				}
	       	}
	       	//ToDo Add Date
	       	DateFormat originalFormat = new SimpleDateFormat("yyyy/MM/dd", Locale.ENGLISH);
	       	DateFormat targetFormat = new SimpleDateFormat("yyyyMMdd");
	       	
	       	Date dateStart = originalFormat.parse(queryDateStart);
	       	String formattedDateStart = targetFormat.format(dateStart);  // 201270312
	       	Date dateEnd = originalFormat.parse(queryDateEnd);
	       	String formattedDateEnd = targetFormat.format(dateEnd);  // 201270312
       		//log.debug("TrackingAction - "+pTrackingForm.getNovelUserId()+" - findReportPDF - Dates="+formattedDateStart+"-"+formattedDateEnd);

       		List<Date> dateList = new ArrayList<Date>(25);
       		List<String> stringDates = new ArrayList<String>(dateList.size());
       		if (dateStart.compareTo(dateEnd) == 0) {
	       		//log.debug("TrackingAction - "+pTrackingForm.getNovelUserId()+" - findReportPDF - Date the Same="+formattedDateStart+"-"+formattedDateEnd);
	       	}else{
	       		//log.debug("TrackingAction - "+pTrackingForm.getNovelUserId()+" - findReportPDF - Date Not the Same="+formattedDateStart+"-"+formattedDateEnd);

		         Calendar cal = Calendar.getInstance();
		         cal.setTime(dateStart);
		         while (cal.getTime().before(dateEnd)) {
		             cal.add(Calendar.DATE, 1);
		             dateList.add(cal.getTime());
		         }       
		         //Debug
		         for (int i=0; i < dateList.size(); i++){
	    	       		//log.debug("TrackingAction - "+pTrackingForm.getNovelUserId()+" - findReportPDF - dateList=("+i+")"+dateList.get(i)+" ");
	    	       	}
		         
		         for (Date mdates : dateList){
		             String newDates = targetFormat.format(mdates);
		             stringDates.add(String.valueOf(newDates));
		         }
	           //Debug
                for (int i=0; i < stringDates.size(); i++){
    	       		//log.debug("TrackingAction - "+pTrackingForm.getNovelUserId()+" - findReportPDF - stringDates=("+i+")"+stringDates.get(i)+" ");
    	       	}
	       	}

	        //log.debug("TrackingAction - "+pTrackingForm.getNovelUserId()+" - findReportPDF - Get fileFilter Array Values");
	       	for (int x=0; x < fileFilter.size(); x++){
	       		//log.debug("TrackingAction - "+pTrackingForm.getNovelUserId()+" - findReportPDF - fileFilter=("+x+")"+fileFilter.get(x)+" ");
	       	}
	       	
			if (folder.isDirectory()) {
				File[] listOfFiles = folder.listFiles();
				if (listOfFiles.length < 1){
					//log.debug("TrackingAction - "+pTrackingForm.getNovelUserId()+" - findReportPDF - There is no File inside Folder");
					listFilesObj.put("Info","No files matched your search criteria! Please try again");
				}else{
					//log.debug("TrackingAction - "+pTrackingForm.getNovelUserId()+" - findReportPDF - List of Files & Folder");
					
					try{
						for (File file : listOfFiles) {
							if(!file.isDirectory() && file.getName().contains("UNISA_HardCopy_Tracking") && file.getName().contains(".pdf")){
								//log.debug("TrackingAction - "+pTrackingForm.getNovelUserId()+" - findReportPDF - File All=" +file.getCanonicalPath().toString());
								//Filter Files
								if (fileFilter.size() == 1){
									if (file.getName().contains(fileFilter.get(0))){
										//log.debug("TrackingAction - "+pTrackingForm.getNovelUserId()+" - findReportPDF - Filter  1 - File Found=" +file.getCanonicalPath().toString());
										if (dateStart.compareTo(dateEnd) == 0) {
											//log.debug("TrackingAction - "+pTrackingForm.getNovelUserId()+" - findReportPDF - Filter  1 - Dates the same -"+formattedDateStart);
											if (file.getName().contains("_"+formattedDateStart+"_")){
												//log.debug("TrackingAction - "+pTrackingForm.getNovelUserId()+" - findReportPDF - Filter  1 - File Found by Date=" +file.getCanonicalPath().toString());
												String fileNameWithOutExt = FilenameUtils.removeExtension(file.getName());
												mapListFiles.put(Integer.toString(fileFound), fileNameWithOutExt);
												fileFound++;
											}	
										}else if (dateStart.compareTo(dateEnd)  < 0) {
											//log.debug("TrackingAction - "+pTrackingForm.getNovelUserId()+" - findReportPDF - Filter  1 - Dates Not the same");
											for (int i=0; i < stringDates.size(); i++){
							    	       		//log.debug("TrackingAction - "+pTrackingForm.getNovelUserId()+" - findReportPDF - Filter  1 - stringDates=("+i+")"+stringDates.get(i)+" ");
												if (file.getName().contains("_"+stringDates.get(i)+"_")){
													//log.debug("TrackingAction - "+pTrackingForm.getNovelUserId()+" - findReportPDF - Filter  1 - File Found Date ("+i+")=" +file.getCanonicalPath().toString());
													String fileNameWithOutExt = FilenameUtils.removeExtension(file.getName());
													mapListFiles.put(Integer.toString(fileFound), fileNameWithOutExt);
													fileFound++;
								    	       	}
											}	
										}
									}
								}else if (fileFilter.size() == 2){
									if (file.getName().contains(fileFilter.get(0)) && file.getName().contains(fileFilter.get(1))){
										//log.debug("TrackingAction - "+pTrackingForm.getNovelUserId()+" - findReportPDF - Filter  2 - File Found=" +file.getCanonicalPath().toString());
										if (dateStart.compareTo(dateEnd) == 0) {
											//log.debug("TrackingAction - "+pTrackingForm.getNovelUserId()+" - findReportPDF - Filter  2 - Dates the same -"+formattedDateStart);
											if (file.getName().contains("_"+formattedDateStart+"_")){
												//log.debug("TrackingAction - "+pTrackingForm.getNovelUserId()+" - findReportPDF - Filter  2 - File Found by Date=" +file.getCanonicalPath().toString());
												String fileNameWithOutExt = FilenameUtils.removeExtension(file.getName());
												mapListFiles.put(Integer.toString(fileFound), fileNameWithOutExt);
												fileFound++;
											}	
										}else if (dateStart.compareTo(dateEnd)  < 0) {
											//log.debug("TrackingAction - "+pTrackingForm.getNovelUserId()+" - findReportPDF - Filter  2 - Dates Not the same");
											for (int i=0; i < stringDates.size(); i++){
							    	       		//log.debug("TrackingAction - "+pTrackingForm.getNovelUserId()+" - findReportPDF - Filter  2 - stringDates=("+i+")"+stringDates.get(i)+" ");
												if (file.getName().contains("_"+stringDates.get(i)+"_")){
													//log.debug("TrackingAction - "+pTrackingForm.getNovelUserId()+" - findReportPDF - Filter  2 - File Found Date ("+i+")=" +file.getCanonicalPath().toString());
													String fileNameWithOutExt = FilenameUtils.removeExtension(file.getName());
													mapListFiles.put(Integer.toString(fileFound), fileNameWithOutExt);
													fileFound++;
								    	       	}
											}	
										}
									}
								}else if (fileFilter.size() == 3){
									if (file.getName().contains(fileFilter.get(0)) && file.getName().contains(fileFilter.get(1)) && file.getName().contains(fileFilter.get(2))){
										//log.debug("TrackingAction - "+pTrackingForm.getNovelUserId()+" - findReportPDF - Filter  3 - File Found=" +file.getCanonicalPath().toString());
										if (dateStart.compareTo(dateEnd) == 0) {
											//log.debug("TrackingAction - "+pTrackingForm.getNovelUserId()+" - findReportPDF - Filter  3 - Dates the same -"+formattedDateStart);
											if (file.getName().contains("_"+formattedDateStart+"_")){
												//log.debug("TrackingAction - "+pTrackingForm.getNovelUserId()+" - findReportPDF - Filter  3 - File Found by Date=" +file.getCanonicalPath().toString());
												String fileNameWithOutExt = FilenameUtils.removeExtension(file.getName());
												mapListFiles.put(Integer.toString(fileFound), fileNameWithOutExt);
												fileFound++;
											}	
										}else if (dateStart.compareTo(dateEnd)  < 0) {
											//log.debug("TrackingAction - "+pTrackingForm.getNovelUserId()+" - findReportPDF - Filter  3 - Dates Not the same");
											for (int i=0; i < stringDates.size(); i++){
							    	       		//log.debug("TrackingAction - "+pTrackingForm.getNovelUserId()+" - findReportPDF - Filter  3 - stringDates=("+i+")"+stringDates.get(i)+" ");
												if (file.getName().contains("_"+stringDates.get(i)+"_")){
													//log.debug("TrackingAction - "+pTrackingForm.getNovelUserId()+" - findReportPDF - Filter  3 - File Found Date ("+i+")=" +file.getCanonicalPath().toString());
													String fileNameWithOutExt = FilenameUtils.removeExtension(file.getName());
													mapListFiles.put(Integer.toString(fileFound), fileNameWithOutExt);
													fileFound++;
								    	       	}
											}	
										}
									}
								}else{
									//log.debug("TrackingAction - "+pTrackingForm.getNovelUserId()+" - findReportPDF - File All=" +file.getCanonicalPath().toString());
									if (dateStart.compareTo(dateEnd) == 0) {
										//log.debug("TrackingAction - "+pTrackingForm.getNovelUserId()+" - findReportPDF - File All - Dates the same -"+formattedDateStart);
										if (file.getName().contains("_"+formattedDateStart+"_")){
											//log.debug("TrackingAction - "+pTrackingForm.getNovelUserId()+" - findReportPDF - File All - File Found by Date=" +file.getCanonicalPath().toString());
											String fileNameWithOutExt = FilenameUtils.removeExtension(file.getName());
											mapListFiles.put(Integer.toString(fileFound), fileNameWithOutExt);
											fileFound++;
										}	
									}else if (dateStart.compareTo(dateEnd)  < 0) {
										//log.debug("TrackingAction - "+pTrackingForm.getNovelUserId()+" - findReportPDF - File All - Dates Not the same");
										for (int i=0; i < stringDates.size(); i++){
						    	       		//log.debug("TrackingAction - "+pTrackingForm.getNovelUserId()+" - findReportPDF - File All - stringDates=("+i+")"+stringDates.get(i)+" ");
											if (file.getName().contains("_"+stringDates.get(i)+"_")){
												//log.debug("TrackingAction - "+pTrackingForm.getNovelUserId()+" - findReportPDF - File All - File Found Date ("+i+")=" +file.getCanonicalPath().toString());
												String fileNameWithOutExt = FilenameUtils.removeExtension(file.getName());
												mapListFiles.put(Integer.toString(fileFound), fileNameWithOutExt);
												fileFound++;
							    	       	}
										}	
									}
								}
							}
							fileCount++;
						}
					 }catch(Exception ex){
						 listFilesObj.put("Error","The PDF File Listing Failed! Please try again / <br/>"+ex);
				     }
					listFilesObj.put("PFDFiles",mapListFiles);
				}
			}else{
				listFilesObj.put("Error","There is no Folder at given path! Please try again / <br/>");
				//log.debug("There is no Folder @ given path :" + pdfPath);
			}
		}catch (Exception e){
			listFilesObj.put("Error","searchFile Crashed! Please try again / <br/>"+e);
			//log.debug("TrackingAction - "+pTrackingForm.getNovelUserId()+" - findReportPDF Crashed / "+e);
		}
		if (fileFound == 0){
			listFilesObj.put("Info","No files match your search criteria! Please try again");
		}
		// Convert the map to json
		PrintWriter out = response.getWriter();
		JSONObject jsonObject = JSONObject.fromObject(listFilesObj);
		//log.debug("TrackingAction - "+pTrackingForm.getNovelUserId()+" - findReportPDF - Final - **************************************************************");
		//log.debug("TrackingAction - "+pTrackingForm.getNovelUserId()+" - findReportPDF - Final - jsonObject="+jsonObject.toString());
		//log.debug("TrackingAction - "+pTrackingForm.getNovelUserId()+" - findReportPDF - Final - **************************************************************");
		out.write(jsonObject.toString());
		out.flush();

		return null; //Returning null to prevent struts from interfering with ajax/json
    }
    
	public ActionForward getPDFtoPrint(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)throws Exception {

		TrackingForm pTrackingForm = (TrackingForm)form;
		ActionMessages messages = new ActionMessages();
		
		//log.debug("TrackingAction - "+pTrackingForm.getNovelUserId()+" - getPDFtoPrint");

		String pdfFileName = request.getParameter("pdfFileName").trim();
		String pdfPath = getServlet().getServletContext().getInitParameter("applicationPDFFullPath");
		
		//log.debug("TrackingAction - "+pTrackingForm.getNovelUserId()+" - getPDFtoPrint - pdfFile="+pdfPath+"/"+pdfFileName);

		try{
			if (pdfPath == null || "".equals(pdfPath) || pdfFileName == null || "".equals(pdfFileName)){
				//log.debug("TrackingAction - "+pTrackingForm.getNovelUserId()+" - getPDFtoPrint - PDF Path or FileName Empty");
	           	messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("message.generalmessage", "PDF Not found. Please try again."));
            	addErrors(request, messages);
            	return mapping.findForward("printpdf");
			}else{
				PdfDownloader pdfViewer=new PdfDownloader();
				pdfViewer.processPDFRequest(pdfPath, pdfFileName, response);
			}
		}catch (Exception e){
			//log.debug("TrackingAction - "+pTrackingForm.getNovelUserId()+" - getPDFtoPrint - Error Occurred recalling PDF / "+e);
		}
		
		//log.debug("TrackingAction - "+pTrackingForm.getNovelUserId()+" - getPDFtoPrint - Done");
		
		return null; //Returning null to prevent struts from interfering with ajax/json
	}
	
	public ActionForward reportPDF(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)throws Exception {

			TrackingForm pTrackingForm = (TrackingForm)form;
			ActionMessages messages = new ActionMessages();
			
			//log.debug("TrackingAction - "+pTrackingForm.getNovelUserId()+" - reportPDF");

			String pdfPath = getServlet().getServletContext().getInitParameter("applicationPDFFullPath");
			String pdfFileName = pTrackingForm.getReportPDFToDownload();
			
			//log.debug("TrackingAction - "+pTrackingForm.getNovelUserId()+" - reportPDF - pdfFile="+pdfPath+"/"+pdfFileName);
		
			try{
				if (pdfPath == null || "".equals(pdfPath) || pdfFileName == null || "".equals(pdfFileName)){
					//log.debug("TrackingAction - "+pTrackingForm.getNovelUserId()+" - reportPDF - PDF Path or FileName Empty");
		           	messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("message.generalmessage", "PDF Not found. Please try again."));
	            	addErrors(request, messages);
	            	return mapping.findForward("result");
				}
				PdfDownloader pdfViewer=new PdfDownloader();
				pdfViewer.processPDFRequest(pdfPath, pdfFileName, response);
			}catch (Exception e){
				//log.debug("TrackingAction - "+pTrackingForm.getNovelUserId()+" - reportPDF - Error Occurred generating PDF / "+e);
			}
			//log.debug("TrackingAction - "+pTrackingForm.getNovelUserId()+" - reportPDF - Done");
		return null;
	}
	
    	
    public ActionForward displayUserList(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception{
		
        prepareBackendSession();
    	TrackingForm pTrackingForm = (TrackingForm)form;
    	//WebServiceGateWay pGateWay = new WebServiceGateWay();
	//log.debug("TrackingAction - "+pTrackingForm.getNovelUserId()+" - displayUserList - **************************************************************");
		
		int keyCounter = 0;
		JSONObject userObj = new JSONObject();
	   	Map<String, String> mapUsers = new LinkedHashMap<String, String>();
	   	ArrayList<KeyValue> getValues = null;

		HttpSession session = request.getSession(true);
		
		String searchCheck = pTrackingForm.getSearchCheck().trim();
		//log.debug("TrackingAction - "+pTrackingForm.getNovelUserId()+" - displayUserList - SearchCheck="+searchCheck);
		//log.debug("TrackingAction - "+pTrackingForm.getNovelUserId()+" - displayUserList - **************************************************************");
        try{
            int webserviceId = 18428;
            if (pTrackingForm.getSearchCheck().equalsIgnoreCase("Surname") || pTrackingForm.getSearchCheck().equalsIgnoreCase("PersNo")){
	            if(searchCheck.equalsIgnoreCase("Surname") && pTrackingForm.getSurname() != null && pTrackingForm.getSurname().length() != 0){
	            	//log.debug("TrackingAction - "+pTrackingForm.getNovelUserId()+" - displayUserList - getUserList - Surname="+pTrackingForm.getSurname());
	               	getValues = pGateWay.getUserList(pTrackingForm.getWebServiceURL() ,webserviceId, "value1", "value2", "value3", "value4", "value5", "value6", pTrackingForm.getSurname());
	               	//log.debug("TrackingAction - "+pTrackingForm.getNovelUserId()+" - displayUserList - getUserList - Surname="+pTrackingForm.getSurname()+" - After Webservice");
	            }else if (searchCheck.equalsIgnoreCase("PersNo") && pTrackingForm.getSearchPersNo() != null && pTrackingForm.getSearchPersNo().length() != 0){
	            	//log.debug("TrackingAction - "+pTrackingForm.getNovelUserId()+" - displayUserList - getUserList - PersNo="+pTrackingForm.getSearchPersNo());
	               	getValues = pGateWay.getUserList1(pTrackingForm.getWebServiceURL() ,webserviceId, "value1", "value2", "value3", "value4", "value5", "value6", pTrackingForm.getSearchPersNo());
	            }else{
	            	userObj.put("Empty", "No Search criteria defined. Please Enter a partial or full Surname");
	            }
            }else if (pTrackingForm.getSearchCheck().equalsIgnoreCase("College")){
	            if(pTrackingForm.getCollege() != null || pTrackingForm.getCollege().equals("-1")){
	            	//log.debug("TrackingAction - "+pTrackingForm.getNovelUserId()+" - displayUserList - College="+pTrackingForm.getCollege());
	            	String collegeID = "";
	            	String schoolID = "";
	            	String departmentID = "";
	            	String moduleID = "";
	            	if (pTrackingForm.getCollege() != null && !pTrackingForm.getCollege().equals("-1")){
	            		collegeID = pTrackingForm.getCollege();
	            	}
	            	if (pTrackingForm.getSchool() != null && !pTrackingForm.getSchool().equals("-1")){
	            		schoolID = pTrackingForm.getSchool();
	            	}
	            	if (pTrackingForm.getDepartment() != null && !pTrackingForm.getDepartment().equals("-1")){
	            		departmentID = pTrackingForm.getDepartment();
	            	}
	            	if (pTrackingForm.getModule() != null && !pTrackingForm.getModule().equals("-1")){
	            		moduleID = pTrackingForm.getModule();
	            	}
	            	//log.debug("TrackingAction - "+pTrackingForm.getNovelUserId()+" - displayUserList - getUserList2 - collegeID="+collegeID+", schoolID="+schoolID+", departmentID="+departmentID+"; moduleID="+moduleID);
	               	getValues = pGateWay.getUserList2(pTrackingForm.getWebServiceURL(),webserviceId, "value1", "value2", "value3", "value4", "value5", "value6", collegeID, schoolID, departmentID, moduleID);
	            }else{
	               	userObj.put("Empty", "No Search criteria defined. Please select at least a College");
            	}
            }else if (pTrackingForm.getSearchCheck().equalsIgnoreCase("Province")){
            	if(pTrackingForm.getProvince() != null || pTrackingForm.getProvince().equals("-1")){
	            	//log.debug("TrackingAction - "+pTrackingForm.getNovelUserId()+" - displayUserList - Province="+pTrackingForm.getProvince());
	            	String provinceID = "";
	            	String regionID = "";
	            	if (!pTrackingForm.getProvince().equals("-1")){
	            		provinceID = pTrackingForm.getProvince();
	            	}
	            	if (!pTrackingForm.getRegion().equals("-1")){
	            		regionID = pTrackingForm.getRegion();
	            	}
	            	if (regionID.equals("") && provinceID.equals("")){
	            		//log.debug("TrackingAction - "+pTrackingForm.getNovelUserId()+" - displayUserList - No College Selected");
		    			userObj.put("Empty", "No Search criteria defined. Please select a Province or Regional Office");
	            	}
	            	//log.debug("TrackingAction - "+pTrackingForm.getNovelUserId()+" - displayUserList - getUserList3 - provinceID="+provinceID+", regionID="+regionID);
	               	getValues = pGateWay.getUserList3(pTrackingForm.getWebServiceURL() ,webserviceId, "value1", "value2", "value3", "value4", "value5", "value6", provinceID, regionID);
            	}else{
            		userObj.put("Empty", "No Search criteria defined. Please select at least a Province");
            	}
            }else if (pTrackingForm.getSearchCheck().equalsIgnoreCase("Building")){
            	if (pTrackingForm.getBuilding() != null){
	            	//log.debug("TrackingAction - "+pTrackingForm.getNovelUserId()+" - displayUserList - getUserList4 - Building="+pTrackingForm.getBuilding());
	            	String buildingName = pTrackingForm.getBuilding().replaceAll("_", " ");
	            	getValues = pGateWay.getUserList4(pTrackingForm.getWebServiceURL() ,webserviceId, "value1", "value2", "value3", "value4", "value5", "value6", buildingName);
            	}else{
            		userObj.put("Empty", "No Search criteria defined. Please select a Building");
            	}
            }else{
        		userObj.put("Empty", "No Search criteria defined.");
            }
            
            //log.debug("TrackingAction - "+pTrackingForm.getNovelUserId()+" - displayUserList - Getting Here - Testing getValues");
            if (!getValues.isEmpty()){
	    		Iterator<KeyValue> it = getValues.iterator();
	    		//log.debug("TrackingAction - "+pTrackingForm.getNovelUserId()+" - displayUserList - Iterator");
	    		
	    		if (it.hasNext()){
	    			
		    		while(it.hasNext()){
		    			//log.debug("TrackingAction - "+pTrackingForm.getNovelUserId()+" - displayUserList - Result Iterator Has Next");
		    			String userKey = "";
		    			StringBuilder userValue = new StringBuilder();
		    			
						KeyValue test = (KeyValue) it.next();
						//log.debug("TrackingAction - "+pTrackingForm.getNovelUserId()+" - displayUserList - Result StaffNumber="+test.getValue1());
						userKey = test.getValue1().trim().toString();
	
						if(test.getValue2() != null &&  !test.getValue2().isEmpty()){
							if(!test.getValue2().equals("null")){
								//log.debug("TrackingAction - "+pTrackingForm.getNovelUserId()+" - displayUserList - Result Surname="+test.getValue2());
								userValue = (new StringBuilder()).append(userValue).append(test.getValue2().trim().toString());
							}else{
								userValue = (new StringBuilder()).append(userValue).append("");
							}
						}else{
							userValue = (new StringBuilder()).append(userValue).append("");
						}
						if(test.getValue3() != null &&  !test.getValue3().isEmpty()){
							if(!test.getValue3().equals("null")){
								//log.debug("TrackingAction - "+pTrackingForm.getNovelUserId()+" - displayUserList - Result Initials="+test.getValue3());
								userValue = (new StringBuilder()).append(userValue).append("~").append(test.getValue3().trim().toString());
							}else{
								userValue = (new StringBuilder()).append(userValue).append("~").append("");
							}
						}else{
							userValue = (new StringBuilder()).append(userValue).append("~").append("");
						}
						if(test.getValue4() != null &&  !test.getValue4().isEmpty()){
							if(!test.getValue4().equals("null")){
								//log.debug("TrackingAction - "+pTrackingForm.getNovelUserId()+" - displayUserList - Result Title="+test.getValue4());
								userValue = (new StringBuilder()).append(userValue).append("~").append(test.getValue4().trim().toString());
							}else{
								userValue = (new StringBuilder()).append(userValue).append("~").append("");
							}
						}else{
							userValue = (new StringBuilder()).append(userValue).append("~").append("");
						}
						if(test.getValue5() != null &&  !test.getValue5().isEmpty()){
							if(!test.getValue5().equals("null")){
								//log.debug("TrackingAction - "+pTrackingForm.getNovelUserId()+" - displayUserList - Result FirstNames="+test.getValue5());
								userValue = (new StringBuilder()).append(userValue).append("~").append(test.getValue5().trim().toString());
							}else{
								userValue = (new StringBuilder()).append(userValue).append("~").append("");
							}
						}else{
							userValue = (new StringBuilder()).append(userValue).append("~").append("");
						}
	
		    			mapUsers.put(Integer.toString(keyCounter), userKey+"~"+userValue);
						keyCounter++;
						//log.debug("TrackingAction - "+pTrackingForm.getNovelUserId()+" - displayUserList - Result **************************************************************");
		    		}
		    		
		    		userObj.put("Users",mapUsers);
	    		}else{
	    			//log.debug("TrackingAction - "+pTrackingForm.getNovelUserId()+" - displayUserList - No Person Found");
	    			userObj.put("Empty", "No User(s) Found");
	    		}
            }else{
    			//log.debug("TrackingAction - "+pTrackingForm.getNovelUserId()+" - displayUserList - No Person Found");
    			userObj.put("Empty", "No User(s) Found. Please select College, School, Department, Module, Building or search for Person directly or check spelling.");
    		}
    		
        }catch(Exception ex){
        	//log.debug("TrackingAction - "+pTrackingForm.getNovelUserId()+" - displayUserList - The getUserList Web Service Failed!");
        	userObj.put("Error","The getUserList Web Service Failed! Please try again / <br/>"+ex);
        }

    	// Convert the map to json
    	PrintWriter out = response.getWriter();
       	JSONObject jsonObject = JSONObject.fromObject(userObj);
       	//log.debug("TrackingAction - "+pTrackingForm.getNovelUserId()+" - displayUserList - Final - **************************************************************");
       	//log.debug("TrackingAction - "+pTrackingForm.getNovelUserId()+" - displayUserList - Final - jsonObject="+jsonObject.toString());
       	//log.debug("TrackingAction - "+pTrackingForm.getNovelUserId()+" - displayUserList - Final - **************************************************************");
       	out.write(jsonObject.toString());
       	out.flush();

    	return null; //Returning null to prevent struts from interfering with ajax/json
    }

    public ActionForward displayUserAddress(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception{

        prepareBackendSession();
    	TrackingForm pTrackingForm = (TrackingForm)form;
    	//WebServiceGateWay pGateWay = new WebServiceGateWay();
    	//log.debug("TrackingAction - "+pTrackingForm.getNovelUserId()+" - displayUserAddress - Personnel/Staff");
        int addressId = 892254;
        pTrackingForm.setUserAddress("");
        
        int keyCounter = 0;
    	JSONObject addressObj = new JSONObject();
       	Map<String, String> mapAddresses = new LinkedHashMap<String, String>();
       	String user = pTrackingForm.getUser();
        //log.debug("TrackingAction - "+pTrackingForm.getNovelUserId()+" - displayUserAddress - Person - USER ADDRESS="+user);

        	try{
        		ArrayList<KeyValue> getValues = pGateWay.getSelectedUserAddress(pTrackingForm.getWebServiceURL() ,addressId, user, "value1", "value2", "value3", "value4", "value5", "value6", "Personnel");
        		Iterator<KeyValue> it = getValues.iterator();
        		
        		if (it.hasNext()){
        			String addressKey = "";
	    			StringBuilder addressValue = new StringBuilder();
	    			
					KeyValue test = (KeyValue) it.next();
					if (test.getValue1() != null && !test.getValue1().equals("")){
						//log.debug("TrackingAction - "+pTrackingForm.getNovelUserId()+" - displayUserAddress - StaffNumber="+test.getValue());
						//addressKey = test.getValue().trim().toString();
	
						if(test.getValue1() != null &&  !test.getValue1().isEmpty()){
							if(!test.getValue1().equals("null")){
								//log.debug("TrackingAction - "+pTrackingForm.getNovelUserId()+" - displayUserAddress - userAddress1="+test.getValue1());
								addressValue = (new StringBuilder()).append(addressValue).append(test.getValue1().trim().toString());
							}else{
								addressValue = (new StringBuilder()).append(addressValue).append("");
							}
						}else{
							addressValue = (new StringBuilder()).append(addressValue).append("");
						}
						if(test.getValue2() != null &&  !test.getValue2().isEmpty()){
							if(!test.getValue2().equals("null")){
								//log.debug("TrackingAction - "+pTrackingForm.getNovelUserId()+" - displayUserAddress - userAddress2="+test.getValue2());
								addressValue = (new StringBuilder()).append(addressValue).append("~").append(test.getValue2().trim().toString());
							}else{
								addressValue = (new StringBuilder()).append(addressValue).append("~").append("");
							}
						}else{
							addressValue = (new StringBuilder()).append(addressValue).append("~").append("");
						}
						if(test.getValue3() != null &&  !test.getValue3().isEmpty()){
							if(!test.getValue3().equals("null")){
								//log.debug("TrackingAction - "+pTrackingForm.getNovelUserId()+" - displayUserAddress - userEmail="+test.getValue3());
								addressValue = (new StringBuilder()).append(addressValue).append("~").append(test.getValue3().trim().toString());
							}else{
								addressValue = (new StringBuilder()).append(addressValue).append("~").append("");
							}
						}else{
							addressValue = (new StringBuilder()).append(addressValue).append("~").append("");
						}
						
						mapAddresses.put(Integer.toString(keyCounter), addressValue.toString());

					}else{
						addressObj.put("Empty","Selected user's address not Found. Please select another user or enter address manually.");
					}
					//log.debug("TrackingAction - "+pTrackingForm.getNovelUserId()+" - displayUserAddress - **************************************************************");

        		}else{
        			addressObj.put("Empty","Selected user's address not Found. Please select another user or enter address manually.");
        		}
        		addressObj.put("Address",mapAddresses);
            }catch(Exception ex){
            	addressObj.put("Error","The displayUserAddress Web Service Failed! Please try again / <br/>"+ex);
            }

    		// Convert the map to json
    		PrintWriter out = response.getWriter();
           	JSONObject jsonObject = JSONObject.fromObject(addressObj);
           	//log.debug("TrackingAction - "+pTrackingForm.getNovelUserId()+" - displayUserAddress - Final - **************************************************************");
           	//log.debug("TrackingAction - "+pTrackingForm.getNovelUserId()+" - displayUserAddress - Final - jsonObject="+jsonObject.toString());
           	//log.debug("TrackingAction - "+pTrackingForm.getNovelUserId()+" - displayUserAddress - Final - **************************************************************");
           	out.write(jsonObject.toString());
           	out.flush();
           	
           	return null; //Returning null to prevent struts from interfering with ajax/json
    }
        
    public ActionForward getSavedAddress(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception{
    
    	
        prepareBackendSession();
        int addressId = 367199;
        TrackingForm pTrackingForm = (TrackingForm)form;
//        WebServiceGateWay pGateWay = new WebServiceGateWay();
        
        int keyCounter = 0;
    	JSONObject addressObj = new JSONObject();
       	Map<String, String> mapAddresses = new LinkedHashMap<String, String>();

        String FromUserID = pTrackingForm.getNovelUserId().toUpperCase();
        //log.debug("TrackingAction - "+pTrackingForm.getNovelUserId()+" - getSavedAddress - FromUserID="+FromUserID);

            try{
        		ArrayList<KeyValue> getValues = pGateWay.getSavedUserAddress(pTrackingForm.getWebServiceURL() ,addressId, FromUserID, "value1", "value2", "value3", "value4", "value5", "value6", "value7", "value8");
        		Iterator<KeyValue> it = getValues.iterator();
        		
        		if (it.hasNext()){
        			String addressKey = "";
	    			StringBuilder addressValue = new StringBuilder();
	    			
					KeyValue test = (KeyValue) it.next();
					//log.debug("TrackingAction - "+pTrackingForm.getNovelUserId()+" - getSavedAddress - FromUserID="+test.getValue2());
					addressKey = test.getValue2().trim().toString();

					if(test.getValue3() != null &&  !test.getValue3().isEmpty()){
						if(!test.getValue3().equals("null")){
							//log.debug("TrackingAction - "+pTrackingForm.getNovelUserId()+" - getSavedAddress - userAddress1="+test.getValue3());
							addressValue = (new StringBuilder()).append(addressValue).append(test.getValue3().trim().toString());
						}else{
							addressValue = (new StringBuilder()).append(addressValue).append("");
						}
					}else{
						addressValue = (new StringBuilder()).append(addressValue).append("");
					}
					if(test.getValue4() != null &&  !test.getValue4().isEmpty()){
						if(!test.getValue4().equals("null")){
							//log.debug("TrackingAction - "+pTrackingForm.getNovelUserId()+" - getSavedAddress - userAddress2="+test.getValue4());
							addressValue = (new StringBuilder()).append(addressValue).append("~").append(test.getValue4().trim().toString());
						}else{
							addressValue = (new StringBuilder()).append(addressValue).append("~").append("");
						}
					}else{
						addressValue = (new StringBuilder()).append(addressValue).append("~").append("");
					}
					if(test.getValue5() != null &&  !test.getValue5().isEmpty()){
						if(!test.getValue5().equals("null")){
							//log.debug("TrackingAction - "+pTrackingForm.getNovelUserId()+" - getSavedAddress - userAddress3="+test.getValue5());
							addressValue = (new StringBuilder()).append(addressValue).append("~").append(test.getValue5().trim().toString());
						}else{
							addressValue = (new StringBuilder()).append(addressValue).append("~").append("");
						}
					}else{
						addressValue = (new StringBuilder()).append(addressValue).append("~").append("");
					}
					if(test.getValue6() != null &&  !test.getValue6().isEmpty()){
						if(!test.getValue6().equals("null")){
							//log.debug("TrackingAction - "+pTrackingForm.getNovelUserId()+" - getSavedAddress - userAddress4="+test.getValue6());
							addressValue = (new StringBuilder()).append(addressValue).append("~").append(test.getValue6().trim().toString());
						}else{
							addressValue = (new StringBuilder()).append(addressValue).append("~").append("");
						}
					}else{
						addressValue = (new StringBuilder()).append(addressValue).append("~").append("");
					}
					if(test.getValue7() != null &&  !test.getValue7().isEmpty()){
						if(!test.getValue7().equals("null")){
							//log.debug("TrackingAction - "+pTrackingForm.getNovelUserId()+" - getSavedAddress - userPostal="+test.getValue7());
							addressValue = (new StringBuilder()).append(addressValue).append("~").append(test.getValue7().trim().toString());
						}else{
							addressValue = (new StringBuilder()).append(addressValue).append("~").append("");
						}
					}else{
						addressValue = (new StringBuilder()).append(addressValue).append("~").append("");
					}
					if(test.getValue8() != null &&  !test.getValue8().isEmpty()){
						if(!test.getValue8().equals("null")){
							//log.debug("TrackingAction - "+pTrackingForm.getNovelUserId()+" - getSavedAddress - userEmail="+test.getValue8());
							addressValue = (new StringBuilder()).append(addressValue).append("~").append(test.getValue8().trim().toString());
						}else{
							addressValue = (new StringBuilder()).append(addressValue).append("~").append("");
						}
					}else{
						addressValue = (new StringBuilder()).append(addressValue).append("~").append("");
					}
					//mapAddresses.put(Integer.toString(keyCounter), addressKey+"~"+addressValue);
					mapAddresses.put(Integer.toString(keyCounter), addressValue.toString());
					//log.debug("TrackingAction - "+pTrackingForm.getNovelUserId()+" - getSavedAddress - **************************************************************");

        		}else{
        			addressObj.put("Empty","Selected user Address not Found");
        		}
        		addressObj.put("Address",mapAddresses);
            }catch(Exception ex){
            	addressObj.put("Error","The getSavedAddress Web Service Failed! Please try again / <br/>"+ex);
            }


		// Convert the map to json
		PrintWriter out = response.getWriter();
       	JSONObject jsonObject = JSONObject.fromObject(addressObj);
       	//log.debug("TrackingAction - "+pTrackingForm.getNovelUserId()+" - getSavedAddress - Final - **************************************************************");
       	//log.debug("TrackingAction - "+pTrackingForm.getNovelUserId()+" - getSavedAddress - Final - jsonObject="+jsonObject.toString());
       	//log.debug("TrackingAction - "+pTrackingForm.getNovelUserId()+" - getSavedAddress - Final - **************************************************************");
       	out.write(jsonObject.toString());
       	out.flush();
       	
       	return null; //Returning null to prevent struts from interfering with ajax/json
    }

    //Main Method to build Arrays of Shipping Lists (Parents) with siblings : Attached Dockets (with linked Assignments) and Unique Assignments)
    public ActionForward validateConsignmentList(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)throws Exception {

    	prepareBackendSession();
       	TrackingForm pTrackingForm = (TrackingForm)form;
 //   	WebServiceGateWay pGateWay = new WebServiceGateWay();
    	
    	JSONObject conObj = new JSONObject();
    	JSONObject dctObj = new JSONObject();
    	Consignment consignment = new Consignment();
    	List<Assignment> assignmentList = new ArrayList<Assignment>();
       	Map<String, String> mapConsignments = new LinkedHashMap<String, String>();
       	Map<String, String> mapRemoveDockets = new LinkedHashMap<String, String>();
       	Map<String, String> mapRemoveAssignments = new LinkedHashMap<String, String>();
		Map<String, String> mapConDctAssignments = new LinkedHashMap<String, String>();
		Map<String, String> mapConUnqAssignments = new LinkedHashMap<String, String>();
	   	String errorMsg = "";
	   	
		//log.debug("TrackingAction - "+pTrackingForm.getNovelUserId()+" - validateConsignmentList");
		if(pTrackingForm.getConListCount() == 0){
			pTrackingForm.setConListCount(pTrackingForm.getConListCount()+1);
		   //log.debug("conListCount"+pTrackingForm.getConListCount());
		}
		Session currentSession = sessionManager.getCurrentSession();
		HttpSession session = request.getSession(true);
	
    	ActionMessages messages = new ActionMessages();

    	//log.debug("TrackingAction - "+pTrackingForm.getNovelUserId()+" - validateConsignmentList - Form="+pTrackingForm.getEnteredConsignmentNumber());

		try{
			pTrackingForm.setConsignmentNumber(pTrackingForm.getEnteredConsignmentNumber().trim());
            if(pTrackingForm.getEnteredConsignmentNumber() != null && pTrackingForm.getEnteredConsignmentNumber().length() > 0 && isNumberValid(pTrackingForm.getEnteredConsignmentNumber().trim())){
            	pTrackingForm.setConsignmentListNumber(pTrackingForm.getEnteredConsignmentNumber().trim());
                int shipListCreatedDate = 121868;      	
                //Edmund - Removed Status, have to check with Harry if In/Out matters for Consignment List
                //ArrayList getDate = pGateWay.showConsignmentListInfo(webServiceURL ,shipListCreatedDate, consignmentNumber,pTrackingForm.getUserSelection().toUpperCase(), "value1", "value4");
                ArrayList getDate = pGateWay.showConsignmentListInfo(pTrackingForm.getWebServiceURL() ,shipListCreatedDate, pTrackingForm.getConsignmentNumber(),"", "value1", "value4");
                for(Iterator date = getDate.iterator(); date.hasNext();){
                    KeyValue conKey = (KeyValue)date.next();
                    //log.debug("TrackingAction - "+pTrackingForm.getNovelUserId()+" - validateConsignmentList - conKey: " + conKey.getValue());
                    if(conKey.getValue().contains("Consignment list number invalid")){
                    	errorMsg = "Consignment List "+pTrackingForm.getConsignmentNumber()+" is invalid.<br/>Please Enter a valid Consignment List Number";
                    }else{
                    	//Test if Consignment List has been added already 
                    	for(Consignment existConsignment : pTrackingForm.getMasterConsignmentList()) { 
                    		//log.debug("TrackingAction - "+pTrackingForm.getNovelUserId()+" - validateConsignmentList - *******************************************************");
                    		//log.debug("TrackingAction - "+pTrackingForm.getNovelUserId()+" - validateConsignmentList - Existing Consignment Number : " +existConsignment.getConsignmentNumber());
                    		//log.debug("TrackingAction - "+pTrackingForm.getNovelUserId()+" - validateConsignmentList - Existing Consignment Date : " +existConsignment.getDate());			
                    		//log.debug("TrackingAction - "+pTrackingForm.getNovelUserId()+" - validateConsignmentList - *******************************************************");
                    		if (pTrackingForm.getConsignmentNumber().equals(existConsignment.getConsignmentNumber())){
                    			//log.debug("TrackingAction - "+pTrackingForm.getNovelUserId()+" - validateConsignmentList - Consignment list number already added.");
                    			errorMsg = "Consignment List "+pTrackingForm.getConsignmentNumber()+" has already been added.<br/>Please Enter a different Consignment List Number.";
                    		}
                    	}
                    	
                    	/**
                         * Creating detail for Consignment Lists.
                         */
                    	//Adding to main list to keep track in beans
                		consignment.setConsignmentNumber(pTrackingForm.getConsignmentNumber());
                		consignment.setDate(conKey.getValue());
                    	//Adding to temp map to test/validate
                    	mapConsignments.put(pTrackingForm.getConsignmentNumber(),conKey.getValue());
						/**Debug**/
                    	//log.debug("TrackingAction - "+pTrackingForm.getNovelUserId()+" - validateConsignmentList - **************************************************************");
						Set<String> keys = mapConsignments.keySet();
				        for(String k:keys){
				            //log.debug("TrackingAction - "+pTrackingForm.getNovelUserId()+" - validateConsignmentList - Map Put mapConsignment=" +k+" -- "+mapConsignments.get(k));
				        }
				        //log.debug("TrackingAction - "+pTrackingForm.getNovelUserId()+" - validateConsignmentList - **************************************************************");     		
                		
				        //Adding to Main Map
				        conObj.put(pTrackingForm.getConsignmentNumber(),conKey.getValue());
				        
                    	//Get dockets and assignments for this shipping list
                    	int showConginmentInfo = 593166;
                 	   	int showUniqueAssignments = 156154;
                 	   	//Edmund - Removed Status, have to check with Harry if In/Out matters for Consignment List
                        //ArrayList getValues = pGateWay.showConsignmentListInfo(webServiceURL ,showConginmentInfo, consignmentNumber,pTrackingForm.getUserSelection().toUpperCase() ,"id", "value1");
                 	   	ArrayList getValues = pGateWay.showConsignmentListInfo(pTrackingForm.getWebServiceURL() ,showConginmentInfo, pTrackingForm.getConsignmentNumber(),"" ,"id", "value1");
                        Iterator dct = getValues.iterator();
                        String errMsgDct="";
                        String errMsgStu="";
                        int conDctCount=0;
                        if(dct.hasNext()){
                        	List<Docket> dockets = new ArrayList<Docket>();
            	            while(dct.hasNext()){
            	    			KeyValue dctKey = (KeyValue) dct.next();
            	    			if(dctKey.getValue().equals("No records returned")){
            	    				errMsgDct = " Docket Numbers ";
            	    				//log.debug("TrackingAction - "+pTrackingForm.getNovelUserId()+" - validateConsignmentList - Docket Numbers: No records returned");
            	    			}else{
            	    				conDctCount++;
            	    				//log.debug("TrackingAction - "+pTrackingForm.getNovelUserId()+" - validateConsignmentList - Docket Numbers: Successful records returned="+dctKey.getValue());
            	    				//Add Docket to main list to check for duplicates
                                	Docket docketListCon = populateDocket(pTrackingForm, Integer.toString(conDctCount), dctKey.getValue());
                                	//Check if Cover Docket has been added manually, if so, add to new map to remove from web page
                                	if (duplicateCheckConDockets(pTrackingForm, dctKey.getValue())){
                                		//log.debug("TrackingAction - "+pTrackingForm.getNovelUserId()+" - validateConsignmentList - Docket Number: "+dctKey.getValue()+" already manually added, adding to remove map");
                                		mapRemoveDockets.put(Integer.toString(conDctCount), dctKey.getValue());
                                	}
            	    				//Retrieve all Assignments linked to the Cover Docket
       	    						LinkedHashMap<String, String> mapDctAssignments = new LinkedHashMap<String, String>();
       								int docketAssignmentID = 702449 ;
       							    //log.debug("TrackingAction - "+pTrackingForm.getNovelUserId()+" - validateConsignmentList - Retrieve linked Assignments for docketNumber="+dctKey.getValue());
       							    ArrayList dctAssignments = pGateWay.getDocketAssignments(pTrackingForm.getWebServiceURL(), docketAssignmentID ,dctKey.getValue(), "value1", "value2");
       							    Iterator dctAssign = dctAssignments.iterator();
       							    int conDctAssCounter = 0;
       							    if(dctAssign.hasNext()){
       							    	List<Assignment> dctAssignList = new ArrayList<Assignment>();
       							    	while(dctAssign.hasNext()){
       							    		KeyValue test = (KeyValue) dctAssign.next();
       										if(test.getValue().equals("No records returned")){
       											//log.debug("TrackingAction - "+pTrackingForm.getNovelUserId()+" - validateConsignmentList - No linked Assignments returned");
       										}else{
       											conDctAssCounter++;
       											//log.debug("TrackingAction - "+pTrackingForm.getNovelUserId()+" - validateConsignmentList  - Linked Assignments: Successful records returned - Student="+test.getKey()+", Assignment="+test.getValue());
       											// Generate a Docket-Assignment map
       											mapConDctAssignments.put(String.valueOf(conDctAssCounter), test.getKey()+"~"+test.getValue());
       		                                	//Check if Assignment has been added manually, if so, add to new map to remove from web page
       		                                	if (duplicateCheckConAssignment(pTrackingForm, test.getKey(), test.getValue())){
       		                                		//log.debug("TrackingAction - "+pTrackingForm.getNovelUserId()+" - validateConsignmentList - Assignment: "+test.getKey()+"~"+test.getValue()+" already manually added, adding to remove map");
       		                                		mapRemoveAssignments.put(String.valueOf(conDctAssCounter), test.getKey()+"~"+test.getValue());
       		                                	}
       											//Add to main list to check for duplicates
       											dctAssignList.add(populateAssignment(pTrackingForm, test.getKey(),test.getValue()));
       											/**Debug**/
       											//log.debug("TrackingAction - "+pTrackingForm.getNovelUserId()+" - validateConsignmentList - **************************************************************");
       											Set<String> DctAssKeys = mapDctAssignments.keySet();
       									        for(String k:DctAssKeys){
       									            //log.debug("Map Put mapDctAssignments=" +k+" -- "+mapDctAssignments.get(k));
       									        }
       									        //log.debug("TrackingAction - "+pTrackingForm.getNovelUserId()+" - validateConsignmentList - **************************************************************");
       										}
       							    	}
       							    	docketListCon.setAssignments(dctAssignList);
       							    }else{
       							    	//log.debug("TrackingAction - "+pTrackingForm.getNovelUserId()+" - validateConsignmentList - No linked Assignments returned");
       							    }
            	    				//log.debug("TrackingAction - "+pTrackingForm.getNovelUserId()+" - validateConsignmentList - Docket Numbers: " + dctKey.getValue());
            	    				dctObj.put(dctKey.getValue(), mapConDctAssignments);
            	    				if(!mapRemoveAssignments.isEmpty()){
    	            	            	//log.debug("TrackingAction - "+pTrackingForm.getNovelUserId()+" - validateConsignmentList - (mapRemoveAssignments - docket) Adding Assignment to be removed from Unique Map on screen");
    	            	            	conObj.put("RemoveAssignments", mapRemoveAssignments);
    	            	            }
            	    				dockets.add(docketListCon);
            	                 }	
            	    		}
            	            //consignment.setDockets(docketList);
            	            //Add dockets to main consignment object
            	            if (conDctCount>0){
	            	            conObj.put("Dockets", dctObj);
	            	            if(!mapRemoveDockets.isEmpty()){
	            	            	conObj.put("RemoveDockets", mapRemoveDockets);
	            	            }
	            	            consignment.setDockets(dockets);
            	            }
                        }else{
                        	errMsgDct = " Docket Numbers ";
            				//log.debug("TrackingAction - "+pTrackingForm.getNovelUserId()+" - validateConsignmentList - Docket Numbers: No records returned");
                        }
                        
                        //Edmund - Removed Status, have to check with Harry if In/Out matters for Consignment List
                        //ArrayList uniqueNumbers = pGateWay.showUniqueAssignmentListInfo(webServiceURL ,showUniqueAssignments, consignmentNumber,pTrackingForm.getUserSelection().toUpperCase() ,"id", "value1", "value2");
                        //log.debug("TrackingAction - "+pTrackingForm.getNovelUserId()+" - validateConsignmentList - Unique Assignments");
                        ArrayList uniqueNumbers = pGateWay.showUniqueAssignmentListInfo(pTrackingForm.getWebServiceURL() ,showUniqueAssignments, pTrackingForm.getConsignmentNumber(),"" ,"value1", "value2", "value3");
                        Iterator uniqueNumber = uniqueNumbers.iterator();
                    	int conAssCount=0;
                        if(uniqueNumber.hasNext()){
                        	List<Assignment> conUnqAssignmentList = new ArrayList<Assignment>();
                        	while(uniqueNumber.hasNext()){
            	    			KeyValue assKey = (KeyValue) uniqueNumber.next();
            	    			if(assKey.getValue1().equals("No records returned")){
            	    				errMsgStu = " Unique Assignments ";
            	    				//log.debug("TrackingAction - "+pTrackingForm.getNovelUserId()+" - validateConsignmentList - Unique Assignments: No records returned");
            	   			 	}else{
            	   			 		//log.debug("TrackingAction - "+pTrackingForm.getNovelUserId()+" - validateConsignmentList - Unique Assignments: Successful records returned");
            	   			 		//log.debug("TrackingAction - "+pTrackingForm.getNovelUserId()+" - validateConsignmentList - Unique Assignments: " + assKey.getValue1()+"~"+assKey.getValue2());
	                                //Check for Duplicate Unique Assignments, which may already exist in Unique Cover Dockets added manually
            	   			 		if (duplicateCheckDctAssignment(pTrackingForm, assKey.getValue1(), assKey.getValue2())){
            	   			 			//log.debug("TrackingAction - "+pTrackingForm.getNovelUserId()+" - validateConsignmentList - Unique Assignment: "+assKey.getValue1()+"~"+assKey.getValue2()+" already in manually added Cover Docket, therefor not adding to list");
            	   			 		}else{
            	   			 			conAssCount++;
            	   			 			// Generate a map of Unique Assignments
	            	   			 		mapConUnqAssignments.put(Integer.toString(conAssCount), (String) assKey.getValue1()+"~"+(String) assKey.getValue2());
	                                	//Check if Assignment has been added manually, if so, add to new map to remove from web page
	                                	if (duplicateCheckConAssignment(pTrackingForm, assKey.getValue1(), assKey.getValue2())){
	                                		//log.debug("TrackingAction - "+pTrackingForm.getNovelUserId()+" - validateConsignmentList - Assignment: "+assKey.getValue1()+"~"+assKey.getValue2()+" already manually added, adding to remove map");
	                                		mapRemoveAssignments.put(Integer.toString(conAssCount), assKey.getValue1()+"~"+assKey.getValue2());
	                                	}
	            	   			 		//Add Unique Assignment on Consignment list to main list for duplicate checking
	            	   			 		conUnqAssignmentList.add(populateAssignment(pTrackingForm, Integer.toString(conAssCount), (String) assKey.getValue1()+"~"+(String) assKey.getValue2()));
	            	   			 		/**Debug**/
	            	   			 		//log.debug("TrackingAction - "+pTrackingForm.getNovelUserId()+" - validateConsignmentList - **************************************************************");
	            	   			 		Set<String> unqConAssKeys = mapConUnqAssignments.keySet();
								        for(String k:unqConAssKeys){
								            //log.debug("Map Put mapConUnqAssignments=" +k+" -- "+mapConUnqAssignments.get(k));
								        }
            	   			 		}
							        //log.debug("TrackingAction - "+pTrackingForm.getNovelUserId()+" - validateConsignmentList - **************************************************************");
            	   			 	}
            	            }
                        	//Add Assignments to main Consignment map
                        	if (conAssCount > 0){
	                        	conObj.put("Assignments", mapConUnqAssignments);
	            	            if(!mapRemoveAssignments.isEmpty()){
	            	            	//log.debug("TrackingAction - "+pTrackingForm.getNovelUserId()+" - validateConsignmentList - (mapRemoveAssignments - unique) Remove Assignment from Main Unique Map");
	            	            	Set<String> unqAssKeys = pTrackingForm.getMapUnqAssignments().keySet();
							        for(String k:unqAssKeys){
							        	//log.debug("TrackingAction - "+pTrackingForm.getNovelUserId()+" - validateConsignmentList - (mapRemoveAssignments - unique - K) "+k+"~"+pTrackingForm.getMapUnqAssignments().get(k));
							        	Set<String> unqRemAssKeys = mapRemoveAssignments.keySet();
								        for(String r:unqRemAssKeys){
								        	//log.debug("TrackingAction - "+pTrackingForm.getNovelUserId()+" - validateConsignmentList - (mapRemoveAssignments - unique - R) "+r+"~"+mapRemoveAssignments.get(r));
								        	if (pTrackingForm.getMapUnqAssignments().get(k).equals(mapRemoveAssignments.get(r))){
								        		// remove value for key 2
								        		//log.debug("TrackingAction - "+pTrackingForm.getNovelUserId()+" - validateConsignmentList - (mapRemoveAssignments from pTrackingForm.getMapUnqAssignments(): "+k+"~"+pTrackingForm.getMapUnqAssignments().get(k));
								        		pTrackingForm.getMapUnqAssignments().remove(k);
								        	}
								        }
							        }
		                        	//log.debug("TrackingAction - "+pTrackingForm.getNovelUserId()+" - validateConsignmentList - **************************************************************");
	            	            	conObj.put("RemoveAssignments", mapRemoveAssignments);
	            	            }
	                        	consignment.setAssignments(conUnqAssignmentList);
                        	}
                        }else{
                        	errMsgStu = " Unique Assignments ";
            				//log.debug("TrackingAction - "+pTrackingForm.getNovelUserId()+" - validateConsignmentList - Unique Assignments: No records returned");
                        }
                        
                        //log.debug("TrackingAction - "+pTrackingForm.getNovelUserId()+" - validateConsignmentList - Error Checking - Dockets: "+errMsgDct+", Student: "+errMsgStu);
                        //log.debug("TrackingAction - "+pTrackingForm.getNovelUserId()+" - validateConsignmentList - Error Checking Sizes - Dockets: "+conDctCount+", Assignments: "+conAssCount);
                        
                        if(conDctCount== 0 && conAssCount== 0){ 
            	            if(!errMsgDct.equals("") || !errMsgStu.equals("")){
            	            	//log.debug("TrackingAction - "+pTrackingForm.getNovelUserId()+" - validateConsignmentList - Error Checking");
            	            	String newErrorMsg = "Consignment list " + pTrackingForm.getConsignmentNumber() + " doesn't contain any ";
            	            	StringBuilder newError;
            	            	if(!errMsgDct.equals("") && (errMsgStu.equals("") && mapConUnqAssignments.isEmpty())){
            	            		//log.debug("TrackingAction - "+pTrackingForm.getNovelUserId()+" - validateConsignmentList - Docket Error");
            	            		newError = (new StringBuilder()).append(newErrorMsg).append(errMsgDct);
            	            	}else if(errMsgDct.equals("") && !errMsgStu.equals("")){
            	            		//log.debug("TrackingAction - "+pTrackingForm.getNovelUserId()+" - validateConsignmentList - Student Error");
            	            		newError = (new StringBuilder()).append(newErrorMsg).append(errMsgStu);
            	            	}else{
            	            		//log.debug("TrackingAction - "+pTrackingForm.getNovelUserId()+" - validateConsignmentList - Docket & Student Error");
            	            		newError = (new StringBuilder()).append(newErrorMsg).append(errMsgDct).append("or").append(errMsgStu);
            	            	}
            	            	newError = (new StringBuilder()).append(newErrorMsg).append(" <br>Cover Dockets or Assignments may be booked in or out on a later Consignment List.");
            	            	//log.debug("TrackingAction - "+pTrackingForm.getNovelUserId()+" - validateConsignmentList - Final Error: "+newError.toString());
            	            	errorMsg = newError.toString();
            	            }
                        }
                        //log.debug("TrackingAction - "+pTrackingForm.getNovelUserId()+" - validateConsignmentList - Before Building Consignment Collection");
                		//Add Dockets and Assignments to Consignment List
                        
                    		//log.debug("TrackingAction - "+pTrackingForm.getNovelUserId()+" - validateConsignmentList - After Building Consignment Collection");
                    }
            		
            		//xxx consignment.setAssignments(assignmentList);
                    pTrackingForm.getMasterConsignmentList().add(consignment);
                }
            }else{
            	errorMsg = "Consignment List "+pTrackingForm.getConsignmentNumber()+" is invalid.<br/>Please Enter a valid Consignment List Number";

                addErrors(request, messages);
            }
        }catch(Exception e){
        	errorMsg = "Web Server not responding. Please try again. / " +e +" / " +e.getMessage();

        }
		
		if(mapRemoveDockets != null ) {
			Set<String> keys = mapRemoveDockets.keySet();
	        for(String dct:keys){
	        	//log.debug("TrackingAction - "+pTrackingForm.getNovelUserId()+" - validateConsignmentList - *******************************************************");
	        	//log.debug("TrackingAction - "+pTrackingForm.getNovelUserId()+" - validateConsignmentList - Docket to Remove from Unique List: " +dct+" -- "+mapRemoveDockets.get(dct));
	        	//log.debug("TrackingAction - "+pTrackingForm.getNovelUserId()+" - validateConsignmentList - *******************************************************");
	        }
		}
		if(mapRemoveAssignments != null ) {
			Set<String> keys = mapRemoveAssignments.keySet();
	        for(String ass:keys){
	        	//log.debug("TrackingAction - "+pTrackingForm.getNovelUserId()+" - validateConsignmentList - *******************************************************");
	        	//log.debug("TrackingAction - "+pTrackingForm.getNovelUserId()+" - validateConsignmentList - Assignment to Remove from Unique List: " +ass+" -- "+mapRemoveAssignments.get(ass));
	        	//log.debug("TrackingAction - "+pTrackingForm.getNovelUserId()+" - validateConsignmentList - *******************************************************");
	        }
		}
		
		//Test final consignment list data for duplicate tests
		for(Consignment consignTest : pTrackingForm.getMasterConsignmentList()) {
			//log.debug("TrackingAction - "+pTrackingForm.getNovelUserId()+" - validateConsignmentList - *******************************************************");
			//log.debug("TrackingAction - "+pTrackingForm.getNovelUserId()+" - validateConsignmentList - Consignment Test - Consignment Number : " +consignTest.getConsignmentNumber());
			//log.debug("TrackingAction - "+pTrackingForm.getNovelUserId()+" - validateConsignmentList - Consignment Test - Consignment Date : " +consignTest.getDate());			
			//log.debug("TrackingAction - "+pTrackingForm.getNovelUserId()+" - validateConsignmentList - *******************************************************");
			
			if(consignTest.getDockets() != null ) {
				for(Docket docket : consignTest.getDockets()) {
					//log.debug("TrackingAction - "+pTrackingForm.getNovelUserId()+" - validateConsignmentList - Consignment Test - Docket Id : " +docket.getDocketID());
					//log.debug("TrackingAction - "+pTrackingForm.getNovelUserId()+" - validateConsignmentList - Consignment Test - Docket Number : " +docket.getDocketNumber());
					//log.debug("TrackingAction - "+pTrackingForm.getNovelUserId()+" - validateConsignmentList - *******************************************************");
					if(docket.getAssignments() != null ) {
						for(Assignment assignment : docket.getAssignments()) {
							//log.debug("TrackingAction - "+pTrackingForm.getNovelUserId()+" - validateConsignmentList - Consignment Test - Dct Student Number : " +assignment.getStudentNumber());
							//log.debug("TrackingAction - "+pTrackingForm.getNovelUserId()+" - validateConsignmentList - Consignment Test - Dct Unique Assignment : " +assignment.getUniqueAssignmentNumber());
							//log.debug("TrackingAction - "+pTrackingForm.getNovelUserId()+" - validateConsignmentList - *******************************************************");
						}
					}
				}
				//log.debug("TrackingAction - "+pTrackingForm.getNovelUserId()+" - validateConsignmentList - *******************************************************");
			}
			
			if(consignTest.getAssignments() != null) {
				for(Assignment assignment : consignTest.getAssignments()) {
					//log.debug("TrackingAction - "+pTrackingForm.getNovelUserId()+" - validateConsignmentList - Consignment Test - Unique Student Number : " +assignment.getStudentNumber());
					//log.debug("TrackingAction - "+pTrackingForm.getNovelUserId()+" - validateConsignmentList - Consignment Test - Unique Assignment Number : " +assignment.getUniqueAssignmentNumber());
					//log.debug("TrackingAction - "+pTrackingForm.getNovelUserId()+" - validateConsignmentList - *******************************************************");
				}
			}
			//log.debug("TrackingAction - "+pTrackingForm.getNovelUserId()+" - validateConsignmentList - *******************************************************");
		}

		// Convert the map to json
		PrintWriter out = response.getWriter();
        if (errorMsg != null && !errorMsg.equals("")){
        	Map<String, String> mapErrors = new LinkedHashMap<String, String>();
        	mapErrors.put("Error",errorMsg);
        	JSONObject jsonObject = JSONObject.fromObject(mapErrors);
        	out.write(jsonObject.toString());
        	out.flush();
        }else{
        	JSONObject jsonObject = JSONObject.fromObject(conObj);
        	//log.debug("TrackingAction - "+pTrackingForm.getNovelUserId()+" - validateConsignmentList - Final - **************************************************************");
        	//log.debug("TrackingAction - "+pTrackingForm.getNovelUserId()+" - validateConsignmentList - Final - jsonObject="+jsonObject.toString());
        	//log.debug("TrackingAction - "+pTrackingForm.getNovelUserId()+" - validateConsignmentList - Final - **************************************************************");
        	out.write(jsonObject.toString());
        	out.flush();
        }

		return null; //Returning null to prevent struts from interfering with ajax/json
    }
    
    public ActionForward validateDocketNumber(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {

        prepareBackendSession();
	    String errorMsg="";
	    Map<String, String> mapRemoveAssignments = new LinkedHashMap<String, String>();
	    Map<String, String> mapRemoveConUnqAssignments = new LinkedHashMap<String, String>();
    	StringWriter outObj = new StringWriter();
    	JSONObject dctObj = new JSONObject();
    	ActionMessages messages = new ActionMessages();
    	TrackingForm pTrackingForm = (TrackingForm) form;
//    	WebServiceGateWay pGateWay = new WebServiceGateWay();
    	
    	//log.debug("TrackingAction - "+pTrackingForm.getNovelUserId()+" - validateDocketNumber");
    	boolean addDocketCount = false;
    	if(pTrackingForm.getDocCount() == 0){
    		pTrackingForm.setDocCount(pTrackingForm.getDocCount()+1);
    		addDocketCount = true;
    	   //log.debug("docCount"+pTrackingForm.getDocCount());
    	}
    	//log.debug("TrackingAction - "+pTrackingForm.getNovelUserId()+" - validateDocketNumber - First Docket Count="+pTrackingForm.getDocCount());
    	
    	Session currentSession = sessionManager.getCurrentSession();
    	HttpSession session = request.getSession(true);
    	//log.debug("Docket number test="+pTrackingForm.getDocketNumber());

    		   try {
    		      
    			   if(pTrackingForm.getDocketNumber() != null && !pTrackingForm.getDocketNumber().trim().equals("") && isNumberValid(pTrackingForm.getDocketNumber().trim())) {
    				   int docketNumberID = 118648 ;
    				   pTrackingForm.setValidationValues(pGateWay.docketNumberValidationCheck(pTrackingForm.getWebServiceURL() ,docketNumberID,Integer.parseInt(pTrackingForm.getDocketNumber().trim())));
    				   //log.debug("Method Calling 2.....");
    				   //Check if Docket already in Consignment or Unique Cover Docket List
    	               if (duplicateCheckDockets(pTrackingForm, pTrackingForm.getDocketNumber().trim())){
    	                	errorMsg = "The Cover Docket "+pTrackingForm.getDocketNumber().trim()+" has already been added. <br/>Please check Consignment lists and Cover Dockets";
    	               }else{
		    				if(pTrackingForm.getDocCount() <= 20) {
		    					if(pTrackingForm.getValidationValues().contains("Docket number found")) {
		    						boolean dctError = false;
		    						//log.debug("TrackingAction - "+pTrackingForm.getNovelUserId()+" - validateDocketNumber - Docket number found");
		    						if (!addDocketCount){
		    							pTrackingForm.setDocCount(pTrackingForm.getDocCount()+1);
		    						}
		    						//Retrieve all Assignments linked to the Cover Docket
		    						LinkedHashMap<String, String> mapDctAssignments = new LinkedHashMap<String, String>();
		    						//Add to main Cover Docket List for duplicate check
									Docket unqDockets = populateDocket(pTrackingForm, String.valueOf(pTrackingForm.getDocCount()), pTrackingForm.getDocketNumber().trim());
									int docketAssignmentID = 702449 ;
								    //log.debug("TrackingAction - "+pTrackingForm.getNovelUserId()+" - validateDocketNumber - Retrieve linked Assignments for docketNumber="+pTrackingForm.getDocketNumber());
								    ArrayList dctAssignments = pGateWay.getDocketAssignments(pTrackingForm.getWebServiceURL(), docketAssignmentID ,pTrackingForm.getDocketNumber().trim(), "value1", "value2");
								    Iterator dctAssign = dctAssignments.iterator();
								    int assCounter = 0;
								    if(dctAssign.hasNext()){					    	
								    	List<Assignment> uniqueDctAssignList = new ArrayList<Assignment>();
								    	while(dctAssign.hasNext()){
								    		KeyValue test = (KeyValue) dctAssign.next();
											if(test.getValue().equals("No records returned")){
												//log.debug("TrackingAction - "+pTrackingForm.getNovelUserId()+" - validateDocketNumber - No linked Assignments returned");
											}else{
												assCounter++;
												//log.debug("TrackingAction - "+pTrackingForm.getNovelUserId()+" - validateDocketNumber  - Linked Assignments: Successful records returned - Student="+test.getKey().trim()+", Assignment="+test.getValue().trim());
												// Generate a Docket-Assignment map
												mapDctAssignments.put(String.valueOf(assCounter), test.getKey().trim()+"~"+test.getValue().trim());
												//Check if Assignment is a Unique Assignment in the Consignment List, if so, add to new map to remove from web page
												if (duplicateCheckConUniqueAssignment(pTrackingForm, test.getKey().trim(), test.getValue().trim())){
       		                                		//log.debug("TrackingAction - "+pTrackingForm.getNovelUserId()+" - validateDocketNumber - (mapRemoveConUnqAssignments) Assignment: "+test.getKey().trim()+"~"+test.getValue().trim()+" already in Consignment List, adding to remove map");
       		                                		mapRemoveConUnqAssignments.put(String.valueOf(assCounter), test.getKey().trim()+"~"+test.getValue().trim());
       		                                	}
												//Check if Assignment has been added manually, if so, add to new map to remove from web page
												if (duplicateCheckUniqueAssignment(pTrackingForm, test.getKey().trim(), test.getValue().trim())){
       		                                		//log.debug("TrackingAction - "+pTrackingForm.getNovelUserId()+" - validateDocketNumber - (duplicateCheckUniqueAssignment) Assignment: "+test.getKey().trim()+"~"+test.getValue().trim()+" already manually added, adding to remove map");
       		                                		mapRemoveAssignments.put(String.valueOf(assCounter), test.getKey().trim()+"~"+test.getValue().trim());
       		                                	}
												//Add to main docket list for duplicate check
												uniqueDctAssignList.add(populateAssignment(pTrackingForm, test.getKey(),test.getValue()));
												/**Debug**/
												Set<String> keys = mapDctAssignments.keySet();
										        for(String a:keys){
										        	//log.debug("Map Put: " +a+" -- "+mapDctAssignments.get(a));
										        }
											}
											
								    	}
				    					 //Add Cover Docket linked assignments to list for duplicate check
				    					 unqDockets.setAssignments(uniqueDctAssignList);
								    }else{
								    	//log.debug("TrackingAction - "+pTrackingForm.getNovelUserId()+" - validateDocketNumber - No linked Assignments returned");
								    	dctObj.put("Empty", "Cover Docket "+pTrackingForm.getDocketNumber()+" is invalid as it has no linked Assignments");
								    	dctError = true;
								    }
								    	//log.debug("TrackingAction - "+pTrackingForm.getNovelUserId()+" - validateDocketNumber - Checking Linked Assignments");
								    	/**Debug**/
								        Set<String> keys = mapDctAssignments.keySet();
								        for(String k:keys){
								        	//log.debug("Map Check: " +k+" -- "+mapDctAssignments.get(k));
								        }
								    //Remove Duplicate from Main Unique Assignment Map
		            	            if(!mapRemoveAssignments.isEmpty()){
		            	            	//log.debug("TrackingAction - "+pTrackingForm.getNovelUserId()+" - validateDocketNumber - (mapRemoveAssignments - unique) Remove assignment from Main Unique Map");
		            	            	Set<String> unqAssKeys = pTrackingForm.getMapUnqAssignments().keySet();
								        for(String k:unqAssKeys){
								        	//log.debug("TrackingAction - "+pTrackingForm.getNovelUserId()+" - validateDocketNumber - (mapRemoveAssignments - unique - K) "+k+"~"+pTrackingForm.getMapUnqAssignments().get(k));
								        	Set<String> unqRemAssKeys = mapRemoveAssignments.keySet();
									        for(String r:unqRemAssKeys){
									        	//log.debug("TrackingAction - "+pTrackingForm.getNovelUserId()+" - validateDocketNumber - (mapRemoveAssignments - unique - R) "+r+"~"+mapRemoveAssignments.get(r));
									        	if (pTrackingForm.getMapUnqAssignments().get(k).equals(mapRemoveAssignments.get(r))){
									        		// remove value for key 2
									        		//log.debug("TrackingAction - "+pTrackingForm.getNovelUserId()+" - validateDocketNumber - (mapRemoveAssignments from MapUnqAssignments: "+k+"~"+pTrackingForm.getMapUnqAssignments().get(k));
									        		pTrackingForm.getMapUnqAssignments().remove(k);
									        	}
									        }
								        }
			                        	//log.debug("TrackingAction - "+pTrackingForm.getNovelUserId()+" - validateDocketNumber - **************************************************************");
		            	            }

            	    				if(!mapRemoveAssignments.isEmpty()){
    	            	            	//log.debug("TrackingAction - "+pTrackingForm.getNovelUserId()+" - validateDocketNumber - (mapRemoveAssignments - docket) Adding Assignment to be removed from Consignment Unique Map on screen");
    	            	            	dctObj.put("RemoveAssignments", mapRemoveAssignments);
    	            	            }
            	    				if(!mapRemoveConUnqAssignments.isEmpty()){
    	            	            	//log.debug("TrackingAction - "+pTrackingForm.getNovelUserId()+" - validateDocketNumber - (mapRemoveConUnqAssignments - docket) Adding Assignment to be removed from Consignment Unique Map on screen");
    	            	            	dctObj.put("RemoveConAssignments", mapRemoveConUnqAssignments);
    	            	            }
            	    				if (!dctError){
				    					dctObj.put((String) pTrackingForm.getDocketNumber(), mapDctAssignments);
				    					pTrackingForm.getMasterDocketList().add(unqDockets);
            	    				}
		    					}else {
			    					//log.debug("TrackingAction - "+pTrackingForm.getNovelUserId()+" - validateDocketNumber - Docket number is not valid");
			    					errorMsg = "Cover Docket "+pTrackingForm.getDocketNumber()+" is invalid.<br/>Please Enter a valid Cover Docket Number";
		    					}
		    					//log.debug("TrackingAction - "+pTrackingForm.getNovelUserId()+" - validateDocketNumber - Final Docket Count="+pTrackingForm.getDocCount());
		    				}else{
		    					errorMsg = "You may only enter 20 docket numbers at a time";
		    				}	    	
    	               }
	    				pTrackingForm.setDocketNumber("");
	    		   }else {
	    			    //log.debug("TrackingAction - "+pTrackingForm.getNovelUserId()+" - validateDocketNumber - Docket number is not valid");
	    			    errorMsg = "Cover Docket "+pTrackingForm.getDocketNumber()+" is invalid.<br/>Please Enter a valid Cover Docket Number";
	    		   }
    		 	
    		   }catch(Exception ex){
    			   errorMsg = "A WebService link is not responding. Please try again later / "+ex;

    		   }

    		   //Final check of main list for duplicate testing
    		   //log.debug("TrackingAction - "+pTrackingForm.getNovelUserId()+" - validateDocketNumber - *******************************************************");
			
				if(!pTrackingForm.getMasterDocketList().isEmpty()) {
					for(Docket docketTest : pTrackingForm.getMasterDocketList()) {
						//log.debug("TrackingAction - "+pTrackingForm.getNovelUserId()+" - validateDocketNumber - Cover Docket Test - Docket ID : " +docketTest.getDocketID());
						//log.debug("TrackingAction - "+pTrackingForm.getNovelUserId()+" - validateDocketNumber - Cover Docket Test - Docket Number : " +docketTest.getDocketNumber());
						//log.debug("TrackingAction - "+pTrackingForm.getNovelUserId()+" - validateDocketNumber - *******************************************************");
						if(docketTest.getAssignments() != null ) {
							for(Assignment assignment : docketTest.getAssignments()) {
								//log.debug("TrackingAction - "+pTrackingForm.getNovelUserId()+" - validateDocketNumber - Cover Docket Test - Dct Student Number : " +assignment.getStudentNumber());
								//log.debug("TrackingAction - "+pTrackingForm.getNovelUserId()+" - validateDocketNumber - Cover Docket Test - Dct Unique Assignment : " +assignment.getUniqueAssignmentNumber());
								//log.debug("TrackingAction - "+pTrackingForm.getNovelUserId()+" - validateDocketNumber - *******************************************************");
							}
						}
					}
					//log.debug("TrackingAction - "+pTrackingForm.getNovelUserId()+" - validateDocketNumber - *******************************************************");
				}
    		   
		// Convert the map to json
		PrintWriter out = response.getWriter();
		if (errorMsg != null && !errorMsg.equals("")){
		   	Map<String, String> mapErrors = new LinkedHashMap<String, String>();
		   	mapErrors.put("Error",errorMsg);
		   	JSONObject jsonObject = JSONObject.fromObject(mapErrors);
		   	out.write(jsonObject.toString());
		   	out.flush();
		}else{
		   	JSONObject jsonObject = JSONObject.fromObject(dctObj);
        	//log.debug("TrackingAction - "+pTrackingForm.getNovelUserId()+" - validateDocketNumber - Final - **************************************************************");
        	//log.debug("TrackingAction - "+pTrackingForm.getNovelUserId()+" - validateDocketNumber - Final - jsonObject="+jsonObject.toString());
        	//log.debug("TrackingAction - "+pTrackingForm.getNovelUserId()+" - validateDocketNumber - Final - **************************************************************");
		   	out.write(jsonObject.toString());
		   	out.flush();
		}
		        
   		   //log.debug("jsonString:"+dctObj.toString());
   		   //PrintWriter out = response.getWriter();
   		   //out.write(dctObj.toString());
   		   //out.flush();

   		   return null; //Returning null to prevent struts from interfering with ajax/json
    }

    public ActionForward validateStudentUniqueNumber(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception{

        prepareBackendSession();
        String errorMsg = "";
        TrackingForm pTrackingForm = (TrackingForm)form;
//      WebServiceGateWay pGateWay = new WebServiceGateWay();
        try
        {
        	//log.debug("TrackingAction - "+pTrackingForm.getNovelUserId()+" - validateStudentUniqueNumber");
        	if(pTrackingForm.getUniqueAssCount() == 0){
            	//log.debug("TrackingAction - "+pTrackingForm.getNovelUserId()+" - validateStudentUniqueNumber - First Unique Assignment Count="+pTrackingForm.getUniqueAssCount());
        	}
        	
        	
        	//log.debug("TrackingAction - "+pTrackingForm.getNovelUserId()+" - validateStudentUniqueNumber");
            Session currentSession = sessionManager.getCurrentSession();
            HttpSession session = request.getSession(true);

            if(pTrackingForm.getStudNo() != null && !pTrackingForm.getStudNo().trim().equals("") && isNumberValid(pTrackingForm.getStudNo().trim()) && !(pTrackingForm.getStudNo().trim().length()>8) && pTrackingForm.getUniqueAssignmentNr() != null && !pTrackingForm.getUniqueAssignmentNr().trim().equals("") && isNumberValid(pTrackingForm.getUniqueAssignmentNr().trim()))
            {
                DocketNumberDetails details = new DocketNumberDetails();
                int studentAssignID = 196148;
                pTrackingForm.setValidationResult(pGateWay.studentAssignNumberValidation(pTrackingForm.getWebServiceURL() ,studentAssignID, pTrackingForm.getStudNo().trim(), pTrackingForm.getUniqueAssignmentNr().trim()));
                //log.debug("TrackingAction - "+pTrackingForm.getNovelUserId()+" - validateStudentUniqueNumber - uniqueAssCount="+pTrackingForm.getUniqueAssCount());
                if (duplicateCheckAllAssignments(pTrackingForm, pTrackingForm.getStudNo().trim(), pTrackingForm.getUniqueAssignmentNr().trim())){
                	errorMsg = "The Student Number "+pTrackingForm.getStudNo().trim()+" and Unique Assignment number "+pTrackingForm.getUniqueAssignmentNr().trim()+" combination has already been added. <br/>Please check Consignment lists, Cover Dockets and Unique Assignments";
                }else{
	                if(pTrackingForm.getUniqueAssCount() < 20){
	                    if(pTrackingForm.getValidationResult().contains("Valid - assignment found")){
	                    	pTrackingForm.setUniqueAssCount(pTrackingForm.getUniqueAssCount()+1);
	                        details.setStudentNumber(pTrackingForm.getStudNo().trim());
	                        details.setUniqueAssignmentNumber(pTrackingForm.getUniqueAssignmentNr().trim());
	                        //log.debug("TrackingAction - "+pTrackingForm.getNovelUserId()+" - validateStudentUniqueNumber - StudentNumber="+pTrackingForm.getStudNo().trim()+", UniqueAssignmentNumber="+pTrackingForm.getUniqueAssignmentNr().trim());
	                        // Generate a map
	                        pTrackingForm.getMapUnqAssignments().put(Integer.toString(pTrackingForm.getUniqueAssCount()), (String) pTrackingForm.getStudNo().trim()+"~"+(String) pTrackingForm.getUniqueAssignmentNr().trim());
	                    }else{
	                    	errorMsg = pTrackingForm.getValidationResult();
	                    }
	                    //log.debug("TrackingAction - "+pTrackingForm.getNovelUserId()+" - validateStudentUniqueNumber - Final Unique Assignment Count="+pTrackingForm.getUniqueAssCount());

	                }else{
	                	errorMsg = "You may only enter 20 docket numbers at a time";
	                }
                }
            }else {
            	errorMsg = "Assignment "+pTrackingForm.getStudNo().trim()+" / "+pTrackingForm.getUniqueAssignmentNr().trim()+" is invalid.<br/>Please Enter a valid Student and Unique Assignment Number";
 		   	}
           
        }catch(Exception ex){
        	errorMsg = "A WebService link is not responding. Please try again later / "+ex;
        }
		// Convert the map to json
		PrintWriter out = response.getWriter();
        if (errorMsg != null && !errorMsg.equals("")){
        	Map<String, String> mapErrors = new LinkedHashMap<String, String>();
        	mapErrors.put("Error",errorMsg);
        	JSONObject jsonObject = JSONObject.fromObject(mapErrors);
        	out.write(jsonObject.toString());
        	out.flush();
        }else{
        	JSONObject jsonObject = JSONObject.fromObject(pTrackingForm.getMapUnqAssignments());
        	//log.debug("TrackingAction - "+pTrackingForm.getNovelUserId()+" - validateStudentUniqueNumber - Final - **************************************************************");
        	//log.debug("TrackingAction - "+pTrackingForm.getNovelUserId()+" - validateStudentUniqueNumber - Final - jsonObject="+jsonObject.toString());
        	//log.debug("TrackingAction - "+pTrackingForm.getNovelUserId()+" - validateStudentUniqueNumber - Final - **************************************************************");
        	out.write(jsonObject.toString());
        	out.flush();
        }

		return null; //Returning null to prevent struts from interfering with ajax/json
    }
    
    public ActionForward retrieveCSDInformation(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
       
    	String returnType = null;
    	int webserviceId = 958843; /*VALIDATE_USER 958843 A */
    	ActionMessages messages = new ActionMessages();
        TrackingForm pTrackingForm = (TrackingForm)form;
        sessionManager = (SessionManager) ComponentManager.get(SessionManager.class);
        Session currentSession = sessionManager.getCurrentSession();

//        WebServiceGateWay pGateWay = new WebServiceGateWay();

        //Validate User
       if (pTrackingForm.getNovelUserId() != null && !pTrackingForm.getNovelUserId().isEmpty()){
    	   String userRecord[] = new String[2];
    	   if((currentSession.getAttribute("backEndSessionCookieValue") == null) || (currentSession.getAttribute("backEndSessionCookieValue").toString() == null) || (currentSession.getAttribute("backEndSessionCookieValue").toString().equals(""))) {
    		   userRecord = pGateWay.validateUserStartSession(webServiceURL, webserviceId,"value1", pTrackingForm.getNovelUserId());
    		   currentSession.setAttribute("backEndSessionCookieValue",userRecord[0]);
    	   } else {
    		   log.debug("currentSession: "+currentSession.getId());
    		   log.debug("currentSession attribute backEndSessionCookieValue: "+currentSession.getAttribute("backEndSessionCookieValue"));
    		   log.debug("currentSession attribute backEndSessionCookieValue toString: "+currentSession.getAttribute("backEndSessionCookieValue").toString());
    		   userRecord[0] = prepareBackendSession(currentSession.getAttribute("backEndSessionCookieValue").toString());
    		   userRecord[1] = pGateWay.validateUser(webServiceURL, webserviceId,"value1", pTrackingForm.getNovelUserId());
    	   }
           if (userRecord[1] == null  || userRecord[1].equals("")){
           	log.debug("TrackingAction - retrieveCSDInformation - NovelUserId NOT Found");
           	messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("message.generalmessage", "User's Personnel record not found"));
           	addErrors(request, messages);
        	return mapping.findForward(pTrackingForm.getUser_Selection());
//           	return mapping.findForward(USER_SELECTION);
           }
       }else{
    	   //log.debug("TrackingAction - "+pTrackingForm.getNovelUserId()+" - retrieveCSDInformation - novelUserId NOT Found");
    	   messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("message.generalmessage", "User's Network ID not found"));
    	   addErrors(request, messages);
    	   return mapping.findForward(pTrackingForm.getUser_Selection());
       }
         
       try{
            HttpSession session = request.getSession(true);
            resetForm(pTrackingForm, "retrieveCSDInformation");
            resetLocal(pTrackingForm,"retrieveCSDInformation");
            pTrackingForm.setUserSelection(request.getParameter("type"));
            //log.debug("TrackingAction - "+pTrackingForm.getNovelUserId()+" - retrieveCSDInformation - user id="+pTrackingForm.getNovelUserId());
            
            if(pTrackingForm.getNovelUserId() != null && pTrackingForm.getNovelUserId().length() > 0 && pTrackingForm.getUserSelection() != null && !pTrackingForm.getUserSelection().equals("")){
                if(pTrackingForm.getUserSelection().equals("out")){
                    returnType = "checkout";
                }else if (pTrackingForm.getUserSelection().equals("in")){
                    returnType = "checkin";
                }else if (pTrackingForm.getUserSelection().equals("printpdf")){
                	returnType = "printpdf";
                }else if (pTrackingForm.getUserSelection().equals("search")){
                	returnType = "search";
                }else if (pTrackingForm.getUserSelection().equals("report")){
                	returnType = "report";
                }
            }else{
                returnType = "error";
            }
       }catch(Exception e){
            messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("message.generalmessage", "retrieveCSDInformation Error! Please try again / " +e));
            addErrors(request, messages);
            e.printStackTrace();
            return mapping.findForward(pTrackingForm.getUser_Selection());
       }
       //log.debug("TrackingAction - "+pTrackingForm.getNovelUserId()+" - retrieveCSDInformation - returnType="+returnType);
       return mapping.findForward(returnType);
    }

    public ActionForward getUserInfo(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
    	
    	String novelUserId = "";
    	int webserviceId = 958843; /*VALIDATE_USER 958843 A */
    	
    	ActionMessages messages = new ActionMessages();
    	TrackingForm pTrackingForm = (TrackingForm)form;
//    	WebServiceGateWay pGateWay = new WebServiceGateWay();
        sessionManager = (SessionManager) ComponentManager.get(SessionManager.class);
        Session currentSession = sessionManager.getCurrentSession();
        
        //log.debug("TrackingAction - "+pTrackingForm.getNovelUserId()+" - getUserInfo - NovelUserId (Form) ="+pTrackingForm.getNovelUserId());
        //log.debug("TrackingAction - "+pTrackingForm.getNovelUserId()+" - getUserInfo - NovelUserId (Parameter) ="+request.getParameter("NETWORKID"));
        
        if (pTrackingForm.getNovelUserId() == null || pTrackingForm.getNovelUserId().isEmpty()){
	        novelUserId = request.getParameter("NETWORKID");
	        
    		//Hardcoded Test User
	        //novelUserId = "KRUGEGJ";
	        //pTrackingForm.setUserID("3346");
            //pTrackingForm.setNovelUserId("3346");
	        
	        if(novelUserId != null && !novelUserId.isEmpty() && !"null".equalsIgnoreCase(novelUserId)){
	        	//log.debug("TrackingAction - "+pTrackingForm.getNovelUserId()+" - getUserInfo - novelUserId in Not Empty");
	            pTrackingForm.setNovelUserId(novelUserId);
	        } else{
	        	if(currentSession.getUserEid() != null && !"".equals(currentSession.getUserEid())){
		        	//log.debug("TrackingAction - "+pTrackingForm.getNovelUserId()+" - getUserInfo - novelUserId in Empty - Getting Session UserEid");
	        		pTrackingForm.setUserID(currentSession.getUserEid());
		            pTrackingForm.setNovelUserId(currentSession.getUserEid());
	        	}else{
	        		//log.debug("TrackingAction - "+pTrackingForm.getNovelUserId()+" - getUserInfo - UserEid NOT Found");
	        		messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("message.generalmessage", "User's Personnel record (UserEid) not found"));
		            	addErrors(request, messages);
		            	return mapping.findForward(pTrackingForm.getUser_Selection());
	        	}
	        }
        }

        //Validate User
       if (pTrackingForm.getNovelUserId() != null && !pTrackingForm.getNovelUserId().isEmpty()){
    	   String userRecord[] = new String[2];
    	   if((currentSession.getAttribute("backEndSessionCookieValue") == null) || (currentSession.getAttribute("backEndSessionCookieValue").toString() == null) || (currentSession.getAttribute("backEndSessionCookieValue").toString().equals(""))) {
    		   userRecord = pGateWay.validateUserStartSession(webServiceURL, webserviceId,"value1", pTrackingForm.getNovelUserId());
    		   log.debug("started new backend session and setting tomcat session with backEndSessionCookieValue: "+userRecord[0]);
               currentSession.setAttribute("backEndSessionCookieValue", userRecord[0]);
    	   } else {
    		   
    		   userRecord[0] = prepareBackendSession(currentSession.getAttribute("backEndSessionCookieValue").toString());
    		   userRecord[1] = pGateWay.validateUser(webServiceURL, webserviceId,"value1", pTrackingForm.getNovelUserId());
    	   }
    	   //String userRecord[] = pGateWay.validateUser(webServiceURL, webserviceId,"value1", pTrackingForm.getNovelUserId());
            if (userRecord[1] == null  || userRecord[1].equals("")){
            	log.debug("TrackingAction - getUserInfo - NovelUserId NOT Found");
            	messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("message.generalmessage", "User's Personnel record not found"));
            	addErrors(request, messages);
         	   return mapping.findForward(pTrackingForm.getUser_Selection());
            }
       }

       if (request.getParameter("MACCODE") != null && !"".equals(request.getParameter("MACCODE").trim())){
       		//log.debug("TrackingAction - "+pTrackingForm.getNovelUserId()+" - getUserInfo - MACCODE="+request.getParameter("MACCODE") +", therefore LSS User");
       		//log.debug("TrackingAction - "+pTrackingForm.getNovelUserId()+" - getUserInfo - MACCODE="+request.getParameter("MACCODE") +", therefore LSS User");
       		
       		pTrackingForm.setMacCode(request.getParameter("MACCODE"));
       		pTrackingForm.setLssClient(true);
       }else{
    	   	pTrackingForm.setLssClient(false); //Should be false from Form variable, but set it jut the same
    	   	//log.debug("TrackingAction - "+pTrackingForm.getNovelUserId()+" - getUserInfo - MACCODE is Empty, therefore myUnisa User");
       }
       
        return mapping.findForward(pTrackingForm.getUser_Selection());
    }

    public ActionForward clear(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
    	
    	TrackingForm pTrackingForm = (TrackingForm)form;
    	//log.debug("TrackingAction - "+pTrackingForm.getNovelUserId()+" - clear - Calling clear method");

        HttpSession session = request.getSession(true);
        String returnType="";
        if(pTrackingForm.getUserSelection() != null && !pTrackingForm.getUserSelection().equals("") && pTrackingForm.getUserSelection().equals("out")){
            //log.debug("Calling clear method from book out");
            resetForm(pTrackingForm, "clear+"+pTrackingForm.getUserSelection());
            returnType = "checkout";
        }else if(pTrackingForm.getUserSelection() != null && !pTrackingForm.getUserSelection().equals("") && pTrackingForm.getUserSelection().equals("in")){
        	//log.debug("Calling clear method from book in");
        	resetForm(pTrackingForm, "clear+"+pTrackingForm.getUserSelection());
            returnType = "checkin";
        }else if(pTrackingForm.getUserSelection() != null && !pTrackingForm.getUserSelection().equals("") && pTrackingForm.getUserSelection().equals("printpdf")){
        	//log.debug("Calling clear method from PrintPDF");
        	resetForm(pTrackingForm, "clear+"+pTrackingForm.getUserSelection());
        	returnType = "printpdf";
        }else if(pTrackingForm.getUserSelection() != null && !pTrackingForm.getUserSelection().equals("") && pTrackingForm.getUserSelection().equals("search")){
        	//log.debug("Calling clear method from Search");
        	resetForm(pTrackingForm, "clear+"+pTrackingForm.getUserSelection());
        	returnType = "search";
        }else if(pTrackingForm.getUserSelection() != null && !pTrackingForm.getUserSelection().equals("") && pTrackingForm.getUserSelection().equals("report")){
        	//log.debug("Calling clear method from Report");
        	resetForm(pTrackingForm, "clear+"+pTrackingForm.getUserSelection());
        	returnType = "report";
        }
        return mapping.findForward(returnType);
    }

    public ActionForward displayRegionalOfficeDSAAInformation(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws IOException{

    	TrackingForm pTrackingForm = (TrackingForm)form;
    	//log.debug("TrackingAction - "+pTrackingForm.getNovelUserId()+" - displayRegionalOfficeDSAAInformation - **************************************************************");
       	//log.debug("TrackingAction - "+pTrackingForm.getNovelUserId()+" - displayRegionalOfficeDSAAInformation - Input="+request.getParameter("input"));

    	JSONObject dsaaRegionObj = new JSONObject();
       	Map<String, String> mapDSAARegions = new LinkedHashMap<String, String>();

        try{
   			mapDSAARegions.put("0", "FLORIDA~FLORIDA");
   			mapDSAARegions.put("1", "SUNNYSIDE~SUNNYSIDE");
   			dsaaRegionObj.put("RegionalOfficesDSAA",mapDSAARegions);
        }catch(Exception ex){
        	dsaaRegionObj.put("Error","The displayRegionalOfficeDSAAInformation Web Service Failed! Please try again / <br/>"+ex);
        }
        
		// Convert the map to json
		PrintWriter out = response.getWriter();
       	JSONObject jsonObject = JSONObject.fromObject(dsaaRegionObj);
       	//log.debug("TrackingAction - "+pTrackingForm.getNovelUserId()+" - displayRegionalOfficeDSAAInformation - Final - **************************************************************");
       	//log.debug("TrackingAction - "+pTrackingForm.getNovelUserId()+" - displayRegionalOfficeDSAAInformation - Final - jsonObject="+jsonObject.toString());
       	//log.debug("TrackingAction - "+pTrackingForm.getNovelUserId()+" - displayRegionalOfficeDSAAInformation - Final - **************************************************************");
       	out.write(jsonObject.toString());
       	out.flush();

		return null; //Returning null to prevent struts from interfering with ajax/json
    }
    
    public ActionForward displayCollegeInformation(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws IOException{

    	prepareBackendSession();
    	TrackingForm pTrackingForm = (TrackingForm)form;
//    	WebServiceGateWay pGateWay = new WebServiceGateWay();
    	//log.debug("TrackingAction - "+pTrackingForm.getNovelUserId()+" - displayCollegeInformation - **************************************************************");
    	int keyCounter = 0;
    	JSONObject collegeObj = new JSONObject();
       	Map<String, String> mapColleges = new LinkedHashMap<String, String>();

        try{
    		int webserviceId = 397381;
    		ArrayList<KeyValue> getValues = pGateWay.getCollegeList(pTrackingForm.getWebServiceURL(), webserviceId,"value1","value2");
    		Iterator<KeyValue> it = getValues.iterator();
    		
    		while(it.hasNext()){
    			KeyValue test = (KeyValue) it.next();
    			mapColleges.put(Integer.toString(keyCounter), test.getKey()+"~"+test.getValue());
    			keyCounter++;
    		}
    		collegeObj.put("Colleges",mapColleges);
        }catch(Exception ex){
        	collegeObj.put("Error","The getCollegeList Web Service Failed! Please try again / <br/>"+ex);
        }
        
		// Convert the map to json
		PrintWriter out = response.getWriter();
       	JSONObject jsonObject = JSONObject.fromObject(collegeObj);
       	//log.debug("TrackingAction - "+pTrackingForm.getNovelUserId()+" - displayCollegeInformation - Final - **************************************************************");
       	//log.debug("TrackingAction - "+pTrackingForm.getNovelUserId()+" - displayCollegeInformation - Final - jsonObject="+jsonObject.toString());
       	//log.debug("TrackingAction - "+pTrackingForm.getNovelUserId()+" - displayCollegeInformation - Final - **************************************************************");
       	out.write(jsonObject.toString());
       	out.flush();

		return null; //Returning null to prevent struts from interfering with ajax/json
    }

    public ActionForward displaySchoolInformation(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws IOException{
        
    	prepareBackendSession();
    	TrackingForm pTrackingForm = (TrackingForm)form;
//    	WebServiceGateWay pGateWay = new WebServiceGateWay();
    	//log.debug("TrackingAction - "+pTrackingForm.getNovelUserId()+" - displaySchoolInformation - **************************************************************");
    	int keyCounter = 0;
    	JSONObject schoolObj = new JSONObject();
       	Map<String, String> mapSchools = new LinkedHashMap<String, String>();
       	
        try{
        	int schoolID = 964504;
        	if(pTrackingForm.getCollege() != null && !pTrackingForm.getCollege().equals("-1")){
	        	int collegeCode = Integer.parseInt(pTrackingForm.getCollege().trim());
	        	//log.debug("TrackingAction - "+pTrackingForm.getNovelUserId()+" - displaySchoolInformation - College="+collegeCode);
	    		ArrayList<KeyValue> getValues = pGateWay.getSchoolList1(pTrackingForm.getWebServiceURL() ,schoolID,"value1","value3",collegeCode);
	    		Iterator<KeyValue> it = getValues.iterator();
	    		
	    		while(it.hasNext()){
	    			KeyValue test = (KeyValue) it.next();
	    			mapSchools.put(Integer.toString(keyCounter), test.getKey()+"~"+test.getValue());
	    			keyCounter++;
	    		}
	    		schoolObj.put("Schools",mapSchools);
        	}
        }catch(Exception ex){
        	schoolObj.put("Error","The getSchoolList Web Service Failed! Please try again / <br/>"+ex);
        }
        
		// Convert the map to json
		PrintWriter out = response.getWriter();
       	JSONObject jsonObject = JSONObject.fromObject(schoolObj);
       	//log.debug("TrackingAction - "+pTrackingForm.getNovelUserId()+" - displaySchoolInformation - Final - **************************************************************");
       	//log.debug("TrackingAction - "+pTrackingForm.getNovelUserId()+" - displaySchoolInformation - Final - jsonObject="+jsonObject.toString());
       	//log.debug("TrackingAction - "+pTrackingForm.getNovelUserId()+" - displaySchoolInformation - Final - **************************************************************");
       	out.write(jsonObject.toString());
       	out.flush();

		return null; //Returning null to prevent struts from interfering with ajax/json
    }
    
    public ActionForward displayDepartmentInformation(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws IOException{
        
    	prepareBackendSession();
    	TrackingForm pTrackingForm = (TrackingForm)form;
//    	WebServiceGateWay pGateWay = new WebServiceGateWay();
    	//log.debug("TrackingAction - "+pTrackingForm.getNovelUserId()+" - displayDepartmentInformation - **************************************************************");
    	int keyCounter = 0;
    	JSONObject departmentObj = new JSONObject();
       	Map<String, String> mapDepartments = new LinkedHashMap<String, String>();
       	
        try{
        	int departmentId = 571560;
        	
        	if(pTrackingForm.getSchool() != null && pTrackingForm.getCollege() != null && !pTrackingForm.getSchool().equals("-1") && !pTrackingForm.getCollege().equals("-1")){
	        	int collegeCode = Integer.parseInt(pTrackingForm.getCollege().trim());
	        	int schoolCode = Integer.parseInt(pTrackingForm.getSchool().trim());
	        	//log.debug("TrackingAction - "+pTrackingForm.getNovelUserId()+" - displayDepartmentInformation - College="+collegeCode);
	        	//log.debug("TrackingAction - "+pTrackingForm.getNovelUserId()+" - displayDepartmentInformation - School="+schoolCode);
	        	ArrayList<KeyValue> getValues = pGateWay.getDepartmentList1(pTrackingForm.getWebServiceURL() ,departmentId,"value1","value2",collegeCode,schoolCode);
	    		Iterator<KeyValue> it = getValues.iterator();
	    		
	    		while(it.hasNext()){
	    			KeyValue test = (KeyValue) it.next();
	    			mapDepartments.put(Integer.toString(keyCounter), test.getKey()+"~"+test.getValue());
	    			keyCounter++;
	    		}
	    		departmentObj.put("Departments",mapDepartments);
        	}
        }catch(Exception ex){
        	departmentObj.put("Error","The getDepartmentList Web Service Failed! Please try again / <br/>"+ex);
        }
        
		// Convert the map to json
		PrintWriter out = response.getWriter();
       	JSONObject jsonObject = JSONObject.fromObject(departmentObj);
       	//log.debug("TrackingAction - "+pTrackingForm.getNovelUserId()+" - displayDepartmentInformation - Final - **************************************************************");
       	//log.debug("TrackingAction - "+pTrackingForm.getNovelUserId()+" - displayDepartmentInformation - Final - jsonObject="+jsonObject.toString());
       	//log.debug("TrackingAction - "+pTrackingForm.getNovelUserId()+" - displayDepartmentInformation - Final - **************************************************************");
       	out.write(jsonObject.toString());
       	out.flush();

		return null; //Returning null to prevent struts from interfering with ajax/json
    }
    
    public ActionForward displayModuleInformation(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws IOException{
        
    	prepareBackendSession();
    	TrackingForm pTrackingForm = (TrackingForm)form;
//    	WebServiceGateWay pGateWay = new WebServiceGateWay();
    	//log.debug("TrackingAction - "+pTrackingForm.getNovelUserId()+" - displayModuleInformation - **************************************************************");
    	int keyCounter = 0;
    	JSONObject moduleObj = new JSONObject();
       	Map<String, String> mapModules = new LinkedHashMap<String, String>();

        try{
        	int moduleID = 347652;
        	if(pTrackingForm.getSchool() != null && pTrackingForm.getCollege() != null && !pTrackingForm.getSchool().equals("-1") && !pTrackingForm.getCollege().equals("-1") && pTrackingForm.getDepartment() != null && !pTrackingForm.getDepartment().equals("-1")){
	        	int collegeCode = Integer.parseInt(pTrackingForm.getCollege().trim());
	        	int schoolCode = Integer.parseInt(pTrackingForm.getSchool().trim());
	        	int departmentCode = Integer.parseInt(pTrackingForm.getDepartment().trim());
	        	//log.debug("TrackingAction - "+pTrackingForm.getNovelUserId()+" - displayModuleInformation - College="+collegeCode);
	        	//log.debug("TrackingAction - "+pTrackingForm.getNovelUserId()+" - displayModuleInformation - School="+schoolCode);
	        	//log.debug("TrackingAction - "+pTrackingForm.getNovelUserId()+" - displayModuleInformation - Department="+departmentCode);
	        	ArrayList<KeyValue> getValues = pGateWay.getModuleList1(pTrackingForm.getWebServiceURL() ,moduleID,"value1","value2",collegeCode,schoolCode,departmentCode);
	    		Iterator<KeyValue> it = getValues.iterator();
	   		
	    		while(it.hasNext()){
	    			KeyValue test = (KeyValue) it.next();
	    			mapModules.put(Integer.toString(keyCounter), test.getKey()+"~"+test.getValue());
	    			keyCounter++;
	    		}
	    		moduleObj.put("Modules",mapModules);
        	}
        }catch(Exception ex){
        	moduleObj.put("Error","The getModuleList1 Web Service Failed! Please try again / <br/>"+ex);
        }
        
		// Convert the map to json
		PrintWriter out = response.getWriter();
       	JSONObject jsonObject = JSONObject.fromObject(moduleObj);
       	//log.debug("TrackingAction - "+pTrackingForm.getNovelUserId()+" - displayModuleInformation - Final - **************************************************************");
       	//log.debug("TrackingAction - "+pTrackingForm.getNovelUserId()+" - displayModuleInformation - Final - jsonObject="+jsonObject.toString());
       	//log.debug("TrackingAction - "+pTrackingForm.getNovelUserId()+" - displayModuleInformation - Final - **************************************************************");
       	out.write(jsonObject.toString());
       	out.flush();

		return null; //Returning null to prevent struts from interfering with ajax/json
    }

public ActionForward displayProvinceInformation(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws IOException{
    
	prepareBackendSession();
	TrackingForm pTrackingForm = (TrackingForm)form;
//	WebServiceGateWay pGateWay = new WebServiceGateWay();
	//log.debug("TrackingAction - "+pTrackingForm.getNovelUserId()+" - displayProvinceInformation - **************************************************************");
	int keyCounter = 0;
	JSONObject provinceObj = new JSONObject();
   	Map<String, String> mapProvinces = new LinkedHashMap<String, String>();

    try{
    	int provinceWebserviceId = 304912;
    	ArrayList<KeyValue> getValues = pGateWay.getProvinceList(pTrackingForm.getWebServiceURL(),provinceWebserviceId,"value1","value2");
    	Iterator<KeyValue> it = getValues.iterator();
   		
    	while(it.hasNext()){
    		KeyValue test = (KeyValue) it.next();
    		mapProvinces.put(Integer.toString(keyCounter), test.getKey()+"~"+test.getValue());
    		keyCounter++;
    	}
    	provinceObj.put("Provinces",mapProvinces);

    }catch(Exception ex){
    	provinceObj.put("Error","The getProvinceList Web Service Failed! Please try again / <br/>"+ex);
    }
    
	// Convert the map to json
	PrintWriter out = response.getWriter();
   	JSONObject jsonObject = JSONObject.fromObject(provinceObj);
   	//log.debug("TrackingAction - "+pTrackingForm.getNovelUserId()+" - displayProvinceInformation - Final - **************************************************************");
   	//log.debug("TrackingAction - "+pTrackingForm.getNovelUserId()+" - displayProvinceInformation - Final - jsonObject="+jsonObject.toString());
   	//log.debug("TrackingAction - "+pTrackingForm.getNovelUserId()+" - displayProvinceInformation - Final - **************************************************************");
   	out.write(jsonObject.toString());
   	out.flush();

	return null; //Returning null to prevent struts from interfering with ajax/json
}

    public ActionForward displayRegionalOfficeInformation(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws IOException{
        
    	prepareBackendSession();
    	TrackingForm pTrackingForm = (TrackingForm)form;
//    	WebServiceGateWay pGateWay = new WebServiceGateWay();
    	//log.debug("TrackingAction - "+pTrackingForm.getNovelUserId()+" - displayRegionalOfficeInformation - **************************************************************");
    	int keyCounter = 0;
    	JSONObject officeObj = new JSONObject();
       	Map<String, String> mapOffices = new LinkedHashMap<String, String>();
       	
        try{
        	int regionalOfficeWebserviceId = 768398;
        	int provinceCode = Integer.parseInt(pTrackingForm.getProvince());
        	//log.debug("TrackingAction - "+pTrackingForm.getNovelUserId()+" - displayRegionalOfficeInformation - Province="+provinceCode);
            ArrayList<KeyValue> getValues = pGateWay.getRegionalOfficeList(pTrackingForm.getWebServiceURL(),regionalOfficeWebserviceId,"value1","value2",provinceCode);
            Iterator<KeyValue> it = getValues.iterator();
	   		
	    	while(it.hasNext()){
	    		KeyValue test = (KeyValue) it.next();
	    		mapOffices.put(Integer.toString(keyCounter), test.getKey()+"~"+test.getValue());
	    		keyCounter++;
	    	}
	    	officeObj.put("RegionalOffices",mapOffices);
        }catch(Exception ex){
        	officeObj.put("Error","The getRegionalOffice Web Service Failed! Please try again / <br/>"+ex);
        }
        
		// Convert the map to json
		PrintWriter out = response.getWriter();
       	JSONObject jsonObject = JSONObject.fromObject(officeObj);
       	//log.debug("TrackingAction - "+pTrackingForm.getNovelUserId()+" - displayRegionalOfficeInformation - Final - **************************************************************");
       	//log.debug("TrackingAction - "+pTrackingForm.getNovelUserId()+" - displayRegionalOfficeInformation - Final - jsonObject="+jsonObject.toString());
       	//log.debug("TrackingAction - "+pTrackingForm.getNovelUserId()+" - displayRegionalOfficeInformation - Final - **************************************************************");
       	out.write(jsonObject.toString());
       	out.flush();

		return null; //Returning null to prevent struts from interfering with ajax/json
    }
    
    public ActionForward displayBuildingInformation(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws IOException{
        
    	prepareBackendSession();
    	TrackingForm pTrackingForm = (TrackingForm)form;
//    	WebServiceGateWay pGateWay = new WebServiceGateWay();
    	//log.debug("TrackingAction - "+pTrackingForm.getNovelUserId()+" - displayBuildingInformation - **************************************************************");
    	int keyCounter = 0;
    	JSONObject buildingObj = new JSONObject();
       	Map<String, String> mapBuildings = new LinkedHashMap<String, String>();
       	
       	//log.debug("TrackingAction - "+pTrackingForm.getNovelUserId()+" - displayBuildingInformation - Building="+pTrackingForm.getBuilding());
       	
        try{
        	int buildingWebserviceID = 408107 ;
        	String noSpace = pTrackingForm.getBuilding().trim().replace(" ", "%20");
        	//log.debug("TrackingAction - "+pTrackingForm.getNovelUserId()+" - displayBuildingInformation - getBuildingList - Building="+noSpace);
        	ArrayList<KeyValue> getValues = pGateWay.getBuildingList(pTrackingForm.getWebServiceURL() ,buildingWebserviceID,"id","value1", noSpace);
           	
	    	Iterator<KeyValue> it = getValues.iterator();
	   		
	    		while(it.hasNext()){
	    			KeyValue test = (KeyValue) it.next();
	    			mapBuildings.put(Integer.toString(keyCounter), test.getValue()+"~"+test.getValue());
	    			keyCounter++;
	    		}
	    		buildingObj.put("Buildings",mapBuildings);
        }catch(Exception ex){
        	buildingObj.put("Error","The getBuildingList Web Service Failed! Please try again / <br/>"+ex);
        }
        
		// Convert the map to json
		PrintWriter out = response.getWriter();
       	JSONObject jsonObject = JSONObject.fromObject(buildingObj);
       	//log.debug("TrackingAction - "+pTrackingForm.getNovelUserId()+" - displayBuildingInformation - Final - **************************************************************");
       	//log.debug("TrackingAction - "+pTrackingForm.getNovelUserId()+" - displayBuildingInformation - Final - jsonObject="+jsonObject.toString());
       	//log.debug("TrackingAction - "+pTrackingForm.getNovelUserId()+" - displayBuildingInformation - Final - **************************************************************");
       	out.write(jsonObject.toString());
       	out.flush();

		return null; //Returning null to prevent struts from interfering with ajax/json
    }
 
	public ActionForward processInput(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception{

    	prepareBackendSession();
	   	TrackingForm pTrackingForm = (TrackingForm) form;
	   	//log.debug("TrackingAction - "+pTrackingForm.getNovelUserId()+" - processInput - **************************************************************");
		//log.debug("TrackingAction - "+pTrackingForm.getNovelUserId()+" - processInput - Start - Process");
		//log.debug("TrackingAction - "+pTrackingForm.getNovelUserId()+" - processInput - **************************************************************");

	   	String processType = "";
		String addressFinal = "";
		String destinationAddress = "";
		boolean addressOK = false;
		boolean addressError = false;
	   	boolean chkConsignmentInput = false;
	   	boolean isStaff = false;
	   	
//	   	WebServiceGateWay pGateWay = new WebServiceGateWay();
	   	Map<String, String> mapOut = new LinkedHashMap<String, String>();
	   	
	   	//Clear any previous lists
	   	resetLocal(pTrackingForm,"processInput");
	   	
	   	
	   	//Re-Initialize Final Address and re-configure below
	   	pTrackingForm.setSaveAddress1(" "); //Not used in DB (Saved as Address_Line_2). Using it for processing and display only
	   	pTrackingForm.setSaveAddress2(" ");
	   	pTrackingForm.setSaveAddress3(" ");
	   	pTrackingForm.setSaveAddress4(" ");
	   	pTrackingForm.setSaveAddress5(" ");
	   	pTrackingForm.setSavePostal("0");
		pTrackingForm.setDisplayAddress1(" "); //Not used in DB (Saved as Address_Line_2). Using it for processing and display only
	   	pTrackingForm.setDisplayAddress2(" ");
	   	pTrackingForm.setDisplayAddress3(" ");
	   	pTrackingForm.setDisplayAddress4(" ");
	   	pTrackingForm.setDisplayPostal("0");
	   	
	   	//log.debug("TrackingAction - "+pTrackingForm.getNovelUserId()+" - processInput - **************************************************************");
		//log.debug("TrackingAction - "+pTrackingForm.getNovelUserId()+" - processInput - In Process");
		//log.debug("TrackingAction - "+pTrackingForm.getNovelUserId()+" - processInput - **************************************************************");
		
		if (pTrackingForm.getUserSelection() == null || pTrackingForm.getUserSelection().equals("")){
			//log.debug("TrackingAction - "+pTrackingForm.getNovelUserId()+" - processInput - Start - No Process");
			mapOut.put("Error", "Illegal Process - No Input Argument");
			addressError=true;
			//log.debug("TrackingAction - "+pTrackingForm.getNovelUserId()+" - processInput - **************************************************************");
		}else{
			processType =  pTrackingForm.getUserSelection().toUpperCase();
			//log.debug("TrackingAction - "+pTrackingForm.getNovelUserId()+" - processInput - Start - Process="+processType);
			//log.debug("TrackingAction - "+pTrackingForm.getNovelUserId()+" - processInput - **************************************************************");
	
			//Process Address details if BookOut (Do before rest as this is non negotiable for Booking OUT

			if (processType.equalsIgnoreCase("OUT")){
				//log.debug("TrackingAction - "+pTrackingForm.getNovelUserId()+" - processInput - AddressType="+pTrackingForm.getAddressType());
				if (pTrackingForm.getAddressType() != null && !pTrackingForm.getAddressType().equals("")){
					String addressType =  pTrackingForm.getAddressType();
					//log.debug("TrackingAction - "+pTrackingForm.getNovelUserId()+" - processInput - Processing Address Input");
					/**
					 	if (addressType.equalsIgnoreCase("DSAA")){
							//log.debug("TrackingAction - "+pTrackingForm.getNovelUserId()+" - processInput - Destination Address=DSSA");
							addressFinal = "DSSA";
							destinationAddress = (new StringBuilder()).append(destinationAddress).append("DSAA|DSAA").toString();
							pTrackingForm.setSaveAddress1("DSAA|DSAA");
							//Populate DisplayAddress with Description for Reports and Result/PDF Printing
							pTrackingForm.setDisplayAddress1("DSAA (Directorate: Student Assessment Administration)");
					**/
					if (addressType.equalsIgnoreCase("DSAA")){
						//log.debug("TrackingAction - "+pTrackingForm.getNovelUserId()+" - processInput - Destination Address=DSAA");
						addressFinal = "DSAA";
						if(pTrackingForm.getRegionDSAA() != null && !pTrackingForm.getRegionDSAA().equals("0")){
							//log.debug("TrackingAction - "+pTrackingForm.getNovelUserId()+" - processInput - Destination DSAA="+pTrackingForm.getRegionDSAA());
							destinationAddress = (new StringBuilder()).append(destinationAddress).append("DSAA_").append(pTrackingForm.getRegionDSAA()).toString();
							pTrackingForm.setSaveAddress1("DSAA_"+pTrackingForm.getRegionDSAA().toUpperCase());
							//Populate DisplayAddress with Description for Reports and Result/PDF Printing
							pTrackingForm.setDisplayAddress1("DSAA (Directorate: Student Assessment Administration)");
							pTrackingForm.setDisplayAddress2("Office: "+request.getParameter("labelDSAA"));
							//log.debug("TrackingAction - "+pTrackingForm.getNovelUserId()+" - processInput - DSAA Description="+request.getParameterValues("labelDSAA"));
						}else{
							//log.debug("TrackingAction - "+pTrackingForm.getNovelUserId()+" - processInput - Destination DSAA Empty");
							mapOut.put("Error", "No Destination Address.<br/>Please select a Region.");
							addressError=true;
						}
					}else if (addressType.equalsIgnoreCase("DISPATCH")){
						//log.debug("TrackingAction - "+pTrackingForm.getNovelUserId()+" - processInput - Destination Address=DISPATCH");
						addressFinal = "DISPATCH";
						destinationAddress = (new StringBuilder()).append(destinationAddress).append("DISPATCH_DISPATCH").toString();
						pTrackingForm.setSaveAddress1("DISPATCH_DISPATCH");
						//Populate DisplayAddress with Description for Reports and Result/PDF Printing
						pTrackingForm.setDisplayAddress1("DISPATCH");
					
					}else if (addressType.equalsIgnoreCase("PROVINCE")){
						//log.debug("TrackingAction - "+pTrackingForm.getNovelUserId()+" - processInput - Destination Address=PROVINCE");
						addressFinal = "PROVINCE";
						if(pTrackingForm.getProvince() != null && !pTrackingForm.getProvince().equals("0")){
							//log.debug("TrackingAction - "+pTrackingForm.getNovelUserId()+" - processInput - Destination Province="+pTrackingForm.getProvince());
							destinationAddress = (new StringBuilder()).append(destinationAddress).append("Province_").append(pTrackingForm.getProvince()).toString();
							pTrackingForm.setSaveAddress1("Province_"+pTrackingForm.getProvince());
							//Populate DisplayAddress with Description for Reports and Result/PDF Printing
							pTrackingForm.setDisplayAddress1(request.getParameter("labelProvince"));
							//log.debug("TrackingAction - "+pTrackingForm.getNovelUserId()+" - processInput - Province Description="+request.getParameterValues("labelProvince"));
							if(pTrackingForm.getRegion() != null && !pTrackingForm.getRegion().equals("0")){
								//log.debug("TrackingAction - "+pTrackingForm.getNovelUserId()+" - processInput - Destination Region="+pTrackingForm.getRegion());
								destinationAddress = (new StringBuilder()).append(destinationAddress).append(",Region_").append(pTrackingForm.getRegion()).toString();
								pTrackingForm.setSaveAddress2("Region_"+pTrackingForm.getRegion());
								//Populate DisplayAddress with Description for Reports and Result/PDF Printing
								pTrackingForm.setDisplayAddress2(request.getParameter("labelRegion"));
							}else{
								//log.debug("TrackingAction - "+pTrackingForm.getNovelUserId()+" - processInput - Destination Region Empty");
							}
						}else{
							//log.debug("TrackingAction - "+pTrackingForm.getNovelUserId()+" - processInput - Destination Province Empty");
							mapOut.put("Error", "No Destination Address.<br/>Please select at least a Province.");
							addressError=true;
						}
	
					}else if (addressType.equalsIgnoreCase("OTHER")){
						//log.debug("TrackingAction - "+pTrackingForm.getNovelUserId()+" - processInput - Destination Address=COLLEGE");
						addressFinal = "COLLEGE";
						if(pTrackingForm.getCsdmUsers() != null && !pTrackingForm.getCsdmUsers().equals("0")){
							//log.debug("TrackingAction - "+pTrackingForm.getNovelUserId()+" - processInput - Destination Person="+pTrackingForm.getCsdmUsers());
							destinationAddress = (new StringBuilder()).append(destinationAddress).append("Person_").append(pTrackingForm.getCsdmUsers()).toString();
							pTrackingForm.setSaveAddress1("Person_"+pTrackingForm.getCsdmUsers());
							//Populate DisplayAddress with Description for Reports and Result/PDF Printing
							pTrackingForm.setDisplayAddress1(request.getParameter("labelCsdmUsers"));
							isStaff = true;
						}else if(pTrackingForm.getCollege() != null && !pTrackingForm.getCollege().equals("0")){
							//log.debug("TrackingAction - "+pTrackingForm.getNovelUserId()+" - processInput - Destination College="+pTrackingForm.getCollege());
							destinationAddress = (new StringBuilder()).append(destinationAddress).append("College_").append(pTrackingForm.getCollege()).toString();
							pTrackingForm.setSaveAddress1("College_"+pTrackingForm.getCollege());
							//Populate DisplayAddress with Description for Reports and Result/PDF Printing
							pTrackingForm.setDisplayAddress1(request.getParameter("labelCollege"));
							
							if(pTrackingForm.getSchool() != null && !pTrackingForm.getSchool().equals("0")){
								//log.debug("TrackingAction - "+pTrackingForm.getNovelUserId()+" - processInput - Destination School="+pTrackingForm.getSchool());
								destinationAddress = (new StringBuilder()).append(destinationAddress).append(",School_").append(pTrackingForm.getSchool()).toString();
								pTrackingForm.setSaveAddress2("School_"+pTrackingForm.getSchool());
								//Populate DisplayAddress with Description for Reports and Result/PDF Printing
								pTrackingForm.setDisplayAddress2(request.getParameter("labelSchool"));
								
								if(pTrackingForm.getDepartment() != null && !pTrackingForm.getDepartment().equals("0")){
									//log.debug("TrackingAction - "+pTrackingForm.getNovelUserId()+" - processInput - Destination Department="+pTrackingForm.getDepartment());
									destinationAddress = (new StringBuilder()).append(destinationAddress).append(",Department_").append(pTrackingForm.getDepartment()).toString();
									pTrackingForm.setSaveAddress3("Department_"+pTrackingForm.getDepartment());
									//Populate DisplayAddress with Description for Reports and Result/PDF Printing
									pTrackingForm.setDisplayAddress3(request.getParameter("labelDepartment"));
									
									if(pTrackingForm.getModule() != null && !pTrackingForm.getModule().equals("0")){
										//log.debug("TrackingAction - "+pTrackingForm.getNovelUserId()+" - processInput - Destination Module="+pTrackingForm.getModule());
										destinationAddress = (new StringBuilder()).append(destinationAddress).append(",Module_").append(pTrackingForm.getModule()).toString();
										pTrackingForm.setSaveAddress4("Module_"+pTrackingForm.getModule());
										//Populate DisplayAddress with Description for Reports and Result/PDF Printing
										pTrackingForm.setDisplayAddress4(request.getParameter("labelModule"));
									}else{
										//log.debug("TrackingAction - "+pTrackingForm.getNovelUserId()+" - processInput - Destination Module Empty");
									}
									
								}else{
									//log.debug("TrackingAction - "+pTrackingForm.getNovelUserId()+" - processInput - Destination Department Empty");
								}
								
							}else{
								//log.debug("TrackingAction - "+pTrackingForm.getNovelUserId()+" - processInput - Destination School Empty");
							}
						}else{
							//log.debug("TrackingAction - "+pTrackingForm.getNovelUserId()+" - processInput - Destination College Empty");
							mapOut.put("Error", "No Destination Address.<br/>Please select at least a College or a Person.");
							addressError=true;
						}
						
					}else if (addressType.equalsIgnoreCase("BUILDING")){
						//log.debug("TrackingAction - "+pTrackingForm.getNovelUserId()+" - processInput - Destination Address=BUILDING");
						addressFinal = "BUILDING";
						if(pTrackingForm.getBuildingUsers() != null && !pTrackingForm.getBuildingUsers().equals("0")){
							//log.debug("TrackingAction - "+pTrackingForm.getNovelUserId()+" - processInput - Destination Person="+pTrackingForm.getBuildingUsers());
							destinationAddress = (new StringBuilder()).append(destinationAddress).append("Person_").append(pTrackingForm.getBuildingUsers()).toString();
							pTrackingForm.setSaveAddress1("Person_"+pTrackingForm.getBuildingUsers());
							//Populate DisplayAddress with Description for Reports and Result/PDF Printing
							pTrackingForm.setDisplayAddress1(request.getParameter("labelBuildingUsers"));
							isStaff = true;
						}else if(pTrackingForm.getBuilding() != null && !pTrackingForm.getBuilding().equals("0")){
								//log.debug("TrackingAction - "+pTrackingForm.getNovelUserId()+" - processInput - Destination Building="+pTrackingForm.getBuilding());
								destinationAddress = (new StringBuilder()).append(destinationAddress).append("Building_").append(pTrackingForm.getBuilding()).toString();
								pTrackingForm.setSaveAddress1("Building_"+pTrackingForm.getBuilding());
								//Populate DisplayAddress with Description for Reports and Result/PDF Printing
								pTrackingForm.setDisplayAddress1(request.getParameter("labelBuilding"));
								
						}else{
								//log.debug("TrackingAction - "+pTrackingForm.getNovelUserId()+" - processInput - Destination Building Empty");
								mapOut.put("Error", "No Destination Address.<br/>Please select at least a Building or a Person.");
								addressError=true;
						}
						
					}else if (addressType.equalsIgnoreCase("PERSON")){
						//log.debug("TrackingAction - "+pTrackingForm.getNovelUserId()+" - processInput - Destination Address=PERSON");
						addressFinal = "PERSON";
						if(pTrackingForm.getUsers() != null && !pTrackingForm.getUsers().equals("0")){
							//log.debug("TrackingAction - "+pTrackingForm.getNovelUserId()+" - processInput - Destination Person="+pTrackingForm.getUsers());
							addressOK = true;
							destinationAddress = (new StringBuilder()).append(destinationAddress).append("Person_").append(pTrackingForm.getUsers()).toString();
							pTrackingForm.setSaveAddress1("Person_"+pTrackingForm.getUsers());
							//Populate DisplayAddress with Description for Reports and Result/PDF Printing
							pTrackingForm.setDisplayAddress1(request.getParameter("labelUsers"));
							isStaff = true;
						}else{
							//log.debug("TrackingAction - "+pTrackingForm.getNovelUserId()+" - processInput - Destination User Empty");
							mapOut.put("Error", "No Destination Address.<br/>Please select a Person.");
							addressError=true;
						}
						
					}else if (addressType.equalsIgnoreCase("MANUAL")){
						//log.debug("TrackingAction - "+pTrackingForm.getNovelUserId()+" - processInput - Destination Address=MANUAL - Before Address1-1 - addressError="+addressError);

						//log.debug("TrackingAction - "+pTrackingForm.getNovelUserId()+" - processInput - Destination Address=MANUAL");
						//Check if Address lines more that 28 Characters (DB Restriction)
						if (pTrackingForm.getUserAddress1() == null || pTrackingForm.getUserAddress1().trim().equals("") || pTrackingForm.getUserAddress1().trim().equals(" ")){
							//log.debug("TrackingAction - "+pTrackingForm.getNovelUserId()+" - processInput - Destination Address=MANUAL - Line 1 Empty");
							mapOut.put("Error", "Address Line 1 is Empty.<br/>Please enter at least one line of the destination address or the reipient's name.");
							addressError=true;
						}
						//log.debug("TrackingAction - "+pTrackingForm.getNovelUserId()+" - processInput - Destination Address=MANUAL - Before Address1-2 - addressError="+addressError);
						if (pTrackingForm.getUserAddress1().trim().length() > 28 && !addressError){
							//log.debug("TrackingAction - "+pTrackingForm.getNovelUserId()+" - processInput - Destination Address=MANUAL - Line 1 more than 28 Characters");
							mapOut.put("Error", "Address Line 1 is too long.<br/>Please enter maximum 28 characters.");
							addressError=true;
						}
						//log.debug("TrackingAction - "+pTrackingForm.getNovelUserId()+" - processInput - Destination Address=MANUAL - Before Address1-3 - addressError="+addressError);
						if (!addressError){
							boolean result = checkInputValid("UserAddress1", pTrackingForm.getUserAddress1());
							if (!result){
								//log.debug("TrackingAction - "+pTrackingForm.getNovelUserId()+" - processInput - Destination Address=MANUAL - Line 1 Failed Regex input Validation");
								mapOut.put("Error", "Address Line 1 contains invalid characters.<br/>Please try again.");
								addressError=true;
							}
						}
						if (!addressError){
							//log.debug("TrackingAction - "+pTrackingForm.getNovelUserId()+" - processInput - Destination Address=MANUAL - Before Address2-1 - addressError="+addressError);
							if (pTrackingForm.getUserAddress2() != null && !pTrackingForm.getUserAddress2().trim().equals("")){
								if (pTrackingForm.getUserAddress2().trim().length() > 28){
									//log.debug("TrackingAction - "+pTrackingForm.getNovelUserId()+" - processInput - Destination Address=MANUAL - Line 2 more than 28 Characters");
									mapOut.put("Error", "Address Line 2 is too long.<br/>Please enter maximum 28 characters.");
									addressError=true;
								}else{
									//log.debug("TrackingAction - "+pTrackingForm.getNovelUserId()+" - processInput - Destination Address=MANUAL - Before Address2-2 - addressError="+addressError);
									if (!addressError){
										boolean result = checkInputValid("UserAddress2", pTrackingForm.getUserAddress2());
										if (!result){
											//log.debug("TrackingAction - "+pTrackingForm.getNovelUserId()+" - processInput - Destination Address=MANUAL - Line 2 Failed Regex input Validation");
											mapOut.put("Error", "Address Line 2 contains invalid characters.<br/>Please try again.");
											addressError=true;
										}
									}
								}
							}
						}
						
						if (!addressError){
							//log.debug("TrackingAction - "+pTrackingForm.getNovelUserId()+" - processInput - Destination Address=MANUAL - Before Address3-1 - addressError="+addressError);
							if (pTrackingForm.getUserAddress3() != null && !pTrackingForm.getUserAddress3().trim().equals("")){
								if (pTrackingForm.getUserAddress3().trim().length() > 28){
									//log.debug("TrackingAction - "+pTrackingForm.getNovelUserId()+" - processInput - Destination Address=MANUAL - Line 3 more than 28 Characters");
									mapOut.put("Error", "Address Line 3 is too long.<br/>Please enter maximum 28 characters.");
									addressError=true;
								}else{
									//log.debug("TrackingAction - "+pTrackingForm.getNovelUserId()+" - processInput - Destination Address=MANUAL - Before Address3-1 - addressError="+addressError);
									if (!addressError){
										boolean result = checkInputValid("UserAddress3", pTrackingForm.getUserAddress3());
										if (!result){
											//log.debug("TrackingAction - "+pTrackingForm.getNovelUserId()+" - processInput - Destination Address=MANUAL - Line 3 Failed Regex input Validation");
											mapOut.put("Error", "Address Line 3 contains invalid characters.<br/>Please try again.");
											addressError=true;
										}
									}
								}
							}
						}
						
						if (!addressError){
							//log.debug("TrackingAction - "+pTrackingForm.getNovelUserId()+" - processInput - Destination Address=MANUAL - Before Address4-1 - addressError="+addressError);
							if (pTrackingForm.getUserAddress4() != null && !pTrackingForm.getUserAddress4().trim().equals("")){
								if (pTrackingForm.getUserAddress4().trim().length() > 28){
									//log.debug("TrackingAction - "+pTrackingForm.getNovelUserId()+" - processInput - Destination Address=MANUAL - Line 4 more than 28 Characters");
									mapOut.put("Error", "Address Line 4 is too long.<br/>Please enter maximum 28 characters.");
									addressError=true;
								}else{
									//log.debug("TrackingAction - "+pTrackingForm.getNovelUserId()+" - processInput - Destination Address=MANUAL - Before Address4-2 - addressError="+addressError);
									if (pTrackingForm.getUserAddress4() != null && !addressError){
										boolean result = checkInputValid("UserAddress4", pTrackingForm.getUserAddress4());
										if (!result){
											//log.debug("TrackingAction - "+pTrackingForm.getNovelUserId()+" - processInput - Destination Address=MANUAL - Line 4 Failed Regex input Validation");
											mapOut.put("Error", "Address Line 4 contains invalid characters.<br/>Please try again.");
											addressError=true;
										}
									}
								}
							}
						}
						
						if (!addressError){
							//log.debug("TrackingAction - "+pTrackingForm.getNovelUserId()+" - processInput - Destination Address=MANUAL - Before Postal Code - addressError="+addressError);
							if (pTrackingForm.getUserPostal() != null && !pTrackingForm.getUserPostal().trim().equals("")){
								if (pTrackingForm.getUserPostal().trim().length() > 4){
									//log.debug("TrackingAction - "+pTrackingForm.getNovelUserId()+" - processInput - Destination Address=MANUAL - Postal Code more than 4 Numbers");
									mapOut.put("Error", "Postal Code is too long.<br/>Please enter maximum 4 numbers.");
									addressError=true;
								}else{
									if (!addressError){
										boolean result = isNumberValid(pTrackingForm.getUserPostal());
										if (!result){
											//log.debug("TrackingAction - "+pTrackingForm.getNovelUserId()+" - processInput - Destination Address=MANUAL - Postal Code Failed Regex input Validation");
											mapOut.put("Error", "Postal Code contains invalid characters.<br/>Please try again.");
											addressError=true;
											
										}
									}
								}
							}
						}
						
						if (!addressError){
							//log.debug("TrackingAction - "+pTrackingForm.getNovelUserId()+" - processInput - Destination Address=MANUAL - Email-1 - addressError="+addressError);
							if (pTrackingForm.getUserEmail() == null  || pTrackingForm.getUserEmail().trim().equals("")){
								//log.debug("TrackingAction - "+pTrackingForm.getNovelUserId()+" - processInput - Destination Address=MANUAL - Email address is empty="+pTrackingForm.getUserEmail().trim());
								mapOut.put("Error", "Email address is empty. Please supply a valid email address to notify a department or a recipient");
								addressError=true;
							}else{
								//log.debug("TrackingAction - "+pTrackingForm.getNovelUserId()+" - processInput - Destination Address=MANUAL - Email-2 - addressError="+addressError);
								if (!addressError && pTrackingForm.getUserEmail().trim().length() > 60){
									//log.debug("TrackingAction - "+pTrackingForm.getNovelUserId()+" - processInput - Destination Address=MANUAL - Email Address more than 60 Characters");
									mapOut.put("Error", "Email Address is too long.<br/>Please enter maximum 60 characters.");
									addressError=true;
								}else{
									if (!addressError){
										//log.debug("TrackingAction - "+pTrackingForm.getNovelUserId()+" - processInput - Destination Address=MANUAL - Email-3 - addressError="+addressError);
										// Validate email address
										//log.debug("TrackingAction - "+pTrackingForm.getNovelUserId()+" - processInput - Destination Address=MANUAL - Validate email address="+pTrackingForm.getUserEmail().trim());
										String emailErrorMsg=validateEmail(pTrackingForm);
										if(!emailErrorMsg.equals("")){
											//log.debug("TrackingAction - "+pTrackingForm.getNovelUserId()+" - processInput - Destination Address=MANUAL - Email address="+pTrackingForm.getUserEmail().trim()+" Error="+emailErrorMsg);
											mapOut.put("Error", emailErrorMsg);
											addressError=true;
										}
									}
								}
							}
							
							if (!addressError){
								if (pTrackingForm.getUserEmail() != null && !addressError){
									boolean result = checkEmailValid(pTrackingForm.getUserEmail());
									if (!result){
										//log.debug("TrackingAction - "+pTrackingForm.getNovelUserId()+" - processInput - Destination Address=MANUAL - Email address Failed Regex input Validation");
										mapOut.put("Error", "Email address contains invalid characters.<br/>Please try again.");
										addressError=true;
									}
								}
							}
						}
							
						if (!addressError){
							//Remove empty address lines by moving everything up one.
							if((pTrackingForm.getUserAddress3() == null  || pTrackingForm.getUserAddress3().trim().equals("")) && (pTrackingForm.getUserAddress4() != null && !pTrackingForm.getUserAddress4().trim().equals(""))){
								pTrackingForm.setUserAddress3(pTrackingForm.getUserAddress4().trim());
							}
							if((pTrackingForm.getUserAddress2() == null  || pTrackingForm.getUserAddress2().trim().equals("")) && (pTrackingForm.getUserAddress3() != null && !pTrackingForm.getUserAddress3().trim().equals(""))){
								pTrackingForm.setUserAddress2(pTrackingForm.getUserAddress3().trim());
							}
							if((pTrackingForm.getUserAddress1() == null  || pTrackingForm.getUserAddress1().trim().equals("")) && (pTrackingForm.getUserAddress2() != null && !pTrackingForm.getUserAddress2().trim().equals(""))){
								pTrackingForm.setUserAddress1(pTrackingForm.getUserAddress2().trim());
							}
							//Now process resulting address lines 1 - 4 (Remember they are saved as Address_Line2 to Address_line5 in the DB as Address_Line1 is now used for NovellUserID
							if(pTrackingForm.getUserAddress1() != null && !pTrackingForm.getUserAddress1().trim().equals("")){
								//log.debug("TrackingAction - "+pTrackingForm.getNovelUserId()+" - processInput - Destination AddressLine1="+pTrackingForm.getUserAddress1().trim());
								destinationAddress = (new StringBuilder()).append(destinationAddress).append("AddressLine1_").append(pTrackingForm.getUserAddress1()).toString();
								pTrackingForm.setSaveAddress1(pTrackingForm.getUserAddress1());
								//Populate DisplayAddress with Description for Reports and Result/PDF Printing
								pTrackingForm.setDisplayAddress1(pTrackingForm.getUserAddress1());
								
								if(pTrackingForm.getUserAddress2() != null && !pTrackingForm.getUserAddress2().trim().equals("")){
									//log.debug("TrackingAction - "+pTrackingForm.getNovelUserId()+" - processInput - Destination AddressLine2="+pTrackingForm.getUserAddress2().trim());
									destinationAddress = (new StringBuilder()).append(destinationAddress).append(",AddressLine2_").append(pTrackingForm.getUserAddress2()).toString();
									pTrackingForm.setSaveAddress2(pTrackingForm.getUserAddress2());
									//Populate DisplayAddress with Description for Reports and Result/PDF Printing
									pTrackingForm.setDisplayAddress2(pTrackingForm.getUserAddress2());
									
									if(pTrackingForm.getUserAddress3() != null && !pTrackingForm.getUserAddress3().trim().equals("")){
										//log.debug("TrackingAction - "+pTrackingForm.getNovelUserId()+" - processInput - Destination AddressLine3="+pTrackingForm.getUserAddress3().trim());
										destinationAddress = (new StringBuilder()).append(destinationAddress).append("AddressLine3_").append(pTrackingForm.getUserAddress3()).toString();
										pTrackingForm.setSaveAddress3(pTrackingForm.getUserAddress3());
										//Populate DisplayAddress with Description for Reports and Result/PDF Printing
										pTrackingForm.setDisplayAddress3(pTrackingForm.getUserAddress3());
										
										if(pTrackingForm.getUserAddress4() != null && !pTrackingForm.getUserAddress4().trim().equals("")){
											//log.debug("TrackingAction - "+pTrackingForm.getNovelUserId()+" - processInput - Destination AddressLine4="+pTrackingForm.getUserAddress4().trim());
											destinationAddress = (new StringBuilder()).append(destinationAddress).append("AddressLine4_").append(pTrackingForm.getUserAddress4()).toString();
											pTrackingForm.setSaveAddress4(pTrackingForm.getUserAddress4());
											//Populate DisplayAddress with Description for Reports and Result/PDF Printing
											pTrackingForm.setDisplayAddress4(pTrackingForm.getUserAddress4());
										}
									}
								}
								if(pTrackingForm.getUserPostal() != null && !pTrackingForm.getUserPostal().trim().equals("")){
									//log.debug("TrackingAction - "+pTrackingForm.getNovelUserId()+" - processInput - Destination AddressPostal="+pTrackingForm.getUserPostal().trim());
									destinationAddress = (new StringBuilder()).append(destinationAddress).append("AddressPostal_").append(pTrackingForm.getUserPostal()).toString();
									pTrackingForm.setSavePostal(pTrackingForm.getUserPostal());
									//Populate DisplayAddress with Description for Reports and Result/PDF Printing
									pTrackingForm.setDisplayPostal(pTrackingForm.getUserPostal());
								}else{
									pTrackingForm.setSavePostal("0");
									//Populate DisplayAddress with Description for Reports and Result/PDF Printing
									pTrackingForm.setDisplayPostal("0");
								}
								
								if(pTrackingForm.getUserEmail() != null && !pTrackingForm.getUserEmail().trim().equals("")){
									//log.debug("TrackingAction - "+pTrackingForm.getNovelUserId()+" - processInput - Destination AddressEmail="+pTrackingForm.getUserEmail().trim());
									destinationAddress = (new StringBuilder()).append(destinationAddress).append("AddressEmail_").append(pTrackingForm.getUserEmail()).toString();
									pTrackingForm.setSaveEmail(pTrackingForm.getUserEmail());
									//Populate DisplayAddress with Description for Reports and Result/PDF Printing
									pTrackingForm.setDisplayEmail(pTrackingForm.getUserEmail());
								}else{
									pTrackingForm.setSaveEmail("");
									//Populate DisplayAddress with Description for Reports and Result/PDF Printing
									pTrackingForm.setDisplayEmail("");
								}
						    }else{
						    	//log.debug("TrackingAction - "+pTrackingForm.getNovelUserId()+" - processInput - Destination Manual Empty");
						    	mapOut.put("Error", "No Destination Address.<br/>Please Enter at least the first line of the Address.");
						    	addressError = true;
		        			}
						}
					}else{
						//log.debug("TrackingAction - "+pTrackingForm.getNovelUserId()+" - processInput - No Destination Address (Radio button) selected");
						mapOut.put("Error", "No Destination Address (Radio button) selected.");
						addressError = true;
					}
				}else{
					//log.debug("TrackingAction - "+pTrackingForm.getNovelUserId()+" - processInput - No Address Input to Process");
					mapOut.put("Error", "No Destination Address Selected.<br/>Please select or enter an Address.");
					addressError = true;
				}
				if (addressError){
					//log.debug("TrackingAction - "+pTrackingForm.getNovelUserId()+" - processInput - Final Address Error");
					//log.debug("TrackingAction - "+pTrackingForm.getNovelUserId()+" - processInput - **************************************************************");
					PrintWriter out = response.getWriter();
					JSONObject jsonObject = JSONObject.fromObject(mapOut);
					out.write(jsonObject.toString());
					out.flush();
					
					return null;
				}else{
					pTrackingForm.setDestinationAddress(destinationAddress);
					//log.debug("TrackingAction - "+pTrackingForm.getNovelUserId()+" - processInput - Final destinationAddress="+destinationAddress);
					//log.debug("TrackingAction - "+pTrackingForm.getNovelUserId()+" - processInput - **************************************************************");
					if (isStaff){
						//Retrieve User Address from Personnel/Staff ID
						//log.debug("TrackingAction - "+pTrackingForm.getNovelUserId()+" - processInput - Retrieve User Address from Personell/Staff ID");
						
				        int addressId = 892254;
				        int keyCounter = 0;
				        String PersNo = "";
				        if (pTrackingForm.getUsers() != null && !pTrackingForm.getUsers().equals("") && !pTrackingForm.getUsers().equals("0")){
				        	PersNo = pTrackingForm.getUsers().trim();
				        }else if (pTrackingForm.getCsdmUsers() != null && !pTrackingForm.getCsdmUsers().equals("") && !pTrackingForm.getCsdmUsers().equals("0")){
				        	PersNo = pTrackingForm.getCsdmUsers().trim();
				        }else if (pTrackingForm.getBuildingUsers() != null && !pTrackingForm.getBuildingUsers().equals("") && !pTrackingForm.getBuildingUsers().equals("0")){
				        	PersNo = pTrackingForm.getBuildingUsers().trim();
				        }

				        //log.debug("TrackingAction - "+pTrackingForm.getNovelUserId()+" - processInput - PersNo="+PersNo);

				        //Start Setting up new Address
				        try{
				        	ArrayList<KeyValue> getValues = pGateWay.getSelectedUserAddress(pTrackingForm.getWebServiceURL() ,addressId, PersNo, "value1", "value2", "value3", "value4", "value5", "value6", "Personnel");
				        	Iterator<KeyValue> it = getValues.iterator();
				        		
				        	if (it.hasNext()){
				        		String addressKey = "";
					    			
								KeyValue test = (KeyValue) it.next();
								if (test.getValue1() != null && !test.getValue1().equals("")){
									//log.debug("TrackingAction - "+pTrackingForm.getNovelUserId()+" - processInput - StaffNumber="+test.getValue());
									//addressKey = test.getValue().trim().toString();
					
									if(test.getValue1() != null &&  !test.getValue1().isEmpty()){
										if(!test.getValue1().equals("null")){
											//log.debug("TrackingAction - "+pTrackingForm.getNovelUserId()+" - processInput - userAddress2="+test.getValue1());
												pTrackingForm.setDisplayAddress2(test.getValue1().trim().toString());
											}else{
												pTrackingForm.setDisplayAddress2("");
											}
										}else{
											pTrackingForm.setDisplayAddress2("");
										}
										if(test.getValue2() != null &&  !test.getValue2().isEmpty()){
											if(!test.getValue2().equals("null")){
												//log.debug("TrackingAction - "+pTrackingForm.getNovelUserId()+" - processInput - userAddress3="+test.getValue2());
												pTrackingForm.setDisplayAddress3(test.getValue2().trim().toString());
											}else{
												pTrackingForm.setDisplayAddress3("");
											}
										}else{
											pTrackingForm.setDisplayAddress3("");
										}
										if(test.getValue3() != null &&  !test.getValue3().isEmpty()){
											if(!test.getValue3().equals("null")){
												//log.debug("TrackingAction - "+pTrackingForm.getNovelUserId()+" - processInput - userEmail="+test.getValue3());
												pTrackingForm.setDisplayEmail(test.getValue3().trim().toString());
											}else{
												pTrackingForm.setDisplayEmail("");
											}
										}else{
											pTrackingForm.setDisplayEmail("");
										}
									}else{
										//log.debug("TrackingAction - "+pTrackingForm.getNovelUserId()+" - processInput - Selected user's address not Found.");
									}
									//log.debug("TrackingAction - "+pTrackingForm.getNovelUserId()+" - processInput - **************************************************************");

				        		}else{
				        			//log.debug("TrackingAction - "+pTrackingForm.getNovelUserId()+" - processInput - Selected user's address not Found.");
				        		}
				            }catch(Exception ex){
				            	//log.debug("Error: The displayUserAddress Web Service Failed!"+ex);
				            }
						
					}

					//log.debug("TrackingAction - "+pTrackingForm.getNovelUserId()+" - processInput - Final DisplayAddress1="+pTrackingForm.getDisplayAddress1());
					//log.debug("TrackingAction - "+pTrackingForm.getNovelUserId()+" - processInput - Final DisplayAddress2="+pTrackingForm.getDisplayAddress2());
					//log.debug("TrackingAction - "+pTrackingForm.getNovelUserId()+" - processInput - Final DisplayAddress3="+pTrackingForm.getDisplayAddress3());
					//log.debug("TrackingAction - "+pTrackingForm.getNovelUserId()+" - processInput - Final DisplayAddress4="+pTrackingForm.getDisplayAddress4());
					//log.debug("TrackingAction - "+pTrackingForm.getNovelUserId()+" - processInput - Final DisplayPostal="+pTrackingForm.getDisplayPostal());
					//log.debug("TrackingAction - "+pTrackingForm.getNovelUserId()+" - processInput - Final DisplayEmail="+pTrackingForm.getDisplayEmail());
				}
				//log.debug("TrackingAction - "+pTrackingForm.getNovelUserId()+" - processInput - **************************************************************");
			}
			
			
			//Get all Cover Dockets in the Consignment List
			//log.debug("TrackingAction - "+pTrackingForm.getNovelUserId()+" - processInput - Get all Cover Dockets in the Consignment List");
			ArrayList<String> groupOfDcts = new ArrayList<String>();
	        String[] paramValuesConDct = request.getParameterValues("chkConDocket"); 
	        if (paramValuesConDct != null){
	        	//log.debug("TrackingAction - "+pTrackingForm.getNovelUserId()+" - processInput - paramValuesConDct="+paramValuesConDct.length);
	        }
	        if (paramValuesConDct != null && paramValuesConDct.length > 0){
		        for (int i = 0; i < paramValuesConDct.length; i++) { 
		            String paramValueConDct = paramValuesConDct[i]; 
		            if (paramValueConDct !=null && !paramValueConDct.equals("")){
			            //log.debug("TrackingAction - "+pTrackingForm.getNovelUserId()+" - Consignment Cover Dockets - Full="+paramValueConDct);
		            	//log.debug("TrackingAction - "+pTrackingForm.getNovelUserId()+" - processInput - List of docketnumber is "+paramValueConDct);
		        		String []  splitConDct  = paramValueConDct.trim().split("~");
		        		if(splitConDct[0] != null && !splitConDct[0].isEmpty() && splitConDct[1] != null){
		        			//log.debug("TrackingAction - "+pTrackingForm.getNovelUserId()+" - processInput - Consignment Cover Dockets to "+processType+"="+splitConDct[1]);
		        			pTrackingForm.getProcessDocketList().put(Integer.toString(i), splitConDct[1]);
		        			groupOfDcts.add(splitConDct[1]);
		        			chkConsignmentInput = true;
		        		}
		        	}else{
		        		//log.debug("TrackingAction - "+pTrackingForm.getNovelUserId()+" - processInput - No Consignment Cover Dockets to "+processType+"");
		        	}
		        } 
	        }else{
	        	//log.debug("TrackingAction - "+pTrackingForm.getNovelUserId()+" - processInput - No Consignment Cover Dockets Selected or Entered");
	        }
	        
		    //Get all Consignment Cover Dockets - Linked Assignments
	        //log.debug("TrackingAction - "+pTrackingForm.getNovelUserId()+" - processInput - Get all Consignment Cover Dockets - Linked Assignments");
		    String[] paramValuesConDctAss = request.getParameterValues("chkConDctAssignment");
	        if (paramValuesConDctAss != null){
	        	//log.debug("TrackingAction - "+pTrackingForm.getNovelUserId()+" - processInput - paramValuesConDctAss="+paramValuesConDctAss.length);
	        }
		    if (paramValuesConDctAss != null && paramValuesConDctAss.length > 0){
		        for (int i = 0; i < paramValuesConDctAss.length; i++) { 
		            String paramValueConDctAss = paramValuesConDctAss[i]; 
        			boolean testConDctAss = false;
		            if (paramValueConDctAss !=null && !paramValueConDctAss.equals("")){
			            //log.debug("TrackingAction - "+pTrackingForm.getNovelUserId()+" - processInput - Consignment Docket - Linked Assignments - Full="+paramValueConDctAss);
		            	//log.debug("TrackingAction - "+pTrackingForm.getNovelUserId()+" - processInput - List of Consignment assignments is "+paramValueConDctAss);
		        		String []  splitConDctAss  = paramValueConDctAss.trim().split("~");
		        		if(splitConDctAss[0] != null && !splitConDctAss[0].isEmpty()){
		        			//log.debug("TrackingAction - "+pTrackingForm.getNovelUserId()+" - processInput - Consignment Docket="+splitConDctAss[1]+" -- Student Number="+splitConDctAss[2]+" -- Unique Assignment="+splitConDctAss[3]+" (ADD TO LIST)");
		        			pTrackingForm.getProcessDocketAssignmentList().put(splitConDctAss[1],splitConDctAss[2]+"~"+splitConDctAss[3]);
        					chkConsignmentInput = true;
		        		}else{
		        			//log.debug("TrackingAction - "+pTrackingForm.getNovelUserId()+" - processInput - Split Consignment Docket - Linked Assignments to "+processType+" not possible - Possible malformed input (~)");
		        		}
		        	}else{
		        		//log.debug("TrackingAction - "+pTrackingForm.getNovelUserId()+" - processInput - No Consignment Docket - Linked Assignments to "+processType+"");
		        	}
		        } 
		    }else{
		    	//log.debug("TrackingAction - "+pTrackingForm.getNovelUserId()+" - processInput - No Consignment Docket - Linked Assignments Selected or Entered");
	        }
	
		    //Get all Consignment Unique Assignments
		    //log.debug("TrackingAction - "+pTrackingForm.getNovelUserId()+" - processInput - Get all Consignment Unique Assignments");
		    String[] paramValuesConUnqAss = request.getParameterValues("chkConUniqueAssignment");
	        if (paramValuesConUnqAss != null){
	        	//log.debug("TrackingAction - "+pTrackingForm.getNovelUserId()+" - processInput - paramValuesConUnqAss="+paramValuesConUnqAss.length);
	        }
		    if (paramValuesConUnqAss != null && paramValuesConUnqAss.length > 0){
		        for (int i = 0; i < paramValuesConUnqAss.length; i++) { 
		            String paramValueConUnqAss = paramValuesConUnqAss[i]; 
		            if (paramValueConUnqAss !=null && !paramValueConUnqAss.equals("")){
			            //log.debug("TrackingAction - "+pTrackingForm.getNovelUserId()+" - processInput - Consignment Unique Assignments to Book "+processType+" - Full="+paramValueConUnqAss);
		            	//log.debug("TrackingAction - "+pTrackingForm.getNovelUserId()+" - processInput - List of unique assignments is "+paramValueConUnqAss);
		        		String []  splitConUnqAss  = paramValueConUnqAss.trim().split("~");
		        		if(splitConUnqAss[0] != null && !splitConUnqAss[0].isEmpty() && splitConUnqAss[1] != null && splitConUnqAss[2] != null){
		        			//log.debug("TrackingAction - "+pTrackingForm.getNovelUserId()+" - processInput - Consignment Unique Assignments to Book "+processType+" - Student Number="+splitConUnqAss[1]+" -- Unique Assignment="+splitConUnqAss[2]+" (ADD TO LIST)");
		        			pTrackingForm.getProcessAssignmentList().put(splitConUnqAss[1],splitConUnqAss[2]);
		        			chkConsignmentInput = true;
		        		}else{
		        			//log.debug("TrackingAction - "+pTrackingForm.getNovelUserId()+" - processInput - Split Consignment Unique Assignments to Book "+processType+" not possible - Possible malformed input (~)");
		        		}
		        	}else{
		        		//log.debug("TrackingAction - "+pTrackingForm.getNovelUserId()+" - processInput - No Consignment Unique Assignments to Book "+processType+"");
		        	}
		        } 
		    }else{
		    	//log.debug("TrackingAction - "+pTrackingForm.getNovelUserId()+" - processInput - No Consignment Unique Assignments Selected or Entered");
	        }
			
			//Get all Unique Cover Dockets
		    //log.debug("TrackingAction - "+pTrackingForm.getNovelUserId()+" - processInput - Get all Unique Cover Dockets");
	        String[] paramValuesDct = request.getParameterValues("chkDocket"); 
	        if (paramValuesDct != null){
	        	//log.debug("TrackingAction - "+pTrackingForm.getNovelUserId()+" - processInput - paramValuesDct="+paramValuesDct.length);
	        }
	        if (paramValuesDct != null && paramValuesDct.length > 0){
		        for (int i = 0; i < paramValuesDct.length; i++) { 
		            String paramValueDct = paramValuesDct[i]; 
		            if (paramValueDct !=null && !paramValueDct.equals("")){
			            //log.debug("TrackingAction - "+pTrackingForm.getNovelUserId()+" - processInput - Unique Cover Dockets - Full="+paramValueDct);
		            	//log.debug("TrackingAction - "+pTrackingForm.getNovelUserId()+" - processInput - List of docketnumber is "+paramValueDct);
		        		String []  splitDct  = paramValueDct.trim().split("~");
		        		if(splitDct[0] != null && !splitDct[0].isEmpty()){
		        			//log.debug("TrackingAction - "+pTrackingForm.getNovelUserId()+" - processInput - Unique Cover Dockets to "+processType+"="+splitDct[1]+" (ADD TO LIST)");
		        			boolean inBounds = (1 < splitDct.length);
		        			//log.debug("TrackingAction - "+pTrackingForm.getNovelUserId()+" - processInput - Unique Cover Dockets to "+processType+"="+splitDct[0]+" InBounds="+inBounds);
		        			if (inBounds){
		        				pTrackingForm.getProcessDocketList().put(Integer.toString(i), splitDct[1]);
			        			groupOfDcts.add(splitDct[1]);
			        			chkConsignmentInput = true;
		        			}
		        			
		        		}
		        	}else{
		        		//log.debug("TrackingAction - "+pTrackingForm.getNovelUserId()+" - processInput - No Unique Cover Dockets to "+processType+"");
		        	}
		        } 
	        }else{
	        	//log.debug("TrackingAction - "+pTrackingForm.getNovelUserId()+" - processInput - No Unique Cover Dockets Selected or Entered");
	        }
	        
		    //Get all Unique Cover Dockets - Linked Assignments
	        //log.debug("TrackingAction - "+pTrackingForm.getNovelUserId()+" - processInput - Get all Unique Cover Dockets - Linked Assignments");
		    String[] paramValuesUnqDctAss = request.getParameterValues("chkDctAssignment");
	        if (paramValuesUnqDctAss != null){
	        	//log.debug("TrackingAction - "+pTrackingForm.getNovelUserId()+" - processInput - paramValuesUnqDctAss="+paramValuesUnqDctAss.length);
	        }
		    if (paramValuesUnqDctAss != null && paramValuesUnqDctAss.length > 0){
		        for (int i = 0; i < paramValuesUnqDctAss.length; i++) { 
		            String paramValueUnqDctAss = paramValuesUnqDctAss[i]; 
		            boolean testUnqDctAss = false;
		            if (paramValueUnqDctAss !=null && !paramValueUnqDctAss.equals("")){
			            //log.debug("TrackingAction - "+pTrackingForm.getNovelUserId()+" - processInput - Unique Cover Docket - Linked Assignments - Full="+paramValueUnqDctAss);
		            	//log.debug("TrackingAction - "+pTrackingForm.getNovelUserId()+" - processInput - List of unique assignments is "+paramValueUnqDctAss);
		        		String []  splitDctAss  = paramValueUnqDctAss.trim().split("~");
		        		if(splitDctAss[0] != null && !splitDctAss[0].isEmpty()){
		        				//log.debug("TrackingAction - "+pTrackingForm.getNovelUserId()+" - processInput -  Unique Cover Docket="+splitDctAss[1]+" -- Student Number="+splitDctAss[2]+" -- Unique Assignment="+splitDctAss[3]+" (ADD TO LIST)");
		        				pTrackingForm.getProcessDocketAssignmentList().put(splitDctAss[1],splitDctAss[2]+"~"+splitDctAss[3]);
        						chkConsignmentInput = true;
		        		}else{
		        			//log.debug("TrackingAction - "+pTrackingForm.getNovelUserId()+" - processInput - Split Unique Cover Docket - Linked Assignments to "+processType+" not possible - Possible malformed input (~)");
		        		}
		        	}else{
		        		//log.debug("TrackingAction - "+pTrackingForm.getNovelUserId()+" - processInput - No Unique Cover Docket - Linked Assignments to "+processType+"");
		        	}
		        } 
		    }else{
		    	//log.debug("TrackingAction - "+pTrackingForm.getNovelUserId()+" - processInput - No Unique Cover Docket - Linked Assignments Selected or Entered");
	        }
		    
		    //Get all Unique Assignments
		    //log.debug("TrackingAction - "+pTrackingForm.getNovelUserId()+" - processInput - Get all Unique Assignments");
		    String[] paramValuesUnqAss = request.getParameterValues("chkUniqueAssignment");
	        if (paramValuesUnqAss != null){
	        	//log.debug("TrackingAction - "+pTrackingForm.getNovelUserId()+" - processInput - paramValuesUnqAss="+paramValuesUnqAss.length);
	        }
		    if (paramValuesUnqAss != null && paramValuesUnqAss.length > 0){
		        for (int i = 0; i < paramValuesUnqAss.length; i++) { 
		            String paramValueUnqAss = paramValuesUnqAss[i]; 
		            if (paramValueUnqAss !=null && !paramValueUnqAss.equals("")){
			            //log.debug("TrackingAction - "+pTrackingForm.getNovelUserId()+" - processInput - Unique Assignments to Book IN - Full="+paramValueUnqAss);
		            	//log.debug("TrackingAction - "+pTrackingForm.getNovelUserId()+" - processInput - List of unique assignments is "+paramValueUnqAss);
		        		String []  splitter  = paramValueUnqAss.trim().split("~");
		        		if(splitter[0] != null && !splitter[0].isEmpty() && splitter[1] != null){
		        			//log.debug("TrackingAction - "+pTrackingForm.getNovelUserId()+" - processInput - Unique Assignments to "+processType+" - Student Number="+splitter[0]+" -- Unique Assignment="+splitter[1]+ " (ADD TO LIST)");
		        			pTrackingForm.getProcessAssignmentList().put(splitter[0],splitter[1]);
		        			chkConsignmentInput = true;
		        		}else{
		        			//log.debug("TrackingAction - "+pTrackingForm.getNovelUserId()+" - processInput - Split Unique Assignments to "+processType+" not possible - Possible malformed input (~)");
		        		}
		        	}else{
		        		//log.debug("TrackingAction - "+pTrackingForm.getNovelUserId()+" - processInput - No Unique Assignments to "+processType+"");
		        	}
		        } 
		    }else{
		    	//log.debug("TrackingAction - "+pTrackingForm.getNovelUserId()+" - processInput - No Unique Assignments Selected or Entered");
	        }
		   	
		    //log.debug("TrackingAction - "+pTrackingForm.getNovelUserId()+" - processInput - **************************************************************");

		    if (!chkConsignmentInput){
		    	//log.debug("TrackingAction - "+pTrackingForm.getNovelUserId()+" - processInput - Final - No Consignment List, Cover Docket or Unique Assignments Selected or Entered");
		    	mapOut.put("Error","You must enter or select at least one consignment list, docket number or individual assignment");
	        }else{
	        	
	        	String result = processData(mapping, form, request, response);
	        	if (result.toUpperCase().contains("SUCCESS")){
		        	//log.debug("TrackingAction - "+pTrackingForm.getNovelUserId()+" - processInput - Final  - "+processType+" - "+result);
		        	mapOut.put("Success","Book "+processType+" Process - "+result);
		        	String [] getConList = result.split("~");
		        	pTrackingForm.setDisplayShipListNumber(getConList[1]);
		        	processResult(mapping, form, request, response);
	        	}else{
	        		//log.debug("TrackingAction - "+pTrackingForm.getNovelUserId()+" - processInput - Final  - "+processType+" processData FAILED");
		        	mapOut.put("Error","Failure "+processType+" processData FAILED: "+result);
	        	}
	        }
	    
		}
		//log.debug("TrackingAction - "+pTrackingForm.getNovelUserId()+" - processInput - Done");
	    //log.debug("TrackingAction - "+pTrackingForm.getNovelUserId()+" - processInput - **************************************************************");
	    PrintWriter out = response.getWriter();
	    JSONObject jsonObject = JSONObject.fromObject(mapOut);
    	out.write(jsonObject.toString());
    	out.flush();
    	
    	return null; //Returning null to prevent struts from interfering with ajax/json
    }

    private String processData(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response){
    	
    	prepareBackendSession();
    	String result = "";
    	String processConsignmentNumber = "";
    	String consignmentListCreationStatus = "";
    	String processStatus = "";
    	String destinationAddress;
    	
    	
    	boolean errorStatus = false;
    	TrackingForm pTrackingForm = (TrackingForm)form;
        //TrackingDAO pTrackingDAO = new TrackingDAO();
//        WebServiceGateWay pGateWay = new WebServiceGateWay();

    	String processType =  pTrackingForm.getUserSelection().toUpperCase();
    	
    	//log.debug("TrackingAction - "+pTrackingForm.getNovelUserId()+" - processData - *******************************************************");
    	//log.debug("TrackingAction - "+pTrackingForm.getNovelUserId()+" - processData - processType="+processType);
    	if (processType.equalsIgnoreCase("IN")){
    		destinationAddress = "";
    	}else{
    		destinationAddress = pTrackingForm.getDestinationAddress();
    	}
    	//log.debug("TrackingAction - "+pTrackingForm.getNovelUserId()+" - processData - destinationAddress=["+destinationAddress+"]");
    	//log.debug("TrackingAction - "+pTrackingForm.getNovelUserId()+" - processData - *******************************************************");
    	
    	//If BookOUT or NO Consignment List Number exists during BookIN, create new Consignment List
    	boolean isExistList = true;
    	try{
	    	if (processType.equalsIgnoreCase("IN")){
	    		if(pTrackingForm.getConsignmentListNumber() != null && !pTrackingForm.getConsignmentListNumber().isEmpty()){
	    			//log.debug("TrackingAction - "+pTrackingForm.getNovelUserId()+" - processData - "+processType+" ConsignmentListNumber Exists=["+pTrackingForm.getConsignmentListNumber()+"]");
	    			processConsignmentNumber = pTrackingForm.getConsignmentListNumber();
	    		}else{
	    			//log.debug("TrackingAction - "+pTrackingForm.getNovelUserId()+" - processData - "+processType+" ConsignmentListNumber Empty");
	    			isExistList = false;
	    		}
	    	}
	    	if (!isExistList || processType.equalsIgnoreCase("OUT")){
	    		//log.debug("TrackingAction - "+pTrackingForm.getNovelUserId()+" - processData - "+pTrackingForm.getUserSelection()+" OR ConsignmentListNumber Does NOT Exist, Therefore create new Consignment List");
	       		consignmentListCreationStatus = pTrackingDAO.creatingShiplistNumber(pTrackingForm.getWebServiceURL(), pTrackingForm.getNovelUserId().trim());
	       		//log.debug("TrackingAction - "+pTrackingForm.getNovelUserId()+" - processData - Creating Consignment List (creatingShiplistNumber) - consignmentListCreationStatus="+consignmentListCreationStatus+", UserID="+pTrackingForm.getNovelUserId().trim());
		
	         	if(pTrackingDAO.checkNumbersOnly(consignmentListCreationStatus)){
	         		processConsignmentNumber = consignmentListCreationStatus ;
	        		//log.debug("TrackingAction - "+pTrackingForm.getNovelUserId()+" - processData - Creating Consignment List (Success), Consignment List="+processConsignmentNumber);
	         	}else{
	         		errorStatus = true;
	         		result = "Error~"+consignmentListCreationStatus;  
	        		//log.debug("TrackingAction - "+pTrackingForm.getNovelUserId()+" - processData - Creating Consignment List (Failed), consignmentListCreationStatus=" + consignmentListCreationStatus);
	         	}
	    	}
        }catch(NullPointerException ex){
        	result = "Error~"+consignmentListCreationStatus+ ":" +ex.getMessage();
        	errorStatus = true;
        }catch(Exception e){
        	result = "Error~"+consignmentListCreationStatus+ "~" +e.getMessage();
        	errorStatus = true;
        }
    	
    	if (!errorStatus && processConsignmentNumber != null && !processConsignmentNumber.equals("")){
	    	//log.debug("TrackingAction - "+pTrackingForm.getNovelUserId()+" - processData - Using Consignment List for processing=" +processConsignmentNumber);
	
	    	int webServiceID = 620922 ; /**BOOK IN OR OUT 620922 A **/
	    	int countDocket = 0;
	    	int countDctAss = 0;
	    	int countUnqAss = 0;
	    	int countTotal = 0;
	    	
	    	String webAddress = pTrackingForm.getBookInOutURL();
	        String fromUserID = "";
	        String toUserID = "";
	        String addFullDctRecords = "";
	        String addDctAssRecords = "";
	        String addUnqAssRecords = "";
	        
	        String baseXML = "<data>" +
	        					"<fields>" +
		        					"<field1>Booking</field1>" +
		        					"<field2>ShipNo</field2>" +
		        					"<field3>DNr</field3>" +
		        					"<field4>StuNr</field4>" +
		        					"<field5>UnqNr</field5>" +
		        					"<field6>status</field6>" +
		        					"<field7>FromUserID</field7>" +
		        					"<field8>sno</field8>" +
		        					"<field9>ToUserID</field9>" +
		        					"<field10>Adr</field10>" +
	        					"</fields>" +
	        					"<records>"; //Append Dockets and Assignments in one large XML;
	        
	        //Set up User ID's and Destination Addresses
        	//fromUserID = "CDTRACKING"; //This has to be CDTRACKING as it is the default user for the web services
	        fromUserID = pTrackingForm.getNovelUserId().toUpperCase();
	        if (processType.equalsIgnoreCase("IN")){
	        	toUserID = pTrackingForm.getNovelUserId().toUpperCase();
	        }else{
	        	if (pTrackingForm.getSaveAddress1() != null && !pTrackingForm.getSaveAddress1().equalsIgnoreCase("")){
	        		if (pTrackingForm.getSaveAddress1().toUpperCase().contains("PERSON")){
	        			//log.debug("PERSON="+pTrackingForm.getSaveAddress1());
	        			String [] splitPerson  = pTrackingForm.getSaveAddress1().trim().split("_");
		        		if(splitPerson[0] != null && !splitPerson[0].isEmpty() && splitPerson[1] != null){
		        			toUserID = splitPerson[1];
		        			//log.debug("PERSON Split="+toUserID);
		        		}
	        		}
	        	}
 	        }
	    	
	    	
	    	if(!pTrackingForm.getProcessDocketList().isEmpty()) {
	    		//log.debug("TrackingAction - "+pTrackingForm.getNovelUserId()+" - processData - *******************************************************");
	            String docketID = "";
	            String docketNumber = "";
				Set<String> keys = pTrackingForm.getProcessDocketList().keySet();
		        for(String key:keys){
		        	countDocket++;
		        	countTotal++;
		        	docketNumber = key;
		        	docketNumber = pTrackingForm.getProcessDocketList().get(key);
		        	
					//log.debug("TrackingAction - "+pTrackingForm.getNovelUserId()+" - processData - DocketID=" +countDocket);
					//log.debug("TrackingAction - "+pTrackingForm.getNovelUserId()+" - processData - DocketNumber : " +docketNumber);
					//log.debug("TrackingAction - "+pTrackingForm.getNovelUserId()+" - processData - *******************************************************");
					addFullDctRecords = (new StringBuilder()).append(addFullDctRecords)
							.append("<record id='").append(countTotal).append("'>")
								.append("<value1>").append(processType.toUpperCase().trim()).append("</value1>")
								.append("<value2>").append(processConsignmentNumber.trim()).append("</value2>")
								.append("<value3>").append(docketNumber).append("</value3>")
								.append("<value4>").append("").append("</value4>")
								.append("<value5>").append("").append("</value5>")
								.append("<value6>").append("Y").append("</value6>")
								.append("<value7>").append(fromUserID).append("</value7>")
								.append("<value8>").append("").append("</value8>")
								.append("<value9>").append(toUserID).append("</value9>")
								.append("<value10>").append(destinationAddress).append("</value10>")
							.append("</record>").toString();
				}
		        //log.debug("TrackingAction - "+pTrackingForm.getNovelUserId()+" - processData - *******************************************************");
		        //log.debug("TrackingAction - "+pTrackingForm.getNovelUserId()+" - processData - addFullDctRecords XML="+addFullDctRecords);
		        //log.debug("TrackingAction - "+pTrackingForm.getNovelUserId()+" - processData - *******************************************************");
	    	}else{
	    		//log.debug("TrackingAction - "+pTrackingForm.getNovelUserId()+" - processData - No Full Cover Dockets");
	    	}
	    	
	    	if(!pTrackingForm.getProcessDocketAssignmentList().isEmpty()) {
	    		//log.debug("TrackingAction - "+pTrackingForm.getNovelUserId()+" - processData - *******************************************************");
	            String docketNumber = "";
	            String studentNumber = "";
	            String assignmentNumber = "";
	            String AssignmentString = "";
				Set<String> keys = pTrackingForm.getProcessDocketAssignmentList().keySet();
		        for(String key:keys){
		        	countDctAss++;
		        	countTotal++;
		        	docketNumber = key;
		            AssignmentString = pTrackingForm.getProcessDocketAssignmentList().get(key);
		            
					//log.debug("TrackingAction - "+pTrackingForm.getNovelUserId()+" - processData - docketNumber=" +docketNumber);
					//log.debug("TrackingAction - "+pTrackingForm.getNovelUserId()+" - processData - Assignment ; String : " +AssignmentString);
					//log.debug("TrackingAction - "+pTrackingForm.getNovelUserId()+" - processData - *******************************************************");
					
					//log.debug("TrackingAction - "+pTrackingForm.getNovelUserId()+" - processData - Splitting ; String");
		      		String [] splitDctAss  = AssignmentString.trim().split("~");
	        		if(splitDctAss[0] != null && !splitDctAss[0].isEmpty() && splitDctAss[1] != null){
	        			studentNumber = splitDctAss[0];
	        			assignmentNumber = splitDctAss[1];
	        			
	        			//log.debug("TrackingAction - "+pTrackingForm.getNovelUserId()+" - processData - Docket StudentNumber : " +studentNumber);
	        			//log.debug("TrackingAction - "+pTrackingForm.getNovelUserId()+" - processData - Docket UniqueAssignment : " +assignmentNumber);
	        			//log.debug("TrackingAction - "+pTrackingForm.getNovelUserId()+" - processData - *******************************************************");
	        			addDctAssRecords = (new StringBuilder()).append(addDctAssRecords)
								.append("<record id='").append(countTotal).append("'>")
									.append("<value1>").append(processType.toUpperCase().trim()).append("</value1>")
									.append("<value2>").append(processConsignmentNumber.trim()).append("</value2>")
									.append("<value3>").append(docketNumber).append("</value3>")
									.append("<value4>").append(studentNumber).append("</value4>")
									.append("<value5>").append(assignmentNumber).append("</value5>")
									.append("<value6>").append("Y").append("</value6>")
									.append("<value7>").append(fromUserID).append("</value7>")
									.append("<value8>").append("").append("</value8>")
									.append("<value9>").append(toUserID).append("</value9>")
									.append("<value10>").append(destinationAddress).append("</value10>")
								.append("</record>").toString();
					}
				}
			    //log.debug("TrackingAction - "+pTrackingForm.getNovelUserId()+" - processData - *******************************************************");
			    //log.debug("TrackingAction - "+pTrackingForm.getNovelUserId()+" - processData - addDctAssignmentRecords XML="+addDctAssRecords);
			    //log.debug("TrackingAction - "+pTrackingForm.getNovelUserId()+" - processData - *******************************************************");
	    	}else{
	    		//log.debug("TrackingAction - "+pTrackingForm.getNovelUserId()+" - processData - No Cover Docket Linked Assignments");
	    	}
	    	
	    	if(!pTrackingForm.getProcessAssignmentList().isEmpty()) {
	    		//log.debug("TrackingAction - "+pTrackingForm.getNovelUserId()+" - processData - *******************************************************");
	    		 String studentNumber = "";
	             String assignmentNumber = "";
				Set<String> keys = pTrackingForm.getProcessAssignmentList().keySet();
		        for(String key:keys){
		        	countUnqAss++;
		        	countTotal++;
		        	studentNumber = key;
		        	assignmentNumber = pTrackingForm.getProcessAssignmentList().get(key);
		        	
					//log.debug("TrackingAction - "+pTrackingForm.getNovelUserId()+" - processData - Unique studentNumber=" +studentNumber);
					//log.debug("TrackingAction - "+pTrackingForm.getNovelUserId()+" - processData - Unique AssignmentNumber=" +assignmentNumber);
					//log.debug("TrackingAction - "+pTrackingForm.getNovelUserId()+" - processData - *******************************************************");
					addUnqAssRecords = (new StringBuilder()).append(addUnqAssRecords)
							.append("<record id='").append(countTotal).append("'>")
								.append("<value1>").append(processType.toUpperCase().trim()).append("</value1>")
								.append("<value2>").append(processConsignmentNumber.trim()).append("</value2>")
								.append("<value3>").append("").append("</value3>")
								.append("<value4>").append(studentNumber).append("</value4>")
								.append("<value5>").append(assignmentNumber).append("</value5>")
								.append("<value6>").append("Y").append("</value6>")
								.append("<value7>").append(fromUserID).append("</value7>")
								.append("<value8>").append("").append("</value8>")
								.append("<value9>").append(toUserID).append("</value9>")
								.append("<value10>").append(destinationAddress).append("</value10>")
							.append("</record>").toString();
				}
			    //log.debug("TrackingAction - "+pTrackingForm.getNovelUserId()+" - processData - *******************************************************");
			    //log.debug("TrackingAction - "+pTrackingForm.getNovelUserId()+" - processData - addUniqueAssignmentRecords XML="+addUnqAssRecords);
			    //log.debug("TrackingAction - "+pTrackingForm.getNovelUserId()+" - processData - *******************************************************");
	    	}else{
	    		//log.debug("TrackingAction - "+pTrackingForm.getNovelUserId()+" - processData - No Unique Assignments");
	    	}
	    	
	    	//Close XML
	    	//log.debug("TrackingAction - "+pTrackingForm.getNovelUserId()+" - processData - *******************************************************");
	    	String processXML = "";
	    	processXML = (new StringBuilder()).append(baseXML).append(addFullDctRecords).append(addDctAssRecords).append(addUnqAssRecords).toString();
	    	processXML = (new StringBuilder()).append(processXML).append("</records></data>".trim()).toString();
    		//log.debug("TrackingAction - "+pTrackingForm.getNovelUserId()+" - processData - processXML=" +processXML);
	    	processXML = processXML.trim().replace(" ", "%20");
	    	//log.debug("TrackingAction - "+pTrackingForm.getNovelUserId()+" - processData - processXML Spaces Removed=" +processXML);
            String urlParameters = (new StringBuilder()).append("&ID=").append(webServiceID).append("&USERID=CDTRACKING").append("&inXML=").append(processXML.trim()).toString();
            //log.debug("TrackingAction - "+pTrackingForm.getNovelUserId()+" - processData - urlParameters=" +urlParameters);
            String URL = (new StringBuilder()).append(webAddress).append(urlParameters).toString();
            //log.debug("TrackingAction - "+pTrackingForm.getNovelUserId()+" - processData - *******************************************************");
            try{
            	//log.debug("TrackingAction - "+pTrackingForm.getNovelUserId()+" - processData - Executing WebService="+webServiceID);
            	
            	processStatus = pGateWay.responseFromWebService(URL, urlParameters);
            	//log.debug("TrackingAction - "+pTrackingForm.getNovelUserId()+" - processData - *******************************************************");
            	//log.debug("TrackingAction - "+pTrackingForm.getNovelUserId()+" - processData - Executing processStatus="+processStatus);
            	//log.debug("TrackingAction - "+pTrackingForm.getNovelUserId()+" - processData - *******************************************************");

            	if(processStatus.contains("OK") || processStatus.contains("SUCCESS")){
            		//log.debug("TrackingAction - "+pTrackingForm.getNovelUserId()+" - processData - (Book "+processType+") - SUCCESS");
            		
            		//Get Date & Status for Consignment number for Report/Result
            		int websrcConsignmentId = 121868; /* GET_SHPLST 121868 A */
            		ArrayList getResult = pGateWay.showConsignmentListInfo(pTrackingForm.getWebServiceURL() ,websrcConsignmentId, processConsignmentNumber,"","id", "value4");
                	for(Iterator resultConsign = getResult.iterator(); resultConsign.hasNext();){
                        KeyValue testConsign = (KeyValue)resultConsign.next();
                       //log.debug("TrackingAction - "+pTrackingForm.getNovelUserId()+" - searchDetail - testConsign.getValue="+testConsign.getValue());
                       if (testConsign.getValue() != null && !testConsign.getValue().isEmpty()){
                    	   pTrackingForm.setDisplayShipListDate(testConsign.getValue());
                       }
                	}
            		result = "Success~"+processConsignmentNumber+"~"+pTrackingForm.getDisplayShipListDate();
            		
            		//Once Successful, write Destination Address to ADR for next Book Out
            		if (processType.equalsIgnoreCase("OUT")){
            			String addressStatus;
            			int webSetAddressId = 710033;
            			
            			//Note: Using Entered Address 1 as 2 and 2 as 3 etc as Address 1 is used for User ID
            			//log.debug("TrackingAction - "+pTrackingForm.getNovelUserId()+" - processData - (Final Address) - userID="+pTrackingForm.getNovelUserId()+", Line1="+pTrackingForm.getSaveAddress1()+", Line2="+pTrackingForm.getSaveAddress2()+", Line3="+pTrackingForm.getSaveAddress3()+", Line4="+pTrackingForm.getSaveAddress4()+", Consignment="+processConsignmentNumber+", Postal="+pTrackingForm.getSavePostal());
            			addressStatus = pGateWay.setChangedAddress(pTrackingForm.getWebServiceURL() ,webSetAddressId, pTrackingForm.getNovelUserId(), pTrackingForm.getSaveAddress1(), pTrackingForm.getSaveAddress2(), pTrackingForm.getSaveAddress3(), pTrackingForm.getSaveAddress4(), processConsignmentNumber, pTrackingForm.getSavePostal(), "value1", "value2");
               			
            			if(addressStatus.contains("OK") || addressStatus.contains("SUCCESS")){
                    		//log.debug("TrackingAction - "+pTrackingForm.getNovelUserId()+" - processData - (addressStatus) - SUCCESS");
               			}else{
               				//log.debug("TrackingAction - "+pTrackingForm.getNovelUserId()+" - processData - (addressStatus) - FAILED");
               				result = "Failed; AddressUpdate<br/>"+processStatus+"<br/>"+addressStatus;
                    		errorStatus = true;
               			}
            		}
            	}else{
            		//log.debug("TrackingAction - "+pTrackingForm.getNovelUserId()+" - processData - (Book OUT) - FAILED<br/>"+processStatus);
            		result = "Failed~"+processStatus;
            		errorStatus = true;
            	}
            }catch(Exception e){
            	result = "Error~"+e;
            	errorStatus = true;
            }
    	}else{
    		//errorStatus IS true;
    		//log.debug("TrackingAction - "+pTrackingForm.getNovelUserId()+" - processData - *******************************************************");
    		//log.debug("TrackingAction - "+pTrackingForm.getNovelUserId()+" - processData - ErrorStatus=" +errorStatus+ "Cannot Continue");
    		result = "Failed~"+errorStatus;
    	}
    	
		//log.debug("TrackingAction - "+pTrackingForm.getNovelUserId()+" - processData - Final *************************************************");
		//log.debug("TrackingAction - "+pTrackingForm.getNovelUserId()+" - processData - Final Result="+result);
		//log.debug("TrackingAction - "+pTrackingForm.getNovelUserId()+" - processData - Final *************************************************");
    	return result;
    }
    
    public ActionForward processResult(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response){

    	TrackingForm pTrackingForm = (TrackingForm)form;
	    //Get Book IN/OUT Process Results
    	
    	//PRINT_CONSIGNMENT_LIST_DCT 945964 A 
    	//PRINT_CONSIGNMENT_LIST_STU 406414 A 
    	// PRINT_RECEIPT 562557 A 
    	// PRINT_RECEIPT_DCT 697460 A 
    	// PRINT_RECEIPT_STU 333995 A 

    		//log.debug("TrackingAction - "+pTrackingForm.getNovelUserId()+" - processResult - *******************************************************");
	
	    	String url ;
	    	if ("my.unisa.ac.za".equals(pTrackingForm.getHost())){
	            url = "http://www2.unisa.ac.za/aol/asp/sql_exec_report4.asp?Export=XML&ID=";
	    	}else if("mydev.int.unisa.ac.za".equals(pTrackingForm.getHost())){
	    	    url = "http://stratusdev.unisa.ac.za/aol/asp/sql_exec_report4.asp?Export=XML&ID=";
	    	}else if("myqa.int.unisa.ac.za".equals(pTrackingForm.getHost())){
	    		 url = "http://stratusqa.unisa.ac.za/aol/asp/sql_exec_report4.asp?Export=XML&ID=";
	    	}else {
	    		url = "http://stratusdev.unisa.ac.za/aol/asp/sql_exec_report4.asp?Export=XML&ID=";
	    	}
	    	
	    	String processType =  pTrackingForm.getUserSelection().toUpperCase();
	    	
	    	//log.debug("TrackingAction - "+pTrackingForm.getNovelUserId()+" - processData - processType="+processType);
	    	if (processType.equalsIgnoreCase("IN")){
	    		
	    	}
	
	       //log.debug("TrackingAction - "+pTrackingForm.getNovelUserId()+" - processResult - Result URL is " + url+"ShipNo="+pTrackingForm.getShipListNumber()+"&Status="+processType+"&SYSTEM=SQL");
	       //log.debug("TrackingAction - "+pTrackingForm.getNovelUserId()+" - processResult - *******************************************************");
	       //Test to see if ShipList created above does actually exist
	       	try {
	        	//Get Shiplist Dockets and Assignments for results page only
	       		//log.debug("TrackingAction - "+pTrackingForm.getNovelUserId()+" - processResult - Final *******************************************************");
	       		//log.debug("TrackingAction - "+pTrackingForm.getNovelUserId()+" - processResult - Final Retrieve Consignment results after Book "+processType);
	       		//log.debug("TrackingAction - "+pTrackingForm.getNovelUserId()+" - processResult - Final *******************************************************");
				displayConsignmentDetails(mapping,form,request, response, "Result");
				//log.debug("TrackingAction - "+pTrackingForm.getNovelUserId()+" - processResult - Return from displayConsignmentDetails");
			} catch (Exception e) {
				//messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("message.generalmessage", "Error retrieving BOOK_IN Result" + e));
			}
	       	//log.debug("TrackingAction - "+pTrackingForm.getNovelUserId()+" - processResult - *******************************************************");
	    try{

	       
	    }catch(NullPointerException ex){
	        //messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("message.generalmessage",ex.getMessage()));
	    }
	    //log.debug("TrackingAction - "+pTrackingForm.getNovelUserId()+" - processResult - *******************************************************");
	    //log.debug("TrackingAction - "+pTrackingForm.getNovelUserId()+" - processResult - Redirect to RESULT");
	    //log.debug("TrackingAction - "+pTrackingForm.getNovelUserId()+" - processResult - *******************************************************");
	    return null;
	}
    
    public ActionForward searchPDF(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws IOException{
        
    	prepareBackendSession();
    	TrackingForm pTrackingForm = (TrackingForm)form;
//    	WebServiceGateWay pGateWay = new WebServiceGateWay();
        ActionMessages messages = new ActionMessages();
        HttpSession session = request.getSession(true);
        Map<String, String> displayDocketList = new LinkedHashMap<String, String>();
    	Map<String, String> displayDocketAssignmentList = new LinkedHashMap<String, String>();
    	Map<String, String> displayAssignmentList = new LinkedHashMap<String, String>();
    	JSONObject userObj = new JSONObject();
    	
        try
        {
        	//log.debug("TrackingAction - "+pTrackingForm.getNovelUserId()+" - searchPDF - **************************************************************");
            String searchType =  pTrackingForm.getSearchRadio();
            String searchRadio = request.getParameter("searchRadio");
            //log.debug("TrackingAction - "+pTrackingForm.getNovelUserId()+" - searchPDF - searchType="+searchType+", searchRadio="+searchRadio);
            
            if (searchType == null || searchType.equals("")){
            	userObj.put("Empty", "No Search criteria selected.");
            }else{
            	//log.debug("TrackingAction - "+pTrackingForm.getNovelUserId()+" - searchPDF - SearchString="+pTrackingForm.getSearchString().trim());
            	//log.debug("TrackingAction - "+pTrackingForm.getNovelUserId()+" - searchPDF - **************************************************************");
	            int websrcConsignmentId = 121868; /* GET_SHPLST 121868 A */
	            int websrcConsignDctId = 697460; /* PRINT_RECEIPT_DCT 697460 A */
	            int websrcConDctAssId = 702449;/* LIST_ASSIGN_ON_DOCKET 702449 A */
	            int websrcConsignAssId = 333995; /* PRINT_RECEIPT_STU 333995 A */
	            
	            int websrcDocketId = 12345;
	            int websrcAssignmentId = 12345;
	            int websrcUserId = 12345;
	            if(pTrackingForm.getSearchString() != null && pTrackingForm.getSearchString().length() != 0){
	            	//log.debug("TrackingAction - "+pTrackingForm.getNovelUserId()+" - searchPDF - searchType="+searchType+", SearchString="+pTrackingForm.getSearchString().trim());
	                if(searchType.equalsIgnoreCase("Consignment")){
	                	ArrayList getResult = pGateWay.showConsignmentListInfo(pTrackingForm.getWebServiceURL() ,websrcConsignmentId, pTrackingForm.getSearchString().trim(),"","id", "value4");
	                    //results = pGateWay.getSearch(webServiceURL ,websrcConsignmentId, pTrackingForm.getSearchString(),"Value1", "Value2");
	                	for(Iterator result = getResult.iterator(); result.hasNext();){
	                        KeyValue test1 = (KeyValue)result.next();
	                       //log.debug("TrackingAction - "+pTrackingForm.getNovelUserId()+" - searchPDF - test1.getValue="+test1.getValue());
	                        if(test1.getValue().contains("Consignment list number invalid")){
	                            messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage("message.generalmessage", "Consignment list number is not valid."));
	                            addErrors(request, messages);
	                        }else{
	                        	pTrackingForm.setDate(test1.getValue());
	                        	userObj.put("Consignment",pTrackingForm.getSearchString().trim()+"~"+test1.getValue());
	                        	//Get Cover Dockets from Database/Web Service
	                     	   	//log.debug("TrackingAction - "+pTrackingForm.getNovelUserId()+" - searchPDF - consignmentNumber="+pTrackingForm.getSearchString().trim());
	                     	    //log.debug("TrackingAction - "+pTrackingForm.getNovelUserId()+" - searchPDF - **************************************************************");
	                            ArrayList getValues = pGateWay.showConsignmentListInfo(pTrackingForm.getWebServiceURL() ,websrcConsignDctId, pTrackingForm.getSearchString().trim(),"" ,"id", "value1");
	                            Iterator it = getValues.iterator();
	                            String errMsgDct="";
	                            String errMsgStu="";
	                            int countDct = 0;
	                            if(it.hasNext()){
	                            	while(it.hasNext()){
	                            		KeyValue testDct = (KeyValue) it.next();
	                	    			if(testDct.getValue().equals("No records returned")){
	                	    				errMsgDct = " Docket Numbers ";
	                	    				//log.debug("TrackingAction - "+pTrackingForm.getNovelUserId()+" - searchPDF  - Docket Numbers: No records returned");
	                	    			}else{
	                	    				countDct++;
	                	    				//log.debug("TrackingAction - "+pTrackingForm.getNovelUserId()+" - searchPDF  - Docket Numbers Added="+testDct.getValue());
	                	    				//log.debug("TrackingAction - "+pTrackingForm.getNovelUserId()+" - searchPDF - **************************************************************");
	                	    				//Get Assignments per Docket
	                	    				//log.debug("TrackingAction - "+pTrackingForm.getNovelUserId()+" - searchPDF - Retrieve linked Assignments for docketNumber="+testDct.getValue());
	                	    				ArrayList dctAssignments = pGateWay.getDocketAssignments(pTrackingForm.getWebServiceURL(), websrcConDctAssId ,testDct.getValue(), "value1", "value2");
	                	    				Iterator dctAssign = dctAssignments.iterator();
	                	    				int conDctAssCounter = 0;
	                	    				if(dctAssign.hasNext()){
	                	    					List<Assignment> dctAssignList = new ArrayList<Assignment>();
	                	    					while(dctAssign.hasNext()){
	                	    						KeyValue testDctAss = (KeyValue) dctAssign.next();
	                	    						if(testDctAss.getValue().equals("No records returned")){
	                	    							//log.debug("TrackingAction - "+pTrackingForm.getNovelUserId()+" - searchPDF - No linked Assignments returned");
	            									}else{
	            										conDctAssCounter++;
	            										//log.debug("TrackingAction - "+pTrackingForm.getNovelUserId()+" - searchPDF  - Linked Assignments: Successful records returned - Student="+testDctAss.getKey()+", Assignment="+testDctAss.getValue());
	            										// Generate a Docket-Assignment map
	        	                	    				displayDocketList.put(Integer.toString(countDct), conDctAssCounter+"~"+testDct.getValue()+"~"+testDctAss.getKey()+"~"+testDctAss.getValue());
	            										//log.debug("TrackingAction - "+pTrackingForm.getNovelUserId()+" - searchPDF - **************************************************************");
	            									}
	            							    }
	                	    				}else{
	                	    					//log.debug("TrackingAction - "+pTrackingForm.getNovelUserId()+" - searchPDF - No linked Assignments returned");
	                	    					displayDocketList.put(Integer.toString(countDct), "1~"+testDct.getValue()+"~~");
	                	    				}
	                	    			}	
	                	    		}
	                            	if (!displayDocketList.isEmpty()){
	                            		userObj.put("Dockets",displayDocketList);
		                            }
	                            }else{
	                            	errMsgDct = " Docket Numbers ";
	                				//log.debug("TrackingAction - "+pTrackingForm.getNovelUserId()+" - searchPDF  - Docket Numbers: No records returned");
	                            }
	                            
	                            
	                            //Getting Unique Assignments
	                            //log.debug("TrackingAction - "+pTrackingForm.getNovelUserId()+" - searchPDF - consignmentNumber="+pTrackingForm.getConsignmentNumber());
	                            ArrayList uniqueNumbers = pGateWay.showUniqueAssignmentListInfo(pTrackingForm.getWebServiceURL() ,websrcConsignAssId, pTrackingForm.getConsignmentNumber(),"","id", "value1", "value2");
	                            Iterator uniqueNumber = uniqueNumbers.iterator();
	                            if(uniqueNumber.hasNext()){
	                            	while(uniqueNumber.hasNext()){
	                	    			KeyValue testAss = (KeyValue) uniqueNumber.next();
	                	    			if(testAss.getValue1().equals("No records returned")){
	                	    				errMsgStu = " Unique Assignments ";
	                	    				//log.debug("TrackingAction - "+pTrackingForm.getNovelUserId()+" - searchPDF  - Unique Assignments: No records returned");
	                	   			 	}else{
	                	   			 		//log.debug("TrackingAction - "+pTrackingForm.getNovelUserId()+" - searchPDF  - Unique Assignments="+testAss.getValue1()+ "----" + testAss.getValue2());
	                	   			 		displayAssignmentList.put(Integer.toString(countDct), testAss.getValue1()+"~"+testAss.getValue2());
	                	   			 	}
	                	            }
	                            }else{
	                            	errMsgStu = " Unique Assignments ";
	                				//log.debug("TrackingAction - "+pTrackingForm.getNovelUserId()+" - searchPDF  - Unique Assignments: No records returned");
	                            }
	                            if (!displayAssignmentList.isEmpty()){
	                            	userObj.put("Assignments",displayAssignmentList);
	                            }
	                            /*
	                            //log.debug("TrackingAction - "+pTrackingForm.getNovelUserId()+" - searchDetail  - Error Checking - Dockets: "+errMsgDct+", Student: "+errMsgStu);
	                            //log.debug("TrackingAction - "+pTrackingForm.getNovelUserId()+" - searchDetail  - Error Checking Sizes - Dockets: "+pTrackingForm.getDisplayDockets().size()+", Student: "+pTrackingForm.getDisplayUniqueNumbers().size());
	                            
	                            if(pTrackingForm.getDisplayDockets().size()== 0 && pTrackingForm.getDisplayUniqueNumbers().size()== 0){ 
	                	            if(!errMsgDct.equals("") || !errMsgStu.equals("")){
	                	            	//log.debug("TrackingAction - "+pTrackingForm.getNovelUserId()+" - searchDetail  - Error Checking");
	                	            	String newErrorMsg = "Consignment list " + consignmentNumber + " doesn't contain any ";
	                	            	StringBuilder newError;
	                	            	if(!errMsgDct.equals("") && (errMsgStu.equals("") && pTrackingForm.getDisplayUniqueNumbers().isEmpty())){
	                	            		//log.debug("TrackingAction - "+pTrackingForm.getNovelUserId()+" - searchDetail  - Docket Error");
	                	            		newError = (new StringBuilder()).append(newErrorMsg).append(errMsgDct);
	                	            	}else if(errMsgDct.equals("") && !errMsgStu.equals("")){
	                	            		//log.debug("TrackingAction - "+pTrackingForm.getNovelUserId()+" - searchDetail  - Student Error");
	                	            		newError = (new StringBuilder()).append(newErrorMsg).append(errMsgStu);
	                	            	}else{
	                	            		//log.debug("TrackingAction - "+pTrackingForm.getNovelUserId()+" - searchDetail  - Docket & Student Error");
	                	            		newError = (new StringBuilder()).append(newErrorMsg).append(errMsgDct).append("or").append(errMsgStu);
	                	            	}
	                	            	//log.debug("TrackingAction - "+pTrackingForm.getNovelUserId()+" - searchDetail  - Final Error: "+newError);
	                	            	messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage("message.generalmessage", newError));
	                	                addErrors(request, messages);
	                	            }
	                            }*/
	                        }
	                    }
	                }else if(searchType.equalsIgnoreCase("Docket")){
	                	//results = pGateWay.getSearch(webServiceURL ,websrcDocketId, pTrackingForm.getSearchString(),"Value1", "Value2");
	                }else if(searchType.equalsIgnoreCase("Assignment")){
	                    //results = pGateWay.getSearch(webServiceURL ,websrcAssignmentId, pTrackingForm.getSearchString(),"Value1", "Value2");
	                }else if(searchType.equalsIgnoreCase("Person")){
	                    //results = pGateWay.getSearch(webServiceURL ,websrcUserId, pTrackingForm.getSearchString(),"Value1", "Value2");
	                }
	                
	            }else{
	            	userObj.put("Error", "No Search String Entered");
	            }
            }

        }catch(Exception ex){
        	userObj.put("Error", "Search PDF failed.<br/>Please try again.<br/>" + ex);
        }
        
    	// Convert the map to json
    	PrintWriter out = response.getWriter();
       	JSONObject jsonObject = JSONObject.fromObject(userObj);
       	//log.debug("TrackingAction - "+pTrackingForm.getNovelUserId()+" - searchPDF - Final - **************************************************************");
       	//log.debug("TrackingAction - "+pTrackingForm.getNovelUserId()+" - searchPDF - Final - jsonObject="+jsonObject.toString());
       	//log.debug("TrackingAction - "+pTrackingForm.getNovelUserId()+" - searchPDF - Final - **************************************************************");
       	out.write(jsonObject.toString());
       	out.flush();

    	return null; //Returning null to prevent struts from interfering with ajax/json
    }

    
     public ActionForward back(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
    	
    	 TrackingForm pTrackingForm = (TrackingForm)form;
    	 //log.debug("TrackingAction - "+pTrackingForm.getNovelUserId()+" - back - Calling back method");

        HttpSession session = request.getSession(true);
        String returnType;
        if(pTrackingForm.getUserSelection() != null && !pTrackingForm.getUserSelection().equals("") && pTrackingForm.getUserSelection().equals("out")){
        	//log.debug("TrackingAction - "+pTrackingForm.getNovelUserId()+" - back - Calling back method from book out");
        	if(pTrackingForm.getListOfDocketNumbers() != null && !pTrackingForm.getListOfDocketNumbers().isEmpty() && pTrackingForm.getListOfDocketNumbers().size() > 0){
        		pTrackingForm.getListOfDocketNumbers().clear();
            }
            if(pTrackingForm.getListOfStudentAssignments() != null && !pTrackingForm.getListOfStudentAssignments().isEmpty() && pTrackingForm.getListOfStudentAssignments().size() > 0){
                pTrackingForm.getListOfStudentAssignments().clear();
            }
            resetForm(pTrackingForm,"back");
            returnType = pTrackingForm.getUser_Selection();
        }else{
        	//log.debug("TrackingAction - "+pTrackingForm.getNovelUserId()+" - back - Calling back method from book in");
        	if(pTrackingForm.getListOfDocketNumbers() != null && !pTrackingForm.getListOfDocketNumbers().isEmpty() && pTrackingForm.getListOfDocketNumbers().size() > 0){
        		pTrackingForm.getListOfDocketNumbers().clear();
            }
            if(pTrackingForm.getListOfStudentAssignments() != null && !pTrackingForm.getListOfStudentAssignments().isEmpty() && pTrackingForm.getListOfStudentAssignments().size() > 0){
                pTrackingForm.getListOfStudentAssignments().clear();
            }
            resetForm(pTrackingForm,"back");
            returnType = pTrackingForm.getUser_Selection();
        }
        return mapping.findForward(returnType);
    }
    

 	private Docket populateDocket(TrackingForm form, String docketID, String docketNumber) {	
 		
 		TrackingForm pTrackingForm = (TrackingForm)form;
		//log.debug("TrackingAction - "+pTrackingForm.getNovelUserId()+" - populateDocket - *******************************************************");
		//log.debug("TrackingAction - "+pTrackingForm.getNovelUserId()+" - populateDocket - DocketID="+docketID+", Docket Number="+docketNumber);
		//log.debug("TrackingAction - "+pTrackingForm.getNovelUserId()+" - populateDocket - *******************************************************");

		Docket docket = new Docket();
		docket.setDocketID(docketID);
		docket.setDocketNumber(docketNumber);		
		return docket;
	}
	
	private Assignment populateAssignment(TrackingForm form, String studentNumber, String uniqueAssignmentNumber) {	
		
		TrackingForm pTrackingForm = (TrackingForm)form;
		//log.debug("TrackingAction - "+pTrackingForm.getNovelUserId()+" - populateAssignment - *******************************************************");
		//log.debug("TrackingAction - "+pTrackingForm.getNovelUserId()+" - populateAssignment - StudentNumber="+studentNumber+", Assignment Number="+uniqueAssignmentNumber);
		//log.debug("TrackingAction - "+pTrackingForm.getNovelUserId()+" - populateAssignment - *******************************************************");
		Assignment assignment = new Assignment();
		assignment.setStudentNumber(studentNumber);
		assignment.setUniqueAssignmentNumber(uniqueAssignmentNumber);
		return assignment;	
	}
	
	private Consignment populateConsignment(String consignmentNumber, String date) {
		Consignment consignment = new Consignment();
		consignment.setConsignmentNumber(consignmentNumber);
		consignment.setDate(date);
		return consignment;
	}
	
	public boolean duplicateCheckConDockets (TrackingForm form, String docketNumber){
		
		TrackingForm pTrackingForm = (TrackingForm)form;
		boolean check = false;
		//log.debug("TrackingAction - "+pTrackingForm.getNovelUserId()+" - duplicateCheckConDockets - *******************************************************");
		//Use main Cover Docket list to check if docket is already in list of Cover Dockets
		if(!pTrackingForm.getMasterDocketList().isEmpty()) {
			for(Docket docket : pTrackingForm.getMasterDocketList()) {
				//log.debug("TrackingAction - "+pTrackingForm.getNovelUserId()+" - duplicateCheckConDockets - Docket Id : " +docket.getDocketID());
				//log.debug("TrackingAction - "+pTrackingForm.getNovelUserId()+" - duplicateCheckConDockets - Docket Number : " +docket.getDocketNumber());
				//log.debug("TrackingAction - "+pTrackingForm.getNovelUserId()+" - duplicateCheckConDockets - *******************************************************");
				if (docketNumber.equalsIgnoreCase(docket.getDocketNumber())){
					//log.debug("TrackingAction - "+pTrackingForm.getNovelUserId()+" - duplicateCheckConDockets - Docket Number Already Exists");
					check = true;
				}else{
					//log.debug("TrackingAction - "+pTrackingForm.getNovelUserId()+" - duplicateCheckConDockets - Docket Number unique, therefore NOT in List");
				}
			}
		}else{
			//log.debug("TrackingAction - "+pTrackingForm.getNovelUserId()+" - duplicateCheckConDockets - No Cover Dockets in Master List");
			//log.debug("TrackingAction - "+pTrackingForm.getNovelUserId()+" - duplicateCheckConDockets - *******************************************************");
		}
		return check;
	}

	public boolean duplicateCheckConAssignment (TrackingForm form, String studentNumber, String assignmentNumber){
		
		TrackingForm pTrackingForm = (TrackingForm)form;
		boolean check = false;
	   //Check of unique Assignment list for duplicate testing
	   //log.debug("TrackingAction - "+pTrackingForm.getNovelUserId()+" - duplicateCheckConAssignment - *******************************************************");
	   //log.debug("TrackingAction - "+pTrackingForm.getNovelUserId()+" - duplicateCheckConAssignment - Checking Unique Assignment Master List");
	   //log.debug("TrackingAction - "+pTrackingForm.getNovelUserId()+" - duplicateCheckConAssignment - *******************************************************");
		if(!pTrackingForm.getMapUnqAssignments().isEmpty()) {
            String AssignmentID = "";
            String AssignmentString = "";
			Set<String> keys = pTrackingForm.getMapUnqAssignments().keySet();
	        for(String key:keys){

	            AssignmentID = key;
	            AssignmentString = pTrackingForm.getMapUnqAssignments().get(key);
				//log.debug("TrackingAction - "+pTrackingForm.getNovelUserId()+" - duplicateCheckConAssignment - Assignment ID : " +AssignmentID);
				//log.debug("TrackingAction - "+pTrackingForm.getNovelUserId()+" - duplicateCheckConAssignment - Assignment ; String : " +AssignmentString);
				//log.debug("TrackingAction - "+pTrackingForm.getNovelUserId()+" - duplicateCheckConAssignment - *******************************************************");
				//log.debug("TrackingAction - "+pTrackingForm.getNovelUserId()+" - duplicateCheckConAssignment - Splitting ; String");
	      		String []  splitUnqAss  = AssignmentString.trim().split("~");
        		if(splitUnqAss[0] != null && !splitUnqAss[0].isEmpty()){
        			//log.debug("TrackingAction - "+pTrackingForm.getNovelUserId()+" - duplicateCheckConAssignment - Student Number : " +splitUnqAss[0]);
        			//log.debug("TrackingAction - "+pTrackingForm.getNovelUserId()+" - duplicateCheckConAssignment - Unique Assignment : " +splitUnqAss[1]);
        			//log.debug("TrackingAction - "+pTrackingForm.getNovelUserId()+" - duplicateCheckConAssignment - *******************************************************");
        			if(studentNumber.equalsIgnoreCase(splitUnqAss[0]) && assignmentNumber.equalsIgnoreCase(splitUnqAss[1])) {
       					//log.debug("TrackingAction - "+pTrackingForm.getNovelUserId()+" - duplicateCheckConAssignment - Assignment Already Exists");
       					check = true;
       				}else{
       					//log.debug("TrackingAction - "+pTrackingForm.getNovelUserId()+" - duplicateCheckConAssignment - Assignment unique, therefore NOT in List");
       				}
				}
			}
			//log.debug("TrackingAction - "+pTrackingForm.getNovelUserId()+" - duplicateCheckConAssignment - *******************************************************");
		}else{
			//log.debug("TrackingAction - "+pTrackingForm.getNovelUserId()+" - duplicateCheckConAssignment - No Unique Assignments in Master List");
			//log.debug("TrackingAction - "+pTrackingForm.getNovelUserId()+" - duplicateCheckConAssignment - *******************************************************");
		}
		return check;
	}
	
	public boolean duplicateCheckDctAssignment (TrackingForm form, String studentNumber, String assignmentNumber){
		
		TrackingForm pTrackingForm = (TrackingForm)form;
		boolean check = false;
		//Use main Cover Docket list to check if Consignment List Unique Assignment is already in list of Cover Dockets Linked Assignments
		//log.debug("TrackingAction - "+pTrackingForm.getNovelUserId()+" - duplicateCheckDctAssignment - Checking Cover Docket Master List");
		//log.debug("TrackingAction - "+pTrackingForm.getNovelUserId()+" - duplicateCheckDctAssignment - *******************************************************");
		if(!pTrackingForm.getMasterDocketList().isEmpty()) {
			for(Docket docket : pTrackingForm.getMasterDocketList()) {
				//log.debug("TrackingAction - "+pTrackingForm.getNovelUserId()+" - duplicateCheckDctAssignment - Docket Id : " +docket.getDocketID());
				//log.debug("TrackingAction - "+pTrackingForm.getNovelUserId()+" - duplicateCheckDctAssignment - Docket Number : " +docket.getDocketNumber());
				//log.debug("TrackingAction - "+pTrackingForm.getNovelUserId()+" - duplicateCheckDctAssignment - *******************************************************");
				if(docket.getAssignments() != null ) {
					for (Assignment assignment : docket.getAssignments()){
						//log.debug("TrackingAction - "+pTrackingForm.getNovelUserId()+" - duplicateCheckDctAssignment - StudentNumber : " +assignment.getStudentNumber().trim());
						//log.debug("TrackingAction - "+pTrackingForm.getNovelUserId()+" - duplicateCheckDctAssignment - UniqueAssignmentNumber : " +assignment.getUniqueAssignmentNumber().trim());
						if (studentNumber.equalsIgnoreCase(assignment.getStudentNumber().trim()) && assignmentNumber.equalsIgnoreCase(assignment.getUniqueAssignmentNumber().trim())){
							//log.debug("TrackingAction - "+pTrackingForm.getNovelUserId()+" - duplicateCheckDctAssignment - Assignment Already Exists in Cover Docket List : "+docket.getDocketNumber());
							check = true;
						}else{
							//log.debug("TrackingAction - "+pTrackingForm.getNovelUserId()+" - duplicateCheckDctAssignment - Assignment unique, therefore NOT in Cover Docket "+docket.getDocketNumber()+" List");
						}
					}
				}else{
					//log.debug("TrackingAction - "+pTrackingForm.getNovelUserId()+" - duplicateCheckDctAssignment - No Cover Dockets linked Assignments in Master Docket List");
				}

			}
		}else{
			//log.debug("TrackingAction - "+pTrackingForm.getNovelUserId()+" - duplicateCheckDctAssignment - No Cover Dockets in Master List");
			//log.debug("TrackingAction - "+pTrackingForm.getNovelUserId()+" - duplicateCheckDctAssignment - *******************************************************");
		}

		return check;
	}

	public boolean duplicateCheckDockets (TrackingForm form, String docketNumber){
		
		TrackingForm pTrackingForm = (TrackingForm)form;
		boolean check = false;
		//log.debug("TrackingAction - "+pTrackingForm.getNovelUserId()+" - duplicateCheckDockets - *******************************************************");
		//Use main consignment list to check if docket is already in Consignment list of Cover Dockets
		if(!pTrackingForm.getMasterConsignmentList().isEmpty()) {
			for(Consignment consignDupTest : pTrackingForm.getMasterConsignmentList()) {
				//log.debug("TrackingAction - "+pTrackingForm.getNovelUserId()+" - duplicateCheckDockets - Consignment Number : " +consignDupTest.getConsignmentNumber());
				//log.debug("TrackingAction - "+pTrackingForm.getNovelUserId()+" - duplicateCheckDockets - Consignment Date : " +consignDupTest.getDate());			
				//log.debug("TrackingAction - "+pTrackingForm.getNovelUserId()+" - duplicateCheckDockets - *******************************************************");
				
				if(consignDupTest.getDockets() != null ) {
					for(Docket docket : consignDupTest.getDockets()) {
						//log.debug("TrackingAction - "+pTrackingForm.getNovelUserId()+" - duplicateCheckDockets - Docket Id : " +docket.getDocketID());
						//log.debug("TrackingAction - "+pTrackingForm.getNovelUserId()+" - duplicateCheckDockets - Docket Number : " +docket.getDocketNumber());
						//log.debug("TrackingAction - "+pTrackingForm.getNovelUserId()+" - duplicateCheckDockets - *******************************************************");
						if (docketNumber.equalsIgnoreCase(docket.getDocketNumber())){
							//log.debug("TrackingAction - "+pTrackingForm.getNovelUserId()+" - duplicateCheckDockets - Docket Number Already Exists in Consignment List");
							check = true;
						}else{
							//log.debug("TrackingAction - "+pTrackingForm.getNovelUserId()+" - duplicateCheckDockets - Docket Number unique, therefore NOT in consignment List");
						}
					}
				}else{
					//log.debug("TrackingAction - "+pTrackingForm.getNovelUserId()+" - duplicateCheckDockets - No Cover Dockets in Master Consignment List");
					//log.debug("TrackingAction - "+pTrackingForm.getNovelUserId()+" - duplicateCheckDockets - *******************************************************");
				}
			}
		}else{
			//log.debug("TrackingAction - "+pTrackingForm.getNovelUserId()+" - duplicateCheckDockets - No Consignments in Master List");
			//log.debug("TrackingAction - "+pTrackingForm.getNovelUserId()+" - duplicateCheckDockets - *******************************************************");
		}
		//Use main Cover Docket list to check if docket is already in list of Cover Dockets
		if(!pTrackingForm.getMasterDocketList().isEmpty()) {
			for(Docket docket : pTrackingForm.getMasterDocketList()) {
				//log.debug("TrackingAction - "+pTrackingForm.getNovelUserId()+" - duplicateCheckDockets - Docket Id : " +docket.getDocketID());
				//log.debug("TrackingAction - "+pTrackingForm.getNovelUserId()+" - duplicateCheckDockets - Docket Number : " +docket.getDocketNumber());
				//log.debug("TrackingAction - "+pTrackingForm.getNovelUserId()+" - duplicateCheckDockets - *******************************************************");
				if (docketNumber.equalsIgnoreCase(docket.getDocketNumber())){
					//log.debug("TrackingAction - "+pTrackingForm.getNovelUserId()+" - duplicateCheckDockets - Docket Number Already Exists");
					check = true;
				}else{
					//log.debug("TrackingAction - "+pTrackingForm.getNovelUserId()+" - duplicateCheckDockets - Docket Number unique, therefore NOT in List");
				}
			}
		}else{
			//log.debug("TrackingAction - "+pTrackingForm.getNovelUserId()+" - duplicateCheckDockets - No Cover Dockets in Master List");
			//log.debug("TrackingAction - "+pTrackingForm.getNovelUserId()+" - duplicateCheckDockets - *******************************************************");
		}
		return check;
	}				
	
	
	public boolean duplicateCheckConUniqueAssignment (TrackingForm form, String studentNumber, String assignmentNumber){
		
		TrackingForm pTrackingForm = (TrackingForm)form;
		boolean check = false;
		//log.debug("TrackingAction - "+pTrackingForm.getNovelUserId()+" - duplicateCheckConUniqueAssignment - *******************************************************");
		//log.debug("TrackingAction - "+pTrackingForm.getNovelUserId()+" - duplicateCheckConUniqueAssignment - Checking Consignment Master List for Unique Assignments");
		//log.debug("TrackingAction - "+pTrackingForm.getNovelUserId()+" - duplicateCheckConUniqueAssignment - Checking StudentNumber="+studentNumber+", Unique Assignment="+assignmentNumber);
		//log.debug("TrackingAction - "+pTrackingForm.getNovelUserId()+" - duplicateCheckConUniqueAssignment - *******************************************************");
		//Use main consignment list to check if assignment is already in Consignment list
		if(!pTrackingForm.getMasterConsignmentList().isEmpty()) {
			for(Consignment consignDupTest : pTrackingForm.getMasterConsignmentList()) {
				//log.debug("TrackingAction - "+pTrackingForm.getNovelUserId()+" - duplicateCheckConUniqueAssignment - Consignment Number : " +consignDupTest.getConsignmentNumber());
				//log.debug("TrackingAction - "+pTrackingForm.getNovelUserId()+" - duplicateCheckConUniqueAssignment - Consignment Date : " +consignDupTest.getDate());			
				//log.debug("TrackingAction - "+pTrackingForm.getNovelUserId()+" - duplicateCheckConUniqueAssignment - *******************************************************");
				if(consignDupTest.getAssignments() != null ) {
					for (Assignment assignment : consignDupTest.getAssignments()){
						String []  splitUnqAss  = assignment.getUniqueAssignmentNumber().trim().split("~");
		        		if(splitUnqAss[0] != null && !splitUnqAss[0].isEmpty()){
		        			//log.debug("TrackingAction - "+pTrackingForm.getNovelUserId()+" - duplicateCheckConUniqueAssignment - Consignment Student Number : " +splitUnqAss[0].trim());
		        			//log.debug("TrackingAction - "+pTrackingForm.getNovelUserId()+" - duplicateCheckConUniqueAssignment - Consignment Unique Assignment : " +splitUnqAss[1].trim());
		        			//log.debug("TrackingAction - "+pTrackingForm.getNovelUserId()+" - duplicateCheckAssignment - *******************************************************");
		        			if(studentNumber.equalsIgnoreCase(splitUnqAss[0].trim()) && assignmentNumber.equalsIgnoreCase(splitUnqAss[1].trim())) {
		        				//log.debug("TrackingAction - "+pTrackingForm.getNovelUserId()+" - duplicateCheckAssignment - Assignment Already Exists in Consignment List : "+consignDupTest.getConsignmentNumber());
		       					check = true;
		       				}else{
		       					//log.debug("TrackingAction - "+pTrackingForm.getNovelUserId()+" - duplicateCheckAssignment - Assignment unique, therefore NOT in Consignment List:" +consignDupTest.getConsignmentNumber());
		       				}
						}
					}
				}else{
					//log.debug("TrackingAction - "+pTrackingForm.getNovelUserId()+" - duplicateCheckAssignment - No Unique Assignments in Master Consignment List:" +consignDupTest.getConsignmentNumber());
				}
			}
		}else{
			//log.debug("TrackingAction - "+pTrackingForm.getNovelUserId()+" - duplicateCheckConUniqueAssignment - No Consignments in Master List:");
			//log.debug("TrackingAction - "+pTrackingForm.getNovelUserId()+" - duplicateCheckConUniqueAssignment - *******************************************************");
		}
		return check;
	}

	public boolean duplicateCheckUniqueAssignment (TrackingForm form, String studentNumber, String assignmentNumber){
		
		TrackingForm pTrackingForm = (TrackingForm)form;
		boolean check = false;
		//Check of unique Assignment list for duplicate testing
		//log.debug("TrackingAction - "+pTrackingForm.getNovelUserId()+" - duplicateCheckUniqueAssignment - *******************************************************");
		//log.debug("TrackingAction - "+pTrackingForm.getNovelUserId()+" - duplicateCheckUniqueAssignment - Checking Assignment Master List for Student="+studentNumber+", Assignment="+assignmentNumber);
		//log.debug("TrackingAction - "+pTrackingForm.getNovelUserId()+" - duplicateCheckUniqueAssignment - *******************************************************");
		if(!pTrackingForm.getMapUnqAssignments().isEmpty()) {
            String AssignmentID = "";
            String AssignmentString = "";
			Set<String> keys = pTrackingForm.getMapUnqAssignments().keySet();
	        for(String key:keys){

	            AssignmentID = key.trim();
	            AssignmentString = pTrackingForm.getMapUnqAssignments().get(key).trim();
				//log.debug("TrackingAction - "+pTrackingForm.getNovelUserId()+" - duplicateCheckUniqueAssignment - Found Assignment ID : " +AssignmentID);
				//log.debug("TrackingAction - "+pTrackingForm.getNovelUserId()+" - duplicateCheckUniqueAssignment - Found Assignment ; String : " +AssignmentString);
				//log.debug("TrackingAction - "+pTrackingForm.getNovelUserId()+" - duplicateCheckUniqueAssignment - *******************************************************");
				//log.debug("TrackingAction - "+pTrackingForm.getNovelUserId()+" - duplicateCheckUniqueAssignment - Splitting ; String");
	      		String []  splitUnqAss  = AssignmentString.trim().split("~");
        		if(splitUnqAss[0] != null && !splitUnqAss[0].isEmpty()){
        			//log.debug("TrackingAction - "+pTrackingForm.getNovelUserId()+" - duplicateCheckUniqueAssignment - Found Student Number : " +splitUnqAss[0].trim());
        			//log.debug("TrackingAction - "+pTrackingForm.getNovelUserId()+" - duplicateCheckUniqueAssignment - Found Unique Assignment : " +splitUnqAss[1].trim());
        			//log.debug("TrackingAction - "+pTrackingForm.getNovelUserId()+" - duplicateCheckUniqueAssignment - *******************************************************");
        			if(studentNumber.equalsIgnoreCase(splitUnqAss[0].trim()) && assignmentNumber.equalsIgnoreCase(splitUnqAss[1].trim())) {
       					//log.debug("TrackingAction - "+pTrackingForm.getNovelUserId()+" - duplicateCheckUniqueAssignment - Assignment Already Exists");
       					check = true;
       				}else{
       					//log.debug("TrackingAction - "+pTrackingForm.getNovelUserId()+" - duplicateCheckUniqueAssignment - Assignment unique, therefore NOT in List");
       				}
				}
			}
			//log.debug("TrackingAction - "+pTrackingForm.getNovelUserId()+" - duplicateCheckUniqueAssignment - *******************************************************");
		}else{
			//log.debug("TrackingAction - "+pTrackingForm.getNovelUserId()+" - duplicateCheckUniqueAssignment - No Unique Assignments in Master List");
			//log.debug("TrackingAction - "+pTrackingForm.getNovelUserId()+" - duplicateCheckUniqueAssignment - *******************************************************");
		}
		return check;
	}
	
	public boolean duplicateCheckAllAssignments (TrackingForm form, String studentNumber, String assignmentNumber){
		
		TrackingForm pTrackingForm = (TrackingForm)form;
		boolean check = false;
		//log.debug("TrackingAction - "+pTrackingForm.getNovelUserId()+" - duplicateCheckAllAssignments - *******************************************************");
		//log.debug("TrackingAction - "+pTrackingForm.getNovelUserId()+" - duplicateCheckAllAssignments - Checking Consignment Master List for Student="+studentNumber+", Assignment="+assignmentNumber);
		//log.debug("TrackingAction - "+pTrackingForm.getNovelUserId()+" - duplicateCheckAllAssignments - *******************************************************");
		//Use main consignment list to check if assignment is already in Consignment list of Cover Dockets
		if(!pTrackingForm.getMasterConsignmentList().isEmpty()) {
			for(Consignment consignDupTest : pTrackingForm.getMasterConsignmentList()) {
				//log.debug("TrackingAction - "+pTrackingForm.getNovelUserId()+" - duplicateCheckAllAssignments - Consignment Number : " +consignDupTest.getConsignmentNumber());
				//log.debug("TrackingAction - "+pTrackingForm.getNovelUserId()+" - duplicateCheckAllAssignments - Consignment Date : " +consignDupTest.getDate());			
				//log.debug("TrackingAction - "+pTrackingForm.getNovelUserId()+" - duplicateCheckAllAssignments - *******************************************************");
				
				if(consignDupTest.getDockets() != null ) {
					for(Docket docket : consignDupTest.getDockets()) {
						//log.debug("TrackingAction - "+pTrackingForm.getNovelUserId()+" - duplicateCheckAllAssignments - Found Docket Id : " +docket.getDocketID());
						//log.debug("TrackingAction - "+pTrackingForm.getNovelUserId()+" - duplicateCheckAllAssignments - Found Docket Number : " +docket.getDocketNumber());
						//log.debug("TrackingAction - "+pTrackingForm.getNovelUserId()+" - duplicateCheckAllAssignments - *******************************************************");
						if(docket.getAssignments() != null ) {
							for (Assignment assignment : docket.getAssignments()){
								//log.debug("TrackingAction - "+pTrackingForm.getNovelUserId()+" - duplicateCheckAllAssignments - Found Docket StudentNumber : " +assignment.getStudentNumber().trim());
								//log.debug("TrackingAction - "+pTrackingForm.getNovelUserId()+" - duplicateCheckAllAssignments - Found Docket UniqueAssignmentNumber : " +assignment.getUniqueAssignmentNumber().trim());
								if (studentNumber.equalsIgnoreCase(assignment.getStudentNumber().trim()) && assignmentNumber.equalsIgnoreCase(assignment.getUniqueAssignmentNumber().trim())){
									//log.debug("TrackingAction - "+pTrackingForm.getNovelUserId()+" - duplicateCheckAllAssignments - Assignment Already Exists in Consignment List Cover Docket: "+consignDupTest.getConsignmentNumber()+", Docket: "+docket.getDocketNumber());
									check = true;
								}else{
									//log.debug("TrackingAction - "+pTrackingForm.getNovelUserId()+" - duplicateCheckAllAssignments - Assignment unique, therefore NOT in Consignment List Cover Docket: "+consignDupTest.getConsignmentNumber()+", Docket: "+docket.getDocketNumber());
								}
							}
						}else{
							//log.debug("TrackingAction - "+pTrackingForm.getNovelUserId()+" - duplicateCheckAllAssignments - No Cover Dockets linked Assignments in Master Consignment List: " +consignDupTest.getConsignmentNumber());
						}
					}
				}else{
					//log.debug("TrackingAction - "+pTrackingForm.getNovelUserId()+" - duplicateCheckAllAssignments - No Cover Dockets in Master Consignment List: " +consignDupTest.getConsignmentNumber());
					//log.debug("TrackingAction - "+pTrackingForm.getNovelUserId()+" - duplicateCheckAllAssignments - *******************************************************");
				}
				if(consignDupTest.getAssignments() != null ) {
					for (Assignment assignment : consignDupTest.getAssignments()){
						String []  splitUnqAss  = assignment.getUniqueAssignmentNumber().trim().split("~");
		        		if(splitUnqAss[0] != null && !splitUnqAss[0].isEmpty()){
		        			//log.debug("TrackingAction - "+pTrackingForm.getNovelUserId()+" - duplicateCheckAllAssignments - Found Consignment Student Number : " +splitUnqAss[0].trim());
		        			//log.debug("TrackingAction - "+pTrackingForm.getNovelUserId()+" - duplicateCheckAllAssignments - Found Consignment Unique Assignment : " +splitUnqAss[1].trim());
		        			//log.debug("TrackingAction - "+pTrackingForm.getNovelUserId()+" - duplicateCheckAllAssignments - *******************************************************");
		        			if(studentNumber.equalsIgnoreCase(splitUnqAss[0].trim()) && assignmentNumber.equalsIgnoreCase(splitUnqAss[1].trim())) {
		        				//log.debug("TrackingAction - "+pTrackingForm.getNovelUserId()+" - duplicateCheckAllAssignments - Assignment Already Exists in Consignment List : "+consignDupTest.getConsignmentNumber());
		       					check = true;
		       				}else{
		       					//log.debug("TrackingAction - "+pTrackingForm.getNovelUserId()+" - duplicateCheckAllAssignments - Assignment unique, therefore NOT in Consignment List:" +consignDupTest.getConsignmentNumber());
		       				}
						}
					}
				}else{
					//log.debug("TrackingAction - "+pTrackingForm.getNovelUserId()+" - duplicateCheckAllAssignments - No Unique Assignments in Master Consignment List:" +consignDupTest.getConsignmentNumber());
				}
			}
		}else{
			//log.debug("TrackingAction - "+pTrackingForm.getNovelUserId()+" - duplicateCheckAllAssignments - No Consignments in Master List");
			//log.debug("TrackingAction - "+pTrackingForm.getNovelUserId()+" - duplicateCheckAllAssignments - *******************************************************");
		}
		//Use main Cover Docket list to check if docket is already in list of Cover Dockets
		//log.debug("TrackingAction - "+pTrackingForm.getNovelUserId()+" - duplicateCheckAllAssignments - Checking Unique Cover Docket Master List");
		//log.debug("TrackingAction - "+pTrackingForm.getNovelUserId()+" - duplicateCheckAllAssignments - *******************************************************");
		if(!pTrackingForm.getMasterDocketList().isEmpty()) {
			for(Docket docket : pTrackingForm.getMasterDocketList()) {
				//log.debug("TrackingAction - "+pTrackingForm.getNovelUserId()+" - duplicateCheckAllAssignments - Found Docket Id : " +docket.getDocketID());
				//log.debug("TrackingAction - "+pTrackingForm.getNovelUserId()+" - duplicateCheckAllAssignments - Found Docket Number : " +docket.getDocketNumber());
				//log.debug("TrackingAction - "+pTrackingForm.getNovelUserId()+" - duplicateCheckAllAssignments - *******************************************************");
				if(docket.getAssignments() != null ) {
					for (Assignment assignment : docket.getAssignments()){
						//log.debug("TrackingAction - "+pTrackingForm.getNovelUserId()+" - duplicateCheckAllAssignments - Found StudentNumber : " +assignment.getStudentNumber().trim());
						//log.debug("TrackingAction - "+pTrackingForm.getNovelUserId()+" - duplicateCheckAllAssignments - Found UniqueAssignmentNumber : " +assignment.getUniqueAssignmentNumber().trim());
						if (studentNumber.equalsIgnoreCase(assignment.getStudentNumber().trim()) && assignmentNumber.equalsIgnoreCase(assignment.getUniqueAssignmentNumber().trim())){
							//log.debug("TrackingAction - "+pTrackingForm.getNovelUserId()+" - duplicateCheckAllAssignments - Assignment Already Exists in Cover Docket List : "+docket.getDocketNumber());
							check = true;
						}else{
							//log.debug("TrackingAction - "+pTrackingForm.getNovelUserId()+" - duplicateCheckAllAssignments - Assignment unique, therefore NOT in Cover Docket "+docket.getDocketNumber()+" List");
						}
					}
				}else{
					//log.debug("TrackingAction - "+pTrackingForm.getNovelUserId()+" - duplicateCheckAllAssignments - No Cover Dockets linked Assignments in Master Docket List");
				}

			}
		}else{
			//log.debug("TrackingAction - "+pTrackingForm.getNovelUserId()+" - duplicateCheckAllAssignments - No Cover Dockets in Master List");
			//log.debug("TrackingAction - "+pTrackingForm.getNovelUserId()+" - duplicateCheckAllAssignments - *******************************************************");
		}
		
	   //Final check of unique Assignment list for duplicate testing
	   //log.debug("TrackingAction - "+pTrackingForm.getNovelUserId()+" - duplicateCheckAllAssignments - *******************************************************");
	   //log.debug("TrackingAction - "+pTrackingForm.getNovelUserId()+" - duplicateCheckAllAssignments - Checking Unique Assignment Master List");
	   //log.debug("TrackingAction - "+pTrackingForm.getNovelUserId()+" - duplicateCheckAllAssignments - *******************************************************");
		if(!pTrackingForm.getMapUnqAssignments().isEmpty()) {
            String AssignmentID = "";
            String AssignmentString = "";
			Set<String> keys = pTrackingForm.getMapUnqAssignments().keySet();
	        for(String key:keys){

	            AssignmentID = key.trim();
	            AssignmentString = pTrackingForm.getMapUnqAssignments().get(key).trim();
				//log.debug("TrackingAction - "+pTrackingForm.getNovelUserId()+" - duplicateCheckAllAssignments - Found Assignment ID : " +AssignmentID);
				//log.debug("TrackingAction - "+pTrackingForm.getNovelUserId()+" - duplicateCheckAllAssignments - Found Assignment ; String : " +AssignmentString);
				//log.debug("TrackingAction - "+pTrackingForm.getNovelUserId()+" - duplicateCheckAllAssignments - *******************************************************");
				//log.debug("TrackingAction - "+pTrackingForm.getNovelUserId()+" - duplicateCheckAllAssignments - Splitting ; String");
	      		String []  splitUnqAss  = AssignmentString.trim().split("~");
        		if(splitUnqAss[0] != null && !splitUnqAss[0].isEmpty()){
        			//log.debug("TrackingAction - "+pTrackingForm.getNovelUserId()+" - duplicateCheckAllAssignments - Found Student Number : " +splitUnqAss[0].trim());
        			//log.debug("TrackingAction - "+pTrackingForm.getNovelUserId()+" - duplicateCheckAllAssignments - Found Unique Assignment : " +splitUnqAss[1].trim());
        			//log.debug("TrackingAction - "+pTrackingForm.getNovelUserId()+" - duplicateCheckAllAssignments - *******************************************************");
        			if(studentNumber.equalsIgnoreCase(splitUnqAss[0].trim()) && assignmentNumber.equalsIgnoreCase(splitUnqAss[1].trim())) {
       					//log.debug("TrackingAction - "+pTrackingForm.getNovelUserId()+" - duplicateCheckAllAssignments - Assignment Already Exists");
       					check = true;
       				}else{
       					//log.debug("TrackingAction - "+pTrackingForm.getNovelUserId()+" - duplicateCheckAllAssignments - Assignment unique, therefore NOT in List");
       				}
				}
			}
			//log.debug("TrackingAction - "+pTrackingForm.getNovelUserId()+" - duplicateCheckAllAssignments - *******************************************************");
		}else{
			//log.debug("TrackingAction - "+pTrackingForm.getNovelUserId()+" - duplicateCheckAllAssignments - No Unique Assignments in Master List");
			//log.debug("TrackingAction - "+pTrackingForm.getNovelUserId()+" - duplicateCheckAllAssignments - *******************************************************");
		}
		return check;
	}

	public String sendNotificationToUser(TrackingForm form) throws Exception {

    	prepareBackendSession();
		TrackingForm pTrackingForm = (TrackingForm)form;
//		WebServiceGateWay pGateWay = new WebServiceGateWay();
		String result = "";
		try {
			//log.debug("TrackingAction - "+pTrackingForm.getNovelUserId()+" - sendNotificationToUser - **************************************************************");

			int webserviceEmailId = 355144; //GET_DEFAULT_EMAIL_ADDRESS 355144 A 
			int webserviceIdPersNo = 18428; //LIST_USERS 18428 A 
			int webserviceIdNovellID = 760219; //GET_USER_DETAILS_USR 18428 A 
			ArrayList<KeyValue> getValues = null;
			String userEmail = "";
			String toUser = "";
			StringBuilder userValue = new StringBuilder();
			StringBuilder fromUserValue = new StringBuilder();

			String serverPath = ServerConfigurationService.getToolUrl().toLowerCase();
			//Do not send email to actual recipient on localhost,dev or qa, in that case, rather send it to the current tester

			//if (serverPath.contains("mydev") || serverPath.contains("myqa") || serverPath.contains("localhost")){
			if ("X".equals("Y")){
				//Get Default Email Address (ADRPH - 1, TRACKING DEFAULT, 40)
				//log.debug("TrackingAction - "+pTrackingForm.getNovelUserId()+" - sendNotificationToUser - Get Default Email Address from ADRPH");
				String tmpMail = pGateWay.getDefaultEmail(pTrackingForm.getWebServiceURL() ,webserviceEmailId, "value1");
				if (tmpMail != null && !tmpMail.equals("")){
					userEmail = tmpMail;
					userValue = (new StringBuilder()).append(userValue).append("Test User ("+form.getNovelUserId().toString()+")");
					//log.debug("TrackingAction - "+pTrackingForm.getNovelUserId()+" - sendNotificationToUser - Default Email Address="+userEmail);
					//log.debug("TrackingAction - "+pTrackingForm.getNovelUserId()+" - sendNotificationToUser - Default Email Recipient="+userValue.toString());
					//log.debug("TrackingAction - "+pTrackingForm.getNovelUserId()+" - sendNotificationToUser - **************************************************************");
				}
			}else{ //Production
				//Get Actual destincation Person's email address
				//log.debug("TrackingAction - "+pTrackingForm.getNovelUserId()+" - sendNotificationToUser - SaveAddress1="+pTrackingForm.getSaveAddress1());
				if (form.getSaveAddress1() != null && !form.getSaveAddress1().equals("")){
					if (form.getSaveAddress1().toUpperCase().contains("PERSON")){
						//log.debug("TrackingAction - "+pTrackingForm.getNovelUserId()+" - sendNotificationToUser - PERSON="+pTrackingForm.getSaveAddress1());
	        			String [] splitPerson  = form.getSaveAddress1().trim().split("_");
		        		if(splitPerson[0] != null && !splitPerson[0].isEmpty()){
		        			toUser = splitPerson[1];
		        			//log.debug("TrackingAction - "+pTrackingForm.getNovelUserId()+" - sendNotificationToUser - PERSON Split="+toUser);
		        		}
					}
				}
				
				if (toUser != null && !toUser.equals("")){
					//log.debug("TrackingAction - "+pTrackingForm.getNovelUserId()+" - sendNotificationToUser - **************************************************************");
					//log.debug("TrackingAction - "+pTrackingForm.getNovelUserId()+" - sendNotificationToUser - Get Destination User Details");
					//log.debug("TrackingAction - "+pTrackingForm.getNovelUserId()+" - sendNotificationToUser - getUserList1 - UserID="+toUser);

					getValues = pGateWay.getUserList1(pTrackingForm.getWebServiceURL() ,webserviceIdPersNo, "value1", "value2", "value3", "value4", "value5", "value6", toUser);
						if (!getValues.isEmpty()){
				    		Iterator<KeyValue> it = getValues.iterator();
				    		//log.debug("TrackingAction - "+pTrackingForm.getNovelUserId()+" - sendNotificationToUser - Iterator");
				    		if (it.hasNext()){
					    		while(it.hasNext()){
					    			//log.debug("TrackingAction - "+pTrackingForm.getNovelUserId()+" - sendNotificationToUser - Result Iterator Has Next");
									KeyValue test = (KeyValue) it.next();
									//log.debug("TrackingAction - "+pTrackingForm.getNovelUserId()+" - sendNotificationToUser - Result ToUser StaffNumber="+test.getValue1());
									//userKey = test.getValue().trim().toString();
				
									//Build String in order (Title, Initials, Surname);
									if(test.getValue4() != null &&  !test.getValue4().isEmpty()){
										if(!test.getValue4().equals("null")){
											//log.debug("TrackingAction - "+pTrackingForm.getNovelUserId()+" - sendNotificationToUser - Result ToUser Title="+test.getValue4());
											userValue = (new StringBuilder()).append(userValue).append(test.getValue4().trim().toString()).append(".");
										}
									}
									if(test.getValue3() != null &&  !test.getValue3().isEmpty()){
										if(!test.getValue3().equals("null")){
											//log.debug("TrackingAction - "+pTrackingForm.getNovelUserId()+" - sendNotificationToUser - Result ToUser Initials="+test.getValue3());
											userValue = (new StringBuilder()).append(userValue).append(" ").append(test.getValue3().trim().toString());
										}
									}
									if(test.getValue2() != null &&  !test.getValue2().isEmpty()){
										if(!test.getValue2().equals("null")){
											//log.debug("TrackingAction - "+pTrackingForm.getNovelUserId()+" - sendNotificationToUser - Result ToUser Surname="+test.getValue2());
											userValue = (new StringBuilder()).append(userValue).append(" ").append(test.getValue2().trim().toString());
										}
									}
									if(test.getValue6() != null &&  !test.getValue6().isEmpty()){
										if(!test.getValue6().equals("null")){
											//log.debug("TrackingAction - "+pTrackingForm.getNovelUserId()+" - sendNotificationToUser - Result ToUser Email="+test.getValue6());
											userEmail = test.getValue6().trim().toString();
										}
									}
					    		}
				    		}
						}
				}else if (form.getAddressType().equalsIgnoreCase("MANUAL")){
					//log.debug("TrackingAction - "+pTrackingForm.getNovelUserId()+" - sendNotificationToUser - **************************************************************");
					//log.debug("TrackingAction - "+pTrackingForm.getNovelUserId()+" - sendNotificationToUser - Get Manually Entered Destination User Details");

	    			if (form.getUserEmail()!=null && !form.getUserEmail().equals("")){
	    				userEmail = form.getDisplayEmail(); 
	    				userValue = (new StringBuilder()).append(userValue).append(form.getDisplayAddress1());
	    			}
	    		}
			}
			
			//log.debug("TrackingAction - "+pTrackingForm.getNovelUserId()+" - sendNotificationToUser - **************************************************************");
			//log.debug("TrackingAction - "+pTrackingForm.getNovelUserId()+" - sendNotificationToUser - userValue="+userValue.toString());
			//log.debug("TrackingAction - "+pTrackingForm.getNovelUserId()+" - sendNotificationToUser - userEmail="+userEmail);
			//log.debug("TrackingAction - "+pTrackingForm.getNovelUserId()+" - sendNotificationToUser - **************************************************************");

			
			if (userValue == null || userValue.equals("")){
				//log.debug("TrackingAction - "+pTrackingForm.getNovelUserId()+" - sendNotificationToUser - **************************************************************");
				//log.debug("TrackingAction - "+pTrackingForm.getNovelUserId()+" - sendNotificationToUser - No Destination User. Using NovelUserID");
				userValue = (new StringBuilder()).append(userValue).append(" ").append("(").append(form.getNovelUserId()).append(")");
				//log.debug("TrackingAction - "+pTrackingForm.getNovelUserId()+" - sendNotificationToUser - Destination User="+userValue.toString());
				//log.debug("TrackingAction - "+pTrackingForm.getNovelUserId()+" - sendNotificationToUser - **************************************************************");
			}
			
			//Get Sender's Information as we don't want to use the Novel ID
			//log.debug("TrackingAction - "+pTrackingForm.getNovelUserId()+" - sendNotificationToUser - **************************************************************");
			//log.debug("TrackingAction - "+pTrackingForm.getNovelUserId()+" - sendNotificationToUser - Get Sending User Details");
			if (form.getPersNo() != null && !"".equals(form.getPersNo())){
				//log.debug("TrackingAction - "+pTrackingForm.getNovelUserId()+" - sendNotificationToUser - getUserList1 - PERSNO="+form.getPersNo());
				getValues = pGateWay.getUserList1(pTrackingForm.getWebServiceURL() ,webserviceIdPersNo, "value1", "value2", "value3", "value4", "value5", "value6", form.getPersNo());
			}else{
				//log.debug("TrackingAction - "+pTrackingForm.getNovelUserId()+" - sendNotificationToUser - getUserList5 - UserID="+form.getPersNo());
				getValues = pGateWay.getUserList5(pTrackingForm.getWebServiceURL() ,webserviceIdNovellID, "value1", "value2", "value3", "value4", "value5", "value6", form.getNovelUserId().toString());
			}
			
			if (!getValues.isEmpty()){
	    		Iterator<KeyValue> it = getValues.iterator();
	    		//log.debug("TrackingAction - "+pTrackingForm.getNovelUserId()+" - sendNotificationToUser - Iterator");
	    		if (it.hasNext()){
		    		while(it.hasNext()){
		    			//log.debug("TrackingAction - "+pTrackingForm.getNovelUserId()+" - sendNotificationToUser - Result Iterator Has Next");
		    			String userKey = "";
						KeyValue test = (KeyValue) it.next();
						//log.debug("TrackingAction - "+pTrackingForm.getNovelUserId()+" - sendNotificationToUser - Result FromUser StaffNumber="+test.getValue1());
						//userKey = test.getValue().trim().toString();
	
						//Build String in order (Title, Initials, Surname);
						if(test.getValue4() != null &&  !test.getValue4().isEmpty()){
							if(!test.getValue4().equals("null")){
								//log.debug("TrackingAction - "+pTrackingForm.getNovelUserId()+" - sendNotificationToUser - Result FromUser Title="+test.getValue4());
								fromUserValue = (new StringBuilder()).append(fromUserValue).append(test.getValue4().trim().toString()).append(".");
							}
						}
						if(test.getValue3() != null &&  !test.getValue3().isEmpty()){
							if(!test.getValue3().equals("null")){
								//log.debug("TrackingAction - "+pTrackingForm.getNovelUserId()+" - sendNotificationToUser - Result FromUser Initials="+test.getValue3());
								fromUserValue = (new StringBuilder()).append(fromUserValue).append(" ").append(test.getValue3().trim().toString());
							}
						}
						if(test.getValue2() != null &&  !test.getValue2().isEmpty()){
							if(!test.getValue2().equals("null")){
								//log.debug("TrackingAction - "+pTrackingForm.getNovelUserId()+" - sendNotificationToUser - Result FromUser Surname="+test.getValue2());
								fromUserValue = (new StringBuilder()).append(fromUserValue).append(" ").append(test.getValue2().trim().toString());
							}
						}
						
						//Finally Add Sending User's Personnel number
						fromUserValue = (new StringBuilder()).append(fromUserValue).append(" ").append("Personel Number (").append(test.getValue1().trim().toString()).append(")");

		    		}
	    		}else{
	    			//log.debug("TrackingAction - "+pTrackingForm.getNovelUserId()+" - sendNotificationToUser - No Sending User - Set Novell User Details");
	    			fromUserValue = (new StringBuilder()).append(fromUserValue).append(" ").append("User (").append(form.getNovelUserId().toString()).append(")");
	    		}
			}

			//log.debug("TrackingAction - "+pTrackingForm.getNovelUserId()+" - sendNotificationToUser - **************************************************************");
			//log.debug("TrackingAction - "+pTrackingForm.getNovelUserId()+" - sendNotificationToUser - fromUserValue="+fromUserValue.toString());
			//log.debug("TrackingAction - "+pTrackingForm.getNovelUserId()+" - sendNotificationToUser - **************************************************************");

			//Only send email if email address is not empty
			if (userEmail != null && !userEmail.equals("")){
				
				//Create a Receipient hash map
				HashMap iaSendTo= new HashMap();
				
				EmailService emailService = (EmailService) ComponentManager.get(EmailService.class);
				//String tmpEmailFrom = ServerConfigurationService.getString("noReplyEmailFrom");
				String tmpEmailFrom = "assign@unisa.ac.za";
				InternetAddress toEmail = new InternetAddress(userEmail);
				InternetAddress iaTo[] = new InternetAddress[1];
				iaTo[0] = toEmail;
				InternetAddress iaHeaderTo[] = new InternetAddress[1];
				iaHeaderTo[0] = toEmail;
				iaSendTo.put(RecipientType.TO,toEmail);
				InternetAddress iaReplyTo[] = new InternetAddress[1];
				iaReplyTo[0] = new InternetAddress(tmpEmailFrom);
				List<String> contentList = new ArrayList<String>();
				contentList.add("Content-Type: text/html"); 
				
				//log.debug("TrackingAction - "+pTrackingForm.getNovelUserId()+" - sendNotificationToUser - **************************************************************");
				//log.debug("TrackingAction - "+pTrackingForm.getNovelUserId()+" - sendNotificationToUser - PDF Attachment");
				String pdfPath = getServlet().getServletContext().getInitParameter("applicationPDFFullPath");
				String pdfFileName = pTrackingForm.getReportPDFToDownload();
				
				//log.debug("TrackingAction - "+pTrackingForm.getNovelUserId()+" - sendNotificationToUser - PDF File="+pdfPath+"/"+pdfFileName);
				//log.debug("TrackingAction - "+pTrackingForm.getNovelUserId()+" - sendNotificationToUser - **************************************************************");
				
				/* Email to student informing him/her of re-assignment*/
				String subject = "Unisa Assignment Hardcopy Tracking ("+form.getDisplayShipListNumber()+")";
				String body = "<html> "+
			    "<body>Dear "+userValue.toString()+"," + "<br/><br/>"+
			    "The Consignment List (<b>"+form.getDisplayShipListNumber()+"</b>) was booked OUT to you on "+form.getDisplayShipListDate()+" for your attention.<br/><br/>" +
			    "Booked Out by: "+fromUserValue.toString()+".<br/><br/>" +			
				"Please visit myUnisa (https://my.unisa.ac.za) to access the consignment list via Course Admin or function (F856) on the Student System.<br/><br/> " +
			    "Should you have any further queries please contact:<br/>" +
				"  The Directorate: Student Assessment Administration (DSAA)<br/>" +
			    "  Email: <strong><a href='mailto:assign@unisa.ac.za'>assign@unisa.ac.za</a></strong><br/><br/>" +
				"Regards<br/>"+
				"Student Assessment Administration Team" +
				"</body>"+
				"</html>";

				//toEmail = "twilsce@unisa.ac.za";
				//log.debug("TrackingAction - "+pTrackingForm.getNovelUserId()+" - sendNotificationToUser - **************************************************************");
				//log.debug("TrackingAction - "+pTrackingForm.getNovelUserId()+" - sendNotificationToUser - Sending mail from="+iaReplyTo[0].toString());
				//log.debug("TrackingAction - "+pTrackingForm.getNovelUserId()+" - sendNotificationToUser - Sending mail To="+toEmail.toString() + " - userEmail="+userEmail);
				//log.debug("TrackingAction - "+pTrackingForm.getNovelUserId()+" - sendNotificationToUser - Sending mail tmpEmailFrom="+tmpEmailFrom.toString());
				//log.debug("TrackingAction - "+pTrackingForm.getNovelUserId()+" - sendNotificationToUser - Sending mail Subject="+subject.toString());
				//log.debug("TrackingAction - "+pTrackingForm.getNovelUserId()+" - sendNotificationToUser - Sending mail Body="+body.toString());
				//log.debug("TrackingAction - "+pTrackingForm.getNovelUserId()+" - sendNotificationToUser - Sending mail ContentList="+contentList.toString());
				//log.debug("TrackingAction - "+pTrackingForm.getNovelUserId()+" - sendNotificationToUser - **************************************************************");

				//log.debug("TrackingAction - "+pTrackingForm.getNovelUserId()+" - sendNotificationToUser - Before Sending Email");
				try{
					emailService.sendMail(iaReplyTo[0],iaTo,subject,body,iaHeaderTo,iaReplyTo,contentList); //Send with no attachment
					
					List<Attachment> attachmentList;
					File fileObject = new File(pdfPath);
					attachmentList = new ArrayList<Attachment>();
					Attachment fileList = new Attachment(fileObject, pdfFileName);
					attachmentList.add(fileList);
							 
					//emailService.sendMail(iaReplyTo[0], iaTo, subject, body, iaSendTo, iaReplyTo, contentList, attachmentList); //Send with PDF attachment
					 
				}catch (Exception e){
					// Don't fail if email could not be send
					//log.debug("TrackingAction - "+pTrackingForm.getNovelUserId()+" - sendNotificationToUser - Email failed: "+e+" / "+e.getMessage());
	        	}
				//log.debug("TrackingAction - "+pTrackingForm.getNovelUserId()+" - sendNotificationToUser - After Sending Email");
				//log.debug("TrackingAction - "+pTrackingForm.getNovelUserId()+" - sendNotificationToUser - **************************************************************");

				//Future Log Email sent
				//EmailLogRecord log = new EmailLogRecord();
				//	log.setRecipient(userValue);
				//	log.setRecipientType("TUTOR");
				//	log.setEmailAddress(toAddress);
				//	log.setProgram("TRACKING:MYUNISA");
				//	log.setSubject(subject);
				//	log.setEmailType("BOOKOUT");
				//	log.setBody(body);
				//insertEmailLog(log);
			}else{
				result = "No email sent as Destination email address could not be found or destination is a Province, College or Building etc";
			}
		} catch (Exception e) {
			result = "Email Failed; "+e+" "+e.getMessage();
		}	
		//log.debug("TrackingAction - "+pTrackingForm.getNovelUserId()+" - sendNotificationToUser - **************************************************************");
		return result;
	}

	public void resetForm(TrackingForm form, String method) throws Exception {
		
		//log.debug("TrackingAction - "+form.getNovelUserId()+" - resetForm - *******************************************************");
		//log.debug("TrackingAction - "+form.getNovelUserId()+" - resetForm - Calling Method="+method);
		//log.debug("TrackingAction - "+form.getNovelUserId()+" - resetForm - *******************************************************");
		//log.debug("TrackingAction - "+form.getNovelUserId()+" - resetForm - Local Lists");
		// Clear fields
		form.getMasterConsignmentList().clear();
		form.getMasterDocketList().clear();
		form.getMapUnqAssignments().clear();
		form.getDisplayDockets().clear();
		form.getDisplayDctAssignments().clear();
		form.getDisplayUniqueNumbers().clear();
		form.getProcessDocketList().clear();
		form.getProcessDocketAssignmentList().clear();
		form.getProcessAssignmentList().clear();
		
		form.setConListCount(0);
		form.setDocCount(0);
		form.setUniqueAssCount(0);
	
		//log.debug("TrackingAction - "+form.getNovelUserId()+" - resetForm - Form Variables");
		
		form.setDisplayDocketsForConsignment(null);
		form.setDisplayDctAssignmentsForConsignment(null);
		form.setDisplayUniqueNumbersForConsignment(null);
		form.setDisplayShipListNumber("");
		form.setDisplayShipListDate("");
		form.setDisplayAddress1("");
		form.setDisplayAddress2("");
		form.setDisplayAddress3("");
		form.setDisplayAddress4("");
		form.setDisplayPostal("");
		
		form.setEnteredConsignmentNumber("");
		form.setConsignmentListNumber("");
		form.setAddressType("");
		form.setSearchString("");
		form.setSearchRadio("");
		
		form.setSaveAddress1("");
		form.setSaveAddress2("");
		form.setSaveAddress3("");
		form.setSaveAddress4("");
		form.setSaveAddress5("");
		form.setSavePostal("");
		//log.debug("TrackingAction - "+form.getNovelUserId()+" - resetForm - *******************************************************");
	}
	
	public void resetLocal(TrackingForm form, String method) throws Exception {
		
		//log.debug("TrackingAction - "+form.getNovelUserId()+" - resetLocal - *******************************************************");
		//log.debug("TrackingAction - "+form.getNovelUserId()+" - resetLocal - Calling Method="+method);
		//log.debug("TrackingAction - "+form.getNovelUserId()+" - resetLocal - *******************************************************");
		//log.debug("TrackingAction - "+form.getNovelUserId()+" - resetLocal - Local Lists");
		// Clear fields
		form.setEnteredConsignmentNumber("");
		form.getMasterConsignmentList().clear();
		form.getMasterDocketList().clear();
		form.getMapUnqAssignments().clear();
		form.getDisplayDockets().clear();
		form.getDisplayDctAssignments().clear();
		form.getDisplayUniqueNumbers().clear();
		form.getProcessDocketList().clear();
		form.getProcessDocketAssignmentList().clear();
		form.getProcessAssignmentList().clear();
		//log.debug("TrackingAction - "+form.getNovelUserId()+" - resetLocal - *******************************************************");
	}

	public Boolean isNumberValid(String numVal) {
		Pattern regexPattern = Pattern.compile("^[0-9]*$");
		Matcher regMatcher   = regexPattern.matcher(numVal);
	    //if (regMatcher.find()) {
	    if(regMatcher.matches()){
	        return true;
	    } else {
	    	return false;
	    }
	}
	
	public String validateEmail(TrackingForm form){
     	String emailAdrress=form.getUserEmail();
       if( emailAdrress== null || "".equals(emailAdrress.trim())){
             return  "Recipient E-mail address may not be empty when entering a manual address.";
       }
       EmailValidator  emValidator=new EmailValidator();
       boolean valid = emValidator.validate(emailAdrress);
       if(!valid){			
           return "E-mail address is invalid. Please enter a valid E-mail address.";
       }else{
           form.setUserEmail(emailAdrress);
       }
       return "";
}
 	
	  public void getAllRequestParamaters(HttpServletRequest req, HttpServletResponse res){ 
		  //log.debug("TrackingAction - getAllRequestParamaters");
		    Enumeration<String> parameterNames = req.getParameterNames(); 
		    while (parameterNames.hasMoreElements()) { 
		        String paramName = parameterNames.nextElement(); 
		        //log.debug("param: " + paramName); 

		        String[] paramValues = req.getParameterValues(paramName); 
		        for (int i = 0; i < paramValues.length; i++) { 
		            String paramValue = paramValues[i]; 
		            //log.debug("value: " + paramValue); 
		        } 
		    } 
		}
	  
	  public boolean checkInputValid(String callInput, String inText){
		  //log.debug("TrackingAction - checkInputValid - callInput - inText="+inText);
		  boolean result = true;
		  Pattern pattern = Pattern.compile("^[A-Za-z0-9-.()]++$");
		  if (pattern.matcher(inText).matches()) {
			  //log.debug("TrackingAction - checkInputValid - callInput - Pattern Matched");
			  result = true;
		  }else{
			  //log.debug("TrackingAction - checkInputValid - callInput - Pattern Not Matched");
			  result = false;
		  }
		  //log.debug("TrackingAction - checkInputValid - callInput - Result="+result);
		  return result;
		  // !#$%&'*+/=?`{|}~^
	  }
	  
	  public boolean checkEmailValid(String inEmail){
		  // Allow underscore
		  // ^[_A-Za-z0-9-']+(\\.[_A-Za-z0-9-']+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*(\\.[A-Za-z]{2,})$
		  // A-Za-z0-9 = \\w
		  //log.debug("TrackingAction - checkEmailValid - inEmail="+inEmail);
		  boolean result = true;
		  Pattern pattern = Pattern.compile("^[A-Za-z0-9-\\+]+(\\.[A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$");
		  if (pattern.matcher(inEmail).matches()) {
			  //log.debug("TrackingAction - checkEmailValid - Pattern Matched");
			  result = true;
		  }else{
			  //log.debug("TrackingAction - checkEmailValid - Pattern Not Matched");
			  result = false;
		  }
		  //log.debug("TrackingAction - checkEmailValid - Result="+result);
		  return result;
	  }
	  
}
