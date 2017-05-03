<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@ taglib prefix="ccl" uri="/WEB-INF/tags/ccl-tags.tld"%>

<s:if test="misCase != null and misCase.id != null">
  <s:set var="legendText">Edit Case</s:set>
</s:if>
<s:else>
  <s:set var="legendText">Create New Case</s:set>
</s:else>
<fieldset>
  <legend>
    <s:property value="%{legendText}" />
  </legend>
  <s:actionerror />
  <s:fielderror />
  <s:form id="caseForm" action="save-case" method="post"
    cssClass="ajaxify {target: '#caseSection'} ccl-action-save">
    <s:hidden name="screeningId" />
    <s:hidden name="caseId" value="%{misCase.id}" />
    <s:hidden name="misCase.id" />
    <s:hidden name="misCase.trackingRecordScreening.id" />
    <ol class="fieldList medium">
      <li>
        <label for="misCaseType"><span class="redtext">* </span>Type:</label>
        <s:select id="misCaseType" name="caseType" value="caseType.id" list="caseTypes" listKey="id" listValue="value" headerKey="-1" headerValue="- Select -" cssClass="required" />
      </li>
      <li>
        <label for="misCaseNumber" title="Case Number">Case Number:</label>
        <s:textfield id="misCaseNumber" name="misCase.caseNumber" cssClass=" required" maxlength="100" />
      </li>
      <li>
        <label for="misCaseDate">Date:</label>
        <s:date id="misCaseDateFormatted" name="misCase.caseDate" format="MM/dd/yyyy" />
        <s:textfield id="misCaseDate" name="misCase.caseDate" value="%{misCaseDateFormatted}" cssClass="required datepicker" maxlength="10"/>
      </li>
      <li>
        <label for="misCaseDetail">Case Details:</label>
		<s:textarea id="misCaseDetail" name="misCase.detail" cssClass="smalltextarea" />
		<div id="detailsCharCount" class="charCount" style="margin-left:11em;"></div>
      </li>
      <li class="submit">
        <s:submit value="Save"/>
        <s:url id="caseCancelUrl" action="case-list" includeParams="false">
          <s:param name="screeningId" value="screeningId"/>
        </s:url>
        <s:a href="%{caseCancelUrl}" cssClass="ajaxify {target: '#caseSection'}">
          Cancel
        </s:a>
      </li>
    </ol>
  </s:form>
</fieldset>
<script type="text/javascript">
  $("#misCaseDetail").charCounter(100, {
    container : "#caseForm #detailsCharCount"
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

