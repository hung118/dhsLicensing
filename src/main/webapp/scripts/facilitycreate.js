$(document).ready(function() {
	$("input[name='facilitySubType']:radio").change(function() {
		toggleSubTypeChange()
	});
	toggleSubTypeChange();
       
	$("#locationAddressaddressOne").change(function() {
		$("#mailingAddressaddressOne").val($(this).val());
	});
	$("#locationAddressaddressTwo").change(function() {
		$("#mailingAddressaddressTwo").val($(this).val());
	});
	$("#locationAddresszipCode").change(function() {
		$("#mailingAddresszipCode").val($(this).val()).change();
	});
	$("#locationAddresscity").change(function() {
		$("#mailingAddresscity").val($(this).val()).change();
	});
	$("#locationAddressstate").change(function() {
		$("#mailingAddressstate").val($(this).val()).change();
	});
	
	$("#exemptionButton").click(function() {
		$.getJSON(context + '/facility/create-add-exemption.action',
				{exeId: $("#exeId").val(), exeStartDate: $("#exeStartDate").val(), exeExpDate: $("#exeExpDate").val()},
				function(data) {
			handleResponse(data, handleAdd, handleError);
		});
		return false;
	});
	
	//$.getJSON(context + '/facility/create-list-exemptions.action', function(data) { handleResponse(data, handleList, null)});
});

function addRow(exemption) {
	var clone = $("#exemptionsTable .ccl-tmpl").clone();
	clone.removeClass("ccl-tmpl").addClass("exe-" + exemption.id);
	clone.find(".exeVal").html(exemption.exeVal);
	clone.find(".exeStartDt").html(exemption.startDt);
	clone.find(".exeExpDt").html(exemption.expDt);
	var delLink = clone.find(".exeDel");
	delLink.attr("href", delLink.attr("href") + "?exeId=" + exemption.id);
	delLink.click(function() {
		$.getJSON(context + '/facility/create-delete-exemption.action', {exeId: exemption.id}, function(data) {
			handleResponse(data, handleDelete, null);
		});
		return false;
	});
	clone.appendTo("#exemptionsTable tbody");
}

function removeRow(id) {
	$("#exemptionsTable tbody tr.exe-" + id).remove();
}

function handleResponse(data, successCallback, errorCallback) {
	$("#exemptionFields :input").removeClass("inputerror");
	if (data.response == "success") {
		$("#exemptionErrors").empty().hide();
		if (successCallback != null) {
			successCallback(data);
		}
	} else {
		if (errorCallback != null) {
			errorCallback(data);
		} else {
			alert("An unknown error has occurred.");
		}
	}
}

var handleAdd = function(data) {
	addRow(data.exemption);
	$("#exemptionsTable").show();
	$("#exemptionFields :input").val("");
}

var handleDelete = function(data) {
	removeRow(data.exeId);
	if ($("#exemptionsTable tbody tr").size() == 0) {
		$("#exemptionsTable").hide();
	}
}

var handleList = function(data) {
	$("#exemptionsTable tbody").empty();
	if (data.exemptions != null) {
		for (var i = 0; i < data.exemptions.length; i++) {
			addRow(data.exemptions[i]);
		}
	}
	if ($("#exemptionsTable tbody tr").size() > 0) {
		$("#exemptionsTable").show();
	} else {
		$("#exemptionsTable").hide();
	}
}

var handleError = function(data) {
	if (data.response == "validation-error") {
		var html = "<ul class='errorList'>";
		for (var key in data.valErrors) {
			$("#exemptionFields :input[name='" + key + "']").addClass("inputerror");
			var arr = data.valErrors[key];
			for (var i = 0; i < arr.length; i++) {
				html += "<li>" + arr[i] + "</li>";
			}
		}
		html += "</ul>";
		$("#exemptionErrors").html(html).show();
	} else {
		alert("An unknown error occurred processing this request.");
	}
}

function toggleSubTypeChange() {
	var values = new Array("licensed", "exempt");
	var checked = $("input[name='facilitySubType']:radio:checked");
	var selector = "";
	for (i = 0; i < values.length; i++) {
		selector += "." + values[i];
		if (i + 1 < values.length) {
			selector += ", ";
		}
	}
	$(selector).hide();
	$("." + checked.val()).show();
	
	for (i = 0; i < values.length; i++) {
		$("." + values[i] + "-required span.redtext").hide();
		$("." + values[i] + "-required .required").addClass("notrequired").removeClass("required");
	}
	$("." + checked.val() + "-required span.redtext").show();
	$("." + checked.val() + "-required .notrequired").addClass("required").removeClass("notrequired");
}