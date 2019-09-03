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
import za.ac.unisa.lms.tools.tpustudentplacement.uiLayer.ProvinceUI;
import za.ac.unisa.lms.tools.tpustudentplacement.uiLayer.SubProvinceUI;

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
		map.put("button.delete", "delete");	
		return map;
	}
		public ActionForward prevPage(
			ActionMapping mapping,
			ActionForm form,
			HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
	                	StudentPlacementForm studentPlacementForm = (StudentPlacementForm) form;	
	                	if( studentPlacementForm.getCurrentPage().equals("listSubProvinces")){
	                	   	           studentPlacementForm.setPreviousPage("inputStudentPlacement");
	                	 }
	                	 if(  studentPlacementForm.getCurrentPage().equals("editSubProv")){
               	   	                studentPlacementForm.setPreviousPage("listSubProvinces");
               	          }
	                	 studentPlacementForm.setCurrentPage( studentPlacementForm.getPreviousPage());
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
                          									"Please select a sub province"));
                          			}
                          		if (!messages.isEmpty()) {
                          			addErrors(request,messages);
                          					return mapping.findForward("listSubProvinces");				
                          		}
                          	 SubProvinceDAO  subProvinceDAO =new SubProvinceDAO();
 			                  DistrictUI      districtUI   =new    DistrictUI();
                          		for (int i=0; i <studentPlacementForm.getIndexNrSelected().length; i++) {
                          			                    String array[] = studentPlacementForm.getIndexNrSelected();
                          			                    SubProvince  subProvince = (SubProvince)studentPlacementForm.getListSubProvincesOfProvince().get(Integer.parseInt(array[i]));		
                          			                    subProvinceDAO.delete(subProvince.getCode());
                          			                    districtUI.unlinkAllDistrictsLinkedToSubProv(subProvince.getCode());
                          		}
                          	               	return display(mapping,form,request,response);	
	}
	public ActionForward addSubProv(
            ActionMapping mapping,
 	       ActionForm form,
            HttpServletRequest request,
            HttpServletResponse response) throws Exception {
                                                     StudentPlacementForm studentPlacementForm = (StudentPlacementForm) form;	
                                	                SubProvince subProvince =  studentPlacementForm.getSubProvince();
                         				             subProvince.setProvinceCode(studentPlacementForm.getDistrictFilterProvince());
                         				             subProvince.setCode(0);
                          		                      ProvinceUI provUI=new ProvinceUI();
                                                    subProvince.setProvinceDescription(provUI.getProvinceDescription(studentPlacementForm.getDistrictFilterProvince()));
                                                   subProvince.setProvinceCode(studentPlacementForm.getDistrictFilterProvince());
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
		                                                             SubProvince subProvince=new SubProvince();
		                                                             studentPlacementForm.setSubProvince(subProvince);
		                                                                         studentPlacementForm.setCurrentPage("listSubProvinces");
		              		                              return mapping.findForward( studentPlacementForm.getCurrentPage());
	}
	public ActionForward display(
			ActionMapping mapping,
			ActionForm form,
			HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		                             StudentPlacementForm studentPlacementForm = (StudentPlacementForm) form;	
		                             SubProvinceUI  subProvinceUI  = new SubProvinceUI();
		                             subProvinceUI.setSubProvinceListToFrom(studentPlacementForm);
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
	}
