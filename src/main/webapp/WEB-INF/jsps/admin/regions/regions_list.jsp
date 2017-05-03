<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="display" uri="http://displaytag.sf.net"%>
<fieldset>
	<legend>Regions</legend>
	<div class="topControls">
		<div class="mainControls">
			<s:form action="edit-region" method="get" cssClass="ajaxify {target: '#regionsBase'}">
				<s:submit value="New Region"/>
			</s:form>
		</div>
	</div>
	<s:if test="!regions.isEmpty">
		<display:table name="regions" id="regions" class="tables">
			<display:column title="Region">
				<s:property value="#attr.regions.name"/>
			</display:column>
			<display:column class="shrinkCol">
				<s:url id="regionEditUrl" action="edit-region" includeParams="false">
					<s:param name="region.id" value="#attr.regions.id"/>
				</s:url>
				<s:a cssClass="ajaxify {target: '#regionsBase'}" href="%{regionEditUrl}">
					edit
				</s:a>
				|
				<s:url id="regionDeleteUrl" action="delete-region" includeParams="false">
					<s:param name="region.id" value="#attr.regions.id"/>
				</s:url>
				<s:a cssClass="ajaxify {target: '#regionsBase'} ccl-action-delete" href="%{regionDeleteUrl}">
					delete
				</s:a>
			</display:column>
		</display:table>
	</s:if>
</fieldset>