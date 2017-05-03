<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="dts" uri="/WEB-INF/tags/dts-tags.tld"%>
<%@ taglib prefix="ccl" uri="/WEB-INF/tags/ccl-tags.tld"%>
<s:set var="showControls">${param.showControls == null ? false : param.showControls}</s:set>
<s:set var="canEdit" value="%{false}"/>
<security:authorize access="hasAnyRole('ROLE_SUPER_ADMIN','ROLE_OFFICE_SPECIALIST','ROLE_LICENSOR_SPECIALIST','ROLE_BACKGROUND_SCREENING_MANAGER','ROLE_BACKGROUND_SCREENING')">
	<s:set var="canEdit" value="%{true}"/>
</security:authorize>
<div class="topControls">
	<div class="mainControls">
		<s:if test="#attr.showControls == 'true' && #attr.canEdit">
			<s:form action="edit-action" method="get" cssClass="ajaxify {target: '#actionLogSection'}">
				<s:hidden name="facilityId"/>
				<s:submit value="New Action Log Entry"/>
			</s:form>
		</s:if>
	</div>
	<dts:listcontrols id="actionsTopControls" name="lstCtrl" action="actions-list" namespace="/facility/actionlognotes" useAjax="true" ajaxTarget="#actionsListSection" paramExcludes="%{'lstCtrl.range'}">
		<ccl:listrange id="actionsTopControls" name="lstCtrl"/>
		<s:param name="showControls" value="showControls"/>
		<s:param name="facilityId" value="facilityId"/>
	</dts:listcontrols>
</div>
<s:if test="!lstCtrl.results.isEmpty">
	<ol class="ccl-list">
		<s:iterator value="lstCtrl.results" status="row">
			<li class="ccl-list-item <s:if test="#row.odd">odd</s:if><s:else>even</s:else>">
				<div class="left-column">
					<div>
						<span class="label">Action Date:</span>
						<s:if test="#attr.editable">
							<s:url id="editActionUrl" action="edit-action" includeParams="false">
								<s:param name="facilityId" value="facilityId"/>
								<s:param name="action.id" value="id"/>
							</s:url>
							<s:a href="%{editActionUrl}" cssClass="ajaxify {target: '#actionLogSection'}">
								<s:date name="actionDate" format="MM/dd/yyyy hh:mm a"/>
							</s:a>
						</s:if>
						<s:else>
							<s:date name="actionDate" format="MM/dd/yyyy hh:mm a"/>
						</s:else>
					</div>
					<div>
						<span class="label">Action:</span>
						<s:property value="actionType.value"/>
					</div>
				</div>
				<div class="right-column">
					<div>
						<span class="label">Staff Member:</span>
						<s:property value="takenBy.firstAndLastName"/>
					</div>
					<div>
						<span class="label">Entry Time:</span>
						<s:date name="creationDate" format="MM/dd/yyyy hh:mm a"/>
					</div>
				</div>
				<div class="clear">
					<span class="label">Comments:</span>
					<span class="description"><s:property value="note"/></span>
				</div>
				<security:authorize access="hasRole('ROLE_SUPER_ADMIN')">
					<div class="ccl-list-item-ctrls clearfix">
						<span class="ccl-right-float" style="margin-left: .5em;">
							<s:url id="deleteActionUrl" action="delete-action" includeParams="false">
								<s:param name="facilityId" value="facilityId"/>
								<s:param name="action.id" value="id"/>
							</s:url>
							<s:a href="%{deleteActionUrl}" cssClass="ajaxify {target: '#actionLogSection'} ccl-action-delete">
								delete
							</s:a>
						</span>
					</div>
				</security:authorize>
			</li>
		</s:iterator>
	</ol>
	<div class="bottomControls">
		<dts:listcontrols id="actionsBottomControls" name="lstCtrl" action="actions-list" namespace="/facility/actionlognotes" useAjax="true" ajaxTarget="#actionsListSection">
			<s:param name="showControls" value="showControls"/>
			<s:param name="facilityId" value="facilityId"/>
		</dts:listcontrols>
	</div>
</s:if>