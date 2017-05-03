<%@ taglib prefix="s" uri="/struts-tags"%>
<s:if test="referral != null and referral.id != null">
	<s:set var="legendText">Edit</s:set>
</s:if>
<s:else>
	<s:set var="legendText">Create</s:set>
</s:else>
<fieldset>
	<legend><s:property value="%{legendText}"/> Referral</legend>
	<s:fielderror/>
	<s:form id="referralForm" action="save-referral" method="post" cssClass="ajaxify {target: '#referralsSection'} ccl-action-save">
		<s:hidden name="facilityId"/>
		<s:hidden name="complaintId"/>
		<s:hidden name="referral.id"/>
		<ol class="fieldList">
			<li>
				<label for="agency"><span class="redtext">* </span>Agency:</label>
				<s:select id="agency" name="referral.agency" value="referral.agency.id" list="agencies" listKey="id" listValue="value"
						  cssClass="required"/>
			</li>
			<li>
				<label for="referralDate"><span class="redtext">* </span>Date:</label>
				<s:date id="referralDateFormatted" name="referral.referralDate" format="MM/dd/yyyy"/>
				<s:textfield id="referralDate" name="referral.referralDate" value="%{referralDateFormatted}" cssClass="required datepicker" maxlength="10"/>
			</li>
			<li class="submit">
				<s:submit value="Save"/>
				<s:url id="referralEditCancelUrl" action="referrals-list" includeParams="false">
					<s:param name="facilityId" value="facilityId"/>
					<s:param name="complaintId" value="complaintId"/>
				</s:url>
				<s:a id="referralEditCancel" href="%{referralEditCancelUrl}" cssClass="ajaxify {target: '#referralsSection'}">
					Cancel
				</s:a>
			</li>
		</ol>
	</s:form>
	<div id="referralsListSection">
		<s:include value="referrals_table.jsp"/>
	</div>
</fieldset>

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
