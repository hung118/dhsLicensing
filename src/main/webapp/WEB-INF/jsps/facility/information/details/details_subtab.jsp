<%@ taglib prefix="s" uri="/struts-tags"%>
<s:include value="../navigation.jsp"><s:param name="selectedTab">details</s:param></s:include>
<div id="informationSection">
	<s:action name="view-information" executeResult="true"/>
</div>
<!--  
<div id="statusSection">
	<s:action name="status-section" executeResult="true"/>
</div>
-->
<div id="associatedFacilitiesSection">
	<s:action name="associated-facilities-list" executeResult="true"/>
</div>
