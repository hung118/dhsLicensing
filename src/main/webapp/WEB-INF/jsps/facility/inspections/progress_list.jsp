<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="dts" uri="/WEB-INF/tags/dts-tags.tld"%>
<fieldset>
	<legend>Inspections in Progress</legend>
	<span id="inspection-errors"></span>
	<div class="topControls">
		<div class="mainControls">
			<security:authorize access="hasClassPermission('create', 'Inspection')">
				<s:url id="newInsUrl" action="new-inspection">
					<s:param name="facilityId" value="facilityId"/>
				</s:url>
				<s:a href="%{newInsUrl}" cssClass="ccl-button ajaxify {target: '#inspectionsBase'}">
					New Inspection
				</s:a>
			</security:authorize>
		</div>
		<dts:listcontrols id="inspectionProgressTopControls" name="lstCtrl" action="progress-list" namespace="/facility/inspections" useAjax="true" ajaxTarget="#inspectionsProgressSection">
			<s:param name="facilityId" value="facilityId"/>
		</dts:listcontrols>
	</div>
	<s:if test="!lstCtrl.results.isEmpty">
		<display:table name="lstCtrl.results" id="inspections" class="tables">
			<display:column title="Date" headerClass="shrinkCol">
				<s:url id="viewInspectionUrl" action="edit-inspection-record" includeParams="false">
					<s:param name="facilityId" value="facilityId"/>
					<s:param name="inspectionId" value="#attr.inspections.id"/>
				</s:url>
				<s:a cssClass="ajaxify {target: '#inspectionsBase'}" href="%{viewInspectionUrl}">
					<s:date name="#attr.inspections.inspectionDate" format="MM/dd/yyyy"/>
				</s:a>
			</display:column>
			<display:column title="Type">
				<strong><s:property value="#attr.inspections.primaryInspectionType"/></strong><s:if test="@org.apache.commons.lang.StringUtils@isNotBlank(#attr.inspections.otherInspectionTypes)">,</s:if>
				<s:property value="#attr.inspections.otherInspectionTypes"/>
			</display:column>
			<display:column title="License">
				<s:property value="#attr.inspections.license.licenseListDescriptor"/>
			</display:column>
			<display:column>
				<s:set var="inspection" value="%{#attr.inspections}" scope="request"/>
				<security:authorize access="hasPermission('delete', 'inspection')">
					<s:url id="deleteInspectionUrl" action="delete-inspection" includeParams="false">
						<s:param name="facilityId" value="facilityId"/>
						<s:param name="inspectionId" value="#attr.inspections.id"/>
					</s:url>
					<s:a href="%{deleteInspectionUrl}" cssClass="ccl-action-delete ccl-delete-link">
						delete
					</s:a>
				</security:authorize>
			</display:column>
		</display:table>
		<div class="bottomControls">
<%-- 			<dts:listcontrols id="inspectionProgressBottomControls" name="lstCtrl" action="progress-list" namespace="/facility/inspections" useAjax="true" ajaxTarget="#inspectionsProgressSection"> --%>
<%-- 				<s:param name="facilityId" value="facilityId"/> --%>
<%-- 			</dts:listcontrols> --%>
		</div>
	</s:if>
</fieldset>
<script type="text/javascript">
	$("#inspections").ccl("tableDelete", {errorContainer: "#inspection-errors"});
</script>