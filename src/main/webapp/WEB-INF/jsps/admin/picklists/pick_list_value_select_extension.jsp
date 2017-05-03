<%@ taglib prefix="s" uri="/struts-tags"%>
<li>
	<label for="<s:property value='#field.name'/>"><s:if test="#field.required"><span class="redtext">* </span></s:if><s:property value="#field.label"/>:</label>
	<select id="<s:property value='#field.name'/>" name="plv.<s:property value='#field.name'/>" class="<s:if test="#field.required">required </s:if>">
		<option value="-1">- Select <s:property value='#field.label'/> -</option>
		<s:iterator value="#field.list" var="listItem">
			<option value="<s:property value='#listItem.name'/>" <s:if test="#field.value == #listItem.name">selected="selected"</s:if>><s:property value="#listItem.value"/></option>
		</s:iterator>
	</select>
</li>