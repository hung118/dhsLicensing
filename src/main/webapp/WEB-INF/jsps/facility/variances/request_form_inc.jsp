<%@ taglib prefix="s" uri="/struts-tags"%>
<s:actionerror/>
<s:fielderror/>
<s:form id="varianceRequestForm" action="%{action}" method="post" cssClass="%{formClass}">
	<s:hidden id="varianceRequestFacilityId" name="facilityId"/>
	<s:hidden name="varianceId"/>
	<ol class="fieldList">
		<li>
			<label for="var-rule"><span class="redtext">* </span>Rule #: R501-</label>
			<s:textfield id="var-rule" name="variance.rule" value="%{variance.rule.id}" cssClass="required"/>
		</li>
		<li>
			<label for="requestDate"><span class="redtext">* </span>Request Date:</label>
			<s:date id="requestDateFormatted" name="variance.requestDate" format="MM/dd/yyyy"/>
			<s:textfield id="requestDate" name="variance.requestDate" value="%{requestDateFormatted}" cssClass="required datepicker" maxlength="10"/>
		</li>
		<li>
			<label><span class="redtext">* </span>Request Duration:</label>
			<ol class="fieldGroup">
				<li>
					<label for="requestedStartDate">Start Date:</label>
					<s:date id="requestedStartDateFormatted" name="variance.requestedStartDate" format="MM/dd/yyyy"/>
					<s:textfield id="requestedStartDate" name="variance.requestedStartDate" value="%{requestedStartDateFormatted}" cssClass="required datepicker" maxlength="10"/>
				</li>
				<li>
					<label for="requestedEndDate">End Date:</label>
					<s:date id="requestedEndDateFormatted" name="variance.requestedEndDate" format="MM/dd/yyyy"/>
					<s:textfield id="requestedEndDate" name="variance.requestedEndDate" value="%{requestedEndDateFormatted}" cssClass="required datepicker" maxlength="10"/>
				</li>
			</ol>
		</li>
		<li>
			<label for="purpose"><span class="redtext">* </span>Purpose:</label>
			<s:textarea id="purpose" name="variance.purpose" cssClass="required"  />
			<div id="charCount1" class="charCount" style="margin-left:11em;"></div>
		</li>
		<li>
			<label for="healthSafetyInsuredBy"><span class="redtext">* </span>How will health and safety be insured if approved?</label>
			<s:textarea id="healthSafetyInsuredBy" name="variance.healthSafetyInsuredBy" cssClass="required"/>
			<div id="charCount2" class="charCount" style="margin-left:11em;"></div>
		</li>
		<li>
			<label for="clientName">Client name if for a specific client:</label>
			<s:textfield id="clientName" name="variance.clientName" value="%{variance.clientName}" cssClass="required"/>
		</li>
		<li class="submit">
			<s:submit value="Save"/>
			<s:a href="%{requestEditCancelUrl}" cssClass="%{cancelClass}">
				Cancel
			</s:a>
		</li>
	</ol>
</s:form>
<script type="text/javascript">
$("#var-rule").ruleautocomplete({
	showRuleInfo: true
});
$("#purpose").charCounter(2000, {container: "#varianceRequestForm #charCount1"});
$("#healthSafetyInsuredBy").charCounter(4000, {container: "#varianceRequestForm #charCount2"});
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


