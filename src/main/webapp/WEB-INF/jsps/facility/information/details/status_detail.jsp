<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<fieldset>
	<legend>Status Details</legend>
	<s:actionerror/>
	<ol class="fieldList">
		<li>
			<div class="label">Status:</div>
			<div class="value"><s:property value="facility.status.label"/></div>
		</li>
		<s:if test="deactivation != null">
			<li>
				The facility is set to deactivate on <s:date name="deactivation.startDate" format="MM/dd/yyyy"/>.  Reason: <s:property value="deactivation.tag.value"/>
				<s:if test="statusEditable">
					<s:url id="cancelDeactivationUrl" action="cancel-deactivation">
						<s:param name="facilityId" value="facilityId"/>
					</s:url>
					<s:a href="%{cancelDeactivationUrl}" cssClass="ccl-button ajaxify {target: '#statusSection'}">
						Cancel Deactivation
					</s:a>
				</s:if>
			</li>
		</s:if>
		<s:if test="facility.status.name().equals('REGULATED') or facility.status.name().equals('IN_PROCESS')">
			<li>
				<div class="label">Licensing Specialist:</div>
				<div class="value"><s:property value="facility.licensingSpecialist.firstAndLastName"/></div>			
			</li>
			 
			<li>
				<div class="label">Initial Regulation Date:</div>
				<div class="value"><s:date name="facility.initialRegulationDate" format="MM/dd/yyyy"/></div>
			</li>
		</s:if>
		<li class="submit">
			<s:if test="statusEditable">
				<s:url id="editStatusUrl" action="edit-status">
					<s:param name="facilityId" value="facilityId"/>
				</s:url>
				<s:a href="%{editStatusUrl}" cssClass="ccl-button ajaxify {target: '#statusSection'}">
					Edit
				</s:a>
			</s:if>
		</li>
	</ol>
</fieldset>