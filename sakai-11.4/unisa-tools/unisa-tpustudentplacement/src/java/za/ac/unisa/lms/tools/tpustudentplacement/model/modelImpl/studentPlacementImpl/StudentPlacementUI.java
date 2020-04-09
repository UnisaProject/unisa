package za.ac.unisa.lms.tools.tpustudentplacement.model.modelImpl.studentPlacementImpl;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionMessages;

import za.ac.unisa.lms.tools.tpustudentplacement.dao.StudentPlacementDAO;
import za.ac.unisa.lms.tools.tpustudentplacement.forms.PlacementListRecord;
import za.ac.unisa.lms.tools.tpustudentplacement.forms.Qualification;
import za.ac.unisa.lms.tools.tpustudentplacement.forms.StudentPlacement;
import za.ac.unisa.lms.tools.tpustudentplacement.forms.StudentPlacementForm;
import za.ac.unisa.lms.tools.tpustudentplacement.forms.StudentPlacementListRecord;
import za.ac.unisa.lms.tools.tpustudentplacement.model.modelImpl.schoolImpl.SchoolUI;
import za.ac.unisa.lms.tools.tpustudentplacement.uiLayer.StudentPlacementEditUI;
import za.ac.unisa.lms.tools.tpustudentplacement.utils.PlacementUtilities;
import za.ac.unisa.lms.tools.tpustudentplacement.utils.InfoMessagesUtil;

public class StudentPlacementUI  extends StudentPlacementImpl{
	
	public void epiloueAddEditPlacementScreen(StudentPlacementForm studentPlacementForm,  HttpServletRequest request){
		                       studentPlacementForm.setSchoolCalledFrom("editStudentPlacement");
                                       studentPlacementForm.setPreviousPage(studentPlacementForm.getCurrentPage());
                                       studentPlacementForm.setCurrentPage("editStudentPlacement");
                                       StudentPlacement.setDatesDataToRequest(studentPlacementForm ,request);
 }
	
