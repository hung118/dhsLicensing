<%@ taglib prefix="s" uri="/struts-tags"%>
<fieldset>
	<legend>Welcome Message</legend>
	<s:form id="welcomeMessageForm" action="save-welcome" method="post" cssClass="ajaxify {target: '#welcomeBase'} ccl-action-save">
		<ol class="fieldList">
			<li>
				<label for="messageText">Message:</label>
				<s:textarea id="messageText" name="welcomeMessage" cssClass="tallTextArea"/>
			</li>
			<li class="submit">
				<s:submit value="Save"/>
				<s:url id="welcomeEditCancelUrl" action="view-welcome" includeParams="false"/>
				<s:a cssClass="ajaxify {target: '#welcomeBase'}" href="%{welcomeEditCancelUrl}">
					Cancel
				</s:a>
			</li>
		</ol>
	</s:form>
</fieldset>