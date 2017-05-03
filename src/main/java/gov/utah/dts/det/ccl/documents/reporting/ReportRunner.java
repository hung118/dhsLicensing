package gov.utah.dts.det.ccl.documents.reporting;

import javax.persistence.Query;

public interface ReportRunner {

	public Query getNativeQuery(String sql);
	
	public Query getQuery(String jpaql);
}