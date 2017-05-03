package gov.utah.dts.det.ccl.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@SuppressWarnings("serial")
@Embeddable
public class Phone implements Serializable {

	@Column(name = "PHONENUMBER")
	private String phoneNumber;
	
	public String getPhoneNumber() {
		return phoneNumber;
	}
	
	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
	
	public String getFormattedPhoneNumber() {
		if (phoneNumber == null || (phoneNumber.length() != 7 && phoneNumber.length() != 10)) {
			return "";
		}
		
		int start = 0;
		StringBuilder sb = new StringBuilder();
		if (phoneNumber.length() == 10) {
			sb.append("(");
			sb.append(phoneNumber.substring(0, 3));
			sb.append(") ");
			start = 3;
		}
		sb.append(phoneNumber.substring(start, start + 3));
		sb.append("-");
		sb.append(phoneNumber.substring(start + 3));
		return sb.toString();
	}
	
	public String toString() {
		return getFormattedPhoneNumber();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((phoneNumber == null) ? 0 : phoneNumber.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Phone other = (Phone) obj;
		if (phoneNumber == null) {
			if (other.phoneNumber != null)
				return false;
		} else if (!phoneNumber.equals(other.phoneNumber))
			return false;
		return true;
	}
}