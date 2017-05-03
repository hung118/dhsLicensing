<%@ taglib prefix="s" uri="/struts-tags"%>
<s:url id="requestEditCancelUrl" action="tab" includeParams="false">
	<s:param name="facilityId" value="facilityId"/>
</s:url>
<s:set var="formClass">ajaxify {target: '#variancesBase'} ccl-action-save</s:set>
<s:set var="cancelClass">ajaxify {target: '#variancesBase'}</s:set>
<s:set var="action">save-new-variance</s:set>
<fieldset>
	<legend>Request Variance</legend>
	<s:include value="request_form_inc.jsp"/>
</fieldset>