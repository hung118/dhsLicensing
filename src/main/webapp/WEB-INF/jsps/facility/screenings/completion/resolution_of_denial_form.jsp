<%@ taglib prefix="s" uri="/struts-tags"%>
<script type="text/javascript">
	initResolutionOfDenialForm();
</script>
<fieldset>
	<legend>Edit Resolution of Denial</legend>
	<s:fielderror/>
	<s:form id="resolutionOfDenialForm" action="save-resolution-of-denial" method="post" cssClass="ajaxify {target: '#resolutionOfDenialSection'} ccl-action-save">
		<s:hidden name="facilityId"/>
		<s:hidden name="screeningId"/>
		<ol class="fieldList">
			<li>
				<label for="resolution"><span class="redtext">* </span>Resolution:</label>
				<s:select id="resolution" name="screening.resolution" value="%{screening.resolution.id}" list="resolutions" listKey="id"
						  listValue="value" headerKey="-1" headerValue="- Select Resolution" cssClass="required"/>
			</li>
			<li>
				<label for="resolutionDate"><span class="redtext">* </span>Resolution Date:</label>
				<s:date id="resolutionDateFormatted" name="screening.resolutionDate" format="MM/dd/yyyy"/>
				<s:textfield id="resolutionDate" name="screening.resolutionDate" value="%{resolutionDateFormatted}" cssClass="required datepicker" maxlength="10"/>
			</li>
			<li>
				<label>Appeal:</label>
				<ol class="fieldGroup">
					<li class="clearLeft fieldMargin">
						<label for="appealRequestDate">Appeal Request Date:</label>
						<s:date id="appealRequestDateFormatted" name="screening.appealRequestDate" format="MM/dd/yyyy"/>
						<s:textfield id="appealRequestDate" name="screening.appealRequestDate" value="%{appealRequestDateFormatted}" cssClass="datepicker" maxlength="10"/>
					</li>
					<li class="hiddenAppealField fieldMargin">
						<label for="appealOutcome">Appeal Outcome:</label>
						<s:select id="appealOutcome" name="screening.appealOutcome" list="appealOutcomes" listValue="displayName" headerKey="-1"
								  headerValue="- Select Outcome -"/>
					</li>
					<li class="hiddenAppealField fieldMargin">
						<label for="appealOutcomeOther">Other:</label>
						<s:textfield id="appealOutcomeOther" name="screening.appealOutcomeOther"/>
					</li>
				</ol>
			</li>
			<li>
				<label>Attestations:</label>
				<ol class="fieldGroup">
					<li>
						<label for="attestationSentDate">Attestation Sent Date:</label>
						<s:date id="attestationSentDateFormatted" name="screening.attestationSentDate" format="MM/dd/yyyy"/>
						<s:textfield id="attestationSentDate" name="screening.attestationSentDate" value="%{attestationSentDateFormatted}" cssClass="datepicker" maxlength="10"/>
					</li>
					<li>
						<label for="attestationDueDate">Attestation Due Date:</label>
						<s:date id="attestationDueDateFormatted" name="screening.attestationDueDate" format="MM/dd/yyyy"/>
						<s:textfield id="attestationDueDate" name="screening.attestationDueDate" value="%{attestationDueDateFormatted}" cssClass="datepicker" maxlength="10"/>
					</li>
					<li class="clearLeft fieldMargin">
						<label for="attestationReceivedDate">Attestation Received Date:</label>
						<s:date id="attestationReceivedDateFormatted" name="screening.attestationReceivedDate" format="MM/dd/yyyy"/>
						<s:textfield id="attestationReceivedDate" name="screening.attestationReceivedDate" value="%{attestationReceivedDateFormatted}" cssClass="datepicker" maxlength="10"/>
					</li>
				</ol>
			</li>
			<li class="submit">
				<s:submit value="Save"/>
				<s:url id="resolutionOfDenialEditCancelUrl" action="view-resolution-of-denial" includeParams="false">
					<s:param name="facilityId" value="facilityId"/>
					<s:param name="screeningId" value="screeningId"/>
				</s:url>
				<s:a id="resolutionOfDenialEditCancel" href="%{resolutionOfDenialEditCancelUrl}" cssClass="ajaxify {target: '#resolutionOfDenialSection'}">
					Cancel
				</s:a>
			</li>
		</ol>
	</s:form>
</fieldset>

<script type="text/javascript">
	// *** Short key ctrl-q for current date on fields that ends with 'Date' in id attribute. ***
	$('[id $= "Date"]').keydown(function(e) {
        //CTRL + Q keydown for current date. Key codes: http://www.cambiaresearch.com/articles/15/javascript-char-codes-key-codes
        if(e.ctrlKey && e.keyCode == 81){
            var currentDate = new Date();
            $(this).val(currentDate.format("mm/dd/yyyy"));
        }
    });
</script>
