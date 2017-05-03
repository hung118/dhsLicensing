<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="ccl" uri="/WEB-INF/tags/ccl-tags.tld"%>
<%@ taglib prefix="dts" uri="/WEB-INF/tags/dts-tags.tld"%>
<s:if test="!lstCtrl.results.isEmpty">
	<fieldset>
		<legend><a name="work_in_progress" class="quickLink">Work In Progress</a></legend>
		<div class="ccl-list-ctrls clearfix">
			<dts:listcontrols id="workInProgressTopControls" name="lstCtrl" namespace="/home/alerts" action="work-in-progress" useAjax="true" ajaxTarget="#workInProgressSection">
				<s:param name="personId" value="user.person.id"/>
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
							<s:property value="facility.locationAddress.city"/>, <s:property value="facility.locationAddress.state"/>
							<s:property value="facility.locationAddress.zipCode"/>
						</div>
					</div>
					<div class="right-column">
						<s:if test="objectType eq 'INSPECTION'">
							<div><span class="label">Type:</span> Inspection</div>
							<div><span class="label">Inspection Date:</span>
								<s:url id="editInspectionUrl" action="open-tab" namespace="/facility" includeParams="false">
									<s:param name="facilityId" value="facility.id"/>
									<s:param name="tab" value="%{'inspections'}"/>
									<s:param name="act" value="%{'edit-inspection-record'}"/>
									<s:param name="ns" value="%{'/inspections'}"/>
									<s:param name="inspectionId" value="objectId"/>
								</s:url>
								<s:a href="%{editInspectionUrl}">
									<s:date name="objectDate" format="MM/dd/yyyy"/>
								</s:a>
							</div>
							<div><span class="label">Inspection Type(s):</span>
								<s:iterator value="getInspectionTypeIterator(metadata.getValue('inspectionTypes'))" status="row"><s:if test="#row.first"><strong><s:property/></strong></s:if><s:else>, <s:property/></s:else></s:iterator>
							</div>
							<s:if test="metadata.getValue('note') != null"><div><span class="label">Note:</span> <s:property value="metadata.getValue('note')"/></div></s:if>
						</s:if>
						<s:elseif test="objectType eq 'INCIDENT'">
							<div><span class="label">Type:</span> Incident/Injury</div>
							<div><span class="label">Incident/Injury Date:</span>
								<s:url id="editIncidentUrl" action="open-tab" namespace="/facility" includeParams="false">
									<s:param name="facilityId" value="facility.id"/>
									<s:param name="tab" value="%{'incidents'}"/>
									<s:param name="act" value="%{'edit-incident'}"/>
									<s:param name="ns" value="%{'/incidents'}"/>
									<s:param name="incidentId" value="objectId"/>
								</s:url>
								<s:a href="%{editIncidentUrl}">
									<s:date name="objectDate" format="MM/dd/yyyy"/>
								</s:a>
							</div>
							<s:if test="metadata.getValue('note') != null"><div><span class="label">Note:</span> <s:property value="metadata.getValue('note')"/></div></s:if>
						</s:elseif>
						<s:elseif test="objectType eq 'COMPLAINT'">
							<div><span class="label">Type:</span> Complaint</div>
							<div><span class="label">Date Received:</span>
								<s:url id="editComplaintUrl" action="open-tab" namespace="/facility" includeParams="false">
									<s:param name="facilityId" value="facility.id"/>
									<s:param name="tab" value="%{'complaints'}"/>
									<s:param name="act" value="%{'tab'}"/>
									<s:param name="ns" value="%{'/complaints/intake'}"/>
									<s:param name="complaintId" value="objectId"/>
								</s:url>
								<s:a href="%{editComplaintUrl}">
									<s:date name="objectDate" format="MM/dd/yyyy"/>
								</s:a>
							</div>
							<s:if test="metadata.getValue('note') != null"><div><span class="label">Note:</span> <s:property value="metadata.getValue('note')"/></div></s:if>
						</s:elseif>
					</div>
				</li>
			</s:iterator>
		</ol>
		<div class="ccl-list-ctrls clearfix">
			<dts:listcontrols id="workInProgressBottomControls" name="lstCtrl" namespace="/home/alerts" action="work-in-progress" useAjax="true" ajaxTarget="#workInProgressSection">
				<s:param name="personId" value="user.person.id"/>
			</dts:listcontrols>
		</div>
	</fieldset>
</s:if>