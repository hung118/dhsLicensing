<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@ taglib prefix="dts" uri="/WEB-INF/tags/dts-tags.tld"%>
<%@ taglib prefix="ccl" uri="/WEB-INF/tags/ccl-tags.tld"%>
<s:if test="!lstCtrl.results.isEmpty">
	<fieldset>
		<legend><a name="outstanding_cmps" class="quickLink">License Fees waiting for approval</a></legend>
		<div class="ccl-list-ctrls clearfix">
			<dts:listcontrols id="cmpWaitingTopControls" name="lstCtrl" action="license-fees" namespace="/home/alerts" useAjax="true" ajaxTarget="#licenseFeesSection" />
		</div>
		<display:table name="lstCtrl.results" id="cmps" class="tables">
			<display:column title="Received Date" headerClass="shrinkCol">
				<s:url id="editCmpUrl" action="open-tab" namespace="/facility" includeParams="false">
					<s:param name="facilityId" value="#attr.cmps.facility.id"/>
					<s:param name="tab" value="%{'cmps'}"/>
					<s:param name="act" value="%{'edit-cmp'}"/>
					<s:param name="ns" value="%{'/cmps'}"/>
					<s:param name="transaction.id" value="#attr.cmps.id"/>
				</s:url>
				<s:a href="%{editCmpUrl}">
					<s:date name="#attr.cmps.date" format="MM/dd/yyyy"/>
				</s:a>
			</display:column>
			<display:column title="Facility Name">
				<s:property value="#attr.cmps.facility.name"/>
			</display:column>
			<display:column title="Check Date" headerClass="shrinkCol">
				<s:date name="#attr.cmps.checkDate" format="MM/dd/yyyy"/>
			</display:column>
			<display:column title="Check Owner">
				<s:property value="#attr.cmps.checkOwner"/>
			</display:column>
			<display:column title="Amount" headerClass="displayHeaderCurrency" decorator="gov.utah.dts.det.ccl.view.admin.DisplayCurrency" style="text-align:right;">
				<s:property value="#attr.cmps.amount"/>
			</display:column>
		</display:table>
		<div class="ccl-list-ctrls clearfix">
			<dts:listcontrols id="cmpWaitingBottomControls" name="lstCtrl" action="license-fees" namespace="/home/alerts" useAjax="true" ajaxTarget="#licenseFeesSection" />
		</div>
	</fieldset>
</s:if>