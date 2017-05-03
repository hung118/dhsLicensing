<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<s:if test="!status.name().equals('REGULATED') && !status.name().equals('IN_PROCESS')"><s:set var="licStyle">display: none;</s:set></s:if>
<s:if test="!status.name().equals('INACTIVE')"><s:set var="inactStyle">display: none;</s:set></s:if>
<fieldset>
	<legend>Edit Status Details</legend>
	<s:actionerror/>
	<s:fielderror/>
	<s:form id="facilityStatusForm" action="save-status" method="post" cssClass="ajaxify {target: '#statusSection'} ccl-action-save">
		<s:hidden name="facilityId"/>
		<ol class="fieldList">
			<s:if test="!statuses.isEmpty">
				<li>
					<label for="status"><span class="redtext">* </span>Status:</label>
					<s:select id="status" name="status" list="statuses" listKey="name()" listValue="label" cssClass="required"/>
				</li>
			</s:if>
			<s:else>
				<li>
					<div class="label">Status:</div>
					<div class="value"><s:property value="status.label"/></div>
				</li>
			</s:else>
			<li class="lic-field"<s:if test="#licStyle != null"> style="<s:property value="licStyle"/>"</s:if>>
				<s:if test="licensingSpecialists.isEmpty">
					<label>Licensing Specialist:</label>
					<span><s:property value="facility.licensingSpecialist.firstAndLastName"/></span>
				</s:if>
				<s:else>
					<label for="licensingSpecialist"><span class="redtext">*</span>Licensing Specialist:</label>
					<s:select id="licensingSpecialist" name="licensingSpecialist" value="licensingSpecialist.id" list="licensingSpecialists" 
							  listKey="id" listValue="firstAndLastName" headerKey="-1" headerValue="- Select a Specialist -" cssClass="required"/>
				</s:else>
			</li>
			<li class="lic-field"<s:if test="#licStyle != null"> style="<s:property value="licStyle"/>"</s:if>>
				<security:authorize access="hasAnyRole('ROLE_SUPER_ADMIN')">
					<label for="initialRegulationDate"><span class="redtext">* </span>Initial Regulation Date:</label>
					<s:date id="initialRegulationDateFormatted" name="initialRegulationDate" format="MM/dd/yyyy" />
					<s:textfield id="initialRegulationDate" name="initialRegulationDate"
								 value="%{initialRegulationDateFormatted}" cssClass="required datepicker" maxlength="10"/>
				</security:authorize>
				<security:authorize access="!hasAnyRole('ROLE_SUPER_ADMIN')">
					<div class="label">Initial Regulation Date:</div>
					<div class="value"><s:date name="facility.initialRegulationDate" format="MM/dd/yyyy"/></div>
				</security:authorize>
			</li>
			<s:if test="!facility.status.name().equals('INACTIVE') && !facility.status.name().equals('IN_PROCESS')">
				<li class="inact-field"<s:if test="#inactStyle != null"> style="<s:property value="inactStyle"/>"</s:if>>
					<label for="reason"><span class="redtext">* </span>Reason:</label>
					<s:select id="reason" name="reason" value="reason.id" list="deactivationReasons" listKey="id" listValue="value"
						headerKey="-1" headerValue="- Select a Deactivation Reason -" cssClass="required"/>
				</li>
				<li class="inact-field"<s:if test="#inactStyle != null"> style="<s:property value="inactStyle"/>"</s:if>>
					<label for="effectiveDate">Effective Date:</label>
					<s:date id="effectiveDateFormatted" name="effectiveDate" format="MM/dd/yyyy" />
					<s:textfield id="effectiveDate" name="effectiveDate" value="%{effectiveDateFormatted}" cssClass="datepicker" maxlength="10"/>
				</li>
			</s:if>
			<li class="submit">
				<s:submit value="Save"/>
				<s:url id="cancelUrl" action="status-section">
					<s:param name="facilityId" value="facilityId"/>
				</s:url>
				<s:a href="%{cancelUrl}" cssClass="ajaxify {target: '#statusSection'}">
					Cancel
				</s:a>
			</li>
		</ol>
	</s:form>
</fieldset>

<script type="text/javascript">
	$("#status").change(function() {
		if ($(this).val() == 'REGULATED' || $(this).val() == 'IN_PROCESS') {
			$(".lic-field").show();
			$(".inact-field").hide();
		} else if ($(this).val() == 'INACTIVE') {
			$(".lic-field").hide();
			$(".inact-field").show();
		} else {
			$(".lic-field").hide();
			$(".inact-field").hide();
		}
	});
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
