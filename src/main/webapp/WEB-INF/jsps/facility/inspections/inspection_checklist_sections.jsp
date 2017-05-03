<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<style>
<!--
ol.fieldList > li > input[type=checkbox] {
	height: 10px;
	float:none !important;
	margin: 4px;
	}
ol.fieldList > li {
	//border:1px red solid;
	margin-top: .5em;
	margin-bottom: -.3em;
	text-align: top;
}

-->
</style>
<script type="text/javascript">
<!--
function checkIt(objId) {
	var obj = document.getElementById(objId);
	if (obj.checked) 
		obj.checked = false;
	else
		obj.checked = true;
}
//-->
</script>
	<s:set var="formClass">ajaxify {target: '#inspectionsBase'} ccl-action-save</s:set>
	<s:set var="formAction">inspection-checklist-build</s:set>
<fieldset>
<legend>Inspection - Rule Sections</legend>
	<s:fielderror/>
	<s:actionerror/>
<s:form id="checkListForm" action="%{formAction}" method="post" cssClass="%{formClass}">
	<s:hidden name="facilityId"/>
	<s:hidden name="complaintId"/>
	<s:hidden name="inspectionId"/>
	<ol class="fieldList">
		<s:iterator value="sectionList" var="item">
		<li>
		  <input type=checkbox id="section_<s:property value="#item[1]"/>" name="section_<s:property value="#item[1]"/>" value="section_<s:property value="#item[1]"/>">
		  <a style="color:#333;text-decoration:none" href="javascript:checkIt('section_<s:property value="#item[1]"/>')"><s:property value="#item[1]"/> - <s:property value="#item[0]"/></a>
		</li>
		</s:iterator>
<s:url id="inspectionEditCancelUrl" action="view-inspection" includeParams="false">
	<s:param name="facilityId" value="facilityId"/>
	<s:param name="inspectionId" value="inspectionId"/>
</s:url>
		<li>
			<input type="submit" id="inspectionForm_0" value="Next" class="ui-button ui-widget ui-state-default ui-corner-all" role="button">
			<s:a href="%{inspectionEditCancelUrl}" cssClass="ccl-button ajaxify {target: '#inspectionsBase'}">
				Cancel
			</s:a>
		</li>
	</ol>
</s:form>
</fieldset>
