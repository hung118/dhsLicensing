<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="ccl" uri="/WEB-INF/tags/ccl-tags.tld"%>
<%@ taglib prefix="display" uri="http://displaytag.sf.net"%>

<s:if test="director != null and director.id != null">
	<s:set var="legendText">Edit</s:set>
</s:if>
<s:else>
	<s:set var="legendText">Assign</s:set>
</s:else>
<fieldset>
	<legend><s:property value="%{legendText}"/> Director</legend>
	<s:actionerror/>
	<s:fielderror/>
	<s:form id="directorForm" action="save-director" method="post" cssClass="ajaxify {target: '#directorsSection'} ccl-action-save">
		<s:hidden name="facilityId"/>
		<s:hidden name="director.id"/>
		<s:hidden name="personId"/>
		<ol class="fieldList">
			<li>
				<div class="label">Name:</div>
				<div class="value"><s:property value="director.person.firstAndLastName"/></div>
			</li>
			<li>
				<s:checkbox id="first" name="first"/>
				<label for="first">First Director</label>
			</li>
			<li>
				<label><span class="redtext">* </span>Duration:</label>
				<ol class="fieldGroup">
					<li>
						<label for="directorStartDate">Start Date:</label>
						<s:date id="directorStartDateFormatted" name="director.startDate" format="MM/dd/yyyy" />
						<s:textfield id="directorStartDate" name="director.startDate" value="%{directorStartDateFormatted}" cssClass="required datepicker" maxlength="10"/>
					</li>
					<li>
						<label for="directorEndDate">End Date:</label>
						<s:date id="directorEndDateFormatted" name="director.endDate" format="MM/dd/yyyy" />
						<s:textfield id="directorEndDate" name="director.endDate" value="%{directorEndDateFormatted}" cssClass="datepicker" maxlength="10"/>
					</li>
				</ol>
			</li>
			
			<li class="submit">
				<s:submit value="Save"/>
				<s:url id="directorEditCancelUrl" action="directors-list" includeParams="false">
					<s:param name="facilityId" value="facilityId"/>
				</s:url>
				<s:a id="directorEditCancel" href="%{directorEditCancelUrl}" cssClass="ajaxify {target: '#directorsSection'}">
					Cancel
				</s:a>
			</li>
		</ol>
	</s:form>
	<div>
		<s:include value="directors_table.jsp"/>
	</div>
</fieldset>

<script type="text/javascript">
	$("#directorAttachment").ccl("tableDeleteNew");
</script>

<script type="text/javascript">
	// *** Short key ctrl-q for current date on fields that have 'Date' in id attribute. ***
	$('[id $= "Date"]').keydown(function(e) {
        //CTRL + Q keydown for current date. Key codes: http://www.cambiaresearch.com/articles/15/javascript-char-codes-key-codes
        if(e.ctrlKey && e.keyCode == 81){
            var currentDate = new Date();
            $(this).val(currentDate.format("mm/dd/yyyy"));
        }
    });
</script>

