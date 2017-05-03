<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<tiles:insertDefinition name="tab">
	<tiles:putAttribute name="baseDivId" value="accountsBase"/>
	<tiles:putAttribute name="body">
		<s:action name="accounts-list" executeResult="true"/>
	</tiles:putAttribute>
</tiles:insertDefinition>