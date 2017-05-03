<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<fieldset>
	<legend>Resolution of Denial</legend>
	<ol class="fieldList">
		<s:url id="editResUrl" action="edit-resolution-of-denial">
			<s:param name="facilityId" value="facilityId"/>
			<s:param name="screeningId" value="screeningId"/>
		</s:url>
		<s:if test="screening.resolution != null">
			<li>
				<div class="label">Resolution:</div>
				<div class="value"><s:property value="screening.resolution.value"/></div>
			</li>
			<li>
				<div class="label">Resolution Date:</div>
				<div class="value"><s:date name="screening.resolutionDate" format="MM/dd/yyyy"/></div>
			</li>
			<s:if test="screening.appealRequestDate != null">
				<li>
					<div class="label">Appeal:</div>
					<ol class="fieldGroup">
						<li>
							<div class="label">Appeal Request Date:</div>
							<div class="value"><s:date name="screening.appealRequestDate" format="MM/dd/yyyy"/></div>
						</li>
						<li>
							<div class="label">Appeal Outcome:</div>
							<div class="value">
								<s:if test="screening.appealOutcomeValue != null">
									<s:property value="screening.appealOutcomeValue"/>
								</s:if>
								<s:else>
									This appeal has not been resolved
								</s:else>
							</div>
						</li>
					</ol>
				</li>
			</s:if>
			<s:if test="screening.attestationSentDate != null || screening.attestationDueDate != null || screening.attestationReceivedDate != null">
				<li>
					<div class="label">Attestations:</div>
					<ol class="fieldGroup">
						<s:set var="numDates" value="0"/>
						<s:if test="screening.attestationSentDate != null">
							<li>
								<div class="label">Attestation Sent Date:</div>
								<div class="value"><s:date name="screening.attestationSentDate" format="MM/dd/yyyy"/></div>
							</li>
							<s:set var="numDates" value="#attr.numDates + 1"/>
						</s:if>
						<s:if test="screening.attestationDueDate != null">
							<li>
								<div class="label">Attestation Due Date:</div>
								<div class="value"><s:date name="screening.attestationDueDate" format="MM/dd/yyyy"/></div>
							</li>
							<s:set var="numDates" value="#attr.numDates + 1"/>
						</s:if>
						<s:if test="screening.attestationReceivedDate != null">
							<li class="<s:if test='#attr.numDates == 2'>clearLeft fieldMargin</s:if>">
								<div class="label">Attestation Received Date:</div>
								<div class="value"><s:date name="screening.attestationReceivedDate" format="MM/dd/yyyy"/></div>
							</li>
						</s:if>
					</ol>
				</li>
			</s:if>
			<li class="submit">
				<s:a href="%{editResUrl}" cssClass="ccl-button ajaxify {target: '#resolutionOfDenialSection'}">
					Edit
				</s:a>
			</li>
		</s:if>
		<s:elseif test="screening.denied">
			<li class="submit">
				<s:a href="%{editResUrl}" cssClass="ccl-button ajaxify {target: '#resolutionOfDenialSection'}">
					Enter Resolution of Denial
				</s:a>
			</li>
		</s:elseif>
	</ol>
</fieldset>