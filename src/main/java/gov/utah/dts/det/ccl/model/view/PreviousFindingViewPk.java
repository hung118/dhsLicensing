package gov.utah.dts.det.ccl.model.view;

import gov.utah.dts.det.ccl.model.Finding;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;

@SuppressWarnings("serial")
@Embeddable
public class PreviousFindingViewPk implements Serializable {

	@ManyToOne(fetch = FetchType.LAZY)
	private Finding currentFinding;
	
	@Column(name = "PREV_FIND_ID")
	private Long previousFindingId;
	
	public PreviousFindingViewPk() {
		
	}
	
	public Finding getCurrentFinding() {
		return currentFinding;
	}
	
	public void setCurrentFinding(Finding currentFinding) {
		this.currentFinding = currentFinding;
	}
	
	public Long getPreviousFindingId() {
		return previousFindingId;
	}
	
	public void setPreviousFindingId(Long previousFindingId) {
		this.previousFindingId = previousFindingId;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((currentFinding == null) ? 0 : currentFinding.hashCode());
		result = prime * result + ((previousFindingId == null) ? 0 : previousFindingId.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		PreviousFindingViewPk other = (PreviousFindingViewPk) obj;
		if (currentFinding == null) {
			if (other.currentFinding != null)
				return false;
		} else if (!currentFinding.equals(other.currentFinding))
			return false;
		if (previousFindingId == null) {
			if (other.previousFindingId != null)
				return false;
		} else if (!previousFindingId.equals(other.previousFindingId))
			return false;
		return true;
	}
}