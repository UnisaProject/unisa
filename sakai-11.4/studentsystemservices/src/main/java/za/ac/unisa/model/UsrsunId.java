package za.ac.unisa.model;

import java.io.Serializable;

public class UsrsunId implements Serializable{
	
	private static final long serialVersionUID = 1L;

	private String mkStudyUnitCode;
	
	private String novelUserId;
	
	private String systemType;
	
	private int userSquence=0;

	
	
	public String getMkStudyUnitCode() {
		return mkStudyUnitCode;
	}

	public void setMkStudyUnitCode(String mkStudyUnitCode) {
		this.mkStudyUnitCode = mkStudyUnitCode;
	}

	public String getNovelUserId() {
		return novelUserId;
	}

	public void setNovelUserId(String novelUserId) {
		this.novelUserId = novelUserId;
	}

	public String getSystemType() {
		return systemType;
	}

	public void setSystemType(String systemType) {
		this.systemType = systemType;
	}

	public int getUserSquence() {
		return userSquence;
	}

	public void setUserSquence(int userSquence) {
		this.userSquence = userSquence;
	}

}
