package gov.utah.dts.det.ccl.view.facility;

import gov.utah.dts.det.ccl.model.CmpTransaction;
import gov.utah.dts.det.ccl.model.view.InspectionView;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.struts2.json.annotations.JSON;

@SuppressWarnings("serial")
public class CmpInspectionView implements Serializable {
	
	private Long inspectionId;
	private Date inspectionDate;
	private Date cmpDueDate;
	private Double cmpAmount;
	private Double totalReductions;
	private Double totalPayments;
	private Double outstanding;
	private List<TransactionWrapper> reductions = new ArrayList<TransactionWrapper>();
	private List<TransactionWrapper> payments = new ArrayList<TransactionWrapper>();
	
	public CmpInspectionView(InspectionView inspectionView) {
		this.inspectionId = inspectionView.getId();
		this.inspectionDate = inspectionView.getInspectionDate();
		this.cmpDueDate = inspectionView.getCmpDueDate();
		this.cmpAmount = inspectionView.getCmpAmount();
		this.totalReductions = inspectionView.getTotalReductions();
		this.totalPayments = inspectionView.getTotalPayments();
		this.outstanding = inspectionView.getOutstanding();
		for (CmpTransaction red : inspectionView.getReductions()) {
			reductions.add(new TransactionWrapper(red));
		}
		for (CmpTransaction pmt : inspectionView.getPayments()) {
			payments.add(new TransactionWrapper(pmt));
		}
	}
	
	public Long getInspectionId() {
		return inspectionId;
	}

	@JSON(format = "MM/dd/yyyy")
	public Date getInspectionDate() {
		return inspectionDate;
	}

	@JSON(format = "MM/dd/yyyy")
	public Date getCmpDueDate() {
		return cmpDueDate;
	}

	public Double getCmpAmount() {
		return cmpAmount;
	}

	public Double getTotalReductions() {
		return totalReductions;
	}

	public Double getTotalPayments() {
		return totalPayments;
	}

	public Double getOutstanding() {
		return outstanding;
	}

	public List<TransactionWrapper> getReductions() {
		return reductions;
	}

	public List<TransactionWrapper> getPayments() {
		return payments;
	}

	public static class TransactionWrapper implements Serializable {
		
		private Long transactionId;
		private Date transactionDate;
		private Double amount;
		private String comment;
		
		public TransactionWrapper(CmpTransaction cmpTransaction) {
			this.transactionId = cmpTransaction.getId();
			this.transactionDate = cmpTransaction.getDate();
			this.amount = cmpTransaction.getAmount();
			this.comment = cmpTransaction.getReason();
		}
		
		public Long getTransactionId() {
			return transactionId;
		}
		
		@JSON(format = "MM/dd/yyyy")
		public Date getTransactionDate() {
			return transactionDate;
		}
		
		public Double getAmount() {
			return amount;
		}
		
		public String getComment() {
			return comment;
		}
	}
}