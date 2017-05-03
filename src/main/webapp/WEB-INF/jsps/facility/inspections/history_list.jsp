<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="dts" uri="/WEB-INF/tags/dts-tags.tld"%>
<%@ taglib prefix="ccl" uri="/WEB-INF/tags/ccl-tags.tld"%>
<fieldset>
	<legend>Inspection History</legend>
	<div class="topControls">
		<dts:listcontrols id="inspectionHistoryTopControls" name="lstCtrl" action="history-list" namespace="/facility/inspections" useAjax="true" ajaxTarget="#inspectionsHistorySection" paramExcludes="%{'lstCtrl.range'}">
			<ccl:listrange id="inspectionHistoryTopControls" name="lstCtrl"/>
			<s:param name="facilityId" value="facilityId"/>
		</dts:listcontrols>
	</div>
	<s:if test="!lstCtrl.results.isEmpty">
		<display:table name="lstCtrl.results" id="inspections" class="tables">
			<display:column title="Date and Type">
				<s:url id="viewInspectionUrl" action="edit-inspection-record" includeParams="false">
					<s:param name="facilityId" value="facilityId"/>
					<s:param name="inspectionId" value="#attr.inspections.id"/>
				</s:url>
				<s:a cssClass="ajaxify {target: '#inspectionsBase'}" href="%{viewInspectionUrl}">
					<s:date name="#attr.inspections.inspectionDate" format="MM/dd/yyyy"/>
				</s:a><br/>
				<strong><s:property value="#attr.inspections.primaryInspectionType"/></strong><s:if test="@org.apache.commons.lang.StringUtils@isNotBlank(#attr.inspections.otherInspectionTypes)">,</s:if>
				<s:property value="#attr.inspections.otherInspectionTypes"/>
			</display:column>
			<display:column title="Licensing Specialist">
				<s:if test="#attr.inspections.licensingSpecialist == null">Not Entered</s:if><s:else><s:property value="#attr.inspections.licensingSpecialist.firstAndLastName"/></s:else>
			</display:column>
			<display:column title="Findings" class="noWrap shrinkCol">
				<s:if test="#attr.inspections.citedFindings">
					<a href="#">Cited</a>
				</s:if>
				<s:if test="#attr.inspections.taFindings">
					<a href="#">TA</a>
				</s:if>
				<s:if test="#attr.inspections.taFindings && #attr.inspections.citedFindings">
					<a href="#">All</a>
				</s:if>
			</display:column>
			<display:column title="Follow-Up Needed" class="shrinkCol">
				<s:property value="@gov.utah.dts.det.ccl.view.YesNoChoice@valueOfBoolean(#attr.inspections.followUpNeeded).displayName"/>
			</display:column>
		</display:table>
		<div class="bottomControls">
			<dts:listcontrols id="inspectionHistoryBottomControls" name="lstCtrl" action="history-list" namespace="/facility/inspections" useAjax="true" ajaxTarget="#inspectionsHistorySection">
				<s:param name="facilityId" value="facilityId"/>
			</dts:listcontrols>
		</div>
	</s:if>
</fieldset>