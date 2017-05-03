<%@ taglib prefix="s" uri="/struts-tags"%>
<s:include value="../navigation.jsp"><s:param name="selectedTab">staff</s:param></s:include>
<div id="directorsSection">
	<s:action name="directors-list" executeResult="true"/>
</div>
<div id="directorsAttachmentsSection">
	<s:action name="directors-attachments-list" executeResult="true"/>
</div>
<div id="contactsSection">
	<s:action name="contacts-list" executeResult="true"/>
</div>