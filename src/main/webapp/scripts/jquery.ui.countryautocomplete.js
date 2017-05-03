(function($) {
	$.widget("ui.countryautocomplete", {
		options: {
			country: null,
			countryName: null,
			selectCountry: null
		},
		
		_create: function() {
			var self = this,
				o = self.options,
				el = self.element.addClass("ui-countryautocomplete-input"),
				hidden = $("<input type='hidden'/>").attr("name", el.attr("name")).addClass("ui-countryautocomplete-country-id"),
				resCon = $("<div></div>").addClass("ui-countryautocomplete-results-container"),
				con = $("<div></div>").addClass("ui-countryautocomplete-container").append(resCon).append(hidden).insertAfter(el);
			
			el.attr("name", el.attr("name") + "-ac");
			
			el.autocomplete({
				source: function(request, response) {
					var opts = self._getAjaxOptions();
					opts.success = function(data) {
						response(data);
					}
					$.ajax(opts);
				},
				minLength: 1,
				appendTo: resCon,
				select: function(event, ui) {
					self._selectCountry(ui.item);
					return false;
				},
				change: function(event, ui) {
					if (!ui.item) {
						if (el.val()) {
							var opts = self._getAjaxOptions();
							opts.success = function(data) {
								self._selectCountry(data[0]);
							}
							$.ajax(opts);
						} else {
							self._clear();
						}
					}
				}
			}).data("autocomplete")._renderItem = function(ul, item) {
				return $("<li></li>").data("item.autocomplete", item)
				.append("<a><strong>" + item.value + "</strong></a>")
				.appendTo(ul);
			};
			
			if (el.val()) {
				var opts = self._getAjaxOptions();
				opts.data.countryName = el.val();
				opts.success = function(data) {
					self._selectCountry(data[0]);
				}
				$.ajax(opts);
			}
		},
		
		destroy: function() {
			var el = this.element.remove("ui-countryautocomplete-input");
			el.val(el.next().find("ui-countryautocomplete-country-id").val());
			el.autocomplete("destroy");
			el.next().remove();
			el.attr("name", el.attr("name").replace(/-ac$/, ""));
		},
		
		_setOption: function(option, value) {
			$.Widget.prototype._setOption.apply(this, arguments);
		},
		
		_selectCountry: function(country) {
			var self = this,
				o = self.options,
				el = self.element,
				hidden = el.next().find(".ui-countryautocomplete-country-id");
			
			if (this.selectCountry) {
				this.selectCountry();
			}
			
			hidden.val(country.value);
			el.val(country.value);
		},
		
		_clear: function() {
			this.element.next().find(".ui-countryautocomplete-country-id").val("");
		},
		
		_getAjaxOptions: function() {
			return {
				url: context + "/country-autocomplete.action",
				dataType: "json",
				data: {
					countryName: this.element.val()
				}
			};
		}
	});
})(jQuery);