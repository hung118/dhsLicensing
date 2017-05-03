<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="ccl" uri="/WEB-INF/tags/ccl-tags.tld"%>
<s:fielderror/>
<s:actionerror/>
<s:form id="individualForm" action="%{action}" namespace="%{namespace}" method="post" cssClass="%{formClass}">
	<s:hidden name="facilityId"/>
	<s:hidden name="screeningId"/>
	<s:hidden name="personId"/>
	<ol class="fieldList">
		<li>
			<label><span class="redtext">* </span>Name:</label>
			<ol class="fieldGroup">
				<li>
					<label for="firstName">First Name:</label>
					<s:textfield id="firstName" name="screening.screenedAsPerson.firstName" cssClass="required name"/>
				</li>
				<li>
					<label for="middleName">Middle Name:</label>
					<s:textfield id="middleName" name="screening.screenedAsPerson.middleName" cssClass="optionalName"/>
				</li>
				<li>
					<label for="lastName">Last Name:</label>
					<s:textfield id="lastName" name="screening.screenedAsPerson.lastName" cssClass="required name"/>
				</li>
				<li class="clearLeft fieldMargin">
					<label for="maidenName">Maiden Name:</label>
					<s:textfield id="maidenName" name="screening.screenedAsPerson.maidenName" cssClass="optionalName"/>
				</li>
				<li class="fieldMargin">
					<label for="previousMarriedNames">Previous Married Names:</label>
					<s:textfield id="previousMarriedNames" name="screening.previousMarriedNames"/>
				</li>
			</ol>
		</li>
		<li>
			<label for="aliases">Aliases:</label>
			<s:textfield id="aliases" name="screening.screenedAsPerson.aliasesString"/>
		</li>
		<li>
			<label for="birthday"><span class="redtext">* </span>Date of Birth:</label>
			<s:date id="birthdayFormatted" name="screening.screenedAsPerson.birthday" format="MM/dd/yyyy"/>
			<s:textfield id="birthday" name="screening.screenedAsPerson.birthday" value="%{birthdayFormatted}" cssClass="required date" maxlength="10"/> (MM/DD/YYYY)
		</li>
		<li>
			<label for="gender"><span class="redtext">* </span>Gender:</label>
			<s:select id="gender" name="screening.screenedAsPerson.gender" list="genders" listValue="displayName" cssClass="required"/>
		</li>
		<li>
			<label for="ssnLastFour">SSN (Last 4):</label>
			<s:textfield id="ssnLastFour" name="ssnLastFour" cssClass="ssnLastFour" cssStyle="%{screening.screenedPerson.ssnLastFour != null ? 'display: none;' : ''}" maxlength="4"/>
			<s:if test="screening.screenedPerson.ssnLastFour != null">
				Last 4 digits of SSN have been entered.
				<a id="update-ssn" href="#">
					Update SSN Last Four
				</a>
			</s:if>
		</li>
		<li>
			<label>Driver's License:</label>
			<ol class="fieldGroup">
				<li>
					<label for="driversLicenseNumber">License #:</label>
					<s:textfield id="driversLicenseNumber" name="screening.driversLicenseNumber"/>
				</li>
				<li>
					<label for="driversLicenseState">State:</label>
					<s:textfield id="driversLicenseState" name="screening.driversLicenseState" cssClass="state" maxlength="2"/>
				</li>
			</ol>
		</li>
		<li>
			<label><span class="redtext">* </span>Address:</label>
			<ccl:address id="individualAdddress" name="screening.screenedAsPerson.address" requiredLabel="true"/>
		</li>
		<li>
			<label for="phone">Phone:</label>
			<s:textfield id="phone" name="screening.screenedAsPerson.homePhone" cssClass="phone"/>
		</li>
		<s:if test="screening.screenedPerson != null and screening.screenedPerson.id != null">
			<li class="checkbox">
				<s:checkbox id="updateCaregiver" name="updateCaregiver"/>
				<label for="updateCaregiver">I would like this person's caregiver record to be updated with the changes made here</label>
			</li>
		</s:if>
		<li class="radio">
			<label for="livedInUtah"><span class="redtext">* </span>Lived in Utah Continuously for the past 5 years?:</label>
			<span class="radio">
				<s:radio id="livedInUtah" name="screening.livedInStateFiveYears" list="yesNoChoices" listKey="value" listValue="displayName"/>
			</span>
		</li>
		<li>
			<label><span class="redtext">* </span>Form Dates:</label>
			<ol class="fieldGroup">
				<li>
					<label for="pmReceivedDate">PM Received Date:</label>
					<s:date id="pmReceivedDateFormatted" name="screening.pmReceivedDate" format="MM/dd/yyyy"/>
					<s:textfield id="pmReceivedDate" name="screening.pmReceivedDate" value="%{pmReceivedDateFormatted}" cssClass="required datepicker" maxlength="10"/>
				</li>
				<li>
					<label for="bcuSentDate">Date Sent to BCU:</label>
					<s:date id="bcuSentDateFormatted" name="screening.bcuSentDate" format="MM/dd/yyyy"/>
					<s:textfield id="bcuSentDate" name="screening.bcuSentDate" value="%{bcuSentDateFormatted}" cssClass="required datepicker" maxlength="10"/>
				</li>
				<li class="clearLeft fieldMargin">
					<label for="bcuReceivedDate">BCU Received Date:</label>
					<s:date id="bcuReceivedDateFormatted" name="screening.bcuReceivedDate" format="MM/dd/yyyy"/>
					<s:textfield id="bcuReceivedDate" name="screening.bcuReceivedDate" value="%{bcuReceivedDateFormatted}" cssClass="required datepicker" maxlength="10"/>
				</li>
			</ol>
		</li>
		<li>
			<label>Incomplete Form:</label>
			<ol class="fieldGroup">
				<li>
					<label for="returnedToProviderDate">Date Returned to Provider:</label>
					<s:date id="returnedToProviderDateFormatted" name="screening.returnedToProviderDate" format="MM/dd/yyyy"/>
					<s:textfield id="returnedToProviderDate" name="screening.returnedToProviderDate" value="%{returnedToProviderDateFormatted}" cssClass="datepicker" maxlength="10"/>
				</li>
				<li>
					<label for="completeFormReceivedDate">Date Complete Form Received:</label>
					<s:date id="completeFormReceivedDateFormatted" name="screening.completeFormReceivedDate" format="MM/dd/yyyy"/>
					<s:textfield id="completeFormReceivedDate" name="screening.completeFormReceivedDate" value="%{completeFormReceivedDateFormatted}" cssClass="datepicker" maxlength="10"/>
				</li>
				<li class="clearLeft fieldMargin">
					<label for="note">Note:</label>
					<s:textarea id="note" name="screening.note"/>
				</li>
			</ol>
		</li>
		<li>
			<label>Fingerprint Cards:</label>
			<ol class="fieldGroup">
				<li>
					<label for="cardMailedDate">Date Card Mailed:</label>
					<s:date id="cardMailedDateFormatted" name="screening.cardMailedDate" format="MM/dd/yyyy"/>
					<s:textfield id="cardMailedDate" name="screening.cardMailedDate" value="%{cardMailedDateFormatted}" cssClass="datepicker" maxlength="10"/>
				</li>
				<li>
					<label for="returnDueDate">Return Due Date:</label>
					<s:date id="returnDueDateFormatted" name="screening.returnDueDate" format="MM/dd/yyyy"/>
					<s:textfield id="returnDueDate" name="screening.returnDueDate" value="%{returnDueDateFormatted}" cssClass="datepicker" maxlength="10"/>
				</li>
				<li class="clearLeft fieldMargin">
					<label for="reminderMailedDate">Date Reminder Mailed:</label>
					<s:date id="reminderMailedDateFormatted" name="screening.reminderMailedDate" format="MM/dd/yyyy"/>
					<s:textfield id="reminderMailedDate" name="screening.reminderMailedDate" value="%{reminderMailedDateFormatted}" cssClass="datepicker" maxlength="10"/>
				</li>
				<li class="fieldMargin">
					<label for="reminderDueDate">Reminder Due Date:</label>
					<s:date id="reminderDueDateFormatted" name="screening.reminderDueDate" format="MM/dd/yyyy"/>
					<s:textfield id="reminderDueDate" name="screening.reminderDueDate" value="%{reminderDueDateFormatted}" cssClass="datepicker" maxlength="10"/>
				</li>
			</ol>
		</li>
		<li>
			<label><span class="redtext">* </span>Screening Information:</label>
			<ol class="fieldGroup">
				<li>
					<label for="screeningType">Screening Type:</label>
					<span class="radio">
						<s:radio id="screeningType" name="screening.screeningType" list="screeningTypes" listValue="displayName"/>
					</span>
				</li>
				<s:if test="lastScreeningDate != null">
					<li>
						<div class="label">Last Screening Date:</div>
						<div class="value"><s:date name="lastScreeningDate" format="MM/dd/yyyy"/></div>
					</li>
				</s:if>
			</ol>
		</li>
		<li class="submit">
			<s:submit value="Save"/>
			<s:a href="%{individualEditCancelUrl}" cssClass="%{cancelClass}">
				Cancel
			</s:a>
		</li>
	</ol>
</s:form>
<script type="text/javascript">
	$("#update-ssn").click(function() {
		if ($(this).hasClass("toggled")) {
			$("#ssnLastFour").hide();
			$(this).html("Update SSN Last Four");
		} else {
			$("#ssnLastFour").show().val("");
			$(this).html("Cancel");
		}
		$(this).toggleClass("toggled");
		return false;
	});
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
