<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>

<s:if test="transaction != null and transaction.id != null">
	<s:set var="legendText">Edit Fee</s:set>
</s:if>
<s:else>
	<s:set var="legendText">Create New Fee</s:set>
</s:else>
<fieldset>
	<legend><s:property value="%{legendText}"/></legend>
	<s:fielderror/>
	<s:form id="cmpForm" action="save-cmp" method="post" cssClass="ajaxify {target: '#cmpsBase'} ccl-action-save">
		<s:hidden name="facilityId"/>
		<s:hidden name="transaction.id"/>
		<ol class="fieldList">
			<li>
				<label for="transactionDate"><span class="redtext">* </span>Received Date:</label>
				<s:date id="transactionDateFormatted" name="transaction.date" format="MM/dd/yyyy" />
				<s:textfield id="transactionDate" name="transaction.date" value="%{transactionDateFormatted}" cssClass="required datepicker" maxlength="10"/>				
			</li>
			<li>
				<label for="transactionCheckDate">Check Date:</label>
				<s:date id="transactionCheckDateFormatted" name="transaction.checkDate" format="MM/dd/yyyy" />
				<s:textfield id="transactionCheckDate" name="transaction.checkDate" value="%{transactionCheckDateFormatted}" cssClass="required datepicker" maxlength="10"/>				
			</li>
			<li>
				<label for="transactionCheckNumber">Check Number:</label>
				<s:textfield id="transactionCheckNumber" name="transaction.checkNumber"/>
			</li>
			<li>
				<label for="transactionCheckOwner">Check Owner:</label>
				<s:textfield id="transactionCheckOwner" name="transaction.checkOwner"/>
			</li>
			<li>
				<label for="transactionAmount"><span class="redtext">* </span>Amount:</label>			
				<s:textfield id="transactionAmount" name="transaction.amount"/>
			</li>
			<li>
				<label for="transactionMemo">Memo:</label>
				<s:textarea id="transactionMemo" name="transaction.memo"/>
				<div id="tmCharCount" class="charCount" style="margin-left:11em;"></div>
			</li>
			<li>
				<security:authorize access="!hasRole('ROLE_SUPER_ADMIN')">
					<s:checkbox id="transactionApproval" name="transaction.approval" disabled="true"/>
					<label for="transactionApproval"><font color="gray">Approval:</font></label>
				</security:authorize>
				<security:authorize access="hasRole('ROLE_SUPER_ADMIN')">
					<s:checkbox id="transactionApproval2" name="transaction.approval"/>
					<label for="transactionApproval2">Approval:</label>
				</security:authorize>
			</li>

			<li class="submit">
				<s:submit value="Save"/>
				<s:url id="cmpCancelUrl" action="tab" includeParams="false">
					<s:param name="facilityId" value="facilityId"/>
				</s:url>
				<s:a href="%{cmpCancelUrl}" cssClass="ajaxify {target: '#cmpsBase'}">
					Cancel
				</s:a>
			</li>
		</ol>
	</s:form>
</fieldset>

<script type="text/javascript">
	$("#transactionMemo").charCounter(500, {container: "#cmpForm #tmCharCount"});
</script>

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


