<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<fieldset>
	<legend>Complaints</legend>
	<h1>Complaints Investigated on this Inspection</h1>
	<s:if test="!inspection.complaints.isEmpty">
		<display:table name="inspection.complaints" id="complaintsInspected" class="tables">
			<display:column title="Date">
				<s:url id="editComplaintUrl" action="open-tab" namespace="/facility" includeParams="false">
					<s:param name="facilityId" value="facilityId"/>
					<s:param name="tab" value="%{'complaints'}"/>
					<s:param name="act" value="%{'tab'}"/>
					<s:param name="ns" value="%{'/complaints/intake'}"/>
					<s:param name="complaintId" value="#attr.complaintsInspected.id"/>
				</s:url>
				<s:a href="%{editComplaintUrl}">
					<s:date name="#attr.complaintsInspected.dateReceived" format="MM/dd/yyyy"/>
				</s:a>
			</display:column>
			<s:if test="editable">
				<display:column>
					<s:url id="removeComplaintUrl" action="remove-complaint">
						<s:param name="facilityId" value="facilityId"/>
						<s:param name="inspectionId" value="inspectionId"/>
						<s:param name="complaintId" value="#attr.complaintsInspected.id"/>
					</s:url>
					<s:a href="%{removeComplaintUrl}" cssClass="ajaxify {target: '#complaintsSection'}">
						Remove from inspection
					</s:a>
				</display:column>
			</s:if>
		</display:table>
	</s:if>
	<s:if test="editable">
		<h1>Other Complaints in Progress</h1>
		<s:if test="!complaints.isEmpty">
			<display:table name="complaints" id="complaints" class="tables">
				<display:column title="Date">
					<s:url id="editComplaintUrl" action="open-tab" namespace="/facility" includeParams="false">
						<s:param name="facilityId" value="facilityId"/>
						<s:param name="tab" value="%{'complaints'}"/>
						<s:param name="act" value="%{'tab'}"/>
						<s:param name="ns" value="%{'/complaints/intake'}"/>
						<s:param name="complaintId" value="#attr.complaints.id"/>
					</s:url>
					<s:a href="%{editComplaintUrl}">
						<s:date name="#attr.complaints.dateReceived" format="MM/dd/yyyy"/>
					</s:a>
				</display:column>
				<display:column>
					<s:url id="addComplaintUrl" action="add-complaint">
						<s:param name="facilityId" value="facilityId"/>
							<s:param name="inspectionId" value="inspectionId"/>
						<s:param name="complaintId" value="#attr.complaints.id"/>
					</s:url>
					<s:a href="%{addComplaintUrl}" cssClass="ajaxify {target: '#complaintsSection'}">
						Add to inspection
					</s:a>
				</display:column>
			</display:table>
		</s:if>
	</s:if>
</fieldset>