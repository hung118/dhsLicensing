<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="json" uri="http://www.atg.com/taglibs/json" %>
<fieldset>
	<legend>Edit Screening Checks</legend>
	<s:actionerror/>
	<s:fielderror/>
	<s:form id="screeningChecksForm" action="save-screening-checks" method="post" cssClass="ajaxify {target: '#screeningChecksSection', beforeSerialize: function() {serializeCases();}} ccl-action-save">
		<s:hidden name="facilityId"/>
		<s:hidden name="screeningId"/>
		<ol class="fieldList">
			<li><h1>Juvenile Check</h1></li>
			<li>
				<label for="juvScreeningResults"><span class="redtext">* </span>Screening Results:</label>
				<s:select id="juvScreeningResults" name="juvCheck.screeningResult" list="getScreeningResults(juvCheck.screeningCheckType)" listValue="displayName"
						  cssClass="required"/>
			</li>
			<li>
				<label for="juvScreeningCheckDate"><span class="redtext">* </span>Screening Date:</label>
				<s:date id="juvCheckDateFormatted" name="juvCheck.screeningDate" format="MM/dd/yyyy"/>
				<s:textfield id="juvScreeningCheckDate" name="juvCheck.screeningDate" value="%{juvCheckDateFormatted}" cssClass="required datepicker" maxlength="10"/>
			</li>
			<li><h1>Rap Sheet Check</h1></li>
			<li>
				<label for="rapScreeningResults"><span class="redtext">* </span>Screening Results:</label>
				<s:select id="rapScreeningResults" name="rapCheck.screeningResult" list="getScreeningResults(rapCheck.screeningCheckType)" listValue="displayName"
						  cssClass="required"/>
			</li>
			<li>
				<label for="rapScreeningCheckDate"><span class="redtext">* </span>Screening Date:</label>
				<s:date id="rapCheckDateFormatted" name="rapCheck.screeningDate" format="MM/dd/yyyy"/>
				<s:textfield id="rapScreeningCheckDate" name="rapCheck.screeningDate" value="%{rapCheckDateFormatted}" cssClass="required datepicker" maxlength="10"/>
			</li>
			<li><h1>Courts Check</h1></li>
			<li>
				<label for="courtsScreeningResults"><span class="redtext">* </span>Screening Results:</label>
				<s:select id="courtsScreeningResults" name="courtsCheck.screeningResult" list="getScreeningResults(courtsCheck.screeningCheckType)" listValue="displayName"
						  cssClass="required"/>
			</li>
			<li>
				<label for="courtsScreeningCheckDate"><span class="redtext">* </span>Screening Date:</label>
				<s:date id="courtsCheckDateFormatted" name="courtsCheck.screeningDate" format="MM/dd/yyyy"/>
				<s:textfield id="courtsScreeningCheckDate" name="courtsCheck.screeningDate" value="%{courtsCheckDateFormatted}" cssClass="required datepicker" maxlength="10"/>
			</li>
			<li><h1>FBI Check</h1></li>
			<li class="checkbox">
				<s:checkbox id="multiStateOffender" name="multiStateOffender"/>
				<label for="multiStateOffender">This person is a multi-state offender.</label>
			</li>
			<li>
				<label for="fbiScreeningFingerprintsReceivedDate">Fingerprints Received:</label>
				<s:date id="screeningFingerprintsReceivedDateFormatted" name="fbiCheck.fingerprintsReceivedDate" format="MM/dd/yyyy"/>
				<s:textfield id="fbiScreeningFingerprintsReceivedDate" name="fbiCheck.fingerprintsReceivedDate" value="%{screeningFingerprintsReceivedDateFormatted}" cssClass="datepicker" maxlength="10"/>
			</li>
			<li>
				<label for="fbiScreeningSubmittedToFbiDate">Submitted to FBI:</label>
				<s:date id="screeningSubmittedToFbiDateFormatted" name="fbiCheck.submittedToFbiDate" format="MM/dd/yyyy"/>
				<s:textfield id="fbiScreeningSubmittedToFbiDate" name="fbiCheck.submittedToFbiDate" value="%{screeningSubmittedToFbiDateFormatted}" cssClass="datepicker" maxlength="10"/>
			</li>
			<li>
				<label>Rejection:</label>
				<ol class="fieldGroup">
					<li>
						<label for="fbiScreeningFingerprintsRejectedOneDate">Fingerprints Rejected:</label>
						<s:date id="screeningFingerprintsRejectedOneDateFormatted" name="fbiCheck.fingerprintsRejectedOneDate" format="MM/dd/yyyy"/>
						<s:textfield id="fbiScreeningFingerprintsRejectedOneDate" name="fbiCheck.fingerprintsRejectedOneDate" value="%{screeningFingerprintsRejectedOneDateFormatted}" cssClass="datepicker" maxlength="10"/>
					</li>
					<li>
						<label for="fbiScreeningFingerprintsResubmittedOneDate">Fingerprints Resubmitted:</label>
						<s:date id="screeningFingerprintsResubmittedOneDateFormatted" name="fbiCheck.fingerprintsResubmittedOneDate" format="MM/dd/yyyy"/>
						<s:textfield id="fbiScreeningFingerprintsResubmittedOneDate" name="fbiCheck.fingerprintsResubmittedOneDate" value="%{screeningFingerprintsResubmittedOneDateFormatted}" cssClass="datepicker" maxlength="10"/>
					</li>
					<li class="clearLeft fieldMargin">
						<label for="fbiScreeningFingerprintsRejectedTwoDate">Fingerprints Rejected:</label>
						<s:date id="screeningFingerprintsRejectedTwoDateFormatted" name="fbiCheck.fingerprintsRejectedTwoDate" format="MM/dd/yyyy"/>
						<s:textfield id="fbiScreeningFingerprintsRejectedTwoDate" name="fbiCheck.fingerprintsRejectedTwoDate" value="%{screeningFingerprintsRejectedTwoDateFormatted}" cssClass="datepicker" maxlength="10"/>
					</li>
					<li class="fieldMargin">
						<label for="fbiScreeningFingerprintsResubmittedTwoDate">Fingerprints Resubmitted:</label>
						<s:date id="screeningFingerprintsResubmittedTwoDateFormatted" name="fbiCheck.fingerprintsResubmittedTwoDate" format="MM/dd/yyyy"/>
						<s:textfield id="fbiScreeningFingerprintsResubmittedTwoDate" name="fbiCheck.fingerprintsResubmittedTwoDate" value="%{screeningFingerprintsResubmittedTwoDateFormatted}" cssClass="datepicker" maxlength="10"/>
					</li>
				</ol>
			</li>
			<li>
				<label for="fbiScreeningNameSearchSubmittedDate">Name Search Submitted:</label>
				<s:date id="screeningNameSearchSubmittedDateFormatted" name="fbiCheck.nameSearchSubmittedDate" format="MM/dd/yyyy"/>
				<s:textfield id="fbiScreeningNameSearchSubmittedDate" name="fbiCheck.nameSearchSubmittedDate" value="%{screeningNameSearchSubmittedDateFormatted}" cssClass="datepicker" maxlength="10"/>
			</li>
			<li>
				<label for="fbiScreeningReturnedFromFbiDate"><span class="redtext">* </span>Returned From FBI:</label>
				<s:date id="screeningReturnedFromFbiDateFormatted" name="fbiCheck.returnedFromFbiDate" format="MM/dd/yyyy"/>
				<s:textfield id="fbiScreeningReturnedFromFbiDate" name="fbiCheck.returnedFromFbiDate" value="%{screeningReturnedFromFbiDateFormatted}" cssClass="required datepicker" maxlength="10"/>
			</li>
			<li>
				<label for="fbiScreeningResults"><span class="redtext">* </span>Screening Results:</label>
				<s:select id="fbiScreeningResults" name="fbiCheck.screeningResult"
						  list="getScreeningResults(fbiCheck.screeningCheckType)" listValue="displayName" cssClass="required"/>
			</li>
			<li><h1>DCFS Check</h1></li>
			<li>
				<label for="dcfsScreeningResults"><span class="redtext">* </span>Screening Results:</label>
				<s:select id="dcfsScreeningResults" name="dcfsCheck.screeningResult"
						  list="getScreeningResults(dcfsCheck.screeningCheckType)" listValue="displayName" cssClass="required"/>
			</li>
			<li>
				<label for="dcfsScreeningCheckDate"><span class="redtext">* </span>Screening Date:</label>
				<s:date id="screeningCheckDateFormatted" name="dcfsCheck.screeningDate" format="MM/dd/yyyy"/>
				<s:textfield id="dcfsScreeningCheckDate" name="dcfsCheck.screeningDate" value="%{screeningCheckDateFormatted}" cssClass="required datepicker" maxlength="10"/>
			</li>
			<li>
				<label for="dcfsScreeningDcfsEmailedDate">Date DCFS Emailed:</label>
				<s:date id="screeningDcfsEmailedDateFormatted" name="dcfsCheck.dcfsEmailedDate" format="MM/dd/yyyy"/>
				<s:textfield id="dcfsScreeningDcfsEmailedDate" name="dcfsCheck.dcfsEmailedDate" value="%{screeningDcfsEmailedDateFormatted}" cssClass="datepicker" maxlength="10"/>
			</li>
			<li>
				<label>DCFS Cases:</label>
				<s:hidden id="dcfs-case-info" name="dcfsCheck.dcfsCaseInfo"/>
				<ol id="dcfs-cases" class="fieldGroup">
				</ol>
			</li>
			<li>
				<label for="dcfsScreeningDcfsConfirmationDate">DCFS Confirmation Date:</label>
				<s:date id="screeningDcfsConfirmationDateFormatted" name="dcfsCheck.dcfsConfirmationDate" format="MM/dd/yyyy"/>
				<s:textfield id="dcfsScreeningDcfsConfirmationDate" name="dcfsCheck.dcfsConfirmationDate" value="%{screeningDcfsConfirmationDateFormatted}" cssClass="datepicker" maxlength="10"/>
			</li>
			<li><h1>Convictions</h1></li>
			<li>
				<div id="con-container">
				
				</div>
			</li>
			<li class="submit">
				<s:submit value="Save"/>
				<s:url id="screeningCheckEditCancelUrl" action="screening-checks-section" includeParams="false">
					<s:param name="facilityId" value="facilityId"/>
					<s:param name="screeningId" value="screeningId"/>
				</s:url>
				<s:a id="screeningCheckEditCancel" href="%{screeningCheckEditCancelUrl}" cssClass="ajaxify {target: '#screeningChecksSection'}">
					Cancel
				</s:a>
			</li>
		</ol>
	</s:form>
