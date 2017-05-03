<%@ taglib prefix="s" uri="/struts-tags"%>
<fieldset>
	<legend>Allegations</legend>
	<ol class="fieldList ccl-margin-bottom">
		<li>
			<div><span class="label">Narrative:</span> <span class="description"><s:property value="narrative"/></span></div>
		</li>
	</ol>
	<div id="allegationsListSection">
		<s:include value="allegations_table.jsp">
			<s:param name="showControls">true</s:param>
		</s:include>
	</div>
</fieldset>