<%@ taglib prefix="s" uri="/struts-tags"%>
<s:url id="incidentEditCancelUrl" action="view-incident-info" includeParams="false">
	<s:param name="facilityId" value="facilityId"/>
	<s:param name="incidentId" value="incidentId"/>
</s:url>
<s:set var="formClass">ajaxify {target: '#incidentSection'} ccl-action-save</s:set>
<s:set var="cancelClass">ajaxify {target: '#incidentSection'}</s:set>
<s:set var="action">save-incident-info</s:set>
<fieldset>
	<legend>Edit Incident &amp; Injury Report</legend>
	<p class="formInfo">
		Fields marked with an asterisk are required for the report to be finalized.  They may be left blank while the report is in progress.
	</p>
	<s:include value="incident_form_inc.jsp"/>
</fieldset>