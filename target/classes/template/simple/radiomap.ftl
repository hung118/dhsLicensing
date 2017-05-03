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
<@s.iterator value="parameters.list">
    <#if parameters.listKey?exists>
        <#assign itemKey = stack.findValue(parameters.listKey)/>
    <#else>
        <#assign itemKey = stack.findValue('top')/>
    </#if>
    <#assign itemKeyStr = itemKey.toString() />
    <#if parameters.listValue?exists>
        <#assign itemValue = stack.findString(parameters.listValue)/>
    <#else>
        <#assign itemValue = stack.findString('top')/>
    </#if>
<input type="radio"<#rt/>
<#if parameters.name?exists>
 name="${parameters.name?html}"<#rt/>
</#if>
 id="${parameters.id?html}${itemKeyStr?html}"<#rt/>
<#if tag.contains(parameters.nameValue?default(''), itemKeyStr)>
 checked="checked"<#rt/>
</#if>
<#if itemKey?exists>
 value="${itemKeyStr?html}"<#rt/>
</#if>
<#if parameters.disabled?default(false)>
 disabled="disabled"<#rt/>
</#if>
<#if parameters.tabindex?exists>
 tabindex="${parameters.tabindex?html}"<#rt/>
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
<#include "/${parameters.templateDir}/simple/scripting-events.ftl" />
<#include "/${parameters.templateDir}/simple/common-attributes.ftl" />
/><#rt/>
<label for="${parameters.id?html}${itemKeyStr?html}"><#rt/>
    ${itemValue}<#t/>
</label>
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
<#if parameters.nameValue?exists>
	<#assign itemFound = false/>
	<@s.iterator value="parameters.list">
		<#if parameters.listKey??>
			<#assign listKeyString = "stack.findValue('top').${parameters.listKey}.toString()"?eval/>
		<#else>
			<#assign listKeyString = stack.findValue('top').toString()/>
		</#if>
		<#if listKeyString == parameters.nameValue.toString()>
			<#assign itemFound = true/>
		</#if>
	</@s.iterator>
	<#if !itemFound>
		<#assign itemObject = stack.findValue(parameters.name)/>
		<#assign itemKey = "itemObject.${parameters.listKey}"?eval/>
    	<#assign itemKeyStr = itemKey.toString() />
		<#assign itemValue = "itemObject.${parameters.listValue}"?eval/>
		<input type="radio"<#rt/>
<#if parameters.name?exists>
 name="${parameters.name?html}"<#rt/>
</#if>
 id="${parameters.id?html}${itemKeyStr?html}"<#rt/>
 checked="checked"<#rt/>
<#if itemKey?exists>
 value="${itemKeyStr?html}"<#rt/>
</#if>
<#if parameters.disabled?default(false)>
 disabled="disabled"<#rt/>
</#if>
<#if parameters.tabindex?exists>
 tabindex="${parameters.tabindex?html}"<#rt/>
</#if>
<#assign hasFieldErrors = parameters.name?exists && fieldErrors?exists && fieldErrors[parameters.name]?exists/>
<#if parameters.cssClass?exists || hasFieldErrors>
 class="<#if parameters.cssClass?exists> ${parameters.cssClass?html}</#if><#if hasFieldErrors> inputerror</#if>"<#rt/>
</#if>
<#if parameters.cssStyle?exists>
 style="${parameters.cssStyle?html}"<#rt/>
</#if>
<#if parameters.title?exists>
 title="${parameters.title?html}"<#rt/>
</#if>
<#include "/${parameters.templateDir}/simple/scripting-events.ftl" />
<#include "/${parameters.templateDir}/simple/common-attributes.ftl" />
/><#rt/>
			<label for="${parameters.id?html}${itemKeyStr?html}"><#rt/>
			    ${itemValue}<#t/>
			</label>
	</#if>
</#if>
<#recover>
<!-- error probably just that the types don't match -->
</#attempt>