<%@ taglib prefix="s" uri="/struts-tags"%>
<fieldset>
	<legend>Facility Action Log</legend>
	<div id="actionsListSection">
		<s:include value="actions_list.jsp">
			<s:param name="showControls">true</s:param>
		</s:include>
	</div>
</fieldset>