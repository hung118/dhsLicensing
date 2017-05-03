<%@ taglib prefix="s" uri="/struts-tags"%>
<s:if test="complaintId != null">
	<s:set var="legendText">Create Complaint Investigation</s:set>
	<s:url id="inspectionEditCancelUrl" action="open-tab" namespace="/facility" includeParams="false">
		<s:param name="facilityId" value="facilityId"/>
		<s:param name="tab" value="%{'complaints'}"/>
		<s:param name="act" value="%{'tab'}"/>
		<s:param name="ns" value="%{'/complaints/investigation'}"/>
		<s:param name="complaintId" value="complaintId"/>
	</s:url>
</s:if>
<s:else>
	<s:set var="legendText">Create Inspection</s:set>
	<s:url id="inspectionEditCancelUrl" action="tab" includeParams="false">
		<s:param name="facilityId" value="facilityId"/>
	</s:url>
	<s:set var="cancelClass">ajaxify {target: '#inspectionsBase'}</s:set>
</s:else>
<s:set var="formClass">ajaxify {target: '#inspectionsBase'} ccl-action-save</s:set>
<s:set var="formAction">save-new-inspection</s:set>
<fieldset>
	<legend><s:property value="%{legendText}"/></legend>
	<s:include value="inspection_form_inc.jsp"/>
</fieldset>