<div class="address">
<#if parameters.address??>
	<#if parameters.address.addressOne?? && !parameters.address.addressOne.equals('')>
		${parameters.address.addressOne?default('')}<br/>
	</#if>
	<#if parameters.address.addressTwo?? && !parameters.address.addressTwo.equals('')>
		${parameters.address.addressTwo}<br/>
	</#if>
	<#if parameters.address.city?? && !parameters.address.city.equals('')>
		${parameters.address.city?default('')},
	</#if>
	${parameters.address.state?default('')} ${parameters.address.zipCode?default('')}
	<#if parameters.address.county??>
		(${parameters.address.county} County)
	</#if>
</#if>
</div>