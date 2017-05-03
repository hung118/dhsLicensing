<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="dts" uri="/WEB-INF/tags/dts-tags.tld"%>
<%@ taglib prefix="ccl" uri="/WEB-INF/tags/ccl-tags.tld"%>
<fieldset>
	<legend>Letters List</legend>
	<s:actionerror/>
	<div class="topControls">
		<div class="mainControls">
			<s:url id="newLetterUrl" action="new-letter">
				<s:param name="screeningId" value="screeningId"/>
			</s:url>
			<s:a href="%{newLetterUrl}" cssClass="ccl-button ajaxify {target: '#letterSection'}">
				New Letter
			</s:a>
		</div>
		<dts:listcontrols id="lettersTopControls" name="lstCtrl" action="list-letters" namespace="/trackingrecordscreening/letters" useAjax="true" ajaxTarget="#letterSection">
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
				<s:if test="#attr.letters.letterType.name() != 'NAA'" >
					<s:url id="printLetterUrl" action="print-letter" includeParams="false">
						<s:param name="screeningId" value="screeningId"/>
						<s:param name="letterId" value="#attr.letters.id"/>
					</s:url>
					<s:a href="%{printLetterUrl}">
						print
					</s:a>
				</s:if>
				<s:else>
					&nbsp;
				</s:else>
			</display:column>
			<display:column>
				<s:url id="deleteLetterUrl" action="delete-letter" includeParams="false">
					<s:param name="screeningId" value="screeningId"/>
					<s:param name="letterId" value="#attr.letters.id"/>
				</s:url>
				<s:a href="%{deleteLetterUrl}" cssClass="ajaxify {target: '#letterSection'} ccl-action-delete ccl-delete-link">
					delete
				</s:a>
			</display:column>
		</display:table>
	</s:if>
	<div class="bottomControls">
		<dts:listcontrols id="lettersBottomControls" name="lstCtrl" action="list-letters" namespace="/trackingrecordscreening/letters" useAjax="true" ajaxTarget="#letterSection">
			<s:param name="screeningId" value="screeningId"/>
		</dts:listcontrols>
	</div>
</fieldset>
<s:if test="letterId != null">
	<s:form id="lettersPrintForm" action="print-letter">
		<s:hidden name="screeningId"/>
		<s:hidden name="letterId"/>
	</s:form>
	<script type="text/javascript">
		$(document).ready(function() {
			$('#lettersPrintForm').submit();
		});
	</script>
</s:if>
