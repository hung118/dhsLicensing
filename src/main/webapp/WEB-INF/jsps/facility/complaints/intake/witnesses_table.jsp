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
			<s:url id="newWitUrl" action="edit-witness">
				<s:param name="facilityId" value="facilityId"/>
				<s:param name="complaintId" value="complaintId"/>
			</s:url>
			<s:a href="%{newWitUrl}" cssClass="ccl-button ajaxify {target: '#witnessesSection'}">
				New Witness
			</s:a>
		</div>
	</div>
</s:if>
<s:if test="!lstCtrl.results.isEmpty">
	<display:table name="lstCtrl.results" id="witnesses" class="tables">
		<display:column title="Name">
			<s:if test="#editable">
				<s:url id="editWitnessUrl" action="edit-witness" includeParams="false">
					<s:param name="facilityId" value="facilityId"/>
					<s:param name="complaintId" value="complaintId"/>
					<s:param name="witness.id" value="#attr.witnesses.id"/>
				</s:url>
				<s:a href="%{editWitnessUrl}" cssClass="ajaxify {target: '#witnessesSection'}">
					<s:property value="#attr.witnesses.firstAndLastName"/>
				</s:a>
			</s:if>
			<s:else>
				<s:property value="#attr.witnesses.firstAndLastName"/>
			</s:else>
			<s:component template="addressdisplay.ftl">
				<s:param name="address" value="#attr.witnesses.address"/>
			</s:component>
		</display:column>
		<display:column title="Phone">
			Home: <s:property value="#attr.witnesses.homePhone.formattedPhoneNumber"/><br/>
			Alt: <s:property value="#attr.witnesses.alternatePhone.formattedPhoneNumber"/>
		</display:column>
		<s:if test="#editable">
			<display:column class="shrinkCol">
				<s:url id="deleteWitnessUrl" action="delete-witness" includeParams="false">
					<s:param name="facilityId" value="facilityId"/>
					<s:param name="complaintId" value="complaintId"/>
					<s:param name="witness.id" value="#attr.witnesses.id"/>
				</s:url>
				<s:a href="%{deleteWitnessUrl}" cssClass="ajaxify {target: '#witnessesSection'} ccl-action-delete">
					Delete
				</s:a>
			</display:column>
		</s:if>
	</display:table>
</s:if>