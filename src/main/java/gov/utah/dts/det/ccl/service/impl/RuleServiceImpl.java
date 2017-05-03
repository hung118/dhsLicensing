package gov.utah.dts.det.ccl.service.impl;

import gov.utah.dts.det.admin.model.PickListValue;
import gov.utah.dts.det.admin.quartz.RuleFileParser;
import gov.utah.dts.det.ccl.dao.FacilityDao;
import gov.utah.dts.det.ccl.dao.InspectionDao;
import gov.utah.dts.det.ccl.dao.RuleCrossReferenceDao;
import gov.utah.dts.det.ccl.dao.RuleDao;
import gov.utah.dts.det.ccl.dao.RuleSectionDao;
import gov.utah.dts.det.ccl.dao.RuleSubSectionDao;
import gov.utah.dts.det.ccl.model.Finding;
import gov.utah.dts.det.ccl.model.FindingCategoryPickListValue;
import gov.utah.dts.det.ccl.model.Inspection;
import gov.utah.dts.det.ccl.model.Rule;
import gov.utah.dts.det.ccl.model.RuleCrossReference;
import gov.utah.dts.det.ccl.model.RuleSection;
import gov.utah.dts.det.ccl.model.RuleSubSection;
import gov.utah.dts.det.ccl.model.enums.RuleCategory;
import gov.utah.dts.det.ccl.repository.RuleSectionRepository;
import gov.utah.dts.det.ccl.service.RuleService;
import gov.utah.dts.det.ccl.view.RuleResultView;
import gov.utah.dts.det.query.SortBy;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("ruleService")
public class RuleServiceImpl implements RuleService {
	
	protected final Log log = LogFactory.getLog(getClass());

	@Autowired
	private RuleDao ruleDao;

	@Autowired
	private RuleSectionDao ruleSectionDao;

	@Autowired
	private RuleSubSectionDao ruleSubSectionDao;

	@Autowired
	private RuleSectionRepository ruleSectionRepository;

	@Autowired
	private RuleCrossReferenceDao ruleCrossReferenceDao;

	@Autowired
	private FacilityDao facilityDao;

	@Autowired
	private InspectionDao inspectionDao;

	@Override
	public Rule loadRuleById(Long id) {
		return ruleDao.load(id);
	}

	@Override
	public Rule loadRuleByIdOrdered(Long id) {
		return ruleDao.loadOrdered(id);
	}

	@Override
	public Rule saveRule(Rule rule) {
		return ruleDao.save(rule);
	}

	@Override
	public void deleteRule(Rule rule) {
		ruleDao.delete(rule);
	}

	@Override
	public List<Rule> getRules() {
		return ruleDao.getAllRules();
	}

	@Override
	public RuleSection loadRuleSectionById(Long id) {
		return ruleSectionDao.load(id);
	}

	@Override
	public RuleSection saveRuleSection(RuleSection ruleSection, Long genInfoFileId) throws IOException {
		ruleSection = ruleSectionRepository.save(ruleSection);
		return ruleSection;
	}
	
	@Override
	public RuleSection saveRuleSection(RuleSection ruleSection) {
		return ruleSectionDao.save(ruleSection);
	}

	@Override
	public void deleteRuleSection(RuleSection ruleSection) {
		ruleSectionDao.delete(ruleSection);
	}

	@Override
	public List<RuleSection> getActiveSectionsForRule(Long ruleId) {
		return ruleSectionDao.getActiveSectionsForRule(ruleId);
	}

	@Override
	public RuleSubSection loadRuleSubSectionById(Long id) {
		return ruleSubSectionDao.load(id);
	}

	@Override
	public RuleSubSection saveRuleSubSection(RuleSubSection ruleSubSection, Long textFileId, Long enforcementFileId, Long rationaleFileId, Long infoFileId)
	throws IOException {
		try {
			
			ruleSubSection = ruleSubSectionDao.save(ruleSubSection);
			
		} catch (Exception e) {
			e.printStackTrace();
			throw new IOException(e);
		}

		return ruleSubSection; 
	}
	
