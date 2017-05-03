/**
 *
 * Copyright (c) 2007 Tom Deater (http://www.tomdeater.com)
 * Project site http://www.tomdeater.com/jquery/character_counter/
 * Licensed under the MIT License:
 * http://www.opensource.org/licenses/mit-license.php
 * 
 * 
 * Copyright (c) 2009 Todd Horst (http://www.managingmeals.com)
 * Project site  http://managingmeals.com/blog/?p=138
 * Licensed under the MIT License:
 * http://www.opensource.org/licenses/mit-license.php
 * 
 */
 
(function($) {
	/**
	 * attaches a character counter to each textarea element in the jQuery object
	 * usage: $("#myTextArea").charCounter(max, settings);
	 */
	
	$.fn.charCounter = function (max, settings) {
		max = max || 100;
		settings = $.extend({
			container: "<span></span>",
			classname: "charcounter",
			format: "(%1 characters remaining)",
			pulse: true,
			delay: 0
		}, settings);
		var p, timeout;
		var isCtrl = false;

		function count(el, container) {
			el = $(el);
			if (el.val().length > max) {
			    el.val(el.val().substring(0, max));
			    if (settings.pulse && !p) {
			    	pulse(container, true);
			    };
			};
			if (settings.delay > 0) {
				if (timeout) {
					window.clearTimeout(timeout);
				}
				timeout = window.setTimeout(function () {
					container.html(settings.format.replace(/%1/, (max - el.val().length)));
				}, settings.delay);
			} else {
				container.html(settings.format.replace(/%1/, (max - el.val().length)));
			}
		};
		
		function pulse(el, again) {
			if (p) {
				window.clearTimeout(p);
				p = null;
			};
			el.animate({ opacity: 0.1 }, 100, function () {
				$(this).animate({ opacity: 1.0 }, 100);
			});
			if (again) {
				p = window.setTimeout(function () { pulse(el) }, 200);
			};
		};
		
		function remCTRL(e) {
			if(e.which == 17) isCtrl=false;
		}

		function keyBLOCK(e,el) {
			el = $(el);
			if (el.val().length >= max) {
				if(e.which == 17) {
					isCtrl=true;
					return true;
				} else if(e.which == 65 && isCtrl == true) {
					return true;//allow ctrl+a (select all)
				} else if(e.which == 88 && isCtrl == true) {
					return true;//allow ctrl+x (cut)
				} else if(e.which == 67 && isCtrl == true) {
					return true;//allow ctrl+c (copy)
				} else if(e.which == 90 && isCtrl == true) {
					return true;//allow ctrl+z (undo)
				} else if(e.which == 8 || e.which == 46 || e.which == 37 || e.which == 38 || e.which == 39 || e.which == 40) {
					return true;//up, down, left, right, delete, backspace
				} else {
					return false;//Block any other characters
				}
			} else {
				return true;
			}
		}

		return this.each(function () {
			var container = (!settings.container.match(/^<.+>$/)) 
				? $(settings.container) 
				: $(settings.container)
					.insertAfter(this)
					.addClass(settings.classname);
			$(this)
				.bind("keydown", function (e) { return keyBLOCK(e,this); })
				.bind("keyup", function (e) { remCTRL(e); })
				.bind("focus", function () { count(this, container); })
				.bind("mouseover", function () { count(this, container); })
				.bind("mouseout", function () { count(this, container); })
				.bind("paste", function () { 
					var me = this;
					setTimeout(function () { count(me, container); }, 10);
				});
			if (this.addEventListener) {
				this.addEventListener('input', function () { count(this, container); }, false);
			};
			count(this, container);
		});
	};
})(jQuery);