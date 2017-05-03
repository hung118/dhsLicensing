<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<input type="hidden" class="print-ctx-data" name="docctx" value="doc-ctx.facility"/>
<s:hidden cssClass="print-ctx-attr" name="ds.facility" value="%{facilityId}"/>
<div id="tabs">
	<ul>
		<s:url id="infoTabUrl" action="tab" namespace="/facility/information" escapeAmp="false">
			<s:param name="facilityId" value="facilityId"/>
		</s:url>
		<li>
			<a href="<s:if test="isValidFacility() && tab == 'information'">#preloaded-tab</s:if><s:else><s:property value="infoTabUrl"/></s:else>">Information</a>
		</li>
		
		<security:authorize access="hasRole('ROLE_ACCESS_PROFILE_VIEW')">
			<s:url id="screeningsTabUrl" action="tab" namespace="/facility/screenings" escapeAmp="false">
				<s:param name="facilityId" value="facilityId"/>
			</s:url>
			<li class="facilityTab"><a href="<s:if test="isValidFacility() && tab == 'screenings'">#preloaded-tab</s:if><s:else><s:property value="screeningsTabUrl"/></s:else>">Background Screening</a></li>
		</security:authorize>
		
		<security:authorize access="isAuthenticated() and principal.isInternal()">
			<s:url id="alNotesTabUrl" action="tab" namespace="/facility/actionlognotes" escapeAmp="false">
				<s:param name="facilityId" value="facilityId"/>
			</s:url>
			<li class="facilityTab"><a href="<s:if test="isValidFacility() && tab == 'actionLog'">#preloaded-tab</s:if><s:else><s:property value="alNotesTabUrl"/></s:else>">Action Log &amp; Notes</a></li>
			<s:url id="inspectionsTabUrl" action="tab" namespace="/facility/inspections" escapeAmp="false">
				<s:param name="facilityId" value="facilityId"/>
			</s:url>
			<li class="facilityTab"><a href="<s:if test="isValidFacility() && tab == 'inspections'">#preloaded-tab</s:if><s:else><s:property value="inspectionsTabUrl"/></s:else>">Inspections</a></li>
			<s:url id="complaintsTabUrl" action="tab" namespace="/facility/complaints" escapeAmp="false">
				<s:param name="facilityId" value="facilityId"/>
			</s:url>
			<li class="facilityTab"><a href="<s:if test="isValidFacility() && tab == 'complaints'">#preloaded-tab</s:if><s:else><s:property value="complaintsTabUrl"/></s:else>">Complaints</a></li>
			<s:url id="cmpsTabUrl" action="tab" namespace="/facility/cmps" escapeAmp="false">
				<s:param name="facilityId" value="facilityId"/>
			</s:url>
			<li class="facilityTab"><a href="<s:if test="isValidFacility() && tab == 'cmps'">#preloaded-tab</s:if><s:else><s:property value="cmpsTabUrl"/></s:else>">License Fees</a></li>
			<s:url id="compAndHistTabUrl" action="tab" namespace="/facility/complianceandhistory" escapeAmp="false">
				<s:param name="facilityId" value="facilityId"/>
			</s:url>
			<li class="facilityTab"><a href="<s:if test="isValidFacility() && tab == 'compliance'">#preloaded-tab</s:if><s:else><s:property value="compAndHistTabUrl"/></s:else>">Compliance &amp; History</a></li>
			<s:url id="screeningsTabUrl" action="tab" namespace="/facility/screenings" escapeAmp="false">
				<s:param name="facilityId" value="facilityId"/>
			</s:url>
			<li class="facilityTab"><a href="<s:if test="isValidFacility() && tab == 'screenings'">#preloaded-tab</s:if><s:else><s:property value="screeningsTabUrl"/></s:else>">Background Screening</a></li>
		</security:authorize>
		<security:authorize access="isAuthenticated() and (principal.isInternal() or hasRole('ROLE_FACILITY_PROVIDER'))">
			<s:url id="variancesTabUrl" action="tab" namespace="/facility/variances" escapeAmp="false">
				<s:param name="facilityId" value="facilityId"/>
			</s:url>
			<li class="facilityTab"><a href="<s:if test="isValidFacility() && tab == 'variances'">#preloaded-tab</s:if><s:else><s:property value="variancesTabUrl"/></s:else>">Variances</a></li>
		</security:authorize>
		<security:authorize access="isAuthenticated() and principal.isInternal()">
			<s:url id="incAndInjTabUrl" action="tab" namespace="/facility/incidents" escapeAmp="false">
				<s:param name="facilityId" value="facilityId"/>
			</s:url>
			<li class="facilityTab"><a href="<s:if test="isValidFacility() && tab == 'incidents'">#preloaded-tab</s:if><s:else><s:property value="incAndInjTabUrl"/></s:else>">Incidents &amp; Injuries</a></li>
		</security:authorize>
	</ul>
	<s:if test="act != null">
		<s:set var="preloadedData"><s:if test="tab == 'information'">{index: 0, url: '<s:property value="infoTabUrl"/>'}</s:if><s:elseif test="tab == 'actionLog'">{index: 1, url: '<s:property value="alNotesTabUrl"/>'}</s:elseif><s:elseif test="tab == 'inspections'">{index: 2, url: '<s:property value="inspectionsTabUrl"/>'}</s:elseif><s:elseif test="tab == 'complaints'">{index: 3, url: '<s:property value="complaintsTabUrl"/>'}</s:elseif><s:elseif test="tab == 'cmps'">{index: 4, url: '<s:property value="cmpsTabUrl"/>'}</s:elseif><s:elseif test="tab == 'compliance'">{index: 5, url: '<s:property value="compAndHistTabUrl"/>'}</s:elseif><s:elseif test="tab == 'screenings'">{index: 6, url: '<s:property value="screeningsTabUrl"/>'}</s:elseif><s:elseif test="tab == 'variances'">{index: 7, url: '<s:property value="variancesTabUrl"/>'}</s:elseif><s:elseif test="tab == 'incidents'">{index: 8, url: '<s:property value="incAndInjTabUrl"/>'}</s:elseif></s:set>
		<div id="preloaded-tab" class="<s:property value="%{preloadedData}"/>">
			<div id="<s:property value='tab'/>Base">
				<s:action name="%{act}" namespace="/facility%{ns}" executeResult="true"/>
			</div>
		</div>
	</s:if>
