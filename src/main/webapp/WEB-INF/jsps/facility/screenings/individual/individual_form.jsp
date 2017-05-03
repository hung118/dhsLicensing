<%@ taglib prefix="s" uri="/struts-tags"%>
<s:set var="action">save-individual</s:set>
<s:set var="namespace">/facility/screenings/individual</s:set>
<s:url id="individualEditCancelUrl" action="view-individual" includeParams="false">
	<s:param name="facilityId" value="facilityId"/>
	<s:param name="screeningId" value="screeningId"/>
</s:url>
<s:set var="formClass">ajaxify {target: '#screeningInformationSection'} ccl-action-save</s:set>
<s:set var="cancelClass">ajaxify {target: '#screeningInformationSection'}</s:set>
<s:include value="../navigation.jsp"><s:param name="selectedTab">individual</s:param></s:include>
<fieldset>
	<legend>Edit Covered Individuals Information</legend>
	<s:include value="../screening_individual_form.jsp"/>
</fieldset>