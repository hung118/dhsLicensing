<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="dts" uri="/WEB-INF/tags/dts-tags.tld"%>
<%@ taglib prefix="ccl" uri="/WEB-INF/tags/ccl-tags.tld"%>
<fieldset>
	<legend>Variance History</legend>
	<span id="variance-errors"></span>
	<div class="topControls">
		<div class="mainControls">
			<security:authorize access="principal.isInternal() or hasAnyRole('ROLE_FACILITY_PROVIDER')">
				<s:url id="newVarUrl" action="new-variance">
					<s:param name="facilityId" value="facilityId"/>
				</s:url> 
				<s:a href="%{newVarUrl}" cssClass="ccl-button ajaxify {target: '#variancesBase'}">
					New Variance
				</s:a>
			</security:authorize>
		</div>
		<dts:listcontrols id="varianceHistoryTopControls" name="lstCtrl" action="variances-list" namespace="/facility/variances" useAjax="true" ajaxTarget="#variancesBase" paramExcludes="%{'lstCtrl.range'}">
			<ccl:listrange id="varianceHistoryTopControls" name="lstCtrl"/>
			<s:param name="facilityId" value="facilityId"/>
		</dts:listcontrols>
	</div>
	<s:if test="!lstCtrl.results.isEmpty">
		<display:table name="lstCtrl.results" id="variances" class="tables" decorator="gov.utah.dts.det.ccl.view.facility.VariancesListDecorator">
			<display:column title="Request Date" headerClass="shrinkCol">
				<s:url id="viewVarianceUrl" action="edit-variance" includeParams="false">
					<s:param name="facilityId" value="facilityId"/>
					<s:param name="varianceId" value="#attr.variances.id"/>
				</s:url>
				<s:a cssClass="ajaxify {target: '#variancesBase'}" href="%{viewVarianceUrl}">
					<s:date name="#attr.variances.requestDate" format="MM/dd/yyyy"/>
				</s:a>
			</display:column>
			<display:column title="Outcome">
				<s:set var="variance" value="%{#attr.variances}" scope="request"/>
				<security:authorize access="hasPermission('view-outcome','variance') and request.getAttribute('variance').isFinalized()">
					<s:if test="#attr.variances.isRevokeFinalized()">Revoked</s:if>
					<s:else><s:property value="#attr.variances.outcome.displayName"/></s:else>
				</security:authorize>
				<security:authorize access="!hasPermission('view-outcome','variance') or (hasPermission('view-outcome','variance') and !request.getAttribute('variance').isFinalized())">
					Not Reviewed
				</security:authorize>
			</display:column>
			<display:column title="Rule">
				R501-<s:property value="#attr.variances.rule.generatedRuleNumber"/>
			</display:column>
			<display:column title="Start Date" headerClass="shrinkCol">
				<s:date name="#attr.variances.startDate" format="MM/dd/yyyy"/>
			</display:column>
			<display:column title="End Date" headerClass="shrinkCol">
				<s:if test="#attr.variances.revocationDate != null">
					<s:date name="#attr.variances.revocationDate" format="MM/dd/yyyy"/>
				</s:if>
				<s:else>
					<s:date name="#attr.variances.endDate" format="MM/dd/yyyy"/>
				</s:else>
			</display:column>
			<display:column title="Requested Start" headerClass="shrinkCol">
				<s:date name="#attr.variances.requestedStartDate" format="MM/dd/yyyy"/>
			</display:column>
			<display:column title="Requested End" headerClass="shrinkCol">
				<s:date name="#attr.variances.requestedEndDate" format="MM/dd/yyyy"/>
			</display:column>
			<display:column>
				<s:set var="variance" value="%{#attr.variances}" scope="request"/>
				<security:authorize access="hasPermission('save','variance') and !request.getAttribute('variance').isFinalized()">
						<s:url id="deleteVarianceUrl" action="delete-variance" includeParams="false">
							<s:param name="facilityId" value="facilityId"/>
							<s:param name="varianceId" value="#attr.variances.id"/>
						</s:url>
						<s:a href="%{deleteVarianceUrl}" cssClass="ccl-action-delete ccl-delete-link">
							Delete
						</s:a>
				</security:authorize>
			</display:column>
		</display:table>
		<div class="bottomControls">
			<dts:listcontrols id="varianceHistoryBottomControls" name="lstCtrl" action="variances-list" namespace="/facility/variances" useAjax="true" ajaxTarget="#variancesBase">
				<s:param name="facilityId" value="facilityId"/>
			</dts:listcontrols>
		</div>
	</s:if>
</fieldset>
<script type="text/javascript">
	$("#variances").ccl("tableDelete", {errorContainer: "#variance-errors"});
</script>