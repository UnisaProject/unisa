package za.ac.unisa.lms.tools.tpustudentplacement.model.modelImpl.studentPlacementImpl;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.apache.struts.action.ActionMessages;
import za.ac.unisa.lms.tools.tpustudentplacement.forms.Qualification;
import za.ac.unisa.lms.tools.tpustudentplacement.forms.Module;
import za.ac.unisa.lms.tools.tpustudentplacement.forms.PracticeDatesMaintenance;
import za.ac.unisa.lms.tools.tpustudentplacement.forms.Student;
import za.ac.unisa.lms.tools.tpustudentplacement.forms.StudentPlacement;
import  za.ac.unisa.lms.tools.tpustudentplacement.forms.StudentPlacementForm;
import za.ac.unisa.lms.tools.tpustudentplacement.forms.StudentPlacementListRecord;
import za.ac.unisa.lms.tools.tpustudentplacement.utils.DateUtil;
import za.ac.unisa.lms.tools.tpustudentplacement.utils.PlacementUtilities;
import za.ac.unisa.lms.tools.tpustudentplacement.utils.StuEditWinValidator;;
public class StuPlacementEditWinBuilder {
	
	 public void buildEditWin(StudentPlacementForm studentPlacementForm,ActionMessages messages,   HttpServletRequest request )throws Exception{ 
		               StuEditWinValidator stuEditWinValidator=new StuEditWinValidator();
		               String[] indexArr=studentPlacementForm.getIndexNrPlacementSelected();
		               stuEditWinValidator.validateSelectedPlacement(messages, indexArr);
		               if(!messages.isEmpty()){
		            	   return;
		               }
		               StudentPlacementListRecordImpl spImpl=new  StudentPlacementListRecordImpl();
		               StudentPlacementListRecord studentPlacementListRecord =spImpl.getStuPlacementListRecord(studentPlacementForm);
		       		   StuPlacementReader stuPlacementReader=new StuPlacementReader();
		       		   StudentPlacement studentPlacement =stuPlacementReader.getStudentPlacement(studentPlacementForm, studentPlacementListRecord);
		       		   studentPlacementForm.setStudentPlacement(studentPlacement);
		       		 studentPlacement.setCountryCode(studentPlacementForm.getStudent().getCountryCode());
		    		studentPlacementForm.setPlacementFilterCountry( studentPlacement.getCountryCode());
		    		studentPlacementForm.setSchoolFilterCountry( studentPlacement.getCountryCode());
		    		 studentPlacementForm.setSupervisorFilterCountry( studentPlacement.getCountryCode());
		    	
		       		   String moduleCode=studentPlacement.getModule();
		       		   if((studentPlacement==null)||(moduleCode==null)||(moduleCode.equals(""))){
		       				  return;
		          	   }
		       		   setModulesForPrevYear(studentPlacementForm,moduleCode);
		       		   studentPlacementForm.setSupervisorCode(studentPlacement.getSupervisorCode());
		       		   studentPlacementForm.setStudentPlacement(studentPlacement);
		       		   studentPlacementForm.setStudentPlacementAction("edit");	
		       		if(studentPlacementForm.getStudent().getCountryCode().trim().equals("1015")){
                                                studentPlacementForm.setLocalSchool("Y");
                        }else{
                                              studentPlacementForm.setLocalSchool("N");
 	                     }
		       	          Module module=new Module();
	       	              Qualification qual=studentPlacementForm.getStudent().getQualification();
                          int acadYear=Integer.parseInt(studentPlacementForm.getAcadYear());
                          module=module.getModule(qual,moduleCode,acadYear);
                          studentPlacementForm.setStudyLevel(module.getLevel());
		       	   	      studentPlacementForm.setDisplaySecDatesBatch("N");
		       	          studentPlacementForm.getStudentPlacement().setTwoPlacements(false);
		       	          studentPlacementForm.setPracticeBatchDateListsIndex(-1);
                          studentPlacementForm.setPracticeBatchDateSecPracPrdListsIndex(-1);
                         if(studentPlacementForm.getStudent().getCountryCode().trim().equals("1015")){
                                 PracticeDatesMaintenance practiceDatesMaintenance=new PracticeDatesMaintenance();
                     	         practiceDatesMaintenance.setPracDateBatcheLists(studentPlacementForm);
                     	}
                     	studentPlacementForm.setSchoolFilterCountry(studentPlacementForm.getPlacementFilterCountry());
                      	studentPlacementForm.setSupervisorFilterCountry(studentPlacementForm.getPlacementFilterCountry());
            	         PlacementUtilities placementUtilities=new PlacementUtilities();
                         StudentPlacement placement=studentPlacementForm.getStudentPlacement();
                         placementUtilities.setPlacementDateToRequestObject(request, placement);
                         studentPlacementForm.setPreviousPage(studentPlacementForm.getCurrentPage());
		       		    studentPlacementForm.setCurrentPage("editStudentPlacement");
		       		    StudentPlacementImage imageBuilder=new StudentPlacementImage(studentPlacement);
		       		    studentPlacementForm.setPlacementImage(imageBuilder.getPlacementImage());
		       		    //studentPlacement.setTotPracDays();
		       		  	 if(qual.isPgceStudent()){
     	                                    studentPlacementForm.setIsPGCE("Y");
     	                 }else{
     	                                    studentPlacementForm.setIsPGCE("N");
                         }
		       		    studentPlacement.initialiseTotalPracticeDays();
                        studentPlacement .setPacementDatesForView();
		       	        StudentPlacement.setDatesDataToRequest(studentPlacementForm ,request);
	 }
	 private void setModulesForPrevYear(StudentPlacementForm studentPlacementForm,String module){
		                                               DateUtil   dateUtil=new DateUtil(); 
                                                       if(Integer.parseInt(studentPlacementForm.getAcadYear())<dateUtil.yearInt()){
 		                                                                     Student  student=studentPlacementForm.getStudent();
 		                                                                     List listPracticalModules=new ArrayList();
 		                                                                     listPracticalModules.add(module);
                                                                             student.setListPracticalModules(listPracticalModules);
                                                      }              
 	}

}