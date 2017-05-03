<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="ccl" uri="/WEB-INF/tags/ccl-tags.tld"%>

<script type="text/javascript">
	$(document).ready(function() {
		$("input[name=facility.type]:radio").change(function() {
			namechg();
		});
		namechg();
	});

	function cpymailing() {
		var add1 = $("#mailingAddress-address-one").val();
		var add2 = $("#mailingAddress-address-two").val();
		var zip = $("#mailingAddress-zip-code").val();
		var city = $("#mailingAddress-city").val();
		var state = $("#mailingAddress-state").val();
		var county = $("#mailingAddress-county").val();

		if ($("#copymailing").attr('checked')) {
			$("#locationAddress-address-one").val(add1);
			$("#locationAddress-address-two").val(add2);
			$("#locationAddress-zip-code").val(zip);
			$("#locationAddress-city").val(city);
			$("#locationAddress-state").val(state);
			$("#locationAddress-county").val(county);
		} else {
			$("#locationAddress-address-one").val("");
			$("#locationAddress-address-two").val("");
			$("#locationAddress-zip-code").val("");
			$("#locationAddress-city").val("");
			$("#locationAddress-state").val("");
			$("#locationAddress-county").val("");
		}
	}

	function namechg() {
		var checkedFacilityType = $("input[name='facility.type']:checked");
		if (checkedFacilityType.size() == 0 || checkedFacilityType.val() == "LICENSE_FOSTER_CARE" || checkedFacilityType.val() == "LICENSE_SPECIFIC_CARE") {
			$("#nameid").hide();
			$("#lfnameid").show();
		} else {
			$("#nameid").show();
			$("#lfnameid").hide();
		}
	}
</script>
<fieldset>
	<legend>Facility Information</legend>
	<s:fielderror/>
	<s:actionerror/>
	<s:form id="createFacilityForm" action="save-new-facility" method="post">
		<ol class="fieldList">
			<li>
				<label for="facilityType"><span class="redtext">* </span>Facility Type:</label>
				<ol class="fieldGroup">
					<li class="radio">
						<s:radio id="facilityType" name="facility.type" value="%{facility.type}" list="facilityTypes" listKey="name()" listValue="displayName" cssClass="required" />
					</li>
				</ol>
			</li>
			<li>
				<label id="nameid" for="facilityName"><span class="redtext">* </span>Name:</label>
				<label id="lfnameid"   for="facilityName"><span class="redtext">* </span>Last, First Name:</label>
				<s:textfield id="facilityName" name="facility.name" cssClass="required"/>
			</li>
			<li class="required">
				<label><span class="redtext">* </span>Phone:</label>
				<ol class="fieldGroup">
					<li>
						<label for="facilityPrimaryPhone"><span class="redtext">* </span>Primary:</label>
						<s:textfield id="facilityPrimaryPhone" name="facility.primaryPhone" cssClass="phone required"/>
					</li>
					<li>
						<label for="facilityAlternatePhone">Alternate Phone:</label>
						<s:textfield id="facilityAlternatePhone" name="facility.alternatePhone" cssClass="phone"/>
					</li>
					<li>
						<label for="facilityFax">Fax:</label>
						<s:textfield id="facilityFax" name="facility.fax" cssClass="phone"/>
					</li>
				</ol>
			</li>
			<li>
				<label for="facilityWebsite">Website URL:</label>
				<s:textfield id="facilityWebsite" name="facility.websiteUrl"/>
			</li>
			<li>
				<label for="facilityEmail"><span class="redtext">* </span>Email:</label>
				<s:textfield id="facilityEmail" name="facility.email"/>
			</li>			
			<li>
				<label><span class="redtext">* </span>Mailing Address:</label>
				<ccl:address id="mailingAddress" name="facility.mailingAddress"/>
			</li>
			<li>
				<label><span class="redtext">* </span>Location Address:</label>
				<ccl:address id="locationAddress" name="facility.locationAddress" requiredLabel="true"/>
				<div class="checkbox">
					<s:checkbox id="copymailing" name="cpymail" onclick="cpymailing()"/>
					<label for="copymailing">Same as Mailing Address</label>
				</div>
			</li>
			<li>
				<label for="licensingSpecialist"><span class="redtext">* </span>Licensing Specialist:</label>
				<s:select id="licensingSpecialist" name="licensingSpecialist" value="licensingSpecialist.id" list="licensingSpecialists"
						  listKey="id" listValue="firstAndLastName" headerKey="-1" headerValue="- Select a Specialist -" cssClass="required"/>
			</li>
			<li class="submit">
				<s:submit value="Save"/>
				<s:url id="cancelUrl" action="index" namespace="/facility/search"/>
				<s:a id="cancel" href="%{cancelUrl}">
					Cancel
				</s:a>
			</li>
		</ol>
	</s:form>
</fieldset>