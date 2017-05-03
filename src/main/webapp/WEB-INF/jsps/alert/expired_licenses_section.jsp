<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="ccl" uri="/WEB-INF/tags/ccl-tags.tld"%>
<%@ taglib prefix="dts" uri="/WEB-INF/tags/dts-tags.tld"%>
<s:if test="!lstCtrl.results.isEmpty">
	<s:url var="printLabelsUrl" action="print-labels" escapeAmp="false">
		<s:param name="personId" value="user.person.id"/>
		<s:param name="showWholeRegion" value="showWholeRegion"/>
	</s:url>
	<fieldset>
		<legend><a name="<s:if test="showWholeRegion">reg_</s:if>exp_lic" class="quickLink">Expired or Expiring Licenses or Certificates</a></legend>
		<div class="ccl-list-ctrls clearfix">
			<div class="ccl-list-left-ctrls">
				<button type="button" class="mailingLabelsPrint {url: '<s:property value='%{printLabelsUrl}' escape='false'/>'}">Print Mailing Labels</button>
			</div>
			<dts:listcontrols id="expLicTopControls" name="lstCtrl" namespace="/alert/expired-licenses" action="list" useAjax="true" ajaxTarget="%{showWholeRegion ? '#regionExpiredLicensesSection' : '#expiredLicensesSection'}">
				<s:param name="personId" value="user.person.id"/>
				<s:param name="showWholeRegion" value="showWholeRegion"/>
			</dts:listcontrols>
		</div>
		<ol class="ccl-list">
			<s:iterator value="lstCtrl.results" status="row">
				<li class="ccl-list-item <s:if test="#row.odd">odd</s:if><s:else>even</s:else>">
					<div class="left-column">
					   <ccl:basicfacilitydetail name="facility"/>
					</div>
					<div class="right-column">
						<div><span class="label">Service Code:</span> <s:property value="serviceCode"/></div>
						<div><span class="label">Expiration Date:</span> <s:date name="licenseExpirationDate" format="MM/dd/yyyy"/></div>
						<div>
							<s:if test="applicationReceivedDate == null">Application not received</s:if>
							<s:else><s:property value="applicationReceivedAction.value"/> on <s:date name="applicationReceivedDate" format="MM/dd/yyyy"/></s:else>
						</div>
						<div><span class="label">Licensing Specialist:</span> <s:property value="facility.licensingSpecialist.firstAndLastName"/></div>
					</div>
				</li>
			</s:iterator>
		</ol>
		<div class="ccl-list-ctrls clearfix">
			<div class="mainControls">
				<button type="button" class="mailingLabelsPrint {url: '<s:property value='%{printLabelsUrl}' escape='false'/>'}">Print Mailing Labels</button>
			</div>
			<dts:listcontrols id="expLicBottomControls" name="lstCtrl" namespace="/alert/expired-licenses" action="list" useAjax="true" ajaxTarget="%{showWholeRegion ? '#regionExpiredLicensesSection' : '#expiredLicensesSection'}">
				<s:param name="personId" value="user.person.id"/>
				<s:param name="showWholeRegion" value="showWholeRegion"/>
			</dts:listcontrols>
		</div>
	</fieldset>
</s:if>
