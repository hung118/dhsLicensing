<%@ taglib prefix="s" uri="/struts-tags"%>
<s:include value="../navigation.jsp"><s:param name="selectedTab">notes</s:param></s:include>
<div id="COMPLAINT_NotesSection">
	<s:action name="notes-list" namespace="/note" executeResult="true">
		<s:param name="objectId" value="complaintId"/>
		<s:param name="noteType" value="%{'COMPLAINT'}"/>
		<s:param name="disableRange" value="true"/>
	</s:action>
</div>