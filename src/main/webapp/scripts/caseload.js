$(document).ready(function() {
	populateOptionsFromCaseload(caseloadData);
	$("#fromLs").change(function() {
		if ($(this).val() == -1) {
			$(".from-caseload-panel").empty();
		} else {
			getAndRenderCaseload($(".from-caseload-panel"), $(this).val(), $("#fromLsSort").val(), true);
		}
	}).change();
	$("#fromLsSort").change(function() {
		if ($("#fromLs").val() != -1) {
			getAndRenderCaseload($(".from-caseload-panel"), $("#fromLs").val(), $(this).val(), true);
		}
	});
	$("#toLs").change(function() {
		if ($(this).val() == -1) {
			$(".to-caseload-panel").empty();
		} else {
			getAndRenderCaseload($(".to-caseload-panel"), $(this).val(), $("#toLsSort").val());
		}
	}).change();
	$("#toLsSort").change(function() {
		if ($("#toLs").val() != -1) {
			getAndRenderCaseload($(".to-caseload-panel"), $("#toLs").val(), $(this).val(), true);
		}
	});
	$("#caseload-form").submit(function() {
		dialogOpen($(this));
		var data = $(this).serializeArray();
		$.ajax(context + "/caseloadmanagement/transfer-caseload.action", {
			data: data,
			success: function(data, textStatus, jqXHR) {
				$("#fromLs, #toLs").empty();
				populateOptionsFromCaseload(data);
				$("#fromLs").change();
				$("#toLs").change();
				dialogClose();
			},
			error: function(jqXHR, textStatus, errorThrown) {
				alert(errorThrown);
				dialogClose();
			}	
		});
		return false;
	});
	$(".csld-button").click(function() {
		var template = "licensor-caseload";
				
		if ( $(this).hasClass('csld-from')) {
			var specId = $("#fromLs").val();
			var sortBy = $("#fromLsSort").val();
		} else {
			var specId = $("#toLs").val();
			var sortBy = $("#toLsSort").val();
		}
		
		var ajaxOpts = {
			url: context + "/docs/render.action",
			dataType: "json",
			data: {template: template, specId: specId, sortBy: sortBy},
			success: function(data, textStatus, jqXHR) {
				Utils.download(context + "/docs/download-file.action?fileId=" + data.id);
			},
			error: function(jqXHR, textStatus, errorThrown) {
				var output = jQuery.parseJSON(jqXHR.responseText);
				var errors = output.errors;
				var fieldErrors = output.fieldErrors;
				if(errors !== undefined) {	
					alert(errors);
				}
				if(fieldErrors !== undefined) {	
					var specId = fieldErrors.specId;
					if(specId !== undefined) {
						alert(specId);
					} else {
						alert(errorThrown);
					}
				}
			}
		};
		$.ajax(ajaxOpts);
	});
});

function getAndRenderCaseload(jqObj, specialistId, sortBy, checkbox) {
	$.getJSON(context + "/caseloadmanagement/user-caseload.action", {"specialist.id": specialistId, "sortBy": sortBy}, function(data, textStatus, jqXHR) {
		var rendered = Mustache.to_html($("#caseload-tmpl").html(), {"length": data.length, "caseload": data, checkbox: checkbox, listClass: function() {
			if (this.status == 'IN_PROCESS') {
				return "inactive";
			} else {
				return "";
			}
		}});
		jqObj.empty().html(rendered);
	});
}

function populateOptionsFromCaseload(data) {
	populateOptions($("#fromLs"), data, 'ROLE_LICENSOR_SPECIALIST', false, true);
	populateOptions($("#toLs"), data, 'ROLE_LICENSOR_SPECIALIST', true, false);
}

function populateOptions(jqObj, options, roleType, addNoChange, addInactive) {
	var optionTmpl = "<option value=\"{{id}}\">{{name}}{{#count}} ({{count}}) {{/count}}</option>";
	if (addNoChange) {
		jqObj.append(Mustache.to_html(optionTmpl, {id: "-1", name: "-No Change-"}));
	}
	for (var i = 0; i < options.length; i++) {
		if (options[i].roleType == roleType && (options[i].active || (!options[i].active && addInactive && options[i].count > 0))) {
			jqObj.append(Mustache.to_html(optionTmpl, options[i]));
		}
	}
}