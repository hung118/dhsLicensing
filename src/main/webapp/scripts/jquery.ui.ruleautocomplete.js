(function($) {
	$.widget("ui.ruleautocomplete", {
		options: {
			inspectionId: null,
			findingId: null,
			excludeInactive: true,
			showRuleInfo: false,
			selectRule: null,
			afterInit: null
		},
		
		_selected: null,
		
		_create: function() {
			var self = this,
				o = self.options,
				el = self.element.addClass("ui-ruleautocomplete-input"),
				hidden = $("<input type='hidden'/>").attr("name", el.attr("name")).addClass("ui-ruleautocomplete-rule-id"),
				resCon = $("<div></div>").addClass("ui-ruleautocomplete-results-container"),
				con = $("<div><div class='ui-ruleautocomplete-rule-text-container'></div></div>").addClass("ui-ruleautocomplete-container").append(resCon).append(hidden).insertAfter(el);
			
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
					self._selectRule(ui.item);
					return false;
				},
				change: function(event, ui) {
					if (!ui.item) {
						if (el.val()) {
							if (el.val() != self._selected) {
								var opts = self._getAjaxOptions();
								opts.success = function(data) {
									if (data.length == 1) {
										self._selectRule(data[0]);
									} else {
										self._clear();
										el.val("Invalid Rule Number").addClass("inputerror");
									}
								}
								$.ajax(opts);
							}
						} else {
							self._clear();
						}
					}
				},
				create: function(event, ui) {
					if (el.val()) {
						var opts = self._getAjaxOptions();
						opts.data.ruleId = el.val();
						opts.success = function(data) {
							self._selectRule(data[0]);
						}
						$.ajax(opts);
					}
					
					if (o.afterInit) {
						o.afterInit();
					}
				}
			}).data("autocomplete")._renderItem = function(ul, item) {
				return $("<li></li>").data("item.autocomplete", item)
				.append("<a><strong>" + item.ruleNumber + "</strong>: " + item.description + "</a>")
				.appendTo(ul);
			};
		},
		
		destroy: function() {
			var el = this.element.remove("ui-ruleautocomplete-input");
			el.val(el.siblings(".ui-ruleautocomplete-container").find("ui-ruleautocomplete-rule-id").val());
			el.autocomplete("destroy");
			el.siblings(".ui-ruleautocomplete-container").remove();
			el.attr("name", el.attr("name").replace(/-ac$/, ""));
		},
		
		_setOption: function(option, value) {
			$.Widget.prototype._setOption.apply(this, arguments);
			
			var el = this.element;
			switch(option) {
				case "selectedRuleId":
					
					break;
			}
		},
		
		_selectRule: function(rule) {
			var self = this,
				o = self.options,
				el = self.element,
				hidden = el.siblings(".ui-ruleautocomplete-container").find(".ui-ruleautocomplete-rule-id");
			
			self._selected = rule.ruleNumber;
			
			el.removeClass("inputerror");
			hidden.val(rule.id);
			el.val(rule.ruleNumber);
			
			el.siblings(".ui-ruleautocomplete-container").find(".ui-ruleautocomplete-rule-details").remove();
			if (o.showRuleInfo) {
				$(".ui-ruleautocomplete-rule-text-container").html(rule.ruleText);
			}
			
			if (o.selectRule) {
				o.selectRule(rule);
			}
		},
		
		_clear: function() {
			this.element.val("").removeClass("inputerror");
			this.element.siblings(".ui-ruleautocomplete-container").find(".ui-ruleautocomplete-rule-id").val("");
			$(".ui-ruleautocomplete-rule-text-container").html("");
		},
		
		_getAjaxOptions: function() {
			return {
				url: context + "/components/rule-autocomplete.action",
				dataType: "json",
				data: {
					ruleNumber: this.element.val(),
					inspectionId: this.options.inspectionId,
					findingId: this.options.findingId,
					excludeInactive: this.options.excludeInactive
				}
			};
		}
	});
})(jQuery);