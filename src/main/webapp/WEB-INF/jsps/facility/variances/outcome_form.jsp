<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<fieldset>
	<legend>Licensing Director Variance Review Outcome</legend>
	<s:actionerror/>
	<s:fielderror/>
	<s:form id="varianceOutcomeForm" action="save-outcome" method="post" cssClass="ajaxify {target: '#outcomeSection'} ccl-action-save">
		<s:hidden name="facilityId"/>
		<s:hidden name="varianceId"/>
		<ol class="fieldList">
			<li>
				<label for="reviewDate"><span class="redtext">* </span>Review Date:</label>
				<s:date id="reviewDateFormatted" name="variance.reviewDate" format="MM/dd/yyyy"/>
				<s:textfield id="reviewDate" name="variance.reviewDate" value="%{reviewDateFormatted}" cssClass="required datepicker" maxlength="10"/>
			</li>
			<li>
				<label for="outcome"><span class="redtext">* </span>Outcome:</label>
				<ol class="fieldGroup">
					<li class="radio">
						<s:radio id="outcome" name="variance.outcome" list="outcomes" listValue="displayName" cssClass="required"/>
					</li>
					<li class="hiddenVarianceApprovedField clearLeft fieldMargin">
						<label for="startDate">Start Date:</label>
						<s:date id="startDateFormatted" name="variance.startDate" format="MM/dd/yyyy"/>
						<s:textfield id="startDate" name="variance.startDate" value="%{startDateFormatted}" cssClass="required datepicker" maxlength="10"/>
					</li>
					<li class="hiddenVarianceApprovedField fieldMargin">
						<label for="endDate">End Date:</label>
						<s:date id="endDateFormatted" name="variance.endDate" format="MM/dd/yyyy"/>
						<s:textfield id="endDate" name="variance.endDate" value="%{endDateFormatted}" cssClass="required datepicker" maxlength="10"/>
					</li>
				</ol>
			</li>
			<li>
				<label for="directorResponse"><span class="redtext">* </span>Response:</label>
				<s:textarea id="directorResponse" name="variance.directorResponse" cssClass="required"/>
				<div id="charCount" class="charCount" style="margin-left:11em;"></div>
			</li>
			<li class="checkbox">
				<s:checkbox id="finalized" name="variance.finalized"/>
				<label for="finalized">I wish to finalize this outcome and send notification to the facility contacts.</label>
			</li>
			<li class="submit">
				<s:submit value="Save"/>
				<s:url id="outcomeEditCancelUrl" action="view-outcome" includeParams="false">
					<s:param name="facilityId" value="facilityId"/>
					<s:param name="varianceId" value="varianceId"/>
				</s:url>
				<s:a href="%{outcomeEditCancelUrl}" cssClass="ajaxify {target: '#outcomeSection'}">
					Cancel
				</s:a>
			</li>
		</ol>
	</s:form>
</fieldset>
<script type="text/javascript">
	initVarianceOutcomeForm();
	$("#directorResponse").charCounter(4000, {container: "#varianceOutcomeForm #charCount"});
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

