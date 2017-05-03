<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="display" uri="http://displaytag.sf.net"%>

<s:if test="!primaryContacts.isEmpty">
	<div class="label">Primary Contact(s):</div>
	
	<display:table name="primaryContacts" id="primaryContacts" class="tables" style="width:auto;" decorator="gov.utah.dts.det.ccl.view.admin.InactiveRecordDecorator">
		<display:column title="Name">
				<s:property value="#attr.primaryContacts.person.firstAndLastName"/>
		</display:column>
		<display:column title="Email">
			<s:property value="#attr.primaryContacts.person.email"/>
		</display:column>
	</display:table>
</s:if>
