<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<s:set var="complaint" value="complaint" scope="request"/>
<fieldset>
	<legend>Complaint Narrative</legend>
	<ol class="fieldList">
		<li>
			<div class="label">Narrative:</div>
			<div class="value description"><s:property value="complaint.narrative"/></div>
		</li>
		<security:authorize access="hasPermission('save-intake', 'complaint')">
			<li class="submit">
				<s:url id="editNarrUrl" action="edit-narrative">
					<s:param name="facilityId" value="facilityId"/>
					<s:param name="complaintId" value="complaintId"/>
				</s:url>
				<s:a href="%{editNarrUrl}" cssClass="ccl-button ajaxify {target: '#narrativeSection'}">
					Edit
				</s:a>
			</li>
		</security:authorize>
	</ol>
</fieldset>