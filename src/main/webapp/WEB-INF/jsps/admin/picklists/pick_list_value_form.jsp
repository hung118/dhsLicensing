<%@ taglib prefix="s" uri="/struts-tags"%>
<fieldset>
	<legend><s:if test="pickListValue != null and pickListValue.id != null">Edit</s:if><s:else>Create</s:else> Pick List Value</legend>
	<s:fielderror/>
	<s:form id="pickListValueForm" action="save-pick-list-value" method="post" cssClass="ajaxify {target: '#pickListValuesSection'} ccl-action-save">
		<s:hidden name="pickList.id"/>
		<s:hidden name="pickListValue.id"/>
		<ol class="fieldList">
			<li>
				<label for="pickListValue">Value:</label>
				<s:textfield id="pickListValue" name="pickListValue.value"/>
			</li>
			<s:iterator value="metadata.pickListValueExtensionFields" var="field">
				<s:if test="#field.inputType.name() == 'SELECT'">
					<s:include value="pick_list_value_select_extension.jsp"/>
				</s:if>
				<s:elseif test="#field.inputType.name() == 'TEXTFIELD'">
					<s:include value="pick_list_value_textfield_extension.jsp"/>
				</s:elseif>
				<s:elseif test="#field.inputType.name() == 'CHECKBOX'">
					<s:include value="pick_list_value_checkbox_extension.jsp"/>
				</s:elseif>
			</s:iterator>
			<li>
				<label for="sortOrder">Order:</label>
				<s:textfield id="sortOrder" name="pickListValue.sortOrder"/>
			</li>
			<li class="submit">
				<s:submit value="Save"/>
				<s:url id="pickListValueEditCancelUrl" action="view-pick-list-value-list" includeParams="false">
					<s:param name="pickList.id" value="pickList.id"/>
				</s:url>
				<s:a cssClass="ajaxify {target: '#pickListValuesSection'}" href="%{pickListValueEditCancelUrl}">
					Cancel
				</s:a>
			</li>
		</ol>
	</s:form>
</fieldset>