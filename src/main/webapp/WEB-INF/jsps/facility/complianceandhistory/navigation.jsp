<%@ taglib prefix="s" uri="/struts-tags"%>
<s:set var="selectedTab">${param.selectedTab}</s:set>
<ul class="ccl-subnav">
	<!-- li>
		<s:url id="complianceTabUrl" action="noncompliance" includeParams="false">
			<s:param name="facilityId" value="facilityId"/>
		</s:url>
		<s:a cssClass="ajaxify {target: '#complianceandhistoryBase'} %{#attr.selectedTab == 'compliance' ? 'selected' : ''}" href="%{complianceTabUrl}">
			Compliance
		</s:a>
	</li -->
	<li>
		<s:url id="historyTabUrl" action="history" includeParams="false">
			<s:param name="facilityId" value="facilityId"/>
		</s:url>
		<s:a cssClass="ajaxify {target: '#complianceandhistoryBase'} %{#attr.selectedTab == 'history' ? 'selected' : ''}" href="%{historyTabUrl}">
			History
		</s:a>
	</li>
	<li>
		<s:url id="fileCheckTabUrl" action="file-check" includeParams="false">
			<s:param name="facilityId" value="facilityId"/>
		</s:url>
		<s:a cssClass="ajaxify {target: '#complianceandhistoryBase'} %{#attr.selectedTab == 'fileCheck' ? 'selected' : ''}" href="%{fileCheckTabUrl}">
			Licensing Record
		</s:a>
	</li>
	<!-- li>
		<s:url id="ruleViolationsTabUrl" action="rule-violations-tab" includeParams="false">
			<s:param name="facilityId" value="facilityId"/>
		</s:url>
		<s:a cssClass="ajaxify {target: '#complianceandhistoryBase'} %{#attr.selectedTab == 'ruleViolations' ? 'selected' : ''}" href="%{ruleViolationsTabUrl}">
			Rule Violations Check
		</s:a>
	</li -->
</ul>