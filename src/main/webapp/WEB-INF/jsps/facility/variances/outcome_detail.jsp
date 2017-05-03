<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<fieldset>
	<legend>Variance Review Outcome</legend>
	<s:if test="variance.reviewDate == null">
		<ol class="fieldList">
			<li>
				<div class="value">This variance has not been reviewed by a licensing director.</div>
			</li>
		</ol>
	</s:if>
	<s:else>
		<security:authorize access="isAuthenticated() and principal.isInternal()">
			<div class="ccl-fs-ctrls">
				<ul>
					<li><span class="label">Last Modified By:</span> <s:property value="variance.directorModifiedBy.firstAndLastName"/></li>
					<li><span class="label">Last Modified Date:</span> <s:date name="variance.directorModifiedDate" format="MM/dd/yyyy hh:mm a"/></li>
					<s:if test="variance.revocationDate != null">
						<li><span class="label">Revocation By:</span> <s:property value="variance.revokeModifiedBy.firstAndLastName"/> (<s:property value="variance.revokeModifiedById"/>)</li>
						<li><span class="label">Revocation Date:</span> <s:date name="variance.revokeModifiedDate" format="MM/dd/yyyy hh:mm a"/></li>
					</s:if>
				</ul>
			</div>
		</security:authorize>
		<ol class="fieldList">
			<li>
				<div class="label">Review Date:</div>
				<div class="value"><s:date name="variance.reviewDate" format="MM/dd/yyyy"/></div>
			</li>
			<li>
				<div class="label">Outcome:</div>
				<div class="value"><s:property value="variance.outcome.displayName"/></div>
			</li>
			<s:if test="variance.outcome.name().equals('APPROVED')">
				<li>
					<div class="label">Approved Duration:</div>
					<ol class="fieldGroup">
						<li>
							<div class="label">Start Date:</div>
							<div class="value"><s:date name="variance.startDate" format="MM/dd/yyyy"/></div>
						</li>
						<li>
							<div class="label">End Date:</div>
							<div class="value"><s:date name="variance.endDate" format="MM/dd/yyyy"/></div>
						</li>
					</ol>
				</li>
			</s:if>
			<li>
				<div class="label">Response:</div>
				<div class="value" style="width:550px;"><s:property value="variance.directorResponse"/></div>
			</li>
			<li>
				<div class="label">Outcome Finalized?</div>
				<div class="value"><s:property value="@gov.utah.dts.det.ccl.view.YesNoChoice@valueOfBoolean(variance.finalized).displayName"/></div>
			</li>
			<s:if test="variance.isFinalized() && variance.outcome.name().equals('APPROVED') && (variance.revocationDate != null || variance.revokeReason != null)">
				<li class="clearLeft fieldMargin">
					<div class="label">Revocation Date:</div>
					<div class="value"><s:date name="variance.revocationDate" format="MM/dd/yyyy"/></div>
				</li>
				<li>
					<div class="label">Revocation Reason:</div>
					<div class="value" style="width:550px;"><s:property value="variance.revokeReason"/></div>
				</li>
				<li>
					<div class="label">Revocation Finalized:</div>
					<div class="value"><s:property value="@gov.utah.dts.det.ccl.view.YesNoChoice@valueOfBoolean(variance.revokeFinalized).displayName"/></div>
				</li>
			</s:if>
		</ol>
	</s:else>
	<security:authorize access="hasRole('ROLE_LICENSING_DIRECTOR')">
		<s:if test="!variance.isFinalized()">
			<ol class="fieldList">
				<li class="submit">
					<s:url id="editOutUrl" action="edit-outcome">
						<s:param name="facilityId" value="facilityId"/>
						<s:param name="varianceId" value="varianceId"/>
					</s:url>
					<s:a href="%{editOutUrl}" cssClass="ajaxify {target: '#outcomeSection'} ccl-button">Edit</s:a>
				</li>
			</ol>
		</s:if>
        <s:elseif test="variance.isRevokeFinalized()">
            <ol class="fieldList">
                <li class="submit">
                    <s:url id="printRevokeUrl" action="print-revoke">
                        <s:param name="facilityId" value="facilityId"/>
                        <s:param name="varianceId" value="varianceId"/>
                    </s:url>
                    <s:a href="%{printRevokeUrl}" cssClass="ccl-button" target="_blank">Print Revocation Document</s:a>
                </li>
            </ol>
        </s:elseif>
		<s:else>
            <ol class="fieldList">
                <li class="submit">
                    <s:url id="printOutcomeUrl" action="print-outcome">
                       <s:param name="facilityId" value="facilityId"/>
                       <s:param name="varianceId" value="varianceId"/>
                    </s:url>
                    <s:a href="%{printOutcomeUrl}" cssClass="ccl-button" target="_blank">Print Outcome Document</s:a>
		    <s:if test="variance.isRevocable()">
				    <s:url id="editRevokeUrl" action="edit-revoke">
						<s:param name="facilityId" value="facilityId"/>
						<s:param name="varianceId" value="varianceId"/>
					</s:url>
					<s:a href="%{editRevokeUrl}" cssClass="ajaxify {target: '#outcomeSection'} ccl-button">Revoke Variance</s:a>
		    </s:if>
				</li>
			</ol>
		</s:else>
	</security:authorize>
</fieldset>