<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="ccl" uri="/WEB-INF/tags/ccl-tags.tld"%>

<s:if test="screeningRequest != null and screeningRequest.id != null">
	<s:set var="legendText">Edit Request</s:set>
</s:if>
<s:else>
	<s:set var="legendText">Create New Request</s:set>
</s:else>
<fieldset>
	<legend><s:property value="%{legendText}"/></legend>
	<s:actionerror/>
	<s:fielderror/>
	<s:form id="requestForm" action="save-request" method="post" cssClass="ajaxify {target: '#requestsSection'} ccl-action-save">
		<s:hidden name="screeningId"/>
		<s:hidden name="requestId" value="%{screeningRequest.id}" />
		<s:hidden name="screeningRequest.id"/>
		<s:hidden name="screeningRequest.trackingRecordScreening.id"/>
    <ol class="fieldList">
    	<li class="widecolumn">
			<ol >
				<li>
					<label for="requestsCountry"><span class="redtext">* </span>Country:</label>
					<s:textfield id="requestsCountry" name="screeningRequest.country" cssClass="required" />
				</li>
				<li>
					<label for="requestsFromDate">From Date:</label>
					<s:date id="requestsFromDateFormatted" name="screeningRequest.fromDate" format="MM/dd/yyyy" />
					<s:textfield id="requestsFromDate" name="screeningRequest.fromDate" value="%{requestsFromDateFormatted}" cssClass="required datepicker" maxlength="10"/>       
				</li>
				<li>
					<label for="requestsToDate">To Date:</label>
					<s:date id="requestsToDateFormatted" name="screeningRequest.toDate" format="MM/dd/yyyy" />
					<s:textfield id="requestsToDate" name="screeningRequest.toDate" value="%{requestsToDateFormatted}" cssClass="required datepicker" maxlength="10"/>       
				</li>
				<li>
					<label for="requestsReceivedDate"><span class="redtext">* </span>Date Received:</label>
					<s:date id="requestsReceivedDateFormatted" name="screeningRequest.receivedDate" format="MM/dd/yyyy" />
					<s:textfield id="requestsReceivedDate" name="screeningRequest.receivedDate" value="%{requestsReceivedDateFormatted}" cssClass="required datepicker" maxlength="10"/>       
				</li>
				<li>
					<label for="requestsOcDocument">OC Document:</label>
					<s:textfield id="requestsOcDocument" name="screeningRequest.ocDocument" cssClass="required widefield" />
				</li>
				<li class="submit">
					<s:submit value="Save"/>
					<s:url id="requestsCancelUrl" action="list-requests" includeParams="false">
						<s:param name="screeningId" value="screeningId"/>
					</s:url>
					<s:a href="%{requestsCancelUrl}" cssClass="ajaxify {target: '#requestsSection'}">
						Cancel
					</s:a>
				</li>
			</ol>
    	</li>
    </ol>
	</s:form>
</fieldset>
<script type="text/javascript">
	var countryName = "<s:property value='screeningRequest.country'/>";
	$("#requestsCountry").countryautocomplete({
		countryName: countryName
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

