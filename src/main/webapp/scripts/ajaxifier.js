var containerSelector = ".yui-content > div:not(.yui-hidden)";
var lastUrl = "";

$(document).ready(function() {
	initLivequery();
	initCacheClearing();
});

function initLivequery() {
	var removeErrors = function() {
		$(".ccl-errors").empty();
	}
	$("a").live("click", removeErrors);
	$("form.ajaxify").live("submit", function() {
		target = getAjaxTarget($(this));
		var beforeSerialize = $(this).metadata().beforeSerialize;
		if (beforeSerialize) {
			beforeSerialize();
		}
		var data = $(this).serializeArray();
		var obj = $(this);
		$.ajax({
			type: "POST",
			url: $(this).attr("action"),
			data: data,
			beforeSend: function() {
				dialogOpen(obj);
			},
			success: function(data) {
				$(target).html(data);
				var input = $(target + " :input").filter(":visible");
				if (input.size() > 0) {
					input.get(0).focus();
				}
				dialogClose();
			}
		});
		return false;
	});
	$("a.ajaxify").live("click", function() {
		var target = getAjaxTarget($(this));
		var obj = $(this);
		var href = $(this).attr("href");
		var onSuccess = $(this).metadata().onSuccess;
		if (obj.hasClass("ccl-action-delete")) {
			$(".ccl-confirm-delete-dialog").dialog({
				resizable: false,
				modal: true,
				closeOnEscape: false,
				buttons: {
					"Delete": function() {
						$(this).dialog("close");
						$.ajax({
							url: href,
							beforeSend: function() {
								dialogOpen(obj);
							},
							success: function(data) {
								if (onSuccess) {
									onSuccess(data);
								} else {
									$(target).html(data);
								}
								dialogClose();
							}
						});
					},
					Cancel: function() {
						$(this).dialog("close");
					}
				}	
			});
		} else {
			$.ajax({
				url: href,
				beforeSend: function() {
					dialogOpen(obj);
				},
				success: function(data) {
					if (onSuccess) {
						onSuccess(data);
					} else {
						$(target).html(data);
						var input = $(target + " :input").filter(":visible");
						if (input.size() > 0) {
							input.get(0).focus();
						}
					}
					dialogClose();
				}
			});
		}
		return false;
	});
	$("button.ajaxify").live("click", function() {
		target = getAjaxTarget($(this));
		var obj = $(this);
		$.ajax({
			url: $(this).metadata().href,
			beforeSend: function() {
				dialogOpen(obj);
			},
			success: function(data) {
				$(target).html(data);
				dialogClose();
			}
		});
	});
	$("input.datepicker").livequery(function() {
		$(this).datepicker({
			changeMonth: true,
			changeYear: true,
			showOtherMonths: true,
			selectOtherMonths: true,
			constrainInput: true,
			dateFormat: 'mm/dd/yy',
			showOn: "button",
			buttonImage: context + "/images/calbtn.gif",
			buttonImageOnly: true});
		$(this).mask("99/99/9999");
	});
	$("input.date").livequery(function() {
		$(this).mask("99/99/9999");
	});
	$("input.phone").livequery(function() {
		$(this).mask("(999) 999-9999");
	});
	$("input.zipCode").livequery(function() {
		$(this).mask("99999?-9999");
	});
	$("input.ssn").livequery(function() {
		$(this).mask("999-99-9999");
	});
	$("input.time").livequery(function() {
		$.mask.definitions['~']='[AaPp]';
		$(this).mask("99:99 ~M");
	});
	$("input.default-rule-selector").livequery(function() {
		$(this).ruleautocomplete({
			excludeInactive: false,
			excludeDontIssueFindings: false
		});
	});
	$("a.ccl-button, input:submit, button").livequery(function() {
		$(this).button();
	});
	$("select.ccl-list-controls-submit, input.ccl-list-controls-submit").live("change", function() {
		$(this).closest("form").submit();
	});
	$(".username").live("click", function() {
		var obj = $(this);
		$.ajax({
			url: context + "/home/userInfo.action",
			beforeSend: function() {
				dialogOpen(obj);
			},
			success: function(data) {
				dialogClose();
				$(".ccl-generic-dialog").html(data).dialog({
					resizable: false,
					modal: true,
					closeOnEscape: true,
					width: 600,
					height: 400,
					title: "User Information",
					beforeClose: function() {
						//clears html error brings back ... specifically css that was loaded.
						$(".ccl-generic-dialog").empty();
					},
					buttons: {}
					});
			},
			error: function(data, status, error) {
				//alert('data: '+data.responseText  +' Status: '+ status +' - Error: '+ error);
				dialogClose(); //close loading dialog
				alert("User Info: " + error);
//				uncomment below if you want the tomcat error to be displayed in dialog
//				$(".ccl-generic-dialog").html(data.responseText).dialog({
//					resizable: false,
//					modal: true,
//					closeOnEscape: true,
//					width: 700,
//					height: 500,
//					title: "User Information",
//					beforeClose: function() {
//						//clears html error brings back ... specifically css that was loaded.
//						$(".ccl-generic-dialog").empty();
//					},
//					buttons: {}
//					});
			}
		});
	});
}

