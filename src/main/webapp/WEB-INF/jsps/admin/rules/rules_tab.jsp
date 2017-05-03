<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<tiles:insertDefinition name="tab">
	<tiles:putAttribute name="baseDivId" value="rulesBase"/>
	<tiles:putAttribute name="body">
		<s:action name="edit-rule" executeResult="true"/>
	</tiles:putAttribute>
</tiles:insertDefinition>