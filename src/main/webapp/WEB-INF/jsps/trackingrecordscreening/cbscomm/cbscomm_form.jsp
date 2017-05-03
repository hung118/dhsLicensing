<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="ccl" uri="/WEB-INF/tags/ccl-tags.tld"%>

<fieldset>
  <legend>Edit CBS Comm</legend>
  <s:actionerror />
  <s:fielderror />
  <s:form id="cbscommForm" action="save-cbscomm" method="post"
    cssClass="ajaxify {target: '#cbscommSection'} ccl-action-save">
    <s:hidden name="screeningId" />
    <s:hidden name="cbscomm.id" value="%{cbscomm.id}" />
    <ol class="fieldList medium">
      <li>
        <label for="cbsCommitteeDecision">CBS Committee Decision:</label>
        <s:select title="Comprehensive Review Committee" id="cbsCommitteeDecision" name="cbsComm.cbsCommitteeDecision"  list="YesNoList" listKey="value" listValue="displayName" headerKey="" headerValue="- Select -"/>
      </li>
      <li>
        <label for="cbsDecisionDate">Decision Date:</label>
        <s:date id="cbsDecisionDateFormatted" name="cbsComm.decisionDate" format="MM/dd/yyyy" />
        <s:textfield id="cbsDecisionDate" name="cbsComm.decisionDate" value="%{cbsDecisionDateFormatted}" cssClass="datepicker" maxlength="10"/>       
      </li>
      <li>
        <label for="cbsOahRequestDate">OAH Request:</label>
        <s:date id="cbsOahRequestDateFormatted" name="cbsComm.oahRequestDate" format="MM/dd/yyyy" />
        <s:textfield title="Office of Administrative Hearings"  id="cbsOahRequestDate" name="cbsComm.oahRequestDate" value="%{cbsOahRequestDateFormatted}" cssClass="datepicker" maxlength="10"/>       
      </li>
      <li>
        <label for="cbsOahDecision">OAH Decision:</label>
        <s:select title="Office of Administrative Hearings" id="cbsOahDecision" name="cbsComm.oahDecision" value="cbsComm.oahDecision.id" list="oahDecisions" listKey="id" listValue="value" headerKey="-1" headerValue="- Select -" />
      </li>
      <li>
        <label for="cbsCommReasons" title="Committee Reasons">Com Reasons:</label>
        <s:textarea id="cbsCommReasons" name="cbsComm.comReasons" title="Comprehensive Review Committee"/>
        <div id="cbsCommReasonsCharCount" class="charCount" style="margin-left:11em;"></div>
      </li>
      <li class="submit">
        <s:submit value="Save"/>
      </li>
    </ol>  </s:form>
</fieldset>
<script type="text/javascript">
  $("#cbscommForm #cbsCommReasons").charCounter(4000, {
    container : "#cbscommForm #cbsCommReasonsCharCount"
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

