<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<s:if test="action != null and action.id != null">
	<s:set var="legendText">Edit</s:set>
</s:if>
<s:else>
	<s:set var="legendText">Create</s:set>
</s:else>
<script type="text/javascript">
	initActionAlert();
</script>
<fieldset>
	<legend><s:property value="%{legendText}"/> Action Log Entry</legend>
	<s:fielderror/>
	<s:form id="actionForm" action="save-action" method="post" cssClass="ajaxify {target: '#actionLogSection'} ccl-action-save">
		<s:hidden name="facilityId"/>
		<s:hidden id="actionId" name="action.id"/>
		<ol class="fieldList">
			<li>
				<label for="actionDate"><span class="redtext">* </span>Action Date:</label>
				<s:date id="actionDateFormatted" name="action.actionDate" format="MM/dd/yyyy"/>
				<s:textfield id="actionDate" name="action.actionDate" value="%{actionDateFormatted}" cssClass="required datepicker" maxlength="10"/>
			</li>
			<li>
				<label for="actionTime"><span class="redtext">* </span>Action Time:</label>
				<s:date id="actionTimeFormatted" name="actionTime" format="hh:mm a"/>
				<s:textfield id="actionTime" name="actionTime" value="%{actionTimeFormatted}" cssClass="required time" maxlength="8"/> (HH:MM AM/PM)
			</li>
			<li>
				<label for="action" class="shortLabel"><span class="redtext">* </span>Action:</label>
				<s:select id="action" name="action.actionType" value="%{action.actionType.id}" list="actionTypes" listKey="id" listValue="value"
						  headerKey="-1" headerValue="- Select an Action -" cssClass="required"/>
			</li>
			<li class="alertSection" style="display: none;">
				<ol class="fieldGroup infoPanel">
					<li>
						<h1>Alerts that will be sent:</h1>
						<div class="value alertsText"></div>
					</li>
					<li class="clearLeft fieldMargin">
						<label for="dueDate">Due Date:</label>
						<s:date id="dueDateFormatted" name="dueDate" format="MM/dd/yyyy"/>
						<s:textfield id="dueDate" name="dueDate" value="%{dueDateFormatted}" cssClass="datepicker" maxlength="10"/>
					</li>
				</ol>
			</li>
			<security:authorize access="hasAnyRole('ROLE_SUPER_ADMIN')">
				<li>
					<label for="takenBy">Taken By:</label>
					<s:select id="takenBy" name="action.takenBy" value="%{action.takenBy.id}" list="actionTakers" listKey="id" listValue="firstAndLastName"
							  headerKey="-1" headerValue="- Select Action Taker -"/>
				</li>
			</security:authorize>
			<li>
				<label for="actionNote">Comments:</label>
				<s:textarea id="actionNote" name="action.note"/>
			</li>
			<li class="submit">
				<s:submit value="Save"/>
				<s:url id="actionEditCancelUrl" action="action-log-section" includeParams="false">
					<s:param name="facilityId" value="facilityId"/>
				</s:url>
				<s:a id="actionEditCancel" href="%{actionEditCancelUrl}" cssClass="ajaxify {target: '#actionLogSection'}">
					Cancel
				</s:a>
			</li>
		</ol>
	</s:form>
	<div id="actionsListSection" class="marginTop">
		<s:include value="actions_list.jsp"/>
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
