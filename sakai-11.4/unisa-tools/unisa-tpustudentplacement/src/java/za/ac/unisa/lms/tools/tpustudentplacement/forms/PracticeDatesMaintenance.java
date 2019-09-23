package za.ac.unisa.lms.tools.tpustudentplacement.forms;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import za.ac.unisa.lms.tools.tpustudentplacement.dao.PracticePeriodDAO;
import za.ac.unisa.lms.tools.tpustudentplacement.utils.DateUtil;

public class PracticeDatesMaintenance extends PracticePeriodDAO{

	    public void  saveCopyOfProvDateBatches(int selectedProv,List<Province> provList )throws Exception{
	    	                                            List copiedPracPeriodList=new ArrayList<PracticeBatchDate>();
	    	                                            DateUtil dateUtil=new DateUtil();
	                                                    int year=dateUtil.getYearInt();
	                                                    List<PracticeBatchDate>  provPracPeriodList=getAllDateBatchesForAprov( year,selectedProv);
	                                                    removeBatchesOfNonSelectedProvinces(provPracPeriodList,selectedProv);
	                                                      deleteAllForAyearExceptProv(year,selectedProv);
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
	    private List getAllDateBatchesForAprov(int year,int prov)throws Exception{
	    	                                         PracticeBatchDate practiceBatchDate=new  PracticeBatchDate();
                                                   practiceBatchDate.setAcademicYear(year);
                                                   practiceBatchDate.setProvCode(prov);
                                                   practiceBatchDate.setLevel(-1);
                                                   practiceBatchDate.setPracticalPeriod(-1);
	    	                                      return  getPracticePeriodList(practiceBatchDate);
	   }
	    private void removeBatchesOfNonSelectedProvinces(List<PracticeBatchDate>  provsPracPeriodList,int selectedProv){
	    	                                             List pracPeriodToBeDeletedList=new ArrayList<PracticeBatchDate>();
                                                         for(PracticeBatchDate practicePeriod:provsPracPeriodList){
                                        	                              if( practicePeriod.getProvCode()!=selectedProv){
                                     	                                                   PracticeBatchDate newPracticePeriod=new PracticeBatchDate(practicePeriod);
                                                                                           pracPeriodToBeDeletedList.add(newPracticePeriod);
                                                                          }
                                                         }
                                                         provsPracPeriodList.removeAll(pracPeriodToBeDeletedList);
       }
	       public void initialiseDataForMaintenanceFunction(StudentPlacementForm studentPlacementForm,   HttpServletRequest request){
		                                                    PracticePeriod  practicePeriod=new PracticePeriod();
		                                                    practicePeriod.setPracticePeriods(studentPlacementForm);
		                                                    Level level=new Level();
		                                                    level.setPracticeLevels(studentPlacementForm);
		                                                    PracticeBatchDate practiceBatchDate=new PracticeBatchDate();
		                                                    studentPlacementForm.setPracticeBatchDate(practiceBatchDate);
		                                                    PracticalYear practicalYear =new  PracticalYear ();
		                                                    practicalYear.setListPracticalYear(studentPlacementForm);
		                                                    request.setAttribute("startDate",practiceBatchDate.getFromDate());
	                                                        request.setAttribute("endDate",practiceBatchDate.getToDate());
	       }
	 
}
