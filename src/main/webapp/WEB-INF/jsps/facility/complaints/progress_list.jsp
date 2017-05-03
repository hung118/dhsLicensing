<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<fieldset>
	<legend>Complaints in Progress</legend>
	<span id="complaint-errors"></span>
	<div class="topControls">
		<div class="mainControls">
			<security:authorize access="hasClassPermission('create', 'Complaint')">
				<s:url id="newComplUrl" action="new-complaint">
					<s:param name="facilityId" value="facilityId"/>
				</s:url>
				<s:a href="%{newComplUrl}" cssClass="ccl-button ajaxify {target: '#complaintsBase'}">
					New Complaint
				</s:a>
			</security:authorize>
		</div>
	</div>
	<s:if test="!lstCtrl.results.isEmpty">
		<display:table name="lstCtrl.results" id="complaints" class="tables">
			<display:column title="Date Received">
				<security:authorize access="hasAnyRole('ROLE_SUPER_ADMIN','ROLE_OFFICE_SPECIALIST','ROLE_LICENSOR_SPECIALIST')">
					<s:url id="viewComplaintUrl" action="tab" namespace="/facility/complaints/intake" includeParams="false">
						<s:param name="facilityId" value="facilityId"/>
						<s:param name="complaintId" value="#attr.complaints.id"/>
					</s:url>
					<s:a cssClass="ajaxify {target: '#complaintsBase'}" href="%{viewComplaintUrl}">
						<s:date name="#attr.complaints.dateReceived" format="MM/dd/yyyy"/>
					</s:a>
				</security:authorize>
				<security:authorize access="!hasAnyRole('ROLE_SUPER_ADMIN','ROLE_OFFICE_SPECIALIST','ROLE_LICENSOR_SPECIALIST')">
					<s:date name="#attr.complaints.dateReceived" format="MM/dd/yyyy"/>
				</security:authorize>
			</display:column>

<%-- Redmine #26323 - State removed from the list view
			<display:column title="State">
				<s:property value="#attr.complaints.state"/>
			</display:column>
--%>
			<display:column title="Conclusion">
				<s:property value="#attr.complaints.screening.conclusionType.value"/>
			</display:column>

			<display:column class="shrinkCol">
				<s:set var="complaint" value="%{#attr.complaints}" scope="request"/>
				<security:authorize access="hasPermission('delete', 'complaint')">
					<s:url id="deleteComplaintUrl" action="delete-complaint" includeParams="false">
						<s:param name="facilityId" value="facilityId"/>
						<s:param name="complaintId" value="#attr.complaints.id"/>
					</s:url>
					<s:a href="%{deleteComplaintUrl}" cssClass="ccl-action-delete ccl-delete-link">
						delete
					</s:a>
				</security:authorize>
			</display:column>
		</display:table>
	</s:if>
</fieldset>
<script type="text/javascript">
	$("#complaints").ccl("tableDelete", {errorContainer: "#complaint-errors"});
</script>