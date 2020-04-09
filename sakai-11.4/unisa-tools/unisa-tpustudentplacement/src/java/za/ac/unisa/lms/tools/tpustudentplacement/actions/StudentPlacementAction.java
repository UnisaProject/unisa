package za.ac.unisa.lms.tools.tpustudentplacement.actions;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessages;
import org.apache.struts.actions.LookupDispatchAction;
import org.apache.struts.util.LabelValueBean;
import org.sakaiproject.component.cover.ComponentManager;
import org.sakaiproject.component.cover.ServerConfigurationService;
import org.sakaiproject.tool.api.Session;
import org.sakaiproject.tool.api.SessionManager;
import org.sakaiproject.user.api.User;
import org.sakaiproject.user.api.UserDirectoryService;

import za.ac.unisa.lms.dao.Gencod;
import za.ac.unisa.lms.dao.StudentSystemGeneralDAO;
import za.ac.unisa.lms.dao.general.UserDAO;
import za.ac.unisa.lms.domain.general.Person;
import za.ac.unisa.lms.tools.tpustudentplacement.forms.*;
import za.ac.unisa.lms.tools.tpustudentplacement.model.*;
import za.ac.unisa.lms.tools.tpustudentplacement.model.Email.TeachPracticeEmailUI;
import za.ac.unisa.lms.tools.tpustudentplacement.model.modelImpl.PrelimStudentPlacementUI;
import za.ac.unisa.lms.tools.tpustudentplacement.model.modelImpl.schoolImpl.SchoolUI;
import za.ac.unisa.lms.tools.tpustudentplacement.model.modelImpl.studentPlacementImpl.StuPlacementEditWinBuilder;
import za.ac.unisa.lms.tools.tpustudentplacement.model.modelImpl.studentPlacementImpl.StudentPlacementLogUI;
import za.ac.unisa.lms.tools.tpustudentplacement.model.modelImpl.studentPlacementImpl.StudentPlacementUI;
import za.ac.unisa.lms.tools.tpustudentplacement.uiLayer.CommunicationUI;
import za.ac.unisa.lms.tools.tpustudentplacement.uiLayer.ProvinceUI;
import za.ac.unisa.lms.tools.tpustudentplacement.uiLayer.StudentUI;
import za.ac.unisa.lms.tools.tpustudentplacement.model.modelImpl.LetterUI;
import za.ac.unisa.lms.tools.tpustudentplacement.model.modelImpl.PrelimStudentPlacementImpl;
import za.ac.unisa.lms.tools.tpustudentplacement.utils.*;
public class StudentPlacementAction extends LookupDispatchAction{
	
	               private SessionManager sessionManager;
	               private UserDirectoryService userDirectoryService;
	             	            	protected Map getKeyMethodMap() {
		// TODO Auto-generated method stub
		Map map = new HashMap();
		map.put("initial", "initial");	
		map.put("button.continue", "nextPage");
		map.put("button.placeModule", "addStudentPlacement");
                map.put("addStudentPlacement","addStudentPlacement");
		map.put("button.placeModuleNoBlocks", "addStudentPlacementNoBlockDates");
		map.put("button.save", "saveStudentPlacement");
		map.put("button.edit", "editStudentPlacement");
		map.put("button.remove", "removeStudentPlacement");
		map.put("button.contactDetails", "studentContactDetails");
		map.put("button.back","prevPage");
		map.put("button.searchSupervisor", "searchSupervisor");
		map.put("button.searchSchool", "searchSchool");
		map.put("inputCorrespondence","inputCorrespondence");
		map.put("button.correspondence","inputCorrespondence");
		map.put("button.sendEmail", "sendEmail");
		map.put("button.getEmailAdress", "getEmailAddress");
		map.put("button.getCellNumber", "getCellNumber");
		map.put("button.viewLetter", "viewLetter");
		map.put("button.SendSMS", "sendSMS");
		map.put("button.getContactDetails", "getContactDetails");
		map.put("button.listLogs","displayLogList");
		map.put("button.previous","prevPrelimPlacement");
		map.put("button.next","nextPrelimPlacement");
		map.put("button.searchMentor","searchMentor");
		map.put("handleEndDate","handleEndDate");
		map.put("handleStartDate","handleStartDate");
		map.put("editPrelimPlacements","editPrelimPlacements");
		map.put("supervForProvOnchangeAction","supervForProvOnchangeAction");
                map.put("addStudentPlacementNoBlockDates","addStudentPlacementNoBlockDates");
		return map;
	  }
	             	            	
