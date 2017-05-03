<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<tiles:insertDefinition name="tab">
	<tiles:putAttribute name="baseDivId" value="actionLogNotesBase"/>
	<tiles:putAttribute name="body">
		<div id="actionLogSection">
			<s:action name="action-log-section" executeResult="true"/>
		</div>
		<div id="FACILITY_NotesSection">
			<s:action name="notes-list" namespace="/note" executeResult="true">
				<s:param name="objectId" value="facilityId"/>
				<s:param name="noteType" value="%{'FACILITY'}"/>
			</s:action>
		</div>
	</tiles:putAttribute>
</tiles:insertDefinition>