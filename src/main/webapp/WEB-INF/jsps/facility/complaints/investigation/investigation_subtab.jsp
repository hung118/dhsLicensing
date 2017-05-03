<%@ taglib prefix="s" uri="/struts-tags"%>
<s:url id="refreshUrl" action="tab" includeParams="false" escapeAmp="false">
	<s:param name="facilityId" value="facilityId"/>
	<s:param name="complaintId" value="complaintId"/>
</s:url>
<script type="text/javascript">
	$("body").data("complaint.refreshUrl", "<s:property value='refreshUrl' escapeHtml='false'/>");
	$("body").data("complaint.id", "<s:property value='complaintId'/>");
</script>
<s:include value="../navigation.jsp"><s:param name="selectedTab">investigation</s:param></s:include>
<s:if test="!unlicensed">
	<fieldset>
		<legend>Complaint Inspection Visits</legend>
		<div id="inspectionsSection">
			<s:action name="inspections-list" executeResult="true"/>
		</div>
	</fieldset>
</s:if>
<s:else>
	<fieldset>
		<legend>Investigation and Follow-Up</legend>
		<div id="investigationSection">
			<s:action name="investigation-section" executeResult="true"/>
		</div>
	</fieldset>
</s:else>