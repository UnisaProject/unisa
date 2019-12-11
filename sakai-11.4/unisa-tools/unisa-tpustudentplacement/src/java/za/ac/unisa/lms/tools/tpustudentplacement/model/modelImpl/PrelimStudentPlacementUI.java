package za.ac.unisa.lms.tools.tpustudentplacement.model.modelImpl;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import za.ac.unisa.lms.tools.tpustudentplacement.forms.Module;
import za.ac.unisa.lms.tools.tpustudentplacement.forms.PlacementListRecord;
import za.ac.unisa.lms.tools.tpustudentplacement.forms.PracticeDatesMaintenance;
import za.ac.unisa.lms.tools.tpustudentplacement.forms.Qualification;
import za.ac.unisa.lms.tools.tpustudentplacement.forms.StudentPlacement;
import za.ac.unisa.lms.tools.tpustudentplacement.forms.StudentPlacementForm;
import za.ac.unisa.lms.tools.tpustudentplacement.model.modelImpl.studentPlacementImpl.StudentPlacementUI;
import za.ac.unisa.lms.tools.tpustudentplacement.uiLayer.StudentPlacementListRecordUI;
import za.ac.unisa.lms.tools.tpustudentplacement.uiLayer.StudentUI;
import za.ac.unisa.lms.tools.tpustudentplacement.utils.DateUtil;
import za.ac.unisa.lms.tools.tpustudentplacement.utils.PlacementUtilities;
import org.apache.struts.action.ActionMessages;
public class PrelimStudentPlacementUI{

	public void setPrelimPlacementScreen(StudentPlacementForm studentPlacementForm,HttpServletRequest request)
            throws Exception {
		                        PrelimStudentPlacementImpl  prelimPlacementImpl=new  PrelimStudentPlacementImpl();
		                        List   listOfPlacements=studentPlacementForm.getListPlacement();
		                        int pos=prelimPlacementImpl.getPosOfSelectedPlacement(listOfPlacements,request);
		                        setPrelimPlacementScreen(studentPlacementForm,pos, request);
                                setNavigationBtnsTrackingValues(studentPlacementForm,pos,listOfPlacements);
                                studentPlacementForm.setPosOfCurrPrelimPlacement(pos);
                                studentPlacementForm.setStudentPlacementAction("editPrelimPlacement");	
                                studentPlacementForm.setCurrentPage("editPrelimPlacement");
    }
	
