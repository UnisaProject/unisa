package za.ac.unisa.lms.tools.tpustudentplacement.forms;
public class Province {
	private Short code;
	private String description;
	private String in_use;
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
	public String getIn_use() {
		return in_use;
	}
	public void setIn_use(String inUse) {
		in_use = inUse;
	}
	 public static boolean isProvince(int code){
         if((code>0)&&(code <10)){
              	  return true;
         }
         return false;
}
	 public static boolean isProvince(Short code){
                                             if((code!=null)&&(!code.toString().equals("0"))&&(code>0)&&(code <10)){
              	                                            return true;
                                             }
                                             return false;
  }
	 
}
