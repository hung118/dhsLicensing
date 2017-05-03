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
@Table(name = "TRAC_REC_SCREENING_CBSCOMM")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class TrackingRecordScreeningCbsComm extends AbstractAuditableEntity<Long> implements Serializable {

  @Id
  @Column(name = "ID", unique = true, nullable = false)
  private Long id;

  @Column(name = "CBS_COMMITTEE_DECISION")
  private String cbsCommitteeDecision;

  @Column(name = "CBS_COMM_DECISION_DATE")
  @Temporal(TemporalType.DATE)
  private Date decisionDate;

  @Column(name = "OAH_REQUEST_DATE")
  @Temporal(TemporalType.DATE)
  private Date oahRequestDate;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "OAH_DECISION_ID")
  private PickListValue oahDecision;

  @Column(name = "COM_REASONS")
  private String comReasons;

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

  public Date getDecisionDate() {
    return decisionDate;
  }

  public void setDecisionDate(Date decisionDate) {
    this.decisionDate = decisionDate;
  }

  public Date getOahRequestDate() {
    return oahRequestDate;
  }

  public void setOahRequestDate(Date oahRequestDate) {
    this.oahRequestDate = oahRequestDate;
  }

  public PickListValue getOahDecision() {
    return oahDecision;
  }

  public void setOahDecision(PickListValue oahDecision) {
    this.oahDecision = oahDecision;
  }

  public String getComReasons() {
    return comReasons;
  }

  public void setComReasons(String comReasons) {
    this.comReasons = comReasons;
  }

  public String getCbsCommitteeDecision() {
    return cbsCommitteeDecision;
  }

  public void setCbsCommitteeDecision(String cbsCommitteeDecision) {
    this.cbsCommitteeDecision = cbsCommitteeDecision;
  }
  
  /**
   * Check to see whether the object contains any data.
   * @return true if any object attribute is non-null, false otherwise.
   */
  public boolean hasData() {
      return decisionDate != null || oahRequestDate != null || StringUtils.isNotBlank(cbsCommitteeDecision) || 
              StringUtils.isNotBlank(comReasons) || (oahDecision != null && oahDecision.getId() != null);
  }

}
