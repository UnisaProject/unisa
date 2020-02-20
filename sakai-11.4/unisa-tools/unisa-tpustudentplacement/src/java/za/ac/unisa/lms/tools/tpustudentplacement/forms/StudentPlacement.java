package za.ac.unisa.lms.tools.tpustudentplacement.forms;
import za.ac.unisa.lms.tools.tpustudentplacement.model.modelImpl.studentPlacementImpl.StudentPlacementImpl;
import za.ac.unisa.lms.tools.tpustudentplacement.utils.DateUtil;
import za.ac.unisa.lms.tools.tpustudentplacement.utils.StudentPlacementValidator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
public class StudentPlacement extends StudentPlacementImpl{
	protected String module;
	protected Integer schoolCode;
	private Integer schoolCode2;
	private Integer mentorCode;
	protected String schoolDesc;
	protected Integer supervisorCode;
	protected String supervisorName;
	protected String startDate;
	protected String endDate;
	private String startDateSecPracPeriod;
	private String endDateSecPracPeriod;
	protected String numberOfWeeks;
	private String numberOfWeeksSecPracPrd;
	protected String evaluationMark;
	protected String stuNum;
	protected String name;
	private String studentContactNumber;
	private String schoolContactNumber;
	private Short districtCode;
	private String districtDescr;
	private String countryDescr;
	private String provinceDescr;
	private Short provinceCode;
	private String countryCode;
	private String town;
	private  StudentPlacementValidator studentPlacementValidator;
	private String stuFullTime;
	private String mentorName;
	private int placementPrd;
    private boolean twoPlacements;
	private String userSelectedDateBlock;
	private String startDateView;
	private String endDateView;
	private String startDateSecPracPeriodView;
	 private String endDateSecPracPeriodView;
	private boolean secPrelimPlacement;
	 public String getCountryCode(){
	    return countryCode;
    }
    public void setCountryCode(String countryCode){
	    this.countryCode=countryCode;
    }
	public Short getProvinceCode(){
		return provinceCode;
	}
	public void  setProvinceCode(Short provinceCode){
		    this.provinceCode=provinceCode;
	}
	StudentPlacementImpl  studentplacementImlp;
	public  StudentPlacement(){
	           	    studentPlacementValidator=new StudentPlacementValidator();
	}
	public  List validateStuPlacement( StudentPlacement  studentPlacement,int academicYear){
		             return studentPlacementValidator.validateStudentPlacement(studentPlacement,academicYear);
	}
    public String getDistrictDescr() {
		return districtDescr;
	}
	public Short getDistrictCode() {
		return districtCode;
	}
	public void setDistrictCode(Short districtCode) {
		this.districtCode = districtCode;
	}
	public void setDistrictDescr(String districtDescr) {
		this.districtDescr = districtDescr;
	}
	
    public String  getCountryDescr(){
	    return countryDescr;
    }
    public void setCountryDescr(String countryDescr){
	    this.countryDescr=countryDescr;
    }
    public String getProvinceDescr(){
	         return provinceDescr;
    }
    public void setProvinceDescr(String provinceDescr){
	           this.provinceDescr=provinceDescr;
    }
    public String getStudentContactNumber() {
		return studentContactNumber;
	}
	public void setStudentContactNumber(String studentContactNumber) {
		this.studentContactNumber = studentContactNumber;
	}
	public String getSchoolContactNumber() {
		return schoolContactNumber;
	}
	public void setSchoolContactNumber(String schoolContactNumber) {
		this.schoolContactNumber = schoolContactNumber;
	}
	public String  getName(){
		return name;
	}
	public void setName(String name){
		  this.name=name;
	}
	
	public void setStuNum(String stuNum){
		this.stuNum=stuNum;
	}
	public String getStuNum(){
		return stuNum;
	}
	public String getModule() {
		return module;
	}
	
	public Integer getSchoolCode() {
		return schoolCode;
	}

	public void setSchoolCode(Integer schoolCode) {
		this.schoolCode = schoolCode;
	}

	public Integer getSupervisorCode() {
		return supervisorCode;
	}

	public void setSupervisorCode(Integer supervisorCode) {
		this.supervisorCode = supervisorCode;
	}

