<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>

<s:if test="license != null and license.id != null">
	<s:set var="legendText">Edit</s:set>
</s:if>
<s:else>
	<s:set var="legendText">Create</s:set>
</s:else>

<!-- certificate is not needed -->

<script type="text/javascript">
function showHideAdultYouthCounts() {
	if ($("#ageGroup").val() == null ||  $("#ageGroup").find("option:selected").text() == "" || $("#ageGroup").find("option:selected").text() == "Adult & Youth") {
		// Adult and Youth
		$("#adultCapacityLabel").html("Total Slots:")
		$("#adultCapacity").show();
		$("#adultMaleCountLi").hide();
		$("#adultFemaleCountLi").hide();
		$("#youthCapacity").hide();
	} else 
	if ($("#ageGroup").find("option:selected").text() == "Adult") {
		// Adult
		$("#adultCapacityLabel").html("Adult Total:")
		$("#adultCapacity").show();
		$("#adultMaleCountLi").show();
		$("#adultFemaleCountLi").show();
		$("#youthCapacity").hide();
	} else {
		// Youth
		$("#adultCapacity").hide();
		$("#youthCapacity").show();
	}
}
function showHideRequiredFlags() {
	if ($("#licenseStatus").val() == null || $("#licenseStatus").find("option:selected").text() == "In Process") {
		$("#facilityLicenseForm .lic-required-flag").hide();
	} else {
		$("#facilityLicenseForm .lic-required-flag").show();
	}
}
function confirmBeforeSaving() {
	var licenseNumber = '<s:property value="license.licenseNumber"/>';
	// Redmine 29236
	if (licenseNumber == '') {	// new license
		if ($("#previousLicenseNumber").val() != '') {
			$(".ccl-confirm-save-dialog").dialog({
				resizable: false,
				modal: true,
				closeOnEscape: false,
				buttons: {
					"Continue": function() {
						$(this).dialog("close");
						saveAfterConfirm();
					},
					Cancel: function() {
						$(this).dialog("close");
					}
				}	
			});
		} else {
			saveAfterConfirm();
		}
	} else {	// update license
		// Redmine 29237
		if ($("#licenseStatus").find("option:selected").text() == "Closed") {
			if ($("#isActiveLicenseOnly").val() == 'true') {
				$(".ccl-confirm-save2-dialog").dialog({
					resizable: false,
					modal: true,
					closeOnEscape: false,
					buttons: {
						"Continue": function() {
							$(this).dialog("close");
							saveAfterConfirm();
						},
						Cancel: function() {
							$(this).dialog("close");
						}
					}	
				});				
			} else {
				saveAfterConfirm();
			}
		} else {
			saveAfterConfirm();
		}
	}
}
function saveAfterConfirm() {
	var obj = $("#facilityLicenseForm");
	target = getAjaxTarget(obj);
	var beforeSerialize = obj.metadata().beforeSerialize;
	if (beforeSerialize) {
		beforeSerialize();
	}
	var data = obj.serializeArray();
	$.ajax({
		type: "POST",
		url: obj.attr("action"),
		data: data,
		beforeSend: function() {
			dialogOpen(obj);
		},
		success: function(data) {
			$(target).html(data);
			var input = $(target + " :input").filter(":visible");
			if (input.size() > 0) {
				input.get(0).focus();
			}
			dialogClose();
		}
	});
	return false;
}
</script>
<script type="text/javascript">
$(document).ready(function() {
	$("#licenseStatus").change(function() {
		showHideRequiredFlags();
		
		if ($("#licenseStatus").find("option:selected").text() == "Closed") {
			$("#facilityLicenseForm #closedDate").show();
			$("#facilityLicenseForm #closedReason").show();
		} else {
			$("#facilityLicenseForm #closedDate").hide();
			$("#facilityLicenseForm #closedReason").hide();
		}
	});
	$("#serviceCode").change(function() {
		$.getJSON(context + '/facility/information/license/report-codes.action', {serviceCode: $(this).val()}, function(data) {
			$("#programCode").empty(); // remove all previous options
			$("#programCode").append('<option value="-1">- Select a Program Code -</option>');
			for (var i = 0; i < data.programCodes.length; i++) {
				$("#programCode").append('<option value="' + data.programCodes[i].id + '">' + data.programCodes[i].value + '</option>');
			}
			
			$("#specificServiceCode").empty();
			$("#specificServiceCode").append('<option value="-1">- Select a Specific Service Code -</option>');
			for (var i = 0; i < data.specificServiceCodes.length; i++) {
				$("#specificServiceCode").append('<option value="' + data.specificServiceCodes[i].id + '">' + data.specificServiceCodes[i].value + '</option>');
			}
			
			$("#ageGroup").empty();
			for (var i = 0; i < data.ageGroups.length; i++) {
				$("#ageGroup").append('<option value="' + data.ageGroups[i].id + '">' + data.ageGroups[i].value + '</option>');
			}
			showHideAdultYouthCounts();
		});
	});
	$("#ageGroup").change(function() {
		showHideAdultYouthCounts();
	});
	$("#licenseClosedReason").charCounter(80, {container: "#facilityLicenseForm #lcrCharCount"});
	$("#licenseComment").charCounter(4000, {container: "#facilityLicenseForm #lcCharCount"});
    $("#licenseCertificateComment").charCounter(80, {container: "#facilityLicenseForm #lccCharCount"});

	$('span.radio input:nth-child(13)').css('clear','left'); //Fix wrapping of radio buttons
	
	showHideAdultYouthCounts();
	showHideRequiredFlags();
	
	if ($("#licenseStatus").find("option:selected").text() == "Closed") {
		$("#facilityLicenseForm #closedDate").show();
		$("#facilityLicenseForm #closedReason").show();
	} else {
		$("#facilityLicenseForm #closedDate").hide();
		$("#facilityLicenseForm #closedReason").hide();
	}
});
</script>
<fieldset>
	<legend><s:property value="%{legendText}"/> License</legend>
	<s:actionerror/>
	<s:fielderror/>
	<s:form id="facilityLicenseForm" action="save-license" method="post" cssClass="ajaxify {target: '#licensesSection'} ccl-action-save">
		<s:hidden name="facilityId"/>
		<s:hidden name="license.id"/>
		<s:hidden name="license.licenseNumber"/>
		<ol class="fieldList">
			<s:if test="license.licenseNumber != null">
			    <li>
			    	<div class="label">License Number:</div>
			    	<div class="mediumreadonly"><s:property value="license.licenseNumber"/></div>
		    	</li>
		    </s:if>
		    <s:else>
			    <li>
					<label for="previousLicenseNumber">Previous License Number:</label>
					<s:select id="previousLicenseNumber" name="license.previousLicenseNumber" list="previousLicenseNumbers" listKey="id" listValue="label" headerKey="" headerValue=""  />
		    	</li>		    
		    </s:else>
			<li>
				<label for="licenseStatus">Status:</label>
				<s:select id="licenseStatus" name="license.status" value="license.status.id" list="licenseStatuses" listKey="id" listValue="value" headerKey="-1" headerValue="- Select Status -"/>
			</li>
			<li id="closedDate" style="display: none;">
				<label for="licenseClosedDate">Closed Date:</label>
				<s:date id="licenseClosedDateFormatted" name="license.closedDate" format="MM/dd/yyyy" />
				<s:textfield id="licenseClosedDate" name="license.closedDate" value="%{licenseClosedDateFormatted}" cssClass="datepicker" maxlength="10"/>
			</li>
			<li id="closedReason" style="display: none;">
                <label for="licenseClosedReason">Closed Reason:</label>
                <s:textarea id="licenseClosedReason" name="license.closedReason" />
                <div id="lcrCharCount" class="charCount" style="margin-left:11em;"></div>
            </li>
			<li>
				<label for="licenseSubtype"><span class="lic-required-flag redtext">* </span>License Type:</label>
				<s:select id="licenseSubtype" name="license.subtype" value="license.subtype.id" list="licenseSubtypes" listKey="id" listValue="value" headerKey="-1" headerValue="- Select License Type -" cssClass="required"/>
			</li>
			<li>
				<label for="serviceCode"><span class="lic-required-flag redtext">* </span>Service Code:</label>
				<s:select id="serviceCode" name="license.serviceCode" value="license.serviceCode.id" list="serviceCodes" listKey="id" listValue="value" headerKey="-1" headerValue="- Select a Service Code -" cssClass="required"/>
			</li>
			<li>
				<label for="programCode">Program Codes:</label>
				<s:select id="programCodeIds" name="license.programCodeIds" value="%{license.programCodeIds.{id}}" list="programCodes" listKey="id" listValue="value" multiple="true" size="4" cssClass="multiselect"/>
			</li>
			<li>
				<label for="programCode">Specific Service Code:</label>
				<s:select id="specificServiceCode" name="license.specificServiceCode" value="license.specificServiceCode.id" list="specificServiceCodes" listKey="id" listValue="value" headerKey="-1" headerValue="- Select a Specific Service Code -"/>
			</li>
			<li>
				<label>Age Group:</label>
				<ol class="fieldGroup">
					<li>
						<label for="ageGroup">Age Group:</label>
						<s:select id="ageGroup" name="license.ageGroup" value="license.ageGroup.id" list="ageGroups" listKey="id" listValue="value" />
					</li>
					<li>
						<label for="fromAge">From Age:</label>
						<s:textfield id="fromAge" name="license.fromAge" cssClass="amountField"/>
					</li>
					<li>
						<label for="toAge">To Age:</label>
						<s:textfield id="toAge" name="license.toAge" cssClass="amountField"/>
					</li>
				</ol>
			</li>
			<li>
				<label>Capacity:</label>
				<ol id="adultCapacity" class="fieldGroup">
					<li>
						<label id="adultCapacityLabel" for="adultTotalSlots">Adult Total:</label>
						<s:textfield id="adultTotalSlots" name="license.adultTotalSlots" cssClass="amountField"/>
					</li>
					<li id="adultMaleCountLi">
						<label for="adultMaleCount"># Male:</label>
						<s:textfield id="adultMaleCount" name="license.adultMaleCount" cssClass="amountField"/>
					</li>
					<li id="adultFemaleCountLi">
						<label for="adultFemaleCount"># Female:</label>
						<s:textfield id="adultFemaleCount" name="license.adultFemaleCount" cssClass="amountField"/>
					</li>
				</ol>
				<ol id="youthCapacity" class="fieldGroup">
					<li>
						<label for="youthTotalSlots">Youth Total:</label>
						<s:textfield id="youthTotalSlots" name="license.youthTotalSlots" cssClass="amountField"/>
					</li>
					<li>
						<label for="youthMaleCount"># Male:</label>
						<s:textfield id="youthMaleCount" name="license.youthMaleCount" cssClass="amountField"/>
					</li>
					<li>
						<label for="youthFemaleCount"># Female:</label>
						<s:textfield id="youthFemaleCount" name="license.youthFemaleCount" cssClass="amountField"/>
					</li>
				</ol>
			</li>
			<li>
				<label for="condSanc">Conditional / Sanction:</label>
				<s:select id="condSanc" name="license.condSanc" list="condSancs" listValue="displayName" headerKey="" headerValue="- Select -" />
			</li>
			<li>
				<label for="licenseComment">Comment:</label>
				<s:textarea id="licenseComment" name="license.comment" />
				<div id="lcCharCount" class="charCount" style="margin-left:11em;"></div>
			</li>
			<li>
				<label><span class="redtext">* </span><span class="typeLabel">License</span>:</label>
				<ol class="fieldGroup">
					<li <s:if test="license.facility.type.character == 'F' || license.facility.type.character == 'S'">style="width:15em;"</s:if>>
						<label for="licenseStartDate">Start Date:</label>
						<s:date id="licenseStartDateFormatted" name="license.startDate" format="MM/dd/yyyy" />
						<s:textfield id="licenseStartDate" name="license.startDate" value="%{licenseStartDateFormatted}" cssClass="required datepicker" maxlength="10"/>
					</li>
					<li>
						<label for="licenseExpirationDate">Expiration Date:</label>
						<s:date id="licenseExpirationDateFormatted" name="license.expirationDate" format="MM/dd/yyyy" />
						<s:textfield id="licenseExpirationDate" name="license.expirationDate" value="%{licenseExpirationDateFormatted}" cssClass="required datepicker" maxlength="10"/>
					</li>
				</ol>
			</li>
			<s:if test="license.facility.type.character == 'F' || license.facility.type.character == 'S'">
				<li>
					<label></label>
					<ol class="fieldGroup">
						<li style="width:15em;">
							<label for="licenseAppRcvd">Application Received:</label>
							<s:date id="licenseAppRcvdFormatted" name="license.applicationReceived" format="MM/dd/yyyy" />
							<s:textfield id="licenseAppRcvd" name="license.applicationReceived" value="%{licenseAppRcvdFormatted}" cssClass="datepicker" maxlength="10"/>
						</li>
						<li>
							<label for="licenseSigFormRcvd">Signature Form Received:</label>
							<s:date id="licenseSigFormRcvdFormatted" name="license.signatureFormReceived" format="MM/dd/yyyy" />
							<s:textfield id="licenseSigFormRcvd" name="license.signatureFormReceived" value="%{licenseSigFormRcvdFormatted}" cssClass="datepicker" maxlength="10"/>
						</li>
					</ol>
				</li>
				<li>
					<label></label>
					<ol class="fieldGroup">
						<li style="width:15em;">
							<label for="licenseVoi">Verification of Income Received:</label>
							<s:date id="licenseVoiFormatted" name="license.voiReceived" format="MM/dd/yyyy" />
							<s:textfield id="licenseVoi" name="license.voiReceived" value="%{licenseVoiFormatted}" cssClass="datepicker" maxlength="10"/>
						</li>
						<li>
							<label for="licenseHomeStudyRcvd">Home Study Received:</label>
							<s:date id="licenseHomeStudyRcvdFormatted" name="license.homeStudyReceived" format="MM/dd/yyyy" />
							<s:textfield id="licenseHomeStudyRcvd" name="license.homeStudyReceived" value="%{licenseHomeStudyRcvdFormatted}" cssClass="datepicker" maxlength="10"/>
						</li>
					</ol>
				</li>
				<li>
					<label></label>
					<ol class="fieldGroup">
						<li style="width:15em;">
							<label for="licenseMedRcvd">Medical Received:</label>
							<s:date id="licenseMedRcvdFormatted" name="license.medicalReceived" format="MM/dd/yyyy" />
							<s:textfield id="licenseMedRcvd" name="license.medicalReceived" value="%{licenseMedRcvdFormatted}" cssClass="datepicker" maxlength="10"/>
						</li>
						<li>
							<label for="licenseSpouseMedRcvdRcvd">Spouse Medical Received:</label>
							<s:date id="licenseSpouseMedRcvdFormatted" name="license.spouseMedicalReceived" format="MM/dd/yyyy" />
							<s:textfield id="licenseSpouseMedRcvd" name="license.spouseMedicalReceived" value="%{licenseSpouseMedRcvdFormatted}" cssClass="datepicker" maxlength="10"/>
						</li>
					</ol>
				</li>
				<li>
					<label></label>
					<ol class="fieldGroup">
						<li style="width:15em;">
							<label for="licenseTrainVerified">Training Verified:</label>
							<s:date id="licenseTrainVerifiedFormatted" name="license.trainingVerified" format="MM/dd/yyyy" />
							<s:textfield id="licenseTrainVerified" name="license.trainingVerified" value="%{licenseTrainVerifiedFormatted}" cssClass="datepicker" maxlength="10"/>
						</li>
						<li>
							<label for="licenseSpouseTrainVerified">Spouse Training Verified:</label>
							<s:date id="licenseSpouseTrainVerifiedFormatted" name="license.spouseTrainingVerified" format="MM/dd/yyyy" />
							<s:textfield id="licenseSpouseTrainVerified" name="license.spouseTrainingVerified" value="%{licenseSpouseTrainVerifiedFormatted}" cssClass="datepicker" maxlength="10"/>
						</li>
					</ol>
				</li>
			</s:if>
			<li>
				<label for="twoYear">2 Year:</label>
				<s:checkbox id="twoYear" name="license.twoYear"/>
			</li>
            <li>
                <label for="licenseCertificateComment">Certificate Comment:</label>
                <s:textarea id="licenseCertificateComment" name="license.certificateComment" />
                <div id="lccCharCount" class="charCount" style="margin-left:11em;"></div>
            </li>
			<s:if test="license.id != null">
				<li>
					<label for="finalized">Finalized:</label>
					<s:checkbox id="finalized" name="license.finalized" />
				</li>
			</s:if>
			
			<li class="submit">
				<s:if test="license.isFinalized()">
					<security:authorize access="!hasRole('ROLE_LICENSOR_SPECIALIST')">				
						<input class="ui-button ui-widget ui-state-default ui-corner-all ui-button-text-only" type="button" value="Save" role="button" onclick="confirmBeforeSaving();">
					</security:authorize>							
				</s:if>
				<s:else>
					<input class="ui-button ui-widget ui-state-default ui-corner-all ui-button-text-only" type="button" value="Save" role="button" onclick="confirmBeforeSaving();">
				</s:else>
				
				<s:if test="license.isFinalized()">
					<s:url id="printCertUrl" action="print-license-cert" includeParams="false">
						<s:param name="facilityId" value="facilityId"/>
						<s:param name="license.id" value="license.id"/>
					</s:url>
					<s:a href="%{printCertUrl}" cssClass="ccl-button">
						Print Certificate
					</s:a>
					<s:url id="printLetterUrl" action="print-license-letter" includeParams="false">
						<s:param name="facilityId" value="facilityId"/>
						<s:param name="license.id" value="license.id"/>
					</s:url>
					<s:a href="%{printLetterUrl}" cssClass="ccl-button">
						Print Letter
					</s:a>
				</s:if>
				
				<s:url id="cancelUrl" action="licenses-list">
					<s:param name="facilityId" value="facilityId"/>
				</s:url>
				<s:a id="cancel" href="%{cancelUrl}" cssClass="ajaxify {target: '#licensesSection'}">
					Cancel
				</s:a>
				
				<s:hidden name="isActiveLicenseOnly" id="isActiveLicenseOnly" />
			</li>
		</ol>
	</s:form>
</fieldset>

<script type="text/javascript">
	// redmine 29755
	$("input.datepicker").livequery(function() {
		$(this).datepicker({
			changeMonth: true,
			changeYear: true,
			yearRange: '2004:2024',
			showOtherMonths: true,
			selectOtherMonths: true,
			constrainInput: true,
			dateFormat: 'mm/dd/yy',
			showOn: "button",
			buttonImage: context + "/images/calbtn.gif",
			buttonImageOnly: true});
	});

	// *** Short key ctrl-q for current date on fields that begins with 'license' in id attribute. ***
	$('[id ^= "license"]').keydown(function(e) {
        //CTRL + Q keydown for current date. Key codes: http://www.cambiaresearch.com/articles/15/javascript-char-codes-key-codes
        if(e.ctrlKey && e.keyCode == 81){
            var currentDate = new Date();
            $(this).val(currentDate.format("mm/dd/yyyy"));
        }
    });
</script>
