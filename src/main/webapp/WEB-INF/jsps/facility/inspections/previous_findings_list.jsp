<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="display" uri="http://displaytag.sf.net"%>
<s:if test="previousFindingsForRule.isEmpty">
	<span class="redtext">There are no previous findings for this rule</span>
</s:if>
<s:else>
	<div class="previousFindingsPanel">
		<h1>Summary of Previous Findings for Rule <s:property value="finding.rule.ruleNumber"/></h1>
		<display:table name="previousFindingsForRule" id="previousFindingsCMP" class="tables">
			<display:column title="Inspection Date">
				<s:date name="#attr.previousFindingsCMP.inspection.inspectionDate" format="MM/dd/yyyy"/>
			</display:column>
			<display:column title="NC Category">
				<s:property value="#attr.previousFindingsCMP.findingCategory.value"/>
			</display:column>
			<display:column title="NC Level">
				<s:property value="#attr.previousFindingsCMP.noncomplianceLevel.value"/>
			</display:column>
			<display:column title="CMP Amount">
				<s:if test="#attr.previousFindingsCMP.cmpAmount != null"><s:text name="format.currency"><s:param name="value" value="#attr.previousFindingsCMP.cmpAmount"/></s:text></s:if>
			</display:column>
		</display:table>
		<h1>Detail of Previous Findings for Rule <s:property value="finding.rule.ruleNumber"/></h1>
		<ol class="searchResults">
			<s:iterator value="previousFindingsForRule" status="row">
				<li class="<s:if test="#row.odd">odd</s:if><s:else>even</s:else> twoColumn">
					<ol class="column">
						<li>
							<div class="label">Inspection Date:</div>
							<div class="value"><s:date name="inspection.inspectionDate" format="MM/dd/yyyy"/></div>
						</li>
						<li>
							<div class="label">Inspection Types:</div>
							<div class="value">
								<strong><s:property value="inspection.primaryInspectionType"/></strong>
								<s:property value="inspection.otherInspectionTypes"/>
							</div>
						</li>
						<li>
							<div class="label">Correction Date:</div>
							<div class="value"><s:date name="correctionDate" format="MM/dd/yyyy"/></div>
						</li>
						<li>
							<div class="label">Corrected on Site:</div>
							<div class="value"><s:property value="@gov.utah.dts.det.ccl.view.YesNoChoice@valueOfBoolean(correctedOnSite).displayName"/></div>
						</li>
						<li>
							<div class="label">Declarative Statement:</div>
							<div class="value description"><s:property value="declarativeStatement"/></div>
						</li>
						<li>
							<div class="label">Additional Information:</div>
							<div class="value description"><s:property value="additionalInformation"/></div>
						</li>
					</ol>
					<ol class="column">
						<li>
							<div class="label">Findings Category:</div>
							<div class="value"><s:property value="findingCategory.value"/></div>
						</li>
						<li>
							<div class="label">NC Level:</div>
							<div class="value"><s:property value="noncomplianceLevel.value"/></div>
						</li>
						<li>
							<div class="label">CMP Amount:</div>
							<div class="value"><s:if test="#attr.cmpAmount != null"><s:text name="format.currency"><s:param name="value" value="cmpAmount"/></s:text></s:if></div>
						</li>
						<li>
							<div class="label">Under Appeal:</div>
							<div class="value"><s:property value="@gov.utah.dts.det.ccl.view.YesNoChoice@valueOfBoolean(underAppeal).displayName"/></div>
						</li>
					</ol>
				</li>
			</s:iterator>
		</ol>
	</div>
</s:else>
<ol class="fieldList">
	<li>
		<label>Compliance:</label>
		<ol class="fieldGroup">
			<li>
				<label for="findingCategory"><span class="redtext">* </span>Findings Category:</label>
				<s:select id="findingCategory" name="finding.findingCategory" value="finding.findingCategory.id" list="findingCategories"
						  listKey="id" listValue="value" headerKey="-1" headerValue="- Select a Category -" cssClass="required"/>
			</li>
			<li>
				<label for="ncLevel"><span class="redtext">* </span>NC Level:</label>
				<s:select id="ncLevel" name="finding.noncomplianceLevel" value="finding.noncomplianceLevel.id" list="nonComplianceLevels"
						  listKey="id" listValue="value" headerKey="-1" headerValue="- Select a NC Level -" cssClass="required"/>
			</li>
		</ol>
	</li>
</ol>