<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="dts" uri="/WEB-INF/tags/dts-tags.tld"%>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="display" uri="http://displaytag.sf.net"%>
<fieldset>
	<legend>Existing Facilities</legend>
	<div class="topControls">
		<div class="mainControls">
			<s:url id="newUnlicComplUrl" action="new-complaint" namespace="/unlicensedcomplaints" includeParams="all"/>
			<s:a href="%{newUnlicComplUrl}" cssClass="ccl-button">
				No Match
			</s:a>
			<s:url id="searchAgainUrl" action="search-form" namespace="/unlicensedcomplaints" includeParams="all"/>
			<s:a href="%{searchAgainUrl}" cssClass="ccl-button">
				Search Again
			</s:a>
		</div>
		<dts:listcontrols id="unlicComplTopControls" name="lstCtrl" action="search-results" namespace="/unlicensedcomplaints" enablePaging="true" maxPagesToShow="4"/>
	</div>
	<s:if test="lstCtrl.results.isEmpty">
		<div class="searchResults">There were no results matching your search criteria.</div>
	</s:if>
	<s:else>
		<display:table name="lstCtrl.results" id="searchResults" class="tables">
			<display:column title="Facility Name / Phone #">
				<s:url id="facComplUrl" action="open-tab" namespace="/facility" includeParams="false">
					<s:param name="tab" value="%{'complaints'}"/>
					<s:param name="act" value="%{'tab'}"/>
					<s:param name="ns" value="%{'/complaints'}"/>
					<s:param name="facilityId" value="#attr.searchResults.id"/>
				</s:url>
				<s:a href="%{facComplUrl}">
					<s:property value="#attr.searchResults.facilityName"/>
				</s:a>
				<br/>
				<s:property value="#attr.searchResults.primaryPhone.formattedPhoneNumber"/>
			</display:column>
			<display:column title="Owner">
				<s:property value="#attr.searchResults.ownerName"/>
			</display:column>
			<display:column title="Address">
				<s:component template="addressdisplay.ftl">
					<s:param name="address" value="#attr.searchResults.locationAddress"/>
				</s:component>
			</display:column>
			<display:column title="Status">
				<s:if test="#attr.searchResults.status == 'R'">Regulated</s:if>
				<s:elseif test="#attr.searchResults.status == 'X'">Exempt</s:elseif>
				<s:elseif test="#attr.searchResults.status == 'I'">Inactive</s:elseif>
				<s:elseif test="#attr.searchResults.status == 'P'">In Process</s:elseif>
				<s:else>Unlicensed</s:else>
			</display:column>
		</display:table>
		<div class="bottomControls">
			<div class="mainControls">
				<s:a href="%{newUnlicComplUrl}" cssClass="ccl-button">
					No Match
				</s:a>
				<s:a href="%{searchAgainUrl}" cssClass="ccl-button">
					Search Again
				</s:a>
			</div>
			<dts:listcontrols id="unlicComplBottomControls" name="lstCtrl" action="search-results" namespace="/unlicensedcomplaints" enablePaging="true" maxPagesToShow="4"/>
		</div>
	</s:else>
</fieldset>