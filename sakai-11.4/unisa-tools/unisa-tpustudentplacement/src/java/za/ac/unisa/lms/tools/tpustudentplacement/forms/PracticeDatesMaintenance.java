package za.ac.unisa.lms.tools.tpustudentplacement.forms;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import za.ac.unisa.lms.tools.tpustudentplacement.dao.PracticePeriodDAO;
import za.ac.unisa.lms.tools.tpustudentplacement.utils.DateUtil;

public class PracticeDatesMaintenance extends PracticePeriodDAO{

	    public void  saveCopyOfProvDateBatches(List<PracticeBatchDate>  provPracPeriodList,int selectedProv,List<Province> provList )throws Exception{
	                                 List copiedPracPeriodList=new ArrayList<PracticeBatchDate>();
	                                 PracticeBatchDate pracPeriod= (PracticeBatchDate)provPracPeriodList.get(0);
	                                 Iterator iter=provList.iterator();
	                                 for(Province prov:provList){
		                                          if(prov.getCode()!=selectedProv){
			                                            for(PracticeBatchDate practicePeriod:provPracPeriodList){
				                                                 PracticeBatchDate newPracticePeriod=new PracticeBatchDate(practicePeriod);
				                                                 newPracticePeriod.setProvCode(prov.getCode());
				                                                 copiedPracPeriodList.add(newPracticePeriod);
			                                            }
		                                       }
			   
	                                 }
	                                 saveListOfPracticePeriods(copiedPracPeriodList);
		  
	         }
	   public void initialiseDataForMaintenanceFunction(StudentPlacementForm studentPlacementForm){
		                              PracticePeriod  practicePeriod=new PracticePeriod();
		                              practicePeriod.setPracticePeriods(studentPlacementForm);
		                              Level level=new Level();
		                              level.setPracticeLevels(studentPlacementForm);
		                              PracticeBatchDate practiceBatchDate=new PracticeBatchDate();
		                              DateUtil dateUtil=new DateUtil();
		                              practiceBatchDate.setAcademicYear(dateUtil.getYearInt());
	 }
	 
}
