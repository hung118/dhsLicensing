<%@ taglib prefix="s" uri="/struts-tags"%>
<h1>Office Specialist Case Load</h1>
<div id="regionExpiredLicensesSection">
	<s:action name="list" namespace="/alert/expired-licenses" executeResult="true">
		<s:param name="person.id" value="user.person.id"/>
		<s:param name="showWholeRegion" value="%{'true'}"/>
	</s:action>
</div>
<div id="newApplicationPendingDeadlinesSection">
	<s:action name="list" namespace="/alert/new-application-pending-deadlines" executeResult="true">
		<s:param name="person.id" value="user.person.id"/>
	</s:action>
</div>
<div id="accreditationExpirationSection">
	<s:action name="list" namespace="/alert/accreditation-expiration" executeResult="true">
		<s:param name="person.id" value="user.person.id"/>
		<s:param name="roleType" value="%{'ROLE_OFFICE_SPECIALIST'}"/>
	</s:action>
</div>