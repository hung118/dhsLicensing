<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<tiles:insertDefinition name="tab">
	<tiles:putAttribute name="baseDivId" value="incidentsBase"/>
	<tiles:putAttribute name="body">
		<div id="incidentsProgressSection">
			<s:action name="progress-list" executeResult="true"/>
		</div>
		<div id="incidentsHistorySection">
			<s:action name="history-list" executeResult="true"/>
		</div>
	</tiles:putAttribute>
</tiles:insertDefinition>