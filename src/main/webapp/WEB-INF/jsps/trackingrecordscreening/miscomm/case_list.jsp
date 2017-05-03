<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="dts" uri="/WEB-INF/tags/dts-tags.tld"%>
<%@ taglib prefix="ccl" uri="/WEB-INF/tags/ccl-tags.tld"%>
<fieldset>
	<legend>Case List</legend>
	<div class="topControls">
		<div class="mainControls">
			<s:url id="newCaseUrl" action="edit-case">
				<s:param name="screeningId" value="screeningId"/>
			</s:url>
			<s:a href="%{newCaseUrl}" cssClass="ccl-button ajaxify {target: '#caseSection'}">
				New Case
			</s:a>
		</div>
	</div>
	<s:if test="!cases.isEmpty">
		<display:table name="cases" id="case" class="tables">
			<display:column title="Type">
				<s:url id="editCaseUrl" action="edit-case" includeParams="false">
					<s:param name="screeningId" value="screeningId"/>
					<s:param name="caseId" value="#attr.case.id"/>
				</s:url>
				<s:a cssClass="ajaxify {target: '#caseSection'}" href="%{editCaseUrl}">
					<s:property value="%{#attr.case.caseType.value}" />
				</s:a>
			</display:column>
			<display:column title="Case Number">
				<s:property value="%{#attr.case.caseNumber}"/>
			</display:column>
			<display:column title="Date" headerClass="shrinkCol">
				<s:date name="#attr.case.caseDate" format="MM/dd/yyyy"/>
			</display:column>
			<display:column title="Details">
				<s:property value="%{#attr.case.shortDetail}"/>
			</display:column>
			<display:column>
				<s:url id="deleteCaseUrl" action="delete-case" includeParams="false">
					<s:param name="screeningId" value="screeningId"/>
					<s:param name="caseId" value="#attr.case.id"/>
				</s:url>
				<s:a href="%{deleteCaseUrl}" cssClass="ajaxify {target: '#caseSection'} ccl-action-delete ccl-delete-link">
					delete
				</s:a>
			</display:column>
		</display:table>
	</s:if>
</fieldset>
