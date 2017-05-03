<#assign idPrefix = parameters.id!"addr"/>
<ol class="fieldGroup">
	<li>
		<label for="${idPrefix}-address-one">Address One:</label>
		<input type="text" id="${idPrefix}-address-one" name="${parameters.name}.addressOne"<#rt/>
<#if parameters.nameValue?? && parameters.nameValue.addressOne??>
 value="${parameters.nameValue.addressOne}"<#rt/>
</#if>
 class="street<#rt/>
<#assign hasFieldErrors = fieldErrors?? && fieldErrors['${parameters.name}.addressOne']??/>
<#if parameters.required!false>
 required<#rt/>
</#if>
<#if hasFieldErrors>
 inputerror<#rt/>
</#if>
"/><#lt/>
	</li>
	<li class="clearLeft fieldMargin">
		<label for="${idPrefix}-address-two">Address Two:</label>
		<input type="text" id="${idPrefix}-address-two" name="${parameters.name}.addressTwo"<#rt/>
<#if parameters.nameValue?? && parameters.nameValue.addressTwo??>
 value="${parameters.nameValue.addressTwo}"<#rt/>
</#if>
 class="street"/>
	</li>
	<li class="clearLeft fieldMargin">
		<label for="${idPrefix}-zip-code">Zip Code:</label>
		<input type="text" id="${idPrefix}-zip-code" name="${parameters.name}.zipCode"<#rt/>
<#if parameters.nameValue?? && parameters.nameValue.zipCode??>
 value="${parameters.nameValue.zipCode}"<#rt/>
</#if>
 class="ccl-address-zip-code zipCode<#rt/>
<#assign hasFieldErrors = fieldErrors?? && fieldErrors['${parameters.name}.zipCode']??/>
<#if parameters.required!false>
 required<#rt/>
</#if>
<#if hasFieldErrors>
 inputerror<#rt/>
</#if>
"/><#lt/>
	</li>
	<li class="custom fieldMargin">
		<label for="${idPrefix}-city">City:</label>
		<input type="text" id="${idPrefix}-city" name="${parameters.name}.city"<#rt/>
<#if parameters.nameValue?? && parameters.nameValue.city??>
 value="${parameters.nameValue.city}"<#rt/>
</#if>
 class="ccl-address-city city
<#if parameters.required!false>
 required<#rt/>
</#if> 
"/>
	</li>
	<li class="custom fieldMargin">
		<label for="${idPrefix}-state">State:</label>
		<input type="text" id="${idPrefix}-state" name="${parameters.name}.state"<#rt/>
<#if parameters.nameValue?? && parameters.nameValue.state??>
 value="${parameters.nameValue.state}"<#rt/>
</#if>
 class="ccl-address-state state
<#if parameters.required!false>
 required<#rt/>
</#if> 
" maxlength="2"/>
	</li>
	<li class="ccl-address-county-container clearLeft fieldMargin"<#rt/>
<#if !parameters.county??>
 style="display: none;"<#rt/>
</#if>
>
		(<span id="${idPrefix}-county" class="ccl-address-county"><#rt/>
<#if parameters.county??>
${parameters.county}<#rt/>
</#if>
		</span> County)<#lt/>
	</li>
</ol>
<script type="text/javascript">
	$("#${idPrefix}-zip-code").ccl("addressAutoComplete", 
<#if parameters.locationsStr??>
${parameters.locationsStr}
<#else>
null
</#if>
);
</script>