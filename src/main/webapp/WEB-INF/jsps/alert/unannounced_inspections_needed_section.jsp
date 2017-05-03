<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="ccl" uri="/WEB-INF/tags/ccl-tags.tld"%>
<%@ taglib prefix="dts" uri="/WEB-INF/tags/dts-tags.tld"%>
<s:if test="!lstCtrl.results.isEmpty">
	<s:url var="printLabelsUrl" action="print-labels" escapeAmp="false">
		<s:param name="personId" value="user.person.id"/>
	</s:url>
	<fieldset>
		<legend><a name="annual_unann_ins_needed" class="quickLink"><span>Annual Unannounced Inspections Needed</span></a></legend>
		<div class="ccl-list-ctrls clearfix">
			<div class="ccl-list-left-ctrls">
				<button type="button" class="mailingLabelsPrint {url: '<s:property value='%{printLabelsUrl}' escape='false'/>'}">Print Mailing Labels</button>
			</div>
			<dts:listcontrols id="unannInsNeedTopControls" name="lstCtrl" namespace="/alert/unannounced-inspections-needed" action="list" useAjax="true" ajaxTarget="#unannouncedInspectionsSection">
				<s:param name="personId" value="user.person.id"/>
			</dts:listcontrols>
		</div>
		<ol class="ccl-list">
			<s:iterator value="lstCtrl.results" status="row">
				<li class="ccl-list-item <s:if test="#row.odd">odd</s:if><s:else>even</s:else><s:if test="alertType.name().equals('RED_ALERT')"> redAlert</s:if><s:elseif test="alertType.name().equals('ORANGE_ALERT')"> warningAlert</s:elseif>">
					<div class="left-column">
						<ccl:facilitydetail name="facility"/>
					</div>
					<div class="right-column">
						<div><span class="label">Expiration Date:</span> <s:date name="facility.licenseExpirationDate" format="MM/dd/yyyy"/></div>
						<div><span class="label">Total # Children:</span> <s:property value="facility.totalSlots"/></div>
						<div><span class="label">Last Announced Inspection:</span> <s:date name="lastAnnouncedInspectionDate" format="MM/dd/yyyy"/></div>
						<div><span class="label">Last Unannounced Inspection:</span> <s:date name="lastUnannouncedInspectionDate" format="MM/dd/yyyy"/></div>
					</div>
				</li>
			</s:iterator>
		</ol>
		<div class="ccl-list-ctrls clearfix">
			<div class="ccl-list-left-ctrls">
				<button type="button" class="mailingLabelsPrint {url: '<s:property value='%{printLabelsUrl}' escape='false'/>'}">Print Mailing Labels</button>
			</div>
			<dts:listcontrols id="unannInsNeedBottomControls" name="lstCtrl" namespace="/alert/unannounced-inspections-needed" action="list" useAjax="true" ajaxTarget="#unannouncedInspectionsSection">
				<s:param name="personId" value="user.person.id"/>
			</dts:listcontrols>
		</div>
	</fieldset>
</s:if>