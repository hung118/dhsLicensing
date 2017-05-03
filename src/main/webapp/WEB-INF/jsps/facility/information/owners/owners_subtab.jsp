<%@ taglib prefix="s" uri="/struts-tags"%>
<s:include value="../navigation.jsp"><s:param name="selectedTab">owners</s:param></s:include>
<div id="ownershipSection">
	<s:action name="ownership-section" executeResult="true"/>
</div>
<div id="ownersSection">
	<s:action name="owners-list" executeResult="true"/>
</div>