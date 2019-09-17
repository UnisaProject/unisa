package za.ac.unisa.lms.tools.tpustudentplacement.forms;

import za.ac.unisa.lms.tools.tpustudentplacement.utils.DateUtil;

public class PracticeBatchDate {
	 private int provCode;
	  private String provDescr;
	private int level;
	private int practicalPeriod;
	private String fromDate;
	private String toDate;
	private int numOfDays;
	private int academicYear;
	private int practicalDays;
	public int getProvCode() {
		return provCode;
	}
	public void setProvCode(int provCode) {
		this.provCode = provCode;
	}
	public String getProvDescr() {
		return provDescr;
	}
	public void setProvDescr(String provDescr) {
		this.provDescr = provDescr;
	}
	public int getLevel() {
		return level;
	}
	public void setLevel(int level) {
		this.level = level;
	}
	public int getPracticalPeriod() {
		return practicalPeriod;
	}
	public void setPracticalPeriod(int practicalPeriod) {
		this.practicalPeriod = practicalPeriod;
	}
	public String getFromDate() {
		return fromDate;
	}
	public void setFromDate(String fromDate) {
		this.fromDate = fromDate;
	}
	public String getToDate() {
		return toDate;
	}
	public void setToDate(String toDate) {
		this.toDate = toDate;
	}
	public int getNumOfDays() {
		return numOfDays;
	}
	public void setNumOfDays(int numOfDays) {
		this.numOfDays = numOfDays;
	}
	public int getAcademicYear() {
		return academicYear;
	}
	public void setAcademicYear(int academicYear) {
		this.academicYear = academicYear;
	}
	public int getPracticalDays() {
		return practicalDays;
	}
	public void setPracticalDays(int practicalDays) {
		this.practicalDays = practicalDays;
	}
	public  PracticeBatchDate(PracticeBatchDate practiceBatchDate){
		                 this.academicYear=practiceBatchDate.getAcademicYear();
		                 this.fromDate=practiceBatchDate.getFromDate();
		                 this.level=practiceBatchDate.getLevel();
		                  this.numOfDays=practiceBatchDate.getNumOfDays();
		                 this.practicalDays=practiceBatchDate.getPracticalDays();
		                 this.practicalPeriod=practiceBatchDate.getPracticalPeriod();
		                 this.provCode=practiceBatchDate.getProvCode();
		                 this.provDescr=practiceBatchDate.getProvDescr();
		                 this.toDate=practiceBatchDate.getToDate();
		}
	public  PracticeBatchDate(){
		              this.academicYear=0;
		              this.level=1;
		              this.practicalPeriod=1;
	   }
	public void resetToDisplayAllDateBatches(){
	                               	DateUtil dateUtil=new DateUtil();
                                     setAcademicYear(dateUtil.getYearInt());
 	                                 setFromDate(null);
 	                                 setToDate(null);
 	                                 setLevel(-1);
                                     setPracticalPeriod(-1);
 	                                 setProvCode(-1);
	}
	
}
	  

