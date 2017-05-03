<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<fieldset>
	<legend>Edit Ownership</legend>
	<s:fielderror/>
	<s:form id="facilityOwnershipForm" action="save-ownership" method="post" cssClass="ajaxify {target: '#ownershipSection'} ccl-action-save">
		<s:hidden name="facilityId"/>
		<ol class="fieldList">
			<li>
				<security:authorize access="hasPermission('edit-details', 'facility')">
					<label for="ownershipType"><span class="redtext">* </span>Type:</label>
					<s:select id="ownershipType" name="ownershipType" value="ownershipType.id"
						  list="ownershipTypes" listKey="id" listValue="value" headerKey="-1" headerValue="- Select an Ownership Type -" cssClass="required"/>
				</security:authorize>
				<security:authorize access="!hasPermission('edit-details', 'facility')">
					<div class="label">Type:</div>
					<div class="value"><s:property value="ownershipType.value"/></div>
				</security:authorize>
			</li>
			<li>
				<security:authorize access="hasPermission('edit-details', 'facility')">
					<label for="ownerName">Owner Name:</label>
					<s:textfield id="ownerName" name="ownerName" cssClass="longName"/>
				</security:authorize>
				<security:authorize access="!hasPermission('edit-details', 'facility')">
					<div class="label">Owner Name:</div>
					<div class="value"><s:property value="ownerName"/></div>
				</security:authorize>
			</li>
			<li class="submit">
				<security:authorize access="hasPermission('edit-details', 'facility')">
					<s:submit value="Save"/>
				</security:authorize>
				<s:url id="cancelUrl" action="view-ownership">
					<s:param name="facilityId" value="facilityId"/>
				</s:url>
				<s:a id="cancel" href="%{cancelUrl}" cssClass="ajaxify {target: '#ownershipSection'}">
					Cancel
				</s:a>
			</li>
		</ol>
	</s:form>
</fieldset>