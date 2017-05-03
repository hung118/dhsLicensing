<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<s:set var="regOnly" value="%{false}"/>
<security:authorize access="!principal.isInternal()">
	<s:set var="regOnly" value="%{true}"/>
</security:authorize>
<s:if test="!#attr.regOnly">
	<script type="text/javascript">
		$(document).ready(function() {
			$("#facStatus").change(function(){
				showHideStatusFields();
			});
         
			showHideStatusFields();
			$("#facilityName").focus();                 
		});
		
		function showHideStatusFields() {
			$("#regulatedOptions").hide();  
			$("#exemptOptions").hide(); 
			$("#inProcOptions").hide();
			$("#inactiveOptions").hide();
			$("#unlicensedOptions").hide();
			//  Set the old check box values to false       
			$('#includeRegulated').val('false');
			$('#includeExempt').val('false');
			$('#includeInProcess').val('false');
			$('#includeInactive').val('false');
			$('#includeUnlicensed').val('false');

			var value = $("#facStatus").val();

			if (value == "REGULATED") {    
				$("#regulatedOptions").show();
				// set the old checkbox value to true to match the new dropdown list selected value      
				$('#includeRegulated').val('true');
			}
			if (value == "EXEMPT"){
				$("#exemptOptions").show();
				$('#includeExempt').val('true');
			}
			if (value == "IN_PROCESS"){
				$("#inProcOptions").show(); 
				$('#includeInProcess').val('true');
			}
			if (value == "INACTIVE"){
				$("#inactiveOptions").show();
				$('#includeInactive').val('true');
			}
		}
	</script>
</s:if>
<fieldset>
	<legend>Search</legend>
	<s:actionerror/>
	<h1>Search Providers Alphabetically</h1>
	<div class="subSection">
		<s:iterator value="{'A','B','C','D','E','F','G','H','I','J','K','L','M','N','O','P','Q','R','S','T','U','V','W','X','Y','Z'}" status="row">
			<s:url id="searchUrl" action="search-results">
				<s:param name="fName"><s:property value="top"/></s:param>
				<s:param name="incReg" value="true"/>
				<security:authorize access="principal.isInternal()">
					<s:param name="incExe" value="true"/>
					<s:param name="incInact" value="true"/>
					<s:param name="incInProc" value="true"/>
					<s:param name="incUnlic" value="true"/>
				</security:authorize>
				<s:param name="nmSrchType" value="%{'STARTS_WITH'}"/>
			</s:url>
			<s:a href="%{searchUrl}">
				<s:property value="top"/>
			</s:a>
			<s:if test="!#row.last">|</s:if>
		</s:iterator>
	</div>
	<h1>Search By Criteria</h1>
	<div class="subSection">
		Please enter the search criteria.  For dropdowns that allow multiple selections you can hold control while clicking on the item
		to select or deselect it.
	</div>
	<s:form id="facilitySearchForm" action="search-results" method="get" cssClass="searchForm">
		<s:if test="regOnly">
			<s:hidden name="incReg" value="%{'true'}"/>
		</s:if>
		<ol class="fieldList">
			<li>
				<label for="facilityName">Name:</label>
				<ol class="fieldGroup">
				<li>
					<s:textfield name="fName" id="facilityName" cssClass="longName"/>
				</li>
				<li>
					<span class="radio">
						<s:radio name="nmSrchType" list="nameSearchTypes" listValue="displayName"/>
					</span>
				</li>
				</ol>
			</li>
			<li>
				<label for="siteName">Site Name:</label>
				<ol class="fieldGroup">
				<li>
					<s:textfield name="siteName" id="siteName" cssClass="longName"/>
				</li>
				<li>
					<span class="radio">
						<s:radio name="siteNmSrchType" list="nameSearchTypes" listValue="displayName"/>
					</span>
				</li>
				</ol>
			</li>
			<li>
				<label>Location:</label>
				<ol class="fieldGroup">
					<security:authorize access="principal.isInternal()">
						<li>
							<label for="regions">Region:</label>
							<s:select id="regions" name="rId" list="regions" listKey="id" listValue="name" headerKey="-1" headerValue="All"/>
						</li>
					</security:authorize>
					<li class="clearLeft fieldMargin">
						<label for="counties">County:</label>
						<s:textfield name="county"/>
					</li>
					<li class="fieldMargin">
						<label for="city">City:</label>
						<s:textfield name="city"/>
					</li>
					<li class="fieldMargin">
						<label for="zipCode">Zip Code:</label>
						<s:textfield name="zip"/>
					</li>
				</ol>
			</li>
			<li>
				<label for="facilityType">Facility Type:</label>
				<s:select id="facilityType" name="facType" list="facilityTypes" listKey="name()" listValue="displayName" headerKey="Both" headerValue="All"/>
			</li>
			<li>
				<label for="facStatus">Facility Status:</label>
