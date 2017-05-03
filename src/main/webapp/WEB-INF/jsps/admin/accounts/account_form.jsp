<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="ccl" uri="/WEB-INF/tags/ccl-tags.tld"%>
<fieldset>
	<legend><s:if test="user != null and user.id != null">Edit</s:if><s:else>Create</s:else> Account</legend>
	<s:fielderror/>
	<s:form id="accountForm" action="save-account" method="post" cssClass="ajaxify {target: '#accountsBase'} ccl-action-save">
		<s:hidden name="user.id"/>
		<ol class="fieldList">
			<li>
				<label for="email" style="width: auto;"><span class="redtext">* </span>Username (Email):</label>
				<s:textfield id="email" name="user.username" cssClass="required"/>
			</li>
			<li>
				<label><span class="redtext">* </span>Name:</label>
				<ol class="fieldGroup">
					<li>
						<label for="firstName">First Name:</label>
						<s:textfield id="firstName" name="user.person.firstName" cssClass="required name"/>
					</li>
					<li>
						<label for="lastName">Last Name:</label>
						<s:textfield id="lastName" name="user.person.lastName" cssClass="required name"/>
					</li>
				</ol>
			</li>
			<li>
				<label><span class="redtext">* </span>Address:</label>
				<ccl:address id="userAddress" name="user.person.address" requiredLabel="true"/>
			</li>
			<li>
				<label for="workPhone"><span class="redtext">* </span>Work Phone:</label>
				<s:textfield id="workPhone" name="user.person.workPhone" cssClass="phone required"/>
			</li>
			<li>
				<span class="checkboxListLabel"><strong>Roles:</strong></span>
				<span class="twoColumn checkboxList">
					<s:checkboxlist name="formRoles" list="availableRoles" listValue="displayName" template="checkboxlistcolumns"/>
				</span>
			</li>
			<li class="submit">
				<s:submit value="Save"/>
				<s:url id="accountEditCancelUrl" action="accounts-list" includeParams="false"/>
				<s:a cssClass="ajaxify {target: '#accountsBase'}" href="%{accountEditCancelUrl}">
					Cancel
				</s:a>
			</li>
		</ol>
	</s:form>
</fieldset>