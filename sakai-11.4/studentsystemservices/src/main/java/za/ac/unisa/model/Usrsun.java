package za.ac.unisa.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;

@Entity
@IdClass(UsrsunId.class)
@Table(name = "USRSUN")
public class Usrsun {

	@Id
	@Column(name = "MK_STUDY_UNIT_CODE", length = 7)
	private String mkStudyUnitCode;

	@Id
	@Column(name = "NOVELL_USER_ID", length = 10)
	private String novelUserId;
 
	@Column(name="USER_SEQUENCE",length = 3)
    private int userSquence=0;
 
	@Id
	@Column(name = "SYSTEM_TYPE", length = 1)
	private String systemType;

	@Column(name = "ACCESS_LEVEL", length = 8)
	private String accessLevel;

	@Column(name = "MK_ACADEMIC_YEAR", length = 4)
	private Integer mkAcademicYear;

	@Column(name = "MK_SEMESTER_PERIOD", length = 2)
	private Integer mkSemesterPeriod;
	
	
	

	public int getUserSquence() {
		return userSquence;
	}

	public void setUserSquence(int userSquence) {
		this.userSquence = userSquence;
	}

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

	public String getAccessLevel() {
		return accessLevel;
	}

	public void setAccessLevel(String accessLevel) {
		this.accessLevel = accessLevel;
	}
 

	public Integer getMkAcademicYear() {
		return mkAcademicYear;
	}

	public void setMkAcademicYear(Integer mkAcademicYear) {
		this.mkAcademicYear = mkAcademicYear;
	}

	public Integer getMkSemesterPeriod() {
		return mkSemesterPeriod;
	}

	public void setMkSemesterPeriod(Integer mkSemesterPeriod) {
		this.mkSemesterPeriod = mkSemesterPeriod;
	}
}
