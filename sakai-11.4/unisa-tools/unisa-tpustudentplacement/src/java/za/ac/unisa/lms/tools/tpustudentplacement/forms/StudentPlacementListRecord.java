package za.ac.unisa.lms.tools.tpustudentplacement.forms;

public class StudentPlacementListRecord {
	private String module;
	private Integer schoolCode;
	private String schoolDesc;
	private Integer supervisorCode;
	private String supervisorName;
	private String supervisorContactNumber;
	private String startDate;
	private String endDate;
	private String startDateSecPracPeriod;
	private String endDateSecPracPeriod;
	private String numberOfWeeks;
	private String numberOfWeeksSecPracPrd;
	private String evaluationMark;
	private String town;
	private Integer mentorCode;
	private String stuFullTime;
	private String mentorName;
	private int placementPrd;
	private int stuNr;
	private short semester;
	private int acadYear;
	
	  public  StudentPlacementListRecord (StudentPlacement  studentPlacement){
	                          	this.module=studentPlacement.getModule();
		                       this.schoolCode=studentPlacement.getSchoolCode();
		                       this.supervisorCode=studentPlacement.getSupervisorCode();
		                       this.placementPrd=studentPlacement.getPlacementPrd();
		                       this.setStuNr(Integer.parseInt(studentPlacement.getStuNum()));
		                       this.setAcadYear(studentPlacement.getAcadYear());
		                       this.semester=studentPlacement.getSemester();
	   }
	  public  StudentPlacementListRecord (){
        	
    }
	   private boolean twoPlacements;
	   public String getModule() {
		                       return module;
	}
	public void setModule(String module) {
		                       this.module = module;
	}
	public Integer getSchoolCode() {
		return schoolCode;
	}
	public void setSchoolCode(Integer schoolCode) {
		this.schoolCode = schoolCode;
	}
	public String getSchoolDesc() {
		return schoolDesc;
	}
	public void setSchoolDesc(String schoolDesc) {
		this.schoolDesc = schoolDesc;
	}
	public Integer getSupervisorCode() {
		return supervisorCode;
	}
	public void setSupervisorCode(Integer supervisorCode) {
		this.supervisorCode = supervisorCode;
	}
	public String getSupervisorName() {
		return supervisorName;
	}
	public void setSupervisorName(String supervisorName) {
		this.supervisorName = supervisorName;
	}
	public String getSupervisorContactNumber() {
		return supervisorContactNumber;
	}
	public void setSupervisorContactNumber(String supervisorContactNumber) {
		this.supervisorContactNumber = supervisorContactNumber;
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
public String getNumberOfWeeks() {
		return numberOfWeeks;
	}
	public void setNumberOfWeeks(String numberOfWeeks) {
		this.numberOfWeeks = numberOfWeeks;
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
	public String getMentorName() {
		return mentorName;
	}
	public void setMentorName(String mentorName) {
		this.mentorName = mentorName;
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
	public String getNumberOfWeeksSecPracPrd() {
		return numberOfWeeksSecPracPrd;
	}
	public void setNumberOfWeeksSecPracPrd(String numberOfWeeksSecPracPrd) {
		this.numberOfWeeksSecPracPrd = numberOfWeeksSecPracPrd;
	}
	public int getPlacementPrd() {
		return placementPrd;
	}
	public void setPlacementPrd(int placementPrd) {
		this.placementPrd = placementPrd;
	}
	public boolean isTwoPlacements() {
		return twoPlacements;
	}
	public void setTwoPlacements(boolean twoPlacements) {
		this.twoPlacements = twoPlacements;
	}
	public int getStuNr() {
		return stuNr;
	}
	public void setStuNr(int stuNr) {
		this.stuNr = stuNr;
	}
	public short getSemester() {
		return semester;
	}
	public void setSemester(short semester) {
		this.semester = semester;
	}
	public int getAcadYear() {
		return acadYear;
	}
	public void setAcadYear(int acadYear) {
		this.acadYear = acadYear;
	}
}