</fieldset>
<s:url id="deleteConvictionUrl" action="delete-conviction" escapeAmp="false" encode="false">
	<s:param name="facilityId" value="facilityId"/>
	<s:param name="screeningId" value="screeningId"/>
</s:url>
<s:set var="conJSON">
	<json:array var="conviction" items="${convictions}">
		<json:object>
			<json:property name="id" value="${conviction.id}"/>
			<json:property name="checkTypeId" value="${conviction.checkTypeId}"/>
			<json:property name="checkTypeValue" value="${conviction.checkTypeValue}"/>
			<json:property name="date"><s:date name="#attr.conviction.date" format="MM/dd/yyyy"/></json:property>
			<json:property name="categoryId" value="${conviction.categoryId}"/>
			<json:property name="categoryValue" value="${conviction.categoryValue}"/>
		</json:object>
	</json:array>
</s:set>
<script id="con-tbl-tmpl" type="text/x-jquery-tmpl">
	<div class="ccl-table-form-container"></div>
	<div class="topControls marginTop">
		<div class="mainControls">
			<a href="#" class="ccl-button ccl-table-row-add">
				New Conviction
			</a>
		</div>
	</div>
	<table id="con-tbl" class="tables ccl-table">
		<thead>
			<tr>
				<th>Screening Check Type</th>
				<th>Date</th>
				<th>Category</th>
				<th></th>
			</tr>
		</thead>
		<tbody id="con-tbl-body" class="ccl-table-body">
			{{tmpl(rowData) '#con-row-tmpl'}}
		</tbody>
	</table>
