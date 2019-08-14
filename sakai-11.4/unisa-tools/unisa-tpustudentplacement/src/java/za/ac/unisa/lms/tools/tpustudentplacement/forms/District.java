package za.ac.unisa.lms.tools.tpustudentplacement.forms;

public class District {
	private Short code;
	private String description;
	private String inUse;
	private Province province;
	private String provinceName;
	private String subProvince;
   private Short subProvCode;
	public Short getCode() {
		return code;
	}
	public void setCode(Short code) {
		this.code = code;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}	
	public String getInUse() {
		return inUse;
	}
	public void setInUse(String inUse) {
		this.inUse = inUse;
	}
	public Province getProvince() {
		return province;
	}
	public void setProvince(Province province) {
		this.province = province;
	}
	public String getSubProvince() {
		return subProvince;
	}
	public void setSubProvince(String subProvince) {
		this.subProvince = subProvince;
	}
	public String getProvinceName() {
		return provinceName;
	}
	public void setProvinceName(String provinceName) {
		this.provinceName = provinceName;
	}
	public Short getSubProvCode() {
		return subProvCode;
	}
	public void setSubProvCode(Short subProvCode) {
		this.subProvCode = subProvCode;
	}
	
}
