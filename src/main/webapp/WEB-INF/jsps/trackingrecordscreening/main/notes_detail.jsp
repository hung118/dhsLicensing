<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="ccl" uri="/WEB-INF/tags/ccl-tags.tld"%>

<fieldset>
	<legend>Notes And Comments</legend>
	<ol class="fieldList medium">
		<li>
			<div class="label">Notes:</div>
			<div class="value" style="width:550px;"><s:property value="notes"/></div>
		</li>
		<li>
			<div class="label">Comments:</div>
			<div class="value" style="width:550px;"><s:property value="comments"/></div>
		</li>
	</ol>
</fieldset>
