$(window).scroll(function(){
	if ($(this).scrollTop() >= 153) {
		$("#leftNav").css({"position":"fixed","top":"0"});
	} else {
		$("#leftNav").css({"position":"absolute","top":""});
	}
});

function posfooter() {
	var dh = 0;
	dh = $("#doc2").outerHeight(true);
	if ($("#ft").css("position") == "fixed") {
		dh += $("#ft").outerHeight(true);
	}
	if (dh > $(window).height()){
		$("#ft").css({"position":"relative"});
	} else {
		$("#ft").css({"position":"fixed"});
	}
}

(function( $, oldHtmlMethod ){
    // Override the core html method in the jQuery object so we can position the footer when the page length changes.
    $.fn.html = function(){
        // Execute the original HTML method using the
        // augmented arguments collection.

        var results = oldHtmlMethod.apply( this, arguments );
        posfooter();
		return results;

    };
})( jQuery, jQuery.fn.html );

$(function (){
	
	// ***************** Footer Positioning ***************** //
	posfooter();
	$(window).resize(function() {
		posfooter();
	});
	
	// ***************** Text Counter for Text Area ***************** //
	
	var tmout;
	function cleanWordClipboard(input) {
		var swapCodes   = new Array(8211, 8212, 8216, 8217, 8220, 8221, 8226, 8230); // dec codes from char at
		var swapStrings = new Array("--", "--", "'",  "'",  '"',  '"',  "*",  "...");
		var output = input;
		for (i = 0; i < swapCodes.length; i++) {
			var swapper = new RegExp("\\u" + swapCodes[i].toString(16), "g"); // hex codes
			output = output.replace(swapper, swapStrings[i]);
		}
		return output;
	}
	
	$('textarea[maxlength]').bind('textchange', function (event, previousText) {
		var that = $(this);
		var maxlen = that.attr('maxlength');
		var curlen;
		var counter = that.parent().find('div.counter');
		var curinput,curoutput;
		if (counter.length <= 0) {
			that.after($('<div class="counter"></div><div class="replacedtext" style="display:none;"></div>'));
			counter = that.parent().find('div.counter');
		}
		curinput = that.val();
		curoutput = cleanWordClipboard(curinput);
		if (curinput != curoutput) {
			that.parent().find('div.replacedtext').html('Some special characters were replaced').fadeIn('fast');
			that.val(curoutput);
		} else {
			if (tmout) {
				clearTimeout(tmout)
			}
			tmout = setTimeout(function(){that.parent().find('div.replacedtext').fadeOut('fast');}, '2000')
		}
		
		curlen = that.val().length;
		counter.html(curlen + "/" + maxlen + " characters remaining");
		if (curlen > maxlen) {
			that.val(that.val().substr(0, maxlen));
		}
	});
});