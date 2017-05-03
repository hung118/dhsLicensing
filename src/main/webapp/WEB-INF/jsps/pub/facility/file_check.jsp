<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="ccl" uri="/WEB-INF/tags/ccl-tags.tld"%>
<s:url var="ruleUrlTmpl" action="download-file" namespace="/docs" escapeAmp="false"/>
<script id="rule-dialog-tmpl" type="text/x-jquery-tmpl">
	<object id='rule-pdf' class='embedded' width='100%' height='95%' type='application/pdf' data='{{= url}}'>You must install Adobe Reader in order to view this file.</object>
</script>
<script type="text/javascript">
	$(document).ready(function() {
		$(".ccl-rule").click(function() {
			$(".ccl-generic-dialog").empty();
			if ($(this).metadata().ruleTextDocId) {
				var data = {
					url: '<s:property value='ruleUrlTmpl' escape='false'/>?fileId=' + $(this).metadata().ruleTextDocId + '&attachment=false&attachment=false#toolbar=0&navpanes=0&view=fitH'
				}
				
				$("#rule-dialog-tmpl").tmpl(data).appendTo(".ccl-generic-dialog");
			} else {
				$("<span>No text found for rule</span>").appendTo(".ccl-generic-dialog");
			}
			
			$(".ccl-generic-dialog").dialog({
				resizable: false,
				modal: true,
				closeOnEscape: false,
				width: 800,
				height: 500,
				title: $(this).metadata().title});
			return false;
		});
	});
</script>
<p>
	The following information displays only substantiated complaint allegations and cited findings for the past 2 years.  It does not include
	any inspections completed in the last 30 days.  If you would like to check prior to the last 2 years, please contact the Child Care Licensing
	Program. Contact information can be found at <a href="http://health.utah.gov/licensing/contact.htm" target="_blank">http://health.utah.gov/licensing/contact.htm</a>.
</p>
<p class="ccl-section">
	<span class="label">Inspections -</span>
	All providers receive two on-site inspections each year.  One is scheduled in advance with the provider, and one is not.  If rule violations
	are found during an inspection, a follow-up inspection is completed to ensure the violations have been corrected.  The Department of Health
	also receives and investigates complaints of rule violations by providers.  Any findings of noncompliance resulting from a complaint
	investigation are public information and will appear on this website.
</p>
<p class="ccl-section">
	<span class="label">Types of Rule Violations -</span>
	Rule violations are classified as Level 1, 2, or 3 violations, depending on both the seriousness of harm to a child that could result from
	the violation, as well as the likelihood that harm will occur.  Level 1 findings are categorized as "cited" findings the first time they
	occur.  Level 2 and 3 findings are initially classified as "technical assistance" findings, which means that providers are given technical
	assistance and the opportunity to correct the violation.  If the provider corrects one of these Level 2 or Level 3 violations and does not
	repeat the violation, a cited finding is not issued and the technical assistance finding is not public information.  However, repeated
	instances of Level 2 or Level 3 rule violations will result in a cited finding, which will then become public information and will appear
	on this website.  If a cited finding of noncompliance is not corrected, the provider is required to pay a civil money penalty.
</p>
<s:if test="lstCtrl.results.isEmpty">
	<div class="ccl-section">There are no results to display.</div>
</s:if>
<s:else>
	<fieldset class="ccl-file-check">
		<legend>Findings</legend>
		<s:actionerror/>
		<ol class="ccl-list">
			<s:iterator value="lstCtrl.results" status="row">
				<li>
					<div class="left-column">
						<div><span class="label">Inspection Date:</span> <s:date name="inspectionDate" format="MM/dd/yyyy"/></div>
						<div><span class="label">Inspection Type(s):</span> <s:property value="inspectionTypes"/></div>
						<s:if test="#attr.complaintDate != null"><div><span class="label">Complaint Date:</span> <s:date name="complaintDate" format="MM/dd/yyyy"/></div></s:if>
						<div><span class="label">Rule: </span> <ccl:rule ruleId="ruleId" format="RNUM: RDESC"/> - <a href="#" class="ccl-rule {ruleTextDocId: <s:if test="ruleTextDocId != null"><s:property value="ruleTextDocId"/></s:if><s:else>null</s:else>, title: '<ccl:rule ruleId="ruleId" format="RNUM: RDESC"/>'}">Click here to view the rule text</a></div>
					</div>
					<div class="right-column">
						<div><span class="label">Finding Category:</span> <s:property value="findingCategory"/></div>
						<div><span class="label">Noncompliance Level:</span> <s:property value="noncomplianceLevel"/></div>
						<div><span class="label">Date Correction was Verified:</span> <s:if test="verificationType.name().equals('VERIFIED')"><s:date name="correctionDate" format="MM/dd/yyyy"/></s:if><s:elseif test="!verificationType.name().equals('VERIFICATION_PENDING')"><s:property value="verificationType.label"/></s:elseif></div>
					</div>
					<div class="clear">
						<div><span class="label">Finding:</span>
						<span class="description"><s:if test="@org.apache.commons.lang.StringUtils@isNotEmpty(declarativeStatement)"><s:property value="declarativeStatement.trim()"/></s:if><s:else>Please contact the Child Care Licensing Program  at <a href="http://health.utah.gov/licensing/contact.htm" target="_blank">http://health.utah.gov/licensing/contact.htm</a> for more information on this finding.</s:else></span></div>
					</div>
				</li>
			</s:iterator>
		</ol>
	</fieldset>
</s:else>