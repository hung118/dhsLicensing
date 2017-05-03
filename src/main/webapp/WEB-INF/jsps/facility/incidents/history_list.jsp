<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="dts" uri="/WEB-INF/tags/dts-tags.tld"%>
<%@ taglib prefix="ccl" uri="/WEB-INF/tags/ccl-tags.tld"%>
<fieldset>
	<legend>Reports History</legend>
	<div class="topControls">
		<dts:listcontrols id="incidentHistoryTopControls" name="lstCtrl" action="history-list" namespace="/facility/incidents" useAjax="true" ajaxTarget="#incidentsHistorySection" paramExcludes="%{'lstCtrl.range'}">
			<ccl:listrange id="incidentHistoryTopControls" name="lstCtrl"/>
			<s:param name="facilityId" value="facilityId"/>
		</dts:listcontrols>
	</div>
	<s:if test="!lstCtrl.results.isEmpty">
		<display:table name="lstCtrl.results" id="incidents" class="tables">
			<display:column title="Date" headerClass="shrinkCol">
				<s:url id="viewIncidentUrl" action="edit-incident" includeParams="false">
					<s:param name="facilityId" value="facilityId"/>
					<s:param name="incidentId" value="#attr.incidents.id"/>
				</s:url>
				<s:a cssClass="ajaxify {target: '#incidentsBase'}" href="%{viewIncidentUrl}">
					<s:date name="#attr.incidents.date" format="MM/dd/yyyy"/>
				</s:a>
			</display:column>
			<display:column title="Name">
				<s:property value="#attr.incidents.childName"/>
			</display:column>
			<display:column title="Age">
				<s:property value="#attr.incidents.childAge"/>
			</display:column>
			<display:column title="Death?" headerClass="shrinkCol">
				<s:property value="@gov.utah.dts.det.ccl.view.YesNoChoice@valueOfBoolean(#attr.incidents.death).displayName" escape="false"/>
			</display:column>
		</display:table>
		<div class="bottomControls">
			<dts:listcontrols id="incidentHistoryBottomControls" name="lstCtrl" action="history-list" namespace="/facility/incidents" useAjax="true" ajaxTarget="#incidentsHistorySection">
				<s:param name="facilityId" value="facilityId"/>
			</dts:listcontrols>
		</div>
	</s:if>
</fieldset>