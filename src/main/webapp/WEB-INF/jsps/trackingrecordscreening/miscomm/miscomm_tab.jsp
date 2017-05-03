<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<tiles:insertDefinition name="tab">
	<tiles:putAttribute name="baseDivId" value="miscommBase" />
	<tiles:putAttribute name="body">
		<div id="caseSection">
			<s:action name="case-list" namespace="/trackingrecordscreening/miscomm" executeResult="true">
				<s:param name="screeningId" value="screeningId" />
			</s:action>
		</div>
		<div id="miscommSection">
			<s:action name="edit-miscomm" namespace="/trackingrecordscreening/miscomm" executeResult="true">
				<s:param name="screeningId" value="screeningId" />
			</s:action>
		</div>
		<div id="miscommNotesSection">
			<s:set var="formSection">#miscommNotesSection</s:set>
			<s:action name="administer-notes" namespace="/trackingrecordscreening/main" executeResult="true">
				<s:param name="screeningId" value="screeningId" />
				<s:param name="formSection" value="%{formSection}"/>
				<s:param name="editable" value="false" />
			</s:action>
		</div>
	</tiles:putAttribute>
</tiles:insertDefinition>