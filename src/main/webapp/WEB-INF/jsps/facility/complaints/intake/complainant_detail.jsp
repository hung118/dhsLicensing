<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<s:set var="complaint" value="complaint" scope="request"/>
<fieldset>
	<legend>Complainant</legend>
	<ol class="fieldList">
		<li>
			<div class="label">Name:</div>
			<div class="value"><s:if test="@org.apache.commons.lang.StringUtils@isNotEmpty(complainant.person.fullName)"><s:property value="complainant.person.fullName"/></s:if><s:else>Anonymous</s:else></div>
		</li>
		<li>
			<div class="label">Relationship to Provider:</div>
			<div class="value"><s:property value="complainant.complainantRelationship.value"/></div>
		</li>
		<li>
			<div class="label">Name Usage:</div>
			<div class="value"><s:property value="complainant.nameUsage.label"/></div>
		</li>
		<li>
			<div class="label">Statements:</div>
			<ol class="fieldGroup">
				<li>
					<div class="label">Anonymous Disclosure Statement Read?:</div>
					<div class="value"><s:property value="@gov.utah.dts.det.ccl.view.YesNoChoice@valueOfBoolean(complainant.anonymousStatementRead).displayName"/></div>
				</li>
				<li>
					<div class="label">Confidential Disclosure Statement Read?:</div>
					<div class="value"><s:property value="@gov.utah.dts.det.ccl.view.YesNoChoice@valueOfBoolean(complainant.confidentialStatementRead).displayName"/></div>
				</li>
			</ol>
		</li>
		<s:if test="complainant.person.address != null">
			<li>
				<div class="label">Address:</div>
				<div class="value">
					<s:component template="addressdisplay.ftl">
						<s:param name="address" value="complainant.person.address"/>
					</s:component>
				</div>
			</li>
		</s:if>
		<s:if test="@org.apache.commons.lang.StringUtils@isNotBlank(complainant.person.homePhone) || @org.apache.commons.lang.StringUtils@isNotBlank(complainant.person.workPhone) || @org.apache.commons.lang.StringUtils@isNotBlank(complainant.person.cellPhone)">
			<li>
				<div class="label">Phone:</div>
				<ol class="fieldGroup">
					<s:if test="@org.apache.commons.lang.StringUtils@isNotBlank(complainant.person.homePhone)">
						<li>
							<div class="label">Home:</div>
							<div class="value"><s:property value="complainant.person.homePhone"/></div>
						</li>
					</s:if>
					<s:if test="@org.apache.commons.lang.StringUtils@isNotBlank(complainant.person.workPhone)">
						<li>
							<div class="label">Work:</div>
							<div class="value"><s:property value="complainant.person.workPhone"/></div>
						</li>
					</s:if>
					<s:if test="@org.apache.commons.lang.StringUtils@isNotBlank(complainant.person.cellPhone)">
						<li>
							<div class="label">Cell:</div>
							<div class="value"><s:property value="complainant.person.cellPhone"/></div>
						</li>
					</s:if>
				</ol>
			</li>
		</s:if>
		<s:if test="@org.apache.commons.lang.StringUtils@isNotBlank(complainant.bestTimetoCall)">
			<li>
				<div class="label">Best Time to Call:</div>
				<div class="value"><s:property value="complainant.bestTimeToCall"/></div>
			</li>
		</s:if>
		<s:if test="@org.apache.commons.lang.StringUtils@isNotBlank(complainant.person.email)">
			<li>
				<div class="label">Email:</div>
				<div class="value"><s:property value="complainant.person.email"/></div>
			</li>
		</s:if>
		<security:authorize access="hasPermission('save-intake', 'complaint')">
			<li class="submit">
				<s:url id="editComplainantUrl" action="edit-complainant">
					<s:param name="facilityId" value="facilityId"/>
					<s:param name="complaintId" value="complaintId"/>
				</s:url>
				<s:a href="%{editComplainantUrl}" cssClass="ccl-button ajaxify {target: '#complainantSection'}">
					Edit
				</s:a>
			</li>
		</security:authorize>
	</ol>
</fieldset>