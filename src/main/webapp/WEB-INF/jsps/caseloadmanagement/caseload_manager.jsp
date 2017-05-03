<%@ taglib prefix="s" uri="/struts-tags"%>
<script type="text/javascript">
	var caseloadData = <s:property value="caseloadData" escapeJavaScript="false" escapeHtml="false"/>;
</script>
<fieldset>
	<legend>Transfer Caseload</legend>
<s:form id="caseload-form" action="transfer-caseload" method="post">
	<div class="csld-ctrls clearfix">
		<ol class="csld-ctrl">
			<li><span class="label">From LS:</span> <select id="fromLs" name="fromLS" class="csld-spec"></select></li>
			<li><span class="label">Sort By:</span> <s:select id="fromLsSort" name="fromLsSort" listKey="key" listValue="label" list="sortBys" cssClass="csld-sort"/></li>
			
		</ol>
		<ol class="csld-ctrl">
			<li><span class="label">To LS:</span> <select id="toLs" name="toLS" class="csld-spec"></select></li>
			<li><span class="label">Sort By:</span> <s:select id="toLsSort" name="toLsSort" listKey="key" listValue="label" list="sortBys" cssClass="csld-sort"/></li>
		</ol>
		<div class="csld-ctrl">
			<s:submit value="Transfer"/>
		</div>
	</div>
		
	<div class="csld-print">
		<div><a href="#" class="ccl-button csld-button csld-from">Print Caseload</a></div>
		<div><a href="#" class="ccl-button csld-button csld-to">Print Caseload</a></div>
	</div>
	
	<div class="clearfix csld-list">
		<div class="column from-ls-column">
			<div class="from-caseload-panel caseload-panel">
			
			</div>
		</div>
		<div class="column to-ls-column">
			<ul class="to-caseload-panel caseload-panel">
			
			</ul>
		</div>
	</div>
			
</fieldset>
</s:form>
<script id="caseload-tmpl" type="text/html">
	<h2>Total Facilities: {{length}}</h2>
	<ul>
	{{#caseload}}
		<li class="{{listClass}}">
			{{#checkbox}}
			<input type="checkbox" name="facilities" value="{{id}}" style="float: left;"/>
			<div style="margin-left: 20px">
			{{/checkbox}}
				{{name}} ({{type}})<br/>
				{{city}} {{zipCode}}
			{{#checkbox}}
			</div>
			{{/checkbox}}
		</li>
	{{/caseload}}
	</ul>
</script>