	@Override
	public RuleSubSection saveRuleSubSection(RuleSubSection ruleSubSection) {
		return ruleSubSectionDao.save(ruleSubSection);
	}

	@Override
	public void deleteRuleSubSection(RuleSubSection ruleSubSection) {
		ruleSubSectionDao.delete(ruleSubSection);
	}

	@Override
	public RuleResultView getRuleView(Long ruleId) {
		return ruleSubSectionDao.getRuleView(ruleId);
	}

	@Override
	public List<RuleResultView> doRuleSearch(Long inspectionId, Long findingId, String ruleQuery, boolean excludeInactive) {
		List<RuleSubSection> ruleExcludes = null;

		if (inspectionId != null) {
			Inspection inspection = inspectionDao.load(inspectionId);
			ruleExcludes = new ArrayList<RuleSubSection>();
			for (Finding find : inspection.getFindings()) {
				if (findingId == null || (findingId != null && !find.getId().equals(findingId))) {
					ruleExcludes.add(find.getRule());
				}
			}
		}

		return ruleSubSectionDao.doRuleSearch(ruleQuery, excludeInactive, ruleExcludes);
	}

	@Override
	public List<FindingCategoryPickListValue> getFindingCategoriesForRule(Long ruleId) {
		return ruleSubSectionDao.getFindingCategoriesForRule(ruleId);
	}

	@Override
	public List<PickListValue> getNoncomplianceLevelsForRule(Long ruleId) {
		return ruleSubSectionDao.getNoncomplianceLevelsForRule(ruleId);
	}

	/*
	 * @Override public void renderSection(Long sectionId) throws Exception {
	 * Long sectionDocId = new
	 * Long(applicationService.getApplicationPropertyValue
	 * (ApplicationPropertyKey
	 * .INTERPRETATION_MANUAL_SECTION_TEMPLATE.getKey()));
	 * 
	 * String filePath = getSectionPath(ruleSubSection) +
	 * fileUtils.getUniqueFileName("pdf"); File file =
	 * fileUtils.getFile(filePath);
	 * 
	 * FileOutputStream fos = new FileOutputStream(file);
	 * 
	 * Map<String, Object> context = new HashMap<String, Object>();
	 * context.put("rule", ruleSubSection);
	 * 
	 * documentService.renderDocument(dtlsDocId, context, fos);
	 * 
	 * FilesystemFile dtlFile =
	 * fileService.createFile(ruleSubSection.getGeneratedRuleNumber() +
	 * "-details", filePath, ruleDetailsFileType);
	 * ruleSubSection.setRuleDetails(dtlFile);
	 * 
	 * 
	 * 
	 * ApplicationProperty property =
	 * applicationService.findApplicationPropertyByKey
	 * (ApplicationPropertyKey.INTERPRETATION_MANUAL_SECTION_TEMPLATE.getKey());
	 * if (property != null) { JSONString templateProp = new
	 * JSONString(property.getValue()); RuleSection section =
	 * ruleSectionDao.getSectionWithSubSections(sectionId);
	 * 
	 * ByteArrayOutputStream baos = new ByteArrayOutputStream();
	 * documentRenderingService.render((String)
	 * templateProp.getValue("managerKey"), (String)
	 * templateProp.getValue("key"), baos, FileType.PDF, new HashMap<String,
	 * String>(), null, section);
	 * 
	 * //store the rendered file File file = new File();
	 * file.setName(section.getName()); file.setType(FileType.PDF); byte[]
	 * fileBytes = baos.toByteArray(); file.setBinaryData(fileBytes);
	 * file.setSize(new Long(fileBytes.length));
	 * 
	 * //create a new document Document sectionInterpret = new Document();
	 * sectionInterpret
	 * .setCategory(pickListService.getPickListValueByValue("Interpretation Manual"
	 * , "Document Category")); sectionInterpret.setFile(file);
	 * sectionInterpret.setName(section.getName()); SimpleDateFormat sdf = new
	 * SimpleDateFormat("MM/dd/yyyy");
	 * sectionInterpret.setMetadataValue("versionDate",
	 * sdf.format(section.getVersionDate()));
	 * sectionInterpret.setMetadataValue("sectionId",
	 * section.getId().toString());
	 * sectionInterpret.setMetadataValue("sectionNumber",
	 * section.getNumber().toString());
	 * 
	 * documentService.saveDocument(sectionInterpret); } }
	 */

