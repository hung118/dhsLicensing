package gov.utah.dts.det.ccl.documents.reporting;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Repository;

@Repository("reportRunner")
public class ReportRunnerImpl implements ReportRunner {

	@PersistenceContext
	private EntityManager em;
	
	@Override
	public Query getNativeQuery(String sql) {
		return em.createNativeQuery(sql);
	}
	
	@Override
	public Query getQuery(String jpaql) {
		return em.createQuery(jpaql);
	}
}