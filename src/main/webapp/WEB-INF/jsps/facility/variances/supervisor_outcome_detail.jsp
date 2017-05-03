<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<fieldset>
	<legend>Licensing Manager Variance Review Outcome</legend>
	<s:if test="variance.supervisorOutcome == null">
		<ol class="fieldList">
			<li>
				<div class="value">This variance has not been reviewed by a licensing manager.</div>
			</li>
		</ol>
	</s:if>
	<s:else>
		<security:authorize access="isAuthenticated() and principal.isInternal()">
			<div class="ccl-fs-ctrls">
				<ul>
					<li><span class="label">Last Modified By:</span> <s:property value="variance.supervisorModifiedBy.firstAndLastName"/></li>
					<li><span class="label">Last Modified Date:</span> <s:date name="variance.supervisorModifiedDate" format="MM/dd/yyyy hh:mm a"/></li>
				</ul>
			</div>
		</security:authorize>
		<ol class="fieldList">
			<li>
				<div class="label">Review Date:</div>
				<div class="value"><s:date name="variance.supervisorModifiedDate" format="MM/dd/yyyy"/></div>
			</li>
			<li>
				<div class="label">Proposed Outcome:</div>
				<div class="value"><s:property value="variance.supervisorOutcome.displayName"/></div>
			</li>
			<li>
				<div class="label">Response:</div>
				<div class="value" style="width:550px;"><s:property value="variance.supervisorResponse"/></div>
			</li>
		</ol>
	</s:else>
	<security:authorize access="hasPermission('save-manager','variance')">
		<ol class="fieldList">
			<li class="submit">
				<s:url id="editVarUrl" action="edit-supervisor-outcome">
					<s:param name="facilityId" value="facilityId"/>
					<s:param name="varianceId" value="varianceId"/>
				</s:url>
				<s:a href="%{editVarUrl}" cssClass="ccl-button ajaxify {target: '#supervisorOutcomeSection'}">
					Edit
				</s:a>
			</li>
		</ol>
	</security:authorize>
</fieldset>