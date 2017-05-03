<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<script type="text/javascript">
	$(document).ready(function() {
		window.print();
	});
</script>
<p class="searchCriteria">
<h1>Search Criteria</h1>
<table>
	<tbody>
		<s:iterator value="searchCriteria" status="row">
			<tr><td><s:property value="key"/>: <s:property value="value"/></td></tr>
		</s:iterator>
	</tbody>
</table>
</p>
<s:if test="hasActionErrors()">
	<s:actionerror/>
</s:if>
<s:elseif test="lstCtrl.results.isEmpty">
	<span>There were no results matching your search criteria.</span>
</s:elseif>
<s:else>
	<s:set var="todaysDate" value="new java.util.Date()"/>
	<table class="ccl-search-results">
		<tbody>
			<s:iterator value="lstCtrl.results" status="row">
				<tr class="<s:if test="#row.odd">odd</s:if><s:else>even</s:else><s:if test="#attr.licenseExpirationDate.before(#attr.todaysDate)"> inactive</s:if> two-column">
						<td>
							<p>
								<span class="label">Facility Name:</span>
								<span class="value"><s:property value="facilityName"/></span>
							</p>
							<p>
								<span class="label">Phone:</span> 
								<span class="value"><s:property value="primaryPhone.formattedPhoneNumber"/></span>
							</p>
						<security:authorize access="principal.isInternal() or hasAnyRole('ROLE_FACILITY_PROVIDER')">
							<p><span class="label">Licensor:</span> <span class="value"><s:property value="licenseeName"/></span></p>
						</security:authorize>
						<security:authorize access="principal.isInternal() or hasAnyRole('ROLE_FACILITY_PROVIDER')">
							<p><span class="label">Primary Owner:</span> <span class="value"><s:property value="ownerName"/></span></p>
						</security:authorize>
						<p><span class="label">Director:</span> <span class="value"><s:property value="directorName"/></span></p>
						<p><span class="label">Address:</span>
							<s:if test="locationAddress != null">
								<span class="value">
									<s:property value="locationAddress.addressOne"/><br/>
									<s:if test="@org.apache.commons.lang.StringUtils@isNotBlank(locationAddress.addressTwo)">
										<s:property value="locationAddress.addressTwo"/><br/>
									</s:if>
									<s:property value="locationAddress.city"/>, <s:property value="locationAddress.state"/> <s:property value="locationAddress.zipCode"/><s:if test="@org.apache.commons.lang.StringUtils@isNotBlank(locationAddress.county)"> (<s:property value="locationAddress.county"/> County)</s:if>
									</span>
							</s:if>
						</p>

					</td>
					<td>
						<p>
							<span class="label">Facility Type:</span>
							<span class="value">
								<s:if test="status == 'R'">
									<s:if test="licenseType != null"><s:property value="licenseType"/> - <s:if test="conditional"><span class="redtext">Conditional</span></s:if> <s:property value="licenseSubtype"/></s:if><s:else><s:property value="facilityType.displayName"/></s:else>
								</s:if>
								<s:elseif test="status == 'I' or status == 'P'">
									<s:if test="licenseType != null"><s:property value="licenseType"/> - <s:property value="licenseSubtype"/></s:if><s:else><s:property value="facilityType.displayName"/></s:else>
								</s:elseif>
								<s:elseif test="status == 'X'">
									<s:property value="facilityType.displayName"/>
								</s:elseif>
								<s:else>
									<s:property value="facilityType.displayName"/>
								</s:else>
							</span>
						</p>
						<p><span class="label">Facility Status:</span>
							<span class="value">
								<s:if test="status == 'R'">Regulated</s:if>
								<s:elseif test="status == 'I'">Inactive</s:elseif>
								<s:elseif test="status == 'P'">In Process</s:elseif>
								<s:elseif test="status == 'X'">Exempt</s:elseif>
								<s:else>Unlicensed</s:else>
								</span>
							</p>
						<security:authorize access="principal.isInternal() or hasAnyRole('ROLE_FACILITY_PROVIDER')">
							<s:if test="licenseTypeId == null and exemptions != null">
								<p><span class="label">Exemptions:</span> <span class="value"><s:property value="exemptions"/></span></p>
								<p><span class="label">Exemption Expiration Date:</span> <span class="value"><s:property value="exemptionExpirationDate"/></span></p>
								</s:if>
								<s:if test="inactiveReasonId != null">
								<p><span class="label">Inactive Reason:</span> <span class="value"><s:property value="inactiveReason"/></span></p>
								</s:if>
								<s:if test="initialRegulationDate != null">
								<p><span class="label">Initial Regulation Date:</span> <span class="value"><s:date name="initialRegulationDate" format="MM/dd/yyyy"/></span></p>
								</s:if>
								<s:if test="closureDate != null">
								<p><span class="label">Closure Date:</span> <span class="value"><s:date name="closureDate" format="MM/dd/yyyy"/></span></p>
								</s:if>
							</security:authorize>
							<s:if test="licenseTypeId != null">
							<p><span class="label">Expiration Date:</span> <span class="value"><s:date name="licenseExpirationDate" format="MM/dd/yyyy"/></span></p>
								<security:authorize access="principal.isInternal() or hasAnyRole('ROLE_FACILITY_PROVIDER')">
								<p><span class="label">Total # of Children:</span><span class="value"> <s:property value="totalSlots"/></span></p>
								<p><span class="label">Total # of Children &lt; Age 2:</span><span class="value"> <s:property value="slotsAgeTwo"/></span></p>
							</security:authorize>
						</s:if>
						<security:authorize access="principal.isInternal() or hasAnyRole('ROLE_FACILITY_PROVIDER')">
							<s:if test="licensingSpecialist != null">
								<p><span class="label">Licensing Specialist:</span><span class="value"> <s:property value="licensingSpecialistName"/></span></p>
							</s:if>
						</security:authorize>
						<p><span class="label">Facility ID#:</span><span class="value"><s:property value="facilityIdNumber"/></span></p>
					</td>
				</tr>
			</s:iterator>
		</tbody>
	</table>
</s:else>