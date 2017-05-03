package gov.utah.dts.det.ccl.model;

import gov.utah.dts.det.ccl.model.enums.ScreeningLetterType;
import gov.utah.dts.det.model.AbstractAuditableEntity;
import gov.utah.dts.det.util.EntityUtil;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
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
import javax.persistence.OneToOne;
import javax.persistence.OrderBy;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import org.apache.commons.lang.StringUtils;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * 
 * @author jtorres
 * 
 */

@SuppressWarnings("serial")
@Entity
@Table(name = "TRACKING_RECORD_SCREENING")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class TrackingRecordScreening extends AbstractAuditableEntity<Long> implements Serializable {

  @Id
  @Column(name = "ID", unique = true, nullable = false, updatable = false)
  @GeneratedValue(strategy = GenerationType.AUTO, generator = "TRS_SEQ")
  @SequenceGenerator(name = "TRS_SEQ", sequenceName = "TRS_SEQ")
  private Long id;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "FACILITY_ID")
  private Facility facility;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "PERSON_ID")
  private Person person;

  @Column(name = "SSN_LAST_FOUR")
  private String ssnLastFour;

  @Column(name = "FIRSTNAME")
  private String firstName;
  
  @Column(name = "LASTNAME")
  private String lastName;
  
  @Column(name = "BIRTHDATE")
  @Temporal(TemporalType.DATE)
  private Date birthday;

  @Column(name = "ADULT_IN_HOME")
  private Boolean adultInHome;

  @Column(name = "CPF")
  private Boolean cpf;

  @Column(name = "CLOSED")
  private Boolean closed;

  @Column(name = "AW")
  private Boolean aw;

  @Column(name = "RELATIVE")
  private Boolean relative;

  @Column(name = "NSC")
  private Boolean nsc;

  @Column(name = "DSPD_SAS")
  private Boolean dspdSas;

  @Column(name = "EXPEDITED")
  private Boolean expedited;

  @Column(name = "PERSON_ALIASES")
  private String personAliases;
  
  @Transient
  private String personIdentifier;

  @OneToOne(orphanRemoval = true, fetch = FetchType.EAGER, cascade = CascadeType.ALL)
  @PrimaryKeyJoinColumn
  private TrackingRecordScreeningMain trsMain;

  @OneToOne(orphanRemoval = true, fetch = FetchType.EAGER, cascade = CascadeType.ALL)
  @PrimaryKeyJoinColumn
  private TrackingRecordScreeningDpsFbi trsDpsFbi;

  @OneToMany(mappedBy = "trackingRecordScreening", cascade = { CascadeType.ALL }, fetch = FetchType.LAZY, orphanRemoval = true)
  @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
  @OrderBy("receivedDate DESC")
  private List<TrackingRecordScreeningRequests> requestsList = new ArrayList<TrackingRecordScreeningRequests>();

  @OneToMany(mappedBy = "trackingRecordScreening", cascade = { CascadeType.ALL }, fetch = FetchType.LAZY, orphanRemoval = true)
  @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
  @OrderBy("letterDate DESC")
  private List<TrackingRecordScreeningLetter> lettersList = new ArrayList<TrackingRecordScreeningLetter>();

  @OneToMany(mappedBy = "trackingRecordScreening", cascade = { CascadeType.ALL }, fetch = FetchType.LAZY, orphanRemoval = true)
  @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
  @OrderBy("dueDate DESC")
  private List<TrackingRecordScreeningLtr15> ltr15List = new ArrayList<TrackingRecordScreeningLtr15>();

  @OneToOne(orphanRemoval = true, fetch = FetchType.EAGER, cascade = CascadeType.ALL)
  @PrimaryKeyJoinColumn
  private TrackingRecordScreeningCbsComm cbsComm;

  @OneToMany(mappedBy = "trackingRecordScreening", cascade = { CascadeType.ALL }, fetch = FetchType.LAZY, orphanRemoval = true)
  @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
  @OrderBy("convictionDate DESC")
  private List<TrackingRecordScreeningConviction> convictionList = new ArrayList<TrackingRecordScreeningConviction>();
  
  @OneToMany(mappedBy = "trackingRecordScreening", cascade = { CascadeType.ALL }, fetch = FetchType.LAZY, orphanRemoval = true)
  @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
  @OrderBy("letterDate DESC")
  private List<TrackingRecordScreeningConvictionLetter> convictionLettersList = new ArrayList<TrackingRecordScreeningConvictionLetter>();

  @OneToOne(orphanRemoval = true, fetch = FetchType.EAGER, cascade = CascadeType.ALL)
  @PrimaryKeyJoinColumn
  private TrackingRecordScreeningMisComm misComm;

  @OneToMany(mappedBy = "trackingRecordScreening", cascade = { CascadeType.ALL }, fetch = FetchType.LAZY, orphanRemoval = true)
  @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
  @OrderBy("caseDate DESC")
  private List<TrackingRecordScreeningCase> caseList = new ArrayList<TrackingRecordScreeningCase>();

  @OneToMany(mappedBy = "trackingRecordScreening", cascade = { CascadeType.ALL }, fetch = FetchType.LAZY, orphanRemoval = true)
  @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
  @OrderBy("oscarDate DESC")
  private List<TrackingRecordScreeningOscar> oscarList = new ArrayList<TrackingRecordScreeningOscar>();

  @OneToMany(mappedBy = "trackingRecordScreening", cascade = { CascadeType.ALL }, fetch = FetchType.LAZY, orphanRemoval = true)
  @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
  private List<TrackingRecordScreeningActivity> activityList = new ArrayList<TrackingRecordScreeningActivity>();

  @Transient
  private String firstAndLastName;
  
  public void addTRSRequests(TrackingRecordScreeningRequests trackingRecordScreeningRequests) {
    EntityUtil.initialize(requestsList);
    if (trackingRecordScreeningRequests != null) {
      trackingRecordScreeningRequests.setTrackingRecordScreening(this);
      requestsList.add(trackingRecordScreeningRequests);
    }
  }

  public TrackingRecordScreeningRequests getTRSRequests(Long id) {
    if (id != null) {
      for (TrackingRecordScreeningRequests item : requestsList) {
        if (item.getId().equals(id)) {
          return item;
        }
      }
    }

    return null;
  }

  public void addTRSLetters(TrackingRecordScreeningLetter trackingRecordScreeningLetter) {
    EntityUtil.initialize(lettersList);
    if (trackingRecordScreeningLetter != null) {
      trackingRecordScreeningLetter.setTrackingRecordScreening(this);
      lettersList.add(trackingRecordScreeningLetter);
    }
  }

  public TrackingRecordScreeningLetter getTRSLetters(Long id) {
    if (id != null) {
      for (TrackingRecordScreeningLetter item : lettersList) {
        if (item.getId().equals(id)) {
          return item;
        }
      }
    }

    return null;
  }

  public void addTRSLtr15(TrackingRecordScreeningLtr15 ltr15) {
    EntityUtil.initialize(ltr15List);
    if (ltr15 != null) {
      ltr15.setTrackingRecordScreening(this);
      ltr15List.add(ltr15);
    }
  }

  public TrackingRecordScreeningLtr15 getTRSLtr15(Long id) {
    if (id != null) {
      for (TrackingRecordScreeningLtr15 item : ltr15List) {
        if (item.getId().equals(id)) {
          return item;
        }
      }
    }

    return null;
  }

  public void addTRSOscar(TrackingRecordScreeningOscar trackingRecordScreeningOscar) {
    EntityUtil.initialize(oscarList);
    if (trackingRecordScreeningOscar != null) {
      trackingRecordScreeningOscar.setTrackingRecordScreening(this);
      oscarList.add(trackingRecordScreeningOscar);
    }
  }

  public TrackingRecordScreeningOscar getTRSOscar(Long id) {
    if (id != null) {
      for (TrackingRecordScreeningOscar item : oscarList) {
        if (item.getId().equals(id)) {
          return item;
        }
      }
    }

    return null;
  }

  public void addTRSActivity(TrackingRecordScreeningActivity trackingRecordScreeningActivity) {
    EntityUtil.initialize(activityList);
    if (trackingRecordScreeningActivity != null) {
      trackingRecordScreeningActivity.setTrackingRecordScreening(this);
      activityList.add(trackingRecordScreeningActivity);
    }
  }

  public TrackingRecordScreeningActivity getTRSActivity(Long id) {
    if (id != null) {
      for (TrackingRecordScreeningActivity item : activityList) {
        if (item.getId().equals(id)) {
          return item;
        }
      }
    }

    return null;
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

  public List<TrackingRecordScreeningRequests> getRequestsList() {
    return requestsList;
  }

  public void setRequestsList(List<TrackingRecordScreeningRequests> requestsList) {
    this.requestsList = requestsList;
  }

  public List<TrackingRecordScreeningLetter> getLettersList() {
    return lettersList;
  }

  public void setLettersList(List<TrackingRecordScreeningLetter> letters) {
    this.lettersList = letters;
  }
  
  public boolean hasNAALetter() {
      boolean hasLetter = Boolean.FALSE;
      if (getLettersList() != null && getLettersList().size() > 0) {
          for (int i=getLettersList().size() - 1; i > -1 && !hasLetter; i -= 1) {
              TrackingRecordScreeningLetter letter = getLettersList().get(i);
              if (letter.getLetterType() != null && letter.getLetterType().equals(ScreeningLetterType.NAA)) {
                  hasLetter = Boolean.TRUE;
              }
          }
      }
      return hasLetter;
  }

  public List<TrackingRecordScreeningLtr15> getLtr15List() {
    return ltr15List;
  }

  public void setLtr15List(List<TrackingRecordScreeningLtr15> ltr15List) {
    this.ltr15List = ltr15List;
  }

  public TrackingRecordScreeningCbsComm getCbsComm() {
    return cbsComm;
  }

  public void setCbsComm(TrackingRecordScreeningCbsComm cbsComm) {
    this.cbsComm = cbsComm;
  }

  public List<TrackingRecordScreeningConviction> getConvictionList() {
    return convictionList;
  }

  public void setConvictionList(List<TrackingRecordScreeningConviction> convictionList) {
    this.convictionList = convictionList;
  }

  public List<TrackingRecordScreeningConvictionLetter> getConvictionLettersList() {
    return convictionLettersList;
  }

  public void setConvictionLettersList(List<TrackingRecordScreeningConvictionLetter> convictionLettersList) {
    this.convictionLettersList = convictionLettersList;
  }

  public TrackingRecordScreeningMisComm getMisComm() {
    return misComm;
  }

  public void setMisComm(TrackingRecordScreeningMisComm misComm) {
    this.misComm = misComm;
  }

  public List<TrackingRecordScreeningCase> getCaseList() {
    return caseList;
  }

  public void setCaseList(List<TrackingRecordScreeningCase> caseList) {
    this.caseList = caseList;
  }

  public List<TrackingRecordScreeningOscar> getOscarList() {
    return oscarList;
  }

  public void setOscarList(List<TrackingRecordScreeningOscar> oscarList) {
    this.oscarList = oscarList;
  }

  public List<TrackingRecordScreeningActivity> getActivityList() {
    return activityList;
  }

  public void setActivityList(List<TrackingRecordScreeningActivity> activityList) {
    this.activityList = activityList;
  }

  public Date getBirthday() {
 	return birthday;
  }

  public void setBirthday(Date birthday) {
	this.birthday = birthday;
  }
  
  public String getFirstName() {
    return firstName;
  }

  public void setFirstName(String firstName) {
    this.firstName = firstName;
  }

  public String getLastName() {
    return lastName;
  }

  public void setLastName(String lastName) {
    this.lastName = lastName;
  }

  public String getPersonAliases() {
    return personAliases;
  }

  public void setPersonAliases(String personAliases) {
    this.personAliases = personAliases;
  }

  public Boolean getAdultInHome() {
    return adultInHome;
  }

  public void setAdultInHome(Boolean adultInHome) {
    this.adultInHome = adultInHome;
  }

  public Boolean getCpf() {
    return cpf;
  }

  public void setCpf(Boolean cpf) {
    this.cpf = cpf;
  }

  public Boolean getAw() {
    return aw;
  }

  public void setAw(Boolean aw) {
    this.aw = aw;
  }

  public Boolean getRelative() {
    return relative;
  }

  public void setRelative(Boolean relative) {
    this.relative = relative;
  }

  public Boolean getNsc() {
    return nsc;
  }

  public void setNsc(Boolean nsc) {
    this.nsc = nsc;
  }

  public Boolean getDspdSas() {
    return dspdSas;
  }

  public void setDspdSas(Boolean dspdSas) {
    this.dspdSas = dspdSas;
  }

  public Boolean getExpedited() {
    return expedited;
  }

  public void setExpedited(Boolean expedited) {
    this.expedited = expedited;
  }

  public Facility getFacility() {
    return facility;
  }

  public void setFacility(Facility facility) {
    this.facility = facility;
  }

  public Person getPerson() {
    return person;
  }

  public void setPerson(Person person) {
    this.person = person;
  }

  public String getSsnLastFour() {
    return ssnLastFour;
  }

  public void setSsnLastFour(String ssnLastFour) {
    this.ssnLastFour = ssnLastFour;
  }

  public String getPersonIdentifier() {
	if (personIdentifier == null) {
		StringBuilder sb = new StringBuilder();
		sb.append(StringUtils.isNotBlank(firstName.trim()) ? firstName.trim().substring(0, 1) : "");
        sb.append(StringUtils.isNotBlank(lastName.trim()) ? lastName.trim().substring(0, 1) : "");
		sb.append("-");
		if (StringUtils.isNotBlank(ssnLastFour)) {
			sb.append(ssnLastFour);
		}
		personIdentifier = sb.toString();
	}
    return personIdentifier;
  }

  public String getFirstAndLastName() {
      if (firstAndLastName == null) {
          StringBuilder sb = new StringBuilder(firstName == null ? "" : firstName);
          if (lastName != null) {
              sb.append(" ");
          }
          sb.append(lastName == null ? "" : lastName);
          firstAndLastName = sb.toString();
      }
      return firstAndLastName;
  }

  public TrackingRecordScreeningMain getTrsMain() {
    return trsMain;
  }

  public void setTrsMain(TrackingRecordScreeningMain trsMain) {
    this.trsMain = trsMain;
  }

  public TrackingRecordScreeningDpsFbi getTrsDpsFbi() {
    return trsDpsFbi;
  }

  public void setTrsDpsFbi(TrackingRecordScreeningDpsFbi trsDpsFbi) {
    this.trsDpsFbi = trsDpsFbi;
  }

  public Boolean getClosed() {
    return closed;
  }

  public void setClosed(Boolean closed) {
    this.closed = closed;
  }
  
}
