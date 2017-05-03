<%@ taglib prefix="s" uri="/struts-tags"%>
<li>
	<label for="<s:property value='#field.name'/>"><s:if test="#field.required"><span class="redtext">* </span></s:if><s:property value="#field.label"/>:</label>
	<input type="checkbox" id="<s:property value='#field.name'/>" name="plv.<s:property value='#field.name'/>" <s:if test="#field.value == 'true'">checked="checked"</s:if> class="<s:if test="#field.required">required </s:if>"/>
</li>