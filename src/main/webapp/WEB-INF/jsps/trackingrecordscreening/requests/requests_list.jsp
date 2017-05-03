<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="dts" uri="/WEB-INF/tags/dts-tags.tld"%>
<%@ taglib prefix="ccl" uri="/WEB-INF/tags/ccl-tags.tld"%>
<fieldset>
	<legend>Requests List</legend>
	<div class="topControls">
		<div class="mainControls">
			<s:url id="newRequestUrl" action="edit-request">
				<s:param name="screeningId" value="screeningId"/>
			</s:url>
			<s:a href="%{newRequestUrl}" cssClass="ccl-button ajaxify {target: '#requestsSection'}">
				New Request
			</s:a>
		</div>
		<dts:listcontrols id="requestsTopControls" name="lstCtrl" action="list-requests" namespace="/trackingrecordscreening/requests" useAjax="true" ajaxTarget="#requestsSection">
			<s:param name="screeningId" value="screeningId"/>
		</dts:listcontrols>
	</div>
	<s:if test="!lstCtrl.results.isEmpty">
		<display:table name="lstCtrl.results" id="requests" class="tables">
			<display:column title="Country">
				<s:url id="editRequestUrl" action="edit-request" includeParams="false">
					<s:param name="screeningId" value="screeningId"/>
					<s:param name="requestId" value="#attr.requests.id"/>
				</s:url>
				<s:a cssClass="ajaxify {target: '#requestsSection'}" href="%{editRequestUrl}">
					<s:property value="%{#attr.requests.country}" />
				</s:a>
			</display:column>
			<display:column title="From Date" headerClass="shrinkCol">
				<s:date name="#attr.requests.fromDate" format="MM/dd/yyyy"/>
			</display:column>
			<display:column title="To Date" headerClass="shrinkCol">
				<s:date name="#attr.requests.toDate" format="MM/dd/yyyy"/>
			</display:column>
			<display:column title="Received Date" headerClass="shrinkCol">
				<s:date name="#attr.requests.receivedDate" format="MM/dd/yyyy"/>
			</display:column>
			<display:column>
				<s:url id="deleteRequestUrl" action="delete-request" includeParams="false">
					<s:param name="screeningId" value="screeningId"/>
					<s:param name="requestId" value="#attr.requests.id"/>
				</s:url>
				<s:a href="%{deleteRequestUrl}" cssClass="ajaxify {target: '#requestsSection'} ccl-action-delete ccl-delete-link">
					delete
				</s:a>
			</display:column>
		</display:table>
	</s:if>
	<div class="bottomControls">
		<dts:listcontrols id="requestsBottomControls" name="lstCtrl" action="list-requests" namespace="/trackingrecordscreening/requests" useAjax="true" ajaxTarget="#requestsSection">
			<s:param name="screeningId" value="screeningId"/>
		</dts:listcontrols>
	</div>
</fieldset>
