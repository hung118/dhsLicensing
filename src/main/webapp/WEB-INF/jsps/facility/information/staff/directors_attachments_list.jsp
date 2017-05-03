<%@ taglib prefix="s" uri="/struts-tags"%>
<fieldset>
	<legend>Directors Attachments</legend>
	<div id="directorsAttachmentsListSection">
		<s:include value="directors_attachments_table.jsp">
			<s:param name="showControls">true</s:param>
		</s:include>
	</div>
</fieldset>