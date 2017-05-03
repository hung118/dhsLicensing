<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="dts" uri="/WEB-INF/tags/dts-tags.tld"%>
<%@ taglib prefix="ccl" uri="/WEB-INF/tags/ccl-tags.tld"%>
<fieldset>
	<legend>Reports In Progress</legend>
	<span id="incident-errors"></span>
	<div class="topControls">
		<div class="mainControls">
			<security:authorize access="hasClassPermission('create', 'Incident')">
				<s:url id="newIncUrl" action="new-incident">
					<s:param name="facilityId" value="facilityId"/>
				</s:url>
				<s:a href="%{newIncUrl}" cssClass="ccl-button ajaxify {target: '#incidentsBase'}">
					New Incident & Injury
				</s:a>
			</security:authorize>
		</div>
		<dts:listcontrols id="incidentProgressTopControls" name="lstCtrl" action="progress-list" namespace="/facility/incidents" useAjax="true" ajaxTarget="#incidentsProgressSection">
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
				<s:property value="#attr.incidents.age"/>
			</display:column>
			<display:column title="Death?" headerClass="shrinkCol">
				<s:property value="@gov.utah.dts.det.ccl.view.YesNoChoice@valueOfBoolean(#attr.incidents.death).displayName" escape="false"/>
			</display:column>
			<display:column class="shrinkCol">
				<s:set var="incident" value="%{#attr.incidents}" scope="request"/>
				<security:authorize access="hasPermission('delete', 'incident')">
					<s:url id="deleteIncidentUrl" action="delete-incident" includeParams="false">
						<s:param name="facilityId" value="facilityId"/>
						<s:param name="incidentId" value="#attr.incidents.id"/>
					</s:url>
					<s:a href="%{deleteIncidentUrl}" cssClass="ccl-action-delete ccl-delete-link">
						delete
					</s:a>
				</security:authorize>
			</display:column>
		</display:table>
		<div class="bottomControls">
			<dts:listcontrols id="incidentProgressBottomControls" name="lstCtrl" action="progress-list" namespace="/facility/incidents" useAjax="true" ajaxTarget="#incidentsProgressSection">
				<s:param name="facilityId" value="facilityId"/>
			</dts:listcontrols>
		</div>
	</s:if>
</fieldset>
<script type="text/javascript">
	$("#incidents").ccl("tableDelete", {errorContainer: "#incident-errors"});
</script>