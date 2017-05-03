<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<tiles:insertDefinition name="tab">
	<tiles:putAttribute name="baseDivId" value="complianceandhistoryBase"/>
	<tiles:putAttribute name="body">
		<!-- s:action name="noncompliance" executeResult="true"/ -->
		<s:action name="history" executeResult="true"/>
	</tiles:putAttribute>
</tiles:insertDefinition>