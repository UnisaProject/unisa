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
import za.ac.unisa.lms.tools.tpustudentplacement.model.modelImpl.DistrictUI;

public class SubProvinceMaintenanceAction extends LookupDispatchAction{
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
		map.put("button.edit", "editSubProv");
		map.put("button.save", "saveSubProv");	
		map.put("button.add", "addSubProv");	
		return map;
	}
		public ActionForward prevPage(
			ActionMapping mapping,
			ActionForm form,
			HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		StudentPlacementForm studentPlacementForm = (StudentPlacementForm) form;	

			return mapping.findForward(studentPlacementForm.getPreviousPage());	
		}
	public ActionForward editSubProv(
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
		                                                             									"Please select a sub province"));
		                                                             			}
		                                                             		if (studentPlacementForm.getIndexNrSelected()!=null &&
		                                                             				studentPlacementForm.getIndexNrSelected().length>1){
		                                                             				messages.add(ActionMessages.GLOBAL_MESSAGE,
		                                                             						new ActionMessage("message.generalmessage",
		                                                             									"Please select only one sub province"));
		                                                             			}
		                                                             		if (!messages.isEmpty()) {
		                                                             			addErrors(request,messages);
		                                                             					return mapping.findForward("listSubProvinces");				
		                                                             		}
		                                                             		SubProvince subProvince = new SubProvince ();
		                                                             		Province province = new Province();
		                                                             		
		                                                             		for (int i=0; i <studentPlacementForm.getIndexNrSelected().length; i++) {
		                                                             			String array[] = studentPlacementForm.getIndexNrSelected();
		                                                             			subProvince = (SubProvince)studentPlacementForm.getListSubProvincesOfProvince().get(Integer.parseInt(array[i]));			
		                                                             			studentPlacementForm.setSelectedDistrictIndex(i);
		                                                             			i=studentPlacementForm.getIndexNrSelected().length;
		                                                             		}
		                                                             		studentPlacementForm.setSubProvince(subProvince );
		                                                             		studentPlacementForm.setPreviousPage(studentPlacementForm.getCurrentPage());
		                                                                    studentPlacementForm.setCurrentPage("editSubProv");
		                                                                 	return mapping.findForward( studentPlacementForm.getCurrentPage());	
	}
	public ActionForward addSubProv(
            ActionMapping mapping,
 	       ActionForm form,
            HttpServletRequest request,
            HttpServletResponse response) throws Exception {
                                                     StudentPlacementForm studentPlacementForm = (StudentPlacementForm) form;	
                                	                 ActionMessages messages = new ActionMessages();	
                         				             SubProvince subProvince = new SubProvince ();
                         				             subProvince.setProvinceCode(studentPlacementForm.getDistrictFilterProvince());
                         				             subProvince.setCode(0);
                          		                     studentPlacementForm.setSubProvince(subProvince );
                          		                      studentPlacementForm.setPreviousPage(studentPlacementForm.getCurrentPage());
                                                      studentPlacementForm.setCurrentPage("editSubProv");
                                              return mapping.findForward( studentPlacementForm.getCurrentPage());	
}
		public ActionForward initial(
			                         ActionMapping mapping,
			                        ActionForm form,
			                        HttpServletRequest request,
			                        HttpServletResponse response) throws Exception {
		
		                                                         StudentPlacementForm studentPlacementForm = (StudentPlacementForm) form;	
		                                                         studentPlacementForm.setPreviousPage("inputStudentPlacement");
		                                                     	List list = new ArrayList<Province>();
		                                                		ProvinceDAO dao = new ProvinceDAO();
		                                                		         	list = dao.getProvinceExSubPrvLabelValueList();
		                                                			studentPlacementForm.setListSubProvincesOfProvince(list);
		                                                			 studentPlacementForm.setPreviousPage(studentPlacementForm.getCurrentPage());
		                                                			  studentPlacementForm.setCurrentPage("listSubProvinces");
		              		                              return mapping.findForward( studentPlacementForm.getCurrentPage());
	}
	public ActionForward display(
			ActionMapping mapping,
			ActionForm form,
			HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		StudentPlacementForm studentPlacementForm = (StudentPlacementForm) form;	
		ActionMessages messages = new ActionMessages();	
		
		List list = new ArrayList<SubProvince>();
		SubProvinceDAO dao = new SubProvinceDAO();
		         	list = dao.getSubProvinceList(studentPlacementForm.getDistrictFilterProvince());
			studentPlacementForm.setListSubProvincesOfProvince(list);
			 studentPlacementForm.setPreviousPage(studentPlacementForm.getCurrentPage());
			  studentPlacementForm.setCurrentPage("listSubProvinces");
              return mapping.findForward( studentPlacementForm.getCurrentPage());
	}
	
	public ActionForward saveSubProv(
			ActionMapping mapping,
			ActionForm form,
			HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		StudentPlacementForm studentPlacementForm = (StudentPlacementForm) form;
		ActionMessages messages = new ActionMessages();	
		
		if (studentPlacementForm.getSubProvince().getDescription()==null || studentPlacementForm.getSubProvince().getDescription().equals("")){
			messages.add(ActionMessages.GLOBAL_MESSAGE,
					new ActionMessage("message.generalmessage",
								"Please enter a name for the sub province"));
		}
		if (studentPlacementForm.getDistrictFilterProvince()!=studentPlacementForm.getSubProvince().getProvinceCode()){
			messages.add(ActionMessages.GLOBAL_MESSAGE,
					new ActionMessage("message.generalmessage",
								"Something went wrong when validating the data,please  try again"));
		}
		if (!messages.isEmpty()) {
			addErrors(request,messages);
				return mapping.findForward( studentPlacementForm.getCurrentPage());				
		}
		SubProvinceDAO dao = new SubProvinceDAO();
		if(studentPlacementForm.getSubProvince().getCode()==0){
		            dao.insert(studentPlacementForm.getSubProvince());
		}else{
		        	dao.update(studentPlacementForm.getSubProvince().getCode(),studentPlacementForm.getSubProvince().getDescription());
		}
		return display(mapping,form,request,response);	
	}
	public ActionForward deleteSubProv(
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
            									"Please select a sub province"));
            			}
            		if (studentPlacementForm.getIndexNrSelected()!=null &&
            				studentPlacementForm.getIndexNrSelected().length>1){
            				messages.add(ActionMessages.GLOBAL_MESSAGE,
            						new ActionMessage("message.generalmessage",
            									"Please select only one sub province"));
            			}
            		if (!messages.isEmpty()) {
            			addErrors(request,messages);
            					return mapping.findForward("listSubProvinces");				
            		}
            		SubProvince subProvince = new SubProvince ();
            		Province province = new Province();
            		
            		for (int i=0; i <studentPlacementForm.getIndexNrSelected().length; i++) {
            			String array[] = studentPlacementForm.getIndexNrSelected();
            			subProvince = (SubProvince)studentPlacementForm.getListSubProvincesOfProvince().get(Integer.parseInt(array[i]));			
            			studentPlacementForm.setSelectedDistrictIndex(i);
            			i=studentPlacementForm.getIndexNrSelected().length;
            		}
            		studentPlacementForm.setSubProvince(subProvince );
            		SubProvinceDAO dao = new SubProvinceDAO();
		            dao.delete(studentPlacementForm.getSubProvince().getCode());
		           return display(mapping,form,request,response);	
	}

}