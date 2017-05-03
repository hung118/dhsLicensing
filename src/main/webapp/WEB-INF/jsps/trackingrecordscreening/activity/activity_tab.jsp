<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<tiles:insertDefinition name="tab">
	<tiles:putAttribute name="baseDivId" value="activityBase" />
	<tiles:putAttribute name="body">
		<div id="activitySection">
			<s:action name="activity-list" namespace="/trackingrecordscreening/activity" executeResult="true">
				<s:param name="screeningId" value="screeningId" />
			</s:action>
		</div>
		<div id="activityNotesSection">
			<s:set var="formSection">#activityNotesSection</s:set>
			<s:action name="administer-notes" namespace="/trackingrecordscreening/main" executeResult="true">
				<s:param name="screeningId" value="screeningId" />
				<s:param name="formSection" value="%{formSection}"/>
				<s:param name="editable" value="true" />
			</s:action>
		</div>
	</tiles:putAttribute>
</tiles:insertDefinition>