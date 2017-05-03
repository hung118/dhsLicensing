<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="ccl" uri="/WEB-INF/tags/ccl-tags.tld"%>
<fieldset>
	<legend>Edit Facility Forms</legend>
	<s:actionerror/>
	<s:fielderror/>
	<s:form id="facilityFormsForm" action="save-forms" method="post" cssClass="ajaxify ccl-action-save {target: '#formsSection'}">
		<s:hidden name="facilityId" value="%{facility.id}"/>
		<s:hidden name="facility.id"/>
		<ol class="fieldList">
			<li class="widercolumn">
				<ol>
					<li>
						<label for="rulesCertRcvd">Rules Certification Received:</label>
						<s:date id="rulesCertRcvdFormatted" name="facility.rulesCertReceived" format="MM/dd/yyyy" />
						<s:textfield id="rulesCertRcvd" name="facility.rulesCertReceived" value="%{rulesCertRcvdFormatted}" cssClass="datepicker" maxlength="10"/>
					</li>
					<li class="clear"></li>
					<li>
						<label for="codeOfConductRcvd">Code of Conduct Received:</label>
						<s:date id="codeOfConductRcvdFormatted" name="facility.codeOfConductReceived" format="MM/dd/yyyy" />
						<s:textfield id="codeOfConductRcvd" name="facility.codeOfConductReceived" value="%{codeOfConductRcvdFormatted}" cssClass="datepicker" maxlength="10"/>
					</li>
					<li class="clear"></li>
					<li>
						<label for="confidentialFormRcvd">Confidential Form Received:</label>
						<s:date id="confidentialFormRcvdFormatted" name="facility.confidentialFormReceived" format="MM/dd/yyyy" />
						<s:textfield id="confidentialFormRcvd" name="facility.confidentialFormReceived" value="%{confidentialFormRcvdFormatted}" cssClass="datepicker" maxlength="10"/>
					</li>
					<li class="clear"></li>
					<li>
						<label for="emergencyPlanRcvd">Emergency Plan Received:</label>
						<s:date id="emergencyPlanRcvdFormatted" name="facility.emergencyPlanReceived" format="MM/dd/yyyy" />
						<s:textfield id="emergencyPlanRcvd" name="facility.emergencyPlanReceived" value="%{emergencyPlanRcvdFormatted}" cssClass="datepicker" maxlength="10"/>
					</li>
					<li class="clear"></li>
					<li>
						<label for="referenceOneRcvd">Reference #1 Received:</label>
						<s:date id="referenceOneRcvdFormatted" name="facility.referenceOneReceived" format="MM/dd/yyyy" />
						<s:textfield id="referenceOneRcvd" name="facility.referenceOneReceived" value="%{referenceOneRcvdFormatted}" cssClass="datepicker" maxlength="10"/>
					</li>
					<li class="clear"></li>
					<li>
						<label for="referenceTwoRcvd">Reference #2 Received:</label>
						<s:date id="referenceTwoRcvdFormatted" name="facility.referenceTwoReceived" format="MM/dd/yyyy" />
						<s:textfield id="referenceTwoRcvd" name="facility.referenceTwoReceived" value="%{referenceTwoRcvdFormatted}" cssClass="datepicker" maxlength="10"/>
					</li>
					<li class="clear"></li>
					<li>
						<label for="referenceThreeRcvd">Reference #3 Received:</label>
						<s:date id="referenceThreeRcvdFormatted" name="facility.referenceThreeReceived" format="MM/dd/yyyy" />
						<s:textfield id="referenceThreeRcvd" name="facility.referenceThreeReceived" value="%{referenceThreeRcvdFormatted}" cssClass="datepicker" maxlength="10"/>
					</li>
					<li class="clear"></li>
					<li class="submit">
						<s:submit id="facilityFormsSubmitBtn" value="Save"/>
						<s:url id="cancelUrl" action="view-forms">
							<s:param name="facilityId" value="facilityId"/>
						</s:url>
						<s:a href="%{cancelUrl}" cssClass="ajaxify {target: '#formsSection'}">
							Cancel
						</s:a>
					</li>
				</ol>
			</li>
		</ol>
	</s:form>
</fieldset>

<script type="text/javascript">
	// *** Short key ctrl-q for current date on fields that have 'Rcvd' in id attribute. ***
	$('[id $= "Rcvd"]').keydown(function(e) {
        //CTRL + Q keydown for current date. Key codes: http://www.cambiaresearch.com/articles/15/javascript-char-codes-key-codes
        if(e.ctrlKey && e.keyCode == 81){
            var currentDate = new Date();
            $(this).val(currentDate.format("mm/dd/yyyy"));
        }
    });
</script>
