<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@ taglib prefix="dts" uri="/WEB-INF/tags/dts-tags.tld"%>
<%@ taglib prefix="ccl" uri="/WEB-INF/tags/ccl-tags.tld"%>
<fieldset>
	<legend>Conviction Letters List</legend>
	<s:actionerror/>
	<div class="topControls">
		<div class="mainControls">
			<s:url id="newConvictLtrUrl" action="new-conviction-letter">
				<s:param name="screeningId" value="screeningId"/>
			</s:url>
			<s:a href="%{newConvictLtrUrl}" cssClass="ccl-button ajaxify {target: '#convictionLettersSection'}">
				New Letter
			</s:a>
		</div>
		<dts:listcontrols id="convictLtrsTopControls" name="lstCtrl" action="list-conviction-letters" namespace="/trackingrecordscreening/cbscomm" useAjax="true" ajaxTarget="#convictionLettersSection">
			<s:param name="screeningId" value="screeningId"/>
		</dts:listcontrols>
	</div>
	<s:if test="!lstCtrl.results.isEmpty">
		<display:table name="lstCtrl.results" id="letters" class="tables">
			<display:column title="Letter Type" >
				<s:property value="%{#attr.letters.letterType.label}" />
			</display:column>
			<display:column title="Letter Date" headerClass="shrinkCol">
				<s:date name="#attr.letters.letterDate" format="MM/dd/yyyy"/>
			</display:column>
			<display:column title="Created By" >
				<s:property value="%{#attr.letters.createdBy.firstAndLastName}" />
			</display:column>
			<display:column title="Creation Date" headerClass="shrinkCol">
				<s:date name="#attr.letters.creationDate" format="MM/dd/yyyy"/>
			</display:column>
			<display:column headerClass="shrinkCol">
				<s:url id="printConvictLtrUrl" action="print-conviction-letter" includeParams="false">
					<s:param name="screeningId" value="screeningId"/>
					<s:param name="letterId" value="#attr.letters.id"/>
				</s:url>
				<s:a href="%{printConvictLtrUrl}">
					Print
				</s:a>
			</display:column>
			<display:column>
				<s:url id="deleteConvictLtrUrl" action="delete-conviction-letter" includeParams="false">
					<s:param name="screeningId" value="screeningId"/>
					<s:param name="letterId" value="#attr.letters.id"/>
				</s:url>
				<s:a href="%{deleteConvictLtrUrl}" cssClass="ajaxify {target: '#convictionLettersSection'} ccl-action-delete ccl-delete-link">
					delete
				</s:a>
			</display:column>
		</display:table>
	</s:if>
	<div class="bottomControls">
		<dts:listcontrols id="convictLtrsBottomControls" name="lstCtrl" action="list-conviction-letters" namespace="/trackingrecordscreening/cbscomm" useAjax="true" ajaxTarget="#convictionLettersSection">
			<s:param name="screeningId" value="screeningId"/>
		</dts:listcontrols>
	</div>
</fieldset>
<s:if test="letterId != null">
	<s:form id="convictLtrsPrintForm" action="print-conviction-letter">
		<s:hidden name="screeningId"/>
		<s:hidden name="letterId"/>
	</s:form>
	<script type="text/javascript">
		$(document).ready(function() {
			$('#convictLtrsPrintForm').submit();
		});
	</script>
</s:if>
