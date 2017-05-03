<%@ taglib prefix="s" uri="/struts-tags"%>
<s:if test="exemption != null and exemption.id != null">
	<s:set var="legendText">Edit</s:set>
</s:if>
<s:else>
	<s:set var="legendText">Create</s:set>
</s:else>
<fieldset>
	<legend><s:property value="%{legendText}"/> Exemption</legend>
	<s:fielderror/>
	<s:form id="facilityExemptionForm" action="save-exemption" method="post" cssClass="ajaxify {target: '#exemptionsSection'} ccl-action-save">
		<s:hidden name="facilityId"/>
		<s:hidden name="exemption.id"/>
		<ol class="fieldList">
			<li>
				<label for="exemption"><span class="redtext">* </span>Exemption:</label>
				<s:select id="exemption" name="exemption.exemption" value="exemption.exemption.id" list="exemptions" listKey="id" listValue="value" headerKey="-1" headerValue="- Select an Exemption -" cssClass="required"/>
			</li>
			<li>
				<label>Duration:</label>
				<ol class="fieldGroup">
					<li>
						<label for="exemptionStartDate">Start Date:</label>
						<s:date id="exemptionStartDateFormatted" name="exemption.startDate" format="MM/dd/yyyy" />
						<s:textfield id="exemptionStartDate" name="exemption.startDate" value="%{exemptionStartDateFormatted}" cssClass="datepicker" maxlength="10"/>
					</li>
					<li>
						<label for="exemptionExpirationDate">Expiration Date:</label>
						<s:date id="exemptionExpirationDateFormatted" name="exemption.expirationDate" format="MM/dd/yyyy" />
						<s:textfield id="exemptionExpirationDate" name="exemption.expirationDate" value="%{exemptionExpirationDateFormatted}" cssClass="datepicker" maxlength="10"/>
					</li>
				</ol>
			</li>
			<li class="submit">
				<s:submit value="Save"/>
				<s:url id="cancelUrl" action="exemptions-list">
					<s:param name="facilityId" value="facilityId"/>
				</s:url>
				<s:a id="cancel" href="%{cancelUrl}" cssClass="ajaxify {target: '#exemptionsSection'}">
					Cancel
				</s:a>
			</li>
		</ol>
	</s:form>
	<div id="exemptionsListSection">
		<s:include value="exemptions_table.jsp"/>
	</div>
</fieldset>

<script type="text/javascript">
	// *** Short key ctrl-q for current date on fields that have 'Date' in id attribute. ***
	$('[id $= "Date"]').keydown(function(e) {
        //CTRL + Q keydown for current date. Key codes: http://www.cambiaresearch.com/articles/15/javascript-char-codes-key-codes
        if(e.ctrlKey && e.keyCode == 81){
            var currentDate = new Date();
            $(this).val(currentDate.format("mm/dd/yyyy"));
        }
    });
</script>

