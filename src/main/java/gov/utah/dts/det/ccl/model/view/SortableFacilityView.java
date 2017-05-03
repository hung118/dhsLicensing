package gov.utah.dts.det.ccl.model.view;

import gov.utah.dts.det.ccl.model.Facility;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Immutable;

@SuppressWarnings("serial")
@Entity
@Table(name = "SORTABLE_FACILITY_VIEW")
@Immutable
public class SortableFacilityView implements Serializable {

	@Id
    @Column(name = "ID", unique = true, nullable = false, insertable = false, updatable = false)
	private Long id;
	
	@ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "ID", insertable = false, updatable = false)
    private Facility facility;

	@Column(name = "FACILITY_NAME")
	private String facilityName;

	@Column(name = "SITE_NAME")
	private String siteName;

	public SortableFacilityView() {

	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

    public Facility getFacility() {
        return facility;
    }

	public void setFacility(Facility facility) {
	    this.facility = facility;
	}

	public String getFacilityName() {
		return facilityName;
	}

	public void setFacilityName(String facilityName) {
		this.facilityName = facilityName;
	}

	public String getSiteName() {
		return siteName;
	}

	public void setSiteName(String siteName) {
		this.siteName = siteName;
	}

}