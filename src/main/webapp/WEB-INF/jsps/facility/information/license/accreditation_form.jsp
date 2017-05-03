<%@ taglib prefix="s" uri="/struts-tags"%>
<s:if test="accreditation != null and accreditation.id != null">
	<s:set var="legendText">Edit</s:set>
</s:if>
<s:else>
	<s:set var="legendText">Create</s:set>
</s:else>
<fieldset>
	<legend><s:property value="%{legendText}"/> Accreditation</legend>
	<s:fielderror/>
	<s:form id="facilityAccreditationForm" action="save-accreditation" method="post" cssClass="ajaxify {target: '#accreditationsSection'} ccl-action-save">
		<s:hidden name="facilityId"/>
		<s:hidden name="accreditation.id"/>
		<ol class="fieldList">
			<li>
				<label for="agency"><span class="redtext">* </span>Agency:</label>
				<s:select id="agency" name="accreditation.agency" value="accreditation.agency.id" list="agencies" listKey="id" listValue="value" headerKey="-1" headerValue="- Select an Agency -" cssClass="required"/>
			</li>
			<li>
				<label>Duration:</label>
				<ol class="fieldGroup">
					<li>
						<label for="accreditationStartDate">Start Date:</label>
						<s:date id="accreditationStartDateFormatted" name="accreditation.startDate" format="MM/dd/yyyy" />
						<s:textfield id="accreditationStartDate" name="accreditation.startDate" value="%{accreditationStartDateFormatted}" cssClass="datepicker" maxlength="10"/>
					</li>
					<li>
						<label for="accreditationEndDate">Expiration Date:</label>
						<s:date id="accreditationEndDateFormatted" name="accreditation.endDate" format="MM/dd/yyyy" />
						<s:textfield id="accreditationEndDate" name="accreditation.endDate" value="%{accreditationEndDateFormatted}" cssClass="datepicker" maxlength="10"/>
					</li>
				</ol>
			</li>
			<li class="submit">
				<s:submit value="Save"/>
				<s:url id="cancelUrl" action="accreditations-list">
					<s:param name="facilityId" value="facilityId"/>
				</s:url>
				<s:a id="cancel" href="%{cancelUrl}" cssClass="ajaxify {target: '#accreditationsSection'}">
					Cancel
				</s:a>
			</li>
		</ol>
	</s:form>
	<div id="accreditationsListSection">
		<s:include value="accreditations_table.jsp"/>
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
