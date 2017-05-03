<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<fieldset>
	<legend>Licensing Director Variance Revocation</legend>
	<s:fielderror/>
	<s:form id="varianceRevokeForm" action="save-revoke" method="post" cssClass="ajaxify {target: '#outcomeSection'} ccl-action-save">
		<s:hidden name="facilityId"/>
		<s:hidden name="varianceId"/>
		<ol class="fieldList">
			<li>
				<div class="label">Review Date:</div>
				<div class="value"><s:date name="variance.reviewDate" format="MM/dd/yyyy"/></div>
			</li>
			<li>
				<div class="label">Outcome:</div>
				<div class="value"><s:property value="variance.outcome.displayName"/></div>
			</li>
			<li>
				<div class="label">Approved Duration:</div>
				<ol class="fieldGroup">
					<li>
						<div class="label">Start Date:</div>
						<div class="value"><s:date name="variance.startDate" format="MM/dd/yyyy"/></div>
					</li>
					<li>
						<div class="label">End Date:</div>
						<div class="value"><s:date name="variance.endDate" format="MM/dd/yyyy"/></div>
					</li>
				</ol>
			</li>
			<li>
				<div class="label">Response:</div>
				<div class="value" style="width:550px;"><s:property value="variance.directorResponse"/></div>
			</li>
			<li>
				<div class="label">Outcome Finalized?</div>
				<div class="value"><s:property value="@gov.utah.dts.det.ccl.view.YesNoChoice@valueOfBoolean(variance.finalized).displayName"/></div>
			</li>
			<li>
				<label for="revocationDate"><span class="redtext">* </span>Revocation Date</label>
				<s:date id="revocationDateFormatted" name="variance.revocationDate" format="MM/dd/yyyy"/>
				<s:textfield id="revocationDate" name="variance.revocationDate" value="%{revocationDateFormatted}" cssClass="required datepicker" maxlength="10"/>
			</li>
			<li>
				<label for="revokeReason"><span class="redtext">* </span>Reason:</label>
				<s:textarea id="revokeReason" name="variance.revokeReason" cssClass="required"/>
			</li>
			<li class="checkbox">
				<s:checkbox id="revokeFinalized" name="variance.revokeFinalized"/>
				<label for="revokeFinalized">I wish to finalize this revocation and send notification to the facility contacts.</label>
			</li>
			<li class="submit">
				<s:submit value="Save"/>
				<s:url id="outcomeEditCancelUrl" action="view-outcome" includeParams="false">
					<s:param name="facilityId" value="facilityId"/>
					<s:param name="varianceId" value="varianceId"/>
				</s:url>
				<s:a href="%{outcomeEditCancelUrl}" cssClass="ajaxify {target: '#outcomeSection'}">Cancel</s:a>
			</li>
		</ol>
	</s:form>
</fieldset>
<script type="text/javascript">
	initVarianceOutcomeForm();
</script>

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

