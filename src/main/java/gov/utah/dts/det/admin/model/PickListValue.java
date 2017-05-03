/**
 * $Rev: 154 $:
 * $LastChangedDate: 2009-07-02 07:23:30 -0600 (Thu, 02 Jul 2009) $:
 * $Author: dunnjo $:
 */
package gov.utah.dts.det.admin.model;

import gov.utah.dts.det.ccl.model.Activatable;
import gov.utah.dts.det.model.AbstractBaseEntity;
import gov.utah.dts.det.util.CompareUtils;

import java.io.Serializable;

import javax.persistence.Cacheable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.codehaus.jackson.annotate.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Type;

import com.opensymphony.xwork2.validator.annotations.ConversionErrorFieldValidator;
import com.opensymphony.xwork2.validator.annotations.RequiredStringValidator;

/**
 * @author DOLSEN
 * 
 */
@SuppressWarnings("serial")
@Entity
@Table(name = "PICKLISTVALUE")
@Inheritance(strategy = InheritanceType.JOINED)
@Cacheable
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class PickListValue extends AbstractBaseEntity<Long> implements Serializable, Activatable, Comparable<PickListValue> {

	@Id
	@Column(name = "ID", unique = true, nullable = false, updatable = false)
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "PICKLISTVALUE_SEQ")
	@SequenceGenerator(name = "PICKLISTVALUE_SEQ", sequenceName = "PICKLISTVALUE_SEQ")
	private Long id;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "PICKLIST_ID")
	private PickList pickList;

	@Column(name = "PLVVALUE")
	private String value;

	@Column(name = "ISACTIVE")
	@Type(type = "yes_no")
	private boolean active;

	@Column(name = "SORTORDER")
	private Double sortOrder;
	
	public PickListValue() {
		
	}
	
	@Override
	@JsonIgnore
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

	@JsonIgnore
	public PickList getPickList() {
		return pickList;
	}

	public void setPickList(PickList pickList) {
		this.pickList = pickList;
	}

	@RequiredStringValidator(message = "Value is required.")
	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	@JsonIgnore
	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	@JsonIgnore
	@ConversionErrorFieldValidator(message = "Sort order must be a numeric value.")
	public Double getSortOrder() {
		return sortOrder;
	}

	public void setSortOrder(Double sortOrder) {
		this.sortOrder = sortOrder;
	}
	
	@Override
	public String toString() {
		return value;
	}
	
	@Override
	public int compareTo(PickListValue o) {
		if (this == o) {
			return 0;
		}
		
		int comp = CompareUtils.nullSafeComparableCompare(getPickList(), o.getPickList(), false);
		if (comp == 0) {
			comp = CompareUtils.nullSafeComparableCompare(Boolean.valueOf(isActive()), Boolean.valueOf(o.isActive()), false);
		}
		if (comp == 0) {
			comp = CompareUtils.nullSafeComparableCompare(getSortOrder(), o.getSortOrder(), false);
		}
		if (comp == 0) {
			comp = CompareUtils.nullSafeComparableCompare(getValue(), o.getValue(), true);
		}
		if (comp == 0) {
			comp = CompareUtils.nullSafeComparableCompare(getId(), o.getId(), true);
		}
			
		return comp;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((getPickList() == null) ? 0 : (getPickList().getId() == null ? getPickList().hashCode() : getPickList().getId().hashCode()));
		result = prime * result + (isActive() ? 1231 : 1237);
		result = prime * result + ((getSortOrder() == null) ? 0 : getSortOrder().hashCode());
		result = prime * result + ((getValue() == null) ? 0 : getValue().hashCode());
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
		if (!(obj instanceof PickListValue)) {
			return false;
		}
		PickListValue other = (PickListValue) obj;
		if (getId() == null) {
			if (other.getId() != null) {
				return false;
			}
		} else if (!getId().equals(other.getId())) {
			return false;
		}
		if (getPickList() == null) {
			if (other.getPickList() != null) {
				return false;
			}
		} else if (!getPickList().equals(other.getPickList())) {
			return false;
		}
		if (isActive() != other.isActive()) {
			return false;
		}
		if (getSortOrder() == null) {
			if (other.getSortOrder() != null) {
				return false;
			}
		} else if (!getSortOrder().equals(other.getSortOrder())) {
			return false;
		}
		if (getValue() == null) {
			if (other.getValue() != null) {
				return false;
			}
		} else if (!getValue().equals(other.getValue())) {
			return false;
		}
		return true;
	}
}