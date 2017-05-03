<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<tiles:insertDefinition name="tab">
	<tiles:putAttribute name="baseDivId" value="cbscommBase"/>
	<tiles:putAttribute name="body">
		<div id="convictionsSection">
			<s:action name="list-convictions" namespace="/trackingrecordscreening/cbscomm" executeResult="true">
				<s:param name="screeningId" value="screeningId" />
			</s:action>
		</div>
		<div id="cbscommSection">
			<s:action name="edit-cbscomm" namespace="/trackingrecordscreening/cbscomm" executeResult="true">
				<s:param name="screeningId" value="screeningId" />
			</s:action>
		</div>
		<div id="convictionLettersSection">
			<s:action name="list-conviction-letters" namespace="/trackingrecordscreening/cbscomm" executeResult="true">
				<s:param name="screeningId" value="screeningId" />
			</s:action>
		</div>
		<div id="cbscommNotesSection">
			<s:set var="formSection">#cbscommNotesSection</s:set>
			<s:action name="administer-notes" namespace="/trackingrecordscreening/main" executeResult="true">
				<s:param name="screeningId" value="screeningId" />
				<s:param name="formSection" value="%{formSection}" />
				<s:param name="editable" value="true" />
			</s:action>
		</div>
	</tiles:putAttribute>
</tiles:insertDefinition>