	// @Override
	// public List<Document> getPreviousSectionRenders(Long sectionId) {
	// RuleSection section = loadRuleSectionById(sectionId);
	// return documentService.getDocumentsByName(section.getName(),
	// "#sectionId == " + sectionId);
	// }

	@Override
	public void updateRuleSubSectionDocuments(RuleSubSection ruleSubSection) throws FileNotFoundException, JSONException {

	}

	@Override
	public RuleCrossReference loadCrossReferenceById(Long id) {
		return ruleCrossReferenceDao.load(id);
	}

	@Override
	public RuleCrossReference saveCrossReference(RuleCrossReference ref) {
		return ruleCrossReferenceDao.save(ref);
	}

	@Override
	public void deleteCrossReference(RuleCrossReference ref) {
		ruleCrossReferenceDao.delete(ref);
	}

	@Override
	public List<RuleCrossReference> searchCrossReferences(String ruleNumFilter, SortBy sortBy, int page, int resultsPerPage) {
		return ruleCrossReferenceDao.searchCrossReferences(ruleNumFilter, sortBy, page, resultsPerPage);
	}

	@Override
	public int searchCrossReferencesCount(String ruleNumFilter) {
		return ruleCrossReferenceDao.searchCrossReferencesCount(ruleNumFilter);
	}

	@Override
	public void evict(Object entity) {
		ruleDao.evict(entity);
	}

