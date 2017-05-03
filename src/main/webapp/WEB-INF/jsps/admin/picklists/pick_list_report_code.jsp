<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="display" uri="http://displaytag.sf.net"%>

<script type="text/javascript">
	$("#serviceCode").change(function() {
		$.getJSON(context + '/admin/picklists/report-codes.action', {serviceCode: $(this).val()}, function(data) {
			$("#programCode").empty(); // remove all previous options
			for (var i = 0; i < data.programCodes.length; i++) {
				$("#programCode").append(data.programCodes[i]);
			}
			
			$("#specificServiceCode").empty();
			for (var i = 0; i < data.specificServiceCodes.length; i++) {
				$("#specificServiceCode").append(data.specificServiceCodes[i]);
			}
			
			$("#ageGroup").empty();
			for (var i = 0; i < data.ageGroups.length; i++) {
				$("#ageGroup").append(data.ageGroups[i]);
			}
		});
	});

</script>

<fieldset>
	<legend><s:property value="pickList.name"/></legend>
	<s:fielderror/>
	<s:form id="pickListValuesForm" action="save-report-code-dependencies" method="post" cssClass="ajaxify {target: '#pickListValuesSection'} ccl-action-save">
		<s:hidden name="pickList.id"/>
		<ol class="fieldList">
			<li>
				<label for="serviceCode"><span class="redtext">* </span>Service Code:</label>
				<s:select id="serviceCode" name="serviceCode" value="serviceCode.id" list="serviceCodes" listKey="id" listValue="value" headerKey="-1" headerValue="- Select a Service Code -"/>
			</li>
			<li>
				<label for="programCode">Program Code:</label>
				<s:select id="programCode" name="programCode" value="programCode.id" list="programCodes" listKey="id" listValue="value" headerKey="-1" headerValue="" multiple="true" size="13" cssClass="multiselect"/>
			</li>
			<li>
				<label for="programCode">Specific Service Code:</label>
				<s:select id="specificServiceCode" name="specificServiceCode" value="specificServiceCode.id" list="specificServiceCodes" listKey="id" listValue="value" headerKey="-1" headerValue="" multiple="true" size="6" cssClass="multiselect"/>
			</li>
			<li>
				<label for="ageGroup">Age Group:</label>
				<s:select id="ageGroup" name="ageGroup" value="ageGroup.id" list="ageGroups" listKey="id" listValue="value" headerKey="-1" headerValue="" multiple="true" size="5" cssClass="multiselect"/>
			</li>
		</ol>
		<div class="bottomControls">
			<div class="mainControls">
				<s:submit value="Save Dependencies"/>
			</div>
		</div>
	</s:form>
</fieldset>