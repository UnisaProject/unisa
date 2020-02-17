package za.ac.unisa.lms.tools.tpustudentplacement.model.modelImpl.studentPlacementImpl;
import java.util.Iterator;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import za.ac.unisa.lms.tools.tpustudentplacement.dao.StudentPlacementDAO;
import za.ac.unisa.lms.tools.tpustudentplacement.forms.StudentPlacement;
import za.ac.unisa.lms.tools.tpustudentplacement.forms.StudentPlacementForm;
import za.ac.unisa.lms.tools.tpustudentplacement.forms.StudentPlacementListRecord;
import za.ac.unisa.lms.tools.tpustudentplacement.model.Coordinator;
import za.ac.unisa.lms.tools.tpustudentplacement.uiLayer.CommunicationUI;
import za.ac.unisa.lms.tools.tpustudentplacement.utils.DateUtil;

public class StudentPlacementImpl extends StuPlacementCRUDHelperClass{
	
	           StudentPlacementDAO dao;
	           public StudentPlacementImpl(){
		                             dao=new StudentPlacementDAO();
		     }
	   	      public List getPlacementListForSupervEmail(int supervisorCode) throws Exception {
		                       return dao.getPlacementListForSupervEmail(supervisorCode);
	          }
	   	     public boolean isDateBlockAssigned(String fromDate,String toDate) throws Exception {
	   		                                return dao. isDateBlockAssigned(fromDate,toDate);
              }
	          public List getPlacementListForSuperv(int supervisorCode) throws Exception {
		                       return dao.getPlacementListForSuperv(supervisorCode);	
              }
	          public List getPlacementList(Short acadYear, Short semester, Short province,
			                        Short district,Integer supervisor, Integer school,
			                        String module,String sortOn,String country,String town) throws Exception {
		                                  return dao.getPlacementList(acadYear, semester, province, 
		                        		              district, supervisor,school, module, sortOn, country,town);
	         }
	          public List getPrelimPlacementList(Short acadYear, Short semester, Short province,
                      Short district,Integer supervisor, Integer school,
                      String module,String sortOn,String country,String town) throws Exception {
                            return dao.getPrelimPlacementList(acadYear, semester, province, 
                  		              district, supervisor,school, module, sortOn, country,town);
          }
           public StudentPlacement getStudentPlacement(Short acadYear,Short semester,Integer studentNr,
			                       String module, Integer school,int pracprd) throws Exception {
		                                   return dao.getStudentPlacement(acadYear,semester,studentNr,module,school,pracprd);
	        }
	         public void updateStudentPlacement(Short acadYear, Short semester, Integer studentNr, 
			              String originalModule, Integer originalSchool, StudentPlacement placement,
			               int originalSupCode) throws Exception {
		                                dao.updateStudentPlacement(acadYear, semester, studentNr, originalModule, originalSchool, placement, originalSupCode);
		   }
		   public void insertStudentPlacement(Short acadYear, Short semester, Integer studentNr,
			             StudentPlacement placement) throws Exception {
		                              dao.insertStudentPlacement(acadYear, semester, studentNr, placement);
	       }
	       public void updateEmailToSupField(int supervCode) throws Exception{
		                    dao.updateEmailToSupField(supervCode);
           }
	       public StudentPlacement getPlacementFromPlacementListRecord(StudentPlacementListRecord splr){
	    	                          StudentPlacement placement=new StudentPlacement();
	    	                          placement.setSchoolCode(splr.getSchoolCode());
	    				              placement.setModule(splr.getModule());
	    				              placement.setSupervisorCode(splr.getSupervisorCode());
	    				              placement.setStartDate(splr.getStartDate());
	    				              placement.setEndDate(splr.getEndDate());
	    				              placement.setNumberOfWeeks(splr.getNumberOfWeeks());
	    				              placement.setStartDateSecPracPeriod(splr.getStartDateSecPracPeriod());
	    				              placement.setEndDateSecPracPeriod(splr.getEndDateSecPracPeriod());
	    				              placement.setNumberOfWeeksSecPracPrd(splr.getNumberOfWeeksSecPracPrd());
	    				              placement.setPlacementPrd(splr.getPlacementPrd());
	    				              placement.setEvaluationMark(splr.getEvaluationMark());	
	    				              placement.setTwoPlacements(splr.isTwoPlacements());
	    				            return placement;
	       }
	       public boolean isPlacementExisting(Short acadYear, Short semester, 
	                              Integer studentNr, StudentPlacement placement)throws Exception{
	    	                      return  dao.isPlacementExisting(acadYear, semester, studentNr, placement.getSchoolCode(), placement.getPlacementPrd(), placement.getModule());
	       }
	       public  void replaceDummySupervWithCoordForProv(StudentPlacementForm studentPlacementForm)throws Exception{
	    	                      CommunicationUI communicationUI=new CommunicationUI();
	    	                      List  placementList=communicationUI.getStuPlacementsForSchool(studentPlacementForm);
                                  Iterator i=placementList.iterator();
	    	                      while(i.hasNext()){
	    	                	          StudentPlacementListRecord  placement=(StudentPlacementListRecord)i.next();
	    	                	          replaceDummySupervWithCoordForProv(placement);
     	                         }
	       }
           public  void replaceDummySupervWithCoordForProv(StudentPlacementListRecord placement)throws Exception{
        	                     if(placement.getSupervisorCode()==283){
           		                          Coordinator coordinator=new Coordinator();
        	                              coordinator=coordinator.getCoordForProvGivenSchoolCode(placement.getSchoolCode());
        	                              placement.setSupervisorName(coordinator.getName());
        	                              placement.setSupervisorContactNumber(coordinator.getContactNumber());
        	                      }
          }
           public void setDatesToRequest(HttpServletRequest request,StudentPlacement stuPlacement){
       		                                 DateUtil dateUtil=new DateUtil();
                                             String startDate=stuPlacement.getStartDate();
                                             String endDate=stuPlacement.getEndDate();
                                             String startDateSecPracPrd=stuPlacement.getStartDateSecPracPeriod();
                                             String endDateSecPracPrd=stuPlacement.getEndDateSecPracPeriod();
                                             if(startDate==null||(startDate.trim().equals(""))){
                                                               stuPlacement.setStartDate(dateUtil.todayDateOnly());
                                                               startDate=dateUtil.todayDateOnly();
                                             }
                                              if(endDate==null||endDate.trim().equals("")){
                                                               stuPlacement.setEndDate(dateUtil.todayDateOnly());
                                                             endDate=dateUtil.todayDateOnly();
                                           }
                                           if(startDateSecPracPrd==null||(startDateSecPracPrd.trim().equals(""))){
                                                                    stuPlacement.setStartDateSecPracPeriod(dateUtil.todayDateOnly());
                                                                    startDateSecPracPrd=dateUtil.todayDateOnly();
                                             }
                                             if(endDateSecPracPrd==null||endDateSecPracPrd.trim().equals("")){
                                                                stuPlacement.setEndDateSecPracPeriod(dateUtil.todayDateOnly());
                                                                endDateSecPracPrd=dateUtil.todayDateOnly();
                                             }
                                             request.setAttribute("startDate",startDate);
                                             request.setAttribute("endDate",endDate);
                                             request.setAttribute("startDateSecPracPrd",startDateSecPracPrd);
                                             request.setAttribute("endDateSecPracPrd",endDateSecPracPrd);
           }
           public void setDatesFromRequest(HttpServletRequest request,StudentPlacement stuPlacement){
                                          if(request.getAttribute("startDate")!=null){
            	                                 stuPlacement.setStartDate(request.getAttribute("startDate").toString());
            	                                 stuPlacement.setEndDate(request.getAttribute("endDate").toString());
                                          }
                                          if(request.getAttribute("startDateSecPracPrd")!=null){
                                        	                stuPlacement.setStartDateSecPracPeriod(request.getAttribute("startDateSecPracPrd").toString());
                                        	                stuPlacement.setEndDateSecPracPeriod(request.getAttribute("endDateSecPracPrd").toString());
                                          }
         }
           protected void setTotNumberOfDays(StudentPlacement studentPlacement){
                                            DateUtil  dateUtil=new DateUtil();
                                                    int totDaysBetweenDates=dateUtil.totWeekDaysBetweenDates(studentPlacement.getStartDate(),studentPlacement.getEndDate());
            	                                     studentPlacement.setNumberOfWeeks(""+totDaysBetweenDates );
            	                                     if((studentPlacement.getStartDateSecPracPeriod()!=null)&&!studentPlacement.getStartDateSecPracPeriod().trim().isEmpty()){
            	                                               totDaysBetweenDates=dateUtil.totWeekDaysBetweenDates(studentPlacement.getStartDateSecPracPeriod(),studentPlacement.getEndDateSecPracPeriod());
            	                                               studentPlacement.setNumberOfWeeksSecPracPrd(""+totDaysBetweenDates ); 
            	                                     }
          }
           public void changeDateFormat(StudentPlacement studentPlacement){
        	                                       studentPlacement.setStartDate(DateUtil.dateFormatChange(studentPlacement.getStartDate(),"/"));
        	                                       studentPlacement.setEndDate(DateUtil.dateFormatChange(studentPlacement.getEndDate(),"/"));
        	                                       if((studentPlacement.getStartDateSecPracPeriod()!=null)&&(!studentPlacement.getStartDateSecPracPeriod().trim().isEmpty())){
        	                                                   studentPlacement.setStartDateSecPracPeriod(DateUtil.dateFormatChange(studentPlacement.getStartDateSecPracPeriod(),"/"));
        	                                                   studentPlacement.setEndDateSecPracPeriod(DateUtil.dateFormatChange(studentPlacement.getEndDateSecPracPeriod(),"/"));
        	                                       }
           }
           public void  setPacementDatesForView(StudentPlacement stuPlacement){
                                             stuPlacement.setStartDateView(stuPlacement.getStartDate());
                                             stuPlacement.setEndDateView(stuPlacement.getEndDate());
                                               stuPlacement.setStartDateSecPracPeriodView(stuPlacement.getStartDateSecPracPeriod());
                                             stuPlacement.setEndDateSecPracPeriodView(stuPlacement.getEndDateSecPracPeriod());
           }      
           public void initialiseNumOfWeeks(StudentPlacement stuPlacement){
        	                                if((stuPlacement.getNumberOfWeeks()==null)||
	                        		                     (stuPlacement.getNumberOfWeeks().equals("0"))){
        	                        	                          stuPlacement.setNumberOfWeeks("");
        	                                }
        	                                if((stuPlacement.getNumberOfWeeksSecPracPrd()==null)||
        	                        		               (stuPlacement.getNumberOfWeeksSecPracPrd().equals("0"))){
        	                        	                                 stuPlacement.setNumberOfWeeksSecPracPrd("");
     	                                   }
         }
}
