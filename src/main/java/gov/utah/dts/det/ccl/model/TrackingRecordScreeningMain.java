package gov.utah.dts.det.ccl.model;

import gov.utah.dts.det.model.AbstractAuditableWithComments;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * 
 * @author jtorres
 * 
 */

@SuppressWarnings("serial")
@Entity
@Table(name = "TRACKING_RECORD_SCREENING_MAIN")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class TrackingRecordScreeningMain extends AbstractAuditableWithComments<Long> implements Serializable {

  @Id
  @Column(name = "ID", unique = true, nullable = false)
  private Long id;

  @Column(name = "DATE_RECEIVED")
  @Temporal(TemporalType.DATE)
  private Date dateReceived;

  @Column(name = "CAL_CLEARED_DATE")
  @Temporal(TemporalType.DATE)
  private Date calClearedDate;

  @Column(name = "LISA_CLEARED_DATE")
  @Temporal(TemporalType.DATE)
  private Date lisaClearedDate;

  @Column(name = "AMIS_CLEARED_DATE")
  @Temporal(TemporalType.DATE)
  private Date amisClearedDate;

  @Column(name = "OSCAR_CLEARED_DATE")
  @Temporal(TemporalType.DATE)
  private Date oscarClearedDate;

  @Column(name = "ADAM_FBI_CLEARED_DATE")
  @Temporal(TemporalType.DATE)
  private Date adamFbiClearedDate;

  @Column(name = "APPROVAL_MAILED_DATE")
  @Temporal(TemporalType.DATE)
  private Date approvalMailedDate;

  @Column(name = "TO_MIS_A_DATE")
  @Temporal(TemporalType.DATE)
  private Date toMisADate;

  @Column(name = "BACK_FROM_MIS_A_DATE")
  @Temporal(TemporalType.DATE)
  private Date backFromMisA;

  @Column(name = "TO_OSCAR_DATE")
  @Temporal(TemporalType.DATE)
  private Date toOscarDate;

  @Column(name = "NAA_DATE")
  @Temporal(TemporalType.DATE)
  private Date naaDate;

  @Column(name = "FPI_DATE")
  @Temporal(TemporalType.DATE)
  private Date fpiDate;

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

  public Date getDateReceived() {
    return dateReceived;
  }

  public void setDateReceived(Date dateReceived) {
    this.dateReceived = dateReceived;
  }

  public Date getCalClearedDate() {
    return calClearedDate;
  }

  public void setCalClearedDate(Date calClearedDate) {
    this.calClearedDate = calClearedDate;
  }

  public Date getLisaClearedDate() {
    return lisaClearedDate;
  }

  public void setLisaClearedDate(Date lisaClearedDate) {
    this.lisaClearedDate = lisaClearedDate;
  }

  public Date getAmisClearedDate() {
    return amisClearedDate;
  }

  public void setAmisClearedDate(Date amisClearedDate) {
    this.amisClearedDate = amisClearedDate;
  }

  public Date getOscarClearedDate() {
    return oscarClearedDate;
  }

  public void setOscarClearedDate(Date oscarClearedDate) {
    this.oscarClearedDate = oscarClearedDate;
  }

  public Date getAdamFbiClearedDate() {
    return adamFbiClearedDate;
  }

  public void setAdamFbiClearedDate(Date adamFbiClearedDate) {
    this.adamFbiClearedDate = adamFbiClearedDate;
  }

  public Date getApprovalMailedDate() {
    return approvalMailedDate;
  }

  public void setApprovalMailedDate(Date approvalMailedDate) {
    this.approvalMailedDate = approvalMailedDate;
  }

  public Date getToMisADate() {
    return toMisADate;
  }

  public void setToMisADate(Date toMisADate) {
    this.toMisADate = toMisADate;
  }

  public Date getBackFromMisA() {
    return backFromMisA;
  }

  public void setBackFromMisA(Date backFromMisA) {
    this.backFromMisA = backFromMisA;
  }

  public Date getToOscarDate() {
    return toOscarDate;
  }

  public void setToOscarDate(Date toOscarDate) {
    this.toOscarDate = toOscarDate;
  }

  public Date getNaaDate() {
    return naaDate;
  }

  public void setNaaDate(Date naaDate) {
    this.naaDate = naaDate;
  }

  public Date getFpiDate() {
    return fpiDate;
  }

  public void setFpiDate(Date fpiDate) {
    this.fpiDate = fpiDate;
  }
  
  /**
   * Check to see whether the object contains any data.
   * @return true if any object attribute is non-null, false otherwise.
   */
  public boolean hasData() {
      return dateReceived != null || calClearedDate != null || lisaClearedDate != null || amisClearedDate != null || oscarClearedDate != null ||
              adamFbiClearedDate != null || toMisADate != null || backFromMisA != null || toOscarDate != null || naaDate != null || fpiDate != null;
  }

}
