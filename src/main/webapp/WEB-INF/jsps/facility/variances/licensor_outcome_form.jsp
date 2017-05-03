<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<fieldset>
	<legend>Licensor Variance Review Outcome</legend>
	<s:actionerror/>
	<s:fielderror/>
	<s:form id="licensorOutcomeForm" action="save-licensor-outcome" method="post" cssClass="ajaxify {target: '#licensorOutcomeSection'} ccl-action-save">
		<s:hidden name="facilityId"/>
		<s:hidden name="varianceId"/>
		<ol class="fieldList">
			<li>
				<label for="licensorOutcome"><span class="redtext">* </span>Proposed Outcome:</label>
				<ol class="fieldGroup">
					<li class="radio">
						<s:radio id="licensorOutcome" name="variance.licensorOutcome" list="outcomes" listValue="displayName" cssClass="required"/>
					</li>
				</ol>
			</li>
			<li class="clearLeft fieldMargin">
				<label for="licensorResponse"><span class="redtext">* </span>Response:</label>
				<s:textarea id="licensorResponse" name="variance.licensorResponse" cssClass="required" />
				<div id="charCount" class="charCount" style="margin-left:11em;"></div>
			</li>
			<li class="submit">
				<s:submit value="Save"/>
				<s:url id="licensorEditCancelUrl" action="view-licensor-outcome" includeParams="false">
					<s:param name="facilityId" value="facilityId"/>
					<s:param name="varianceId" value="varianceId"/>
				</s:url>
				<s:a href="%{licensorEditCancelUrl}" cssClass="ajaxify {target: '#licensorOutcomeSection'}">
					Cancel
				</s:a>
			</li>
		</ol>
	</s:form>
</fieldset>
<script type="text/javascript">
	$("#licensorResponse").charCounter(4000, {container: "#licensorOutcomeForm #charCount"});
</script>
