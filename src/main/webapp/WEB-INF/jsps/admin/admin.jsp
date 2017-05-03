<%@ taglib prefix="s" uri="/struts-tags"%>
<div id="tabs">
	<s:url id="welcomeTabUrl" action="tab" namespace="/admin/welcome" escapeAmp="false"/>
	<s:url id="accountsTabUrl" action="tab" namespace="/admin/accounts" escapeAmp="false"/>
	<s:url id="pickListsTabUrl" action="tab" namespace="/admin/picklists" escapeAmp="false"/>
	<s:url id="ncpointsTabUrl" action="tab" namespace="/admin/noncompliance" escapeAmp="false"/>
	<s:url id="regionsTabUrl" action="tab" namespace="/admin/regions" escapeAmp="false"/>
	<s:url id="rulesTabUrl" action="tab" namespace="/admin/rules" escapeAmp="false"/>
	<s:url id="rulecrTabUrl" action="tab" namespace="/admin/rulecrossreference" escapeAmp="false"/>
	<s:url id="templatesTabUrl" action="tab" namespace="/admin/documents" escapeAmp="false"/>
	<s:url id="locationsTabUrl" action="tab" namespace="/admin/locations" escapeAmp="false"/>
	<ul>
		<li><s:a href="%{welcomeTabUrl}">Welcome</s:a></li>
		<li><s:a href="%{accountsTabUrl}">Accounts</s:a></li>
		<li><s:a href="%{pickListsTabUrl}">Pick Lists</s:a></li>
		<li><s:a href="%{ncpointsTabUrl}">NC Points &amp; Triggers</s:a></li>
		<li><s:a href="%{regionsTabUrl}">Regions</s:a></li>
		<li><s:a href="%{rulesTabUrl}">Rules</s:a></li>
<%-- 	
		<li><s:a href="%{rulecrTabUrl}">Rule Cross Reference</s:a></li>
--%>
		<li><s:a href="%{templatesTabUrl}">Docs &amp; Templates</s:a></li>
		<li><s:a href="%{locationsTabUrl}">Locations</s:a></li>
	</ul>
</div>