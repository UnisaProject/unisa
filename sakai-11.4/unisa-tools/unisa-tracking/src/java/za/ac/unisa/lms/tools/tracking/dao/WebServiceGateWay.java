package za.ac.unisa.lms.tools.tracking.dao;
import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Iterator;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class WebServiceGateWay {
	
	public String backEndSessionCookieName = "";
	public String backEndSessionCookieValue = "";
	
	public String getBackEndSessionCookieName() {
		return backEndSessionCookieName;
	}
	public void setBackEndSessionCookieName(String backEndSessionCookieName) {
		this.backEndSessionCookieName = backEndSessionCookieName;
	}
	public String getBackEndSessionCookieValue() {
		return backEndSessionCookieValue;
	}
	public void setBackEndSessionCookieValue(String backEndSessionCookieValue) {
		this.backEndSessionCookieValue = backEndSessionCookieValue;
	}
	public void setRequestCookie(URLConnection uc) {
		 uc.setRequestProperty("Cookie", this.getBackEndSessionCookieName()+"="+this.getBackEndSessionCookieValue());
	}
	
  //private String webServiceURL = "http://www2dev.unisa.ac.za/aol/asp/sql_exec_report4.asp?export=XML&myid=CDTRACKING&ID=";
	private Log log = LogFactory.getLog(WebServiceGateWay.class.getName());

	public ArrayList<KeyValue> getCollegeList(String webServiceURL ,int id , String value1, String value2 ) throws Exception {		
		return openWebService(webServiceURL+id ,value1, value2);
	}
	public ArrayList<KeyValue> getSchoolList1(String webServiceURL ,int id , String value1, String value2 , int collegeCode ) throws Exception {		
		return openWebService(webServiceURL+id+"&CollegeCode="+collegeCode ,value1, value2);
	}
	public ArrayList<KeyValue> getDepartmentList1(String webServiceURL ,int id , String value1, String value2 , int collegeCode , int schoolCode) throws Exception {		
		 return openWebService(webServiceURL+id+"&CollegeCode="+collegeCode+"&SchoolCode="+schoolCode ,value1, value2);
	}
	public ArrayList<KeyValue> getModuleList1(String webServiceURL ,int id , String value1, String value2 , int collegeCode , int schoolCode , int deptCode) throws Exception {		
		return openWebService(webServiceURL+id+"&CollegeCode="+collegeCode+"&SchoolCode="+schoolCode+"&DeptCode="+deptCode ,value1, value2);
	}
	public ArrayList<KeyValue> getBuildingList(String webServiceURL, int id , String value1, String value2, String building ) throws Exception {		
		return openWebServiceWithID(webServiceURL+id+"&Building="+building ,value1, value2);
	}
	public String getPersNo(String webServiceURL ,int id ,String value1,String Novell_User_Id) throws Exception{
		return openWebServiceVal(webServiceURL+id+"&Novell_User_Id="+Novell_User_Id,value1);
	}
	public String[] validateUserStartSession(String webServiceURL ,int id ,String value1,String Novell_User_Id) throws Exception{
		return openWebServiceValStartSession(webServiceURL+id+"&Novell_User_Id="+Novell_User_Id,value1);
	}
	public String validateUser(String webServiceURL ,int id ,String value1,String Novell_User_Id) throws Exception{
		//log.debug("WebServiceGateWay - validateUser - webServiceURL="+webServiceURL+" ID="+id+", Value1="+value1+", Novell_User_Id="+Novell_User_Id);
		return openWebServiceVal(webServiceURL+id+"&Novell_User_Id="+Novell_User_Id,value1);
	}
	public String getDefaultEmail(String webServiceURL ,int id ,String value1) throws Exception{
		return openWebServiceVal(webServiceURL+id,value1);
	}
	public ArrayList<KeyValue> getUserList(String webServiceURL ,int id ,String value1 ,String value2, String value3, String value4 ,String value5 ,String value6 ,String surname) throws Exception{
		return openWebServiceWithIDAddress1(webServiceURL+id+"&surname="+surname,value1,value2,value3,value4,value5,value6);
	}
	public ArrayList<KeyValue> getUserList1(String webServiceURL ,int id ,String value1 ,String value2, String value3, String value4 ,String value5 ,String value6 ,String persNo) throws Exception{
		return openWebServiceWithIDAddress1(webServiceURL+id+"&PersNo="+persNo,value1,value2,value3,value4,value5,value6);
	}
	public ArrayList<KeyValue> getUserList2(String webServiceURL ,int id , String value1 ,String value2, String value3, String value4 ,String value5 ,String value6, String collegeCode, String schoolCode, String departmentCode, String moduleCode  ) throws Exception {		
		return openWebServiceWithIDAddress1( webServiceURL+id+"&CollegeCode="+collegeCode+"&SchoolCode="+schoolCode+"&DeptCode="+departmentCode+"&ModuleCode="+moduleCode,value1,value2,value3,value4,value5,value6);
	}
	public ArrayList<KeyValue> getUserList3(String webServiceURL ,int id , String value1 ,String value2, String value3, String value4 ,String value5 ,String value6, String provinceCode, String RegionCode) throws Exception {		
		return openWebServiceWithIDAddress1( webServiceURL+id+"&ProvinceCode="+provinceCode+"&RegionCode="+RegionCode,value1,value2,value3,value4,value5,value6);
	}
	public ArrayList<KeyValue> getUserList4(String webServiceURL ,int id , String value1 ,String value2, String value3, String value4 ,String value5 ,String value6, String buildingName  ) throws Exception {		
		return openWebServiceWithIDAddress1( webServiceURL+id+"&BuildingName="+buildingName ,value1,value2,value3,value4,value5,value6);
	}
	public ArrayList<KeyValue> getUserList5(String webServiceURL ,int id ,String value1 ,String value2, String value3, String value4 ,String value5 ,String value6 ,String Novell_User_Id) throws Exception{
		return openWebServiceWithIDAddress1(webServiceURL+id+"&NovellUserCode="+Novell_User_Id,value1,value2,value3,value4,value5,value6);
	}
	public ArrayList<KeyValue> getProvinceList(String webServiceURL ,int id ,String value1, String value2) throws Exception {		
		return openWebService( webServiceURL+id, value1, value2);
	}
	public ArrayList<KeyValue> getRegionalOfficeList(String webServiceURL ,int id , String value1, String value2 , int provinceCode ) throws Exception {		
		return openWebService( webServiceURL+id+"&hub="+provinceCode,value1, value2);
	}
	public ArrayList<KeyValue> getSearch(String webServiceURL, int id, String searchString, String value1, String value2) throws Exception {		
		return openWebService( webServiceURL+id+"&searchString='"+searchString+"'",value1, value2);
	}
	public String docketNumberValidationCheck(String webServiceURL ,int id , int docketNumber) throws Exception{
		return getDocketNumberStatus( webServiceURL+id+"&DocketNr="+docketNumber);
	}
	public String shipListCreationCheck(String webServiceURL ,int id , String surname) throws Exception{
		return getDocketNumberStatus( webServiceURL+id+"&FromUser="+surname);
	}
	public String studentAssignNumberValidation(String webServiceURL ,int id , String studentNumber , String uniqueAssignNumber) throws Exception{
		return getDocketNumberStatus( webServiceURL+id+"&StudentNo="+studentNumber+"&UniqueNo="+uniqueAssignNumber);
	}
	public ArrayList<KeyValue> CreateConsignmentListNumber(String webServiceURL ,int id , String novelUserCode ,String value1 , String value2) throws Exception{
		return openWebServiceWithID(webServiceURL+id+"&FromUser="+novelUserCode,value1, value2);
	}
	public String addDocketNumbertoShipList(String webServiceURL ,int id ,String networkCode,String assignStatus, String shipListNumber ,String docketNumber ,String destination) throws Exception {
		 return getDocketNumberStatus( webServiceURL+id+"&CoverDocketNr="+docketNumber+"&FromNetworkCode="+networkCode+"&AssignStatus="+assignStatus+"&ShippingListNr="+shipListNumber+"&Destination="+"CollegeCode:"+destination);
	}
	 public ArrayList<KeyValue> showConsignmentListInfo(String webServiceURL ,int id ,String consignmentListNumber ,String status ,String value1 ,String value2) throws Exception{
		 return openWebServiceWithID2(webServiceURL+id+"&ShipNo="+consignmentListNumber+"&Status="+status ,value1 ,value2);
	 }
	 public ArrayList<KeyValue> showConsignmentInfo(String webServiceURL ,int id ,String consignmentListNumber ,String status ,String value1 ,String value2,String value3) throws Exception{
		 return openWebServiceWithIDValues(webServiceURL+id+"&ShipNo="+consignmentListNumber+"&Status="+status ,value1 ,value2,value3);
	 }

	 public ArrayList<KeyValue> getDocketAssignments(String webServiceURL ,int id ,String docketNumber,String value1 ,String value2) throws Exception{
		return openWebService(webServiceURL+id+"&InDctNr="+docketNumber ,value1, value2);
	 }
	 public ArrayList<KeyValue> showUniqueAssignmentListInfo(String webServiceURL ,int id ,String consignmentListNumber ,String status ,String value1 ,String value2,String value3) throws Exception{
		 return openWebServiceAssignment(webServiceURL+id+"&ShipNo="+consignmentListNumber+"&Status="+status ,value1 ,value2);
	 }
	 public ArrayList<KeyValue> getSelectedUserAddress(String webServiceURL ,int id ,String staffNumber , String value1 ,String value2, String value3,String value4 ,String value5 ,String value6 ,String destinationType) throws Exception{
		 return openWebServiceWithIDAddress1(webServiceURL+id+"&Destination="+staffNumber+"&DestinationType="+destinationType ,value1 ,value2 ,value3 ,value4 ,value5 ,value6);
	 }
	 public ArrayList<KeyValue> getSavedUserAddress(String webServiceURL ,int id ,String novellUserID , String value1 ,String value2, String value3,String value4 ,String value5 ,String value6 ,String value7 ,String value8) throws Exception{
		 return openWebServiceSavedAddress2(webServiceURL+id+"&FromUser="+novellUserID ,value1 ,value2 ,value3 ,value4 ,value5 ,value6 ,value7 ,value8);
	 }
	 public String setChangedAddress(String webServiceURL ,int id , String fromUser , String addr2, String addr3, String addr4, String addr5, String ShipNo , String addrPostal, String value1 , String value2) throws Exception {
		 return openWebServiceSaveAddress(webServiceURL+id+"&FromUser="+fromUser+"&Adr2="+addr2+"&Adr3="+addr3+"&Adr4="+addr4+"&Adr5="+addr5+"&ShipNo="+ShipNo+"&PostalCode="+addrPostal, value1, value2); 
	 }
	 
	 private String[] openWebServiceValStartSession(String req_webServiceURL , String value1) throws Exception  {
		 String returnValues[] = new String[2];
		 ArrayList<KeyValue> keyvalues = new ArrayList<KeyValue>();
		 String noWhiteSpaceURL = req_webServiceURL.replaceAll(" ", "\\%20");
		 log.debug("WebServiceGateWay - openWebServiceValStartSession - Requested URL : " + noWhiteSpaceURL);
		 log.debug("Looking for cookie named "+getBackEndSessionCookieName());
		 log.debug("Current cookie value stored in tomcat session is "+getBackEndSessionCookieValue());
		 
	     URL webServiceURL = new URL(noWhiteSpaceURL);
		 DocumentBuilderFactory docBuilderFactory = DocumentBuilderFactory.newInstance();
		 DocumentBuilder docBuilder = docBuilderFactory.newDocumentBuilder();
		 URLConnection urlConn = webServiceURL.openConnection();
		 String backEndCookieSetting = "";
		 Iterator<String> cookieIterator = urlConn.getHeaderFields().get("Set-Cookie").iterator();
		 while(cookieIterator.hasNext()) {
			 backEndCookieSetting = cookieIterator.next();
			 log.debug("Set-Cookie: "+backEndCookieSetting);
			 if(backEndCookieSetting.toLowerCase().startsWith(this.backEndSessionCookieName.toLowerCase())) {
				 String cookieValuePair = backEndCookieSetting.substring(0,backEndCookieSetting.indexOf(';'));
				 returnValues[0] = cookieValuePair.substring(cookieValuePair.indexOf('=')+1,cookieValuePair.length());
			 }
		 }
		 if(returnValues[0] != null) {
			 log.debug("new Cookie value: "+returnValues[0]);
			 //urlConn.setRequestProperty("Cookie", getBackEndSessionCookieName()+"="+returnValues[0]);
		 }
		 try {
		 Document doc = docBuilder.parse(urlConn.getInputStream());
		 NodeList listOfRecords = doc.getElementsByTagName("record");
		
		 if(listOfRecords.getLength()!=0){          
             
			 for (int s = 0; s < listOfRecords.getLength(); s++) {
				 //log.debug("WebServiceGateWay - openWebServiceVal - listOfRecords="+ listOfRecords.getLength());
                    Node firstDocTypeNode = listOfRecords.item(s);
                         if (firstDocTypeNode.getNodeType() == Node.ELEMENT_NODE) {
                        	 KeyValue test = new KeyValue();
                        	 Element firstElement = (Element) firstDocTypeNode;
                        	 NodeList valueOneE = firstElement.getElementsByTagName(value1);
                             Element valueOne = (Element)valueOneE.item(0);
                             NodeList valueOneList = valueOne.getChildNodes();
                            
                             try{
                            	 returnValues[1] = valueOneList.item(0).getNodeValue();
                            	 //log.debug("WebServiceGateWay - openWebServiceVal - keyValue="+ valueOneList.item(0).getNodeValue());
                             }catch(Exception e){
                            	 e.printStackTrace();
                             }
                         }
                      }
                  }
		 } catch(org.xml.sax.SAXParseException spe) {
			 log.debug(urlConn.getInputStream());
			 InputStream inputStream = urlConn.getInputStream();
			 String text = org.apache.commons.io.IOUtils.toString(inputStream, StandardCharsets.UTF_8.name());
			 log.error(text);
			 spe.printStackTrace();
		 } catch(Exception e) {
			 e.printStackTrace();
		 }
         return returnValues;
	 }

	 private String openWebServiceVal(String req_webServiceURL , String value1) throws Exception  {
		 String keyValue = "";
		 ArrayList<KeyValue> keyvalues = new ArrayList<KeyValue>();
		 String noWhiteSpaceURL = req_webServiceURL.replaceAll(" ", "\\%20");
		 log.debug("WebServiceGateWay - openWebServiceVal - Requested URL : " + noWhiteSpaceURL);
	     URL webServiceURL = new URL(noWhiteSpaceURL);
		 DocumentBuilderFactory docBuilderFactory = DocumentBuilderFactory.newInstance();
		 DocumentBuilder docBuilder = docBuilderFactory.newDocumentBuilder();
		 URLConnection urlConn = webServiceURL.openConnection();
		 setRequestCookie(urlConn);
		 log.debug("calling gateway with request property "+urlConn.getRequestProperty("Cookie"));
		 Document doc = docBuilder.parse(urlConn.getInputStream());
		 NodeList listOfRecords = doc.getElementsByTagName("record");
		
		 if(listOfRecords.getLength()!=0){          
             
			 for (int s = 0; s < listOfRecords.getLength(); s++) {
				 //log.debug("WebServiceGateWay - openWebServiceVal - listOfRecords="+ listOfRecords.getLength());
                    Node firstDocTypeNode = listOfRecords.item(s);
                         if (firstDocTypeNode.getNodeType() == Node.ELEMENT_NODE) {
                        	 KeyValue test = new KeyValue();
                        	 Element firstElement = (Element) firstDocTypeNode;
                        	 NodeList valueOneE = firstElement.getElementsByTagName(value1);
                             Element valueOne = (Element)valueOneE.item(0);
                             NodeList valueOneList = valueOne.getChildNodes();
                            
                             try{
                            	 keyValue = valueOneList.item(0).getNodeValue();
                            	 //log.debug("WebServiceGateWay - openWebServiceVal - keyValue="+ valueOneList.item(0).getNodeValue());
                             }catch(Exception e){
                            	 e.printStackTrace();
                             }
                         }
                      }
                  }
         return keyValue;
	 }
	 
	 private ArrayList<KeyValue> openWebService(String req_webServiceURL , String value1, String value2) throws Exception  {
		 ArrayList<KeyValue> keyvalues = new ArrayList<KeyValue>();
		 String noWhiteSpaceURL = req_webServiceURL.replaceAll(" ", "\\%20");
		 //log.debug("WebServiceGateWay - openWebService - Requested URL : " + noWhiteSpaceURL);
	     URL webServiceURL = new URL(noWhiteSpaceURL);
	     URLConnection urlConn = webServiceURL.openConnection();
	     setRequestCookie(urlConn);
		 DocumentBuilderFactory docBuilderFactory = DocumentBuilderFactory.newInstance();
		 DocumentBuilder docBuilder = docBuilderFactory.newDocumentBuilder();
		 Document doc = docBuilder.parse(urlConn.getInputStream());
		 NodeList listOfRecords = doc.getElementsByTagName("record");
		
		 if(listOfRecords.getLength()!=0){          
             
			 for (int s = 0; s < listOfRecords.getLength(); s++) {
				 //log.debug("WebServiceGateWay - openWebService - listOfRecords="+ listOfRecords.getLength());
                    Node firstDocTypeNode = listOfRecords.item(s);
                         if (firstDocTypeNode.getNodeType() == Node.ELEMENT_NODE) {
                        	 KeyValue test = new KeyValue();
                        	 Element firstElement = (Element) firstDocTypeNode;
                        	 NodeList valueOneE = firstElement.getElementsByTagName(value1);
                             Element valueOne = (Element)valueOneE.item(0);
                             NodeList valueOneList = valueOne.getChildNodes();
                            
                             try{
                            	 
                            	 test.setKey(valueOneList.item(0).getNodeValue());
                            	 //log.debug("WebServiceGateWay - openWebService - Key="+ valueOneList.item(0).getNodeValue());
                              }catch(Exception e){
                                   e.printStackTrace();
                             }
                             NodeList valueTwoE = firstElement.getElementsByTagName(value2);
                             Element  valueTwo= (Element) valueTwoE.item(0);
                             NodeList valueTwoList = valueTwo.getChildNodes();    
                                   
                                    try{
                                    	   
                                    	test.setValue(valueTwoList.item(0).getNodeValue().trim());
                                    	//log.debug("WebServiceGateWay - openWebService - Value="+ valueTwoList.item(0).getNodeValue().trim());
                                     }catch(Exception e){
                                          e.printStackTrace();
                                    }
                                    keyvalues.add(test);
                         }
                      }
                  }
         return keyvalues;
	 }
	
	 private ArrayList<KeyValue> openWebServiceWithID(String req_webServiceURL,String value1,String value2) throws Exception  {

		 	//log.debug("WebServiceGateWay - openWebServiceWithID - Requested URL : " + req_webServiceURL);
		 	 ArrayList<KeyValue> keyvalues = new ArrayList<KeyValue>();
			 URL webServiceURL = new URL(req_webServiceURL);
			 URLConnection urlConn = webServiceURL.openConnection();
			 setRequestCookie(urlConn);
			 DocumentBuilderFactory docBuilderFactory = DocumentBuilderFactory.newInstance();
			 DocumentBuilder docBuilder = docBuilderFactory.newDocumentBuilder();
			 Document doc = docBuilder.parse(urlConn.getInputStream());
		  	 NodeList listOfRecords = doc.getElementsByTagName("record");
			     if(listOfRecords.getLength()!=0){
	            //log.debug("one1 ");
	                for (int s = 0; s < listOfRecords.getLength(); s++) {
	                	 KeyValue test = new KeyValue();
	                	 //log.debug("one2 ");
	                	 Node firstDocTypeNode = listOfRecords.item(s);
	                     if (firstDocTypeNode.getNodeType() == Node.ELEMENT_NODE) {
	                    	 //log.debug("one3 ");
	                        	
	                    	 Element firstElement = (Element) firstDocTypeNode;
	                    	 if(firstElement.hasAttributes()) {
	                    		 //log.debug("one4 ");  
	                    		 NamedNodeMap map = firstElement.getAttributes();  		  
	                    		 for(int i =0 ; i < map.getLength() ; i++){		
	                    			 //log.debug("one5 ");
	                    			 test.setKey(map.item(i).getNodeValue());
	                    		 }
	                    	 }
	                       	 NodeList valueOneE = firstElement.getElementsByTagName(value2);
	                         Element valueOne = (Element)valueOneE.item(0);
	                         NodeList valueOneList = valueOne.getChildNodes();
	                               
	                         try{
	                        	 //log.debug("one6 ");     
	                        	 test.setValue(valueOneList.item(0).getNodeValue().trim());
	                         }catch(Exception e){
	                        	 //log.debug("exception "+e.getMessage());
	                        	 value1 = " ";
	                         }
	                         keyvalues.add(test);  
	                     }
	                }
			     }else {
	        	 //log.debug("WebServiceGateWay - openWebServiceWithID : "+req_webServiceURL);	
	        	 String docketNumberStatus = doc.getDocumentElement().getTextContent() ;
			    	 KeyValue test = new KeyValue();
			    	 //log.debug("WebServiceGateWay - openWebServiceWithID - status: " + docketNumberStatus);
			    	if(docketNumberStatus.contains("FromUser invalid")){
			    		test.setKey("2");
				    	test.setValue(docketNumberStatus);
			    	}
			    	else {
			    	test.setKey("2");
			    	test.setValue(docketNumberStatus);
			    	 
			    	}
			    	keyvalues.add(test);
			     }
			     return keyvalues;
		 }
	 
	 @SuppressWarnings("unused")
	private ArrayList<KeyValue> openWebServiceWithID1(String req_webServiceURL,String value1,String value2) throws Exception  {

		 	//log.debug("WebServiceGateWay - openWebServiceWithID1 - ConsignmentList number check URL: "+req_webServiceURL);
			 ArrayList<KeyValue> keyvalues = new ArrayList<KeyValue>();
			 URL webServiceURL = new URL(req_webServiceURL);
			 URLConnection urlConn = webServiceURL.openConnection();
			 setRequestCookie(urlConn);
			 DocumentBuilderFactory docBuilderFactory = DocumentBuilderFactory.newInstance();
			 DocumentBuilder docBuilder = docBuilderFactory.newDocumentBuilder();
			 Document doc = docBuilder.parse(urlConn.getInputStream());
		  	 NodeList listOfRecords = doc.getElementsByTagName("record");
			     if(listOfRecords.getLength()!=0){
	            //log.debug("one1 ");
	                for (int s = 0; s < listOfRecords.getLength(); s++) {
	                	 KeyValue test = new KeyValue();
	                	//log.debug("one2 ");
	                  Node firstDocTypeNode = listOfRecords.item(s);
	                        if (firstDocTypeNode.getNodeType() == Node.ELEMENT_NODE) {
	                        	//log.debug("one3 ");
	                        	
	                        	Element firstElement = (Element) firstDocTypeNode;
	                            if(firstElement.hasAttributes()) {
	                            	 //log.debug("one4 ");  
	                            	NamedNodeMap map = firstElement.getAttributes();  		  
				                        for(int i =0 ; i < map.getLength() ; i++){		
				                        	 //log.debug("one5 ");
				                        	test.setKey(map.item(i).getNodeValue());
				                      }
	                            }
	                       	 NodeList valueOneE = firstElement.getElementsByTagName(value2);
	                                Element valueOne = (Element)valueOneE.item(0);
	                                NodeList valueOneList = valueOne.getChildNodes();
	                               
	                                try{
	                                	  //log.debug("one6 ");     
	                                	test.setValue(valueOneList.item(0).getNodeValue().trim());
	                                         }catch(Exception e){
	                                           //log.debug("exception "+e.getMessage());
	                                        	 value1 = " ";
	                                }
	                                keyvalues.add(test);  
	                                
	                        }
	                       
	                }
	                
	               
	        }     else {
			    	 String docketNumberStatus = doc.getDocumentElement().getTextContent() ;
			    	 KeyValue test = new KeyValue();
			    	 //log.debug("WebServiceGateWay - openWebServiceWithID1 - status: " + docketNumberStatus);
			    	if(docketNumberStatus.contains("No records returned")){
			    	test.setKey("1");
			    	test.setValue("No records returned");
			    	 
			    	}
			    	else {
			    		test.setKey("1");
				    	test.setValue(docketNumberStatus);
			    	}
			    	keyvalues.add(test);
			     }
			     return keyvalues;
		 }
	 private ArrayList<KeyValue> openWebServiceWithIDValues(String req_webServiceURL,String value1,String value2,String value3) throws Exception  {

		 //log.debug("WebServiceGateWay - openWebServiceWithIDValues - Requested URL: "+req_webServiceURL);
	     ArrayList<KeyValue> keyvalues = new ArrayList<KeyValue>();
		 URL webServiceURL = new URL(req_webServiceURL);
		 URLConnection urlConn = webServiceURL.openConnection();
		 setRequestCookie(urlConn);
		 DocumentBuilderFactory docBuilderFactory = DocumentBuilderFactory.newInstance();
		 DocumentBuilder docBuilder = docBuilderFactory.newDocumentBuilder();
		 Document doc = docBuilder.parse(urlConn.getInputStream());
	  	 NodeList listOfRecords = doc.getElementsByTagName("record");
		     if(listOfRecords.getLength()!=0){
         
                for (int s = 0; s < listOfRecords.getLength(); s++) {
                  Node firstDocTypeNode = listOfRecords.item(s);
                        if (firstDocTypeNode.getNodeType() == Node.ELEMENT_NODE) {
                        	 KeyValue test = new KeyValue();
                        	Element firstElement = (Element) firstDocTypeNode;
                            if(firstElement.hasAttributes()) {
                           		  NamedNodeMap map = firstElement.getAttributes();  		  
			                      for(int i =0 ; i < map.getLength() ; i++){		
			                    	  //test.setKey(map.item(i).getNodeValue());
			                      }
                            }
                       	 	NodeList valueOneE = firstElement.getElementsByTagName(value1);
                            Element valueOne = (Element)valueOneE.item(0);
                            NodeList valueOneList = valueOne.getChildNodes();
                               
                            try{
                            	test.setValue1(valueOneList.item(0).getNodeValue().trim());
                            }catch(Exception e){
                            	test.setValue1(" ");
                            }
                           	 NodeList valueTwo = firstElement.getElementsByTagName(value2);
                             Element valueTwo1 = (Element)valueTwo.item(0);
                             NodeList valueOneListTwo = valueTwo1.getChildNodes();
                            
                             try{
                            	 test.setValue2(valueOneListTwo.item(0).getNodeValue().trim());
                             }catch(Exception e){
                            	 test.setValue2(" ");
                             }
                             NodeList valueThree = firstElement.getElementsByTagName(value3);
                             Element valueThree1 = (Element)valueThree.item(0);
                             NodeList valueThreeListTwo = valueThree1.getChildNodes();
                            
                             try{
                            	 test.setValue3(valueThreeListTwo.item(0).getNodeValue().trim());
                             }catch(Exception e){
                            	 test.setValue3(" ");
                             }
                                
                                keyvalues.add(test);  
                                
                        }
                       
                }
                
               
        }//-------
		     return keyvalues;
	 }

	 private ArrayList<KeyValue> openWebServiceAssignment(String req_webServiceURL,String value1,String value2) throws Exception  {

		 //log.debug("WebServiceGateWay - openWebServiceAssignment - Requested URL: "+req_webServiceURL);
	     ArrayList<KeyValue> keyvalues = new ArrayList<KeyValue>();
		 URL webServiceURL = new URL(req_webServiceURL);
		 URLConnection urlConn = webServiceURL.openConnection();
		 setRequestCookie(urlConn);
		 DocumentBuilderFactory docBuilderFactory = DocumentBuilderFactory.newInstance();
		 DocumentBuilder docBuilder = docBuilderFactory.newDocumentBuilder();
		 Document doc = docBuilder.parse(urlConn.getInputStream());
	  	 NodeList listOfRecords = doc.getElementsByTagName("record");
		     if(listOfRecords.getLength()!=0){
         
                for (int s = 0; s < listOfRecords.getLength(); s++) {
                  Node firstDocTypeNode = listOfRecords.item(s);
                        if (firstDocTypeNode.getNodeType() == Node.ELEMENT_NODE) {
                        	 KeyValue test = new KeyValue();
                        	Element firstElement = (Element) firstDocTypeNode;
                            if(firstElement.hasAttributes()) {
                           		  NamedNodeMap map = firstElement.getAttributes();  		  
			                      for(int i =0 ; i < map.getLength() ; i++){		
			                    	  //log.debug("WebServiceGateWay - openWebServiceAssignment - Key="+map.item(i).getNodeValue());
			                    	  //test.setKey(map.item(i).getNodeValue());
			                      }
                            }
                       	 	NodeList valueOneE = firstElement.getElementsByTagName(value1);
                            Element valueOne = (Element)valueOneE.item(0);
                            NodeList valueOneList = valueOne.getChildNodes();
                               
                            try{
                            	//log.debug("WebServiceGateWay - openWebServiceAssignment - Value1="+valueOneList.item(0).getNodeValue().trim());
                            	test.setValue1(valueOneList.item(0).getNodeValue().trim());
                            }catch(Exception e){
                            	test.setValue1(" ");
                            }
                           	 NodeList valueTwo = firstElement.getElementsByTagName(value2);
                             Element valueTwo1 = (Element)valueTwo.item(0);
                             NodeList valueOneListTwo = valueTwo1.getChildNodes();
                            
                             try{
                            	 //log.debug("WebServiceGateWay - openWebServiceAssignment - Value2="+valueOneListTwo.item(0).getNodeValue().trim());
                            	 test.setValue2(valueOneListTwo.item(0).getNodeValue().trim());
                             }catch(Exception e){
                            	 test.setValue2(" ");
                             }
                                
                                keyvalues.add(test);  
                                
                        }
                       
                }
                
               
        }//-------
		     return keyvalues;
	 }
	 private ArrayList<KeyValue> openWebServiceWithIDAddress1(String req_webServiceURL,String value1,String value2,String value3 ,String value4 ,String value5 ,String value6) throws Exception  {

		 ArrayList<KeyValue> keyvalues = new ArrayList<KeyValue>();
		 String noWhiteSpaceURL = req_webServiceURL.replaceAll(" ", "\\%20");
		//log.debug("WebServiceGateWay - openWebServiceWithIDAddress1 - Requested URL: "+noWhiteSpaceURL); 
	     URL webServiceURL = new URL(noWhiteSpaceURL);
	     URLConnection urlConn = webServiceURL.openConnection();
	     setRequestCookie(urlConn);
		 DocumentBuilderFactory docBuilderFactory = DocumentBuilderFactory.newInstance();
		 DocumentBuilder docBuilder = docBuilderFactory.newDocumentBuilder();
		 Document doc = docBuilder.parse(urlConn.getInputStream(), "UTF-8");
	  	 NodeList listOfRecords = doc.getElementsByTagName("record");
		     if(listOfRecords.getLength()!=0){
        
		    	 for (int s = 0; s < listOfRecords.getLength(); s++) {
		    		 Node firstDocTypeNode = listOfRecords.item(s);
		    		 if (firstDocTypeNode.getNodeType() == Node.ELEMENT_NODE) {
		    			 KeyValue test = new KeyValue();
		    			 Element firstElement = (Element) firstDocTypeNode;
		    			 if(firstElement.hasAttributes()) {
		    				 NamedNodeMap map = firstElement.getAttributes();  		  
		    				 for(int i =0 ; i < map.getLength() ; i++){		
		    					 test.setKey(map.item(i).getNodeValue());
		    				 }
		    			 }
                      	 
		    			 NodeList valueOneE = firstElement.getElementsByTagName(value1);
		    			 Element valueOne = (Element)valueOneE.item(0);
		    			 NodeList valueOneList = valueOne.getChildNodes();
                              
		    			 try{
		    				 //log.debug("WebServiceGateWay - openWebServiceWithIDAddress1 - Value1="+valueOneList.item(0).getNodeValue().trim());
		    				 test.setValue1(valueOneList.item(0).getNodeValue().trim());
		    			 }catch(Exception e){
		    				 value1 = " ";
		    			 }

		    			 NodeList valueTwo = firstElement.getElementsByTagName(value2);
		    			 Element valueTwo1 = (Element)valueTwo.item(0);
		    			 NodeList valueTwoList = valueTwo1.getChildNodes();
                              
		    			 try{
		    				 //log.debug("WebServiceGateWay - openWebServiceWithIDAddress1 - Value2="+valueTwoList.item(0).getNodeValue().trim());
		    				 test.setValue2(valueTwoList.item(0).getNodeValue().trim());
		    			 }catch(Exception e){
		    				 value2 = " ";
		    			 }
		    			 
		    			 NodeList valueThree = firstElement.getElementsByTagName(value3);
		    			 Element valueThree1 = (Element)valueThree.item(0);
		    			 NodeList valueThreeList = valueThree1.getChildNodes();
		    			 	
		    			 try{
		    				 //log.debug("WebServiceGateWay - openWebServiceWithIDAddress1 - Value3="+valueThreeList.item(0).getNodeValue().trim());
		    				 test.setValue3(valueThreeList.item(0).getNodeValue().trim());
		    			 }catch(Exception e){
		    				 value3 = " ";
		    			 }
		    			 
		    			 NodeList valueFour = firstElement.getElementsByTagName(value4);
		    			 Element valueFour1 = (Element)valueFour.item(0);
		    			 NodeList valueFourList = valueFour1.getChildNodes();
                              
		    			 try{
		    				 //log.debug("WebServiceGateWay - openWebServiceWithIDAddress1 - Value4="+valueFourList.item(0).getNodeValue().trim());
		    				 test.setValue4(valueFourList.item(0).getNodeValue().trim());
		    			 }catch(Exception e){
		    				 value4 = " ";
		    			 }
		    			 
		    			 NodeList valueFive = firstElement.getElementsByTagName(value5);
		    			 Element valueFive1 = (Element)valueFive.item(0);
		    			 NodeList valueFiveList = valueFive1.getChildNodes();
		    			 
		    			 try{
		    				 //log.debug("WebServiceGateWay - openWebServiceWithIDAddress1 - Value5="+valueFiveList.item(0).getNodeValue().trim());
		    				 test.setValue5(valueFiveList.item(0).getNodeValue().trim());
		    			 }catch(Exception e){
		    				 value5 = " ";
		    			 }
		    			 NodeList valueSix = firstElement.getElementsByTagName(value6);
		    			 Element valueSix1 = (Element)valueSix.item(0);
		    			 NodeList valueSixList = valueSix1.getChildNodes();
		    			 
		    			 try{
		    				 //log.debug("WebServiceGateWay - openWebServiceWithIDAddress1 - Value6="+valueSixList.item(0).getNodeValue().trim());
		    				 test.setValue6(valueSixList.item(0).getNodeValue().trim());
		    			 }catch(Exception e){
		    				 value6 = " ";
		    			 }
		    			 //log.debug("WebServiceGateWay - openWebServiceWithIDAddress1 - Done - Adding Values for Return");

		    			 keyvalues.add(test);      
                       }	
		    	 }
		     }//-------
		     return keyvalues;
	 }

	 private ArrayList<KeyValue> openWebServiceSavedAddress2(String req_webServiceURL,String value1,String value2,String value3 ,String value4 ,String value5 ,String value6 ,String value7 ,String value8) throws Exception  {

		 ArrayList<KeyValue> keyvalues = new ArrayList<KeyValue>();
		 String noWhiteSpaceURL = req_webServiceURL.replaceAll(" ", "\\%20");
		//log.debug("WebServiceGateWay - openWebServiceSavedAddress2 - Requested URL: "+noWhiteSpaceURL); 
	     URL webServiceURL = new URL(noWhiteSpaceURL);
	     URLConnection urlConn = webServiceURL.openConnection();
	     setRequestCookie(urlConn);
		 DocumentBuilderFactory docBuilderFactory = DocumentBuilderFactory.newInstance();
		 DocumentBuilder docBuilder = docBuilderFactory.newDocumentBuilder();
		 Document doc = docBuilder.parse(urlConn.getInputStream());
	  	 NodeList listOfRecords = doc.getElementsByTagName("record");
		     if(listOfRecords.getLength()!=0){
        
		    	 for (int s = 0; s < listOfRecords.getLength(); s++) {
		    		 Node firstDocTypeNode = listOfRecords.item(s);
		    		 if (firstDocTypeNode.getNodeType() == Node.ELEMENT_NODE) {
		    			 KeyValue test = new KeyValue();
		    			 Element firstElement = (Element) firstDocTypeNode;
		    			 if(firstElement.hasAttributes()) {
		    				 NamedNodeMap map = firstElement.getAttributes();  		  
		    				 for(int i =0 ; i < map.getLength() ; i++){		
		    					 test.setKey(map.item(i).getNodeValue());
		    				 }
		    			 }
                      	 
		    			 NodeList valueOneE = firstElement.getElementsByTagName(value1);
		    			 Element valueOne = (Element)valueOneE.item(0);
		    			 NodeList valueOneList = valueOne.getChildNodes();
                              
		    			 try{
		    				 test.setValue1(valueOneList.item(0).getNodeValue().trim());
		    			 }catch(Exception e){
		    				 value1 = " ";
		    			 }
		    			 //keyvalues.add(test);  
		    			 NodeList valueTwo = firstElement.getElementsByTagName(value2);
		    			 Element valueTwo1 = (Element)valueTwo.item(0);
		    			 NodeList valueOneListTwo = valueTwo1.getChildNodes();
                              
		    			 try{
		    				 //test.setKey1("2");
		    				 test.setValue2(valueOneListTwo.item(0).getNodeValue().trim());
		    			 }catch(Exception e){
		    				 value2 = " ";
		    			 }
		    			 NodeList valueThree = firstElement.getElementsByTagName(value3);
		    			 Element valueThree1 = (Element)valueThree.item(0);
		    			 NodeList valueOneListThree = valueThree1.getChildNodes();
		    			 	
		    			 try{
		    				 //test.setKey1("2");
		    				 test.setValue3(valueOneListThree.item(0).getNodeValue().trim());
		    			 }catch(Exception e){
		    				 value3 = " ";
		    			 }
		    			 NodeList valueFour = firstElement.getElementsByTagName(value4);
		    			 Element valueFour1 = (Element)valueFour.item(0);
		    			 NodeList valueOneListFour = valueFour1.getChildNodes();
                              
		    			 try{
		    				 //test.setKey1("2");
		    				 test.setValue4(valueOneListFour.item(0).getNodeValue().trim());
		    			 }catch(Exception e){
		    				 value4 = " ";
		    			 }
		    			 NodeList valueFive = firstElement.getElementsByTagName(value5);
		    			 Element valueFive1 = (Element)valueFive.item(0);
		    			 NodeList valueOneListFive = valueFive1.getChildNodes();
		    			 
		    			 try{
		    				 //test.setKey1("2");
		    				 test.setValue5(valueOneListFive.item(0).getNodeValue().trim());
		    			 }catch(Exception e){
		    				 value5 = " ";
		    			 }
		    			 
		    			 NodeList valueSix = firstElement.getElementsByTagName(value6);
		    			 Element valueSix1 = (Element)valueSix.item(0);
		    			 NodeList valueOneListSix = valueSix1.getChildNodes();
		    			 
		    			 try{
		    				 //test.setKey1("2");
		    				 test.setValue6(valueOneListSix.item(0).getNodeValue().trim());
		    			 }catch(Exception e){
		    				 value6 = " ";
		    			 }
		    			 NodeList valueSeven = firstElement.getElementsByTagName(value7);
		    			 Element valueSeven1 = (Element)valueSeven.item(0);
		    			 NodeList valueOneListSeven = valueSeven1.getChildNodes();
		    			 
		    			 try{
		    				 //test.setKey1("2");
		    				 test.setValue7(valueOneListSeven.item(0).getNodeValue().trim());
		    			 }catch(Exception e){
		    				 value7 = " ";
		    			 }
		    			 NodeList valueEight = firstElement.getElementsByTagName(value8);
		    			 Element valueEight1 = (Element)valueEight.item(0);
		    			 NodeList valueOneListEight = valueEight1.getChildNodes();
		    			 
		    			 try{
		    				 //test.setKey1("2");
		    				 test.setValue8(valueOneListEight.item(0).getNodeValue().trim());
		    			 }catch(Exception e){
		    				 value8 = " ";
		    			 }
		    			 keyvalues.add(test);      
                       }	
		    	 }
		     }//-------
		     return keyvalues;
	 }
	 

     private String openWebServiceSaveAddress(String req_webServiceURL,String value1,String value2) throws Exception  {

    	 String resultStatus = null;
		 ArrayList<KeyValue> keyvalues = new ArrayList<KeyValue>();
		 String noWhiteSpaceURL = req_webServiceURL.replaceAll(" ", "\\%20");
		 //log.debug("WebServiceGateWay - openWebServiceSaveAddress - Requested URL: "+noWhiteSpaceURL);
		
		 URL webServiceURL = new URL(noWhiteSpaceURL);
		 URLConnection urlConn = webServiceURL.openConnection();
		 setRequestCookie(urlConn);
		 DocumentBuilderFactory docBuilderFactory = DocumentBuilderFactory.newInstance();
		 DocumentBuilder docBuilder = docBuilderFactory.newDocumentBuilder();
		 Document doc = docBuilder.parse(urlConn.getInputStream());
	  	 NodeList listOfRecords = doc.getElementsByTagName("record");
	  	 
	  	 if(listOfRecords.getLength()!=0){
	  		 for (int s = 0; s < listOfRecords.getLength(); s++) {
	  			 Node firstDocTypeNode = listOfRecords.item(s);
	  			 if (firstDocTypeNode.getNodeType() == Node.ELEMENT_NODE) {
	  				 KeyValue test = new KeyValue();
	  				 Element firstElement = (Element) firstDocTypeNode;
	  				 if(firstElement.hasAttributes()) {
	  					 NamedNodeMap map = firstElement.getAttributes();  		  
	  					 for(int i =0 ; i < map.getLength() ; i++){		
	  						 test.setKey(map.item(i).getNodeValue());
	  					 }
	  				 }
	  				 
	  				 NodeList valueOneE = firstElement.getElementsByTagName(value1);
	  				 Element valueOne = (Element)valueOneE.item(0);
	  				 NodeList valueOneList = valueOne.getChildNodes();
                              
	  				 try{
	  					 resultStatus  = valueOneList.item(0).getNodeValue().trim() ;      
	  					 test.setValue(valueOneList.item(0).getNodeValue().trim());
	  				 }catch(Exception e){
	  					 value1 = " ";
	  				 }
	  				 keyvalues.add(test);            
	  			 }
               }
	  	 }//-------
		 return resultStatus;
     }
	 
      private String getDocketNumberStatus(String req_webServiceURL) throws Exception {
    	  URL webServiceURL = new URL(req_webServiceURL);
    	  URLConnection urlConn = webServiceURL.openConnection();
    	  setRequestCookie(urlConn);
 		 //log.debug("WebServiceGateWay - getDocketNumberStatus - Docketnumber Valid check URL: "+req_webServiceURL);
 		 DocumentBuilderFactory docBuilderFactory = DocumentBuilderFactory.newInstance();
 		 DocumentBuilder docBuilder = docBuilderFactory.newDocumentBuilder();
 		 Document doc = docBuilder.parse(urlConn.getInputStream());
 	     String docketNumberStatus = doc.getDocumentElement().getTextContent() ;
 	     return docketNumberStatus ;
      }
      
    
      @SuppressWarnings("unused")
	public String responseFromWebService(String address,String urlParameters){
    	  int successFound = 0;
    	  int errorsFound = 0;
    	  String errorStatus = null ;
    	  
    	  try{ 
    		  	//log.debug("WebServiceGateWay - responseFromWebService - Requested URL: "+address);
    		  	//log.debug("WebServiceGateWay - responseFromWebService - urlParameters: "+urlParameters);
    		    //log.debug("WebServiceGateWay - responseFromWebService - Requested URL: *["+address+"]*");
    		    //log.debug("WebServiceGateWay - responseFromWebService - Requested urlParameters: *["+urlParameters+"]*");
    		    //FilterInputStream 
    		    
    		    //log.debug("WebServiceGateWay - responseFromWebService - Cleaned address: *["+address+"]*");
    		  
    		  	URL url = new URL(address.trim()); 
    		  	URLConnection uc = url.openConnection(); 
    		  	HttpURLConnection conn = (HttpURLConnection) uc; 
    		  	conn.setDoOutput(true); 
    		  	conn.setRequestMethod("POST"); 
    		  	conn.setRequestProperty("Accept-charset", "UTF-8");
    		  	conn.setRequestProperty("Content-type", "text/xml; charset=UTF-8");
                setRequestCookie(conn);
    		  	conn.setRequestProperty("Content-length", Integer.toString(urlParameters.getBytes().length)); 
    		  	conn.setConnectTimeout(10000);
    		  	conn.setReadTimeout(10000);
    		  	DataOutputStream wr = new DataOutputStream(conn.getOutputStream());
    		  	wr.writeBytes(urlParameters);
    		  	wr.flush();
    		  	wr.close();
	           
	           
    		  	//log.debug(">>>>>>>>"+conn.getResponseCode());
    		  	//log.debug(">>>>>>>>"+conn.getResponseMessage());
    		  	//log.debug("WebServiceGateWay - responseFromWebService - getResponseCode=" +conn.getResponseCode());
    		  	//log.debug("WebServiceGateWay - responseFromWebService - getResponseMessage="+conn.getResponseMessage());
    		  	 
    		  	/* Debug *
    		  	String UTF8 = "utf8";
    	        int BUFFER_SIZE = 8192;

    	        BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream(), UTF8), BUFFER_SIZE);
    	        String str;
    	        while ((str = br.readLine()) != null) {
    	        	//log.debug("WebServiceGateWay - responseFromWebService String="+str);
    	        }

    	        */
	           
    		  	conn.setConnectTimeout(10000);
    		  	conn.setReadTimeout(10000);
    		  	BufferedInputStream bis = new BufferedInputStream(conn.getInputStream()); 
    		  	DataInputStream dis = new DataInputStream(bis);
    		  	DocumentBuilderFactory docBuilderFactory = DocumentBuilderFactory.newInstance();
    		  	DocumentBuilder docBuilder = docBuilderFactory.newDocumentBuilder();
    		  	Document doc = docBuilder.parse(dis);
    		  	NodeList listOfRecords = doc.getElementsByTagName("result");
  	  	   
    		  	//log.debug("WebServiceGateWay - listOfRecords= " +listOfRecords.getLength());
	    	    if(listOfRecords.getLength()!=0){
	    	    	//log.debug("WebServiceGateWay - responseFromWebService - 1");
	                  //outerloop: //log.debug("Breaking"); break outerloop;
	                  for (int s = 0; s < listOfRecords.getLength(); s++) {
	                	  //log.debug("WebServiceGateWay - responseFromWebService - 2 - RecordNo= " + s);
	                	  Node firstDocTypeNode = listOfRecords.item(s);
	                	  //log.debug("WebServiceGateWay - responseFromWebService - firstDocTypeNode="+firstDocTypeNode.getNodeName().indexOf(s)+", Type="+firstDocTypeNode.getNodeType());
	                	  if (firstDocTypeNode.getNodeType() == Node.ELEMENT_NODE) {
	                		  //log.debug("WebServiceGateWay - responseFromWebService - 3 - NodeType= " + firstDocTypeNode.getNodeType());
	                		  Element firstElement = (Element) firstDocTypeNode;

	                		  try {
	                			  NodeList valueOneE = firstElement.getElementsByTagName("value1");
	                			  Element valueOne = (Element)valueOneE.item(0);
	                			  if(valueOne.hasChildNodes()){
	                				  NodeList valueOneList = valueOne.getChildNodes();
	                				  String valuet ;
	                				  valuet = valueOneList.item(0).getNodeValue().trim() ;
	                				  successFound ++ ;
	                			  }else{
	                				  //log.debug("WebServiceGateWay - responseFromWebService - 4 (valueOne.getChildNodes is NULL)");
	                				  NodeList valueOneErr = firstElement.getElementsByTagName("error");
	                    			  Element valueOneElement = (Element)valueOneErr.item(0);
	                    			  NodeList valueOneListErr = valueOneElement.getChildNodes();
	                    			  //log.debug("WebServiceGateWay - responseFromWebService - 5 NodeValue: "+valueOneListErr.item(0).getNodeValue().trim());
	                    			  errorStatus = valueOneListErr.item(0).getNodeValue().trim();
	                    			  errorsFound++;
	                    			  return "FAILED "+errorStatus;
	                			  }
	                		  }catch(Exception e){
	                			  //log.debug("WebServiceGateWay - responseFromWebService - If this fails in DEV or QA, then the STUASA & STUAST Sequence numbers are most probably out of step since a DB Restore");
	                			  e.printStackTrace();
	                			  errorsFound++;
	                			  
	                		  }
	                	  }
	                  }
		          }else {
		        	  //log.debug("WebServiceGateWay - responseFromWebService - 6 - Something went wrong from here ");
		        	  errorsFound++;
		        	  NodeList listOfRecords1 = doc.getElementsByTagName("results");
		        	  if(listOfRecords1.getLength()!=0){
		               //log.debug("1");
		        		  for (int s = 0; s < listOfRecords1.getLength(); s++) {
		        			  //log.debug("2");
		        			  Node firstDocTypeNode = listOfRecords1.item(s);
		                      if (firstDocTypeNode.getNodeType() == Node.ELEMENT_NODE) {
		                    	  //log.debug("3");
		                    	  Element firstElement = (Element) firstDocTypeNode;
		                          if(firstElement.hasAttributes()) {
		                        	  //log.debug("4"); 
		                        	  NamedNodeMap map = firstElement.getAttributes();  		  
		 			                  for(int i =0 ; i < map.getLength() ; i++){		
		 			                	  //log.debug("node value :" + map.item(i).getNodeValue());
	 			                      }
		                          }
		                          NodeList valueOneE = firstElement.getElementsByTagName("error");
		                          Element valueOne = (Element)valueOneE.item(0);
		                          NodeList valueOneList = valueOne.getChildNodes();
		                            
		                          try{
		                        	  errorsFound++;
		                        	  errorStatus = valueOneList.item(0).getNodeValue().trim();
		                               
		                           }catch(Exception e){
		                               e.printStackTrace();
		                              //log.debug("failures found");
		                               errorsFound++;
		                           }
		                      }
		                 }
		        	  }
		        	 // errorsFound ++ ;
		          }
			   }catch(Exception ex){
			  	   errorsFound++;
				   ex.printStackTrace();
			   }
	      if(errorsFound > 0){
	    	//log.debug("returns errors");
	    	  //log.debug("WebServiceGateWay - ResponseFromWebService - returns errors:FAILED="+errorStatus);
	    	  return "FAILED "+errorStatus;
	      }
	      else { 
	    	//log.debug("return no errors");
	    	  //log.debug("WebServiceGateWay - ResponseFromWebService - returns errors:SUCCESS");
	    	  return "SUCCESS" ;
	      }
      }
      
      private ArrayList<KeyValue> openWebServiceWithID2(String req_webServiceURL,String value1,String value2) throws Exception  {

    	     //log.debug("WebServiceGateWay - openWebServiceWithID2 - Requested URL: "+req_webServiceURL);
			 ArrayList<KeyValue> keyvalues = new ArrayList<KeyValue>();
			 URL webServiceURL = new URL(req_webServiceURL);
			 URLConnection urlConn = webServiceURL.openConnection();
			 setRequestCookie(urlConn);
			 DocumentBuilderFactory docBuilderFactory = DocumentBuilderFactory.newInstance();
			 DocumentBuilder docBuilder = docBuilderFactory.newDocumentBuilder();
			 Document doc = docBuilder.parse(urlConn.getInputStream());
		  	 NodeList listOfRecords = doc.getElementsByTagName("record");
		  	 if(listOfRecords.getLength()!=0){
	            //log.debug("one1 ");
	                for (int s = 0; s < listOfRecords.getLength(); s++) {
	                	 KeyValue test = new KeyValue();
	                	//log.debug("one2 ");
	                  Node firstDocTypeNode = listOfRecords.item(s);
	                        if (firstDocTypeNode.getNodeType() == Node.ELEMENT_NODE) {
	                        	//log.debug("one3 ");
	                        	
	                        	Element firstElement = (Element) firstDocTypeNode;
	                            if(firstElement.hasAttributes()) {
	                            	 //log.debug("one4 ");  
	                            	NamedNodeMap map = firstElement.getAttributes();  		  
				                        for(int i =0 ; i < map.getLength() ; i++){		
				                        	 //log.debug("one5 ");
				                        	test.setKey(map.item(i).getNodeValue());
				                      }
	                            }
	                       	 NodeList valueOneE = firstElement.getElementsByTagName(value2);
	                                Element valueOne = (Element)valueOneE.item(0);
	                                NodeList valueOneList = valueOne.getChildNodes();
	                               
	                                try{
	                                	  //log.debug("one6 ");     
	                                	test.setValue(valueOneList.item(0).getNodeValue().trim());
	                                         }catch(Exception e){
	                                           //log.debug("exception "+e.getMessage());
	                                        	 value1 = " ";
	                                }
	                                keyvalues.add(test);  
	                                
	                        }
	                       
	                }
	                
	               
	        }     else {
			    	 String docketNumberStatus = doc.getDocumentElement().getTextContent() ;
			    	 KeyValue test = new KeyValue();
			    	 //log.debug("WebServiceGateWay - openWebServiceWithID2 - status" + docketNumberStatus);
			    	if(docketNumberStatus.contains("No records returned")){
			    	test.setKey("1");
			    	test.setValue("No records returned");
			    	 
			    	}
			    	else {
			    		test.setKey("1");
				    	test.setValue(docketNumberStatus);
			    	}
			    	keyvalues.add(test);
			     }
			     return keyvalues;
      }
      
      @SuppressWarnings("unused")
	private String openWebServiceWithIDAddress(String req_webServiceURL,String value1,String value2) throws Exception  {

    	 String resultStatus = null;
		 ArrayList<KeyValue> keyvalues = new ArrayList<KeyValue>();
		 String noWhiteSpaceURL = req_webServiceURL.replaceAll(" ", "\\%20");
		 //log.debug("WebServiceGateWay - openWebServiceWithIDAddress - Requested URL: "+noWhiteSpaceURL);	 
   		URL webServiceURL = new URL(noWhiteSpaceURL);
   		URLConnection urlConn = webServiceURL.openConnection();
   		setRequestCookie(urlConn);
		 DocumentBuilderFactory docBuilderFactory = DocumentBuilderFactory.newInstance();
		 DocumentBuilder docBuilder = docBuilderFactory.newDocumentBuilder();
		 Document doc = docBuilder.parse(urlConn.getInputStream());
	  	 NodeList listOfRecords = doc.getElementsByTagName("record");
		     if(listOfRecords.getLength()!=0){
         
                for (int s = 0; s < listOfRecords.getLength(); s++) {
                  Node firstDocTypeNode = listOfRecords.item(s);
                        if (firstDocTypeNode.getNodeType() == Node.ELEMENT_NODE) {
                        	 KeyValue test = new KeyValue();
                        	Element firstElement = (Element) firstDocTypeNode;
                            if(firstElement.hasAttributes()) {
                           		  NamedNodeMap map = firstElement.getAttributes();  		  
			                        for(int i =0 ; i < map.getLength() ; i++){		
			            	       test.setKey(map.item(i).getNodeValue());
			                      }
                            }
                       	 NodeList valueOneE = firstElement.getElementsByTagName(value1);
                                Element valueOne = (Element)valueOneE.item(0);
                                NodeList valueOneList = valueOne.getChildNodes();
                               
                                try{
                                	resultStatus  = valueOneList.item(0).getNodeValue().trim() ;      
                                	test.setValue(valueOneList.item(0).getNodeValue().trim());
                                         }catch(Exception e){
                                        value1 = " ";
                                }
                                keyvalues.add(test);  
                                
                        }
                       
                }
                
               
        }//-------
		     return resultStatus;
	 }
      
 	 @SuppressWarnings("unused")
	private ArrayList<KeyValue> openWebServiceSearch(String req_webServiceURL) throws Exception  {
		  
		    //log.debug("WebServiceGateWay - openWebServiceSearch - Search webservice URL: "+req_webServiceURL);
			 ArrayList<KeyValue> keyvalues = new ArrayList<KeyValue>();
			 URL webServiceURL = new URL(req_webServiceURL);
			 URLConnection urlConn = webServiceURL.openConnection();
			 setRequestCookie(urlConn);
			 DocumentBuilderFactory docBuilderFactory = DocumentBuilderFactory.newInstance();
			 DocumentBuilder docBuilder = docBuilderFactory.newDocumentBuilder();
			 Document doc = docBuilder.parse(urlConn.getInputStream());
		  	 NodeList listOfRecords = doc.getElementsByTagName("record");
			 if(listOfRecords.getLength()!=0){
			   	 
				 //log.debug("WebServiceGateWay - openWebServiceSearch - one1 ");
	                for (int s = 0; s < listOfRecords.getLength(); s++) {
	                	 KeyValue test = new KeyValue();
	                	 //log.debug("WebServiceGateWay - openWebServiceSearch - one2 ");
	                  Node firstDocTypeNode = listOfRecords.item(s);
	                        if (firstDocTypeNode.getNodeType() == Node.ELEMENT_NODE) {
	                        	//log.debug("WebServiceGateWay - openWebServiceSearch - one3 ");
	                        	
	                        	Element firstElement = (Element) firstDocTypeNode;
	                            if(firstElement.hasAttributes()) {
	                            	//log.debug("WebServiceGateWay - openWebServiceSearch - one4 ");  
	                            	NamedNodeMap map = firstElement.getAttributes();  		  
				                        for(int i =0 ; i < map.getLength() ; i++){		
				                        	//log.debug("WebServiceGateWay - openWebServiceSearch - one5 ");
				                        	test.setKey(map.item(i).getNodeValue());
				                      }
	                            }
	                       	 	//NodeList valueOneE = firstElement.getElementsByTagName(value2);
	                            //Element valueOne = (Element)valueOneE.item(0);
	                            //NodeList valueOneList = valueOne.getChildNodes();
	                               
	                            try{
	                            	//log.debug("WebServiceGateWay - openWebServiceSearch - one6 ");     
	                               	//test.setValue(valueOneList.item(0).getNodeValue().trim());
	                            }catch(Exception e){
	                            	//log.debug("WebServiceGateWay - Exception "+e.getMessage());
	                           	 //value1 = " ";
	                            }
	                            keyvalues.add(test);  
	                        }
	                }
	               
	        }else{
	        	//log.debug("ShipList Number Creation : "+req_webServiceURL);	
	        	String docketNumberStatus = doc.getDocumentElement().getTextContent() ;
			    	KeyValue test = new KeyValue();
			    	//log.debug("WebServiceGateWay - openWebServiceSearch - status: " + docketNumberStatus);
			    	if(docketNumberStatus.contains("FromUser invalid")){
			    		test.setKey("2");
				    	test.setValue(docketNumberStatus);
			    	}else {
			    		test.setKey("2");
			    		test.setValue(docketNumberStatus);
			    	}
			    	keyvalues.add(test);
	        }
			return keyvalues;
 	 }
}


