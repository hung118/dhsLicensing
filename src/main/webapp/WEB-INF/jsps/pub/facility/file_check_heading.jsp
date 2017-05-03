<%@ taglib prefix="s" uri="/struts-tags"%>
<div class="introParagraph">
	<strong>Licensing Record</strong>
</div>
<div class="ccl-section">
	<div class="left-column">
		<div class="introParagraph">
			Facility: <strong><s:property value="facility.name" /></strong>
		</div>
		<s:if test="facility.type.name() == 'CENTER_BASED'">
			<s:component template="addressdisplay.ftl">
				<s:param name="address" value="facility.locationAddress"/>
			</s:component>
		</s:if>
		<s:else>
			<s:property value="facility.locationAddress.city"/>, <s:property value="facility.locationAddress.state"/> <s:property value="facility.locationAddress.zipCode"/>
		</s:else>
		<s:property value="facility.primaryPhone.formattedPhoneNumber"/>
	</div>
	<div class="right-column">
		<div>
			License Type: <s:property value="facility.latestLicense.type.value"/><s:if test="conditional"> - <span class="redtext">Conditional License</span></s:if>
		</div>
		<div>
			Capacity: <s:property value="facility.latestLicense.totalSlots"/><s:if test="facility.latestLicense.ageTwoSlots != null && facility.latestLicense.ageTwoSlots > 0"> (<s:property value="facility.latestLicense.ageTwoSlots"/> under age 2)</s:if>
		</div>
		<div>
			Initial Regulation Date: <s:date name="facility.initialRegulationDate" format="MM/dd/yyyy"/>
		</div>
	</div>
	<div style="clear:both;"></div>
</div>