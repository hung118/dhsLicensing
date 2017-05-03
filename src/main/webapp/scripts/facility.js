var selected = null;

$(document).ready(function() {
	var preloadedTab = $("#preloaded-tab");
	$("#tabs").tabs({
		ajaxOptions: {
			beforeSend: function() {
				dialogOpen();
			},
			success: function() {
				dialogClose();
			}
		},
		create: function() {
			if (preloadedTab.size() > 0) {
				$("#tabs").tabs("url", preloadedTab.metadata().index, preloadedTab.metadata().url);
			}
		},
		selected: preloadedTab.size() > 0 ? preloadedTab.metadata().index : 0
	});
	selected = $("#tabs").tabs("option", "selected");

	toggleFacilityTabs();

	$("#tabs ul.ui-tabs-nav a").click(function() {
		var thisIndex = $("#tabs ul.ui-tabs-nav a").index($(this));
		if (thisIndex == selected) {
			$("#tabs").tabs("load", thisIndex);
		} else {
			selected = $("#tabs").tabs("option", "selected");
		}
	});
	
	$(".ccl-so-hist").live("click", function() {
		$(".ccl-generic-dialog").html("<span style=\"margin-right: .5em;\">Loading, please wait...</span><img src=\"" + context + "/images/ajax-loader.gif\" style=\"vertical-align: middle;\"/>").dialog({
			resizable: false,
			modal: true,
			closeOnEscape: true,
			width: 700,
			height: 500,
			title: "Status History",
			buttons: {}
		});

		$.getJSON($(this).attr("href"), function(data) {
			$("#ccl-state-hist-tmpl").tmpl({stateChanges: data}).appendTo($(".ccl-generic-dialog").empty());
		});

		return false;
	});
	
	$(".ccl-state-change").live("click", function() {
		var _href = $(this).attr("href");
		var _idx = _href.indexOf("?");
		
		$("#ccl-state-chg-dialog-tmpl").tmpl({
			formId: $(this).attr("id"),
			action: _idx == -1 ? _href : _href.substring(0, _idx),
			params: _idx == -1 ? {} : urlToNameValuePair(_href.substring(_idx + 1))
		}).appendTo($(".ccl-generic-dialog").empty());
		
		var _form = $(".ccl-generic-dialog form");
		var _title = $(this).metadata().title;
		
		_form.find("#" + $(this).attr("id") + "-note").charCounter(500, {container: ".ccl-generic-dialog form .ccl-char-limit"});
		
		var onSetupCB = $(this).metadata().onSetup;
		if (onSetupCB) {
			onSetupCB();
		}
		
		var onSuccessCB = $(this).metadata().onSuccess;
		
		var _submit = function() {
			$.ajax({
				type: 'POST',
				url: _form.attr("action"),
				data: _form.serialize(),
				dataType: "json",
				beforeSend: function() {
					dialogOpen(_form);
				},
				success: function() {
					if (onSuccessCB) {
						onSuccessCB();
					}
					dialogClose();
				},
				error: function(jqXhr) {
					var errorsParsed = parseJsonResponseForErrors(jqXhr);
					$("#ccl-error-tmpl").tmpl({errors: errorsParsed}).appendTo($(".ccl-generic-dialog .ccl-error-container").empty());
				},
				complete: function() {
					dialogClose(_form);
				}
			});
			return false;
		}
		
		_form.submit(_submit);
		$(".ccl-generic-dialog").dialog({
			resizable: false,
			modal: true,
			closeOnEscape: true,
			width: $(this).metadata().dialogWidth == null ? 400 : $(this).metadata().dialogWidth,
			height: $(this).metadata().dialogHeight == null ? 300 : $(this).metadata().dialogHeight,
			title: _title,
			buttons: {
				"Submit": _submit,
				Cancel: function() {
					$(this).dialog("close");
				}
			}
		});
		
		return false;
	});
	$("#ccl-fac-st-nt").click(function() {
		var _getUrl = $(this).metadata().getUrl;
		var _saveUrl = $(this).metadata().saveUrl;
		var _deleteUrl = $(this).metadata().deleteUrl;
		var _notes;
		var _editingItem;
		
		function _showNotes(data, onLoadCallback) {
			loadTemplate("facility", "ccl-facility-sticky-notes-tmpl", data, null, function(tmpl) {
				tmpl.appendTo($(".ccl-generic-dialog").empty());
				$(".ccl-generic-dialog .ccl-st-nt-save").click(function() {
					_saveNote();
				});
				
				$(".ccl-generic-dialog .ccl-st-nt-edit").click(function() {
					_editNote($(this));
				});
				
				$(".ccl-generic-dialog .ccl-st-nt-delete").click(function() {
					_deleteNote($(this));
				}).button({icons: {primary: "ui-icon-trash"}, text: false});
				
				if (onLoadCallback) {
					onLoadCallback();
				}

				$("#ccl-st-nt-note").focus();
			});
		}
		
		function _editNote(jqObj) {
			_editingItem = jqObj.closest("tr").metadata().id;
			$("#ccl-st-nt-note").val(_notes[_editingItem]);
		}
		
		function _saveNote() {
			var data = {
				facilityId: facilityId,
				note: $("#ccl-st-nt-note").val(),
				id: _editingItem
			};
			$.post(_saveUrl, data, function(data, textStatus, jqXHR) {
				_editingItem = null;
				_notes = data.notes;
				_showNotes(data, null);
			}, "json");
		}
		
		function _deleteNote(jqObj) {
			var data = {
				facilityId: facilityId,
				id: jqObj.closest("tr").metadata().id
			};
			$.post(_deleteUrl, data, function(data, textStatus, jqXHR) {
				_notes = data.notes;
				_showNotes(data, null);
			}, "json");
		}
		
		$.getJSON(_getUrl, function(data) {
			_notes = data.notes;
			_showNotes(data, function() {
				$(".ccl-generic-dialog").dialog({
					resizable: false,
					modal: true,
					closeOnEscape: true,
					width: "auto",
					height: "auto",
					title: "Sticky Notes"
				});
			});
		});
		
		return false;
	});

});

