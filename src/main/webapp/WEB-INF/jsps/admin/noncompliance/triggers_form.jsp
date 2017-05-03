<%@ taglib prefix="s" uri="/struts-tags"%>
<fieldset>
	<legend>Edit Noncompliance Triggers</legend>
	<s:fielderror/>
	<s:form id="ncTriggersForm" action="save-triggers" method="post" cssClass="ajaxify {target: '#ncTriggersSection'} ccl-action-save">
		<ol class="fieldList">
			<li>
				<label for="center-trigger">Center Trigger:</label>
				<s:textfield id="center-trigger" name="centerTrigger" required="true"/>
			</li>
			<li>
				<label for="home-trigger">Home Trigger:</label>
				<s:textfield id="home-trigger" name="homeTrigger" required="true"/>
			</li>
			<li class="submit">
				<s:submit value="Save"/>
				<s:url id="triggersEditCancelUrl" action="edit-triggers" includeParams="false"/>
				<s:a id="triggersEditCancel" href="%{triggersEditCancelUrl}" cssClass="ajaxify {target: '#ncTriggersSection'}">
					Cancel
				</s:a>
			</li>
		</ol>
	</s:form>
</fieldset>