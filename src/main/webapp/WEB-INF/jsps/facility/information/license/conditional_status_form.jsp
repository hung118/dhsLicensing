<%@ taglib prefix="s" uri="/struts-tags"%>
<s:if test="conditional != null and conditional.id != null">
	<s:set var="legendText">Edit</s:set>
</s:if>
<s:else>
	<s:set var="legendText">Create</s:set>
</s:else>
<fieldset>
	<legend><s:property value="%{legendText}"/> Conditional License/Certificate</legend>
	<s:actionerror/>
	<s:fielderror/>
	<s:form id="conditionalStatusForm" action="save-conditional-status" method="post" cssClass="ajaxify {target: '#conditionalStatusesSection'} ccl-action-save">
		<s:hidden name="facilityId"/>
		<s:hidden name="conditionalId"/>
		<ol class="fieldList">
			<li>
				<label>Duration:</label>
				<ol class="fieldGroup">
					<li>
						<label for="conditionalStatusStartDate"><span class="redtext"> *</span>Start Date:</label>
						<s:date id="conditionalStatusStartDateFormatted" name="conditional.startDate" format="MM/dd/yyyy" />
						<s:textfield id="conditionalStatusStartDate" name="conditional.startDate" value="%{conditionalStatusStartDateFormatted}" cssClass="datepicker required" maxlength="10"/>
					</li>
					<li>
						<label for="conditionalStatusEndDate"><span class="redtext"> *</span>Expiration Date:</label>
						<s:date id="conditionalStatusEndDateFormatted" name="conditional.expirationDate" format="MM/dd/yyyy" />
						<s:textfield id="conditionalStatusEndDate" name="conditional.expirationDate" value="%{conditionalStatusEndDateFormatted}" cssClass="datepicker required" maxlength="10"/>
					</li>
				</ol>
			</li>
			<li class="submit">
				<s:submit value="Save"/>
				<s:url id="cancelUrl" action="conditional-statuses-list">
					<s:param name="facilityId" value="facilityId"/>
				</s:url>
				<s:a id="cancel" href="%{cancelUrl}" cssClass="ajaxify {target: '#conditionalStatusesSection'}">
					Cancel
				</s:a>
			</li>
		</ol>
	</s:form>
	<div id="conditionalStatusesListSection">
		<s:include value="conditional_statuses_table.jsp"/>
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

