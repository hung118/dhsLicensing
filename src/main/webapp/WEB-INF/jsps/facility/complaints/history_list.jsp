<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="dts" uri="/WEB-INF/tags/dts-tags.tld"%>
<%@ taglib prefix="ccl" uri="/WEB-INF/tags/ccl-tags.tld"%>
<fieldset>
	<legend>Complaint History</legend>
	<div class="topControls">
		<dts:listcontrols id="complaintHistoryTopControls" name="lstCtrl" action="history-list" namespace="/facility/complaints" useAjax="true" ajaxTarget="#complaintsHistorySection" paramExcludes="%{'lstCtrl.range'}">
			<ccl:listrange id="complaintHistoryTopControls" name="lstCtrl"/>
			<s:param name="facilityId" value="facilityId"/>
		</dts:listcontrols>
	</div>
	<s:if test="!lstCtrl.results.isEmpty">
		<display:table name="lstCtrl.results" id="complaints" class="tables">
			<display:column title="Date Received">
				<security:authorize access="isAuthenticated() and principal.isInternal()">
					<s:url id="viewComplaintUrl" action="tab" namespace="/facility/complaints/intake" includeParams="false">
						<s:param name="facilityId" value="facilityId"/>
						<s:param name="complaintId" value="#attr.complaints.id"/>
					</s:url>
					<s:a cssClass="ajaxify {target: '#complaintsBase'}" href="%{viewComplaintUrl}">
						<s:date name="#attr.complaints.dateReceived" format="MM/dd/yyyy"/>
					</s:a>
				</security:authorize>
				<security:authorize access="!isAuthenticated()">
					<s:date name="#attr.complaints.dateReceived" format="MM/dd/yyyy"/>
				</security:authorize>
			</display:column>
<%--  Redmine #26323 - Removed State from the list view			
			<display:column title="State">
				<s:property value="#attr.complaints.state"/>
			</display:column>
--%>			
			<display:column title="Conclusion">
				<s:property value="#attr.complaints.conclusion"/>
			</display:column>
<%--
			<display:column title="Anonymous?" headerClass="shrinkCol">
				<s:property value="@gov.utah.dts.det.ccl.view.YesNoChoice@valueOfBoolean(#attr.complaints.anonymous).displayName"/>
			</display:column>
			<display:column title="Substantiated?" headerClass="shrinkCol">
				<s:if test="#attr.complaints.anonymous">N/A</s:if><s:else><s:property value="@gov.utah.dts.det.ccl.view.YesNoChoice@valueOfBoolean(#attr.complaints.substantiated).displayName"/></s:else>
			</display:column>
--%>
		</display:table>
		<div class="bottomControls">
			<dts:listcontrols id="complaintHistoryBottomControls" name="lstCtrl" action="history-list" namespace="/facility/complaints" useAjax="true" ajaxTarget="#complaintsHistorySection">
				<s:param name="facilityId" value="facilityId"/>
			</dts:listcontrols>
		</div>
	</s:if>
</fieldset>