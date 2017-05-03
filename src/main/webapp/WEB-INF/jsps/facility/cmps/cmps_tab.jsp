<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<tiles:insertDefinition name="tab">
	<tiles:putAttribute name="baseDivId" value="cmpsBase"/>
	<tiles:putAttribute name="body">
		<div id="cmpsWaitingSection">
			<s:action name="waiting-list" executeResult="true"/>
		</div>
		<div id="cmpsHistorySection">
			<s:action name="history-list" executeResult="true"/>
		</div>
	</tiles:putAttribute>
</tiles:insertDefinition>