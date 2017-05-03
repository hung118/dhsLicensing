<%@ taglib prefix="s" uri="/struts-tags"%>
<fieldset>
	<legend>Section</legend>
	<ol class="fieldList">
		<li>	
			<div class="label">Section:</div>
			<div class="value"><s:property value="rule.number" escape="true"/>-<s:property value="section.sectionBase" escape="true"/>-<s:property value="section.number" escape="true"/></div>
		</li>
		<li>
			<div class="label">Section Name:</div>
			<div class="value"><s:property value="section.name" escape="true"/></div>
		</li>

		<s:if test="section.comment != null">
		<li>
			<div class="value"><s:property value="section.comment" escape="true"/></div>
		</li>
		</s:if>	

		<li>
			<div class="label">Version Date:</div>
			<div class="value"><s:date name="section.versionDate" format="MM/dd/yyyy"/></div>
		</li>
		<li>
			<div class="label">Version History:</div>
			<ol class="fieldGroup" id="renderHistory">
				<s:action name="view-render-history" executeResult="true">
					<s:param name="rule.id" value="rule.id"/>
					<s:param name="section.id" value="section.id"/>
				</s:action>
			</ol>
		</li>
<%-- 		<li class="submit">
			<s:form action="render-section" method="get" cssClass="ajaxify {target: '#renderHistory'}">
				<s:hidden name="rule.id"/>
				<s:hidden name="section.id"/>
				<s:submit value="Render Section of Interpretation Manual"/>
			</s:form>
		</li>
 --%>
	</ol>
</fieldset>