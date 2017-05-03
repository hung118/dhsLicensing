<div class="facilityDetail">
	<ol>
		<li class="facilityName">
<#if parameters.linkToFacility!true>
	<@s.url var="facilityEditLink" action="edit-facility" namespace="/facility" escapeAmp="false">
		<@s.param name="facility.id">${parameters.nameValue.id?c}</@s.param>
	</@s.url>
			<a href="<@s.property value="facilityEditLink"/>">${parameters.nameValue.name!''}</a>
<#else>
	${parameters.nameValue.name!''}
</#if>
		</li>
<#if parameters.showPhone!true>
		<li class="facilityPhone">${parameters.nameValue.primaryPhone!''}</li>
</#if>
<#if parameters.showAddress!true && parameters.nameValue.locationAddress??>
		<li class="facilityAddress">
			<ol>
				<li class="addressLine">
					<span class="addressOne">${parameters.nameValue.locationAddress.addressOne!''}</span>
				<li>
	<#if parameters.nameValue.locationAddress.addressTwo??>
				<li class="addressLine">
					<span class="addressTwo">${parameters.nameValue.locationAddress.addressTwo}</span>
				</li>
	</#if>
				<li class="addressLine">
					<span class="city">${parameters.nameValue.locationAddress.city!''}</span>, <span class="state">${parameters.nameValue.locationAddress.state!''}</span> <span class="zipCode">${parameters.nameValue.locationAddress.zipCode!''}</span>
				</li>
			</ol>
		</li>
</#if>
	</ol>
</div>