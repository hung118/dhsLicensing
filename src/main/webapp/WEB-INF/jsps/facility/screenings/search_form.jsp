<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="ccl" uri="/WEB-INF/tags/ccl-tags.tld"%>
<script type="text/javascript">
  $(document).ready(function() {
    $("#firstName").focus();
  });
</script>
<fieldset>
	<legend>Search for Screening</legend>
	<s:actionerror/>
	<s:form id="newScreeningSearchForm" action="search" namespace="/facility/screenings" method="get" cssClass="searchForm ">
		<s:hidden name="facilityId"/>
		<ol class="fieldList">
			<li>
				<label for="ssnLastFour">SSN Last 4:</label>
				<s:textfield id="ssnLastFour" name="ssnLastFour" cssClass="ssnLastFour" maxlength="4"/>
			</li>
			<li>
				<div class="label">Name:</div>
				<ol class="fieldGroup">
					<li>
						<label for="firstName">First:</label>
						<s:textfield id="firstName" name="firstName"/>
					</li>
					<li>
						<label for="lastName">Last:</label>
						<s:textfield id="lastName" name="lastName"/>
					</li>
				</ol>
			</li>
			<li>
				<label for="birthday">Birthday:</label>
				<s:textfield id="birthday" name="birthday" cssClass="date" maxlength="4"/>
			</li>
			<li class="submit">
				<s:submit value="Search"/>
			</li>
		</ol>
	</s:form>
</fieldset>