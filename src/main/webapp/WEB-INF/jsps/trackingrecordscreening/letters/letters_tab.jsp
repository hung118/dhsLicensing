<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<tiles:insertDefinition name="tab">
	<tiles:putAttribute name="baseDivId" value="lettersBase"/>
	<tiles:putAttribute name="body">
		<div id="letterSection">
			<s:action name="list-letters" namespace="/trackingrecordscreening/letters" executeResult="true">
				<s:param name="screeningId" value="screeningId" />
			</s:action>
		</div>
		<div id="ltr15Section">
			<s:action name="list-ltr15" namespace="/trackingrecordscreening/letters" executeResult="true">
				<s:param name="screeningId" value="screeningId" />
			</s:action>
		</div>
		<div id="lettersNotesSection">
			<s:set var="formSection">#lettersNotesSection</s:set>
			<s:action name="administer-notes" namespace="/trackingrecordscreening/main" executeResult="true">
				<s:param name="screeningId" value="screeningId" />
				<s:param name="formSection" value="%{formSection}" />
				<s:param name="editable" value="true" />
			</s:action>
		</div>
	</tiles:putAttribute>
</tiles:insertDefinition>