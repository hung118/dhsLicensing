/**
 * 
 */
package gov.utah.dts.det.ccl.model;

import gov.utah.dts.det.model.AbstractBaseEntity;
import java.io.Serializable;
import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

/**
 * @author Dan Olsen
 * 
 */
@SuppressWarnings("serial")
@Entity
@Table(name = "COMPLAINT_PERSON")
public class ComplaintPerson extends AbstractBaseEntity<Long> implements Serializable {
	
	@Id
	@Column(name = "ID", unique = true, nullable = false, updatable = false)
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "COMPLAINT_PERSON_SEQ")
	@SequenceGenerator(name = "COMPLAINT_PERSON_SEQ", sequenceName = "COMPLAINT_PERSON_SEQ")
	private Long id;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "COMPLAINT_ID", nullable = false, updatable = false)
	private Complaint complaint;

	@Embedded
	@AttributeOverrides({
		@AttributeOverride(name = "firstName", column = @Column(name = "FIRSTNAME")),
		@AttributeOverride(name = "middleName", column = @Column(name = "MIDDLENAME")),
		@AttributeOverride(name = "lastName", column = @Column(name = "LASTNAME"))
	})
	private PersonalName name = new PersonalName();
	
	public ComplaintPerson() {
		
	}
	
	@Override
	public Long getPk() {
		return id;
	}
	
	@Override
	public void setPk(Long pk) {
		this.id = pk;
	}
	
	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}
	
	public Complaint getComplaint() {
		return complaint;
	}
	
	public void setComplaint(Complaint complaint) {
		this.complaint = complaint;
	}
	
	public String getFirstName() {
		return name.getFirstName();
	}

	public void setFirstName(String firstName) {
		name.setFirstName(firstName);
	}

	public String getLastName() {
		return name.getLastName();
	}

	public void setLastName(String lastName) {
		name.setLastName(lastName);
	}
}