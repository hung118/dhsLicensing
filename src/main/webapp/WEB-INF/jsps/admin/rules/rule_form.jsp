<%@ taglib prefix="s" uri="/struts-tags"%>
<s:include value="rule_list.jsp"/>
<fieldset>
	<legend><s:if test="rule != null and rule.id != null">Edit</s:if><s:else>Create</s:else>  Rule</legend>
	<s:fielderror/>
	<s:actionerror/>
	<s:form id="ruleForm" action="save-rule" method="post" cssClass="ajaxify {target: '#rulesBase'} ccl-action-save">
		<s:hidden name="rule.id"/>
		<ol class="fieldList">
 			<li>
				<label for="ruleNumber">Rule #:</label>
				<s:textfield id="ruleNumber" name="rule.number"/>
			</li>
			<li>
				<label for="ruleName">Name:</label>
				<s:textfield id="ruleName" name="rule.name" size="70"/>
			</li>
			<li>
				<label for="referenceUrl">Reference URL:</label>
				<s:textfield id="referenceUrl" name="rule.referenceUrl" size="70"/>
				<s:if test="rule.referenceUrl != null">
					<a href="<s:property value="rule.referenceUrl"/>" target="_blank">Link</a>
				</s:if>
			</li>
			<ol class="fieldList" style="border:1px lightblue solid">
				<li>
					<label for="downloadUrl">Download URL:</label>
					<s:textfield id="downloadUrl" name="rule.downloadUrl" size="70"/>
				</li>
				<li>
					<label for="downloadFrequency">Download Frequency:</label>
					<span class="radio">
						<s:radio id="downloadFrequency" name="rule.downloadFrequency" list="downloadTimes" listKey="character" listValue="label"/>
						</span>
				</li>
				<li>
					<label for="lastDownload">Last Download:</label>
					<div id="lastDownload" style="padding: 0.37em 0.4em;">
						<s:date name="rule.lastDownload" format="MM/dd/yyyy hh:mm a"/> 
					</div>	
				</li>
			</ol>		
			<li class="submit">
				<s:submit value="Save"/>
				<s:url id="ruleEditCancelUrl" action="edit-rule" includeParams="false"/>
				<s:a cssClass="ajaxify {target: '#rulesBase'}" href="%{ruleEditCancelUrl}">
					Cancel
				</s:a>
			</li>
		</ol>
		<s:hidden name="rule.facilityType" />
	</s:form>
</fieldset>
