<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="ccl" uri="/WEB-INF/tags/ccl-tags.tld"%>
<%@ taglib prefix="display" uri="http://displaytag.sf.net"%>

<fieldset>
	<legend>Edit Contact</legend>
	<s:actionerror/>
	<s:fielderror/>
	<s:form id="contactForm" action="save-contact" method="post" cssClass="ajaxify {target: '#contactsSection'} ccl-action-save">
		<s:hidden name="facilityId"/>
		<s:hidden name="contact.id"/>
		<s:hidden name="personId"/>
		<ol class="fieldList">
			<li>
				<div class="label">Name:</div>
				<div class="value"><s:property value="contact.person.firstAndLastName"/></div>
			</li>
			<li>
				<div class="label">Email:</div>
				<div class="value">
					<s:textfield id="contactPersonEmail" name="contact.person.email" cssClass="required"/>
				</div>
			</li>
			<li>
				<div class="label">Work Phone:</div>
				<div class="value">
					<s:textfield id="workPhone" name="contact.person.workPhone" cssClass="phone"/>
				</div>
			</li>

			<li>
				<div class="label" style="width:auto;">Primary Contact:</div>
				<div class="value">
					<s:checkbox id="primary" name="primary"/>
				</div>
			</li>
			<li class="submit">
				<s:submit value="Save"/>
				<s:url id="contactEditCancelUrl" action="contacts-list" includeParams="false">
					<s:param name="facilityId" value="facilityId"/>
				</s:url>
				<s:a id="contactEditCancel" href="%{contactEditCancelUrl}" cssClass="ajaxify {target: '#contactsSection'}">
					Cancel
				</s:a>
			</li>
		</ol>
	</s:form>
	<div>
		<s:include value="contacts_table.jsp"/>
	</div>
</fieldset>