function toggleFacilityTabs() {
	var hide = true;
	var facName = $("#infoFacilityName");
	var primPhone = $("#infoPrimaryPhone");
	var email = $("#infoEmail");
	var mailAddress1 = $("#infoMailAddressOne");
	var mailZipcode = $("#infoMailZipCode");
	var mailCity = $("#infoMailCity");
	var mailState = $("#infoMailState");
	var facType = $("#infoFacilityType");
	if (facName && primPhone && email && mailAddress1 && mailZipcode && mailCity && mailState && facType) {
		if (facName.val() != null && facName.val().trim() != "" &&
			primPhone.val() != null && primPhone.val().trim() != "" &&
			email.val() != null && email.val().trim() != "" &&
			mailAddress1.val() != null && mailAddress1.val().trim() != "" &&
			mailZipcode.val() != null && mailZipcode.val().trim() != "" &&
			mailCity.val() != null && mailCity.val().trim() != "" &&
			mailState.val() != null && mailState.val().trim() != "" &&
			facType.val() != null && facType.val().trim() != "")
		{
			hide = false;
		}
	}
	if (hide == true) {
		$(".facilityTab").hide();
	} else {
		$(".facilityTab").show();
	}
}

function initActionAlert() {
	if (!$("#actionId").val()) {
		$("#action").change(function() {
			var typeId = $(this).val();
			if (typeId == '-1') {
				$(".alertSection").hide();
			} else {
				$.getJSON(context + "/facility/actionlognotes/get-alerts.action", {"facilityId": facilityId, actionTypeId: typeId}, function(data) {
					if (data.alerts != null && data.alerts.length > 0) {
						var alertText = "<ul class='actionAlertsList'>";
						for (var i = 0; i < data.alerts.length; i++) {
							alertText += "<li class='actionAlert'>";
							if (data.alerts[i].alert != null) {
								alertText += "<div class='actionAlertText'>Alert: " + data.alerts[i].alert;
							}
							alertText += "</div><div class='actionAlertRecipient'>Recipient(s): ";
							if (data.alerts[i].type == "FACILITY") {
								alertText += "This facility's " + getArrayAsString(data.alerts[i].roles);
							} else if (data.alerts[i].type == "ROLE") {
								alertText += "People in the role" + (data.alerts[i].roles.length > 1 ? "s " : " ") + getArrayAsString(data.alerts[i].roles);
							} else if (data.alerts[i].type == "PERSON") {
								alertText += getArrayAsString(data.alerts[i].people);
							} else if (data.alerts[i].type == "SELF") {
								alertText += "Yourself";
							}
							alertText += "</div></li>";
						}
						alertText += "</ul>";
						
						$(".alertSection .alertsText").html(alertText);
						$(".alertSection").show();
					} else {
						$(".alertSection").hide();
					}
				});
			}
		});
	}
}

