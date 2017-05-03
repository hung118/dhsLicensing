<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="display" uri="http://displaytag.sf.net"%>
<fieldset>
	<legend>All Accounts</legend>
	<s:form action="edit-account" method="get" cssClass="ajaxify {target: '#accountsBase'}">
		<s:submit value="Add Account"/>
	</s:form>
	<s:iterator value="roleUserMap">
		<s:if test="!value.isEmpty">
			<display:table id="value" name="value" class="tables" decorator="gov.utah.dts.det.ccl.view.admin.InactiveRecordDecorator">
				<display:column title="${key}" style="width: 40%;">
					<s:property value="#attr.value.person.firstAndLastName"/>
				</display:column>
				<display:column style="width: 40%;">
					<s:property value="#attr.value.username"/>
				</display:column>
				<display:column style="width: 20%;">
					<s:url id="accountEditUrl" action="edit-account" includeParams="false">
						<s:param name="user.id" value="#attr.value.id"/>
					</s:url>
					<s:a cssClass="ajaxify {target: '#accountsBase'}" href="%{accountEditUrl}">
						edit
					</s:a>
					|
					<s:if test="#attr.value.active">
						<s:url id="accountDeactivateUrl" action="deactivate-account" includeParams="false">
							<s:param name="user.id" value="#attr.value.id"/>
						</s:url>
						<s:a cssClass="ajaxify {target: '#accountsBase'}" href="%{accountDeactivateUrl}">
							deactivate
						</s:a>
					</s:if>
					<s:else>
						<s:url id="accountActivateUrl" action="activate-account" includeParams="false">
							<s:param name="user.id" value="#attr.value.id"/>
						</s:url>
						<s:a cssClass="ajaxify {target: '#accountsBase'}" href="%{accountActivateUrl}">
							activate
						</s:a>
					</s:else>
				</display:column>
			</display:table>
		</s:if>
	</s:iterator>
</fieldset>