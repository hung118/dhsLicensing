<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="ccl" uri="/WEB-INF/tags/ccl-tags.tld"%>
<%@ taglib prefix="display" uri="http://displaytag.sf.net"%>
<s:if test="finding != null and finding.id != null">
	<s:set var="legendText">Edit</s:set>
</s:if>
<s:else>
	<s:set var="legendText">Create</s:set>
</s:else>
<s:if test="finding == null or finding.rule == null">
	<s:set var="ruleStyle">style="display: none;"</s:set>
</s:if>
<s:url var="prevFindingsUrl" action="select-rule" namespace="/facility/inspections" escapeAmp="false">
	<s:param name="facilityId" value="facilityId"/>
	<s:param name="inspectionId" value="inspectionId"/>
</s:url>
<script type="text/javascript">
	var rvData = <s:if test="rvJson != null"><s:property value="rvJson" escapeJavaScript="false" escapeHtml="false"/></s:if><s:else>{previousFindings: []}</s:else>;
	var fcs = <s:property value="findingCategoriesJson" escapeJavaScript="false" escapeHtml="false"/>;
	var ncls = <s:property value="noncomplianceLevelsJson" escapeJavaScript="false" escapeHtml="false"/>;

	$("input[name=correctedOnSite]").change(function() {
		if (!$("input[name='finding.rule']").val() || $("input[name=correctedOnSite]:checked").val() == "true") {
			$(".corr-dt").hide();
		} else {
			$(".corr-dt").show();
		}
	}).change();

	$("#ncLevel").change(function() {
		var cmpAmount;
		for (var i = 0; i < ncls.length; i++) {
			if (ncls[i].id == $(this).val()) {
				cmpAmount = ncls[i].cmpAmount;
			}
		}

		if (cmpAmount) {
			$(".cmp-warn-amt").html("$" + formatCurrency(cmpAmount));
		} else {
			$(".cmp-warn-amt").html("--");
		}
	}).change();

	$("#findingCategory").change(function() {
		var displayCmp = false;
		var displayWarning = false;
		
		for (var i = 0; i < fcs.length; i++) {
			if (fcs[i].id == $(this).val()) {
				displayCmp = fcs[i].displayCmp;
				displayWarning = fcs[i].displayWarning;
			}
		}

		if (displayCmp) {
			$(".cmp-amt").show();
		} else {
			$(".cmp-amt").hide();
		}
		if (displayWarning) {
			$(".cmp-warn").show();
		} else {
			$(".cmp-warn").hide();
		}
			
	}).change();

	$("#find-rule").ruleautocomplete({
		facilityId: facilityId,
		inspectionId: $("body").data("inspection.id"),
		<s:if test="finding != null and finding.id != null">
			findingId: <s:property value="finding.id"/>,
		</s:if>
		excludeInactive: true,
		showRuleInfo: true,
		selectRule: function(rule) {
			$(".pf-info").empty();
			$(".pf-loading, .rule-info").show();
			$.getJSON("<s:property value='prevFindingsUrl' escape='false'/>", {ruleId: rule.id}, function(data) {
				$("#ccl-prev-find-tmpl").tmpl(data).appendTo(".pf-info");
				
				var findingCat = $("#findingCategory option:selected").val();
				var ncLev = $("#ncLevel option:selected").val();
				
				$("#findingCategory option[value!='-1']").remove();
				for (var i = 0; i < data.findingCategories.length; i++) {
					$("<option value='" + data.findingCategories[i].key + "'" + (findingCat != null && data.findingCategories[i].key == findingCat ? "selected='selected'" : "") + ">" + data.findingCategories[i].value + "</option>").appendTo("#findingCategory");
				}

				$("#ncLevel option[value!='-1']").remove();
				for (var i = 0; i < data.noncomplianceLevels.length; i++) {
					$("<option value='" + data.noncomplianceLevels[i].key + "'" + (ncLev != null && data.noncomplianceLevels[i].key == ncLev ? "selected='selected'" : "") + ">" + data.noncomplianceLevels[i].value + "</option>").appendTo("#ncLevel");
				}

				$("#findingCategory").change();
				$("#ncLevel").change();
				$("input[name=correctedOnSite]").change();
				
				$(".pf-loading").hide();
			});
		},
		afterInit: function() {
			$("<br/><br/><span class='redtext'><strong>IMPORTANT:</strong> Scroll down to review Rationale, Enforcement and Assessment Instructions before entering a finding.</span>")
				.insertAfter($("#find-rule"));
		}
	});

	$("#ccl-prev-find-tmpl").tmpl(rvData).appendTo(".pf-info");
