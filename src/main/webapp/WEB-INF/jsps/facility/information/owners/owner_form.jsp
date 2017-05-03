<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="ccl" uri="/WEB-INF/tags/ccl-tags.tld"%>
<s:if test="owner != null and owner.id != null">
	<s:set var="legendText">Edit</s:set>
</s:if>
<s:else>
	<s:set var="legendText">Create</s:set>
</s:else>
<fieldset>
	<legend><s:property value="%{legendText}"/> Owner</legend>
	<s:fielderror/>
	<s:form id="ownerForm" action="save-owner" method="post" cssClass="ajaxify {target: '#ownersSection'} ccl-action-save">
		<s:hidden name="facilityId"/>
		<s:hidden name="owner.id"/>
		<ol class="fieldList">
			<li>
				<s:checkbox id="primary" name="primary"/>
				<label for="primary">Primary Owner</label>
			</li>
			<li>
				<label><span class="redtext">* </span>Name:</label>
				<ol class="fieldGroup">
					<li>
						<label for="ownerFirstName">First:</label>
						<s:textfield id="ownerFirstName" name="owner.person.firstName" cssClass="name required"/>
					</li>
					<li>
						<label for="ownerMiddleName">Middle:</label>
						<s:textfield id="ownerMiddleName" name="owner.person.middleName" cssClass="optionalName"/>
					</li>
					<li>
						<label for="ownerLastName">Last:</label>
						<s:textfield id="ownerLastName" name="owner.person.lastName" cssClass="name required"/>
					</li>
				</ol>
			</li>
			<li>
				<label><span class="redtext">* </span>Address:</label>
				<ccl:address id="ownerAddress" name="owner.person.address" requiredLabel="true"/>
			</li>
			<li>
				<label for="ownerPhone">Phone:</label>
				<s:textfield id="ownerPhone" name="owner.person.homePhone" cssClass="phone"/>
			</li>
			<li>
				<label for="email">Email:</label>
				<s:textfield id="ownerEmail" name="owner.person.email" cssClass="email"/>
			</li>
			<li>
				<label><span class="redtext">* </span>Duration:</label>
				<ol class="fieldGroup">
					<li>
						<label for="ownerStartDate">Start Date:</label>
						<s:date id="ownerStartDateFormatted" name="owner.startDate" format="MM/dd/yyyy" />
						<s:textfield id="ownerStartDate" name="owner.startDate" value="%{ownerStartDateFormatted}" cssClass="required datepicker" maxlength="10"/>
					</li>
					<li>
						<label for="ownerEndDate">End Date:</label>
						<s:date id="ownerEndDateFormatted" name="owner.endDate" format="MM/dd/yyyy" />
						<s:textfield id="ownerEndDate" name="owner.endDate" value="%{ownerEndDateFormatted}" cssClass="datepicker" maxlength="10"/>
					</li>
				</ol>
			</li>
			<li class="submit">
				<s:submit value="Save"/>
				<s:url id="ownerEditCancelUrl" action="owners-list" includeParams="false">
					<s:param name="facilityId" value="facilityId"/>
				</s:url>
				<s:a id="ownerEditCancel" href="%{ownerEditCancelUrl}" cssClass="ajaxify {target: '#ownersSection'}">
					Cancel
				</s:a>
			</li>
		</ol>
	</s:form>
	<div id="ownersListSection">
		<s:include value="owners_table.jsp"/>
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

