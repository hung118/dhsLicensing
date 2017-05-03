<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags"%>

<s:if test="trackingRecordScreeningDpsFbi != null and trackingRecordScreeningDpsFbi.id != null">
  <s:set var="legendText">Edit DPS/FBI</s:set>
</s:if>
<s:else>
  <s:set var="legendText">Create DPS/FBI</s:set>
</s:else>
<fieldset>
  <legend>
    <s:property value="%{legendText}" />
  </legend>
  <s:fielderror />
  <s:form id="dpsFbi-Form" action="save-trsDpsFbi" method="post" cssClass="ajaxify {target: '#dpsFbiBase'} ccl-action-save">
    <s:hidden name="screeningId" />
    <s:hidden name="trsdpsFbiId" value="%{trackingRecordScreeningDpsFbi.id}" />
    <ol class="fieldList ">

      <li class="column">
        <ol>
          <li><label for="firstFbiRequestDate">1st FBI Req:</label> <s:date
              id="firstFbiRequestFormatted" name="trackingRecordScreeningDpsFbi.firstFbiRequestDate" format="MM/dd/yyyy"
            /> <s:textfield tabindex="1" title="Federal Bureau of Investigation" id="firstFbiRequestDate"
              name="trackingRecordScreeningDpsFbi.firstFbiRequestDate" value="%{firstFbiRequestFormatted}" cssClass="datepicker"
              maxlength="10"
            /></li>

          <li><label for="secondFbiRequestDate">2nd FBI Req:</label> <s:date id="secondFbiRequestDateFormatted"
              name="trackingRecordScreeningDpsFbi.secondFbiRequestDate" format="MM/dd/yyyy"
            /> <s:textfield tabindex="3" title="Federal Bureau of Investigation" id="secondFbiRequestDate"
              name="trackingRecordScreeningDpsFbi.secondFbiRequestDate" value="%{secondFbiRequestDateFormatted}" cssClass=" datepicker"
              maxlength="10"
            /></li>


          <li><label for="fbiNameCheckDate">FBI Name Check:</label> <s:date id="fbiNameCheckDateFormatted"
              name="trackingRecordScreeningDpsFbi.fbiNameCheckDate" format="MM/dd/yyyy"
            /> <s:textfield tabindex="5" title="Federal Bureau of Investigation" id="fbiNameCheckDate"
              name="trackingRecordScreeningDpsFbi.fbiNameCheckDate" value="%{fbiNameCheckDateFormatted}" cssClass=" datepicker"
              maxlength="10"
            /></li>
          <li><label for="livescanDate">Livescan:</label> <s:date id="livescanDateFormatted"
              name="trackingRecordScreeningDpsFbi.livescanDate" format="MM/dd/yyyy"
            /> <s:textfield tabindex="7" id="livescanDate" name="trackingRecordScreeningDpsFbi.livescanDate"
              value="%{livescanDateFormatted}" cssClass=" datepicker" maxlength="10"
            /></li>
          <li><label for="toDpsForVerificationDate">To DPS for Verification:</label> <s:date id="toDpsForVerificationDateFormatted"
              name="trackingRecordScreeningDpsFbi.toDpsForVerificationDate" format="MM/dd/yyyy"
            /> <s:textfield tabindex="9" title="Department of Public Safety" id="toDpsForVerificationDate"
              name="trackingRecordScreeningDpsFbi.toDpsForVerificationDate" value="%{toDpsForVerificationDateFormatted}"
              cssClass=" datepicker" maxlength="10"
            /></li>

          <li><label for="dpsVerifiedDate">DPS Verified:</label> <s:date id="dpsVerifiedDateFormatted"
              name="trackingRecordScreeningDpsFbi.dpsVerifiedDate" format="MM/dd/yyyy"
            /> <s:textfield tabindex="11" title="Department of Public Safety" id="dpsVerifiedDate"
              name="trackingRecordScreeningDpsFbi.dpsVerifiedDate" value="%{dpsVerifiedDateFormatted}" cssClass=" datepicker" maxlength="10"
            /></li>


        </ol>
      </li>

      <li class="column">
        <ol>
          <li><label for="firstRejectedDate">1st Rejected:</label> <s:date id="firstRejectedDateFormatted"
              name="trackingRecordScreeningDpsFbi.firstRejectedDate" format="MM/dd/yyyy"
            /> <s:textfield tabindex="2" id="firstRejectedDate" name="trackingRecordScreeningDpsFbi.firstRejectedDate"
              value="%{firstRejectedDateFormatted}" cssClass=" datepicker" maxlength="10"
            /></li>

          <li><label for="secondRejectedDate">2nd Rejected:</label> <s:date id="secondRejectedDateFormatted"
              name="trackingRecordScreeningDpsFbi.secondRejectedDate" format="MM/dd/yyyy"
            /> <s:textfield tabindex="4" id="secondRejectedDate" name="trackingRecordScreeningDpsFbi.secondRejectedDate"
              value="%{secondRejectedDateFormatted}" cssClass=" datepicker" maxlength="10"
            /></li>
          <li><label for="receivedFromFbiDate">Received from FBI:</label> <s:date id="receivedFromFbiDateFormatted"
              name="trackingRecordScreeningDpsFbi.receivedFromFbiDate" format="MM/dd/yyyy"
            /> <s:textfield tabindex="6" title="Federal Bureau of Investigation" id="receivedFromFbiDate"
              name="trackingRecordScreeningDpsFbi.receivedFromFbiDate" value="%{receivedFromFbiDateFormatted}" cssClass=" datepicker"
              maxlength="10"
            /></li>



          <li><label for="billing">Billing:</label> <s:select tabindex="8" id="billing" name="trackingRecordScreeningDpsFbi.billing"
              value="trackingRecordScreeningDpsFbi.billing.id" list="billingTypes" listKey="id" listValue="value" headerKey=""
              headerValue="- Select type -"
            /></li>


          <li><label for="moNumber">MO#:</label> <s:textfield tabindex="10" id="moNumber" name="trackingRecordScreeningDpsFbi.moNumber"
              cssClass=" required" maxlength="100" title="Money Order"
            /></li>


          <li><label for="issuedBy">Issued By:</label> <s:textfield tabindex="12" id="issuedBy"
              name="trackingRecordScreeningDpsFbi.issuedBy" cssClass=" required" maxlength="100"
            /></li>

          <li><label for="searchFee">Search Fee:</label> <s:textfield tabindex="13" id="searchFee"
              name="trackingRecordScreeningDpsFbi.searchFee"
              value="%{getText('format.currencyNoSymbol',{trackingRecordScreeningDpsFbi.searchFee})}" cssClass=" required" maxlength="7"
            /></li>

          <li><label for="scanFee">Scan Fee:</label> <s:textfield tabindex="14" id="searchFee"
              name="trackingRecordScreeningDpsFbi.scanFee"
              value="%{getText('format.currencyNoSymbol',{trackingRecordScreeningDpsFbi.scanFee})}" cssClass=" required" maxlength="7"
            /></li>

        </ol>
      </li>

      <li class="submit"><s:submit tabindex="15" value="Save" /></li>
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
