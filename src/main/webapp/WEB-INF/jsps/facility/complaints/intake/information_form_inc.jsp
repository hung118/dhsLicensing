<%@ taglib prefix="s" uri="/struts-tags"%>
<s:fielderror/>
<s:actionerror/>
<s:form id="complaintInformationForm" action="%{formAction}" method="post" cssClass="%{formClass}">
	<s:hidden name="facilityId"/>
	<s:hidden name="complaintId"/>
	<ol class="fieldList">
		<li>
			<label><span class="redtext">* </span>Received:</label>
			<ol class="fieldGroup">
				<li>
					<label for="dateReceived">Date:</label>
					<s:date id="dateReceivedFormatted" name="dateReceived" format="MM/dd/yyyy"/>
					<s:textfield id="dateReceived" name="dateReceived" value="%{dateReceivedFormatted}" cssClass="required datepicker" maxlength="10"/>
				</li>
				<li>
					<label for="timeReceived">Time:</label>
					<s:date id="timeReceivedFormatted" name="timeReceived" format="hh:mm a" />
					<s:textfield id="timeReceived" name="timeReceived" value="%{timeReceivedFormatted}" cssClass="required time" maxlength="8"/> (HH:MM AM/PM)
				</li>
			</ol>
		</li>
		<li>
			<label for="deliveryMethod"><span class="redtext">* </span>Delivery Method:</label>
			<ol class="fieldGroup">
				<li class="radio">
					<s:radio id="deliveryMethod" name="complaint.deliveryMethod" list="deliveryMethods" listKey="name()" listValue="label"/>
				</li>
			</ol>
		</li>
		<s:if test="!unlicensed">
			<li><strong>Disclosure Statement</strong></li>
			<li>
				If a person knowingly gives false information to the Office of Licensing for the purposes of changing the status of a Human
				Service program license, the person giving the false information may be guilty of a Class B Misdemeanor.  It is not necessary
				for the complainant to be able to "prove" that the information being shared is true, only that they not knowingly provide
				false information.
			</li>
			<li class="checkbox">
				<s:checkbox id="disclosureStatementRead" name="complaint.disclosureStatementRead"/>
				<label for="disclosureStatementRead">I have read the disclosure statement to the complainant.</label>
			</li>
		</s:if>
		<s:if test="facility.licensingSpecialist != null">
			<li>
				<label style="width: auto;">Facility Licensing Specialist:</label>
				<span class="value"><s:property value="facility.licensingSpecialist.firstAndLastName"/></span>
			</li>
		</s:if>

			<s:if test="%{#formAction eq 'save-information'}">	
				<li>
					<label for="conclusionType">Conclusion:</label>
					<s:select id="conclusionType" name="complaint.screening.conclusionType" value="complaint.screening.conclusionType.id" list="conclusionTypes"
					listKey="id" listValue="value" headerKey="-1" headerValue="- Select Conclusion -" />
				</li>
			</s:if>
			<s:else>
				<li>
					<label for="conclusionType">Conclusion:</label>
					<s:select id="conclusionType" name="conclusionType" value="complaint.screening.conclusionType.id" list="conclusionTypes"
					listKey="id" listValue="value" headerKey="-1" headerValue="- Select Conclusion -" />
				</li>
			</s:else>	
			
		<li class="submit">
			<security:authorize access="isAuthenticated() and principal.isInternal()">
				<s:submit value="Save"/>
			</security:authorize>
			<s:a href="%{complInfoEditCancelUrl}" cssClass="%{cancelClass}">
				Cancel
			</s:a>
		</li>
	</ol>
</s:form>

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
