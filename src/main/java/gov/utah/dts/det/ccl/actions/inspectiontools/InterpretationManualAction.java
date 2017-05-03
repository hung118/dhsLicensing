package gov.utah.dts.det.ccl.actions.inspectiontools;

import gov.utah.dts.det.ccl.model.Rule;
import gov.utah.dts.det.ccl.model.RuleSection;
import gov.utah.dts.det.ccl.service.RuleService;

import java.text.SimpleDateFormat;
import java.util.List;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;

import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.Preparable;

@SuppressWarnings("serial")
@ParentPackage("main")
@Namespace("/inspectiontools/interpretationmanual")
public class InterpretationManualAction extends ActionSupport implements Preparable {

	private RuleService ruleService;
//	private DocumentService documentService;
	
	private Rule rule;
	private RuleSection section;
	private Long fileId;
	
	private List<Rule> rules;
	private List<RuleSection> sections; 
	
	public void prepare() {
		if (rule != null && rule.getId() != null) {
			rule = ruleService.loadRuleById(rule.getId());
		}
		if (section != null && section.getId() != null) {
			section = ruleService.loadRuleSectionById(section.getId());
		}
	}
	
	@Action(value = "rules", results = {
		@Result(name = "success", location = "interpretationManualRules", type = "tiles")
	})
	public String doIndex() {
		rules = ruleService.getRules();
		
		return SUCCESS;
	}
	
	@Action(value = "sections", results = {
		@Result(name = "success", location = "interpretationManualSections", type = "tiles")
	})
	public String doShowSections() {
		if (rule == null || rule.getId() == null) {
			return null;
		}
		
		sections = ruleService.getActiveSectionsForRule(rule.getId());
		
		return SUCCESS;
	}
	
	@Action(value = "print-manual", results = {
		@Result(name = "success", type = "redirectAction", params = {"actionName", "download", "namespace", "/file", "file.id", "${fileId}"}),
		@Result(name = "error", type = "redirectAction", location = "sections", params = {"rule.id", "${rule.id}"})
	})
	public String doPrintSectionManual() {
		//find the file for the section
		SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
		String versionDate = sdf.format(section.getVersionDate());
		
//		List<Document> documents = documentService.getDocumentsByName(section.getName(), "#sectionId == " + section.getId() + " and #versionDate.equals('" + versionDate + "')");
//		if (documents.isEmpty() || documents.size() > 1) {
//			addActionError("No manual found.  Please contact the administrator to get one added.");
//			return ERROR;
//		} else {
//			fileId = documents.get(0).getFile().getId();
//		}
		
		return SUCCESS;
	}
	
	public void setRuleService(RuleService ruleService) {
		this.ruleService = ruleService;
	}
	
//	public void setDocumentService(DocumentService documentService) {
//		this.documentService = documentService;
//	}
	
	public Rule getRule() {
		return rule;
	}
	
	public void setRule(Rule rule) {
		this.rule = rule;
	}
	
	public RuleSection getSection() {
		return section;
	}
	
	public void setSection(RuleSection section) {
		this.section = section;
	}
	
	public Long getFileId() {
		return fileId;
	}
	
	public void setFileId(Long fileId) {
		this.fileId = fileId;
	}
	
	public List<Rule> getRules() {
		return rules;
	}
	
	public List<RuleSection> getSections() {
		return sections;
	}
}