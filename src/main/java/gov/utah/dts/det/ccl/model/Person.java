/**
 * 
 */
package gov.utah.dts.det.ccl.model;

import gov.utah.dts.det.ccl.model.enums.Gender;
import gov.utah.dts.det.model.AbstractBaseEntity;
import gov.utah.dts.det.util.CompareUtils;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.CascadeType;
import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Embedded;
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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateUtils;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Type;

import com.opensymphony.xwork2.conversion.annotations.Conversion;
import com.opensymphony.xwork2.conversion.annotations.TypeConversion;
import com.opensymphony.xwork2.validator.annotations.ConversionErrorFieldValidator;
import com.opensymphony.xwork2.validator.annotations.RequiredStringValidator;

/**
 * @author DOLSEN
 * 
 */
@SuppressWarnings("serial")
@Entity
@Table(name = "PERSON")
@Conversion
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Person extends AbstractBaseEntity<Long> implements Serializable, Activatable, Comparable<Person> {

  @Id
  @Column(name = "ID", unique = true, nullable = false, updatable = false)
  @GeneratedValue(strategy = GenerationType.AUTO, generator = "PERSON_SEQ")
  @SequenceGenerator(name = "PERSON_SEQ", sequenceName = "PERSON_SEQ")
  private Long id;

  @Embedded
  @AttributeOverrides({ @AttributeOverride(name = "firstName", column = @Column(name = "FIRSTNAME")),
      @AttributeOverride(name = "middleName", column = @Column(name = "MIDDLENAME")),
      @AttributeOverride(name = "lastName", column = @Column(name = "LASTNAME")) })
  private PersonalName name = new PersonalName();

  @Column(name = "MAIDENNAME")
  private String maidenName;

  @Column(name = "GENDER")
  @Type(type = "Gender")
  private Gender gender;

  @OneToMany(mappedBy = "person", cascade = { CascadeType.ALL }, fetch = FetchType.LAZY, orphanRemoval = true)
  @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
  private List<TrackingRecordScreening> trsList = new ArrayList<TrackingRecordScreening>();

  @Column(name = "BIRTHDAY")
  @Temporal(TemporalType.DATE)
  private Date birthday;

  @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
  @JoinColumn(name = "ADDRESSID")
  private Address address;

  @Embedded
  @AttributeOverride(name = "phoneNumber", column = @Column(name = "WORKPHONE"))
  private Phone workPhone;

  @Embedded
  @AttributeOverride(name = "phoneNumber", column = @Column(name = "HOMEPHONE"))
  private Phone homePhone;

  @Embedded
  @AttributeOverride(name = "phoneNumber", column = @Column(name = "CELLPHONE"))
  private Phone cellPhone;

  @Embedded
  @AttributeOverride(name = "phoneNumber", column = @Column(name = "ALTERNATEPHONE"))
  private Phone alternatePhone;

  @Column(name = "EMAIL")
  private String email;

  @ElementCollection(fetch = FetchType.LAZY)
  @CollectionTable(name = "PERSON_ALIAS", joinColumns = @JoinColumn(name = "PERSON_ID"))
  @Column(name = "ALIAS")
  private Set<String> aliases = new HashSet<String>();

  @Transient
  private transient Integer age;

  public Person() {

  }

  public Person(Long id, String firstName, String lastName) {
    this.id = id;
    this.name.setFirstName(firstName);
    this.name.setLastName(lastName);
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

  @RequiredStringValidator(message = "First name is required.")
  public String getFirstName() {
    return name.getFirstName();
  }

  public void setFirstName(String firstName) {
    name.setFirstName(firstName);
  }

  public String getMiddleName() {
    return name.getMiddleName();
  }

  public void setMiddleName(String middleName) {
    name.setMiddleName(middleName);
  }

  @RequiredStringValidator(message = "Last name is required.")
  public String getLastName() {
    return name.getLastName();
  }

  public void setLastName(String lastName) {
    name.setLastName(lastName);
  }

  public String getMaidenName() {
    return maidenName;
  }

  public void setMaidenName(String maidenName) {
    this.maidenName = maidenName;
  }

  @TypeConversion(converter = "gov.utah.dts.det.ccl.view.converter.EnumConverter")
  public Gender getGender() {
    return gender;
  }

  public void setGender(Gender gender) {
    this.gender = gender;
  }

  @ConversionErrorFieldValidator(message = "Birthday is not a valid date. (MM/DD/YYYY)", shortCircuit = true)
  public Date getBirthday() {
    return birthday;
  }

  public void setBirthday(Date birthday) {
    this.birthday = birthday;
    age = null;
  }

  public Address getAddress() {
    return address;
  }

  public void setAddress(Address address) {
    this.address = address;
  }

  @ConversionErrorFieldValidator(message = "Work phone is not a valid phone number. ((###) ### - ####)", shortCircuit = true)
  public Phone getWorkPhone() {
    return workPhone;
  }

  public void setWorkPhone(Phone workPhone) {
    this.workPhone = workPhone;
  }

  @ConversionErrorFieldValidator(message = "Home phone is not a valid phone number. ((###) ### - ####)", shortCircuit = true)
  public Phone getHomePhone() {
    return homePhone;
  }

  public void setHomePhone(Phone homePhone) {
    this.homePhone = homePhone;
  }

  @ConversionErrorFieldValidator(message = "Primary phone is not a valid phone number. ((###) ### - ####)", shortCircuit = true)
  public Phone getPrimaryPhone() {
    return homePhone;
  }

  public void setPrimaryPhone(Phone primaryPhone) {
    this.homePhone = primaryPhone;
  }

  @ConversionErrorFieldValidator(message = "Cell phone is not a valid phone number. ((###) ### - ####)", shortCircuit = true)
  public Phone getCellPhone() {
    return cellPhone;
  }

  public void setCellPhone(Phone cellPhone) {
    this.cellPhone = cellPhone;
  }

  @ConversionErrorFieldValidator(message = "Alternate phone is not a valid phone number. ((###) ### - ####)", shortCircuit = true)
  public Phone getAlternatePhone() {
    return alternatePhone;
  }

  public void setAlternatePhone(Phone alternatePhone) {
    this.alternatePhone = alternatePhone;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public Set<String> getAliases() {
    return aliases;
  }

  public void setAliases(Set<String> aliases) {
    this.aliases = aliases;
  }

  
  public List<TrackingRecordScreening> getTrsList() {
	return trsList;
  }

  public void setTrsList(List<TrackingRecordScreening> trsList) {
	this.trsList = trsList;
  }

  public String getFirstAndLastName() {
    return name.getFirstAndLastName();
  }

  public String getFullName() {
    return name.getFullName();
  }
  
  public String getIntials() {
	  return name.getInitials();
  }

  public Integer getAge() {
    if (age == null && birthday != null) {
      Calendar bdayCal = DateUtils.toCalendar(birthday);
      Calendar now = Calendar.getInstance();
      // find the difference in years between now and the birthday
      int yearDifference = now.get(Calendar.YEAR) - bdayCal.get(Calendar.YEAR);
      // roll the year of the birthday up that amount of years
      bdayCal.roll(Calendar.YEAR, yearDifference);
      // now compare the dates
      if (bdayCal.after(now)) {
        age = yearDifference - 1;
      } else {
        age = yearDifference;
      }
    }
    return age;
  }

  public void addAlias(String alias) {
    aliases.add(alias);
  }

  public void removeAlias(String alias) {
    aliases.remove(alias);
  }

  public void setAliasesString(String aliasesString) {
    aliases = new HashSet<String>();
    if (StringUtils.isNotBlank(aliasesString)) {
      for (String alias : aliasesString.split(",")) {
        aliases.add(alias.trim());
      }
    }
  }

  public String getAliasesString() {
    StringBuilder sb = new StringBuilder();
    for (Iterator<String> itr = getAliases().iterator(); itr.hasNext();) {
      String alias = itr.next();
      sb.append(alias);
      if (itr.hasNext()) {
        sb.append(", ");
      }
    }
    return sb.toString();
  }

  @Override
  public String toString() {
    return getFirstAndLastName();
  }

  @Override
  public int compareTo(Person o) {
    if (this == o) {
      return 0;
    }
    int comp = CompareUtils.nullSafeComparableCompare(name, o.name, false);
    if (comp == 0) {
      comp = CompareUtils.nullSafeComparableCompare(getBirthday(), o.getBirthday(), false);
    }
    if (comp == 0) {
      comp = CompareUtils.nullSafeComparableCompare(getGender(), o.getGender(), false);
    }
    if (comp == 0) {
      comp = CompareUtils.nullSafeComparableCompare(getId(), o.getId(), false);
    }
    return comp;
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((getBirthday() == null) ? 0 : getBirthday().hashCode());
    result = prime * result + ((getGender() == null) ? 0 : getGender().hashCode());
    result = prime * result + ((name == null) ? 0 : name.hashCode());
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
    if (!(obj instanceof Person)) {
      return false;
    }
    Person other = (Person) obj;
    if (getId() == null) {
      if (other.getId() != null) {
        return false;
      }
    } else if (!getId().equals(other.getId())) {
      return false;
    }
    if (getBirthday() == null) {
      if (other.getBirthday() != null) {
        return false;
      }
    } else if (!getBirthday().equals(other.getBirthday())) {
      return false;
    }
    if (getGender() == null) {
      if (other.getGender() != null) {
        return false;
      }
    } else if (!getGender().equals(other.getGender())) {
      return false;
    }
    if (name == null) {
      if (other.name != null) {
        return false;
      }
    } else if (!name.equals(other.name)) {
      return false;
    }
    return true;
  }

  @Override
  public boolean isActive() {
    return true;
  }
}