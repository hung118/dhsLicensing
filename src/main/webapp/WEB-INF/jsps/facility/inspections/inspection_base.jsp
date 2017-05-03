<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<s:url id="refreshUrl" action="edit-inspection-record" includeParams="false" escapeAmp="false">
	<s:param name="facilityId" value="facilityId"/>
	<s:param name="inspectionId" value="inspection.id"/>
</s:url>
<script type="text/javascript">
	$("body").data("inspection.refreshUrl", "<s:property value='refreshUrl' escapeHtml='false'/>");
	$("body").data("inspection.id", "<s:property value='inspectionId'/>");
</script>
<input type="hidden" class="print-ctx-data" name="docctx" value="doc-ctx.inspection"/>
<s:hidden cssClass="print-ctx-attr" name="ds.inspection" value="%{inspection.id}"/>
<div id="inspectionSection">
	<s:action name="view-inspection" executeResult="true"/>
</div>
<div id="followUpSection">
	<s:action name="follow-up-section" executeResult="true"/>
</div>
<div id="findingsSection">
	<s:action name="findings-section" executeResult="true"/>
</div>
<div id="complaintsSection">
	<s:action name="complaints-list" executeResult="true"/>
</div>
<div id="INSPECTION_NotesSection">
	<s:action name="notes-list" namespace="/note" executeResult="true">
		<s:param name="objectId" value="inspectionId"/>
		<s:param name="noteType" value="%{'INSPECTION'}"/>
		<s:param name="disableRange" value="true"/>
	</s:action>
</div>