    public void setNavigationBtnsTrackingValues(StudentPlacementForm studentPlacementForm,int pos,List listOfPlacements){
	                                               PrelimStudentPlacementImpl  prelimPlacementImpl=new  PrelimStudentPlacementImpl();
	                                               String prevBtnBoundryReached=prelimPlacementImpl.prevBtnBoundryReached(pos);
	                                               String nextBtnBoundryReached=prelimPlacementImpl.nextBtnBoundryReached(pos,listOfPlacements);
                                                   studentPlacementForm.setFirstPlacementReached(prevBtnBoundryReached);
                                                   studentPlacementForm.setLastPlacementReached(nextBtnBoundryReached);
    }
    public boolean setPrelimPlacementScreen(StudentPlacementForm studentPlacementForm,int pos,HttpServletRequest request) throws Exception {//pos is position of current placemnt
                                                          boolean endReached=false;
                                                          StudentPlacement stuPlacement=new StudentPlacement();
                                                          List   listOfPlacements=studentPlacementForm.getListPlacement();
                                                          if((pos!=-1)&&(pos!=listOfPlacements.size())){
                            	                                                          PlacementListRecord stuPlacementListRec=(PlacementListRecord)listOfPlacements.get(pos);
                            	                                                          StudentPlacementListRecordUI splr=new StudentPlacementListRecordUI();
                                                                                          splr.getStuPlacementFromPlacementListRec(stuPlacement,stuPlacementListRec);
                                                                                          studentPlacementForm.setStudentPlacement(stuPlacement);
                                                                                          PracticeDatesMaintenance practiceDatesMaintenance=new PracticeDatesMaintenance();
                                                                                          	stuPlacement.setCountryCode(studentPlacementForm.getPlacementFilterCountry());

                                                                                          	studentPlacementForm.setSchoolFilterCountry(studentPlacementForm.getPlacementFilterCountry());
                                                                                          	studentPlacementForm.setSupervisorFilterCountry(studentPlacementForm.getPlacementFilterCountry());

                                                                                	     if(studentPlacementForm.getPlacementFilterCountry().equals(PlacementUtilities.getSaCode())){
                                                                          		                               studentPlacementForm.setLocalSchool("Y");
                                                                          		           }else{
                                                                          			                            studentPlacementForm.setLocalSchool("N");
                                                                          	            	}
                                                                                	     	Module module=new Module();
                                                                                	     	module=module.getModule(stuPlacement.getModule());

                                                                                	     	 studentPlacementForm.setStudyLevel(module.getLevel());
                                                                                	     	  if(module.getLevel()==1){
                                                                        		       	    	           studentPlacementForm.setDisplaySecDatesBatch("N");
                                                                        		       	    	        studentPlacementForm.getStudentPlacement().setTwoPlacements(false);
                                                                        		       	    	     if((stuPlacement==null)||(stuPlacement.equals("0"))){
                                                                     	                            	stuPlacement.setNumberOfWeeks("");
                                                                     	                            }
                                                                        		       	       }else{
                                                                        		       	    	           studentPlacementForm.getStudentPlacement().setTwoPlacements(true);

                                                                        		       	    	           studentPlacementForm.setDisplaySecDatesBatch("Y");
                                                                        		       	       }
                                                                                          studentPlacementForm.setPracticeBatchDateListsIndex(0);
                                                        			                     studentPlacementForm.setPracticeBatchDateSecPracPrdListsIndex(0);

                                                        			                     if(studentPlacementForm.getPlacementFilterCountry().equals(PlacementUtilities.getSaCode())){
                                                                               		           practiceDatesMaintenance.setPracDateBatcheLists(studentPlacementForm);
                                                        			                     }

                                                                                          studentPlacementForm.setStudentNr(stuPlacement.getStuNum());
                                                                                          StudentUI studentUI=new StudentUI();
                                                                                          studentUI.setStudent(studentPlacementForm);
                                                                                           studentPlacementForm.setOriginalPrelimPlacement(stuPlacementListRec);
                                                                                          endReached=true;
                                                                                          PlacementUtilities placementUtilities=new PlacementUtilities();

                                                                                          studentPlacementForm.setStudyLevel(module.getLevel());
                                                                                          placementUtilities.setPlacementDateToRequestObject(request, stuPlacement);
                                                                                          //stuPlacement.setTotPracDays();
                                                                                          stuPlacement.setPacementDatesForView();
                                                                                               studentPlacementForm.setStudentPlacementAction("editPrelimPlacement");	

                                                                                          Qualification qualification=new Qualification();
                                                                                          String qualCode=studentPlacementForm.getStudent().getQual().getCode();
                                                                                          if(qualification.isPGCE(qualCode)){
                                                                                        	                studentPlacementForm.setIsPGCE("Y");

                                                                                        	                studentPlacementForm.getStudentPlacement().setTwoPlacements(false);
                                                                                        	                if((stuPlacement==null)||(stuPlacement.equals("0"))){
                                                                                        	                            	stuPlacement.setNumberOfWeeks("");
                                                                                        	                }
                                                                          		          }else{

                                                                                        	             studentPlacementForm.setIsPGCE("N");
                                                                                          }
                                                                                          StudentPlacement.setDatesDataToRequest(studentPlacementForm ,request);
                                                                                          
                                                       }
                                                     return endReached;
     }
     public ActionMessages setListPrelimPlacementScreen(StudentPlacementForm studentPlacementForm)throws Exception{
	                                                             PrelimPlacementScreenBuilder screenBuilder=new PrelimPlacementScreenBuilder(this);
	                                                             return screenBuilder.setListPrelimPlacementScreen(studentPlacementForm);
     }
     
     public void setPlacementList(StudentPlacementForm studentPlacementForm,Short province) throws Exception {     
                       StudentPlacementUI stuPlacementUI = new StudentPlacementUI();
                       stuPlacementUI .setPrelimPlacementList(studentPlacementForm, province);
     }
     public void initForIntCountry(StudentPlacementForm studentPlacementForm){
    	                 StudentPlacementUI stuPlacementUI = new StudentPlacementUI();
    	                 stuPlacementUI.initForIntCountry(studentPlacementForm);
     }
     public ActionMessages buildPrelimPlacementScreen(StudentPlacementForm studentPlacementForm)throws Exception { 
    	               Short province = studentPlacementForm.getPlacementFilterProvince();
                       studentPlacementForm.setListPlacement(new ArrayList());
		               if(!studentPlacementForm.getPlacementFilterCountry().equals(PlacementUtilities.getSaCode())){
		    	                 province=Short.parseShort("0");
		               }
		               DistrictUI districtUI=new DistrictUI();
		               districtUI.setDistrictValue(studentPlacementForm,province);
                       ActionMessages messages =setListPrelimPlacementScreen(studentPlacementForm);
                       return messages;
     }
     
    
}