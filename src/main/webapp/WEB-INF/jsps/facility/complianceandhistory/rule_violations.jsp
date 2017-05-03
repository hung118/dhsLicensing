<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<s:include value="navigation.jsp"><s:param name="selectedTab">ruleViolations</s:param></s:include>
<fieldset>
	<legend>Rule Violations Check</legend>
	Use this check to find if there have been any violations to a specific rule of any facility type. Simply type the rule number in the box
	below.  All rules are available, so remember to search with the correct rule type (i.e. 90 for Licensed Family, 50 for RC, 6 for BCU, ect.).
	Findings that have been rescinded will appear grayed out.<br/><br/>
	<label for="ruleViolationsAC" style="display: inline;"><strong>Rule #: R501-</strong></label>
	<input type="text" id="ruleViolationsAC" class="ruleAC"/>
	<div class="rule-violations-container"></div>
</fieldset>
<script id="rule-item-tmpl" type="text/html">
	{{#hasViolations}}
	<table class="tables">
		<thead>
			<tr>
				<th class="shrinkCol">Inspection Date</th>
				<th>Inspection Type(s)</th>
				<th class="shrinkCol">NC Level</th>
				<th class="shrinkCol">Finding Category</th>
				<th class="shrinkCol">Appeal Date</th>
				<th class="shrinkCol">Rescinded Date</th>
			</tr>
		</thead>
		<tbody>
			{{#violations}}
			<tr class="{{listItemClass}}">
				<td><a href="{{inspectionUrl}}">{{inspectionDate}}</a></td>
				<td><strong>{{primaryType}}</strong>{{#otherTypes}}, {{otherTypes}}{{/otherTypes}}</td>
				<td>{{ncLevel}}</td>
				<td class="shrinkCol">{{findingCategory}}</td>
				<td>{{rescindedDate}}</td>
				<td>{{appealDate}}</td>
			</tr>
			{{/violations}}
		</tbody>
	</table>
	{{/hasViolations}}
	{{^hasViolations}}
		No violations to this rule found.
	{{/hasViolations}}
</script>
<script type="text/javascript">
	$("#ruleViolationsAC").ruleautocomplete({
		selectRule: function(rule) {
			getRuleViolations(facilityId, rule.id);
		}
	});
</script>