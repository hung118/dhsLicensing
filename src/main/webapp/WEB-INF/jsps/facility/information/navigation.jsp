<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<s:set var="selectedTab">${param.selectedTab}</s:set>
<ul class="ccl-subnav">
	<li>
		<s:url id="detailsTabUrl" action="tab" namespace="/facility/information/details" includeParams="false">
			<s:param name="facilityId" value="facilityId"/>
		</s:url>
		<s:a cssClass="ajaxify {target: '#informationBase'} %{#attr.selectedTab == 'details' ? 'selected' : ''}" href="%{detailsTabUrl}">
			Facility Details
		</s:a>
	</li>
	<s:if test="facility.type != null && facility.type.character != 'T'">
		<li>
			<s:url id="formsTabUrl" action="tab" namespace="/facility/information/forms" includeParams="false">
				<s:param name="facilityId" value="facilityId"/>
			</s:url>
			<s:a cssClass="ajaxify {target: '#informationBase'} %{#attr.selectedTab == 'forms' ? 'selected' : ''}" href="%{formsTabUrl}">
				Forms
			</s:a>
		</li>
	</s:if>
	<li>
		<s:url id="licenseTabUrl" action="tab" namespace="/facility/information/license" includeParams="false">
			<s:param name="facilityId" value="facilityId"/>
		</s:url>
		<s:a cssClass="ajaxify {target: '#informationBase'} %{#attr.selectedTab == 'license' ? 'selected' : ''}" href="%{licenseTabUrl}">
			License
		</s:a>
	</li>
	<security:authorize access="isAuthenticated() and principal.isInternal()">
		<li>
			<s:url id="ownersTabUrl" action="tab" namespace="/facility/information/owners" includeParams="false">
				<s:param name="facilityId" value="facilityId"/>
			</s:url>
			<s:a cssClass="ajaxify {target: '#informationBase'} %{#attr.selectedTab == 'owners' ? 'selected' : ''}" href="%{ownersTabUrl}">
				Owners
			</s:a>
		</li>
		<li>
			<s:url id="boardTabUrl" action="tab" namespace="/facility/information/board" includeParams="false">
				<s:param name="facilityId" value="facilityId"/>
			</s:url>
			<s:a cssClass="ajaxify {target: '#informationBase'} %{#attr.selectedTab == 'board' ? 'selected' : ''}" href="%{boardTabUrl}">
				Board
			</s:a>
		</li>
		<li>
			<s:url id="staffTabUrl" action="tab" namespace="/facility/information/staff" includeParams="false">
				<s:param name="facilityId" value="facilityId"/>
			</s:url>
			<s:a cssClass="ajaxify {target: '#informationBase'} %{#attr.selectedTab == 'staff' ? 'selected' : ''}" href="%{staffTabUrl}">
				Staff
			</s:a>
		</li>
	</security:authorize>
</ul>