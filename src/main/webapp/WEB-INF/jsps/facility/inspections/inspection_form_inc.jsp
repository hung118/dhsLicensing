<%@ taglib prefix="s" uri="/struts-tags"%>
<script type="text/javascript">
	$(document).ready(function() {
		initInspectionEdit(<s:if test="complaintTypeIfComplaintInvestigation != null"><s:property value="complaintTypeIfComplaintInvestigation"/></s:if><s:else>null</s:else>);
	});
	
	function showHide(rdoObj) {
		var obj = document.getElementById("followUpDiv");
		if (obj != null && rdoObj != null) {
			if (rdoObj.value == "true") {
				obj.style.visibility = "hidden";
			} else {
				obj.style.visibility = "visible";
			}
		} else {
			alert("houston we have  problem: rdoObj["+rdoObj+" obj["+obj+"]");
		}
	}
</script>
<s:fielderror/>
<s:form id="inspectionForm" action="%{formAction}" method="post" cssClass="%{formClass}">
	<s:hidden name="facilityId"/>
	<s:hidden name="complaintId"/>
	<s:hidden name="inspectionId"/>
	<ol class="fieldList">
		<li>
			<label for="inspectionDate"><span class="redtext">* </span>Date:</label>
			<s:date id="inspectionDateFormatted" name="inspection.inspectionDate" format="MM/dd/yyyy"/>
			<s:textfield id="inspectionDate" name="inspection.inspectionDate" value="%{inspectionDateFormatted}" cssClass="required datepicker" maxlength="10"/>
		</li>
		<li>
			<label>Type:</label>
			<ol class="fieldGroup">
				<li>
					<label for="primaryInspectionType"><span class="redtext">* </span>Primary Type:</label>
					<s:select id="primaryInspectionType" name="primaryType" value="primaryType != null ? primaryType.id : @gov.utah.dts.det.ccl.view.ViewUtils@getDefaultKeyValuePairId(inspectionTypes).id"
							  list="inspectionTypes" listKey="id" listValue="value" cssClass="required"/>
				</li>
				<!-- 
				<li class="clearLeft fieldMargin">
					<label for="secondaryInspectionTypes">Secondary Type(s) (Hold control to select multiples):</label>
					<s:select id="secondaryInspectionTypes" name="nonPrimaryTypes" value="%{nonPrimaryTypes.{id}}"
							  list="inspectionTypes" listKey="id" listValue="value" multiple="true" size="5"/>
				</li>
				 -->
			</ol>
		</li>
		<li>
			<s:if test="inspectionId != null && inspection.license != null">
				<div class="label">License:</div>
				<div class="value"><s:property value="inspection.license.licenseListDescriptor"/></div>
			</s:if>
			<s:else>
				<label for="inspectionLicense"><span class="redtext">* </span>License:</label>
				<s:select id="inspectionLicense" name="licenseId" value="licenseId"
					  list="activeLicenses" listKey="id" listValue="licenseListDescriptor" headerKey="-1" headerValue="- Select a License -"
					  cssClass="required"/>
			</s:else>
		</li>
		<li>
			<label>Specialists:</label>
			<ol class="fieldGroup">
				<li>
					<label for="primaryLicensingSpecialist"><span class="redtext">* </span>Primary Licensing Specialist:</label>
					<s:select id="primaryLicensingSpecialist" name="primarySpecialist" value="primarySpecialist.id"
							  list="licensingSpecialists" listKey="id" listValue="firstAndLastName" headerKey="-1" headerValue="- Select a Specialist -"
							  cssClass="required"/>
				</li>
				<li class="clearLeft fieldMargin">
					<label for="secondLicensingSpecialist">Second Licensing Specialist:</label>
					<s:select id="secondLicensingSpecialist" name="secondSpecialist" value="secondSpecialist.id"
							  list="licensingSpecialists" listKey="id" listValue="firstAndLastName" headerKey="-1" headerValue="- Select a Specialist -"/>
				</li>
				<li class="clearLeft fieldMargin">
					<label for="thirdLicensingSpecialist">Third Licensing Specialist:</label>
					<s:select id="thirdLicensingSpecialist" name="thirdSpecialist" value="thirdSpecialist.id"
							  list="licensingSpecialists" listKey="id" listValue="firstAndLastName" headerKey="-1" headerValue="- Select a Specialist -"/>
				</li>
			</ol>
		</li>
		<li>
			<!-- 
			<label><span class="redtext">* </span>Duration:</label>
			<ol class="fieldGroup">
				<li>
					<label for="arrivalTime">Arrival Time:</label>
					<s:if test="inspection.arrivalTime != null">
						<s:date var="arrivalTimeFormatted" name="inspection.arrivalTime" format="hh:mm a"/>
					</s:if>
					<s:else>
						<s:set var="arrivalTimeFormatted" value="%{'08:00 AM'}"/>
					</s:else>
					<s:select id="arrivalTime" name="inspection.arrivalTime"
							  value="%{arrivalTimeFormatted}" list="times" listKey="name"
							  listValue="value" headerKey="-1" headerValue="- Select a Time -" cssClass="required"/>
				</li>
				<li>
					<label for="departureTime">Departure Time:</label>
					<s:if test="inspection.departureTime != null">
						<s:date var="departureTimeFormatted" name="inspection.departureTime" format="hh:mm a"/>
					</s:if>
					<s:else>
						<s:set var="departureTimeFormatted" value="%{'08:00 AM'}"/>
					</s:else>
					<s:select id="departureTime" name="inspection.departureTime"
							  value="%{departureTimeFormatted}" list="times" listKey="name"
							  listValue="value" headerKey="-1" headerValue="- Select a Time -" cssClass="required"/>
				</li>
				 -->
			</ol>
		</li>
		<li>
			<label for="hasFindings"><span class="redtext">* </span>Findings:</label>
			<!--
			<ol class="fieldGroup">
				<li class="radio">
					<s:radio id="hasFindings" name="inspection.hasFindings" list="yesNoChoices" listKey="value" listValue="displayName"/>
				</li>
			</ol>
			-->
			<input type="hidden" name="inspection.hasFindings" value="false" />
			 <textarea name="inspection.findingsComment" cols="70" rows="5"><s:property value="inspection.findingsComment"/></textarea>
		</li>
		<li>
				<ol class="fieldGroup">
					<li class="radio">
					<label for="Resolved"><span class="redtext">* </span>
						Resolved:
					</label>
						<s:radio id="resolved" name="inspection.resolved" list="yesNoChoices" listKey="value" listValue="displayName" onclick="showHide(this)"/>
					</li>
				</ol>
				<div id="followUpDiv"
				<s:if test="inspection.resolved == true">
					 style="visibility:hidden"
				</s:if>
				>
				<br>
				&nbsp;
				<label for="followupComment" style="text-align:left;">Follow Up:</label>
				<textarea name="inspection.followupComment" cols="70" rows="5"><s:property value="inspection.followupComment"/></textarea>
				</div>
		</li>
		<li class="submit">
			<s:submit value="Save"/>
			<s:a href="%{inspectionEditCancelUrl}" cssClass="%{cancelClass}">
				Cancel
			</s:a>
		</li>
	</ol>
</s:form>

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


