<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<style>
<!--
caption {
	font-weight:bold;
	text-align:left;
}
table th {
	background-color:#EEEEEE;
	font-weight:bold;
}
-->
</style>
<s:if test="refreshFindings">
	<script type="text/javascript">
		refreshFindings(<s:property value="inspectionId"/>);
	</script>
</s:if>
<s:set var="inspection" value="inspection" scope="request"/>
<fieldset id="ins-dtl-fieldset">
	<legend>Inspection</legend>
	<security:authorize access="hasAnyRole('ROLE_SUPER_ADMIN')">
		<div class="ccl-fs-ctrls">
			<ul>
				<li><span class="label">Created By:</span> <s:property value="inspection.createdBy.firstAndLastName"/></li>
				<li><span class="label">Creation Date:</span> <s:date name="inspection.creationDate" format="MM/dd/yyyy hh:mm a"/></li>
				<li><span class="label">Last Modified By:</span> <s:property value="inspection.modifiedBy.firstAndLastName"/></li>
				<li><span class="label">Last Modified Date:</span> <s:date name="inspection.modifiedDate" format="MM/dd/yyyy hh:mm a"/></li>
			</ul>
			<s:url id="insHistUrl" action="object-history" namespace="/facility">
				<s:param name="objectId" value="inspectionId"/>
			</s:url>
			<s:a id="ccl-ins-hist" href="%{insHistUrl}" cssClass="ccl-so-hist">
				History
			</s:a>
		</div>
	</security:authorize>
	<s:actionerror/>
	<span class="ccl-error-container"></span>
	<ol class="fieldList">
		<li>
			<div class="label">Date:</div>
			<div class="value"><s:date name="inspection.inspectionDate" format="MM/dd/yyyy"/></div>
		</li>
		<li>
			<div class="label">Types:</div>
			<div class="value">
				 <strong><s:property value="inspection.primaryInspectionType.value"/></strong><s:if test="!inspection.nonPrimaryInspectionTypes.isEmpty">,</s:if>
				<s:iterator value="inspection.nonPrimaryInspectionTypes" id="type" status="row">
					<s:property value="value"/><s:if test="!#row.last">,</s:if>
				</s:iterator>
			</div>
		</li>
		<li>
			<div class="label">License:</div>
			<div class="value"><s:property value="inspection.license.licenseListDescriptor"/></div>
		</li>
		<li>
			<div class="label">Specialists:</div>
			<ol class="fieldGroup">
				<li>
					<div class="label">Primary:</div>
					<div class="value"><s:property value="inspection.primarySpecialist.firstAndLastName"/></div>
				</li>
				<s:if test="inspection.secondSpecialist != null">
					<li>
						<div class="label">Second:</div>
						<div class="value"><s:property value="inspection.secondSpecialist.firstAndLastName"/></div>
					</li>
				</s:if>
				<s:if test="inspection.thirdSpecialist != null">
					<li>
						<div class="label">Third:</div>
						<div class="value"><s:property value="inspection.thirdSpecialist.firstAndLastName"/></div>
					</li>
				</s:if>
			</ol>
		</li>
		<!-- 
		<li>
			<div class="label">Duration:</div>
			<ol class="fieldGroup">
				<li>
					<div class="label">Arrival Time:</div>
					<div class="value"><s:date name="inspection.arrivalTime" format="hh:mm a"/></div>
				</li>
				<li>
					<div class="label">Departure Time:</div>
					<div class="value"><s:date name="inspection.departureTime" format="hh:mm a"/></div>
				</li>
			</ol>
		</li>
		 -->
		<s:if test="inspection.findingsComment != null">
			<li>
				<div class="label">Findings:</div>
				<div class="value"><s:property value="inspection.findingsComment"/></div>
			</li>
		</s:if>
		<s:if test="inspection.followupComment != null">
			<li>
				<div class="label">Follow Up:</div>
				<div class="value"><s:property value="inspection.followupComment"/></div>
			</li>
		</s:if>
		<s:if test="inspection.followUpRequired">
			<li>
				<div class="label">&nbsp;</div>
				<div class="value redtext labelWidthMargin"><strong>A follow up is required</strong></div>
			</li>
		</s:if>
		<s:if test="inspection.cmpDueDateRequired and (inspection.state.name() == 'APPROVAL' or inspection.state.name() == 'FINALIZED')">
			<li>
				<div class="label">CMP:</div>
				<ol class="fieldGroup">
					<s:if test="inspection.cmpDueDate == null">
						<li>
							<div class="redtext"><strong>CMP due date is required.</strong></div>
						</li>
					</s:if>
					<security:authorize access="hasPermission('set-cmp-due-date', 'inspection')">
						<li<s:if test="inspection.cmpDueDate == null"> class='clearLeft fieldMargin'</s:if>>
							<s:form id="cmpDueDateForm" action="set-cmp-due-date" method="get" cssClass="ajaxify {target: '#inspectionSection'} ccl-action-save">
								<s:hidden name="facilityId"/>
								<s:hidden name="inspectionId"/>
								<ol class="formList">
									<li>
										<label for="cmpDueDate">CMP Due Date:</label>
										<s:date id="cmpDueDateFormatted" name="inspection.cmpDueDate" format="MM/dd/yyyy"/>
										<s:textfield id="cmpDueDate" name="inspection.cmpDueDate" value="%{cmpDueDateFormatted}" cssClass="required datepicker" maxlength="10"/>
									</li>
								</ol>
							</s:form>
							<script type="text/javascript">
								$("#cmpDueDate").change(function() {
									$("#cmpDueDateForm").submit();
								});
							</script>
						</li>
					</security:authorize>
					<security:authorize access="!hasPermission('set-cmp-due-date', 'inspection')">
						<li>
							<div class="label">CMP Due Date:</div>
							<div class="value"><s:date name="inspection.cmpDueDate" format="MM/dd/yyyy"/></div>
						</li>
					</security:authorize>
				</ol>
			</li>
		</s:if>
		<li class="submit">
			<security:authorize access="hasPermission('save-entry', 'inspection')">
				<s:url id="editInsUrl" action="edit-inspection">
					<s:param name="facilityId" value="facilityId"/>
					<s:param name="inspectionId" value="inspectionId"/>
				</s:url>
				<s:a href="%{editInsUrl}" cssClass="ccl-button ajaxify {target: '#inspectionSection'}">
					Edit
				</s:a>
			</security:authorize>
			<security:authorize access="hasPermission('complete-entry', 'inspection')">
				<!-- 
				<s:url id="completeEntryUrl" action="complete-entry">
					<s:param name="facilityId" value="facilityId"/>
					<s:param name="inspectionId" value="inspectionId"/>
				</s:url>
				<s:a id="ins-complete-entry" href="%{completeEntryUrl}" cssClass="ccl-button ccl-action-save ccl-state-change {title: 'Entry Complete', onSetup: function() {inspectionApproverCallback();}, onSuccess: function() {refreshEntireInspection();}}">
					Entry Complete
				</s:a>
				-->
			</security:authorize>
			<security:authorize access="hasPermission('reject-entry', 'inspection')">
				<s:url id="rejectEntryUrl" action="reject-entry">
					<s:param name="facilityId" value="facilityId"/>
					<s:param name="inspectionId" value="inspectionId"/>
				</s:url>
				<s:a id="ins-reject" href="%{rejectEntryUrl}" cssClass="ccl-button ccl-action-save ccl-state-change {title: 'Correction Required', onSuccess: function() {refreshEntireInspection();}}">
					Correction Required
				</s:a>
			</security:authorize>
			<security:authorize access="hasPermission('finalize', 'inspection')">
				<s:url id="finInsUrl" action="finalize">
					<s:param name="facilityId" value="facilityId"/>
					<s:param name="inspectionId" value="inspectionId"/>
				</s:url>
				<s:a id="ins-finalize" href="%{finInsUrl}" cssClass="ccl-button ccl-action-save ccl-state-change {title: 'Finalize', onSuccess: function() {refreshEntireInspection();}}">
					Finalize
				</s:a>
			</security:authorize>
			<security:authorize access="hasPermission('unfinalize', 'inspection')">
				<s:url id="unfinInsUrl" action="unfinalize">
					<s:param name="facilityId" value="facilityId"/>
					<s:param name="inspectionId" value="inspectionId"/>
				</s:url>
				<s:a id="ins-unfinalize" href="%{unfinInsUrl}" cssClass="ccl-button ccl-action-save ccl-state-change {title: 'Unfinalize',onSuccess: function() {refreshEntireInspection();}}">
					Unfinalize
				</s:a>
			</security:authorize>

		</li>
	</ol>
