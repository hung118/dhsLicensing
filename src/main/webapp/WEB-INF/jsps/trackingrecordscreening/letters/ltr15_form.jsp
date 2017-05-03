<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="ccl" uri="/WEB-INF/tags/ccl-tags.tld"%>

<s:if test="screeningLtr15 != null and screeningLtr15.id != null">
	<s:set var="legendText">Edit 15 Day Letter</s:set>
</s:if>
<s:else>
	<s:set var="legendText">Create New 15 Day Letter</s:set>
</s:else>
<fieldset>
	<legend><s:property value="%{legendText}"/></legend>
	<s:actionerror/>
	<s:fielderror/>
	<s:form id="ltr15Form" action="save-ltr15" method="post" cssClass="ajaxify {target: '#ltr15Section'} ccl-action-save">
		<s:hidden name="screeningId"/>
		<s:hidden name="ltr15Id" value="%{screeningLtr15.id}" />
		<s:hidden name="screeningLtr15.id"/>
		<s:hidden name="screeningLtr15.trackingRecordScreening.id"/>
    <ol class="fieldList">
    <li class="column">
		<ol>
			<li>
				<label for="ltr15IssuedDate"><span class="redtext">* </span>Issued Date:</label>
				<s:date id="ltr15IssuedDateFormatted" name="screeningLtr15.issuedDate" format="MM/dd/yyyy" />
				<s:textfield id="ltr15IssuedDate" name="screeningLtr15.issuedDate" value="%{ltr15IssuedDateFormatted}" cssClass="required datepicker" maxlength="10"/>       
			</li>
			<li>
				<label for="ltr15Reason"><span class="redtext">* </span>Reason:</label>
				<s:select id="ltr15Reason" name="reason" value="reason.id" list="reasons" listKey="id" listValue="value" headerKey="-1" headerValue="- Select a Reason -" cssClass="required"/>
			</li>
			<li>
				<label for="ltr15Resolved">Resolved:</label>
				<s:checkbox id="ltr15Resolved" name="screeningLtr15.resolved"/>
			</li>
			<li class="submit">
				<s:submit value="Save"/>
				<s:url id="ltr15CancelUrl" action="list-ltr15" includeParams="false">
					<s:param name="screeningId" value="screeningId"/>
				</s:url>
				<s:a href="%{ltr15CancelUrl}" cssClass="ajaxify {target: '#ltr15Section'}">
					Cancel
				</s:a>
			</li>
		</ol>
    </li>
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


