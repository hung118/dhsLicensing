<%@ taglib prefix="s" uri="/struts-tags"%>
<fieldset>
	<legend>Complaint Narrative</legend>
	<s:fielderror/>
	<s:form id="complaintNarrativeForm" action="save-narrative" method="post" cssClass="ajaxify {target: '#narrativeSection'} ccl-action-save">
		<s:hidden name="facilityId"/>
		<s:hidden name="complaintId"/>
		<ol class="fieldList">
			<li>
				<label for="narrative"><span class="redtext">* </span>Narrative:</label>
				<s:textarea id="narrative" name="complaint.narrative" cssClass="tallTextArea"/>
			</li>
			<li class="submit">
				<s:submit value="Save"/>
				<s:url id="narrativeCancelUrl" action="view-narrative" includeParams="false">
					<s:param name="facilityId" value="facilityId"/>
					<s:param name="complaintId" value="complaintId"/>
				</s:url>
				<s:a id="narrativeCancel" href="%{narrativeCancelUrl}" cssClass="ajaxify {target: '#narrativeSection'}">
					Cancel
				</s:a>
			</li>
		</ol>
	</s:form>
</fieldset>