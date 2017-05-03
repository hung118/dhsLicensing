package gov.utah.dts.det.ccl.model;

import gov.utah.dts.det.admin.model.PickListValue;
import gov.utah.dts.det.model.AbstractAuditableEntity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * 
 * @author jtorres
 * 
 */
@SuppressWarnings("serial")
@Entity
@Table(name = "TRACKING_REC_SCREENING_DPSFBI")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class TrackingRecordScreeningDpsFbi extends AbstractAuditableEntity<Long> implements Serializable {

  @Id
  @Column(name = "ID", unique = true, nullable = false)
  private Long id;

  @Column(name = "FIRST_FBI_REQ_DATE")
  @Temporal(TemporalType.DATE)
  private Date firstFbiRequestDate;

  @Column(name = "SECOND_FBI_REQ_DATE")
  @Temporal(TemporalType.DATE)
  private Date secondFbiRequestDate;

  @Column(name = "FBI_NAME_CHECK_DATE")
  @Temporal(TemporalType.DATE)
  private Date fbiNameCheckDate;

  @Column(name = "FIRST_REJECTED_DATE")
  @Temporal(TemporalType.DATE)
  private Date firstRejectedDate;

  @Column(name = "SECOND_REJECTED_DATE")
  @Temporal(TemporalType.DATE)
  private Date secondRejectedDate;

  @Column(name = "RECEIVED_FROM_FBI_DATE")
  @Temporal(TemporalType.DATE)
  private Date receivedFromFbiDate;

  @Column(name = "LIVE_SCAN_DATE")
  @Temporal(TemporalType.DATE)
  private Date livescanDate;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "BILLING_ID")
  private PickListValue billing;

  @Column(name = "MO_NUMBER")
  private String moNumber;

  @Column(name = "ISSUED_BY")
  private String issuedBy;

  @Column(name = "SEARCH_FEE")
  private Double searchFee;

  @Column(name = "SCAN_FEE")
  private Double scanFee;

  @Column(name = "TO_DPS_FOR_VERIFICATION_DATE")
  @Temporal(TemporalType.DATE)
  private Date toDpsForVerificationDate;

  @Column(name = "DPS_VERIFIED_DATE")
  @Temporal(TemporalType.DATE)
  private Date dpsVerifiedDate;

  @Override
  public Long getPk() {
    return id;
  }

  @Override
  public void setPk(Long pk) {
    id = pk;

  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public Date getFirstFbiRequestDate() {
    return firstFbiRequestDate;
  }

  public void setFirstFbiRequestDate(Date firstFbiRequestDate) {
    this.firstFbiRequestDate = firstFbiRequestDate;
  }

  public Date getSecondFbiRequestDate() {
    return secondFbiRequestDate;
  }

  public void setSecondFbiRequestDate(Date secondFbiRequestDate) {
    this.secondFbiRequestDate = secondFbiRequestDate;
  }

  public Date getFbiNameCheckDate() {
    return fbiNameCheckDate;
  }

  public void setFbiNameCheckDate(Date fbiNameCheckDate) {
    this.fbiNameCheckDate = fbiNameCheckDate;
  }

  public Date getFirstRejectedDate() {
    return firstRejectedDate;
  }

  public void setFirstRejectedDate(Date firstRejectedDate) {
    this.firstRejectedDate = firstRejectedDate;
  }

  public Date getSecondRejectedDate() {
    return secondRejectedDate;
  }

  public void setSecondRejectedDate(Date secondRejectedDate) {
    this.secondRejectedDate = secondRejectedDate;
  }

  public Date getReceivedFromFbiDate() {
    return receivedFromFbiDate;
  }

  public void setReceivedFromFbiDate(Date receivedFromFbiDate) {
    this.receivedFromFbiDate = receivedFromFbiDate;
  }

  public Date getLivescanDate() {
    return livescanDate;
  }

  public void setLivescanDate(Date livescanDate) {
    this.livescanDate = livescanDate;
  }

  public String getMoNumber() {
    return moNumber;
  }

  public void setMoNumber(String moNumber) {
    this.moNumber = moNumber;
  }

  public String getIssuedBy() {
    return issuedBy;
  }

  public void setIssuedBy(String issuedBy) {
    this.issuedBy = issuedBy;
  }

  public Double getSearchFee() {
    return searchFee;
  }

  public void setSearchFee(Double searchFee) {
    this.searchFee = searchFee;
  }

  public Double getScanFee() {
    return scanFee;
  }

  public void setScanFee(Double scanFee) {
    this.scanFee = scanFee;
  }

  public Date getToDpsForVerificationDate() {
    return toDpsForVerificationDate;
  }

  public void setToDpsForVerificationDate(Date toDpsForVerificationDate) {
    this.toDpsForVerificationDate = toDpsForVerificationDate;
  }

  public Date getDpsVerifiedDate() {
    return dpsVerifiedDate;
  }

  public void setDpsVerifiedDate(Date dpsVerifiedDate) {
    this.dpsVerifiedDate = dpsVerifiedDate;
  }

  public PickListValue getBilling() {
    return billing;
  }

  public void setBilling(PickListValue billing) {
    this.billing = billing;
  }

  // returns a new anonymous class for validating the first fbi request date range
  public DateRange getFirstRejectDateRange() {
    return new DateRange() {

      public Date getStartDate() {
        return firstFbiRequestDate;
      }

      public Date getEndDate() {
        return firstRejectedDate;
      }
    };
  }

  // returns a new anonymous class for validating the second fbi request date range
  public DateRange getSecondRejectDateRange() {
    return new DateRange() {

      public Date getStartDate() {
        return secondFbiRequestDate;
      }

      public Date getEndDate() {
        return secondRejectedDate;
      }
    };
  }

  // returns a new anonymous class for validating the second fbi request date is after the first rejected date
  public DateRange getSecondRequestDateRange() {
    return new DateRange() {

      public Date getStartDate() {
        return firstRejectedDate;
      }

      public Date getEndDate() {
        return secondFbiRequestDate;
      }
    };
  }
  
  /**
   * Check to see whether the object contains any data.
   * @return true if any object attribute is non-null, false otherwise.
   */
  public boolean hasData() {
      return firstFbiRequestDate != null || secondFbiRequestDate != null || fbiNameCheckDate != null || secondRejectedDate != null ||
              receivedFromFbiDate != null || livescanDate != null || searchFee != null || scanFee != null || toDpsForVerificationDate != null || 
              dpsVerifiedDate != null || StringUtils.isNotBlank(moNumber) || StringUtils.isNotBlank(issuedBy) || 
              (billing != null && billing.getId() != null);
              
  }

}
