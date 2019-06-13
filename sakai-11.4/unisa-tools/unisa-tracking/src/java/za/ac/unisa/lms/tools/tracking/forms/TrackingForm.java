package za.ac.unisa.lms.tools.tracking.forms;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionForm;

import za.ac.unisa.lms.tools.tracking.bo.Consignment;
import za.ac.unisa.lms.tools.tracking.bo.Docket;

@SuppressWarnings({"unused", "rawtypes", "unchecked"})
public class TrackingForm extends ActionForm{

	/**
	 * 
	 */
	private static final long serialVersionUID = -3996784762350764989L;
	private static final String version = "2018001b";
	private Log log = LogFactory.getLog(TrackingForm.class.getName());
	
	
	private String unchecked;
	public String getUnchecked() {
		return unchecked;
	}
	public void setUnchecked(String unchecked) {
		this.unchecked = unchecked;
	}
	private String userID;
    private String host;
	private String  novelUserId="";
	private String persNo="";
   	private String searchPersNo="";
   	private String macCode="";
    private ArrayList searhResults;
    
    private String shipListNumber;
    private String displayShipListNumber;
    private String displayShipListDate;
    private String regionDSAA;
    private String college;
    private String school;
    private String department;
    private String module;
    private String csdmUsers;
    private String building;    
    private String buildingUsers;
    private String user;
    private String users;
    private String province;
    private String provinceUsers;
    private String region;
    private String date;
    
    private boolean dsaaCheck = false;
    private boolean dispatchCheck = false ;
    private boolean shipListIncluded = false;
    private boolean singleAssignment;
    private boolean docketValidationCheck = false;
    private boolean search = false;
	private boolean lssClient=false;
	
	
   	private String surname;
   	private String userAddress;
	private String userAddress1;
	private String userAddress2;
	private String userAddress3;
	private String userAddress4;
	private String userAddress5;
	private String userAddress6;
	private String userPostal;
	private String userEmail;
	//Final Result Address
	private String saveAddress1;
	private String saveAddress2;
	private String saveAddress3;
	private String saveAddress4;
	private String saveAddress5;
	private String savePostal;
	private String saveEmail;
	//Report Address
	private String displayAddress1;
	private String displayAddress2;
	private String displayAddress3;
	private String displayAddress4;
	private String displayPostal;
	private String displayEmail;
	
	private String destinationAddress;
	private String emailAddress;

	private boolean userAdd;
   	private boolean dsaaAdd;
   	private boolean dispatchAdd;
   	private boolean csdAdd;
   	
    private String userSelection;
    private boolean remove = true;
    private String bookoutErrorStatus;
    private boolean hiddenButtonBookIn;
    private boolean hiddenButtonBookOut;
    private String webErrMsg = "";
    private String searchCheck;
    
	private List<Consignment> consignment;
    private int consignmentCounter=0;
    private String docCompare = "";
    private String docID = "";
    
    private int conListCount=0;
    private int docCount=0;
    private int uniqueAssCount=0;
    
    private int currentPDFPage=0;
    private boolean continuePDFPage=false;
    private int countPDFDct=0;;
    private int countPDFStuAss=0;
    
    private float tableStartY=0;
    private String unisaImage;
    
    private float reportPDFMargin=0;
    private float reportPDFCellMargin=0;
    private int reportPDFColumnSize1=0;
    private int reportPDFColumnSize2=0;
    private int reportPDFColumnSize3=0;
    private int reportPDFColumnSize4=0;
    private int reportPDFHeaderSize=0;
    private String reportPDFToDownload = "";
    private String reportPDFToPrint = "";
	//SearchPage
    private String searchString="";
    
    //ReportPDF Page
    private Integer currentPage=0;
    
    private String validationValues;
    private String validationResult;
    private String bookInOutURL ;
    private String webServiceURL ;
    
    private String consignmentNumber;
    
	ArrayList displayDockets = new ArrayList();
    ArrayList displayDctAssignments = new ArrayList();
    ArrayList displayUniqueNumbers = new ArrayList();
    
	List<Consignment> masterConsignmentList = new ArrayList<Consignment>();  
	List<Docket> masterDocketList = new ArrayList<Docket>();
	Map<String, String> mapUnqAssignments = new LinkedHashMap<String, String>();
    
	Map<String, String> processDocketList = new LinkedHashMap<String, String>();
	Map<String, String> processDocketAssignmentList = new LinkedHashMap<String, String>();
	Map<String, String> processAssignmentList = new LinkedHashMap<String, String>();

    private String book_IN = "checkin";
    private String book_OUT = "checkout";
    private String result = "Result";
    private String searchSelect = "searchSelect";
    private String report = "report";
    private String user_Selection = "userselection";
    
	public String getVersion() {
		return version;
	}
    public boolean isRemove() {
		return remove;
	}
	public void setRemove(boolean remove) {
		this.remove = remove;
	}
	private String hostName ;

	public String getUserID() {
		return userID;
	}
	public void setUserID(String userID) {
		this.userID = userID;
	}
	public String getHost() {
		return host;
	}
	public void setHost(String host) {
		this.host = host;
	}
	public String getUserAddress1() {
		return userAddress1;
	}
	public void setUserAddress1(String userAddress1) {
		this.userAddress1 = userAddress1;
	}
	public String getUserAddress2() {
		return userAddress2;
	}
	public void setUserAddress2(String userAddress2) {
		this.userAddress2 = userAddress2;
	}
	public String getUserAddress3() {
		return userAddress3;
	}
	public void setUserAddress3(String userAddress3) {
		this.userAddress3 = userAddress3;
	}
	public String getUserAddress4() {
		return userAddress4;
	}
	public void setUserAddress4(String userAddress4) {
		this.userAddress4 = userAddress4;
	}
	public String getUserAddress5() {
		return userAddress5;
	}
	public void setUserAddress5(String userAddress5) {
		this.userAddress5 = userAddress5;
	}
	public String getUserAddress6() {
		return userAddress6;
	}
	public void setUserAddress6(String userAddress6) {
		this.userAddress6 = userAddress6;
	}
	public String getUserPostal() {
		return userPostal;
	}
	public void setUserPostal(String userPostal) {
		this.userPostal = userPostal;
	}
	public String getUserEmail() {
		return userEmail;
	}
	public void setUserEmail(String userEmail) {
		this.userEmail = userEmail;
	}	
	public String getSaveAddress1() {
		return saveAddress1;
	}
	public void setSaveAddress1(String saveAddress1) {
		this.saveAddress1 = saveAddress1;
	}
	public String getSaveAddress2() {
		return saveAddress2;
	}
	public void setSaveAddress2(String saveAddress2) {
		this.saveAddress2 = saveAddress2;
	}
	public String getSaveAddress3() {
		return saveAddress3;
	}
	public void setSaveAddress3(String saveAddress3) {
		this.saveAddress3 = saveAddress3;
	}
	public String getSaveAddress4() {
		return saveAddress4;
	}
	public void setSaveAddress4(String saveAddress4) {
		this.saveAddress4 = saveAddress4;
	}
	public String getSaveAddress5() {
		return saveAddress5;
	}
	public void setSaveAddress5(String saveAddress5) {
		this.saveAddress5 = saveAddress5;
	}
	public String getDestinationAddress() {
		return destinationAddress;
	}
	public void setDestinationAddress(String destinationAddress) {
		this.destinationAddress = destinationAddress;
	}
	public String getSavePostal() {
		return savePostal;
	}
	public void setSavePostal(String savePostal) {
		this.savePostal = savePostal;
	}
	public String getSaveEmail() {
		return saveEmail;
	}
	public void setSaveEmail(String saveEmail) {
		this.saveEmail = saveEmail;
	}
	public String getDisplayAddress1() {
		return displayAddress1;
	}
	public void setDisplayAddress1(String displayAddress1) {
		this.displayAddress1 = displayAddress1;
	}
	public String getDisplayAddress2() {
		return displayAddress2;
	}
	public void setDisplayAddress2(String displayAddress2) {
		this.displayAddress2 = displayAddress2;
	}
	public String getDisplayAddress3() {
		return displayAddress3;
	}
	public void setDisplayAddress3(String displayAddress3) {
		this.displayAddress3 = displayAddress3;
	}
	public String getDisplayAddress4() {
		return displayAddress4;
	}
	public void setDisplayAddress4(String displayAddress4) {
		this.displayAddress4 = displayAddress4;
	}
	public String getDisplayPostal() {
		return displayPostal;
	}
	public void setDisplayPostal(String displayPostal) {
		this.displayPostal = displayPostal;
	}
	public String getDisplayEmail() {
		return displayEmail;
	}
	public void setDisplayEmail(String displayEmail) {
		this.displayEmail = displayEmail;
	}
    public boolean isUserAdd() {
		return userAdd;
	}
	public void setUserAdd(boolean userAdd) {
		this.userAdd = userAdd;
	}
	public boolean isDsaaAdd() {
		return dsaaAdd;
	}
	public void setDsaaAdd(boolean dsaaAdd) {
		this.dsaaAdd = dsaaAdd;
	}
	public boolean isDispatchAdd() {
		return dispatchAdd;
	}
	public void setDispatchAdd(boolean dispatchAdd) {
		this.dispatchAdd = dispatchAdd;
	}
	public boolean isCsdAdd() {
		return csdAdd;
	}
	public void setCsdAdd(boolean csdAdd) {
		this.csdAdd = csdAdd;
	}
	public String getUserAddress() {
		return userAddress;
	}
	public void setUserAddress(String userAddress) {
		this.userAddress = userAddress;
	}
	private String addressType;
	private String searchRadio;
	private String consignmentListNumber;
	private String enteredConsignmentNumber;
    private String docketNumber;
    private String studentNumber;
    private String uniqueNumber;
    private final Map values = new HashMap();   
    private int count; 
    private String studNo;
    private String uniqueAssignmentNr ;
    private String assignStatus ;
    private ArrayList noOfRecordsOptions; 
    private ArrayList courseList;
    private int noOfRecords = 0;
    private String singleDocketNumber;
    private String value;
    private ArrayList listOfDocketNumbers = new ArrayList();
    private ArrayList listOfStudentAssignments = new ArrayList();
    
