<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="ccl" uri="/WEB-INF/tags/ccl-tags.tld"%>
<script type="text/javascript">
function confirmBeforeSaving() {
	// Redmine 29237 #19
	if ($("#status").find("option:selected").text() == "Inactive") {
		$(".ccl-confirm-save3-dialog").dialog({
			resizable: false,
			modal: true,
			closeOnEscape: false,
			buttons: {
				"Continue": function() {
					$(this).dialog("close");
					$("#facilityInformationForm").submit();
				},
				Cancel: function() {
					$(this).dialog("close");
				}
			}	
		});
	} else {
		$("#facilityInformationForm").submit();
	}
}
</script>
<security:authorize access="hasPermission('edit-details', 'facility')">
	<script type="text/javascript">
		$(document).ready(function() {
			$("#facilityType").change(function() {
				namechg();
			});
			namechg();
			
			if ($("#status").find("option:selected").text() == "Inactive") {
				$("#facilityInformationForm #closedDate").show();
			} else {
				$("#facilityInformationForm #closedDate").hide();
			}
		});

		function namechg() {
			var facilityType = $("#facilityType");
			if (facilityType.size() == 0 || facilityType.val() == "LICENSE_FOSTER_CARE" || facilityType.val() == "LICENSE_SPECIFIC_CARE") {
				$("#nameid").hide();
				$("#lfnameid").show();
			} else {
				$("#nameid").show();
				$("#lfnameid").hide();
			}
		}
	</script>
</security:authorize>
<security:authorize access="!hasPermission('edit-details', 'facility')">
	<script type="text/javascript">
		$(document).ready(function() {
			<s:if test="facility.type == null or facility.type.name().equals('LICENSE_FOSTER_CARE') or facility.type.name().equals('LICENSE_SPECIFIC_CARE')">
				$("#nameid").hide();
				$("#lfnameid").show();
			</s:if>
			<s:else>
				$("#nameid").show();
				$("#lfnameid").hide();
			</s:else>
		});
	</script>
