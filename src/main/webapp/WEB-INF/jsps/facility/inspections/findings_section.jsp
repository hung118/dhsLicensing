<%@ taglib prefix="s" uri="/struts-tags"%>
<s:if test="refreshInspection">
	<script type="text/javascript">
		refreshInspection(<s:property value="inspectionId"/>);
	</script>
</s:if>
<!-- 
<fieldset>
	<legend>Findings</legend>
	<div id="findingsListSection">
		<s:include value="findings_list.jsp">
			<s:param name="showControls">true</s:param>
		</s:include>
	</div>
</fieldset>
 -->