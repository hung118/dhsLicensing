package gov.utah.dts.det.ccl.model.view;

import gov.utah.dts.det.admin.model.PickListValue;
import gov.utah.dts.det.ccl.model.Facility;
import gov.utah.dts.det.ccl.model.enums.FacilityType;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.apache.commons.lang.StringUtils;
import org.hibernate.annotations.Immutable;
import org.hibernate.annotations.Type;

@SuppressWarnings("serial")
@Entity
@Table(name = "FACILITY_LICENSE_VIEW")
@Immutable
public class FacilityLicenseView implements Serializable {

	@Id
    @Column(name = "ID", unique = true, nullable = false, insertable = false, updatable = false)
	private Long id;

	@Column(name = "FACILITY_ID")
	private Long facilityId;
	
	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "FACILITY_ID", insertable = false, updatable = false)
    private Facility facility;

    
	@Column(name = "FACILITYNAME")
	private String facilityName;

	@Column(name = "SITE_NAME")
	private String siteName;

	@Column(name = "FACILITY_TYPE")
	@Type(type = "FacilityType")
	private FacilityType facilityType;

    @Column(name = "LICENSE_ID")
    private Long licenseId;

    @Column(name = "STATUS")
    private String status;
    
    @Column(name = "LICENSE_SUBTYPE")
    private String subtype;

    @Column(name = "START_DATE")
    @Temporal(TemporalType.DATE)
    private Date startDate;

    @Column(name = "EXPIRATION_DATE")
    @Temporal(TemporalType.DATE)
    private Date expirationDate;
    
    @Column(name = "ADULT_TOTAL_SLOTS")
    private Integer adultTotalSlots;

    @Column(name = "ADULT_MALE_COUNT")
    private Integer adultMaleCount;
    
    @Column(name = "ADULT_FEMALE_COUNT")
    private Integer adultFemaleCount;

    @Column(name = "YOUTH_TOTAL_SLOTS")
    private Integer youthTotalSlots;

    @Column(name = "YOUTH_MALE_COUNT")
    private Integer youthMaleCount;
    
    @Column(name = "YOUTH_FEMALE_COUNT")
    private Integer youthFemaleCount;

    @Column(name = "SERVICE_CODE")
    private String serviceCode;
    
    @ManyToMany
    @JoinTable(name = "LICENSE_PROGRAM_CODE",
        joinColumns = @JoinColumn(name = "LICENSE_ID", referencedColumnName = "ID"),
        inverseJoinColumns = @JoinColumn(name = "PROGRAM_CODE_ID", referencedColumnName = "ID")
    )
    private List<PickListValue> programCodeIds = new ArrayList<PickListValue>();
    
    @Column(name = "SPECIFIC_SERVICE_CODE")
    private String specificServiceCode;

    @Column(name = "AGE_GROUP")
    private String ageGroup;
    
    @Column(name = "FROM_AGE")
    private String fromAge;
    
    @Column(name = "TO_AGE")
    private String toAge;

	public FacilityLicenseView() {

	}

	public Long getPk() {
		return id;
	}

	public void setPk(Long pk) {
		this.id = pk;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

    public Long getFacilityId() {
        return facilityId;
    }

    public void setFacilityId(Long facilityId) {
        this.facilityId = facilityId;
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

	public FacilityType getFacilityType() {
		return facilityType;
	}

	public void setFacilityType(FacilityType facilityType) {
		this.facilityType = facilityType;
	}

	public String getSubtype() {
	    return subtype;
	}

	public void setSubtype(String subtype) {
	    this.subtype = subtype;
	}

	public String getStatus() {
	    return status;
	}

	public void setStatus(String status) {
	    this.status = status;
	}

	public String getServiceCode() {
	    return serviceCode;
	}

	public void setServiceCode(String serviceCode) {
	    this.serviceCode = serviceCode;
	}

	public String getServiceCodeCode() {
	    String rval = null;
	    if (StringUtils.isNotBlank(serviceCode)) {
	        String code = serviceCode;
	        int idx = code.indexOf("-");
	        if (idx > 0) {
	            rval = code.substring(0,idx).trim();
	        }
	    }
	    return rval;
	}

	public String getServiceCodeDesc() {
	    String rval = null;
	    if (StringUtils.isNotBlank(serviceCode)) {
	        String code = serviceCode;
	        int idx = code.indexOf("-");
	        if (idx >= 0 && (idx+1 <= code.length())) {
	            idx++;
	            rval = code.substring(idx).trim();
	        }
	    }
	    return rval;
	}

	public Integer getAdultTotalSlots() {
	    return adultTotalSlots;
	}

	public void setAdultTotalSlots(Integer adultTotalSlots) {
	    this.adultTotalSlots = adultTotalSlots;
	}

	public Integer getAdultMaleCount() {
	    return adultMaleCount;
	}

	public void setAdultMaleCount(Integer adultMaleCount) {
	    this.adultMaleCount = adultMaleCount;
	}

	public Integer getAdultFemaleCount() {
	    return adultFemaleCount;
	}

	public void setAdultFemaleCount(Integer adultFemaleCount) {
	    this.adultFemaleCount = adultFemaleCount;
	}

	public Date getStartDate() {
	    return startDate;
	}

	public void setStartDate(Date startDate) {
	    this.startDate = org.apache.commons.lang.time.DateUtils.truncate(startDate, Calendar.DATE);
	}

	public Date getExpirationDate() {
	    return expirationDate;
	}

	public void setExpirationDate(Date expirationDate) {
	    this.expirationDate = org.apache.commons.lang.time.DateUtils.truncate(expirationDate, Calendar.DATE);
	}

	public String getExpirationDateFormatted() {
	    SimpleDateFormat df = new SimpleDateFormat("MM/dd/yyyy");
	    if (getExpirationDate() == null) {
	        return null;
	    }
	    return df.format(getExpirationDate());
	}

	public String getSpecificServiceCode() {
	    return specificServiceCode;
	}

	public void setSpecificServiceCode(String specificServiceCode) {
	    this.specificServiceCode = specificServiceCode;
	}

	public List<PickListValue> getProgramCodeIds() {
	    return programCodeIds;
	}

	public void setProgramCodeIds(List<PickListValue> programCodeIds) {
	    this.programCodeIds = programCodeIds;
	}

	public String getAgeGroup() {
	    return ageGroup;
	}

	public void setAgeGroup(String ageGroup) {
	    this.ageGroup = ageGroup;
	}

	public String getFromAge() {
	    return fromAge;
	}

	public void setFromAge(String fromAge) {
	    this.fromAge = fromAge;
	}

	public String getToAge() {
	    return toAge;
	}

	public void setToAge(String toAge) {
	    this.toAge = toAge;
	}

	public Integer getYouthTotalSlots() {
	    return youthTotalSlots;
	}

	public void setYouthTotalSlots(Integer youthTotalSlots) {
	    this.youthTotalSlots = youthTotalSlots;
	}

	public Integer getYouthMaleCount() {
	    return youthMaleCount;
	}

	public void setYouthMaleCount(Integer youthMaleCount) {
	    this.youthMaleCount = youthMaleCount;
	}

	public Integer getYouthFemaleCount() {
	    return youthFemaleCount;
	}

	public void setYouthFemaleCount(Integer youthFemaleCount) {
	    this.youthFemaleCount = youthFemaleCount;
	}

	public Long getLicenseNumber() {
	    return licenseId;
	}

	public String getLicenseListDescriptor() {
	    StringBuilder sb = new StringBuilder();
	    if (getLicenseNumber() != null) {
	        sb.append("(");
            sb.append(getLicenseNumber().toString());
            sb.append(")");
	    }
	    if (StringUtils.isNotBlank(serviceCode)) {
	        if (sb.length() > 0) {
	            sb.append(" ");
	        }
	        sb.append(serviceCode);
	    }
	    if (StringUtils.isNotBlank(subtype)) {
	        if (sb.length() > 0) {
	            sb.append(" ");
	        }
	        sb.append(":");
            sb.append(subtype);
            sb.append(":");
	    }
	    return sb.toString();
	}

}