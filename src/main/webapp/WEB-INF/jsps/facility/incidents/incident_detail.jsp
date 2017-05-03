<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<s:set var="incident" value="incident" scope="request"/>
<fieldset id="inc-dtl-fieldset">
	<legend>Incident &amp; Injury Report</legend>
	<span class="ccl-error-container"></span>
	<security:authorize access="hasAnyRole('ROLE_SUPER_ADMIN')">
		<div class="ccl-fs-ctrls">
			<ul>
				<li><span class="label">Created By:</span> <s:property value="incident.createdBy.firstAndLastName"/></li>
				<li><span class="label">Creation Date:</span> <s:date name="incident.creationDate" format="MM/dd/yyyy hh:mm a"/></li>
				<li><span class="label">Last Modified By:</span> <s:property value="incident.modifiedBy.firstAndLastName"/></li>
				<li><span class="label">Last Modified Date:</span> <s:date name="incident.modifiedDate" format="MM/dd/yyyy hh:mm a"/></li>
			</ul>
			<s:url id="incHistUrl" action="object-history" namespace="/facility">
				<s:param name="objectId" value="incidentId"/>
			</s:url>
			<s:a id="ccl-ins-hist" href="%{incHistUrl}" cssClass="ccl-so-hist">
				History
			</s:a>
		</div>
	</security:authorize>
	<ol class="fieldList">
		<li>
			<div class="label">Incident/Injury Date:</div>
			<div class="value"><s:date name="incident.date" format="MM/dd/yyyy"/></div>
		</li>
		<li>
			<div class="label">Client:</div>
			<ol class="fieldGroup">
				<li>
					<div class="label">Name:</div>
					<div class="value"><s:property value="incident.child.firstAndLastName"/></div>
				</li>
				<li class="clearLeft fieldMargin">
					<div class="label">Age:</div>
					<div class="value"><s:property value="incident.childAge.value"/></div>
				</li>
				<li class="fieldMargin">
					<div class="label">Gender:</div>
					<div class="value"><s:property value="incident.child.gender.displayName"/></div>
				</li>
			</ol>
		</li>
		<li>
			<div class="label">Injury:</div>
			<ol class="fieldGroup">
				<li>
					<div class="label">Type:</div>
					<div class="value"><s:property value="incident.injuryType.value"/></div>
				</li>
				<s:if test="!incident.locationsOfInjury.isEmpty">
					<li class="clearLeft fieldMargin">
						<div class="label">Incident/Injury Locations:</div>
						<div class="value">
							<s:iterator value="incident.locationsOfInjury" status="row">
								<s:property value="value"/><s:if test="!#row.last">,</s:if>
							</s:iterator>
						</div>
					</li>
				</s:if>
				<li class="clearLeft fieldMargin">
					<div class="label">Death:</div>
					<div class="value"><s:property value="@gov.utah.dts.det.ccl.view.YesNoChoice@valueOfBoolean(incident.death).displayName"/></div>
				</li>
				<li class="clearLeft fieldMargin">
					<div class="label">Received Treatment:</div>
					<div class="value"><s:property value="@gov.utah.dts.det.ccl.view.YesNoChoice@valueOfBoolean(incident.receivedTreatment).displayName"/></div>
				</li>
				<s:if test="@org.apache.commons.lang.StringUtils@isNotBlank(incident.incidentText)">
					<li class="clearLeft fieldMargin">
						<div class="label">Description of How Incident/Injury Occurred:</div>
						<div class="value description"><s:property value="incident.incidentText"/></div>
					</li>
				</s:if>
				<s:if test="@org.apache.commons.lang.StringUtils@isNotBlank(incident.injuryText)">
					<li class="clearLeft fieldMargin">
						<div class="label">Description of Any Injuries:</div>
						<div class="value description"><s:property value="incident.injuryText"/></div>
					</li>
				</s:if>
				<s:if test="@org.apache.commons.lang.StringUtils@isNotBlank(incident.treatmentText)">
					<li class="clearLeft fieldMargin">
						<div class="label">Description of Treatment Received:</div>
						<div class="value description"><s:property value="@gov.utah.dts.det.ccl.view.ViewUtils@replaceNewlines(incident.treatmentText)"/></div>
					</li>
				</s:if>
			</ol>
		</li>
		<li>
			<label>License Specialist Notification:</label>
			<ol class="fieldGroup">
				<li>
					<s:if test="incident.reportedOverPhone != null and incident.reportedOverPhone">
						The provider called within 24 hours of the accident.
					</s:if>
					<s:elseif test="incident.reportedOverPhone != null and !incident.reportedOverPhone">
						<span class="redtext">The provider did not call within 24 hours of the accident.</span>
					</s:elseif>
				</li>
				<li class="clearLeft fieldMargin">
					<s:if test="incident.sentWrittenReport != null and incident.sentWrittenReport">
						The provider sent a written report within 5 days of the accident.
					</s:if>
					<s:elseif test="incident.sentWrittenReport != null and !incident.sentWrittenReport">
						<span class="redtext">The provider did not send a written report within 5 days of the accident.</span>
					</s:elseif>
				</li>
			</ol>
		</li>
		<li class="submit">
			<security:authorize access="hasPermission('save-entry', 'incident')">
				<s:url id="editIncUrl" action="edit-incident-info">
					<s:param name="facilityId" value="facilityId"/>
					<s:param name="incidentId" value="incidentId"/>
				</s:url>
				<s:a href="%{editIncUrl}" cssClass="ccl-button ajaxify {target: '#incidentSection'}">
					Edit
				</s:a>
			</security:authorize>
			<security:authorize access="hasPermission('complete-entry', 'incident')">
				<s:url id="completeEntryUrl" action="complete-entry">
					<s:param name="facilityId" value="facilityId"/>
					<s:param name="incidentId" value="incidentId"/>
				</s:url>
				<s:a id="inc-complete-entry" href="%{completeEntryUrl}" cssClass="ccl-button ccl-action-save ccl-state-change {title: 'Entry Complete', onSuccess: function() {refreshIncident();}}">
					Entry Complete
				</s:a>
			</security:authorize>
			<security:authorize access="hasPermission('reject-entry', 'incident')">
				<s:url id="rejectEntryUrl" action="reject-entry">
					<s:param name="facilityId" value="facilityId"/>
					<s:param name="incidentId" value="incidentId"/>
				</s:url>
				<s:a id="inc-reject" href="%{rejectEntryUrl}" cssClass="ccl-button ccl-action-save ccl-state-change {title: 'Correction Required', onSuccess: function() {refreshIncident();}}">
					Correction Required
				</s:a>
			</security:authorize>
			<security:authorize access="hasPermission('finalize', 'incident')">
				<s:url id="finIncUrl" action="finalize">
					<s:param name="facilityId" value="facilityId"/>
					<s:param name="incidentId" value="incidentId"/>
				</s:url>
				<s:a id="inc-finalize" href="%{finIncUrl}" cssClass="ccl-button ccl-action-save ccl-state-change {title: 'Finalize', onSuccess: function() {refreshIncident();}}">
					Finalize
				</s:a>
			</security:authorize>
			<security:authorize access="hasPermission('unfinalize', 'incident')">
				<s:url id="unfinIncUrl" action="unfinalize">
					<s:param name="facilityId" value="facilityId"/>
					<s:param name="incidentId" value="incidentId"/>
				</s:url>
				<s:a id="inc-unfinalize" href="%{unfinIncUrl}" cssClass="ccl-button ccl-action-save ccl-state-change {title: 'Unfinalize',onSuccess: function() {refreshIncident();}}">
					Unfinalize
				</s:a>
			</security:authorize>
		</li>
	</ol>
</fieldset>