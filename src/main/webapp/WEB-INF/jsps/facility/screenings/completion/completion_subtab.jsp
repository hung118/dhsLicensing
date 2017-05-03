<%@ taglib prefix="s" uri="/struts-tags"%>
<s:include value="../navigation.jsp"><s:param name="selectedTab">completion</s:param></s:include>
<div id="completionSection">
	<s:action name="view-completion" executeResult="true"/>
</div>
<s:if test="screening.denied">
	<div id="resolutionOfDenialSection">
		<s:action name="view-resolution-of-denial" executeResult="true"/>
	</div>
</s:if>
<div id="BACKGROUND_SCREENING_NotesSection">
	<s:action name="notes-list" namespace="/note" executeResult="true">
		<s:param name="objectId" value="screening.screenedPerson.id"/>
		<s:param name="noteType" value="%{'BACKGROUND_SCREENING'}"/>
		<s:param name="facilityId" value="facilityId"/>
	</s:action>
</div>