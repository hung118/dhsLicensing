<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<security:authorize access="hasPermission('edit-licenses','facility')">
	<s:if test="lstCtrl.showControls">
		<div class="topControls">
			<div class="mainControls">
				<s:url id="newAccredUrl" action="edit-accreditation">
					<s:param name="facilityId" value="facilityId"/>
				</s:url>
				<s:a href="%{newAccredUrl}" cssClass="ccl-button ajaxify {target: '#accreditationsSection'}">
					New Accreditation
				</s:a>
			</div>
		</div>
	</s:if>
</security:authorize>
<s:if test="!lstCtrl.results.isEmpty">
	<display:table name="lstCtrl.results" id="accreditations" class="tables" decorator="gov.utah.dts.det.ccl.view.admin.InactiveRecordDecorator">
		<display:column title="Accreditation">
			<security:authorize access="hasPermission('edit-licenses','facility')">
				<s:url id="accreditationEditUrl" action="edit-accreditation" includeParams="false">
					<s:param name="facilityId" value="facilityId"/>
					<s:param name="accreditation.id" value="#attr.accreditations.id"/>
				</s:url>
				<s:a href="%{accreditationEditUrl}" cssClass="ajaxify {target: '#accreditationsSection'}">
					<s:property value="#attr.accreditations.agency.value"/>
				</s:a>
			</security:authorize>
			<security:authorize access="!hasPermission('edit-licenses','facility')">
				<s:property value="#attr.accreditations.agency.value"/>
			</security:authorize>
		</display:column>
		<display:column title="Start Date">
			<s:date name="#attr.accreditations.startDate" format="MM/dd/yyyy"/>
		</display:column>
		<display:column title="End Date">
			<s:date name="#attr.accreditations.endDate" format="MM/dd/yyyy"/>
		</display:column>
		<security:authorize access="hasPermission('edit-licenses','facility')">
			<s:if test="lstCtrl.showControls">
				<display:column>
					<s:url id="accreditationDeleteUrl" action="delete-accreditation" includeParams="false">
						<s:param name="facilityId" value="facilityId"/>
						<s:param name="accreditation.id" value="#attr.accreditations.id"/>
					</s:url>
					<s:a cssClass="ajaxify {target: '#accreditationsSection'} ccl-action-delete" href="%{accreditationDeleteUrl}">
						delete
					</s:a>
				</display:column>
			</s:if>
		</security:authorize>
	</display:table>
</s:if>