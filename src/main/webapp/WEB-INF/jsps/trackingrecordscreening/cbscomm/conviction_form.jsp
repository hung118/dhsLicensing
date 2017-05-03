<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@ taglib prefix="ccl" uri="/WEB-INF/tags/ccl-tags.tld"%>

<s:if test="conviction != null and conviction.id != null">
  <s:set var="legendText">Edit Conviction</s:set>
</s:if>
<s:else>
  <s:set var="legendText">Create New Conviction</s:set>
</s:else>
<fieldset>
  <legend>
    <s:property value="%{legendText}" />
  </legend>
  <s:actionerror />
  <s:fielderror />
  <s:form id="convictionForm" action="save-conviction" method="post"
    cssClass="ajaxify {target: '#convictionsSection'} ccl-action-save">
    <s:hidden name="screeningId" />
    <s:hidden name="convictionId" value="%{conviction.id}" />
    <s:hidden name="conviction.id" />
    <s:hidden name="conviction.trackingRecordScreening.id" />
    <ol class="fieldList medium">
      <li>
        <label for="cbsConvictionType"><span class="redtext">* </span>Type:</label>
        <s:select id="cbsConvictionType" name="convictionType" value="convictionType.id" list="convictionTypes" listKey="id" listValue="value" headerKey="-1" headerValue="- Select -" cssClass="required" />
      </li>
      <li>
        <label for="cbsConvictionDesc" title="DUI text field">Conviction:</label>
        <s:textarea id="cbsConvictionDesc" name="conviction.convictionDesc" cssClass="smalltextarea" />
        <div id="convictionCharCount" class="charCount" style="margin-left:11em;"></div>
      </li>
      <li>
        <label for="cbsConvictionDate"><span class="redtext">* </span>Date:</label>
        <s:date id="cbsConvictionDateFormatted" name="conviction.convictionDate" format="MM/dd/yyyy" />
        <s:textfield id="cbsConvictionDate" name="conviction.convictionDate" value="%{cbsConvictionDateFormatted}" cssClass="required datepicker" maxlength="10"/>
      </li>
      <li>
        <label for="cbsConvictionDismissed">Dismissed:</label>
        <s:checkbox id="cbsConvictionDismissed" name="conviction.dismissed"/>
      </li>
      <li>
        <label for="cbsConvictionCourtInfo">Court Info:</label>
        <s:textarea id="cbsConvictionCourtInfo" name="conviction.courtInfo" cssClass="smalltextarea" />
        <div id="courtCharCount" class="charCount" style="margin-left:11em;"></div>
      </li>
      <li class="submit">
        <s:submit value="Save"/>
        <s:url id="convictionCancelUrl" action="list-convictions" includeParams="false">
          <s:param name="screeningId" value="screeningId"/>
        </s:url>
        <s:a href="%{convictionCancelUrl}" cssClass="ajaxify {target: '#convictionsSection'}">
          Cancel
        </s:a>
      </li>
    </ol>
  </s:form>
</fieldset>
<script type="text/javascript">
  $("#cbsConvictionDesc").charCounter(100, {
    container : "#convictionForm #convictionCharCount"
  });
  $("#cbsConvictionCourtInfo").charCounter(100, {
    container : "#convictionForm #courtCharCount"
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

