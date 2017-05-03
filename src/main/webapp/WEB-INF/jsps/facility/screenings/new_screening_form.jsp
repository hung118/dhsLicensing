<%@ taglib prefix="s" uri="/struts-tags"%>
<s:set var="action">save-new-screening</s:set>
<s:set var="namespace">/facility/screenings</s:set>
<s:set var="formClass">ajaxify {target: '#screeningsBase'} ccl-action-save</s:set>
<s:set var="cancelClass">ajaxify {target: '#screeningsBase'}</s:set>
<s:url id="individualEditCancelUrl" action="tab" namespace="/facility/screenings" includeParams="false">
	<s:param name="facilityId" value="facilityId"/>
</s:url>
<fieldset>
	<legend>Covered Individuals Information</legend>
	<s:include value="screening_individual_form.jsp"/>
</fieldset>