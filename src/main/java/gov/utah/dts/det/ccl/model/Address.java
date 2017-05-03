/**
 * 
 */
package gov.utah.dts.det.ccl.model;

import gov.utah.dts.det.model.AbstractBaseEntity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.apache.commons.lang.StringUtils;
import org.codehaus.jackson.annotate.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import com.opensymphony.xwork2.conversion.annotations.Conversion;
import com.opensymphony.xwork2.validator.annotations.RegexFieldValidator;

/**
 * @author DOLSEN
 * 
 */
@Conversion
@SuppressWarnings("serial")
@Entity
@Table(name = "ADDRESS")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Address extends AbstractBaseEntity<Long> implements Serializable, gov.utah.dts.det.ccl.view.Address {

	@Id
	@Column(name = "ID", unique = true, nullable = false, updatable = false)
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "ADDRESS_SEQ")
	@SequenceGenerator(name = "ADDRESS_SEQ", sequenceName = "ADDRESS_SEQ")
	private Long id;
	
	@Column(name = "ADDRESS_ONE")
	private String addressOne;

	@Column(name = "ADDRESS_TWO")
	private String addressTwo;

	@Column(name = "CITY")
	private String city;

	@Column(name = "STATE")
	private String state;

	@Column(name = "ZIP_CODE")
	private String zipCode;
	
	public Address() {
		
	}
	
	@JsonIgnore
	@Override
	public Long getPk() {
		return id;
	}
	
	@Override
	public void setPk(Long pk) {
		this.id = pk;
	}
	
	@JsonIgnore
	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}

	public String getAddressOne() {
		return addressOne;
	}

	public void setAddressOne(String addressOne) {
		this.addressOne = addressOne;
	}

	public String getAddressTwo() {
		return addressTwo;
	}

	public void setAddressTwo(String addressTwo) {
		this.addressTwo = addressTwo;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}
	
	@RegexFieldValidator(regexExpression = "^\\d{5}([\\-]\\d{4})?$", message = "${zipCode} is not a valid zip code (##### or #####-####)", shortCircuit = true)
	public String getZipCode() {
		return zipCode;
	}

	public void setZipCode(String zipCode) {
		this.zipCode = zipCode;
	}
	
	public String getCityStateZip() {
		StringBuilder sb = new StringBuilder();
	    if (StringUtils.isNotBlank(city)) {
	    	sb.append(city);
	    }
	    if (StringUtils.isNotBlank(state)) {
	    	if (sb.length() > 0) {
	    		sb.append(", ");
	    	}
	    	sb.append(state);
	    }
	    if (StringUtils.isNotBlank(zipCode)) {
	    	if (sb.length() > 0) {
	    		sb.append(" ");
	    	}
	    	sb.append(zipCode);
	    }
	    return sb.toString();
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append(addressOne);
		if (StringUtils.isNotBlank(addressTwo)) {
			sb.append(" ");
			sb.append(addressTwo);
		}
		sb.append(" ").append(city).append(", ").append(state).append(" ").append(zipCode);
		return sb.toString();
	}
}