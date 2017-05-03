<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="ccl" uri="/WEB-INF/tags/ccl-tags.tld"%>
<%@ taglib prefix="dts" uri="/WEB-INF/tags/dts-tags.tld"%>
<h1>Facility Contact Case Load</h1>
<fieldset>
	<legend><a name="facility_contact_home" class="quickLink">Facilities</a></legend>
	<div class="ccl-list-ctrls clearfix">
		<dts:listcontrols id="facilityContactHomeTopControls" name="lstCtrl" namespace="/home" action="facility-contact-home" useAjax="true" ajaxTarget="#facilityContactSection">
			<s:param name="personId" value="user.person.id"/>
		</dts:listcontrols>
	</div>
	<ol class="ccl-list">
<s:if test="!lstCtrl.results.isEmpty">
	<s:iterator value="lstCtrl.results" status="row">
		<li class="ccl-list-item <s:if test="#row.odd">odd</s:if><s:else>even</s:else>">
			<div class="facilityName">
				<s:url var="facilityEditLink" action="edit-facility" namespace="/facility">
					<s:param name="facilityId" value="id"/>
				</s:url>
				<s:a href="%{facilityEditLink}">
					<s:property value="facilityName"/>
				</s:a>
			</div>
			<div><s:property value="licenseType"/></div>
			<div><s:property value="primaryPhone.formattedPhoneNumber"/></div>
			<div><s:property value="locationAddress.addressOne"/></div>
			<s:if test="locationAddress.addressTwo != null">
				<div><s:property value="locationAddress.addressTwo"/></div>
			</s:if>
			<div>
				<s:property value="locationAddress.city"/>, <s:property value="locationAddress.state"/>
				<s:property value="locationAddress.zipCode"/>
			</div>
		</li>
	</s:iterator>
</s:if>
<s:else>
		<li class="ccl-list-item odd">You are currently not associated with any facilities.</li>
</s:else>
	</ol>
	<div class="ccl-list-ctrls clearfix">
		<dts:listcontrols id="facilityContactHomeBottomControls" name="lstCtrl" namespace="/home" action="facility-contact-home" useAjax="true" ajaxTarget="#facilityContactSection">
			<s:param name="personId" value="user.person.id"/>
		</dts:listcontrols>
	</div>
</fieldset>
