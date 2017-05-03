<%@ taglib prefix="s" uri="/struts-tags"%>
<s:include value="../navigation.jsp"><s:param name="selectedTab">board</s:param></s:include>

<div id="boardMembersSection">
	<s:action name="board-members-list" executeResult="true"/>
</div>
<div id="boardMembersAttachmentsSection">
	<s:action name="board-members-attachments-list" executeResult="true"/>
</div>
