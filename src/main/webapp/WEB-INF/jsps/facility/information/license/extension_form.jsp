<%@ taglib prefix="s" uri="/struts-tags"%>
<s:set var="lTypeLabel">License</s:set>
<s:set var="lPersLabel">Licensee</s:set>
<s:set var="cTypeLabel">Certificate</s:set>
<s:set var="cPersLabel">Certificate Holder</s:set>
<s:set var="typeLabel"><s:property value="lTypeLabel"/></s:set>
<s:set var="personLabel"><s:property value="lPersLabel"/></s:set>
<s:if test="certType">
	<s:set var="typeLabel"><s:property value="cTypeLabel"/></s:set>
	<s:set var="personLabel"><s:property value="cPersLabel"/></s:set>
</s:if>
<fieldset>
	<legend>Extend <span class="typeLabel"><s:property value="%{typeLabel}"/></span></legend>
	<ol class="fieldList">
		<li>
			<div class="label">Type:</div>
			<ol class="fieldGroup">
				<li>
					<div class="label">License Type:</div>
					<div class="value"><s:property value="license.type.value"/></div>
				</li>
				<li class="clearLeft fieldMargin">
					<div class="label">License Subtype:</div>
					<div class="value"><s:property value="license.subtype.value"/></div>
				</li>
			</ol>
		</li>
		<li>
			<div class="label">Children:</div>
			<ol class="fieldGroup">
				<li>
					<div class="label">Total:</div>
					<div class="value"><s:property value="license.totalSlots"/></div>
				</li>
				<li>
					<div class="label">Under Age 2:</div>
					<div class="value"><s:property value="license.ageTwoSlots"/></div>
				</li>
			</ol>
		</li>
		<li>
			<div class="label"><s:property value="%{personLabel}"/>:</div>
			<div class="value"><s:property value="license.licenseHolderName"/></div>
		</li>
		<li>
			<div class="label"><s:property value="%{typeLabel}"/>:</div>
			<ol class="fieldGroup">
				<li>
					<div class="label">Start Date:</div>
					<div class="value"><s:date name="license.startDate" format="MM/dd/yyyy"/></div>
				</li>
				<li>
					<div class="label">Expiration Date:</div>
					<div class="value"><s:date name="license.expirationDate" format="MM/dd/yyyy"/></div>
				</li>
			</ol>
		</li>
	</ol>
	<s:actionerror/>
	<s:fielderror/>
	<s:form id="facilityLicenseExtensionForm" action="save-extension" method="post" cssClass="ajaxify {target: '#licensesSection'} ccl-action-save">
		<s:hidden name="facilityId"/>
		<s:hidden name="licenseId"/>
		<ol class="fieldList">
			<li>
				<label for="extensionExpirationDate"><span class="redtext">* </span>Extension Expiration Date:</label>
				<s:textfield id="extensionExpirationDate" name="extensionExpirationDate" cssClass="required datepicker" maxlength="10"/>
			</li>
			<li class="submit">
				<s:submit value="Save"/>
				<s:url id="cancelUrl" action="licenses-list">
					<s:param name="facilityId" value="facilityId"/>
				</s:url>
				<s:a id="cancel" href="%{cancelUrl}" cssClass="ajaxify {target: '#licensesSection'}">
					Cancel
				</s:a>
			</li>
		</ol>
	</s:form>
</fieldset>

<script type="text/javascript">
	// *** Short key ctrl-q for current date on fields that have 'Date' in id attribute. ***
	$('[id $= "Date"]').keydown(function(e) {
        //CTRL + Q keydown for current date. Key codes: http://www.cambiaresearch.com/articles/15/javascript-char-codes-key-codes
        if(e.ctrlKey && e.keyCode == 81){
            var currentDate = new Date();
            $(this).val(currentDate.format("mm/dd/yyyy"));
        }
    });
</script>

