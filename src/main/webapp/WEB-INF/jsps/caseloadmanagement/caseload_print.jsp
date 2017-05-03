<%@ taglib prefix="s" uri="/struts-tags"%>
<script type="text/javascript">
	$(document).ready(function() {
		window.print();
	});
</script>
<h1>Licensing Specialist Caseload for <s:property value="specialist.firstAndLastName"/></h1>
<s:if test="hasActionErrors()">
	<s:actionerror/>
</s:if>
<table class="ccl-search-results">
	<thead>
		<tr>
			<td>Facility Name</td>
			<td>Facility ID#</td>
			<td>Address</td>
			<td>1st Director(s)</td>
			<td>Status</td>
			<td>Type</td>
			<td>Phone</td>
			<td>Capacity (&lt;2)</td>
		</tr>
	</thead>
	<tbody>
		<s:iterator value="caseload" status="row">
			<tr class="<s:if test="#row.odd">odd</s:if><s:else>even</s:else>">
				<td>
					<s:property value="name"/>
				</td>
				<td>
					<s:property value="idNumber"/>
				</td>
					<p><s:property value="primaryPhone.formattedPhoneNumber"/></p>
					<s:if test="locationAddress != null">
						<p>
							<s:property value="locationAddress.addressOne"/><br/>
							<s:if test="@org.apache.commons.lang.StringUtils@isNotBlank(locationAddress.addressTwo)">
								<s:property value="locationAddress.addressTwo"/><br/>
							</s:if>
							<s:property value="locationAddress.city"/>, <s:property value="locationAddress.state"/> <s:property value="locationAddress.zipCode"/><s:if test="@org.apache.commons.lang.StringUtils@isNotBlank(locationAddress.county)"> (<s:property value="locationAddress.county"/> County)</s:if>
						</p>
					</s:if>
					<s:if test="@org.apache.commons.lang.StringUtils@isNotBlank(licenseeName)">
						<p><span class="label">Licensee:</span><span class="value"><s:property value="licenseeName"/></span></p>
					</s:if>
					<s:if test="@org.apache.commons.lang.StringUtils@isNotBlank(ownerName)">
						<p><span class="label">Primary Owner:</span><span class="value"><s:property value="ownerName"/></span></p>
					</s:if>
					<s:if test="@org.apache.commons.lang.StringUtils@isNotBlank(directorName)">
						<p><span class="label">Director:</span><span class="value"><s:property value="directorName"/></span></p>
					</s:if>
				</td>
				<td>
					<p><span class="label">Facility Type:</span><span class="value"><s:if test="licenseTypeId != null"><s:property value="licenseType"/> - <s:property value="licenseSubtype"/></s:if><s:else><s:if test="exemptions != null">Exempt</s:if><s:else>Unlicensed</s:else> <s:property value="facilityType.displayName"/></s:else></span></p>
					<s:if test="licenseTypeId == null and exemptions != null">
						<p><span class="label">Exemptions:</span><span class="value"><s:property value="exemptions"/></span></p>
					</s:if>
					<s:if test="inactiveReasonId != null">
						<p><span class="label">Inactive Reason:</span><span class="value"><s:property value="inactiveReason"/></span></p>
					</s:if>
					<s:if test="initialRegulationDate != null">
						<p><span class="label">Initial Regulation Date:</span><span class="value"><s:date name="initialRegulationDate" format="MM/dd/yyyy"/></span></p>
					</s:if>
					<s:if test="licenseTypeId != null">
						<p><span class="label">Expiration Date:</span><span class="value"><s:date name="licenseExpirationDate" format="MM/dd/yyyy"/></span></p>
						<p><span class="label">Total # of Children:</span><span class="value"><s:property value="totalSlots"/></span></p>
						<p><span class="label">Total # of Children &lt; Age 2:</span><span class="value"><s:property value="slotsAgeTwo"/></span></p>
					</s:if>
					<s:if test="licensingSpecialist != null">
						<p><span class="label">Licensing Specialist:</span><span class="value"><s:property value="licensingSpecialist.firstAndLastName"/></span></p>
					</s:if>
					<p><span class="label">Facility ID#:</span><span class="value"><s:property value="facilityIdNumber"/></span></p>
				</td>
			</tr>
		</s:iterator>
	</tbody>
</table>