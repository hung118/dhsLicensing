<%@ taglib prefix="s" uri="/struts-tags"%>
<s:include value="../navigation.jsp"><s:param name="selectedTab">forms</s:param></s:include>
<div id="formsSection">
	<s:action name="view-forms" executeResult="true"/>
</div>
