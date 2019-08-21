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
import za.ac.unisa.lms.tools.tpustudentplacement.uiLayer.SubProvinceUI;

public class DistrictAction extends LookupDispatchAction{
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
		map.put("button.link", "linkDistrictToSubProv");
		map.put("button.save", "saveDistrictSubProvLink");	
		map.put("button.remove", "removeDistrictFromSubProv");	
		return map;
	}
	public ActionForward prevPage(
			ActionMapping mapping,
			ActionForm form,
			HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
	                	StudentPlacementForm studentPlacementForm = (StudentPlacementForm) form;	
	                	if( studentPlacementForm.getCurrentPage().equals("districtScreen")){
	                	   	              studentPlacementForm.setPreviousPage("inputStudentPlacement");
	                	 }
	                	 if(  studentPlacementForm.getCurrentPage().equals("linkDistToSubProv")){
               	   	                    studentPlacementForm.setPreviousPage("districtScreen");
               	          }
	                	 studentPlacementForm.setCurrentPage( studentPlacementForm.getPreviousPage());
                 	return mapping.findForward(studentPlacementForm.getPreviousPage());	
		}
public ActionForward linkDistrictToSubProv(
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
		                                                             									"Please select a district"));
		                                                             			}
		                                                             		if (studentPlacementForm.getIndexNrSelected()!=null &&
		                                                             				studentPlacementForm.getIndexNrSelected().length>1){
		                                                             				messages.add(ActionMessages.GLOBAL_MESSAGE,
		                                                             						new ActionMessage("message.generalmessage",
		                                                             									"Please select only one district"));
		                                                             			}
		                                                             		if (!messages.isEmpty()) {
		                                                             			addErrors(request,messages);
		                                                             					return mapping.findForward("districtScreen");				
		                                                             		}
		                                                             		District district = new District();
		                                                             		for (int i=0; i <studentPlacementForm.getIndexNrSelected().length; i++) {
		                                                             			String array[] = studentPlacementForm.getIndexNrSelected();
		                                                             			district = (District)studentPlacementForm.getListDistrictsOfProvince().get(Integer.parseInt(array[i]));			
		                                                             			studentPlacementForm.setSelectedDistrictIndex(i);
		                                                             			i=studentPlacementForm.getIndexNrSelected().length;
		                                                             		}
		                                                             		studentPlacementForm.setDistrict(district);
		                                                             		 SubProvinceUI  subProvinceUI  = new SubProvinceUI();
		                                		                             subProvinceUI.setSubProvinceListToFrom(studentPlacementForm);
		                                			                         studentPlacementForm.setPreviousPage(studentPlacementForm.getCurrentPage());
		                                                                    studentPlacementForm.setCurrentPage("linkDistToSubProv");
		                                                                 	return mapping.findForward( studentPlacementForm.getCurrentPage());	
	}
		public ActionForward initial(
			                                           ActionMapping mapping,
			                                           ActionForm form,
			                                           HttpServletRequest request,
			                                           HttpServletResponse response) throws Exception {
			                                                                                       StudentPlacementForm studentPlacementForm = (StudentPlacementForm) form;	
			                                                                                       studentPlacementForm.setPreviousPage("inputStudentPlacement");
		                                                                                           studentPlacementForm.setCurrentPage("districtScreen");
		                                                                               return mapping.findForward("districtScreen");
	}
	
	public ActionForward display(
			ActionMapping mapping,
			ActionForm form,
			HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		StudentPlacementForm studentPlacementForm = (StudentPlacementForm) form;	
		ActionMessages messages = new ActionMessages();	
		
		List list = new ArrayList<District>();
		DistrictDAO dao = new DistrictDAO();
		         	list = dao.getDistrictList(studentPlacementForm.getDistrictFilterProvince());
			studentPlacementForm.setListDistrictsOfProvince(list);
			 studentPlacementForm.setPreviousPage(studentPlacementForm.getCurrentPage());
			 studentPlacementForm.setCurrentPage("districtScreen");
     	return mapping.findForward("districtScreen");
	}
	
	public ActionForward saveDistrictSubProvLink(
			ActionMapping mapping,
			ActionForm form,
			HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		StudentPlacementForm studentPlacementForm = (StudentPlacementForm) form;
		ActionMessages messages = new ActionMessages();	
		
		if (studentPlacementForm.getDistrict().getSubProvCode()==null || studentPlacementForm.getDistrict().getSubProvCode()==0){
			messages.add(ActionMessages.GLOBAL_MESSAGE,
					new ActionMessage("message.generalmessage",
								"Please select a sub province"));
		}
		if (!messages.isEmpty()) {
			addErrors(request,messages);
				return mapping.findForward( studentPlacementForm.getCurrentPage());				
		}
		DistrictUI district = new DistrictUI();
		district.linkToSubProv(studentPlacementForm.getDistrict().getCode(),studentPlacementForm.getDistrict().getSubProvCode());
		return display(mapping,form,request,response);	
	}
	public ActionForward removeDistrictFromSubProv(
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
				"Please select a district"));
}
if (studentPlacementForm.getIndexNrSelected()!=null &&
studentPlacementForm.getIndexNrSelected().length>1){
messages.add(ActionMessages.GLOBAL_MESSAGE,
	new ActionMessage("message.generalmessage",
				"Please select only one district"));
}
if (!messages.isEmpty()) {
addErrors(request,messages);
return mapping.findForward("districtScreen");				
}
District district = new District();
for (int i=0; i <studentPlacementForm.getIndexNrSelected().length; i++) {
		String array[] = studentPlacementForm.getIndexNrSelected();
		district = (District)studentPlacementForm.getListDistrictsOfProvince().get(Integer.parseInt(array[i]));			
		studentPlacementForm.setSelectedDistrictIndex(i);
		i=studentPlacementForm.getIndexNrSelected().length;
	}

studentPlacementForm.setDistrict(district);

DistrictDAO dao = new DistrictDAO();
dao.unlinkToSubProv(district.getCode());
return display(mapping,form,request,response);	
}
}
