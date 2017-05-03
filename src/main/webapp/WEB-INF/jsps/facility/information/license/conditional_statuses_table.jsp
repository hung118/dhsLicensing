<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<security:authorize access="hasAnyRole('ROLE_SUPER_ADMIN','ROLE_OFFICE_SPECIALIST')">
	<s:if test="lstCtrl.showControls">
		<div class="topControls">
			<div class="mainControls">
				<s:url id="newCondStatUrl" action="edit-conditional-status">
					<s:param name="facilityId" value="facilityId"/>
				</s:url>
				<s:a href="%{newCondStatUrl}" cssClass="ccl-button ajaxify {target: '#conditionalStatusesSection'}">
					New Conditional License
				</s:a>
			</div>
		</div>
	</s:if>
</security:authorize>
<s:if test="!lstCtrl.results.isEmpty">
	<display:table name="lstCtrl.results" id="conditionalStatuses" class="tables" decorator="gov.utah.dts.det.ccl.view.admin.InactiveRecordDecorator">
		<display:column title="Start Date">
			<security:authorize access="hasAnyRole('ROLE_SUPER_ADMIN','ROLE_OFFICE_SPECIALIST')">
				<s:url id="condStatEditUrl" action="edit-conditional-status" includeParams="false">
					<s:param name="facilityId" value="facilityId"/>
					<s:param name="conditionalId" value="#attr.conditionalStatuses.id"/>
				</s:url>
				<s:a href="%{condStatEditUrl}" cssClass="ajaxify {target: '#conditionalStatusesSection'}">
					<s:date name="#attr.conditionalStatuses.startDate" format="MM/dd/yyyy"/>
				</s:a>
			</security:authorize>
			<security:authorize access="!hasAnyRole('ROLE_SUPER_ADMIN','ROLE_OFFICE_SPECIALIST')">
				<s:date name="#attr.conditionalStatuses.startDate" format="MM/dd/yyyy"/>
			</security:authorize>
		</display:column>
		<display:column title="Expiration Date" headerClass="shrinkCol">
			<s:date name="#attr.conditionalStatuses.expirationDate" format="MM/dd/yyyy"/>
		</display:column>
		<security:authorize access="hasAnyRole('ROLE_SUPER_ADMIN','ROLE_OFFICE_SPECIALIST')">
			<s:if test="lstCtrl.showControls">
				<display:column class="shrinkCol">
					<s:url id="condStatDeleteUrl" action="delete-conditional-status" includeParams="false">
						<s:param name="facilityId" value="facilityId"/>
						<s:param name="conditionalId" value="#attr.conditionalStatuses.id"/>
					</s:url>
					<s:a cssClass="ajaxify {target: '#conditionalStatusesSection'} ccl-action-delete" href="%{condStatDeleteUrl}">
						delete
					</s:a>
				</display:column>
			</s:if>
		</security:authorize>
	</display:table>
</s:if>