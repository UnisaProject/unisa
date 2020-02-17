package za.ac.unisa.lms.tools.tpustudentplacement.forms;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import za.ac.unisa.lms.tools.tpustudentplacement.dao.PracticePeriodDAO;
import za.ac.unisa.lms.tools.tpustudentplacement.model.modelImpl.schoolImpl
.SchoolUI;
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
	    private List getPracticalDateBatches(int year,int prov,int level,int pracPrd,StudentPlacementForm studentPlacementForm)throws Exception{
                                                 PracticeBatchDate practiceBatchDate=new  PracticeBatchDate();
                                                 practiceBatchDate.setAcademicYear(year);
                                                 practiceBatchDate.setProvCode(prov);
                                                 practiceBatchDate.setLevel(level);
                                                 practiceBatchDate.setPracticalPeriod(pracPrd);
                                                 studentPlacementForm.setPracticeBatchDate(practiceBatchDate);
                                                 List  listPracticalDateBatche=getPracticePeriodList(practiceBatchDate);
                                                 StudentPlacement studentPlacement=studentPlacementForm.getStudentPlacement();
                                                 addPrelimSelectedDates(listPracticalDateBatche,practiceBatchDate,studentPlacement);
                                         return  listPracticalDateBatche;
       }
	    private void addPrelimSelectedDates(List  listPracticalDateBatche,PracticeBatchDate practiceBatchDate,StudentPlacement studentPlacement)throws Exception{
	    	                                                                                	                                                      practiceBatchDate.setPracticalDateRange( "--select ---");
	    	                                                                             listPracticalDateBatche.add(0, practiceBatchDate);
	   }
	  	    public  List getPracticalDateBatches(StudentPlacement studentPlacement,int pracPrd,StudentPlacementForm studentPlacementForm)throws Exception{
	                                        	int level=1;
	                                        	String moduleCode=studentPlacement.getModule().trim();
	    	                                    if((moduleCode!=null)&&(!moduleCode.equals(""))){
	    		                                                   Module module=new Module();
	    		                                                   Qualification qual=studentPlacementForm.getStudent().getQualification();
	    		                                                   int acadYear=Integer.parseInt(studentPlacementForm.getAcadYear());
	    		                                                   module=module.getModule(qual,moduleCode,acadYear);
	    		                                                   level=module.getLevel();
	    	                                   }
	    	                                    studentPlacementForm.setStudyLevel(level);
                                              SchoolUI  schoolUI=new SchoolUI();
                                              int provCode=1;
                                              if(studentPlacement.getSchoolCode()!=-1){
         	                                                provCode=schoolUI.getSchoolProvCode(studentPlacement.getSchoolCode());
                                              }
                                              DateUtil  dateUtil =new DateUtil ();
                                              int year=dateUtil.getYearInt();
                                              if( studentPlacementForm.getIsPGCE().equals("N")){
                                                             if((level==1)&&(pracPrd==2)){
         	                                                             return new ArrayList();
                                                             }
                                              }
                                           return  getPracticalDateBatches(year,provCode,level,pracPrd,studentPlacementForm);
       }
	    public void setPracDateBatcheLists(StudentPlacementForm  studentPlacementForm)throws Exception{
	    	                                                           StudentPlacement  studentPlacement=studentPlacementForm.getStudentPlacement();
			                                                           List pracDateBatchesPrd1=getPracticalDateBatches(studentPlacement,1,studentPlacementForm);
			                                                           List pracDateBatchesPrd2=getPracticalDateBatches(studentPlacement,2,studentPlacementForm);
	                                                                   studentPlacementForm.setPracticeBatchDateList(pracDateBatchesPrd1);
	                                                                   studentPlacementForm.setPracticeBatchDateSecPracPrdList(pracDateBatchesPrd2);
	   	}
	    private void removeBatchesOfNonSelectedProvinces(List<PracticeBatchDate>  provsPracPeriodList,int selectedProv){
	    	                                             List pracPeriodToBeDeletedList=new ArrayList<PracticeBatchDate>();
                                                         for(PracticeBatchDate practicePeriod:provsPracPeriodList){
                                      	                              if(practicePeriod.getProvCode()!=selectedProv){
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
		                                                    studentPlacementForm.getPracticeBatchDate().setProvCode(-1);
		                                                    studentPlacementForm.getPracticeBatchDate().setLevel(-1);
		                                                    studentPlacementForm.getPracticeBatchDate().setPracticalPeriod(-1);
      }
	   public void setDateBlockDefaults( HttpServletRequest request, StudentPlacementForm studentPlacementForm){
                                                          PracticeBatchDate practiceBatchDate=studentPlacementForm.getPracticeBatchDate();
                                                          String defaultDate=""+practiceBatchDate.getAcademicYear()+"/03/01";
                                                          if(practiceBatchDate.getPracticalPeriod()==2){
                                                                         defaultDate=""+practiceBatchDate.getAcademicYear()+"/07/01";
                                                          }
                                                          request.setAttribute("startDate", defaultDate);
                                                          request.setAttribute("endDate",  defaultDate);
                                                          practiceBatchDate.seStartDate(defaultDate);
                                                          practiceBatchDate.setEndDate(defaultDate);
      }
      public boolean checkDateBlockAssigned(StudentPlacementForm studentPlacementForm) throws Exception{
		                                          String array[] = studentPlacementForm.getIndexNrSelected();
		                                          boolean dateBlockAsigned=false;
	                                               for (int i=0; i <array.length; i++) {
                                                                     PracticeBatchDate practiceBatchDate =
               		                                                  (PracticeBatchDate)studentPlacementForm.getPracticeBatchDateList().get(Integer.parseInt(array[i]));	
                                                                      String startDate=practiceBatchDate.getStartDate();
                                                                      String endDate=practiceBatchDate.getEndDate();
                                                                      StudentPlacement studentPlacement=new StudentPlacement();
                                                                      dateBlockAsigned=studentPlacement.isDateBlockAssigned(startDate,endDate );
                                                                      if(dateBlockAsigned){
                                                                    	      dateBlockAsigned=true;
                                                                              break;
                                                                      }
                                                  }
	                                              return dateBlockAsigned;
	   }
      public String validateDates(int selectedAcadYear,String   startDateStr,String endDateStr) throws Exception{
                                            DateUtil dateUtil=new DateUtil();
                                            String returnMsg="";
                                            if(dateUtil.isAfterSecDate(startDateStr,endDateStr)){
                                            	      returnMsg="Start Date of a date block should be before the End Date of  a date block";
                                            }
                                            if(dateUtil.isDateEqual(startDateStr,endDateStr)){
                                            	       returnMsg="The a date block  cannot have same Start and End date";
                                            }
                                            if(!dateUtil.datesOfSameYear(startDateStr,endDateStr)){
                                            	 returnMsg="The  Start Date  and  End Date of a date block must be in the same year";
                                            }
                                            if((selectedAcadYear!=Integer.parseInt(startDateStr.substring(0, 4)))||
                                            		(selectedAcadYear!=Integer.parseInt(endDateStr.substring(0, 4)))){
                                            	           returnMsg="The  Start and  End dates of a date block must be in the  Academic Year";
                                           }
                                return returnMsg;
  }
  /*    
      public void addProvPracDateBatcheLists(int provCode)throws Exception{
    	             saveDateBlocks(List<PracticeBatchDate>  practicePeriodList)
    }*/
}