function getArrayAsString(array) {
	var str = array[0];
	if (array.length == 2) {
		str = str + " and " + array[1];
	} else if (array.length > 2) {
		for (var i = 1; i < array.length; i++) {
			str = str + ", ";
			if (i == array.length -1) {
				str = str + "and ";
			}
			str = str + array[i];
		}
	}
	return str;
}

function initInspectionEdit(keepTypeSelected) {
	$("#primaryInspectionType").change(function() {
		var primaryTypeVal = $(this).val();
		$("#secondaryInspectionTypes option:disabled").removeAttr("disabled");
		$("#secondaryInspectionTypes option[value=" + primaryTypeVal + "]").removeAttr("selected").attr("disabled", "disabled");
		if (keepTypeSelected != null && primaryTypeVal != keepTypeSelected) {
			$("#secondaryInspectionTypes option[value=" + keepTypeSelected + "]").attr("selected", "selected");
		}
	}).change();
	if (keepTypeSelected != null) {
		$("#secondaryInspectionTypes").change(function(event) {
			if ($("#primaryInspectionType").val() != keepTypeSelected) {
				$("#secondaryInspectionTypes option[value=" + keepTypeSelected + "]").attr("selected", "selected");
			}
		});
	}
}

function initFindingForm() {
	$("#findingRule").change(function() {
		var facilityId = $("#findingFacilityId").val();
		var inspectionId = $("#findingInspectionId").val();
		var findingId = $("#findingId").val();
		var ruleId = $(this).val();
		$.get(context + "/facility/inspections/select-rule.action", {"facilityId": facilityId, "inspection.id": inspectionId,
				"finding.id": findingId == null ? "" : findingId, "finding.rule": ruleId},
				function(data) {
			$("#previousFindingsSection").html(data).show();
		});
	});
}

function refreshEntireInspection() {
	$.ajax({url: $("body").data("inspection.refreshUrl"),
		success: function(data) {
			$("#inspectionsBase").html(data);
		},
		async: false
	});
}

function refreshInspection(inspectionId) {
	$("#inspectionSection").load(context + "/facility/inspections/view-inspection", {"facilityId": facilityId, "inspectionId": inspectionId});
}

function refreshFindings(inspectionId) {
	$("#findingsSection").load(context + "/facility/inspections/findings-section", {"facilityId": facilityId, "inspectionId": inspectionId});
}

function inspectionApproverCallback() {
	$("#ccl-appr-mgr-tmpl").tmpl().prependTo(".ccl-generic-dialog form .fieldList");
}

