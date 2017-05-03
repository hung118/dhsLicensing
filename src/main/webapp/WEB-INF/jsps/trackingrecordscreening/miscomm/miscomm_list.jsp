<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="dts" uri="/WEB-INF/tags/dts-tags.tld"%>
<%@ taglib prefix="ccl" uri="/WEB-INF/tags/ccl-tags.tld"%>
<fieldset>
	<legend>MIS Committee List</legend>
	<div class="topControls">
		<div class="mainControls">
			<security:authorize access="hasAnyRole('ROLE_SUPER_ADMIN','ROLE_BACKGROUND_SCREENING_MANAGER','ROLE_BACKGROUND_SCREENING')">
				<s:url id="newMisCommUrl" action="new-miscomm">
					<s:param name="screeningId" value="screeningId"/>
				</s:url>
				<s:a href="%{newMisCommUrl}" cssClass="ccl-button ajaxify {target: '#miscommSection'}">
					New MIS Committee
				</s:a>
			</security:authorize>
		</div>
		<dts:listcontrols id="misCommTopControls" name="lstCtrl" action="miscomm-list" namespace="/trackingrecordscreening/miscomm" useAjax="true" ajaxTarget="#miscommSection">
			<s:param name="screeningId" value="screeningId"/>
		</dts:listcontrols>
	</div>
	<s:if test="!lstCtrl.results.isEmpty">
		<display:table name="lstCtrl.results" id="misComm" class="tables">
			<display:column title="Date" headerClass="shrinkCol">
				<s:url id="editMisCommUrl" action="edit-miscomm" includeParams="false">
					<s:param name="screeningId" value="screeningId"/>
					<s:param name="misComm.id" value="#attr.misComm.id"/>
				</s:url>
				<s:a cssClass="ajaxify {target: '#miscommSection'}" href="%{editMisCommUrl}">
					<s:date name="#attr.misComm.misCommDate" format="MM/dd/yyyy"/>
				</s:a>
			</display:column>
			<display:column title="Type">
				<s:property value="%{#attr.misComm.misCommType.value}" />
			</display:column>
			<display:column title="Notes">
				<s:property value="%{#attr.misComm.notes}" />
			</display:column>
			<security:authorize access="hasAnyRole('ROLE_SUPER_ADMIN','ROLE_BACKGROUND_SCREENING_MANAGER','ROLE_BACKGROUND_SCREENING')">
				<display:column headerClass="shrinkCol">
					<s:url id="deleteMisCommUrl" action="delete-miscomm" includeParams="false">
						<s:param name="screeningId" value="screeningId"/>
						<s:param name="misComm.id" value="#attr.misComm.id"/>
					</s:url>
					<s:a href="%{deleteMisCommUrl}" cssClass="ajaxify {target: '#miscommSection'} ccl-action-delete ccl-delete-link">
						delete
					</s:a>
				</display:column>
			</security:authorize>
		</display:table>
	</s:if>
	<div class="bottomControls">
		<dts:listcontrols id="misCommBottomControls" name="lstCtrl" action="miscomm-list" namespace="/trackingrecordscreening/misComm" useAjax="true" ajaxTarget="#miscommSection">
			<s:param name="screeningId" value="screeningId"/>
		</dts:listcontrols>
	</div>
</fieldset>
