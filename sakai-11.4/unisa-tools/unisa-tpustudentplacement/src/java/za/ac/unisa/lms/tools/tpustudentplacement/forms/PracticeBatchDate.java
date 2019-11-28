package za.ac.unisa.lms.tools.tpustudentplacement.forms;

import za.ac.unisa.lms.tools.tpustudentplacement.utils.DateUtil;

public class PracticeBatchDate {
	 private int provCode;
	  private String provDescr;
	private int level;
	private int practicalPeriod;
	private String startDate;
	private String endDate;
	private int academicYear;
	private int practicalDays;
	private String practicalDateRange;
	private int index;
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
	public String getStartDate() {
		return startDate;
	}
	public void seStartDate(String startDate) {
		this.startDate = startDate;
	}
	public String getEndDate() {
		return endDate;
	}
	public void setEndDate(String endDate) {
		this.endDate = endDate;
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
   	DateUtil dateUtil;
	public  PracticeBatchDate(PracticeBatchDate practiceBatchDate){
		                 this.academicYear=practiceBatchDate.getAcademicYear();
		                 this.startDate=practiceBatchDate.getStartDate();
		                 this.level=practiceBatchDate.getLevel();
		                 this.practicalDays=practiceBatchDate.getPracticalDays();
		                 this.practicalPeriod=practiceBatchDate.getPracticalPeriod();
		                 this.provCode=practiceBatchDate.getProvCode();
		                 this.provDescr=practiceBatchDate.getProvDescr();
		                 this.endDate=practiceBatchDate.getEndDate();
		}
	public  PracticeBatchDate(){
		              dateUtil=new DateUtil();
		              setAcademicYear(dateUtil.getYearInt());
		              this.level=1;
		              this.practicalPeriod=1;
		              this.startDate=dateUtil.todayDateOnly();
		              this.endDate=dateUtil.todayDateOnly();
	   }
	public void resetToDisplayAllDateBatches(){
	                                setAcademicYear(dateUtil.getYearInt());
 	                                 seStartDate(null);
 	                                 setEndDate(null);
 	                                 setLevel(-1);
                                     setPracticalPeriod(-1);
 	                                 setProvCode(-1);
	}
	public String  getPracticalDateRange() {
		return practicalDateRange;
	}
	public void setPracticalDateRange(String practicalDateRange) {
		this.practicalDateRange = practicalDateRange;
	}
	public int getIndex() {
		return index;
	}
	public void setIndex(int index) {
		this.index = index;
	}
	
	public static boolean batchesHasSameKey(PracticeBatchDate storedPracticePeriod,PracticeBatchDate practicePeriod){
		     if(storedPracticePeriod.academicYear==practicePeriod.getAcademicYear()&&
		           storedPracticePeriod.startDate==practicePeriod.getStartDate()&&
		           storedPracticePeriod.level==practicePeriod.getLevel()&&
		          storedPracticePeriod.practicalPeriod==practicePeriod.getPracticalPeriod()&&
		          storedPracticePeriod.provCode==practicePeriod.getProvCode()){
		    	 return  true;
		     }else{ 
		    	       return false;
		     }
   	}
	public  boolean isAllSelected(){
		 if((academicYear==-1)||(level==-1)||
		          (practicalPeriod==-1)||
		             (provCode==-1)){
		    	 return  true;
		     }else{ 
		    	       return false;
		     }
		
	}
}
	  

