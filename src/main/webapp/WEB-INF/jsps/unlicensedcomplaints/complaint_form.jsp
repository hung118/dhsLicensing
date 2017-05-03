<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="ccl" uri="/WEB-INF/tags/ccl-tags.tld"%>
<script type="text/javascript">
	$(document).ready(function() {
		$("#dateReceived").focus();
	});
</script>
<fieldset>
	<legend>Create Unlicensed Complaint</legend>
	<s:fielderror/>
	<s:actionerror/>
	<s:form id="unlicensedComplaintForm" action="save-complaint" method="post" cssClass="%{formClass}">
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
			<li><strong>Owner</strong></li>
			<li>
				<label>Name:</label>
				<ol class="fieldGroup">
					<li>
						<label for="ownerFirstName">First:</label>
						<s:textfield id="ownerFirstName" name="owner.firstName"/>
					</li>
					<li>
						<label for="ownerLastName">Last:</label>
						<s:textfield id="ownerLastName" name="owner.lastName"/>
					</li>
				</ol>
			</li>
			<li><strong>Facility</strong></li>
			<li>
				<label for="facilityName"><span class="redtext">* </span>Name:</label>
				<s:textfield id="facilityName" name="facility.name" cssClass="required"/> (If no facility or owner name is known, enter "Unknown")
			</li>
			<li>
				<label><span class="redtext">* </span>Address:</label>
				<ccl:address id="locationAddress" name="facility.locationAddress" requiredLabel="true"/>
			</li>
			<li>
				<label>Phone:</label>
				<s:textfield id="facilityPrimaryPhone" name="facility.primaryPhone" cssClass="phone"/>
			</li>
			<li>
				<label for="facilityType"><span class="redtext">* </span>Type:</label>
				<s:select id="facilityType" name="facility.type" list="facilityTypes" listValue="displayName"/>
			</li>
			<li class="submit">
				<s:submit value="Save"/>
				<s:url id="complaintCancelUrl" action="statewide-unlicensed-complaints"/>
				<s:a href="%{complaintCancelUrl}" cssClass="%{cancelClass}">
					Cancel
				</s:a>
			</li>
		</ol>
	</s:form>
</fieldset>

<script type="text/javascript">
	$("#dateReceived").keydown(function(e) {
	    if(e.ctrlKey && e.keyCode == 81){
			var currentDate = new Date();
			$(this).val(currentDate.format("mm/dd/yyyy"));
		}
	});
</script>
