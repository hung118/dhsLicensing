<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<s:if test="complaintId != null and complaint.state.name() != 'INTAKE'">
	<s:set var="selectedTab">${param.selectedTab}</s:set>
	<ul class="ccl-subnav">
		<li>
			<s:url id="intakeTabUrl" action="tab" namespace="/facility/complaints/intake" includeParams="false">
				<s:param name="facilityId" value="facilityId"/>
				<s:param name="complaintId" value="complaintId"/>
			</s:url>
			<s:a cssClass="ajaxify {target: '#complaintsBase'} %{#attr.selectedTab == 'intake' ? 'selected' : ''}" href="%{intakeTabUrl}">
				Intake
			</s:a>
		</li>
	</ul>
</s:if>