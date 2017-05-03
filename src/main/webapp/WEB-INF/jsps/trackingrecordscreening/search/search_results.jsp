<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="dts" uri="/WEB-INF/tags/dts-tags.tld"%>
<%@ taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<s:url var="searchAgainUrl" action="search-form" includeParams="all" escapeAmp="false"/>
<script type="text/javascript">
	$(document).ready(function() {
		$(".searchAgain").click(function() {
			window.location.href="<s:property value='%{searchAgainUrl}' escape='false'/>";
		});
	});
</script>
<fieldset>
	<legend>Possible Matches</legend>
	<div class="topControls">
		<div class="mainControls">
			<button type="button" class="searchAgain">Search Again</button>
			&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
			<security:authorize access="!hasRole('ROLE_ACCESS_PROFILE_VIEW')">
				<s:url id="newTrackingRecordScreeningUrl" action="trackingRecordScreeningCreate" namespace="/trackingrecordscreening" includeParams="all">
				</s:url>
				<s:a href="%{newTrackingRecordScreeningUrl}" cssClass="ccl-button ">
					New Person
				</s:a>
			</security:authorize>
		</div>
		<dts:listcontrols id="trsTopControls" name="lstCtrl" action="search-results" namespace="/trackingrecordscreening/search" enablePaging="true" maxPagesToShow="4"/>
	</div>
	<s:if test="!lstCtrl.results.isEmpty">
		<display:table name="lstCtrl.results" id="searchResults" class="tables">
			<display:column title="First Name">
				<s:property value="#attr.searchResults.firstName"/>
			</display:column>
			<display:column title="Last Name">
				<s:if test="#attr.searchResults.trsId != null" >
					<security:authorize access="hasAnyRole('ROLE_SUPER_ADMIN','ROLE_BACKGROUND_SCREENING','ROLE_BACKGROUND_SCREENING_MANAGER','ROLE_ACCESS_PROFILE_VIEW')">
						<s:url id="trsUrl" action="edit-tracking-record-screening" namespace="/trackingrecordscreening" includeParams="false" escapeAmp="false">
							<s:param name="screeningId" value="#attr.searchResults.trsId"/>
						</s:url>
						<s:a href="%{trsUrl}">
							<s:property value="#attr.searchResults.lastName"/>
						</s:a>
					</security:authorize>
					<security:authorize access="!hasAnyRole('ROLE_SUPER_ADMIN','ROLE_BACKGROUND_SCREENING','ROLE_BACKGROUND_SCREENING_MANAGER','ROLE_ACCESS_PROFILE_VIEW')">
						<s:property value="#attr.searchResults.lastName"/>
					</security:authorize>
				</s:if>
				<s:else>
					<s:property value="#attr.searchResults.lastName"/>
				</s:else>
			</display:column>
			
			<security:authorize access="!hasRole('ROLE_ACCESS_PROFILE_VIEW')">
				<display:column title="Birthdate">
					<s:property value="#attr.searchResults.birthday"/>
				</display:column>
			</security:authorize>
			<display:column title="Facility Name">
				<s:property value="#attr.searchResults.facilityName"/>
			</display:column>
			<display:column title="Approval Date">
				<s:property value="#attr.searchResults.approvalDate"/>
			</display:column>
			<display:column title="Person Name / Aliases">
				<span style="color:#4e8c56;"><s:property value="#attr.searchResults.personName"/></span><s:if test="@org.apache.commons.lang.StringUtils@isNotBlank(#attr.searchResults.alias)"> / <s:property value="#attr.searchResults.alias"/></s:if>
			</display:column>
			<display:column title="Person Identifier">
				<s:property value="#attr.searchResults.personIdentifier"/>
			</display:column>
			<display:column title="Create">
				<s:if test="#attr.searchResults.trsId != null && #attr.searchResults.personId != null" >
					<security:authorize access="hasAnyRole('ROLE_SUPER_ADMIN','ROLE_BACKGROUND_SCREENING','ROLE_BACKGROUND_SCREENING_MANAGER')">
						<s:url id="copyTrsUrl" action="copyTrs" namespace="/trackingrecordscreening" includeParams="false" escapeAmp="false">
							<s:param name="screeningId" value="#attr.searchResults.trsId"/>
							<s:param name="personId" value="#attr.searchResults.personId"/>
						</s:url>
						<s:a href="%{copyTrsUrl}">Clone</s:a>
					</security:authorize>
					<security:authorize access="!hasAnyRole('ROLE_SUPER_ADMIN','ROLE_BACKGROUND_SCREENING','ROLE_BACKGROUND_SCREENING_MANAGER')">
						&nbsp;
					</security:authorize>
				</s:if>
				<s:else>
					<security:authorize access="hasAnyRole('ROLE_SUPER_ADMIN','ROLE_BACKGROUND_SCREENING','ROLE_BACKGROUND_SCREENING_MANAGER')">
						<s:url id="newTrsUrl" action="trackingRecordScreeningCreate" namespace="/trackingrecordscreening" includeParams="false" escapeAmp="false">
							<s:param name="personId" value="#attr.searchResults.personId"/>
						</s:url>
						<s:a href="%{newTrsUrl}">New</s:a>
					</security:authorize>
					<security:authorize access="!hasAnyRole('ROLE_SUPER_ADMIN','ROLE_BACKGROUND_SCREENING','ROLE_BACKGROUND_SCREENING_MANAGER')">
						&nbsp;
					</security:authorize>
				</s:else>
			</display:column>
			<display:column title="Has Data?">
				<s:if test="#attr.searchResults.trsId != null">
					<s:checkbox name="#attr.searchResults.hasData" disabled="true"/>
				</s:if>
				<s:else>
					&nbsp;
				</s:else>
			</display:column>
		</display:table>
		<div class="bottomControls">
			<div class="mainControls">
				<button type="button" class="searchAgain">Search Again</button>
			</div>
			<dts:listcontrols id="trsBottomControls" name="lstCtrl" action="search-results" namespace="/trackingrecordscreening/search" enablePaging="true" maxPagesToShow="4"/>
		</div>
	</s:if>
	<s:else>
		<span class="tables">There were no results matching your search criteria.</span>
	</s:else>
</fieldset>