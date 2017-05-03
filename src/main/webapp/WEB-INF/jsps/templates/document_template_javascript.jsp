<script id="print-list-tmpl" type="text/html">
	<div class="print-container">
		<div class="print-errors"></div>
		<ul class="print-section-list">
			<li>
				{{#categories}}
					<h2>{{name}}</h2>
					<ul class="print-document-list">
						{{#documents}}
							<li><a href="#" class="print-doc-input-link {document: {{id}} }" title="{{description}}">{{name}}</a></li>
						{{/documents}}
					</ul>
				{{/categories}}
			</li>
		</ul>
	</div>
</script>
<script id="print-input-tmpl" type="text/html">
	<div class="print-container">
		<h2>{{name}} Template Fields</h2>
		<div class="print-errors"></div>
		<form id="print-input-form" action="{{action}}" class="print-form">
			{{#hidden}}
				<input type="hidden" name="{{name}}" value="{{value}}"/>
			{{/hidden}}
			<ol class="fieldList">
				{{#fields}}
					<li>
						{{{.}}}
					</li>
				{{/fields}}
			</ol>
		</form>
		<a href="#" class="print-doc-input-print ccl-button">Print</a> <a href="#" class="print-doc-input-cancel">Cancel</a>
	</div>
</script>
<script id="print-input-textfield-tmpl" type="text/html">
	<label for="{{id}}">{{#required}}<span class="redtext">* </span>{{/required}}{{label}}:</label>
	<input id="{{id}}" type="text" name="{{name}}" value="{{value}}" class="{{cssClass}}"/>
</script>
<script id="print-input-textarea-tmpl" type="text/html">
	<label for="{{id}}">{{#required}}<span class="redtext">* </span>{{/required}}{{label}}:</label>
	<textarea id="{{id}}" name="{{name}}" value="{{value}}" class="{{cssClass}}"/>
</script>
<script id="print-input-select-tmpl" type="text/html">
	<label for="{{id}}">{{#required}}<span class="redtext">* </span>{{/required}}{{label}}:</label>
	<select id="{{id}}" name="{{name}}" class="{{cssClass}}">
		{{#options}}
			<option value="{{value}}">{{label}}</option>
		{{/options}}
	</select>
</script>
<script id="print-input-checkbox-tmpl" type="text/html">
	<label for="{{id}}">{{#required}}<span class="redtext">* </span>{{/required}}{{label}}:</label>
	<input id="{{id}}" type="checkbox" name="{{name}}" value="true" class="{{cssClass}}" {{#value}}checked="checked"{{/value}}/>
</script>
<script id="print-input-radio-tmpl" type="text/html">
	<label>{{#required}}<span class="redtext">* </span>{{/required}}{{label}}:</label>
	<ol class="fieldGroup">
		<li class="radio">
			{{#options}}
				<input id="{{id}}" type="radio" name="{{name}}" value="{{value}}" class="{{cssClass}}"/><label for="{{id}}">{{label}}</label>
			{{/options}}
		</li>
	</ol>
</script>
<script id="print-input-date-tmpl" type="text/html">
	<label for="{{id}}">{{#required}}<span class="redtext">* </span>{{/required}}{{label}}:</label>
	<input id="{{id}}" type="text" name="{{name}}" value="{{value}}" class="datepicker {{cssClass}}"/>
</script>
<script id="print-input-rule-tmpl" type="text/html">
	<label for="{{id}}">{{#required}}<span class="redtext">* </span>{{/required}}{{label}}:</label>
	<input id="{{id}}" type="text" name="{{name}}" value="{{value}}" class="{{cssClass}} default-rule-selector"/>
</script>