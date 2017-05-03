<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<fieldset>
	<legend>Live Scans Issued By CBS Technician</legend>
	<s:actionerror/>
	<s:fielderror/>
	<h1>Information</h1>
	<div class="subSection">
		This report produces a summary listing of all Live Scans issued for a selected CBS Technician. The report is
		filtered to display only Live Scans that were issued between <Strong>Start Date</Strong> and <strong>End Date</strong>. 
	</div>
	<h1 style="padding-top:10px;">Report Criteria</h1>
	<div class="subSection">
		Please select the CBS Technician and enter start and end dates to report Live Scans for.
	</div>
	<s:form id="livescansIssuedSearchForm" action="validate-livescans-issued" method="get" cssClass="searchForm ajaxify {target: '#maincontent'}">
		<ol class="fieldList">
			<li>
				<label for="technicianId"><span class="redtext">* </span>Technician:</label>
				<ol class="fieldGroup">
					<li>
						<s:select id="technicianId" name="technicianId" list="cbsTechnicians" listKey="id" listValue="firstAndLastName" />
					</li>
				</ol>
			</li>
			<li>
				<label for="startDate"><span class="redtext">* </span>Start Date:</label>
				<ol class="fieldGroup">
					<li>
						<s:date id="startDateFormatted" name="startDate" format="MM/dd/yyyy" />	
						<s:textfield id="startDate" name="startDate" value="%{startDateFormatted}" cssClass="required datepicker" maxlength="10"/>
					</li>
				</ol>
			</li>
			<li>
				<label for="endDate"><span class="redtext">* </span>End Date:</label>
				<ol class="fieldGroup">
					<li>
						<s:date id="endDateFormatted" name="endDate" format="MM/dd/yyyy" />	
						<s:textfield id="endDate" name="endDate" value="%{endDateFormatted}" cssClass="required datepicker" maxlength="10"/>
					</li>
				</ol>
			</li>
			<li class="submit">
				<s:submit value="Print"/>
				<s:url id="cancelUrl" action="index" />
				<s:a href="%{cancelUrl}">Return</s:a>
			</li>
		</ol>
	</s:form>
</fieldset>
<s:if test="validated">
	<s:form id="livescansIssuedPrintForm" action="print-livescans-issued">
		<s:hidden name="technicianId"/>
		<s:hidden name="startDate"/>
		<s:hidden name="endDate"/>
	</s:form>
	<script type="text/javascript">
		$(document).ready(function() {
			$('#livescansIssuedPrintForm').submit();
		});
	</script>
</s:if>

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

