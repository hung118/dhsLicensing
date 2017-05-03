package gov.utah.dts.det.ccl.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.Type;
import org.json.JSONObject;

@SuppressWarnings("serial")
@Entity
@Table(name = "FACILITY")
public class FacilityNotes implements Serializable {

	@Id
	private Long id;
	
	@Column(name = "NOTES")
	@Type(type = "Json")
	private JSONObject notes;
	
	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}

	public JSONObject getNotes() {
		return notes;
	}
	
	public void setNotes(JSONObject notes) {
		this.notes = notes;
	}
}