</fieldset>

<fieldset id="ins-dtl-fieldset">
<legend>Inspection Check Lists</legend>
	<div id="inspectionCheckListSection">
		<ol>
			<li>
				<security:authorize access="hasPermission('save-entry', 'inspection')">
					<s:url id="insChecklistUrl" action="inspection-checklist-sections">
						<s:param name="facilityId" value="facilityId"/>
						<s:param name="inspectionId" value="inspectionId"/>
					</s:url>
					<s:a href="%{insChecklistUrl}" cssClass="ccl-button ajaxify {target: '#inspectionsBase'}">
						Start Checklist
					</s:a>
				</security:authorize>
			</li>
			<li>
				<br>
				<s:if test="inspection.inspectionChecklist != null && inspection.inspectionChecklist.size > 0">
					<table>
						<thead>
							<th>&nbsp;</th>
							<th>Date</th>
							<th>By</th>
							<th title="Non-Compliant Count">NC</th>
							<th>&nbsp;</th>
						</thead>
						<s:iterator value="inspection.inspectionChecklist" var="checklist" status="idx" >
							<tr>
								<td>
									<s:property value="%{#idx.index + 1}" />.
								</td>
								<td>
									<s:date id="checklistDateFormatted" name="#checklist.resultDate" format="MM/dd/yyyy"/>
									<s:property value="%{checklistDateFormatted}" />
								</td>
								<td>
									<s:property value="#checklist.user.username" />
								</td>
								<td align="center">
									<s:if test="#checklist.nonCompliantCount > 0">
										<div style="background-color:red"><s:property value="#checklist.nonCompliantCount" /></div>
									</s:if>
									<s:else >
										<s:property value="#checklist.nonCompliantCount" />
									</s:else>
								</td>
								<td width="110">
									<s:url id="insChkListViewUrl" action="inspection-checklist-view">
										<s:param name="objectId" value="inspectionId"/>
										<s:param name="checklistId" value="#checklist.id"/>
									</s:url>
									<s:a href="%{insChkListViewUrl}" cssClass="ajaxify {target: '#inspectionsBase'}" title="View & Edit">
										view
									</s:a>
									|
									<s:url id="insChkListPrintUrl" action="inspection-checklist-print">
										<s:param name="objectId" value="inspectionId"/>
										<s:param name="checklistId" value="#checklist.id"/>
									</s:url>
									<s:a href="%{insChkListPrintUrl}" cssClass="" target="_blank">print</s:a>
									|
									<s:url id="insChkListDelUrl" action="inspection-checklist-delete">
										<s:param name="objectId" value="inspectionId"/>
										<s:param name="checklistId" value="#checklist.id"/>
									</s:url>
									<s:a href="%{insChkListDelUrl}" cssClass="ajaxify {target: '#inspectionsBase'}" title="Delete Checklist">
										delete
									</s:a>
								</td>
							</tr>
						</s:iterator>
					</table>
				</s:if>		
			</li>
		</ol>

</fieldset>

<script type="text/javascript">
	// *** Short key ctrl-q for current date on fields that ends with 'Date' in id attribute. ***
	$('[id $= "Date"]').keydown(function(e) {
        //CTRL + Q keydown for current date. Key codes: http://www.cambiaresearch.com/articles/15/javascript-char-codes-key-codes
        if(e.ctrlKey && e.keyCode == 81){
            var currentDate = new Date();
            $(this).val(currentDate.format("mm/dd/yyyy"));
        }
    });
</script>
