<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags"%>

<s:if test="trackingRecordScreeningMain != null and trackingRecordScreeningMain.id != null">
  <s:set var="legendText">Edit Main</s:set>
</s:if>
<s:else>
  <s:set var="legendText">Create New Main</s:set>
</s:else>
<fieldset>
  <legend>
    <s:property value="%{legendText}" />
  </legend>
  <s:fielderror />
  <s:form id="main-Form" action="save-trsMain" method="post" cssClass="ajaxify {target: '#mainBase'} ccl-action-save">
    <s:hidden name="screeningId" />
    <s:hidden name="trsMainId" value="%{trackingRecordScreeningMain.id}" />

    <ol class="fieldList ">
      <li class="column">
        <ol>
          <li>
          	<label for="dateReceived">Date Received:</label>
          	<s:date id="dateReceivedFormatted" name="trackingRecordScreeningMain.dateReceived" format="MM/dd/yyyy" />
          	<s:if test="isViewOnly()">
          		<s:textfield id="dateReceived" name="trackingRecordScreeningMain.dateReceived" value="%{dateReceivedFormatted}" maxlength="10" readonly="true" />
          	</s:if>
			<s:else>
				<s:textfield id="dateReceived" name="trackingRecordScreeningMain.dateReceived" value="%{dateReceivedFormatted}" cssClass="datepicker" maxlength="10" />
			</s:else>
          </li>
          <li>
          	<label for="calClearedDate">CAL Cleared:</label>
          	<s:date id="calClearedDateFormatted" name="trackingRecordScreeningMain.calClearedDate" format="MM/dd/yyyy" />
          	<s:if test="isViewOnly()">
          		<s:textfield id="calClearedDate" title="Criminal" name="trackingRecordScreeningMain.calClearedDate" value="%{calClearedDateFormatted}" maxlength="10" readonly="true" />
          	</s:if>
			<s:else>
				<s:textfield id="calClearedDate" title="Criminal" name="trackingRecordScreeningMain.calClearedDate" value="%{calClearedDateFormatted}" cssClass=" datepicker" maxlength="10" />
			</s:else>
          </li>
          <li>
          	<label for="lisaClearedDate">LISA Cleared:</label>
          	<s:date id="lisaClearedDateFormatted" name="trackingRecordScreeningMain.lisaClearedDate" format="MM/dd/yyyy" />
          	<s:if test="isViewOnly()">
          		<s:textfield title="Licensing Information System" id="lisaClearedDate" name="trackingRecordScreeningMain.lisaClearedDate" value="%{lisaClearedDateFormatted}" maxlength="10" readonly="true" />
          	</s:if>
			<s:else>
				<s:textfield title="Licensing Information System" id="lisaClearedDate" name="trackingRecordScreeningMain.lisaClearedDate" value="%{lisaClearedDateFormatted}" cssClass=" datepicker" maxlength="10" />
			</s:else>
          </li>
          <li>
          	<label for="amisClearedDate">AMIS Cleared:</label>
          	<s:date id="amisClearedDateFormatted" name="trackingRecordScreeningMain.amisClearedDate" format="MM/dd/yyyy" />
          	<s:if test="isViewOnly()">
          		<s:textfield title="Adult Management Information System" id="amisClearedDate" name="trackingRecordScreeningMain.amisClearedDate" value="%{amisClearedDateFormatted}" maxlength="10" readonly="true"/>
          	</s:if>
			<s:else>
				<s:textfield title="Adult Management Information System" id="amisClearedDate" name="trackingRecordScreeningMain.amisClearedDate" value="%{amisClearedDateFormatted}" cssClass=" datepicker" maxlength="10" />
			</s:else>
          </li>
          <li>
          	<label for="oscarClearedDate">OSCAR Cleared:</label>
          	<s:date id="oscarClearedDateFormatted" name="trackingRecordScreeningMain.oscarClearedDate" format="MM/dd/yyyy" />
          	<s:if test="isViewOnly()">
          		<s:textfield title="Out of State Child Abuse Registry" id="oscarClearedDate" name="trackingRecordScreeningMain.oscarClearedDate" value="%{oscarClearedDateFormatted}" maxlength="10" readonly="true" />
          	</s:if>
			<s:else>
				<s:textfield title="Out of State Child Abuse Registry" id="oscarClearedDate" name="trackingRecordScreeningMain.oscarClearedDate" value="%{oscarClearedDateFormatted}" cssClass=" datepicker" maxlength="10" />
			</s:else>
          </li>
          <li>
          	<label for="adamFbiClearedDate">ADAM/FBI Cleared:</label>
          	<s:date id="adamFbiClearedDateFormatted" name="trackingRecordScreeningMain.adamFbiClearedDate" format="MM/dd/yyyy" />
          	<s:if test="isViewOnly()">
          		<s:textfield id="adamFbiClearedDate" title="Adam Walsh" name="trackingRecordScreeningMain.adamFbiClearedDate" value="%{adamFbiClearedDateFormatted}" maxlength="10" readonly="true" />
          	</s:if>
			<s:else>
				<s:textfield id="adamFbiClearedDate" title="Adam Walsh" name="trackingRecordScreeningMain.adamFbiClearedDate" value="%{adamFbiClearedDateFormatted}" cssClass=" datepicker" maxlength="10" />
			</s:else>
          </li>
          <li>
          	<label for="approvalMailedDate">Approval Mailed:</label>
          	<s:date id="approvalMailedDateFormatted" name="trackingRecordScreeningMain.approvalMailedDate" format="MM/dd/yyyy" />
          	<s:if test="isViewOnly()">
          		<s:textfield id="approvalMailedDate" name="trackingRecordScreeningMain.approvalMailedDate" value="%{approvalMailedDateFormatted}" maxlength="10" readonly="true" />
          	</s:if>
			<s:else>
				<s:textfield id="approvalMailedDate" name="trackingRecordScreeningMain.approvalMailedDate" value="%{approvalMailedDateFormatted}" cssClass=" datepicker" maxlength="10" />
			</s:else>
          </li>
        </ol>
      </li>

	<security:authorize access="!hasRole('ROLE_ACCESS_PROFILE_VIEW')">
      <li class="column">
        <ol>
          <li>
          	<label for="toMisADate">To MIS/A:</label>
          	<s:date id="toMisADateFormatted" name="trackingRecordScreeningMain.toMisADate" format="MM/dd/yyyy" />
          	<s:textfield id="toMisADate" title="Adult Management Information System" name="trackingRecordScreeningMain.toMisADate" value="%{toMisADateFormatted}" cssClass=" datepicker" maxlength="10" />
          </li>
          <li>
          	<label for="backFromMisA">Back from MIS/A:</label>
          	<s:date id="backFromMisAFormatted" name="trackingRecordScreeningMain.backFromMisA" format="MM/dd/yyyy" />
          	<s:textfield title="Adult Management Information System" id="backFromMisA" name="trackingRecordScreeningMain.backFromMisA" value="%{backFromMisAFormatted}" cssClass=" datepicker" maxlength="10" />
          </li>
          <li>
          	<label for="toOscarDate">Date to OSCAR:</label>
          	<s:date id="toOscarDateFormatted" name="trackingRecordScreeningMain.toOscarDate" format="MM/dd/yyyy" />
          	<s:textfield title="Out of State Child Abuse Registry" id="toOscarDate" name="trackingRecordScreeningMain.toOscarDate" value="%{toOscarDateFormatted}" cssClass=" datepicker" maxlength="10" />
          </li>
          <li>
          	<label for="fpiDate">FPI letters tab:</label>
          	<s:date id="fpiDateFormatted" name="trackingRecordScreeningMain.fpiDate" format="MM/dd/yyyy" />
          	<s:textfield title="Failure to Provide Information" id="fpiDate" name="trackingRecordScreeningMain.fpiDate" value="%{fpiDateFormatted}" cssClass="datepicker" maxlength="10" />
          </li>
          <li>
          	<label for="naaDate">NAA letters tab:</label>
          	<s:date id="naaDateFormatted" name="trackingRecordScreeningMain.naaDate" format="MM/dd/yyyy" />
          	<s:textfield title="Notice of Agency Action" id="naaDate" name="trackingRecordScreeningMain.naaDate" value="%{naaDateFormatted}" cssClass="datepicker" maxlength="10" />
          </li>
        </ol>
      </li>
      <li class="clearfix"></li>
      <li class="column">
      	<label for="notes">Note:</label>
      	<s:textarea id="notes" name="trackingRecordScreeningMain.notes" />
        <div id="notesCharCount" class="charCount"></div>
      </li>
      <li class="column" style=" float:left;">
      	<label for="comments">Comments:</label>
      	<s:textarea id="comments" name="trackingRecordScreeningMain.comments" />
        <div id="commentsCharCount" class="charCount"></div>
      </li>
      <li class="clearfix"></li>

      <li class="submit"><s:submit value="Save" /></li>
    </security:authorize>
      
    </ol>
  </s:form>

</fieldset>

<script type="text/javascript">
  $("#notes").charCounter(200, {
    container : "#notesCharCount"
  });
  $("#comments").charCounter(200, {
    container : "#commentsCharCount"
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

