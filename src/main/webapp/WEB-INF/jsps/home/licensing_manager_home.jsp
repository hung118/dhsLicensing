<%@ taglib prefix="s" uri="/struts-tags"%>
<h1>Licensing Manager Case Load</h1>
<div id="managerVariancesSection">
	<s:action name="manager-variances" namespace="/home/alerts" executeResult="true">
		<s:param name="person.id" value="user.person.id"/>
	</s:action>
</div>
<div id="complaintsInProgressAlertSection-MGR">
	<s:action name="list" namespace="/alert/complaints-in-progress" executeResult="true">
		<s:param name="person.id" value="user.person.id"/>
		<s:param name="roleType" value="%{'MGR'}"/>
	</s:action>
</div>