package gov.utah.dts.det.ccl.model;

import gov.utah.dts.det.admin.model.PickListValue;
import gov.utah.dts.det.ccl.model.enums.TransactionType;
import gov.utah.dts.det.ccl.model.view.InspectionView;
import gov.utah.dts.det.model.AbstractAuditableEntity;

import java.io.Serializable;
import java.text.NumberFormat;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Type;

@SuppressWarnings("serial")
@Entity
@Table(name = "CMP_TRANSACTION")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class CmpTransaction extends AbstractAuditableEntity<Long> implements Serializable, Comparable<CmpTransaction> {
	
	@Id
	@Column(name = "ID", unique = true, nullable = false, updatable = false)
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "CMP_TRANSACTION_SEQ")
	@SequenceGenerator(name = "CMP_TRANSACTION_SEQ", sequenceName = "CMP_TRANSACTION_SEQ")
	private Long id;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "INSPECTION_ID")
	private Inspection inspection;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "INSPECTION_ID", insertable = false, updatable = false)
	private InspectionView inspectionView;
	
	@Column(name = "TYPE")
	@Type(type = "TransactionType")
	private TransactionType type;
	
	@Column(name = "AMOUNT")
	private Double amount;
	
	@Column(name = "REASON")
	private String reason;
	
	@Column(name = "TRANSACTION_DATE")
	@Temporal(TemporalType.TIMESTAMP)
	private Date date;
		
	@Column(name = "MEMO")
	private String memo;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "FACILITY_ID")
	private Facility facility;
	
	@Column(name = "CHECK_DATE")
	@Temporal(TemporalType.TIMESTAMP)
	private Date checkDate;
	
	@Column(name = "CHECK_NUMBER")
	private String checkNumber;
	
	@Column(name = "CHECK_OWNER")
	private String checkOwner;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "FEE_ID")
	private PickListValue fee;
	
	@Column(name = "QUANTITY")
	private Integer quantity;
	
	@Column(name = "RATE")
	private Double rate;
	
	@Column(name = "APPROVAL")
	private Boolean approval;
	
	@Transient
	private String totalFee;

	public CmpTransaction() {
		
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
	
	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}
	
	@Override
	public int compareTo(CmpTransaction o) {
		int compareVal = this.date.compareTo(o.date);
		if (compareVal == 0) {
			compareVal = this.id.compareTo(o.id);
		}
		return compareVal;
	}

	public Facility getFacility() {
		return facility;
	}

	public void setFacility(Facility facility) {
		this.facility = facility;
	}

	public Date getCheckDate() {
		return checkDate;
	}

	public void setCheckDate(Date checkDate) {
		this.checkDate = checkDate;
	}

	public String getCheckNumber() {
		return checkNumber;
	}

	public void setCheckNumber(String checkNumber) {
		this.checkNumber = checkNumber;
	}

	public String getCheckOwner() {
		return checkOwner;
	}

	public void setCheckOwner(String checkOwner) {
		this.checkOwner = checkOwner;
	}

	public PickListValue getFee() {
		return fee;
	}

	public void setFee(PickListValue fee) {
		this.fee = fee;
	}

	public Integer getQuantity() {
		return quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}

	public Double getRate() {
		return rate;
	}

	public void setRate(Double rate) {
		this.rate = rate;
	}

	public Boolean getApproval() {
		return approval;
	}

	public void setApproval(Boolean approval) {
		this.approval = approval;
	}

	public String getTotalFee() {
		Double money = null;
		if (quantity == null || rate == null) {
			money = new Double(fee.getValue());
		} else {
			money = new Double(fee.getValue()) + quantity * rate;
		}
			
		NumberFormat formatCurrency = NumberFormat.getCurrencyInstance(); 
		totalFee = formatCurrency.format(money);
		return totalFee;
	}

	public void setTotalFee(String totalFee) {
		this.totalFee = totalFee;
	}

	public Inspection getInspection() {
		return inspection;
	}

	public void setInspection(Inspection inspection) {
		this.inspection = inspection;
	}

	public InspectionView getInspectionView() {
		return inspectionView;
	}

	public void setInspectionView(InspectionView inspectionView) {
		this.inspectionView = inspectionView;
	}

	public TransactionType getType() {
		return type;
	}

	public void setType(TransactionType type) {
		this.type = type;
	}

	public Double getAmount() {
		return amount;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}
}