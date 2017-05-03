package gov.utah.dts.det.ccl.model;

import gov.utah.dts.det.ccl.model.enums.RoleType;
import gov.utah.dts.det.model.AbstractAuditableEntity;
import gov.utah.dts.det.util.CompareUtils;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
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

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Type;

import com.opensymphony.xwork2.validator.annotations.RequiredStringValidator;
import com.opensymphony.xwork2.validator.annotations.VisitorFieldValidator;

@SuppressWarnings("serial")
@Entity
@Table(name = "SECURITY_USER")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class User extends AbstractAuditableEntity<Long> implements Serializable, Activatable, Comparable<User> {

	@Id
	@Column(name = "ID", unique = true, nullable = false, updatable = false)
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "SECURITY_USER_SEQ")
	@SequenceGenerator(name = "SECURITY_USER_SEQ", sequenceName = "SECURITY_USER_SEQ")
	private Long id;
	
	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "PERSONID")
	private Person person;

	@Column(name = "USERNAME")
	private String username;

	@Column(name = "ACTIVE")
	@Type(type = "yes_no")
	private boolean active;

	@ElementCollection(fetch = FetchType.EAGER)
	@CollectionTable(name = "USER_ROLE", joinColumns = @JoinColumn(name = "USER_ID"))
	@Column(name = "ROLE_NAME")
	@Enumerated(EnumType.STRING)
	private Set<RoleType> roles = new HashSet<RoleType>();

	public User() {
		
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

	@VisitorFieldValidator(message = "&zwnj;")
	public Person getPerson() {
		return person;
	}

	public void setPerson(Person person) {
		this.person = person;
	}

	@RequiredStringValidator(message = "Email address is required.")
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}
	
	public Set<RoleType> getRoles() {
		return roles;
	}
	
	public void setRoles(Set<RoleType> roles) {
		this.roles = roles;
	}
	
	@Override
	public String toString() {
		return person.getFirstAndLastName();
	}
	
	@Override
	public int compareTo(User o) {
		if (this == o) {
			return 0;
		}
		
		int comp = CompareUtils.nullSafeComparableCompare(getPerson(), o.getPerson(), false);
		if (comp == 0) {
			comp = CompareUtils.nullSafeComparableCompare(getId(), o.getId(), false);
		}
		
		return comp;
	}
}