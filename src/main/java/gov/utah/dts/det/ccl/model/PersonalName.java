package gov.utah.dts.det.ccl.model;

import gov.utah.dts.det.util.CompareUtils;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Transient;

import org.hibernate.annotations.Formula;

@SuppressWarnings("serial")
@Embeddable
public class PersonalName implements Serializable, Comparable<PersonalName> {

	@Column(name = "FIRST_NAME")
	private String firstName;
	
	@Column(name = "MIDDLE_NAME")
	private String middleName;
	
	@Column(name = "LAST_NAME")
	private String lastName;
	
	@SuppressWarnings("unused")
	@Formula("0")
	private int dummy;

	@Transient
	private transient String firstAndLastName;
	
	@Transient
	private transient String fullName;
	
	public PersonalName() {
		
	}
	
	public PersonalName(String firstName, String lastName) {
		this.firstName = firstName;
		this.lastName = lastName;
	}
	
	public PersonalName(String firstName, String middleName, String lastName) {
		this.firstName = firstName;
		this.middleName = middleName;
		this.lastName = lastName;
	}
	
	public String getFirstName() {
		return firstName;
	}
	
	public void setFirstName(String firstName) {
		this.firstName = firstName;
		firstAndLastName = null;
		fullName = null;
	}
	
	public String getMiddleName() {
		return middleName;
	}
	
	public void setMiddleName(String middleName) {
		this.middleName = middleName;
		fullName = null;
	}
	
	public String getLastName() {
		return lastName;
	}
	
	public void setLastName(String lastName) {
		this.lastName = lastName;
		firstAndLastName = null;
		fullName = null;
	}
	
	@Override
	public String toString() {
		return getFirstAndLastName();
	}
	
	public String getFirstAndLastName() {
		if (firstAndLastName == null) {
			StringBuilder sb = new StringBuilder(firstName == null ? "" : firstName);
			if (lastName != null) {
				sb.append(" ");
			}
			sb.append(lastName == null ? "" : lastName);
			firstAndLastName = sb.toString();
		}
		return firstAndLastName;
	}
	
	public String getFullName() {
		if (fullName == null) {
			StringBuilder sb = new StringBuilder(firstName == null ? "" : firstName);
			if (middleName != null) {
				sb.append(" ");
			}
			sb.append(middleName == null ? "" : middleName);
			if (lastName != null) {
				sb.append(" ");
			}
			sb.append(lastName == null ? "" : lastName);
			fullName = sb.toString();
		}
		return fullName;
	}

	public String getInitials() {
		StringBuilder sb = new StringBuilder((firstName == null || firstName.trim() == null) ? "" : firstName.trim().substring(0,1).toUpperCase());
		sb.append((middleName == null || middleName.trim() == null) ? "" : middleName.trim().substring(0,1).toUpperCase());
		sb.append((lastName == null || lastName.trim() == null) ? "" : lastName.trim().substring(0,1).toUpperCase());
		if (sb.length() > 0) {
			return sb.toString();
		} else {
			return null;
		}
	}
	
	@Override
	public int compareTo(PersonalName o) {
		if (this == o) {
			return 0;
		}
		int comp = CompareUtils.nullSafeComparableCompare(this.firstName, o.firstName, true);
		if (comp == 0) {
			comp = CompareUtils.nullSafeComparableCompare(this.lastName, o.lastName, true);
		}
		if (comp == 0) {
			comp = CompareUtils.nullSafeComparableCompare(this.middleName, o.middleName, true);
		}
		return comp;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((firstName == null) ? 0 : firstName.hashCode());
		result = prime * result + ((lastName == null) ? 0 : lastName.hashCode());
		result = prime * result + ((middleName == null) ? 0 : middleName.hashCode());
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
		if (!(obj instanceof PersonalName)) {
			return false;
		}
		PersonalName other = (PersonalName) obj;
		if (firstName == null) {
			if (other.firstName != null) {
				return false;
			}
		} else if (!firstName.equals(other.firstName)) {
			return false;
		}
		if (lastName == null) {
			if (other.lastName != null) {
				return false;
			}
		} else if (!lastName.equals(other.lastName)) {
			return false;
		}
		if (middleName == null) {
			if (other.middleName != null) {
				return false;
			}
		} else if (!middleName.equals(other.middleName)) {
			return false;
		}
		return true;
	}
}