function initCmpsTab(outstanding, paidDataOptions, tabUrl) {
	$(document).unbind(".cmp");
	$(document).unbind(".paid-cmp");
	$("#cmps-history-container").cclTable("init", {
		tmplNS: "cmps",
		tableTmpl: "#paid-cmp-table-tmpl",
		rowTmpl: "#paid-cmp-table-row-tmpl",
		dataUrl: paidDataOptions.paidDataUrl,
		formParams: paidDataOptions.paidDataParams,
		tableData: paidDataOptions.tableData,
		rowData: paidDataOptions.rowData,
		showSorting: true,
		showRange: true
	});
	$(".paid-cmp-row-edit").live("click.paid-cmp", function() {
		if ($(this).closest("tr").next().is(".paid-cmp-form-row")) {
			$(this).closest("tr").next().remove();
		} else {
			var tmplItem = $.tmplItem($(this));
			var row = $(this).closest("tr");
			$("<tr class='paid-cmp-form-row'><td class='paid-cmp-form-cell' colspan='6'></td></tr>").insertAfter(row);
			$("#cmp-ledger-tmpl").tmpl(tmplItem.data).appendTo(row.next().find(".paid-cmp-form-cell"));
		}
		return false;
	});
	
	if (outstanding != null) {
		$("#cmp-list-tmpl").tmpl(outstanding).appendTo("#outstand-cmp-container");
	}

	function showCmpForm(jqObj, tmplItem) {
		var formContainer = jqObj.closest(".cmp-ledger").find(".cmp-trans-form-container");
		formContainer.empty();
		var formTmpl = "#cmp-trans-red-form-tmpl";
		if (jqObj.is(".cmp-pmt-add, .cmp-pmt-edit")) {
			formTmpl = "#cmp-trans-pmt-form-tmpl";
		}
		$(formTmpl).tmpl(tmplItem == null ? null : tmplItem.data).appendTo(formContainer);
	}
	
	$(".cmp-red-add, .cmp-pmt-add").live("click.cmp", function() {
		showCmpForm($(this), $.tmplItem($(this)));
		
		return false;
	});
	$(".cmp-red-edit, .cmp-pmt-edit").live("click.cmp", function() {
		showCmpForm($(this), $.tmplItem($(this)));
		return false;
	});
	$(".cmp-trans-edit-cancel").live("click.cmp", function() {
		$(this).closest(".cmp-trans-form-container").empty();
		return false;
	});
	$(".cmp-trans-save").live("click.cmp", function() {
		var wasOutstanding = $.tmplItem($(this).closest(".cmp-ledger")).data.outstanding > 0;
		var formContainer = $(this).closest(".cmp-trans-form-container");
		formContainer.find(".cmp-errors").empty();
		var _form = formContainer.find(".cmp-trans-form");

		dialogOpen($(this));
		$.post(_form.attr("action"), _form.serialize(), function(data) {
			if (data.response == "success") {
				var ledger = formContainer.closest(".cmp-ledger");
				if ((data.item.outstanding == 0 && wasOutstanding) || data.item.outstanding > 0 && !wasOutstanding) {
					$("#cmpsBase").load(tabUrl, function(data) {dialogClose();});
				} else {
					$("#cmp-ledger-tmpl").tmpl(data.item).insertAfter(ledger);
					ledger.remove();
					dialogClose();
				}
			} else {
				$("#cmp-trans-err-tmpl").tmpl(data).appendTo(formContainer.find(".cmp-errors"));
				dialogClose();
			}
		});
		return false;
	});
	$(".cmp-trans-delete").live("click.cmp", function() {
		var _link = $(this);
		var _ledger = $(this).closest(".cmp-ledger");
		var _wasOutstanding = $.tmplItem($(this).closest(".cmp-ledger")).data.outstanding > 0;
		
		var data = {
			url: _link.attr("href"),
			dataType: 'json',
			beforeSend: function() {
				dialogOpen(_link);
			},
			success: function(data) {
				if (data.response == "success") {
					if ((data.item.outstanding == 0 && _wasOutstanding) || data.item.outstanding > 0 && !_wasOutstanding) {
						$("#cmpsBase").load(tabUrl, function(data) {dialogClose();});
					} else {
						$("#cmp-ledger-tmpl").tmpl(data.item).insertAfter(_ledger);
						_ledger.remove();
						dialogClose();
					}
				}
			}
		};
		
		$(".ccl-confirm-delete-dialog").dialog({
			resizable: false,
			modal: true,
			closeOnEscape: false,
			buttons: {
				"Delete": function() {
					$(this).dialog("close");
					$.ajax(data);
				},
				Cancel: function() {
					$(this).dialog("close");
				}
			}	
		});
		
		return false;
	});
}

function initScreeningForm() {
	toggleHiddenScreeningFields();
	
	$("#incompleteForm").click(function() {
		toggleHiddenScreeningFields();
	});
}

function toggleHiddenScreeningFields() {
	if ($("#incompleteForm").attr("checked")) {
		$(".hiddenScreeningField").show();
	} else {
		$(".hiddenScreeningField").hide();
	}
}

function initResolutionOfDenialForm() {
	toggleHiddenAppealFields();
	
	$("#appealRequestDate").change(function() {
		toggleHiddenAppealFields();
	});
}

function toggleHiddenAppealFields() {
	if ($("#appealRequestDate").val().length > 0) {
		$(".hiddenAppealField").show();
	} else {
		$(".hiddenAppealField").hide();
	}
}

function setupComplaintIntakeNorm() {
	$("<li>If you would like to give the complaint investigator any additional information you can enter it in the note below.</li>").prependTo(".ccl-generic-dialog form .fieldList");
}

function refreshComplaint() {
	$.ajax({url: $("body").data("complaint.refreshUrl"),
		success: function(data) {
			$("#complaintsBase").html(data);
		},
		async: false
	});
}

