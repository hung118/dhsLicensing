var selected = null;

$(document).ready(function() {
	$("#tabs").tabs({
		ajaxOptions: {
			beforeSend: function() {
				dialogOpen();
			},
			success: function() {
				dialogClose();
			}
		}
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
});