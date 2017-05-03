<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<s:set var="showControls">${param.showControls == null ? false : param.showControls}</s:set>
<s:set var="complaint" value="complaint" scope="request"/>
<s:set var="editable" value="%{false}"/>
<security:authorize access="hasPermission('follow-up-entry', 'complaint')">
	<s:if test="#attr.showControls == 'true'">
		<s:set var="editable" value="%{true}"/>
	</s:if>
</security:authorize>
<s:if test="!complaint.followUps.isEmpty">
	<display:table name="complaint.followUps" id="followUps" class="tables">
		<display:column title="Date" headerClass="shrinkCol">
			<s:if test="#editable">
				<s:url id="editFollowUpUrl" action="edit-follow-up" includeParams="false">
					<s:param name="facilityId" value="facilityId"/>
					<s:param name="complaintId" value="complaintId"/>
					<s:param name="followUpId" value="#attr.followUps.id"/>
				</s:url>
				<s:a href="%{editFollowUpUrl}" cssClass="ajaxify {target: '#investigationSection'}">
					<s:date name="#attr.followUps.followUpDate" format="MM/dd/yyyy"/>
				</s:a>
			</s:if>
			<s:else>
				<s:date name="#attr.followUps.followUpDate" format="MM/dd/yyyy"/>
			</s:else>
		</display:column>
		<display:column title="Details">
			<s:property value="#attr.followUps.details"/>
		</display:column>
		<s:if test="#editable">
			<display:column class="shrinkCol">
				<s:url id="deleteFollowUpUrl" action="delete-follow-up" includeParams="false">
					<s:param name="facilityId" value="facilityId"/>
					<s:param name="complaintId" value="complaintId"/>
					<s:param name="followUpId" value="#attr.followUps.id"/>
				</s:url>
				<s:a href="%{deleteFollowUpUrl}" cssClass="ajaxify {target: '#investigationSection'} ccl-action-delete">
					Delete
				</s:a>
			</display:column>
		</s:if>
	</display:table>
</s:if>