package za.ac.unisa.lms.tools.tpustudentplacement.uiLayer;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import za.ac.unisa.lms.tools.tpustudentplacement.forms.Province;
import za.ac.unisa.lms.tools.tpustudentplacement.forms.StudentPlacementForm;
import za.ac.unisa.lms.tools.tpustudentplacement.forms.SubProvince;
import za.ac.unisa.lms.tools.tpustudentplacement.model.modelImpl.ProvinceImpl;
import za.ac.unisa.lms.tools.tpustudentplacement.model.modelImpl.schoolImpl.SchoolUI;

public class ProvinceUI extends Province{
		       public void setProvinceData(int schoolCode)throws Exception {
                              SchoolUI school=new SchoolUI();
                              int provCode=school.getSchoolProvCode(schoolCode);
                              setCode((short)provCode);
                              setDescription(getProvinceDescription(provCode));
               }
		       public String provinceListAsString(List provinceCodes)throws Exception {
                               ProvinceImpl probImpl=new ProvinceImpl();
                               return probImpl.provinceListAsString(provinceCodes);
               }
	           public List getProvinceList()throws Exception {
	        	                 ProvinceImpl probImpl=new ProvinceImpl();
	        	                 return probImpl.getProvinceList();
	           }
	           public String getProvinceDescription(int code)throws Exception {
	        	                ProvinceImpl probImpl=new ProvinceImpl();
	                            return probImpl.getProvinceDescription(code);
               }
	           public void setProvinceListToForm(StudentPlacementForm studentPlacementForm) throws Exception{
	        	                                              studentPlacementForm.setListRSAProvinces(getProvinceList());
                                                             setProvAndSubProvListToForm(studentPlacementForm) ;
	          }
	           public void setProvAndSubProvListToForm(StudentPlacementForm studentPlacementForm) throws Exception{
	        	                                             Province  province=new Province();
	        	                                             List  list =new ArrayList<Province>();
                                                             list =getProvinceList();
                                                             SubProvince   subProvince=new SubProvince();
                                                             List  supProvList = subProvince.getSubProvinceList();
                                                             list.addAll(transformSubProvToProv(supProvList));
                                                             studentPlacementForm.setListProvince(list);
                                                             List listFilter = new ArrayList<Province>();
                                                             province.setCode(Short.parseShort("0"));
                                                             province.setDescription("ALL");
                                                             province.setIn_use("Y");
                                                             listFilter.addAll(list);
                                                             listFilter.add(0, province);
                	                                         studentPlacementForm.setListFilterProvince(listFilter);		
            }
	        private void  transformSubProvToProv( Province  province,SubProvince subProvince){
	        	                                         province.setCode(new Short(""+subProvince.getCode()));
	        	                                         province.setDescription( subProvince.getDescription());
	        	                                         province.setIn_use( subProvince.getInUseFlag());
	       }
	       private List<Province> transformSubProvToProv( List<SubProvince> listSubProv){
                                                                 List   provList=new ArrayList<Province>();
                                                                 Iterator<SubProvince> iter= listSubProv.iterator();
                                                                 while(iter.hasNext()){
                                                    	                      SubProvince subProv=iter.next();
                                                    	                      Province prov=new Province();
                                                    	                      transformSubProvToProv(prov,subProv);
                                                    	                      provList.add(prov);
                                                    	     }
                                                             return provList;
            }
}
