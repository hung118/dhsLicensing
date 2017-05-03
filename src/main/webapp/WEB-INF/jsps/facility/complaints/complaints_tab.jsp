<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<tiles:insertDefinition name="tab">
	<tiles:putAttribute name="baseDivId" value="complaintsBase"/>
	<tiles:putAttribute name="body">
		<div id="complaintsProgressSection">
			<s:action name="progress-list" executeResult="true"/>
		</div>
		<div id="complaintsHistorySection">
			<s:action name="history-list" executeResult="true"/>
		</div>
	</tiles:putAttribute>
</tiles:insertDefinition>