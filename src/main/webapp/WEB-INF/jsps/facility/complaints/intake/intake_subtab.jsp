<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<s:url id="refreshUrl" action="tab" includeParams="false" escapeAmp="false">
	<s:param name="facilityId" value="facilityId"/>
	<s:param name="complaintId" value="complaintId"/>
</s:url>
<script type="text/javascript">
	$("body").data("complaint.refreshUrl", "<s:property value='refreshUrl' escapeHtml='false'/>");
	$("body").data("complaint.id", "<s:property value='complaintId'/>");
</script>
<s:include value="../navigation.jsp"><s:param name="selectedTab">intake</s:param></s:include>
<div id="complaintInformationSection">
	<s:action name="information-section" executeResult="true"/>
</div>
<div id="narrativeSection">
	<s:action name="view-narrative" executeResult="true"/>
</div>
<s:if test="!unlicensed">
	<div id="peopleInvolvedSection">
		<s:action name="people-involved-list" executeResult="true"/>
	</div>
</s:if>

<div id="complainantSection">
	<s:action name="view-complainant" executeResult="true"/>
</div>
<s:set var="complaint" value="complaint" scope="request"/>
<security:authorize access="hasPermission('complete-intake', 'complaint')">
	<s:set var="intButtonClass">ccl-button ccl-action-save ccl-state-change {title: 'Finalize', onSetup: function() {setupComplaintIntakeNorm();}, onSuccess: function() {refreshComplaint();}}</s:set>
	<security:authorize access="hasAnyRole('ROLE_LICENSOR_SPECIALIST')">
		<s:set var="intButtonClass">ccl-button ccl-action-save ccl-state-change {title: 'Finalize', onSuccess: function() {refreshComplaint();}}</s:set>
	</security:authorize>
	<div id="completeIntakeSection" class="tabControlSection">
		<s:url id="intakeCompleteUrl" action="complete-intake">
			<s:param name="facilityId" value="facilityId"/>
			<s:param name="complaintId" value="complaintId"/>
		</s:url>
		<s:a id="compl-complete-intake" href="%{intakeCompleteUrl}" cssClass="%{intButtonClass}">
			<strong>Submit Complaint</strong>
		</s:a>
	</div>
</security:authorize>