<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<s:set var="complaint" value="complaint" scope="request"/>
<fieldset>
	<legend>Complaint Information</legend>
		<div class="ccl-fs-ctrls">
			<div class="ccl-hist">
				<ul>
					<li><span class="label">Created By:</span> <s:property value="complaint.createdBy.firstAndLastName"/></li>
					<li><span class="label">Creation Date:</span> <s:date name="complaint.creationDate" format="MM/dd/yyyy hh:mm a"/></li>
					<li><span class="label">Last Modified By:</span> <s:property value="complaint.modifiedBy.firstAndLastName"/></li>
					<li><span class="label">Last Modified Date:</span> <s:date name="complaint.modifiedDate" format="MM/dd/yyyy hh:mm a"/></li>
				</ul>
				<s:url id="insHistUrl" action="object-history" namespace="/facility">
					<s:param name="objectId" value="complaintId"/>
				</s:url>
				<s:a id="ccl-ins-hist" href="%{insHistUrl}" cssClass="ccl-so-hist">
					History
				</s:a>
			</div>
			<a href="#" class="ccl-hist-toggle">Show/Hide Audit Information</a>
		</div>
		<script type="text/javascript">
			$(".ccl-hist-toggle").click(function() {
				$(".ccl-hist").toggle();
			});
		</script>
	<ol class="fieldList">
		<li>
			<div class="label">Status:</div>
			<div class="value">
				<s:if test="complaint.state.name() == 'INTAKE'">
					Intake incomplete
				</s:if>
				<s:elseif test="complaint.state.name() == 'FINALIZED'">
					Finalized
				</s:elseif>
				<s:if test="complaint.anonymous"> - Anonymous Complainant </s:if>
				</div>
			</li>
			<li>
				<div class="label">Received:</div>
				<div class="value"><s:date name="complaint.dateReceived" format="MM/dd/yyyy hh:mm a"/></div>
		</li>
		<li>
			<div class="label">Delivery Method:</div>
			<div class="value"><s:property value="complaint.deliveryMethod.label"/></div>
		</li>
		<li>
			<div class="label">Disclosure Statement Read:</div>
			<div class="value"><s:property value="@gov.utah.dts.det.ccl.view.YesNoChoice@valueOfBoolean(complaint.disclosureStatementRead).displayName"/></div>
		</li>
		<li>
			<div class="label">Person Completing Intake:</div>
			<div class="value"><s:property value="complaint.createdBy.firstAndLastName"/></div>
		</li>
		<s:if test="facilityLicensingSpecialist != null">
			<li>
				<div class="label">Facility Licensing Specialist:</div>
				<div class="value"><s:property value="facilityLicensingSpecialist.firstAndLastName"/></div>
			</li>
		</s:if>
		<s:if test="complaint.screening.conclusionType != null">
			<li class="clearLeft fieldMargin">
				<div class="label">Conclusion:</div>
				<div class="value"><s:property value="complaint.screening.conclusionType.value"/></div>
			</li>
		</s:if>

		<security:authorize access="hasPermission('save-intake', 'complaint')">
			<li class="submit">
				<s:url id="editInfoUrl" action="edit-information">
					<s:param name="facilityId" value="facilityId"/>
					<s:param name="complaintId" value="complaintId"/>
				</s:url>
				<s:a href="%{editInfoUrl}" cssClass="ccl-button ajaxify {target: '#complaintInformationSection'}">
					Edit
				</s:a>
			</li>
		</security:authorize>
	</ol>
</fieldset>