         public void   initialiseNewPlacement(StudentPlacementForm studentPlacementForm,List moduleList)  throws Exception{
         /*initialising   the new placement has been moved from  StudentPlacementAction to StudentPlacmentUI
         -this is required to  avoid duplicate code in the  event handler for   
         adding  placements using date blocks and adding placements without using date blocks
         a new method to be created in StudentPlacmentUI  for intitialization  mentioned above
         -this methods assumes a new placement will stored in the placement variable of the  studentPlacementForm,
         hence the  input parameter is  of type studentPlacementForm
         -for initialization we use the  first placement form the placements  the student already has,
         else we just initialise the module to the first module  the student is registered for
           */
        	                      StudentPlacement placement=studentPlacementForm.getStudentPlacement();
        	                      if (studentPlacementForm.getListStudentPlacement().size()>0){
                                          	for (int i=0; i < studentPlacementForm.getListStudentPlacement().size(); i++){
                                                                 StudentPlacementListRecord record = new StudentPlacementListRecord();
                                                                 record=(StudentPlacementListRecord)studentPlacementForm.getListStudentPlacement().get(i);
                                                                 placement.setModule(record.getModule());
                                                                 placement.setSchoolCode(record.getSchoolCode());
                                                                 placement.setSchoolCode2(record.getSchoolCode());
                                                                 placement.setSchoolDesc(record.getSchoolDesc());
                                                                 placement.setSupervisorCode(record.getSupervisorCode());
                                                                 placement.setSupervisorName(record.getSupervisorName());
                                                                 placement.setStartDate("");
                                                                 placement.setEndDate("");
                                                                 placement.setStartDateSecPracPeriod("");
                                                                 placement.setEndDateSecPracPeriod("");
                                                                 placement.setNumberOfWeeks("");
                                                                  placement.setNumberOfWeeksSecPracPrd("");
                                                        break;
                                                 }
                               }else{
        	                                    placement.setSchoolCode(-1);
                                                placement.setModule(moduleList.get(0).toString());
                            }
        	                      
        	                      Integer  schoolCode=placement.getSchoolCode();
        	                      if((schoolCode!=null)&&(schoolCode!=-1)){
        	       			                       SchoolUI schoolUI=new  SchoolUI();
        	       			                       String town=schoolUI.getTown(schoolCode);
        	       			                       placement.setTown(town);
        	       		       }
        	                    if(studentPlacementForm.getStudent().getCountryCode().trim().equals("1015")){
                                                            Short districtCode=studentPlacementForm.getStudent().getDistrictCode();
                                                            Short provCode=studentPlacementForm.getStudent().getProvinceCode();
                                                            placement.setProvinceCode(provCode);
        	                    }
        	                      
         }
	      public void   createAddPlacementScreen(StudentPlacementForm studentPlacementForm){
		                 StudentPlacement placement = new StudentPlacement();			
			            //initialise values
			            studentPlacementForm.setStudentPlacement(placement);
			          studentPlacementForm.setStudentPlacementAction("add");	
			          studentPlacementForm.setSchoolCalledFrom("editStudentPlacement");
			          studentPlacementForm.setPreviousPage(studentPlacementForm.getCurrentPage());
			          studentPlacementForm.setCurrentPage("editStudentPlacement");
	       }
	        public void editplacement(StudentPlacementForm studentPlacementForm,ActionMessages messages )throws Exception{
	    	                StudentPlacementEditUI speui=new StudentPlacementEditUI();
	    	                speui.editplacement(studentPlacementForm, messages);
                              studentPlacementForm.setModuleCode(studentPlacementForm.getStudentPlacement().getModule());

	        }
	        public ActionMessages  saveStuPlacement(StudentPlacementForm studentPlacementForm)throws Exception{
                                       StuPlacementCRUDHelperClass stuPlacementCRUDHelperClass=new StuPlacementCRUDHelperClass();
                                       if(studentPlacementForm.getStudentPlacementAction().equals("Add")){
                                             if( checkForDuplicatePlacement(studentPlacementForm)){
                                            	 InfoMessagesUtil msgUtil=new InfoMessagesUtil();
                                            	 ActionMessages  messages=new ActionMessages();
                                            	 String message="The placement already exists";
                                            	 msgUtil.addMessages(messages, message);
                                            	 return messages;
                                             }
                                        }
                                       return stuPlacementCRUDHelperClass.saveStudentPlacement(studentPlacementForm);
	        }
	        public void setStuPlacementList(StudentPlacementForm studentPlacementForm){
	                                        try{
	                                                             StudentPlacementDAO dao = new StudentPlacementDAO();
                                                                 List studentPlacementList = dao.getStudentPlacementList(Short.parseShort(studentPlacementForm.getAcadYear()),
				                                                 Short.parseShort(studentPlacementForm.getSemester()),Integer.parseInt(studentPlacementForm.getStudentNr().trim()));
                                                               studentPlacementForm.setListStudentPlacement(studentPlacementList);
	                                       }catch(Exception ex){}
            }
	        private boolean checkForDuplicatePlacement(StudentPlacementForm studentPlacementForm)throws Exception {  
	        	                 return isPlacementExisting(Short.parseShort(studentPlacementForm.getAcadYear())
	        	                		 ,Short.parseShort(studentPlacementForm.getSemester()), 
	        	                		 Integer.parseInt(studentPlacementForm.getStudentNr()), studentPlacementForm.getStudentPlacement());
	        }
	        public void setPlacementList(StudentPlacementForm studentPlacementForm,Short province) throws Exception {     
                                StudentPlacement stuPlacement = new StudentPlacement();
                                List list = new ArrayList<PlacementListRecord>();
                                list = stuPlacement.getPlacementList(Short.parseShort(studentPlacementForm.getAcadYear()), 
                                  Short.parseShort(studentPlacementForm.getSemester()), 
                                  province, 
                                  studentPlacementForm.getPlacementFilterDistrict(), 
                                  studentPlacementForm.getPlacementFilterSupervisor(), 
                                  studentPlacementForm.getPlacementFilterSchool(), 
                                  studentPlacementForm.getPlacementFilterModule(), 
                                  studentPlacementForm.getPlacementSortOn(),
                                  studentPlacementForm.getPlacementFilterCountry(),studentPlacementForm.getTown());
                                  studentPlacementForm.setListPlacement(list);
          }
	        public void setPrelimPlacementList(StudentPlacementForm studentPlacementForm,Short province) throws Exception {     
                StudentPlacement stuPlacement = new StudentPlacement();
                List list = new ArrayList<PlacementListRecord>();
                list = stuPlacement.getPrelimPlacementList(Short.parseShort(studentPlacementForm.getAcadYear()), 
                  Short.parseShort(studentPlacementForm.getSemester()), 
                  province, 
                  studentPlacementForm.getPlacementFilterDistrict(), 
                  studentPlacementForm.getPlacementFilterSupervisor(), 
                  studentPlacementForm.getPlacementFilterSchool(), 
                  studentPlacementForm.getPlacementFilterModule(), 
                  studentPlacementForm.getPlacementSortOn(),
                  studentPlacementForm.getPlacementFilterCountry(),studentPlacementForm.getTown());
                  studentPlacementForm.setListPlacement(list);
}
	      public void initForIntCountry(StudentPlacementForm studentPlacementForm){
		                    if(!studentPlacementForm.getPlacementFilterCountry().equals(PlacementUtilities.getSaCode())){
     	                         studentPlacementForm.setSchoolFilterProvince(Short.parseShort("0"));
     	                         studentPlacementForm.setSchoolFilterCountry(studentPlacementForm.getPlacementFilterCountry());
     	                         studentPlacementForm.setPlacementFilterDistrict(Short.parseShort("0"));
     	                         studentPlacementForm.setPlacementFilterDistrictValue(null);
     	                         studentPlacementForm.setPlacementFilterProvince(Short.parseShort("0"));
     	                         studentPlacementForm.setSchoolCalledFrom("inputPlacement");
                            }
	    }
	      public void setFilters(StudentPlacementForm studentPlacementForm){
	                                      if(studentPlacementForm.getStudent().getCountryCode().trim().equals("1015")){
                                                                          Short districtCode=studentPlacementForm.getStudent().getDistrictCode();
                                                                          Short provCode=studentPlacementForm.getStudent().getProvinceCode();
                                                                          studentPlacementForm.setSchoolFilterProvince(provCode);
                                                                          studentPlacementForm.setSupervisorFilterProvince(provCode);
                                                                          studentPlacementForm.setSupervisorFilterDistrict(districtCode);
                                           }
	                                      StudentPlacement placement=studentPlacementForm.getStudentPlacement();
	                                      studentPlacementForm.setPlacementFilterCountry(placement.getCountryCode());
                                          studentPlacementForm.setSchoolFilterCountry(placement.getCountryCode());
                                           studentPlacementForm.setSupervisorFilterCountry(placement.getCountryCode());
      }
	  public void setFlagsForAddEditScreen(StudentPlacementForm studentPlacementForm){
		                                setFlags(studentPlacementForm);
                                              studentPlacementForm.getStudentPlacement().setTwoPlacements(false);
                          studentPlacementForm.setDisplaySecDatesBatch("N");
	                     	                                                      
    }
public void setFlags(StudentPlacementForm studentPlacementForm){
		                                      if(studentPlacementForm.getStudent().getCountryCode().trim().equals("1015")){
                                                        studentPlacementForm.setLocalSchool("Y");
                                             }else{
                                                           studentPlacementForm.setLocalSchool("N");
                                              }
		                                 Qualification qualification=studentPlacementForm.getStudent().getQualification();
                                              if(qualification.isPgceStudent()){
                                                                studentPlacementForm.setIsPGCE("Y");
                                                         }else{
                                                              studentPlacementForm.setIsPGCE("N");
                                              }
                                              
    }
 
}