<s:if test="!#attr.regOnly">
				<s:select id="facStatus" name="facStatus" list="facilityStatuses" listKey="name()" listValue="label" headerKey="" headerValue="All" />
</s:if>
<s:else>
				<s:select id="facStatus" name="facStatus" list="facilityStatuses" listKey="name()" listValue="label" />
</s:else>
			</li>
			<s:hidden id="includeRegulated" name="incReg" value = "false"/> 
			<s:hidden id="includeExempt" name="incExe" value = "false"/> 
			<s:hidden id="includeInProcess" name="incInProc" value = "false"/> 
			<s:hidden id="includeInactive" name="incInact" value = "false"/> 
			<s:hidden id="includeUnlicensed" name="incUnlic" value = "false"/> 
			<li>
				<ol class="fieldGroup">
					<li>
						<ol id="regulatedOptions" class="fieldGroup borderedGroup"<s:if test='!#attr.regOnly'> style="display: none;"</s:if>>
							<li>
								<label for="licenseStatuses">License Status:</label>
								<s:select id="licenseStatuses" name="licStatusIds" list="licenseStatuses" listKey="id" listValue="value" multiple="true" size="5" cssClass="multiselect"/>
							</li>
							<li>
								<label for="licenseServiceCodes">Service Code:</label>
								<s:select id="licenseServiceCodes" name="licServiceCodeIds" list="licenseServiceCodes" listKey="id" listValue="value" multiple="true" size="5" cssClass="multiselect"/>
							</li>
							<li>
								<label for="licenseTypes">License Type:</label>
								<s:select id="licenseTypes" name="licTypeIds" list="licenseTypes" listKey="id" listValue="value" multiple="true" size="5" cssClass="multiselect"/>
							</li>
							<li class="clearLeft fieldMargin checkbox">
								<s:checkbox id="conditional" name="cond"/><label for="conditional">Is conditional</label>
							</li>
							<li class="clearLeft fieldMargin">
								<label for="licenseExpRange">License Expiration Between:</label>
								<ol class="fieldGroup">
									<li>
										<label for="licExpRangeStartDate">Start Date:</label>
										<s:date id="licExpRangeStartFormatted" name="licExpRangeStart" format="MM/dd/yyyy" />
										<s:textfield id="licExpRangeStartDate" name="licExpRangeStart" value="%{licExpRangeStartFormatted}" cssClass="datepicker" maxlength="10"/>
									</li>
									<li>
										<label for="licExpRangeEndDate">End Date:</label>
										<s:date id="licExpRangeEndFormatted" name="licExpRangeEnd" format="MM/dd/yyyy" />
										<s:textfield id="licExpRangeEndDate" name="licExpRangeEnd" value="%{licExpRangeEndFormatted}" cssClass="datepicker" maxlength="10"/>
									</li>
								</ol>
							</li>
<security:authorize access="principal.isInternal()">
							<li class="clearLeft fieldMargin">
								<label for="licensingSpecialists">Licensing Specialist:</label>
								<s:select id="licensingSpecialists" name="lsId" list="licensingSpecialists" listKey="id" listValue="firstAndLastName" headerKey="-1" headerValue="All"/>
							</li>
</security:authorize>
						</ol>
					</li>
<security:authorize access="principal.isInternal()">
					<li class="clearLeft fieldMargin">
						<ol id="exemptOptions" class="fieldGroup borderedGroup" style="display: none;">
							<li>
								<label for="exemptions">Reason(s) For Exemption:</label>
								<s:select id="exemptions" name="exeIds" list="exemptions" listKey="id" listValue="value" multiple="true" size="5" disabled="false" theme="simple" cssClass="multiselect"/>
							</li>
						</ol>
					</li>
					<li class="clearLeft fieldMargin"></li>
					<li class="clearLeft fieldMargin">
						<ol id="inactiveOptions" class="fieldGroup borderedGroup" style="display: none;">
							<li class="clearLeft fieldMargin">
								<label for="">Inactive Reason(s):</label>
								<s:select id="inactiveReasons" name="inactReasIds" list="inactiveReasons" listKey="id" listValue="value" multiple="true" size="5" cssClass="multiselect"/>
							</li>
						</ol>
					</li>
					<li class="clearLeft fieldMargin"></li>
</security:authorize>
				</ol>
			</li>
			<li class="submit">
				<s:submit value="Search"/>
				<security:authorize access="hasClassPermission('create', 'Facility')">
					<s:url id="newFacilityUrl" action="create-facility" namespace="/facility"/>
					<s:a href="%{newFacilityUrl}" cssClass="ccl-button">
						New Facility
					</s:a>
				</security:authorize>
			</li>
		</ol>
	</s:form>
</fieldset>

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

