<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<fieldset>
	<legend>Licensor Facility Summary By Licensor</legend>
	<s:actionerror/>
	<h1>Information</h1>
	<div class="subSection">
		This report produces a summary listing of all active status facility licenses for a selected licensor. The report can be filtered to 
		display only licenses that will expire on the date specified by <strong>End Date</strong>. 
	</div>
	<h1 style="padding-top:10px;">Report Criteria</h1>
	<div class="subSection">
		Please select the licensor and enter a license expiration end date to report licenses for.  If End Date is left blank, 
		a listing of all current active licenses will be generated.
	</div>
	<s:form id="licenseRenewalsSearchForm" action="print-facility-license-summary" method="get" cssClass="searchForm">
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
			<li>
				<label for="facLicenseSummarySortBy">Sort By:</label>
				<ol class="fieldGroup">
					<li>
						<s:select id="facLicenseSummarySortBy" name="facLicenseSummarySortBy" list="facilityLicenseSummarySortBys" listKey="key" listValue="label" />
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
