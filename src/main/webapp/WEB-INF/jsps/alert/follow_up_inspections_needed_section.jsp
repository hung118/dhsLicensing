<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="ccl" uri="/WEB-INF/tags/ccl-tags.tld"%>
<%@ taglib prefix="dts" uri="/WEB-INF/tags/dts-tags.tld"%>
<%@ taglib prefix="display" uri="http://displaytag.sf.net"%>
<s:if test="!lstCtrl.results.isEmpty">
	<fieldset>
		<legend><a name="foll_up_ins_needed-<s:property value="roleType"/>" class="quickLink"><span><s:if test="roleType.equals('COMPL')">Complaint </s:if>Follow-up Inspections Needed</span></a></legend>
		<div class="ccl-list-ctrls clearfix">
			<dts:listcontrols id="follUpInsNeedBottomControls-%{roleType}" name="lstCtrl" namespace="/alert/follow-up-inspections-needed" action="list" useAjax="true" ajaxTarget="#followUpInspectionsSection-%{roleType}">
				<s:param name="personId" value="user.person.id"/>
				<s:param name="roleType" value="roleType"/>
			</dts:listcontrols>
		</div>
		<ol class="ccl-list">
			<s:iterator value="lstCtrl.results" status="row">
				<li class="ccl-list-item <s:if test="#row.odd">odd</s:if><s:else>even</s:else><s:if test="alertType.name().equals('RED_ALERT')"> redAlert</s:if><s:elseif test="alertType.name().equals('ORANGE_ALERT')"> warningAlert</s:elseif>">
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
						<div><span class="label">Inspection Type:</span>
							<s:url id="editInspectionUrl" action="open-tab" namespace="/facility" includeParams="false">
								<s:param name="facilityId" value="facility.id"/>
								<s:param name="tab" value="%{'inspections'}"/>
								<s:param name="act" value="%{'edit-inspection-record'}"/>
								<s:param name="ns" value="%{'/inspections'}"/>
								<s:param name="inspectionId" value="inspectionId"/>
							</s:url>
							<s:a href="%{editInspectionUrl}">
								<strong><s:property value="primaryType"/></strong><s:if test="otherTypes != null">, <s:property value="otherTypes"/></s:if>
								<s:iterator value="inspectionTypes" status="row">
									<s:if test="#row.index == 0"><strong><s:property/></strong></s:if><s:else>, <s:property/></s:else>
								</s:iterator>
							</s:a>
						</div>
						<div><span class="label">Inspection Date:</span> <s:date name="inspectionDate" format="MM/dd/yyyy"/>
						</div>
						<div><span class="label">Findings Needing Follow-ups:</span>
							<display:table name="findings" id="follAlerts" class="tables">
								<display:column title="Rule">
									<ccl:rule ruleId="#attr.follAlerts.rule.id" format="RNUM"/>
								</display:column>
								<display:column title="Correction Date">
									<s:date name="#attr.follAlerts.correctionDate" format="MM/dd/yyyy"/>
								</display:column>
							</display:table>
						</div>
					</div>
				</li>
			</s:iterator>
		</ol>
		<div class="ccl-list-ctrls clearfix">
			<dts:listcontrols id="follUpInsNeedBottomControls-%{roleType}" name="lstCtrl" namespace="/alert/follow-up-inspections-needed" action="list" useAjax="true" ajaxTarget="#followUpInspectionsSection-%{roleType}">
				<s:param name="personId" value="user.person.id"/>
				<s:param name="roleType" value="roleType"/>
			</dts:listcontrols>
		</div>
	</fieldset>
</s:if>