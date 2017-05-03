<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="ccl" uri="/WEB-INF/tags/ccl-tags.tld"%>
<%@ taglib prefix="display" uri="http://displaytag.sf.net"%>

<s:if test="boardMember != null and boardMember.id != null">
	<s:set var="legendText">Edit</s:set>
</s:if>
<s:else>
	<s:set var="legendText">Create</s:set>
</s:else>
<fieldset>
	<legend><s:property value="%{legendText}"/> Board Member</legend>
	<s:fielderror/>
	<s:form id="boardMemberForm" action="save-board-member" method="post" cssClass="ajaxify {target: '#boardMembersSection'} ccl-action-save">
		<s:hidden name="facilityId"/>
		<s:hidden name="boardMember.id"/>
		<ol class="fieldList">
			<li>
				<label><span class="redtext">* </span>Name:</label>
				<ol class="fieldGroup">
					<li>
						<label for="boardMemberFirstName">First:</label>
						<s:textfield id="boardMemberFirstName" name="boardMember.person.firstName" cssClass="name required"/>
					</li>
					<li>
						<label for="boardMemberMiddleName">Middle:</label>
						<s:textfield id="boardMemberMiddleName" name="boardMember.person.middleName" cssClass="optionalName"/>
					</li>
					<li>
						<label for="boardMemberLastName">Last:</label>
						<s:textfield id="boardMemberLastName" name="boardMember.person.lastName" cssClass="name required"/>
					</li>
				</ol>
			</li>
			<li>
				<label>Address:</label>
				<ccl:address id="boardMemberAddress" name="boardMember.person.address"/>
			</li>
			<li>
				<label for="boardMemberPhone">Phone:</label>
				<s:textfield id="boardMemberPhone" name="boardMember.person.homePhone" cssClass="phone"/>
			</li>
			<li>
				<label for="email">Email:</label>
				<s:textfield id="boardMemberEmail" name="boardMember.person.email" cssClass="email"/>
			</li>
			<li>
				<label><span class="redtext">* </span>Duration:</label>
				<ol class="fieldGroup">
					<li>
						<label for="boardMemberStartDate">Start Date:</label>
						<s:date id="boardMemberStartDateFormatted" name="boardMember.startDate" format="MM/dd/yyyy" />
						<s:textfield id="boardMemberStartDate" name="boardMember.startDate" value="%{boardMemberStartDateFormatted}" cssClass="required datepicker" maxlength="10"/>
					</li>
					<li>
						<label for="boardMemberEndDate">End Date:</label>
						<s:date id="boardMemberEndDateFormatted" name="boardMember.endDate" format="MM/dd/yyyy" />
						<s:textfield id="boardMemberEndDate" name="boardMember.endDate" value="%{boardMemberEndDateFormatted}" cssClass="datepicker" maxlength="10"/>
					</li>
				</ol>
			</li>
			
			<li class="submit">
				<s:submit name="save" value="Save"/>
				<s:url id="boardMemberEditCancelUrl" action="board-members-list" includeParams="false">
					<s:param name="facilityId" value="facilityId"/>
				</s:url>
				<s:a id="boardMemberEditCancel" href="%{boardMemberEditCancelUrl}" cssClass="ajaxify {target: '#boardMembersSection'}">
					Cancel
				</s:a>
			</li>
		</ol>
	</s:form>
	<div id="boardMembersSection">
		<s:include value="board_members_table.jsp"/>
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
