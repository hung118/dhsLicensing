<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="dts" uri="/WEB-INF/tags/dts-tags.tld"%>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<s:url var="searchAgainUrl" action="index" includeParams="all" escapeAmp="false"/>
<script type="text/javascript">
	$(document).ready(function() {
		$(".searchAgain").click(function() {
			window.location.href="<s:property value='%{searchAgainUrl}' escape='false'/>";
		});
	});
</script>
<fieldset>
	<legend>Search Results</legend>
	<div class="topControls">
		<div class="mainControls">
			<button type="button" class="searchAgain">Search Again</button>
		</div>
		<dts:listcontrols id="facilityTopControls" name="lstCtrl" action="search-results" namespace="/public/facility/search" enablePaging="true" maxPagesToShow="4"/>
	</div>
	<s:if test="lstCtrl.results.isEmpty">
		<div class="searchResults">There were no results matching your search criteria.</div>
	</s:if>
	<s:else>
		<ol class="ccl-list">
			<s:set var="todaysDate" value="new java.util.Date()"/>
			<s:iterator value="lstCtrl.results" status="row">
				<li class="ccl-list-item clearfix <s:if test="#row.odd">odd</s:if><s:else>even</s:else>">
					<div class="left-column">
						<div>
							<span class="label">Facility Name:</span>
							<s:property value="facilityName"/>
						</div>
						<div>
							<span class="label">Phone:</span>
							<s:property value="primaryPhone.formattedPhoneNumber"/>
						</div>
						<s:if test="#attr.licenseType != 'Licensed Family' && #attr.licenseType != 'Residential Certificate'">
							<div>
								<span class="label">Address:</span>
								<s:component template="addressdisplay.ftl">
									<s:param name="address" value="locationAddress"/>
								</s:component>
							</div>
						</s:if>
						<s:else>
							<div>
								<span class="label">Location:</span>
								<s:property value="address.city"/>, <s:property value="address.state"/> <s:property value="address.zipCode"/>
							</div>
						</s:else>
					</div>
					<div class="right-column">
						<div>
							<span class="label">Provider Type:</span>
							<s:property value="licenseType"/><s:if test="conditional"> - Conditional License</s:if>
						</div>
						<div>
							<span class="label">Total # of Children:</span>
							<s:property value="totalSlots"/>
						</div>
						<div>
							<span class="label">Total # of Children &lt; Age 2:</span>
							<s:property value="slotsAgeTwo"/>
						</div>
						<s:if test="directorName != null">
						<div>
							<span class="label">Director:</span>
							<s:property value="directorName"/>
						</div>
						</s:if>
					</div>
					<div class="ccl-abs-bot-right">
						<s:url id="fileCheckUrl" action="file-check" namespace="/pub/facility" includeParams="false">
							<s:param name="facilityId" value="id"/>
						</s:url>
						<s:a href="%{fileCheckUrl}" cssClass="ccl-button">
							View Licensing Record
						</s:a>
					</div>
				</li>
			</s:iterator>
		</ol>
		<div class="bottomControls">
			<div class="mainControls">
				<button type="button" class="searchAgain">Search Again</button>
			</div>
			<dts:listcontrols id="facilityBottomControls" name="lstCtrl" action="search-results" namespace="/public/facility/search" enablePaging="true" maxPagesToShow="4"/>
		</div>
	</s:else>
</fieldset>