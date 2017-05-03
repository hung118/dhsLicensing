<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="dts" uri="/WEB-INF/tags/dts-tags.tld"%>
<%@ taglib prefix="ccl" uri="/WEB-INF/tags/ccl-tags.tld"%>
<fieldset>
	<legend>OSCAR List</legend>
	<div class="topControls">
		<div class="mainControls">
			<security:authorize access="hasAnyRole('ROLE_SUPER_ADMIN','ROLE_BACKGROUND_SCREENING_MANAGER','ROLE_BACKGROUND_SCREENING')">
				<s:url id="newOscarUrl" action="new-oscar">
					<s:param name="screeningId" value="screeningId"/>
				</s:url>
				<s:a href="%{newOscarUrl}" cssClass="ccl-button ajaxify {target: '#oscarSection'}">
					New OSCAR
				</s:a>
			</security:authorize>
		</div>
		<dts:listcontrols id="oscarTopControls" name="lstCtrl" action="oscar-list" namespace="/trackingrecordscreening/oscar" useAjax="true" ajaxTarget="#oscarSection">
			<s:param name="screeningId" value="screeningId"/>
		</dts:listcontrols>
	</div>
	<s:if test="!lstCtrl.results.isEmpty">
		<display:table name="lstCtrl.results" id="oscar" class="tables">
			<display:column title="Date" headerClass="shrinkCol">
				<s:url id="editOscarUrl" action="edit-oscar" includeParams="false">
					<s:param name="screeningId" value="screeningId"/>
					<s:param name="oscar.id" value="#attr.oscar.id"/>
				</s:url>
				<s:a cssClass="ajaxify {target: '#oscarSection'}" href="%{editOscarUrl}">
					<s:date name="#attr.oscar.oscarDate" format="MM/dd/yyyy"/>
				</s:a>
			</display:column>
			<display:column title="Type">
				<s:property value="%{#attr.oscar.oscarType.value}" />
			</display:column>
			<display:column title="State">
				<s:property value="%{#attr.oscar.state.displayName}" />
			</display:column>
			<display:column title="Notes">
				<s:property value="%{#attr.oscar.notes}" />
			</display:column>
			<security:authorize access="hasAnyRole('ROLE_SUPER_ADMIN','ROLE_BACKGROUND_SCREENING_MANAGER','ROLE_BACKGROUND_SCREENING')">
				<display:column headerClass="shrinkCol">
					<s:url id="deleteOscarUrl" action="delete-oscar" includeParams="false">
						<s:param name="screeningId" value="screeningId"/>
						<s:param name="oscar.id" value="#attr.oscar.id"/>
					</s:url>
					<s:a href="%{deleteOscarUrl}" cssClass="ajaxify {target: '#oscarSection'} ccl-action-delete ccl-delete-link">
						delete
					</s:a>
				</display:column>
			</security:authorize>
		</display:table>
	</s:if>
	<div class="bottomControls">
		<dts:listcontrols id="oscarBottomControls" name="lstCtrl" action="oscar-list" namespace="/trackingrecordscreening/oscar" useAjax="true" ajaxTarget="#oscarSection">
			<s:param name="screeningId" value="screeningId"/>
		</dts:listcontrols>
	</div>
</fieldset>
