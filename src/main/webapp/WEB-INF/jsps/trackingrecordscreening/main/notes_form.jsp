<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="ccl" uri="/WEB-INF/tags/ccl-tags.tld"%>

<fieldset>
	<legend>Edit Notes And Comments</legend>
	<s:actionerror/>
	<s:fielderror/>
	<s:form id="notesForm" action="save-notes" method="post" cssClass="ajaxify {target: '%{formSection}'} ccl-action-save">
		<s:hidden name="screeningId"/>
		<s:hidden name="formSection"/>
		<ol class="fieldList">
			<li class="column">
				<label for="notes">Notes:</label>
				<s:textarea id="notes" name="notes" />
				<div id="notesCharCount" class="charCount"></div>
			</li>
			<li class="column" style=" float:left;">
				<label for="comments">Comments:</label>
				<s:textarea id="comments" name="comments" />
				<div id="commentsCharCount" class="charCount"></div>
			</li>
			<li class="clearfix"></li>
			<li class="submit">
				<s:submit value="Save"/>
			</li>
		</ol>
	</s:form>
</fieldset>
<script type="text/javascript">
	$("<s:property value="formSection"/> #notes").charCounter(200, {container: "<s:property value="formSection"/> #notesCharCount"});
	$("<s:property value="formSection"/> #comments").charCounter(200, {container: "<s:property value="formSection"/> #commentsCharCount"});
</script>