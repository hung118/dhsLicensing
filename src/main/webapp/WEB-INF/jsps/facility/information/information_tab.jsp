<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<tiles:insertDefinition name="tab">
	<tiles:putAttribute name="baseDivId" value="informationBase"/>
	<tiles:putAttribute name="body">
		<s:action name="tab" namespace="/facility/information/details" executeResult="true"/>
	</tiles:putAttribute>
</tiles:insertDefinition>