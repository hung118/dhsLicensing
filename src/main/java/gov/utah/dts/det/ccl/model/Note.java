package gov.utah.dts.det.ccl.model;

import gov.utah.dts.det.ccl.model.enums.NoteType;
import gov.utah.dts.det.model.AbstractAuditableEntity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import com.opensymphony.xwork2.validator.annotations.RequiredStringValidator;

@SuppressWarnings("serial")
@Entity
@Table(name = "NOTE")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Note extends AbstractAuditableEntity<Long> implements Serializable, Secured {
 
	@Id
	@Column(name = "ID", unique = true, nullable = false, updatable = false)
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "NOTE_SEQ")
	@SequenceGenerator(name = "NOTE_SEQ", sequenceName = "NOTE_SEQ")
	private Long id;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "FACILITY_ID")
	private Facility facility;
	
	@Column(name = "NOTE_TYPE", nullable = false)
	@Enumerated(EnumType.STRING)
	private NoteType noteType;
	
	@Column(name = "LINK_TO_ID")
	private Long linkToId;
	
	@Column(name = "NOTE_TEXT")
	private String text;
	
	@Transient
	private boolean editable = false;
	
	public Note() {
		
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

	public NoteType getNoteType() {
		return noteType;
	}

	public void setNoteType(NoteType noteType) {
		this.noteType = noteType;
	}

	public Long getLinkToId() {
		return linkToId;
	}

	public void setLinkToId(Long linkToId) {
		this.linkToId = linkToId;
	}

	@RequiredStringValidator(message = "Note text is required.")
	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}
	
	public Facility getFacility() {
		return facility;
	}
	
	public void setFacility(Facility facility) {
		this.facility = facility;
	}

	@Override
	public Long getOwnerId() {
		if (getModifiedBy() == null) {
			return null;
		}
		return getModifiedBy().getId();
	}
	
	public boolean isEditable() {
		return editable;
	}
	
	public void setEditable(boolean editable) {
		this.editable = editable;
	}

	@Override
	public int getDaysUntilUneditable() {
		return 4;
	}

	@Override
	public Date getSecurityDate() {
		return getCreationDate();
	}
}