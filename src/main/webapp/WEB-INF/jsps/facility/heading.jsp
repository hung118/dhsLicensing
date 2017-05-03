<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<div class="introParagraph">
	Facility: <strong><s:property value="facility.name" /> (<s:property value="facility.idNumber"/>)</strong>
</div>
<security:authorize access="isAuthenticated() and principal.isInternal()">
	<s:url id="getStickyNotesUrl" action="get-sticky-notes" namespace="/facility" escapeAmp="false">
		<s:param name="facilityId" value="facility.id"/>
	</s:url>
	<s:url id="saveStickyNoteUrl" action="save-sticky-note" namespace="/facility" escapeAmp="false"/>
	<s:url id="deleteStickyNoteUrl" action="delete-sticky-note" namespace="/facility" escapeAmp="false"/>
	<s:a id="ccl-fac-st-nt" href="#" cssClass="{getUrl: '%{getStickyNotesUrl}', saveUrl: '%{saveStickyNoteUrl}', deleteUrl: '%{deleteStickyNoteUrl}'}">Sticky Notes</s:a>
</security:authorize>
<div class="clearfix">
	<s:hidden id="infoFacilityName" name="infoFacilityName" value="%{facility.name}" />
	<s:hidden id="infoPrimaryPhone" name="infoPrimaryPhone" value="%{facility.primaryPhone}" />
	<s:hidden id="infoEmail" name="infoEmail" value="%{facility.email}" />
	<s:hidden id="infoMailAddressOne" name="infoMailAddressOne" value="%{facility.mailingAddress.addressOne}" />
	<s:hidden id="infoMailZipCode" name="infoMailZipCode" value="%{facility.mailingAddress.zipCode}" />
	<s:hidden id="infoMailCity" name="infoMailCity" value="%{facility.mailingAddress.city}" />
	<s:hidden id="infoMailState" name="infoMailState" value="%{facility.mailingAddress.state}" />
	<s:hidden id="infoFacilityType" name="infoFacilityType" value="%{facility.type}" />
	<div class="left-column">
		<h2>Location Address:</h2>
		<s:component template="addressdisplay.ftl">
			<s:param name="address" value="facility.searchView.locationAddress"/>
		</s:component>
		Primary Phone: <s:property value="facility.primaryPhone.formattedPhoneNumber"/>
	</div>
	<div class="right-column">
		<s:if test="facility.searchView.directorName != null">Director(s): <s:property value="facility.searchView.directorName"/></s:if><s:else><br/></s:else>
		<h2>Mailing Address:</h2>
		<s:component template="addressdisplay.ftl">
			<s:param name="address" value="facility.searchView.mailingAddress"/>
		</s:component>
		<s:if test="facility.licensingSpecialist != null">
			Licensing Specialist: <s:property value="facility.licensingSpecialist.firstAndLastName"/>
		</s:if>
		 
	</div>
</div>