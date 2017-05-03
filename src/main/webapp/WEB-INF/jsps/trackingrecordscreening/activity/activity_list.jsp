<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="dts" uri="/WEB-INF/tags/dts-tags.tld"%>
<%@ taglib prefix="ccl" uri="/WEB-INF/tags/ccl-tags.tld"%>
<fieldset>
	<legend>Activity List</legend>
	<div class="topControls">
		<div class="mainControls">
			<security:authorize access="hasAnyRole('ROLE_SUPER_ADMIN','ROLE_BACKGROUND_SCREENING_MANAGER','ROLE_BACKGROUND_SCREENING')">
				<s:url id="newOscarUrl" action="new-activity">
					<s:param name="screeningId" value="screeningId"/>
				</s:url>
				<s:a href="%{newOscarUrl}" cssClass="ccl-button ajaxify {target: '#activitySection'}">
					New Activity
				</s:a>
			</security:authorize>
		</div>
		<dts:listcontrols id="activityTopControls" name="lstCtrl" action="activity-list" namespace="/trackingrecordscreening/activity" useAjax="true" ajaxTarget="#activitySection">
			<s:param name="screeningId" value="screeningId"/>
		</dts:listcontrols>
	</div>
	<s:if test="!lstCtrl.results.isEmpty">
		<display:table name="lstCtrl.results" id="activity" class="tables">
			<display:column title="Date" headerClass="shrinkCol">
				<s:url id="editOscarUrl" action="edit-activity" includeParams="false">
					<s:param name="screeningId" value="screeningId"/>
					<s:param name="activity.id" value="#attr.activity.id"/>
				</s:url>
				<s:a cssClass="ajaxify {target: '#activitySection'}" href="%{editOscarUrl}">
					<s:date name="#attr.activity.activityDate" format="MM/dd/yyyy"/>
				</s:a>
			</display:column>
			<display:column title="Description">
				<s:property value="%{#attr.activity.shortDescription}" />
			</display:column>
			<security:authorize access="hasAnyRole('ROLE_SUPER_ADMIN','ROLE_BACKGROUND_SCREENING_MANAGER','ROLE_BACKGROUND_SCREENING')">
				<display:column headerClass="shrinkCol">
					<s:url id="deleteOscarUrl" action="delete-activity" includeParams="false">
						<s:param name="screeningId" value="screeningId"/>
						<s:param name="activity.id" value="#attr.activity.id"/>
					</s:url>
					<s:a href="%{deleteOscarUrl}" cssClass="ajaxify {target: '#activitySection'} ccl-action-delete ccl-delete-link">
						delete
					</s:a>
				</display:column>
			</security:authorize>
		</display:table>
	</s:if>
	<div class="bottomControls">
		<dts:listcontrols id="activityBottomControls" name="lstCtrl" action="activity-list" namespace="/trackingrecordscreening/activity" useAjax="true" ajaxTarget="#activitySection">
			<s:param name="screeningId" value="screeningId"/>
		</dts:listcontrols>
	</div>
</fieldset>
