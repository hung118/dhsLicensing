<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@ taglib prefix="dts" uri="/WEB-INF/tags/dts-tags.tld"%>
<%@ taglib prefix="ccl" uri="/WEB-INF/tags/ccl-tags.tld"%>
<s:if test="!lstCtrl.results.isEmpty">
	<fieldset>
		<legend><a name="manager_variances" class="quickLink">Variance Requests Needing A Manager Response</a></legend>
		<div class="ccl-list-ctrls clearfix">
			<dts:listcontrols id="licensorVariancesTopControls" name="lstCtrl" action="manager-variances" namespace="/home/alerts" useAjax="true" ajaxTarget="#managerVariancesSection"/>
		</div>
		<display:table name="lstCtrl.results" id="variances" class="tables">
			<display:column title="Request Date" headerClass="shrinkCol">
				<s:url id="viewVarianceUrl" action="open-tab" namespace="/facility" includeParams="false">
					<s:param name="facilityId" value="#attr.variances.facilityId"/>
					<s:param name="tab" value="%{'variances'}"/>
					<s:param name="act" value="%{'edit-variance'}"/>
					<s:param name="ns" value="%{'/variances'}"/>
					<s:param name="varianceId" value="#attr.variances.id"/>
				</s:url>
				<s:a href="%{viewVarianceUrl}">
					<s:date name="#attr.variances.requestDate" format="MM/dd/yyyy"/>
				</s:a>
			</display:column>
			<display:column title="Facility">
				<s:property value="#attr.variances.facilityName"/>
			</display:column>
			<display:column title="Rule">
				501-<s:property value="#attr.variances.ruleNumber"/>
			</display:column>
		</display:table>
		<div class="ccl-list-ctrls clearfix">
			<dts:listcontrols id="licensorVariancesBottomControls" name="lstCtrl" action="manager-variances" namespace="/home/alerts" useAjax="true" ajaxTarget="#managerVariancesSection"/>
		</div>
	</fieldset>
</s:if>