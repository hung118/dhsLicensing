<%@ taglib prefix="s" uri="/struts-tags"%>
<s:if test="note != null and note.id != null">
	<s:set var="legendText">Edit</s:set>
</s:if>
<s:else>
	<s:set var="legendText">Create</s:set>
</s:else>
<fieldset>
	<legend><s:property value="%{legendText}"/> Note</legend>
	<s:fielderror/>
	<s:form id="%{noteType}_noteForm" action="save-note" namespace="/note" method="post" cssClass="ajaxify {target: '#%{noteType}_NotesSection'} ccl-action-save">
		<s:hidden name="facilityId"/>
		<s:hidden name="note.id"/>
		<s:hidden name="noteType"/>
		<s:hidden name="objectId"/>
		<s:hidden name="disableRange"/>
		<ol class="fieldList">
			<li>
				<label for="<s:property value='noteType'/>_noteText">Note:</label>
				<s:textarea id="%{noteType}_noteText" name="note.text"/>
			</li>
			<li class="charCount labelWidthMargin">
				
			</li>
			<li class="submit">
				<s:submit value="Save"/>
				<s:url id="noteEditCancelUrl" action="notes-list" namespace="/note" includeParams="false">
					<s:param name="facilityId" value="facilityId"/>
					<s:param name="noteType" value="noteType"/>
					<s:param name="objectId" value="objectId"/>
					<s:param name="disableRange" value="disableRange"/>
				</s:url>
				<s:a id="%{noteType}_noteEditCancel" href="%{noteEditCancelUrl}" cssClass="ajaxify {target: '#%{noteType}_NotesSection'}">
					Cancel
				</s:a>
			</li>
		</ol>
	</s:form>
</fieldset>
<script type="text/javascript">
	$("#<s:property value='noteType'/>_noteText").charCounter(4000, {container: "#<s:property value='noteType'/>_noteForm .charCount"});
</script>