	@Override
	public int[] processRuleFile(File file, String ruleNumber) throws Exception {
		int[] results = {0, 0};
		
		log.debug("file="+file+" ruleNumber="+ruleNumber);
		
		Rule rule = ruleDao.getByRuleNumber(ruleNumber);
		
		RuleFileParser rfp = new RuleFileParser();
		rfp.parse(file);

		List<RuleSection> rules = rfp.getRules();

		Iterator<RuleSection> it = rules.iterator();
		while (it.hasNext()) {
			RuleSection newSection = it.next();

			/* see if we already have this section and do a comparison, 
			 * so we're not saving multiple copies of the same thing
			 * ---
			 * NOTE: a section object can contain active and pending sub sections...
			 * this means you can have two subsections (one active and one pending)
			 * ---
			 */
			List<RuleSection> existingSections = ruleSectionDao.getSectionWithSubSections(rule.getId(), newSection.getSectionBase(), newSection.getNumber());
			
			if (existingSections != null && existingSections.size() > 0) {
					log.debug("existing sections found: count="+existingSections.size()+" for "+newSection.getNumber());		
			}
			
			/*	The 3 scenarios
			 * --------------------
			 * 1. a completely new section
			 * 2. a modified (changed title) section
			 * 3. changes to one or more sub-sections
			 */

			// we have a whole new section
			if (existingSections == null || existingSections.size() == 0){

				newSection.setRule(rule);
				newSection.setActive(true); // make sure the new section is active

				// set all the sub sections to active as well (even though their category is Pending)
				Iterator<RuleSubSection> iterator2 = newSection.getSubSections().iterator();
				while (iterator2.hasNext()) {
					RuleSubSection newSub = iterator2.next();
					newSub.setActive(true);
					results[1]++;
				}

				try {
					ruleSectionDao.save(newSection);
					log.debug("no existing section found(so we saved): "+newSection.getNumber()); 
				} catch (Exception e) {
					log.error("Error processing file ["+file.getName()+"] section ["+newSection.getNumber()+"]");
					throw e;
				}
				results[0]++;

			} else {
				
				RuleSection _sec = existingSections.get(0);
				
				// if the section is different save it as a new record (and all it's children [sub sections])
				if (_sec.isDifferent(newSection)) {
					newSection.setActive(false); // we don't want this to be active
					newSection.setRule(rule);
					newSection.setReferenceUrl(_sec.getReferenceUrl());
					ruleSectionDao.save(newSection);
					log.debug("just saved a section b/c it was different "+newSection.getNumber()); 
					results[0]++;
				} else {

					/*
					 * sub section scenarios
					 * ----------------
					 * 1. different number of sections (add or delete a section) or
					 * 2. a modified section
					 * NOTE: if we are adding a subsection we want to attach the new subsections to the existing section!!! 
					 */
					
					HashMap<String, String> newKeys = new HashMap<String, String>(); // use this later to see if a subsection has been deleted
					Iterator<RuleSubSection> iterator = newSection.getSubSections().iterator();
					while (iterator.hasNext()) {
						RuleSubSection newSub =  iterator.next();
						newKeys.put(newSub.getNumber(), newSub.getNumber());

						// see if we can find a match
						// NOTE: we need to make sure we find the most recent subsection
						RuleSubSection matchingSub = null;
						Iterator<RuleSubSection> iterator2 = _sec.getSubSections().iterator();
						while (iterator2.hasNext()) {
							RuleSubSection _sub = iterator2.next();
							if (_sub.getNumber().equals(newSub.getNumber())) {
								if (matchingSub == null) {
									matchingSub = _sub;
								} else {
									if (matchingSub.isNewer(_sub)) 
										matchingSub = _sub;
								}
							}
						}

						// add new sub (b/c there wasn't a pre-existing one)
						if (matchingSub == null) {
							newSub.setSection(_sec);
							newSub.setActive(true);
							newSub.setCategory(RuleCategory.PENDING);
							newSub.setVersionDate(new java.util.Date());
							ruleSubSectionDao.save(newSub);
							log.debug("just saved a subsection because there wasn't a matching one "+newSection.getNumber()+"-"+newSub.getNumber());
							results[1]++;
						} 
						
						// do we have a modified sub
						else if (newSub.isDifferent(matchingSub)) {
							newSub.setSection(_sec);
							newSub.setReferenceUrl(matchingSub.getReferenceUrl());
							// Note: new sub sections are set to "pending" by default so we can just save it
							ruleSubSectionDao.save(newSub);
							log.debug("just saved a subsection b/c it is different than an existing one "+newSection.getNumber()+"-"+newSub.getNumber());
							results[1]++;
						}
					}
					
					// deactivate deleted sections ...
					Iterator<RuleSubSection> iterator2 = _sec.getSubSections().iterator();
					while (iterator2.hasNext()) {
						RuleSubSection _sub = iterator2.next();
						if (!newKeys.containsKey(_sub.getNumber())) {
							_sub.setActive(false);
							_sub.setCategory(RuleCategory.INACTIVE);
							ruleSubSectionDao.save(_sub);
							log.debug("just deactivated a subsection b/c it is different than an existing one "+newSection.getNumber()+"-"+_sub.getNumber());
						}
					}
				}
			}
		}

		return results;
	}

	public List<String[]> getSectionTitles(String ruleNumber) {
		return ruleSectionDao.getActiveRuleSectionTitles(ruleNumber);
		
	}

	@Override
	public List<RuleSection> getSections(Collection<Integer> selections) {
		return ruleSectionDao.loadSections(selections);
	}

	@Override
	public void clearCache() {
		ruleDao.clearCache();	
	}
}