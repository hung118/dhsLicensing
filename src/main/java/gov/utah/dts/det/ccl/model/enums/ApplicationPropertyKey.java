package gov.utah.dts.det.ccl.model.enums;

public enum ApplicationPropertyKey {

	//messages
	WELCOME_MSG("app.welcome-msg"),
	ACCESS_DENIED_MSG("app.access-denied-msg"),
	NO_ACCOUNT_MSG("app.no-account-msg"),
	
	//urls
	LOGIN_URL("app.login-url"),
	LOGOUT_URL("app.logout-url"),
	
	//headers
	ON_NETWORK_HEADER("app.on-network-header"),
	ENVIRONMENT_HEADER("app.environment-header"),
	
	//services
	FILE_CONVERSION_URL("app.file-conversion-url"),
	EREP_USERNAME("app.erep.username"),
	EREP_PASSWORD("app.erep.password"),
	EREP_HOST("app.erep.host"),
	EREP_PORT("app.erep.port"),
	EREP_FILENAME("app.erep.filename"),
	EREP_REMOTE_PATH("app.erep.remote-path"),
	BMI_URL("app.bmi.url"),
	
	//facility
	PEOPLE_FACILITY_OWNERSHIP_TYPES("facility.ownership.people"),
	DIRECTOR_REQUIRED_TYPES("facility.license.type.director-required"),
	FACILITY_ACTIVATION_ACTION("facility.activation"),
	FACILITY_DEACTIVATION_REASON_LICENSE_EXPIRED("facility.deactivation-reason.license-expired"),
	FACILITY_DEACTIVATION_REASON_EXEMPTIONS_EXPIRED("facility.deactivation-reason.exemptions-expired"),
	TAG_CONDITIONAL("facility.tag.conditional"),
	NEW_FACILITY_FOOD_PROGRAM("facility.food-program.default"),
	NEW_FACILITY_FOOD_PROGRAM_STATUS("facility.food-program-status.default"),
	
	//facility person type
	FACILITY_OWNER_TYPE("facility.person-type.primary-owner"),
	
	//license
	UNDER_AGE_TWO_REQUIRED("facility.license.type.under-age-two-required"),
	ACTIVE_LICENSE_STATUS("facility.license.status.active"),
	INACTIVE_LICENSE_STATUS("facility.license.status.inactive"),
	CLOSED_LICENSE_STATUS("facility.license.status.closed"),
	IN_PROCESS_LICENSE_STATUS("facility.license.status.in-process"),
	NONE_LICENSE_SUBTYPE("facility.license.subtype.none"),
	DOES_NOT_CALCULATE_ALERTS_LICENSE_SUBTYPE("facility.license.subtype.does-not-calculate-alerts"),
	ADULT_LICENSE_AGEGROUP("facility.license.agegroup.adult"),
	ADULT_YOUTH_LICENSE_AGEGROUP("facility.license.agegroup.adult-youth"),
	YOUTH_LICENSE_AGEGROUP("facility.license.agegroup.youth"),
	CERTIFICATE_TYPES("facility.license.type.certificate"),
	DEFAULT_FACILITY_LICENSE_TYPE("facility.license.type."),
	
	//inspection
	COMPLAINT_INVESTIGATION_TYPE("facility.inspection.type.complaint-investigation"),
	COMPLIANCE_RESET_TYPES("facility.inspection.type.resets-compliance"),
	FOLLOW_UP_TYPES("facility.inspection.type.follow-up"),
	ANNUAL_ANNOUNCED_TYPES("facility.inspection.type.annual-announced"),
	UNVERIFIED_PROVIDER_CLOSED_FOLLOW_UP_TYPE("facility.inspection.type.unverified-provider-closed-follow-up"),
	
	//misc
	INTERPRETATION_MANUAL_SECTION_TEMPLATE("templating.interpretation-manual-section-template"),
	FILE_TYPE_RULE_FILES("file.type.rule-files"),
	FILE_TYPE_RULE_TEXT_PREVIEW("file.type.rule-text-preview"),
	FILE_TYPE_RULE_DETAILS_PREVIEW("file.type.rule-details-preview"),
	FILE_TYPE_TEMP_FILE("file.type.temp-file"),
	FILE_TYPE_DOCUMENT("file.type.document"),
	FILE_TYPE_TEMPLATE("file.type.template"),
	RULE_TEXT_TEMPLATE("templating.rule-text"),
	RULE_DETAILS_TEMPLATE("template.rule-details"),
	CENTER_NONCOMPLIANCE_TRIGGER("noncompliance.center-trigger"),
	HOME_NONCOMPLIANCE_TRIGGER("noncompliance.home-trigger"),
	SYSTEM_PERSON("app.system-user"),
	
	// rules import processing
	RULES_IMPORT_FREQUENCY("rules.import.freqency");
	
	private final String key;
	
	private ApplicationPropertyKey(String key) {
		this.key = key;
	}
	
	public String getKey() {
		return key;
	}
}