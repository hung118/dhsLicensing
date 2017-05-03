<%@ taglib prefix="s" uri="/struts-tags"%>
<ol class="fieldList">
	<s:include value="unlicensed_investigation_detail_fields.jsp"/>
</ol>
<h1>Follow-Up</h1>
<s:fielderror/>
<s:form id="followUpForm" action="save-follow-up" method="post" cssClass="ajaxify {target: '#investigationSection'} ccl-action-save">
	<s:hidden name="facilityId"/>
	<s:hidden name="complaintId"/>
	<s:hidden name="followUpId"/>
	<ol class="fieldList">
		<li>
			<label for="followUpDate"><span class="redtext">* </span>Follow-Up Date:</label>
			<s:date id="followUpDateFormatted" name="followUp.followUpDate" format="MM/dd/yyyy"/>
			<s:textfield id="followUpDate" name="followUp.followUpDate" value="%{followUpDateFormatted}" cssClass="required datepicker" maxlength="10"/>
		</li>
		<li>
			<label for="followUpDetails"><span class="redtext">* </span>Follow-Up Details:</label>
			<s:textarea id="followUpDetails" name="followUp.details" cssClass="tallTextArea"/>
		</li>
		<li class="submit">
			<s:submit value="Save"/>
			<s:url id="followUpEditCancelUrl" action="investigation-section" includeParams="false">
				<s:param name="facilityId" value="facilityId"/>
				<s:param name="complaintId" value="complaintId"/>
			</s:url>
			<s:a id="followUpEditCancel" href="%{followUpEditCancelUrl}" cssClass="ajaxify {target: '#investigationSection'}">
				Cancel
			</s:a>
		</li>
	</ol>
</s:form>
<s:include value="unlicensed_follow_ups_table.jsp"/>

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
