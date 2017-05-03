<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@ taglib prefix="dts" uri="/WEB-INF/tags/dts-tags.tld"%>
<fieldset>
	<legend>Rule Cross References</legend>
	<div class="topControls">
		<div class="mainControls">
			<s:form action="edit-cross-reference" namespace="/admin/rulecrossreference" method="get" cssClass="ajaxify {target: '#ruleCrossReferenceBase'}">
				<s:submit value="New Rule Cross Reference"/>
			</s:form>
		</div>
		<dts:listcontrols id="crossReferencesTopControls" name="lstCtrl" action="cross-reference-list" namespace="/admin/rulecrossreference" enablePaging="true" maxPagesToShow="4" useAjax="true" ajaxTarget="#ruleCrossReferenceBase" paramExcludes="ruleNumFilter">
			<div class="filterContainer">
				<label for="ruleNumFilter">Filter List:</label>
				<s:textfield id="ruleNumFilter" name="ruleNumFilter"/>
			</div>
		</dts:listcontrols>
	</div>
	<s:if test="!lstCtrl.results.isEmpty">
		<display:table name="lstCtrl.results" id="refs" class="tables">
			<display:column title="New Rule">
				<s:url id="editUrl" action="edit-cross-reference" namespace="/admin/rulecrossreference" includeParams="false">
					<s:param name="ref.id" value="#attr.refs.id"/>
				</s:url>
				<s:a href="%{editUrl}" cssClass="ajaxify {target: '#ruleCrossReferenceBase'}">
					<s:property value="#attr.refs.newRule.ruleNumber"/>
				</s:a>
			</display:column>
			<display:column title="Old Rule">
				<s:property value="#attr.refs.oldRule.ruleNumber"/>
			</display:column>
			<display:column>
				<s:url id="deleteUrl" action="delete-cross-reference" namespace="/admin/rulecrossreference" includeParams="false">
					<s:param name="ref.id" value="#attr.refs.id"/>
				</s:url>
				<s:a href="%{deleteUrl}" cssClass="ajaxify ccl-action-delete {target: '#ruleCrossReferenceBase'}">
					delete
				</s:a>
			</display:column>
		</display:table>
		<div class="bottomControls">
			<dts:listcontrols id="crossReferencesBottomControls" name="lstCtrl" action="cross-reference-list" namespace="/admin/rulecrossreference" enablePaging="true" maxPagesToShow="4" useAjax="true" ajaxTarget="#ruleCrossReferenceBase"/>
		</div>
	</s:if>
	<script type="text/javascript">
		$("#ruleNumFilter").focus(function() {
			this.select();
		}).focus();
		$("#ruleNumFilter").change(function() {
			$("input[name='lstCtrl.page']").val("");
		});
	</script>
</fieldset>