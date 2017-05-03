<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<tiles:insertDefinition name="tab">
	<tiles:putAttribute name="baseDivId" value="requestsBase"/>
	<tiles:putAttribute name="body">
		<div id="requestsSection">
			<s:action name="list-requests" namespace="/trackingrecordscreening/requests" executeResult="true">
				<s:param name="screeningId" value="screeningId" />
			</s:action>
		</div>
		<div id="requestsNotesSection">
			<s:set var="formSection">#requestsNotesSection</s:set>
			<s:action name="administer-notes" namespace="/trackingrecordscreening/main" executeResult="true">
				<s:param name="screeningId" value="screeningId" />
				<s:param name="formSection" value="%{formSection}"/>
				<s:param name="editable" value="true" />
			</s:action>
		</div>
	</tiles:putAttribute>
</tiles:insertDefinition>