</div>
<script id="ccl-state-chg-dialog-tmpl" type="text/x-jquery-tmpl">
	<span class="ccl-error-container"></span>
	<form id="{{= formId}}" action="{{= action}}" method="post" class="ccl-action-save">
		{{each params}}
			<input type="hidden" name="\${\$value.name}" value="\${\$value.value}"/>
		{{/each}}
		<ol class="fieldList">
			<li>
				<label for="{{= formId}}-note">Note:</label>
				<textarea id="{{= formId}}-note" name="note" class="dialogTextArea"/>
			</li>
			<li class="ccl-char-limit"></li>
		</ol>
	</form>
</script>
<script id="ccl-state-hist-tmpl" type="text/x-jquery-tmpl">
	<table id="obj-state-hist" class="tables">
		<thead>
			<tr>
				<th>Date</th>
				<th class="shrinkCol">Action Taker</th>
				<th class="shrinkCol">Action Type</th>
				<th>Note</th>
			</tr>
		</thead>
		<tbody>
			{{each(i, item) stateChanges}}
				<tr class="{{= i % 2 == 0 ? 'even' : 'odd'}}">
					<td class="shrinkCol">{{= formatDateTime(item.changeDate)}}</td>
					<td class="shrinkCol">{{= item.changedBy}}</td>
					<td class="shrinkCol">{{= item.changeType}}</td>
					<td>{{= item.note}}</td>
				</tr>
			{{/each}}
		</tbody>
	</table>
</script>
