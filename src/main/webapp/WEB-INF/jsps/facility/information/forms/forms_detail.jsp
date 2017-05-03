<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<fieldset>
	<legend>Facility Forms</legend>
	<ol class="fieldList">
		<li class="widercolumn">
			<ol>
				<li>
					<div class="label">Rules Certification Received:</div>
					<div class="value"><s:property value="facility.rulesCertReceived"/></div>
				</li>
				<li class="clear"></li>
				<li>
					<div class="label">Code of Conduct Received:</div>
					<div class="value"><s:property value="facility.codeOfConductReceived"/></div>
				</li>
				<li class="clear"></li>
				<li>
					<div class="label">Confidential Form Received:</div>
					<div class="value"><s:property value="facility.confidentialFormReceived"/></div>
				</li>
				<li class="clear"></li>
				<li>
					<div class="label">Emergency Plan Received:</div>
					<div class="value"><s:property value="facility.emergencyPlanReceived"/></div>
				</li>
				<li class="clear"></li>
				<li>
					<div class="label">Reference 1 Received:</div>
					<div class="value"><s:property value="facility.referenceOneReceived"/></div>
				</li>
				<li class="clear"></li>
				<li>
					<div class="label">Reference 2 Received:</div>
					<div class="value"><s:property value="facility.referenceTwoReceived"/></div>
				</li>
				<li class="clear"></li>
				<li>
					<div class="label">Reference 3 Received:</div>
					<div class="value"><s:property value="facility.referenceThreeReceived"/></div>
				</li>
				<li class="clear"></li>
				<security:authorize access="hasPermission('edit-details', 'facility')">
					<li class="submit">
						<s:url id="editFormsUrl" action="edit-forms">
							<s:param name="facilityId" value="facilityId"/>
						</s:url>
						<s:a href="%{editFormsUrl}" cssClass="ajaxify ccl-button {target: '#formsSection'}">
							Edit
						</s:a>
					</li>
				</security:authorize>
			</ol>
		</li>
	</ol>
</fieldset>
