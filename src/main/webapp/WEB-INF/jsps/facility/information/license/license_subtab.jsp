<%@ taglib prefix="s" uri="/struts-tags"%>
<s:include value="../navigation.jsp"><s:param name="selectedTab">license</s:param></s:include>
<div id="licensesSection">
	<s:action name="licenses-list" namespace="/facility/information/license" executeResult="true"/>
</div>
<!-- 
<div id="conditionalStatusesSection">
	<s:action name="conditional-statuses-list" namespace="/facility/information/license" executeResult="true"/>
</div>
-->
<!-- div id="ratingsSection"> Redmine 24671
	<s:action name="ratings-list" namespace="/facility/information/license" executeResult="true"/>
</div -->
<div id="accreditationsSection">
	<s:action name="accreditations-list" namespace="/facility/information/license" executeResult="true"/>
</div>
<div id="exemptionsSection">
	<s:action name="exemptions-list" namespace="/facility/information/license" executeResult="true"/>
</div>