</script>
<script id="ccl-prev-find-tmpl" type="text/x-jquery-tmpl">
	{{if previousFindings.length > 0}}
		<h1>Summary of Previous Findings for Rule {{= ruleNumber}}</h1>
		<table id="pf-sum" class="tables">
			<thead>
				<tr>
					<th>InspectionDate</th>
					<th>NC Category</th>
					<th>NC Level</th>
					<th>CMP Amount</th>
				</tr>
			</thead>
			<tbody>
				{{each(i, finding) previousFindings}}
					<tr class="{{= i % 2 == 0 ? 'even' : 'odd'}}">
						<td>{{= finding.inspectionDate}}</td>
						<td>{{= finding.findingCategory}}</td>
						<td>{{= finding.noncomplianceLevel}}</td>
						<td>&#36;\${formatCurrency(finding.cmpAmount)}</td>
					</tr>
				{{/each}}
			</tbody>
		</table>
		<h1 class="marginTop">Detail of Previous Findings for Rule {{= ruleNumber}}</h1>
		<ol id="pf-detail" class="ccl-list">
			{{each(i, finding) previousFindings}}
				<li class="ccl-list-item {{= i % 2 == 0 ? 'even' : 'odd'}}">
					<div class="left-column">
						<div><span class="label">Inspection Date:</span> {{= finding.inspectionDate}}</div>
						<div><span class="label">Inspection Types:</span> {{= finding.primaryInspectionType}}{{each(i, type) finding.otherInspectionTypes}}, {{= type}}{{/each}}</div>
						<div><span class="label">Correction:</span> {{= finding.inspectionDate}}</div>
						{{if finding.underAppeal}}<div class="redtext"><strong>Finding Under Appeal</strong></div>{{/if}}
					</div>
					<div class="right-column">
						<div><span class="label">Finding Category:</span> {{= finding.findingCategory}}</div>
						<div><span class="label">NC Level:</span> {{= finding.noncomplianceLevel}}</div>
						<div><span class="label">CMP Amount:</span> &#36;\${formatCurrency(finding.cmpAmount)}</div>
					</div>
					<div class="clear">
						<span class="label">Declarative Statement: </span><span class="description">{{= finding.declarativeStatement}}</span> 
					</div>
					<div>
						<span class="label">Additional Finding Text: </span><span class="description">{{= finding.additionalInformation}}</span>
					</div>
				</li>
			{{/each}}
		</ol>
	{{else}}
		<h1>There are no previous findings for rule {{= ruleNumber}}</h1>
	{{/if}}
