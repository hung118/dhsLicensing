<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="dts" uri="/WEB-INF/tags/dts-tags.tld"%>
<%@ taglib prefix="ccl" uri="/WEB-INF/tags/ccl-tags.tld"%>
<s:include value="navigation.jsp"><s:param name="selectedTab">history</s:param></s:include>
<fieldset>
	<legend>Events</legend>
	<div>
		<s:form id="historyControlForm" action="history" method="get" cssClass="ajaxify {target: '#complianceandhistoryBase'}">
			<s:hidden name="facilityId"/>
			<s:hidden name="lstCtrl.sortBy"/>
			<s:hidden name="lstCtrl.range"/>
			<ol class="fieldList">
				<li class="twoColumn checkboxList">
					<span class="checkboxListLabel">Select Event(s) to narrow by (Leave blank to see all):</span>
					<s:checkboxlist name="eventTypes" value="%{eventTypes.{name()}}" list="facilityEventTypes" listKey="name()" listValue="displayName" template="checkboxlistcolumns"/>
				</li>
				<li class="submit">
					<s:submit value="Get History"/>
					<s:submit name="clear" value="Clear"/>
				</li>
			</ol>
		</s:form>
	</div>
	<div class="topControls marginTop">
		<dts:listcontrols id="historyTopControls" name="lstCtrl" action="history" namespace="/facility/complianceandhistory" useAjax="true" ajaxTarget="#complianceandhistoryBase" paramExcludes="%{'lstCtrl.range'}">
			<ccl:listrange id="historyTopControls" name="lstCtrl"/>
			<s:param name="facilityId" value="facilityId"/>
			<s:param name="eventTypes" value="eventTypes"/>
		</dts:listcontrols>
	</div>
	<s:if test="!lstCtrl.results.isEmpty">
		<display:table name="lstCtrl.results" id="history" class="tables">
			<display:column title="Date">
				<s:if test="#attr.history.eventType.name() == 'INSPECTION'">
					<s:url id="eventUrl" action="open-tab" namespace="/facility" includeParams="false">
						<s:param name="facilityId" value="facilityId"/>
						<s:param name="tab" value="%{'inspections'}"/>
						<s:param name="act" value="%{'edit-inspection-record'}"/>
						<s:param name="ns" value="%{'/inspections'}"/>
						<s:param name="inspectionId" value="#attr.history.eventId"/>
					</s:url>
				</s:if>
				<s:elseif test="#attr.history.eventType.name() == 'INCIDENT_AND_INJURY'">
					<s:url id="eventUrl" action="open-tab" namespace="/facility" includeParams="false">
						<s:param name="facilityId" value="facilityId"/>
						<s:param name="tab" value="%{'incidents'}"/>
						<s:param name="act" value="%{'edit-incident-record'}"/>
						<s:param name="ns" value="%{'/incidents'}"/>
						<s:param name="incident.id" value="#attr.history.eventId"/>
					</s:url>
				</s:elseif>
				<s:elseif test="#attr.history.eventType.name() == 'VARIANCE'">
					<s:url id="eventUrl" action="open-tab" namespace="/facility" includeParams="false">
						<s:param name="facilityId" value="facilityId"/>
						<s:param name="tab" value="%{'variances'}"/>
						<s:param name="act" value="%{'edit-variance'}"/>
						<s:param name="ns" value="%{'/variances'}"/>
						<s:param name="variance.id" value="#attr.history.eventId"/>
					</s:url>
				</s:elseif>
				<s:elseif test="#attr.history.eventType.name() == 'COMPLAINT'">
					<s:url id="eventUrl" action="open-tab" namespace="/facility" includeParams="false">
						<s:param name="facilityId" value="facilityId"/>
						<s:param name="tab" value="%{'complaints'}"/>
						<s:param name="act" value="%{'tab'}"/>
						<s:param name="ns" value="%{'/complaints/intake'}"/>
						<s:param name="complaintId" value="#attr.history.eventId"/>
					</s:url>
				</s:elseif>
				<s:if test="#attr.eventUrl != null">
					<s:a href="%{eventUrl}">
						<s:date name="#attr.history.eventDate" format="MM/dd/yyyy"/>
					</s:a>
				</s:if>
				<s:else>
					<s:date name="#attr.history.eventDate" format="MM/dd/yyyy"/>
				</s:else>
			</display:column>
			<display:column title="Event">
				<span class="description"><strong><s:property value="#attr.history.eventType.displayName"/></strong>: <s:property value="#attr.history.event"/></span>
			</display:column>
		</display:table>
		<div class="bottomControls">
		<dts:listcontrols id="historyBottomControls" name="lstCtrl" action="history" namespace="/facility/complianceandhistory" useAjax="true" ajaxTarget="#complianceandhistoryBase">
			<s:param name="facilityId" value="facilityId"/>
			<s:param name="eventTypes" value="eventTypes"/>
		</dts:listcontrols>
		</div>
	</s:if>
</fieldset>