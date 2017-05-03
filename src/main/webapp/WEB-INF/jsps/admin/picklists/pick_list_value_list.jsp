<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="display" uri="http://displaytag.sf.net"%>
<script type="text/javascript">
	$("#defaultBlank").click(function() {
		$("input[name=pickList.defaultValue]:radio:checked").removeAttr("checked");
	});
</script>
<fieldset>
	<legend><s:property value="pickList.name"/></legend>
	<s:fielderror/>
	<div class="topControls">
		<div class="mainControls">
			<s:form action="edit-pick-list-value" method="get" cssClass="ajaxify {target: '#pickListValuesSection'}">
				<s:hidden name="pickList.id"/>
				<s:submit value="New Value"/>
			</s:form>
			<s:if test="!pickList.pickListValues.isEmpty">
				<button id="defaultBlank">Default to Blank</button>
				<s:form action="alphabetize-pick-list-values" method="get" cssClass="ajaxify {target: '#pickListValuesSection'} ccl-action-save">
					<s:hidden name="pickList.id"/>
					<s:submit value="Alphabetize and Save"/>
				</s:form>
			</s:if>
		</div>
	</div>
	<s:if test="!pickList.pickListValues.isEmpty">
		<s:form id="pickListValuesForm" action="save-pick-list-values" method="post" cssClass="ajaxify {target: '#pickListValuesSection'} ccl-action-save">
			<s:hidden name="pickList.id"/>
			<display:table name="pickList.pickListValues" id="pickListValues" class="tables" style="width: 100%;" decorator="gov.utah.dts.det.ccl.view.admin.InactiveRecordDecorator">
				<display:column title="Order">
					<s:set name="orderFieldId" value="#attr.pickListValues.id"/>
					<s:set name="sortOrder" value="#attr.pickListValues.sortOrder"/>
					<s:textfield id="order-%{orderFieldId}" name="order-%{orderFieldId}" cssClass="orderField" maxlength="6" value="%{sortOrder}"/>
				</display:column>
				<display:column title="Default">
					<s:if test="#attr.pickListValues.id == pickList.defaultValue">
						<input type="radio" checked="checked" name="pickList.defaultValue" value="<s:property value='#attr.pickListValues.id'/>"/>
					</s:if>
					<s:else>
						<input type="radio" name="pickList.defaultValue" value="<s:property value='#attr.pickListValues.id'/>"/>
					</s:else>
				</display:column>
				<display:column title="Value">
					<s:property value="#attr.pickListValues.value"/>
				</display:column>
				<display:column class="nowrap">
					<s:url id="pickListValueEditUrl" action="edit-pick-list-value" includeParams="false">
						<s:param name="pickList.id" value="pickList.id"/>
						<s:param name="pickListValue.id" value="#attr.pickListValues.id"/>
					</s:url>
					<s:a cssClass="ajaxify {target: '#pickListValuesSection'}" href="%{pickListValueEditUrl}">
						edit
					</s:a>
					|
					<s:if test="#attr.pickListValues.active">
						<s:url id="pickListValueDeactivateUrl" action="deactivate-pick-list-value" includeParams="false">
							<s:param name="pickList.id" value="pickList.id"/>
							<s:param name="pickListValue.id" value="#attr.pickListValues.id"/>
						</s:url>
						<s:a cssClass="ajaxify {target: '#pickListValuesSection'} ccl-action-save" href="%{pickListValueDeactivateUrl}">
							deactivate
						</s:a>
					</s:if>
					<s:else>
						<s:url id="pickListValueActivateUrl" action="activate-pick-list-value" includeParams="false">
							<s:param name="pickList.id" value="pickList.id"/>
							<s:param name="pickListValue.id" value="#attr.pickListValues.id"/>
						</s:url>
						<s:a cssClass="ajaxify {target: '#pickListValuesSection'} ccl-action-save" href="%{pickListValueActivateUrl}">
							activate
						</s:a>
					</s:else>
					|
					<s:url id="pickListValueDeleteUrl" action="delete-pick-list-value" includeParams="false">
						<s:param name="pickList.id" value="pickList.id"/>
						<s:param name="pickListValue.id" value="#attr.pickListValues.id"/>
					</s:url>
					<s:a cssClass="ajaxify {target: '#pickListValuesSection'} ccl-action-delete" href="%{pickListValueDeleteUrl}">
						delete
					</s:a>
				</display:column>
			</display:table>
			<div class="bottomControls">
				<div class="mainControls">
					<s:submit value="Save Order and Default"/>
				</div>
			</div>
		</s:form>
	</s:if>
</fieldset>