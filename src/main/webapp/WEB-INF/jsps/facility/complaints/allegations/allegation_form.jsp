<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="ccl" uri="/WEB-INF/tags/ccl-tags.tld"%>
<s:if test="allegation != null and allegation.id != null">
	<s:set var="legendText">Edit</s:set>
</s:if>
<s:else>
	<s:set var="legendText">Create</s:set>
</s:else>
<fieldset>
	<legend><s:property value="%{legendText}"/> Allegation</legend>
	<div class="ccl-margin-bottom"><span class="label">Narrative:</span> <span class="description"><s:property value="narrative"/></span></div>
	<s:fielderror/>
	<s:form id="allegationForm" action="save-allegation" method="post" cssClass="ajaxify {target: '#allegationsContent'} ccl-action-save">
		<s:hidden name="facilityId"/>
		<s:hidden name="complaintId"/>
		<s:hidden name="allegation.id"/>
		<ol class="fieldList">
			<li>
				<label for="alleg-rule"><span class="redtext">* </span>Rule #: R501-</label>
				<s:textfield id="alleg-rule" name="allegation.rule" value="%{allegation.rule.id}" cssClass="required"/>
			</li>
			<s:if test="allegation.allegationView.substantiated != null && allegation.allegationView.substantiated">
				<li>
					<label for="supportingEvidence"><span class="redtext">* </span>Supporting Evidence:</label>
					<s:textarea id="supportingEvidence" name="allegation.supportingEvidence" cssClass="required"/>
				</li>
			</s:if>
			<li class="submit">
				<s:submit value="Save"/>
				<s:url id="allegationEditCancelUrl" action="allegations-list" includeParams="false">
					<s:param name="facilityId" value="facilityId"/>
					<s:param name="complaintId" value="complaintId"/>
				</s:url>
				<s:a id="allegationEditCancel" href="%{allegationEditCancelUrl}" cssClass="ajaxify {target: '#allegationsContent'}">
					Cancel
				</s:a>
			</li>
		</ol>
	</s:form>
	<div id="allegationsListSection">
		<s:include value="allegations_table.jsp"/>
	</div>
</fieldset>
<script type="text/javascript">
	$("#alleg-rule").ruleautocomplete({
		showRuleInfo: true
	});
</script>