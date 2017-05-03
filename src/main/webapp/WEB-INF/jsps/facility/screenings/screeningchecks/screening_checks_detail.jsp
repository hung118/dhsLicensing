<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="display" uri="http://displaytag.sf.net"%>
<fieldset>
	<legend>Screening Checks</legend>
	<ol class="fieldList">
		<li><h1>Juvenile Check</h1></li>
		<li>
			<div class="label">Screening Results:</div>
			<div class="value"><s:property value="juvCheck.screeningResult.displayName"/></div>
		</li>
		<s:if test="juvCheck.screeningDate != null">
			<li>
				<div class="label">Screening Date:</div>
				<div class="value"><s:date name="juvCheck.screeningDate" format="MM/dd/yyyy"/></div>
			</li>
			<li>
				<div class="label">Processing Time:</div>
				<div class="value"><s:property value="juvCheck.processingTime"/> days</div>
			</li>
		</s:if>
		<li><h1>Rap Sheet Check</h1></li>
		<li>
			<div class="label">Screening Results:</div>
			<div class="value"><s:property value="rapCheck.screeningResult.displayName"/></div>
		</li>
		<s:if test="rapCheck.screeningDate != null">
			<li>
				<div class="label">Screening Date:</div>
				<div class="value"><s:date name="rapCheck.screeningDate" format="MM/dd/yyyy"/></div>
			</li>
			<li>
				<div class="label">Processing Time:</div>
				<div class="value"><s:property value="rapCheck.processingTime"/> days</div>
			</li>
		</s:if>
		<li><h1>Courts Check</h1></li>
		<li>
			<div class="label">Screening Results:</div>
			<div class="value"><s:property value="courtsCheck.screeningResult.displayName"/></div>
		</li>
		<s:if test="courtsCheck.screeningDate != null">
			<li>
				<div class="label">Screening Date:</div>
				<div class="value"><s:date name="courtsCheck.screeningDate" format="MM/dd/yyyy"/></div>
			</li>
			<li>
				<div class="label">Processing Time:</div>
				<div class="value"><s:property value="courtsCheck.processingTime"/> days</div>
			</li>
		</s:if>
		<li><h1>FBI Check</h1></li>
		<s:if test="multiStateOffender"><li class="redtext"><strong>This person is a multi-state offender.</strong></li></s:if>
		<s:if test="fbiCheck.fingerprintsReceivedDate != null">
			<li>
				<div class="label">Fingerprints Received:</div>
				<div class="value"><s:date name="fbiCheck.fingerprintsReceivedDate" format="MM/dd/yyyy"/></div>
			</li>
		</s:if>
		<s:if test="fbiCheck.submittedToFbiDate != null">
			<li>
				<div class="label">Submitted to FBI:</div>
				<div class="value"><s:date name="fbiCheck.submittedToFbiDate" format="MM/dd/yyyy"/></div>
			</li>
		</s:if>
		<s:if test="fbiCheck.fingerprintsRejectedOneDate != null || fbiCheck.fingerprintsResubmittedOneDate != null || fbiCheck.fingerprintsRejectedTwoDate || fbiCheck.fingerprintsResubmittedTwoDate != null">
			<li>
				<div class="label">Rejection:</div>
				<ol class="fieldGroup">
					<s:set var="numDates" value="0"/>
					<s:if test="fbiCheck.fingerprintsRejectedOneDate != null">
						<li>
							<div class="label">Fingerprints Rejected:</div>
							<div class="value"><s:date name="fbiCheck.fingerprintsRejectedOneDate" format="MM/dd/yyyy"/></div>
						</li>
						<s:set var="numDates" value="#attr.numDates + 1"/>
					</s:if>
					<s:if test="fbiCheck.fingerprintsResubmittedOneDate != null">
						<li>
							<div class="label">Fingerprints Resubmitted:</div>
							<div class="value"><s:date name="fbiCheck.fingerprintsResubmittedOneDate" format="MM/dd/yyyy"/></div>
						</li>
						<s:set var="numDates" value="#attr.numDates + 1"/>
					</s:if>
					<s:if test="fbiCheck.fingerprintsRejectedTwoDate != null">
						<li class="<s:if test='#attr.numDates == 2'>clearLeft fieldMargin</s:if>">
							<div class="label">Fingerprints Rejected:</div>
							<div class="value"><s:date name="fbiCheck.fingerprintsRejectedTwoDate" format="MM/dd/yyyy"/></div>
						</li>
						<s:set var="numDates" value="#attr.numDates + 1"/>
					</s:if>
					<s:if test="fbiCheck.fingerprintsResubmittedTwoDate != null">
						<li class="<s:if test='#attr.numDates == 2'>clearLeft</s:if><s:elseif test='#attr.numDates > 2'> fieldMargin</s:elseif>">
							<div class="label">Fingerprints Resubmitted:</div>
							<div class="value"><s:date name="fbiCheck.fingerprintsResubmittedTwoDate" format="MM/dd/yyyy"/></div>
						</li>
					</s:if>
				</ol>
			</li>
		</s:if>
		<s:if test="fbiCheck.nameSearchSubmittedDate != null">
			<li>
				<div class="label">Name Search Submitted:</div>
				<div class="value"><s:date name="fbiCheck.nameSearchSubmittedDate" format="MM/dd/yyyy"/></div>
			</li>
		</s:if>
		<s:if test="fbiCheck.returnedFromFbiDate != null">
			<li>
				<div class="label">Returned From FBI:</div>
				<div class="value"><s:date name="fbiCheck.returnedFromFbiDate" format="MM/dd/yyyy"/></div>
			</li>
		</s:if>
		<li>
			<div class="label">Screening Results:</div>
			<div class="value"><s:property value="fbiCheck.screeningResult.displayName"/></div>
		</li>
		<s:if test="fbiCheck.screeningDate != null">
			<li>
				<div class="label">Screening Date:</div>
				<div class="value"><s:date name="fbiCheck.screeningDate" format="MM/dd/yyyy"/></div>
			</li>
			<li>
				<div class="label">Processing Time:</div>
				<div class="value"><s:property value="fbiCheck.processingTime"/> days</div>
			</li>
		</s:if>
		<li><h1>DCFS Check</h1></li>
		<li>
			<div class="label">Screening Results:</div>
			<div class="value"><s:property value="dcfsCheck.screeningResult.displayName"/></div>
		</li>
		<s:if test="dcfsCheck.screeningDate != null">
			<li>
				<div class="label">Screening Date:</div>
				<div class="value"><s:date name="dcfsCheck.screeningDate" format="MM/dd/yyyy"/></div>
			</li>
			<li>
				<div class="label">Processing Time:</div>
				<div class="value"><s:property value="dcfsCheck.processingTime"/> days</div>
			</li>
		</s:if>
		<s:if test="dcfsCheck.dcfsEmailedDate != null">
			<li>
				<div class="label">Date DCFS Emailed:</div>
				<div class="value"><s:date name="dcfsCheck.dcfsEmailedDate" format="MM/dd/yyyy"/></div>
			</li>
		</s:if>
		<s:if test="!dcfsCases.empty">
			<li>
				<div class="label">DCFS Cases:</div>
				<ol id="dcfs-cases" class="fieldGroup">
					<s:iterator value="dcfsCases" status="row">
						<li<s:if test="!#row.first"> class="clearLeft fieldMargin"</s:if>>
							<div class="value">
							Case #: <s:property value="caseNumber"/> Start Date: <s:date name="startDate" format="MM/dd/yyyy"/>
							</div>
						</li>
					</s:iterator>
				</ol>
			</li>
		</s:if>
		<s:if test="@org.apache.commons.lang.StringUtils@isNotBlank(dcfsCheck.dcfsCaseNumber)">
			<li>
				<div class="label">DCFS Case #:</div>
				<div class="value"><s:property value="dcfsCheck.dcfsCaseNumber"/></div>
			</li>
		</s:if>
		<s:if test="dcfsCheck.dcfsStartDate != null">
			<li>
				<div class="label">DCFS Start Date:</div>
				<div class="value"><s:date name="dcfsCheck.dcfsStartDate" format="MM/dd/yyyy"/></div>
			</li>
		</s:if>
		<s:if test="dcfsCheck.dcfsConfirmationDate != null">
			<li>
				<div class="label">DCFS Confirmation Date:</div>
				<div class="value"><s:date name="dcfsCheck.dcfsConfirmationDate" format="MM/dd/yyyy"/></div>
			</li>
		</s:if>
		<li><h1>Convictions</h1></li>
		<s:if test="convictions.isEmpty">
			<li>There are no convictions for this person</li>
		</s:if>
		<s:else>
			<li>
				<display:table name="convictions" id="convictions" class="tables noMarginTop">
					<display:column title="Screening Check Type" headerClass="shrinkCol">
						<s:property value="#attr.convictions.checkTypeValue"/>
					</display:column>
					<display:column title="Conviction Date" headerClass="shrinkCol">
						<s:date name="#attr.convictions.date" format="MM/dd/yyyy"/>
					</display:column>
					<display:column title="Category">
						<s:property value="#attr.convictions.categoryValue"/>
					</display:column>
				</display:table>
			</li>
		</s:else>
		<s:set var="screening" value="screening" scope="request"/>
		<security:authorize access="hasPermission('save', 'screening')">
			<li class="submit">
				<s:url id="editSCUrl" action="edit-screening-checks">
					<s:param name="facilityId" value="facilityId"/>
					<s:param name="screeningId" value="screeningId"/>
				</s:url>
				<s:a href="%{editSCUrl}" cssClass="ccl-button ajaxify {target: '#screeningChecksSection'}">
					Edit
				</s:a>
			</li>
		</security:authorize>
	</ol>
</fieldset>