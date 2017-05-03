<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="dts" uri="/WEB-INF/tags/dts-tags.tld"%>
<%@ taglib prefix="ccl" uri="/WEB-INF/tags/ccl-tags.tld"%>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="display" uri="http://displaytag.sf.net"%>
<fieldset>
	<legend>Statewide Unlicensed Complaint History</legend>
	<div class="topControls">
		<div class="mainControls">
			<s:url id="newUnlicComplUrl" action="search-form" namespace="/unlicensedcomplaints"/>
			<s:a href="%{newUnlicComplUrl}" cssClass="ccl-button">
				New Unlicensed Complaint
			</s:a>
		</div>
		<dts:listcontrols id="statewideUnlicComplTopControls" name="lstCtrl" action="statewide-unlicensed-complaints" namespace="/unlicensedcomplaints" enablePaging="true" maxPagesToShow="4">
			<ccl:listrange id="statewideUnlicComplTopControls" name="lstCtrl"/>
		</dts:listcontrols>
	</div>
	<display:table name="lstCtrl.results" id="searchResults" class="tables">
		<display:column title="Facility Name">
			<s:url id="facComplUrl" action="open-tab" namespace="/facility" includeParams="false">
				<s:param name="tab" value="%{'complaints'}"/>
				<s:param name="act" value="%{'tab'}"/>
				<s:param name="ns" value="%{'/complaints'}"/>
				<s:param name="facilityId" value="#attr.searchResults.facilityId"/>
			</s:url>
			<s:a href="%{facComplUrl}">
				<s:property value="#attr.searchResults.facilityName"/>
			</s:a>
		</display:column>
		<display:column title="Owner">
			<s:property value="#attr.searchResults.ownerNames"/>
		</display:column>
		<display:column title="Date Received" headerClass="shrinkCol">
			<s:url id="complUrl" action="open-tab" namespace="/facility" includeParams="false">
				<s:param name="tab" value="%{'complaints'}"/>
				<s:param name="act" value="%{'tab'}"/>
				<s:param name="ns" value="%{'/complaints/intake'}"/>
				<s:param name="facilityId" value="#attr.searchResults.facilityId"/>
				<s:param name="complaintId" value="#attr.searchResults.complaintId"/>
			</s:url>
			<s:a href="%{complUrl}">
				<s:property value="#attr.searchResults.dateReceived"/>
			</s:a>
		</display:column>
		<display:column title="Address">
			<s:component template="addressdisplay.ftl">
				<s:param name="address" value="#attr.searchResults.address"/>
			</s:component>
		</display:column>
		<display:column title="Status" headerClass="shrinkCol">
			<s:if test="#attr.searchResults.status == 'R'">Regulated</s:if>
			<s:elseif test="#attr.searchResults.status == 'N'">Exempt</s:elseif>
			<s:elseif test="#attr.searchResults.status == 'I'">Inactive</s:elseif>
			<s:else>Unlicensed</s:else>
		</display:column>
	</display:table>
	<div class="bottomControls">
		<dts:listcontrols id="statewideUnlicComplBottomControls" name="lstCtrl" action="statewide-unlicensed-complaints" namespace="/unlicensedcomplaints" enablePaging="true" maxPagesToShow="4">
			<ccl:listrange id="statewideUnlicComplBottomControls" name="lstCtrl"/>
		</dts:listcontrols>
	</div>
</fieldset>