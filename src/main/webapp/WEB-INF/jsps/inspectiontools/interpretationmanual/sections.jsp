<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="display" uri="http://displaytag.sf.net"%>
<fieldset>
	<legend>Interpretation Manual</legend>
	<s:actionerror/>
	<s:if test="!rule.sections.isEmpty">
		<display:table name="rule.sections" id="sections" class="tables">
			<display:column title="Date of Last Update">
				<s:date name="#attr.sections.versionDate" format="MM/dd/yyyy"/>
			</display:column>
			<display:column title="Section">
				<s:property value="#attr.sections.number"/>
			</display:column>
			<display:column title="Topic">
				<s:url id="manualUrl" action="print-manual">
					<s:param name="rule.id" value="rule.id"/>
					<s:param name="section.id" value="#attr.sections.id"/>
				</s:url>
				<a href="<s:property value='manualUrl' escape='false'/>">
					<s:property value="#attr.sections.name"/>
				</a>
			</display:column>
		</display:table>
	</s:if>
</fieldset>