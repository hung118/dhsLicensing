<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<s:set var="complaint" value="complaint" scope="request"/>
<security:authorize access="hasPermission('investigation-entry', 'complaint')">
	<div class="topControls">
		<div class="mainControls">
			<s:url id="newInsUrl" action="open-tab" namespace="/facility">
				<s:param name="facilityId" value="facilityId"/>
				<s:param name="tab" value="%{'inspections'}"/>
				<s:param name="act" value="%{'new-inspection'}"/>
				<s:param name="ns" value="%{'/inspections'}"/>
				<s:param name="complaintId" value="complaintId"/>
			</s:url>
			<s:a href="%{newInsUrl}" cssClass="ccl-button">
				New Complaint Inspection Visit
			</s:a>
		</div>
	</div>
</security:authorize>
<s:if test="!complaint.inspections.isEmpty">
	<display:table name="complaint.inspections" id="inspections" class="tables">
		<display:column title="Date">
			<s:url id="editInspectionUrl" action="open-tab" namespace="/facility" includeParams="false">
				<s:param name="facilityId" value="facilityId"/>
				<s:param name="tab" value="%{'inspections'}"/>
				<s:param name="act" value="%{'edit-inspection-record'}"/>
				<s:param name="ns" value="%{'/inspections'}"/>
				<s:param name="inspectionId" value="#attr.inspections.id"/>
			</s:url>
			<s:a href="%{editInspectionUrl}">
				<s:date name="#attr.inspections.inspectionDate" format="MM/dd/yyyy"/>
			</s:a>
		</display:column>
		<security:authorize access="hasPermission('investigation-entry', 'complaint')">
			<display:column>
				<s:url id="removeInspectionUrl" action="remove-inspection">
					<s:param name="facilityId" value="facilityId"/>
					<s:param name="complaintId" value="complaintId"/>
					<s:param name="inspectionId" value="#attr.inspections.id"/>
				</s:url>
				<s:a href="%{removeInspectionUrl}" cssClass="ajaxify {target: '#inspectionsSection'}">
					Remove from complaint
				</s:a>
			</display:column>
		</security:authorize>
	</display:table>
</s:if>