var Utils = {};

Utils.download = function(url) {
	var iframe = $("#hidden-downloader");
	if (iframe.size() == 0) {
		$("<iframe></iframe>").attr("id", "hidden-downloader").css("visibility", "hidden").appendTo("body");
		iframe = $("#hidden-downloader");
	}
	iframe.attr("src", url);
}

function formatCurrency(amount) {
	var amt = parseFloat(amount);
	if (isNaN(amt)) {
		return '0.00';
	}
	var delimiter = ","; // replace comma if desired
	amount = new String(amt.toFixed(2));
	var a = amount.split('.',2)
	var d = a[1];
	var i = parseInt(a[0]);
	if (isNaN(i)) {
		return '';
	}
	var minus = '';
	if (i < 0) {
		minus = '-';
	}
	i = Math.abs(i);
	var n = new String(i);
	var a = [];
	while(n.length > 3) {
		var nn = n.substr(n.length-3);
		a.unshift(nn);
		n = n.substr(0,n.length-3);
	}
	if (n.length > 0) {
		a.unshift(n);
	}
	n = a.join(delimiter);
	if (d.length < 1) {
		amount = n;
	} else {
		amount = n + '.' + d;
	}
	amount = minus + amount;
	return amount;
}

function formatDate(date) {
	if (date) {
		var d = new Date(date);
		var m = d.getMonth() + 1
		var dateStr = (m < 10 ? "0" : "") + m + "/" + (d.getDate() < 10 ? "0" : "") + d.getDate() + "/" + d.getFullYear();
		return dateStr;
	}
	return null;
}

function formatDateTime(date) {
	if (date) {
		var d = new Date(date);
		var m = d.getMonth() + 1;
		var h = d.getHours();
		if (h > 12) {
			h = h - 12;
		}
		if (h == 0) {
			h = 12;
		}
		var dateStr = (m < 10 ? "0" : "") + m + "/" + (d.getDate() < 10 ? "0" : "") + d.getDate() + "/" + d.getFullYear() + " " + h + ":" + (d.getMinutes() < 10 ? "0" : "") + d.getMinutes() + (d.getHours() < 12 ? "AM" : "PM");
		return dateStr;
	}
	return null;
}

function convertResponseToJson(jqXHR) {
	return $.parseJSON(jqXHR.responseText);
}

function parseJsonResponseForErrors(jqXhr) {
	var errorsParsed = $.parseJSON(jqXhr.responseText);
	if (!(errorsParsed instanceof Array)) {
		errorsParsed = new Array(errorsParsed);
	}
	return errorsParsed;
}

function urlToNameValuePair(url) {
	var pairs = url.substring(url.indexOf('?') + 1).split('&');
	var params = new Array(pairs.length);
	for (var i = 0; i < pairs.length; i++) {
		var pair = pairs[i].split('=');
		params[i] = {
			name: pair[0],
			value: pair[1]
		};
	}
	return params;
}

function loadTemplate(group, tmpl, data, options, callback) {
	var t = $("#" + tmpl);
	if (t.size() == 1) {
		callback(t.tmpl(data, options));
	} else {
		$.get(context + "/template.action?group=" + group, function(response) {
			$("body").append(response);
			callback($("#" + tmpl).tmpl(data, options));
		});
	}
}

function appendUrlParam(url, paramName, paramValue) {
	if (url.indexOf("?") == -1) {
		url = url + "?";
	} else {
		url = url + "&";
	}
	url = url + paramName + "=" + paramValue;
	return url;
}

function equalizeHeights(elems) {
	var tallest = 0;
	elems.each(function () {
		if ($(this).height() > tallest) {
			tallest = $(this).height();
		}
	});
	elems.height(tallest);
}