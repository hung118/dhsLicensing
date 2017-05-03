<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<fieldset>
	<legend>Ownership</legend>
	<ol class="fieldList">
		<li>
			<div class="label">Type:</div>
			<div class="value"><s:property value="facility.ownershipType.value"/></div>
		</li>
		<li>
			<div class="label">Owner Name:</div>
			<div class="value"><s:property value="facility.ownerName"/></div>
		</li>
		<security:authorize access="hasPermission('edit-details','facility')">
			<li class="submit">
				<s:url id="editOwnershipUrl" action="edit-ownership">
					<s:param name="facilityId" value="facilityId"/>
				</s:url>
				<s:a href="%{editOwnershipUrl}" cssClass="ccl-button ajaxify {target: '#ownershipSection'}">
					Edit
				</s:a>
			</li>
		</security:authorize>
	</ol>
</fieldset>