<#--
/*
 * $Id: Action.java 502296 2007-02-01 17:33:39Z niallp $
 *
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
-->
<#setting number_format="#.#####">
<select<#rt/>
 name="${parameters.name?default("")?html}"<#rt/>
<#if parameters.get("size")?exists>
 size="${parameters.get("size")?html}"<#rt/>
</#if>
<#if parameters.disabled?default(false)>
 disabled="disabled"<#rt/>
</#if>
<#if parameters.tabindex?exists>
 tabindex="${parameters.tabindex?html}"<#rt/>
</#if>
<#if parameters.id?exists>
 id="${parameters.id?html}"<#rt/>
</#if>
<#assign hasFieldErrors = parameters.name?exists && fieldErrors?exists && fieldErrors[parameters.name]?exists/>
<#if parameters.cssClass?exists || hasFieldErrors>
 class="<#if parameters.cssClass?exists>${parameters.cssClass?html}</#if><#if hasFieldErrors> inputerror</#if>"<#rt/>
</#if>
<#if parameters.cssStyle?exists>
 style="${parameters.cssStyle?html}"<#rt/>
</#if>
<#if parameters.title?exists>
 title="${parameters.title?html}"<#rt/>
</#if>
<#if parameters.multiple?default(false)>
 multiple="multiple"<#rt/>
</#if>
<#include "/${parameters.templateDir}/simple/scripting-events.ftl" />
<#include "/${parameters.templateDir}/simple/common-attributes.ftl" />
>
<#if parameters.headerKey?exists && parameters.headerValue?exists>
    <option value="${parameters.headerKey?html}"
    <#if tag.contains(parameters.nameValue, parameters.headerKey) == true>
    selected="selected"
    </#if>
    >${parameters.headerValue?html}</option>
</#if>
<#if parameters.emptyOption?default(false)>
    <option value=""></option>
</#if>
<@s.iterator value="parameters.list">
        <#if parameters.listKey?exists>
            <#if stack.findValue(parameters.listKey)?exists>
              <#assign itemKey = stack.findValue(parameters.listKey)/>
              <#assign itemKeyStr = itemKey.toString()/>
            <#else>
              <#assign itemKey = ''/>
              <#assign itemKeyStr = ''/>
            </#if>
        <#else>
            <#assign itemKey = stack.findValue('top')/>
            <#assign itemKeyStr = itemKey.toString()/>
        </#if>
        <#if parameters.listValue?exists>
            <#if stack.findString(parameters.listValue)?exists>
              <#assign itemValue = stack.findString(parameters.listValue)/>
            <#else>
              <#assign itemValue = ''/>
            </#if>
        <#else>
            <#assign itemValue = stack.findString('top')/>
        </#if>
    <option value="${itemKeyStr?html}"<#rt/>
        <#if tag.contains(parameters.nameValue, itemKey) == true>
 selected="selected"<#rt/>
        </#if>
    >${itemValue?html}</option><#lt/>
</@s.iterator>
<#--
/*
 * Custom extension to the template to add the ability to insert values that are no longer active into the dropdown so that
 * historical data can be preserved on an edit.  For example if the admin deactivates an inspection type pick list value
 * that is in use on an inspection and someone edits that inspection the inspection type will still be set.  If it is changed
 * and saved it will no longer be available to be selected.  We can only do this if the nameValue is the same type as the
 * items in the list.
 */
-->
<#attempt>
<#if parameters.nameValue?exists && !parameters.nameValue?is_enumerable>
	<#assign itemFound = false/>
	<@s.iterator value="parameters.list">
		<#if parameters.listKey??>
			<#assign listKeyString = "stack.findValue('top').${parameters.listKey}"?eval/>
		<#else>
			<#assign listKeyString = stack.findValue('top').toString()/>
		</#if>
		<#if listKeyString == parameters.nameValue>
			<#assign itemFound = true/>
		</#if>
	</@s.iterator>
	<#if !itemFound>
		<#assign itemObject = stack.findValue(parameters.name)/>
		<#assign itemKey = "itemObject.${parameters.listKey}"?eval/>
		<#assign itemValue = "itemObject.${parameters.listValue}"?eval/>
		<option value="${itemKey?html}" selected="selected">${itemValue?html}</option>
	</#if>
<#elseif parameters.nameValue?exists && parameters.nameValue?is_enumerable && stack.findValue(parameters.name)?exists>
	<#list stack.findValue(parameters.name) as n>
		<#if "n.${parameters.listKey}"?eval?exists && "n.${parameters.listValue}"?eval?exists>
			<#assign itemFound = false/>
			<@s.iterator value="parameters.list">
				<#if "n.${parameters.listKey}"?eval?exists && "stack.findValue('top').${parameters.listKey}"?eval == "n.${parameters.listKey}"?eval>
					<#assign itemFound = true/>
				</#if>
			</@s.iterator>
			<#if !itemFound>
				<#assign itemKey = "n.${parameters.listKey}"?eval/>
				<#assign itemValue = "n.${parameters.listValue}"?eval/>
				<#if itemKey?exists && itemValue?exists>
					<option value="${itemKey?html}" selected="selected">${itemValue?html}</option>
				</#if>
			</#if>
		</#if>
	</#list>
</#if>
<#recover>
<!-- error probably just that the types don't match -->
</#attempt>

<#include "/${parameters.templateDir}/simple/optgroup.ftl" />

</select>