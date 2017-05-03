<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<tiles:insertDefinition name="tab">
	<tiles:putAttribute name="baseDivId" value="oscarBase" />
	<tiles:putAttribute name="body">
		<div id="oscarSection">
			<s:action name="oscar-list" namespace="/trackingrecordscreening/oscar" executeResult="true">
				<s:param name="screeningId" value="screeningId" />
			</s:action>
		</div>
		<div id="oscarNotesSection">
			<s:set var="formSection">#oscarNotesSection</s:set>
			<s:action name="administer-notes" namespace="/trackingrecordscreening/main" executeResult="true">
				<s:param name="screeningId" value="screeningId" />
				<s:param name="formSection" value="%{formSection}"/>
				<s:param name="editable" value="false" />
			</s:action>
		</div>
	</tiles:putAttribute>
</tiles:insertDefinition>