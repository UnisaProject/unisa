package za.ac.unisa.lms.tools.tpustudentplacement.uiLayer;

import java.util.ArrayList;
import java.util.List;

import za.ac.unisa.lms.tools.tpustudentplacement.dao.SubProvinceDAO;
import za.ac.unisa.lms.tools.tpustudentplacement.forms.StudentPlacementForm;
import za.ac.unisa.lms.tools.tpustudentplacement.forms.SubProvince;

public class SubProvinceUI {

	public SubProvinceUI() {
		// TODO Auto-generated constructor stub
	}
  public void setSubProvinceListToFrom(StudentPlacementForm studentPlacementForm) throws Exception{
	                            List list = new ArrayList<SubProvince>();
                                SubProvinceDAO dao = new SubProvinceDAO();
                             	list = dao.getSubProvinceList(studentPlacementForm.getDistrictFilterProvince());
                                studentPlacementForm.setListSubProvincesOfProvince(list);
  }
  
}
