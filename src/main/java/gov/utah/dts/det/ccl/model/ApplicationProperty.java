package gov.utah.dts.det.ccl.model;

import gov.utah.dts.det.model.AbstractBaseEntity;
import java.io.Serializable;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

@SuppressWarnings("serial")
@Entity
@Table(name = "APPLICATIONPROPERTY")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class ApplicationProperty extends AbstractBaseEntity<String> implements Serializable {

	@Id
	@Column(name = "NAME", unique = true, nullable = false, updatable = false)
	private String name;
	
	@Lob
	@Column(name = "VALUE")
	private String value;
	
	public ApplicationProperty() {
	
	}
	
	@Override
	public String getPk() {
		return name;
	}
	
	@Override
	public void setPk(String pk) {
		this.name = pk;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getValue() {
		return value;
	}
	
	public void setValue(String value) {
		this.value = value;
	}
}