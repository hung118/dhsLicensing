<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<tiles:insertDefinition name="tab">
	<tiles:putAttribute name="baseDivId" value="inspectionsBase"/>
	<tiles:putAttribute name="body">
		<div id="inspectionsProgressSection">
			<s:action name="progress-list" executeResult="true"/>
		</div>
		<div id="inspectionsHistorySection">
			<s:action name="history-list" executeResult="true"/>
		</div>
	</tiles:putAttribute>
</tiles:insertDefinition>