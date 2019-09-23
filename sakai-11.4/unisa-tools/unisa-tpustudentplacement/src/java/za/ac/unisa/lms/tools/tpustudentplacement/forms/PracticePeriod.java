package za.ac.unisa.lms.tools.tpustudentplacement.forms;

import java.util.ArrayList;
import java.util.List;

import org.apache.struts.util.LabelValueBean;

public class PracticePeriod {
	public  PracticePeriod() {
	}
	public  PracticePeriod(int period,String periodDescription) {
		this.period=period;
		this.periodDescription=periodDescription;
	}
	
	   private int period;
	   private String periodDescription;
	public int getPeriod() {
		return period;
	}
	public void setPeriod(int period) {
		this.period = period;
	}
	public String getPeriodDescription() {
		return periodDescription;
	}
	public void setPeriodDescription(String periodDescription) {
		this.periodDescription = periodDescription;
	}
	
	public List getPracticePeriodList(int totalPeriods){
		                            List  listPracticePeriod=new ArrayList();
                                     for(int x=1;x<=totalPeriods;x++){
                                              listPracticePeriod.add(new  PracticePeriod(x,""+x));
                                     }
	                                  return   listPracticePeriod;
    }
	public List getPracticePeriodList(int totalPeriods,String defaultString,int defaultValue)  {
		                          List  listPracticePeriod=new ArrayList();
                                   for(int x=1;x<=totalPeriods;x++){
                                	               listPracticePeriod.add(new  PracticePeriod(x,""+x));
                                   }
                                   listPracticePeriod.add(0,new  PracticePeriod(defaultValue,defaultString));
                                  return  listPracticePeriod;
    }
	public void setPracticePeriods(StudentPlacementForm studentPlacementForm)  {
                                     	  studentPlacementForm.setListPracticalPeriods(getPracticePeriodList(2));
     	                                  studentPlacementForm.setListPracticalPeriodsforview(getPracticePeriodList(2,"All",-1));
    }

}
