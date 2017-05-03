<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@ taglib prefix="ccl" uri="/WEB-INF/tags/ccl-tags.tld"%>

<s:if test="oscar != null and oscar.id != null">
  <s:set var="legendText">Edit OSCAR</s:set>
</s:if>
<s:else>
  <s:set var="legendText">Create OSCAR</s:set>
</s:else>
<fieldset>
  <legend>
    <s:property value="%{legendText}" />
  </legend>
  <s:actionerror />
  <s:fielderror />
  <s:form id="oscarForm" action="save-oscar" method="post" cssClass="ajaxify {target: '#oscarSection'} ccl-action-save">
    <s:hidden name="screeningId" />
    <s:hidden name="oscar.id" />
    <ol class="fieldList medium">
      <li>
        <label for="oscarDate"><span class="redtext">* </span>Date:</label>
        <s:date id="oscarDateFormatted" name="oscar.oscarDate" format="MM/dd/yyyy" />
        <s:textfield id="oscarDate" name="oscar.oscarDate" value="%{oscarDateFormatted}" cssClass=" datepicker" maxlength="10"/>       
      </li>
      <li>
        <label for="oscarType">Type:</label>
        <s:select id="oscarType" name="oscar.oscarType" value="oscar.oscarType.id" list="oscarTypes" listKey="id" listValue="value" headerKey="" headerValue="- Select type -" />
      </li>
      <li>
        <label for="state">State:</label>
		<s:select id="state" name="oscar.state"  list="states" listKey="name()" listValue="name()" headerKey="" headerValue="- Select -" />
      </li>
      <li>
        <label for="caseNumber">Case #:</label>     
        <s:textfield id="caseNumber" name="oscar.caseNumber" maxlength="100" />
      </li>
      <li class="fieldMargin" style="float:left;">
        <label for="oscarNotes">OSCAR Notes:</label>
        <s:textarea id="oscarNotes" name="oscar.notes"/>
        <div id="oscarNotesCharCount" class="charCount" style="margin-left:11em;"></div>
      </li>

      <li class="submit">
        <s:submit value="Save"/>
        <s:url id="oscarCancelUrl" action="oscar-list" includeParams="false">
          <s:param name="screeningId" value="screeningId"/>
        </s:url>
        <s:a href="%{oscarCancelUrl}" cssClass="ajaxify {target: '#oscarSection'}">
          Cancel
        </s:a>
      </li>
    </ol>
  </s:form>
</fieldset>
<script type="text/javascript">
  $("<s:property value="formSection"/> #oscarNotes").charCounter(2000, {
    container : "<s:property value="formSection"/> #oscarNotesCharCount"
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


