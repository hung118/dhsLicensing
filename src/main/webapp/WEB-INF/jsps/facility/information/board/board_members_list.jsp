<%@ taglib prefix="s" uri="/struts-tags"%>
<fieldset>
	<legend>Governing Board Members</legend>
	<div id="boardMembersListSection">
		<s:include value="board_members_table.jsp">
			<s:param name="showControls">true</s:param>
		</s:include>
	</div>
</fieldset>