<%@ taglib prefix="s" uri="/struts-tags"%>
<s:if test="person != null and person.id != null">
	<s:set var="legendText">Edit</s:set>
</s:if>
<s:else>
	<s:set var="legendText">Create</s:set>
</s:else>
<fieldset>
	<legend><s:property value="%{legendText}"/> Person Involved</legend>
	<s:actionerror/>
	<s:fielderror/>
	<s:form id="personInvolvedForm" action="save-person-involved" method="post" cssClass="ajaxify {target: '#peopleInvolvedSection'} ccl-action-save">
		<s:hidden name="facilityId"/>
		<s:hidden name="complaintId"/>
		<s:hidden name="person.id"/>
		<ol class="fieldList">
			<li>
				<label>Name:</label>
				<ol class="fieldGroup">
					<li>
						<label for="personFirstName">First Name:</label>
						<s:textfield id="personFirstName" name="personInvolved.firstName" cssClass="required"/>
					</li>
					<li>
						<label for="personLastName">Last Name:</label>
						<s:textfield id="personLastName" name="personInvolved.lastName"/>
					</li>
				</ol>
			</li>
			<li class="submit">
				<s:submit value="Save"/>
				<s:url id="personInvolvedEditCancelUrl" action="people-involved-list" includeParams="false">
					<s:param name="facilityId" value="facilityId"/>
					<s:param name="complaintId" value="complaintId"/>
				</s:url>
				<s:a id="personInvolvedEditCancel" href="%{personInvolvedEditCancelUrl}" cssClass="ajaxify {target: '#peopleInvolvedSection'}">
					Cancel
				</s:a>
			</li>
		</ol>
	</s:form>
	<div id="peopleInvolvedListSection">
		<s:include value="people_involved_table.jsp"/>
	</div>
</fieldset>