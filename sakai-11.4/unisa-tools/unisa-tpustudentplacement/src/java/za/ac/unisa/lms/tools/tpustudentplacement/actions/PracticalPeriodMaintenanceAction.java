package za.ac.unisa.lms.tools.tpustudentplacement.actions;

	import java.util.ArrayList;
	import java.util.HashMap;
	import java.util.List;
	import java.util.Map;
	import javax.servlet.http.HttpServletRequest;
	import javax.servlet.http.HttpServletResponse;
	import org.apache.struts.action.ActionForm;
	import org.apache.struts.action.ActionForward;
	import org.apache.struts.action.ActionMapping;
	import org.apache.struts.action.ActionMessage;
	import org.apache.struts.action.ActionMessages;
	import org.apache.struts.actions.LookupDispatchAction;
    import za.ac.unisa.lms.tools.tpustudentplacement.dao.*;
	import za.ac.unisa.lms.tools.tpustudentplacement.forms.*;
import za.ac.unisa.lms.tools.tpustudentplacement.utils.DateUtil;
import za.ac.unisa.lms.tools.tpustudentplacement.utils.PlacementUtilities;

	public class PracticalPeriodMaintenanceAction extends LookupDispatchAction{
		private class operListener implements java.awt.event.ActionListener {
			private Exception exception = null;
		
			operListener() {
				exception = null;
			}
		
			public Exception getException() {
				return exception;
			}
		
			public void actionPerformed(java.awt.event.ActionEvent aEvent) {
				exception = new Exception(aEvent.getActionCommand());
			}
		}
		
		protected Map getKeyMethodMap() {
			// TODO Auto-generated method stub
			Map map = new HashMap();
			map.put("initial", "initial");
			map.put("button.display", "display");
			map.put("button.back", "prevPage");
			map.put("button.edit", "editPracPeriodBatchDate");
			map.put("button.save", "savePracPeriodBatchDate");	
			map.put("button.add", "addPracPeriodBatchDate");	
			map.put("button.delete", "delete");	
			map.put("button.copy", "confirmDateBlockDeleteForCopyDates");	
			map.put("editPracPeriodBatchDate", "editPracPeriodBatchDate");
			map.put("addPracPeriodBatchDate", "addPracPeriodBatchDate");
			map.put("acadYearChangeAction","acadYearChangeAction");
			map.put("button.confirmDelete","confirmDelete");
			map.put("button.cancel","cancel");
			return map;
		}
			public ActionForward prevPage(
				                                    ActionMapping mapping,
				                                    ActionForm form,
				                                    HttpServletRequest request,
				                                    HttpServletResponse response) throws Exception {
			                                                         StudentPlacementForm studentPlacementForm = (StudentPlacementForm) form;	
		                	                                         if( studentPlacementForm.getCurrentPage().equals("listPracticalBatches")){
		                	   	                                                          studentPlacementForm.setPreviousPage("inputStuPlacement");
		                	   	                                                          
		                	                                          }
		                	                                          if(studentPlacementForm.getCurrentPage().equals("inputPractical")){
	               	   	                                                                studentPlacementForm.setPreviousPage("listPracticalBatches");
	               	   	                                                          return display(mapping,form,request,response);	
	               	                                                  }
		                	                                           studentPlacementForm.setCurrentPage( studentPlacementForm.getPreviousPage());
	                 	                                              return mapping.findForward(studentPlacementForm.getPreviousPage());	
			}
			public ActionForward cancel(
					                                  ActionMapping mapping,
				                                      ActionForm form,
					                                  HttpServletRequest request,
					                                  HttpServletResponse response) throws Exception {
				                                                                           StudentPlacementForm studentPlacementForm = (StudentPlacementForm) form;	
			                	                                                           studentPlacementForm.setCurrentPage("listPracticalBatches");
			                	                                                           studentPlacementForm.setIndexNrSelected(null);
			                   	                                     return mapping.findForward(studentPlacementForm.getCurrentPage());	
			}
		    public ActionForward editPracPeriodBatchDate(
				                                           ActionMapping mapping,
			                                    	       ActionForm form,
				                                           HttpServletRequest request,
				                                           HttpServletResponse response) throws Exception {
			                                                                     StudentPlacementForm studentPlacementForm = (StudentPlacementForm) form;	
			                                                                 	ActionMessages messages = new ActionMessages();	
			                                                            		 if (studentPlacementForm.getIndexNrSelected()==null ||
			                                                             				studentPlacementForm.getIndexNrSelected().length==0){
			                                                             				messages.add(ActionMessages.GLOBAL_MESSAGE,
			                                                             						new ActionMessage("message.generalmessage",
			                                                             									"Please select a practice date batch"));
			                                                             			}
			                                                             		if (studentPlacementForm.getIndexNrSelected()!=null &&
			                                                             				studentPlacementForm.getIndexNrSelected().length>1){
			                                                             				messages.add(ActionMessages.GLOBAL_MESSAGE,
			                                                             						new ActionMessage("message.generalmessage",
			                                                             									"Please select only one practice date batch"));
			                                                             			}
			                                                             		if (!messages.isEmpty()) {
			                                                             			          addErrors(request,messages);
			                                                             					  return mapping.findForward("listPracticalBatches");				
			                                                             		}
			                                                                	String array[] = studentPlacementForm.getIndexNrSelected();
			                                                             		PracticeBatchDate practiceBatchDate=(PracticeBatchDate)studentPlacementForm.getPracticeBatchDateList().get(Integer.parseInt(array[0]));			
			                                                             		studentPlacementForm.setPracticeBatchDate(practiceBatchDate);
			                                                             		PracticeBatchDate originalPracticeBatchDate=new PracticeBatchDate(practiceBatchDate);
			                                                             		studentPlacementForm.setOriginalPracticeBatchDate(originalPracticeBatchDate);
			                                                             		studentPlacementForm.setAddPracActive(false);
			                                                                    request.setAttribute("startDate", DateUtil.dateFormatChange(practiceBatchDate.getStartDate(),"/"));
			                                                                    request.setAttribute("endDate", DateUtil.dateFormatChange(practiceBatchDate.getEndDate(),"/"));
			                                                                    studentPlacementForm.setCurrentPage("inputPractical");
			                                                             		 return mapping.findForward(studentPlacementForm.getCurrentPage());	
		}
		public ActionForward confirmDelete(
	                                                    ActionMapping mapping,
	 	                                                ActionForm form,
	                                                    HttpServletRequest request,
	                                                    HttpServletResponse response) throws Exception {
	                                                                         StudentPlacementForm studentPlacementForm = (StudentPlacementForm) form;	
	                                                                    	ActionMessages messages = new ActionMessages();	
	                                                                    	 if (studentPlacementForm.getIndexNrSelected()==null ||
		                                                             				studentPlacementForm.getIndexNrSelected().length==0){
		                                                             				messages.add(ActionMessages.GLOBAL_MESSAGE,
		                                                             						new ActionMessage("message.generalmessage",
		                                                             									"Please select a date block to delete"));
		                                                             			}
		                                                             			if (!messages.isEmpty()) {
		                                                             		         	addErrors(request,messages);
		                                                             					return mapping.findForward("listPracticalBatches");				
		                                                             		    }
		                                                             			PracticeDatesMaintenance practiceDatesMaintenance=new PracticeDatesMaintenance();
		                                                      		            studentPlacementForm.setCurrentPage("confirmDateBlockDelete");
		                                                             			if(practiceDatesMaintenance.checkDateBlockAssigned(studentPlacementForm)){
		                                                             				           studentPlacementForm.setDateBlockAssigned("Y");
		                                                             		    }else{
		                                                             			             	studentPlacementForm.setDateBlockAssigned("N");
		                                                             			}
		                                                             			return mapping.findForward(studentPlacementForm.getCurrentPage());
		}
		public ActionForward confirmDateBlockDeleteForCopyDates(
                                                                 ActionMapping mapping,
                                                                 ActionForm form,
                                                                 HttpServletRequest request,
                                                                 HttpServletResponse response) throws Exception {
                                                                                    StudentPlacementForm studentPlacementForm = (StudentPlacementForm) form;	
                                	                                                ActionMessages messages = new ActionMessages();	
                                	                                                int selectedProv=studentPlacementForm.getPracticeBatchDate().getProvCode();
                                                                                     if (selectedProv==-1){
                                                                                                    	messages.add(ActionMessages.GLOBAL_MESSAGE,
                                                                                                  	new ActionMessage("message.generalmessage",
                           	                                                                            "Please select a province  to copy  its  date Blocks"));
                                                                                       }
                                                                                   	if (!messages.isEmpty()) {
  				                                                                                   addErrors(request,messages);
  				                                                                                	return mapping.findForward( "listPracticalBatches");				
  		                                                                         	}
                                                                                   	studentPlacementForm.setCurrentPage("confirmDateBlockDeleteForCopyDates");
                                                                     return mapping.findForward("confirmDateBlockDelete");
      }
	  public ActionForward delete(
                                                ActionMapping mapping,
                                                ActionForm form,
                                                HttpServletRequest request,
                                                HttpServletResponse response) throws Exception {
                                                              StudentPlacementForm studentPlacementForm = (StudentPlacementForm) form;	
                                	                           PracticeDatesMaintenance practiceDatesMaintenance=new PracticeDatesMaintenance();
                                	                           if(studentPlacementForm.getCurrentPage().equals("confirmDateBlockDelete")){
                             		                                     String array[] = studentPlacementForm.getIndexNrSelected();
             			                                                 for (int i=0; i <array.length; i++) {
             			            	                                    PracticeBatchDate practiceBatchDate =
             			            	                		              (PracticeBatchDate)studentPlacementForm.getPracticeBatchDateList().get(Integer.parseInt(array[i]));			
                             			                                             practiceDatesMaintenance.deletePracticalDateBatch( practiceBatchDate);
                                                                   	       }
                                	                               }
                                	                                if(studentPlacementForm.getCurrentPage().equals("confirmDateBlockDeleteForCopyDates")){
                                	                                          	    List<Province> provList=studentPlacementForm.getListRSAProvinces();
                                	                                	            int selectedProv=studentPlacementForm.getPracticeBatchDate().getProvCode();
                                                                                	practiceDatesMaintenance.saveCopyOfProvDateBatches(selectedProv, provList);
                                                                      	            PracticeBatchDate practiceBatchDate=studentPlacementForm.getPracticeBatchDate();
                                                                                	practiceBatchDate.resetToDisplayAllDateBatches();
                                	                                }
                                       	return display(mapping,form,request,response);	
       }
		public ActionForward addPracPeriodBatchDate(
	                                                         ActionMapping mapping,
	 	                                                     ActionForm form,
	                                                         HttpServletRequest request,
	                                                         HttpServletResponse response) throws Exception {
	                                                         StudentPlacementForm studentPlacementForm = (StudentPlacementForm) form;	
	                                                                        ActionMessages messages = new ActionMessages();	
	                                                                       PracticeBatchDate  practiceBatchDate= studentPlacementForm.getPracticeBatchDate();
	                                                                        if (practiceBatchDate.isAllSelected()){
	                                                                        	  studentPlacementForm.setCurrentPage("listPracticalBatches");
	      			                                                    		         messages.add(ActionMessages.GLOBAL_MESSAGE,
	      					                                                              new ActionMessage("message.generalmessage",
	      								                                          "To add you need to select a specific province, study level, practical period and academic year"));
	      			                                                    		            if (!messages.isEmpty()) {
	      			                                                              	                  addErrors(request,messages);
	      			                                                    		                	return mapping.findForward( studentPlacementForm.getCurrentPage());	  
	      			                                                    		            }
	                                                                        }
	                                                                        studentPlacementForm.setCurrentPage("inputPractical");
	                                	                           	        studentPlacementForm.setAddPracActive(true);
	                                	                                    PracticeDatesMaintenance practiceDatesMaintenance=new PracticeDatesMaintenance();
	                                	                                    practiceDatesMaintenance.setDateBlockDefaults(request, studentPlacementForm);
	                                                         return mapping.findForward( studentPlacementForm.getCurrentPage());	
	       }
		public ActionForward initial(
				                                            ActionMapping mapping,
				                                            ActionForm form,
				                                            HttpServletRequest request,
				                                            HttpServletResponse response) throws Exception {
			                                                              StudentPlacementForm studentPlacementForm = (StudentPlacementForm) form;	
			                                                              PracticeDatesMaintenance practiceDatesMaintenance=new PracticeDatesMaintenance();
			                                                              practiceDatesMaintenance.initialiseDataForMaintenanceFunction(studentPlacementForm,request);
			                                                                                               studentPlacementForm.setCurrentPage("listPracticalBatches");
			              		                              return mapping.findForward( studentPlacementForm.getCurrentPage());
		}
		public ActionForward display(
			                                         	ActionMapping mapping,ActionForm form,
				                                        HttpServletRequest request, HttpServletResponse response) throws Exception {
			                                                                         StudentPlacementForm studentPlacementForm = (StudentPlacementForm) form;	
			                                                                          PracticeDatesMaintenance practiceDatesMaintenance=new PracticeDatesMaintenance();
			                                                                          List practicalDAteBatchList=practiceDatesMaintenance.getPracticePeriodList(studentPlacementForm.getPracticeBatchDate());
			                                                                          studentPlacementForm.setPracticeBatchDateList(practicalDAteBatchList);
			                                                                          studentPlacementForm.setCurrentPage("listPracticalBatches");
			                                                                          studentPlacementForm.setIndexNrSelected(new String[practicalDAteBatchList.size()]);
	                                                            return mapping.findForward( studentPlacementForm.getCurrentPage());
		}
		public ActionForward savePracPeriodBatchDate(
			                                    	ActionMapping mapping,
				                                    ActionForm form,
				                                    HttpServletRequest request,
				                                    HttpServletResponse response) throws Exception {
			                                                       StudentPlacementForm studentPlacementForm = (StudentPlacementForm) form;
			                                                       ActionMessages messages = new ActionMessages();	
			                                                       PracticeDatesMaintenance practiceDatesMaintenance=new PracticeDatesMaintenance();
			                                                       studentPlacementForm.getPracticeBatchDate().seStartDate(request.getParameter("startDate").toString());
			                                                       studentPlacementForm.getPracticeBatchDate().setEndDate(request.getParameter("endDate").toString());
			                                                       
			                                                       if (!PlacementUtilities.isInt(""+studentPlacementForm.getPracticeBatchDate().getPracticalDays())){
			                                                    		                	messages.add(ActionMessages.GLOBAL_MESSAGE,
					                                                                    	new ActionMessage("message.generalmessage",
								                                                                    	                               "Please enter number of days for the practical ,must be a number"));
			                                                        }
			                                                        if (studentPlacementForm.getPracticeBatchDate().getPracticalDays()<=0){
			                                                                                    	messages.add(ActionMessages.GLOBAL_MESSAGE,
					                                                                             	new ActionMessage("message.generalmessage",
								                                                                                                                         "Practical  days  must be greater than zero "));
			                                                        }
			                                                        if (studentPlacementForm.getPracticeBatchDate().getPracticalDays()>=100){
                                                                    	messages.add(ActionMessages.GLOBAL_MESSAGE,
                                                                     	new ActionMessage("message.generalmessage",
	                                                                                                                         "Practical  days  must less than 100 "));
                                                                   }
			                                                        String startDateStr=studentPlacementForm.getPracticeBatchDate().getStartDate();
			                                                        String  endDateStr=studentPlacementForm.getPracticeBatchDate().getEndDate();
			                                                         int  selectedAcadYear=studentPlacementForm.getPracticeBatchDate().getAcademicYear();
			                                                         String dateValidationReturnMsg=practiceDatesMaintenance.validateDates(selectedAcadYear, startDateStr, endDateStr);
			                                                         if (!dateValidationReturnMsg.isEmpty()){
                                                                                              	messages.add(ActionMessages.GLOBAL_MESSAGE,
                                                                      	                               new ActionMessage("message.generalmessage",dateValidationReturnMsg));
                                                                    }
			                                                        request.setAttribute("startDate",startDateStr);
			                                                        request.setAttribute("endDate",endDateStr);
			                                                          if (!messages.isEmpty()) {
                                                     	                        addErrors(request,messages);
	                                                                          return mapping.findForward("inputPractical");
                                                                       }
                                                                      if(studentPlacementForm.isAddPracActive()){
			                                            	                      practiceDatesMaintenance.savePracticePeriod(studentPlacementForm.getPracticeBatchDate());
			                                                          }else{
			                                                	                   practiceDatesMaintenance.updatePracticePeriod(studentPlacementForm.getPracticeBatchDate(),
			                                                	                            		                                               studentPlacementForm.getOriginalPracticeBatchDate());
			                                              	          }
			                                                         return display(mapping,form,request,response);	
		}
		 public ActionForward acadYearChangeAction(
                                                               ActionMapping mapping,
                                                               ActionForm form,
                                                               HttpServletRequest request,
                                                               HttpServletResponse response) throws Exception {
                                                                                      StudentPlacementForm studentPlacementForm = (StudentPlacementForm) form;
                                                                                      PracticeDatesMaintenance practiceDatesMaintenance=new PracticeDatesMaintenance();
                                                                                      practiceDatesMaintenance.setDateBlockDefaults(request, studentPlacementForm);
                                                                   return mapping.findForward("inputPractical");
      }
}
