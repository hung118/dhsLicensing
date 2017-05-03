<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="display" uri="http://displaytag.sf.net"%>
<fieldset>
	<legend><a name="alerts" class="quickLink">Action Log Alerts</a></legend>
	<display:table name="alerts" id="alerts" class="tables">
		<display:column title="Alert">
			<s:property value="#attr.alerts.alert" escape="false"/>
		</display:column>
		<display:column title="Date">
			<s:date name="#attr.alerts.alertDate" format="MM/dd/yyyy"/>
		</display:column>
		<display:column title="Due Date" headerClass="shrinkCol">
			<s:property value="#attr.alerts.actionDueDate"/>
		</display:column>
		<display:column title="Sent By" class="shrinkCol">
			<s:property value="#attr.alerts.sentByFirstName"/> <s:property value="#attr.alerts.sentByLastName"/>
		</display:column>
		<display:column class="shrinkCol">
			<a id="<s:property value="#attr.alerts.id"/>-delegate" href="#" class="al-delegate">delegate</a> |
			<s:url id="alertDeleteUrl" action="delete" includeParams="false">
				<s:param name="alertId" value="#attr.alerts.id"/>
				<s:param name="personId" value="user.person.id"/>
			</s:url>
			<s:a cssClass="ajaxify {target: '#alertsSection'} ccl-action-delete" href="%{alertDeleteUrl}">
				delete
			</s:a>
		</display:column>
	</display:table>
</fieldset>
<script type="text/javascript">
	recipients = <s:property value="recipientsJson" escape="false"/>;
</script>