package za.ac.unisa.lms.tools.tpustudentplacement.model.modelImpl.studentPlacementImpl;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessages;

import za.ac.unisa.lms.tools.tpustudentplacement.dao.StudentPlacementDAO;
import za.ac.unisa.lms.tools.tpustudentplacement.forms.StudentPlacementListRecord;
import za.ac.unisa.lms.tools.tpustudentplacement.model.modelImpl.studentPlacementImpl.StudentPlacementLogUI;
import za.ac.unisa.lms.tools.tpustudentplacement.model.modelImpl.studentPlacementImpl.StudentPlacementUI;
import za.ac.unisa.lms.tools.tpustudentplacement.uiLayer.StudentPlacementListRecordUI;
import za.ac.unisa.lms.tools.tpustudentplacement.forms.StudentPlacement;
import za.ac.unisa.lms.tools.tpustudentplacement.forms.StudentPlacementForm;
import za.ac.unisa.lms.tools.tpustudentplacement.utils.StuPlacementActionValidator;
import za.ac.unisa.lms.tools.tpustudentplacement.utils.InfoMessagesUtil;

public class StuPlacementRemover {
	    
	         StudentPlacementDAO dao;
	         StuPlacementReader stuPlacementReader;
	         public StuPlacementRemover(){
                       dao=new  StudentPlacementDAO();
                       stuPlacementReader=new StuPlacementReader();
             }
	  	     public void removeStudentPlacement(Short acadYear,Short semester,Integer studentNr,
                               String module,int schoolCode,int  pracprd) throws Exception {
                               dao.removeStudentPlacement(acadYear, semester, studentNr,module,schoolCode, pracprd);
             }
	  	   public void removeStudentPlacement(StudentPlacementForm studentPlacementForm, ActionMessages messages) {
	  		                       List  placementList=studentPlacementForm.getListStudentPlacement();
                                                      
if((placementList!=null)&&(!placementList.isEmpty())){
 Iterator iter=placementList.iterator();
                                                       while(iter.hasNext()){
                                                               	StudentPlacementListRecord placementListRecord=(StudentPlacementListRecord)iter.next();
                                                                removeStudentPlacement(placementListRecord,studentPlacementForm, messages);
                                                           	    if (!messages.isEmpty()) {
                                                           	    	         break;
                                                           	    }else{
                                                           	    	         setLog(studentPlacementForm,placementListRecord,0);
                                                           	    }
                                                   }
}

           }
	  	   private void removeStudentPlacement(StudentPlacementListRecord placementListRecord,
	  			                              StudentPlacementForm studentPlacementForm,
	  			                              ActionMessages messages){
	  	    	                                 try{
	  		                                                int schoolCode=placementListRecord.getSchoolCode();
	  		                                String module=placementListRecord.getModule();
	  		                                short semester=Short.parseShort(studentPlacementForm.getSemester());
	  		                                short acadYear=Short.parseShort(studentPlacementForm.getAcadYear());
	  		                                int studentNr=Integer.parseInt(studentPlacementForm.getStudentNr());
	  		                                int pracprd=placementListRecord.getPlacementPrd();
	  		                               dao.removeStudentPlacement(acadYear, semester, studentNr,module,schoolCode, pracprd);
	  	    	                    }catch(Exception ex){
	  	    	                    	InfoMessagesUtil infoMessagesUtil=new InfoMessagesUtil();
	  	    	                    	String message="There was an error removing a placement: eror is" +ex.getMessage();
	  	    	                    	infoMessagesUtil.addMessages(messages, message);
                    	  	    	 }
             }
	  	     public String removeStupdentPlacement(ActionMapping mapping,
	                                   HttpServletRequest request,
	                                   HttpServletResponse response,
	                                   StudentPlacementForm studentPlacementForm,
	                                   ActionMessages messages) throws Exception{
	  		                                  
	  	    	                          StuPlacementActionValidator validator=new StuPlacementActionValidator();
                                          String []  indexArr=studentPlacementForm.getIndexNrPlacementSelected();
                                           validator.validateIndexForRemovingPlacement(indexArr, messages);
                                          StudentPlacementListRecordUI stuPlacListRecUI=new StudentPlacementListRecordUI();
                                          StudentPlacementListRecord placementListRecord =
                                        		  stuPlacListRecUI.getSelectedPlacemenRecord(studentPlacementForm);
                                          String nextPage="listStudentPlacement";
                                          validateEvalMarkForRemovePlacement(studentPlacementForm,placementListRecord,messages);
                                          if (messages.isEmpty()) {
              	                                    removeStudentPlacement(placementListRecord,studentPlacementForm,messages);
                                                    studentPlacementForm.setPreviousPage(studentPlacementForm.getCurrentPage());
                                                    studentPlacementForm.setCommunicationSchool(placementListRecord.getSchoolCode());
                                                    nextPage=stuPlacementReader.listStudentPlacement(mapping, studentPlacementForm,    
                                                    		 request, response,messages);
                                         }
                                         if (messages.isEmpty()) {
                                        	       setLog(studentPlacementForm,placementListRecord);
                                         }
                                 return nextPage;
                }
	  	        private void validateEvalMarkForRemovePlacement(StudentPlacementForm studentPlacementForm,
                                               StudentPlacementListRecord placementListRecord,ActionMessages messages){
                                               if (messages.isEmpty()) {
                    	                                  StuPlacementActionValidator validator=new StuPlacementActionValidator();
                                                         String evalMark=placementListRecord.getEvaluationMark();
                                                         validator.validateEvalMark(evalMark, messages);
                                              }
               }
	  	        private void setLog(StudentPlacementForm studentPlacementForm,
	  	        		                                       StudentPlacementListRecord placementListRecord){
	  	        	                                                    try{
	  	        	                                                                    StudentPlacementLogUI  splUI=new StudentPlacementLogUI();
	  	        	                                                                    int persno=Integer.parseInt(studentPlacementForm.getPersonnelNumber());
	  	        	                                                                     splUI.setLogOnDeletePlacement(studentPlacementForm,persno);
	  	        	                                                    }catch(Exception ex){}
	  	      }
	  	      private void setLog(StudentPlacementForm studentPlacementForm,
                                                              StudentPlacementListRecord placementListRecord,int personnelNum){
                                                                    try{
                                                                                    StudentPlacementLogUI  splUI=new StudentPlacementLogUI();
                                                                                    splUI.setLogOnDeletePlacement(studentPlacementForm,personnelNum);
                                                                    }catch(Exception ex){}
          }
public void removeStuPlacement(StudentPlacementForm studentPlacementForm,
	  			                              ActionMessages messages){
	  	    	                                 try{
StudentPlacement placementRecord=studentPlacementForm.getStudentPlacement();
	  		                                String module=placementRecord.getModule();
	  		                                short semester=Short.parseShort(studentPlacementForm.getSemester());
	  		                                short acadYear=Short.parseShort(studentPlacementForm.getAcadYear());
	  		                                int studentNr=Integer.parseInt(studentPlacementForm.getStudentNr());
	  		                                int pracprd=placementRecord.getPlacementPrd();
	  		                               dao.removeStudentPlacement(acadYear, semester, studentNr,module);
	  	    	                    }catch(Exception ex){
	  	    	                    	InfoMessagesUtil infoMessagesUtil=new InfoMessagesUtil();
	  	    	                    	String message="There was an error removing a placement: eror is" +ex.getMessage();
	  	    	                    	infoMessagesUtil.addMessages(messages, message);
                    	  	    	 }
             }
}