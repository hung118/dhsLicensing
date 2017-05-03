<%@ taglib prefix="s" uri="/struts-tags"%>
<s:actionerror/>
<s:fielderror/>
<s:form id="incidentForm" action="%{action}" method="post" cssClass="%{formClass}">
	<s:hidden name="facilityId"/>
	<s:hidden name="incidentId"/>
	<ol class="fieldList">
		<li>
			<label for="incidentDate"><span class="redtext">* </span>Incident/Injury Date:</label>
			<s:date id="incidentDateFormatted" name="incident.date" format="MM/dd/yyyy"/>
			<s:textfield id="incidentDate" name="incident.date" value="%{incidentDateFormatted}" cssClass="required datepicker" maxlength="10"/>
		</li>
		<li>
			<label><span class="redtext">* </span>Client:</label>
			<ol class="fieldGroup">
				<li>
					<label for="childFirstName"><span class="redtext">* </span>First Name:</label>
					<s:textfield id="childFirstName" name="incident.child.firstName" cssClass="required name"/>
				</li>
				<li>
					<label for="childLastName"><span class="redtext">* </span>Last Name:</label>
					<s:textfield id="childLastName" name="incident.child.lastName" cssClass="required name"/>
				</li>
				<li class="clearLeft fieldMargin">
					<label for="childAge"><span class="redtext">* </span>Age:</label>
					<s:select id="childAge" name="incident.childAge" value="%{incident.childAge.id}"
							  list="childAges" listKey="id" listValue="value" cssClass="required"/>
				</li>
				<li class="fieldMargin">
					<label for="childGender"><span class="redtext">* </span>Gender:</label>
					<s:select id="childGender" name="incident.child.gender" list="genders" 
							  listValue="displayName" headerKey="-1" headerValue="- Select Gender -" cssClass="required"/>
				</li>	
			</ol>
		</li>
		<li>
			<label><span class="redtext">* </span>Injury:</label>
			<ol class="fieldGroup">
				<li>
					<label for="injuryType"><span class="redtext">* </span>Type:</label>
					<s:select id="injuryType" name="incident.injuryType" value="incident.injuryType.id" list="injuryTypes" listKey="id"
							  listValue="value" headerKey="-1" headerValue="- Select Injury Type -" cssClass="required"/>
				</li>
				<li class="clearLeft fieldMargin">
					<label for="incidentLocations">Location(s) of Incident/Injury (Hold control to select multiples):</label>
					<s:select id="incidentLocations" name="incident.locationsOfInjury" value="%{incident.locationsOfInjury.{id}}"
							  list="incidentLocations" listKey="id" listValue="value" multiple="true" size="4" cssClass="multiselect"/>
				</li>
				<li class="clearLeft fieldMargin">
					<label for="death"><span class="redtext">* </span>Death?</label>
					<span class="radio">
						<s:radio id="death" name="incident.death" list="yesNoChoices" listKey="value" listValue="displayName"/>
					</span>
				</li>
				<li class="clearLeft fieldMargin">
					<label for="receivedTreatment">Did they receive treatment by a medical professional?</label>
					<span class="radio">
						<s:radio id="receivedTreatment" name="incident.receivedTreatment" list="yesNoChoices" listKey="value" listValue="displayName"/>
					</span>
				</li>
				<li class="clearLeft fieldMargin">
					<label for="incidentDescription">Description of How Incident/Injury Occurred:</label>
					<s:textarea id="incidentDescription" name="incident.incidentText"/>
				</li>
				<li class="clearLeft fieldMargin">
					<label for="incidentInjuries">Description of Any Injuries:</label>
					<s:textarea id="incidentInjuries" name="incident.injuryText"/>
				</li>
				<li class="clearLeft fieldMargin">
					<label for="incidentTreatment">Description of Treatment Received:</label>
					<s:textarea id="incidentTreatment" name="incident.treatmentText"/>
				</li>
			</ol>
		</li>
		<li>
			<label><span class="redtext">* </span>License Specialist Notification:</label>
			<ol class="fieldGroup">
				<li>
					<label for="reportedOverPhone"><span class="redtext">* </span>Did the provider call within 24 hours of the accident?</label>
					<span class="radio">
						<s:radio id="reportedOverPhone" name="incident.reportedOverPhone" list="yesNoChoices" listKey="value" listValue="displayName"/>
					</span>
				</li>
				<li class="clearLeft fieldMargin">
					<label for="sentWrittenReport"><span class="redtext">* </span>Did the provider send a written report within 5 days of the accident?</label>
					<span class="radio">
						<s:radio id="sentWrittenReport" name="incident.sentWrittenReport" list="yesNoChoices" listKey="value" listValue="displayName"/>
					</span>
				</li>
			</ol>
		</li>
		<li class="submit">
			<s:submit value="Save"/>
			<s:a id="incidentEditCancel" href="%{incidentEditCancelUrl}" cssClass="%{cancelClass}">
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

