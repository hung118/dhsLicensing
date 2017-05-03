<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<fieldset>
	<legend>Expired Licenses By Licenser</legend>
	<s:actionerror/>
	<h1>Information</h1>
	<div class="subSection">
		This report produces a listing of all facilities with licenses that are past due for renewal. 
	</div>
	<h1 style="padding-top:10px;">Report Criteria</h1>
	<div class="subSection">
		Please select the licenser to report expired licenses for.
	</div>
	<s:form id="expiredLicensesSearchForm" action="print-expired-licenses" method="get" cssClass="searchForm">
		<ol class="fieldList">
			<li>
				<label for="licensingSpecialist">Licensor:</label>
				<ol class="fieldGroup">
					<li>
						<s:select id="licensingSpecialist" name="specialistId" list="licensingSpecialists" listKey="id" listValue="firstAndLastName" />
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