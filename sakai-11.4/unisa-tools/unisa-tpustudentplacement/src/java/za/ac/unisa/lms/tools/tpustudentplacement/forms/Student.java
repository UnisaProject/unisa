package za.ac.unisa.lms.tools.tpustudentplacement.forms;
import java.util.List;
import za.ac.unisa.lms.tools.tpustudentplacement.model.modelImpl.ContactUI;
import za.ac.unisa.lms.tools.tpustudentplacement.model.modelImpl.StudentImp;

public class Student {
	  public Student(int stuNum,int acadYear)throws Exception{
		                           qualification=  new Qualification(stuNum,(short)acadYear);
		                           ContactUI contact=new  ContactUI();
		                           studentImp=new StudentImp(qualification,contact);
		                           studentImp.setStudentData(this, stuNum,(short)acadYear,(short)0);
	    }
	  public Student(){
       } 
	private String name;
	private String printName;
	private Integer number;
	private Address postalAddress;
	private Address physicalAddress;
	private Contact contactInfo;
	private List listPracticalModules;
	private String practicalModules;
	private String countryCode;
	private short provinceCode;
	private short districtCode;
	private StudentImp  studentImp;
	private Qualification qualification;
	
	public String getStudentEmail() throws Exception{
		return  contactInfo.getEmailAddress();
	}
	public  String getCellNumber() throws Exception{
		return  contactInfo.getCellNumber();
	}
	public String getPracticalModules() {
		return practicalModules;
	}
	public String getPrintName() {
		return printName;
	}
	public void setPrintName(String printName) {
		this.printName = printName;
	}
	public void setPracticalModules(String practicalModules) {
		this.practicalModules = practicalModules;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Integer getNumber() {
		return number;
	}
	public void setNumber(Integer number) {
		this.number = number;
	}
	public Address getPostalAddress() {
		return postalAddress;
	}
	public void setPostalAddress(Address postalAddress) {
		this.postalAddress = postalAddress;
	}
	public Address getPhysicalAddress() {
		return physicalAddress;
	}
	public void setPhysicalAddress(Address physicalAddress) {
		this.physicalAddress = physicalAddress;
	}
	public Contact getContactInfo() {
		return contactInfo;
	}
	public void setContactInfo(Contact contactInfo) {
		this.contactInfo = contactInfo;
	}
	public List getListPracticalModules() {
		return listPracticalModules;
	}
	public void setListPracticalModules(List listPracticalModules) {
		this.listPracticalModules = listPracticalModules;
	}	
	public String getCountryCode() {
				return countryCode;
			}
			public void setCountryCode(String countryCode) {
				this.countryCode = countryCode;
			}
			public short getProvinceCode() {
				return provinceCode;
			}
			public void setProvinceCode(short provinceCode) {
				this.provinceCode = provinceCode;
			}
			public short getDistrictCode() {
				return districtCode;
			}
			public void setDistrictCode(short districtCode) {
				this.districtCode = districtCode;
			}
			public Qualification getQualification() {
				return qualification;
			}
			public void setQualification(Qualification qualification) {
				this.qualification = qualification;
			}
}