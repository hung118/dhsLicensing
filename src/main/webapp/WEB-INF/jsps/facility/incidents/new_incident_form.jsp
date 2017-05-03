<%@ taglib prefix="s" uri="/struts-tags"%>
<s:url id="incidentEditCancelUrl" action="tab" includeParams="false">
	<s:param name="facilityId" value="facilityId"/>
</s:url>
<s:set var="formClass">ajaxify {target: '#incidentsBase'} ccl-action-save</s:set>
<s:set var="cancelClass">ajaxify {target: '#incidentsBase'}</s:set>
<s:set var="action">save-new-incident</s:set>
<fieldset>
	<legend>Create Incident &amp; Injury Report</legend>
	<p class="formInfo">
		Fields marked with an asterisk are required for the report to be finalized.  They may be left blank while the report is in progress.
	</p>
	<s:include value="incident_form_inc.jsp"/>
</fieldset>