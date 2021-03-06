<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="display" uri="http://displaytag.sf.net"%>
<fieldset>
	<legend>Add Contact</legend>
	<s:actionerror/>
	<s:fielderror/>
	<h1>People who have passed a background screening:</h1>
	<display:table name="lstCtrl.results" id="screened" class="tables">
		<display:column title="Name">
			<s:url id="screenedAddUrl" action="add-contact" includeParams="false">
				<s:param name="facilityId" value="facilityId"/>
				<s:param name="personId" value="#attr.screened.person.id"/>
			</s:url>
			<s:a href="%{screenedAddUrl}" cssClass="ajaxify {target: '#contactsSection'}">
				<s:property value="#attr.screened.person.firstAndLastName"/>
			</s:a>
		</display:column>
		<display:column title="Date of Birth">
			<s:date name="#attr.screened.birthdate" format="MM/dd/yyyy"/>
		</display:column>
		<display:column title="Screening Facility">
			<s:property value="#attr.screened.facilityName" />
		</display:column>
		<display:column title="Approval Date">
			<s:date name="#attr.screened.approvalDate" format="MM/dd/yyyy"/>
		</display:column>
	</display:table>
	<div class="tabControlSection">
		<s:url id="contactEditCancelUrl" action="contacts-list" includeParams="false">
			<s:param name="facilityId" value="facilityId"/>
		</s:url>
		<s:a id="contactEditCancel" href="%{contactEditCancelUrl}" cssClass="ajaxify {target: '#contactsSection'}">
			Cancel
		</s:a>
	</div>
</fieldset>
