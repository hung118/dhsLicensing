<%@ taglib prefix="s" uri="/struts-tags"%>
<h1>Admin Manager Case Load</h1>
<div id="complaintsInProgressAlertSection-ADMIN">
	<s:action name="list" namespace="/alert/complaints-in-progress" executeResult="true">
		<s:param name="person.id" value="user.person.id"/>
		<s:param name="roleType" value="%{'ADMIN'}"/>
	</s:action>
</div>