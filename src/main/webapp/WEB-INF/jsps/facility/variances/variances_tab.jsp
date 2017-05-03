<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<tiles:insertDefinition name="tab">
	<tiles:putAttribute name="baseDivId" value="variancesBase"/>
	<tiles:putAttribute name="body">
		<s:action name="variances-list" executeResult="true"/>
	</tiles:putAttribute>
</tiles:insertDefinition>