</script>
<script id="con-row-tmpl" type="text/x-jquery-tmpl">
	<tr class="ccl-table-row">
		<td><a href='#' class='ccl-table-row-edit'>{{= checkTypeValue}}</a></td>
		<td>{{= date}}</td>
		<td>{{= categoryValue}}</td>
		<td><a href='<s:property value='%{deleteConvictionUrl}' escapeHtml='false'/>&conSCType={{= checkTypeId}}&conviction.id={{= id}}' class='ccl-table-row-delete ccl-action-delete'>delete</a></td>
	</tr>
</script>
<script id="con-form-tmpl" type="text/x-jquery-tmpl">
	<s:form id="convictionForm" action="save-conviction" method="post" cssClass="ccl-action-save ccl-table-form">
		<s:hidden name="facilityId"/>
		<s:hidden name="screeningId"/>
		<s:hidden name="conviction.id" value="{{= id}}"/>
		<ol class="fieldGroup">
			<li>
				<label for="conChkType">Conviction Type:</label>
				<select id="conChkType" name="conSCType" class="required">
					<option value="-1">- Select Type -</option>
					<s:iterator value="screeningCheckTypes" var="scType">
						<option value="<s:property/>"{{if checkTypeId}}{{if checkTypeId == '<s:property/>'}} selected="selected"{{/if}}{{/if}}><s:property value="label"/></option>
					</s:iterator>
				</select>
			</li>
			<li>
				<label for="conDate">Conviction Date:</label>
				<s:textfield id="conDate" name="conviction.convictionDate" value="{{= date}}" cssClass="required datepicker" maxlength="10"/>
			</li>
			<li>
				<label for="conCat">Category:</label>
				<select id="conCat" name="conviction.category" class="required">
					<option value="-1">- Select Category -</option
					<s:iterator value="convictionCategories" var="cat">
						<option value="<s:property value="id"/>"{{if categoryId}}{{if categoryId == '<s:property value="id"/>'}} selected="selected"{{/if}}{{/if}}><s:property value="value"/></option>
					</s:iterator>
				</select>
			</li>
			<li>
				<label for="conOther">Other:</label>
				<s:textfield id="conOther" name="conviction.otherCategory" value="{{if categoryId}}{{else}}{{= categoryValue}}{{/if}}"/>
			</li>
			<li>
				<a id="con-form-save" href="#" class="ccl-button ccl-table-row-save">Save</a>
				<a id="con-form-cancel" href="#" class="ccl-table-row-edit-cancel">Cancel</a>
			</li>
		</ol>
	</s:form>
