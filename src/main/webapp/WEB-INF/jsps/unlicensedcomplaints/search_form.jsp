<%@ taglib prefix="s" uri="/struts-tags"%><fieldset>
<script type="text/javascript">
	$(document).ready(function() {
		$("#ownerName").focus();
	});
</script>
	<legend>Search for a Facility</legend>
	<s:actionerror/>
	<s:form id="facilityComplaintSearchForm" action="search-results" method="get" cssClass="searchForm">
		<ol class="fieldList">
			<li>
				<label for="ownerName">Owner Name:</label>
				<s:textfield id="ownerName" name="ownerName"/>
			</li>
			<li>
				<label>Facility:</label>
				<ol class="fieldGroup">
					<li>
						<label for="facName">Name:</label>
						<s:textfield name="facName" cssClass="longName"/>
					</li>
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
						<s:textfield name="zipCode"/>
					</li>
					<li class="clearLeft fieldMargin">
						<label for="phone">Phone #:</label>
						<s:textfield name="phone"/>
					</li>
				</ol>
			</li>
			<li class="submit">
				<s:submit value="Search"/>
			</li>
		</ol>
	</s:form>
</fieldset>