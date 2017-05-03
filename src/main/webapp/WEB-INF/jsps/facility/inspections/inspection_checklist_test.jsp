<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>

<legend>Test Page</legend>

<s:set id="actionBean" />

<table border="1" style="border:1px red solid">
<s:iterator value="#actionBean.checklistView" var="row">
	<tr>
		<td>
			<s:property value="#row.section.rule.number" />-<s:property value="#row.section.sectionBase" />-<s:property value="#row.section.number" /> 
			<s:property value="#row.section.title" />
			<s:property value="#row.section.name" />
		 </td>
	</tr>
	<s:iterator value="#row.results" var="result">
	<tr>
		<td>
			<s:property value="#result.id" /> 
			<s:property value="#result.result" /> 
			<s:property value="#result.comments" /> 
		 </td>
	</tr>
	</s:iterator>
</s:iterator>
</table>