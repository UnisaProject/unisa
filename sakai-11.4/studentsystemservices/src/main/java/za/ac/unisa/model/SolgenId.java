package za.ac.unisa.model;

import java.io.Serializable;

public class SolgenId implements Serializable{

	private static final long serialVersionUID = 1L;

	private String studentNr;	
	private String timestamp;
	private String category;	
	private String sequenceNr;
	
	public String getStudentNr() {
		return studentNr;
	}
	public void setStudentNr(String studentNr) {
		this.studentNr = studentNr;
	}
	public String getTimestamp() {
		return timestamp;
	}
	public void setTimestamp(String timestamp) {
		this.timestamp = timestamp;
	}
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	public String getSequenceNr() {
		return sequenceNr;
	}
	public void setSequenceNr(String sequenceNr) {
		this.sequenceNr = sequenceNr;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	
	
}
