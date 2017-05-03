package gov.utah.dts.det.ccl.view;

public class RuleResultView {

	private Long id;
	private String ruleNumber;
	private String description;
	private String ruleText;
	
	public RuleResultView(Long id, String ruleNumber, String description, String ruleText) {
		this.id = id;
		this.ruleNumber = ruleNumber;
		this.description = description;
		this.ruleText = ruleText;
	}

	public Long getId() {
		return id;
	}

	public String getRuleNumber() {
		return ruleNumber;
	}

	public String getDescription() {
		return description;
	}

	public String getRuleText() {
		return ruleText;
	}
}