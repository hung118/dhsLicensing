/**
 * $Rev: 14 $:
 * $LastChangedDate: 2009-02-26 08:32:19 -0700 (Thu, 26 Feb 2009) $:
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
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

@SuppressWarnings("serial")
@Entity
@Table(name = "PICK_LIST_GROUP")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class PickListGroup extends AbstractBaseEntity<Long> implements Serializable, Comparable<PickListGroup> {

	@Id
	@Column(name = "ID", unique = true, nullable = false, updatable = false)
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "PICK_LIST_GROUP_SEQ")
	@SequenceGenerator(name = "PICK_LIST_GROUP_SEQ", sequenceName = "PICK_LIST_GROUP_SEQ")
	private Long id;
	
	@Column(name = "NAME")
	private String name;

	@Column(name = "SORT_ORDER", nullable = true)
	private Long sortOrder;

	@OneToMany(mappedBy = "pickListGroup", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private List<PickList> pickLists = new ArrayList<PickList>();
	
	public PickListGroup() {
		
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

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Long getSortOrder() {
		return sortOrder;
	}

	public void setSortOrder(Long sortOrder) {
		this.sortOrder = sortOrder;
	}

	public List<PickList> getPickLists() {
		return pickLists;
	}

	public void setPickLists(List<PickList> pickLists) {
		this.pickLists = pickLists;
	}
	
	@Override
	public int compareTo(PickListGroup o) {
		if (this == o) {
			return 0;
		}
		
		int comp = CompareUtils.nullSafeComparableCompare(getSortOrder(), o.getSortOrder(), false);
		if (comp == 0) {
			comp = CompareUtils.nullSafeComparableCompare(getName(), o.getName(), true);
		}
		if (comp == 0) {
			comp = CompareUtils.nullSafeComparableCompare(getId(), o.getId(), false);
		}
		return comp;
	}
}