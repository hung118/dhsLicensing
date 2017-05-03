<%@ taglib prefix="s" uri="/struts-tags"%>
<script type="text/javascript">
	$("#pickList").change(function() {
		$("#pickListValuesSection").empty();
		$("#pickListSelectionForm").submit();
	});
</script>
<s:form id="pickListSelectionForm" action="view-pick-list-value-list" method="get" cssClass="marginTop ajaxify {target: '#pickListValuesSection'}">
	<ol class="fieldList">
		<li>
			<label for="pickList">Pick List:</label>
			<s:select id="pickList" name="pickList.id" list="pickLists" listKey="id" listValue="name" headerKey="-1"
					  headerValue="- Select a Pick List -"/>
		</li>
	</ol>
</s:form>