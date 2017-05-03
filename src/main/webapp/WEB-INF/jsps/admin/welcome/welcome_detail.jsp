<%@ taglib prefix="s" uri="/struts-tags"%>
<fieldset>
	<legend>Welcome Message</legend>
	<ol class="fieldList">
		<li>
			<div class="label">Message:</div>
			<div class="value description"><s:property value="welcomeMessage"/></div>
		</li>
		<li class="submit">
			<s:form action="edit-welcome" method="get" cssClass="ajaxify {target: '#welcomeBase'}">
				<s:submit value="Edit"/>
			</s:form>
		</li>
	</ol>
</fieldset>