<#assign personName = parameters.name!'person'/>
<#assign idPrefix = parameters.idPrefix!'${personName}'/>
<#assign formId = '${idPrefix}PersonForm'/>
<#assign firstNameId = '${idPrefix}FirstName'/>
<#assign middleNameId = '${idPrefix}MiddleName'/>
<#assign lastNameId = '${idPrefix}LastName'/>
<#assign primaryPhoneId = '${idPrefix}PrimaryPhone'/>
<#assign emailId = '${idPrefix}Email'/>
<form id="${formId}" action="${parameters.action}" method="post" class="personForm ajaxify {target: '#${parameters.target}'}">
<#if parameters.person?? && parameters.person.id??>
	<input type="hidden" name="${personName}" value="${parameters.person.id.toString()}"/>
</#if>
	<ol class="fieldList">
		${parameters.additionalFieldsTop!''}
		<li>
			<label><span class="redtext">* </span>Name:</label>
			<ol class="fieldGroup">
				<li>
					<label for="${firstNameId}">First Name:</label>
					<input type="text" id="${firstNameId}" name="${personName}.firstName"
<#if parameters.person?? && parameters.person.firstName??>
 value="${parameters.person.firstName}"<#rt/>
</#if>
 class="required name<#rt/>
<#assign hasFieldErrors = fieldErrors?? && fieldErrors['${personName}.firstName']??/>
<#if hasFieldErrors>
 inputerror<#rt/>
</#if>
"/>
				</li>
				<li>
					<label for="${middleNameId}">Middle Name:</label>
					<input type="text" id="${middleNameId}" name="${personName}.middleName"
<#if parameters.person?? && parameters.person.middleName??>
 value="${parameters.person.middleName}"<#rt/>
</#if>
 class="optionalName"<#rt/>
/>
				</li>
				<li>
					<label for="${lastNameId}">Last Name:</label>
					<input type="text" id="${lastNameId}" name="${personName}.lastName"
<#if parameters.person?? && parameters.person.lastName??>
 value="${parameters.person.lastName}"<#rt/>
</#if>
 class="required name<#rt/>
<#assign hasFieldErrors = fieldErrors?? && fieldErrors['${personName}.lastName']??/>
<#if hasFieldErrors>
 inputerror<#rt/>
</#if>
"/>
				</li>
			</ol>
		</li>
		<li>
			<label><#if parameters.addressRequired!false><span class="redtext">* </span></#if>Address:</label>
			<#assign addressName = '${personName}.address'/>
			<#if parameters.person?? && parameters.person.address??>
				<#assign addressObject = parameters.person.address/>
			</#if>
			<#assign addressRequired = parameters.addressRequired!false/>
			<#include "addressinput.ftl">
		</li>
		<li>
			<label for="${primaryPhoneId}AreaCode">Phone:</label>
			( <input type="text" id="${primaryPhoneId}AreaCode" name="${personName}.primaryPhone.areaCode" class="phoneAreaCode" maxlength="3" /> ) 
			 <input type="text" id="${primaryPhoneId}Prefix" name="${personName}.primaryPhone.prefix" class="phonePrefix" maxlength="3" /> - 
			 <input type="text" id="${primaryPhoneId}Suffix" name="${personName}.primaryPhone.suffix" class="phoneSuffix" maxlength="4" />
		</li>
		<li>
			<label for="${emailId}">Email:</label>
			<input type="text" id="${emailId}" name="${personName}.email"
<#if parameters.person?? && parameters.person.email??>
 value="${parameters.person.email}"<#rt/>
</#if>
 class="email<#rt/>
<#assign hasFieldErrors = fieldErrors?? && fieldErrors['${personName}.email']??/>
<#if hasFieldErrors>
 inputerror<#rt/>
</#if>
"/>
		</li>
		${parameters.additionalFieldsBottom!''}
		<li class="submit">
			<input type="submit" value="Save"/>
			<a class="ajaxify {target: '#${parameters.target}'}" href="${parameters.cancelUrl}">Cancel</a>
		</li>
	</ol>
</form>