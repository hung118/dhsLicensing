<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@ taglib prefix="ccl" uri="/WEB-INF/tags/ccl-tags.tld"%>

<fieldset>
  <legend>
    Edit MIS Comm
  </legend>
  <s:actionerror />
  <s:fielderror />
  <s:form id="misCommForm" action="save-miscomm" method="post"
    cssClass="ajaxify {target: '#miscommSection'} ccl-action-save">
    <s:hidden name="screeningId" />
    <s:hidden name="misComm.id" />
    <ol class="fieldList medium">
      <li>
        <label for="misCommitteeDecision">MIS Committee Decision:</label>
        <s:select title="Comprehensive Review Committee" id="misCommitteeDecision" name="misComm.misCommDecision"  list="YesNoList" listKey="value" listValue="displayName" headerKey="" headerValue="- Select -"/>
      </li>
      <li>
        <label for="misCommDate">Decision Date:</label>
        <s:date id="misCommDateFormatted" name="misComm.misCommDate" format="MM/dd/yyyy" />
        <s:textfield id="misCommDate" name="misComm.misCommDate" value="%{misCommDateFormatted}" cssClass=" datepicker" maxlength="10"/>       
      </li>
      <li>
        <label for="oahRequestDate">OAH Request:</label>
        <s:date id="oahRequestDateFormatted" name="misComm.oahRequestDate" format="MM/dd/yyyy" />
        <s:textfield id="oahRequestDate" name="misComm.oahRequestDate" value="%{oahRequestDateFormatted}" cssClass=" datepicker" maxlength="10"/>       
      </li>
      <li>
        <label for="misOahDecision">OAH Decision:</label>
        <s:select title="Office of Administrative Hearings" id="misOahDecision" name="misComm.oahDecision" value="misComm.oahDecision.id" list="oahDecisions" listKey="id" listValue="value" headerKey="-1" headerValue="- Select -" />
      </li>
      <li class="fieldMargin" style="float:left;">
        <label for="commNotes">Comm Reasons:</label>
        <s:textarea id="commNotes" name="misComm.notes"/>
        <div id="commNotesCharCount" class="charCount" style="margin-left:11em;"></div>
      </li>

      <li class="submit">
        <s:submit value="Save"/>
      </li>
    </ol>
  </s:form>
</fieldset>
<script type="text/javascript">

	$("#misCommForm #commNotes").charCounter(4000, {
		container : "#misCommForm #commNotesCharCount"
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