    private int expandCollapse;
    private int loopCount = 0;
    public ArrayList getListOfStudentAssignments() {
		return listOfStudentAssignments;
	}
	public void setListOfStudentAssignments(ArrayList listOfStudentAssignments) {
		this.listOfStudentAssignments = listOfStudentAssignments;
	}
	private ArrayList displayDocketsForConsignment ;
	private ArrayList displayDctAssignmentsForConsignment;
	private ArrayList expandDisplayDocketsForConsignment ;
	private ArrayList displayUniqueNumbersForConsignment;
	private ArrayList unCheckedDockets;
	private ArrayList unCheckedStudentNumbers;
	
	private ArrayList bookoutunCheckedDockets;
	private ArrayList bookoutunCheckedStudentNumbers;
	
	
    public ArrayList getBookoutunCheckedDockets() {
		return bookoutunCheckedDockets;
	}
	public void setBookoutunCheckedDockets(ArrayList bookoutunCheckedDockets) {
		this.bookoutunCheckedDockets = bookoutunCheckedDockets;
	}
	public ArrayList getBookoutunCheckedStudentNumbers() {
		return bookoutunCheckedStudentNumbers;
	}
	public void setBookoutunCheckedStudentNumbers(
			ArrayList bookoutunCheckedStudentNumbers) {
		this.bookoutunCheckedStudentNumbers = bookoutunCheckedStudentNumbers;
	}
	public ArrayList getUnCheckedStudentNumbers() {
		return unCheckedStudentNumbers;
	}
	public void setUnCheckedStudentNumbers(ArrayList unCheckedStudentNumbers) {
		this.unCheckedStudentNumbers = unCheckedStudentNumbers;
	}
	public ArrayList getUnCheckedDockets() {
		return unCheckedDockets;
	}
	public void setUnCheckedDockets(ArrayList unCheckedDockets) {
		this.unCheckedDockets = unCheckedDockets;
	}

    private String enteredCosignmentNumber ;
   
