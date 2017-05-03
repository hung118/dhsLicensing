<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<fieldset>
	<legend>Completion</legend>
	<s:actionerror/>
	<s:form id="denialLetterDateForm" action="save-denial-date" method="post" cssClass="ajaxify {target: '#completionSection'}">
		<s:hidden name="facilityId"/>
		<s:hidden name="screeningId"/>
		<ol class="fieldList">
			<s:if test="screening.allScreeningsCompleteDate != null">
				<li>
					<div class="label">Date All Checks Completed:</div>
					<div class="value"><s:date name="screening.allScreeningsCompleteDate" format="MM/dd/yyyy"/></div>
				</li>
				<s:if test="screening.denied && screening.finalizedDate == null">
					<li>
						<label for="denialLetterDate">Denial Letter Date:</label>
						<s:date id="denialLetterDateFormatted" name="screening.denialLetterDate" format="MM/dd/yyyy"/>
						<s:textfield id="denialLetterDate" name="denialLetterDate" value="%{denialLetterDateFormatted}" cssClass="datepicker" maxlength="10"/>
					</li>
				</s:if>
				<s:elseif test="screening.denied && screening.denialLetterDate != null">
					<li>
						<div class="label">Denial Letter Date:</div>
						<div class="value"><s:date name="screening.denialLetterDate" format="MM/dd/yyyy"/></div>
					</li>
				</s:elseif>
			</s:if>
			<s:else>
				<li>
					<div class="value">You must complete all screening checks before finalizing this screening.</div>
				</li>
			</s:else>
			<s:set var="screening" value="screening" scope="request"/>
			<security:authorize access="hasPermission('finalize', 'screening')">
				<li class="submit">
					<s:url id="finScrnUrl" action="finalize-screening">
						<s:param name="facilityId" value="facilityId"/>
						<s:param name="screeningId" value="screeningId"/>
					</s:url>
					<s:a href="%{finScrnUrl}" cssClass="ccl-button ajaxify {target: '#completionSection'} ccl-action-save">
						Finalize Screening
					</s:a>
				</li>
			</security:authorize>
			<security:authorize access="hasPermission('unfinalize', 'screening')">
				<li>
					<s:url id="unfinScrnUrl" action="unfinalize-screening">
						<s:param name="facilityId" value="facilityId"/>
						<s:param name="screeningId" value="screeningId"/>
					</s:url>
					<s:a href="%{unfinScrnUrl}" cssClass="ccl-button ajaxify {target: '#completionSection'} ccl-action-save">
						Unfinalize Screening
					</s:a>
				</li>
			</security:authorize>
		</ol>
	</s:form>
</fieldset>

<script type="text/javascript">
	$("#denialLetterDate").change(function() {
		$(this).closest("form").submit();
	});	
</script>

<script type="text/javascript">
	// *** Short key ctrl-q for current date on fields that ends with 'Date' in id attribute. ***
	$('[id $= "Date"]').keydown(function(e) {
        //CTRL + Q keydown for current date. Key codes: http://www.cambiaresearch.com/articles/15/javascript-char-codes-key-codes
        if(e.ctrlKey && e.keyCode == 81){
            var currentDate = new Date();
            $(this).val(currentDate.format("mm/dd/yyyy"));
        }
    });
</script>

