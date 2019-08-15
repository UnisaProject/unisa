package za.ac.unisa.lms.tools.tpustudentplacement.forms;

import java.util.List;

import za.ac.unisa.lms.tools.tpustudentplacement.dao.SubProvinceDAO;

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
}
/*

   -if we are dealing with subprovince, use tpusar, to get the ditricts the supervisor is linked to,
      >get the sup province code   from the district found above
     If(sup province code   ==selectedProvCode)  add this supervisor in list

-user should be able to   list schools in a province
-user should be able to   list schools in a sub province
-sub province should   be   displayed as part of the province list

*/