<%@ taglib prefix="s" uri="/struts-tags"%>
<ul class="ccl-subnav rule-nav">
	<s:url id="rulesUrl" action="edit-rule" includeParams="false"/>
	<li>
		<s:a cssClass="ajaxify {target: '#rulesBase'}" href="%{rulesUrl}">
			Back to Facility Rule Numbers
		</s:a>
	</li>
	<s:url id="sectionUrl" action="edit-section" includeParams="false">
		<s:param name="rule.id" value="rule.id"/>
	</s:url>
	<li>
		<s:a cssClass="ajaxify {target: '#rulesBase'}" href="%{sectionUrl}">
			Back to Sections
		</s:a>
	</li>
</ul>
<s:include value="rule_detail.jsp"/>
<s:include value="rule_section_detail.jsp"/>
<s:include value="rule_subsection_list.jsp"/>

<div id="subSectionEditForm" style="">
<fieldset>
	<legend><s:if test="subSection != null and subSection.id != null">Edit</s:if><s:else>Create</s:else> Rule</legend>
	<s:fielderror/>
	<s:actionerror/>
	<s:form id="subSectionForm" action="save-subsection" method="post" cssClass="ajaxify {target: '#rulesBase'} ccl-action-save">
		<s:hidden name="rule.id"/>
		<s:hidden name="section.id"/>
		<s:hidden name="subSection.id"/>
		<ol class="fieldList">
			<li>
				<label for="subSectionNumber">Rule Number: ${rule.number}-${section.sectionBase}-${section.number}</label>
				<s:textfield id="subSectionNumber" name="subSection.number"/>
			</li>
			<li>
				<label for="subSectionName">Description:</label>
				<s:textarea id="subSectionName" name="subSection.ruleContent"/>
			</li>
<%-- 
			<li class="checkbox labelWidthMargin">
				<s:checkbox id="followUpNeeded" name="subSection.noFollowUp"/>
				<label for="followUpNeeded">No follow up needed if corrected on site</label>
			</li>
			<li class="checkbox labelWidthMargin">
				<s:checkbox id="paperworkRequired" name="subSection.paperworkRequired"/>
				<label for="paperworkRequired">Paperwork required</label>
			</li>
			<li class="checkbox labelWidthMargin">
				<s:checkbox id="dontIssueFindings" name="subSection.dontIssueFindings"/>
				<label for="dontIssueFindings">Don't issue findings</label>
			</li>
 --%>
			<li>
				<label for="referenceUrl">Reference URL:</label>
				<s:textfield id="referenceUrl" name="subSection.referenceUrl" size="70"/>
				<s:if test="subSection.referenceUrl != null">
					<a href="<s:property value="subSection.referenceUrl"/>" target="_blank">Link</a>
				</s:if>
			</li>

			<li>
				<label for="ruleCategory">Rule Status:</label>
				<s:select id="ruleCategory" name="subSection.category" list="categories" listKey="name()"
						  listValue="label" cssClass="required"/>
			</li>
			
			<li>
				<div style="width:200px;position:relative;float:right;">
					<label for="sectionVersionDate">Version Date:</label>
					<s:date id="versionDateFormatted" name="subSection.versionDate"
						format="MM/dd/yyyy" />
					<s:property value="%{versionDateFormatted}" />
				</div>
				<div style="width:200px;position:relative;float:right;">
					<label for="sectionCreatedBy">Created By:</label>
					<s:property value="subSection.createdBy" />
				</div>
			</li>

<%-- 			
			<li class="twoColumn checkboxList">
				<span class="checkboxListLabel"><strong>Noncompliance Level(s):</strong></span>
				<s:checkboxlist name="formNonComplianceLevels.id" value="%{formNonComplianceLevels.{id}}" list="nonComLevels" listKey="id" listValue="value" template="checkboxlistcolumns"/>
			</li>
			<li class="twoColumn checkboxList">
				<span class="checkboxListLabel"><strong>Finding Category(s):</strong></span>
				<s:checkboxlist name="formFindingCategories.id" value="%{formFindingCategories.{id}}" list="findingCategories" listKey="id" listValue="value" template="checkboxlistcolumns"/>
			</li>
			<li>
				<s:hidden id="textFile" name="textFileId"/>
			</li>
			<li>
				<s:hidden id="rationaleFile" name="rationaleFileId"/>
			</li>
			<li>
				<s:hidden id="enforcementFile" name="enforcementFileId"/>
			</li>
			<li>
				<s:hidden id="infoFile" name="infoFileId"/>
			</li>
 --%>			
			<li class="submit">
				<s:submit value="Save"/>
				<s:url id="subSectionEditCancelUrl" action="edit-subsection" includeParams="false">
					<s:param name="rule.id" value="rule.id"/>
					<s:param name="section.id" value="section.id"/>
				</s:url>
				<s:a cssClass="ajaxify {target: '#rulesBase'}" href="%{subSectionEditCancelUrl}">
					Cancel
				</s:a>
			</li>
		</ol>
	</s:form>
</fieldset>
</div>

<script type="text/javascript">
	$("#textFile").fileuploader({fieldLabel: "Rule Text"});
	$("#rationaleFile").fileuploader({fieldLabel: "Rationale/Explanation"});
	$("#enforcementFile").fileuploader({fieldLabel: "Enforcement"});
	$("#infoFile").fileuploader({fieldLabel: "Assessment Instructions"});
</script>