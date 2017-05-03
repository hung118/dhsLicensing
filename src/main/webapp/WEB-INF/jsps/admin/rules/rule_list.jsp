<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="display" uri="http://displaytag.sf.net"%>
<fieldset>
	<legend>Rules</legend>
	<s:if test="!rules.isEmpty">
		<display:table name="rules" id="rules" class="tables" decorator="gov.utah.dts.det.ccl.view.admin.InactiveRecordDecorator">
<%--
	 		<display:column title="Facility Type">
				<s:url id="ruleEditUrl" action="edit-section" includeParams="false">
					<s:param name="rule.id" value="#attr.rules.id"/>
				</s:url>
				<s:a cssClass="ajaxify {target: '#rulesBase'}" href="%{ruleEditUrl}">
					<s:property value="#attr.rules.facilityType.value"/>
				</s:a>
			</display:column>
--%>
			<display:column title="Rule #" class="nowrap">
				<s:url id="ruleEditUrl" action="edit-section" includeParams="false">
					<s:param name="rule.id" value="#attr.rules.id"/>
				</s:url>
				<s:a cssClass="ajaxify {target: '#rulesBase'}" href="%{ruleEditUrl}">
					<s:property value="#attr.rules.number"/>
				</s:a>
			</display:column>
			<display:column title="Rule Name">
				<s:property value="#attr.rules.name"/>
				<s:if test="#attr.rules.referenceUrl != null">
					[<a href="<s:property value="#attr.rules.referenceUrl"/>" target="_blank">Link</a>]
				</s:if>
			</display:column>
			<display:column class="nowrap">
				<s:url id="ruleEditUrl" action="edit-rule" includeParams="false">
					<s:param name="rule.id" value="#attr.rules.id"/>
				</s:url>
				<s:a cssClass="ajaxify {target: '#rulesBase'}" href="%{ruleEditUrl}">
					Edit
				</s:a>
				|
				<s:if test="#attr.rules.active">
					<s:url id="ruleDeactivateUrl" action="deactivate-rule" includeParams="false">
						<s:param name="rule.id" value="#attr.rules.id"/>
					</s:url>
					<s:a cssClass="ajaxify {target: '#rulesBase'} ccl-action-save" href="%{ruleDeactivateUrl}">
						Deactivate
					</s:a>
				</s:if>
				<s:else>
					<s:url id="ruleActivateUrl" action="activate-rule" includeParams="false">
						<s:param name="rule.id" value="#attr.rules.id"/>
					</s:url>
					<s:a cssClass="ajaxify {target: '#rulesBase'} ccl-action-save" href="%{ruleActivateUrl}">
						Activate
					</s:a>
				</s:else>
				|
				<s:url id="ruleDeleteUrl" action="delete-rule" includeParams="false">
					<s:param name="rule.id" value="#attr.rules.id"/>
				</s:url>
				<s:a cssClass="ajaxify {target: '#rulesBase'} ccl-action-delete" href="%{ruleDeleteUrl}">
					Delete
				</s:a>
			</display:column>
		</display:table>
	</s:if>
	<s:else>
		there are no rules :(
	</s:else>
</fieldset>