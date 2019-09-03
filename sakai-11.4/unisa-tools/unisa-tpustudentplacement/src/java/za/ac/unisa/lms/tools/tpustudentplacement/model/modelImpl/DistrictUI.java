package za.ac.unisa.lms.tools.tpustudentplacement.model.modelImpl;
import java.util.List;

import za.ac.unisa.lms.tools.tpustudentplacement.dao.DistrictDAO;
import za.ac.unisa.lms.tools.tpustudentplacement.forms.District;
import za.ac.unisa.lms.tools.tpustudentplacement.forms.StudentPlacementForm;
import za.ac.unisa.lms.tools.tpustudentplacement.forms.SubProvince;
import za.ac.unisa.lms.tools.tpustudentplacement.uiLayer.ProvinceUI;
import za.ac.unisa.lms.tools.tpustudentplacement.uiLayer.SubProvinceUI;

public class DistrictUI extends DistrictImpl{
	
	
	
	public void setDistrictValue(StudentPlacementForm formBean,Short province){
                   if (formBean.getPlacementFilterDistrictValue()!=null &&
                           !formBean.getPlacementFilterDistrictValue().trim().equalsIgnoreCase("")){
                            int index = formBean.getPlacementFilterDistrictValue().indexOf("-");
                            String district = formBean.getPlacementFilterDistrictValue().trim().substring(0, index);
                            formBean.setPlacementFilterDistrict(Short.parseShort(district));
                   }
                           
      }
    private List getAllDsitrict(short prov,short district)throws Exception{
    	               DistrictImpl districtImpl=new DistrictImpl();
    	               return districtImpl.getAllDistricts(prov, district);
    }
    private void allowAllDsitrictSelection(StudentPlacementForm studentPlacementForm,String district)throws Exception{
    	              short prov=studentPlacementForm.getPlacementFilterProvince();
	                  studentPlacementForm.setPlacementFilterDistrict(Short.parseShort(district));
	                  short districtValue=studentPlacementForm.getPlacementFilterDistrict();
    	              List districtList=getAllDsitrict(prov,districtValue);
                      studentPlacementForm.setListFilterPlacementDistrict(districtList);
   }
    public void unlinkToSubProv(Short districtCode) throws Exception {
                                  	DistrictDAO districtDAO =new DistrictDAO();
                                  	districtDAO.unlinkToSubProv(districtCode);
  }
    public void unlinkAllDistrictsLinkedToSubProv(int subProvCode) throws Exception {
                                            	DistrictDAO districtDAO =new DistrictDAO();
                                            	districtDAO.unlinkAllDistrictsLinkedToSubProv(subProvCode);
  }
    public void setNamesOfSelectedDitrictsToForm(StudentPlacementForm studentPlacementForm) throws Exception {
                                                               String array[] = studentPlacementForm.getIndexNrSelected();
	                                                           String districtNames="";
                                                               for (int i=0; i <array.length; i++) {
                	                                                               District district  = (District)studentPlacementForm.getListDistrictsOfProvince().get(Integer.parseInt(array[i]));		
                	                                                               districtNames+=district.getDescription();
                	                                                              if(i!=(array.length-1)){
                	                                                                       districtNames+=",";
                	                                                               }
                                                               }
                                                               studentPlacementForm.setDistrictName(districtNames);
   }
    
    public void   setDataPartOfTheLinkDistToSubscreen(StudentPlacementForm studentPlacementForm) throws Exception {
    	                                           setNamesOfSelectedDitrictsToForm(studentPlacementForm);
                                                    ProvinceUI  provinceUI =new ProvinceUI();
		                                            studentPlacementForm.setProvinceName(provinceUI.getProvinceDescription(studentPlacementForm.getDistrictFilterProvince()));
		                                            SubProvince subProvince=new SubProvince();
		                                            subProvince.setCode(0);
		                                            studentPlacementForm.setSubProvince(subProvince);
	                                       	        SubProvinceUI  subProvinceUI  = new SubProvinceUI();
                                                    subProvinceUI.setSubProvinceListToFrom(studentPlacementForm);
                                                    studentPlacementForm.setPreviousPage(studentPlacementForm.getCurrentPage());
                                                    studentPlacementForm.setCurrentPage("linkDistToSubProv");
    }
}