	public void setModule(String module) {
		this.module = module;
	}	
	public String getSchoolDesc() {
		return schoolDesc;
	}
	public void setSchoolDesc(String schoolDesc) {
		this.schoolDesc = schoolDesc;
	}	
	public String getSupervisorName() {
		return supervisorName;
	}
	public void setSupervisorName(String supervisorName) {
		this.supervisorName = supervisorName;
	}
	public String getStartDate() {
		return startDate;
	}
	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}
	public String getEndDate() {
		return endDate;
	}
	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}
	public String getStartDateSecPracPeriod() {
		return startDateSecPracPeriod;
	}
	public void setStartDateSecPracPeriod(String startDateSecPracPeriod) {
		this.startDateSecPracPeriod = startDateSecPracPeriod;
	}
	public String getEndDateSecPracPeriod() {
		return endDateSecPracPeriod;
	}
	public void setEndDateSecPracPeriod(String endDateSecPracPeriod) {
		this.endDateSecPracPeriod = endDateSecPracPeriod;
	}
	public String getNumberOfWeeks() {
		return numberOfWeeks;
	}
	public void setNumberOfWeeks(String numberOfWeeks) {
		this.numberOfWeeks = numberOfWeeks;
	}
	public String getNumberOfWeeksSecPracPrd() {
		return numberOfWeeksSecPracPrd;
	}
	public void setNumberOfWeeksSecPracPrd(String numberOfWeeksSecPracPrd) {
		this.numberOfWeeksSecPracPrd = numberOfWeeksSecPracPrd;
	}
	public String getEvaluationMark() {
		return evaluationMark;
	}
	public void setEvaluationMark(String evaluationMark) {
		this.evaluationMark = evaluationMark;
	}
	public String getTown() {
		return town;
	}
	public void setTown(String town) {
		this.town = town;
	}
	public Integer getMentorCode() {
		return mentorCode;
	}
	public void setMentorCode(Integer mentorCode) {
		this.mentorCode = mentorCode;
	}
	public String getStuFullTime() {
		return stuFullTime;
	}
	public void setStuFullTime(String stuFullTime) {
		this.stuFullTime = stuFullTime;
	}
    public void setMentorName(String mentorName) {
		this.mentorName = mentorName;
	}
    public String  getMentorName() {
		             return this.mentorName;
	}
    public void setDatesToRequest(HttpServletRequest request){
                   setDatesToRequest( request,this);
	 }
      public  void setTotPracDays(){
    	                      setTotNumberOfDays(this);
       }
	public int getPlacementPrd() {
		return placementPrd;
	}
	public void setPlacementPrd(int placementPrd) {
		this.placementPrd = placementPrd;
	}
	public static void setDatesFromRequest(StudentPlacementForm studentPlacementForm,HttpServletRequest request){
   	                                       if(((studentPlacementForm.getLocalSchool().equals("Y"))&&(studentPlacementForm.getIsPGCE().equals("Y")))
   	                                                       ||(studentPlacementForm.getLocalSchool().equals("N"))){
   	                                    	                                 if(request.getParameter("startDate")!=null){
    	                                                                                  studentPlacementForm.getStudentPlacement().setStartDate(request.getParameter("startDate").toString());
   	                                    	                                 } 
   	                                    	                                 if(request.getParameter("endDate")!=null){
   	                                    	                                 studentPlacementForm.getStudentPlacement().setEndDate(request.getParameter("endDate").toString());
   	                                    	                              }
                                                                              if(studentPlacementForm.getStudentPlacementAction().equals("editPrelimPlacement")){
                                                                            	         if(request.getParameter("startDateSecPrd")!=null){
                                                                                                     studentPlacementForm.getStudentPlacement().setStartDateSecPracPeriod(request.getParameter("startDateSecPrd").toString());
                                                                                                     studentPlacementForm.getStudentPlacement().setEndDateSecPracPeriod(request.getParameter("endDateSecPrd").toString());
                                                                            	          }
                                                                               }
                                             }
   }
	public static void setDatesDataToRequest(StudentPlacementForm studentPlacementForm,HttpServletRequest request){
	                                                 	DateUtil   dateUtil =new DateUtil();
	                                                 	if(((studentPlacementForm.getLocalSchool().equals("Y"))&&(studentPlacementForm.getIsPGCE().equals("Y")))
                                            	                       ||(studentPlacementForm.getLocalSchool().equals("N"))){
                                                	                              if((studentPlacementForm.getStudentPlacement().getStartDate()==null)||
                                                	                            		        (studentPlacementForm.getStudentPlacement().getStartDate().trim().isEmpty())){
                                                	                            	                        request.setAttribute("startDate", dateUtil.dateOnly());
                                                	                               }else{
                                            	                                                   request.setAttribute("startDate",studentPlacementForm.getStudentPlacement().getStartDate());
                                                	                               }
                                                	                               if((studentPlacementForm.getStudentPlacement().getEndDate()==null)||
                                           	                            		                       (studentPlacementForm.getStudentPlacement().getEndDate().trim().isEmpty())){
                                                	                            	                               request.setAttribute("endDate", dateUtil.dateOnly());
                                                	                               }else{
                                           		                                                        request.setAttribute("endDate",studentPlacementForm.getStudentPlacement().getEndDate());
                                                	                               }
                                                                                                                 if((studentPlacementForm.getStudentPlacement().getStartDateSecPracPeriod()==null)||
                                          	                            		                                                      (studentPlacementForm.getStudentPlacement().getStartDateSecPracPeriod().trim().isEmpty())){
                                          	                            	                                                               request.setAttribute("startDateSecPrd", dateUtil.dateOnly());
                                          	                                                                      }else{
                                      	                                                                                   request.setAttribute("startDateSecPrd",studentPlacementForm.getStudentPlacement().getStartDateSecPracPeriod());
                                          	                                                                      }
                                                                            	                                 if((studentPlacementForm.getStudentPlacement().getEndDateSecPracPeriod()==null)||
                              	                            		                                                           (studentPlacementForm.getStudentPlacement().getEndDateSecPracPeriod().trim().isEmpty())){
                              	                            	                                                                                   request.setAttribute("endDateSecPrd", dateUtil.dateOnly());
                              	                                                                                     }else{
                          	                                                                                              request.setAttribute("endDateSecPrd",studentPlacementForm.getStudentPlacement().getEndDateSecPracPeriod());
                              	                                                                        }
                                                }
       }
	public String getUserSelectedDateBlock() {
		return userSelectedDateBlock;
	}
	public void setUserSelectedDateBlock(String userSelectedDateBlock) {
		this.userSelectedDateBlock = userSelectedDateBlock;
	}
	public String getStartDateView() {
		return startDateView;
	}
	public void setStartDateView(String startDateView) {
		this.startDateView = startDateView;
	}
	public String getEndDateView() {
		return endDateView;
	}
	public void setEndDateView(String endDateView) {
		this.endDateView = endDateView;
	}
	public String getStartDateSecPracPeriodView() {
		return startDateSecPracPeriodView;
	}
	public void setStartDateSecPracPeriodView(String startDateSecPracPeriodView) {
		this.startDateSecPracPeriodView = startDateSecPracPeriodView;
	}
	public String getEndDateSecPracPeriodView() {
		                    return endDateSecPracPeriodView;
	}
	public void setEndDateSecPracPeriodView(String endDateSecPracPeriodView) {
		                   this.endDateSecPracPeriodView = endDateSecPracPeriodView;
	}
	public boolean isSecPrelimPlacement() {
		return secPrelimPlacement;
	}
	public void setSecPrelimPlacement(boolean secPrelimPlacement) {
		this.secPrelimPlacement = secPrelimPlacement;
	}
	public boolean isTwoPlacements() {
		              return twoPlacements;
	}
	public void setTwoPlacements(boolean twoPlacements) {
		                this.twoPlacements = twoPlacements;
	}
	public void  setPacementDatesForView(){
		                           setPacementDatesForView(this);
	}
	public Integer getSchoolCode2() {
		return schoolCode2;
	}
	public void setSchoolCode2(Integer schoolCode2) {
		this.schoolCode2 = schoolCode2;
	}
	  public void initialiseTotalPracticeDays(){
		            initialiseNumOfWeeks(this);
      }
}