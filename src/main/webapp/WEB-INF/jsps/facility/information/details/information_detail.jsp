<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<fieldset>
	<legend>Facility Information</legend>
	<ol class="fieldList">
		<li>
			<div class="label">
				<s:if test="facility.type == null or facility.type.name().equals('LICENSE_FOSTER_CARE') or facility.type.name().equals('LICENSE_SPECIFIC_CARE')">
					Last, First Name:
				</s:if>
				<s:else>
					Name:
				</s:else>
			</div>
			<div class="value"><s:property value="facility.name"/></div>
		</li>
		<li>
			<div class="label">Site Name:</div>
			<div class="value"><s:property value="facility.siteName"/></div>
		</li>
		<li>
			<div class="label">Phone:</div>
			<ol class="fieldGroup">
				<li>
					<div class="label">Primary:</div>
					<div class="value"><s:property value="facility.primaryPhone.formattedPhoneNumber"/></div>
				</li>
				<s:if test="facility.alternatePhone != null">
					<li>
						<div class="label">Alternate:</div>
						<div class="value"><s:property value="facility.alternatePhone.formattedPhoneNumber"/></div>
					</li>
				</s:if>
				<s:if test="facility.fax != null">
					<li>
						<div class="label">Fax:</div>
						<div class="value"><s:property value="facility.fax.formattedPhoneNumber"/></div>
					</li>
				</s:if>
			</ol>
		</li>
		<s:if test="@org.apache.commons.lang.StringUtils@isNotBlank(facility.websiteUrl)">
			<li>
				<div class="label">Website URL:</div>
				<div class="value">
					<a href="<s:property value='facility.websiteUrl' escape='false'/>">
						<s:property value="facility.websiteUrl" escape="false"/>
					</a>
				</div>
			</li>
		</s:if>
		<s:if test="@org.apache.commons.lang.StringUtils@isNotBlank(facility.email)">
			<li>
				<div class="label">Email:</div>
				<div class="value">
					<a href="mailto:<s:property value='facility.email' escape='false'/>">
						<s:property value="facility.email" escape="false"/>
					</a>
				</div>
			</li>
		</s:if>
		<s:if test="@org.apache.commons.lang.StringUtils@isNotBlank(facility.cbsEmail)">
			<li>
				<div class="label">CBS Email:</div>
				<div class="value">
					<a href="mailto:<s:property value='facility.cbsEmail' escape='false'/>">
						<s:property value="facility.cbsEmail" escape="false"/>
					</a>
				</div>
			</li>
		</s:if>
		<li>	
			<s:action name="primary-contacts-list" executeResult="true"/>
		</li>
		<li>
			<div class="label">Address:</div>
			<ol class="fieldGroup">
				<li>
					<div class="label">Location:</div>
					<div class="value">
						<s:component template="addressdisplay.ftl">
							<s:param name="address" value="facility.locationAddress"/>
						</s:component>
					</div>
				</li>
				<li>
					<div class="label">Mailing:</div>
					<div class="value">
						<s:component template="addressdisplay.ftl">
							<s:param name="address" value="facility.mailingAddress"/>
						</s:component>
					</div>
				</li>
				<li>
					<div class="label">CBS:</div>
					<div class="value">
						<s:component template="addressdisplay.ftl">
							<s:param name="address" value="facility.cbsAddress"/>
						</s:component>
					</div>
				</li>
			</ol>
		</li>
		<li>
			<div class="label">Facility Type:</div>
			<div class="value"><s:property value="facility.type.displayName"/></div>
		</li>
		<li>
			<div class="label">Status:</div>
			<div class="value"><s:property value="facility.status.label"/></div>
		</li>
		<s:if test="facility.status.name().equals('INACTIVE')">
			<li>
				<div class="label">Closed Date:</div>
				<div class="value"><s:date name="facility.closedDate" format="MM/dd/yyyy"/></div>
			</li>
		</s:if>
		<s:if test="deactivation != null">
			<li>
				The facility is set to deactivate on <s:date name="deactivation.startDate" format="MM/dd/yyyy"/>.  Reason: <s:property value="deactivation.tag.value"/>
				<s:if test="statusEditable">
					<s:url id="cancelDeactivationUrl" action="cancel-deactivation">
						<s:param name="facilityId" value="facilityId"/>
					</s:url>
					<s:a href="%{cancelDeactivationUrl}" cssClass="ccl-button ajaxify {target: '#informationSection'}">
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

		<li>
			<div class="label">CBS Technician:</div>
			<div class="value"><s:property value="facility.cbsTechnician.firstAndLastName"/></div>			
		</li>

		<s:if test="facility.indoorSquareFootage != null || facility.outdoorSquareFootage != null">
			<li>
				<div class="label">Square Footage:</div>
				<ol class="fieldGroup">
					<s:if test="facility.indoorSquareFootage != null">
						<li>
							<div class="label">Indoor Square Footage:</div>
							<div class="value"><s:property value="facility.indoorSquareFootage"/> sq. ft.</div>
						</li>
					</s:if>
					<s:if test="facility.outdoorSquareFootage != null">
						<li>
							<div class="label">Outdoor Square Footage:</div>
							<div class="value"><s:property value="facility.outdoorSquareFootage"/> sq. ft.</div>
						</li>
					</s:if>
				</ol>
			</li>
		</s:if>

		<li>
			<div class="label">SAFE Provider ID:</div>
			<div class="value"><s:property value="facility.safeProviderId"/></div>			
		</li>
		<security:authorize access="hasPermission('edit-details', 'facility')">
			<li class="submit">
				<s:url id="editInfoUrl" action="edit-information">
					<s:param name="facilityId" value="facilityId"/>
				</s:url>
				<s:a href="%{editInfoUrl}" cssClass="ajaxify ccl-button {target: '#informationSection'}">
					Edit
				</s:a>
			</li>
		</security:authorize>
	</ol>
</fieldset>
<script type="text/javascript">
$(document).ready(function() {
	$("#infoFacilityName").val("<s:property value="facility.name"/>");
	$("#infoPrimaryPhone").val("<s:property value="facility.primaryPhone.formattedPhoneNumber"/>");
	$("#infoEmail").val("<s:property value="facility.email" escape="false"/>");
	$("#infoMailAddressOne").val("<s:property value="facility.mailingAddress.addressOne"/>");
	$("#infoMailZipCode").val("<s:property value="facility.mailingAddress.zipCode"/>");
	$("#infoMailCity").val("<s:property value="facility.mailingAddress.city"/>");
	$("#infoMailState").val("<s:property value="facility.mailingAddress.state"/>");
	$("#infoFacilityType").val("<s:property value="facility.type"/>");
	toggleFacilityTabs();
});
</script>