<%@ taglib prefix="s" uri="/struts-tags"%>
<s:include value="../navigation.jsp"><s:param name="selectedTab">notes</s:param></s:include>
<div id="BACKGROUND_SCREENING_NotesSection">
	<s:action name="notes-list" namespace="/note" executeResult="true">
		<s:param name="objectId" value="screening.screenedPerson.id"/>
		<s:param name="noteType" value="%{'BACKGROUND_SCREENING'}"/>
		<s:param name="facilityId" value="facilityId"/>
	</s:action>
</div>