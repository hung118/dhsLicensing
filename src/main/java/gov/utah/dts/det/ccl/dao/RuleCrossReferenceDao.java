package gov.utah.dts.det.ccl.dao;

import gov.utah.dts.det.ccl.model.RuleCrossReference;
import gov.utah.dts.det.dao.AbstractBaseDao;
import gov.utah.dts.det.query.SortBy;

import java.util.List;

public interface RuleCrossReferenceDao extends AbstractBaseDao<RuleCrossReference, Long> {

	public List<RuleCrossReference> searchCrossReferences(String ruleNumFilter, SortBy sortBy, int page, int resultsPerPage);
	
	public int searchCrossReferencesCount(String ruleNumFilter);
}