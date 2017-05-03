package gov.utah.dts.det.ccl.model;

import gov.utah.dts.det.ccl.model.enums.DeliveryMethod;
import gov.utah.dts.det.ccl.model.enums.NameUsage;
import gov.utah.dts.det.util.CompareUtils;
import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.Lob;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Type;

import com.opensymphony.xwork2.conversion.annotations.Conversion;

/**
 * @author Dan Olsen
 * 
 */
@SuppressWarnings("serial")
@Entity
@Table(name = "COMPLAINT")
@Conversion
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Inheritance(strategy = InheritanceType.JOINED)
public class Complaint extends StateObject implements Serializable, Comparable<Complaint>, ModifiableStateObject<Complaint.State, Complaint.StateChange> {

	public enum State {INTAKE, FINALIZED};
	//public enum State {SUBSTANTIATED, UNSUBSTANTIATED, NONLICENSING};
	public enum StateChange {CREATED, INTAKE_COMPLETED, FINALIZED};

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "FACILITY_ID", insertable = true, updatable = false, nullable = false)
	private Facility facility;

	@Column(name = "DATE_RECEIVED")
	@Temporal(TemporalType.TIMESTAMP)
	private Date dateReceived;
	
	@Column(name = "DELIVERY_METHOD")
	@Type(type = "DeliveryMethod")
	private DeliveryMethod deliveryMethod = DeliveryMethod.DIRECT_CONTACT;

	@Column(name = "DISCLOSURE_STATEMENT_READ")
	@Type(type = "yes_no")
	private Boolean disclosureStatementRead = false;

	@Column(name = "NARRATIVE")
	@Lob
	private String narrative;
	
	@OneToOne(optional = true, orphanRemoval = true, fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	@PrimaryKeyJoinColumn
	private ComplaintScreening screening;
	
	@OneToOne(optional = true, orphanRemoval = true, fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	@PrimaryKeyJoinColumn
	private ComplaintComplainant complainant;
	
	@OneToMany(mappedBy = "complaint", cascade = {CascadeType.ALL}, fetch = FetchType.LAZY, orphanRemoval = true)
	private Set<ComplaintIncident> incidents = new HashSet<ComplaintIncident>();
	
	@OneToMany(mappedBy = "complaint", cascade = {CascadeType.ALL}, fetch = FetchType.LAZY, orphanRemoval = true)
	private Set<ComplaintPerson> peopleInvolved = new HashSet<ComplaintPerson>();
	
	@OneToMany(cascade = {CascadeType.ALL}, fetch = FetchType.LAZY, orphanRemoval = true)
	@JoinTable(name = "COMPLAINT_WITNESS",
		joinColumns = @JoinColumn(name = "COMPLAINT_ID", referencedColumnName = "ID"),
		inverseJoinColumns = @JoinColumn(name = "WITNESS_ID", referencedColumnName = "ID")
	)
	private Set<Person> witnesses = new HashSet<Person>();
	
	@OneToMany(mappedBy = "complaint", cascade = {CascadeType.ALL}, fetch = FetchType.LAZY, orphanRemoval = true)
	@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
	private Set<Allegation> allegations = new HashSet<Allegation>();
	
	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "COMPLAINT_INSPECTION",
		joinColumns = @JoinColumn(name = "COMPLAINT_ID", referencedColumnName = "ID"),
		inverseJoinColumns = @JoinColumn(name = "INSPECTION_ID", referencedColumnName = "ID")
	)
	private Set<Inspection> inspections = new HashSet<Inspection>();
	
	@OneToMany(mappedBy = "complaint", cascade = {CascadeType.ALL}, fetch = FetchType.LAZY, orphanRemoval = true)
	@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
	private Set<Referral> referrals = new HashSet<Referral>();

	public Complaint() {

	}

	public Facility getFacility() {
		return facility;
	}

	public void setFacility(Facility facility) {
		this.facility = facility;
	}
	
	public Date getDateReceived() {
		return dateReceived;
	}

	public void setDateReceived(Date dateReceived) {
		this.dateReceived = dateReceived;
	}
	
	public DeliveryMethod getDeliveryMethod() {
		return deliveryMethod;
	}
	
	public void setDeliveryMethod(DeliveryMethod deliveryMethod) {
		this.deliveryMethod = deliveryMethod;
	}
	
	public Boolean getDisclosureStatementRead() {
		return disclosureStatementRead;
	}

	public void setDisclosureStatementRead(Boolean disclosureStatementRead) {
		this.disclosureStatementRead = disclosureStatementRead;
	}

	public String getNarrative() {
		return narrative;
	}

	public void setNarrative(String narrative) {
		this.narrative = narrative;
	}
	
	public ComplaintScreening getScreening() {
		return screening;
	}
	
	public void setScreening(ComplaintScreening screening) {
		if (screening != null) {
			screening.setId(getId());
		}
		this.screening = screening;
	}
	
	public ComplaintComplainant getComplainant() {
		return complainant;
	}
	
	public void setComplainant(ComplaintComplainant complainant) {
		if (complainant != null) {
			complainant.setId(getId());
		}
		this.complainant = complainant;
	}
	
	public Set<ComplaintIncident> getIncidents() {
		return incidents;
	}
	
	public void setIncidents(Set<ComplaintIncident> incidents) {
		this.incidents = incidents;
	}
	
	public ComplaintIncident getIncident(Long id) {
		if (id != null) {
			for (ComplaintIncident incident : getIncidents()) {
				if (incident.getId().equals(id)) {
					return incident;
				}
			}
		}
		return null;
	}
	
	public void addIncident(ComplaintIncident incident) {
		if (incident != null) {
			incident.setComplaint(this);
			getIncidents().add(incident);
		}
	}
	
	public void removeIncident(Long id) {
		for (Iterator<ComplaintIncident> itr = getIncidents().iterator(); itr.hasNext();) {
			ComplaintIncident incident = itr.next();
			if (incident.getId().equals(id)) {
				itr.remove();
				break;
			}
		}
	}
	
	public Set<ComplaintPerson> getPeopleInvolved() {
		return peopleInvolved;
	}
	
	public void setPeopleInvolved(Set<ComplaintPerson> peopleInvolved) {
		this.peopleInvolved = peopleInvolved;
	}
	
	public ComplaintPerson getPersonInvolved(Long id) {
		if (id != null) {
			for (ComplaintPerson cp : getPeopleInvolved()) {
				if (cp.getId().equals(id)) {
					return cp;
				}
			}
		}
		return null;
	}
	
	public void addPersonInvolved(ComplaintPerson person) {
		if (person != null) {
			person.setComplaint(this);
			getPeopleInvolved().add(person);
		}
	}
	
	public void removePersonInvolved(Long id) {
		for (Iterator<ComplaintPerson> itr = getPeopleInvolved().iterator(); itr.hasNext();) {
			ComplaintPerson cp = itr.next();
			if (cp.getId().equals(id)) {
				itr.remove();
				break;
			}
		}
	}
	
	public Set<Person> getWitnesses() {
		return witnesses;
	}
	
	public void setWitnesses(Set<Person> witnesses) {
		this.witnesses = witnesses;
	}
	
	public Person getWitness(Long id) {
		if (id != null) {
			for (Person witness : getWitnesses()) {
				if (witness.getId().equals(id)) {
					return witness;
				}
			}
		}
		return null;
	}
	
	public void addWitness(Person witness) {
		if (witness != null) {
			getWitnesses().add(witness);
		}
	}
	
	public void removeWitness(Long id) {
		for (Iterator<Person> itr = getWitnesses().iterator(); itr.hasNext();) {
			Person witness = itr.next();
			if (witness.getId().equals(id)) {
				itr.remove();
				break;
			}
		}
	}
	
	public Set<Allegation> getAllegations() {
		return allegations;
	}
	
	public void setAllegations(Set<Allegation> allegations) {
		this.allegations = allegations;
	}
	
	public Allegation getAllegation(Long id) {
		if (id != null) {
			for (Allegation allegation : getAllegations()) {
				if (allegation.getId().equals(id)) {
					return allegation;
				}
			}
		}
		return null;
	}
	
	public void addAllegation(Allegation allegation) {
		if (allegation != null) {
			allegation.setComplaint(this);
			getAllegations().add(allegation);
		}
	}
	
	public void removeAllegation(Long id) {
		for (Iterator<Allegation> itr = getAllegations().iterator(); itr.hasNext();) {
			Allegation allegation = itr.next();
			if (allegation.getId().equals(id)) {
				itr.remove();
				break;
			}
		}
	}
	
	public Set<Inspection> getInspections() {
		return inspections;
	}
	
	public void setInspections(Set<Inspection> inspections) {
		this.inspections = inspections;
	}
	
	public Inspection getInspection(Long id) {
		if (id != null) {
			for (Inspection inspection : getInspections()) {
				if (inspection.getId().equals(id)) {
					return inspection;
				}
			}
		}
		return null;
	}
	
	public void addInspection(Inspection inspection) {
		if (inspection != null) {
			getInspections().add(inspection);
		}
	}
	
	public void removeInspection(Long id) {
		for (Iterator<Inspection> itr = getInspections().iterator(); itr.hasNext();) {
			Inspection inspection = itr.next();
			if (inspection.getId().equals(id)) {
				itr.remove();
				break;
			}
		}
	}
	
	public void removeInspection(Inspection inspection) {
		getInspections().remove(inspection);
	}
	
	public Set<Referral> getReferrals() {
		return referrals;
	}
	
	public void setReferrals(Set<Referral> referrals) {
		this.referrals = referrals;
	}
	
	public Referral getReferral(Long id) {
		if (id != null) {
			for (Referral referral : getReferrals()) {
				if (referral.getId().equals(id)) {
					return referral;
				}
			}
		}
		return null;
	}
	
	public void addReferral(Referral referral) {
		if (referral != null) {
			referral.setComplaint(this);
			getReferrals().add(referral);
		}
	}
	
	public void removeReferral(Long id) {
		for (Iterator<Referral> itr = getReferrals().iterator(); itr.hasNext();) {
			Referral referral = itr.next();
			if (referral.getId().equals(id)) {
				itr.remove();
				break;
			}
		}
	}
	
	public boolean isAnonymous() {
		if (getComplainant() != null && getComplainant().getNameUsage() != null && getComplainant().getNameUsage() == NameUsage.NAME_REFUSED) {
			return true;
		}
		return false;
	}
	
	@Override
	public State getState() {
		if (super.getInternalState() == null) {
			return null;
		}
		return Complaint.State.valueOf(super.getInternalState());
	}
	
	@Override
	public void setState(State state, StateChange changeType, String note) {
		super.setState(state.name(), changeType.name(), note);
	}
	
	@Override
	public int compareTo(Complaint o) {
		if (this == o) {
			return 0;
		}
		
		int comp = CompareUtils.nullSafeComparableCompare(getFacility(), o.getFacility(), false);
		if (comp == 0) {
			comp = CompareUtils.nullSafeComparableCompare(getDateReceived(), o.getDateReceived(), false);
		}
		if (comp == 0) {
			comp = CompareUtils.nullSafeComparableCompare(getId(), o.getId(), false);
		}
		
		return comp;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((getFacility() == null) ? 0 : (getFacility().getId() == null ? getFacility().hashCode() : getFacility().getId().hashCode()));
		result = prime * result + ((getDateReceived() == null) ? 0 : getDateReceived().hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (!(obj instanceof Complaint)) {
			return false;
		}
		Complaint other = (Complaint) obj;
		if (getId() == null) {
			if (other.getId() != null) {
				return false;
			}
		} else if (!getId().equals(other.getId())) {
			return false;
		}
		if (getFacility() == null) {
			if (other.getFacility() != null) {
				return false;
			}
		} else if (!getFacility().equals(other.getFacility())) {
			return false;
		}
		if (getDateReceived() == null) {
			if (other.getDateReceived() != null) {
				return false;
			}
		} else if (!getDateReceived().equals(other.getDateReceived())) {
			return false;
		}
		return true;
	}
}