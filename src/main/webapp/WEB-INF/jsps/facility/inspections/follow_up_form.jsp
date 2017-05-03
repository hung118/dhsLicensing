<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="ccl" uri="/WEB-INF/tags/ccl-tags.tld"%>
<fieldset>
	<legend>Follow Ups and Corrections</legend>
	<p>
		The following list shows findings grouped by inspection date that require follow ups.  Please select the findings that this
		inspection is following up on and whether or not the finding was corrected.
	</p>
	<s:form id="followUpForm" action="save-follow-up-matrix" method="post" cssClass="ajaxify {target: '#followUpSection'} ccl-action-save">
		<s:hidden name="facilityId"/>
		<s:hidden name="inspectionId"/>
		<ol class="ccl-insp-foll-up-list">
			<s:set name="inspectionId" value="-1"/>
			<s:set name="rowNum" value="1"/>
			<s:iterator value="matrix">
				<s:if test="#inspectionId == -1">
					<s:property value="%{'<li>'}" escapeHtml="false"/>
					<h3>From <strong><s:property value="inspectionDate"/></strong> <strong><s:property value="primaryInspectionType.value"/></strong><s:iterator value="otherInspectionTypes">, <s:property value="value"/></s:iterator> Inspection</h3>
					<s:property value="%{'<ol>'}" escapeHtml="false"/>
					<s:set name="inspectionId" value="inspectionId"/>
					<s:set name="rowNum" value="1"/>
				</s:if>
				<s:elseif test="!#inspectionId.equals(inspectionId)">
					<s:property value="%{'</ol></li><li>'}" escapeHtml="false"/>
					<h3>From <strong><s:property value="inspectionDate"/></strong> <strong><s:property value="primaryInspectionType.value"/></strong><s:iterator value="otherInspectionTypes">, <s:property value="value"/></s:iterator> Inspection</h3>
					<s:property value="%{'<ol>'}" escapeHtml="false"/>
					<s:set name="inspectionId" value="inspectionId"/>
					<s:set name="rowNum" value="1"/>
				</s:elseif>
				<li class="ccl-checkbox <s:if test="#rowNum % 2 == 0">even</s:if><s:else>odd</s:else> clearfix" style="width: 100%;">
					<div style="width: 65%; float: left;">
						<label for="foll-up-<s:property value="findingId"/>" class="foll-up-lbl"><input id="foll-up-<s:property value="findingId"/>" type="checkbox" name="followUps" value="<s:property value="findingId"/>" class="foll-up" <s:if test="followedUpByThis">checked="checked"</s:if>/> <ccl:rule ruleId="ruleId" format="RNUM - RDESC"/></label>
					</div>
					<div style="width: 35%; float: left;">
						<s:if test="corrected and !correctedByThis">
							Corrected on site but followed up on for maintenance
						</s:if>
						<s:else>
							<span class="find-corr">
								<label for="find-corr-<s:property value="findingId"/>"><input id="find-corr-<s:property value="findingId"/>" type="checkbox" name="corrections" value="<s:property value="findingId"/>" <s:if test="correctedByThis">checked="checked"</s:if>/> Correction verified?</label><br/>
							</span>
						</s:else>
						<s:set name="rowNum" value="#rowNum + 1"/>
					</div>
				</li>
			</s:iterator>
			<s:property value="matrix.empty ? '' : '</ol></li>'" escapeHtml="false"/>
			<li class="submit">
				<s:submit value="Save"/>
				<s:url id="followUpCancelUrl" action="follow-up-section" includeParams="false">
					<s:param name="facilityId" value="facilityId"/>
					<s:param name="inspectionId" value="inspectionId"/>
				</s:url>
				<s:a href="%{followUpCancelUrl}" cssClass="ajaxify {target: '#followUpSection'}">
					Cancel
				</s:a>
			</li>
		</ol>
	</s:form>
</fieldset>
<script type="text/javascript">
	$(".foll-up").change(function() {
		if ($(this).attr("checked")) {
			$(this).closest("li").find(".find-corr").show();
		} else {
			$(this).closest("li").find(".find-corr").hide();
		}
	}).change();
</script>