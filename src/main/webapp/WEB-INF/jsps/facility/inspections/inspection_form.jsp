<%@ taglib prefix="s" uri="/struts-tags"%>
<s:set var="formClass">ajaxify {target: '#inspectionSection'} ccl-action-save</s:set>
<s:set var="cancelClass">ajaxify {target: '#inspectionSection'}</s:set>
<s:url id="inspectionEditCancelUrl" action="view-inspection" includeParams="false">
	<s:param name="facilityId" value="facilityId"/>
	<s:param name="inspectionId" value="inspectionId"/>
</s:url>
<s:set var="formAction">save-inspection</s:set>
<fieldset>
	<legend>Edit Inspection</legend>
	<s:include value="inspection_form_inc.jsp"/>
</fieldset>
