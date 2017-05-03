<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<s:set var="complaint" value="complaint" scope="request"/>
<s:set var="editable" value="%{false}"/>
<security:authorize access="hasPermission('save-intake', 'complaint')">
	<s:if test="lstCtrl.showControls">
		<s:set var="editable" value="%{true}"/>
	</s:if>
</security:authorize>
<s:if test="#editable">
	<div class="topControls">
		<div class="mainControls">
			<s:url id="newPersonInvolvedUrl" action="edit-person-involved">
				<s:param name="facilityId" value="facilityId"/>
				<s:param name="complaintId" value="complaintId"/>
			</s:url>
			<s:a href="%{newPersonInvolvedUrl}" cssClass="ccl-button ajaxify {target: '#peopleInvolvedSection'}">
				New Person
			</s:a>
		</div>
	</div>
</s:if>
<s:if test="!lstCtrl.results.isEmpty">
	<display:table name="lstCtrl.results" id="peopleInvolved" class="tables">
		<display:column title="Name">
			<s:if test="#editable">
				<s:url id="editPersonInvolvedUrl" action="edit-person-involved" includeParams="true">
					<s:param name="facilityId" value="facilityId"/>
					<s:param name="complaintId" value="complaintId"/>
					<s:param name="personInvolved.id" value="#attr.peopleInvolved.id"/>
				</s:url>
				<s:a href="%{editPersonInvolvedUrl}" cssClass="ajaxify {target: '#peopleInvolvedSection'}">
					<s:property value="#attr.peopleInvolved.lastName"/>,
					<s:property value="#attr.peopleInvolved.firstName"/>
				</s:a>
			</s:if>
			<s:else>
				<s:property value="#attr.peopleInvolved.lastName"/>,
				<s:property value="#attr.peopleInvolved.firstName"/>
			</s:else>
		</display:column>
		<s:if test="#editable">
			<display:column class="shrinkCol">
				<s:url id="deletePersonInvolvedUrl" action="delete-person-involved" includeParams="false">
					<s:param name="facilityId" value="facilityId"/>
					<s:param name="complaintId" value="complaintId"/>
					<s:param name="personInvolved.id" value="#attr.peopleInvolved.id"/>
				</s:url>
				<s:a href="%{deletePersonInvolvedUrl}" cssClass="ajaxify {target: '#peopleInvolvedSection'} ccl-action-delete">
					Delete
				</s:a>
			</display:column>
		</s:if>
	</display:table>
</s:if>