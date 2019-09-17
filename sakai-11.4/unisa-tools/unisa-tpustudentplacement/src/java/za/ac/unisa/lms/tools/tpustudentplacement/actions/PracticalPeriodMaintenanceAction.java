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
			return map;
		}
			public ActionForward prevPage(
				ActionMapping mapping,
				ActionForm form,
				HttpServletRequest request,
				HttpServletResponse response) throws Exception {
			
		                	StudentPlacementForm studentPlacementForm = (StudentPlacementForm) form;	
		                	if( studentPlacementForm.getCurrentPage().equals("listPracBatches")){
		                	   	           studentPlacementForm.setPreviousPage("inputStuPlacement");
		                	 }
		                	 if(  studentPlacementForm.getCurrentPage().equals("inputPrac")){
	               	   	                studentPlacementForm.setPreviousPage("listPracBatches");
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
			                                                             					return mapping.findForward("listPracBatches");				
			                                                             		}
			                                                             		PracticeBatchDate practiceBatchDate=new PracticeBatchDate();
			                                                             		for (int i=0; i <studentPlacementForm.getIndexNrSelected().length; i++) {
			                                                             			                String array[] = studentPlacementForm.getIndexNrSelected();
			                                                             			                practiceBatchDate = ( PracticeBatchDate)studentPlacementForm.getListPracticalPeriods().get(Integer.parseInt(array[i]));			
			                                                             			                studentPlacementForm.setSelectedDistrictIndex(i);
			                                                             			               i=studentPlacementForm.getIndexNrSelected().length;
			                                                             		}
			                                                             		studentPlacementForm.setPracticeBatchDate(practiceBatchDate);
			                                                             		 studentPlacementForm.setCurrentPage("inputPrac");
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
		                                                             					return mapping.findForward("listPracBatches");				
		                                                             		}
		                                                             		PracticeBatchDate practiceBatchDate=new PracticeBatchDate();
		                                                             		for (int i=0; i <studentPlacementForm.getIndexNrSelected().length; i++) {
		                                                             			                String array[] = studentPlacementForm.getIndexNrSelected();
		                                                             			                practiceBatchDate = ( PracticeBatchDate)studentPlacementForm.getListPracticalPeriods().get(Integer.parseInt(array[i]));			
		                                                             			                studentPlacementForm.setSelectedDistrictIndex(i);
		                                                             			                PracticeDatesMaintenance practiceDatesMaintenance=new PracticeDatesMaintenance();
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
	                                	                         studentPlacementForm.setPracticeBatchDate(new PracticeBatchDate());
	                                	                          studentPlacementForm.setCurrentPage("inputPrac");
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
			           	                            		                                                practiceDatesMaintenance.initialiseDataForMaintenanceFunction(studentPlacementForm);
			                                                                                               studentPlacementForm.setCurrentPage("listPracBatches");
			              		                              return mapping.findForward( studentPlacementForm.getCurrentPage());
		}
		public ActionForward display(
			                                         	ActionMapping mapping,ActionForm form,
				                                        HttpServletRequest request, HttpServletResponse response) throws Exception {
			                                                                         StudentPlacementForm studentPlacementForm = (StudentPlacementForm) form;	
			                                                                          PracticeDatesMaintenance practiceDatesMaintenance=new PracticeDatesMaintenance();
			                                                                          List practicalDAteBatchList=practiceDatesMaintenance.getPracticePeriodList(studentPlacementForm.getPracticeBatchDate());
			                                                                          studentPlacementForm.setPracticeBatchDateList(practicalDAteBatchList);
			                                                                          studentPlacementForm.setCurrentPage("listPracBatches");
	                                                            return mapping.findForward( studentPlacementForm.getCurrentPage());
		}
		
		public ActionForward savePracPeriodBatchDate(
			                                    	ActionMapping mapping,
				                                    ActionForm form,
				                                    HttpServletRequest request,
				                                    HttpServletResponse response) throws Exception {
			
			                                                       StudentPlacementForm studentPlacementForm = (StudentPlacementForm) form;
			                                                       ActionMessages messages = new ActionMessages();	
			                                                       if (!PlacementUtilities.isInt(""+studentPlacementForm.getPracticeBatchDate().getNumOfDays())){
			                                                    		                	messages.add(ActionMessages.GLOBAL_MESSAGE,
					                                                                    	new ActionMessage("message.generalmessage",
								                                                                    	                               "Please enter number of days for the practical ,must be a number"));
			                                                        }
			                                                        if (studentPlacementForm.getPracticeBatchDate().getNumOfDays()<=0){
			                                                                                    	messages.add(ActionMessages.GLOBAL_MESSAGE,
					                                                                             	new ActionMessage("message.generalmessage",
								                                                                                                                         "Practical  days  must be a positive number "));
			                                                        }
			                                                         if (!messages.isEmpty()) {
			                                                          	         addErrors(request,messages);
					                                                             return mapping.findForward( studentPlacementForm.getCurrentPage());				
			                                                          }
			                                                          PracticeDatesMaintenance practiceDatesMaintenance=new PracticeDatesMaintenance();
			                                                          if( studentPlacementForm.getPracticeBatchDate().getAcademicYear()==0){
			                                            	                            DateUtil dateUtil=new DateUtil();
	                                                                                    studentPlacementForm.getPracticeBatchDate().setAcademicYear(dateUtil.getYearInt());
                                                                                        practiceDatesMaintenance.savePracticePeriod(studentPlacementForm.getPracticeBatchDate());
			                                                                       
			                                                          }else{
			                                                	                               practiceDatesMaintenance.updatePracticePeriod(studentPlacementForm.getPracticeBatchDate());
			                                              	          }
			                                                         return display(mapping,form,request,response);	
		}
		public ActionForward copyPracPeriodBatchDate(
			                                                           	ActionMapping mapping,
			                                                           	ActionForm form,
			                                                         	HttpServletRequest request,
				                                                        HttpServletResponse response) throws Exception {
			                                                                       StudentPlacementForm studentPlacementForm = (StudentPlacementForm) form;
			                                                                        ActionMessages messages = new ActionMessages();	
			                                                                        if ((studentPlacementForm.getPracticeBatchDateList()==null )||(studentPlacementForm.getPracticeBatchDateList().isEmpty())){
                                                                                                  	messages.add(ActionMessages.GLOBAL_MESSAGE,
                                                                                                	new ActionMessage("message.generalmessage",
                         	                                                                       "Please enter practice date batches  for atleast one  province, before attempting to copy"));
                                                                                     }
                                                                                 	if (!messages.isEmpty()) {
				                                                                                   addErrors(request,messages);
				                                                                                	return mapping.findForward( studentPlacementForm.getCurrentPage());				
		                                                                         	}
                                                                                 	 PracticeDatesMaintenance practiceDatesMaintenance=new PracticeDatesMaintenance();
                                                                                 	List<PracticeBatchDate> provPracPeriodList=studentPlacementForm.getPracticeBatchDateList();
                                                                                 	int selectedProv=studentPlacementForm.getPracticeBatchDate().getProvCode();
                                                                                 	List<Province> provList=studentPlacementForm.getListRSAProvinces();
                                                                                 	practiceDatesMaintenance.saveCopyOfProvDateBatches(provPracPeriodList, selectedProv, provList);
                                                                                 	PracticeBatchDate practiceBatchDate=studentPlacementForm.getPracticeBatchDate();
                                                                                 	practiceBatchDate.resetToDisplayAllDateBatches();
                                                          return display(mapping,form,request,response);	
		}
}