</security:authorize>
<fieldset>
	<legend>Edit Facility Information</legend>
	<s:actionerror/>
	<s:fielderror/>
	<s:form id="facilityInformationForm" action="save-information" method="post" cssClass="ajaxify ccl-action-save {target: '#informationSection'}">
		<s:hidden name="facilityId" value="%{facility.id}"/>
		<s:hidden name="facility.id"/>
		<ol class="fieldList">
			<li>
				<label id="nameid" for="facilityName"><span class="redtext">* </span>Name:</label>
				<label id="lfnameid"   for="facilityName"><span class="redtext">* </span>Last, First Name:</label>
				<s:textfield id="facilityName" name="facility.name" cssClass="required longName"/>
			</li>
			<li>
				<label for="siteName">Site Name:</label>
				<s:textfield id="siteName" name="facility.siteName"/>
			</li>
			<li>
				<label><span class="redtext">* </span>Phone:</label>
				<ol class="fieldGroup">
					<li>
						<label for="primaryPhone">Primary:</label>
						<s:textfield id="primaryPhone" name="facility.primaryPhone" cssClass="phone required"/>
					</li>
					<li>
						<label for="alternatePhone">Site:</label>
						<s:textfield id="alternatePhone" name="facility.alternatePhone" cssClass="phone"/>
					</li>
					<li>
						<label for="fax">Fax:</label>
						<s:textfield id="fax" name="facility.fax" cssClass="phone"/>
					</li>
				</ol>
			</li>
			<li>
				<label for="facilityWebsite">Website URL:</label>
				<s:textfield id="facilityWebsite" name="facility.websiteUrl" cssClass="url"/>
			</li>
			<li>
				<label for="facilityEmail"><span class="redtext">* </span>Email:</label>
				<s:textfield id="facilityEmail" name="facility.email" cssClass="email"/>
			</li>
			<li>
				<label for="facilityCbsEmail">CBS Email:</label>
				<s:textfield id="facilityCbsEmail" name="facility.cbsEmail" cssClass="email"/>
			</li>
            <li>
				<label><span class="redtext">* </span>Mailing Address:</label>
				<ccl:address id="mailingAddress" name="facility.mailingAddress"/>
			</li>
			<li>
				<label>Location Address:</label>
				<ccl:address id="locationAddress" name="facility.locationAddress" requiredLabel="true"/>
			</li>
            <li>
				<label>CBS Address:</label>
				<ccl:address id="cbsAddress" name="facility.cbsAddress"/>
			</li>
			<li>
				<security:authorize access="hasPermission('edit-details', 'facility')">
					<label for="facilityType"><span class="redtext">* </span>Facility Type:</label>
					<s:select id="facilityType" name="facility.type" list="facilityTypes" listValue="displayName" headerKey="" headerValue="- Select -"/>
				</security:authorize>
				<security:authorize access="!hasPermission('edit-details', 'facility')">
					<div class="label"><span class="redtext">* </span>Facility Type:</div>
					<div class="value"><s:property value="facility.type.displayName"/></div>
				</security:authorize>
			</li>
			<li>
				<label for="status"><span class="redtext">* </span>Status:</label>
				<s:select id="status" name="status" list="statuses" listKey="name()" listValue="label" headerKey="" headerValue="- Select -" cssClass="required"/>
			</li>
			<li id="closedDate" style="display: none;">
				<label for="facilityClosedDate">Closed Date:</label>
				<s:date id="facilityClosedDateFormatted" name="facility.closedDate" format="MM/dd/yyyy" />
				<s:textfield id="facilityClosedDate" name="facility.closedDate" value="%{facilityClosedDateFormatted}" cssClass="datepicker" maxlength="10"/>
			</li>
			<li class="lic-field"<s:if test="!status.name().equals('REGULATED') && !status.name().equals('IN_PROCESS')"> style="display: none;"</s:if>>
				<label for="licensingSpecialist"><span class="redtext">*</span>Licensing Specialist:</label>
				<s:select id="licensingSpecialist" name="licensingSpecialist" value="licensingSpecialist.id" list="licensingSpecialists" 
					  listKey="id" listValue="firstAndLastName" headerKey="-1" headerValue="- Select a Specialist -" cssClass="required"/>
			</li>
			<li class="lic-field"<s:if test="!status.name().equals('REGULATED') && !status.name().equals('IN_PROCESS')"> style="display: none;"</s:if>>
				<security:authorize access="hasPermission('edit-details', 'facility')">
					<label for="initialRegulationDate"><span class="redtext lic-required-flag" <s:if test="!facility.status.name().equals('REGULATED')">style="display: none;"</s:if>>* </span>Initial Regulation Date:</label>
					<s:date id="initialRegulationDateFormatted" name="initialRegulationDate" format="MM/dd/yyyy" />
					<s:textfield id="initialRegulationDate" name="initialRegulationDate" value="%{initialRegulationDateFormatted}" cssClass="required datepicker" maxlength="10"/>
				</security:authorize>
				<security:authorize access="!hasPermission('edit-details', 'facility')">
					<div class="label">Initial Regulation Date:</div>
					<div class="value"><s:date name="facility.initialRegulationDate" format="MM/dd/yyyy"/></div>
				</security:authorize>
			</li>
			<li>
				<label for="cbsTechnician">CBS Technician:</label>
				<s:select id="cbsTechnician" name="facility.cbsTechnician" value="facility.cbsTechnician.id" list="cbsTechnicians" 
					  listKey="id" listValue="firstAndLastName" headerKey="-1" headerValue="- Select a Technician -" cssClass="required"/>
			</li>
			<s:if test="!facility.status.name().equals('INACTIVE') && !facility.status.name().equals('IN_PROCESS')">
				<li class="inact-field"<s:if test="!status.name().equals('INACTIVE')"> style="display: none;"</s:if>>
					<label for="reason"><span class="redtext">* </span>Reason:</label>
					<s:select id="reason" name="reason" value="reason.id" list="deactivationReasons" listKey="id" listValue="value" headerKey="-1" headerValue="- Select a Deactivation Reason -" cssClass="required"/>
				</li>
				<li class="inact-field"<s:if test="!status.name().equals('INACTIVE')"> style="display: none;"</s:if>>
					<label for="effectiveDate">Effective Date:</label>
					<s:date id="effectiveDateFormatted" name="effectiveDate" format="MM/dd/yyyy" />
					<s:textfield id="effectiveDate" name="effectiveDate" value="%{effectiveDateFormatted}" cssClass="datepicker" maxlength="10"/>
				</li>
			</s:if>

			<li>
				<label>License Capacity:</label>
				<ol class="fieldGroup">
					<li>
						<label for="indoorSquareFootage">Indoor Square Footage:</label>
						<s:textfield id="indoorSquareFootage" name="facility.indoorSquareFootage" cssClass="amountField" disabled="true"/> sq. ft.
					</li>
					<li>
						<label for="outdoorSquareFootage">Outdoor Square Footage:</label>
						<s:textfield id="outdoorSquareFootage" name="facility.outdoorSquareFootage" cssClass="amountField" disabled="true"/> sq. ft.
					</li>
				</ol>
			</li>
			<li>
				<label for="safeProviderId">SAFE Provider ID:</label>
				<s:textfield id="safeProviderId" name="facility.safeProviderId" maxlength="7" cssClass="amountField" />
			</li>
			<li>
				<label for="noPublicListing">No Public Listing:</label>
				<s:checkbox id="noPublicListing" name="facility.noPublicListing"/>
			</li>
			<li class="submit">
				<security:authorize access="hasPermission('edit-details', 'facility')">
					<input class="ui-button ui-widget ui-state-default ui-corner-all ui-button-text-only" type="button" value="Save" role="button" onclick="confirmBeforeSaving();">
				</security:authorize>
				<s:url id="cancelUrl" action="view-information">
					<s:param name="facilityId" value="facilityId"/>
				</s:url>
				<s:a href="%{cancelUrl}" cssClass="ajaxify {target: '#informationSection'}">
					Cancel
				</s:a>
			</li>
		</ol>
	</s:form>
