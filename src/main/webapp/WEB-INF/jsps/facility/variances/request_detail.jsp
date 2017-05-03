<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<fieldset>
	<legend>Variance Request</legend>
	<security:authorize access="isAuthenticated() and principal.isInternal()">
		<div class="ccl-fs-ctrls">
			<ul>
				<li><span class="label">Created By:</span> <s:property value="variance.createdBy.firstAndLastName"/></li>
				<li><span class="label">Creation Date:</span> <s:date name="variance.creationDate" format="MM/dd/yyyy hh:mm a"/></li>
				<li><span class="label">Last Modified By:</span> <s:property value="variance.modifiedBy.firstAndLastName"/></li>
				<li><span class="label">Last Modified Date:</span> <s:date name="variance.modifiedDate" format="MM/dd/yyyy hh:mm a"/></li>
			</ul>
		</div>
	</security:authorize>
	<ol class="fieldList">
		<li>
			<div class="label">Rule #:</div>
			<div class="value">R501-<s:property value="variance.rule.generatedRuleNumber"/></div>
		</li>
		<li>
			<div class="label">Request Date:</div>
			<div class="value"><s:date name="variance.requestDate" format="MM/dd/yyyy"/></div>
		</li>
		<li>
			<div class="label">Request Duration:</div>
			<ol class="fieldGroup">
				<li>
					<div class="label">Start Date:</div>
					<div class="value"><s:date name="variance.requestedStartDate" format="MM/dd/yyyy"/></div>
				</li>
				<li>
					<div class="label">End Date:</div>
					<div class="value"><s:date name="variance.requestedEndDate" format="MM/dd/yyyy"/></div>
				</li>
			</ol>
		</li>
		<li>
			<div class="label">Purpose:</div>
			<div class="value" style="width:550px;"><s:property value="variance.purpose"/></div>
		</li>
		<li>
			<div class="label">How will health and safety be insured if approved?</div>
			<div class="value" style="width:550px;"><s:property value="variance.healthSafetyInsuredBy"/></div>
		</li>
		<li>
			<div class="label">Specific client name if for a specific client:</div>
			<div class="value" style="width:550px;"><s:property value="variance.clientName"/></div>
		</li>
		<security:authorize access="hasPermission('save','variance')">
			<li class="submit">
				<s:url id="editVarUrl" action="edit-request">
					<s:param name="facilityId" value="facilityId"/>
					<s:param name="varianceId" value="varianceId"/>
				</s:url>
				<s:url id="requestEditCancelUrl" action="view-outcome">
					<s:param name="facilityId" value="facilityId"/>
					<s:param name="varianceId" value="varianceId"/>
				</s:url>
				<s:set var="cancelClass">ajaxify {target: '#requestSection'}</s:set>
				<s:set var="action">save-request</s:set>
				<s:a href="%{editVarUrl}" cssClass="ccl-button ajaxify {target: '#requestSection'}">
					Edit
				</s:a>
			</li>
		</security:authorize>
	</ol>
</fieldset>