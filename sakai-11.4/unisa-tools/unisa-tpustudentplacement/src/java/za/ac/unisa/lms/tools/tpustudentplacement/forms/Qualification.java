package za.ac.unisa.lms.tools.tpustudentplacement.forms;

import za.ac.unisa.lms.tools.tpustudentplacement.dao.QualificationDAO;

public class Qualification {
	private String code;
	private String description;
	private String shortDesc;
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
	 public Qualification getStudentQual(int studentNr, Short acadYear) throws Exception {
		                              QualificationDAO  qualificationDAO=new  QualificationDAO();
		                              return qualificationDAO.getStudentQual(studentNr, acadYear);
	 }
	 public boolean isPGCE(String qualCode) throws Exception {
                                        QualificationDAO  qualificationDAO=new  QualificationDAO();
                                        return qualificationDAO.isPGCE(qualCode);
     }
	
}
