<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="ccl" uri="/WEB-INF/tags/ccl-tags.tld"%>

<fieldset>
	<legend>New Letter</legend>
	<s:actionerror/>
	<s:fielderror/>
	<s:form id="lettersForm" action="save-letter" method="post" cssClass="ajaxify {target: '#letterSection'} ccl-action-save">
		<s:hidden name="screeningId"/>
    <ol class="fieldList medium">
      <li>
        <label for="lettersLetterType"><span class="redtext">* </span>Letter Type:</label>
        <s:select id="lettersLetterType" name="letterType" list="letterTypes" listKey="name()" listValue="label" cssClass="required"/>
      </li>
      <li>
        <label for="lettersLetterDate"><span class="redtext">* </span>Letter Date:</label>
        <s:date id="lettersLetterDateFormatted" name="screeningLetter.letterDate" format="MM/dd/yyyy" />
        <s:textfield id="lettersLetterDate" name="screeningLetter.letterDate" value="%{lettersLetterDateFormatted}" cssClass="required datepicker" maxlength="10"/>       
      </li>
      <li class="hiddenScreeningLetterField screeningLetterFPI">
        <label for="lettersFpiDetails"><span class="redtext">* </span>FPI Details:</label>
        <s:textfield id="lettersFpiDetails" name="screeningLetter.details" maxlength="120" cssStyle="width:520px;" title="Failure to Provide Information" />
      </li>
      <li class="hiddenScreeningLetterField screeningLetterFC">
        <label><span class="redtext">* </span>Address:</label>
        <ccl:address id="lettersAddress" name="screeningLetter.address" requiredLabel="true"/>
      </li>
      <li class="submit">
        <s:submit value="Print"/>
        <s:url id="lettersCancelUrl" action="list-letters" includeParams="false">
          <s:param name="screeningId" value="screeningId"/>
        </s:url>
        <s:a href="%{lettersCancelUrl}" cssClass="ajaxify {target: '#letterSection'}">
          Cancel
        </s:a>
      </li>
    </ol>
	</s:form>
</fieldset>
<script type="text/javascript">
	initScreeningLetterForm();
</script>

<script type="text/javascript">
	// *** Short key ctrl-q for current date on fields that contains 'Date' in id attribute. ***
	$('[id *= "Date"]').keydown(function(e) {
        //CTRL + Q keydown for current date. Key codes: http://www.cambiaresearch.com/articles/15/javascript-char-codes-key-codes
        if(e.ctrlKey && e.keyCode == 81){
            var currentDate = new Date();
            $(this).val(currentDate.format("mm/dd/yyyy"));
        }
    });
</script>

