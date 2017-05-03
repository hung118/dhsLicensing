<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@ taglib prefix="ccl" uri="/WEB-INF/tags/ccl-tags.tld"%>
<script type="text/javascript">
  $(function () {
    $(":text, textarea").each(function () {
      if ($(this).val().length > 0) {
        $(this).css('color', 'red');
      }
    });

    $(":text, textarea").change(function () {
      $(this).css('color', 'black');
    });

    $("#birthdate").datepicker({
      maxDate : "-18Y",
      yearRange : "c-90:c+0",
      changeMonth : true,
      changeYear : true,
      showOtherMonths : true,
      selectOtherMonths : true,
      constrainInput : true,
      dateFormat : 'mm/dd/yy',
      showOn : "button",
      buttonImage : context + "/images/calbtn.gif",
      buttonImageOnly : true
    });

    $("#birthdate").mask("99/99/9999");

  });
</script>
<fieldset>
  <legend>Clone Tracking Record Screening</legend>
  <s:actionerror />
  <s:fielderror />
  <s:form id="trsForm" action="saveCloneTrs" namespace="/trackingrecordscreening" method="post" cssClass="ccl-action-save">
    <s:hidden name="screeningId" value="%{trsSource.id}" />
    <s:hidden name="personId" value="%{personId}" />
    <s:hidden name="trsMain" value="%{trsMain}" />
    <s:hidden name="trsDpsFbi" value="%{trsDpsFbi}" />
    <s:hidden name="trsRequest" value="%{trsRequest}" />
    <s:hidden name="trsLetter" value="%{trsLetter}" />
    <s:hidden name="trsCbsComm" value="%{trsCbsComm}" />
    <s:hidden name="trsMisComm" value="%{trsMisComm}" />
    <s:hidden name="trsOscar" value="%{trsOscar}" />

    <div id="personSection">
      <fieldset>
        <legend>Person Section</legend>
        <ol class="fieldList">
          <li>
            <label><span class="redtext">* </span>Identifiers:</label>
            <ol class="fieldGroup">
              <li>
                <label for="birthdate">DOB:</label> 
                <s:date id="birthdateDateFormatted" name="trsSource.birthday" format="MM/dd/yyyy" /> 
                <s:textfield id="birthdate" name="trsSource.birthday" value="%{birthdateDateFormatted}" maxlength="10" size="7" />
              </li>
              <li>
                <label for="ssnLastFour">SSN Last four:</label> 
                <s:textfield id="ssnLastFour" name="trsSource.ssnLastFour" cssClass="required" maxlength="4" size="4" />
              </li>
            </ol>
          </li>
          <li>
            <label><span class="redtext">* </span>Name:</label>
            <ol class="fieldGroup">
              <li>
                <label for="firstName">First Name:</label> 
                <s:textfield id="firstName" name="trsSource.firstName" cssClass="required name" cssStyle="width:300px !important;" />
              </li>
              <li>
                <label for="lastName">Last Name:</label> 
                <s:textfield id="lastName" name="trsSource.lastName" cssClass="required name" cssStyle="width:300px !important;" />
              </li>
              <li class="clearLeft fieldMargin" style="width:300px !important;"></li>
              <li class="clearLeft fieldMargin">
                <label for="personAliases">Alias:</label> 
                <s:textfield id="personAliases" name="trsSource.personAliases" cssStyle="width:300px !important;" />
              </li>
            </ol>
          </li>
          <li>
            <label for="provider"><span class="redtext">* </span>Requesting Facility:</label> 
            <s:textfield id="provider" name="trsSource.facility" value="%{trsSource.facility.id}" cssClass="required" />
          </li>
        </ol>
      </fieldset>
    </div>

    <s:if test="trsSource.trsMain != null">
      <div id="mainSection">
        <fieldset>
          <legend>Main Section</legend>
          <ol class="fieldList">
            <li class="column">
              <ol>
                <li>
                    <label for="dateReceived">Date Received:</label> 
                    <s:date id="dateReceivedFormatted" name="trsSource.trsMain.dateReceived" format="MM/dd/yyyy" /> 
                    <s:textfield id="dateReceived" name="trsSource.trsMain.dateReceived" value="%{dateReceivedFormatted}" cssClass="datepicker" maxlength="10"/>
                </li>
                <li>
                    <label for="calClearedDate">CAL Cleared:</label> 
                    <s:date id="calClearedDateFormatted" name="trsSource.trsMain.calClearedDate" format="MM/dd/yyyy" /> 
                    <s:textfield id="calClearedDate" title="Criminal" name="trsSource.trsMain.calClearedDate" value="%{calClearedDateFormatted}" cssClass=" datepicker" maxlength="10" />
                </li>
                <li>
                    <label for="lisaClearedDate">LISA Cleared:</label> 
                    <s:date id="lisaClearedDateFormatted" name="trsSource.trsMain.lisaClearedDate" format="MM/dd/yyyy" /> 
                    <s:textfield title="Licensing Information System" id="lisaClearedDate" name="trsSource.trsMain.lisaClearedDate" value="%{lisaClearedDateFormatted}" cssClass=" datepicker" maxlength="10" />
                </li>
                <li>
                    <label for="amisClearedDate">AMIS Cleared:</label> 
                    <s:date id="amisClearedDateFormatted" name="trsSource.trsMain.amisClearedDate" format="MM/dd/yyyy" /> 
                    <s:textfield title="Adult Management Information System" id="amisClearedDate" name="trsSource.trsMain.amisClearedDate" value="%{amisClearedDateFormatted}" cssClass=" datepicker" maxlength="10" />
                </li>
                <li>
                    <label for="oscarClearedDate">OSCAR Cleared:</label> 
                    <s:date id="oscarClearedDateFormatted" name="trsSource.trsMain.oscarClearedDate" format="MM/dd/yyyy" /> 
                    <s:textfield title="Out of State Child Abuse Registry" id="oscarClearedDate" name="trsSource.trsMain.oscarClearedDate" value="%{oscarClearedDateFormatted}" cssClass=" datepicker" maxlength="10" />
                </li>
                <li>
                    <label for="adamFbiClearedDate">ADAM/FBI Cleared:</label> 
                    <s:date id="adamFbiClearedDateFormatted" name="trsSource.trsMain.adamFbiClearedDate" format="MM/dd/yyyy" /> 
                    <s:textfield id="adamFbiClearedDate" title="Adam Walsh" name="trsSource.trsMain.adamFbiClearedDate" value="%{adamFbiClearedDateFormatted}" cssClass=" datepicker" maxlength="10" />
                </li>
                <li>
                    <label for="approvalMailedDate">Approval Mailed:</label> 
                    <s:date id="approvalMailedDateFormatted" name="trsSource.trsMain.approvalMailedDate" format="MM/dd/yyyy" /> 
                    <s:textfield id="approvalMailedDate" name="trsSource.trsMain.approvalMailedDate" value="%{approvalMailedDateFormatted}" cssClass=" datepicker" maxlength="10" />
                </li>
              </ol>
            </li>

            <li class="column">
              <ol>
                <li>
                    <label for="toMisADate">To MIS/A:</label> 
                    <s:date id="toMisADateFormatted" name="trsSource.trsMain.toMisADate" format="MM/dd/yyyy" /> 
                    <s:textfield id="toMisADate" title="Adult Management Information System" name="trsSource.trsMain.toMisADate" value="%{toMisADateFormatted}" cssClass=" datepicker" maxlength="10" />
                </li>
                <li>
                    <label for="backFromMisA">Back from MIS/A:</label> 
                    <s:date id="backFromMisAFormatted" name="trsSource.trsMain.backFromMisA" format="MM/dd/yyyy" /> 
                    <s:textfield title="Adult Management Information System" id="backFromMisA" name="trsSource.trsMain.backFromMisA" value="%{backFromMisAFormatted}" cssClass=" datepicker" maxlength="10" />
                </li>
                <li>
                    <label for="toOscarDate">Date to OSCAR:</label> 
                    <s:date id="toOscarDateFormatted" name="trsSource.trsMain.toOscarDate" format="MM/dd/yyyy" /> 
                    <s:textfield title="Out of State Child Abuse Registry" id="toOscarDate" name="trsSource.trsMain.toOscarDate" value="%{toOscarDateFormatted}" cssClass=" datepicker" maxlength="10" />
                </li>
                <li>
                    <label for="fpiDate">FPI letters tab:</label> 
                    <s:date id="fpiDateFormatted" name="trsSource.trsMain.fpiDate" format="MM/dd/yyyy" /> 
                    <s:textfield title="Failure to Provide Information" id="fpiDate" name="trsSource.trsMain.fpiDate" value="%{fpiDateFormatted}" cssClass="datepicker" maxlength="10" />
                </li>
                <li>
                    <label for="naaDate">NAA letters tab:</label> 
                    <s:date id="naaDateFormatted" name="trsSource.trsMain.naaDate" format="MM/dd/yyyy" /> 
                    <s:textfield title="Notice of Agency Action" id="naaDate" name="trsSource.trsMain.naaDate" value="%{naaDateFormatted}" cssClass="datepicker" maxlength="10" />
                </li>
              </ol>
            </li>

            <li class="clearfix"></li>
            <li class="column">
                <label for="notes">Note:</label> 
                <s:textarea id="notes" name="trsSource.trsMain.notes" />
                <div id="notesCharCount" class="charCount" style="margin-left:1em;"></div>
            </li>
            <li class="column" style=" float:left;">
                <label for="comments">Comments:</label> 
                <s:textarea id="comments" name="trsSource.trsMain.comments" />
                <div id="commentsCharCount" class="charCount" style="margin-left:1em;"></div>
            </li>
            <li class="clearfix"></li>
          </ol>
        </fieldset>
      </div>
    </s:if>
    <s:if test="trsSource.trsDpsFbi != null">
      <div id="dpsfbiSection">
        <fieldset>
          <legend>DPS/FBI Section</legend>
          <ol class="fieldList ">
            <li class="column">
              <ol>
                <li>
                    <label for="firstFbiRequestDate">1st FBI Req:</label> 
                    <s:date id="firstFbiRequestFormatted" name="trsSource.trsDpsFbi.firstFbiRequestDate" format="MM/dd/yyyy" /> 
                    <s:textfield id="firstFbiRequestDate" name="trsSource.trsDpsFbi.firstFbiRequestDate" value="%{firstFbiRequestFormatted}" cssClass="datepicker" maxlength="10" />
                </li>
                <li>
                    <label for="secondFbiRequestDate">2nd FBI Req:</label> 
                    <s:date id="secondFbiRequestDateFormatted" name="trsSource.trsDpsFbi.secondFbiRequestDate" format="MM/dd/yyyy" /> 
                    <s:textfield id="secondFbiRequestDate" name="trsSource.trsDpsFbi.secondFbiRequestDate" value="%{secondFbiRequestDateFormatted}" cssClass=" datepicker" maxlength="10" />
                </li>
                <li>
                    <label for="fbiNameCheckDate">FBI Name Check:</label> 
                    <s:date id="fbiNameCheckDateFormatted" name="trsSource.trsDpsFbi.fbiNameCheckDate" format="MM/dd/yyyy" /> 
                    <s:textfield id="fbiNameCheckDate" name="trsSource.trsDpsFbi.fbiNameCheckDate" value="%{fbiNameCheckDateFormatted}" cssClass=" datepicker" maxlength="10" />
                </li>
                <li>
                    <label for="livescanDate">Livescan:</label> 
                    <s:date id="livescanDateFormatted" name="trsSource.trsDpsFbi.livescanDate" format="MM/dd/yyyy" /> 
                    <s:textfield id="livescanDate" name="trsSource.trsDpsFbi.livescanDate" value="%{livescanDateFormatted}" cssClass=" datepicker" maxlength="10" />
                </li>
                <li>
                    <label for="toDpsForVerificationDate">To DPS for Verification:</label> 
                    <s:date id="toDpsForVerificationDateFormatted" name="trsSource.trsDpsFbi.toDpsForVerificationDate" format="MM/dd/yyyy" /> 
                    <s:textfield id="toDpsForVerificationDate" name="trsSource.trsDpsFbi.toDpsForVerificationDate" value="%{toDpsForVerificationDateFormatted}" cssClass=" datepicker" maxlength="10" />
                </li>
                <li>
                    <label for="dpsVerifiedDate">Back from MIS/A:</label> 
                    <s:date id="dpsVerifiedDateFormatted" name="trsSource.trsDpsFbi.dpsVerifiedDate" format="MM/dd/yyyy" /> 
                    <s:textfield id="dpsVerifiedDate" name="trsSource.trsDpsFbi.dpsVerifiedDate" value="%{dpsVerifiedDateFormatted}" cssClass=" datepicker" maxlength="10" />
                </li>
              </ol>
            </li>

            <li class="column">
              <ol>
                <li>
                    <label for="firstRejectedDate">1st Rejected:</label> 
                    <s:date id="firstRejectedDateFormatted" name="trsSource.trsDpsFbi.firstRejectedDate" format="MM/dd/yyyy" /> 
                    <s:textfield id="firstRejectedDate" name="trsSource.trsDpsFbi.firstRejectedDate" value="%{firstRejectedDateFormatted}" cssClass=" datepicker" maxlength="10" />
                </li>
                <li>
                    <label for="secondRejectedDate">2nd Rejected:</label> 
                    <s:date id="secondRejectedDateFormatted" name="trsSource.trsDpsFbi.secondRejectedDate" format="MM/dd/yyyy" /> 
                    <s:textfield id="secondRejectedDate" name="trsSource.trsDpsFbi.secondRejectedDate" value="%{secondRejectedDateFormatted}" cssClass=" datepicker" maxlength="10" />
                </li>
                <li>
                    <label for="receivedFromFbiDate">Received from FBI:</label> 
                    <s:date id="receivedFromFbiDateFormatted" name="trsSource.trsDpsFbi.receivedFromFbiDate" format="MM/dd/yyyy" /> 
                    <s:textfield id="receivedFromFbiDate" name="trsSource.trsDpsFbi.receivedFromFbiDate" value="%{receivedFromFbiDateFormatted}" cssClass=" datepicker" maxlength="10" />
                </li>
                <li>
                    <label for="billing">Billing:</label> 
                    <s:select id="billing" name="trsSource.trsDpsFbi.billing" value="trsSource.trsDpsFbi.billing.id" list="billingTypes" listKey="id" listValue="value" headerKey="" headerValue="- Select type -" />
                </li>
                <li>
                    <label for="moNumber">MO#:</label> 
                    <s:textfield id="moNumber" name="trsSource.trsDpsFbi.moNumber" cssClass=" required" maxlength="100" />
                </li>
                <li>
                    <label for="issuedBy">Issued By:</label> 
                    <s:textfield id="issuedBy" name="trsSource.trsDpsFbi.issuedBy" cssClass=" required" maxlength="100" />
                </li>
                <li>
                    <label for="searchFee">Search Fee:</label> 
                    <s:textfield id="searchFee" name="trsSource.trsDpsFbi.searchFee" cssClass=" required" maxlength="7" />
                </li>
                <li>
                    <label for="scanFee">Scan Fee:</label> 
                    <s:textfield id="scanFee" name="trsSource.trsDpsFbi.scanFee" cssClass=" required" maxlength="7" />
                </li>
              </ol>
            </li>
          </ol>
        </fieldset>
      </div>
    </s:if>

    <s:if test="request != null">
      <div id="requestSection">
        <fieldset>
          <legend>Request Section</legend>
          <ol class="fieldList medium">
            <li>
                <label for="requestsCountry"><span class="redtext">* </span>Country:</label> 
                <s:textfield id="requestsCountry" name="request.country" cssClass="required" />
            </li>
            <li>
                <label for="requestsFromDate"><span class="redtext">* </span>From Date:</label> 
                <s:date id="requestsFromDateFormatted" name="request.fromDate" format="MM/dd/yyyy" /> 
                <s:textfield id="requestsFromDate" name="request.fromDate" value="%{requestsFromDateFormatted}" cssClass="required datepicker" maxlength="10" />
            </li>
            <li>
                <label for="requestsToDate"><span class="redtext">* </span>To Date:</label> 
                <s:date id="requestsToDateFormatted" name="request.toDate" format="MM/dd/yyyy" /> 
                <s:textfield id="requestsToDate" name="request.toDate" value="%{requestsToDateFormatted}" cssClass="required datepicker" maxlength="10" />
            </li>
            <li>
                <label for="requestsReceivedDate"><span class="redtext">* </span>Date Received:</label> 
                <s:date id="requestsReceivedDateFormatted" name="request.receivedDate" format="MM/dd/yyyy" /> 
                <s:textfield id="requestsReceivedDate" name="request.receivedDate" value="%{requestsReceivedDateFormatted}" cssClass="required datepicker" maxlength="10" />
            </li>
            <li>
                <label for="requestsOcDocument">OC Document:</label>
                <s:textfield id="requestsOcDocument" name="request.ocDocument" cssClass="required widefield" />
            </li>
          </ol>
        </fieldset>
      </div>
      <script type="text/javascript">
          var countryName = "<s:property value='request.country'/>";
          $("#requestsCountry").countryautocomplete({
              countryName: countryName
          });
      </script>
    </s:if>
    
    <s:if test="trsSource.cbsComm != null">
      <div id="cbsSection">
        <fieldset>
          <legend>CBS Comm Section</legend>
          <ol class="fieldList medium">
            <li>
                <label for="cbsCommitteeDecision">CBS Committee Decision:</label> 
                <s:select id="cbsCommitteeDecision" name="trsSource.cbsComm.cbsCommitteeDecision" value="trsSource.cbsComm.cbsCommitteeDecision" list="YesNoList" listKey="value" listValue="displayName" headerKey="" headerValue="- Select -" />
            </li>
            <li>
            <label for="cbsDecisionDate">Decision Date:</label> 
            <s:date id="cbsDecisionDateFormatted" name="trsSource.cbsComm.decisionDate" format="MM/dd/yyyy" /> 
            <s:textfield id="cbsDecisionDate" name="trsSource.cbsComm.decisionDate" value="%{cbsDecisionDateFormatted}" cssClass="datepicker" maxlength="10" />
            </li>
            <li>
                <label for="cbsOahRequestDate">OAH Request:</label> 
                <s:date id="cbsOahRequestDateFormatted" name="trsSource.cbsComm.oahRequestDate" format="MM/dd/yyyy" /> 
                <s:textfield id="cbsOahRequestDate" name="trsSource.cbsComm.oahRequestDate" value="%{cbsOahRequestDateFormatted}" cssClass="datepicker" maxlength="10" />
            </li>
            <li>
                <label for="cbsOahDecision">OAH Decision:</label> 
                <s:select id="cbsOahDecision" name="trsSource.cbsComm.oahDecision" value="trsSource.cbsComm.oahDecision.id" list="oahDecisions" listKey="id" listValue="value" headerKey="-1" headerValue="- Select -" />
            </li>
            <li>
                <label for="cbsComReasons" title="Committee Reasons">Com Reasons:</label> 
                <s:textarea id="cbsComReasons" name="trsSource.cbsComm.comReasons" />
                <div id="cbsComReasonsCharCount" class="charCount" style="margin-left:11em;"></div>
            </li>
          </ol>
        </fieldset>
      </div>
    </s:if>

    <s:if test="trsSource.misComm != null">
      <div id="misSection">
        <fieldset>
          <legend>MIS Comm Section</legend>
          <ol class="fieldList medium">
            <li>
                <label for="misCommDecision">MIS Committee Decision:</label> 
                <s:select title="Comprehensive Review Committee" id="misCommitteeDecision" name="trsSource.misComm.misCommDecision" list="YesNoList" listKey="value" listValue="displayName" headerKey="" headerValue="- Select -" />
            </li>
            <li>
                <label for="misCommDate">Decision Date:</label> 
                <s:date id="misCommDateFormatted" name="trsSource.misComm.misCommDate" format="MM/dd/yyyy" /> 
                <s:textfield id="misCommDate" name="trsSource.misComm.misCommDate" value="%{misCommDateFormatted}" cssClass=" datepicker" maxlength="10" />
            </li>
            <li>
                <label for="oahRequestDate">OAH Request:</label> 
                <s:date id="oahRequestDateFormatted" name="trsSource.misComm.oahRequestDate" format="MM/dd/yyyy" /> 
                <s:textfield id="oahRequestDate" name="trsSource.misComm.oahRequestDate" value="%{oahRequestDateFormatted}" cssClass=" datepicker" maxlength="10" />
            </li>
            <li>
                <label for="misOahDecision">OAH Decision:</label> 
                <s:select title="Office of Administrative Hearings" id="misOahDecision" name="trsSource.misComm.oahDecision" value="trsSrouce.misComm.oahDecision.id" list="oahDecisions" listKey="id" ListValue="value" headerKey="-1" headerValue="- Select -" />
            </li>
            <li class="fieldMargin" style="float:left;">
                <label for="commNotes">Comm Reasons:</label> 
                <s:textarea id="commNotes" name="trsSource.misComm.notes" />
                <div id="commNotesCharCount" class="charCount" style="margin-left:11em;"></div>
            </li>
          </ol>
        </fieldset>
      </div>
    </s:if>

    <s:if test="oscar != null">
      <div id="oscarSection">
        <fieldset>
          <legend>Oscar Section</legend>
          <ol class="fieldList medium">
            <li>
                <label for="oscarDate"><span class="redtext">* </span>Date:</label> 
                <s:date id="oscarDateFormatted" name="oscar.oscarDate" format="MM/dd/yyyy" /> 
                <s:textfield id="oscarDate" name="oscar.oscarDate" value="%{oscarDateFormatted}" cssClass=" datepicker" maxlength="10" />
            </li>
            <li>
                <label for="oscarType">Type:</label> 
                <s:select id="oscarType" name="oscar.oscarType" value="oscar.oscarType.id" list="oscarTypes" listKey="id" listValue="value" headerKey="" headerValue="- Select type -" />
            </li>
            <li>
                <label for="state">State:</label> 
                <s:textfield id="state" name="oscar.state" cssClass="ccl-address-state state" maxlength="2" />
            </li>
            <li>
                <label for="caseNumber">Case #:</label> 
                <s:textfield id="caseNumber" name="oscar.caseNumber" maxlength="100" />
            </li>
            <li class="fieldMargin" style="float:left;">
                <label for="notes">OSCAR Notes:</label> 
                <s:textarea id="notes" name="oscar.notes" />
            </li>
          </ol>
        </fieldset>
      </div>
    </s:if>
    <ol>
      <li class="submit"><s:submit value="Save" /></li>
    </ol>

  </s:form>
  <script type="text/javascript">
      $("#provider").facilityautocomplete({
        facilityId : facilityId
      });

      var countryName = "<s:property value='request.country'/>";
      $("#requestsCountry").countryautocomplete({
        countryName : countryName
      });

      $("#notes").charCounter(2000, {
        container : "#notesCharCount"
      });
      $("#comments").charCounter(2000, {
        container : "#commentsCharCount"
      });

      $("#misCommForm #commNotes").charCounter(4000, {
        container : "#misCommForm #commNotesCharCount"
      });
    </script>
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

<script type="text/javascript">
	$("#dateReceived").keydown(function(e) {
	    if(e.ctrlKey && e.keyCode == 81){
			var currentDate = new Date();
			$(this).val(currentDate.format("mm/dd/yyyy"));
		}
	});
	
	$("#backFromMisA").keydown(function(e) {
	    if(e.ctrlKey && e.keyCode == 81){
			var currentDate = new Date();
			$(this).val(currentDate.format("mm/dd/yyyy"));
		}
	});
</script>

