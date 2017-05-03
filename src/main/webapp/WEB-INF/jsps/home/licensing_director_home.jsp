<%@ taglib prefix="s" uri="/struts-tags"%>
<h1>Licensing Director Case Load</h1>
<div id="directorVariancesSection">
	<s:action name="director-variances" namespace="/home/alerts" executeResult="true">
		<s:param name="person.id" value="user.person.id"/>
	</s:action>
</div>
