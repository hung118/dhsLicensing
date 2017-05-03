<%@ taglib prefix="s" uri="/struts-tags"%>
<input type="hidden" class="print-ctx-data" name="docctx" value="doc-ctx.screening"/>
<s:hidden cssClass="print-ctx-attr" name="ds.screening" value="%{screeningId}"/>
<s:if test="screening != null and screening.id != null">
	<s:set var="selectedTab">${param.selectedTab}</s:set>
	<ul class="ccl-subnav">
		<li>
			<s:url id="individualTabUrl" action="tab" namespace="/facility/screenings/individual" includeParams="false">
				<s:param name="facilityId" value="facilityId"/>
				<s:param name="screeningId" value="screeningId"/>
			</s:url>
			<s:a cssClass="ajaxify {target: '#screeningsBase'} %{#attr.selectedTab == 'individual' ? 'selected' : ''}" href="%{individualTabUrl}">
				Individual Information
			</s:a>
		</li>
		<li>
			<s:url id="screeningChecksTabUrl" action="tab" namespace="/facility/screenings/screeningchecks" includeParams="false">
				<s:param name="facilityId" value="facilityId"/>
				<s:param name="screeningId" value="screeningId"/>
			</s:url>
			<s:a cssClass="ajaxify {target: '#screeningsBase'} %{#attr.selectedTab == 'screening-checks' ? 'selected' : ''}" href="%{screeningChecksTabUrl}">
				Screening Checks
			</s:a>
		</li>
		<li>
			<s:url id="completionResolutionTabUrl" action="tab" namespace="/facility/screenings/completion" includeParams="false">
				<s:param name="facilityId" value="facilityId"/>
				<s:param name="screeningId" value="screeningId"/>
			</s:url>
			<s:a cssClass="ajaxify {target: '#screeningsBase'} %{#attr.selectedTab == 'completion' ? 'selected' : ''}" href="%{completionResolutionTabUrl}">
				Completion and Resolution
			</s:a>
		</li>
	</ul>
	<s:if test="#attr.selectedTab != 'individual'">
		<div>
			<fieldset>
				<legend>Individual Information</legend>
				<ol class="fieldList">
					<li>
						<div class="label">Name:</div>
						<div class="value"><s:property value="screening.screenedAsPerson.firstName"/> <s:property value="screening.screenedAsPerson.middleName"/> <s:property value="screening.screenedAsPerson.lastName"/></div>
					</li>
					<li>
						<div class="label">Birthday:</div>
						<div class="value"><s:date name="screening.screenedAsPerson.birthday" format="MM/dd/yyyy"/></div>
					</li>
				</ol>
			</fieldset>
		</div>
	</s:if>
</s:if>