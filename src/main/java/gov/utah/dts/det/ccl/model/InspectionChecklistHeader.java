package gov.utah.dts.det.ccl.model;

import gov.utah.dts.det.model.AbstractBaseEntity;
import gov.utah.dts.det.util.CompareUtils;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@SuppressWarnings("serial")
@Entity
@Table(name = "INSPECTION_CHECKLIST_HEADER")
public class InspectionChecklistHeader  extends AbstractBaseEntity<Long> implements Serializable, Comparable<InspectionChecklistHeader> {
	
	public static final String FEE = "fee";
	public static final String FACILITY = "facility";
	public static final String STAFFER = "staffer";
	public static final String DATE = "date";
	public static final String PROGRAM = "program";
	public static final String DIRECTOR = "director";
	public static final String LICENCE_ADULT_CAPACITY = "license_adult_capacity";
	public static final String LICENCE_YOUTH_CAPACITY = "license_youth_capacity";
	public static final String CONSUMERS_ENROLLED = "consumers_enrolled";
	public static final String ADDRESS_1 = "address_1";
	public static final String ADDRESS_2 = "address_2";
	public static final String CITY = "city";
	public static final String STATE = "state";
	public static final String ZIPCODE = "zipcode";
	public static final String SIGNATURE = "signature";

	@Id
	@Column(name = "ID", unique = true, nullable = false, updatable = false)
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "INSP_CHKLST_HDR_SEQ")
	@SequenceGenerator(name = "INSP_CHKLST_HDR_SEQ", sequenceName = "INSP_CHKLST_HDR_SEQ")
	private Long id;
	
	@ManyToOne()
	@JoinColumn(name = "INSPECTION_CHECKLIST_ID")
	private InspectionChecklist inspectionChecklist;
	
	@Column(name = "ITEM_NAME")
	private String itemName;
	
	@Column(name = "ITEM_LABEL")
	private String itemLabel;

	@Column(name = "ITEM_VALUE")
	private String itemValue;
	
	@Column(name = "SORT_ORDER")
	private int sortOrder;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public InspectionChecklist getInspectionChecklist() {
		return inspectionChecklist;
	}

	public void setInspectionChecklist(InspectionChecklist inspectionChecklist) {
		this.inspectionChecklist = inspectionChecklist;
	}


	public int getSortOrder() {
		return sortOrder;
	}

	public void setSortOrder(int sortOrder) {
		this.sortOrder = sortOrder;
	}

	@Override
	public Long getPk() {
		return id;
	}

	@Override
	public void setPk(Long pk) {
		this.id = pk;
	}

	@Override
	public int compareTo(InspectionChecklistHeader o) {
		if (this == o) {
			return 0;
		}
		int comp = 0;
		if (comp == 0) {
			comp = CompareUtils.nullSafeComparableCompare(getItemName(), o.getItemName(), false);
		}
		if (comp == 0) {
			comp = CompareUtils.nullSafeComparableCompare(getItemLabel(), o.getItemLabel(), false);
		}
		if (comp == 0) {
			comp = CompareUtils.nullSafeComparableCompare(getItemValue(), o.getItemValue(), true);
		}
		if (comp == 0) {
			comp = CompareUtils.nullSafeComparableCompare(getSortOrder(), o.getSortOrder(), true);
		}
		if (comp == 0) {
			comp = CompareUtils.nullSafeComparableCompare(getId(), o.getId(), false);
		}
		return comp;
	}

	public String getItemName() {
		return itemName;
	}

	public void setItemName(String itemName) {
		this.itemName = itemName;
	}

	public String getItemLabel() {
		return itemLabel;
	}

	public void setItemLabel(String itemLabel) {
		this.itemLabel = itemLabel;
	}

	public String getItemValue() {
		return itemValue;
	}

	public void setItemValue(String itemValue) {
		this.itemValue = itemValue;
	}


}
