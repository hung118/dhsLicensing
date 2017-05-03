<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@ taglib prefix="dts" uri="/WEB-INF/tags/dts-tags.tld"%>
<s:include value="navigation.jsp"><s:param name="selectedTab">compliance</s:param></s:include>
<fieldset>
	<legend>Inspections with Findings</legend>
	<div class="topControls">
		<dts:listcontrols id="noncomplianceTopControls" name="lstCtrl" action="noncompliance" namespace="/facility/complianceandhistory" useAjax="true" ajaxTarget="#complianceandhistoryBase">
			<s:param name="facilityId" value="facilityId"/>
		</dts:listcontrols>
	</div>
	<s:if test="!lstCtrl.results.isEmpty">
		<display:table name="lstCtrl.results" id="nonComplianceViews" class="tables" varTotals="totals">
			<display:column title="Inspection Type/Date">
				<s:url id="viewInspectionUrl" action="open-tab" namespace="/facility" includeParams="false">
					<s:param name="facilityId" value="facilityId"/>
					<s:param name="tab" value="%{'inspections'}"/>
					<s:param name="act" value="%{'edit-inspection-record'}"/>
					<s:param name="ns" value="%{'/inspections'}"/>
					<s:param name="id" value="id"/>
					<s:param name="inspectionId" value="#attr.nonComplianceViews.inspectionId"/>
				</s:url>
				<s:a href="%{viewInspectionUrl}">
					<s:property value="#attr.nonComplianceViews.primaryInspectionType"/>
				</s:a>
				<s:property value="#attr.nonComplianceViews.otherInspectionTypes"/>
				<s:date name="#attr.nonComplianceViews.inspectionDate" format="MM/dd/yyyy"/>
			</display:column>
			<display:column title="# Cited Findings" property="citedFindings" total="true" format="{0,number,integer}" class="center"/>
			<display:column title="# Repeat Cited Findings" property="repeatCitedFindings" total="true" format="{0,number,integer}" class="center"/>
			<display:column title="Total Cited NC Points" property="citedNCPoints" total="true" format="{0,number,integer}" class="center"/>
			<display:column title="# TA Findings" property="taFindings" total="true" format="{0,number,integer}" class="center"/>
			<display:column title="Total TA NC Points" property="taNCPoints" total="true" format="{0,number,integer}" class="center"/>
			<display:column title="Total Cited + TA NC Points" property="totalNCPoints" total="true" format="{0,number,integer}" class="center"/>
			<display:column title="Total CMP Assessed" property="cmpAmount" total="true" format="{0,number,currency}" class="currency"/>
			<display:footer>
				<tr class="total">
					<td>Totals</td>
					<td class="center"><s:property value="#attr.totals.column2"/></td>
					<td class="center"><s:property value="#attr.totals.column3"/></td>
					<td class="center"><s:property value="#attr.totals.column4"/></td>
					<td class="center"><s:property value="#attr.totals.column5"/></td>
					<td class="center"><s:property value="#attr.totals.column6"/></td>
					<td class="center"><s:property value="#attr.totals.column7"/></td>
					<td class="currency"><s:text name="format.currency"><s:param name="value" value="#attr.totals.column8"/></s:text></td>
				</tr>
			</display:footer>
		</display:table>
		<div class="bottomControls">
			<dts:listcontrols id="noncomplianceBottomControls" name="lstCtrl" action="noncompliance" namespace="/facility/complianceandhistory" useAjax="true" ajaxTarget="#complianceandhistoryBase">
				<s:param name="facilityId" value="facilityId"/>
			</dts:listcontrols>
		</div>
	</s:if>
</fieldset>