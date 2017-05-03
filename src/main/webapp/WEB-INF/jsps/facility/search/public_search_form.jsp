<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<script type="text/javascript">
	$(document).ready(function() {
		$("#facilityName").focus();
	});
</script>
<fieldset>
	<legend>Search</legend>
	<div class="subSection">
		Please enter the search criteria.  For dropdowns that allow multiple selections you can hold control while clicking on the item
		to select or deselect it.
	</div>
	<s:form id="facilitySearchForm" action="search-results" method="get" cssClass="searchForm">
		<ol class="fieldList">
			<li>
				<label for="facilityName">Name:</label>
				<s:textfield name="fName" id="facilityName" cssClass="longName"/>
			</li>
			<li>
				<label>Location:</label>
				<ol class="fieldGroup">
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
				<label for="licenseTypes">Provider Type:</label>
				<s:select id="licenseTypes" name="licTypeIds" list="licenseTypes" listKey="id" listValue="value"
						  multiple="true" size="5"/>
			</li>
			<li class="submit">
				<s:submit value="Search"/>
			</li>
		</ol>
	</s:form>
</fieldset>