	   public ActionForward searchMentor(ActionMapping mapping, ActionForm form,
                        HttpServletRequest request, HttpServletResponse response)
                        throws Exception {
		                                    StudentPlacementForm studentPlacementForm = (StudentPlacementForm) form; 
		                                    studentPlacementForm.setPlacementFilterDistrict(Short.parseShort("0"));
		                                    studentPlacementForm.setPlacementFilterProvince(Short.parseShort("0"));
		                                       if((studentPlacementForm.getMentor()==null)||(studentPlacementForm.getMentorFilterData()==null)){
		                                              Mentor mentor=new Mentor();
		   	                                          studentPlacementForm.setMentor(mentor);
		   	                                          mentor.setMentorTrainedList(studentPlacementForm);
		   	   			                              studentPlacementForm.setMentorFilterData(new MentorFilteringData());
		   	   			                              studentPlacementForm.setMentorModel(new MentorModel());
		                                      }
		                                      if(studentPlacementForm.getStudentPlacement()!=null){
		                        	                 Integer schoolCode=studentPlacementForm.getStudentPlacement().getSchoolCode();
		                        	                 SchoolUI schoolUI=new SchoolUI();
                        		                     String  countryCode=schoolUI.getSchCountryCode(schoolCode);
                        		                     studentPlacementForm.setPlacementFilterCountry(countryCode);
                        		                     if(schoolCode!=0){
                        		                    	     if((""+countryCode).equals(PlacementUtilities.getSaCode())){
		                        		            	             short provCode=Short.parseShort(""+schoolUI.getSchoolProvCode(schoolCode));
		                        		                             short distCode=Short.parseShort(schoolUI.getSchoolDistrictCode(schoolCode));
		                        		                             studentPlacementForm.setPlacementFilterDistrict(distCode);
		                		                                     studentPlacementForm.setPlacementFilterProvince(provCode);
		                		                             }
		                        		                     studentPlacementForm.getMentorFilterData().setMentorFilterSchoolCode(schoolCode);
		                        		                    String schoolName=schoolUI.getSchoolName(schoolCode);
		                        		                     studentPlacementForm.getMentorFilterData().setMentorFilterSchoolValue(schoolName);
		                        	                  }
		                                   }
		                                   studentPlacementForm.getMentorFilterData().setMentorFilterDistrict(studentPlacementForm.getPlacementFilterDistrict());
		                                   studentPlacementForm.getMentorFilterData().setMentorFilterCountry(studentPlacementForm.getPlacementFilterCountry());
                                           studentPlacementForm.getMentorFilterData().setMentorFilterProvince(studentPlacementForm.getPlacementFilterProvince());
		                                   studentPlacementForm.setMentorCalledFrom("editStudentPlacement");
			                     return mapping.findForward("listMentor");	
      }
	      public ActionForward editPrelimPlacements(ActionMapping mapping, ActionForm form,
                                                                            HttpServletRequest request, HttpServletResponse response)
                                                                           throws Exception {
                                                                                                               StudentPlacementForm studentPlacementForm = (StudentPlacementForm) form; 
                                                                                                               Supervisor superv=new Supervisor();
                                                                                                               String country=studentPlacementForm.getPlacementFilterCountry();
                                                                                                               short province=studentPlacementForm.getPlacementFilterProvince();
                                                                                                   studentPlacementForm.setListProvSupervisor(superv.getSupervisorList(country, province,"Y"));
                                                                                                               PrelimStudentPlacementUI prelimPlacementUI=new PrelimStudentPlacementUI();
                                                                                                               prelimPlacementUI.setPrelimPlacementScreen(studentPlacementForm,request);
                                                                                                               studentPlacementForm.setCanSaveEdits(1);
                                                                                                                return mapping.findForward("editPrelimPlacement");	
    }
	 public ActionForward nextPrelimPlacement(ActionMapping mapping, ActionForm form,
                                                                                  HttpServletRequest request, HttpServletResponse response)
                                                                                   throws Exception {
                                                                                                                         StudentPlacementForm studentPlacementForm = (StudentPlacementForm) form; 
                                                                                                                         int pos=studentPlacementForm.getPosOfCurrPrelimPlacement();
                                                                                                                         if(studentPlacementForm.getListPlacement().size()>0){
                                                                                                                                           if(pos<studentPlacementForm.getListPlacement().size()){
                                                                                                                                                            pos++;
	                                                                                                                                       }
                                                                                                                           }
                                                                                                                           PrelimStudentPlacementUI prelimPlacementUI=new PrelimStudentPlacementUI();
 		                                                                                                                   prelimPlacementUI.setNavigationBtnsTrackingValues(studentPlacementForm,pos,
                                		                                                                                   studentPlacementForm.getListPlacement());
 		                                                                                                                   prelimPlacementUI.setPrelimPlacementScreen(studentPlacementForm,pos, request);
                                                                                                                           studentPlacementForm.setPosOfCurrPrelimPlacement(pos);
                                                                                                                           studentPlacementForm.setCanSaveEdits(1);
                                                                                                                              return mapping.findForward("editPrelimPlacement");
      }
	  public ActionForward prevPrelimPlacement(ActionMapping mapping, ActionForm form,
                                      HttpServletRequest request, HttpServletResponse response)
                                      throws Exception {
                                                        StudentPlacementForm studentPlacementForm = (StudentPlacementForm) form; 
                                                        int pos=studentPlacementForm.getPosOfCurrPrelimPlacement();
                                                        if(pos>0){
                                                           pos--;
                                                        }
                                                        PrelimStudentPlacementUI prelimPlacementUI=new PrelimStudentPlacementUI();
                        		                         prelimPlacementUI.setNavigationBtnsTrackingValues(studentPlacementForm,pos,
                                                        		               studentPlacementForm.getListPlacement());
                        		                         prelimPlacementUI.setPrelimPlacementScreen(studentPlacementForm,pos, request);
                                                        studentPlacementForm.setPosOfCurrPrelimPlacement(pos);
                                                        studentPlacementForm.setCanSaveEdits(1);
                                                            return mapping.findForward("editPrelimPlacement");
      }
	  public ActionForward viewLetter(
			                                                       ActionMapping mapping,
			                                                       ActionForm form,
 			                                                       HttpServletRequest request,
			                                                       HttpServletResponse response) throws Exception {
		
		                                          StudentPlacementForm studentPlacementForm = (StudentPlacementForm) form;	
		                                          ActionMessages messages = new ActionMessages();	
		                                          if(studentPlacementForm.getCommunicationTo().equals("Student")){
		                        	                         Coordinator coordinator=new Coordinator();
		                        	                         coordinator.handleNoProvincialCoord(studentPlacementForm,messages);
     	                                                     if (!messages.isEmpty()) {
     	                            	                            addErrors(request,messages);
                                                                   return mapping.findForward("inputCorrespondence");
                                                             }
				            	                	         StudentPlacement studenPlacement=new StudentPlacement();
					                        	             studenPlacement.replaceDummySupervWithCoordForProv(studentPlacementForm);
				            	                 }
		                                         LetterUI letterUI=new  LetterUI();
		                                         letterUI.getLetter(studentPlacementForm,messages);
		                                         addErrors(request,messages);
		                                     return mapping.findForward(studentPlacementForm.getCurrentPage());
	 }
	 private ActionMessages validateSendSmsData(StudentPlacementForm studentPlacementForm){
		                                 StuPlacementActionValidator stuPlacementActionValidator=new StuPlacementActionValidator();
                                         Student student=studentPlacementForm.getStudent();
                                         String cellNumber=studentPlacementForm.getCommunicationCellNumber();
                                         String communicationTo=studentPlacementForm.getCommunicationTo();
                                         return stuPlacementActionValidator.validateSmsCommunicData(student,cellNumber,communicationTo);
	}
	public ActionForward sendSMS(
			ActionMapping mapping,
			ActionForm form,
			HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		            StudentPlacementForm studentPlacementForm = (StudentPlacementForm) form;	
		            ActionMessages messages=validateSendSmsData(studentPlacementForm);
		            ActionMessages infoMessages=new ActionMessages();
		            boolean sendSuccessfuly=false;
		            StudentPlacementSmsClass smsClass=new StudentPlacementSmsClass();
			        if (messages.isEmpty()) {
			        	      sendSuccessfuly=smsClass.sendSmsNotifyingOfEmail(studentPlacementForm, request, infoMessages, infoMessages);
			        }
			        if(sendSuccessfuly){
			        	   addMessages(request,infoMessages);
			        }else{
			        	   addErrors(request,messages);
			        }
			        studentPlacementForm.setCurrentPage("inputCorrespondence");
		    return mapping.findForward("inputCorrespondence");
	}
    private ActionMessages validateSendEmailData(StudentPlacementForm studentPlacementForm){
                              StuPlacementActionValidator stuPlacementActionValidator=new StuPlacementActionValidator();
                              String emailAddress=studentPlacementForm.getCommunicationEmailAddress().trim();
                              String cellNumber=studentPlacementForm.getCommunicationCellNumber();
                              String communicationTo=studentPlacementForm.getCommunicationTo();
                  return stuPlacementActionValidator.validatEmailCommunicData(emailAddress,cellNumber,communicationTo);
    }
	public ActionForward sendEmail(
			                       ActionMapping mapping,
			                       ActionForm form,
			                       HttpServletRequest request,
			                       HttpServletResponse response) throws Exception {
		
		                                  StudentPlacementForm studentPlacementForm = (StudentPlacementForm) form;	
		 		                          ActionMessages infoMessages = new ActionMessages();
		 		                          ActionMessages emailMessages = new ActionMessages();
		 		                          ActionMessages smsLogMessages = new ActionMessages();
		 		                          boolean delivered=false;
		 		                          boolean sendSuccessfully=false;
		 		                          ActionMessages messages=validateSendEmailData(studentPlacementForm);
			                              studentPlacementForm.setCurrentPage("inputCorrespondence");
	            	                      if (messages.isEmpty()) {
		            	                             setStudent(studentPlacementForm);
		            	                             if(studentPlacementForm.getCommunicationTo().equals("Student")){
				                        	                 Coordinator coordinator=new Coordinator();
		            	                                     coordinator.handleNoProvincialCoord(studentPlacementForm,emailMessages);
	                                                         if (!emailMessages.isEmpty()) {
		            	                                            handleEmailErrors(request,emailMessages,delivered);
                                                                    return mapping.findForward("inputCorrespondence");
                                                             }
		            	                             }
		            	                             TeachPracticeEmailUI tpEmailUI=new TeachPracticeEmailUI();
		                                             delivered=tpEmailUI.sendEmail(studentPlacementForm,emailMessages);
		                                             String fromEmail="teachprac@unisa.ac.za";
		                                             String toEmail=studentPlacementForm.getCommunicationEmailAddress().trim();
		    	                                     log.info("sending mail from "+fromEmail+" to "+toEmail);
		                                             StudentPlacementSmsClass smsClass=new StudentPlacementSmsClass();
                                                     sendSuccessfully=smsClass.sendSmsNotifyingOfEmail(studentPlacementForm,request,infoMessages,messages);
		                                 }
		                                 handleSmsErrors(request,infoMessages,messages,sendSuccessfully);
                                         handleEmailErrors(request,emailMessages,delivered);
                                         handleSmsLogErrors(request,smsLogMessages);
                                         return mapping.findForward("inputCorrespondence");	
	}	
	private void  handleSmsErrors(HttpServletRequest request,
			                 ActionMessages infoMessages,ActionMessages messages,boolean sendSuccessfully)throws Exception {
	                                if(sendSuccessfully){
	                                	  addMessages(request,infoMessages);
	                                }else{
	                                       addErrors(request,messages);
	                                }
	}
	private void  handleSmsLogErrors(HttpServletRequest request,ActionMessages messages)throws Exception {
                   if(!messages.isEmpty()){
                   	            addErrors(request,messages);
                   }
    }
	private void  handleEmailErrors(HttpServletRequest request,ActionMessages messages,
			         boolean delivered)throws Exception {
                             if(delivered){
                   	                 addMessages(request,messages);
                             }else{
                                     addErrors(request,messages);
                             }
    }
	public ActionForward getEmailAddress(
			                                 ActionMapping mapping,
			ActionForm form,
			HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		                 StudentPlacementForm studentPlacementForm = (StudentPlacementForm) form;	
		                 ActionMessages messages = new ActionMessages();	
		                 CommunicationDataValidator cv=new CommunicationDataValidator();
		                 String communicationTo=studentPlacementForm.getCommunicationTo();
		                 cv.validateCommunicationTo(communicationTo,messages,"Recipient required to get Email Address.");
		                 if (messages.isEmpty()){
		                	     SchoolUI school=new SchoolUI();
		                	     school.setSchoolEmailToFormBean(studentPlacementForm);
		                	     setStudentEmail(studentPlacementForm);
			             }
		         		if (!messages.isEmpty()) {
			                    addErrors(request,messages);		
		                }
		                studentPlacementForm.setCurrentPage("inputCorrespondence");
		            return mapping.findForward("inputCorrespondence");	
	 }	
	 private void  setStudentCommunicationDataToFormBean(StudentPlacementForm studentPlacementForm )throws Exception {
                        if (studentPlacementForm .getCommunicationTo().equalsIgnoreCase("Student")){
       	                        StudentUI  studentUI  =new StudentUI();
       	                        studentUI.setStudentEmailToFormBean(studentPlacementForm);
       	                        studentUI.setStudentCellNrToFormBean(studentPlacementForm);
                        }
     }
	 private void  setStudentEmail(StudentPlacementForm studentPlacementForm )throws Exception {
                          if (studentPlacementForm .getCommunicationTo().equalsIgnoreCase("Student")){
                        	       StudentUI  studentUI  =new StudentUI  ();
                        	       studentUI.setStudentEmailToFormBean(studentPlacementForm);
                          }
     }
	 public ActionForward getCellNumber(
			               ActionMapping mapping,
			               ActionForm form,
			               HttpServletRequest request,
			               HttpServletResponse response) throws Exception {
		
                    		          StudentPlacementForm studentPlacementForm = (StudentPlacementForm) form;	
		                              ActionMessages messages = new ActionMessages();
		                              CommunicationUI communicationUI=new CommunicationUI();
		                              communicationUI.getCellNr(studentPlacementForm, messages);
		                              if (!messages.isEmpty()) {
			                                 addErrors(request,messages);		
		                              }
				        return mapping.findForward("inputCorrespondence");	
	}	
	public ActionForward getContactDetails(
			               ActionMapping mapping,
			               ActionForm form,
			               HttpServletRequest request,
			               HttpServletResponse response) throws Exception {
		
		                        StudentPlacementForm studentPlacementForm = (StudentPlacementForm) form;
		                        ValidateString validateString=new ValidateString();
		                        ActionMessages messages = new ActionMessages();
		                        String communicationTo=studentPlacementForm.getCommunicationTo();
		                        String msg="Recipient required to get Contact Details.";
		                        validateString.validateStr(communicationTo,messages,msg);
		                        if(messages.isEmpty()){
		                        	     SchoolUI school=new SchoolUI();
		                        	     school.setSchoolCommunicDataToFormBean(studentPlacementForm);
						                 setStudentCommunicationDataToFormBean(studentPlacementForm );
		                                 String cellNumErrorMsg="If an email is sent without a cell phone number specified the notification SMS would not be sent.";
		                                 String communicCellNum=studentPlacementForm.getCommunicationCellNumber();
		                                 validateString.validateStr(communicCellNum,messages,cellNumErrorMsg);
		                        }
		                        if(!messages.isEmpty()) {
			                          addErrors(request,messages);
                         		}
		                        studentPlacementForm.setCurrentPage("inputCorrespondence");
		                   return mapping.findForward("inputCorrespondence");	
	}	
	public ActionForward inputCorrespondence(
			                 ActionMapping mapping,
			                 ActionForm form,
			                 HttpServletRequest request,
			                 HttpServletResponse response) throws Exception {
		                  
		                      StudentPlacementForm studentPlacementForm = (StudentPlacementForm) form;	
		                      CommunicationUI communicationUI=new CommunicationUI();
		                      ActionMessages messages=new ActionMessages(); 
		                      communicationUI.buildCommunicationScreen(request, studentPlacementForm, messages);
		                      if(!messages.isEmpty()){
		                    	  addErrors(request,messages);
		                    	  studentPlacementForm.setCurrentPage(studentPlacementForm.getPreviousPage());
		                      }
		           return mapping.findForward(studentPlacementForm.getCurrentPage());	
	}
	public ActionForward searchSupervisor(
			ActionMapping mapping,
			ActionForm form,
			HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		         StudentPlacementForm studentPlacementForm = (StudentPlacementForm) form;
		         return mapping.findForward("searchSupervisor");	
	}
	public ActionForward searchSchool(
			                  ActionMapping mapping,
			                  ActionForm form,
			                  HttpServletRequest request,
			                  HttpServletResponse response) throws Exception {
		                                 StudentPlacementForm studentPlacementForm = (StudentPlacementForm) form;	
		                                 return mapping.findForward("searchSchool");	
	}
	public ActionForward nextPage(
		          	                         ActionMapping mapping,
			                                 ActionForm form,
			                                 HttpServletRequest request,
			                                 HttpServletResponse response) throws Exception {
		                                                  StudentPlacementForm studentPlacementForm = (StudentPlacementForm) form;	
	                                               	      String nextPage="initial";
		                                                  if (studentPlacementForm.getCurrentPage().equalsIgnoreCase("inputStuPlacement")){
			                                                           studentPlacementForm.setCurrentPage("inputStudentPlacement");
		                                                  }
		if (studentPlacementForm.getCurrentPage().equalsIgnoreCase("inputStudentPlacement")){
			   nextPage = listStudentPlacement(mapping,form,request,response);
		}	
		studentPlacementForm.setPreviousPage(studentPlacementForm.getCurrentPage());
		studentPlacementForm.setCurrentPage(nextPage);
		return mapping.findForward(nextPage);	
	}
	private void setStudent(StudentPlacementForm studentPlacementForm)throws Exception {
		            StudentUI studentUI=new StudentUI();
		             studentUI.setStudent(studentPlacementForm);
	}
	public ActionForward prevPage(
			ActionMapping mapping,
			ActionForm form,
			HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		           StudentPlacementForm studentPlacementForm = (StudentPlacementForm) form;	
		 		   String nextPage="initial";
		 		   if (studentPlacementForm.getCurrentPage().equalsIgnoreCase("inputCorrespondence")){
			                     nextPage=handlePrevForWhenCurrPageInputCorr(studentPlacementForm);
		           }else if (studentPlacementForm.getCurrentPage().equalsIgnoreCase("editPrelimPlacement")){
		             	        studentPlacementForm.setCurrentPage("listPrelimPlacement");  
			                    nextPage="listPrelimPlacement";
		           }else if (studentPlacementForm.getCurrentPage().equalsIgnoreCase("listStudentPlacement")){
			                      nextPage="inputStudentPlacement";
		           }else if (studentPlacementForm.getCurrentPage().equalsIgnoreCase("editStudentPlacement")){
			                      nextPage="listStudentPlacement";
		           }else if (studentPlacementForm.getCurrentPage().equalsIgnoreCase("studentLetter")){
			                      nextPage="inputCorrespondence";
		           }else if (studentPlacementForm.getCurrentPage().equalsIgnoreCase("schoolLetter")){
			                      nextPage="inputCorrespondence";
		           }else if (studentPlacementForm.getCurrentPage().equalsIgnoreCase("studentContactDetails")){	
		        	             if(studentPlacementForm.getStudentPlacement()!=null){
		        	            	 studentPlacementForm.getStudentPlacement().setDatesToRequest(request);
		        	             }
			                     nextPage=studentPlacementForm.getPreviousPage(); 			
		           }else if(studentPlacementForm.getCurrentPage().equalsIgnoreCase("listLogs")){
				              if(studentPlacementForm.getLogButtonTracker().equals("PassedFromLogBtn")){
					                  nextPage=listStudentPlacement(mapping,form, request,response);
				              }else{
					                   nextPage="inputStudentPlacement";	
				              }
				              studentPlacementForm.setLogButtonTracker("");
		          }
   		          //studentPlacementForm.setPreviousPage(studentPlacementForm.getCurrentPage());
		          studentPlacementForm.setCurrentPage(nextPage);
		return mapping.findForward(nextPage);	
	}
	private String handlePrevForWhenCurrPageInputCorr(StudentPlacementForm studentPlacementForm)throws Exception {
		              String nextPage="initial";
		              StudentPlacementLogUI studentPlacementLogUI=new StudentPlacementLogUI();
	                  if (studentPlacementForm.getPreviousPage().equalsIgnoreCase("editPrelimPlacement")){
                              setStudent(studentPlacementForm);
                              nextPage="editPrelimPlacement";
                      }else if(studentPlacementForm.getLogButtonTracker().equals("")){
                                   setStudent(studentPlacementForm);
                                   nextPage="listStudentPlacement";
                      }else{
	                               studentPlacementLogUI.getSelectedLogs(studentPlacementForm);
                                   nextPage="listLogs";
                      }
	                  return nextPage;
	}
	public ActionForward studentContactDetails(
			                  ActionMapping mapping,
			                  ActionForm form,
			                  HttpServletRequest request,
			                  HttpServletResponse response) throws Exception {
		
		                                StudentPlacementForm studentPlacementForm = (StudentPlacementForm) form;			
		                         		studentPlacementForm.setPreviousPage(studentPlacementForm.getCurrentPage());
		                                if(studentPlacementForm.getCurrentPage().equals("editPrelimPlacement")){
		                                	setStudent(studentPlacementForm);
		                                }
		                                studentPlacementForm.setCurrentPage("studentContactDetails");	
		               return mapping.findForward("studentContactDetails");	
	}
    public String listStudentPlacement(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			    throws Exception {
                                StudentPlacementForm studentPlacementForm = (StudentPlacementForm)form;
                                int acadYear=Integer.parseInt(studentPlacementForm.getAcadYear());
if((studentPlacementForm.getStudentNr()==null)||
(studentPlacementForm.getStudentNr().trim().isEmpty())){
}else{
                                 int stuNr=Integer.parseInt(studentPlacementForm.getStudentNr());
                                Student stu=new Student(stuNr,acadYear);
                                Qualification qual= stu.getQualification();
                                if(qual.isPgceStudent()){
                                          studentPlacementForm.setUsingBlockDates("N");
studentPlacementForm.setIsPGCE("Y");
                                   
                                 }else{
                                         studentPlacementForm.setUsingBlockDates("Y");
                                   studentPlacementForm.setIsPGCE("N");
                                 
                                }
}
                                 StudentPlacementUI spUI=new StudentPlacementUI(); 
  		                     ActionMessages messages=new ActionMessages();
  		                    String nextPage=spUI.listStudentPlacement(mapping, form, request, response,messages);
  		                    if (!messages.isEmpty()) {
		                      	     addErrors(request,messages);
		                    } 
		            return nextPage;
	}
	public ActionForward initial(
			ActionMapping mapping,
			ActionForm form,
			HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		             StudentPlacementForm studentPlacementForm = (StudentPlacementForm) form;	
				     //Get user details
		             studentPlacementForm.setUserId(null);
		             User user = null;
		             sessionManager = (SessionManager) ComponentManager.get(SessionManager.class);
	                 userDirectoryService = (UserDirectoryService) ComponentManager.get(UserDirectoryService.class);
	                 String serverpath = ServerConfigurationService.getServerUrl();
		                    	  
		             Session currentSession = sessionManager.getCurrentSession();
		             String userID  = currentSession.getUserId();
		             studentPlacementForm.setLogButtonTracker("");
		             String storedNetWorkCode="";
		             if (userID != null) {
			                 user = userDirectoryService.getUser(userID);
			                 studentPlacementForm.setUserId(user.getEid().toUpperCase().trim());
			                 String networkCode=studentPlacementForm.getUserId();
			                 if ((studentPlacementForm.getUserId()==null)||studentPlacementForm.getUserId().equals("")){
		                            return mapping.findForward("userUnknown");
	                         }
	                         Coordinator coordinator=new Coordinator();
			                 if((networkCode!=null)&&(!networkCode.equals(""))){
				                    studentPlacementForm.setCoordinatorActive(coordinator.isCoordinator(networkCode));
			                 }else{
				                     studentPlacementForm.setCoordinatorActive("N");
				                     return mapping.findForward("userUnknown");
			                 }
	   	                     Personnel personnel=new Personnel(); 
	   	                     String personnelNum=personnel.getPersonnelNumber(networkCode);
	   	                     if ((personnelNum!=null)&&(!personnelNum.equals(""))){
	   	                	        studentPlacementForm.setPersonnelNumber(personnelNum);
	   	 		             }else{
	   	                              return mapping.findForward("userUnknown");
                             }
	   	                     Mentor mentor=new Mentor();
	   	                     studentPlacementForm.setMentor(mentor);
	   	                     mentor.setMentorTrainedList(studentPlacementForm);
	   	   			         studentPlacementForm.setMentorFilterData(new MentorFilteringData());
	   	   			         studentPlacementForm.setMentorModel(new MentorModel());
	   	   			         mentor.refresh(studentPlacementForm);
  	   			        
	   	   			}else{
		            	      return mapping.findForward("userUnknown");
		             }
	   	                    
		studentPlacementForm.setJustEnteredPlacementLogs("no");
		Person person = new Person();
		UserDAO userdao = new UserDAO();
		person = userdao.getPerson(studentPlacementForm.getUserId());			
		studentPlacementForm.setUser(person);
		//initialise input values
		studentPlacementForm.setAcadYear("");
		studentPlacementForm.setSemester("");
		
		//Get semester list
		List list = new ArrayList();
		StudentSystemGeneralDAO dao = new StudentSystemGeneralDAO();
		for (int i=0; i < dao.getGenCodes((short)54,1).size(); i++) {
			list.add(i, (Gencod)(dao.getGenCodes((short)54,1).get(i)));
		}
		studentPlacementForm.setListSemester(list);		
		
		//Get school type list
		//sort alphabetic on eng-desc
		Gencod gencod = new Gencod();
		gencod.setCode("");
		gencod.setEngDescription("All");
		List listFilter = new ArrayList();
		list = new ArrayList();
		listFilter.add(0,gencod);
		
		int index = 0;
		for (int i=0; i < dao.getGenCodes((short)148,1).size(); i++) {
			index = index + 1;
			listFilter.add(index, (Gencod)(dao.getGenCodes((short)148,1).get(i)));
			list.add(i, (Gencod)(dao.getGenCodes((short)148,1).get(i)));
		}
		studentPlacementForm.setListSchoolType(list);	
		studentPlacementForm.setListFilterSchoolType(listFilter);
		
		//Get school category list
		//sort alphabetic on afr-desc
		listFilter = new ArrayList();
		list = new ArrayList();
		listFilter.add(0,gencod);
		
		index = 0;
		for (int i=0; i < dao.getGenCodes((short)149,3).size(); i++) {
			index = index + 1;
			listFilter.add(index, (Gencod)(dao.getGenCodes((short)149,3).get(i)));
			list.add(i, (Gencod)(dao.getGenCodes((short)149,3).get(i)));
		}
		studentPlacementForm.setListFilterSchoolCategory(listFilter);
		studentPlacementForm.setListSchoolCategory(list);	
		
		//Get country list
		Country country=new Country();
		studentPlacementForm.setListCountry(country.listAllCountries());
		
		//Get Province list
			 ProvinceUI   province=new  ProvinceUI();
		 province.setProvAndSubProvListToForm( studentPlacementForm);
		 province.setProvinceListToForm(studentPlacementForm);
		//Initialise values
		PlacementUtilities util = new PlacementUtilities();
		if (studentPlacementForm.getSemester()==null || studentPlacementForm.getSemester().equalsIgnoreCase("")){
			studentPlacementForm.setSemester("0");
		}
		if (studentPlacementForm.getAcadYear()==null || studentPlacementForm.getAcadYear().equalsIgnoreCase("")){
			studentPlacementForm.setAcadYear(util.getDefaultAcadYear().toString());
		}
		  List townList=new ArrayList();
          Town town =new Town();
          town.setCode("-1");
          town.setName("ALL");
          townList.add(0,town);
          studentPlacementForm.setListTown(townList);
    studentPlacementForm.setCurrentPage("inputStudentPlacement");
		return mapping.findForward("inputStudentPlacement");	
	}
	public ActionForward addStudentPlacement(
			                                                  ActionMapping mapping,
			                                                  ActionForm form,
			                                                  HttpServletRequest request,
			                                                  HttpServletResponse response) throws Exception {
		                                                               StudentPlacementForm studentPlacementForm = (StudentPlacementForm) form;
		                                                               List moduleList=studentPlacementForm.getStudent().getListPracticalModules();
		                                                               ActionMessages messages = new ActionMessages();
		                           		                               if((moduleList==null)||(moduleList.isEmpty()) ){
		                                                        	                    InfoMessagesUtil  infoMessagesUtil=new  InfoMessagesUtil();
		   		                            	                                        String message="The student is not registered for Practical Modules";
		   		                            	                                        infoMessagesUtil.addMessages(messages, message);
		   		                            	                                        addErrors(request,messages);
		   		                            	                                        return mapping.findForward("listStudentPlacement");	
		                                                              }
		                           		                             StudentPlacementUI studentPlacementUI=new StudentPlacementUI();
		                                                             StudentPlacement placement = new StudentPlacement();
		                                                             studentPlacementForm.setStudentPlacement(placement);
		                                                             studentPlacementUI.initialiseNewPlacement(studentPlacementForm, moduleList);
		                                                              PlacementUtilities placementUtilities=new PlacementUtilities();
		                                                             placementUtilities.setPlacementDateToRequestObject(request, placement);
		                                                             placement.setCountryCode(studentPlacementForm.getStudent().getCountryCode());
		                                                             studentPlacementUI.setFilters(studentPlacementForm);
		                                                             int studyLevel=1;
studentPlacementForm.setModuleCode("");
	                                                                 if( (placement.getModule()!=null)||(! placement.getModule().trim().equals(""))){
		                                                                             Module module=new Module();
		                                                                             String moduleCode=placement.getModule();
                                                                                     Qualification qual=studentPlacementForm.getStudent().getQualification();
                                                                                      int acadYear=Integer.parseInt(studentPlacementForm.getAcadYear());
                                                                                      module=module.getModule(qual,moduleCode,acadYear);
                                                                                      studyLevel=module.getLevel();
studentPlacementForm.setModuleCode(moduleCode);
		                                                              }


studentPlacementForm.setStudyLevel(studyLevel);
		                                 studentPlacementUI.setFlags(studentPlacementForm);                           
studentPlacementForm.setUsingBlockDates("Y");
if(studentPlacementForm.getStudyLevel()==1){
	                     	    	                          studentPlacementForm.setDisplaySecDatesBatch("N");
studentPlacementForm.getStudentPlacement().setTwoPlacements(false);
	                     	                 
 }else{
	                     	    	                         studentPlacementForm.setDisplaySecDatesBatch("Y");
studentPlacementForm.getStudentPlacement().setTwoPlacements(true);
	                     	                  }
		                                                          PracticeDatesMaintenance practiceDatesMaintenance=new PracticeDatesMaintenance();
	                                                                  if(studentPlacementForm.getLocalSchool().equals("Y")&&
	                                                                		                  studentPlacementForm.getIsPGCE().equals("N")){
	                                                                                                                 studentPlacementForm.setPracticeBatchDateListsIndex(0);
	                                                                                                                 studentPlacementForm.setPracticeBatchDateSecPracPrdListsIndex(0);
	                                                                                                                 practiceDatesMaintenance.setPracDateBatcheLists(studentPlacementForm);
                                                                       }
          studentPlacementForm.setStudentPlacementAction("add");	
                                     
                                                                       studentPlacementUI.epiloueAddEditPlacementScreen(studentPlacementForm, request);
   	                                               return mapping.findForward("editStudentPlacement");	
	}
		//if the student has s placement list  we lists the pacements, copy the school, the suprvisor from the first placement and  and 
	//initialise the new placemnt withthem,the user can change this later
	//the user  is not requiried to choose any of the listed placements fro the new placment iitialization

	public ActionForward 	addStudentPlacementNoBlockDates(
                                                    ActionMapping mapping,
                                                    ActionForm form,
                                                    HttpServletRequest request,
                                                    HttpServletResponse response) throws Exception {
                                                                    StudentPlacementForm studentPlacementForm = (StudentPlacementForm) form;
                                                                    List moduleList=studentPlacementForm.getStudent().getListPracticalModules();
                                                                    ActionMessages messages = new ActionMessages();
                                                                    if((moduleList==null)||(moduleList.isEmpty()) ){
                  	                                                              InfoMessagesUtil  infoMessagesUtil=new  InfoMessagesUtil();
 	                                                                              String message="The student is not registered for Practical Modules";
 	                                                                              infoMessagesUtil.addMessages(messages, message);
 	                                                                              addErrors(request,messages);
 	                                                                              return mapping.findForward("listStudentPlacement");	
                                                                     }
                                                                    StudentPlacementUI studentPlacementUI=new StudentPlacementUI();
		                                                             StudentPlacement placement = new StudentPlacement();
		                                                             studentPlacementForm.setStudentPlacement(placement);
		                                                             studentPlacementUI.initialiseNewPlacement(studentPlacementForm, moduleList);
studentPlacementForm.setModuleCode(placement.getModule());                                                                     
PlacementUtilities placementUtilities=new PlacementUtilities();
                                                                     placementUtilities.setPlacementDateToRequestObject(request, placement);
                                                                     placement.setCountryCode(studentPlacementForm.getStudent().getCountryCode());
                                                                     studentPlacementUI.setFilters(studentPlacementForm);
                                                                     studentPlacementForm.setUsingBlockDates("N");
                                                                     studentPlacementForm.setDisplaySecDatesBatch("N");
                                                                     studentPlacementForm.getStudentPlacement().setTwoPlacements(false);
                                                                     studentPlacementUI.epiloueAddEditPlacementScreen(studentPlacementForm, request);
                                                      studentPlacementForm.getStudentPlacement().setTwoPlacements(false);
 studentPlacementForm.setStudentPlacementAction("add");	
                                                       return mapping.findForward("editStudentPlacement");	
   }
	public ActionForward editStudentPlacement(
			                                          ActionMapping mapping,
			                                          ActionForm form,
		                                         	  HttpServletRequest request,
			                                           HttpServletResponse response) throws Exception {
		                                                            StudentPlacementForm studentPlacementForm = (StudentPlacementForm) form;
		                                                            ActionMessages messages = new ActionMessages();
		                                                                 StuPlacementEditWinBuilder editWinBuilder=new StuPlacementEditWinBuilder();
		                                                                editWinBuilder.buildEditWin(studentPlacementForm, messages,request);
		                                                                if (!messages.isEmpty()) {
			                                                                            addErrors(request,messages);
			                                                                            studentPlacementForm.setCurrentPage("listStudentPlacement");
		                                                                  }
                                                                           studentPlacementForm.setStudentPlacementAction("edit");
                                                                           StudentPlacementUI studentPlacementUI=new StudentPlacementUI();	
                                                                           studentPlacementUI.epiloueAddEditPlacementScreen(studentPlacementForm, request);
                                                                           if(studentPlacementForm.getIsPGCE().equals("Y")||
                                                                        		            studentPlacementForm.getStudentPlacement().getPlacementPrd()==3){
                                                                        	            	           studentPlacementForm.setUsingBlockDates("N");
       	                                                	               }else{
                     	                	                                            studentPlacementForm.setUsingBlockDates("Y");
                            	                                          }
                                                                     studentPlacementForm.setSameSupervisorSelected("Y");
	                                                             return mapping.findForward(studentPlacementForm.getCurrentPage());
		}
		public ActionForward removeStudentPlacement(
			                                    ActionMapping mapping,
			                                   ActionForm form,
			                                   HttpServletRequest request,
			                                   HttpServletResponse response) throws Exception {
		        		                                                 StudentPlacementForm studentPlacementForm = (StudentPlacementForm) form;
		        		                                                 StudentPlacementUI stuPlacementUI=new StudentPlacementUI();
		        		                                                 ActionMessages messages=new ActionMessages();
		        		                                                 String nextPage=stuPlacementUI.removeStudentPlacement(mapping, request, response, studentPlacementForm, messages);
		                                                                 if (!messages.isEmpty()) {
		                         	                                                        addErrors(request,messages);
		                         	                                                        studentPlacementForm.setCurrentPage(nextPage);
		                 	                                              }
		                                                                  return mapping.findForward(nextPage); 
    }
	/*	Removing the  previous placements for a student, that's the placements the student may have selected during registration and 
	 * are no longer interested in going through with them.The decision is to remove them at the controller level, in the StudentPlacementAction class, the method is 
	 * saveStudentPlacement().The reason is to ensure that saving of a placement is never changed and it is the same ,regardless of whether the 
	 * we are saving a placement with Date  Blocks or without Date Blocks.
	 * 
	 * Removing the previous placements calls for addition of a new method to remove placements in the StudentPlacementDAO class.
	 * the new remove placement method will have two arguments, the academic year and the  the student number.
	 * since the aim is to remove the previously selected placements, the method will just delete every record in the tpuspl  table of  the student system database 
	 * for the entered student, for the current year, which will happen to be the selected placements.
	 * This method assumes that we have not added the newly requested placement  for the student in question, so 
	 * this method should be executed before the action of saving the new placement for this student.
	 * 
	 */
	public ActionForward saveStudentPlacement(
			                                              ActionMapping mapping,
			                                              ActionForm form,
			                                              HttpServletRequest request,
			                                              HttpServletResponse response) throws Exception {
		                                                                 StudentPlacementForm studentPlacementForm = (StudentPlacementForm) form;
		                                                                           StudentPlacementUI stuPlacementUI=new StudentPlacementUI(); 
		                                                                           ActionMessages messages=new ActionMessages();
		                                                                           StudentPlacement.setDatesFromRequest(studentPlacementForm, request);
			                                                                        stuPlacementUI.changeDateFormat(studentPlacementForm.getStudentPlacement());
			                                                                       studentPlacementForm.getStudentPlacement().setSecPrelimPlacement(false);
 
InfoMessagesUtil  infoMessagesUtil =new InfoMessagesUtil ();
 Supervisor supervisor=new Supervisor();
int supervisorCode=studentPlacementForm.getStudentPlacement().getSupervisorCode();
          
 if((!studentPlacementForm.getStudentPlacementAction().equals("edit"))||
       studentPlacementForm.getSameSupervisorSelected().equals("N")){

        if((Integer.parseInt(supervisor.getStudentsAllocated(supervisorCode)))>=
                     Integer.parseInt(supervisor.getStudentsAllowed(supervisorCode))){
            String message="Choose another supervisor.This supervisor has reached the maximum allowed total ";
                         infoMessagesUtil.addMessages(messages,message);
              		     addErrors(request,messages);
              		     return mapping.findForward(studentPlacementForm.getCurrentPage());
           }
 }
	       
if(!studentPlacementForm.getStudentPlacementAction().equals("edit")){
    if(studentPlacementForm.getUsingBlockDates().equals("Y")){
     if(studentPlacementForm.getIsPGCE().equals("N")){
       if(studentPlacementForm.getStudyLevel()>1){
           if((Integer.parseInt(supervisor.getStudentsAllocated(supervisorCode))+2)>
                     Integer.parseInt(supervisor.getStudentsAllowed(supervisorCode))){
            String message="The placeents were not updated . This supervisor is one placement short of reaching a maximum allowed\n"+
                           "but there are two placements to update with this supervisor. ";
                         infoMessagesUtil.addMessages(messages,message);
              		     addErrors(request,messages);
              		     return mapping.findForward(studentPlacementForm.getCurrentPage());
           }
       }
    }
  }
}
  if(studentPlacementForm.getUsingBlockDates().equals("N")){
                                                         if(studentPlacementForm.getIsPGCE().equals("N")){
                                                        	       if(studentPlacementForm.getStudentPlacementAction().equals("add")){
                                                                        studentPlacementForm.getStudentPlacement().removeStuPlacement(studentPlacementForm,messages);
                                                                        if(!messages.isEmpty()){
              		                            	                           addErrors(request,messages);
              		                            	                             return mapping.findForward(studentPlacementForm.getCurrentPage());
              		                                                       }
                                                        	        }
                                                            }
                                                       }
                                                                                   if(studentPlacementForm.getStudentPlacementAction().equals("add")){
                                                                                	   if(studentPlacementForm.getUsingBlockDates().equals("N")){
                                                                                        	       studentPlacementForm.getStudentPlacement().setPlacementPrd(3);
                                                                                           }else{
                                                                           
			                                                                    	              studentPlacementForm.getStudentPlacement().setPlacementPrd(1);
                                                                                           }
			                                                                       }
		                                                                            messages=stuPlacementUI.saveStuPlacement(studentPlacementForm);
		                                                                            if(studentPlacementForm.getStudentPlacement().getStartDateSecPracPeriod()==null){
		                                                                            	      studentPlacementForm.getStudentPlacement().setStartDateSecPracPeriod("");
		                                                                            }
		                                                                            if(studentPlacementForm.getStudentPlacement().getEndDateSecPracPeriod()==null){
	                                                                            	           studentPlacementForm.getStudentPlacement().setEndDateSecPracPeriod("");
	                                                                               }
		                                                                            if((studentPlacementForm.getStudentPlacement().getStartDateSecPracPeriod().trim().equals(""))||
                                                                                               (studentPlacementForm.getStudentPlacement().getEndDateSecPracPeriod().trim().equals(""))){
		                                                                            	                studentPlacementForm.getStudentPlacement().setTwoPlacements(false);
		                                                                            }
                                                                                    if( studentPlacementForm.getStudentPlacement().isTwoPlacements()){
		                                                                            	              studentPlacementForm.getStudentPlacement().setSecPrelimPlacement(true);
		                                                                            	              if(studentPlacementForm.getStudentPlacementAction().equals("editPrelimPlacement")||
		                                                                            	            		         studentPlacementForm.getStudentPlacementAction().equals("edit")){
		                                                                            	                             PrelimStudentPlacementImpl  prelimStudentPlacementImpl =new PrelimStudentPlacementImpl();
		                                                                            	                             Short   acadYear=Short.parseShort(studentPlacementForm.getAcadYear());
                                                                           	            		                     Integer     stuNum=Integer.parseInt(studentPlacementForm.getStudentPlacement().getStuNum());
		                                                                            	                             prelimStudentPlacementImpl.updateSecPlacement(acadYear,(short)0, stuNum,
		                                                                            	            		                                          studentPlacementForm.getStudentPlacement());
		                                                                            	              }else{
		                                                                            	                             Integer schoolCode=studentPlacementForm.getStudentPlacement().getSchoolCode();
		                                                                            	                             studentPlacementForm.getStudentPlacement().setSchoolCode(
		                                                                            	            		         studentPlacementForm.getStudentPlacement().getSchoolCode2());
		                                                                            	                             String startDate=studentPlacementForm.getStudentPlacement().getStartDate();
		                                                                            	                             String endDate=studentPlacementForm.getStudentPlacement().getEndDate();
		  		                                                                                                     studentPlacementForm.getStudentPlacement().setStartDate(
                                                                                             		                 studentPlacementForm.getStudentPlacement().getStartDateSecPracPeriod());
                                                                                                                     studentPlacementForm.getStudentPlacement().setEndDate(
                                                                                             		                 studentPlacementForm.getStudentPlacement().getEndDateSecPracPeriod());
                                                                                                                     studentPlacementForm.getStudentPlacement().setPlacementPrd(2);
                                                                                                                     String numOfWeeks= studentPlacementForm.getStudentPlacement().getNumberOfWeeks();
                                                                                                                     studentPlacementForm.getStudentPlacement().setNumberOfWeeks(
                                                                                                    		         studentPlacementForm.getStudentPlacement().getNumberOfWeeksSecPracPrd());
                                                                                                                     messages=stuPlacementUI.saveStuPlacement(studentPlacementForm);
                                                                                                                     studentPlacementForm.getStudentPlacement().setStartDate(startDate);
                                                                                                                     studentPlacementForm.getStudentPlacement().setEndDate(endDate);
                                                                                                                     studentPlacementForm.getStudentPlacement().setPlacementPrd(1);
                                                                                                                     studentPlacementForm.getStudentPlacement().setNumberOfWeeks(numOfWeeks);
                                                                                                                     studentPlacementForm.getStudentPlacement().setSchoolCode(schoolCode);
                                                                                       	              }
                                                                                    }
                                                                                    StudentPlacement.setDatesDataToRequest(studentPlacementForm, request);
		                              		                                        studentPlacementForm.setCommunicationSchool(studentPlacementForm.getStudentPlacement().getSchoolCode());
		                                                                            if(!messages.isEmpty()){
		                            	                                                            addErrors(request,messages);
		                            	                                                            return mapping.findForward(studentPlacementForm.getCurrentPage());
		                                                                           }
		                           return mapping.findForward(listStudentPlacement(mapping,form,request,response));	
	}
	public ActionForward addPlacement(ActionMapping mapping,
			                                                        ActionForm form,
			                                                        HttpServletRequest request,
			                                                        HttpServletResponse response) {
		                                                                         ActionMessages messages=new ActionMessages();
		                                                                         StudentPlacementForm studentPlacementForm = (StudentPlacementForm) form;
		 	         	                                                          try{
		                                                                                              StudentPlacement placement=studentPlacementForm.getStudentPlacement();
		                                                                                               Qualification qualification=studentPlacementForm.getStudent().getQualification();
		                                                                                              if(qualification.isPgceStudent()){
		                                                                                                                        studentPlacementForm.setIsPGCE("Y");
		                                                                                               }else{
		                                                            	                                                        studentPlacementForm.setIsPGCE("N");
		                                                                                               }
		                                                                                              StudentPlacementUI  studentPlacementUI=new StudentPlacementUI();
		 	         	                                                                              messages=studentPlacementUI.addPlacement(studentPlacementForm);
		 	         	                                                                             if(messages.isEmpty()){
		 	         	            	                                                                            studentPlacementForm.setCommunicationSchool(placement.getSchoolCode());
		 	         	                                                                              } 
		 	         	                                                                               if(!messages.isEmpty()){
	 	         	               	                                                                                        handleAddPlacementErrors(messages,request,studentPlacementForm);
	 	 			                                                                                                        return mapping.findForward("editStudentPlacement");	
		                                                                                                }
	 	 		                                                                                       return mapping.findForward(listStudentPlacement(mapping,form,request,response));
		 	         	                                                          }catch(Exception ex){
		 	         	            	                                                                                     handleAddPlacementErrors(messages,request,studentPlacementForm);
	 			                                                                                                              return mapping.findForward("editStudentPlacement");
		 	         	                                                         }
	}
	private void handleAddPlacementErrors(ActionMessages messages,HttpServletRequest request,
	                                               StudentPlacementForm studentPlacementForm){
                                                   addErrors(request,messages);
                                                   studentPlacementForm.setPreviousPage(studentPlacementForm.getCurrentPage());
                                                   studentPlacementForm.setCurrentPage("editStudentPlacement");
   }
   public ActionForward displayLogList(// it lists all the logs  for Placement Log button
			                                      ActionMapping mapping,
			                                      ActionForm form,
			                                      HttpServletRequest request,
			                                      HttpServletResponse response) throws Exception {
		                                                                      StudentPlacementForm studentPlacementForm = (StudentPlacementForm) form;
		                                                                      StudentPlacementLogUI studentPlacementLogUI=new StudentPlacementLogUI();
		                                                                      studentPlacementLogUI.setLogList(studentPlacementForm);
		                                             return mapping.findForward("listLogs");	
	}
    public ActionForward supervForProvOnchangeAction(
		                    	                          ActionMapping mapping,
			                                              ActionForm form,
			                                              HttpServletRequest request,
			                                              HttpServletResponse response) throws Exception {
    	                                                                                   StudentPlacementForm studentPlacementForm = (StudentPlacementForm) form;
if(studentPlacementForm.getModuleCode()==null){
    studentPlacementForm.setModuleCode(studentPlacementForm.getStudentPlacement().getModule());
  }
    	                                                                                     if(!studentPlacementForm.getModuleCode().equals(
    	                                                                                		   studentPlacementForm.getStudentPlacement().getModule())){
    	                                                                                	           if(studentPlacementForm.getStudentPlacementAction().equals("editPrelimPlacement")
                                                                                                             ||studentPlacementForm.getStudentPlacementAction().equals("edit")){    
    	                                                                                	                   studentPlacementForm.getStudentPlacement().setModule(
    	                                                                                			           studentPlacementForm.getModuleCode());
                                                                               	                       }
    	                                                                                	           if(studentPlacementForm.getStudentPlacementAction().equals("add")){    
    	                                                                                	        	              studentPlacementForm.setModuleCode(
    	                                                                                	        			             studentPlacementForm.getStudentPlacement().getModule());
	                                                                                			        }
    	                                                                                	           return mapping.findForward("editPrelimPlacement");
       	                                                                                	  }
    	                                                                                    try{    Module module=new Module();
		                                                                                        String moduleCode=studentPlacementForm.getStudentPlacement().getModule();
		                                                                                        Qualification qual=studentPlacementForm.getStudent().getQualification();
		                                                                                        int acadYear=Integer.parseInt(studentPlacementForm.getAcadYear());
		                                                                                       module=module.getModule(qual,moduleCode,acadYear);
		                                                                                    	 if(!qual.isPgceStudent()){
		                                                                                             StudentPlacement stuplcmt=studentPlacementForm.getStudentPlacement();
		                                                                                            int index=studentPlacementForm.getPracticeBatchDateListsIndex();
		                                                                                            int index2=studentPlacementForm.getPracticeBatchDateSecPracPrdListsIndex();
		                                                                                            PlacementUtilities placementUtilities=new PlacementUtilities();
		                                                                                             StudentPlacement placement=studentPlacementForm.getStudentPlacement();
                                                                                                    if( index!=0){
		                                	                                                                              PracticeBatchDate practiceBatchDate=( PracticeBatchDate)studentPlacementForm.getPracticeBatchDateList().get(index);
		                                	                                                                              stuplcmt.setStartDate(practiceBatchDate.getStartDate());
		                                	                                                                              stuplcmt.setEndDate(practiceBatchDate.getEndDate());
		                                	                                                                              stuplcmt.setNumberOfWeeks(""+practiceBatchDate.getPracticalDays());
		                                	                                                                               placementUtilities.setPlacementDateToRequestObject(request, placement);
		                                	                                                       }else{
		                                	                                                                    	      stuplcmt.setStartDate("");
                  	                                                                                                      stuplcmt.setEndDate("");
                  	                                                                                                      stuplcmt.setNumberOfWeeks("");
                  	                                                                             }
                                                                                                 if( studentPlacementForm.getStudentPlacement().isTwoPlacements()){
		                                                                                                                        if(index2!=0){
		                                                                                        	                                      PracticeBatchDate practiceBatchDate=
		                                                                                        	                            		         (PracticeBatchDate)studentPlacementForm.getPracticeBatchDateSecPracPrdList().get(index2);
                       	                                                                                                                  stuplcmt.setStartDateSecPracPeriod(practiceBatchDate.getStartDate());
                       	                                                                                                                  stuplcmt.setEndDateSecPracPeriod(practiceBatchDate.getEndDate());
                       	                                                                                                                  stuplcmt.setNumberOfWeeksSecPracPrd(""+practiceBatchDate.getPracticalDays());
                       	                                                                                                                 placementUtilities.setPlacementDateToRequestObject(request, placement);
                       	                                                                                                        }else{
                   	                                                                    	                                             stuplcmt.setStartDateSecPracPeriod("");
                                                                                                                                         stuplcmt.setEndDateSecPracPeriod("");
                                                                                                                                         stuplcmt.setNumberOfWeeksSecPracPrd("");
                                                                                                                             }
                                                                                                 }
		                                                                                    }
    	                                                                               }catch(Exception ex){}
		                                                                                      return mapping.findForward("editPrelimPlacement");
    }
	
}