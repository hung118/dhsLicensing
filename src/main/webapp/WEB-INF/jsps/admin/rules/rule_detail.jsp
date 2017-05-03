<%@ taglib prefix="s" uri="/struts-tags"%>
<fieldset>
	<legend>Rule</legend>
	<ol class="fieldList">
<%--
 		<li>
			<div class="label">Facility Type:</div>
			<div class="value"><s:property value="rule.facilityType.value" escape="true"/></div>
		</li>
--%>
		<li>
			<div class="label">Rule #:</div>
			<div class="value"><s:property value="rule.number" escape="true"/></div>
		</li>
		<li>
			<div class="label">Name:</div>
			<div class="value"><s:property value="rule.name" escape="true"/></div>
		</li>
	</ol>
</fieldset>