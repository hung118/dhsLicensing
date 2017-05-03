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
			<s:url id="newIncUrl" action="edit-incident">
				<s:param name="facilityId" value="facilityId"/>
				<s:param name="complaintId" value="complaintId"/>
			</s:url>
			<s:a href="%{newIncUrl}" cssClass="ccl-button ajaxify {target: '#incidentsSection'}">
				New Incident
			</s:a>
		</div>
	</div>
</s:if>
<s:if test="!lstCtrl.results.isEmpty">
	<display:table name="lstCtrl.results" id="incidents" class="tables">
		<display:column title="Date/Time of Incident">
			<s:if test="#editable">
				<s:url id="editIncidentUrl" action="edit-incident" includeParams="false">
					<s:param name="facilityId" value="facilityId"/>
					<s:param name="complaintId" value="complaintId"/>
					<s:param name="incident.id" value="#attr.incidents.id"/>
				</s:url>
				<s:a href="%{editIncidentUrl}" cssClass="ajaxify {target: '#incidentsSection'}">
					<s:date name="#attr.incidents.date" format="MM/dd/yyyy hh:mm a"/> <s:property value="#attr.incidents.dateDescription"/>
				</s:a>
			</s:if>
			<s:else>
				<s:date name="#attr.incidents.date" format="MM/dd/yyyy hh:mm a"/> <s:property value="#attr.incidents.dateDescription"/>
			</s:else>
		</display:column>
		<display:column title="Need to Investigate?" headerClass="shrinkCol">
			<s:property value="@gov.utah.dts.det.ccl.view.YesNoChoice@valueOfBoolean(#attr.incidents.investigable).displayName"/>
		</display:column>
		<display:column title="Description">
			<s:property value="#attr.incidents.description"/>
		</display:column>
		<s:if test="#editable">
			<display:column class="shrinkCol">
				<s:url id="deleteIncidentUrl" action="delete-incident" includeParams="false">
					<s:param name="facilityId" value="facilityId"/>
					<s:param name="complaintId" value="complaintId"/>
					<s:param name="incident.id" value="#attr.incidents.id"/>
				</s:url>
				<s:a href="%{deleteIncidentUrl}" cssClass="ajaxify {target: '#incidentsSection'} ccl-action-delete">
					Delete
				</s:a>
			</display:column>
		</s:if>
	</display:table>
</s:if>