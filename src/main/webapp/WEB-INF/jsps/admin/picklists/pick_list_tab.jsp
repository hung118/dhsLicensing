<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<tiles:insertDefinition name="tab">
	<tiles:putAttribute name="javascript">
		<script type="text/javascript">
			$("#pickListGroup").change(function() {
				$("#pickListValuesSection").empty();
				$("#pickListGroupSelectionForm").submit();
			});
		</script>
	</tiles:putAttribute>
	<tiles:putAttribute name="baseDivId" value="pickListsBase"/>
	<tiles:putAttribute name="body">
		<fieldset>	
			<legend>Pick Lists</legend>
			<s:form id="pickListGroupSelectionForm" action="view-pick-list-selection" method="get" cssClass="ajaxify {target: '#pickListSection'}">
				<ol class="fieldList">
					<li>
						<label for="pickListGroup">Group:</label>
						<s:select id="pickListGroup" name="pickListGroupId" list="pickListGroups" listKey="id" listValue="name"
								  headerKey="-1" headerValue="- Select a Group -"/>
					</li>
				</ol>
			</s:form>
			<div id="pickListSection"></div>
		</fieldset>
		<div id="pickListValuesSection"></div>
	</tiles:putAttribute>
</tiles:insertDefinition>