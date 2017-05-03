package gov.utah.dts.det.ccl.model;

import gov.utah.dts.det.ccl.model.enums.RoleType;
import gov.utah.dts.det.model.AbstractBaseEntity;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Cacheable;
import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

@SuppressWarnings("serial")
@Entity
@Table(name = "REPORT")
@Cacheable
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class ReportConfig extends AbstractBaseEntity<String> implements Serializable {

	@Id
	@Column(name = "REPORT_KEY", unique = true, nullable = false)
	private String reportKey;
	
	@Column(name = "REPORT_NAME")
	private String reportName;
	
	@Column(name = "DESCRIPTION")
	private String description;
	
	@ElementCollection(fetch = FetchType.LAZY)
	@CollectionTable(name = "REPORT_ACCESS", joinColumns = @JoinColumn(name = "REPORT_KEY"))
	@Column(name = "ROLE_NAME")
	@Enumerated(EnumType.STRING)
	private Set<RoleType> roles = new HashSet<RoleType>();
	
	public ReportConfig() {
		
	}
	
	@Override
	public String getPk() {
		return reportKey;
	}
	
	@Override
	public void setPk(String pk) {
		this.reportKey = pk;
	}
	
	public String getReportKey() {
		return reportKey;
	}
	
	public void setReportKey(String reportKey) {
		this.reportKey = reportKey;
	}
	
	public String getReportName() {
		return reportName;
	}
	
	public void setReportName(String reportName) {
		this.reportName = reportName;
	}
	
	public String getDescription() {
		return description;
	}
	
	public void setDescription(String description) {
		this.description = description;
	}
	
	public Set<RoleType> getRoles() {
		return roles;
	}
	
	public void setRoles(Set<RoleType> roles) {
		this.roles = roles;
	}
}