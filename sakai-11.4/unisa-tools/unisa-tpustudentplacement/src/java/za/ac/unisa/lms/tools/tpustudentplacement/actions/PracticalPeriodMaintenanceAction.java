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
			map.put("button.copy", "copyPracPeriodBatchDate");	
			map.put("editPracPeriodBatchDate", "editPracPeriodBatchDate");
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
		                	 if(  studentPlacementForm.getCurrentPage().equals("inputPractical")){
	               	   	                studentPlacementForm.setPreviousPage("listPracticalBatches");
	               	          }
		                	 studentPlacementForm.setCurrentPage( studentPlacementForm.getPreviousPage());
	                 	return mapping.findForward(studentPlacementForm.getPreviousPage());	
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
			                                                             		PracticeBatchDate practiceBatchDate=new PracticeBatchDate();
			                                                             		String array[] = studentPlacementForm.getIndexNrSelected();
                                                      			               for (int i=0; i <array.length; i++) {
			                                                             			                 practiceBatchDate = (PracticeBatchDate)studentPlacementForm.getPracticeBatchDateList().get(Integer.parseInt(array[i]));			
			                                                             			                break;
			                                                             		}
			                                                             		studentPlacementForm.setPracticeBatchDate(practiceBatchDate);
			                                                             		PracticeBatchDate originalPracticeBatchDate=new PracticeBatchDate(practiceBatchDate);
			                                                             		studentPlacementForm.setOriginalPracticeBatchDate(originalPracticeBatchDate);
			                                                             		 request.setAttribute("startDate",practiceBatchDate.getFromDate());
			                               	                                     request.setAttribute("endDate",practiceBatchDate.getToDate());
			                                                             		 studentPlacementForm.setCurrentPage("inputPractical");
			                                                             		 studentPlacementForm.setAddPracActive(false);
			                                                                 	return mapping.findForward( studentPlacementForm.getCurrentPage());	
		}
		public ActionForward delete(
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
		                                                             			if (!messages.isEmpty()) {
		                                                             		         	addErrors(request,messages);
		                                                             					return mapping.findForward("listPracticalBatches");				
		                                                             		}
		                                                             			PracticeDatesMaintenance practiceDatesMaintenance=new PracticeDatesMaintenance();
		                                                             		 String array[] = studentPlacementForm.getIndexNrSelected();
                                                     			             for (int i=0; i <array.length; i++) {
                                                     			            	                 PracticeBatchDate practiceBatchDate =
                                                     			            	                		                                   (PracticeBatchDate)studentPlacementForm.getPracticeBatchDateList().get(Integer.parseInt(array[i]));			
		                                                             			                 practiceDatesMaintenance.deletePracticalDateBatch( practiceBatchDate);
		                    			                                   	}
		                                                             		
	                          	               	return display(mapping,form,request,response);	
		}
		public ActionForward addPracPeriodBatchDate(
	                                                                                      ActionMapping mapping,
	 	                                                                                  ActionForm form,
	                                                                                       HttpServletRequest request,
	                                                                                       HttpServletResponse response) throws Exception {
	                                                                                                                                      StudentPlacementForm studentPlacementForm = (StudentPlacementForm) form;	
	                                                                                                                                      PracticeBatchDate  practiceBatchDate=new PracticeBatchDate();
	                                	                                                                                                 studentPlacementForm.setPracticeBatchDate(practiceBatchDate);
	                                	                                                                                                  request.setAttribute("startDate",practiceBatchDate.getFromDate());
	                       	                                                                                                              request.setAttribute("endDate",practiceBatchDate.getToDate());
	                                	                                                                                                 studentPlacementForm.setCurrentPage("inputPractical");
	                                	                                                                                      	         studentPlacementForm.setAddPracActive(true);
	                                                                                                                 return mapping.findForward( studentPlacementForm.getCurrentPage());	
	       }
		
		public ActionForward initial(
				                                            ActionMapping mapping,
				                                            ActionForm form,
				                                            HttpServletRequest request,
				                                            HttpServletResponse response) throws Exception {
			                                                                                                StudentPlacementForm studentPlacementForm = (StudentPlacementForm) form;	
			                                                                                               PracticeDatesMaintenance practiceDatesMaintenance=new PracticeDatesMaintenance();
			                                                                                               studentPlacementForm.setPracticeBatchDate(new PracticeBatchDate());
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
	                                                            return mapping.findForward( studentPlacementForm.getCurrentPage());
		}
		public ActionForward savePracPeriodBatchDate(
			                                    	ActionMapping mapping,
				                                    ActionForm form,
				                                    HttpServletRequest request,
				                                    HttpServletResponse response) throws Exception {
			
			                                                       StudentPlacementForm studentPlacementForm = (StudentPlacementForm) form;
			                                                       ActionMessages messages = new ActionMessages();	
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
			                                                         if (!messages.isEmpty()) {
			                                                          	                addErrors(request,messages);
					                                                                    if( studentPlacementForm.isAddPracActive()){
					                                                            	                 return addPracPeriodBatchDate(mapping,form,request,response);	
		                                                                                }else{
		                                                                                        	return editPracPeriodBatchDate(mapping,form,request,response);	
		                                              	                                }
			                                                          }
			                                                         studentPlacementForm.getPracticeBatchDate().setFromDate(request.getParameter("startDate").toString());
			                                                         studentPlacementForm.getPracticeBatchDate().setToDate(request.getParameter("endDate").toString());
			                                                         PracticeDatesMaintenance practiceDatesMaintenance=new PracticeDatesMaintenance();
			                                                          if( studentPlacementForm.isAddPracActive()){
			                                            	                               practiceDatesMaintenance.savePracticePeriod(studentPlacementForm.getPracticeBatchDate());
			                                                          }else{
			                                                	                               practiceDatesMaintenance.updatePracticePeriod(studentPlacementForm.getPracticeBatchDate(),
			                                                	                            		                                                                                                              studentPlacementForm.getOriginalPracticeBatchDate());
			                                              	          }
			                                                         return display(mapping,form,request,response);	
		}
		public ActionForward copyPracPeriodBatchDate(
			                                                           	ActionMapping mapping,
			                                                           	ActionForm form,
			                                                         	HttpServletRequest request,
				                                                        HttpServletResponse response) throws Exception {
			                                                                       StudentPlacementForm studentPlacementForm = (StudentPlacementForm) form;
			                                                                     	int selectedProv=studentPlacementForm.getPracticeBatchDate().getProvCode();
                                                                                  ActionMessages messages = new ActionMessages();	
			                                                                        if (selectedProv==-1){
                                                                                                  	messages.add(ActionMessages.GLOBAL_MESSAGE,
                                                                                                	new ActionMessage("message.generalmessage",
                         	                                                                            "Please select a province  to copy  Practical  date batches "));
                                                                                     }
                                                                                 	if (!messages.isEmpty()) {
				                                                                                   addErrors(request,messages);
				                                                                                	return mapping.findForward( studentPlacementForm.getCurrentPage());				
		                                                                         	}
                                                                                 	 PracticeDatesMaintenance practiceDatesMaintenance=new PracticeDatesMaintenance();
                                                                                 	List<Province> provList=studentPlacementForm.getListRSAProvinces();
                                                                                 	practiceDatesMaintenance.saveCopyOfProvDateBatches(selectedProv, provList);
                                                                                 	PracticeBatchDate practiceBatchDate=studentPlacementForm.getPracticeBatchDate();
                                                                                 	practiceBatchDate.resetToDisplayAllDateBatches();
                                                          return display(mapping,form,request,response);	
		}
}
