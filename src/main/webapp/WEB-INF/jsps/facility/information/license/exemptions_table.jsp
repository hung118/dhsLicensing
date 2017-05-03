<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="dts" uri="/WEB-INF/tags/dts-tags.tld"%>
<%@ taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<s:if test="lstCtrl.showControls">
	<div class="topControls">
		<security:authorize access="hasPermission('edit-licenses','facility')">
			<div class="mainControls">
				<s:url id="newExemptUrl" action="edit-exemption">
					<s:param name="facilityId" value="facilityId"/>
				</s:url>
				<s:a href="%{newExemptUrl}" cssClass="ccl-button ajaxify {target: '#exemptionsSection'}">
					New Exemption
				</s:a>
			</div>
		</security:authorize>
		<dts:listcontrols id="exemptionsTopControls" name="lstCtrl" action="exemptions-list" namespace="/facility/information/license" useAjax="true" ajaxTarget="#exemptionsSection">
			<s:param name="lstCtrl.showControls" value="lstCtrl.showControls"/>
		</dts:listcontrols>
	</div>
</s:if>
<s:if test="!lstCtrl.results.isEmpty">
	<display:table name="lstCtrl.results" id="exemptions" class="tables" decorator="gov.utah.dts.det.ccl.view.admin.InactiveRecordDecorator">
		<display:column title="Exemption">
			<security:authorize access="hasPermission('edit-licenses','facility')">
				<s:url id="exemptionEditUrl" action="edit-exemption">
					<s:param name="facilityId" value="facilityId"/>
					<s:param name="exemption.id" value="#attr.exemptions.id"/>
				</s:url>
				<s:a href="%{exemptionEditUrl}" cssClass="ajaxify {target: '#exemptionsSection'}">
					<s:property value="#attr.exemptions.exemption.value"/>
				</s:a>
			</security:authorize>
			<security:authorize access="!hasPermission('edit-licenses','facility')">
				<s:property value="#attr.exemptions.exemption.value"/>
			</security:authorize>
		</display:column>
		<display:column title="Start Date" headerClass="shrinkCol">
			<s:date name="#attr.exemptions.startDate" format="MM/dd/yyyy"/>
		</display:column>
		<display:column title="Expiration Date" headerClass="shrinkCol">
			<s:date name="#attr.exemptions.expirationDate" format="MM/dd/yyyy"/>
		</display:column>
		<security:authorize access="hasPermission('edit-licenses','facility')">
			<s:if test="lstCtrl.showControls">
				<display:column class="shrinkCol">
					<s:url id="exemptionDeleteUrl" action="delete-exemption">
						<s:param name="facilityId" value="facilityId"/>
						<s:param name="exemption.id" value="#attr.exemptions.id"/>
					</s:url>
					<s:a cssClass="ajaxify {target: '#exemptionsSection'} ccl-action-delete" href="%{exemptionDeleteUrl}">
						delete
					</s:a>
				</display:column>
			</s:if>
		</security:authorize>
	</display:table>
</s:if>