</fieldset>

<script type="text/javascript">
	$("#facilityInformationForm").submit(function() {
		$("#infoFacilityName").val($("#facilityName").val());
		$("#infoPrimaryPhone").val($("#primaryPhone").val());
		$("#infoEmail").val($("#facilityEmail").val());
		$("#infoMailAddressOne").val($("#mailingAddress-address-one").val());
		$("#infoMailZipCode").val($("#mailingAddress-zip-code").val());
		$("#infoMailCity").val($("#mailingAddress-city").val());
		$("#infoMailState").val($("#mailingAddress-state").val());
		$("#infoFacilityType").val($("#facilityType").val());
		toggleFacilityTabs();
	});
	$("#status").change(function() {
		if ($(this).val() == 'REGULATED' || $(this).val() == 'IN_PROCESS') {
			$("#facilityInformationForm .lic-field").show();
			$("#facilityInformationForm .inact-field").hide();
			if ($(this).val() == 'REGULATED') {
				$("#facilityInformationForm .lic-required-flag").show();
			} else {
				$("#facilityInformationForm .lic-required-flag").hide();
			}
			$("#facilityInformationForm #closedDate").hide();
		} else if ($(this).val() == 'INACTIVE') {
			$("#facilityInformationForm .lic-field").hide();
			$("#facilityInformationForm .inact-field").show();
			$("#facilityInformationForm #closedDate").show();
		} else {
			$("#facilityInformationForm .lic-field").hide();
			$("#facilityInformationForm .inact-field").hide();
			$("#facilityInformationForm #closedDate").hide();
		}
	});
</script>

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
