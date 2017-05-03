<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<fieldset>
	<legend>Available Reports</legend>
	<security-authorize access="hasAnyRole('ROLE_LICENSOR_SPECIALIST','ROLE_LICENSOR_MANAGER','ROLE_LICENSING_DIRECTOR')">
		<h1>Licensor Caseload Reports</h1>
		<ol style="padding-top:10px; padding-bottom:15px; padding-left:20px;">
			<li>
				<s:url id="openAppUrl" action="open-applications-srch" includeParams="all">
					<security:authorize access="hasAnyRole('ROLE_LICENSOR_SPECIALIST')">
						<s:param name="specialistId" value="@gov.utah.dts.det.ccl.security.SecurityUtil@getUser().person.id" />
					</security:authorize>
				</s:url>
				<s:a href="%{openAppUrl}" cssClass="ajaxify {target: '#maincontent'}">
					Open Applications By Licensor
				</s:a>
			</li>
			<li>
				<s:url id="licensorFacilitySummaryUrl" action="fac-license-summary-srch" includeParams="all">
					<security:authorize access="hasAnyRole('ROLE_LICENSOR_SPECIALIST')">
						<s:param name="specialistId" value="@gov.utah.dts.det.ccl.security.SecurityUtil@getUser().person.id" />
					</security:authorize>
				</s:url>
				<s:a href="%{licensorFacilitySummaryUrl}" cssClass="ajaxify {target: '#maincontent'}">
					Facility Licenses Summary By Licensor
				</s:a>
			</li>
			<li>
				<s:url id="licensorFacilityDetailUrl" action="fac-license-detail-srch" includeParams="all">
					<security:authorize access="hasAnyRole('ROLE_LICENSOR_SPECIALIST')">
						<s:param name="specialistId" value="@gov.utah.dts.det.ccl.security.SecurityUtil@getUser().person.id" />
					</security:authorize>
				</s:url>
				<s:a href="%{licensorFacilityDetailUrl}" cssClass="ajaxify {target: '#maincontent'}">
					Facility Licenses Detail By Licensor
				</s:a>
			</li>
			<li>
				<s:url id="expiredLicensesUrl" action="expired-licenses-srch" includeParams="all">
					<security:authorize access="hasAnyRole('ROLE_LICENSOR_SPECIALIST')">
						<s:param name="specialistId" value="@gov.utah.dts.det.ccl.security.SecurityUtil@getUser().person.id" />
					</security:authorize>
				</s:url>
				<s:a href="%{expiredLicensesUrl}" cssClass="ajaxify {target: '#maincontent'}">
					Expired Licenses By Licensor
				</s:a>
			</li>
			<li>
				<s:url id="expiredLicensesUrl" action="license-renewals-srch" includeParams="all">
					<security:authorize access="hasAnyRole('ROLE_LICENSOR_SPECIALIST')">
						<s:param name="specialistId" value="@gov.utah.dts.det.ccl.security.SecurityUtil@getUser().person.id" />
					</security:authorize>
				</s:url>
				<s:a href="%{expiredLicensesUrl}" cssClass="ajaxify {target: '#maincontent'}">
					License Renewals By Licensor
				</s:a>
			</li>
		</ol>
	</security-authorize>
	<security-authorize access="hasAnyRole('ROLE_BACKGROUND_SCREENING','ROLE_BACKGROUND_SCREENING_MANAGER','ROLE_LICENSING_DIRECTOR')">
		<h1>Screening Technician Reports</h1>
		<ol style="padding-top:10px; padding-bottom:15px; padding-left:20px;">
			<li>
				<s:url id="livescansUrl" action="livescans-issued-srch" includeParams="all">
					<security:authorize access="hasAnyRole('ROLE_BACKGROUND_SCREENING')">
						<s:param name="technicianId" value="@gov.utah.dts.det.ccl.security.SecurityUtil@getUser().person.id" />
					</security:authorize>
				</s:url>
				<s:a href="%{livescansUrl}" cssClass="ajaxify {target: '#maincontent'}">
					Live Scans Issued By Screening Technician
				</s:a>
			</li>
		</ol>
	</security-authorize>
</fieldset>