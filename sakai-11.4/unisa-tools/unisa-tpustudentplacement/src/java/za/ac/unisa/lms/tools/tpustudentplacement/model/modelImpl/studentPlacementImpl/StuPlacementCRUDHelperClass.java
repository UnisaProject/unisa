package za.ac.unisa.lms.tools.tpustudentplacement.model.modelImpl.studentPlacementImpl;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessages;
import za.ac.unisa.lms.tools.tpustudentplacement.forms.StudentPlacement;
import za.ac.unisa.lms.tools.tpustudentplacement.forms.StudentPlacementForm;
import za.ac.unisa.lms.tools.tpustudentplacement.forms.StudentPlacementListRecord;
import za.ac.unisa.lms.tools.tpustudentplacement.utils.StuPlacementActionValidator;

public  class StuPlacementCRUDHelperClass extends StuPlacementAdder{
	                
	                   StuPlacementUpdater stuPlacementUpdater;
	                   StuPlacementRemover  stuPlacementRemover;
	                   StuPlacementReader stuPlacementReader;
	                   public  StuPlacementCRUDHelperClass(){
         		                                     stuPlacementUpdater=new StuPlacementUpdater();
         		                                     stuPlacementRemover=new StuPlacementRemover();
         		                                     stuPlacementReader=new StuPlacementReader();
       	 	           }
                       public ActionMessages  saveStudentPlacement(StudentPlacementForm studentPlacementForm) throws Exception {
            	                                                          StuPlacementActionValidator stuPlacementValidator=new StuPlacementActionValidator();
                                                                          StudentPlacement studentPlacement= studentPlacementForm.getStudentPlacement();
                                                                          int academicYear=Integer.parseInt(studentPlacementForm.getAcadYear());
                                                                          ActionMessages messages =stuPlacementValidator.validateStuPlacement(studentPlacement,academicYear);
                                                                          if (!messages.isEmpty()) {
                                	                                                         studentPlacementForm.setPreviousPage(studentPlacementForm.getCurrentPage());  
                                	                                                         return messages;
                                                                          }
                                                                          if (studentPlacementForm.getStudentPlacementAction().equalsIgnoreCase("Add")){
                                	                                                          messages=addPlacement(studentPlacementForm);
                                                                          }
                                                                          if (!studentPlacementForm.getStudentPlacementAction().equalsIgnoreCase("add")){
                                    	                                                          stuPlacementUpdater.updatePlacement(studentPlacementForm, messages);
                                                                         }
                                                                        return messages;
                    }
                       public void removeStudentPlacement(StudentPlacementForm studentPlacementForm, ActionMessages messages) {
                    	                                        stuPlacementRemover.removeStudentPlacement(studentPlacementForm,messages);
                    }
   public void removeStuPlacement(StudentPlacementForm studentPlacementForm, ActionMessages messages) {
                    	                                        stuPlacementRemover.removeStuPlacement(studentPlacementForm,messages);
                    }
                    
                     public String removeStudentPlacement(ActionMapping mapping,
                    		                                                           HttpServletRequest request,
                                                                                       HttpServletResponse response,
                                                                                       StudentPlacementForm studentPlacementForm,
                                                                                        ActionMessages messages) throws Exception{
                    	                                                                          return stuPlacementRemover.removeStupdentPlacement(
                    	                                                                        		                    mapping,request, response,studentPlacementForm, messages);
                   }
                   public StudentPlacement getStudentPlacement(StudentPlacementForm studentPlacementForm,
       	                                                                                                                     StudentPlacementListRecord placementListRec){
                    	                                                                 return   stuPlacementReader.getStudentPlacement(studentPlacementForm,placementListRec);
                   }
                    public String listStudentPlacement(ActionMapping mapping, ActionForm form,
       				                                                                       HttpServletRequest request, HttpServletResponse response,
       				                                                                         ActionMessages messages) throws Exception {
                    	                                                                           return stuPlacementReader.listStudentPlacement(mapping,form,request,response,messages);
                    }
}