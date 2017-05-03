<%@ taglib prefix="s" uri="/struts-tags"%>
<style>
	#homeUser #homeUserRoles .ccl-list-item {
		border: 1px solid #A0A0A0;
		float: left;
		margin: 2px;
		padding: 1px 20px;
	}
	
	#homeUser .ccl-list > li {
		border-top: 1px ridge #A0A0A0;
		padding: .5em;
	}
	
	#homeUser .ccl-list {
		margin: 0.3em -.1em;
	}
	
</style>
<div id="homeUser">
	<fieldset>
		<legend>General</legend>

		<div id="homeUserInfo">
			<ol class="ccl-list">
				<%-- <li class="ccl-list-item"> <span class="label">ID:</span> <s:property value="user.person.id"/></li> --%>
				<li class="ccl-list-item"><span class="label">Name:</span> <s:property value="user.person.firstAndLastName"/></li>
				<li class="ccl-list-item"><span class="label">Username:</span> <s:property value="user.username"/></li>
				<s:if test="user.person.workPhone != null">
					<li class="ccl-list-item">
						<span class="label">Work Phone:</span> <s:property value="user.person.workPhone"/>
					</li>
				</s:if>
			</ol>
		</div>
	</fieldset>

	<fieldset>
		<legend>Roles</legend>
		<div id="homeUserRoles">
			<ol class="ccl-list">
				<s:iterator value="user.roles">
					<li class="ccl-list-item"><s:property/></li>
				</s:iterator>
			</ol>
		</div>
	</fieldset>
</div>