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

function initScreeningLetterForm() {
	toggleHiddenScreeningLetterFields();

	$("#lettersLetterType").change(function() {
		toggleHiddenScreeningLetterFields();
	});
}

function toggleHiddenScreeningLetterFields() {
	$(".hiddenScreeningLetterField").hide();
	var letterType = $("#lettersLetterType").children(":selected").val();
	if (letterType != null) {
		if (letterType == "FPI_TX" || letterType == "FPI_FC" || letterType == "FPI_DSPDC") {
			try {
				if ($("#lettersFpiDetails").val() == "") {
					$("#lettersFpiDetails").val("No response to additional information letter due by");
				}
			} catch(e) {
				// wrap in try/catch so javascript doesn't fail if element not found
			}
			$(".screeningLetterFPI").show();
		}
		if (letterType == "FCR_FC" || letterType == "FPF_FC" || letterType == "FPI_FC" || letterType == "MSO_FC") {
			$(".screeningLetterFC").show();
		}
		if (letterType == "NAA") {
			$("#lettersSubmitBtn").val("Save");
		} else {
			$("#lettersSubmitBtn").val("Print");
		}
	}
}
