<%@ taglib prefix="s" uri="/struts-tags"%>
<li>
	<div class="label">Date Investigation Completed:</div>
	<div class="value"><s:date name="complaint.investigationCompletedDate" format="MM/dd/yyyy"/></div>
</li>
<li>
	<div class="label">Investigation Details:</div>
	<div class="value"><s:property value="complaint.investigationDetails"/></div>
</li>
<li>
	<div class="label">A license/certificate is required:</div>
	<div class="value"><s:property value="@gov.utah.dts.det.ccl.view.YesNoChoice@valueOfBoolean(complaint.substantiated).displayName"/></div>
</li>
<li>
	<div class="label">Follow-Up Needed:</div>
	<div class="value"><s:property value="@gov.utah.dts.det.ccl.view.YesNoChoice@valueOfBoolean(complaint.followUpNeeded).displayName"/></div>
</li>