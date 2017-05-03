<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<tiles:insertDefinition name="tab">
	<tiles:putAttribute name="baseDivId" value="welcomeBase"/>
	<tiles:putAttribute name="body">
		<s:action name="welcome-section" executeResult="true"/>
	</tiles:putAttribute>
</tiles:insertDefinition>