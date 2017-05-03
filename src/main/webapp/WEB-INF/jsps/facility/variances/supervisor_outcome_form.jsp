<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<fieldset>
	<legend>Licensing Manager Variance Review Outcome</legend>
	<s:actionerror/>
	<s:fielderror/>
	<s:form id="supervisorOutcomeForm" action="save-supervisor-outcome" method="post" cssClass="ajaxify {target: '#supervisorOutcomeSection'} ccl-action-save">
		<s:hidden name="facilityId"/>
		<s:hidden name="varianceId"/>
		<ol class="fieldList">
			<li>
				<label for="supervisorOutcome"><span class="redtext">* </span>Proposed Outcome:</label>
				<ol class="fieldGroup">
					<li class="radio">
						<s:radio id="supervisorOutcome" name="variance.supervisorOutcome" list="outcomes" listValue="displayName" cssClass="required"/>
					</li>
				</ol>
			</li>
			<li class="clearLeft fieldMargin">
				<label for="supervisorResponse"><span class="redtext">* </span>Response:</label>
				<s:textarea id="supervisorResponse" name="variance.supervisorResponse"/>
				<div id="charCount" class="charCount" style="margin-left:11em;"></div>
			</li>
			<li class="submit">
				<s:submit value="Save"/>
				<s:url id="supervisorEditCancelUrl" action="view-supervisor-outcome" includeParams="false">
					<s:param name="facilityId" value="facilityId"/>
					<s:param name="varianceId" value="varianceId"/>
				</s:url>
				<s:a href="%{supervisorEditCancelUrl}" cssClass="ajaxify {target: '#supervisorOutcomeSection'}">
					Cancel
				</s:a>
			</li>
		</ol>
	</s:form>
</fieldset>
<script type="text/javascript">
	$("#supervisorResponse").charCounter(4000, {container: "#supervisorOutcomeForm #charCount"});
</script>
