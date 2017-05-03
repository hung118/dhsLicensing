<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="dts" uri="/WEB-INF/tags/dts-tags.tld"%>
<%@ taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<s:actionmessage/>
<s:if test="lstCtrl.showControls">
	<div class="topControls">
		<security:authorize access="hasPermission('edit-licenses','facility')">
			<s:if test="showNewLicenseButton">
				<div class="mainControls">
					<s:url id="newLicUrl" action="edit-license">
						<s:param name="facilityId" value="facilityId"/>
					</s:url>
					<s:a href="%{newLicUrl}" cssClass="ccl-button ajaxify {target: '#licensesSection'}">
						New License
					</s:a>
				</div>
			</s:if>
		</security:authorize>
		<dts:listcontrols id="licensesTopControls" name="lstCtrl" action="licenses-list" namespace="/facility/information/license" useAjax="true" ajaxTarget="#licensesListSection">
			<s:param name="lstCtrl.showControls" value="lstCtrl.showControls"/>
		</dts:listcontrols>
	</div>
</s:if>
<s:if test="!lstCtrl.results.isEmpty">
	<display:table name="lstCtrl.results" id="licenses" class="tables" decorator="gov.utah.dts.det.ccl.view.admin.InactiveRecordDecorator">
		<display:column title="Number" headerClass="shrinkCol">
			<security:authorize access="hasPermission('edit-licenses','facility')">
				<s:if test="hasLicenseEntityPermission('save', #attr.licenses.id)">
					<s:url id="licenseEditUrl" action="edit-license" includeParams="false">
						<s:param name="facilityId" value="facilityId"/>
						<s:param name="license.id" value="#attr.licenses.id"/>
					</s:url>
					<s:a href="%{licenseEditUrl}" cssClass="ajaxify {target: '#licensesSection'}">
						<s:property value="#attr.licenses.licenseNumber"/>
					</s:a>
				</s:if>
				<s:else>
					<s:url id="licenseViewUrl" action="view-license" includeParams="false">
						<s:param name="facilityId" value="facilityId"/>
						<s:param name="license.id" value="#attr.licenses.id"/>
					</s:url>
					<s:a href="%{licenseViewUrl}" cssClass="ajaxify {target: '#licensesSection'}">
						<s:property value="#attr.licenses.licenseNumber"/>
					</s:a>
				</s:else>
			</security:authorize>
			<security:authorize access="!hasPermission('edit-licenses','facility')">
				<s:property value="#attr.licenses.licenseNumber"/>
			</security:authorize>
		</display:column>
		<display:column title="Service Code">
			<s:property value="#attr.licenses.serviceCodeDesc"/>
		</display:column>
		<display:column title="Status">
			<s:property value="#attr.licenses.status.value"/>
		</display:column>
		<display:column title="License Type">
			<s:property value="#attr.licenses.subtype.value"/>
		</display:column>
		<display:column title="Start Date" headerClass="shrinkCol">
			<security:authorize access="hasPermission('edit-licenses','facility')">
				<s:url id="licenseEditUrl" action="edit-license" includeParams="false">
					<s:param name="facilityId" value="facilityId"/>
					<s:param name="license.id" value="#attr.licenses.id"/>
				</s:url>
				<s:a href="%{licenseEditUrl}" cssClass="ajaxify {target: '#licensesSection'}">
					<s:date name="#attr.licenses.startDate" format="MM/dd/yyyy"/>
				</s:a>
			</security:authorize>
			<security:authorize access="!hasPermission('edit-licenses','facility')">
				<s:date name="#attr.licenses.startDate" format="MM/dd/yyyy"/>
			</security:authorize>
		</display:column>
		<display:column title="Expire Date" headerClass="shrinkCol">
			<s:date name="#attr.licenses.expirationDate" format="MM/dd/yyyy"/>
		</display:column>
		<display:column title="Certificate" headerClass="shrinkCol">
			<s:if test="#attr.licenses.isFinalized()">
				<s:url id="printCertUrl" action="print-license-cert" includeParams="false">
					<s:param name="facilityId" value="facilityId"/>
					<s:param name="license.id" value="#attr.licenses.id"/>
				</s:url>
				<s:a href="%{printCertUrl}">
					print
				</s:a>
			</s:if>
			<s:else>
				&nbsp;
			</s:else>
		</display:column>
		<display:column title="Letter" headerClass="shrinkCol">
			<s:if test="#attr.licenses.isFinalized()">
				<s:url id="printLetterUrl" action="print-license-letter" includeParams="false">
					<s:param name="facilityId" value="facilityId"/>
					<s:param name="license.id" value="#attr.licenses.id"/>
				</s:url>
				<s:a href="%{printLetterUrl}">
					print
				</s:a>
			</s:if>
			<s:else>
				&nbsp;
			</s:else>
		</display:column>
		<s:if test="lstCtrl.showControls">
			<s:if test="hasLicenseEntityPermission('save',license.id)">
				<display:column class="shrinkCol">
					<s:url id="licenseDeleteUrl" action="delete-license" includeParams="false">
						<s:param name="facilityId" value="facilityId"/>
						<s:param name="license.id" value="#attr.licenses.id"/>
					</s:url>
					<s:a cssClass="ajaxify {target: '#licensesSection'} ccl-action-delete" href="%{licenseDeleteUrl}">
						delete
					</s:a>
				</display:column>
			</s:if>
		</s:if>
	</display:table>
</s:if>