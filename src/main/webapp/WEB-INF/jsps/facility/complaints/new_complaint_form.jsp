<%@ taglib prefix="s" uri="/struts-tags"%>
<s:set var="formClass">ajaxify {target: '#complaintsBase'} ccl-action-save</s:set>
<s:set var="cancelClass">ajaxify {target: '#complaintsBase'}</s:set>
<s:url id="complInfoEditCancelUrl" action="tab" namespace="/facility/complaints" includeParams="false">
	<s:param name="facilityId" value="facilityId"/>
</s:url>
<s:set var="formAction">save-new-complaint</s:set>
<fieldset>
	<legend>Create Complaint</legend>
	<s:include value="intake/information_form_inc.jsp"/>
</fieldset>