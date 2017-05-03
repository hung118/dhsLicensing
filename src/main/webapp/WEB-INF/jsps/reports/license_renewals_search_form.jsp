<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<script type="text/javascript">
$(document).ready(function() {
	$("#listBtn").click(function() {
		$("#licenseRenewalsSearchForm").attr("action", "print-license-renewal-list");
		$("#licenseRenewalsSearchForm").submit();
	});
	$("#letterBtn").click(function() {
		$("#licenseRenewalsSearchForm").attr("action", "print-license-renewal-letters");
		$("#licenseRenewalsSearchForm").submit();
	});
});
</script>
<fieldset>
	<legend>License Renewals By Licenser</legend>
	<s:actionerror/>
	<h1>Information</h1>
	<div class="subSection">
		This report produces a listing of all facilities with licenses that expire on the date specified by <strong>End Date</strong>. 
	</div>
	<h1 style="padding-top:10px;">Report Criteria</h1>
	<div class="subSection">
		Please select the licensor and enter a license expiration end date to report licenses for.  If End Date is left blank, the last day of the current month will be used by default.
	</div>
	<s:form id="licenseRenewalsSearchForm" action="print-license-renewal-list" method="get" cssClass="searchForm">
		<ol class="fieldList">
			<li>
				<label for="licensingSpecialist">Licensor:</label>
				<ol class="fieldGroup">
					<li>
						<s:select id="licensingSpecialist" name="specialistId" list="licensingSpecialists" listKey="id" listValue="firstAndLastName" />
					</li>
				</ol>
			</li>
			<li>
				<label for="licExpEnd">End Date:</label>
				<ol class="fieldGroup">
					<li>
						<s:date id="licExpEndFormatted" name="endDate" format="MM/dd/yyyy" />	
						<s:textfield id="licExpEnd" name="endDate" value="%{licExpEndFormatted}" cssClass="required datepicker" maxlength="10"/>
					</li>
				</ol>
			</li>
			<li class="submit">
				<input id="listBtn" type="button" class="ui-button ui-widget ui-state-default ui-corner-all" value="Print List" />
				<input id="letterBtn" type="button" class="ui-button ui-widget ui-state-default ui-corner-all" value="Print Letters" />
				<s:url id="cancelUrl" action="index" />
				<s:a href="%{cancelUrl}">Return</s:a>
			</li>
		</ol>
	</s:form>
</fieldset>

<script type="text/javascript">
	// *** Short key ctrl-q for current date on field that ends with 'ExpEnd' in id attribute. ***
	$('[id $= "ExpEnd"]').keydown(function(e) {
        //CTRL + Q keydown for current date. Key codes: http://www.cambiaresearch.com/articles/15/javascript-char-codes-key-codes
        if(e.ctrlKey && e.keyCode == 81){
            var currentDate = new Date();
            $(this).val(currentDate.format("mm/dd/yyyy"));
        }
    });
</script>
