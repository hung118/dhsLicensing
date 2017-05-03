<%@ taglib prefix="s" uri="/struts-tags"%>
<ul class="ccl-subnav rule-nav">
	<s:url id="rulesUrl" action="edit-rule" includeParams="false"/>
	<li>
		<s:a cssClass="ajaxify {target: '#rulesBase'}" href="%{rulesUrl}">
			Back to Rule Numbers
		</s:a>
	</li>
</ul>
<s:include value="rule_detail.jsp"/>
<s:include value="rule_section_list.jsp"/>

<fieldset>
	<legend><s:if test="section != null and section.id != null">Edit</s:if><s:else>Create</s:else> Rule Section</legend>
	<s:fielderror/>
	<s:actionerror/>
	<s:form id="sectionForm" action="save-section" method="post" cssClass="ajaxify {target: '#rulesBase'} ccl-action-save">
		<s:hidden name="rule.id"/>
		<s:hidden name="section.id"/>
		<ol class="fieldList">
			<li>
				<label for="sectionNumber">Section: ${rule.number}-</label>
				<s:textfield id="sectionBase" name="section.sectionBase" size="2" maxlength="5"/>-<s:textfield id="sectionNumber" name="section.number" size="2" maxlength="5"/>
			</li>
			<li>
				<label for="sectionName">Section Name:</label>
				<s:textarea id="sectionName" name="section.name" rows="3" cols="50"></s:textarea>
			</li>
			<li>
				<label for="referenceUrl">Reference URL:</label>
				<s:textfield id="referenceUrl" name="section.referenceUrl" size="70"/>
				<s:if test="section.referenceUrl != null">
					<a href="<s:property value="section.referenceUrl"/>" target="_blank">Link</a>
				</s:if>
			</li>
			<li>
				<div style="width:200px;position:relative;float:right;">
					<label for="sectionVersionDate">Version Date:</label>
					<s:date id="versionDateFormatted" name="section.versionDate"
						format="MM/dd/yyyy" />
					<s:property value="%{versionDateFormatted}" />
				</div>
				<div style="width:200px;position:relative;float:right;">
					<label for="sectionCreatedBy">Created By:</label>
					<s:property value="section.createdBy" />
				</div>
			</li>
<%-- 			<li>
				<s:hidden id="sectionFile" name="sectionFileId"/>
			</li>
 --%>		
 			<li class="submit">
				<s:submit value="Save"/>
				<s:url id="sectionEditCancelUrl" action="edit-section" includeParams="false">
					<s:param name="rule.id" value="rule.id"/>
				</s:url>
				<s:a cssClass="ajaxify {target: '#rulesBase'}" href="%{sectionEditCancelUrl}">
					Cancel
				</s:a>
			</li>
		</ol>
	</s:form>
</fieldset>

<script type="text/javascript">
	$("#sectionFile").fileuploader({fieldLabel: "General Section Information"});
</script>