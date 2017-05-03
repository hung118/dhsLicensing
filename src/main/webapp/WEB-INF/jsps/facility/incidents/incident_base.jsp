<%@ taglib prefix="s" uri="/struts-tags"%>
<s:url id="refreshUrl" action="edit-incident" includeParams="false" escapeAmp="false">
	<s:param name="facilityId" value="facilityId"/>
	<s:param name="incidentId" value="incidentId"/>
</s:url>
<script type="text/javascript">
	$("body").data("incident.refreshUrl", "<s:property value='refreshUrl' escapeHtml='false'/>");
	$("body").data("incident.id", "<s:property value='incidentId'/>");
</script>
<input type="hidden" class="print-ctx-data" name="docctx" value="doc-ctx.incident"/>
<s:hidden cssClass="print-ctx-attr" name="ds.incident" value="%{incidentId}"/>
<div id="incidentSection">
	<s:action name="view-incident-info" executeResult="true"/>
</div>
<div id="INCIDENT_AND_INJURY_NotesSection">
	<s:action name="notes-list" namespace="/note" executeResult="true">
		<s:param name="objectId" value="incidentId"/>
		<s:param name="noteType" value="%{'INCIDENT_AND_INJURY'}"/>
		<s:param name="disableRange" value="true"/>
	</s:action>
</div>