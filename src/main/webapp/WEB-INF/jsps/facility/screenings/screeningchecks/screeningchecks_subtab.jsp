<%@ taglib prefix="s" uri="/struts-tags"%>
<s:include value="../navigation.jsp"><s:param name="selectedTab">screening-checks</s:param></s:include>
<div id="screeningChecksSection">
	<s:action name="screening-checks-section" executeResult="true"/>
</div>
<div id="BACKGROUND_SCREENING_NotesSection">
	<s:action name="notes-list" namespace="/note" executeResult="true">
		<s:param name="objectId" value="screening.screenedPerson.id"/>
		<s:param name="noteType" value="%{'BACKGROUND_SCREENING'}"/>
		<s:param name="facilityId" value="facilityId"/>
	</s:action>
</div>