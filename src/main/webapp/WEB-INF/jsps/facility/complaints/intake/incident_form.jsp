<%@ taglib prefix="s" uri="/struts-tags"%>
<s:if test="incident != null and incident.id != null">
	<s:set var="legendText">Edit</s:set>
</s:if>
<s:else>
	<s:set var="legendText">Create</s:set>
</s:else>
<fieldset>
	<legend><s:property value="%{legendText}"/> Incident</legend>
	<s:fielderror/>
	<s:form id="incidentForm" action="save-incident" method="post" cssClass="ajaxify {target: '#incidentsSection'} ccl-action-save">
		<s:hidden name="facilityId"/>
		<s:hidden name="complaintId"/>
		<s:hidden name="incident.id"/>
		<ol class="fieldList">
			<li>
				<label>Incident:</label>
				<ol class="fieldGroup">
					<li>
						<label for="incidentDate">Date:</label>
						<s:date id="incidentDateFormatted" name="incident.date" format="MM/dd/yyyy"/>
						<s:textfield id="incidentDate" name="incident.date" value="%{incidentDateFormatted}" cssClass="datepicker" maxlength="10"/>
					</li>
					<li>
						<label for="incidentTime">Time:</label>
						<s:date id="incidentTimeFormatted" name="incident.date" format="hh:mm a"/>
						<s:textfield id="incidentTime" name="incidentTime" value="%{incidentTimeFormatted}" cssClass="time" maxlength="8"/> (HH:MM AM/PM)
					</li>
					<li class="clearLeft fieldMargin">
						<label for="dateDescription">Describe if date is unknown:</label>
						<s:textfield id="dateDescription" name="incident.dateDescription" cssClass="longName" maxlength="100"/>
					</li>
				</ol>
			<li>
				<label for="incidentDescription"><span class="redtext">* </span>Description:</label>
				<s:textfield id="incidentDescription" name="incident.description" cssClass="required longName" maxlength="250"/>
			</li>
			<li class="checkbox">
				<s:checkbox id="investigated" name="incident.investigable"/>
				<label for="investigated">This incident occurred within the past 6 weeks</label>
			</li>
			<li class="submit">
				<s:submit value="Save"/>
				<s:url id="incidentEditCancelUrl" action="incidents-list" includeParams="false">
					<s:param name="facilityId" value="facilityId"/>
					<s:param name="complaintId" value="complaintId"/>
				</s:url>
				<s:a href="%{incidentEditCancelUrl}" cssClass="ajaxify {target: '#incidentsSection'}">
					Cancel
				</s:a>
			</li>
		</ol>
	</s:form>
	<div id="incidentsListSection">
		<s:include value="incidents_table.jsp"/>
	</div>
</fieldset>

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

