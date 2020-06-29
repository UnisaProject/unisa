package za.ac.unisa.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;

@Entity
@IdClass(SolgenId.class)
@Table(name = "SOLGEN")
public class Solgen {

	@Id
	@Column(name = "STUDENT_NR", length = 8)
	private String studentNr;
	
	@Id
	@Column(name = "TIMESTAMP", length = 20)
	private String timestamp;
	
	@Id
	@Column(name = "CATEGORY	", length = 8)
	private String category;
	
	@Id
	@Column(name = "SEQUENCE_NR", length = 20)
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
	
	
}
