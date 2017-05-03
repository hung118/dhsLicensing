<input type="hidden"<#rt/>
 name="${parameters.name!''?html}"<#rt/>
<#if parameters.id??>
 id="${parameters.id?html}"<#rt/>
</#if>
<#if parameters.nameValue?? && parameters.nameValue.id??>
 value="${parameters.nameValue.id}"<#rt/>
</#if>
/>
<input type="text"<#rt/>
 name="${parameters.name!''?html}AC"<#rt/>
<#if parameters.get("size")??>
 size="${parameters.get("size")?html}"<#rt/>
</#if>
<#if parameters.maxlength??>
 maxlength="${parameters.maxlength?html}"<#rt/>
</#if>
<#if parameters.nameValue?? && parameters.nameValue.name??>
 value="${parameters.nameValue.name}"<#rt/>
</#if>
<#if parameters.disabled!false>
 disabled="disabled"<#rt/>
</#if>
<#if parameters.readonly!false>
 readonly="readonly"<#rt/>
</#if>
<#if parameters.tabindex??>
 tabindex="${parameters.tabindex?html}"<#rt/>
</#if>
<#if parameters.id??>
 id="${parameters.id?html}AC"<#rt/>
</#if>
 class="facilityAC<#rt/>
<#assign hasFieldErrors = parameters.name?? && fieldErrors?? && fieldErrors[parameters.name]??/>
<#if parameters.cssClass?? || hasFieldErrors>
 <#if parameters.cssClass??>${parameters.cssClass?html}</#if><#if hasFieldErrors> inputerror</#if><#rt/>
</#if>
<#if parameters.required!false>
 required<#rt/>
</#if>
"<#rt/>
<#if parameters.cssStyle??>
 style="${parameters.cssStyle?html}"<#rt/>
</#if>
<#if parameters.title??>
 title="${parameters.title?html}"<#rt/>
</#if>
<#include "/${parameters.templateDir}/simple/scripting-events.ftl" />
<#include "/${parameters.templateDir}/simple/common-attributes.ftl" />
/>
<div
<#if parameters.id??>
 id="${parameters.id?html}ACContainer"<#rt/>
</#if>
 class="facilityACContainer"></div>
<div
<#if parameters.id??>
 id="${parameters.id?html}facilityDetails"<#rt/>
</#if>
<#if !parameters.nameValue?? || !parameters.nameValue.name??>
 style="display: none;<#rt/>
</#if>
">
	<div>
		<span id="${parameters.id?html}facilityName">
<#if parameters.nameValue?? && parameters.nameValue.name??>
			${parameters.nameValue.name!''}
</#if>
		</span>
		(<span id="${parameters.id?html}facilityIdNumber">
<#if parameters.nameValue?? && parameters.nameValue.idNumber??>
			${parameters.nameValue.idNumber!''}
</#if>
		</span>)
	</div>
	<div>
		<span id="${parameters.id?html}facilityAddressOne">
<#if parameters.nameValue?? && parameters.nameValue.locationAddress??>
	<#assign addrExists = true/>
</#if>
<#if addrExists?? && parameters.nameValue.locationAddress.addressOne??>
			${parameters.nameValue.locationAddress.addressOne!''}
</#if>
		</span>
	</div>
	<div id="${parameters.id?html}facilityAddressTwoWrapper"
<#if !addrExists?? || !parameters.nameValue.locationAddress.addressTwo??>
 style="display: none;"<#rt/>
</#if>
>
		<span id="${parameters.id?html}facilityAddressTwo">
<#if addrExists?? && parameters.nameValue.locationAddress.addressTwo??>
			${parameters.nameValue.locationAddress.addressTwo!''}
</#if>
		</span>
	</div>
	<div>
		<span id="${parameters.id?html}facilityCity">
<#if addrExists?? && parameters.nameValue.locationAddress.city??>
			${parameters.nameValue.locationAddress.city!''}
</#if>
		</span>,
		<span id="${parameters.id?html}facilityState">
<#if addrExists?? && parameters.nameValue.locationAddress.state??>
			${parameters.nameValue.locationAddress.state!''}
</#if>
		</span>
		<span id="${parameters.id?html}facilityZipCode">
<#if addrExists?? && parameters.nameValue.locationAddress.zipCode??>
			${parameters.nameValue.locationAddress.zipCode!''}
</#if>
		</span>
		<span id="${parameters.id?html}facilityCountyWrapper"
<#if !addrExists?? || !parameters.nameValue.locationAddress.county??>
 style="display: none;"<#rt/>
</#if>		
>
			<span id="${parameters.id?html}facilityCounty">(<#rt/>
<#if addrExists?? && parameters.nameValue.locationAddress.county??>
				${parameters.nameValue.locationAddress.county!''}
</#if>
 County)
			</span>
		</span>
	</div>
	<div id="${parameters.id?html}facilityPrimaryPhoneWrapper"
<#if !parameters.nameValue?? || !parameters.nameValue.primaryPhone??>
 style="display: none;"<#rt/>
</#if>
>
		<span id="${parameters.id?html}facilityPrimaryPhone">
<#if parameters.nameValue?? && parameters.nameValue.primaryPhone??>
			${parameters.nameValue.primaryPhone.formattedPhoneNumber!''}
</#if>
		</span>
	</div>
</div>
<script type="text/javascript">
	var params = {<#rt/>
<#if parameters.facilityId??>
 facilityId: ${parameters.facilityId?c}<#rt/>
</#if>
};
	initFacilityAutoComplete("${parameters.id?html}", params);
</script>