function initComplainantForm() {
	toggleComplainantFields();
	
	$("#name-refused, #name-confidential").change(function() {
		toggleComplainantFields();
	});
}

function toggleComplainantFields() {
	if ($("#name-refused").attr("checked")) {
		$(".nm-given").hide();
		$(".nm-confidential").hide();
		$(".nm-refused").show();
	} else if ($("#name-confidential").attr("checked")) {
		$(".nm-refused").hide();
		$(".nm-given").show();
		$(".nm-confidential").show();
	} else {
		$(".nm-confidential").hide();
		$(".nm-refused").hide();
		$(".nm-given").show();
	}
}

function serializeComplainant() {
	var form = $(".ccl-generic-dialog form");
	var params = $("#complainantForm").serializeArray();
	for (i = 0; i < params.length; i++) {
		if (params[i].name != "facilityId" && params[i].name != "complaintId") {
			$("<input type='hidden' name='" + params[i].name + "' value='" + params[i].value + "'/>").appendTo(form);
		}
	}
}

function complaintScreeningCallback() {
	$("#ccl-compl-scrn-tmpl").tmpl().prependTo(".ccl-generic-dialog form .fieldList");
	
	toggleProceedFields();
	
	$(".ccl-generic-dialog input[name=screening.proceedWithInvestigation]:radio").change(function() {
		toggleProceedFields();
	});
}

function toggleProceedFields() {
	var checkedProceed = $(".ccl-generic-dialog input[name=screening.proceedWithInvestigation]:checked");
	if (checkedProceed.size() == 0) {
		$("#proceedWithInvestigationfalse").attr('checked',true);
		$(".dontProceedField").show();
		$(".proceedField").hide();
	} else if (checkedProceed.attr("value") == "false") {
		$(".dontProceedField").show();
		$(".proceedField").hide();
	} else {
		$(".dontProceedField").hide();
		$(".proceedField").show();
	}
}

function initVarianceOutcomeForm() {
	toggleHiddenVarianceOutcomeFields();
	
	$("input[name=variance.outcome]:radio").change(function() {
		toggleHiddenVarianceOutcomeFields();
	});
}

function toggleHiddenVarianceOutcomeFields() {
	var checkedOutcome = $("input[name='variance.outcome']:checked");
	if (checkedOutcome.size() == 0 || checkedOutcome.val() == "NOT_NEEDED") {
		$(".hiddenVarianceApprovedField").hide();
	} else if (checkedOutcome.val() == "APPROVED") {
		$(".hiddenVarianceApprovedField").show();
	} else {
		$(".hiddenVarianceApprovedField").hide();
	}
}

function refreshIncident() {
	$.ajax({url: $("body").data("incident.refreshUrl"),
		success: function(data) {
			$("#incidentsBase").html(data);
		},
		async: false
	});
}

function getRuleViolations(facilityId, ruleId) {
	$.getJSON(context + "/facility/complianceandhistory/rule-violations.action", {facilityId: facilityId, ruleId: ruleId}, function(data) {
		for (var i = 0; i < data.length; i++) {
			data[i].rescindedDate = formatDate(data[i].rescindedDate);
			data[i].appealDate = formatDate(data[i].appealDate);
			data[i].inspectionDate = formatDate(data[i].inspectionDate);
			data[i].listItemClass = i % 2 == 0 ? "odd" : "even";
			if (data[i].rescindedDate) {
				data[i].listItemClass += " inactive";
			}
		}
		
		var rendered = Mustache.to_html($("#rule-item-tmpl").html(), {hasViolations: data.length > 0, violations: data, inspectionUrl: function() {
			return context + "/facility/open-tab.action?facilityId=" + facilityId + "&tab=inspections&act=edit-inspection-record&ns=/inspections&inspectionId=" + this.inspectionId;
		}});
		$(".rule-violations-container").empty().html(rendered);
	});
}

function getTotalFee() {
	var basedFee = $("select#transactionFee option:selected").text()
	basedFee = isNaN(basedFee) ? 0 : parseFloat(basedFee);
	var quantity = $("input#transactionQuantity").val();
	quantity = (quantity=="") ? 0 : parseInt(quantity);
	var rate = $("input#transactionRate").val();
	rate = (rate=="") ? 0 : parseFloat(rate);
	var totalFee = basedFee + quantity * parseFloat(rate);
	$("span#transactionTotalFee").text("$" + formatCurrency(totalFee));
}
