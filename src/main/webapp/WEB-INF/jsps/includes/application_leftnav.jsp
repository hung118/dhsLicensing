<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<div id="leftNav">
	<ul>
		<li>
			<s:url id="homeUrl" namespace="/home" action="index" includeParams="false"/>
			<s:a href="%{homeUrl}">
				Home
			</s:a>
		</li>
		<security:authorize access="principal.isInternal() or hasRole('ROLE_ACCESS_PROFILE_VIEW')">
			<li>
				<s:url id="findFacilityUrl" namespace="/facility/search" action="index" includeParams="false"/>
				<s:a href="%{findFacilityUrl}">
					Find a Facility
				</s:a>
			</li>
		</security:authorize>
		<security:authorize access="hasAnyRole('ROLE_SUPER_ADMIN','ROLE_BACKGROUND_SCREENING_MANAGER','ROLE_BACKGROUND_SCREENING', 'ROLE_ACCESS_PROFILE_VIEW')">
			<li>
				<s:url id="trsSearchUrl" namespace="/trackingrecordscreening/search" action="search-form" includeParams="false"/>
				<s:a href="#" onclick="javascript:window.open('%{trsSearchUrl}','screening');">
					Screening Search
				</s:a>
			</li>
		</security:authorize>
		<security:authorize access="isAuthenticated() and principal.isInternal()">
			<li>
				<s:url id="unlicComplUrl" namespace="/unlicensedcomplaints" action="statewide-unlicensed-complaints" includeParams="false"/>
				<s:a href="%{unlicComplUrl}">
					Unlicensed Complaints
				</s:a>
			</li>
		</security:authorize>
		<security:authorize access="hasAnyRole('ROLE_SUPER_ADMIN','ROLE_ADMIN_MANAGER')">
			<li>
				<s:url id="caseloadUrl" namespace="/caseloadmanagement" action="manage-caseload" includeParams="false"/>
				<s:a href="%{caseloadUrl}">
					Caseload Management
				</s:a>
			</li>
		</security:authorize>
		<security:authorize access="isAuthenticated() and principal.isInternal()">
			<li>
				<s:url id="reportsUrl" namespace="/reports" action="index" includeParams="false"/>
				<s:a href="%{reportsUrl}">
					Reports
				</s:a>
			</li>
		</security:authorize>
<%-- 		
		<li>
			<s:url id="intManualUrl" namespace="/inspectiontools/interpretationmanual" action="rules" includeParams="false"/>
			<s:a href="%{intManualUrl}">
				Interpretation Manual
			</s:a>
		</li>
 --%>		
		<security:authorize access="hasAnyRole('ROLE_SUPER_ADMIN')">
			<li>
				<s:url id="adminUrl" namespace="/admin" action="index" includeParams="false"/>
				<s:a href="%{adminUrl}">
					Administration
				</s:a>
			</li>
		</security:authorize>
		<%-- Removed 1/28/2013	
		<security:authorize access="hasAnyRole('ROLE_SUPER_ADMIN')">
			<li>
				<s:url id="clearCacheUrl" namespace="/admin" action="clear-cache" includeParams="false"/>
				<s:a id="clearCacheLink" href="%{clearCacheUrl}">
					Clear Cache
				</s:a>
			</li>
		</security:authorize>
		--%>
	<!--		<li><a href="#">Receive Payment</a></li>-->
	<!--		<li><a href="#">Payment Records</a></li>-->
	<!--		<li><a href="#">Unlicensed Complaints</a></li>-->
	<!--		<li><a href="#">Provider Training</a></li>-->
	<!--		<li><a href="#">Inspection Evaluations</a></li>-->
	<!--		<li><a href="#">Reports</a></li>-->
	<!--		<li><a href="#">Inspection Tools</a></li>-->
	<!--		<li><a href="#">Test Database</a></li>-->
	</ul>
</div>