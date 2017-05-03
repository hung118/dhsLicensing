<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<tiles:insertDefinition name="tab">
	<tiles:putAttribute name="baseDivId" value="noncomplianceBase"/>
	<tiles:putAttribute name="body">
		<div id="ncPointsSection">
			<s:action name="edit-points" executeResult="true"/>
		</div>
		<div id="ncTriggersSection">
			<s:action name="edit-triggers" executeResult="true"/>
		</div>
	</tiles:putAttribute>
</tiles:insertDefinition>