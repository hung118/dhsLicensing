<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="ccl" uri="/WEB-INF/tags/ccl-tags.tld"%>

<fieldset>
	<legend>New Convictions Letter</legend>
	<s:actionerror/>
	<s:fielderror/>
	<s:form id="convictLtrsForm" action="save-conviction-letter" method="post" cssClass="ajaxify {target: '#convictionLettersSection'} ccl-action-save">
		<s:hidden name="screeningId"/>
		<ol class="fieldList medium">
			<li>
				<label for="convictLtrsLetterType"><span class="redtext">* </span>Letter Type:</label>
				<s:select id="convictLtrsLetterType" name="letterType" list="letterTypes" listKey="name()" listValue="label" cssClass="required"/>
			</li>
			<li>
				<label for="lettersLetterDate"><span class="redtext">* </span>Letter Date:</label>
				<s:date id="lettersLetterDateFormatted" name="convictionLetter.letterDate" format="MM/dd/yyyy" />
				<s:textfield id="lettersLetterDate" name="convictionLetter.letterDate" value="%{lettersLetterDateFormatted}" cssClass="required datepicker" maxlength="10"/>       
			</li>
			<li class="submit">
				<s:submit value="Print"/>
				<s:url id="convictLtrsCancelUrl" action="list-conviction-letters" includeParams="false">
					<s:param name="screeningId" value="screeningId"/>
				</s:url>
				<s:a href="%{convictLtrsCancelUrl}" cssClass="ajaxify {target: '#convictionLettersSection'}">
					Cancel
				</s:a>
			</li>
		</ol>
	</s:form>
</fieldset>

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


