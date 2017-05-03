<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="dts" uri="/WEB-INF/tags/dts-tags.tld"%>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<s:url var="searchAgainUrl" action="index" includeParams="all" escapeAmp="false"/>
<s:url var="printUrl" action="print-search-results" includeParams="all" escapeAmp="false"/>
<s:url var="printLabelsUrl" action="print-labels" includeParams="all" escapeAmp="false"/>
<script type="text/javascript">
	var openHandler = function() {
		window.open($(this).metadata().url, "_blank");
	}

	$(document).ready(function() {
		$("button.win-loc").click(function() {
			window.location.href = $(this).metadata().url;
		});
		$("button.open-win").click(openHandler);

		<s:if test="lstCtrl.numOfResults > 1500">
			$(".print-button").unbind("click", openHandler).click(function() {
				$.modal("There are <s:property value='lstCtrl.numOfResults'/> results.  Please narrow the search to below 1500 results before printing.");
			});
		</s:if>
	});
</script>
<fieldset>
	<legend>Search Results</legend>
	<div class="ccl-list-ctrls clearfix">
		<div class="ccl-list-left-ctrls">
			<security:authorize access="hasClassPermission('create', 'Facility')">
				<s:url id="newFacilityUrl" action="create-facility" namespace="/facility"/>
				<s:a href="%{newFacilityUrl}" cssClass="ccl-button">
					New Facility
				</s:a>
			</security:authorize>
			<button type="button" class="print-button open-win {url: '<s:property value="%{printUrl}"/>'}">Print</button>
			<button type="button" class="win-loc {url: '<s:property value="%{printLabelsUrl}"/>'}">Labels</button>
			<button type="button" class="win-loc {url: '<s:property value="%{searchAgainUrl}"/>'}">Search Again</button>
		</div>
		<dts:listcontrols id="facilityTopControls" name="lstCtrl" action="search-results" namespace="/facility/search" enablePaging="true" maxPagesToShow="4"/>
	</div>
	<s:if test="lstCtrl.results.isEmpty">
		<div class="ccl-list">There were no results matching your search criteria.</div>
	</s:if>
	<s:else>
		<ol class="ccl-list">
			<s:set var="todaysDate" value="new java.util.Date()"/>
			<s:iterator value="lstCtrl.results" status="row">
				<li class="ccl-list-item <s:if test="#row.odd">odd</s:if><s:else>even</s:else><s:if test="status == 'U' or status == 'I'"> inactive</s:if>">
					<div class="left-column">
						<div><span class="label">Facility Name:</span>
							<security:authorize access="hasClassPermission('view-record', 'Facility') or hasRole('ROLE_ACCESS_PROFILE_VIEW')">
								<s:url id="facilityEditLink" action="edit-facility" namespace="/facility">
									<s:param name="facilityId" value="id"/>
								</s:url>
								<s:a href="%{facilityEditLink}">
									<s:property value="facilityName"/>
								</s:a>
							</security:authorize>
							<security:authorize access="!hasClassPermission('view-record', 'Facility') and !hasRole('ROLE_ACCESS_PROFILE_VIEW')">
								<s:if test="#attr.websiteUrl != null">
									<a href="<s:property value="websiteUrl"/>" target="_blank"><s:property value="facilityName"/></a>
								</s:if>
								<s:else>
									<s:property value="facilityName"/>
								</s:else>
							</security:authorize>
						</div>
						<div><span class="label">Site Name:</span> <s:property value="siteName"/></div>
						<div><span class="label">Address:</span>
							<s:component template="addressdisplay.ftl">
								<s:param name="address" value="locationAddress"/>
							</s:component>
						</div>
					</div>
					<div class="right-column">
						<div><span class="label">Director:</span> <s:property value="directorName"/></div>
						<security:authorize access="principal.isInternal() or hasAnyRole('ROLE_FACILITY_PROVIDER')">
							<div><span class="label">Licensor:</span> <s:property value="licensingSpecialistName"/></div>
						</security:authorize>
						<div><span class="label">Active Licenses (Expiration):</span></div>
						<s:iterator var="license" value="activeLicenses" status="licenseStatus">
							<div>
								<s:property value="#attr.license.licenseListDescriptor"/> (<s:property value="#attr.license.expirationDateFormatted"/>)
							</div>
						</s:iterator>
					</div>
				</li>
			</s:iterator>
		</ol>
		<div class="ccl-list-ctrls clearfix">
			<div class="ccl-list-left-ctrls">
				<security:authorize access="hasClassPermission('create', 'Facility')">
					<s:url id="newFacilityUrl" action="create-facility" namespace="/facility"/>
					<s:a href="%{newFacilityUrl}" cssClass="ccl-button">
						New Facility
					</s:a>
				</security:authorize>
				<button type="button" class="print-button open-win {url: '<s:property value="%{printUrl}"/>'}">Print</button>
				<button type="button" class="win-loc {url: '<s:property value="%{printLabelsUrl}"/>'}">Labels</button>
				<button type="button" class="win-loc {url: '<s:property value="%{searchAgainUrl}"/>'}">Search Again</button>
			</div>
			<dts:listcontrols id="facilityBottomControls" name="lstCtrl" action="search-results" namespace="/facility/search" enablePaging="true" maxPagesToShow="4"/>
		</div>
	</s:else>
</fieldset>