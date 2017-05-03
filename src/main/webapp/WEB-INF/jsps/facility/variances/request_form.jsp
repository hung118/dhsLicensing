<%@ taglib prefix="s" uri="/struts-tags"%>
<s:set var="formClass">ajaxify {target: '#requestSection'} ccl-action-save</s:set>
<s:set var="cancelClass">ajaxify {target: '#requestSection'}</s:set>
<s:url id="requestEditCancelUrl" action="view-request" includeParams="false">
	<s:param name="facilityId" value="facilityId"/>
	<s:param name="varianceId" value="varianceId"/>
</s:url>
<s:set var="action">save-request</s:set>
<fieldset>
	<legend>Edit Variance Request</legend>
	<s:include value="request_form_inc.jsp"/>
</fieldset>