</script>
<script type="text/javascript">
	var data = <s:if test="dcfsCheck.dcfsCaseInfo != null"><s:property value="dcfsCheck.dcfsCaseInfo" escapeJavaScript="false" escapeHtml="false"/></s:if><s:else>{}</s:else>;
	
	if (data.cases && data.cases.length > 0) {
		loadTemplate("screenings", "dcfs-case-row-tmpl", data.cases, null, function(tmpl) {
			tmpl.appendTo("#dcfs-cases");
			$("#dcfs-cases input").bind("blur.dcfs", function() {handleCaseBlur($(this));});
		});
		$(".dcfs-case:first").removeClass("clearLeft").removeClass("fieldMargin");
	}

	addBlankCase(false);

	$("#con-container").cclTable("init", {
		tmplNS: "convictions",
		tableTmpl: "#con-tbl-tmpl",
		rowTmpl: "#con-row-tmpl",
		formTmpl: "#con-form-tmpl",
		rowData : <s:property value="%{conJSON}" escapeHtml="false"/>
	});

	function handleCaseBlur(jqObj) {
		var patt =/[^0-9]/g;
		var numBlank = jqObj.parent().find(".dcfs-case-num").val().length == 0;
		var dtBlank = jqObj.parent().find(".dcfs-start-date").val().replace(patt, "").length < 8;
		var last = jqObj.parent().hasClass("blank-case");
		if (numBlank && dtBlank && !last) {
			jqObj.parent().next().find(".dcfs-case-num").focus();
			jqObj.parent().remove();
			$(".dcfs-case:first").removeClass("clearLeft").removeClass("fieldMargin");
		} else if (!numBlank && !dtBlank && last) {
			addBlankCase(true);
		}
	}

	function addBlankCase(focusBlank) {
		$("#dcfs-cases .dcfs-case:last").removeClass("blank-case");
		loadTemplate("screenings", "dcfs-case-row-tmpl", {caseNumber: null, startDate: null}, {blank: true}, function(tmpl) {
			tmpl.appendTo("#dcfs-cases");
			var lastInput = $("#dcfs-cases .dcfs-case:last input").bind("blur.dcfs", function() {handleCaseBlur($(this));});
			if (focusBlank) {
				lastInput.first().focus();
			}
			
			$(".dcfs-case:first").removeClass("clearLeft").removeClass("fieldMargin");
		});
	}

	function serializeCases() {
		var dcfsCases = new Array();
		$(".dcfs-case").each(function() {
			var num = $(this).find("input.dcfs-case-num").val();
			var dt = $(this).find("input.dcfs-start-date").val();
			if (num.length > 0 && dt.length > 0) {
				dcfsCases[dcfsCases.length] = {caseNumber: num, startDate: dt};
			}
		});

		var caseInfo = {
			cases: dcfsCases
		};

		$("#dcfs-case-info").val(JSON.stringify(caseInfo));
	}
</script>

<script type="text/javascript">
	// *** Short key ctrl-q for current date on fields that ends with 'Date' in id attribute. ***
	$('[id $= "Date"]').keydown(function(e) {
        //CTRL + Q keydown for current date. Key codes: http://www.cambiaresearch.com/articles/15/javascript-char-codes-key-codes
        if(e.ctrlKey && e.keyCode == 81){
            var currentDate = new Date();
            $(this).val(currentDate.format("mm/dd/yyyy"));
        }
    });
</script>
