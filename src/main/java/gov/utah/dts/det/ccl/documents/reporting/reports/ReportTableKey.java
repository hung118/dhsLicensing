package gov.utah.dts.det.ccl.documents.reporting.reports;

import java.io.Serializable;

@SuppressWarnings("serial")
public class ReportTableKey implements Serializable {
	
	private String row;
	private String column;
	
	public ReportTableKey(String row, String column) {
		this.row = row;
		this.column = column;
	}
	
	public String getRow() {
		return row;
	}
	
	public String getColumn() {
		return column;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((column == null) ? 0 : column.hashCode());
		result = prime * result + ((row == null) ? 0 : row.hashCode());
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
		if (!(obj instanceof ReportTableKey)) {
			return false;
		}
		ReportTableKey other = (ReportTableKey) obj;
		if (column == null) {
			if (other.column != null) {
				return false;
			}
		} else if (!column.equals(other.column)) {
			return false;
		}
		if (row == null) {
			if (other.row != null) {
				return false;
			}
		} else if (!row.equals(other.row)) {
			return false;
		}
		return true;
	}
}