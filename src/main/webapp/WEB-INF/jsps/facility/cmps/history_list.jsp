<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="dts" uri="/WEB-INF/tags/dts-tags.tld"%>
<%@ taglib prefix="ccl" uri="/WEB-INF/tags/ccl-tags.tld"%>
<fieldset>
	<legend>Fee History</legend>
	<div class="topControls">
		<dts:listcontrols id="cmpHistoryTopControls" name="lstCtrl" action="history-list" namespace="/facility/cmps" useAjax="true" ajaxTarget="#cmpsHistorySection" paramExcludes="%{'lstCtrl.range'}">
			<ccl:listrange id="cmpHistoryTopControls" name="lstCtrl"/>
			<s:param name="facilityId" value="facilityId"/>
		</dts:listcontrols>
	</div>
	<s:if test="!lstCtrl.results.isEmpty">
		<display:table name="lstCtrl.results" id="cmps" class="tables">
			<display:column title="Received Date" headerClass="shrinkCol">
				<s:url id="editCmpUrl" action="edit-cmp" includeParams="false">
					<s:param name="transaction.id" value="#attr.cmps.id"/>
				</s:url>
				<s:a cssClass="ajaxify {target: '#cmpsBase'}" href="%{editCmpUrl}">
					<s:date name="#attr.cmps.date" format="MM/dd/yyyy"/>
				</s:a>
			</display:column>
			<display:column title="Check Date" headerClass="shrinkCol">
				<s:date name="#attr.cmps.checkDate" format="MM/dd/yyyy"/>
			</display:column>
			<display:column title="Check Owner">
				<s:property value="#attr.cmps.checkOwner"/>
			</display:column>
			<display:column title="Amount" headerClass="displayHeaderCurrency shrinkCol" decorator="gov.utah.dts.det.ccl.view.admin.DisplayCurrency" style="text-align:right;">
				<s:property value="#attr.cmps.amount"/>
			</display:column>
		</display:table>
		<div class="bottomControls">
			<dts:listcontrols id="cmpHistoryBottomControls" name="lstCtrl" action="history-list" namespace="/facility/cmps" useAjax="true" ajaxTarget="#cmpsHistorySection">
				<s:param name="facilityId" value="facilityId"/>
			</dts:listcontrols>
		</div>
	</s:if>
</fieldset>
