<#assign idPrefix = parameters.id!"controls"/>
<#assign formId = "${idPrefix}_listControlsForm"/>
<#if parameters.nameValue.ranges?? && !parameters.nameValue.ranges.isEmpty()>
	<#assign rangeId = "${idPrefix}_rangeSelect"/>
	<#assign rangeName = "${parameters.name}.range"/>
		<div id="${idPrefix}_rangeContainer" class="rangeContainer">
	<@s.iterator value="parameters.nameValue.ranges">
		<#assign rangeKey = stack.findString('key')/>
		<#assign rangeValue = stack.findString('label')/>
		<#assign rangeValueKey = "${rangeId}_${rangeKey?html}"/>
			<input type="radio" id="${rangeValueKey?html}" name="${rangeName?html}" value="${rangeKey?html}" class="ccl-list-controls-submit radio"<#rt/>
		<#if tag.contains(parameters.nameValue.range, rangeKey) == true>
 checked="checked"<#rt/>
		</#if>
/>
			<label for="${rangeValueKey?html}" class="radioLabel">${rangeValue?html}</label>
	</@s.iterator>
		</div>
</#if>