<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="ccl" uri="/WEB-INF/tags/ccl-tags.tld"%>
<%@ taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<s:set var="showControls">${param.showControls == null ? false : param.showControls}</s:set>
<s:set var="inspection" value="inspection" scope="request"/>
<security:authorize access="hasPermission('save-entry', 'inspection')">
	<s:if test="#attr.showControls == 'true'">
		<div class="topControls">
			<div class="mainControls">
				<s:url id="newFindUrl" action="edit-finding">
					<s:param name="facilityId" value="facilityId"/>
					<s:param name="inspectionId" value="inspectionId"/>
				</s:url>
				<s:a href="%{newFindUrl}" cssClass="ccl-button ajaxify {target: '#findingsSection'}">
					New Finding
				</s:a>
			</div>
		</div>
	</s:if>
</security:authorize>
<s:if test="#attr.showControls != 'true' && !inspection.findings.isEmpty">
	<h1 class="marginTop">Current Inspection Findings Detail</h1>
</s:if>
<s:if test="!inspection.findings.isEmpty">
	<ol class="ccl-list">
		<s:iterator value="inspection.findings" status="row" var="find">
			<li class="ccl-list-item <s:if test="#row.odd">odd</s:if><s:else>even</s:else><s:if test="#attr.rescindedDate != null"> inactive</s:if>">
				<div class="left-column">
					<div>
						<span class="label">Rule #:</span>
						<security:authorize access="hasPermission('save-entry', 'inspection')">
							<s:url id="editFindingUrl" action="edit-finding" includeParams="false">
								<s:param name="facilityId" value="facilityId"/>
								<s:param name="inspectionId" value="inspectionId"/>
								<s:param name="finding.id" value="id"/>
							</s:url>
							<s:a href="%{editFindingUrl}" cssClass="ajaxify {target: '#findingsSection'}">
								<ccl:rule ruleId="rule.id" format="RNUM"/>
							</s:a>
						</security:authorize>
						<security:authorize access="!hasPermission('save-entry', 'inspection')">
							<ccl:rule ruleId="rule.id" format="RNUM"/>
						</security:authorize>
					</div>
					<div>
						<span class="label">Rule:</span>
						<ccl:rule ruleId="rule.id" format="RDESC"/>
					</div>
					<s:if test="#attr.warningCorrectionDate != null">
						<div>
							<span class="label">Correction Date:</span>
							<s:date name="warningCorrectionDate" format="MM/dd/yyyy"/>
						</div>
					</s:if>
					<s:if test="correctionVerification.name() != 'RESCINDED' and correctionVerification.name() != 'UNDER_APPEAL'">
						<div>
							<span class="label">Correction:</span>
							<s:if test="correctionVerification.name() == 'VERIFIED'">
								<s:if test="correctedOnSite">
									On Site
								</s:if>
								<s:else>
									Verified on inspection conducted on <s:date name="correctedOn.inspectionDate" format="MM/dd/yyyy"/>
								</s:else>
							</s:if>
							<s:elseif test="correctionVerification.name() == 'UNABLE_TO_VERIFY'">
								Unable to verify correction
							</s:elseif>
							<s:elseif test="correctionVerification.name() == 'PROVIDER_CLOSED'">
								Not verified, provider closed
							</s:elseif>
							<s:elseif test="correctionVerification.name() == 'VERIFICATION_PENDING'">
								<span class="redtext">Correction not verified</span>
							</s:elseif>
						</div>
					</s:if>
				</div>
				<div class="right-column">
					<div>
						<span class="label">Finding Category:</span>
						<s:property value="findingCategory.value"/>
					</div>
					<div>
						<span class="label">NC Level:</span>
						<s:property value="noncomplianceLevel.value"/>
					</div>
					<s:if test="#attr.cmpAmount != null">
						<div>
							<span class="label">CMP Amount:</span>
							<s:if test="#attr.cmpAmount != null"><s:text name="format.currency"><s:param name="value" value="cmpAmount"/></s:text></s:if>
						</div>
					</s:if>
				</div>
				<div class="clear">
					<span class="label">Finding:</span>
					<span class="description"><s:property value="declarativeStatement.trim()"/></span>
				</div>
				<s:if test="@org.apache.commons.lang.StringUtils@isNotBlank(additionalInformation)">
					<div>
						<span class="label">Additional Information:</span>
						<span class="description"><s:property value="additionalInformation.trim()"/></span>
					</div>
				</s:if>
				<div class="ccl-list-item-ctrls clearfix">
					<security:authorize access="hasPermission('save-entry', 'inspection')">
						<span class="ccl-right-float" style="margin-left: .5em;">
							<s:url id="deleteFindingUrl" action="delete-finding" includeParams="false">
								<s:param name="facilityId" value="facilityId"/>
								<s:param name="inspectionId" value="inspectionId"/>
								<s:param name="finding.id" value="id"/>
							</s:url>
							<s:a href="%{deleteFindingUrl}" cssClass="ajaxify {target: '#findingsSection'} ccl-action-delete">
								delete
							</s:a>
						</span>
					</security:authorize>
					<security:authorize access="hasAnyRole('ROLE_SUPER_ADMIN')">
						<s:form id="findingListForm" action="save-finding-list" method="post" cssClass="ajaxify {target: '#findingsSection'} ccl-action-save ccl-inline-form ccl-right-float">
							<s:hidden name="facilityId"/>
							<s:hidden name="inspectionId"/>
							<s:hidden name="finding.id" value="%{id}"/>
							<s:if test="underAppeal || !rescinded">
								<span class="ccl-right-float">
									<label for="appealDate-<s:property value='id'/>">Appeal Date:</label>
									<s:set var="appealDateFormatted" value=""/>
									<s:date id="appealDateFormatted" name="appealDate" format="MM/dd/yyyy"/>
									<s:textfield id="appealDate-%{id}" name="appealDate" value="%{appealDateFormatted}" cssClass="datepicker ccl-float-right ccl-appeal-date" maxlength="10"/>
								</span>
							</s:if>
							<s:if test="rescinded || !underAppeal">
								<span class="ccl-right-float">
									<label for="rescindedDate-<s:property value='id'/>">Rescinded Date:</label>
									<s:set var="rescindedDateFormatted" value=""/>
									<s:date id="rescindedDateFormatted" name="rescindedDate" format="MM/dd/yyyy"/>
									<s:textfield id="rescindedDate-%{id}" name="rescindedDate" value="%{rescindedDateFormatted}" cssClass="datepicker ccl-float-right ccl-rescind-date" maxlength="10"/>
								</span>
							</s:if>
							<s:set var="verTypes" value="getCorrectionVerificationTypes(#attr.find)"/>
							<s:if test="!#verTypes.isEmpty()">
								<span class="ccl-right-float">
									<label for="followUp-<s:property value='id'/>">Follow Up:</label>
									<s:select id="correctionVerification" name="verificationType" value="correctionVerification" list="verTypes" listKey="name()"
											  listValue="label" cssClass="required ccl-verification-type" tabindex="104"/>
								</span>
							</s:if>
						</s:form>
					</security:authorize>
				</div>
			</li>
		</s:iterator>
		<li><strong>Total CMP for this inspection:</strong> <s:text name="format.currency"><s:param name="value" value="totalCmpAmount"/></s:text></li>
	</ol>
</s:if>

<script type="text/javascript">
	// *** Short key ctrl-q for current date on fields that contains 'Date' in id attribute. ***
	$('[id *= "Date"]').keydown(function(e) {
        //CTRL + Q keydown for current date. Key codes: http://www.cambiaresearch.com/articles/15/javascript-char-codes-key-codes
        if(e.ctrlKey && e.keyCode == 81){
            var currentDate = new Date();
            $(this).val(currentDate.format("mm/dd/yyyy"));
        }
    });
</script>
