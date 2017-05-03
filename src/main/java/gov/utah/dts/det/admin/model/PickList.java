/**
 * $Rev: 163 $:
 * $LastChangedDate: 2009-07-13 08:10:12 -0600 (Mon, 13 Jul 2009) $:
 * $Author: danolsen $:
 */
package gov.utah.dts.det.admin.model;

import gov.utah.dts.det.model.AbstractBaseEntity;
import gov.utah.dts.det.util.CompareUtils;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;
import org.hibernate.annotations.OrderBy;

/**
 * @author DOLSEN
 * 
 */
@SuppressWarnings("serial")
@Entity
@Table(name = "PICK_LIST")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class PickList extends AbstractBaseEntity<Long> implements Serializable, Comparable<PickList> {

	@Id
	@Column(name = "ID", unique = true, nullable = false, updatable = false)
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "PICK_LIST_SEQ")
	@SequenceGenerator(name = "PICK_LIST_SEQ", sequenceName = "PICK_LIST_SEQ")
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "GROUP_ID")
	private PickListGroup pickListGroup;
	
	@Column(name = "NAME")
	private String name;

	@Column(name = "SORT_ORDER")
	private String sortOrder;

	@Column(name = "DEFAULT_VALUE_ID")
	private Long defaultValue;

	@OneToMany(mappedBy = "pickList", cascade = CascadeType.ALL, orphanRemoval = true)
	@OrderBy(clause = "ISACTIVE desc, SORTORDER, PLVVALUE")
	@LazyCollection(LazyCollectionOption.FALSE)
	@Fetch(FetchMode.JOIN)
	private List<PickListValue> pickListValues = new ArrayList<PickListValue>();
	
	public PickList() {
		
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
	
	public PickListGroup getPickListGroup() {
		return pickListGroup;
	}
	
	public void setPickListGroup(PickListGroup pickListGroup) {
		this.pickListGroup = pickListGroup;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}

	public String getSortOrder() {
		return sortOrder;
	}

	public void setSortOrder(String sortOrder) {
		this.sortOrder = sortOrder;
	}

	public Long getDefaultValue() {
		return defaultValue;
	}

	public void setDefaultValue(Long defaultValue) {
		this.defaultValue = defaultValue;
	}

	public List<PickListValue> getPickListValues() {
		return pickListValues;
	}

	public void setPickListValues(List<PickListValue> pickListValues) {
		this.pickListValues = pickListValues;
	}
	
	@Override
	public int compareTo(PickList o) {
		if (this == o) {
			return 0;
		}
		
		int comp = CompareUtils.nullSafeComparableCompare(getPickListGroup(), o.getPickListGroup(), false);
		if (comp == 0) {
			comp = CompareUtils.nullSafeComparableCompare(getSortOrder(), o.getSortOrder(), false);
		}
		if (comp == 0) {
			comp = CompareUtils.nullSafeComparableCompare(getName(), o.getName(), true);
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
		result = prime * result + ((getPickListGroup() == null) ? 0 : (getPickListGroup().getId() == null ? getPickListGroup().hashCode() : getPickListGroup().getId().hashCode()));
		result = prime * result + ((getSortOrder() == null) ? 0 : getSortOrder().hashCode());
		result = prime * result + ((getName() == null) ? 0 : getName().hashCode());
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
		if (!(obj instanceof PickList)) {
			return false;
		}
		PickList other = (PickList) obj;
		if (getId() == null) {
			if (other.getId() != null) {
				return false;
			}
		} else if (!getId().equals(other.getId())) {
			return false;
		}
		if (getPickListGroup() == null) {
			if (other.getPickListGroup() != null) {
				return false;
			}
		} else if (!getPickListGroup().equals(other.getPickListGroup())) {
			return false;
		}
		if (getSortOrder() == null) {
			if (other.getSortOrder() != null) {
				return false;
			}
		} else if (!getSortOrder().equals(other.getSortOrder())) {
			return false;
		}
		if (getName() == null) {
			if (other.getName() != null) {
				return false;
			}
		} else if (!getName().equals(other.getName())) {
			return false;
		}
		return true;
	}
}