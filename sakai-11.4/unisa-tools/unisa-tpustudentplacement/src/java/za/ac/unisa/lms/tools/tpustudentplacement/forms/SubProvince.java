package za.ac.unisa.lms.tools.tpustudentplacement.forms;

import java.util.Iterator;
import java.util.List;

import za.ac.unisa.lms.tools.tpustudentplacement.dao.SubProvinceDAO;
import za.ac.unisa.lms.tools.tpustudentplacement.model.modelImpl.DistrictUI;
import za.ac.unisa.lms.tools.tpustudentplacement.utils.PlacementUtilities;

public class SubProvince  extends SubProvinceDAO{
	private int code;// the code must be from 30.
	private int provinceCode;
	private String provinceDescription;
	private String description;
	private String inUseFlag;

	
	public int getProvinceCode() {
		return provinceCode;
	}
	public void setProvinceCode(int provinceCode) {
		this.provinceCode = provinceCode;
	}
	public int getCode() {
		return code;
	}
	public void setCode(int code) {
		this.code = code;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getInUseFlag() {
		return inUseFlag;
	}
	public void setInUseFlag(String inUseFlag) {
		this.inUseFlag = inUseFlag;
	}
	public String getProvinceDescription() {
		return provinceDescription;
	}
	public void setProvinceDescription(String provinceDescription) {
		this.provinceDescription = provinceDescription;
	}
	public boolean  isSubProvLinkedToDistInProv(short code,short provinceCode)throws Exception{
                                          DistrictUI districtUI=new DistrictUI();
                                          List list=districtUI.getDistrictList(provinceCode);
                                          Iterator iter=list.iterator();
                                          String districtStr="( ";
                                          boolean linked=false;
                                          while(iter.hasNext()){
                                                   District  district= (District)iter.next();
                                                   if(district.getSubProvCode()==code){
                  	                                            linked=true;
                  	                                            break;
                                                   }
                                          }
                                       return   linked;
    }
	 public boolean isSubProv(String country,Short province,Short district) throws Exception {
         if (country!=null &&(!country.trim().equals("0"))&&(!country.trim().isEmpty() )){
                  if((country!=null)&&country.equals(PlacementUtilities.getSaCode())){  
                              if (province!=null &&(province.compareTo(Short.parseShort("0"))!=0)){
                                           if((province<1)||(province>9)){
        	                                          if((province>=30)){
        	                        	                         return true;
        	                                            }
        	                              }
                                  }
                   }
         }
 return false;
}

}
/*

   -if we are dealing with subprovince, use tpusar, to get the ditricts the supervisor is linked to,
      >get the sup province code   from the district found above
     If(sup province code   ==selectedProvCode)  add this supervisor in list

-user should be able to   list schools in a province
-user should be able to   list schools in a sub province
-sub province should   be   displayed as part of the province list

*/