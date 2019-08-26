package za.ac.unisa.lms.tools.tpustudentplacement.uiLayer;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import za.ac.unisa.lms.tools.tpustudentplacement.dao.ProvinceDAO;
import za.ac.unisa.lms.tools.tpustudentplacement.forms.Province;
import za.ac.unisa.lms.tools.tpustudentplacement.forms.StudentPlacementForm;
import za.ac.unisa.lms.tools.tpustudentplacement.forms.SubProvince;

public class ProvinceUI extends ProvinceDAO{
		          public void setProvinceListToForm(StudentPlacementForm studentPlacementForm) throws Exception{
	        	                                              studentPlacementForm.setListRSAProvinces(getProvinceListExSubPrv());
                                                             setProvAndSubProvListToForm(studentPlacementForm) ;
	          }
	           public void setProvAndSubProvListToForm(StudentPlacementForm studentPlacementForm) throws Exception{
	        	                                             Province  province=new Province();
	        	                                             List  list =new ArrayList<Province>();
                                                             list =getProvinceList();
                                                             List listFilter = new ArrayList<Province>();
                                                             province.setCode(Short.parseShort("0"));
                                                             province.setDescription("ALL");
                                                             province.setIn_use("Y");
                                                             listFilter.addAll(list);
                                                             listFilter.add(0, province);
                	                                         studentPlacementForm.setListFilterProvince(listFilter);	
                	                                         studentPlacementForm.setListProvince(getProvinceList());
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
	       
	       public String provinceListAsString(List provinceCodes)throws Exception {
               String provinceStr="";
                if((provinceCodes==null)||(provinceCodes.isEmpty())){
 	                   return "";
               }else{
   	                Set  setOfProvinceCodes=new  HashSet(provinceCodes);
 	                    Iterator i= setOfProvinceCodes.iterator();
 	                    while (i.hasNext()){
 	    	                  String  provCode=i.next().toString();
 	    	                  String provDescr= getProvinceDescription(Integer.parseInt(provCode));
 	    	                  provinceStr+=provDescr;
 	    	                  provinceStr+=(",");
 	                    }
 	                    provinceStr=provinceStr.substring(0,(provinceStr.length()-1));
               }
               return provinceStr;
       }

}
