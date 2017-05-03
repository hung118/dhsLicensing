<%@ taglib prefix="s" uri="/struts-tags"%>
<s:set var="formClass">ajaxify {target: '#complaintInformationSection'} ccl-action-save</s:set>
<s:set var="cancelClass">ajaxify {target: '#complaintInformationSection'}</s:set>
<s:url id="complInfoEditCancelUrl" action="view-information" includeParams="false">
	<s:param name="facilityId" value="facilityId"/>
	<s:param name="complaintId" value="complaintId"/>
</s:url>
<s:set var="formAction">save-information</s:set>
<fieldset>
	<legend>Edit Complaint Information</legend>
	<s:include value="information_form_inc.jsp"/>
</fieldset>