<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="ccl" uri="/WEB-INF/tags/ccl-tags.tld"%>
<%@ taglib prefix="dts" uri="/WEB-INF/tags/dts-tags.tld"%>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<s:if test="!lstCtrl.results.isEmpty">
	<fieldset>
		<legend><a name="complaints_in_progress" class="quickLink"><span>Facility Complaints in Progress</span></a></legend>
		<div class="ccl-list-ctrls clearfix">
			<dts:listcontrols id="complInProgressTopControls-%{roleType}" name="lstCtrl" namespace="/alert/complaints-in-progress" action="list" useAjax="true" ajaxTarget="#complaintsInProgressAlertSection-%{roleType}">
				<s:param name="personId" value="user.person.id"/>
				<s:param name="roleType" value="roleType"/>
			</dts:listcontrols>
		</div>
		<ol class="ccl-list">
			<s:iterator value="lstCtrl.results" status="row">
				<li class="ccl-list-item <s:if test="#row.odd">odd</s:if><s:else>even</s:else>">
					<div class="left-column">
						<div class="facilityName">
							<s:url var="facilityEditLink" action="edit-facility" namespace="/facility">
								<s:param name="facilityId" value="facility.id"/>
							</s:url>
							<s:a href="%{facilityEditLink}">
								<s:property value="facility.name"/>
							</s:a>
						</div>
						<div><s:property value="facility.licenseType.value"/></div>
						<div><s:property value="facility.primaryPhone.formattedPhoneNumber"/></div>
						<div><s:property value="facility.locationAddress.addressOne"/></div>
						<s:if test="facility.locationAddress.addressTwo != null">
							<div><s:property value="facility.locationAddress.addressTwo"/></div>
						</s:if>
						<div>
							<s:property value="facility.locationAddress.city"/>, <s:property value="facility.locationAddress.state"/> <s:property value="facility.address.zipCode"/>
						</div>
					</div>
					<div class="right-column">
						<div><span class="label">Date Received:</span>
							<s:url id="editComplaintUrl" action="open-tab" namespace="/facility" includeParams="false">
								<s:param name="facilityId" value="facility.id"/>
								<s:param name="tab" value="%{'complaints'}"/>
								<s:param name="act" value="%{'tab'}"/>
								<s:param name="ns" value="%{'/complaints/intake'}"/>
								<s:param name="complaintId" value="complaintId"/>
							</s:url>
							<s:a href="%{editComplaintUrl}">
								<s:date name="dateReceived" format="MM/dd/yyyy"/>
							</s:a>
						</div>
						<div><span class="label">Last Action:</span> <s:elseif test="lastStateChangeType.name() == 'CREATED'">Complaint Created</s:elseif><s:if test="lastStateChangeType.name() == 'INTAKE_COMPLETED'">Intake Completed</s:if><s:elseif test="lastStateChangeType.name() == 'FINALIZED'">Finalized</s:elseif> on <s:date name="lastStateChangeDate" format="MM/dd/yyyy"/></div>
						<div><span class="label">Status:</span> <s:if test="state.name() == 'INTAKE'">Processing Intake</s:if><s:elseif test="state.name() == 'FINALIZED'">Finalized</s:elseif></div>
						<s:if test="lastStateChangeNote != null">
							<div><span class="label">Note:</span> <s:property value="lastStateChangeNote"/></div>
						</s:if>
					</div>
				</li>
			</s:iterator>
		</ol>
		<div class="ccl-list-ctrls clearfix">
			<dts:listcontrols id="complInProgressBottomControls-%{roleType}" name="lstCtrl" namespace="/alert/complaints-in-progress" action="list" useAjax="true" ajaxTarget="#complaintsInProgressAlertSection-%{roleType}">
				<s:param name="personId" value="user.person.id"/>
				<s:param name="roleType" value="roleType"/>
			</dts:listcontrols>
		</div>
	</fieldset>
</s:if>
