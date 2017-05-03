<%@ taglib prefix="s" uri="/struts-tags"%>
<s:fielderror/>
<s:form id="investigationForm" action="save-investigation" method="post" cssClass="ajaxify {target: '#investigationSection'} ccl-action-save">
	<s:hidden name="facilityId"/>
	<s:hidden name="complaintId"/>
	<ol class="fieldList">
		<li>
			<label for="investigationCompletedDate"><span class="redtext">* </span>Date Investigation Completed:</label>
			<s:date id="investigationCompletedDateFormatted" name="complaint.investigationCompletedDate" format="MM/dd/yyyy"/>
			<s:textfield id="investigationCompletedDate" name="complaint.investigationCompletedDate" value="%{investigationCompletedDateFormatted}" cssClass="required datepicker" maxlength="10"/>
		</li>
		<li>
			<label for="investigationDetails"><span class="redtext">* </span>Investigation Details:</label>
			<s:textarea id="investigationDetails" name="complaint.investigationDetails" cssClass="tallTextArea"/>
		</li>
		<li>
			<label>&nbsp;</label>
			<ol class="fieldGroup">
				<li class="checkbox">
					<s:checkbox id="substantiated" name="complaint.substantiated"/>
					<label for="substantiated">A license/certificate is required</label>
				</li>
				<li class="checkbox clearLeft fieldMargin">
					<s:checkbox id="followUpNeeded" name="complaint.followUpNeeded"/>
					<label for="followUpNeeded">A follow-up is needed</label>
				</li>
			</ol>
		</li>
		<li class="submit">
			<s:submit value="Save"/>
			<s:url id="investigationEditCancelUrl" action="view-investigation" includeParams="false">
				<s:param name="facilityId" value="facilityId"/>
				<s:param name="complaintId" value="complaintId"/>
			</s:url>
			<s:a id="investigationEditCancel" href="%{investigationEditCancelUrl}" cssClass="ajaxify {target: '#investigationSection'}">
				Cancel
			</s:a>
		</li>
	</ol>
</s:form>
<div id="followUpSection">
	<s:include value="unlicensed_follow_ups_table.jsp">
		<s:param name="showControls">true</s:param>
	</s:include>
</div>

<script type="text/javascript">
	// *** Short key ctrl-q for current date on fields that have 'Date' in id attribute. ***
	$('[id $= "Date"]').keydown(function(e) {
        //CTRL + Q keydown for current date. Key codes: http://www.cambiaresearch.com/articles/15/javascript-char-codes-key-codes
        if(e.ctrlKey && e.keyCode == 81){
            var currentDate = new Date();
            $(this).val(currentDate.format("mm/dd/yyyy"));
        }
    });
</script>