	public String getEnteredCosignmentNumber() {
		return enteredCosignmentNumber;
	}
	public void setEnteredCosignmentNumber(String enteredCosignmentNumber) {
		this.enteredCosignmentNumber = enteredCosignmentNumber;
	}
	public String getStudNo() {
		return studNo;
	}
	public void setStudNo(String studNo) {
		this.studNo = studNo;
	}
	public String getUniqueAssignmentNr() {
		return uniqueAssignmentNr;
	}
	public void setUniqueAssignmentNr(String uniqueAssignmentNr) {
		this.uniqueAssignmentNr = uniqueAssignmentNr;
	}
	public String getAssignStatus() {
		return assignStatus;
	}
	public void setAssignStatus(String assignStatus) {
		this.assignStatus = assignStatus;
	}
	public String getNovelUserId() {
		return novelUserId;
	}
	public void setNovelUserId(String novelUserId) {
		this.novelUserId = novelUserId.toUpperCase().trim();
	}
	public String getPersNo() {
		return persNo;
	}
	public void setPersNo(String persNo) {
		this.persNo = persNo.trim();
	}
	public String getSearchPersNo() {
		return searchPersNo;
	}
	public void setSearchPersNo(String searchPersNo) {
		this.searchPersNo = searchPersNo.trim();
	}
	public String getRegionDSAA() {
		return regionDSAA;
	}
	public void setRegionDSAA(String regionDSAA) {
		this.regionDSAA = regionDSAA;
	}
	public String getCollege() {
		return college;
	}
	public void setCollege(String college) {
		this.college = college;
	}
	public String getSchool() {
		return school;
	}
	public void setSchool(String school) {
		this.school = school;
	}
	public boolean isDsaaCheck() {
		return dsaaCheck;
	}
	public void setDsaaCheck(boolean dsaaCheck) {
		this.dsaaCheck = dsaaCheck;
	}
	public boolean isDispatchCheck() {
		return dispatchCheck;
	}
	public void setDispatchCheck(boolean dispatchCheck) {
		this.dispatchCheck = dispatchCheck;
	}
	public String getSurname() {
		return surname;
	}
	public void setSurname(String surname) {
		this.surname = surname;
	}
	public String getDepartment() {
		return department;
	}
	public void setDepartment(String department) {
		this.department = department;
	}
	public String getModule() {
		return module;
	}
	public void setModule(String module) {
		this.module = module;
	}
	public String getBuilding() {
		return building;
	}
	public void setBuilding(String building) {
		this.building = building;
	}
	public String getUsers() {
		return users;
	}
	public void setUsers(String users) {
		this.users = users;
	}
	public String getProvinceUsers() {
		return provinceUsers;
	}
	public void setProvinceUsers(String provinceUsers) {
		this.provinceUsers = provinceUsers;
	}
	public String getCsdmUsers() {
		return csdmUsers;
	}
	public void setCsdmUsers(String csdmUsers) {
		this.csdmUsers = csdmUsers;
	}
	public String getBuildingUsers() {
		return buildingUsers;
	}
	public void setBuildingUsers(String buildingUsers) {
		this.buildingUsers = buildingUsers;
	}
	public String getUser() {
		return user;
	}
	public void setUser(String user) {
		this.user = user;
	}
	public String getConsignmentListNumber() {
		return consignmentListNumber;
	}
	public void setConsignmentListNumber(String consignmentListNumber) {
		this.consignmentListNumber = consignmentListNumber;
	}
	public String getEnteredConsignmentNumber() {
		return enteredConsignmentNumber;
	}
	public void setEnteredConsignmentNumber(String enteredConsignmentNumber) {
		this.enteredConsignmentNumber = enteredConsignmentNumber;
	}
	public String getProvince() {
		return province;
	}
	public void setProvince(String province) {
		this.province = province;
	}
	public String getRegion() {
		return region;
	}
	public void setRegion(String region) {
		this.region = region;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public String getDocketNumber() {
		return docketNumber;
	}
	public void setDocketNumber(String docketNumber) {
		this.docketNumber = docketNumber;
	}
	public String getStudentNumber() {
		return studentNumber;
	}
	public void setStudentNumber(String studentNumber) {
		this.studentNumber = studentNumber;
	}
	public String getUniqueNumber() {
		return uniqueNumber;
	}
	public void setUniqueNumber(String uniqueNumber) {
		this.uniqueNumber = uniqueNumber;
	}
	public Map getValues() {
		return values;
	}
	public int getCount() {
		return count;
	}
	public void setCount(int count) {
		this.count = count;
	}
	 public void setValue(String key, Object value)
	 {      
		 values.put(key, value);   
		 }   
	 
	 public Object getValue(String key){     
		 return values.get(key);     
		 } 
	
	 public boolean isSingleAssignment() {
			return singleAssignment;
		}
		public void setSingleAssignment(boolean singleAssignment) {
			this.singleAssignment = singleAssignment;
		}
	
		public ArrayList getNoOfRecordsOptions() {
			noOfRecordsOptions = new ArrayList();
			for (int i=0; i <= 18; i++) {
				noOfRecordsOptions.add(new org.apache.struts.util.LabelValueBean(Integer.toString(i),Integer.toString(i)));
			}
			return noOfRecordsOptions;
		}
		
		public void setNoOfRecordsOptions(ArrayList noOfRecordsOptions) {
			this.noOfRecordsOptions = noOfRecordsOptions;
		}
		public int getNoOfRecords() {
			return noOfRecords;
		}
		public void setNoOfRecords(int noOfRecords) {
			this.noOfRecords = noOfRecords;
		}
		public ArrayList getCourseList() {
			return courseList;
		}
		public void setCourseList(ArrayList courseList) {
			this.courseList = courseList;
		}
		public String getSingleDocketNumber() {
			return singleDocketNumber;
		}
		public void setSingleDocketNumber(String singleDocketNumber) {
			this.singleDocketNumber = singleDocketNumber;
		}
		public boolean isDocketValidationCheck() {
			return docketValidationCheck;
		}
		public void setDocketValidationCheck(boolean docketValidationCheck) {
			this.docketValidationCheck = docketValidationCheck;
		}
		public String getValue() {
			return value;
		}
		public void setValue(String value) {
			this.value = value;
		}
		
		public ArrayList getDisplayDocketsForConsignment() {
			return displayDocketsForConsignment;
		}
		public void setDisplayDocketsForConsignment(
				ArrayList displayDocketsForConsignment) {
			this.displayDocketsForConsignment = displayDocketsForConsignment;
		}	
		public ArrayList getDisplayDctAssignmentsForConsignment() {
			return displayDctAssignmentsForConsignment;
		}
		public void setDisplayDctAssignmentsForConsignment(
				ArrayList displayDctAssignmentsForConsignment) {
			this.displayDctAssignmentsForConsignment = displayDctAssignmentsForConsignment;
		}
		public boolean isShipListIncluded() {
			return shipListIncluded;
		}
		public void setShipListIncluded(boolean shipListIncluded) {
			this.shipListIncluded = shipListIncluded;
		}

		public ArrayList getListOfDocketNumbers() {
			return listOfDocketNumbers;
		}
		public void setListOfDocketNumbers(ArrayList listOfDocketNumbers) {
			this.listOfDocketNumbers = listOfDocketNumbers;
		}
		public Object getRecordIndexed(int index) {
			//log.info("getRecordIndex");
			if(displayDocketsForConsignment != null && !displayDocketsForConsignment.isEmpty() && index >= 0){
			    return displayDocketsForConsignment.get(index);
			}else {
				return displayDocketsForConsignment;
			}
		}
		public Object getRecordIndexed1(int index) {
			if(displayUniqueNumbersForConsignment != null && !displayUniqueNumbersForConsignment.isEmpty() && index >= 0){
			return displayUniqueNumbersForConsignment.get(index);
			}else {
				return displayUniqueNumbersForConsignment;
			}
		}
		public ArrayList getDisplayUniqueNumbersForConsignment() {
			return displayUniqueNumbersForConsignment;
		}
		public void setDisplayUniqueNumbersForConsignment(
				ArrayList displayUniqueNumbersForConsignment) {
			this.displayUniqueNumbersForConsignment = displayUniqueNumbersForConsignment;
		}
		public ArrayList getExpandDisplayDocketsForConsignment() {
			return expandDisplayDocketsForConsignment;
		}
		public void setExpandDisplayDocketsForConsignment(
				ArrayList expandDisplayDocketsForConsignment) {
			this.expandDisplayDocketsForConsignment = expandDisplayDocketsForConsignment;
		}
		public int getExpandCollapse() {
			return expandCollapse;
		}
		public void setExpandCollapse(int expandCollapse) {
			this.expandCollapse = expandCollapse;
		}
		public String getUserSelection() {
			return userSelection;
		}
		public void setUserSelection(String userSelection) {
			this.userSelection = userSelection;
		}
		public String getHostName() {
			return hostName;
		}
		public void setHostName(String hostName) {
			this.hostName = hostName;
		}
		public String getBookoutErrorStatus() {
			return bookoutErrorStatus;
		}
		public void setBookoutErrorStatus(String bookoutErrorStatus) {
			this.bookoutErrorStatus = bookoutErrorStatus;
		}
		
		public boolean isHiddenButtonBookIn() {
			return hiddenButtonBookIn;
		}

		public void setHiddenButtonBookIn(boolean hiddenButtonBookIn) {
			this.hiddenButtonBookIn = hiddenButtonBookIn;
		}
		public boolean isHiddenButtonBookOut() {
			return hiddenButtonBookOut;
		}

		public void setHiddenButtonBookOut(boolean hiddenButtonBookOut) {
			this.hiddenButtonBookOut = hiddenButtonBookOut;
		}
		
		public String getAddressType() {
			return addressType;
		}

		public void setAddressType(String addressType) {
			this.addressType = addressType;
			//if(addressType.equalsIgnoreCase("dsaa")){
			//	setDsaaCheck(true);
			//}else if(addressType.equalsIgnoreCase("dispatch")){
			//	setDispatchCheck(true);
			//}
		}

		public String getSearchRadio() {
			return searchRadio;
		}

		public void setSearchRadio(String searchRadio) {
			this.searchRadio = searchRadio;
		}
		
		public List<Consignment> getConsignment() {
			return consignment;
		}

		public void setConsignment(List<Consignment> consignment) {
			this.consignment = consignment;
		}
	    public int getConsignmentCounter() {
			return consignmentCounter;
		}
		public void setConsignmentCounter(int consignmentCounter) {
			this.consignmentCounter = consignmentCounter;
		}
		public String getDocCompare() {
			return docCompare;
		}
		public void setDocCompare(String docCompare) {
			this.docCompare = docCompare;
		}
		public String getDocID() {
			return docID;
		}
		public void setDocID(String docID) {
			this.docID = docID;
		}
		
		public int getConListCount() {
			return conListCount;
		}
		public void setConListCount(int conListCount) {
			this.conListCount = conListCount;
		}
		public int getDocCount() {
			return docCount;
		}
		public void setDocCount(int docCount) {
			this.docCount = docCount;
		}
		public int getUniqueAssCount() {
			return uniqueAssCount;
		}
		public void setUniqueAssCount(int uniqueAssCount) {
			this.uniqueAssCount = uniqueAssCount;
		}
		public String getSearchString() {
			return searchString;
		}
		
		public void setSearchString(String searchString){
			this.searchString = searchString;
		}

		public ArrayList getSearchResults() {
			return searhResults;
		}
		public void setSearchResults(ArrayList searhResults) {
			this.searhResults = searhResults;
		}
		public String getShipListNumber() {
			return shipListNumber;
		}
		public void setShipListNumber(String shipListNumber) {
			this.shipListNumber = shipListNumber;
		}
		public String getDisplayShipListNumber() {
			return displayShipListNumber;
		}
		public void setDisplayShipListNumber(String displayShipListNumber) {
			this.displayShipListNumber = displayShipListNumber;
		}
		public String getDisplayShipListDate() {
			return displayShipListDate;
		}
		public void setDisplayShipListDate(String displayShipListDate) {
			this.displayShipListDate = displayShipListDate;
		}
		public boolean isSearch() {
			return search;
		}
		public void setSearch(boolean search) {
			this.search = search;
		}
		
	    public String getWebErrMsg() {
			return webErrMsg;
		}
		public void setWebErrMsg(String webErrMsg) {
			this.webErrMsg = webErrMsg;
		}
		public String getSearchCheck() {
			return searchCheck;
		}
		public void setSearchCheck(String searchCheck) {
			this.searchCheck = searchCheck;
		}
		public String getEmailAddress() {
			return emailAddress;
		}
		public void setEmailAddress(String emailAddress) {
			this.emailAddress = emailAddress;
		}
		public String getMacCode() {
			return macCode;
		}
		public void setMacCode(String macCode) {
			this.macCode = macCode;
		}
		public boolean isLssClient() {
			return lssClient;
		}
		public void setLssClient(boolean lssClient) {
			this.lssClient = lssClient;
		}

		public float getTableStartY() {
			return tableStartY;
		}
		public int getCountPDFDct() {
			return countPDFDct;
		}
		public void setCountPDFDct(int countPDFDct) {
			this.countPDFDct = countPDFDct;
		}
		public int getCountPDFStuAss() {
			return countPDFStuAss;
		}
		public void setCountPDFStuAss(int countPDFStuAss) {
			this.countPDFStuAss = countPDFStuAss;
		}
		public int getCurrentPDFPage() {
			return currentPDFPage;
		}
		public void setCurrentPDFPage(int currentPDFPage) {
			this.currentPDFPage = currentPDFPage;
		}
		public boolean isContinuePDFPage() {
			return continuePDFPage;
		}
		public void setContinuePDFPage(boolean continuePDFPage) {
			this.continuePDFPage = continuePDFPage;
		}
		public void setTableStartY(float tableStartY) {
			this.tableStartY = tableStartY;
		}

		public Integer getCurrentPage() {
			return currentPage;
		}

		public void setCurrentPage(Integer currentPage) {
			this.currentPage = currentPage;
		}
		
		public static String getUnisaImage() {
			return "/9j/4AAQSkZJRgABAQEAeAB4AAD/2wBDAAgGBgcGBQgHBwcJCQgKDBQNDAsLDBkSEw8UHRofHh0aHBwgJC4nICIsIxwcKDcpLDAxNDQ0Hyc5PTgyPC4zNDL/2wBDAQkJCQwLDBgNDRgyIRwhMjIyMjIyMjIyMjIyMjIyMjIyMjIyMjIyMjIyMjIyMjIyMjIyMjIyMjIyMjIyMjIyMjL/wAARCACXAoADASIAAhEBAxEB/8QAHwAAAQUBAQEBAQEAAAAAAAAAAAECAwQFBgcICQoL/8QAtRAAAgEDAwIEAwUFBAQAAAF9AQIDAAQRBRIhMUEGE1FhByJxFDKBkaEII0KxwRVS0fAkM2JyggkKFhcYGRolJicoKSo0NTY3ODk6Q0RFRkdISUpTVFVWV1hZWmNkZWZnaGlqc3R1dnd4eXqDhIWGh4iJipKTlJWWl5iZmqKjpKWmp6ipqrKztLW2t7i5usLDxMXGx8jJytLT1NXW19jZ2uHi4+Tl5ufo6erx8vP09fb3+Pn6/8QAHwEAAwEBAQEBAQEBAQAAAAAAAAECAwQFBgcICQoL/8QAtREAAgECBAQDBAcFBAQAAQJ3AAECAxEEBSExBhJBUQdhcRMiMoEIFEKRobHBCSMzUvAVYnLRChYkNOEl8RcYGRomJygpKjU2Nzg5OkNERUZHSElKU1RVVldYWVpjZGVmZ2hpanN0dXZ3eHl6goOEhYaHiImKkpOUlZaXmJmaoqOkpaanqKmqsrO0tba3uLm6wsPExcbHyMnK0tPU1dbX2Nna4uPk5ebn6Onq8vP09fb3+Pn6/9oADAMBAAIRAxEAPwD36iiikMKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigDgG1S+HxwTTBdzfYTpXmm33HZu3EbsdM+9d/Xmrf8nCx/wDYFP8A6Ea9KoQB0oo71VvbyKwtXnmbCqPzpSkoq72BJt2RO7rGpZ2CqBkkmsK98WWNsSsO64b/AGeF/P8AwzXK6prN1qkvzsUhH3Yx0+p9TWbXhYjNpXtR+89qhliteq/kdFP4wvnJ8mKKIe+WP59P0qi/iPVpOt4wHoqqP6Vh3N7bWabriZIx2BPJ+g6mufvfGcMRK2lpLOR0ZiFU+/c/oK5ITxmIfutna6GHpK/KdsdZ1I/8vs3/AH1QNZ1If8vs3/fVeWXXjPWZAQiR249VjyfzOR+lZUviDV5s7tQnGf7jbf5Yrup5Zi5K7nb5nJUxeHjooX+R7aviHVl6Xj/iAf6VZj8Vaso5dH/3o/8ADFeArqd+syzLe3AkUgq4lbIPqDniu88O/F7V9MKQ6rDHqNuCAXICygexAwfxGT611LK8RFXVU5Z42g/+XSPTY/GN6v8ArbWN/wDdJX/GrkfjS3P+ttZF9drA/wCFSeHfGmgeJ0UWN2i3BGTbTALIPwPX6jIroWgicYaND9VFWsPiYae0v8jGVfDy/wCXdvmZMPijS5esxjPo6nj+laEOoWlz/qbiJ/8AdcGmvpljJ9+0gb6xj/Cq7+H9KfrZoPdcj+Vax+sLezMW6L2ujTzS1krokMWPs11dQY6KspI/I5qdbe/iHy3qyjsJYhn81I/lWinP7USHGPRl6j8aqrNdqP3lurf9cnzn88fzqUTqR8wZPXcpAH49KtSRNmTUUgIYZUgjsQaWqEFFFFABRRUbyxxld7qpc7VycZJ7D3oAkooooAKKKo6vqlvoukXWp3e/7PbRmSTYuSAOuBQBeorzX/hefg3/AJ6X3/gOf8a2fDHxK8P+LtUbTtLe5M6xGUiSLaNoIB5z1yRQB2NFFFABRRRQAUUUUAFFef6l8Y/CmlandafcyXguLWVoZAsGRuUkHBzzyK7DRdXtdd0e21SyLm3uV3xl1wcZI5H4UAaFFFFABRWF4p8WaZ4P06K/1UzCCSURKYk3HcQT0z6A1gaP8XPCet6tb6bbXFxHPO22MzRbFLdhnPU9B7mgDvKKKKACiiigAoorI8SeIrHwto76pqPmi2RlVjEm4jccDigDXorzX/hefg3/AJ6X3/gOf8a6Pwp480XxlJdJpDTk2wUyebHt+9nGOfY0AdPRRRQAUV5/qfxi8K6Tql1p9094Li2laKQLBkbgcHBzVZPjh4Od1USX2WIAzb9z+NAHpNFICCAR0IyKWgAooooAKK5fxJ8QPDfhXKajqCm4H/LtCN8n4gdPxIrzu9/aGtVlK2GgTSxjo89wEJ/AA4/OgD2yivCo/wBoh8/vfDi4zzsuyDj8VrpdJ+OvhW/ZUvUu9Pc8EyR70B+qknHuQKAPUKKpabqun6xa/adOvYLuE8b4ZAwHscdD9au0AFFFFABRRRQAUVm67rdj4c0a41XUXZLaADcVGTkkAADuckVyWlfGDwnrGq22nW010s9zII4zLDtXceBk59aAO/ooooAKKKKACiiigAooooA81b/k4WP/ALAp/wDQjXpVeat/ycLH/wBgU/8AoRr0qhAIelcH4q1I3eoG2Rv3MBxx3bv+XT867e5mFvbSzN0jUsa8kv7+O2ilu7lsDJJPcknoPcmvIzWrLlVOO7PUyukpTc30Ce4itomlmdURRySa5TUvFU0pMdiDGnTzCMsfoOgrJ1PVZ9TuC8hKxg/JGDwo/qfeqNcuHwcYq89We62OeR5XLyOzsTkliST+NNooruSsIKaVVh8yg/UU6imm1sS4pqzRCbWE/wAOPocVEbFT91yPqM1borWNepHZmE8JRnvEoi1micPE+GByCCQQfUGu+8NfFTXtFKQanG2pWgwMuf3qj2bv9Dn6iuPorVYubVpHNLLKT+HQ+j/D3jPRPE0X+g3YFxjLW03ySL+B6j3GRXQfjXyijtG4dGKspyGBwQfUGta28U6/aY8jV71QOgMxI/Ikij6wuxzyyqX2ZH0vTq+fIfiX4qiGDqKyf78Kf4Vfh+LniONQHjsZfdojn9GA/Sn7eJi8srLazPdKK8VX4xayAN2n2J9cBx/7MacfjHq/8OnWY+pb/Gq9tAj+zsR2PZsDOcDPrilrw+X4v+IXBCW9hHnoRGxI/wDHsVm3HxK8VXAIGoLED/zzhUEfQ4Jpe3h0LWWVnvZH0DuAHJFYWp+MtA0gN9q1KHevBjjbe2fTAzj8a8EfUNf16byWur++kb/lmGZ+PoOAPwrqdC+FOr6gVl1N1sIDyV4aQj6Dgficj0pe1lLSKLeBp0letP7jdvfinealcrYeGtLeWeQ4V5Rk/UKDxjrkn6ius8N+Hby0f+09cvGvdWkXGSfkgB6qg6D3IAz/ADvaD4X0rw5b+VYW4ViMPM3Lv9T/AEHFbNXGL3kclWrC3LSVl+LFooorQ5wpjokiFHVWVhgqwyCPoetPooA8Q+O/h3SrPR7DVbWxht7trnyneFAu9SpPIHBIIGD15Nc18Bf+SgTf9eEn/oSV3X7QH/Im6f8A9f4/9AauF+Av/JQJv+vCT/0JKQH0pRRRTAKKKKACiiigD458cf8AI+6//wBhCf8A9DNfTHwv/wCSaaF/17n/ANCavmfxx/yPuv8A/YQn/wDQzX0x8L/+SaaF/wBe5/8AQmpIDrqKKKYHk/7QH/Ik2H/YQX/0W9fOau0bq6MVZSCCDggjoQa+jP2gP+RJsP8AsIL/AOi3rw7wt4aufFV/dWNm2LqO1eeJT0dlI+XPYkEge+KTA+h/hV47Hi7Qvs15IP7WslCzA/8ALVegf8eh9/rXoVfGOga5qHhPxDBqVpujubdyHjcEBhnDIw9CMg+nXqK+uPDuvWfiXQrbVrB90M65Kk8ow6qfcGmgNaiiigAqOaGKeMxzRpIh6q6gg/gakooA+dfjr4f0zSNX0u70+1jtnu45BMsShVJUrg4HGTuOfXArQ/Z4J+3a8O3lQn9Wp/7RH/Hz4f8A9yf+aUz9nj/kIa9/1yh/m1ID3qiiimBzfizwvo+u6DfxXmnwNIYnZZhGBIrYyGDdc8Dvz3r5Ch4nj9mH86+2NR/5Bd3/ANcX/wDQTXxNF/r0/wB4fzpMD7ehP7iP/dH8qkqOD/j3j/3R/KpKYBXj3xa+J82hyP4f0OYLfFR9puV5MIPRV/2iOSewIxz09R1vU00fQr/UnAItbd5sepUEgfieK+NJ5rnVtTkmlYy3V1MWYnqzMf6k0MC5o+haz4r1Q22nW015dOS0jE5AyeWZjwOe5PNeqaX+z3dywq+q63FBIRkx28Rkx7biR/KvV/BfhSz8H+HoNPt0Xzioa5mxzLJ3JPp2HoK6SiwHik37PFoUPkeIZ1btvtgR+jCuP174JeKdJVpbIQ6nAoz+4O2QAeqnqfYE19N0UWA8Q+AGnT2k/iF7mGWGVDDEySKVIPzE5B5B6V7fTBGodnCgM2MkDk496fQAUUUUAFFFVr+9g0zTrm+uXCQW0bSyMeyqMn+VAHh/x98TCS6svDcDZEWLm5wf4iCEU/QEn8RXi0UrwTJNExSSNgysDggg5BB9Qa1NTvb3xZ4pmuipe71C5ARBk8sQFUewGAPpXf8AxY8AQ+GNG0K9skBjjhFpdOBjdIAWDn3OW/ICkB7j4N8QJ4n8Jafqqn95LGBMB/DIOGH5g49iK3q8C+AXiTyNQvvDszHbcD7RbgngOowwA9SAD/wE177TAKKKKACiiigAooooA81b/k4WP/sCn/0I16VXmrf8nCx/9gU/+hGvSqEBma9J5eh3Z9U2/nxXz34i1M318YkY+RCSFA6E9z/QV794mtLu+8P3drZAG5kULHk4wSRyT7dfwrA8N/DTSNGRJr2MX15jJaUZRT7L0/E5NcVXDupX5+iR6eDxVOhSfNu2eM6d4f1fVyBYadPOCcB1QhR9WOAPzrprT4U+JbjBlS2th/01lyf/AB0GvdkRUUKqgKOAB2p1bLDxW4p5pVb91WPH4vgzdEZm1iFT6JCSP1Iq2vwYj/i1t/wtx/8AFV6rRVqjAxePxD+0eW/8KXtsf8hib/vwP8aD8F7ftrMv/fgH+tepc+tHPrR7KHYX17EfzHlLfBdf4dbb8bYf/FVC3wYm/h1pD9bc/wDxVeuUUexh2BY/EL7R45J8Gb8D93q1u3+9Ew/kTVVvg7ro+5e2DD3Zwf8A0GvbaKTow7Ff2jXXU8Dvfhb4mtE3RQQXXqIJhkf99Y/SuRv7K+0ptuoWF3bEHH72EqD9CeDX1VTHRJUKOqupHKsMg/WhUIFLMq58l/bIfU/lSfbIv9r8q+ktS+H/AIW1VT9o0e3VzzvgBibPrlcZ/GuS1D4H6PMM2GpXds2ekgWRcemOD+tWqFLqP+06x49Bc28s6o8whUnBkdSQvuQAT+QNdtongew1pAYPFemMx/5ZpncP+AttP6VLe/BDXIsmzv7K4A6By0bH9CP1rCuPhb4wtz/yCjIB3jmRv65qvq1J7EvMa762PRrX4M2SsDdatPIO4ijCfzzXQWPwz8MWTKzWTXLjoZ5Cw/EcA/iK8Xh074haN+7trbXoFXgCFZCo/LIrWtfFfxQtgEEOpSjpiXT9x/Pbk/nTWHitjGWLrS3ke8WlhaWEXl2ltDAn92KMKPyAq1Xi9p4r+Ktx8qaIxOM5msjH+pIFbFsfi3qDAOdOsFPVpFQ4/Abj+lVyWOdtvVnqFNLKCASAT0561xdp4R8Q3J3674vvpeP9VYqtuv4kDJ/IH3rp7DR7HTF/0aH94RhpZGLyN9WYkn8TUtIC/RRRSAKKKKAPJf2gP+RN0/8A6/x/6A1cL8Bf+SgTf9eEn/oSV3X7QH/Im6f/ANf4/wDQGrhfgL/yUCb/AK8JP/QkpAfSlFFFMAooooAKKKKAPjnxx/yPuv8A/YQn/wDQzX0x8L/+SaaF/wBe5/8AQmr5n8cf8j7r/wD2EJ//AEM19MfC/wD5JpoX/Xuf/QmpIDrqKKKYHk/7QH/Ik2H/AGEF/wDRb1598Cv+Sif9ucv81r0H9oD/AJEmw/7CC/8Aot68++BX/JRP+3OX+a0gOg+NngDyJW8VaZD+7cgX0aD7rHgSAeh4B98Hua5T4U+PG8Ja6LS9lP8AZN6wWUE8RN0Dj+R9uewr6euLeG7tpba4jWSGVSkiMMhlIwQfqK+UfiN4Jl8GeImijDNptwTJayEE/LnlSfUZA9wQe9MD6yV1dQykFSAQRyCKdXjfwV8e/wBoWi+F9Sm/0mBSbN2PMkY6p7lRyPb6V7JQAUUUUAeEftEf8fPh/wD3J/5pTP2eP+Qhr3/XKH+bU/8AaI/4+fD/APuT/wA0pn7PH/IQ17/rlD/NqQHvVFFFMCtqP/ILu/8Ari//AKCa+Jov9en+8P519s6j/wAgu7/64v8A+gmviaL/AF6f7w/nQwPt6D/j3j/3R/KpKjg/494/90fyqSgDk/iYkknw310Q/e+zEnB/hBBb9Aa+WPD1xFaeJdKuZyBDDeRSOT2UOCf0FfZV/ZRajp1zY3AzDcRNFIB3VgQf0NfG/iLQrvw3r13pV6pEsEhAYjAdezD2IwaGB9o5BAIPB6GivFvhh8W7WWyt9C8R3AgniUR295IcLIo4CsexA4yeDjkg9fZ1YOoZSCpGQQcg0AOooooAKKKKACiiigAryL47eJRYeH7fQYJMT37eZMAeREp6H6tj8jXrZYIpZiAAMkk9K+Q/H/iRvFXjK+1ANm3DeVbgdBGvAP48n6k0MDqvgd4b/tXxc+qzITb6YgZSRwZWyFH4AMfqBXvHi/QU8T+FdQ0lgu+eI+UW/hkHKn8wKx/hd4b/AOEa8DWUMiFbu6H2m4B4IZsYHthQBj1BrtKAPi3SdRu/DXiO2vo1KXNjcBihOCSpwyn6jIP1NfZGnX0GqabbX9s26C4iWWM+zDI/nXzX8afDQ0Pxq19Cm221NTOMDAEgOHH4kg/8Cr0P4E+JP7Q8N3GhzP8AvtPfdECesTEn9Gz+YoA9booooAKKKKACiiigDzVv+ThY/wDsCn/0I16VXmrf8nCx/wDYFP8A6Ea9KoQBRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAeS/tAf8ibp/wD1/j/0Bq4X4C/8lAm/68JP/Qkruv2gP+RN0/8A6/x/6A1cL8Bf+SgTf9eEn/oSUgPpSiiimAUUUUAFFFFAHxz44/5H3X/+whP/AOhmvpj4X/8AJNNC/wCvc/8AoTV8z+OP+R91/wD7CE//AKGa+mPhf/yTTQv+vc/+hNSQHXUUUUwPJ/2gP+RJsP8AsIL/AOi3rz74Ff8AJRP+3OX+a16D+0B/yJNh/wBhBf8A0W9effAr/kon/bnL/NaQH0zXPeMvClp4w8PT6Zc4WQ/NBNjJikHQj27EdwTXQ0UwPi65t9T8KeImhffa6lYTAgg8qwOQQe4PBHYg19T+AfGVt408OR3q7UvIsR3cIP3H9R7Ecj8R1BrlvjD4A/4SHSzrenRZ1OzQ70UczRDkjHdh1HqMj0rxTwL4wufBniOK/j3PaviO6hB/1kZPOB6jqPfjoTSA+vqKq6ffW2p2EF9ZyrLbToJI3U8EHkVapgeE/tEf8fHh89tk/wDNKj/Z4/5CGvf9cof5tUv7RP8ArvD/APu3H80qL9nj/kIa9/1yh/m1ID3qiiimBW1H/kF3f/XF/wD0E18TRf69P94fzr7Z1H/kF3f/AFxf/wBBNfE0X+vT/eH86GB9vQf8e8f+6P5VJUcH/HvH/uj+VSUAFcX49+Hmn+OLFS7C21GEEQXSrn/gLDuv6g8juD2lFAHxt4l8I614TvTb6tZvGCSEmXmOQeqsOD9Oo7gVo+FviR4j8JlI7S8M9mp5tLjLpj0HOV/Aj8a+r72xtNRtWtb22iuIHGGjlQMp/A15R4o+BGm3u+48PXRsZjnFvNloifY8sv6/SkBpeFvjX4f1rZb6nu0q7bj94d0TH2cdPxA+telRyJLGskbB0YAqynII9jXx14i8Ia54VuPK1awkhUnCTD5o3+jDj8OvtWr4L+JGt+DbhUhla604n95ZysSuO5U/wn6ceoNO4H1pRWN4a8Sad4q0aHU9Nl3xPwyEYaNh1Vh2P/1j0NbNABRRRQBwPxd8S/8ACPeB7iOGQLeX5+zRAHkA/fYfRcjPYkV4J8N/DZ8UeNrGzdS1tC32i4448tSCQfqcD8a1/jH4m/t7xtLawvm000G3QDoXz85/Pj6KK9J+BXhv+zvDE+tzRlZ9QfEZPURKcDH1bJ+gFID1nGAABgCiiimBwHxe8Nf8JB4GuJIkDXWnn7TEe5UffA+q5OO5ArwP4d+I/wDhF/Gthfu2LZ28i45wPLbAJP0OD+FfXJUMpVgCD1BHWvkDx54cbwv4y1DTQuIA5ktzjgxtyuPoOPqDQwPsHIIBHQ0Vw3wn8SnxJ4GtTK+67sv9Fm55O0Da34qR+INdzQAUUUUAFFFFAHmrf8nCx/8AYFP/AKEa9KrzVv8Ak4WP/sCn/wBCNelUIAooooAKKKKACisnRdSvtRa/F7pcliLe6eGEu2fPjHSQcDAPpzWtQAUUUUAFFFFABRWPpWrXl3Nqgv8ATXsIbO4aOGWVuJ4wM+YMgYH51b0rVLPWdOi1Cwm861l3eXIAQGwSCRntkGgC7RRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAeTftAKT4LsGHQX6gn6o9cB8CZAnxCZT1kspVH1BU/0Ner/GfTTqHw4vHUZe0ljuAB6A7T+jE/hXg3w11UaP8AEPRrp2CxtN5LknAxICuT7AsD+FID66ooopgFFFFABRRVXUL2HTdOub64bbDbxNLIfQKCT/KgD5B8ausnjnXnU5U6hOQfX5zX058M42j+G2hK3BNsG/AkkfoRXybdXEuoahPcsMy3ErSEDnJYk4H4mvsvw9YNpXhvTNPYAPb2scTAeoUA/rmkgNOiiimB5P8AtAf8iTYf9hBf/Rb1598Cv+Sif9ucv81r0H9oD/kSbD/sIL/6LevPvgV/yUT/ALc5f5rSA+maKKKYBXzd8YvAP/CP6mdc02LGm3j/AL1FHEMp5P0U8kdgcj0r6RqlqumWms6ZcadfRCW2uEKSIe49R7g8g9iAaAPBfgv4+/sq+Xw3qUuLK6fNq7HiKQ/w+wY/kfqa+h6+O/GXhW78G+JJtOn3GMHzLebGPMjJ4I9x0I7EGvfPhL49HirRf7Pv5R/a1koDknmaMcB/cjoffB70IDkv2if9d4f/AN24/mlRfs8H/iYa8O/lQ/zapf2if9d4f/3bj+aVD+zx/wAhPXf+uMX/AKE1LqB73RRRTAraj/yC7v8A64v/AOgmviaL/Xp/vD+dfbOo/wDILu/+uL/+gmviaL/Xp/vD+dDA+3oP+PeP/dH8qkqOD/j3j/3R/KpKACiiigAooooAr3tja6jaSWt7bx3FvIMPHIoZWHuDXzj8U/hiPCpGr6QHbSZXCvGTk27Hpz1KnoCeh4PUV9LVz/je0hvvA2twTqGQ2cjcjoVUsD+BAP4UAfPfwg8UyeH/ABnBZySkWOosIJVJ43k4RvYgkDPoTX1JXxDZStDqFtIhIZJVZSOxBBFfbqnKg+oBoQC1znjnxGvhXwhf6oGAmRNluCAcyNwvHfBOT7A10dfPXx58TfbdbtfD9vJmKyXzZwOhlYcA/Rf/AEI0AeQu7SyNI7FnYksxOSSTkkmvUbH46a3pthb2NtpGmJBbxrFGuJOFAAH8XoKPhX8MrLxjp97qOrtcJapIIoPJYKWYDLEkg5ABA+pPpXoX/ChvCX/PbUv+/wCv/wATSA4X/hoHxF/0C9M/75k/+Ko/4aB8Rf8AQL0z/vmT/wCKruv+FDeEv+e2pf8Af9f/AImj/hQ3hL/ntqX/AH/X/wCJpgcL/wANA+Iv+gXpn/fMn/xVcZ408cXfji6tbm+sbS3mt0MYe3DAspOQDknoc4+pr23/AIUN4S/57al/3/X/AOJpr/AbwoY2CT6irkYUmZSAexI280gPN/gn4lGi+Mv7OmcLbamoh5OAJQcofxyR9WFfTVfFF5a3ega5NbSEx3dlOVJHZlbgj8QCK+vPCWvR+JvC1hq0e0NPEDIoP3XHDD8CDTQG3RRRQAUUUUAeat/ycLH/ANgU/wDoRr0qvNW/5OFj/wCwKf8A0I16VQgOS+Jeo3mkfD3Vb7T7h7e6iWMxyIcFcyKDj8CR+NUfHWsahp3wlm1Ozu5Ib4W9uwmQjcCzICfxBP510Pi3QV8TeFtQ0ZpfK+1R4WTGQrAhlJHcZAzXDa14V8d+IfBf/CO3UujW8cUcaeZHJIxuNhGAcr8owMnAJJAHAzQBY8Sajr8vjPwrpOl6q9kt/ZyNO+wMOFBLAHgsBnGeASCQak0+41fw18TrPw9PrN5qmnajZvMpvSrSRSLknDADggdPf2rWu/C9/N408N6wrwfZ9MtZIZlLHcWZcArxgjPqRUupeG726+I+jeIY3hFnZWssMisx3lmBxgYwRzzzQBx0mr+JLvwn4uv7PVLj7XpGtzNCMggwxkZjIx93bk49q2vE3ia61a08LWHh+7kguNdlSYywkbo7dV3SHnoRn9CK2PCXhm50RfECX5glj1HUp7pFQkjy3xgNkDnGcjn61ieBvh7feG9fubzULuK4traJ7bS0QkmOJpGclsgYPIHBPU89KAMSfxadd8U6zb3viLU9H07Tpza20WnQMzyspwzuwRuMjgccH80k8Y64/wALfEc7Xc4v9MuFig1DyTE08ZdQr7SBgkZB4rpB4b8ReHPE2qal4bNhdWOqSefcWV5I0Zjl5yyMARgkknI9u2an1/QvEvibwFqelag2mR6jdOvkrAziKNAynDMQSTweQMcjigCpb2Pi230G48RnWLi91OawaSLSwi+QjsAVCjqSoyOTyf1peAr+LV7i0lfxlqkmsIu6+0u7CqNxB3KIyoIAJ4KnsM9a71oL630BbeyaD7dHbqkZmz5e8DHOOcZ9Oa4+Twz4i1/xZo2razbaTYLpkhkL2cjSSznGApJUYX2Oe/rQBm2V1qniG28f2c+sXkSWV7IsDREZWNVb5BkH5Tjnv71T8F6LrB+Dn2rS9cv0vJbZntoQV2RMkjHCgDPzYIOSetdb4b8I3emXnixr6WIw6zdvLF5TEsqMGHOQMHntn60zwDoviXw3YQ6JqQ019MtEkEM8DuZXJfIyCAAMFs856e9AGNq/je81P4daHJo85j1rW5Y7WNoxgxyA4lOOwBBH0INek28RgtoomkeQogUu5yzYGMn3NeTeBtBgu/ijrl7aytLo+k3MotFIwq3EoHmBfUDBH4g16/QAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAVdQsYdT025sLlQ0NzE0Tj1VgQf0NfG+vaNdeHNfvNLugVmtZSuRwGHVWHsRgj619pV5n8V/hz/wltkNT0xANXtUI29PtCDnbn+8OcH3IPbAwNT4Y+NIfF3hqMSSD+0rRVjuk6FjjhwPQ4/A5FdxXxfpWrat4T1sXVnJLZ31uxVlZSDwcFWU9RxyDXvHhf45aJqMMcOuo+m3eMNIFLwsfUEZK59CDj1ouB6xRWJB4w8M3CBovEGmMMZ/4+0B/InNQXvjzwpp8bSXHiHTsKORHOHb8ApJP4CgDoq8Z+OHjeK20/8A4Rexm3XM5DXhU/cjHIT6k4JHoPeqXjD47I0Eln4WhfewKm9nXG33VT39z09K8i0zS9W8Va4La0jmvL64cszEknJPLMT0HOSTQB0Xwp8MP4k8b2hZGNnZMLm4bHGFOVUn3bAx6A+lfV1ct4E8G2vgrw+ljFtkupCHup8YMj+3sOgH1PUmupoAKKKKAPJ/2gP+RJsP+wgv/ot68++BX/JRP+3OX+a16D+0B/yJNh/2EF/9FvXn3wK/5KJ/25y/zWkB9M0UUUwCiiigDj/iJ4Kg8aeHXtwFXUIMyWkp7N/dJ9DjB/A9q+YdL1HVPCHiWO7hDW99ZSlXjcY5BwysPQjINfZ1eLfGvwCbuBvFOmQ5nhUC9jQcuo4D4HcDg+wz2NDA5r4weI7PxVovhbVbFvklScPGTkxuPLyp9wfzGD3rT/Z4/wCQnrv/AFxi/wDQmrxcuxjCFmKgkhc8AnGSB6nA/IV7R+zx/wAhPXf+uMX/AKE1ID3uiiimBW1H/kF3f/XF/wD0E18TRf69P94fzr7Z1H/kF3f/AFxf/wBBNfE0X+vT/eH86GB9vQf8e8f+6P5VJUcH/HvH/uj+VSUAFFQ3c4tbKe4bpDG0h+gBP9K+aPCHxj1zw6VtdQzqeng8JI+JIx/svzx7HI7DFAH07RXE6J8V/CGtRAjVEspscw3n7sj8T8p/AmuiHiXQWj8wa1ppTuwuo8fnmgDUrhPi1r8eh+Ab5N4FzfL9lhUnk7uGOPZc8+pHrTvEPxY8KaDbsV1BNQuMfLDZsJCT7sPlH4n8DXzr4x8Zal401g318QkaArBboSViXPQepPc9/YAAAFbwlpMuueLdK06JS3nXKBvZQcsfwAJ/CvszpXj/AMF/AM2kW7eI9VhaO7uE220TjBjjPViOxbt6D617BQgM/WtUg0TRb3U7k/ubWJpWHrgcAe5OAPc18c3lzeeIdemuHDTXl9cFto5LMzcAfiQB+Fe3fHvxMbfTrPw7bvh7k+fcgH+BT8oP1YE/8BFcj8EfDX9seLzqkybrbTFEgz0MrZCj8ME/UCkB754U0KLw14YsNJjA/cRASEfxOeWP4kmtqiimAUUUUAFFFFAHzv8AHnw39i8QW2vQp+5vlEcxA6SqMAn6rj/vk1f+AfiUx3N94cncBZB9ptgTj5hgOB9Rg/ga9R+IHhseKfBl/p6qDcKvnW+RyJFGRj68j6E18q+H9Yn8O+IrLVYQRJazBip43AHDKfqCR+NID7SoqtY3sGo2Fve2zh4LiNZY2HQqQCD+tWaYBRRRQB5q3/Jwsf8A2BT/AOhGvSq81b/k4WP/ALAp/wDQjXpVCAKK5fx/4lk8J+Dr3VbdA9ym2OEMOAzEAE+uMk474xXOX/hvxTpfhuXW08YalLrMEBuJYZCptWwNzII8YA4IBzQB1HjLxSvhTR4rsWjXlxcXCW1vbq23zJGzgZwcDg9qv6Fc6vd2LPrOnRWF0HIEcU/mqVwMHOB3yMe1eU+M55PFWgeCdfXULu2F/qFtEbeNgEikJbMi8E7gQQCSRgDivYNPtXsrCG2kuprp41CmeYgu/ucADP4UAWqK8p8SavcRePLq08Ra5q2h6J5cY06ey/dxSMR83mSYOCDkYOB9O+j4m1nVLODwz4c0TVzLeaxIU/tVwrsIlAJYYG0kg8Eenqc0Aei0V5f4ij1r4dwWeuQ+I9R1OwFwkV9bagwkyrHG5DgFSD2HqPTB3LHVL2T4uappjXMjWMWmRSpAT8quWAJHuRQB2lZ+naxY6sbz7DOJvslw1tMQDhZFAyMnrjIGR71x0N5qWpfFHxHoR1O6gtE02JoREwzC7BcsuQQDyeuetZfwn0eeK5127Or37pBq91C1uzL5cpGB5jDGSxznIIHHSgDtPB/iVvFWjSag1qLbZcywbA+/Ow4znA6/StHWNGs9e09rG/WRoGYMRHK0ZyORypB/WvK/h/4e1TW/DN6yeIb7TLdL64FtHYsFO/cSWkJBLDJACggYHqa7T4a63f6/4MgudTkEt5HLJBJJgDeVbAJA4yRigDe0XQ9O8PaclhpdqtvbKSQgJJJPUkkkkn1JrSrm/GUsMWjo9x4ifQofOHmXEe3e64PyKSDgk4OQCeK4vw3qd1/wncnh+01/Wb3TLvTHmSXUI2WaKTIAZGdQSMHIOCM/SgDpLfxhquqeLLzS9H0RLiw0+4WC8vJLkRkMfvbVwc7ee/OO2RXaV4/4B0m5tdW8YXa61qDmyv7iNo2Zds7BSN78ZLDg5GBkdKv/AA8tPEfiXRNK8Qan4mvUVHOy1iwElRWIbze7EkEewxigDu9D1HUNRjvTqOltp7Q3Twwqz7vOjGNsg4GAcnjnp1rWryS28b6ppXgPxdq80zXd1a61NaWgl5CA7Ao9wNxOO/Sup0Lwlq9rNaahqfivVrq9GHnt/MUWzEjlQgHAB6EEdKAOyory/wATzW/9qakB461lNQjDPDZaZGXS3wMgSLGjE89SxHWuo+Hus3ev+BNL1K/cPdSxsJHAA3FWK5wOOcA0AdRRRRQAUUUUAFFFFABRRRQAUUUUAcb4x+G2heMVMtxGbXUAMLdwABj6Bh0YfXn0IrxjW/gd4q053bTxBqUAyVMThHx7qxHPsCa+maKAPjmbwP4qt2Ky+HtSB9rZiPzAIqa0+Hvi++cLB4ev+TgGSIxj8S2APzr7AoosB88eH/gHrF1Ismu3sVjDwTHCRJIfUZ+6Prk/Sva/DXhLRvCdibbSbRYt2PMlb5pJCO7MeT9OnoBW5RQAUUUUAFFFFAHBfFjwpqfi/wAM2tjpKRNPHdrMwkcKNoVgefXJFcl8Lvhp4i8KeL/7S1SO3W3+zPHmOYMdxIxwPoa9qooAKKKKACiiigApjosiFHUMrDBBGQc9afRQB89+Kvgfq51+4l8PLbtp0p3okku0xE9V56gHofQj0rr/AIR+Atb8HXuqS6vHAq3Ecax+XIGJIJJzj6ivVaKLAFFFFAEF5E01lPEmN7xsoye5BFfNcfwR8ZLMrGCzwGBP+kD1+lfTdFADYxtiVT1AANOoooAjmijuIZIZVDxyKVdT0IPBB/A15F4l+A2mXrtcaBeNYSEEi3mBkiJ9j95R+f0r2GigD5T1T4ReM9LYkaUbuMHAe0kEmfwyG/SsI+DPE6vsPh7Uw2cY+yv/AIV9k0UWA+T9J+E3jLVnGNIe0jJGZLthGB+B+Y/gDXsHgr4M6V4cmjv9VkXUr9CGQFcRRH2B+8QehPscA16hRRYAooooA+fvGHww8b+KfFV/q7QWipNIREpuRlYwMKOnXAGfcmvUfhv4RPg7wpFYzhPt0rma5ZDkbjwAD3AAA+uT3rsKKACiiigAooooAKKKKACvn/xl8GNdvfFl/eaJHbGxuJPNQPKEKluWGMdAc49iK+gKKAPHPBnhr4n+G3srF7qzOkRzL5kLyq5WPcNwU4yOM4Getex0UUAFFFFAHGN4b1A/FtfEYWP+zxppti2/5t+4nGPTHeuzoooAxfFPh618U+HLzR7piiTr8sijJRgcqw9cEDjuMiuTn0P4g32hf8I7c3+kJaMnkS6jH5hmeLGDhSMBiOCc+v1r0aigDiNc8EPcaJ4Y0rSnijh0e/t5285iC0cYIOMA5Y5z25Jrt6KKAOR8Q2/i68e+srO00K6064XZGbtpAyZUA7lAIbnJGMdqx5fhrPF4T0GzsNTEes6I5mtbt0JQsTllIzwpOB3OB05xXo1FAHnt74Z8VeLbqxg8TS6Za6VazLPJb2JdmuXXoCWAwvXjn+RFrWfDuv2vjceJ/D0llK01qLW5tbxmVSAchlZQeenGO3fPHcUUAcL4Z8Ka5p/jjU/EOr3tpcNfWqRlYAwEbAj5QCPugAAHOT1IFHhzw94j8N6/qUUL6bNol9fyXrSOzidd45UADHBxyT0Hvx3VFAHL+BfDt34Z0CWwvJInla7lmBiJI2s2R1A5xR4D8O3fhfw42n3skLzG5llzCSVwzZAyQDn8K6iigDj/ABn4a1DWb7RdU0x7V7rSrhpRbXefKlBAB5AOCMZBweaq2HhzxHJ8QbfxPqsuniMWT2xt7dnJiBOQASBu55JOOvA457qigDkPD3ha90qTxS08kDf2tey3EGxj8qsCAGyOD64zVzwLoN14Z8G2GkXjxST24YM0RJU5YkYyAeh9K6OigDz2z+Hck3hPxJoeqTxhdU1OW8ilgyxjDbCpIIHIK8jp71oaJF49s3tLLUTotxaREJLdq8glkjHfbjG4j3xmuyooA8403wl4o0Rda03T59MNnqVzJOL+YsZ4w/UFAMMQOmSBk59q6PwLoN14Z8HWGkXjxPPbBwzRElTl2YYyAeh9K6SigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooA//9k=";
		}
		public float getReportPDFMargin() {
			return reportPDFMargin;
		}
		public void setReportPDFMargin(float reportPDFMargin) {
			this.reportPDFMargin = reportPDFMargin;
		}
		public float getReportPDFCellMargin() {
			return reportPDFCellMargin;
		}
		public void setReportPDFCellMargin(float reportPDFCellMargin) {
			this.reportPDFCellMargin = reportPDFCellMargin;
		}
		public int getReportPDFColumnSize1() {
			return reportPDFColumnSize1;
		}
		public void setReportPDFColumnSize1(int reportPDFColumnSize1) {
			this.reportPDFColumnSize1 = reportPDFColumnSize1;
		}
		public int getReportPDFColumnSize2() {
			return reportPDFColumnSize2;
		}
		public void setReportPDFColumnSize2(int reportPDFColumnSize2) {
			this.reportPDFColumnSize2 = reportPDFColumnSize2;
		}
		public int getReportPDFColumnSize3() {
			return reportPDFColumnSize3;
		}
		public void setReportPDFColumnSize3(int reportPDFColumnSize3) {
			this.reportPDFColumnSize3 = reportPDFColumnSize3;
		}
		public int getReportPDFColumnSize4() {
			return reportPDFColumnSize4;
		}
		public void setReportPDFColumnSize4(int reportPDFColumnSize4) {
			this.reportPDFColumnSize4 = reportPDFColumnSize4;
		}
		public int getReportPDFHeaderSize() {
			return reportPDFHeaderSize;
		}
		public void setReportPDFHeaderSize(int reportPDFHeaderSize) {
			this.reportPDFHeaderSize = reportPDFHeaderSize;
		}
		public String getReportPDFToDownload() {
			return reportPDFToDownload;
		}
		public void setReportPDFToDownload(String reportPDFToDownload) {
			this.reportPDFToDownload = reportPDFToDownload;
		}
		public String getReportPDFToPrint() {
			return reportPDFToPrint;
		}
		public void setReportPDFToPrint(String reportPDFToPrint) {
			this.reportPDFToPrint = reportPDFToPrint;
		}
		public String getValidationValues() {
			return validationValues;
		}
		public void setValidationValues(String validationValues) {
			this.validationValues = validationValues;
		}
		public String getValidationResult() {
			return validationResult;
		}
		public void setValidationResult(String validationResult) {
			this.validationResult = validationResult;
		}
		public String getBookInOutURL() {
			return bookInOutURL;
		}
		public void setBookInOutURL(String bookInOutURL) {
			this.bookInOutURL = bookInOutURL;
		}
		public String getWebServiceURL() {
			return webServiceURL;
		}
		public void setWebServiceURL(String webServiceURL) {
			this.webServiceURL = webServiceURL;
		}
		public String getConsignmentNumber() {
			return consignmentNumber;
		}
		public void setConsignmentNumber(String consignmentNumber) {
			this.consignmentNumber = consignmentNumber;
		}
		public ArrayList getDisplayDockets() {
			return displayDockets;
		}
		public void setDisplayDockets(ArrayList displayDockets) {
			this.displayDockets = displayDockets;
		}
		public ArrayList getDisplayDctAssignments() {
			return displayDctAssignments;
		}
		public void setDisplayDctAssignments(ArrayList displayDctAssignments) {
			this.displayDctAssignments = displayDctAssignments;
		}
		public ArrayList getDisplayUniqueNumbers() {
			return displayUniqueNumbers;
		}
		public void setDisplayUniqueNumbers(ArrayList displayUniqueNumbers) {
			this.displayUniqueNumbers = displayUniqueNumbers;
		}
		public List<Consignment> getMasterConsignmentList() {
			return masterConsignmentList;
		}
		public void setMasterConsignmentList(List<Consignment> masterConsignmentList) {
			this.masterConsignmentList = masterConsignmentList;
		}
		public List<Docket> getMasterDocketList() {
			return masterDocketList;
		}
		public void setMasterDocketList(List<Docket> masterDocketList) {
			this.masterDocketList = masterDocketList;
		}
		public Map<String, String> getMapUnqAssignments() {
			return mapUnqAssignments;
		}
		public void setMapUnqAssignments(Map<String, String> mapUnqAssignments) {
			this.mapUnqAssignments = mapUnqAssignments;
		}
		public Map<String, String> getProcessDocketList() {
			return processDocketList;
		}
		public void setProcessDocketList(Map<String, String> processDocketList) {
			this.processDocketList = processDocketList;
		}
		public Map<String, String> getProcessDocketAssignmentList() {
			return processDocketAssignmentList;
		}
		public void setProcessDocketAssignmentList(
				Map<String, String> processDocketAssignmentList) {
			this.processDocketAssignmentList = processDocketAssignmentList;
		}
		public Map<String, String> getProcessAssignmentList() {
			return processAssignmentList;
		}
		public void setProcessAssignmentList(Map<String, String> processAssignmentList) {
			this.processAssignmentList = processAssignmentList;
		}
		public String getUser_Selection() {
			return user_Selection;
		}
		public void setUser_Selection(String user_Selection) {
			this.user_Selection = user_Selection;
		}

}