function getAjaxTarget(jqObj) {
	var data = jqObj.metadata();
	var target = containerSelector;
	if (data.target != null) {
		target = data.target;
	}
	return target;
}

function dialogOpen(jqObj) {
	var dialog = ".ccl-loading-dialog";
	if (jqObj != null) {
		if (jqObj.hasClass("ccl-action-save")) {
			dialog = ".ccl-saving-dialog";
		} else if (jqObj.hasClass("ccl-action-delete")) {
			dialog = ".ccl-deleting-dialog";
		}
	}
	
	$(dialog).dialog({width: "auto", closeOnEscape: false, modal: true, resizable: false,
		open: function(event, ui) {
			$(this).parent().children(".ui-dialog-titlebar").hide();
			$(".embedded").css("visibility", "hidden");
		},
		close: function(event, ui) {
			$(".embedded").css("visibility", "visible");
		}
	});
}

function dialogClose(jqObj) {
	if (jqObj) {
		var dialog = ".ccl-loading-dialog";
		if (jqObj != null) {
			if (jqObj.hasClass("ccl-action-save")) {
				dialog = ".ccl-saving-dialog";
			} else if (jqObj.hasClass("ccl-action-delete")) {
				dialog = ".ccl-deleting-dialog";
			}
		}
		$(dialog).dialog("close");
	} else {
		$(".ccl-dialog").dialog("close");
	}
}

function initCacheClearing() {
	$("#clearCacheLink").click(function() {
		$.get($(this).attr("href"), function(data) {
			$.modal(data);
		});
		return false;
	});
}

function loadTemplate(templateId) {
	if (!$.template[templateId]) {
		$.ajax({
			url: context + "/components/template.action",
			async: false,
			dataType: "json",
			data: {templateName: templateId},
			success: function(data) {
				if (data.template) {
					$.template(templateId, data.template);
				}
			}
		});
	}
	return templateId;
}

function noteSuccess(url, noteType) {
	$("#" + noteType + "_noteBase").load(url);
}

function replaceResponse(src, dst) {
	$("#" + src).appendTo("body");
	$("#" + dst).empty();
	$("#" + src).children().appendTo("#" + dst);
	$("#" + src).remove();
}

function changeTab(tab, dataSrc) {
	var oldDataSrc = tab.get("dataSrc");
	tab.set("dataSrc", dataSrc);
	tab.addListener("contentChange", function() {
		tab.set("dataSrc", oldDataSrc);
		tab.removeListener("contentChange");
	});
	tabView.selectTab(tabView.getTabIndex(tab));
}

function parseJsonError(jqXHR) {
	var errorsParsed = $.parseJSON(jqXhr.responseText);
	if (!(errorsParsed instanceof Array)) {
		errorsParsed = new Array(errorsParsed);
	}
	return errorsParsed;
}