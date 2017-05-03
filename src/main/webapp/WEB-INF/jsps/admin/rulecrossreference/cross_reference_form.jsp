<%@ taglib prefix="s" uri="/struts-tags"%>
<s:if test="ref != null and ref.id != null">
	<s:set var="legendText">Edit</s:set>
</s:if>
<s:else>
	<s:set var="legendText">Create</s:set>
</s:else>
<fieldset>
	<legend><s:property value="%{legendText}"/> Rule Cross Reference</legend>
	<s:actionerror/>
	<s:fielderror/>
	<s:form id="ruleCrossReferenceForm" action="save-cross-reference" method="post" cssClass="ajaxify {target: '#ruleCrossReferenceBase'} ccl-action-save">
		<s:hidden name="ref.id"/>
		<ol class="fieldList">
			<li>
				<label for="newRule"><span class="redtext">* </span>New Rule: R501 -</label>
				<s:textfield id="newRule" name="ref.newRule" value="%{ref.newRule.id}" cssClass="required"/>
			</li>
			<li>
				<label for="oldRule"><span class="redtext">* </span>Old Rule: R501 -</label>
				<s:textfield id="oldRule" name="ref.oldRule" value="%{ref.oldRule.id}" cssClass="required"/>
			</li>
			<li class="submit">
				<s:submit value="Save"/>
				<s:url id="cancelUrl" action="cross-reference-list"/>
				<s:a id="cancel" href="%{cancelUrl}" cssClass="ajaxify {target: '#ruleCrossReferenceBase'}">
					Cancel
				</s:a>
			</li>
		</ol>
	</s:form>
</fieldset>
<script type="text/javascript">
	$("#newRule, #oldRule").ruleautocomplete({
		excludeInactive: false
	});
</script>