</script>
<fieldset>
	<legend><s:property value="%{legendText}"/> Finding</legend>
	<s:actionerror/>
	<s:fielderror/>
	<s:form id="findingForm" action="save-finding" method="post" cssClass="ajaxify {target: '#findingsSection'} ccl-action-save">
		<s:hidden id="findingFacilityId" name="facilityId"/>
		<s:hidden id="findingInspectionId" name="inspectionId"/>
		<s:hidden id="findingId" name="finding.id"/>
		<ol class="fieldList">
			<li>
				<label for="find-rule"><span class="redtext">* </span>Rule #: R430-</label>
				<s:textfield id="find-rule" name="finding.rule" value="%{finding.rule.id}" cssClass="required" tabindex="100"/>
			</li>
			<li class="rule-info" <s:property value="ruleStyle" escape="false"/>>
				Always start with a declarative statement (a statement that the provider was out of compliance). Then enter the details of the
				noncompliance, the specific hazards, and their locations. This information <strong>will</strong> show in a file check.
			</li>
			<li class="rule-info" <s:property value="ruleStyle" escape="false"/>>
				<label for="declarativeStatement"><span class="redtext">* </span>Finding:</label>
				<s:textarea id="declarativeStatement" name="finding.declarativeStatement" cssClass="required" tabindex="101"/>
			</li>
			<li class="rule-info" <s:property value="ruleStyle" escape="false"/>>
				When needed, and using complete sentences, enter staff names, dates of hire, children's names, specific missing immunizations,
				and times and measurements that are not part of the rule. This information <strong>will not</strong> show in a file check.
			</li>
			<li class="rule-info" <s:property value="ruleStyle" escape="false"/>>
				<label for="additionalInformation">Additional Information:</label>
				<s:textarea id="additionalInformation" name="finding.additionalInformation" tabindex="102"/>
			</li>
		</ol>
		<div class="rule-info pf-panel" <s:property value="ruleStyle" escape="false"/>>
			<div class="pf-loading" style="display: none;">
				<s:url var="imagesDir" value="/images" includeParams="false" encode="false"/>
				Loading Previous Findings... <img src="<s:property value="imagesDir"/>/ajax-loader.gif"/>
			</div>
			<div class="pf-info"></div>
		</div>
		<ol class="fieldList">
			<li class="rule-info" <s:property value="ruleStyle" escape="false"/>>
				<label><span class="redtext">* </span>Compliance:</label>
				<ol class="fieldGroup">
					<li>
						<label for="findingCategory"><span class="redtext">* </span>Findings Category:</label>
						<s:select id="findingCategory" name="finding.findingCategory" value="finding.findingCategory.id" list="finding != null and finding.rule != null ? finding.rule.findingCategories : #{}"
								  listKey="id" listValue="value" headerKey="-1" headerValue="- Select a Category -" cssClass="required" tabindex="103"/>
					</li>
					<li>
						<label for="ncLevel"><span class="redtext">* </span>NC Level:</label>
						<s:select id="ncLevel" name="finding.noncomplianceLevel" value="finding.noncomplianceLevel.id" list="finding != null and finding.rule != null ? finding.rule.nonComplianceLevels : #{}"
								  listKey="id" listValue="value" headerKey="-1" headerValue="- Select a NC Level -" cssClass="required" tabindex="104"/>
					</li>
				</ol>
			</li>
			<li class="rule-info" <s:property value="ruleStyle" escape="false"/>>
				<label for="correctedOnSite"><span class="redtext">* </span>Corrected on Site:</label>
				<span class="radio">
					<s:radio id="correctedOnSite" name="correctedOnSite" list="yesNoChoices" listKey="value" listValue="displayName" tabindex="105"/>
				</span>
			</li>
			<li class="cmp-amt rule-info" <s:property value="ruleStyle" escape="false"/>>
				<label for="cmpAmount">CMP Amount:</label>
				$ <s:textfield id="cmpAmount" name="finding.cmpAmount" cssClass="amountField" tabindex="106"/> (If a CMP is assessed)
			</li>
			<li class="corr-dt rule-info" <s:property value="ruleStyle" escape="false"/>>
				<label for="warningCorrectionDate"><span class="redtext">* </span>Correction Date:</label>
				<s:date id="corrDateFormatted" name="finding.warningCorrectionDate" format="MM/dd/yyyy"/>
				<s:textfield id="warningCorrectionDate" name="finding.warningCorrectionDate" value="%{corrDateFormatted}" cssClass="required datepicker" maxlength="10" tabindex="107"/>
			</li>
			<li class="cmp-warn rule-info" <s:property value="ruleStyle" escape="false"/>>
				<label>CMP Warning Amount:</label>
				<div class="value"><span class="cmp-warn-amt"><s:text name="format.currency"><s:param name="value" value="finding.noncomplianceLevel.cmpAmount"/></s:text></span> per item out of compliance</div>
			</li>
			<li class="rule-info submit" <s:property value="ruleStyle" escape="false"/>>
				<s:submit value="Save"/>
				<s:url id="findingEditCancelUrl" action="findings-section" includeParams="false">
					<s:param name="facilityId" value="facilityId"/>
					<s:param name="inspectionId" value="inspectionId"/>
				</s:url>
				<s:a id="findingEditCancel" href="%{findingEditCancelUrl}" cssClass="ajaxify {target: '#findingsSection'}">
					Cancel
				</s:a>
			</li>
		</ol>
	</s:form>
	<div id="findingsListSection">
		<s:include value="findings_list.jsp"/>
	</div>
</fieldset>

<script type="text/javascript">
	// *** Short key ctrl-q for current date on fields that have 'Date' in id attribute. ***
	$('[id $= "Date"]').keydown(function(e) {
        //CTRL + Q keydown for current date. Key codes: http://www.cambiaresearch.com/articles/15/javascript-char-codes-key-codes
        if(e.ctrlKey && e.keyCode == 81){
            var currentDate = new Date();
            $(this).val(currentDate.format("mm/dd/yyyy"));
        }
    });
</script>

