<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<span class="ccl-error-container"></span>
<security:authorize access="hasPermission('edit-details','facility')">
	<s:if test="lstCtrl.showControls">
		<div class="topControls">
			<div class="mainControls">
				<s:url id="newContactUrl" action="contacts-screened-people-list">
					<s:param name="facilityId" value="facilityId"/>
				</s:url>
				<s:a href="%{newContactUrl}" cssClass="ccl-button ajaxify {target: '#contactsSection'}">
					New Contact
				</s:a>
			</div>
		</div>
	</s:if>
</security:authorize>
<s:if test="!lstCtrl.results.isEmpty">
	<display:table name="lstCtrl.results" id="contacts" class="tables" decorator="gov.utah.dts.det.ccl.view.admin.InactiveRecordDecorator">
		<display:column title="First Name">
			<security:authorize access="hasPermission('edit-details', 'facility')">
				<s:url id="contactEditUrl" action="edit-contact" includeParams="false">
					<s:param name="facilityId" value="facilityId"/>
					<s:param name="contact.id" value="#attr.contacts.id"/>
				</s:url>
				<s:a href="%{contactEditUrl}" cssClass="ajaxify {target: '#contactsSection'}">
					<s:property value="#attr.contacts.person.firstAndLastName"/>
				</s:a>
			</security:authorize>
			<security:authorize access="!hasPermission('edit-details', 'facility')">
				<s:property value="#attr.contacts.person.firstAndLastName"/>
			</security:authorize>
		</display:column>
		<display:column title="Type">
			<s:if test="#attr.contacts.type.equals(primaryContactType)">
				Primary Contact
			</s:if>
			<s:else>
				Secondary Contact
			</s:else>
		</display:column>
		<display:column title="Email">
			<s:property value="#attr.contacts.person.email"/>
		</display:column>
		<display:column title="Work Phone">
			<s:property value="#attr.contacts.person.workPhone.formattedPhoneNumber"/>
		</display:column>

		<security:authorize access="hasPermission('edit-details','facility')">
			<s:if test="lstCtrl.showControls">
				<display:column>
					<s:url id="contactDeleteUrl" action="delete-facility-person" namespace="/facility/information" includeParams="false">
						<s:param name="facilityId" value="facilityId"/>
						<s:param name="personId" value="#attr.contacts.id"/>
					</s:url>
					<s:a href="%{contactDeleteUrl}" cssClass="ccl-action-delete ccl-delete-link">
						delete
					</s:a>
				</display:column>
			</s:if>
		</security:authorize>
	</display:table>
</s:if>
<script type="text/javascript">
	$("#contacts").ccl("tableDeleteNew");
</script>