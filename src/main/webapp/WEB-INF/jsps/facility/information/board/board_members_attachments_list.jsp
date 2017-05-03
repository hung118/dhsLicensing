<%@ taglib prefix="s" uri="/struts-tags"%>
<fieldset>
	<legend>Board Members Attachments (PDF)</legend>
	<div id="boardMembersAttachmentsListSection">
		<s:include value="board_members_attachments_table.jsp">
			<s:param name="showControls">true</s:param>
		</s:include>
	</div>
</fieldset>