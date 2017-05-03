package gov.utah.dts.det.ccl.service;

import gov.utah.dts.det.admin.model.PickListValue;
import gov.utah.dts.det.ccl.model.FindingCategoryPickListValue;
import gov.utah.dts.det.ccl.model.Rule;
import gov.utah.dts.det.ccl.model.RuleCrossReference;
import gov.utah.dts.det.ccl.model.RuleSection;
import gov.utah.dts.det.ccl.model.RuleSubSection;
import gov.utah.dts.det.ccl.view.RuleResultView;
import gov.utah.dts.det.query.SortBy;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Collection;
import java.util.List;

import org.json.JSONException;

public interface RuleService {
	
	public Rule loadRuleById(Long id);
	public Rule loadRuleByIdOrdered(Long id);
	
	public Rule saveRule(Rule rule);
	
	public void deleteRule(Rule rule);
	
	public List<Rule> getRules();
	
	public RuleSection loadRuleSectionById(Long id);
	
	public RuleSection saveRuleSection(RuleSection ruleSection, Long genInfoFileId) throws IOException;

	public RuleSection saveRuleSection(RuleSection ruleSection);
	
	public void deleteRuleSection(RuleSection ruleSection);
	
	public List<RuleSection> getActiveSectionsForRule(Long ruleId);
	
	public RuleSubSection loadRuleSubSectionById(Long id);
	
	public RuleSubSection saveRuleSubSection(RuleSubSection ruleSubSection, Long textFileId, Long enforcementFileId, Long rationaleFileId, Long infoFileId)
			throws IOException;
	
	public RuleSubSection saveRuleSubSection(RuleSubSection ruleSubSection);
	
	public void deleteRuleSubSection(RuleSubSection ruleSubSection);
	
	public RuleResultView getRuleView(Long ruleId);

	public List<RuleResultView> doRuleSearch(Long inspectionId, Long findingId, String ruleQuery, boolean excludeInactive);
	
	public List<FindingCategoryPickListValue> getFindingCategoriesForRule(Long ruleId);
	
	public List<PickListValue> getNoncomplianceLevelsForRule(Long ruleId);
	
	public void updateRuleSubSectionDocuments(RuleSubSection ruleSubSection) throws FileNotFoundException, JSONException;
	
	public RuleCrossReference loadCrossReferenceById(Long id);
	
	public RuleCrossReference saveCrossReference(RuleCrossReference ref);
	
	public void deleteCrossReference(RuleCrossReference ref);
	
	public List<RuleCrossReference> searchCrossReferences(String ruleNumFilter, SortBy sortBy, int page, int resultsPerPage);
	
	public int searchCrossReferencesCount(String ruleNumFilter);
	
	public void evict(final Object entity);
	
	public int[] processRuleFile(File filePath, String ruleNumber) throws Exception;
	
	/**
	 * fetches the section title and base section number
	 * @return position: 0 = Title, 1 = section number
	 */
	public List<String[]> getSectionTitles(String ruleNumber);
	
	public List<RuleSection> getSections(Collection<Integer> selections);
	
	public void clearCache();
}