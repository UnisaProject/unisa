package za.ac.unisa.lms.tools.tpustudentplacement.forms;

import za.ac.unisa.lms.tools.tpustudentplacement.dao.QualificationDAO;

public class Qualification {
	 QualificationDAO  qualificationDAO;
	  public Qualification() {
		       qualificationDAO=new  QualificationDAO();
	}
	 public Qualification(int studentNr, Short acadYear) throws Exception {
	            qualificationDAO=new  QualificationDAO();
	            qualificationDAO.getStudentQual(this,studentNr, acadYear);
	}
	private String code;
	private String description;
	private String shortDesc;
	private String type;
	private String specializationCode;
	private boolean pgceStudent=false;
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getDescription() {
		return description;
	}	
	public String getShortDesc() {
		return shortDesc;
	}
	public void setShortDesc(String shortDesc) {
		this.shortDesc = shortDesc;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getSpecializationCode() {
		return specializationCode;
	}
	public void setSpecializationCode(String specializationCode) {
		this.specializationCode = specializationCode;
	}
	public boolean isPgceStudent() {
		return pgceStudent;
	}
	public void setPgceStudent(boolean pgceStudent) {
		this.pgceStudent = pgceStudent;
	}
	
}
