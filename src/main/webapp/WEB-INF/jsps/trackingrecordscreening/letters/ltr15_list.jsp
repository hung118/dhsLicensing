<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="dts" uri="/WEB-INF/tags/dts-tags.tld"%>
<%@ taglib prefix="ccl" uri="/WEB-INF/tags/ccl-tags.tld"%>
<fieldset>
	<legend>15-Day Letters List</legend>
	<div class="topControls">
		<div class="mainControls">
			<s:url id="newLetterUrl" action="edit-ltr15">
				<s:param name="screeningId" value="screeningId"/>
			</s:url>
			<s:a href="%{newLetterUrl}" cssClass="ccl-button ajaxify {target: '#ltr15Section'}">
				New Letter
			</s:a>
		</div>
		<dts:listcontrols id="lettersTopControls" name="lstCtrl" action="list-ltr15" namespace="/trackingrecordscreening/letters" useAjax="true" ajaxTarget="#ltr15Section">
			<s:param name="screeningId" value="screeningId"/>
		</dts:listcontrols>
	</div>
	<s:if test="!lstCtrl.results.isEmpty">
		<display:table name="lstCtrl.results" id="letters" class="tables">
			<display:column title="Issued Date" headerClass="shrinkCol">
				<s:url id="editLtr15Url" action="edit-ltr15" includeParams="false">
					<s:param name="screeningId" value="screeningId"/>
					<s:param name="ltr15Id" value="#attr.letters.id"/>
				</s:url>
				<s:a cssClass="ajaxify {target: '#ltr15Section'}" href="%{editLtr15Url}">
					<s:date name="#attr.letters.issuedDate" format="MM/dd/yyyy"/>
				</s:a>
			</display:column>
			<display:column title="Due Date" headerClass="shrinkCol">
				<s:date name="#attr.letters.dueDate" format="MM/dd/yyyy"/>
			</display:column>
			<display:column title="Reason">
				<s:property value="#attr.letters.reason.value" />
			</display:column>
			<display:column title="Resolved" headerClass="shrinkCol">
				<s:property value="@gov.utah.dts.det.ccl.view.YesNoChoice@valueOfBoolean(#attr.letters.resolved).displayName"/>
			</display:column>
			<display:column title="Creation Date" headerClass="shrinkCol">
				<s:date name="#attr.letters.creationDate" format="MM/dd/yyyy"/>
			</display:column>
			<display:column title="Created By" >
				<s:property value="%{#attr.letters.createdBy.firstAndLastName}" />
			</display:column>
			<display:column>
				<s:url id="deleteLtr15Url" action="delete-ltr15" includeParams="false">
					<s:param name="screeningId" value="screeningId"/>
					<s:param name="ltr15Id" value="#attr.letters.id"/>
				</s:url>
				<s:a href="%{deleteLtr15Url}" cssClass="ajaxify {target: '#ltr15Section'} ccl-action-delete ccl-delete-link">
					delete
				</s:a>
			</display:column>
		</display:table>
	</s:if>
	<div class="bottomControls">
		<dts:listcontrols id="lettersBottomControls" name="lstCtrl" action="list-ltr15" namespace="/trackingrecordscreening/letters" useAjax="true" ajaxTarget="#ltr15Section">
			<s:param name="screeningId" value="screeningId"/>
		</dts:listcontrols>
	</div>
</fieldset>
